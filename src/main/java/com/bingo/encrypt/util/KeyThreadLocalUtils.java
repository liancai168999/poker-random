package com.bingo.encrypt.util;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
public class KeyThreadLocalUtils {

    private final static ThreadLocal<String> CURRENT_KEY = new ThreadLocal<String>();
    private final static ThreadLocal<Map<String, String>> CURRENT_RSA_KEY = new ThreadLocal<>();
    private final static ThreadLocal<String> USER_AGENT = new ThreadLocal<String>();

    public static void setUserAgent(String key) {
        USER_AGENT.set(key);
    }

    public static String getUserAgent() {
        return USER_AGENT.get();
    }

    public static void clearAllUserAgent() {
        USER_AGENT.remove();
    }

    public static void setKey(String key) {
        CURRENT_KEY.set(key);
    }

    public static String getKey() {
        return CURRENT_KEY.get();
    }

    public static String getKeyIfNull() {
        String key = CURRENT_KEY.get();

        if (key == null) {
            throw new NullPointerException();
        }
        return key;
    }

    public static void clearAllKey() {
        CURRENT_KEY.remove();
    }


    public static void setPrivateKey(String key) {
        Map<String, String> map = CURRENT_RSA_KEY.get();
        if (null == map) {
            map = new HashMap<>(2);
            CURRENT_RSA_KEY.set(map);
        }
        map.put("P", key);
    }

    public static void setPublicKey(String key) {
        CURRENT_RSA_KEY.get().put("G", key);
    }

    public static String getPrivateKey() {
        Map<String, String> map = CURRENT_RSA_KEY.get();
        if (null == map) {
            map = new HashMap<>(2);
            CURRENT_RSA_KEY.set(map);
        }
        return map.get("P");
    }

    public static String getPublicKey() {
        return CURRENT_RSA_KEY.get().get("G");
    }

    public static void clearAllRsaKey() {
        CURRENT_RSA_KEY.remove();
    }

}
