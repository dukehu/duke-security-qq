package com.duke.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义拦截器
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        // 方法调用之前调用
        LOGGER.info("preHandle start");

        HandlerMethod handlerMethod = (HandlerMethod) handle;

        // 当前handle类型
        // System.out.println(handlerMethod.getBean().getClass().getName());
        // System.out.println(handlerMethod.getMethod().getName());
        request.setAttribute("startTime", new Date().getTime());

        // 返回true继续执行，false不执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception {
        // 方法调用之后调用，方法出现异常，此方法不会被调用
        LOGGER.info("postHandle start");

        long start = (long) request.getAttribute("startTime");
        // System.out.println("time interceptor 耗时：" + (new Date().getTime() - start));
        LOGGER.info("time interceptor 耗时：" + (new Date().getTime() - start));
    }

    /**
     * @param ex 当方法正常执行时，ex是没有值的，当方法出现异常时ex就是异常信息对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) throws Exception {
        // 此方法始终会被调用
        // System.out.println("afterCompletion");
        LOGGER.info("afterCompletion start");

        long start = (long) request.getAttribute("startTime");
        // System.out.println("time interceptor 耗时：" + (new Date().getTime() - start));
        LOGGER.info("time interceptor 耗时：" + (new Date().getTime() - start));
        LOGGER.info("afterCompletion throw exception：" + ex);
        // System.out.println(ex);
    }
}
