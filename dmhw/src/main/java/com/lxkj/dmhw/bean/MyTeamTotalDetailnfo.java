package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 粉丝成员
 */
@Getter
@Setter
public class MyTeamTotalDetailnfo {

        /**
         * 会员昵称
         */
        private String username = "";
        /**
         * 会员类型
         */
        private String usertype= "";
        /**
         * 手机号
         */
        private String userphone= "";
        /**
         * 创建时间
         */
        private String createtime= "";
        /**
         *会员人数
         */
        private String acnt= "";
        /**
         *会员头像
         */
        private String userpicurl= "";
        /**
         *本月预估
         */
        private String preamount= "";

        /**
         *邀请码
         */
        private String extensionid= "";
        /**
         *微信号
         */
        private String wxcode= "";

        /**
         *上月预估
         */
        private String monthamount= "";
        /**
         *累计收益
         */
        private String profit= "";



}
