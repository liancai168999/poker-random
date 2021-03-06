package com.bingo.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/24
 * @Description:
 */
@ConfigurationProperties(prefix = "client")
@Component
public class ClientSecretProperty {

    private String clientId;
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}


