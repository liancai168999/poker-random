package com.bingo.core.model;


import com.bingo.core.toolkit.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 排序 封装
 */
public class SortDescriptor implements Serializable {

    private String field;
    /** asc desc **/
    private String dir;
    private String compare;

    public boolean conversionFlag(){
        return StringUtils.equals(StringPool.ASC,dir);
    }

    public SortDescriptor() {
    }

    public SortDescriptor(String field, String dir) {
        this.field = field;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "SortDescriptor{" +
                "field='" + field + '\'' +
                ", dir='" + dir + '\'' +
                ", compare='" + compare + '\'' +
                '}';
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }
}
