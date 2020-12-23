package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class AreaBean implements Serializable {
    private String area;
    private String areaId;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
