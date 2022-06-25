package com.phunghung29.microservice.product.repositories;

import com.phunghung29.microservice.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p.id, p.category, p.supplier, p.prdStatus, p.productName, p.price, p.storageQuantity FROM Product p")
    Page<Product> findAll(Pageable pageable);
}
