package com.bingo.core.annotation;

/**
 * 字段策略枚举类
 */
public enum FieldStrategy {
    /**
     * 忽略判断
     */
    IGNORED,
    /**
     * 非NULL判断
     */
    NOT_NULL,
    /**
     * 非空判断
     */
    NOT_EMPTY,
    /**
     * 默认的
     * 1. 在全局里代表 NOT_NULL
     * 2. 在注解里代表 跟随全局
     */
    DEFAULT
}
