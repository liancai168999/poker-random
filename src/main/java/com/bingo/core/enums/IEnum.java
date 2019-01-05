package com.bingo.core.enums;

import java.io.Serializable;

public interface IEnum<T extends Serializable> {

    /**
     * 枚举数据库存储值
     */
    T getValue();
}
