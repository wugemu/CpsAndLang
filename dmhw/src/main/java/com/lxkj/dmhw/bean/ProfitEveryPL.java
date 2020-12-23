package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 收益具体内容
 */
@Getter
@Setter
public class ProfitEveryPL {

        private Object settled;

        /**
         * 自己的推广数据
         */
        private Object self;
        /**
         * 团队推广数据
         */
        private Object team ;

        /**
         * 时间名称
         */
        private String title = "";

        /**
         * 选中状态
         */
        private boolean isCheck=false;
}
