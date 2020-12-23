package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

public class ActivityGoods implements Serializable {
    private List<GoodBean> bannerGoods;//banner图下商品的信息
    private List<GoodBean> notBannerGoods;//非banner图下的商品信息

    public List<GoodBean> getBannerGoods() {
        return bannerGoods;
    }

    public void setBannerGoods(List<GoodBean> bannerGoods) {
        this.bannerGoods = bannerGoods;
    }

    public List<GoodBean> getNotBannerGoods() {
        return notBannerGoods;
    }

    public void setNotBannerGoods(List<GoodBean> notBannerGoods) {
        this.notBannerGoods = notBannerGoods;
    }
}
