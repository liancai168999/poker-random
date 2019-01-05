package com.bingo.core.annotation;

import java.lang.annotation.*;

/**
 * 表字段逻辑处理注解（逻辑删除）
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableLogic {

    /**
     * <p>
     * 默认逻辑未删除值（该值可无、会自动获取全局配置）
     * </p>
     */
    String value() default "";

    /**
     * <p>
     * 默认逻辑删除值（该值可无、会自动获取全局配置）
     * </p>
     */
    String delval() default "";

}