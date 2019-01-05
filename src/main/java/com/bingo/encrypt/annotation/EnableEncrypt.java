package com.bingo.encrypt.annotation;

import com.bingo.encrypt.auto.EncryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用加密Starter
 * <p>在Spring Boot启动类上加上此注解<p>
 *
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptAutoConfiguration.class})
public @interface EnableEncrypt {
}
