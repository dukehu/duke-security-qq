package com.duke.properties;

import com.duke.utils.SecurityConstants;

/**
 * Created duke on 2018/1/1
 */
public class BrowserProperties {

    private String loginPage = "/login.html";

    private SecurityConstants.LoginType loginType = SecurityConstants.LoginType.JSON;

    /**
     * 记住我，时间
     */
    private Integer rememberMeInSeconds = 3600;

    private String registPage = "/regist.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public SecurityConstants.LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(SecurityConstants.LoginType loginType) {
        this.loginType = loginType;
    }

    public Integer getRememberMeInSeconds() {
        return rememberMeInSeconds;
    }

    public void setRememberMeInSeconds(Integer rememberMeInSeconds) {
        this.rememberMeInSeconds = rememberMeInSeconds;
    }

    public String getRegistPage() {
        return registPage;
    }

    public void setRegistPage(String registPage) {
        this.registPage = registPage;
    }
}
