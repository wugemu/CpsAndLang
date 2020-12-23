package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class CardTypeBean implements Serializable {
    private String code;
    private String value;//1-身份证 2-护照 3-港澳通信证 4-港澳居民来往内地通信证 5-台湾居民来往内地通信证
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
