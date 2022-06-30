package com.phunghung29.microservice.user.services.impl;

import com.phunghung29.microservice.user.dto.UserReadDTO;
import com.phunghung29.microservice.user.dto.UserRegisterDTO;
import com.phunghung29.microservice.user.entities.Role;
import com.phunghung29.microservice.user.entities.User;
import com.phunghung29.microservice.user.exceptions.ExceptionCode;
import com.phunghung29.microservice.user.exceptions.ExistedException;
import com.phunghung29.microservice.user.exceptions.NotFoundException;
import com.phunghung29.microservice.user.repositories.RoleRepository;
import com.phunghung29.microservice.user.repositories.UserRepository;
import com.phunghung29.microservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private UserReadDTO convertToDTO(User user) {
        UserReadDTO userReadDTO = new UserReadDTO();
        BeanUtils.copyProperties(user, userReadDTO);
        userReadDTO.setRoleName(user.getRole().getRoleName());
        return userReadDTO;
    }

    @Override
    public List<UserReadDTO> fetchAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserReadDTO> fetchAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        if(userRepository.findByEmail(userRegisterDTO.getEmail()) != null) {
            throw new ExistedException(ExceptionCode.USER_EXISTED, "Email is existed");
        }
        if(userRepository.findByUsername(userRegisterDTO.getUsername()) != null) {
            throw new ExistedException(ExceptionCode.USER_EXISTED, "Username is existed");
        }
        Role role = roleRepository.findByRoleName(userRegisterDTO.getRoleName());
        if (role == null) {
            throw new NotFoundException(ExceptionCode.INVALID_ROLE, "Role not found");
        }

        User newUser = new User();
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setUsername(userRegisterDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        newUser.setAge(userRegisterDTO.getAge());
        newUser.setGender(userRegisterDTO.getGender());
        newUser.setPhone(userRegisterDTO.getPhone());
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    @Override
    public UserReadDTO registerUser(UserRegisterDTO userRegisterDTO) {
        User created = register(userRegisterDTO);
        UserReadDTO userReadDTO = new UserReadDTO();
        BeanUtils.copyProperties(created, userReadDTO);
        userReadDTO.setRoleName(created.getRole().getRoleName());
        return userReadDTO;
    }
}
