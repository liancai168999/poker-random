package com.bingo.encrypt.exception;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/26
 * @Description:
 */
public class InvalidEncryptException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidEncryptException() {
    }

    public InvalidEncryptException(String message) {
        super(message);
    }

    public InvalidEncryptException(Throwable cause) {
        super(cause);
    }

    public InvalidEncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
