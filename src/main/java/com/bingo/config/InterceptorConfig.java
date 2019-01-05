package com.bingo.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bingo.config.interceptorAdapter.TokenCheckInterceptorAdapter;
import com.bingo.core.interceptorAdapter.CorsInterceptor;

/**
 * 拦截器配置
 *
 * @Auther: 郑海育
 * @Date: 2018/10/22
 * @Description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${prefix.service}")
    private String prefixService;
    @Value("${prefix.admin}")
    private String prefixAdmin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new com.bingo.config.CorsInterceptor()).addPathPatterns("/**");
    	/*registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");*/
    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }*/
}
