package com.duke.config;

import com.duke.exception.ValidateCodeException;
import com.duke.properties.SecurityProperties;
import com.duke.utils.SecurityConstants;
import com.duke.utils.ValidateCodeProcessorHolder;
import com.duke.utils.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created duke on 2018/1/3
 * <p>
 * 自定义的spring security过滤器，在UsernamePasswordAuthenticationFilter之前执行
 */
@Component(value = "validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeFilter.class);

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 存放所有需要校验验证码的url，包括图形验证码和短信验证码
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 系统中校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 该方法在初始化bean的时候执行
     * 此处用来收集需要校验验证码的url，放到map中，key是url、value是当前要校验的类型
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
//        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImageCode().getUrl(), ",");
//
//        urls.addAll(Arrays.asList(configUrls));
//        urls.add("/login");
        // 用户名密码登陆url，默认添加
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);

        // 短信登陆url，默认添加
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
    }

    /**
     * 将需要处理的url加入到map中
     *
     * @param urls 需要处理的url
     * @param type 需要处理的类型
     */
    private void addUrlToMap(String urls, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urls)) {
            String[] urlStrs = StringUtils.splitByWholeSeparatorPreserveAllTokens(urls, ",");
            for (String url : urlStrs) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        Boolean action = false;
//
//        for (String url : urls) {
//            if (antPathMatcher.match(url, request.getRequestURI())) {
//                action = true;
//            }
//        }
//
//        // 是登陆请求
//        if (action) {
//            try {
//                validateCodeProcessor.validate(new ServletWebRequest(request));
//            } catch (ValidateCodeException e) {
//                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
//                return;
//            }
//        }

        ValidateCodeType type = getValidateCodeType(request);

        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("校验通过");
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request 请求
     * @return 校验码类型
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
