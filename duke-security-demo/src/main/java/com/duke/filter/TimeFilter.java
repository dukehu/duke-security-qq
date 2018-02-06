package com.duke.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by duke on 2017/12/25
 */
@Component
public class TimeFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // System.out.println("time filter init");
        LOGGER.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // System.out.println("time filter start");
        LOGGER.info("time filter start");
        long start = new Date().getTime();
        chain.doFilter(servletRequest, servletResponse);

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
        // System.out.println(method);
        StringBuffer contentPath = httpServletRequest.getRequestURL();
        // System.out.println(contentPath);
        LOGGER.info(contentPath.toString());

        // System.out.println("time filter：" + (new Date().getTime() - start));
        LOGGER.info("time filter：" + (new Date().getTime() - start));
        // System.out.println("time filter finish");
        LOGGER.info("time filter finish");
    }

    @Override
    public void destroy() {
        // System.out.println("time filter destroy");
        LOGGER.info("time filter destroy");
    }
}
