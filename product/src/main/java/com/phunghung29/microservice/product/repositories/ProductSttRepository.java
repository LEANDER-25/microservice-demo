package com.phunghung29.microservice.product.repositories;

import com.phunghung29.microservice.product.entities.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductSttRepository extends JpaRepository<ProductStatus, UUID> {
}
