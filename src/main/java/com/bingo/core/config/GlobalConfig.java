package com.bingo.core.config;

/**
 * Mybatis 全局缓存
 */
public class GlobalConfig {

    /**
     * 数据库相关配置
     */
    private DbConfig dbConfig;

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public GlobalConfig setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
        return this;
    }
}
