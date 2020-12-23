package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.ZhongCao;
import com.lxkj.dmhw.bean.self.ActivityBean;
import com.lxkj.dmhw.bean.self.HomeZcBean;

import java.util.List;

public class ZhongCaoModel extends BaseLangViewModel {

    private ZhongCao zhongCao;
    private List<HomeZcBean> zcList;

    public List<HomeZcBean> getZcList() {
        return zcList;
    }

    public void setZcList(List<HomeZcBean> zcList) {
        this.zcList = zcList;
    }

    public ZhongCao getZhongCao() {
        return zhongCao;
    }

    public void setZhongCao(ZhongCao zhongCao) {
        this.zhongCao = zhongCao;
    }
}
