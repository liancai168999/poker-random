package com.bingo.core.mapper;

import com.bingo.core.exceptions.DaoException;
import com.bingo.core.metadata.TableInfo;
import com.bingo.core.toolkit.CollectionUtils;
import com.bingo.core.toolkit.StringPool;
import com.bingo.core.toolkit.TableInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class BaseSqlProvider {

    private final Logger logger = LoggerFactory.getLogger(BaseSqlProvider.class);

    public BaseSqlProvider() {
        logger.info("BaseSqlProvider ....");
    }

    /**
     * insert
     */

    public String insert(Object obj) throws DaoException {
        logger.info("BaseSqlProvider insert sql start...");

        Assert.notNull(obj, "clazz must be not null");
        Class<?> clazz = ClassUtils.getUserClass(obj);
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.INSERT_INTO(info.getTableName());
        sql.INTO_COLUMNS(info.getAllInsertSqlColumn());
        sql.INTO_VALUES(info.getAllInsertSqlProperty());

        logger.info("BaseSqlProvider insert sql: {} ", sql.toString());
        return sql.toString();
    }

    public String insertByBatch(@Param("entitiesList") List entitiesList) throws DaoException {
        logger.info("BaseSqlProvider insertByBatch sql start...");

        Assert.notNull(entitiesList, "clazz must be not null");
        Object obj = entitiesList.get(0);
        Class<?> clazz = ClassUtils.getUserClass(obj);
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(info.getTableName()).append(" (");
        sb.append(StringUtils.removeEnd(info.getAllInsertSqlColumnStr(), ",")).append(") ");
        sb.append(" VALUES ");

        String sqlProperty = StringUtils.replace(info.getAllInsertSqlPropertyStr(), "'", "''");
        sqlProperty = StringUtils.replace(sqlProperty, "#{", "#'{'entitiesList[{0}].");
        sqlProperty = StringUtils.removeEnd(sqlProperty, ",");

        MessageFormat mf = new MessageFormat(sqlProperty);
        for (int i = 0, len = entitiesList.size(); i < len; i++) {
            sb.append("(").append(mf.format(new Object[]{i})).append(")");
            if (i < len - 1) {
                sb.append(",");
            }
        }

        logger.info("BaseSqlProvider insert sql: {} ", sb.toString());
        return sb.toString();
    }


    /**
     * update
     */

    public String update(Object obj) throws DaoException {
        logger.info("BaseSqlProvider update sql start...");

        Assert.notNull(obj, "clazz must be not null");
        Class<?> clazz = ClassUtils.getUserClass(obj);
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET(info.getAllSqlSet(true, ""));
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));

        logger.info("BaseSqlProvider update sql: {} ", sql.toString());
        return sql.toString();
    }

    public String updateByBatch(@Param("entitiesList") List entitiesList) throws DaoException {
        logger.info("BaseSqlProvider update sql start...");

        SQL sql = new SQL();


        logger.info("BaseSqlProvider updateByBatch sql: {} ", sql.toString());
        return sql.toString();
    }

    /**
     * del
     */

    public String deleteAll(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider deleteAll sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");

        logger.info("BaseSqlProvider deleteAll sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteById(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider deleteById sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));

        logger.info("BaseSqlProvider deleteById sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByEntity(Object obj) throws DaoException {
        logger.info("BaseSqlProvider deleteByEntity sql start...");

        Assert.notNull(obj, "clazz must be not null");
        Class<?> clazz = ClassUtils.getUserClass(obj);
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));

        logger.info("BaseSqlProvider deleteById sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByIds(@Param("class") Class clazz, @Param("ids") Serializable[] ids) throws DaoException {
        logger.info("BaseSqlProvider deleteByIds sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        sql.WHERE(String.format("%s IN (%s)", info.getKeyColumn(), StringUtils.join(ids, ",")));

        logger.info("BaseSqlProvider deleteByIds sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider deleteByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        sql.WHERE(String.format("%s = #{fieldValue}", fieldName));

        logger.info("BaseSqlProvider deleteByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames) throws DaoException {
        logger.info("BaseSqlProvider deleteByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");

        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }

        logger.info("BaseSqlProvider deleteByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
        logger.info("BaseSqlProvider deleteByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        paramsToWhere(params, sql);

        logger.info("BaseSqlProvider deleteByMap sql: {} ", sql.toString());
        return sql.toString();
    }

    public String deleteByIdAndTrue(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider deleteByIdAndTrue sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.UPDATE(info.getTableName());
        sql.SET("tb_status = '删除'");
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));

        logger.info("BaseSqlProvider deleteByIdAndTrue sql: {} ", sql.toString());
        return sql.toString();
    }

//    public String deleteById(@Param("class") Class clazz) throws DaoException {
//        logger.info("BaseSqlProvider deleteById sql start...");
//
//        Assert.notNull(clazz, "clazz must be not null");
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));
//
//        logger.info("BaseSqlProvider deleteById sql: {} ", sql.toString());
//        return sql.toString();
//    }
//
//    public String deleteByEntity(Object obj) throws DaoException {
//        logger.info("BaseSqlProvider deleteByEntity sql start...");
//
//        Assert.notNull(obj, "clazz must be not null");
//        Class<?> clazz = ClassUtils.getUserClass(obj);
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));
//
//        logger.info("BaseSqlProvider deleteById sql: {} ", sql.toString());
//        return sql.toString();
//    }
//
//    public String deleteByIds(@Param("class") Class clazz,@Param("ids") Serializable[] ids) throws DaoException {
//        logger.info("BaseSqlProvider deleteByIds sql start...");
//
//        Assert.notNull(clazz, "clazz must be not null");
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//        sql.WHERE(String.format("%s IN (%s)", info.getKeyColumn(), StringUtils.join(ids,",")));
//
//        logger.info("BaseSqlProvider deleteByIds sql: {} ", sql.toString());
//        return sql.toString();
//    }
//
//    public String deleteByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
//        logger.info("BaseSqlProvider deleteByField sql start...");
//
//        Assert.notNull(clazz, "clazz must be not null");
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//        sql.WHERE(String.format("%s = #{fieldValue}", fieldName));
//
//        logger.info("BaseSqlProvider deleteByField sql: {} ", sql.toString());
//        return sql.toString();
//    }
//
//    public String deleteByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames) throws DaoException {
//        logger.info("BaseSqlProvider deleteByFields sql start...");
//
//        Assert.notNull(clazz, "clazz must be not null");
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//
//        for (int i = 0, len = fieldNames.length; i < len; i++) {
//            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
//        }
//
//        logger.info("BaseSqlProvider deleteByFields sql: {} ", sql.toString());
//        return sql.toString();
//    }
//
//    public String deleteByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
//        logger.info("BaseSqlProvider deleteByMap sql start...");
//
//        Assert.notNull(clazz, "clazz must be not null");
//        TableInfo info = TableInfoHelper.getTableInfo(clazz);
//        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());
//
//        SQL sql = new SQL();
//        sql.DELETE_FROM(info.getTableName());
//        paramsToWhere(params,sql);
//
//        logger.info("BaseSqlProvider deleteByMap sql: {} ", sql.toString());
//        return sql.toString();
//    }

    /**
     * get
     */

    public String getById(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider getById sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getById sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
        logger.info("BaseSqlProvider getByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getByMap sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider getByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(String.format("%s = #{fieldValue}", fieldName));
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames) throws DaoException {
        logger.info("BaseSqlProvider getByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getVoById(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider getVoById sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(String.format("%s = #{%s}", info.getKeyColumn(), info.getKeyProperty()));
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getVoById sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getVoByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
        logger.info("BaseSqlProvider getVoByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getVoByMap sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getVoByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider getVoByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(String.format("%s = #{fieldValue}", fieldName));
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getVoByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String getVoByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues) throws DaoException {
        logger.info("BaseSqlProvider getVoByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider getVoByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    /**
     * list
     */

    public String listAll(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider listAll sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listAll sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listAllAndOrder(@Param("class") Class clazz, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider listAllAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC"));

        logger.info("BaseSqlProvider listAllAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByIds(@Param("class") Class clazz, @Param("ids") Serializable[] ids) throws DaoException {
        logger.info("BaseSqlProvider listByIds sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(String.format("%s IN (%s)", info.getKeyColumn(), StringUtils.join(ids, ",")));
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listByIds sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider listByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(fieldName + " = #{fieldValue}");
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames) throws DaoException {
        logger.info("BaseSqlProvider listByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByFieldAndOrder(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider listByFieldAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(fieldName + " = #{fieldValue}");
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC"));

        logger.info("BaseSqlProvider listByFieldAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByFieldsAndOrder(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider listByFieldsAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC"));

        logger.info("BaseSqlProvider listByFieldsAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByFieldsAndOrders(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings) throws DaoException {
        logger.info("BaseSqlProvider listByFieldsAndOrders sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.WHERE(info.getLogicDeleteSql(false, false));
        for (int i = 0, len = orderNames.length; i < len; i++) {
            sql.ORDER_BY(orderNames[i] + (ascendings[i] ? " ASC" : " DESC"));
        }

        logger.info("BaseSqlProvider listByFieldsAndOrders sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
        logger.info("BaseSqlProvider listByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listByMap sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByMapAndOrder(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider listByMapAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC"));

        logger.info("BaseSqlProvider listByMapAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String listByMapAndOrders(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings) throws DaoException {
        logger.info("BaseSqlProvider listByMapAndOrders sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        for (int i = 0, len = orderNames.length; i < len; i++) {
            sql.ORDER_BY(orderNames[i] + (ascendings[i] ? " ASC" : " DESC"));
        }

        logger.info("BaseSqlProvider listByMapAndOrders sql: {} ", sql.toString());
        return sql.toString();
    }

    private void paramsToWhere(@Param("params") Map<String, String> params, SQL sql) {
        if (CollectionUtils.isNotEmpty(params)) {
            StringBuilder sb_and = new StringBuilder();
            StringBuilder sb_or = new StringBuilder();
            params.forEach((k, v) -> {
                if (StringUtils.equals(StringPool.AND, v)) {
                    if (sb_and.length() > 0) {
                        sb_and.append(" ").append(StringPool.AND).append(" ");
                    }
                    sb_and.append(String.format("%s", k));
                }

                if (StringUtils.equals(StringPool.OR, v)) {
                    if (sb_or.length() > 0) {
                        sb_or.append(" ").append(StringPool.OR).append(" ");
                    }
                    sb_or.append(String.format("%s", k));
                }
            });
            if (sb_and.length() > 0) {
                sql.WHERE(sb_and.toString());
            }
            if (sb_or.length() > 0) {
                sql.AND();
                sql.WHERE(sb_or.toString());
            }
        }
    }


    /**
     * query
     */

    private int getFromNums(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    private String limitStr(int pageNo, int pageSize) {
        return String.format(" limit %s , %s ", getFromNums(pageNo, pageSize), pageSize);
    }

    public String queryAll(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) throws DaoException {
        logger.info("BaseSqlProvider queryAll sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider listAll sql: {} ", sql.toString() + limitStr(pageNo, pageSize));
        return sql.toString() + limitStr(pageNo, pageSize);
    }

    public String queryAllAndOrder(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider queryAllAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC") + limitStr(pageNo, pageSize));

        logger.info("BaseSqlProvider queryAllAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String queryByField(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider queryByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        sql.WHERE(fieldName + " = #{fieldValue}");
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(info.getKeyColumn() + limitStr(pageNo, pageSize));

        logger.info("BaseSqlProvider queryByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String queryByFields(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames) throws DaoException {
        logger.info("BaseSqlProvider queryByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(info.getKeyColumn() + limitStr(pageNo, pageSize));

        logger.info("BaseSqlProvider queryByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    public String queryByFieldsAndOrder(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider queryByFieldsAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));
        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC") + limitStr(pageNo, pageSize));

        logger.info("BaseSqlProvider queryByFieldsAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String queryByFieldsAndOrders(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings) throws DaoException {
        logger.info("BaseSqlProvider queryByFieldsAndOrders sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));
        for (int i = 0, len = orderNames.length; i < len; i++) {
            sql.ORDER_BY(orderNames[i] + (ascendings[i] ? " ASC" : " DESC"));
        }

        logger.info("BaseSqlProvider queryByFieldsAndOrders sql: {} ", sql.toString() + limitStr(pageNo, pageSize));
        return sql.toString() + limitStr(pageNo, pageSize);
    }

    public String queryByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) throws DaoException {
        logger.info("BaseSqlProvider queryByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider queryByMap sql: {} ", sql.toString() + limitStr(pageNo, pageSize));
        return sql.toString() + limitStr(pageNo, pageSize);
    }

    public String queryByMapAndOrder(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderName") String orderName, @Param("ascending") boolean ascending) throws DaoException {
        logger.info("BaseSqlProvider queryByMapAndOrder sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());

        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        sql.ORDER_BY(orderName + (ascending ? " ASC" : " DESC") + limitStr(pageNo, pageSize));

        logger.info("BaseSqlProvider queryByMapAndOrder sql: {} ", sql.toString());
        return sql.toString();
    }

    public String queryByMapAndOrders(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderNames") String[] orderNames, @Param("ascendings") Boolean[] ascendings) throws DaoException {
        logger.info("BaseSqlProvider queryByMapAndOrders sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT(info.getAllSqlSelect());
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        for (int i = 0, len = orderNames.length; i < len; i++) {
            sql.ORDER_BY(orderNames[i] + (ascendings[i] ? " ASC" : " DESC"));
        }

        logger.info("BaseSqlProvider queryByMapAndOrders sql: {} ", sql.toString() + limitStr(pageNo, pageSize));
        return sql.toString() + limitStr(pageNo, pageSize);
    }


    /**
     * count
     */

    public String count(@Param("class") Class clazz) throws DaoException {
        logger.info("BaseSqlProvider count sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT("count(" + info.getKeyColumn() + ")");
        sql.FROM(info.getTableName());
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider count sql: {} ", sql.toString());
        return sql.toString();
    }

    public String countByField(@Param("class") Class clazz, @Param("fieldName") String fieldName) throws DaoException {
        logger.info("BaseSqlProvider countByField sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT("count(" + info.getKeyColumn() + ")");
        sql.FROM(info.getTableName());
        sql.WHERE(fieldName + " = #{fieldValue}");
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider countByField sql: {} ", sql.toString());
        return sql.toString();
    }

    public String countByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames) throws DaoException {
        logger.info("BaseSqlProvider countByFields sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT("count(" + info.getKeyColumn() + ")");
        sql.FROM(info.getTableName());
        for (int i = 0, len = fieldNames.length; i < len; i++) {
            sql.WHERE(String.format("%s = #{fieldValues[%s]}", fieldNames[i], i));
        }
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider countByFields sql: {} ", sql.toString());
        return sql.toString();
    }

    public String countByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params) throws DaoException {
        logger.info("BaseSqlProvider countByMap sql start...");

        Assert.notNull(clazz, "clazz must be not null");
        TableInfo info = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(info, "Undiscovered table info . " + clazz.getName());

        SQL sql = new SQL();
        sql.SELECT("count(" + info.getKeyColumn() + ")");
        sql.FROM(info.getTableName());
        paramsToWhere(params, sql);
        sql.AND();
        sql.WHERE(info.getLogicDeleteSql(false, false));

        logger.info("BaseSqlProvider countByMap sql: {} ", sql.toString());
        return sql.toString();
    }


}
