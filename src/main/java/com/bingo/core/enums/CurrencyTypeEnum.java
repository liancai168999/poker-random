package com.bingo.core.enums;

import java.io.Serializable;

public enum CurrencyTypeEnum implements IEnum {

    ETH("ETH"), TYT("TYT");

    private String name;

    private CurrencyTypeEnum(String name) {
        this.name = name;
    }

    public String Name() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return Name() ;
    }
}
