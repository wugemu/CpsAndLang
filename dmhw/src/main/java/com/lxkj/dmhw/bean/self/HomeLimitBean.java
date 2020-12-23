package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class HomeLimitBean implements Serializable {
    private List<LimitTimeBean> activityList;
    private ActivityGoods nowActivityGoods;//当前选中的场次的商品数据

    public List<LimitTimeBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<LimitTimeBean> activityList) {
        this.activityList = activityList;
    }

    public ActivityGoods getNowActivityGoods() {
        return nowActivityGoods;
    }

    public void setNowActivityGoods(ActivityGoods nowActivityGoods) {
        this.nowActivityGoods = nowActivityGoods;
    }
}
