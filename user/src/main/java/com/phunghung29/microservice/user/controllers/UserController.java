package com.phunghung29.microservice.user.controllers;

import com.phunghung29.microservice.user.dto.UserRegisterDTO;
import com.phunghung29.microservice.user.response.ResponseTemplate;
import com.phunghung29.microservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> fetchAllUsers() {
        return ResponseTemplate.success(userService.fetchAllUsers()).release();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return ResponseTemplate.success(userService.fetchAllUsers()).release();
    }
}
