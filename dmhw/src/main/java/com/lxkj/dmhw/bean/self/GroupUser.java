package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created By lhp
 * on 2019/12/11
 */
public class GroupUser implements Serializable {


    /**
     * heardUrl : https://image.sudian178.com/sd/heard/20181025123901279314.jpg
     * id : 262487890000367
     * isFirstGroup : true
     * nickName : 你是
     * payTimeStr : 2019-12-02 14:22:51
     */

    private String heardUrl;
    private String id;
    private boolean isFirstGroup;
    private String nickName;
    private String payTimeStr;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeardUrl() {
        return heardUrl;
    }

    public void setHeardUrl(String heardUrl) {
        this.heardUrl = heardUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFirstGroup() {
        return isFirstGroup;
    }

    public void setFirstGroup(boolean firstGroup) {
        isFirstGroup = firstGroup;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPayTimeStr() {
        return payTimeStr;
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }
}
