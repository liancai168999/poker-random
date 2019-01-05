package com.bingo.encrypt.advice;

import com.alibaba.fastjson.serializer.MapSerializer;
import com.bingo.encrypt.annotation.Encrypt;
import com.bingo.encrypt.annotation.EncryptRsa;
import com.bingo.encrypt.auto.EncryptProperties;
import com.bingo.encrypt.exception.InvalidDecryptException;
import com.bingo.encrypt.exception.InvalidEncryptException;
import com.bingo.encrypt.util.AesEncryptUtils;
import com.bingo.encrypt.util.KeyThreadLocalUtils;
import com.bingo.encrypt.util.RSAKeyPairFactory;
import com.bingo.encrypt.util.SecurityBase64;
import com.bingo.encrypt.util.SystemClock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应处理类
 *
 * <p>对加了@Encrypt的方法的数据进行加密操作</p>
 *
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EncryptProperties encryptProperties;

    private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<Boolean>();

    public static void setEncryptStatus(boolean status) {
        encryptLocal.set(status);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private boolean isAndroid() {
        return org.apache.commons.lang3.StringUtils.equals("android", KeyThreadLocalUtils.getUserAgent());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        // 可以通过调用EncryptResponseBodyAdvice.setEncryptStatus(false);来动态设置不加密操作
        Boolean status = encryptLocal.get();
        if (status != null && status == false) {
            encryptLocal.remove();
            return body;
        }

        if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && !encryptProperties.isDebug() && !isAndroid()) {
            return encrypt(body);
        }

        if (returnType.getMethod().isAnnotationPresent(EncryptRsa.class) && !encryptProperties.isDebug() && !isAndroid()) {
            return encryptRsa(body);
        }

        return body;
    }

    private Object encrypt(Object body) {
        try {
            long startTime = SystemClock.now();
            String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            logger.info("content result: {}", content);

            if (!StringUtils.hasText(encryptProperties.getKey())) {
                throw new NullPointerException("请配置spring.encrypt.key");
            }

            String key = encryptProperties.isThreadLocal() ? KeyThreadLocalUtils.getKey() : encryptProperties.getKey();
            logger.info("Encrypt key: {}", key);
            if (org.apache.commons.lang3.StringUtils.isBlank(key)) {
                throw new InvalidKeyException("AES KEY 不存在。");
            }

            String result = AesEncryptUtils.aesEncrypt(content, key);
            logger.info("Encrypt result: {}", result);

            long endTime = SystemClock.now();
            logger.info("Encrypt Time: {}", (endTime - startTime));

            if (!StringUtils.isEmpty(encryptProperties.getValueField())) {
                Map<String, String> results = new HashMap<>(2);
                results.put(encryptProperties.getValueField(), result);
                return results;
            }

            return result;
        } catch (Exception e) {
            logger.error("加密数据异常", e);
            KeyThreadLocalUtils.clearAllKey();
            logger.info("EncryptResponseBodyAdvice clearAllKey()...");
            throw new InvalidEncryptException(e);
        } finally {

        }
    }

    private Object encryptRsa(Object body) {
        try {
            long startTime = SystemClock.now();

            if (!encryptProperties.isThreadLocal()) {
                throw new IllegalArgumentException("配置错误，使用@DecryptRsa必须启用ThreadLocal。");
            }

            String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            logger.info("content result: {}", content);

            logger.info("KeyThreadLocalUtils.getPrivateKey(): {}", KeyThreadLocalUtils.getPrivateKey());
            logger.info("KeyThreadLocalUtils.getPublicKey(): {}", KeyThreadLocalUtils.getPublicKey());

            byte[] bytes = RSAKeyPairFactory.encryptByPublicKey(content.getBytes(), KeyThreadLocalUtils.getPublicKey());
            String sign = RSAKeyPairFactory.sign(content.getBytes(), KeyThreadLocalUtils.getPrivateKey());

            StringBuffer sb = new StringBuffer(SecurityBase64.toBase64(bytes));
            if (org.apache.commons.lang3.StringUtils.isNotBlank(encryptProperties.getSeparateRsa())) {
                sb.append(encryptProperties.getSeparateRsa());
            }
            sb.append(sign);

            bytes = RSAKeyPairFactory.encryptByPublicKey(sb.toString().getBytes(), KeyThreadLocalUtils.getPublicKey());

            long endTime = SystemClock.now();
            logger.info("Encrypt Time: {}", (endTime - startTime));

            if (!StringUtils.isEmpty(encryptProperties.getValueField())) {
                Map<String, String> results = new HashMap<>(2);
                results.put(encryptProperties.getValueField(), SecurityBase64.toBase64(bytes));
                return results;
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error("加密数据异常", e);
            KeyThreadLocalUtils.clearAllKey();
            logger.info("EncryptResponseBodyAdvice clearAllKey()...");
            throw new InvalidEncryptException(e);
        } finally {

        }
    }


//    private Object encryptRsa(Object body) {
//        try {
//            long startTime = SystemClock.now();
//
//            if (!encryptProperties.isThreadLocal()) {
//                throw new IllegalArgumentException("配置错误，使用@DecryptRsa必须启用ThreadLocal。");
//            }
//
//            String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
//
//            logger.info("KeyThreadLocalUtils.getPrivateKey(): {}", KeyThreadLocalUtils.getPrivateKey());
//            logger.info("KeyThreadLocalUtils.getPublicKey(): {}", KeyThreadLocalUtils.getPublicKey());
//
//            byte[] bytes = RSAKeyPairFactory.encryptByPublicKey(content.getBytes(), KeyThreadLocalUtils.getPublicKey());
//            String sign = RSAKeyPairFactory.sign(content.getBytes(), KeyThreadLocalUtils.getPrivateKey());
//
//            StringBuffer sb=new StringBuffer(SecurityBase64.toBase64(bytes));
//            if(org.apache.commons.lang3.StringUtils.isNotBlank(encryptProperties.getSeparateRsa())){
//                sb.append(encryptProperties.getSeparateRsa());
//            }
//            sb.append(sign);
//
//            long endTime = SystemClock.now();
//            logger.info("Encrypt Time: {}", (endTime - startTime));
//
//            if (!StringUtils.isEmpty(encryptProperties.getValueField())) {
//                Map<String, String> results = new HashMap<>(2);
//                results.put(encryptProperties.getValueField(), sb.toString());
//                return results;
//            }
//
//            return sb.toString();
//        } catch (Exception e) {
//            logger.error("加密数据异常", e);
//            throw new InvalidEncryptException(e);
//        } finally {
//            KeyThreadLocalUtils.clearAllKey();
//            logger.info("EncryptResponseBodyAdvice clearAllKey()...");
//        }
//    }


}
