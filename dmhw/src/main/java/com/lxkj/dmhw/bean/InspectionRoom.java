package com.lxkj.dmhw.bean;

import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 验货直播详情
 */
@Getter
@Setter
public class InspectionRoom {

        /**
         * 直播ID
         */
        private String id = "";
        /**
         * 淘宝商品ID
         */
        private String shopid = "";

        /**
         * 我们后台产品库商品ID
         */
        private String shopinfoid = "";

        /**
         * 优惠券id
         */
        private String couponid = "";

        /**
         * 观看人数
         */
        private String num = "";

        /**
         * 商品名称
         */
        private String shopname = "";
        /**
         * 商品短标题
         */
        private String shortname = "";
        /**
         * 商品主图
         */
        private String shopmainpic = "";
        /**
         * 商品价格
         */
        private String shopprice = "";
        /**
         * 预估佣金
         */
        private String precommission = "";
        /**
         * 销量
         */
        private String shopmonthlysales = "";
        /**
         * 平台类型:天猫(true)淘宝(false)
         */
        private String platformtype = "";
        /**
         * 商品视频地址
         */
        private String videolink = "";
        /**
         * 店铺名称
         */
        private String sellername = "";
        /**
         * 优惠券
         */
        private String coupondenomination ="0.0";

        /**
         * 公告
         */
        private String notice ="";

        /**
         * 是否喜欢
         */
        private String islike ="";


        /**
         * 推荐理由
         */
        private String recommended ="";

        /**
         * @return 券后价
         */
        public String getMoney() {
                return Utils.getFloat(Float.parseFloat(shopprice) - Float.parseFloat(coupondenomination)) + "";
        }

        /**
         * @return 商品名称
         */
        public String getTitle() {
                return shortname.equals("") ? shopname : shortname;
        }

        /**
         * @return 优惠券
         */
        public String getDiscount() {
                return coupondenomination;
        }

        /**
         * 平台类型
         * @return 天猫(true)淘宝(false)
         */
        public boolean isCheck() {
                return platformtype.equals("天猫");
        }



        /**
         * 免单用户列表
         */
        public ArrayList<Free> freeList = new ArrayList<>();

        /**
         * 图片列表
         */
        @Getter
        @Setter
        public static class Free {
            /**
             * 手机号码
             */
            private String userphone = "";

                /**
                 * 头像地址
                 */
                private String userpicurl = "";

                /**
                 * 用户昵称
                 */
                private String username = "";

        }


        /**
         * 聊天内容详情
         */
        public ArrayList<Detail> detailList = new ArrayList<>();

        @Getter
        @Setter
        public static class Detail {

                /**
                 * 聊天类型 0-文本 1-图片 2-视频
                 */
                private String type = "";
                /**
                 * 图片地址
                 */
                private String imgurl = "";
                /**
                 * 视频地址
                 */
                private String videourl = "";
                /**
                 * 文本内容
                 */
                private String content = "";
                /**
                 * 图片高度
                 */
                private String imgheight = "";
                /**
                 * 图片宽度
                 */
                private String imgwidth = "";
        }

        /**
         * 底部动画用户数组 XXX正在去买，已购买
         */
        public ArrayList<userInfo> userList = new ArrayList<>();


        @Getter
        @Setter
        public static class userInfo {

                private String name = "";
        }


        /**
         * 商品详情轮播图
         */
        public ArrayList<imageUrl> shoppiclist = new ArrayList<>();

        /**
         * 商品详情轮播图
         */
        @Getter
        @Setter
        public static class imageUrl {
                /**
                 * 商品图片
                 */
                private String shoppicurl = "";
        }



}
