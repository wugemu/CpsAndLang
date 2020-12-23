package com.lxkj.dmhw.utils;

import android.app.Activity;

import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;

public class SDJumpUtil {

    public static boolean goWhere(Activity activity, String adrUrl) {
        return false;
    }
    //首页跳转 -1=不做处理 0=首页  2=玩主玩客主页
    public static void goHomeActivity(Activity activity, int goWhere) {
        ActivityUtil.getInstance().finishAllActivity();
        ActivityManagerDefine.getInstance().popOtherActivity(MainActivity.class);
    }

}
