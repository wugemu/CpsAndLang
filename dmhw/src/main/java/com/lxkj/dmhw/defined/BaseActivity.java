package com.lxkj.dmhw.defined;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.igexin.sdk.PushManager;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.AliAuthWebViewActivityNew;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.LoadMoreFooterView;
import com.lxkj.dmhw.dialog.ChainGoodsDialog;
import com.lxkj.dmhw.dialog.JurisdictionDialog;
import com.lxkj.dmhw.dialog.MonitorDialog;
import com.lxkj.dmhw.dialog.PddAuthLoginDialog;
import com.lxkj.dmhw.dialog.ProgressDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthFailDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.dialog.TbAuthProgressDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Activity父类
 * Created by Zyhant on 2017/2/9.
 */
//@EnableDragToClose() 这个会引起页面滑动问题
public abstract class BaseActivity extends SupportActivity {

    // 弹窗
    private ProgressDialog dialog;
    // 检测弹窗
    public MonitorDialog showDialog;
    // 转链弹框
    public ChainGoodsDialog chainGoodsDialog;
    // 剪贴板
    public ClipboardManager manager;
    // post请求参数集合
    public HashMap<String, String> paramMap = new HashMap<>();
    // 页码
    public int page = 1;
    // 请求条数
    public int pageSize = 10;
    // 用户信息
    public UserInfo login;
    // 下拉刷新样式
    public LoadMoreFooterView loadView;
    //px值
    public  int width;
    public  int height;
//    public  LoginService loginService;
    private Boolean fromGetui=false;

    private String CurrentActivity="";

    TaobaoAuthLoginDialog  taldialog=null;
    PddAuthLoginDialog pddDialog=null;
    TbAuthProgressDialog tbAuthProgressDialog=null;
    //剪切板内容
    public static String clipContent="";
    //用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if(BuildConfig.DEBUG){
                Log.d("0.0","当前activiyt："+getLocalClassName());
            }
            CurrentActivity=getClass().getSimpleName();
            // 设置状态栏透明,5.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置状态栏
                View decorView = getWindow().getDecorView();
                int option, color;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    color = Color.TRANSPARENT;
                    option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    color = Color.parseColor("#50000000");
                    option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                }
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(color);
            }
            // 页面动画
            overridePendingTransition(R.anim.snake_slide_in_right, R.anim.snake_slide_out_left);
            // 设置用户信息
            login = DateStorage.getInformation();
            // 发布/订阅事件
            EventBus.getDefault().register(this);
            // 栈管理
            ActivityManagerDefine.getInstance().pushActivity(this);
            // 初始化弹窗
            dialog = new ProgressDialog(this, "");

            // 设置下拉刷新样式
            loadView = new LoadMoreFooterView(this);
            // 初始化剪贴板方法
            manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            WindowManager manager1 = getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager1.getDefaultDisplay().getMetrics(outMetrics);
            width = outMetrics.widthPixels;
            height = outMetrics.heightPixels;
