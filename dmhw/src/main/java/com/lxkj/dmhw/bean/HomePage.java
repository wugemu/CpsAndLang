package com.lxkj.dmhw.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lombok.Getter;
import lombok.Setter;

/**
 * 每日推荐
 */
@Getter
@Setter
public class HomePage {
    /**
     * 超级分类列表
     */
    private ArrayList<CategoryOne> CategoryOneList = new ArrayList<>();

    @Getter
    @Setter
    public static class CategoryOne {
        /**
         * 类目ID
         */
        private String shopclassone = "";
        /**
         * 类目名称
         */
        private String shopclassname = "";

        /**
         * 类目图片
         */
        private String url = "";
    }


    /**
     * 轮播图列表
     */
    private ArrayList<Banner> BannerList = new ArrayList<>();

    /**
     * 轮播图底部广告
     */
    private ArrayList<Banner> AdvList = new ArrayList<>();

    /**
     * 搜索背景
     */
    private ArrayList<Banner> searchBgList = new ArrayList<>();
    /**
     * 轮播广告背景
     */
    private ArrayList<Banner> BannerBgList = new ArrayList<>();
    /**
     * 金刚区背景
     */
    private ArrayList<Banner> JGQBgList = new ArrayList<>();

    /**
     * 零元购活动轮播
     */
    private ArrayList<Banner> MiddleBannerList = new ArrayList<>();

    /**
     * 热区活动获取背景图片
     */
    private ArrayList<Banner> HotBgList = new ArrayList<>();

    /**
     * 热区活动第一部分
     */
    private ArrayList<Banner> HotPartOneList = new ArrayList<>();

    /**
     * 热区活动第二部分
     */
    private ArrayList<Banner> HotPartTwoList = new ArrayList<>();

    /**
     * 热区活动第三部分
     */
    private ArrayList<Banner> HotPartTthreeList = new ArrayList<>();
    /**
     * 首页轮播图广告实体
     */
    @Getter
    @Setter
    public static class Banner {
        /**
         * 广告ID
         */
        private String advertisemenid = "";
        /**
         * 图片地址
         */
        private String advertisementpic = "";
        /**
         * 跳转类型
         */
        private String jumptype = "";
        /**
         * 链接地址
         */
        private String advertisementlink = "";


        /**
         * 是否需要登录
         */
        private String needlogin ="";
        /**
         * 多商品ID
         */
        private String topicid = "";

        /**
         * 轮播图背景颜色
         */
        private String bgcolor = "";


        public String getLBgClolor(){
            if (!bgcolor.equals("")){
                return bgcolor;
            }
            return "#ff0000";
        }

    }


    /**
     * 金刚区列表
     */
    private ArrayList<JGQAppIcon> JGQListOne = new ArrayList<>();
    private ArrayList<JGQAppIcon> JGQListTwo = new ArrayList<>();

    /**
     * 新金刚区头部分类
     */
    private ArrayList<JGQSort> JGQSortList = new ArrayList<>();

    /**
     * 新金刚区数据实体
     */
    @Getter
    @Setter
    public static class JGQSort {
        /**
         * 名称
         */
        private String name = "";
        /**
         * 分类
         */
        private String key = "";

        /**
         * 对应分类数据
         */
        private String keyjson = "";

    }
    /**
     * 活动精选列表
     */
    private ArrayList<JGQAppIcon> TopicListOne = new ArrayList<>();
    private ArrayList<JGQAppIcon> TopicListTwo = new ArrayList<>();
    private ArrayList<JGQAppIcon> TopicListThree = new ArrayList<>();
    private ArrayList<JGQAppIcon> TopicListFour = new ArrayList<>();

    /**
     * 金刚区数据实体
     */
    @Getter
    @Setter
    public static class JGQAppIcon {
        /**
         * 图标地址
         */
        private String imageurl = "";
        /**
         * 功能类型
         */
        private String labeltype = "";
        /**
         * 名称
         */
        private String name = "";
        /**
         * 链接地址
         */
        private String url = "";

        /**
         * 是否需要登录
         */
        private String needlogin = "";

    }


    //限时抢购数据
    private ArrayList<LimitTimeList> limitTimeParentList = new ArrayList<>();

    /**
     * 每日推荐列表
     */
    @Getter
    @Setter
    public static class LimitTimeList implements Serializable {
        /**
         * 抢购时间
         */
        private String time = "";
        /**
         *抢购时间标题
         */
        private String title = "";

        /**
         *抢购中
         */
        private String remark = "";

        private ArrayList<LimitTimeGoods> limitTimeChildList = new ArrayList<>();

        /**
         * 限时抢购商品数据实体
         */
        @Getter
        @Setter
        public static class LimitTimeGoods implements Serializable {

            /**
             * 商品ID
             */
            private String shopid = "";
            /**
             * 图标地址
             */
            private String shopmainpic = "";
            /**
             * 名称
             */
            private String shopname = "";
            /**
             * 券后价
             */
            private String disprice = "";

        }
    }



    /**
     * 首页底部导航栏
     */
    /**
     *列表
     */
    private ArrayList<BottomNavigation> NavList = new ArrayList<>();
    @Getter
    @Setter
    public static class BottomNavigation {
        /**
         * id
         */
        private String material = "";
        /**
         * 功能类型
         */
        private String type = "";
        /**
         * 名称
         */
        private String title = "";
        /**
         * 描述
         */
        private String desc = "";


    }
    /**
     * 热销榜单
     */
    /**
     *列表
     */
    private ArrayList<HotSell> hotSellList = new ArrayList<>();
    @Getter
    @Setter
    public static class HotSell {
        /**
         * id
         */
        private String id = "";
        /**
         * 图片
         */
        private String shopmainpic = "";
        /**
         * 价格
         */
        private String price = "";

        /**
         * 优惠券
         */
        private String coupondenomination = "";
    }

    /**
     * 验货直播
     */
    /**
     *列表
     */
    private ArrayList<RoomList> RoomList = new ArrayList<>();

    private String headUrl="";
    private String userLevel="";
}
