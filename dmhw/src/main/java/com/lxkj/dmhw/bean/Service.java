package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 客服
 */
@Getter
@Setter
public class Service {
    /**
     * 微信号
     */
    private String wxcode = "";
    /**
     * 二维码
     */
    private String wxqrcode = "";
}
