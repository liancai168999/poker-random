package com.bingo.core.metadata;

import com.bingo.core.annotation.DbType;
import com.bingo.core.annotation.IdType;
import com.bingo.core.annotation.KeySequence;
import com.bingo.core.exceptions.ExceptionUtils;
import com.bingo.core.toolkit.StringPool;
import com.bingo.core.toolkit.StringUtils;
import com.bingo.core.toolkit.sql.SqlScriptUtils;
import com.bingo.core.toolkit.sql.SqlUtils;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.joining;

/**
 * 数据库表反射信息
 */
public class TableInfo {

    public TableInfo getSelf() {
        return this;
    }

    ;
    /**
     * 表主键ID 类型
     */
    private IdType idType = IdType.AUTO;
    /**
     * 数据库类型
     */
    private DbType dbType;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表映射结果集
     */
    private String resultMap;
    /**
     * 主键是否有存在字段名与属性名关联
     * true: 表示要进行 as
     */
    private boolean keyRelated = false;
    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 表主键ID 字段名
     */
    private String keyColumn;
    /**
     * 表主键ID Sequence
     */
    private KeySequence keySequence;

    /**
     * 表字段信息列表
     */
    private List<TableFieldInfo> fieldList;

    /**
     * 命名空间
     */
    private String currentNamespace;

    /**
     * MybatisConfiguration 标记 (Configuration内存地址值)
     */
    private String configMark;
    /**
     * 是否开启逻辑删除
     */
    private boolean logicDelete = true;
    /**
     * 是否开启下划线转驼峰
     */
    private boolean underCamel = true;
    /**
     * 标记该字段属于哪个类
     */
    private Class<?> clazz;
    /**
     * 缓存包含主键及字段的 sql select
     */
    private String allSqlSelect;
    /**
     * 缓存主键字段的 sql select
     */
    private String sqlSelect;

    public void setConfigMark(Configuration configuration) {
        Assert.notNull(configuration, "Error: You need Initialize Mybatis Bingo Configuration !");
        this.configMark = configuration.toString();
    }

    /**
     * 获取主键的 select sql 片段
     * eg. id
     *
     * @return sql 片段
     */
    public String getKeySqlSelect() {
        if (sqlSelect != null) {
            return sqlSelect;
        }
        if (StringUtils.isNotEmpty(keyProperty)) {
            if (keyRelated) {
                sqlSelect = SqlUtils.sqlWordConvert(dbType, keyColumn, true) + " AS " + SqlUtils.sqlWordConvert(dbType, keyProperty, false);
            } else {
                sqlSelect = SqlUtils.sqlWordConvert(dbType, keyColumn, true);
            }
        } else {
            sqlSelect = StringPool.EMPTY;
        }
        return sqlSelect;
    }

    /**
     * 获取包含主键及字段的 select sql 片段
     * eg. id,xx字段,x2字段
     *
     * @return sql 片段
     */
    public String getAllSqlSelect() {
        if (allSqlSelect != null) {
            return allSqlSelect;
        }
        allSqlSelect = chooseSelect(f -> f.isSelect() && f.isTableField());
        return allSqlSelect;
    }

    /**
     * 获取需要进行查询的 select sql 片段
     *
     * @param predicate 过滤条件
     * @return sql 片段
     */
    public String chooseSelect(Predicate<TableFieldInfo> predicate) {
        String sqlSelect = getKeySqlSelect();
        String fieldsSqlSelect = fieldList.stream().filter(predicate).map(i -> i.getSqlSelect(dbType)).collect(joining(StringPool.COMMA));
        if (StringUtils.isNotEmpty(sqlSelect) && StringUtils.isNotEmpty(fieldsSqlSelect)) {
            return sqlSelect + StringPool.COMMA + fieldsSqlSelect;
        } else if (StringUtils.isNotEmpty(fieldsSqlSelect)) {
            return fieldsSqlSelect;
        }
        return sqlSelect;
    }

    /**
     * 获取 inset 时候主键 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "值" 部位
     *
     * @return sql 脚本片段
     */
    public String getKeyInsertSqlProperty() {
        if (StringUtils.isNotEmpty(keyProperty)) {
            if (idType == IdType.AUTO) {
                return StringPool.EMPTY;
            }
            return SqlScriptUtils.safeParam(keyProperty) + StringPool.COMMA + StringPool.NEWLINE;
        }
        return StringPool.EMPTY;
    }

    /**
     * 获取 inset 时候主键 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "字段" 部位
     *
     * @return sql 脚本片段
     */
    public String getKeyInsertSqlColumn() {
        if (StringUtils.isNotEmpty(keyColumn)) {
            if (idType == IdType.AUTO) {
                return StringPool.EMPTY;
            }
            return keyColumn + StringPool.COMMA + StringPool.NEWLINE;
        }
        return StringPool.EMPTY;
    }


    /**
     * 获取所有 inset 时候插入值 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "值" 部位
     *
     * @return sql 脚本片段
     */
    public String getAllInsertSqlPropertyStr() {
        return getKeyInsertSqlProperty() + fieldList.stream().map(TableFieldInfo::getInsertSqlPropertyWithComma).collect(joining(StringPool.NEWLINE));
    }

    public String[] getAllInsertSqlProperty() {
        return fieldList.stream().map(TableFieldInfo::getInsertSqlProperty).toArray(String[]::new);
    }

    /**
     * 获取 inset 时候字段 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "字段" 部位
     *
     * @return sql 脚本片段
     */
    public String getAllInsertSqlColumnStr() {
        return getKeyInsertSqlColumn() + fieldList.stream().map(TableFieldInfo::getInsertSqlColumnWithComma).collect(joining(StringPool.NEWLINE));
    }

