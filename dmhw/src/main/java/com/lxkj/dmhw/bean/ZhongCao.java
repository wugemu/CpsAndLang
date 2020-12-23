package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.bean.self.ActivityBean;

import java.io.Serializable;
import java.util.List;

public class ZhongCao implements Serializable {

    private List<ActivityBean> banner;

    private List<HaoWuClass> coreMaterialList;

    public List<ActivityBean> getBanner() {
        return banner;
    }

    public void setBanner(List<ActivityBean> banner) {
        this.banner = banner;
    }

    public List<HaoWuClass> getCoreMaterialList() {
        return coreMaterialList;
    }

    public void setCoreMaterialList(List<HaoWuClass> coreMaterialList) {
        this.coreMaterialList = coreMaterialList;
    }
}
