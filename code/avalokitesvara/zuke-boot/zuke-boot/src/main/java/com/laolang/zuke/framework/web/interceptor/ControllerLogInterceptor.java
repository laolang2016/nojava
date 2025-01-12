package com.laolang.zuke.framework.web.interceptor;

import com.laolang.zuke.framework.web.annotaion.ControllerLog;
import java.lang.reflect.Method;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ControllerLogInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ControllerLog controllerLog = method.getAnnotation(ControllerLog.class);
        if (Objects.nonNull(controllerLog)) {
            startTimeThreadLocal.set(System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ControllerLog controllerLog = method.getAnnotation(ControllerLog.class);
        if (Objects.nonNull(controllerLog)) {
            long costTime = System.currentTimeMillis() - startTimeThreadLocal.get();
            log.info("url:{} {} ms", request.getRequestURL(), costTime);
        }
    }
}
