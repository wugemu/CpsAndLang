package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取帮助列表
 */
@Getter
@Setter
public class ClassifyHelp {
    /**
     * 分类
     */
    private String problemtype = "";
    /**
     * 问题列表
     */
    private ArrayList<Help> problemdata = new ArrayList<>();
}
