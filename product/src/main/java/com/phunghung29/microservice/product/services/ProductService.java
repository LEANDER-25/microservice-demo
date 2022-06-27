package com.phunghung29.microservice.product.services;

import com.phunghung29.microservice.product.dto.ProductCreateDTO;
import com.phunghung29.microservice.product.dto.ProductFilterDTO;
import com.phunghung29.microservice.product.dto.ProductReadDTO;
import com.phunghung29.microservice.product.utils.Pagination;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductReadDTO> fetchAll();
    Pagination<ProductReadDTO> fetchAll(Pageable pageable);
    ProductReadDTO getDetail(int productId);
    ProductReadDTO addProduct(ProductCreateDTO productCreateDTO);
    ProductReadDTO updateProductStatus(int productId, int statusId, int userId);
    Pagination<ProductReadDTO> filter(ProductFilterDTO productFilterDTO, Pageable pageable);

}
