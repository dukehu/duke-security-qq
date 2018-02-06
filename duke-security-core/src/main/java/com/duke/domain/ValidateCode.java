package com.duke.domain;

import java.time.LocalDateTime;

/**
 * Created duke on 2018/1/7
 */
public class ValidateCode {

    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    ValidateCode() {
    }

    public ValidateCode(String code, Integer expireInSeconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInSeconds);
    }

    ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
