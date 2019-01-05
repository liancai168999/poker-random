package com.bingo.core.exceptions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.core.model.JsonResult;
import com.bingo.core.toolkit.CommonUtils;
import com.bingo.core.toolkit.StringUtils;
import com.netflix.hystrix.exception.HystrixRuntimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static MessageSource resources;

    public static void setResources(MessageSource resources) {
        GlobalExceptionHandler.resources = resources;
    }

    @ResponseBody
    @ExceptionHandler(value = {TokenException.class})
    public JsonResult tokenException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        logger.info("TokenException start.... {}", e);

        String enumStr = e.getMessage();
        logger.info("enumStr: {} ", enumStr);

        if(StringUtils.contains(enumStr, "|")){
            return JsonResult.build(CommonUtils.toInteger(StringUtils.substringBefore(enumStr, "|")), StringUtils.substringAfter(enumStr, "|"), "");
        }

        ResultStatusEnum resultStatusEnum = ResultStatusEnum.valueOf(enumStr);
        logger.info("resultStatusEnum: {} ", resultStatusEnum);

        String msg = resources.getMessage(enumStr, null, resultStatusEnum.getMessage(), Locale.CHINA);

        return JsonResult.build(resultStatusEnum.getCode(), msg, "");
    }


    @ResponseBody
    @ExceptionHandler(value = {DaoException.class, ServiceException.class})
    public JsonResult daoErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        logger.info("daoErrorHandler start....");

        String statusMsg = e.getMessage();

        return JsonResult.build(ResultStatusEnum.SERVER.getCode(), statusMsg, "");
    }

    @ResponseBody
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public JsonResult noHandlerFoundExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        logger.info("noHandlerFoundExceptionHandler start....");

        String statusMsg = e.getMessage();

        return JsonResult.build(ResultStatusEnum.NULL_PAGE.getCode(), statusMsg, "");
    }

    @ResponseBody
    @ExceptionHandler(value = {HystrixRuntimeException.class})
    public JsonResult hystrixRuntimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        logger.info("hystrixRuntimeExceptionHandler start....");

        String statusMsg = e.getMessage();
        logger.error("hystrixRuntimeExceptionHandler: {}", e.getMessage());

        return JsonResult.build(ResultStatusEnum.SERVER.getCode(), String.format("%s: %s", ResultStatusEnum.SERVER.getMessage(),e.getMessage()), "");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult exceptionErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        logger.info("exceptionErrorHandler start....");

        // TODO 未知的异常，应该格外注意，后续发送邮件通知等
        String statusMsg = e.getMessage();
        logger.error("exceptionErrorHandler error：{}", statusMsg);
        logger.error("exceptionErrorHandler ：{}", e);

        return JsonResult.build(ResultStatusEnum.EXCEPTION.getCode(), "服务器异常： " + statusMsg, "");
    }

}
