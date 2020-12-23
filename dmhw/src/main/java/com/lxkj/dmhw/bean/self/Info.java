package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/10/19.
 */

public class Info implements Serializable {
    private String title;
    private String des;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
