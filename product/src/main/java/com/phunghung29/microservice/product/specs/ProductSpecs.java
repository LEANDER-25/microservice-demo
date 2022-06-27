package com.phunghung29.microservice.product.specs;

import com.phunghung29.microservice.product.dto.ProductFilterDTO;
import com.phunghung29.microservice.product.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecs {
    public static Specification<Product> filter(ProductFilterDTO productFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (productFilterDTO.getKewword() != null
                    && !productFilterDTO.getKewword().isBlank()
                    && !productFilterDTO.getKewword().isEmpty()) {
                String keyword = productFilterDTO.getKewword();
                Predicate likePrdName = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + keyword + "%");
                predicateList.add(likePrdName);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
