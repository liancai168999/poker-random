package com.bingo.core.annotation;

/**
 * 数据库类型
 */
public enum DbType {
    /**
     * MYSQL
     */
    MYSQL("mysql", "%s LIKE CONCAT('%%',#{%s},'%%')", "MySql数据库");

    /**
     * 数据库名称
     */
    private final String db;
    /**
     * LIKE 拼接模式
     */
    private final String like;
    /**
     * 描述
     */
    private final String desc;


    DbType(String db, String like, String desc) {
        this.db = db;
        this.like = like;
        this.desc = desc;
    }

    /**
     * <p>
     * 获取数据库类型（默认 MySql）
     * </p>
     *
     * @param dbType 数据库类型字符串
     */
    public static DbType getDbType(String dbType) {
        DbType[] dts = DbType.values();
        for (DbType dt1 : dts) {
            if (dt1.getDb().equalsIgnoreCase(dbType)) {
                return dt1;
            }
        }
        return MYSQL;
    }


    public String getDb() {
        return db;
    }


    public String getLike() {
        return like;
    }


    public String getDesc() {
        return desc;
    }

}
