package com.duke.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * Created pc on 2018/2/2
 */
public class SpringSocialConfigurer extends org.springframework.social.security.SpringSocialConfigurer {

    private String filterProcessesUrl;

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) object;
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) socialAuthenticationFilter;
    }
}
