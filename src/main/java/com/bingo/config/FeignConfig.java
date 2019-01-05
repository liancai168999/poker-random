package com.bingo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/23
 * @Description:
 */
@ConfigurationProperties(prefix = "feign")
@Component
public class FeignConfig {
    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    public String ssoServerIp;
    
    public String backWalletIp;

    public String timeOutMillis;


    public String getTimeOutMillis() {
        return timeOutMillis;
    }

    public void setTimeOutMillis(String timeOutMillis) {
        this.timeOutMillis = timeOutMillis;
    }

    public String getSsoServerIp() {
        return ssoServerIp;
    }

    public void setSsoServerIp(String ssoServerIp) {
        this.ssoServerIp = ssoServerIp;
    }

	public String getBackWalletIp() {
		return backWalletIp;
	}

	public void setBackWalletIp(String backWalletIp) {
		this.backWalletIp = backWalletIp;
	}
}
