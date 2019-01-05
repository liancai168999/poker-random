package com.bingo.core.metadata;

import com.bingo.core.annotation.*;
import com.bingo.core.config.DbConfig;
import com.bingo.core.toolkit.StringPool;
import com.bingo.core.toolkit.StringUtils;
import com.bingo.core.toolkit.TableInfoHelper;
import com.bingo.core.toolkit.sql.SqlScriptUtils;
import com.bingo.core.toolkit.sql.SqlUtils;

import java.lang.reflect.Field;

/**
 * 数据库表字段反射信息
 */
public class TableFieldInfo {

    private boolean isTableField;
    /**
     * 是否有存在字段名与属性名关联
     * true: 表示要进行 as
     */
    private boolean related;
    /**
     * 是否进行 select 查询
     * 大字段可设置为 false 不加入 select 查询范围
     */
    private boolean select = true;
    /**
     * 字段名
     */
    private String column;
    /**
     * 属性名
     */
    private String property;
    /**
     * 属性表达式#{property}, 可以指定jdbcType, typeHandler等
     */
    private String el;
    /**
     * 属性类型
     */
    private Class<?> propertyType;
    /**
     * 属性是否是 CharSequence 类型
     */
    private boolean isCharSequence;
    /**
     * 字段策略【 默认，自判断 null 】
     */
    private FieldStrategy fieldStrategy;
    /**
     * 逻辑删除值
     */
    private String logicDeleteValue;
    /**
     * 逻辑未删除值
     */
    private String logicNotDeleteValue;
    /**
     * 字段 insert 部分注入
     */
    private String insert;
    /**
     * 字段 update set 部分注入
     */
    private String update;

    private UpdateType updateType = UpdateType.FIXED;

    private boolean updatable = true;
    /**
     * where 字段比较条件
     */
    private String condition = SqlCondition.EQUAL;
    /**
     * 字段填充策略
     */
    private FieldFill fieldFill = FieldFill.DEFAULT;
    /**
     * 标记该字段属于哪个类
     */
    private Class<?> clazz;
    /**
     * 缓存 sql select
     */
    private String sqlSelect;


