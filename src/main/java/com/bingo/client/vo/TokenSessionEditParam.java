package com.bingo.client.vo;

import java.io.Serializable;

/**
 * @Auther: 郑海育
 * @Date: 2018/11/30
 * @Description:
 */
public class TokenSessionEditParam implements Serializable {

    private String token;
    private String userId;
    private String userStatus;
    private String betStatus;
    private String withdrawStatus;

    public TokenSessionEditParam() {
    }


    public String getUserId() {
        return userId;
    }

    public TokenSessionEditParam setUserId(String userId) {
        this.userId = userId;return this;
    }

    public String getToken() {
        return token;
    }

    public TokenSessionEditParam setToken(String token) {
        this.token = token;return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public TokenSessionEditParam setUserStatus(String userStatus) {
        this.userStatus = userStatus;return this;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public TokenSessionEditParam setBetStatus(String betStatus) {
        this.betStatus = betStatus;return this;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public TokenSessionEditParam setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;return this;
    }
}
