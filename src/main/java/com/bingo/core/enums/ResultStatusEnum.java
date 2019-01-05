package com.bingo.core.enums;

public enum ResultStatusEnum {

    OK(200, "OK"),

    UN_LOGIN(30001, "未登录"),
    INVALID_LOGIN(30002, "已在其他设备登录。"),

    TOKEN_LOSE(31001, "Token失效"),
    LACK_TOKEN(31002, "缺少Token"),

    PARAMS_ERROR(32001, "参数异常"),

    NULL_PAGE(33001, "页面不存在"),
    NO_PERMISSION(33002, "无访问权限"),
    BAD_REQUEST(33003, "异常请求"),
    NULL_DATA(33005, "空数据返回"),
    ERROR_DATA(33006, "操作失败"),

    //410xx sec
    ERROR_AES_Key_CONVE(41001, "AES密钥转换失败"),
    ERROR_AES_Key_ENCRYPT(41002, "AES密钥加密失败"),
    ERROR_AES_Key_DECRYPT(41003, "AES密钥解密失败"),

    //413xx 用户
    ERROR_AVATAR(41301, "系统头像不存在。"),

    SERVER(10002, "内部服务器错误"),
    REMOTE_SERVER_ERROR(11001, "服务器异常"),
    EXCEPTION(10001, "异常抛出");


    private int code;
    private String message;

    private ResultStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (ResultStatusEnum ms : ResultStatusEnum.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
