package com.bingo.encrypt.exception;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/23
 * @Description:
 */
public class InvalidDecryptException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidDecryptException() {
    }

    public InvalidDecryptException(String message) {
        super(message);
    }

    public InvalidDecryptException(Throwable cause) {
        super(cause);
    }

    public InvalidDecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDecryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}