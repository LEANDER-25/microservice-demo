package com.phunghung29.microservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDTO {
    private UUID id;
    private String roleName;
    private String username;
    private String email;
    private String phone;
    private Integer age;
    private Integer gender;
    private Instant createdAt;
    private Instant updatedAt;
}
