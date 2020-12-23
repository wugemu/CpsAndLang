package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ProvinceBean implements Serializable {
    private String province;
    private String provinceId;
    private List<CityBean> sub;

    public List<CityBean> getSub() {
        return sub;
    }

    public void setSub(List<CityBean> sub) {
        this.sub = sub;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
