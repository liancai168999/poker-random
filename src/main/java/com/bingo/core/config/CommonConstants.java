package com.bingo.core.config;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/22
 * @Description:
 */
public class CommonConstants {

    public static final String USER_TOKEN = "token";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";

    public static final String REDIS_USER_TOKEN = "user:tokens:";
    public static final String REDIS_USER_ID = "user:ids:";
    public static final String REDIS_USER_ONLINE = "user:onlines:";
    public static final String REDIS_USER_AES_KEY = "user:keys:";
    public static final String REDIS_RANDOM_FRONT_KEY = "random:front";
    public static final String REDIS_RANDOM_END_KEY = "random:end";

    public static final String SIGN_TIME_KEY = "timestamp";
    public static final String SIGN_TOKEN_AUTH_KEY = "authorization";
    public static final String SIGN_TOKEN_KEY = "token";
    public static final String SIGN_SIGN_KEY = "sign";
    public static final long SIGN_EXPIRE_TIME = 600000;

    public static final int INVITATION_CODE_LENGTH = 6;
    public static final int TOKEN_INVALID_TIME = 36000;

    public static final String RSA_AGENT_KEY = "User-Agent";
    public static final String RSA_VERSION_KEY = "version";
    public static final String RSA_APP_TYPE_KEY = "type";

    public static final String CLIENT_ID = "clientId";

    public static final long AES_KEY_INVALID_TIME = 36000;
}
