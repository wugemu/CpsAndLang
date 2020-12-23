package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信token
 */
@Getter
@Setter
public class AccessToken {
    /**
     * 接口调用凭证
     */
    private String access_token = "";
    /**
     * 用户刷新access_token
     */
    private String refresh_token = "";
    /**
     * 授权用户唯一标识
     */
    private String openid = "";
}
