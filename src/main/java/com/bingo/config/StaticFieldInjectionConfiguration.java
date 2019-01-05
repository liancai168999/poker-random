package com.bingo.config;

import com.bingo.core.exceptions.GlobalExceptionHandler;
import com.bingo.core.toolkit.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class StaticFieldInjectionConfiguration {
    private final Logger logger = LoggerFactory.getLogger(StaticFieldInjectionConfiguration.class);


    @Resource
    private MessageSource resources;

    @PostConstruct
    private void init() {
        logger.info("StaticFieldInjectionConfiguration start...");

        CheckUtils.setResources(resources);
        GlobalExceptionHandler.setResources(resources);
    }

}
