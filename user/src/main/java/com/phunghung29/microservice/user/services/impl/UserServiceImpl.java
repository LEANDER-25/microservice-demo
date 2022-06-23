package com.phunghung29.microservice.user.services.impl;

import com.phunghung29.microservice.user.dto.UserReadDTO;
import com.phunghung29.microservice.user.entities.User;
import com.phunghung29.microservice.user.repositories.RoleRepository;
import com.phunghung29.microservice.user.repositories.UserRepository;
import com.phunghung29.microservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
