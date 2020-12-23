package com.lxkj.dmhw;

import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.tencent.mmkv.MMKV;

/**
 * 数据迁移
 */
public class DataMigration {

    public static void Migration() {
        // 迁移数据位置
        MMKV mmkv = MMKV.defaultMMKV();
        if (!mmkv.decodeString("userId", "").equals("")) {
            UserInfo login = new UserInfo();
            login.setUserid(mmkv.decodeString("userId", ""));// 用户ID
            login.setUsername(mmkv.decodeString("userName", ""));// 用户昵称
            login.setUsertype(mmkv.decodeString("userType", ""));// 用户标识
            login.setUserphone(mmkv.decodeString("phone", ""));// 手机号
            login.setUserpicurl(mmkv.decodeString("avatar", ""));// 头像
            login.setUserscore(mmkv.decodeString("score", ""));// 积分
            login.setUsergold(mmkv.decodeString("gold", ""));// 金币
            login.setUsercommission(mmkv.decodeString("commission", ""));// 佣金
            login.setExtensionid(mmkv.decodeString("extension", ""));// 推广码
            login.setMerchantid(mmkv.decodeString("merchant", ""));// 商户号
            DateStorage.setInformation(login);
        }
        if (mmkv.decodeBool("loginStatus" )) {
            DateStorage.setLoginStatus(true);
        }
        if (mmkv.decodeBool("franchiserStatus")) {
            DateStorage.setFranchiserStatus(true);
        }
    }
}
