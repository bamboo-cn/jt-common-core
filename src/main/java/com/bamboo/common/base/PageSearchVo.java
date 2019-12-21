package com.bamboo.common.base;

import java.io.Serializable;

public class PageSearchVo implements Serializable {
    public static final int SORT_DES = 0;
    public static final int SORT_ASC = 1;
    private Long page = 0L;
    private Integer size = 20;
    private Integer sort = 0;

    public PageSearchVo() {
    }

    public PageSearchVo(Long page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Long getPage() {
        return this.page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getIndex() {
        return this.page * (long)this.size;
    }
}

