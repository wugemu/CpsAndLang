package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class PostTipBean implements Serializable {
    private String postMessage;//无返回或返回为空时整行不显示 礼包详情同此字段 added by kjl 20190425 ------version109
    private String postMessageDetail;//运费弹窗说明------version109

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public String getPostMessageDetail() {
        return postMessageDetail;
    }

    public void setPostMessageDetail(String postMessageDetail) {
        this.postMessageDetail = postMessageDetail;
    }
}
