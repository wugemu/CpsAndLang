package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.ProvinceBean;
import com.lxkj.dmhw.bean.self.UserAccount;

import java.util.List;

public class SettingModel extends BaseLangViewModel {
    private UserAccount userAccount;
    private List<ProvinceBean> provinceBeanList;
    private List<AddrBean> addrBeanList;


    public List<AddrBean> getAddrBeanList() {
        return addrBeanList;
    }

    public void setAddrBeanList(List<AddrBean> addrBeanList) {
        this.addrBeanList = addrBeanList;
    }

    public List<ProvinceBean> getProvinceBeanList() {
        return provinceBeanList;
    }

    public void setProvinceBeanList(List<ProvinceBean> provinceBeanList) {
        this.provinceBeanList = provinceBeanList;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
