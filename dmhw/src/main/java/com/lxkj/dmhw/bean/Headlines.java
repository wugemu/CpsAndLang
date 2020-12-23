package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 呆萌头条
 */
@Getter
@Setter
public class Headlines {
    /**
     * 图标
     */
    private String imgpic = "";
    /**
     * 标题
     */
    private String title = "";
    /**
     * 标签
     */
    private String topic = "";
    /**
     * 类型
     */
    private String type = "";
    /**
     * 商品ID
     */
    private String shopinfoid = "";
    /**
     * 主题ID
     */
    private String topicid = "";
    /**
     * 推荐理由
     */
    private ArrayList<String> reasonsList = new ArrayList<>();
}
