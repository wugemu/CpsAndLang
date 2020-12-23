package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.GoodBean;

import java.util.List;

public class LimitSaleModel extends BaseLangViewModel {
    public static final int TYPE_BANNERSALE=1;//banner+goods
    public static final int TYPE_GOOD=2;//good
    public static final int BOTTOM=3;//底线
    public static final int TYPE_TITLE=4;//标题
    public static final int TYPE_RECYCLE=5;//热门推荐
    private int type;//商品展示类型
    private GoodBean good;
    private List<GoodBean> recycleGoods;
    private int bootomState;//0继续上拉 1松手刷新 2没数据了
    private int lastOffset;//滚动最终的位置
    private int lastPosition;

    public List<GoodBean> getRecycleGoods() {
        return recycleGoods;
    }

    public void setRecycleGoods(List<GoodBean> recycleGoods) {
        this.recycleGoods = recycleGoods;
    }

    public int getBootomState() {
        return bootomState;
    }

    public void setBootomState(int bootomState) {
        this.bootomState = bootomState;
    }

    public int getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(int lastOffset) {
        this.lastOffset = lastOffset;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public static int getTypeBannersale() {
        return TYPE_BANNERSALE;
    }

    public static int getTypeGood() {
        return TYPE_GOOD;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }
}
