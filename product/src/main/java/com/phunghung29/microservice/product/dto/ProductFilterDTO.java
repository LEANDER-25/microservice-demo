package com.phunghung29.microservice.product.dto;

import com.phunghung29.microservice.product.utils.annotations.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
    private String kewword;
    @UUID(message = "Category id must be an uuid")
    private String categoryId;
    @UUID(message = "Supplier id must be an uuid")
    private String supplierId;
    @UUID(message = "Product status id must be an uuid")
    private String productSttId;

    public String getKewword() {
        return kewword == null ? null : kewword.trim().toLowerCase();
    }
}
