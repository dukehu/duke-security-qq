package com.duke.social.weixin.connect;

import com.duke.domain.WeiXinAccessGrant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created pc on 2018/2/5
 * <p>
 * 处理微信返回的access_token类
 */
public class WeiXinOAuth2Template extends OAuth2Template {

    private static final Logger log = LoggerFactory.getLogger(WeiXinOAuth2Template.class);
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private ObjectMapper objectMapper = new ObjectMapper();
    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;

    public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 这个必须设置为true，不然的话在请求accessToken的参数中会缺少appId,appSecret
        setUseParametersForClientAuthentication(true);

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 获取access_token
     *
     * @param authorizationCode    授权码（授权码模式）
     * @param redirectUri          重定向的地址
     * @param additionalParameters
     * @return AccessGrant
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid=").append(clientId);
        accessTokenRequestUrl.append("&secret=").append(clientSecret);
        accessTokenRequestUrl.append("&code=").append(authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri=").append(redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

    /**
     * 获取access_token
     *
     * @param accessTokenRequestUrl 获取地址
     * @return AccessGrant
     */
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

        log.info("【WeiXinOAuth2Template】获取access_token的url：{}", accessTokenRequestUrl);

        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);

        log.info("【WeiXinOAuth2Template】获取access_token的响应：{}", response);

        Map result = null;

        try {
            result = objectMapper.readValue(response, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回错误是直接抛错
        if (StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))) {
            String errcode = MapUtils.getString(result, "errcode");
            String errmsg = MapUtils.getString(result, "errmsg");
            throw new RuntimeException("【WeiXinOAuth2Template】获取access token失败, errcode:" + errcode + ", errmsg:" + errmsg);
        }

        WeiXinAccessGrant accessGrant = new WeiXinAccessGrant(MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result, "refresh_token"),
                MapUtils.getLong(result, "expires_in"));
        accessGrant.setOpenId(MapUtils.getString(result, "openid"));

        return accessGrant;
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid=" + clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token=" + refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_login";
        return url;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 微信返回的contentType是text/html，添加响应的HttpMessageConvert处理
     *
     * @return RestTemplate
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
