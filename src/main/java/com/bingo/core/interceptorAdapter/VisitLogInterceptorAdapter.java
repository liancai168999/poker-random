package com.bingo.core.interceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.bingo.core.toolkit.IPUtils;
import com.bingo.encrypt.util.KeyThreadLocalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问拦截器
 * 过滤无效ip
 *
 * @Auther: 郑海育
 * @Date: 2018/10/22
 * @Description:
 */
public class VisitLogInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(VisitLogInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        logger.info("VisitLogInterceptorAdapter preHandle...");

        String ip = IPUtils.getIpAddr(request);
        logger.info("VisitLog ip: {} ", ip);

        String userArgent = IPUtils.getUserAgent(request);
        KeyThreadLocalUtils.setUserAgent(userArgent);

        try {
            StringBuffer buf = new StringBuffer();
            String parameter = request.getParameterMap() == null ? "" : JSON.toJSONString(request.getParameterMap());
            buf.append("|").append("X-Real-IP=").append(request.getHeader("X-Real-IP"));
            buf.append("|").append("RequestURL=").append(request.getRequestURL());
            buf.append("|").append("referer=").append(request.getHeader("referer"));
            buf.append("|").append("RequestedSessionId=").append(request.getRequestedSessionId());
            buf.append("|").append("User-Agent=").append(request.getHeader("User-Agent"));
            buf.append("|").append("parameter=").append(parameter);
            buf.append("|").append("SessionId=").append(request.getSession().getId());
            buf.append("|").append("RemoteAddr=").append(request.getRemoteAddr());
            buf.append("|").append("RemoteHost=").append(request.getRemoteHost());
            buf.append("|").append("RemotePort=").append(request.getRemotePort());
            buf.append("\n");
            logger.info(buf.toString());

            return super.preHandle(request, response, handler);
        } catch (Exception e) {
            logger.info("VisitLogInterceptorAdapter|preHandle|" + e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("VisitLogInterceptorAdapter postHandle...");

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("VisitLogInterceptorAdapter afterCompletion...");
        KeyThreadLocalUtils.clearAllUserAgent();
        super.afterCompletion(request, response, handler, ex);
    }
}
