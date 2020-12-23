package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 新接口2.7.0 获取token
 */
@Getter
@Setter
public class LoginToken {
    /**
     * 小程序用户ID
     */
    private String microId = "";
    /**
     * 登录token
     */
    private String token = "";

    /**
     * 小程序原始ID
     */
    private String wechatMicroId = "";

}