    public String[] getAllInsertSqlColumn() {
        return fieldList.stream().map(TableFieldInfo::getInsertSqlColumn).toArray(String[]::new);
    }

    /**
     * 获取所有的查询的 sql 片段
     *
     * @param ignoreLogicDelFiled 是否过滤掉逻辑删除字段
     * @param withId              是否包含 id 项
     * @param prefix              前缀
     * @return sql 脚本片段
     */
    public String getAllSqlWhere(boolean ignoreLogicDelFiled, boolean withId, final String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        String filedSqlScript = fieldList.stream().filter(i -> {
            if (ignoreLogicDelFiled) {
                return !(isLogicDelete() && i.isLogicDelete());
            }
            return true;
        }).map(i -> i.getSqlWhere(newPrefix)).collect(joining(StringPool.NEWLINE));
        if (!withId || StringUtils.isEmpty(keyProperty)) {
            return filedSqlScript;
        }
        String newKeyProperty = newPrefix + keyProperty;
        String keySqlScript = keyColumn + StringPool.EQUALS + SqlScriptUtils.safeParam(newKeyProperty);
        return SqlScriptUtils.convertIf(keySqlScript, String.format("%s != null", newKeyProperty), false) + StringPool.NEWLINE + filedSqlScript;
    }

    /**
     * 获取所有的 sql set 片段
     *
     * @param ignoreLogicDelFiled 是否过滤掉逻辑删除字段
     * @param prefix              前缀
     * @return sql 脚本片段
     */
    public String[] getAllSqlSet(boolean ignoreLogicDelFiled, final String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        return fieldList.stream().filter(i -> {
            if (ignoreLogicDelFiled) {
                return !(isLogicDelete() && i.isLogicDelete());
            }
            return true;
        }).filter(i -> i.isUpdatable()).map(i -> i.getSqlSet(newPrefix)).toArray(String[]::new);
    }

    /**
     * 获取逻辑删除字段的 sql 脚本
     *
     * @param startWithAnd 是否以 and 开头
     * @param deleteValue  是否需要的是逻辑删除值
     * @return sql 脚本
     */
    public String getLogicDeleteSql(boolean startWithAnd, boolean deleteValue) {
        if (logicDelete) {
            TableFieldInfo field = fieldList.stream().filter(TableFieldInfo::isLogicDelete).findFirst()
                    .orElseThrow(() -> ExceptionUtils.mbg(String.format("can't find the logicFiled from table {%s}", tableName)));
            String formatStr = field.isCharSequence() ? "'%s'" : "%s";
            String logicDeleteSql = field.getColumn() + StringPool.EQUALS
                    + String.format(formatStr, deleteValue ? field.getLogicDeleteValue() : field.getLogicNotDeleteValue());
            if (startWithAnd) {
                logicDeleteSql = " AND " + logicDeleteSql;
            }
            return logicDeleteSql;
        }
        return StringPool.EMPTY;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "idType=" + idType +
                ", dbType=" + dbType +
                ", tableName='" + tableName + '\'' +
                ", resultMap='" + resultMap + '\'' +
                ", keyRelated=" + keyRelated +
                ", keyProperty='" + keyProperty + '\'' +
                ", keyColumn='" + keyColumn + '\'' +
                ", keySequence=" + keySequence +
                ", fieldList=" + fieldList +
                ", currentNamespace='" + currentNamespace + '\'' +
                ", configMark='" + configMark + '\'' +
                ", logicDelete=" + logicDelete +
                ", underCamel=" + underCamel +
                ", clazz=" + clazz +
                ", allSqlSelect='" + allSqlSelect + '\'' +
                ", sqlSelect='" + sqlSelect + '\'' +
                '}';
    }

    public List<TableFieldInfo> getFieldList() {
        return fieldList;
    }

    public TableInfo setFieldList(List<TableFieldInfo> fieldList) {
        this.fieldList = fieldList;
        return getSelf();
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public TableInfo setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
        return getSelf();
    }

    public String getCurrentNamespace() {
        return currentNamespace;
    }

    public void setCurrentNamespace(String currentNamespace) {
        this.currentNamespace = currentNamespace;
    }

    public String getConfigMark() {
        return configMark;
    }

    public void setConfigMark(String configMark) {
        this.configMark = configMark;
    }

    public boolean isLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(boolean logicDelete) {
        this.logicDelete = logicDelete;
    }

    public boolean isUnderCamel() {
        return underCamel;
    }

    public void setUnderCamel(boolean underCamel) {
        this.underCamel = underCamel;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public TableInfo setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return getSelf();
    }

    public IdType getIdType() {
        return idType;
    }

    public TableInfo setIdType(IdType idType) {
        this.idType = idType;
        return getSelf();
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getResultMap() {
        return resultMap;
    }

    public void setResultMap(String resultMap) {
        this.resultMap = resultMap;
    }

    public boolean isKeyRelated() {
        return keyRelated;
    }

    public TableInfo setKeyRelated(boolean keyRelated) {
        this.keyRelated = keyRelated;
        return getSelf();
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public TableInfo setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
        return getSelf();
    }

    public KeySequence getKeySequence() {
        return keySequence;
    }

    public void setKeySequence(KeySequence keySequence) {
        this.keySequence = keySequence;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public void setSqlSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
    }

    public void setAllSqlSelect(String allSqlSelect) {
        this.allSqlSelect = allSqlSelect;
    }
}
