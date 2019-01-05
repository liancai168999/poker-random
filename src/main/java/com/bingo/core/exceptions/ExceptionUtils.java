package com.bingo.core.exceptions;

/**
 * 异常辅助工具类
 *
 */
public class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg 消息
     * @param t   异常信息
     * @return 返回异常
     */
    public static MybatisBgException mbg(String msg, Throwable t) {
        return new MybatisBgException(msg, t);
    }

    /**
     * 重载的方法
     *
     * @param msg 消息
     * @return 返回异常
     */
    public static MybatisBgException mbg(String msg) {
        return new MybatisBgException(msg);
    }

    /**
     * 重载的方法
     *
     * @param t 异常
     * @return 返回异常
     */
    public static MybatisBgException mbg(Throwable t) {
        return new MybatisBgException(t);
    }

}
