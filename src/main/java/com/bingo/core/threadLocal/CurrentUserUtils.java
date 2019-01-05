package com.bingo.core.threadLocal;

import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.core.exceptions.TokenException;
import com.bingo.core.web.model.UserSession;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/22
 * @Description:
 */
public class CurrentUserUtils {

    private final static ThreadLocal<UserSession> LOGIN_USER = new ThreadLocal<>();

    public static void setUser(UserSession userSession) {
        LOGIN_USER.set(userSession);
    }

    /**
     * 如果没有登录，返回null
     *
     * @return
     */
    public static UserSession getUserIfLogin() {
        return LOGIN_USER.get();
    }

    /**
     * 如果没有登录会抛出异常
     *
     * @return
     */
    public static UserSession getUser() {
        UserSession user = LOGIN_USER.get();

        if (user == null) {
            throw new TokenException(ResultStatusEnum.UN_LOGIN.name());
        }

        return user;
    }

    public static void clearAllUserInfo() {
        LOGIN_USER.remove();
    }
}
