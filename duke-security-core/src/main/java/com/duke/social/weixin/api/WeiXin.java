package com.duke.social.weixin.api;

import com.duke.domain.WeiXinUserInfo;

/**
 * Created pc on 2018/2/2
 */
public interface WeiXin {

    /**
     * 这个方法相对于qq的getUserInfo,微信多了一个参数openId，这是因为微信的openId是跟accessToken一起返回的
     * 然而，Spring Social获取access_token的类AccessGrant.java中没有openId。因此就需要扩展Spring Social
     * 提供的令牌类（AccessGrant.java）
     *
     * @param openId 用户唯一标识
     * @return WeiXinUserInfo
     */
    WeiXinUserInfo getUserInfo(String openId);

}
