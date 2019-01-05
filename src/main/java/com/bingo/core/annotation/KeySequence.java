package com.bingo.core.annotation;

import java.lang.annotation.*;

/**
 * 序列主键策略
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeySequence {

    /**
     * <p>
     * 序列名
     * </p>
     */
    String value() default "";

    /**
     * <p>
     * id的类型
     * </p>
     */
    Class clazz() default Long.class;

}
