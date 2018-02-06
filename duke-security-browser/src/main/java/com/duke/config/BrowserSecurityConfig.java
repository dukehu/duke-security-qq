package com.duke.config;

import com.duke.properties.SecurityProperties;
import com.duke.security.BrowserAuthenticationFailureHandler;
import com.duke.security.BrowserAuthenticationSuccessHandler;
import com.duke.security.LogoutSuccessHandler;
import com.duke.utils.SecurityConstants;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created duke on 2017/12/28
 *
 * @author duke
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入自定义的登陆成功处理器
     */
    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;

    /**
     * 注入自定义的登陆失败处理器
     */
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 配置加密方式
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 记住我
     *
     * @return PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 图片验证码校验过滤器
         */
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();

        /**
         * 短信验证码校验过滤器
         */
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        smsCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
//        smsCodeFilter.setSecurityProperties(securityProperties);
//        smsCodeFilter.afterPropertiesSet();


        http
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)  // 认证跳转地址
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)  // 处理的表单登陆的地址
                .successHandler(browserAuthenticationSuccessHandler)  // 设置成功处理器
                .failureHandler(browserAuthenticationFailureHandler)  // 设置失败处理器
                .and()
                .apply(validateCodeSecurityConfig)  // 加入校验的配置（图形校验、短信校验）
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)  // 短信登陆的配置
                .and()
                .apply(springSocialConfigurer) // 社交登陆
                .and()
                .rememberMe()  // 记住我
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeInSeconds())  // token有效的时间
                .userDetailsService(userDetailsService)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        "/code/*",
                        securityProperties.getBrowser().getRegistPage(),
                        "/user/regist").permitAll()  // 不需要身份验证的配置
                .anyRequest()  // 任何请求
                .authenticated() // 都需要授权
                .and()
                .csrf().disable();


       /* // 所有的http请求都需要使用表单登陆
        http
                .addFilterBefore(smsCodeFilter,
                        UsernamePasswordAuthenticationFilter.class) // 在UsernamePasswordAuthenticationFilter之前加入自定义的短信验证码校验
                .addFilterBefore(validateCodeFilter,
                        UsernamePasswordAuthenticationFilter.class) // 在UsernamePasswordAuthenticationFilter之前加入
                .formLogin() // 表单登陆的认证
                .loginPage("/mapper/require")   // 自定义登陆页面
                .loginProcessingUrl("/login")
                .successHandler(browserAuthenticationSuccessHandler) // 覆盖spring security 默认的成功处理器
                .failureHandler(browserAuthenticationFailureHandler) // 覆盖spring security 默认的失败处理器
                .and()
                .rememberMe()  // 记住我
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeInSeconds())  // token有效的时间
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/mapper/require",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*").permitAll()  // 不需要身份验证的配置
                .anyRequest()  // 任何请求
                .authenticated() // 都需要授权
                .and()
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);*/
    }
}
