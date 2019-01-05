package com.bingo.config.interceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingo.encrypt.util.KeyThreadLocalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bingo.core.config.ClientSecretProperty;
import com.bingo.core.interceptorAdapter.AbstractTokenCheckInterceptorAdapter;
import com.bingo.core.model.InputForm;
import com.bingo.core.model.JsonResult;
import com.bingo.core.threadLocal.CurrentUserUtils;
import com.bingo.core.toolkit.JsonResultValidator;
import com.bingo.core.web.model.UserSession;

/**
 * token拦截器
 * 验证token， 设置用户信息
 *
 * @Auther: 郑海育
 * @Date: 2018/10/22
 * @Description:
 */
@Component
public class TokenCheckInterceptorAdapter extends AbstractTokenCheckInterceptorAdapter {

   /* private static final Logger logger = LoggerFactory.getLogger(TokenCheckInterceptorAdapter.class);

    @Resource
    @Qualifier("feignSsoClient")
    private FeignSsoClient feignSsoClient;
    @Resource
    private ClientSecretProperty clientSecretProperty;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        InputForm form = preCheck(request, clientSecretProperty.getClientId(), clientSecretProperty.getClientSecret());

        JsonResult jsonResult = feignSsoClient.tokenCheck(form);
        logger.info("feignSsoConfig.tokenCheck: {} ", jsonResult);

        JsonResultValidator.check(jsonResult);

        UserSession userSession = JSON.parseObject(jsonResult.getData().toString(), UserSession.class);
        CurrentUserUtils.setUser(userSession);

        KeyThreadLocalUtils.setKey(userSession.getKey());

        return true;
    }*/
}
