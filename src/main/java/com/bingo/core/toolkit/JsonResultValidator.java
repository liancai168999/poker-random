package com.bingo.core.toolkit;

import com.bingo.core.exceptions.TokenException;
import com.bingo.core.model.JsonResult;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/16
 * @Description:
 */
public class JsonResultValidator {

    public static void check(JsonResult jsonResult) throws TokenException {
        if (!jsonResult.isOK()) {
            throw new TokenException(String.format("%s|%s",jsonResult.getStatusCode(), jsonResult.getStatusMsg()));
        }
    }
}
