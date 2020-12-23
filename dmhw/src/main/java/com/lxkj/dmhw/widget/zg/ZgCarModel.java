package com.lxkj.dmhw.widget.zg;


import com.lxkj.dmhw.bean.self.TradeGoodsCar;

/**
 * Created By lhp
 * on 2020/6/3
 */
public class ZgCarModel {
    public static final int GOOD_TYPE=0;//商品
    public static final int LINE_TYPE=1;//商品分割线
    public static final int INVLID_TOP_TYPE=2;//无效顶部
    private int type;
    private TradeGoodsCar car;

    public TradeGoodsCar getCar() {
        return car;
    }

    public void setCar(TradeGoodsCar car) {
        this.car = car;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
