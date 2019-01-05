package com.bingo.core.annotation;

public enum UpdateType {
    DB("db", "数据库函数"),
    FIXED("fixed", "固态值");

    /**
     * 主键
     */
    private final String type;

    /**
     * 描述
     */
    private final String desc;

    UpdateType(final String type, final String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
