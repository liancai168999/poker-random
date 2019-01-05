package com.bingo.core.enums;

import java.io.Serializable;

/**
 * @Auther: 郑海育
 * @Date: 2018/11/8
 * @Description:
 */
public enum UserOperateFeeEnum implements IEnum {

    OpenInvitationcodeFee("邀请码开通手续费"),
    ModifiNicknameFee("昵称修改手续费"),
    GasFee("矿工费");

    private String name;

    private UserOperateFeeEnum(String name) {
        this.name = name;
    }

    public String Name() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return Name() ;
    }
}
