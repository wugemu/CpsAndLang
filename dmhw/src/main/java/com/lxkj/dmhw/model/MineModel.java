package com.lxkj.dmhw.model;

import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.lxkj.dmhw.bean.self.BreakUpBean;
import com.lxkj.dmhw.bean.self.CardTypeBean;
import com.lxkj.dmhw.bean.self.Info;
import com.lxkj.dmhw.bean.self.PayResultReturnCash;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.TradeCountBean;
import com.lxkj.dmhw.bean.self.UserAccount;

import java.util.List;

public class MineModel extends BaseLangViewModel {
    private UserAccount userAccount;
    private List<UserAccount> authList;
    private TradeCountBean tradeCountBean;
    private String upImgUrl;
    private String authId;
    private List<CardTypeBean> cardTypeBeanList;
    private String otherServiceId;
    private ShareBean shareBean;
    private BreakUpBean breakUpBean;
    private Info Info;
    private PayResultReturnCash payResultReturnCash;

    public PayResultReturnCash getPayResultReturnCash() {
        return payResultReturnCash;
    }

    public void setPayResultReturnCash(PayResultReturnCash payResultReturnCash) {
        this.payResultReturnCash = payResultReturnCash;
    }

    public Info getInfo() {
        return Info;
    }

    public void setInfo(Info info) {
        Info = info;
    }

    public BreakUpBean getBreakUpBean() {
        return breakUpBean;
    }

    public void setBreakUpBean(BreakUpBean breakUpBean) {
        this.breakUpBean = breakUpBean;
    }

    public ShareBean getShareBean() {
        return shareBean;
    }

    public void setShareBean(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    public String getOtherServiceId() {
        return otherServiceId;
    }

    public void setOtherServiceId(String otherServiceId) {
        this.otherServiceId = otherServiceId;
    }

    public List<CardTypeBean> getCardTypeBeanList() {
        return cardTypeBeanList;
    }

    public void setCardTypeBeanList(List<CardTypeBean> cardTypeBeanList) {
        this.cardTypeBeanList = cardTypeBeanList;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public List<UserAccount> getAuthList() {
        return authList;
    }

    public void setAuthList(List<UserAccount> authList) {
        this.authList = authList;
    }

    public String getUpImgUrl() {
        return upImgUrl;
    }

    public void setUpImgUrl(String upImgUrl) {
        this.upImgUrl = upImgUrl;
    }

    public TradeCountBean getTradeCountBean() {
        return tradeCountBean;
    }

    public void setTradeCountBean(TradeCountBean tradeCountBean) {
        this.tradeCountBean = tradeCountBean;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
