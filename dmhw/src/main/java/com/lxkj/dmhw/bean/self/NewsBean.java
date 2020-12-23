package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

public class NewsBean implements Serializable {
    private String createTimeTemp;
    private String id;
    private String imgUrl;
    private boolean readStatus;
    private String title;
    private String titleTemp;
    private int type;
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCreateTimeTemp() {
        return createTimeTemp;
    }

    public void setCreateTimeTemp(String createTimeTemp) {
        this.createTimeTemp = createTimeTemp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleTemp() {
        return titleTemp;
    }

    public void setTitleTemp(String titleTemp) {
        this.titleTemp = titleTemp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
