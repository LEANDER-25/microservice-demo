package com.phunghung29.microservice.user.repositories;

import com.phunghung29.microservice.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(String roleName);
}
