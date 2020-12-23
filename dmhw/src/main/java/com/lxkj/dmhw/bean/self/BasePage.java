package com.lxkj.dmhw.bean.self;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasePage<T> implements Serializable{
    private boolean hasNextPage;
    private ArrayList<T> list;
    private int total;
    private String logisticsMobile;
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLogisticsMobile() {
        return logisticsMobile;
    }

    public void setLogisticsMobile(String logisticsMobile) {
        this.logisticsMobile = logisticsMobile;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
