package com.phunghung29.microservice.product.repositories;

import com.phunghung29.microservice.product.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
