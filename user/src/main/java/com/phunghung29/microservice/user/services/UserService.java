package com.phunghung29.microservice.user.services;

import com.phunghung29.microservice.user.dto.UserReadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserReadDTO> fetchAllUsers();
    Page<UserReadDTO> fetchAllUsers(Pageable pageable);
}
