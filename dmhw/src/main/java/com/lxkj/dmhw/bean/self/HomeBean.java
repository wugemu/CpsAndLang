package com.lxkj.dmhw.bean.self;

import java.util.List;

public class HomeBean {
    private String hotSearch;
    private String headUrl;
    private String userLevel;
    private List<ActivityBean> bannerTheme;
    private List<ActivityBean> navigation;
    private List<ActivityNewBean> navigationAfterTmeme;
    private List<ActivityGoodBean> navigationAfterTwoThemeList;

    public List<ActivityGoodBean> getNavigationAfterTwoThemeList() {
        return navigationAfterTwoThemeList;
    }

    public void setNavigationAfterTwoThemeList(List<ActivityGoodBean> navigationAfterTwoThemeList) {
        this.navigationAfterTwoThemeList = navigationAfterTwoThemeList;
    }

    public String getHotSearch() {
        return hotSearch;
    }

    public void setHotSearch(String hotSearch) {
        this.hotSearch = hotSearch;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public List<ActivityNewBean> getNavigationAfterTmeme() {
        return navigationAfterTmeme;
    }

    public void setNavigationAfterTmeme(List<ActivityNewBean> navigationAfterTmeme) {
        this.navigationAfterTmeme = navigationAfterTmeme;
    }

    public List<ActivityBean> getBannerTheme() {
        return bannerTheme;
    }

    public void setBannerTheme(List<ActivityBean> bannerTheme) {
        this.bannerTheme = bannerTheme;
    }

    public List<ActivityBean> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<ActivityBean> navigation) {
        this.navigation = navigation;
    }
}
