package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享模板
 */
@Getter
@Setter
public class Template {
    /**
     * 文案分享模板
     */
    private String sharetextnew = "";
    /**
     * 淘口令分享模板
     */
    private String sharetpwdnew = "";
    /**
     * 短链接分享模板
     */
    private String sharelinknew = "";
    /**
     * 超级搜索跳转开关(0:关闭;1:开启)
     */
    private int specifiedsearch = 0;
    /**
     * 弹窗跳转方式(0:全网;1:app)
     */
    private int searchjump = 0;

    /**
     * 分享编辑框的推荐理由 替换内容
     */
    private String emptyrecommend = "";


    /**
     * 超级搜索跳转开关(false:关闭;true:开启)
     */
    public boolean SearchSwitch() {
        return specifiedsearch != 0;
    }

    /**
     * 弹窗跳转方式(false:全网;true:app)
     */
    public boolean MonitorSwitch() {
        return searchjump != 0;
    }
}
