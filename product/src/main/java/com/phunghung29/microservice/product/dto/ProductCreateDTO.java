package com.phunghung29.microservice.product.dto;

import com.phunghung29.microservice.product.utils.annotations.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "Category id can not be blank")
    @UUID(message = "Category id must be an uuid")
    private String categoryId;

    @NotBlank(message = "Supplier id can not be blank")
    @UUID(message = "Supplier id must be an uuid")
    private String supplierId;

    @NotBlank(message = "Product status id can not be blank")
    @UUID(message = "Product status id must be an uuid")
    private String prdStatusId;

    @NotBlank(message = "Product name id can not be blank")
    @Size(min = 2, max = 255, message = "Product name size must be in range (2, 255)")
    private String productName;

    @NotBlank(message = "Price can not be blank")
    @Digits(message = "Price must be a decimal number with 10 integer and 2 fraction", integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Storage quantity can not be null")
    @Digits(message = "Storage quantity must be an integer number", integer = 10, fraction = 0)
    private Integer storageQuantity;
    private String description;
}
