package com.lxkj.dmhw.bean.self;

import java.io.Serializable;

/**
 * Created by 1 on 2016/11/4.
 */
public class PayOrder implements Serializable {

//    private String return_code;
//    private String return_msg;
    private String appid;
//    private String mch_id;
//    private String device_info;
    private String nonce_str;
    private String sign;
//    private String result_code;
//    private String err_code;
//    private String err_code_des;
//    private String openid;
//    private String is_subscribe;
//    private String trade_type;
//    private String bank_type;
//    private String total_fee;
//    private String fee_type;
//    private String cash_fee;
//    private String cash_fee_type;
//    private String transaction_id;
//    private String out_trade_no;
//    private String attach;
//    private String time_end;
    private String prepay_id;
    private String packageDto;
    private String timestamp;
    private String partnerid;

    public PayOrder() {
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPackageDto() {
        return packageDto;
    }

    public void setPackageDto(String packageDto) {
        this.packageDto = packageDto;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
