package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CpsType implements Serializable {
    /**
     * 平台标志
     */
    private String code = "";

    /**
     * 平台名称
     */
    private String name = "";

    /**
     * 平台图标
     */
    private String logo = "";


}
