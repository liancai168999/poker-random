package com.bingo.core.annotation;

import java.lang.annotation.*;

/**
 * 表字段标识
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    /**
     * <p>
     * 字段值（驼峰命名方式，该值可无）
     * </p>
     */
    String value() default "";

    /**
     *
     */
    String el() default "";

    /**
     * <p>
     * 是否为数据库表字段
     * </p>
     * <p>
     * 默认 true 存在，false 不存在
     * </p>
     */
    boolean exist() default true;

    /**
     * <p>
     * 字段 where 实体查询比较条件
     * </p>
     * <p>
     * 默认 `=` 等值
     * </p>
     */
    String condition() default "";

    /**
     * <p>
     * 字段 update set 部分注入
     * </p>
     * <p>
     * 例如：@TableField(.. , update="%s+1") 其中 %s 会填充为字段
     * 输出 SQL 为：update 表 set 字段=字段+1 where ...
     * </p>
     * <p>
     * 例如：@TableField(.. , update="now()") 使用数据库时间
     * 输出 SQL 为：update 表 set 字段=now() where ...
     * </p>
     */
    String update() default "";

    UpdateType updateType() default UpdateType.FIXED;

    boolean updatable() default true;

    /**
     * <p>
     * 字段验证策略
     * </p>
     * <p>
     * 默认追随全局配置
     * </p>
     */
    FieldStrategy strategy() default FieldStrategy.DEFAULT;

    /**
     * <p>
     * 字段自动填充策略
     * </p>
     */
    FieldFill fill() default FieldFill.DEFAULT;

    /**
     * <p>
     * 是否进行 select 查询
     * </p>
     * <p>
     * 大字段可设置为 false 不加入 select 查询范围
     * </p>
     */
    boolean select() default true;
}