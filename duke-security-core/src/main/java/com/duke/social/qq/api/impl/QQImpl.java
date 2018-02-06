package com.duke.social.qq.api.impl;

import com.duke.domain.QQUserInfo;
import com.duke.social.qq.api.QQ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * Created pc on 2018/2/2
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final Logger log = LoggerFactory.getLogger(QQImpl.class);
    /**
     * 获取openid的url
     */
    private static final String QQ_URL_GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户信息的url
     */
    private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 申请qq登陆成功之后，分配给应用的appId
     */
    private String appId;

    /**
     * 服务提供商的唯一标识，与qq号一一对应
     * <p>
     * 通过访问https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN获得
     */
    private String openId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(QQ_URL_GET_OPEN_ID, accessToken);

        log.info("【QQImpl】获取openid的url：{}", url);

        String result = getRestTemplate().getForObject(url, String.class);

        log.info("【QQImpl】获取openid的响应：{}", result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * 从服务提供商（qq）获取用户信息
     *
     * @return QQUserInfo
     */
    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);

        log.info("【QQImpl】获取用户信息的url：{}", url);

        String result = getRestTemplate().getForObject(url, String.class);

        log.info("【QQImpl】获取用户信息的响应：{}", result);

        try {
            QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息异常！");
        }
    }
}
