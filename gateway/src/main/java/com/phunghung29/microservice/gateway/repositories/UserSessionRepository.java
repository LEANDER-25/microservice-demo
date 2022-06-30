package com.phunghung29.microservice.gateway.repositories;

import com.phunghung29.microservice.gateway.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    @Query("SELECT us FROM UserSession us WHERE us.user.id = :userID AND us.deviceType = :userAgent AND us.clientID = :clientID")
    UserSession findByUserID(@Param("userID") UUID userID, @Param("userAgent") String userAgent, @Param("clientID") String clientID);

    @Query("SELECT us FROM UserSession us WHERE us.token = :token")
    UserSession findByToken(@Param("token") String token);
}
