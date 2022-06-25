package com.phunghung29.microservice.product.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Pagination<T> {
    List<T> content;
    PageMeta<T> meta;

    public Pagination(Page<T> page) {
        this.meta = new PageMeta<>(page);
        this.content = page.getContent();
    }
}
