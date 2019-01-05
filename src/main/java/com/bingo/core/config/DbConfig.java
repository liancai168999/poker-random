package com.bingo.core.config;

import com.bingo.core.annotation.DbType;
import com.bingo.core.annotation.FieldStrategy;
import com.bingo.core.annotation.IdType;

public class DbConfig {
    /**
     * 数据库类型
     */
    private DbType dbType = DbType.MYSQL;
    /**
     * 主键类型（默认 AUTO）
     */
    private IdType idType = IdType.AUTO;
    /**
     * 表名前缀
     */
    private String tablePrefix;
    /**
     * 表名、是否使用下划线命名（默认 true:默认数据库表下划线命名）
     */
    private boolean tableUnderline = true;
    /**
     * String 类型字段 LIKE
     */
    private boolean columnLike = false;
    /**
     * 大写命名
     */
    private boolean capitalMode = false;
    /**
     * 逻辑删除全局值（默认 删除、表示已删除）
     */
    private String logicDeleteValue = "删除";
    /**
     * 逻辑未删除全局值（默认 正常、表示未删除）
     */
    private String logicNotDeleteValue = "正常";
    /**
     * 字段验证策略
     */
    private FieldStrategy fieldStrategy = FieldStrategy.IGNORED;

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public boolean isTableUnderline() {
        return tableUnderline;
    }

    public void setTableUnderline(boolean tableUnderline) {
        this.tableUnderline = tableUnderline;
    }

    public boolean isColumnLike() {
        return columnLike;
    }

    public void setColumnLike(boolean columnLike) {
        this.columnLike = columnLike;
    }

    public boolean isCapitalMode() {
        return capitalMode;
    }

    public void setCapitalMode(boolean capitalMode) {
        this.capitalMode = capitalMode;
    }

    public String getLogicDeleteValue() {
        return logicDeleteValue;
    }

    public void setLogicDeleteValue(String logicDeleteValue) {
        this.logicDeleteValue = logicDeleteValue;
    }

    public String getLogicNotDeleteValue() {
        return logicNotDeleteValue;
    }

    public void setLogicNotDeleteValue(String logicNotDeleteValue) {
        this.logicNotDeleteValue = logicNotDeleteValue;
    }

    public FieldStrategy getFieldStrategy() {
        return fieldStrategy;
    }

    public void setFieldStrategy(FieldStrategy fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }
}
