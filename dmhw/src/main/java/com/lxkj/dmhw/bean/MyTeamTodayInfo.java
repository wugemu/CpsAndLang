package com.lxkj.dmhw.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 粉丝成员
 */
@Getter
@Setter
public class MyTeamTodayInfo {

        /**
         * 文案名称
         */
        private String title = "";
        /**
         * 人数
         */
        private String cnt= "";


        /**
         * 跳轉下一頁查看明細的傳值
         */
        private String userids= "";

}
