package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 运营推广
 */
@Getter
@Setter
public class NewsExamOther implements Serializable {
    /**
     * 头像
     */
    private String userpicurl = "";
    /**
     *日期
     */
    private String createtime = "";
    /**
     * 用户名
     */
    private String username = "";
    /**
     * 收益
     */
    private String mny = "";
    /**
     * 手机号
     */
    private String userphone = "";

}
