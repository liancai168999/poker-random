package com.bingo.core.model;

import java.io.Serializable;

/**
 * 过滤条件 封装类
 */
public class FilterDescriptor implements Serializable {

    /**
     * or and
     */
    private String logic;
    private String field;
    private Object value;
    private String operator;
    private boolean ignoreCase = true;

    public FilterDescriptor() {
    }

    public FilterDescriptor(String logic, String field, Object value, String operator) {
        this.logic = logic;
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "FilterDescriptor{" +
                "logic='" + logic + '\'' +
                ", field='" + field + '\'' +
                ", value=" + value +
                ", operator='" + operator + '\'' +
                ", ignoreCase=" + ignoreCase +
                '}';
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
