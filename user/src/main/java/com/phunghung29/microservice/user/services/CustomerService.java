package com.phunghung29.microservice.user.services;

import com.phunghung29.microservice.user.dto.CustomerReadDTO;
import com.phunghung29.microservice.user.dto.UserRegisterDTO;

public interface CustomerService {
    CustomerReadDTO registerCustomer(UserRegisterDTO userRegisterDTO);
}
