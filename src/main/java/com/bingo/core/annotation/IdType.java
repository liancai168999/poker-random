package com.bingo.core.annotation;

/**
 * 生成ID类型枚举类
 */
public enum IdType {
    /**
     * 数据库ID自增
     */
    AUTO(0),
    /**
     * 用户输入ID
     */
    INPUT(1);

    private int key;

    IdType(int key) {
        this.key = key;
    }
}
