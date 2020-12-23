package com.lxkj.dmhw;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.bx.adsdk.AdSdk;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.imageload.BitmapFillet;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.IInnerImageSetter;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.igexin.sdk.PushManager;
import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.login.KeplerApiManager;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.defined.GlideImageLoader;
import com.lxkj.dmhw.defined.GlideImageLoaderqiyu;
import com.lxkj.dmhw.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.mmkv.MMKV;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.util.Objects;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * 程序入口
 */
public class MyApplication extends MultiDexApplication {
    public static long NOW_TIME;
    // Application的 上下文对象
    private static MyApplication mContext;
    public static String PAY_RESULT_TRADE_NO = "";//订单tradeNo

    @Override
    public void onCreate() {
        try {
            super.onCreate();
            mContext = this;
             new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        // 淘宝
                        AlibcTradeSDK.asyncInit(MyApplication.this, new AlibcTradeInitCallback() {
                            @Override
                            public void onSuccess() {
                                // 初始化成功，设置相关的全局配置参数
                                Log.e("0.0", "阿里百川初始化成功onSuccess:");
                            }
                            @Override
                            public void onFailure(int i, String s) {
                                // 初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                                Log.e("0.0", "阿里百川初始化失败onFailure:"+i+":"+s);
                            }
                        });
                    // MMKV
                    MMKV.initialize(MyApplication.this);
                    // 初始化个推
                    PushManager.getInstance().initialize(MyApplication.this);
                    // 初始化下载组件
                    FileDownloader.setupOnApplicationOnCreate(MyApplication.this);
                    // Fragment框架
                    Fragmentation.builder()
                            // 设置 栈视图 模式为 BUBBLE: 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                            .stackViewMode(Fragmentation.NONE)
                            .debug(me.yokeyword.fragmentation.BuildConfig.DEBUG)
                            // 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                            .handleException(e -> {
                                // 打印Fragmentation错误
                                Logger.e(e, "Fragmentation");
                            }).install();


                    // TBS初始化
                    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
                    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                        @Override
                        public void onViewInitFinished(boolean arg0) {
                            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                        }

