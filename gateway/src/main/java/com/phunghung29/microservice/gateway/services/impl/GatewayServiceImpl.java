package com.phunghung29.microservice.gateway.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.dto.LoginRequestDTO;
import com.phunghung29.microservice.gateway.entities.Role;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.entities.UserSession;
import com.phunghung29.microservice.gateway.exceptions.ExceptionCode;
import com.phunghung29.microservice.gateway.exceptions.InternalServerException;
import com.phunghung29.microservice.gateway.exceptions.NotFoundException;
import com.phunghung29.microservice.gateway.exceptions.UnAuthorizationException;
import com.phunghung29.microservice.gateway.repositories.RoleRepository;
import com.phunghung29.microservice.gateway.repositories.UserRepository;
import com.phunghung29.microservice.gateway.repositories.UserSessionRepository;
import com.phunghung29.microservice.gateway.services.GatewayService;
import com.phunghung29.microservice.gateway.utils.AESUtils;
import com.phunghung29.microservice.gateway.utils.CustomHttpHeader;
import com.phunghung29.microservice.gateway.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static com.phunghung29.microservice.gateway.utils.Utils.loadProperties;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final UserSessionRepository userSessionRepository;

    private final PasswordEncoder encoder;

    private static final String AES_PASSWORD = "PhungHung_Microservice_Sup3r_1d0l_";

    public void authenticate(String identifier, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (DisabledException e) {
            throw new UnAuthorizationException(ExceptionCode.USER_DISABLED, e.getMessage());
        } catch (BadCredentialsException e) {
            throw new UnAuthorizationException(ExceptionCode.INCORRECT_IDENTIFIER_OR_PASSWORD, "Email or username or password is incorrect");
        }
    }

    @Override
    public void start() {
        /*
        Role sa = new Role();
        sa.setRoleName("Super Admin");
        Role admin = new Role();
        admin.setRoleName("Admin");
        Role customer = new Role();
        customer.setRoleName("Customer");
        roleRepository.save(sa);
        roleRepository.save(admin);
        roleRepository.save(customer);

//        0963357143Ph@
        String password = "$2y$10$OyzGEjGSUhgQ43XeRP/yMOPoHJcMYH4Puq6MTlxFJzz6ZYvWEmZJC";
        User user1 = new User();
        user1.setEmail("phunghung20@gmail.com");
        user1.setUsername("phunghung20");
        user1.setPassword(password);
        user1.setPhone("0963357143");
        user1.setAge(20);
        user1.setGender(0);
        user1.setRole(sa);

        User user2 = new User();
        user2.setEmail("phunghung21@gmail.com");
        user2.setUsername("phunghung21");
        user2.setPassword(password);
        user2.setPhone("0963357144");
        user2.setAge(21);
        user2.setGender(0);
        user2.setRole(admin);

        User user3 = new User();
        user3.setEmail("phunghung22@gmail.com");
        user3.setUsername("phunghung22");
        user3.setPassword(password);
        user3.setPhone("0963357145");
        user3.setAge(22);
        user3.setGender(0);
        user3.setRole(customer);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        */
    }

    @Override
    public String generateToken(Map<String, Object> payload, org.springframework.security.core.userdetails.User user, boolean isAccessToken) {
        Properties prop = loadProperties("jwt.setting.properties");
        if (prop.isEmpty()) {
            throw new InternalServerException("Could not load file property");
        }
        String key = prop.getProperty("key");
        String accessExpired = prop.getProperty("access_expired");
        String refreshExpired = prop.getProperty("refresh_expired");
        assert key != null;
        assert accessExpired != null;
        assert refreshExpired != null;
        long expiredIn = Long.parseLong(isAccessToken ? accessExpired : refreshExpired);
        Algorithm algorithm = Algorithm.HMAC256(key);

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredIn))
                .withClaim("user", payload)
                .sign(algorithm);
    }

    @Override
    public LoginDTO login(LoginRequestDTO loginRequestDto, String userAgent, String encryptedClientID) {
        String identifier = loginRequestDto.getIdentifier();
        String password = loginRequestDto.getPassword();
        log.info("Email: {} is logging into system", identifier);
        authenticate(identifier, password);
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(identifier);
            User detectedUser = userRepository.findByEmailOrUsername(identifier);
            UserSession userSession = null;
            if (encryptedClientID != null && !encryptedClientID.isEmpty() && !encryptedClientID.isBlank()) {
                userSession = checkSession(detectedUser.getId(), userAgent, encryptedClientID);
            }
            if (userSession == null) {
                encryptedClientID = UUID.randomUUID().toString();
            }
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", detectedUser.getId().toString());
            payload.put("email", detectedUser.getEmail());
            payload.put("role", detectedUser.getRole().getRoleName());
            String accessToken = generateToken(
                    payload,
                    new org.springframework.security.core.userdetails.User(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()),
                    true);
            String refreshToken = generateToken(
                    payload,
                    new org.springframework.security.core.userdetails.User(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()),
                    false);
            overrideSession(detectedUser.getId(), refreshToken, userAgent, encryptedClientID);
            return new LoginDTO(accessToken, refreshToken, encryptedClientID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException("Error occurred while logging into system");
        }
    }

    @Override
    public UserSession checkSession(UUID userId, String userAgent, String encryptedClientID) {
        return userSessionRepository.findByUserID(userId, userAgent, encryptedClientID);
    }

    @Override
    public void overrideSession(UUID userId, String token, String userAgent, String encryptedClientID) {
        log.info("encryptedClientID: {}", encryptedClientID);
        UserSession existedSession = checkSession(userId, userAgent, encryptedClientID);
        if (existedSession == null) {
            User user = userRepository.findByUserId(userId);
            existedSession = new UserSession();
            existedSession.setUser(user);
            existedSession.setDeviceType(userAgent);
            existedSession.setClientID(encryptedClientID);
        }
        existedSession.setToken(token);
        userSessionRepository.save(existedSession);
    }

    @Override
    public String createClientID(String rawKey) {
        try {
            String clientID = UUID.randomUUID().toString();
            log.info("ClientID: {}", clientID);
            SecretKey secretKey = AESUtils.getKeyFromPassword(rawKey, AESUtils.BASE_SALT);
            IvParameterSpec ivParameterSpec = AESUtils.generateIv();
            return AESUtils.encrypt(AESUtils.BASE_ALGORITHM, clientID, secretKey, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailOrUsername(email);
    }
}
