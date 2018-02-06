package com.duke.social.qq.connet;

import com.duke.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * Created pc on 2018/2/2
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId   the provider id e.g. "facebook"
     * @param clientId     appId
     * @param clientSecret appSecret
     */
    public QQConnectionFactory(String providerId, String clientId, String clientSecret) {
        super(providerId, new QQServiceProvider(clientId, clientSecret), new QQAdapter());
    }
}
