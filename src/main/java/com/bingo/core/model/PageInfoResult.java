package com.bingo.core.model;

import java.io.Serializable;
import java.util.List;

/**
 *分页返回 封装
 */
public class PageInfoResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //总记录数
    private long total;
    //结果集
    private List<T> list;

    public PageInfoResult() {
    }

    public PageInfoResult(List<T> list) {
        this.list = list;
        this.total = list.size();
    }

    public static <T> PageInfoResult<T> of(List<T> list){
        return new PageInfoResult<T>(list);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageInfoResult{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
