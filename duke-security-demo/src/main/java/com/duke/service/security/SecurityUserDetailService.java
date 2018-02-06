package com.duke.service.security;

import com.duke.domain.UserDetails;
import com.duke.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created duke on 2017/12/28
 *
 * @author duke
 */
@Component
public class SecurityUserDetailService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(SecurityUserDetailService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        // TODO 改成jdbc
        logger.info("表单登陆登陆用户名：{}", username);
        logger.info("加密之后的密码：{}", password);
        return new UserDetails(username, password, "123465789", new ArrayList<>());
    }

    /**
     * @param userId the user ID used to lookup the user details
     * @return the SocialUserDetails requested
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登陆登陆用户名：" + userId);
        return new UserDetails("13246", userId, new ArrayList<>());
    }
}
