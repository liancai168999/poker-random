package com.bingo.core.interceptorAdapter;

import com.bingo.core.config.CommonConstants;
import com.bingo.core.exceptions.ServiceException;
import com.bingo.core.model.InputForm;
import com.bingo.core.threadLocal.CurrentUserUtils;
import com.bingo.core.toolkit.IPUtils;
import com.bingo.core.toolkit.JsonUtils;
import com.bingo.core.toolkit.StringUtils;
import com.bingo.core.toolkit.SystemClock;
import com.bingo.core.toolkit.encrypt.AesEncryptUtils;
import com.bingo.encrypt.util.KeyThreadLocalUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: 郑海育
 * @Date: 2018/10/24
 * @Description:
 */
public abstract class AbstractTokenCheckInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTokenCheckInterceptorAdapter.class);


    protected InputForm preCheck(HttpServletRequest request, String clientId, String clientScrtet) {
        logger.info("TokenCheckInterceptorAdapter preHandle...");

        String ip = IPUtils.getIpAddr(request);
        String agent = IPUtils.getUserAgent(request);
        String version = IPUtils.getVersion(request);
        String token = request.getHeader(CommonConstants.SIGN_TOKEN_AUTH_KEY);
        if (StringUtils.contains(token, "Bearer")) {
            token = StringUtils.substringAfter(token, " ");
        }

        logger.info("token: {} ", token);
        logger.info("ip: {} ", ip);
        logger.info("agent: {} ", agent);
        logger.info("version: {} ", version);

        InputForm form = new InputForm();
        Map<String, String> params = form.getRecord();
        params.put(CommonConstants.SIGN_TOKEN_KEY, token);
        params.put(CommonConstants.CLIENT_ID, clientId);
        params.put(CommonConstants.SIGN_TIME_KEY, String.valueOf(SystemClock.now()));

        Map<String, String> sign = Maps.newHashMap();
        sign.put(CommonConstants.SIGN_TIME_KEY, params.get(CommonConstants.SIGN_TIME_KEY));
        sign.put(CommonConstants.SIGN_TOKEN_KEY, token);

        try {
            params.put(CommonConstants.SIGN_SIGN_KEY, AesEncryptUtils.aesEncrypt(JsonUtils.objectToJson(sign), clientScrtet));
        } catch (Exception e) {
            logger.error("aesEncrypt error: {} ", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        logger.info("InputForm: {} ", form);

        return form;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("TokenCheckInterceptorAdapter postHandle...");

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("TokenCheckInterceptorAdapter afterCompletion...");

        CurrentUserUtils.clearAllUserInfo();
        logger.info("CurrentUserUtils.clearAllUserInfo...");

        KeyThreadLocalUtils.clearAllKey();
        logger.info("KeyThreadLocalUtils.clearAllUserInfo...");

        super.afterCompletion(request, response, handler, ex);
    }
}