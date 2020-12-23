package com.example.test.andlang.util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.test.andlang.andlangutil.BaseLangApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by root on 18-3-14.
 */

public class BaseLangUtil {

    private static StringBuilder sb = new StringBuilder();

    public static boolean isEmpty(String str){
        if(str==null||str.length()==0){
            return true;
        }
        return false;
    }
    //为某个域名 设置cookie
    public static void syncCookie(Context context,String url,String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
    }
    //Screen Width
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    //检验手机号码是否有效
    public static boolean isMobile(String str){
        if (str == null || "".equals(str)) {
            return false;
        }
        if (str.length() != 11) {
            return false;
        }
        if (!str.startsWith("1")) {
            return false;
        }
        if(!isNumericStr(str)){
            return false;
        }
        return true;
    }

    public static final String getLenthTxt(String str,int lenth){
        sb.delete(0,sb.length());
        if(isEmpty(str)){
            return "";
        }
        if(str.length()>lenth){
            sb.append(str.substring(0,lenth));
            sb.append("...");
            return sb.toString();
        }
        return str;
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    public static boolean isNotificationEnabled(Context context) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }

    /**
     * dp转换成px
     *
     * @param context Context
     * @param dp      dp
     * @return px值
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //判断是否是纯数字字符串
    public static boolean isNumericStr(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String getUrlParma(String url, String key) {

        if (url != null && key != null && url.contains(key)) {
            String paramUrl=url.substring(url.indexOf("?")+1);
            if (paramUrl != null && paramUrl.indexOf("=") > -1) {
                String[] arrTemp = paramUrl.split("&");
                if(arrTemp==null){
                    return "";
                }
                for (String str : arrTemp) {
                    String[] qs = str.split("=");
                    if(qs!=null&&qs.length>=2&&key.equals(qs[0])){
                        return qs[1];
                    }
                }
            }
        }
        return "";
    }

    //触发修改icon
    public static void changeLauncher(Activity context) {
//        String nowName=BaseLangApplication.getInstance().getSpUtil().getString(context,Constants.NOW_CLASS);
        String nowName=MMKVUtil.getString(Constants.NOW_CLASS);
//        if (BaseLangUtil.isEmpty(nowName)){
//            nowName=MMKVUtil.getString(Constants.NOW_CLASS);
//        }
//        String changeName= BaseLangApplication.getInstance().getSpUtil().getString(context,Constants.LOCAL_CLASS);
        String changeName= MMKVUtil.getString(Constants.LOCAL_CLASS);
//        if (BaseLangUtil.isEmpty(changeName)){
//            changeName=MMKVUtil.getString(Constants.LOCAL_CLASS);
//        }
        if(isEmpty(changeName)){
            return;
        }
        if(!changeName.equals(nowName)) {
            PackageManager pm = context.getPackageManager();
            //隐藏之前显示的桌面组件
            pm.setComponentEnabledSetting(new ComponentName(context, nowName),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            //显示新的桌面组件
            pm.setComponentEnabledSetting(new ComponentName(context, changeName),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//            BaseLangApplication.getInstance().getSpUtil().putString(context,Constants.LOCAL_CLASS,changeName);
            MMKVUtil.putString(Constants.LOCAL_CLASS,changeName);
        }
    }

    //设置icon修改标示
    //测试 修改桌面icon 活动icon
    //BaseLangUtil.setChangeLauncherName(HomeActivity.this, com.example.test.andlang.util.Constants.APP_ICON_ACTIVITY);
    //测试 修改桌面icon 恢复icon
    //BaseLangUtil.setChangeLauncherName(HomeActivity.this, com.example.test.andlang.util.Constants.APP_ICON_NORMAL);
    public static void setChangeLauncherName(Activity context,String changeName){
        if(!isEmpty(changeName)){
//            BaseLangApplication.getInstance().getSpUtil().putString(context,Constants.LOCAL_CLASS,changeName);
            MMKVUtil.putString(Constants.LOCAL_CLASS,changeName);
        }
    }
    public static void setNowLauncherName(Activity context,String nowName){
        if(!isEmpty(nowName)){
//            BaseLangApplication.getInstance().getSpUtil().putString(context,Constants.NOW_CLASS,nowName);
            MMKVUtil.putString(Constants.NOW_CLASS,nowName);
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = BaseLangApplication.getInstance().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", content);//text是内容
        cmb.setPrimaryClip(myClip);
    }

    public static void installApk(Activity activity,File apkFile,InstallApkListener listener) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(FileUtil.file2Uri(activity, apkFile), "application/vnd.android.package-archive");
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(FileUtil.file2Uri(activity, apkFile), "application/vnd.android.package-archive");
            }
        }catch (Exception e){
            e.printStackTrace();
            listener.fail();
        }
        activity.startActivity(intent);
    }

    //检测是否由本地文件读写权限
    public static boolean isHaveSDPer(){
        if(BaseLangApplication.getInstance()!=null) {
            PackageManager pkgManager = BaseLangApplication.getInstance().getPackageManager();
            boolean sdCardWritePermission =
                    pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, BaseLangApplication.getInstance().getPackageName()) == PackageManager.PERMISSION_GRANTED;
            return sdCardWritePermission;
        }else {
            return false;
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    //获取Manifest渠道名称
    public static String readMetaInfo(Context context,String metakey) {
        String metaStr = "";
        try {
            ApplicationInfo applicationInfo =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                metaStr = (String) applicationInfo.metaData.get(metakey);// 这里为对应meta-data的name
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaStr;
    }

    /**
     * 获取版本姓名
     *
     * @param context
     * @return
     */
    public static Integer getVersionCode(Context context) {
        PackageInfo packInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }

    /**
     * 判断颜色是深色还是浅色
     * @param color true=light false=dark
     * @return
     */
    public static boolean isLightColor(int color) {
        color=Math.abs(color);
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return true; // It's a light color
        } else {
            return false; // It's a dark color
        }
    }

    public static int getViewBackground(View view){
        if (view.getBackground() instanceof ColorDrawable){
            return  ((ColorDrawable)view.getBackground()).getColor();
        }else{
            return -1;//代表白色
        }
    }

    public static int getColorFromAttr(Context context,int attrName,int attrValue){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrName, typedValue, true);
        int[] attribute = new int[] { attrValue };
        TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
        int value = array.getColor(0 /* index */, -1 /* default size */);
        array.recycle();
        return value;
    }

    public static int getResFromAttr(Activity activity, int attrValue) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attrValue, typedValue, true);
        return typedValue.resourceId;
    }

    public static Context getContext() {
        Context activity = ActivityUtil.getInstance().getLastActivity();
        if (activity == null) {
            activity = BaseLangApplication.getInstance();
        }
        if (activity instanceof Activity) {
            if (((Activity) activity).isFinishing()) {
                activity=BaseLangApplication.getInstance();
            }
        }
        return activity;
    }

    public static boolean isHttpUrl(String url){
        if(BaseLangUtil.isEmpty(url)){
            return false;
        }
        if(url.startsWith("http")
                || url.startsWith("https")){
            return true;
        }
        return false;
    }
}
