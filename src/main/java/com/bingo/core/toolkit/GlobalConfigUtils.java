package com.bingo.core.toolkit;

import com.bingo.core.config.DbConfig;
import com.bingo.core.config.GlobalConfig;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis全局缓存工具类
 */
public class GlobalConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(GlobalConfigUtils.class);

    /**
     * 缓存全局信息
     */
    private static final Map<String, GlobalConfig> GLOBAL_CONFIG = new ConcurrentHashMap<>();

    public static GlobalConfig defaults() {
        return new GlobalConfig().setDbConfig(new DbConfig());
    }

    /**
     * 获取MybatisGlobalConfig (统一所有入口)
     * @param configuration
     * @return
     */
    public static GlobalConfig getGlobalConfig(Configuration configuration) {
        Assert.notNull(configuration, "Error: You need Initialize MybatisConfiguration !");
        return getGlobalConfig(configuration.toString());
    }

    /**
     * 获取MybatisGlobalConfig (统一所有入口)
     * @param configMark
     * @return
     */
    public static GlobalConfig getGlobalConfig(String configMark) {
        GlobalConfig cache = GLOBAL_CONFIG.get(configMark);
        if (cache == null) {
            // 没有获取全局配置初始全局配置
            logger.debug("DeBug: MyBatis Bingo Global configuration Initializing !");
            GlobalConfig globalConfig = defaults();
            GLOBAL_CONFIG.put(configMark, globalConfig);
            return globalConfig;
        }
        return cache;
    }

}
