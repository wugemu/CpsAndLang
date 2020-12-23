package com.lxkj.dmhw.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Coupon implements Serializable {
    /**
     * 优惠券id
     */
    private String id = "";

    /**
     * 优惠券起始时间
     */
    private String startTime = "";

    /**
     * 优惠券截止时间
     */
    private String endTime = "";

    /**
     * 金额
     */
    private String price = "";

    /**
     * 领券链接
     */
    private String link = "";

}
