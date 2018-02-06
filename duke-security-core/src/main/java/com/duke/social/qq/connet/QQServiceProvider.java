package com.duke.social.qq.connet;

import com.duke.social.qq.api.QQ;
import com.duke.social.qq.api.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created pc on 2018/2/2
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    /**
     * 将用户导向认证服务器的地址
     */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 获取accessToken的地址
     */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     *
     * @param clientId     appId
     * @param clientSecret appSecret
     */
    public QQServiceProvider(String clientId, String clientSecret) {
        super(new QQOAuth2Template(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));

        this.appId = clientId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
