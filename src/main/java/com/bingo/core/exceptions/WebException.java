package com.bingo.core.exceptions;

public class WebException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WebException() {
    }

    public WebException(String message) {
        super(message);
    }

    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
