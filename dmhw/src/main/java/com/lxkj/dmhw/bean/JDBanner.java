package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 *京东广告
 *  "clickType": "string",
 *   "clickVaule": "string",
 *   "cpsType": "string",
 *   "createTime": "2019-09-28T06:29:03.594Z",
 *   "enabled": true,
 *   "endTime": "2019-09-28T06:29:03.594Z",
 *   "id": 0,
 *   "imageUrl": "string",
 *   "name": "string",
 *   "needLogin": true,
 *   "site": "string",
 *   "sortOrder": 0,
 *   "startTime": "2019-09-28T06:29:03.594Z",
 *   "sysParam": "string",
 *   "updateTime": "2019-09-28T06:29:03.594Z"
 */
@Getter
@Setter
public class JDBanner {
    /**
     * 广告ID
     */
    private String id = "";
    /**
     * 图片地址
     */
    private String imageUrl = "";
    /**
     * 名称
     */
    private String name = "";
    /**
     * 副标题
     */
    private String subName = "";
    /**
     * 跳转类型
     */
    private  String clickType = "";
    /**
     * 链接地址
     */
    private String clickVaule = "";

    /**
     * 订单来源
     */
    private String cpsType = "";

    /**
     * 订单来源
     */
    private String sysParam = "";

    /**
     * 是否需要登录
     */
    private Boolean needLogin =false;

    /**
     * 颜色渐变
     */
    private String color ="";

}
