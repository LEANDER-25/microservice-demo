package com.phunghung29.microservice.user.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @ToString.Include
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User users;

    @ToString.Include
    @Column(name = "rep_point")
    private Integer repPoint;

    @ToString.Include
    @Column(name = "created_at")
    private Instant createdAt;

    @ToString.Include
    @Column(name = "updated_at")
    private Instant updatedAt;

}