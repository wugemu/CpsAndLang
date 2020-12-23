package com.lxkj.dmhw.bean.self;

//首页大活动配置图片和色值
public class HomeBigActBean {
    private String topImgStr;
    private String bigImgStr;
    private String bottomColorStr;
    private String fourSizeColor;

    public String getFourSizeColor() {
        return fourSizeColor;
    }

    public void setFourSizeColor(String fourSizeColor) {
        this.fourSizeColor = fourSizeColor;
    }

    public String getTopImgStr() {
        return topImgStr;
    }

    public void setTopImgStr(String topImgStr) {
        this.topImgStr = topImgStr;
    }

    public String getBigImgStr() {
        return bigImgStr;
    }

    public void setBigImgStr(String bigImgStr) {
        this.bigImgStr = bigImgStr;
    }

    public String getBottomColorStr() {
        return bottomColorStr;
    }

    public void setBottomColorStr(String bottomColorStr) {
        this.bottomColorStr = bottomColorStr;
    }
}
