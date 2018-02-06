package com.duke.domain;

import org.springframework.social.oauth2.AccessGrant;

/**
 * Created pc on 2018/2/5
 * <p>
 * 处理微信返回的access_token，微信在返回access_token的时候，将openId也返回了，所以这块要加一个openId来接收
 */
public class WeiXinAccessGrant extends AccessGrant {

    /**
     * 用户在服务提供商的唯一标识
     */
    private String openId;

    public WeiXinAccessGrant(String accessToken) {
        super("");
    }

    public WeiXinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
