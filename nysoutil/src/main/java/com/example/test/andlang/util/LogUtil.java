package com.example.test.andlang.util;

import android.util.Log;

import com.orhanobut.logger.Logger;


/**
 * Created by Bill56 on 2017/9/5.
 */

public class LogUtil {

    public static final String TAG = "0.0";  // 默认的tag

    private static final int level = 0;     // 当前的输出级别，只要大于该级别才输出,1-v,2-d,3-i,4-w,5-e，所以0是输出所有

    public static final boolean isLog = BaseLangUtil.isApkInDebug();  // 是否输出日志
    public static final boolean isCash = false; //是否内存检测应用安装

    public static void v(String tag, String msg) {
        if (isLog && 1 > level) {
            Log.v(tag, printJson(tag, msg));
        }
    }

    private static String printJson(String tag, String msg) {
        String json = "";
        String label = msg;
        if (hasJson(msg) == 1) {
            label = msg.substring(0, msg.indexOf("{"));
            json = msg.substring(msg.indexOf("{"));
        } else if (hasJson(msg) == 2) {
            label = msg.substring(0, msg.indexOf("["));
            json = msg.substring(msg.indexOf("["));
        }
        if (!BaseLangUtil.isEmpty(json)) {
            println(tag,msg);
            try {
                Logger.t(tag).json(json);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(tag,"无效的json格式===="+json);
            }
        }
        return label;
    }

    private static int hasJson(String msg) {
        if (msg.contains("{") && msg.contains("}") && msg.indexOf("{") < msg.lastIndexOf("}")) {
            return 1;
        }
        if (msg.contains("[") && msg.contains("]") && msg.indexOf("[") < msg.lastIndexOf("]")) {
            return 2;
        }
        return 0;
    }


    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isLog && 2 > level) {
            Log.d(tag, printJson(tag, msg));
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isLog && 3 > level) {
            Log.i(tag, printJson(tag, msg));
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isLog && 4 > level) {
            Log.w(tag, printJson(tag, msg));
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isLog && 5 > level) {
            Log.e(tag, printJson(tag, msg));
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void println(String tag,String msg){
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.d(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.d(tag, logContent);
            }
            Log.d(tag, msg);// 打印剩余日志
        }
    }

}
