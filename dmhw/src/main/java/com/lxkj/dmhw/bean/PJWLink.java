package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PJWLink implements Serializable {
    /**
     * 分享短连接
     */
    private String shortUrl = "";


    /**
     * 唤醒京东
     */
    private String url = "";

    /**
     * 唤醒唯品会
     */
    private String mobileUrl = "";

    /**
     * 唤醒拼多多
     */
    private String schemaUrl = "";


}
