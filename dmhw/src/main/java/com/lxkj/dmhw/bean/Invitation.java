package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 邀请码信息
 */
@Getter
@Setter
public class Invitation {
    /**
     * 邀请码是否存在
     */
    private String isexist = "";
    /**
     * 用户头像
     */
    private String userpicurl = "";
    /**
     * 用户昵称
     */
    private String username = "";

    /**
     * 提示信息
     */
    private String msgstr = "";
}
