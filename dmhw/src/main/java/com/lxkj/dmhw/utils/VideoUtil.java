package com.lxkj.dmhw.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.MMKVUtil;
import com.example.test.andlang.util.NetSpeedUtil;
import com.example.test.andlang.util.PicSelUtil;
import com.example.test.andlang.util.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class VideoUtil {

    public static void doAnim(View view, int width, int height) {
        ViewWrapper viewWrapper = new ViewWrapper(view);
        //沿x轴放大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofInt(viewWrapper, "width", width);
        //沿y轴放大
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofInt(viewWrapper, "height", height);
        AnimatorSet set = new AnimatorSet();
        //同时沿X,Y轴放大
        set.play(scaleXAnimator).with(scaleYAnimator);
        //都设置0.3s，也可以为每个单独设置
        set.setDuration(500);
        set.start();
    }

    //获取屏幕宽度
    public static int getDisplayWidth(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }
    public static int getDisplayHeight(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }



    public static boolean saveScreenshot(Context context, View view) {
        view.getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        if (view.getDrawingCache() == null) {
            return false;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        if (bitmap != null) {
            try {
               String path= saveImageToGallery(context,null, bitmap);
               File file=new File(path);
               if (file.exists()){
                   return true;
               }else {
                   return false;
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        saveImageToGallery(context, null, bmp);
    }

    public static String saveImageToGallery(Context context, String fileName, Bitmap bmp) {
        boolean needF;//需要刷新相册
        // 首先保存图片
//        File appDir = StorageUtils.getOwnCacheDirectory(context.getApplicationContext(), Const.qrImageDir);
        File appDir =new File(Environment.getExternalStorageDirectory(), "nn/qrImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        if (BaseLangUtil.isEmpty(fileName)) {
            needF = true;
            fileName = DateUtil.getNowDateminStr() + ".png";
        } else {
            needF = false;
            fileName += ".png";
        }

        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        if (needF) {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        }
        // 最后通知图库更新
        return file.getAbsolutePath();
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
        ClipData myClip;
        if ("com.nyso.sudian".equals(context.getPackageName())){
            myClip = ClipData.newPlainText("sd_copytext", content);//text是内容
        }else{
            myClip = ClipData.newPlainText("text", content);//text是内容
        }
        cmb.setPrimaryClip(myClip);
    }

    public static String checkTimeIsToday(long nowTime, long startTime) {
        if (startTime - nowTime >= 172800) {
            return DateUtil.getDateTime(new Date(startTime * 1000), "MM-dd");
        } else {
            String nowStr = DateUtil.getDateTime(new Date(nowTime * 1000), "yyyy-MM-dd");
            String targetStr = DateUtil.getDateTime(new Date(startTime * 1000), "yyyy-MM-dd");
            if (nowStr.equals(targetStr)) {
                return "今天";
            } else {
                return "明天";
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * view 为输入框
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFromWindow(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean ifBillVip(Context context) {
        int ifBillVip = BaseLangApplication.getInstance().getSpUtil().getInt(context, "ifBillVip");
        if (ifBillVip > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 去除小数点尾部0
     *
     * @return
     */
    public static String getDoubleFormat(Double price) {
        String s = price.toString();
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }

    /**
     * 数字万以上以x.x万格式输出
     *
     * @param count
     * @return
     */
    public static String getWanFormat(int count,String unit) {
        if (count >= 10000) {
            double result=(count - count % 1000) * 1.0 / 10000;
            return  getDoubleFormat(result)+ unit;
        } else {
            return count + "";
        }
    }

    public static boolean isLogin() {
        return MMKVUtil.getBoolean("isLogin", false);
    }


    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isMIUI()) {
            if (isFullScreen(context)) {
                vh = 0;
            }
        } else {
            if (!hasDeviceNavigationBar(context)) {
                vh = 0;
            }
        }
        return vh;
    }

    /**
     * 获取是否有虚拟按键
     * 通过判断是否有物理返回键反向判断是否有虚拟按键
     *
     * @param context
     * @return
     */
    public static boolean hasDeviceNavigationBar(Context context) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean result = realSize.y != size.y;
            return realSize.y != size.y;
        } else {

            boolean menu = ViewConfiguration.get(((Activity) context)).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }

    }
    public static boolean isFullScreen(Context context) {
        // true 是手势，默认是 false
        // https://www.v2ex.com/t/470543
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;

    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        // 这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public static boolean isWorked(Activity activity,String serviceName)
    {
        ActivityManager myManager=(ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for(int i = 0 ; i<runningService.size();i++)
        {
            if(runningService.get(i).service.getClassName().toString().equals(serviceName))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean neetShowWifiNoConnectTip(Context context){
        if (isAvailable(context)&&!isWifiConnected(context)){
            return true;
        }else {
            return false;
        }
    }

    //当前连接的网络是否是有效的wifi 网速过关
    public static boolean isAvailableWifi(Context context){
        if (isAvailable(context)&&isWifiConnected(context)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 当前网络质量
     * <p>
     * POOR: Bandwidth under 150 kbps.
     * MODERATE: Bandwidth between 150 and 550 kbps.
     * GOOD: Bandwidth between 550 and 2000 kbps.
     * EXCELLENT: Bandwidth over 2000 kbps.
     * UNKNOWN: Placeholder for unknown bandwidth. This is the initial value and will stay at this value if a bandwidth cannot be accurately found.
     */
    public static boolean isNetQuality(String otherQuality){
        boolean isGoodQuality=false;
        String qualityStr=NetSpeedUtil.getCurrentBandwidthQuality();
        if("EXCELLENT".equals(qualityStr)){
            isGoodQuality=true;
        }
        if(!BaseLangUtil.isEmpty(otherQuality)&&otherQuality.equals(qualityStr)){
            isGoodQuality=true;
        }
        return  isGoodQuality;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }


    /**
     * 获取活动网路信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
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

    public static String getVideoCachePath() {
        if (!PicSelUtil.getImgFileDir().exists()) {
            PicSelUtil.getImgFileDir().mkdir();
        }

        File cacheDir = new File(PicSelUtil.getImgFileDir(), "videocache2");
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }

        return cacheDir.getAbsolutePath();
    }


}
