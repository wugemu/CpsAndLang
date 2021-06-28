package com.example.test.andlang.andlangutil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.test.andlang.cockroach.CrashLog;
import com.example.test.andlang.cockroach.DebugSafeModeTipActivity;
import com.example.test.andlang.log.AppCrashHandler;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.MMKVUtil;
import com.example.test.andlang.util.PreferencesUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.VersionUtil;
import com.example.test.andlang.util.imageload.BitmapFillet;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.IInnerImageSetter;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.wanjian.cockroach.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-3-27.
 */

public class BaseLangApplication extends Application {
    public static String TAG = "andlang";
    public static long NOW_TIME; // 当前服务器时间
    public static String logDir;
    public static String tmpCacheName;//图片临时文件夹
    private static Application mApp;
    private PreferencesUtil mSpUtil;
    public static String imageDefUrl;//带背景的默认图url
    public static boolean ifOpenShake;//是否启动摇一摇功能
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        ForegroundCallbacks.init(this);
    }

    private void initData() {
        if (BaseLangUtil.isEmpty(imageDefUrl)){
            imageDefUrl="https://image.sudian178.com/sd/static/img/jinli20191223.jpg";
        }
        MMKV.initialize(this);
        mApp = this;
        LogUtil.e("0.0", "版本号：V" + VersionUtil.getVersionName(this));
    }

    //数据临时文件设置
    public void initCacheName(String tmpName) {
        tmpCacheName = tmpName;
    }

    //崩溃日志保存至本地
    public void initCrashLog(String dirPath) {
        //程序错误日志信息收集
        logDir = Environment.getExternalStorageDirectory().getPath() + dirPath;
        AppCrashHandler crashHandler = AppCrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    //Bugly日志收集 https://bugly.qq.com/v2/
    public void initBugly(String appId, String userId, String channelKeyName) {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = BaseLangUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel(BaseLangUtil.readMetaInfo(context, channelKeyName));  //设置渠道
        // 初始化Bugly
        CrashReport.initCrashReport(context, appId, BaseLangUtil.isApkInDebug(), strategy);

        if (!BaseLangUtil.isEmpty(userId)) {
            CrashReport.setUserId(userId);//Bugly 日志用户id设置
        } else {
            CrashReport.setUserId("");
        }
    }

    //安装防止崩溃库
    public void installCockroach() {
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        Cockroach.install(this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---");
                if (BaseLangUtil.isApkInDebug()) {
                    //crash日志记录
                    CrashLog.saveCrashLog(getApplicationContext(), throwable);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(BaseLangApplication.this, "捕获到导致崩溃的异常");
                        }
                    });
                } else {
                    //上传友盟日志
                    if (throwable != null) {
                        String errorInfo = CrashLog.getCrashLog(getApplicationContext(), false, throwable);
                        if (!BaseLangUtil.isEmpty(errorInfo)) {
                            MobclickAgent.reportError(BaseLangApplication.this, errorInfo);
                        }
                    }
                }
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
                LogUtil.e("Cockroach Worked");
            }

            @Override
            protected void onEnterSafeMode() {
                LogUtil.e("已经进入安全模式");
                if (BaseLangUtil.isApkInDebug()) {
                    Intent intent = new Intent(BaseLangApplication.this, DebugSafeModeTipActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_salf_time", "进入APP安全模式V" + VersionUtil.getVersionName(BaseLangApplication.this));
                    MobclickAgent.onEvent(BaseLangApplication.this, "app_salf", params);
                }
            }

            @Override
            protected void onMayBeBlackScreen(Throwable throwable) {
                if (!BaseLangUtil.isApkInDebug()) {
                    //上传友盟日志
                    if (throwable != null) {
                        String errorInfo = CrashLog.getCrashLog(getApplicationContext(), true, throwable);
                        if (!BaseLangUtil.isEmpty(errorInfo)) {
                            MobclickAgent.reportError(BaseLangApplication.this, errorInfo);
                        }
                    }
                    MobclickAgent.onEvent(BaseLangApplication.this, "app_crash", "APPBlack_V" + VersionUtil.getVersionName(BaseLangApplication.this));
                }
                Thread thread = Looper.getMainLooper().getThread();
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---");
                //黑屏时建议直接杀死app
                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
            }

        });

    }

    //图片设置 tmpDir缓存文件夹
    public void initImageLoad() {
        Fresco.initialize(this);
        ImageLoadUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                } else {
                    GlideUtil.getInstance().display(BaseLangUtil.getContext(), "empty", view);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlCenterCrop(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayCenterCrop(BaseLangUtil.getContext(), url, view);
                    }
                } else {
                    GlideUtil.getInstance().displayCenterCrop(BaseLangUtil.getContext(), "empty", view);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlFitCenter(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayFitCenter(BaseLangUtil.getContext(), url, view);
                    }
                } else {
                    GlideUtil.getInstance().displayFitCenter(BaseLangUtil.getContext(), "empty", view);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")){
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    }else {
                        GlideUtil.getInstance().displayNoBg(BaseLangUtil.getContext(), url, view);
                    }
                } else {
                    GlideUtil.getInstance().displayNoBg(BaseLangUtil.getContext(), "empty", view);
                }
            }


            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,int backRes) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")){
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    }else {
                        GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), url, view,backRes);
                    }
                } else {
                    GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), "empty", view, backRes);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,float round,int backRes) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")){
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    }else {
                        GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), url,round, view,backRes);
                    }
                } else {
                    GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), "empty", round,view, backRes);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url, int width,final int height,float round) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")){
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    }else {
                        GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), url, view,width,height,round);
                    }
                } else {
                    GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), "empty", view,width,height, round);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url, BitmapFillet.CornerType cornerType,int width,final int height,float round) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")){
                        GlideUtil.getInstance().display(BaseLangUtil.getContext(), url, view);
                    }else {
                        GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), url, view,cornerType,width,height, round);
                    }
                } else {
                    GlideUtil.getInstance().displayRoundImg(BaseLangUtil.getContext(), "empty", view,cornerType,width,height, round);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId) {
                GlideUtil.getInstance().displayLocRes(BaseLangUtil.getContext(), resId, view);
            }


        });
    }

    public static Application getInstance() {
        return mApp;
    }

    public static void setmApp(Application mApp) {
        BaseLangApplication.mApp = mApp;
    }

    public synchronized PreferencesUtil getSpUtil() {
        if (mSpUtil == null)
            mSpUtil = new PreferencesUtil(this, PreferencesUtil.PREFERENCES_DEFAULT);
        return mSpUtil;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * imei
     */
    public String getIMEI() {
        String imei = MMKVUtil.getString("imei");
        if (BaseLangUtil.isEmpty(imei)) {
            try {
                TelephonyManager mTelephonyMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                imei = mTelephonyMgr.getDeviceId();
                MMKVUtil.putString("imei", imei);
            } catch (SecurityException e) {
                imei = Settings.Secure.getString(
                        this.getContentResolver(), Settings.Secure.ANDROID_ID);
                MMKVUtil.putString("imei", imei);
            }
        }
        return imei;
    }

    public boolean checkApkExist(String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
