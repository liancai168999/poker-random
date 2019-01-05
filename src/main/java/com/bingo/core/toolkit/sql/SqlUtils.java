package com.bingo.core.toolkit.sql;

import com.bingo.core.annotation.DbType;
import com.bingo.core.annotation.SqlLike;
import com.bingo.core.toolkit.StringPool;
import org.springframework.util.Assert;

/**
 * SqlUtils工具类
 */
public class SqlUtils {
    private final static SqlFormatter SQL_FORMATTER = new SqlFormatter();
    /**
     * 格式sql
     *
     * @param boundSql
     * @param format
     * @return
     */
    public static String sqlFormat(String boundSql, boolean format) {
        if (format) {
            try {
                return SQL_FORMATTER.format(boundSql);
            } catch (Exception ignored) {
            }
        }
        return boundSql;
    }

    /**
     * <p>
     * 用%连接like
     * </p>
     *
     * @param str 原字符串
     * @return
     */
    public static String concatLike(String str, SqlLike type) {
        StringBuilder builder = new StringBuilder(str.length() + 3);
        switch (type) {
            case LEFT:
                builder.append(StringPool.PERCENT).append(str);
                break;
            case RIGHT:
                builder.append(str).append(StringPool.PERCENT);
                break;
            case CUSTOM:
                builder.append(str);
                break;
            default:
                builder.append(StringPool.PERCENT).append(str).append(StringPool.PERCENT);
        }
        return builder.toString();
    }

    /**
     * <p>
     * 获取需要转义的SQL字段
     * </p>
     *
     * @param dbType   数据库类型
     * @param val      值
     * @param isColumn val 是否是数据库字段
     */
    public static String sqlWordConvert(DbType dbType, String val, boolean isColumn) {

        return val;
    }

    /**
     * <p>
     * SQL注入内容剥离
     * </p>
     *
     * @param sql 待处理 SQL 内容
     * @return this
     */
    public static String stripSqlInjection(String sql) {
        Assert.notNull(sql, "strip sql is null.");
        return sql.replaceAll("('.+--)|(--)|(\\|)|(%7C)", StringPool.EMPTY);
    }
}
