package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.List;

/**
 * Created By lhp
 * on 2019/10/12
 */
public class RefundLogistics implements Serializable {
    private String logisticsName;
    private String postId;
    private String logisticsMobile;
    private List<LogisticsBean> list;

    public String getLogisticsMobile() {
        return logisticsMobile;
    }

    public void setLogisticsMobile(String logisticsMobile) {
        this.logisticsMobile = logisticsMobile;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<LogisticsBean> getList() {
        return list;
    }

    public void setList(List<LogisticsBean> list) {
        this.list = list;
    }
}
