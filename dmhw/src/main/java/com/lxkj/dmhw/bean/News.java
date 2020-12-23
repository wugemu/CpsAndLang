package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 最新消息
 */
@Getter
@Setter
public class News {
    /**
     * 消息类型
     */
    private String type = "";
    /**
     * 消息内容
     */
    private String content = "";
    /**
     * 消息时间
     */
    private String time = "";
}
