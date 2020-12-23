package com.example.test.andlang.cockroach;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.MMKVUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class CrashLog {
    public static final String TAG = "CrashLog";

    public static void saveCrashLog(Context context, Throwable throwable) {
        Map<String, String> map = collectDeviceInfo(context,false);
        saveCrashInfo2File(context, throwable, map);
    }

    public static String getCrashLog(Context context, Boolean isBlack,Throwable throwable) {
        Map<String, String> map = collectDeviceInfo(context,isBlack);
        return getCrashInfo(context, throwable, map);
    }


    private static Map<String, String> collectDeviceInfo(Context ctx,Boolean isBlack) {
        Map<String, String> infos = new TreeMap<>();
        try {
            String inviteCode= MMKVUtil.getString("inviteCode");
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            infos.put("logTime",time);
            if(!BaseLangUtil.isEmpty(inviteCode)){
                infos.put("inviteCode", inviteCode);//用户邀请码
            }
            if(isBlack){
                infos.put("isCrash", "true");//app crash日志
            }else {
                infos.put("isCrash", "false");//app crash日志
            }
            infos.put("systemVersion", Build.VERSION.RELEASE);
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
        return infos;
    }

    private static String getCrashInfo(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();

        if(ex!=null) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result).append("\n");
        }

        if(infos!=null) {
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key).append("=").append(value).append("\n");
            }
        }

        try {
            return sb.toString();
        } catch (Exception e) {

        }
        return "";
    }

    private static void saveCrashInfo2File(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();
        if(ex!=null) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result).append("\n");
        }
        if(infos!=null) {
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key).append("=").append(value).append("\n");
            }
        }
        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            String cachePath = crashLogDir(context);
            LogUtil.d("0.0","crash日志目录："+cachePath);
            File dir = new File(cachePath);
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(cachePath + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    public static String crashLogDir(Context context) {
        return context.getCacheDir().getPath() + File.separator + "crash" + File
                .separator;
    }

    //自定义上报bugly错误 putUserData key个数不能超过9个
    public static void postBuglyException(boolean isBlack,Throwable throwable,String extraMsg){
        postCommonBuglyAndNet(isBlack,throwable,extraMsg,"app","");
    }

    //自定义上报bugly错误 putUserData key个数不能超过9个
    public static void postExcForOther(Throwable throwable,String extraMsg,String from){
        postCommonBuglyAndNet(false,throwable,extraMsg,from,"");
    }

    //网络请求超时或域名解析报错的错误上报
    public static void postExcForNet(final boolean isBlack,final Throwable throwable,final String extraMsg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process p = runtime.exec("ping -c 3 www.baidu.com");
                    int ret = p.waitFor();
                    LogUtil.e("0.0", "网络Ping命令结果值（0-则网络真正可用）:"+ret);
                    postCommonBuglyAndNet(isBlack,throwable,extraMsg,"app","网络Ping命令结果值（0-则网络真正可用）:"+ret);
                } catch (Exception e) {
                    postCommonBuglyAndNet(isBlack,throwable,extraMsg,"app","");
                }
            }
        }).start();
    }

    public static void postCommonBuglyAndNet(boolean isBlack,Throwable throwable,String extraMsg,String from,String pingMsg){
        Context context=BaseLangApplication.getInstance();
        CrashReport.putUserData(context, "ping", pingMsg);//附加信息
        postCommonBugly(isBlack,throwable,extraMsg,from);
    }


    public static void postCommonBugly(boolean isBlack,Throwable throwable,String extraMsg,String from){
        if(BaseLangUtil.isEmpty(extraMsg)){
            extraMsg="";
        }
        Context context=BaseLangApplication.getInstance();
        String inviteCode=MMKVUtil.getString("inviteCode");
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        CrashReport.putUserData(context, "logTime", time);//发生时间
        if (!BaseLangUtil.isEmpty(inviteCode)) {
            CrashReport.setUserId(inviteCode);
        } else {
            CrashReport.setUserId("");
        }
        CrashReport.putUserData(context, "isCrash", String.valueOf(isBlack));//是否Crash

        CrashReport.putUserData(context, "isFrom", from);//异常日志来源app 或 weex

        CrashReport.putUserData(context, "extraMsg", extraMsg);//附加信息

        CrashReport.postCatchedException(throwable);
    }
}
