package com.duke.service.security;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created pc on 2018/2/2
 */
// @Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /**
     * 不跳转注册页（/signUp）
     *
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认穿件用户，并返回用户唯一标识
        String providerId = connection.getKey().getProviderId();
        return UUID.randomUUID().toString();
    }
}
