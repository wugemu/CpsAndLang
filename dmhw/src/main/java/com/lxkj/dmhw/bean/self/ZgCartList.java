package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created By lhp
 * on 2020/6/8
 */
public class ZgCartList implements Serializable {

    private List<TradeGoodsCar> available;//有效商品
    private List<TradeGoodsCar> invalid;//无效商品

    public List<TradeGoodsCar> getAvailable() {
        return available;
    }

    public void setAvailable(List<TradeGoodsCar> available) {
        this.available = available;
    }

    public List<TradeGoodsCar> getInvalid() {
        return invalid;
    }

    public void setInvalid(List<TradeGoodsCar> invalid) {
        this.invalid = invalid;
    }
}
