package com.lxkj.dmhw.widget.zg;

import java.util.Map;

/**
 * Created By lhp
 * on 2020/6/3
 */
public interface ZgCallback {
    /**
     * 请求专柜购物车
     */
    void reqCarList();

    /**
     * 清空专柜购物车
     * true=清空失效，false清空所有
     */
    void clearCarList(boolean invalid);

    /**
     * 购物车商品数量变更
     * num=1增加 =-1减少
     */

    void changeCarCount(String skuId, String goodsId, int num);

    /**
     *专柜购物车结算
     * @param params
     */
    void jiesuan(Map<String, Object> params);
}
