package com.phunghung29.microservice.product.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class PageMeta<T> {
    int totalPages;
    int currentPage;
    int totalItems;
    int limit;
    int currentSize;
    boolean hasNextPage;
    boolean hasPreviousPage;

    public PageMeta(int totalPages, int currentPage, int limit, int totalItems, int contentSize) {
        this.totalPages = totalPages;
        this.currentPage = currentPage + 1;
        this.limit = limit;
        this.totalItems = totalItems;
        this.currentSize = contentSize;
        setNextAndPre();
    }

    public PageMeta(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.limit = page.getPageable().getPageSize();
        this.currentSize = page.getContent().size();
        setNextAndPre();
    }

    private void setNextAndPre() {
        this.hasNextPage = this.currentPage < this.totalPages;
        this.hasPreviousPage = this.currentPage > 1;
    }
}
