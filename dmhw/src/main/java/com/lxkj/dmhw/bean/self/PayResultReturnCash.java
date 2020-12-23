package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created By lhp
 * on 2020/6/10
 */
public class PayResultReturnCash implements Serializable {

    private PayResultReturnCashDetial tradeReturnCashDetail;//订单返现信息
    private ShareBean shareDetail;//分享详情
    private boolean ifTradeReturnCash;
    private String title;//顶部文案显示
    private int splitTradeCnt;//已拆多少单

    public PayResultReturnCashDetial getTradeReturnCashDetail() {
        return tradeReturnCashDetail;
    }

    public void setTradeReturnCashDetail(PayResultReturnCashDetial tradeReturnCashDetail) {
        this.tradeReturnCashDetail = tradeReturnCashDetail;
    }

    public ShareBean getShareDetail() {
        return shareDetail;
    }

    public void setShareDetail(ShareBean shareDetail) {
        this.shareDetail = shareDetail;
    }

    public boolean isIfTradeReturnCash() {
        return ifTradeReturnCash;
    }

    public void setIfTradeReturnCash(boolean ifTradeReturnCash) {
        this.ifTradeReturnCash = ifTradeReturnCash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSplitTradeCnt() {
        return splitTradeCnt;
    }

    public void setSplitTradeCnt(int splitTradeCnt) {
        this.splitTradeCnt = splitTradeCnt;
    }
}
