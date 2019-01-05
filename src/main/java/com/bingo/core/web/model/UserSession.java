package com.bingo.core.web.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther: 郑海育
 * @date: 2018/10/22
 * @description:
 */
public class UserSession implements Serializable {

    /**
     * 用户id
     **/
    private String userId;
    /**
     * 邀请码
     **/
    private String invitationCode;
    /**
     * 姓名
     **/
    private String userName;
    /**
     * 账号
     **/
    private String accountName;
    /**
     * 用户类型
     **/
    private String userType;
    /**
     * 用户头像
     **/
    private String pic;
    /**
     * 邀请码开通状态
     **/
    private String openInvitationCode;
    /**
     * 投注状态
     **/
    private String betStatus;
    /**
     * 提现状态
     **/
    private String withdrawStatus;
    /**
     * 用户状态
     **/
    private String userStatus;
    /**
     * 注册钱包状态
     **/
    private String haveWallet;


    @JSONField(serialize = false)
    @JsonIgnore
    private String token;

    private String mobile;
    private String email;


    /**
     * 登录时间
     **/
    private String loginTime;
    /**
     * ip
     **/
    private String ipAddress;
    private String agent;
    private String version;

    private String key;

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", invitationCode='" + invitationCode + '\'' +
                ", userName='" + userName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", token='" + token + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", agent='" + agent + '\'' +
                ", version='" + version + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOpenInvitationCode() {
        return openInvitationCode;
    }

    public void setOpenInvitationCode(String openInvitationCode) {
        this.openInvitationCode = openInvitationCode;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getHaveWallet() {
        return haveWallet;
    }

    public void setHaveWallet(String haveWallet) {
        this.haveWallet = haveWallet;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
