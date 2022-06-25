package com.phunghung29.microservice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadDTO {
    private UUID id;
    private CategoryReadDTO categoryName;
    private SupplierReadDTO supplierName;
    private ProductSttReadDTO prdStatusName;
    private String productName;
    private BigDecimal price;
    private Integer storageQuantity;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