//            fromGetui=getIntent().getBooleanExtra("fromGetui",false);
//            if(fromGetui){
//                paramMap.clear();
//                paramMap.put("type", "push");
//                NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
//            }
        } catch (Exception e) {
            Logger.e("BaseActivit", "");
        }
    }

    /**
     * 弹出提示
     */
    public void toast(String str) {
        try {
            ToastUtil.showImageToast(this,str,R.mipmap.toast_error);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Override
    public void finish() {
        try {
            super.finish();
            // 页面动画
            overridePendingTransition(R.anim.snake_slide_in_left, R.anim.snake_slide_out_right);
            // 隐藏键盘
            Utils.hideSoftInput(this, null);
            // 退出栈
            ActivityManagerDefine.getInstance().popActivity(this);
            if (getIntent().getBooleanExtra("isFinish", false)) {
                if (!ActivityManagerDefine.getInstance().selectActivity(MainActivity.class)) {
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
        } catch (Exception e) {
            Logger.e(e, "返回关闭");
        }
    }

    /**
     * 关闭页面
     */
    public void isFinish() {
        finish();
    }

    /**
     * 加载中弹窗提示
     */
    public void showDialog() {
        try {
            new Handler().postDelayed(dialog::hideDialog, 10000);
            // 判断弹窗是否已显示
            if (!dialog.isDialog()) {
                // 启动弹窗
                dialog.showDialog();
                // 设置弹窗状态
                Variable.dialogStatus = false;
            }
        } catch (Exception e) {
            Logger.e(e, "加载中弹窗提示");
        }
    }
    /**
     * 加载中弹窗提示 避免游戏白屏 1.5s后释放。
     */
    public void showDialogAD(){
        try {
            new Handler().postDelayed(dialog::hideDialog, 1500);
            // 判断弹窗是否已显示
            if (!dialog.isDialog()) {
                // 启动弹窗
                dialog.showDialog();
                // 设置弹窗状态
                Variable.dialogStatus = false;
            }
        } catch (Exception e) {
            Logger.e(e, "加载中弹窗提示");
        }
    }
    /**
     * 隐藏弹窗
     */
    public void dismissDialog() {
        try {
            // 判断弹窗是否已显示
            if (dialog!=null&&dialog.isDialog()) {
                // 隐藏弹窗
                dialog.hideDialog();
                // 设置弹窗状态
                Variable.dialogStatus = true;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 失败回调
     */
    public void Failed(int position, String content,int more) {
        if (position == NetworkRequest.FAILED) {
            if (more == 0) {
                if (!NetStateUtils.isNetworkConnected(MyApplication.getContext()) && Variable.countConnect == 0) {
                    Variable.countConnect++;
                    toast(getResources().getString(R.string.net_work_unconnect));
                } else if ((content.contains("time out") || content.contains("after 30000ms") || content.contains("timeout") || content.contains("timed out")||content.contains("SSL handshare time out")) && Variable.countTimeout == 0) {
                    Variable.countTimeout++;
                    toast(getResources().getString(R.string.net_work_timeout));
                } else {
                    if (Variable.countConnect == 0 && Variable.countConnect == 0 && Variable.countOther == 0) {
                        Variable.countOther++;
                        // 请求失败
                        if (content.startsWith(Variable.unableResolve)) {
                            content = "无法解析主机，请确认网络连接!";
                        } else if (content.startsWith(Variable.connectFailed) || content.contains("Failed to connect")) {
                            content = "网络连接异常，请稍后重试!";
                        } else {
                            if (content.equals("店铺不存在")) {

                            } else {
                                toast(content);
                            }

                        }

                    }
                }
                switch (content) {
                    case "未找到商品！":
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("Undercarriage"), "", 0);
                        break;
                    case "店铺不存在":
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShopInfoNo"), "", 0);
                        break;

                }
            } else{
                if (!content.equals("您的手机还未注册，请重试！")){
                    toast(content);
                }
            }
        }
        if (position==3){
            showChaoJiSouDialog(clipContent);
        }
    }

    protected Handler handler = new Handler(message -> {
        try {
            if (message.what == LogicActions.Failed) {
                Failed(message.arg1, message.obj + "", message.arg2);
                dismissDialog();
            }
            handlerMessage(message);
        } catch (Exception e) {
            Logger.e(e, "回调信息");
        }
        return false;
    });

    /**
     * 剪贴板
     */
    protected void clip() {
        if (CurrentActivity.equals("StartActivity320")||CurrentActivity.equals("GuideActivity")||CurrentActivity.equals("ScreenActivity")||CurrentActivity.equals("OneKeyChainActivity")){
           return;
        }
        try {
            if (manager == null && !manager.hasPrimaryClip()) {
                return;
            }
            if (manager.getPrimaryClip() != null) {
                clipContent = Objects.requireNonNull(manager.getPrimaryClip()).getItemAt(0).getText().toString();
                if (!TextUtils.isEmpty(clipContent)) {
                    if (!clipContent.equals(DateStorage.getClip())) {
                        if (clipContent.length() > 6) {
                            if (ActivityManagerDefine.getInstance().getActivity()!=null&&CurrentActivity.equals(ActivityManagerDefine.getInstance().getActivity().getClass().getSimpleName())) {
                                Variable.AdvertiseShowStatus=false;
                                if (DateStorage.getLoginStatus()) {
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GetMainOnkeyChain"), clipContent, 0);
                                }else{
                                    showChaoJiSouDialog(clipContent);
                                }
                            }
                            }
                        }
                }
            }
        }
        catch (Exception e){

        }
    }

    //显示一键转链弹框
   public void showOnekeyChainDialog(CommodityDetails290 commodityDetails290,String content){
           if (commodityDetails290 != null) {
               //一键弹框
               if (chainGoodsDialog!=null){
                   if (chainGoodsDialog.isShowing()){
                       chainGoodsDialog.dismiss();
                   }
                   chainGoodsDialog=null;
               }
               chainGoodsDialog = new ChainGoodsDialog(ActivityManagerDefine.getInstance().getActivity(), "");
               chainGoodsDialog.showDialog(commodityDetails290, content);
           }

   }

    //显示超级搜
    public void showChaoJiSouDialog(String content){
        // 检测弹窗
        if (showDialog!=null){
            if (showDialog.isShowing()){
            showDialog.dismiss();
            }
            showDialog=null;
        }
        showDialog = new MonitorDialog(ActivityManagerDefine.getInstance().getActivity(), "");
        showDialog.showDialog(content);
    }

    /**
     * 清空剪贴板
     */
    public void clearClip() {
        if (manager!=null)
        manager.setPrimaryClip(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventMain(Message message) {
        try {
            if (message.what == LogicActions.LoginStatusSuccess) {
                if ((Boolean) message.obj) {
                    login = DateStorage.getInformation();
                }
                if (CurrentActivity.equals("MainActivity")){
                    mainMessage(message);
                    return;
                }
                mainMessage(message);
            }

            if (message.what == LogicActions.RefreshStatusSuccess) {
                login = DateStorage.getInformation();
                login.setUsertype("2");
                DateStorage.setInformation(login);
                login = DateStorage.getInformation();
            }
            if (message.what == LogicActions.GoMainSuccess){
             if (CurrentActivity.equals("MainActivity")){
                 mainMessage(message);
               return;
             }else{
                 isFinish();
             }
             mainMessage(message);
            }
            if (message.what == LogicActions.RefreshUserTypeLayoutSuccess){
                if (CurrentActivity.equals("MainActivity")||CurrentActivity.equals("WebViewActivity")||CurrentActivity.equals("AliAuthWebViewActivity")){
                    mainMessage(message);
                   return;
                }else{
                    isFinish();
                }
            }

           //为了避免和BaseFragment重复通知，以下要做限制
            if(message.what!=LogicActions.UpdateNewsDetailsSuccess){
                mainMessage(message);
            }


            //处理授权弹窗
            if (message.what == LogicActions.noAuthSuccessfulSuccess) {
                dismissDialog();
                //统一处理渠道授权
                if (ActivityManagerDefine.getInstance().getActivity() != null && CurrentActivity.equals(ActivityManagerDefine.getInstance().getActivity().getClass().getSimpleName())) {
                    dismissDialog();
                    taldialog = new TaobaoAuthLoginDialog(this, message.obj.toString());
                    taldialog.showDialog();
                }
            }

            //处理授权弹窗
            if (message.what ==LogicActions.GotbAuthSuccess) {
                if (ActivityManagerDefine.getInstance().getActivity() != null && CurrentActivity.equals(ActivityManagerDefine.getInstance().getActivity().getClass().getSimpleName())) {
                    tbAuthProgressDialog =new TbAuthProgressDialog(this,message.obj.toString());
                    tbAuthProgressDialog.showDialog();
                }
            }

            //关闭授权弹窗
            if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
                if (ActivityManagerDefine.getInstance().getActivity() != null && CurrentActivity.equals(ActivityManagerDefine.getInstance().getActivity().getClass().getSimpleName())) {
                    closeTbAuth(message.obj.toString());
                }
            }

            //处理授权弹窗
            if (message.what == LogicActions.noPddAuthSuccess) {
                dismissDialog();
                //统一处理拼多多渠道授权
                if (ActivityManagerDefine.getInstance().getActivity() != null && CurrentActivity.equals(ActivityManagerDefine.getInstance().getActivity().getClass().getSimpleName())) {
                    dismissDialog();
                    pddDialog = new PddAuthLoginDialog(this, message.obj.toString());
                    pddDialog.showDialog();
                }
            }
            //处理未登录 跳转登录页
            if (message.what == LogicActions.noLoginSuccess) {
             startActivity(new Intent(this,LoginActivity.class));
            }

        } catch (Exception e) {
            Logger.e(e, "EventBus");
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventChild(Message message) {
        try {
            childMessage(message);
        } catch (Exception e) {
            Logger.e(e, "EventBus");
        }
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressedSupport() {
        isFinish();
    }

    /**
     * 当Activity与用户能进行交互时被执行
     */
    @Override
    protected void onResume() {
        try{
            super.onResume();
            Variable.countTimeout=0;
            Variable.countConnect=0;
            Variable.countOther=0;
            MobclickAgent.onResume(this);
        }catch (IllegalArgumentException e){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStart() {
        if (!isCurrentRunningForeground) {
          new  Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clip();
                }
            },500);
                if (DateStorage.getLoginStatus()) {
                    PushManager.getInstance().turnOnPush(this);
            }
        }
        super.onStart();
    }

    /**
     * 页面不见
     */
    @Override
    protected void onStop() {
        Variable.countTimeout=0;
        Variable.countConnect=0;
        Variable.countOther=0;
        Variable.isActive = false;
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
        }
        super.onStop();
    }

    /**
     * 当Activity销毁的时候调用
     */
    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            AlibcTradeSDK.destory();
            EventBus.getDefault().unregister(this);
            Share.destroy();
        } catch (Exception e) {
            Logger.e(e, "Activity销毁");
        }
    }

    /**
     * 当Activity彻底运行起来之后回调
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        try {
            super.onPostCreate(savedInstanceState);
        } catch (Exception e) {
            Logger.e(e, "Activity彻底运行起来");
        }
    }

    abstract public void mainMessage(Message message);

    abstract public void childMessage(Message message);

    abstract public void handlerMessage(Message message);


    //获取官方活动统一请求接口
    public void getActivityChain(String userid,String activityid){
        paramMap.put("userid", userid);
        paramMap.put("activityid", activityid);
        NetworkRequest.getInstance().POST(handler, paramMap, "ActivityChain", HttpCommon.ActivtyChain);
}

    //调用打开淘宝用户端
    public void openTaobao(String activityurl,String towp,String activitypic){
        if (activityurl.equals(""))
        {
            return;
        }
        if (activityurl.toLowerCase().startsWith("taobao://")) {
            if (!getIntent().getBooleanExtra("isCheck", false)) {
                Utils.Alibc(this, activityurl.replace("taobao://", "https://"), null, null);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处必须添加这个回调（解决分享不回调问题）
        Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
            @Override
            public void onComplete(Object o) {
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 重置App界面的字体大小，fontScale 值为 1 代表默认字体大小
     * @return res
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = 1;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    /**
     * 程序是否在前台运行
     */
    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    //关闭授权弹窗
    public void closeTbAuth(String textString){
        if (textString.equals("")){
            Variable.tbAuthsuccess=1;
            ToastUtil.showImageToast(ActivityManagerDefine.getInstance().getActivity(),"授权成功",R.mipmap.toast_img);
        }else{
            Variable.tbAuthsuccess=2;
            TaobaoAuthFailDialog taobaoAuthFailDialog=new TaobaoAuthFailDialog(this,textString);
            taobaoAuthFailDialog.show();
            if (AlibcLogin.getInstance()!=null) {
                AlibcLogin.getInstance().logout(new AlibcLoginCallback() {
                    @Override
                    public void onSuccess(int i, String s, String s1) {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        }
        if (tbAuthProgressDialog!=null&&tbAuthProgressDialog.isShowing()){
            tbAuthProgressDialog.dismiss();
        }
        //告知页面授权结果
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("TaoBaoAuthResultToH5"), textString, 0);
        Variable.AdvertiseShowStatus=true;
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
    }

    /**
     * 处理权限返回状态
     * @param requestCode 请求状态
     * @param permissions
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == Utils.WRITE_EXTERNAL_STORAGE_REQUEST) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        // 授权成功
                    } else {
                        // 授权失败
                        switch (permissions[i]) {
                            case Manifest.permission.READ_PHONE_STATE:// 手机状态
                                permissionsPrompt("获取手机信息");
                                break;
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:// 储存空间
                                permissionsPrompt("读写手机存储");
                                break;
                        }
                        return;
                    }
                }
                // 有权限了b

            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 没有权限提醒
     * @param permissions
     */
    private void permissionsPrompt(String permissions) {
        JurisdictionDialog dialog = new JurisdictionDialog(this, permissions);
        dialog.showDialog();
    }
}