                        @Override
                        public void onCoreInitFinished() {
                        }
                    };
                    //x5内核初始化接口
                    QbSdk.initX5Environment(mContext,  cb);

                    // GalleryFinal
                    galleryFinal();
                    // 设置推送渠道
                    setNotificationChannel();
                    // 初始化ImageLoader
                    imageLoader();
                    initImageLoad();

                    BaseLangApplication.tmpCacheName="nncps";
                    /**
                     * 设置组件化的Log开关
                     * 参数: boolean 默认为false，如需查看LOG设置为true
                     */
                    UMConfigure.setLogEnabled(true);
                    /**
                     * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
                     * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
                     * UMConfigure.init调用中appkey和channel参数请置为null）。
                     */
                    if (BuildConfig.DEBUG) {
                        UMConfigure.init(mContext, Variable.UmengDebugAppKey, "UMENG_DEBUG", UMConfigure.DEVICE_TYPE_PHONE, "");
                    }else{
                        UMConfigure.init(mContext, Variable.UmengReleaseAppKey, Utils.getAppMetaData(mContext,"UMENG_CHANNEL"), UMConfigure.DEVICE_TYPE_PHONE, "");
                    }

                    //异常捕捉开关
                    MobclickAgent.setCatchUncaughtExceptions(true);

                    // appKey 可以在七鱼管理系统->设置->App 接入 页面找到
                    Unicorn.init(MyApplication.this, Variable.QYAppKey, Options(), new GlideImageLoaderqiyu(MyApplication.this));

                    //
                    KeplerApiManager.asyncInitSdk(MyApplication.this, Variable.JDAppKey, Variable.JDAppSecret, new AsyncInitListener(){
                        @Override
                        public void onSuccess() {
                            Log.e("0.0", "京东联盟：Kepler asyncInitSdk onSuccess ");
                        }
                        @Override
                        public void onFailure()
                        {
                            Log.e("0.0", "京东联盟：Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
                        } });
                    } catch (Exception e) {
                        Logger.e(e, "syc");
                    }
                    //闪验日志开关
                    OneKeyLoginManager.getInstance().setDebug(false);
                    //闪验SDK初始化（建议放在Application的onCreate方法中执行）
                    initShanyanSDK(getApplicationContext());

                    //广告打开日志
                    AdSdk.setDebug(false);
                    //初始化 需要在Applicaiton 中
                    AdSdk.init(mContext, Variable.ADAppkey, Variable.ADsecretKey);

                    Looper.loop();
                }
            }.start();
        } catch (Exception e) {
            Logger.e(e, "Application");
        }





    }

    private void setNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            // 如果这里用IMPORTANCE_NONE就需要在系统的设置里面开启渠道，通知才能正常弹出
            NotificationChannel notificationChannel = new NotificationChannel(Variable.CHANNEL_ID, mContext.getResources().getString(R.string.app_name) + "通知", NotificationManager.IMPORTANCE_HIGH);
            // 设置闪烁指示灯
            notificationChannel.enableLights(true);
            // 设置通知出现时的震动
            notificationChannel.enableVibration(true);
            // 屏幕锁定时通知显示方式
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            // 是否在久按桌面图标时显示此渠道的通知
            notificationChannel.setShowBadge(true);
            // NotificationChannel中创建该通知渠道
            Objects.requireNonNull(manager).createNotificationChannel(notificationChannel);
        }
    }
    private YSFOptions Options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.statusBarNotificationConfig.notificationSmallIconId = R.mipmap.ic_launcher;
        options.onBotEventListener = new OnBotEventListener() {
            @Override
            public boolean onUrlClick(Context context, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
        };
        // 如果项目中使用了 Glide 可以通过设置 gifImageLoader 去加载 gif 图片
//        options.gifImageLoader = new GlideGifImagerLoader(this);

        Variable.ysfOptions = options;
        return options;
    }
    /**
     * 设置相册
     */
    private void galleryFinal() {
        // 设置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.BLACK)// 标题栏文本字体颜色
                .setTitleBarBgColor(Color.WHITE)// 标题栏背景颜色
                .setCheckSelectedColor(getResources().getColor(R.color.mainColor))// 选择框选中颜色
                .setCropControlColor(getResources().getColor(R.color.mainColor))// 设置裁剪控制点和裁剪框颜色
                .setIconBack(R.mipmap.btn_back_default)// 设置返回按钮icon
                .setFabNornalColor(getResources().getColor(R.color.mainColor))// 设置Floating按钮Nornal状态颜色
                .setFabPressedColor(getResources().getColor(R.color.mainColor))// 设置Floating按钮Pressed状态颜色
                .setIconFolderArrow(R.mipmap.main_sort)// 设置标题栏文件夹下拉arrow图标
                .build();
        // 配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(9)// 配置多选数量
                .setEnableCrop(true)// 开启裁剪功能
                .setEnableEdit(true)// 开启编辑功能
                .setEnableCamera(false)// 开启相机功能
                .setCropSquare(true)// 裁剪正方形
                .setForceCrop(true)// 启动强制裁剪功能
                .setForceCropEdit(false)// 在开启强制裁剪功能时是否可以对图片进行编辑
                .setEnablePreview(false)// 是否开启预览功能
                .build();
        // 配置ImageLoader
        cn.finalteam.galleryfinal.ImageLoader imageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, theme)
                .setFunctionConfig(functionConfig)// 配置全局GalleryFinal功能
                .build();
        GalleryFinal.init(coreConfig);
    }

//    /**
//     * 设置周期性任务
//     */
//    private void jobScheduler() {
//        if (DateStorage.getLoginStatus()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                final int JOB_ID = 1;
//                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(mContext, GTService.class))
//                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                        .setPeriodic(15*60*1000)
//                        .setPersisted(true)
//                        .build();
//                Objects.requireNonNull(scheduler).schedule(jobInfo);
//            }
//        }
//    }

    /**
     * 初始化ImageLoader
     */
    private void imageLoader() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(1024 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.diskCache(new UnlimitedDiskCache(new File(Variable.HomePath + "cache")));//自定义缓存路径
        ImageLoader.getInstance().init(builder.build());//全局初始化此配置
    }

    /**
     * 获取Application的 上下文对象
     * @return
     */
    public static MyApplication getContext() {
        return mContext;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    //闪验本机一键登录
    private void initShanyanSDK(Context context) {
        OneKeyLoginManager.getInstance().init(context, Variable.SYAppID, new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {
                //闪验SDK初始化结果回调
                Log.e("VVV", "初始化： code==" + code + "   result==" + result);
            }
        });
    }

    public void initImageLoad() {
        ImageLoadUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlCenterCrop(@NonNull IMAGE view, @Nullable String url) {
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlFitCenter(@NonNull IMAGE view, @Nullable String url) {
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url) {
                Utils.displayImage(getMyContext(),url,view);
            }


            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,int backRes) {
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,float round,int backRes) {
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url, int width,final int height,float round) {
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url, BitmapFillet.CornerType cornerType,int width,final int height,float round) {
                Utils.displayImage(getMyContext(),url,view);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId) {

            }


        });
    }
    public  Context getMyContext() {
        Context activity = ActivityUtil.getInstance().getLastActivity();
        if (activity == null) {
            activity = mContext;
        }
        if (activity instanceof Activity) {
            if (((Activity) activity).isFinishing()) {
                activity=mContext;
            }
        }
        return activity;
    }
}
