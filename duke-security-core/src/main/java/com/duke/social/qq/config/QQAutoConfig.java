package com.duke.social.qq.config;

import com.duke.properties.QQProperties;
import com.duke.properties.SecurityProperties;
import com.duke.social.qq.connet.QQConnectionFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

/**
 * Created pc on 2018/2/2
 * <p>
 * 配置qq的链接工厂
 */
@Configuration
@ConditionalOnProperty(prefix = "com.duke.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }

//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
//                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        jdbcUsersConnectionRepository.setTablePrefix("security_social_");
//        if (connectionSignUp != null) {
//            // 用户想偷偷注册一个
//            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
//        }
//        return jdbcUsersConnectionRepository;
//    }
}
