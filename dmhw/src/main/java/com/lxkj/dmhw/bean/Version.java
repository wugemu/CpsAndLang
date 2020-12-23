package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 版本查询
 */
@Getter
@Setter
public class Version {
    /**
     * 版本号
     */
    private String devversion = "";
    /**
     * 版本描述
     */
    private String versiondesc = "";
    /**
     * 更新状态
     */
    private String updateflag = "";
    /**
     * 下载地址
     */
    private String downloadurl = "";
}
