package com.game.www.common.betCqssc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: doudo
 * @Date: 2018/11/3 16:51
 */

@EnableAsync
@Configuration
public class TaskPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(TaskPoolConfig.class);

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池维护线程的最少数量
        executor.setCorePoolSize(20);
        // 线程池维护线程的最大数量
        executor.setMaxPoolSize(25);
        // 缓存队列
        executor.setQueueCapacity(200);
        // 允许的空闲时间
        executor.setKeepAliveSeconds(200);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
