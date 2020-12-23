package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 滚动消息
 */
@Getter
@Setter
public class RollingMessage {
    /**
     * 是否存在
     */
    private String isexit = "0";
    /**
     * 消息描述
     */
    private String messagedesc = "";
    /**
     * 消息内容
     */
//    private String messagedtl = "";
    /**
     * 消息内容 新字段
     */
    private String messagedtlhtml = "";


}