    /**
     * <p>
     * 存在 TableField 注解时, 使用的构造函数
     * </p>
     */
    public TableFieldInfo(DbConfig dbConfig, TableInfo tableInfo, Field field, String column, String el, TableField tableField) {
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isCharSequence = StringUtils.isCharSequence(this.propertyType);
        this.fieldFill = tableField.fill();
        this.clazz = field.getDeclaringClass();
        this.update = tableField.update();
        this.updateType = tableField.updateType();
        this.updatable = tableField.updatable();
        this.isTableField = true;
        this.el = el;
        tableInfo.setLogicDelete(this.initLogicDelete(dbConfig, field));

        if (StringUtils.isEmpty(tableField.value())) {
            if (tableInfo.isUnderCamel()) {
                column = StringUtils.camelToUnderline(column);
            }
        }
        this.column = column;
        this.related = TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), this.property, this.column);

        /*
         * 优先使用单个字段注解，否则使用全局配置
         */
        if (tableField.strategy() == FieldStrategy.DEFAULT) {
            this.fieldStrategy = dbConfig.getFieldStrategy();
        } else {
            this.fieldStrategy = tableField.strategy();
        }

        if (StringUtils.isNotEmpty(tableField.condition())) {
            // 细粒度条件控制
            this.condition = tableField.condition();
        } else {
            // 全局配置
            this.setCondition(dbConfig);
        }

        // 字段是否注入查询
        this.select = tableField.select();
    }

    /**
     * <p>
     * 不存在 TableField 注解时, 使用的构造函数
     * </p>
     */
    public TableFieldInfo(DbConfig dbConfig, TableInfo tableInfo, Field field) {
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isTableField = false;
        this.isCharSequence = StringUtils.isCharSequence(this.propertyType);
        this.el = field.getName();
        this.fieldStrategy = dbConfig.getFieldStrategy();
        this.setCondition(dbConfig);
        this.clazz = field.getDeclaringClass();
        tableInfo.setLogicDelete(this.initLogicDelete(dbConfig, field));

        String column = field.getName();
        if (tableInfo.isUnderCamel()) {
            /* 开启字段下划线申明 */
            column = StringUtils.camelToUnderline(column);
        }
        if (dbConfig.isCapitalMode()) {
            /* 开启字段全大写申明 */
            column = column.toUpperCase();
        }
        this.column = column;
        this.related = TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), this.property, this.column);
    }

    /**
     * <p>
     * 逻辑删除初始化
     * </p>
     *
     * @param dbConfig 数据库全局配置
     * @param field    字段属性对象
     */
    private boolean initLogicDelete(DbConfig dbConfig, Field field) {
        /* 获取注解属性，逻辑处理字段 */
        TableLogic tableLogic = field.getAnnotation(TableLogic.class);
        if (null != tableLogic) {
            if (StringUtils.isNotEmpty(tableLogic.value())) {
                this.logicNotDeleteValue = tableLogic.value();
            } else {
                this.logicNotDeleteValue = dbConfig.getLogicNotDeleteValue();
            }
            if (StringUtils.isNotEmpty(tableLogic.delval())) {
                this.logicDeleteValue = tableLogic.delval();
            } else {
                this.logicDeleteValue = dbConfig.getLogicDeleteValue();
            }
            return true;
        }
        return false;
    }
    /**
     * 是否开启逻辑删除
     */
    public boolean isLogicDelete() {
        return StringUtils.isNotEmpty(logicDeleteValue);
    }

    /**
     * 全局配置开启字段 LIKE 并且为字符串类型字段
     * 注入 LIKE 查询！！！
     */
    private void setCondition(DbConfig dbConfig) {
        if (null == condition || SqlCondition.EQUAL.equals(condition)) {
            if (dbConfig.isColumnLike() && isCharSequence) {
                condition = dbConfig.getDbType().getLike();
            }
        }
    }

    /**
     * 获取 select sql 片段
     *
     * @param dbType 数据库类型
     * @return sql 片段
     */
    public String getSqlSelect(DbType dbType) {
        if (sqlSelect != null) {
            return sqlSelect;
        }
        sqlSelect = SqlUtils.sqlWordConvert(dbType, getColumn(), true);
        if (related) {
            sqlSelect += (" AS " + SqlUtils.sqlWordConvert(dbType, getProperty(), false));
        }
        return sqlSelect;
    }

    /**
     * 获取 inset 时候插入值 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "值" 部位
     *
     * @return sql 脚本片段
     */
    public String getInsertSqlProperty() {
        return getInsertSqlPropertyWithComma("");
    }

    public String getInsertSqlPropertyWithComma() {
        return getInsertSqlPropertyWithComma(StringPool.COMMA);
    }

    public String getInsertSqlPropertyWithComma(String comma) {
        String sqlScript ="";
        if (StringUtils.isNotEmpty(update) && (fieldFill == FieldFill.INSERT || fieldFill == FieldFill.INSERT_UPDATE)) {
            if(updateType == UpdateType.FIXED)
                sqlScript += "'"+String.format(update, column)+"'"+ comma;
            else
                sqlScript += String.format(update, column)+ comma;
        } else {
            sqlScript += SqlScriptUtils.safeParam(el) + comma;
        }
        if (fieldFill == FieldFill.INSERT || fieldFill == FieldFill.INSERT_UPDATE) {
            return sqlScript;
        }
        return convertIf(sqlScript, property);
    }

    /**
     * 获取 inset 时候字段 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "字段" 部位
     *
     * @return sql 脚本片段
     */
    public String getInsertSqlColumn() {
        return getInsertSqlColumnWithComma("");
    }

    public String getInsertSqlColumnWithComma() {
        return getInsertSqlColumnWithComma(StringPool.COMMA);
    }

    public String getInsertSqlColumnWithComma(String comma) {
        String sqlScript = column + comma;
        if (fieldFill == FieldFill.INSERT || fieldFill == FieldFill.INSERT_UPDATE) {
            return sqlScript;
        }
        return convertIf(sqlScript, property);
    }

    /**
     * 获取 set sql 片段
     *
     * @param prefix 前缀
     * @return sql 脚本片段
     */
    public String getSqlSet(final String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        // 默认: column=
        String sqlSet = column + StringPool.EQUALS;
        if (StringUtils.isNotEmpty(update) && (fieldFill == FieldFill.UPDATE || fieldFill == FieldFill.INSERT_UPDATE)) {
            sqlSet += String.format(update, column);
        } else {
            sqlSet += SqlScriptUtils.safeParam(newPrefix + el);
        }
        //sqlSet += StringPool.COMMA;
        if (fieldFill == FieldFill.UPDATE || fieldFill == FieldFill.INSERT_UPDATE) {
            // 不进行 if 包裹
            return sqlSet;
        }
        return convertIf(sqlSet, newPrefix + property);
    }

    /**
     * 获取 查询的 sql 片段
     *
     * @param prefix 前缀
     * @return sql 脚本片段
     */
    public String getSqlWhere(final String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        // 默认: AND column=#{prefix + el}
        String sqlScript = " AND " + String.format(condition, column, newPrefix + el);
        // 查询的时候只判非空
        return convertIf(sqlScript, newPrefix + property);
    }

    /**
     * 转换成 if 标签的脚本片段
     *
     * @param sqlScript sql 脚本片段
     * @param property  字段名
     * @return if 脚本片段
     */
    private String convertIf(final String sqlScript, final String property) {
        if (fieldStrategy == FieldStrategy.IGNORED) {
            return sqlScript;
        }
        if (fieldStrategy == FieldStrategy.NOT_EMPTY && isCharSequence) {
            return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and %s != ''", property, property), false);
        }
        return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", property), false);
    }

    @Override
    public String toString() {
        return "TableFieldInfo{" +
                "related=" + related +
                ", select=" + select +
                ", column='" + column + '\'' +
                ", property='" + property + '\'' +
                ", el='" + el + '\'' +
                ", propertyType=" + propertyType +
                ", isCharSequence=" + isCharSequence +
                ", fieldStrategy=" + fieldStrategy +
                ", logicDeleteValue='" + logicDeleteValue + '\'' +
                ", logicNotDeleteValue='" + logicNotDeleteValue + '\'' +
                ", update='" + update + '\'' +
                ", condition='" + condition + '\'' +
                ", fieldFill=" + fieldFill +
                ", clazz=" + clazz +
                ", sqlSelect='" + sqlSelect + '\'' +
                '}';
    }

    public boolean isTableField() {
        return isTableField;
    }

    public void setTableField(boolean tableField) {
        isTableField = tableField;
    }

    public String getInsert() {
        return insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public void setSqlSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
    }

    public String getColumn() {
        return column;
    }

    public String getLogicDeleteValue() {
        return logicDeleteValue;
    }

    public String getLogicNotDeleteValue() {
        return logicNotDeleteValue;
    }

    public String getUpdate() {
        return update;
    }

    public String getCondition() {
        return condition;
    }

    public FieldFill getFieldFill() {
        return fieldFill;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isRelated() {
        return related;
    }

    public void setRelated(boolean related) {
        this.related = related;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isCharSequence() {
        return isCharSequence;
    }

    public void setCharSequence(boolean charSequence) {
        isCharSequence = charSequence;
    }

    public FieldStrategy getFieldStrategy() {
        return fieldStrategy;
    }

    public void setFieldStrategy(FieldStrategy fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }

    public void setLogicDeleteValue(String logicDeleteValue) {
        this.logicDeleteValue = logicDeleteValue;
    }

    public void setLogicNotDeleteValue(String logicNotDeleteValue) {
        this.logicNotDeleteValue = logicNotDeleteValue;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setFieldFill(FieldFill fieldFill) {
        this.fieldFill = fieldFill;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }
}
