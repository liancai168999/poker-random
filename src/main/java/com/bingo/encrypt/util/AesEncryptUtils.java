package com.bingo.encrypt.util;

import com.bingo.encrypt.exception.InvalidDecryptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/17
 * @Description:
 */
public class AesEncryptUtils {
    private static final Logger logger = LoggerFactory.getLogger(AesEncryptUtils.class);

    private static final String KEY_ALGORITHM = "AES";
    //默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String encryptKey) throws InvalidDecryptException {
        KeyGenerator keyGenerator = null;
        try {

            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(128);
            // 转换为AES专用密钥
            return new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM);

        } catch (NoSuchAlgorithmException ex) {
            logger.error("AES密钥转换失败。", ex);
            throw new InvalidDecryptException("AES密钥转换失败。" + ex.getMessage());
        }
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws InvalidDecryptException {

        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(encryptKey));
            return cipher.doFinal(content.getBytes("utf-8"));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | InvalidKeyException e) {
            logger.error("AES密钥加密数据失败。", e);
            throw new InvalidDecryptException("AES密钥加密数据失败。" + e.getMessage());
        }
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return SecurityBase64.toBase64(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws InvalidDecryptException {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(decryptKey));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            logger.error("AES密钥解密数据失败。", e);
            throw new InvalidDecryptException("AES密钥解密数据失败。" + e.getMessage());
        }
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(SecurityBase64.fromBase64(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws Exception {
        final String KEY = "213435fsdfsdfsad";

        String content = "你好";
        System.out.println("加密前：" + content);

        String encrypt = aesEncrypt(content, KEY);
        System.out.println(encrypt.length() + ":加密后：" + encrypt);

        String decrypt = aesDecrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }
}
