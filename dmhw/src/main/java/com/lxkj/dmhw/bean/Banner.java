package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告
 */
@Getter
@Setter
public class Banner {
    /**
     * 广告ID
     */
    private String advertisemenid = "";
    /**
     * 图片地址
     */
    private String advertisementpic = "";
    /**
     * 跳转类型
     */
    private String jumptype = "";
    /**
     * 链接地址
     */
    private String advertisementlink = "";

    /**
     * 广告名称
     */
    private String title = "";

    /**
     * 是否需要登录
     */
    private String needlogin ="";
    /**
     * 多商品ID
     */
    private String topicid = "";

    /**
     * 轮播图背景颜色
     */
    private String bgcolor = "";


    public String getLBgClolor(){
      if (!bgcolor.equals("")){
      return bgcolor;
      }
     return "#ff0000";
    }

}
