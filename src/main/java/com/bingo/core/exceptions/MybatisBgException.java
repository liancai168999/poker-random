package com.bingo.core.exceptions;

/**
 * Mybatis bingo 异常类
 */
public class MybatisBgException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public MybatisBgException(String message) {
        super(message);
    }

    public MybatisBgException(Throwable throwable) {
        super(throwable);
    }

    public MybatisBgException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
