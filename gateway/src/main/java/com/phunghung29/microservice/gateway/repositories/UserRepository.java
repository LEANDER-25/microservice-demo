package com.phunghung29.microservice.gateway.repositories;

import com.phunghung29.microservice.gateway.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email like :identify OR u.username like :identify")
    User findByEmailOrUsername(String identify);
    @Query("select u from User u where u.id = ?1")
    User findByUserId(UUID userId);
}
