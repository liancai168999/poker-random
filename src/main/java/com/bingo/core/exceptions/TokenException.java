package com.bingo.core.exceptions;

import com.bingo.core.enums.ResultStatusEnum;

/**
 * @Auther: 郑海育
 * @Date: 2018/11/9
 * @Description:
 */
public class TokenException extends RuntimeException {

    public TokenException(ResultStatusEnum statusEnum) {
        super(statusEnum.name());
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Exception e) {
        super(message, e);
    }

}
