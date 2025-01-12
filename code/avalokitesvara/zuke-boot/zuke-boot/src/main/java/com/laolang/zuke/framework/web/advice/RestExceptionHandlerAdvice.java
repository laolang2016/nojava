package com.laolang.zuke.framework.web.advice;

import com.laolang.zuke.framework.common.domain.R;
import com.laolang.zuke.framework.common.exception.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class RestExceptionHandlerAdvice {

    /**
     * 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    private R<?> handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex) {
        R<?> r = R.notFound();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        log.info("请求地址不存在. url:{}", ex.getRequestURL());
        return r;
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    private R<?> handleBusinessException(HttpServletResponse response, BusinessException ex) {
        R<?> r = R.failed();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        r.setPropFromBusinessException(ex);
        log.error("code:{},msg:{}", ex.getCode(), ex.getMsg());
        return r;
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    private R<?> handleBusinessException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        R<?> r = R.error();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        log.error("服务异常. url:{}", request.getRequestURI());
        r.setMsg(ExceptionUtils.getMessage(ex));
        return r;
    }
}
