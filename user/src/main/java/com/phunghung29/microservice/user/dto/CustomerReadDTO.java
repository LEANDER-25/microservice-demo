package com.phunghung29.microservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReadDTO {
    private Integer id;
    private UserReadDTO userReadDTO;
    private Integer repPoint;
    private Instant createdAt;
    private Instant updatedAt;
}
