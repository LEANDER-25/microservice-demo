package com.phunghung29.microservice.user.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageOption {

    public static class SortCondition {

        private SortCondition() {}
        public static final String NO_SORT = "none";
    }

    private Integer page;
    private Integer limit;
    private String sort;
    private Boolean asc;

    public Integer getPage() {
        return page == null ? 0 : page - 1;
    }

    public Integer getLimit() {
        return limit == null ? 8 : limit;
    }
}
