package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 公众号信息
 */
@Getter
@Setter
public class WeChatAbout {
    /**
     * 二维码
     */
    private String img = "";
    /**
     * 微信公众号
     */
    private String wechatId = "";
}
