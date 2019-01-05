package com.bingo.encrypt.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingo.encrypt.annotation.Decrypt;
import com.bingo.encrypt.annotation.DecryptRsa;
import com.bingo.encrypt.auto.EncryptProperties;
import com.bingo.encrypt.exception.InvalidDecryptException;
import com.bingo.encrypt.util.AesEncryptUtils;
import com.bingo.encrypt.util.KeyThreadLocalUtils;
import com.bingo.encrypt.util.RSAKeyPairFactory;
import com.bingo.encrypt.util.SecurityBase64;
import com.bingo.encrypt.util.SystemClock;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.util.Map;

/**
 * 请求数据接收处理类<br>
 *
 * <p><对加了@Decrypt的方法的数据进行解密操作<br>
 * 只对@RequestBody参数有效</p>
 *
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
@ControllerAdvice
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(EncryptRequestBodyAdvice.class);

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private boolean isAndroid() {
        return org.apache.commons.lang3.StringUtils.equals("android", KeyThreadLocalUtils.getUserAgent());
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        if (parameter.getMethod().isAnnotationPresent(Decrypt.class) && !encryptProperties.isDebug() && !isAndroid()) {
            try {
                String key = encryptProperties.isThreadLocal() ? KeyThreadLocalUtils.getKey() : encryptProperties.getKey();
                logger.info("Encrypt key: {}", key);

                return new DecryptHttpInputMessage(inputMessage, key, encryptProperties.getCharset(), encryptProperties.getKeyField());
            } catch (Exception e) {
                logger.error("数据解密失败", e);
                KeyThreadLocalUtils.clearAllKey();
                logger.info("EncryptRequestBodyAdvice clearAllKey()...");
                throw new InvalidDecryptException("数据解密失败" + e.getMessage());
            } finally {

            }
        }

        if (parameter.getMethod().isAnnotationPresent(DecryptRsa.class) && !encryptProperties.isDebug() && !isAndroid()) {
            try {
                if (!encryptProperties.isThreadLocal()) {
                    throw new IllegalArgumentException("配置错误，使用@DecryptRsa必须启用ThreadLocal。");
                }

                logger.info("KeyThreadLocalUtils.getPrivateKey(): {}", KeyThreadLocalUtils.getPrivateKey());
                logger.info("KeyThreadLocalUtils.getPublicKey(): {}", KeyThreadLocalUtils.getPublicKey());

                return new DecryptRSAHttpInputMessage(inputMessage, encryptProperties.getCharset(), encryptProperties.getKeyField(), encryptProperties.getSeparateRsa());

            } catch (Exception e) {
                logger.error("数据解密失败", e);
                KeyThreadLocalUtils.clearAllKey();
                logger.info("EncryptRequestBodyAdvice clearAllKey()...");
                throw new InvalidDecryptException("数据解密失败" + e.getMessage());
            } finally {

            }
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}

class DecryptHttpInputMessage implements HttpInputMessage {
    private Logger logger = LoggerFactory.getLogger(DecryptHttpInputMessage.class);
    private HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String key, String charset, String keyVal) throws Exception {
        logger.info("inputMessage: {} ", inputMessage);
        logger.info("key: {} ", key);
        logger.info("charset: {} ", charset);
        logger.info("keyVal: {} ", keyVal);

        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), charset);
        logger.info("content: {} ", content);

        Map jsonMap = JSON.parseObject(content);
        if (jsonMap.containsKey(keyVal)) {
            content = ((JSONObject) jsonMap).getString(keyVal);
        }
        logger.info("content2: {} ", content);

        long startTime = SystemClock.now();
        // JSON 数据格式的不进行解密操作
        String decryptBody = "";
        if (content.startsWith("{")) {
            decryptBody = content;
        } else {
            decryptBody = AesEncryptUtils.aesDecrypt(content, key);
        }
        long endTime = SystemClock.now();
        logger.info("Decrypt Time: {}", (endTime - startTime));
        this.body = IOUtils.toInputStream(decryptBody, charset);
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

}


class DecryptRSAHttpInputMessage implements HttpInputMessage {
    private Logger logger = LoggerFactory.getLogger(DecryptHttpInputMessage.class);
    private HttpHeaders headers;
    private InputStream body;

    public DecryptRSAHttpInputMessage(HttpInputMessage inputMessage, String charset, String keyVal, String separateRsa) throws Exception {
        logger.info("inputMessage: {} ", inputMessage);
        logger.info("charset: {} ", charset);
        logger.info("keyVal: {} ", keyVal);

        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), charset);
        logger.info("content: {} ", content);

        //获取加密内容
        Map jsonMap = JSON.parseObject(content);
        if (jsonMap.containsKey(keyVal)) {
            content = ((JSONObject) jsonMap).getString(keyVal);
        }
        logger.info("content2: {} ", content);

        //解密
        long startTime = SystemClock.now();
        // JSON 数据格式的不进行解密操作
        String decryptBody = "";
        if (content.startsWith("{")) {
            decryptBody = content;
        } else {
            byte[] bytes = RSAKeyPairFactory.decryptByPrivateKey(SecurityBase64.fromBase64(content), KeyThreadLocalUtils.getPrivateKey());
            decryptBody = new String(bytes);
        }
        logger.info("decryptBody: {} ", decryptBody);
        long endTime = SystemClock.now();
        logger.info("Decrypt Time: {}", (endTime - startTime));

        String sign = "";
        if (StringUtils.isNotBlank(separateRsa)) {
            sign = StringUtils.substringAfter(decryptBody, separateRsa);
            content = StringUtils.substringBefore(decryptBody, separateRsa);

            logger.info("sign: {} ", sign);
            logger.info("content3: {} ", content);
        }

        //解密json内容
        byte[] bytes = RSAKeyPairFactory.decryptByPrivateKey(SecurityBase64.fromBase64(content), KeyThreadLocalUtils.getPrivateKey());
        decryptBody = new String(bytes);
        logger.info("decryptBody: {} ", decryptBody);
        long endTime2 = SystemClock.now();
        logger.info("Decrypt2 Time: {}", (endTime2 - startTime));

        //签名验证
        boolean flag = RSAKeyPairFactory.verify(decryptBody.getBytes("UTF-8"), KeyThreadLocalUtils.getPublicKey(), sign);
        logger.info("verify: {} ", flag);
        if (!flag) {
            throw new InvalidKeyException("数据校验不过。");
        }

        this.body = IOUtils.toInputStream(decryptBody, charset);
    }

//    public DecryptRSAHttpInputMessage(HttpInputMessage inputMessage, String charset, String keyVal, String separateRsa) throws Exception {
//        logger.info("inputMessage: {} ", inputMessage);
//        logger.info("charset: {} ", charset);
//        logger.info("keyVal: {} ", keyVal);
//
//        this.headers = inputMessage.getHeaders();
//        String content = IOUtils.toString(inputMessage.getBody(), charset);
//        logger.info("content: {} ", content);
//
//        Map jsonMap = JSON.parseObject(content);
//        if (jsonMap.containsKey(keyVal)) {
//            content = ((JSONObject) jsonMap).getString(keyVal);
//        }
//        logger.info("content2: {} ", content);
//
//        String sign = "";
//        if (StringUtils.isNotBlank(separateRsa)) {
//            sign = StringUtils.substringAfter(content, separateRsa);
//            content = StringUtils.substringBefore(content, separateRsa);
//
//            logger.info("sign: {} ", sign);
//            logger.info("content3: {} ", content);
//        }
//
//        long startTime = SystemClock.now();
//        // JSON 数据格式的不进行解密操作
//        String decryptBody = "";
//        if (content.startsWith("{")) {
//            decryptBody = content;
//        } else {
//            byte[] bytes = RSAKeyPairFactory.decryptByPrivateKey(SecurityBase64.fromBase64(content), KeyThreadLocalUtils.getPrivateKey());
//            decryptBody = new String(bytes);
//        }
//        logger.info("decryptBody: {} ", decryptBody);
//        long endTime = SystemClock.now();
//        logger.info("Decrypt Time: {}", (endTime - startTime));
//
//        boolean flag = RSAKeyPairFactory.verify(decryptBody.getBytes("UTF-8"), KeyThreadLocalUtils.getPublicKey(), sign);
//        logger.info("verify: {} ", flag);
//        if (!flag) {
//            throw new InvalidKeyException("数据校验不过。");
//        }
//
//        this.body = IOUtils.toInputStream(decryptBody, charset);
//    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

}
