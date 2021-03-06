package com.phunghung29.microservice.user.repositories;

import com.phunghung29.microservice.user.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    @Query("SELECT us FROM UserSession us WHERE us.user.id = :userID AND us.deviceType = :userAgent AND us.clientID = :clientID")
    UserSession findByUserID(@Param("userID") UUID userID, @Param("userAgent") String userAgent, @Param("clientID") String clientID);
}
