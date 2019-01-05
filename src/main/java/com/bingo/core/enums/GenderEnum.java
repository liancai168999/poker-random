package com.bingo.core.enums;

import java.io.Serializable;

/**
 * @Auther: 郑海育
 * @Date: 2018/11/6
 * @Description:
 */
public enum  GenderEnum implements IEnum {

    MAN("男"),WOMAN("女");

    private String name;

    private GenderEnum(String name) {
        this.name = name;
    }

    public String Name() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return Name();
    }
}
