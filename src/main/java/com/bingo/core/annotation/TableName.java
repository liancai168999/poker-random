package com.bingo.core.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableName {

    /**
     * <p>
     * 实体对应的表名
     * </p>
     */
    String value() default "";

    /**
     * <p>
     * 实体映射结果集
     * </p>
     */
    String resultMap() default "";

}