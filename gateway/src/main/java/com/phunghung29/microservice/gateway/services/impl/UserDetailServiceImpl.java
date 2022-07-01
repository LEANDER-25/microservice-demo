package com.phunghung29.microservice.gateway.services.impl;

import com.phunghung29.microservice.gateway.entities.Role;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.exceptions.ExceptionCode;
import com.phunghung29.microservice.gateway.exceptions.NotFoundException;
import com.phunghung29.microservice.gateway.exceptions.UnAuthorizationException;
import com.phunghung29.microservice.gateway.repositories.RoleRepository;
import com.phunghung29.microservice.gateway.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username);
        if (user == null) {
            throw new UnAuthorizationException(ExceptionCode.INCORRECT_IDENTIFIER_OR_PASSWORD, "Email or username or password is incorrect");
        }
        Optional<Role> role = roleRepository.findById(user.getRole().getId());
        if (role.isEmpty()) {
            throw new UnAuthorizationException(ExceptionCode.INVALID_ROLE, "Invalid role");
        }
        String roleName = role.get().getRoleName();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
