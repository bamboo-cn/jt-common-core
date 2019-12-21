package com.bamboo.common.base;

import java.io.Serializable;
import java.util.Date;

public class PageVo implements Serializable {
    public static final int SORT_DES = 0;
    public static final int SORT_ASC = 1;
    public Long maxOrMinId;
    public Integer sort;
    public Integer size;
    private Date maxOrMinTime;

    public PageVo() {
    }

    public Long getMaxOrMinId() {
        return this.maxOrMinId == null ? 0L : this.maxOrMinId;
    }

    public void setMaxOrMinId(Long maxOrMinId) {
        this.maxOrMinId = maxOrMinId;
    }

    public Integer getSort() {
        return this.sort == null ? 0 : this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSize() {
        return this.size == null ? 10 : this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getMaxOrMinTime() {
        return this.maxOrMinTime;
    }

    public void setMaxOrMinTime(Date maxOrMinTime) {
        this.maxOrMinTime = maxOrMinTime;
    }
}
