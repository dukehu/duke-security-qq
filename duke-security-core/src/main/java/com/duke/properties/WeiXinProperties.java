package com.duke.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * Created pc on 2018/2/1
 */
public class WeiXinProperties extends SocialProperties {

    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
