package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class CityBean implements Serializable{

    private String city;
    private String cityId;
    private List<AreaBean> sub;

    public List<AreaBean> getSub() {
        return sub;
    }

    public void setSub(List<AreaBean> sub) {
        this.sub = sub;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
