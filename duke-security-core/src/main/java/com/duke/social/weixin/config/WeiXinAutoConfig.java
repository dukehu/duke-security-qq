package com.duke.social.weixin.config;

import com.duke.properties.SecurityProperties;
import com.duke.properties.WeiXinProperties;
import com.duke.social.weixin.connect.WeiXinConnectionFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

/**
 * Created pc on 2018/2/5
 * <p>
 * 配置微信的链接工厂
 */
@Configuration
@ConditionalOnProperty(prefix = "com.duke.security.social.weixin", name = "app-id")
public class WeiXinAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weixin = securityProperties.getSocial().getWeixin();
        return new WeiXinConnectionFactory(weixin.getProviderId(), weixin.getAppId(), weixin.getAppSecret());
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
