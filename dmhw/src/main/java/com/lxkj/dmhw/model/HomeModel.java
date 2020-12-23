package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.ActivityGoods;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.HomeBean;
import com.lxkj.dmhw.bean.self.HomeLimitBean;
import com.lxkj.dmhw.bean.self.HomeZcBean;

import java.util.List;

public class HomeModel extends BaseLangViewModel {
    private HomeBean homeBean;
    private List<HomeZcBean> homeZcBeanList;
    private HomeLimitBean homeLimitBean;
    private List<GoodBean> goodBeanList;//全部的热门推荐商品
    private List<GoodBean> addHotGoodList;//添加的热门商品
    private ActivityGoods nowActivityGoods;//当前选中的场次的商品数据

    public ActivityGoods getNowActivityGoods() {
        return nowActivityGoods;
    }

    public void setNowActivityGoods(ActivityGoods nowActivityGoods) {
        this.nowActivityGoods = nowActivityGoods;
    }

    public List<GoodBean> getGoodBeanList() {
        return goodBeanList;
    }

    public void setGoodBeanList(List<GoodBean> goodBeanList) {
        this.goodBeanList = goodBeanList;
    }

    public List<GoodBean> getAddHotGoodList() {
        return addHotGoodList;
    }

    public void setAddHotGoodList(List<GoodBean> addHotGoodList) {
        this.addHotGoodList = addHotGoodList;
    }

    public HomeLimitBean getHomeLimitBean() {
        return homeLimitBean;
    }

    public void setHomeLimitBean(HomeLimitBean homeLimitBean) {
        this.homeLimitBean = homeLimitBean;
    }

    public List<HomeZcBean> getHomeZcBeanList() {
        return homeZcBeanList;
    }

    public void setHomeZcBeanList(List<HomeZcBean> homeZcBeanList) {
        this.homeZcBeanList = homeZcBeanList;
    }

    public HomeBean getHomeBean() {
        return homeBean;
    }

    public void setHomeBean(HomeBean homeBean) {
        this.homeBean = homeBean;
    }
}
