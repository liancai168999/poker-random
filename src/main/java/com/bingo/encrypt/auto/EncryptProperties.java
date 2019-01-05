package com.bingo.encrypt.auto;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置属性
 *
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    /**
     * AES加密KEY
     */
    private String key;

    private boolean debug = false;

    private String charset = "UTF-8";

    /**
     * 开启ThreadLocal传key
     */
    private boolean threadLocal = false;

    /**
     * 加密传的json key
     */
    private String keyField;
    /**
     * 加密回传传的json key
     */
    private String valueField;

    private String separateRsa;


    /**
     * 签名过期时间（分钟）
     */
    private Long signExpireTime = 10L;

    public String getSeparateRsa() {
        return separateRsa;
    }

    public void setSeparateRsa(String separateRsa) {
        this.separateRsa = separateRsa;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public boolean isThreadLocal() {
        return threadLocal;
    }

    public void setThreadLocal(boolean threadLocal) {
        this.threadLocal = threadLocal;
    }

    public Long getSignExpireTime() {
        return signExpireTime;
    }

    public void setSignExpireTime(Long signExpireTime) {
        this.signExpireTime = signExpireTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
