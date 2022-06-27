package com.phunghung29.microservice.product.repositories;

import com.phunghung29.microservice.product.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
