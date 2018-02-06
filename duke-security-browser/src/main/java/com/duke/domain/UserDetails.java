package com.duke.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * Created duke on 2017/12/28
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails, SocialUserDetails {

    // 登陆名，也就是spring security UserDetails中的userName
    private String loginName;

    // 登陆密码
    private String loginPassword;

    // 用户id
    private String userId;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetails() {
    }

    public UserDetails(String loginName, String userId, Collection<? extends GrantedAuthority> authorities) {
        this.loginName = loginName;
        this.userId = userId;
        this.authorities = authorities;
    }

    public UserDetails(String loginName, String loginPassword, String userId, Collection<? extends GrantedAuthority> authorities) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.userId = userId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.loginPassword;
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    /**
     * 账号是否过期
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否被锁定
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credentials 证书的意思
     * Expired 过期的意思
     *
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
