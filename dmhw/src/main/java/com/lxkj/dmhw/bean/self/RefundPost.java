package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created By lhp
 * on 2019/9/19
 */
public class RefundPost implements Serializable {

    /**
     * code : ane66
     * firstLetter : A
     * ifAppShow : true
     * ifShow : true
     * name : 安能快递
     * seqNum : 0
     */

    private String code;
    private String firstLetter;
    private boolean ifAppShow;
    private boolean ifShow;
    private String name;
    private int seqNum;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public boolean isIfAppShow() {
        return ifAppShow;
    }

    public void setIfAppShow(boolean ifAppShow) {
        this.ifAppShow = ifAppShow;
    }

    public boolean isIfShow() {
        return ifShow;
    }

    public void setIfShow(boolean ifShow) {
        this.ifShow = ifShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }
}
