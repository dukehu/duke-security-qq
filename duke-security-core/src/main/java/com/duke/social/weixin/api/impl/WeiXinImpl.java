package com.duke.social.weixin.api.impl;

import com.duke.domain.WeiXinUserInfo;
import com.duke.social.weixin.api.WeiXin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created pc on 2018/2/2
 */
public class WeiXinImpl extends AbstractOAuth2ApiBinding implements WeiXin {

    private static final Logger log = LoggerFactory.getLogger(WeiXinImpl.class);
    /**
     * 微信获取用户信息的url
     */
    private static final String WEINXIN_URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";
    private ObjectMapper objectMapper = new ObjectMapper();

    public WeiXinImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WeiXinUserInfo getUserInfo(String openId) {
        // 获取用户信息的url
        String url = String.format(WEINXIN_URL_GET_USER_INFO, openId);

        String result = getRestTemplate().getForObject(url, String.class);

        log.info("【WeiXinImpl】获取用户信息的url：{}", url);
        log.info("【WeiXinImpl】获取用户信息的响应：{}", result);

        try {
            return objectMapper.readValue(result, WeiXinUserInfo.class);
        } catch (IOException e) {
            throw new RuntimeException("获取微信用户信息异常！");
        }
    }

    /**
     * 使用utf-8 替换默认的ISO-8859-1编码
     *
     * @return messageConverters
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();

        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return messageConverters;
    }
}
