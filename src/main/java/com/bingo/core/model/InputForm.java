package com.bingo.core.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 输入form
 */
public class InputForm {

    private Map<String, String> record;

    @Override
    public String toString() {
        return "InputForm{" +
                "record=" + record +
                '}';
    }

    public Map<String, String> getRecord() {
        if (null == this.record) {
            this.record = Maps.newHashMap();
        }
        return this.record;
    }

    public void setRecord(Map<String, String> record) {
        this.record = record;
    }
}
