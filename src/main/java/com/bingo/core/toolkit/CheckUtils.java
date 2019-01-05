package com.bingo.core.toolkit;

import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.core.exceptions.TokenException;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 参数校验方法
 */
public class CheckUtils {

    private static MessageSource resources;

    public static void setResources(MessageSource resources) {
        CheckUtils.resources = resources;
    }

    public static void check(boolean condition, String msgKey, Object... args) {
        if (!condition) {
            fail(msgKey, args);
        }
    }

    public static void notEmpty(String str, String msgKey, Object... args) {
        if (str == null || str.isEmpty()) {
            fail(msgKey, args);
        }
    }

    public static void notNull(Object obj, String msgKey, Object... args) {
        if (obj == null) {
            fail(msgKey, args);
        }
    }

    private static void fail(String msgKey, Object... args) {
        throw new TokenException(String.format("%s|%s", ResultStatusEnum.PARAMS_ERROR.getCode(), resources.getMessage(msgKey, args, Locale.CHINA)));
    }

}
