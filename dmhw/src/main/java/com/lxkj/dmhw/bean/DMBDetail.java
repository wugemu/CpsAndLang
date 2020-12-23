package com.lxkj.dmhw.bean;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分总览
 */
@Getter
@Setter
public class DMBDetail {

        /**
         * 兑换时间
         */
        private String createtime = "";
        /**
         * 名称
         */
        private String name = "";
        /**
         * 获取的个数
         */
        private String score = "";
        /**
         * 获取资金
         */
        private String mny = "";
}
