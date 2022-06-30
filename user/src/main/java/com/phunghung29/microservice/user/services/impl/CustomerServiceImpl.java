package com.phunghung29.microservice.user.services.impl;

import com.phunghung29.microservice.user.dto.CustomerReadDTO;
import com.phunghung29.microservice.user.dto.UserRegisterDTO;
import com.phunghung29.microservice.user.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerReadDTO registerCustomer(UserRegisterDTO userRegisterDTO) {
        return null;
    }
}
