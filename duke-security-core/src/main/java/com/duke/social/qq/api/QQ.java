package com.duke.social.qq.api;

import com.duke.domain.QQUserInfo;

/**
 * Created pc on 2018/2/2
 */
public interface QQ {

    /**
     * 从服务提供商（qq）获取用户信息
     *
     * @return QQUserInfo
     */
    QQUserInfo getUserInfo();

}
