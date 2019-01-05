package com.bingo.core.model;

import com.bingo.core.toolkit.CollectionUtils;
import com.bingo.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 分页请求 封装
 */
public class PageInfoRequest {

    private static final Logger log = LoggerFactory.getLogger(PageInfoRequest.class);

    /**
     * 页数
     **/
    private int pageNo = 1;

    /**
     * 页条数
     **/
    private int pageSize = 50;

    private String searchValue;

    private List<FilterDescriptor> filters;

    private List<SortDescriptor> sort;

    public PageInfoRequest() {
        sort = Lists.newArrayList();
    }

    public Map<String, String> getParamsByFilter() {
        if (CollectionUtils.isEmpty(filters)) {
            return Maps.newLinkedHashMap();
        }

        Map<String, String> result = Maps.newLinkedHashMap();
        filters.forEach(f -> {
            if (StringUtils.checkValNotNull(f.getValue())) {
                StringBuilder sb = new StringBuilder();
                sb.append(f.getField()).append(f.getOperator()).append("'").append(f.getValue()).append("'");
                result.put(sb.toString(), f.getLogic());
            }
        });
        log.info("getParamsByFilter: {}", result);
        return result;
    }

    public Map<String, String> getFilterParams() {
        if (CollectionUtils.isEmpty(filters)) {
            return Maps.newLinkedHashMap();
        }

        Map<String, String> result = Maps.newLinkedHashMap();
        filters.forEach(f -> {
            if (StringUtils.checkValNotNull(f.getValue())) {
                result.put(f.getField(), f.getValue().toString());
            }
        });
        log.info("getFilterParams: {}", result);
        return result;
    }

    public String[] getOrdersBySort() {
        if (CollectionUtils.isEmpty(sort)) {
            return new String[0];
        }
        return sort.stream().map(SortDescriptor::getField).toArray(String[]::new);
    }

    public Boolean[] getAscendingsBySort() {
        if (CollectionUtils.isEmpty(sort)) {
            return new Boolean[0];
        }
        return sort.stream().map(SortDescriptor::conversionFlag).toArray(Boolean[]::new);
    }

    @Override
    public String toString() {
        return "PageInfoRequest{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", searchValue='" + searchValue + '\'' +
                ", filters=" + filters +
                ", sort=" + sort +
                '}';
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int page) {
        this.pageNo = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public List<FilterDescriptor> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDescriptor> filters) {
        this.filters = filters;
    }

    public List<SortDescriptor> getSort() {
        return sort;
    }

    public void setSorts(List<SortDescriptor> sort) {
        this.sort = sort;
    }


}
