package com.duke.properties;

/**
 * Created duke on 2018/1/7
 */
public class SmsCodeProperties {

    private Integer length = 6;

    private Integer expireInSeconds = 60;

    /**
     * 需要使用图形验证码验证的url
     */
    private String url;

    SmsCodeProperties() {
    }

    public SmsCodeProperties(Integer length, Integer expireInSeconds, String url) {
        this.length = length;
        this.expireInSeconds = expireInSeconds;
        this.url = url;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(Integer expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
