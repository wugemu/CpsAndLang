package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 粉丝成员
 */
@Getter
@Setter
public class MyTeamTotalInfo {

        /**
         * 文案名称
         */
        private String title = "";
        /**
         * 团队人数
         */
        private String team= "";
        /**
         * 分润文案
         */
        private String subtitle= "";
        /**
         * 比例
         */
        private String comm= "";
        /**
         * 本月贡献
         */
        private String contribution= "";
        /**
         * 本月贡献(文案)
         */
        private String contributiontitle= "";


}
