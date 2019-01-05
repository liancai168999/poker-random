package com.bingo.encrypt.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class SecurityBase64 {
    /**
     * Base64加密
     *
     * @param str
     * @return
     */
    public static String toBase64(String str) {
        byte[] bytes = null;
        try {
            bytes = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return toBase64(bytes);
    }
    public static String toBase64( byte[] bytes) {
        String result = null;
        if (bytes != null) {
            result = new Base64().encodeToString(bytes);
        }
        return result;
    }

    /**
     * Base64解密
     *
     * @param str
     * @return
     */
    public static byte[] fromBase64(String str) {
        return new Base64().decode(str.getBytes());
    }
    public static byte[] fromBase64(byte[] bytes) {
        return new Base64().decode(bytes);
    }
}
