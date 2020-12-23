package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ActivityNewBean implements Serializable {
    private String bgColor;
    private List<ActivityitemitemBean> detailList;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<ActivityitemitemBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ActivityitemitemBean> detailList) {
        this.detailList = detailList;
    }
}
