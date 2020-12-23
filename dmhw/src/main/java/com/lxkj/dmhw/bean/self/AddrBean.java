package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class AddrBean implements Serializable {
    private String address;
    private String area;
    private String city;
    private int flag;//是否默认 0不默认 1 默认
    private String id;
    private String personName;
    private String province;
    private String remark;
    private String servNum;
    private String updateAdrStr;//地址列表更新订单地址文案

    public String getUpdateAdrStr() {
        return updateAdrStr;
    }

    public void setUpdateAdrStr(String updateAdrStr) {
        this.updateAdrStr = updateAdrStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServNum() {
        return servNum;
    }

    public void setServNum(String servNum) {
        this.servNum = servNum;
    }
}
