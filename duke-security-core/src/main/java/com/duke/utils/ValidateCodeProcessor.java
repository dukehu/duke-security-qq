package com.duke.utils;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验处理器，封装不同校验码的处理逻辑
 * 使用模板方法设计模式
 * <p>
 * Created duke on 2018/1/7
 */
public interface ValidateCodeProcessor {

    /**
     * 校验码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验器
     *
     * @param request 封装请求和响应
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param request 封装请求和响应
     */
    void validate(ServletWebRequest request);
}
