package com.duke.social.weixin.connect;

import com.duke.social.weixin.api.WeiXin;
import com.duke.social.weixin.api.impl.WeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * Created pc on 2018/2/5
 * <p>
 * 微信的oauth2流程处理器的提供器，共供spring social的connect体系调用
 */
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {

    /**
     * 将用户导向认证服务器的地址，获取授权码
     */
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 获取accessToken的地址
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     */
    public WeiXinServiceProvider(String clientId, String clientSecret) {
        super(new WeiXinOAuth2Template(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
    }

    @Override
    public WeiXin getApi(String accessToken) {
        return new WeiXinImpl(accessToken);
    }
}
