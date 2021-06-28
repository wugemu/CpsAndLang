package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.android.sdklibrary.presenter.util.Params;
//穿山甲普通版
//import com.bytedance.sdk.openadsdk.TTAdConfig;
//import com.bytedance.sdk.openadsdk.TTAdConstant;
//import com.bytedance.sdk.openadsdk.TTAdSdk;
//import com.bytedance.sdk.openadsdk.TTCustomController;
//穿山甲OPPO版
import com.bykv.vk.openvk.TTCustomController;
import com.bykv.vk.openvk.TTVfConfig;
import com.bykv.vk.openvk.TTVfConstant;
import com.bykv.vk.openvk.TTVfSdk;

import com.igexin.sdk.PushManager;
import com.lxkj.dmhw.DataMigration;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.ActivtyChain;
import com.lxkj.dmhw.bean.Advertising;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.Menu;
import com.lxkj.dmhw.bean.OneKeyItem;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.RollingMessage;
import com.lxkj.dmhw.bean.Screen;
import com.lxkj.dmhw.bean.Version;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.data.ScreenOpen;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.dialog.AdvertisingDialog;
import com.lxkj.dmhw.dialog.RegisterTaskDialog;
import com.lxkj.dmhw.dialog.VersionDialog;
import com.lxkj.dmhw.dialog.WeixinAuthLoginDialog;
import com.lxkj.dmhw.fragment.CommercialCollegeFragment;
import com.lxkj.dmhw.fragment.FiveFragmentNew;
import com.lxkj.dmhw.fragment.OneFragment290;
import com.lxkj.dmhw.fragment.TwoFragment;
import com.lxkj.dmhw.fragment.self.SelfHomeFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.myinterface.HomeI;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScrollTextView;
import com.qq.e.comm.managers.GDTADManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */

public class MainActivity extends BaseActivity implements View.OnClickListener , HomeI {

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    // 记录是否有首次按键
    private static boolean mBackKeyPressed = false;
    private Screen screen;
    private ScreenOpen screenOpen;
    private SelfHomeFragment oneFragment=null;
    private OneFragment290 twoFragment=null;
//    private SpecilFragment twoFragment=null;

    private CommercialCollegeFragment threeFragment=null;
    private TwoFragment fourFragment=null;
//    private FiveFragment_Agent280 fiveFragment=null;
    private FiveFragmentNew fiveFragment=null;
    // 导航栏数据
    private ArrayList<Menu> menus;
    Advertising advertising=new Advertising();


    public static MainActivity mainActivity=null;

    //信用卡参数设置返佣比例等
    private String bonusRate="12";
    private String shareRate="10";
    private String showType="A";
    private String isShare="false";

    private int labelType=0;
    private String labelTitle="";

    @BindView(R.id.one_img)
    ImageView one_img;
    @BindView(R.id.two_img)
    ImageView two_img;
    @BindView(R.id.four_img)
    ImageView four_img;
    @BindView(R.id.five_img)
    ImageView five_img;
    @BindView(R.id.middle_img)
    ImageView middle_img;
    @BindView(R.id.one_text)
    TextView one_text;
    @BindView(R.id.two_text)
    TextView two_text;
    @BindView(R.id.four_text)
    TextView four_text;
    @BindView(R.id.five_text)
    TextView five_text;
    @BindView(R.id.middle_text)
    TextView middle_text;
    @BindView(R.id.tab_one)
    LinearLayout tab_one;
    @BindView(R.id.tab_two)
    LinearLayout tab_two;
    @BindView(R.id.tab_middle)
    LinearLayout tab_middle;
    @BindView(R.id.tab_four)
    LinearLayout tab_four;
    @BindView(R.id.tab_five)
    LinearLayout tab_five;
    private FragmentManager fragementmanager;
    private FragmentTransaction transaction;
    private boolean isFormNet=false;
    public static int currentPos=0;

    WeixinAuthLoginDialog wxdialog;

    @BindView(R.id.fragment_one_rolling_message_close)
    LinearLayout fragmentOneRollingMessageClose;
    @BindView(R.id.fragment_one_rolling_message_content_text)
    ScrollTextView fragmentOneRollingMessageContentText;
    @BindView(R.id.fragment_one_rolling_message_content)
    RelativeLayout fragmentOneRollingMessageContent;
    @BindView(R.id.arrow)
    ImageView fragmentOneRollingMessageArrow;
    @BindView(R.id.fragment_one_rolling_message)
    RelativeLayout fragmentOneRollingMessage;
    // 滚动消息
    private RollingMessage rollingMessage;

    @BindView(R.id.main_login_layout)
    RelativeLayout main_login_layout;
    @BindView(R.id.main_login_close)
    LinearLayout main_login_close;
    @BindView(R.id.main_login_btn)
    Button main_login_btn;


    @BindView(R.id.lead_daren_layout)
    RelativeLayout lead_daren_layout;
    @BindView(R.id.lead_daren_btn)
    ImageView lead_daren_btn;
    @BindView(R.id.lead01_daren)
    ImageView lead01_daren;
    @BindView(R.id.lead02_daren)
    ImageView lead02_daren;


    @BindView(R.id.lead_usercenter_layout_one)
    RelativeLayout lead_usercenter_layout_one;

    @BindView(R.id.lead02_usercenter)
    ImageView lead02_usercenter;
    @BindView(R.id.lead02_usercenter_two)
    ImageView lead02_usercenter_two;

    @BindView(R.id.lead_usercenter_btn)
    ImageView lead_usercenter_btn;
    @BindView(R.id.lead_usercenter_btn_two)
    ImageView lead_usercenter_btn_two;

    //京东呼起
    int plat=1;

      String type;//平台类型
      String chainContent;
      CommodityDetails290 details=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity=this;
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        Variable.ScreenWidth=width;
        Variable.ScreenwHeight=height;
        // 设置文件名字
        File destDir = new File(Variable.HomePath);
        if (!destDir.exists()) {
            // 创建SD卡路径
            destDir.mkdir();
        }
        // 数据迁移
        DataMigration.Migration();
        login = DateStorage.getInformation();
        // 设置动画
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        if (NetStateUtils.isNetworkConnected(this)){
            // 查询更新
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "Version", HttpCommon.Version);


            // 获取导航栏
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "GetMenu", HttpCommon.GetMenu);

            // 判断是否登录
            if (DateStorage.getLoginStatus()) {
                init();
            }
        }
        fragmentOneRollingMessageContentText.setSelected(true);
        // 滚动消息
        if (DateStorage.getLoginStatus()) {
            main_login_layout.setVisibility(View.GONE);
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "RollingMessage", HttpCommon.RollingMessage);
        }else{
            main_login_layout.setVisibility(View.VISIBLE);
        }
        screen();
        screenOpen = new ScreenOpen();

        //打开测试环境 打开测试环境 230 环境 或 210 环境
        //如果上线的话请配置为false或者直接注释此代码
        Params.isDebug = false;
        Params.serverScene = "210";
        //初始化信用卡
        KDFInterface.getInstance().init(getApplicationContext(), Variable.appKey);


        tab_one.setOnClickListener(this);
        tab_two.setOnClickListener(this);
        tab_middle.setOnClickListener(this);
        tab_four.setOnClickListener(this);
        tab_five.setOnClickListener(this);
        fragmentOneRollingMessageClose.setOnClickListener(this);
        fragmentOneRollingMessageContent.setOnClickListener(this);
        main_login_close.setOnClickListener(this);
        main_login_btn.setOnClickListener(this);


        //获取FragmentManager对象
        fragementmanager = getSupportFragmentManager();
        currentPos=0;
        setSwPage(0);
        //个推厂商通道
        doGeTuiOffLine();
        //穿山甲普通版
//        TTAdSdk.init(this, buildConfig(this));
        //穿山甲OPPO
        TTVfSdk.init(this, buildConfig(this));
        //广点通
        GDTADManager.getInstance().initWith(this, Variable.GDTAppID);
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                ActivityManagerDefine.getInstance().popAllActivity();
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void init() {
        //开启推送
        setPush(true);
        // 获取userId
        login = DateStorage.getInformation();
        PushManager.getInstance().turnOnPush(this);
    }


    public void getMenuMagic(){
        if (DateStorage.getLoginStatus()){
            paramMap.clear();
            paramMap.put("userid",login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "RollingMessage", HttpCommon.RollingMessage);
        }
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess) {
            if (message.arg1==0) {
                main_login_layout.setVisibility(View.GONE);
                PushManager.getInstance().turnOnPush(this);
                setPush(true);
                screen();
                //获取收入比例 用于搜索列表全网搜计算佣金
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "GetRatio", HttpCommon.GetRatio);
            } else {
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("UpdateNewsDetails"), false, 0);
                main_login_layout.setVisibility(View.VISIBLE);
                setPush(false);
                if (screenOpen.find() != null) {
                    Utils.deleteFile(screenOpen.find().getPath());
                    screenOpen.delete();
                }
            }
          if (JavascriptHandler.autoLink) {
              Intent intent = new Intent(MainActivity.this, AliAuthWebViewActivity.class);
              intent.putExtra(Variable.webUrl, JavascriptHandler.linkUrl);
              intent.putExtra("isTitle" , true);
              startActivity(intent);
              JavascriptHandler.autoLink=false;
          }

            // 滚动消息
            if (DateStorage.getLoginStatus()){
                paramMap.clear();
                paramMap.put("userid",login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "RollingMessage", HttpCommon.RollingMessage);
            }else{
                fragmentOneRollingMessage.setVisibility(View.GONE);
            }
        }
        if (message.what == LogicActions.OpenArticleOrVideoSuccess) {
            httpPost(message.arg1+"");
        }
        if (message.what == LogicActions.RegisterStatusSuccess) {
            //注册奖励弹框
            if (!Variable.registScore.equals("")) {
                new Handler().postDelayed(() -> {
                    RegisterTaskDialog dialog1 = new RegisterTaskDialog(this);
                    dialog1.showDialog("");
                    Variable.registScore="";
                },1000);
            }
        }
        // 返回首页
        if (message.what == LogicActions.GoMainSuccess) {
            if(message.arg1==1){
//                //定位到升级【店主】
//                currentPos=2;
//                setSwPage(2);
            }else if(message.arg1==2){
                currentPos=4;
                setSwPage(4);
            }else if(message.arg1==3){//【萌圈】
                currentPos=3;
                setSwPage(3);
            }else if(message.arg1==4){//【热卖】
                currentPos=1;
                setSwPage(1);
            }
            else{
                //定位到【首页】
                currentPos=0;
                setSwPage(0);
            }
        }

        // 升级店主 刷新一级界面
        if (message.what == LogicActions.RefreshUserTypeLayoutSuccess) {
            // 获取用户信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);

            oneFragment=null;
            twoFragment=null;
            threeFragment=null;
            fourFragment=null;
            fiveFragment=null;
            if(message.arg1==0){
                //定位【首页】
                currentPos=0;
                setSwPage(0);
            }else if(message.arg1==1){
                //定位【萌圈】
                currentPos=3;
                setSwPage(3);
            }else if(message.arg1==2){
//                //定位【升级店主】
//                currentPos=2;
//                setSwPage(2);
            }else if(message.arg1==3){
                //定位【热卖】
                currentPos=1;
                setSwPage(1);
            }else{
                currentPos=4;
                setSwPage(4);
            }
        }
        if (message.what == LogicActions.ShowAdvertisementSuccess) {
          if(advertising!=null&&advertising.getIsexit().equals("1")&&Variable.AdvertiseShowStatus&&!Variable.AdvertiseHasShow){
              Variable.AdvertiseHasShow=true;
              AdvertisingDialog dialog = new AdvertisingDialog(this, advertising);
              dialog.showDialog();
          }
        }

        //轮播图点击事件统计
        if (message.what == LogicActions.AdvertisementClickSuccess) {
            paramMap.clear();
            paramMap.put("advertisemenid",message.arg1+"");
            NetworkRequest.getInstance().POST(handler, paramMap, "AdvertisementClick", HttpCommon.AdvertisementClick);
        }

        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }

        if (message.what == LogicActions.PJWGetCouponSuccess) {
            if (NetStateUtils.isNetworkConnected(this)) {
                com.alibaba.fastjson.JSONObject obj= (com.alibaba.fastjson.JSONObject) message.obj;

                if (obj!=null) {
                    plat=message.arg1;
                    getGenByUrl(obj.getString("clickValue"), obj.getString("sysParam"));
                }
            } else {
                toast("当前网络已断开，请连接网络");
            }
        }

        if (message.what==LogicActions.CreditTokenBrocastSuccess){
            showDialog();
            httpgetCreditToken();
        }

        //生活券 等等通知
        if (message.what==LogicActions.HomeImgClickBrocastSuccess){
            HomePage.JGQAppIcon jgqAppIcon= (HomePage.JGQAppIcon) message.obj;
            if(message.arg1==44){
                showDialog();
                labelType=44;
                labelTitle= jgqAppIcon.getName();
                paramMap=new HashMap<>();
                paramMap.put("labeltype","44");
                paramMap.put("url",jgqAppIcon.getUrl());
                NetworkRequest.getInstance().POST(handler, paramMap, "HomeImgClick", HttpCommon.HomeImgClick);
            }else if(message.arg1==45){
                showDialog();
                labelType=45;
                labelTitle= jgqAppIcon.getName();
                paramMap=new HashMap<>();
                paramMap.put("labeltype","45");
                paramMap.put("url",jgqAppIcon.getUrl());
                NetworkRequest.getInstance().POST(handler, paramMap, "HomeImgClick", HttpCommon.HomeImgClick);
            }else{
                showDialog();
                labelType=46;
                labelTitle= jgqAppIcon.getName();
                paramMap=new HashMap<>();
                paramMap.put("labeltype","46");
                paramMap.put("url",jgqAppIcon.getUrl());
                NetworkRequest.getInstance().POST(handler, paramMap, "HomeImgClick", HttpCommon.HomeImgClick);
            }
        }

        //弹框一键转链
        if (message.what == LogicActions.GetMainOnkeyChainSuccess) {
            paramMap.clear();
            chainContent=message.obj.toString();
            paramMap.put("text",chainContent);
            NetworkRequest.getInstance().POST(handler, paramMap, "ConVertTextToGoods", HttpCommon.ConVertTextToGoods);
        }
    }

    @Override
    public void childMessage(Message message) {
        // 邀请码弹窗回调
        if (message.what == LogicActions.InvitationDialogSuccess) {
            // 请求邀请码登录
            paramMap.clear();
            paramMap.put("extensionid", message.obj + "");
            NetworkRequest.getInstance().POST(handler, paramMap, "Extension", HttpCommon.Extension);
        }
    }

    @Override
    public void handlerMessage(Message message) {
        // 获取导航栏成功回调
        if (message.what == LogicActions.GetMenuSuccess) {
            menus = (ArrayList<Menu>) message.obj;
           if(menus!=null&&menus.size()>0){
               //有数据才去覆盖底部五颗导航按钮
               isFormNet=true;
               //根据currentPos 判定加载之后那个按钮是选中状态。
               reImgSelect();
             switch (currentPos){
                 case 0:
                     Utils.displayImage(this,menus.get(0).getMenuclickico(),one_img);
                     one_text.setText(menus.get(0).getMenuname());
                     one_text.setTextColor(getResources().getColor(R.color.black));
                     break;
                 case 1:
                     Utils.displayImage(this,menus.get(1).getMenuclickico(),two_img);
                     two_text.setText(menus.get(1).getMenuname());
                     two_text.setTextColor(getResources().getColor(R.color.black));
                     break;
                 case 2:
                     Utils.displayImage(this,menus.get(2).getMenuclickico(),middle_img);
                     middle_text.setText(menus.get(2).getMenuname());
                     middle_text.setTextColor(getResources().getColor(R.color.black));
                     break;
                 case 3:
                     Utils.displayImage(this,menus.get(3).getMenuclickico(),four_img);
                     four_text.setText(menus.get(3).getMenuname());
                     four_text.setTextColor(getResources().getColor(R.color.black));
                     break;
                 case 4:
                     Utils.displayImage(this,menus.get(4).getMenuclickico(),five_img);
                     five_text.setText(menus.get(4).getMenuname());
                     five_text.setTextColor(getResources().getColor(R.color.black));
                     break;


             }
           }
        }
        // 邀请码登录成功回调
        if (message.what == LogicActions.ExtensionSuccess) {
            login = DateStorage.getInformation();
            // 获取导航栏
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "GetMenu", HttpCommon.GetMenu);
        }
        if (message.what == LogicActions.VersionSuccess) {
            Version version = (Version) message.obj;
            if (!Utils.getLocalAppVersion(MainActivity.this).equals(version.getDevversion())){
            if (!Objects.equals(version.getUpdateflag(), "0")) {
                boolean isCheck = Objects.equals(version.getUpdateflag(), "2");
                String versionCode = version.getDevversion();
                String content = version.getVersiondesc();
                String url = version.getDownloadurl();
                if (isCheck) {//强制更新
                    VersionDialog versionDialog = new VersionDialog(this, isCheck, versionCode, content, url);
                    versionDialog.getDialog().show();
                } else {//提示更新
                    if (!versionCode.equals(DateStorage.getIgnoreVersion())) {
                        VersionDialog versionDialog = new VersionDialog(this, isCheck, versionCode, content, url);
                        versionDialog.getDialog().show();
                    }else{
                        //只要没有更新弹框 就可以弹超级搜和广告
                        new  Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                clip();
                                paramMap.clear();
                                paramMap.put("position", "2");
                                NetworkRequest.getInstance().POST(handler, paramMap, "Advertising", HttpCommon.GetScreen);
                            }
                        },200);
                    }
                }
            }else{
                //只要没有更新弹框 就可以弹超级搜和广告
                new  Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clip();
                        paramMap.clear();
                        paramMap.put("position", "2");
                        NetworkRequest.getInstance().POST(handler, paramMap, "Advertising", HttpCommon.GetScreen);
                    }
                },200);
            }
            }
            else {
                //只要没有更新弹框 就可以弹超级搜和广告
                new  Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clip();
                        paramMap.clear();
                        paramMap.put("position", "2");
                        NetworkRequest.getInstance().POST(handler, paramMap, "Advertising", HttpCommon.GetScreen);
                    }
                },200);
            }
        }
        //超级搜弹框优先于广告弹窗
        if (message.what == LogicActions.AdvertisingSuccess) {
            advertising = (Advertising) message.obj;
            if(advertising!=null&&advertising.getIsexit().equals("1")&&Variable.AdvertiseShowStatus&&!Variable.AdvertiseHasShow){
                    Variable.AdvertiseHasShow=true;
                    AdvertisingDialog dialog = new AdvertisingDialog(this, advertising);
                    dialog.showDialog();
            }
        }

        if (message.what == LogicActions.ActivtyChainSuccess) {
            //官方活动 tapbao://开头 打开淘宝
            com.alibaba.fastjson.JSONObject jsonObject= (com.alibaba.fastjson.JSONObject) message.obj;
            if (jsonObject.getString("result").equals("3")) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("noAuthSuccessful"), jsonObject.toString(), 0);
            }else{
                ArrayList<ActivtyChain> tempList = (ArrayList<ActivtyChain>)JSON.parseArray(jsonObject.getJSONArray("activitylist").toString(), ActivtyChain.class);
                openTaobao(tempList.get(0).getActivityurl(),"","");
            }
        }

        if (message.what == LogicActions.GetScreenSuccess) {
            screen = (Screen) message.obj;
            if (screen.isCheck()) {// 有广告
                splashScreen();
            } else {// 无广告
                if (screenOpen.find() != null) {
                    Utils.deleteFile(screenOpen.find().getPath());
                    screenOpen.delete();
                }
            }
        }
//        if (message.what == LogicActions.GetWeChatSuccess) {
//            JSONObject jsonObject=(JSONObject)message.obj;
//            String tempmsg="";
//            if (jsonObject.optString("isbind").equals("0")) {
//                //定位到个人中心
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess(""), false, 2);
//                tempmsg= jsonObject.optString("alertmsg");
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("WxAuthDialog"), tempmsg, 0);
//            }else{
//                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("WxAuthDialog"), tempmsg, 1);
//            }
//        }
        //重要滚动通知接口处理
        if (message.what == LogicActions.RollingMessageSuccess) {
            rollingMessage = (RollingMessage) message.obj;
            if (rollingMessage.getIsexit().equals("0")) {
                fragmentOneRollingMessage.setVisibility(View.GONE);
            } else {
                fragmentOneRollingMessage.setVisibility(View.VISIBLE);
                fragmentOneRollingMessageContentText.setSpeed(-4);
                fragmentOneRollingMessageContentText.setText(rollingMessage.getMessagedesc());
                if (TextUtils.isEmpty(rollingMessage.getMessagedtlhtml())) {
                    fragmentOneRollingMessageArrow.setVisibility(View.GONE);
                } else {
                    fragmentOneRollingMessageArrow.setVisibility(View.VISIBLE);
                }
            }
        }

        //判断类型打开文章或者视频
        if (message.what == LogicActions.ComCollContentDetailSuccess) {
            dismissDialog();
            ComCollArticle article= (ComCollArticle) message.obj;
            if (article.getLabel().equals("0")){//常见问题
                //跳转问题详情
                Intent intent=new Intent(this, ComCollArticleDetailActivity.class);
                intent.putExtra("from","Ques");
                intent.putExtra("articleId",article.getId());
                startActivity(intent);
            }else{//文章和视频
                if (article.getType().equals("1")) {
                    //视频详情
                    Intent intent = new Intent(this, VideoActivity300.class);
                    intent.putExtra("id", article.getId());
                    intent.putExtra("title", article.getTitle());
                    startActivity(intent);
                } else {
                    //跳转文章详情
                    Intent intent = new Intent(this, ComCollArticleDetailActivity.class);
                    intent.putExtra("from", "NoQues");
                    intent.putExtra("articleId", article.getId());
                    startActivity(intent);
                }
            }
        }
        //呼起京东APP
        if (message.what == LogicActions.GetGenByUrlSuccess) {
            dismissDialog();
            PJWLink link = (PJWLink) message.obj;
            if (Utils.isNullOrEmpty(link.getShortUrl())) {
                Toast.makeText(MainActivity.this, "URL为空", Toast.LENGTH_SHORT).show();
                return;
            }

            Utils.callMorePlatFrom(this,plat,link.getSchemaUrl(),link.getUrl());

        }

        //获取信用卡SDK 的Token
        if (message.what == LogicActions.CreditCardTokenSuccess) {
            JSONObject jsonObject=(JSONObject) message.obj;
            try {
                Variable.encrypt=jsonObject.getString("token");
                bonusRate=jsonObject.getString("bonusRate");
                shareRate=jsonObject.getString("shareRate");
                showType=jsonObject.getString("showType");
                isShare=jsonObject.getString("isShare");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loginCreditCard();
            dismissDialog();
        }

        //生活券
        if (message.what == LogicActions.HomeImgClickSuccess) {
            dismissDialog();
            if (labelType==44){
                startActivity(new Intent(this, WebPageNavigationActivity.class).putExtra("url",message.obj+"").putExtra("title",labelTitle));
            }else {
                startActivity(new Intent(this, OnlyUrlWebViewActivity.class).putExtra(Variable.webUrl,message.obj+""));
            }
        }

        if (message.what == LogicActions.ConVertTextToGoodsSuccess) {
            OneKeyItem oneKeyItem= (OneKeyItem) message.obj;
            if (oneKeyItem!=null){
                if (oneKeyItem.getGoodsList().size() > 0) {
                    showOnekeyChainDialog(oneKeyItem.getGoodsList().get(0),chainContent);
                }
            }
            }
        if (message.what == LogicActions.GetRatioSuccess) {
            CommodityRatio ratio = (CommodityRatio) message.obj;
            Variable.ration = ratio.getRatio();
            Variable.superRatio = ratio.getSuperratio();
        }
    }

    private void splashScreen() {
        if (screenOpen.find() != null) {
            File file = new File(screenOpen.find().getPath());
            if (file.exists()) {//文件存在
                if (!screen.getAdvimg().equals(screenOpen.find().getAdvimg())) {
                    new Thread(() -> {
                        try {
                            Utils.deleteFile(screenOpen.find().getPath());
                            Utils.downFile(screen.getAdvimg(), Variable.picturePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                screenOpen.delete();
                screen.setPath(Variable.picturePath + "/" + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf("/") + 1, screen.getAdvimg().lastIndexOf(".")) + "." + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf(".") + 1));
                screenOpen.insert(screen);
            } else {// 文件不存在
                new Thread(() -> {
                    try {
                        Utils.downFile(screen.getAdvimg(), Variable.picturePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                screen.setPath(Variable.picturePath + "/" + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf("/") + 1, screen.getAdvimg().lastIndexOf(".")) + "." + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf(".") + 1));
                screenOpen.insert(screen);
            }
        }else{//数据库为空
            new Thread(() -> {
                try {
                    Utils.downFile(screen.getAdvimg(), Variable.picturePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            screen.setPath(Variable.picturePath + "/" + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf("/") + 1, screen.getAdvimg().lastIndexOf(".")) + "." + screen.getAdvimg().substring(screen.getAdvimg().lastIndexOf(".") + 1));
            screenOpen.insert(screen);
        }
    }

    private void screen() {
        // 获取闪屏广告
        paramMap.clear();
        paramMap.put("position", "1");
        NetworkRequest.getInstance().POST(handler, paramMap, "GetScreen", HttpCommon.GetScreen);
    }

    /**
     * 设置推送
     */
    private void setPush(boolean isCheck) {
        if (PushManager.getInstance().getClientid(this)!=null) {
            paramMap.clear();
            if (isCheck) {
                paramMap.put("userid", login.getUserid());
            }
            paramMap.put("cid", PushManager.getInstance().getClientid(this));
            NetworkRequest.getInstance().POST(handler, paramMap, "SetCid", HttpCommon.SetCid);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_one:
                currentPos=0;
                setSwPage(0);
                oneFragment.onResume();
                break;
            case R.id.tab_two:
                currentPos=1;
                setSwPage(1);
                twoFragment.onResume();
                break;
            case R.id.tab_middle:
//                currentPos=2;
//                setSwPage(2);
                if (DateStorage.getLoginStatus()){
//                    currentPos=2;
//                    setSwPage(2);
                    Intent intent = new Intent(this, MyTaskFragmentActivity.class);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(this,LoginActivity.class).putExtra("relocation",true));
                }
                break;
            case R.id.tab_four:
                currentPos=3;
                setSwPage(3);
                break;
            case R.id.tab_five:
                currentPos=4;
                setSwPage(4);
                break;
            case R.id.fragment_one_rolling_message:
                break;
            case R.id.fragment_one_rolling_message_close:// 关闭
                if (rollingMessage!=null) {
                    rollingMessage.setIsexit("0");
                }
                fragmentOneRollingMessage.setVisibility(View.GONE);
                break;
            case R.id.fragment_one_rolling_message_content:// 跳转消息详情
                if (!TextUtils.isEmpty(rollingMessage.getMessagedtlhtml())) {
                    Intent intent = new Intent(this, MessageDetailsActivity.class);
                    intent.putExtra("mTitle", "重要通知");
                    intent.putExtra("title", "");
//                    intent.putExtra("content", rollingMessage.getMessagedtl());
                    intent.putExtra("content", rollingMessage.getMessagedtlhtml());
                    startActivity(intent);
                }
                break;
            case R.id.main_login_btn:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.main_login_close:
                main_login_layout.setVisibility(View.GONE);
                break;
            case R.id.lead02_daren:
            case R.id.lead_daren_btn:
                lead_daren_layout.setVisibility(View.GONE);
                DateStorage.setIsShowLead01320("hasShow");
                break;
            case R.id.lead_daren_layout:
                break;
            case R.id.lead_usercenter_layout_one:
                break;
            case R.id.lead02_usercenter:
            case R.id.lead_usercenter_btn://功能引导1
                lead02_usercenter.setVisibility(View.GONE);
                lead_usercenter_btn.setVisibility(View.GONE);
                lead_usercenter_btn_two.setVisibility(View.VISIBLE);
                lead02_usercenter_two.setVisibility(View.VISIBLE);
                break;
            case R.id.lead02_usercenter_two:
            case R.id.lead_usercenter_btn_two://功能引导2
                DateStorage.setIsShowLead320("hasShow");
                lead_usercenter_layout_one.setVisibility(View.GONE);
                break;
        }
    }
    //显示达人说功能引导
   public void isshowLeadLayout(int topHeight){
        if (currentPos==1) {
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) lead01_daren.getLayoutParams();
            linearParams.setMargins(0, topHeight, 0, 0);
            lead01_daren.setLayoutParams(linearParams);
            lead02_daren.setOnClickListener(this);
            lead_daren_btn.setOnClickListener(this);
            lead_daren_layout.setOnClickListener(this);
            lead_daren_layout.setVisibility(View.VISIBLE);
        }
   }
    public void isshowLeadUserCenterLayout(int marTopHeight ) {
        if (currentPos==4) {
            lead_usercenter_layout_one.setOnClickListener(this);
            lead02_usercenter.setOnClickListener(this);
            lead02_usercenter_two.setOnClickListener(this);
            lead_usercenter_btn.setOnClickListener(this);
            lead_usercenter_btn_two.setOnClickListener(this);
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) lead02_usercenter.getLayoutParams();
            linearParams.setMargins(0, marTopHeight, 0, 0);
            lead02_usercenter.setLayoutParams(linearParams);
            RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) lead02_usercenter_two.getLayoutParams();
            linearParams2.setMargins(0, marTopHeight, 0, 0);
            lead02_usercenter_two.setLayoutParams(linearParams2);
            lead_usercenter_layout_one.setVisibility(View.VISIBLE);
            lead_usercenter_btn.setVisibility(View.VISIBLE);
            lead02_usercenter.setVisibility(View.VISIBLE);
        }
    }

    //切换选项卡
    public void setSwPage(int i) {
        //获取FragmentTransaction对象
        transaction = fragementmanager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        reImgSelect();
        switch (i) {
            case 0:
                if (isFormNet){
                    Utils.displayImage(this,menus.get(0).getMenuclickico(),one_img);
                }else{
                    Utils.displayImage(this,Variable.tab_url+"homeSelect.png",one_img);
                }
                one_text.setTextColor(getResources().getColor(R.color.black));
                if (oneFragment == null) {
                    oneFragment = new SelfHomeFragment();
                    transaction.add(R.id.main_container, oneFragment);
                } else {
                    transaction.show(oneFragment);
                }
                break;
            case 1:
                if (isFormNet){
                    Utils.displayImage(this,menus.get(1).getMenuclickico(),two_img);
                }else {
                    Utils.displayImage(this,Variable.tab_url+"hotSelect.png",two_img);
                }
                two_text.setTextColor(getResources().getColor(R.color.black));
                if (twoFragment == null) {
                    twoFragment =new OneFragment290();
                    transaction.add(R.id.main_container, twoFragment);
                } else {
                    transaction.show(twoFragment);
                }
                break;
            case 2:
                if (isFormNet){
                    Utils.displayImage(this,menus.get(2).getMenuclickico(),middle_img);
                }else {
                    Utils.displayImage(this,Variable.tab_url+"businessSelect.png",middle_img);
                }
                middle_text.setTextColor(getResources().getColor(R.color.black));
                    if (threeFragment == null) {
                        threeFragment =new CommercialCollegeFragment();
                        transaction.add(R.id.main_container, threeFragment);
                    } else {
                        transaction.show(threeFragment);
                    }
                break;
            case 3:
                if (isFormNet){
                    Utils.displayImage(this,menus.get(3).getMenuclickico(),four_img);
                }else {
                    Utils.displayImage(this,Variable.tab_url+"recommendSelect.png",four_img);
                }
                four_text.setTextColor(getResources().getColor(R.color.black));
                if (fourFragment == null) {
                    fourFragment =new TwoFragment();
//                    fourFragment =new FourFragment();
                    transaction.add(R.id.main_container, fourFragment);
                } else {
                    transaction.show(fourFragment);
                }
                break;
            case 4:
                if (isFormNet){
                    Utils.displayImage(this,menus.get(4).getMenuclickico(),five_img);
                }else {
                    Utils.displayImage(this,Variable.tab_url+"userSelect.png",five_img);
                }
                five_text.setTextColor(getResources().getColor(R.color.black));
                if (fiveFragment == null) {
//                    fiveFragment = new FiveFragment_Agent280();
                    fiveFragment = new FiveFragmentNew();
                    transaction.add(R.id.main_container, fiveFragment);
                } else {
                    transaction.show(fiveFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (oneFragment != null) {
            transaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            transaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
        }
        if (fourFragment != null) {
            transaction.hide(fourFragment);
        }
        if (fiveFragment != null) {
            transaction.hide(fiveFragment);
        }
    }

    //初始化底部菜单选择状态
    private void reImgSelect(){
        if (isFormNet){
            Utils.displayImage(this,menus.get(0).getMenudefaultico(),one_img);
            Utils.displayImage(this,menus.get(1).getMenudefaultico(),two_img);
            Utils.displayImage(this,menus.get(2).getMenudefaultico(),middle_img);
            Utils.displayImage(this,menus.get(3).getMenudefaultico(),four_img);
            Utils.displayImage(this,menus.get(4).getMenudefaultico(),five_img);
        }else{
            Utils.displayImage(this,Variable.tab_url+"home.png",one_img);
            Utils.displayImage(this,Variable.tab_url+"hot.png",two_img);
            Utils.displayImage(this,Variable.tab_url+"business.png",middle_img);
            Utils.displayImage(this,Variable.tab_url+"recommend.png",four_img);
            Utils.displayImage(this,Variable.tab_url+"user.png",five_img);

        }
//        if (DateStorage.getLoginStatus()){
//            if (login.getUsertype().equals("2")){
//                middle_text.setText("升级店主");
//            }else{
//                middle_text.setText("店主中心");
//            }
//        }else{
//            middle_text.setText("升级店主");
//        }
        one_text.setTextColor(getResources().getColor(R.color.color_999999));
        two_text.setTextColor(getResources().getColor(R.color.color_999999));
        middle_text.setTextColor(getResources().getColor(R.color.color_999999));
        four_text.setTextColor(getResources().getColor(R.color.color_999999));
        five_text.setTextColor(getResources().getColor(R.color.color_999999));

    }

    //获取当前的fragment对象
    public SelfHomeFragment getOneFragment(){
        if(oneFragment!=null){
         return oneFragment;
        }
        return new SelfHomeFragment();
    }

//    public void showWxBindDialog(String message){
//        if (wxdialog!=null&&wxdialog.isShowing()){
//        }else{
//            wxdialog = new WeixinAuthLoginDialog(this, message);
//            wxdialog.show();
//        }
//
//    }
    private void httpPost(String id) {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "ComCollContentDetail", HttpCommon.ArticleContentDetail);
    }


    //pjw转链
    //优惠券
    private Coupon coupon= new Coupon();
    private void getGenByUrl(String value,String sysparam){
        showDialog();
        paramMap.clear();
        paramMap.put("clickValue", value);
        paramMap.put("sysParam", sysparam);
        if (plat==2) {
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GetGenByUrl", HttpCommon.JDGetGenByUrl);
        }else if(plat==1){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GetGenByUrl", HttpCommon.PDDGetGenByUrl);
        }else if(plat==3){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GetGenByUrl", HttpCommon.WPHGetGenByUrl);
        }else if(plat==5){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GetGenByUrl", HttpCommon.SNGetGenByUrl);
        }else{
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GetGenByUrl", HttpCommon.KLGetGenByUrl);
        }
    }

//===================================个推推送全局====================================================

    public void doGetui(String content) {
        try {
            Intent intent = null;
            JSONObject object = new JSONObject(content);
            switch (object.getString("parameters")) {
                case "1":
                    intent = new Intent(this,AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, object.getString("link"));
                    startActivity(intent);
                    break;
                case "2":
                    intent = new Intent(this, CommodityActivity290.class);
                    intent.putExtra("shopId", object.getString("shopid"));
                    intent.putExtra("source",  object.getString("source"));
                    intent.putExtra("sourceId", object.getString("sourceId"));
                    startActivity(intent);
                    break;
                case "4"://功能和轮播图一样
                    BannerClick(object.getString("advertisementlink"),object.getString("jumptype"),object.getString("needlogin"));
                    break;
                case "5"://金刚区
                    pushJGQ(object.getString("url"),object.getString("name"),object.getString("labeltype"),object.getString("needlogin"));
                    break;
                default:
                    intent = new Intent(this, Class.forName(object.getString("activity")));
                    startActivity(intent);
                    break;
            }
            doGeTuiNotifition();
        } catch (Exception e) {

        }
    }

    //个推厂商通道
    private void doGeTuiOffLine(){
        if (getIntent().getStringExtra("parameters")!=null&&getIntent().getStringExtra("parameters").equals("1")){
            Intent intent = new Intent(this, AliAuthWebViewActivity.class);
            intent.putExtra(Variable.webUrl, getIntent().getStringExtra("link"));
            intent.putExtra("isFinish", false);
            startActivity(intent);
            doGeTuiNotifition();
        }
        if (getIntent().getStringExtra("parameters")!=null&&getIntent().getStringExtra("parameters").equals("2")){
            Intent intent = new Intent(this, CommodityActivity290.class);
            intent.putExtra("shopId", getIntent().getStringExtra("shopid"));
            intent.putExtra("source", getIntent().getStringExtra("source"));
            intent.putExtra("sourceId", getIntent().getStringExtra("sourceId"));
            intent.putExtra("isFinish", false);
            startActivity(intent);
            doGeTuiNotifition();
        }
        // 个推厂商通道（离线） banner推送
        if (getIntent().getStringExtra("parameters")!=null&&getIntent().getStringExtra("parameters").equals("4")){
            BannerClick(getIntent().getStringExtra("advertisementlink"),getIntent().getStringExtra("jumptype"),getIntent().getStringExtra("needlogin"));
            doGeTuiNotifition();
        }
        //个推厂商通道（离线） 金刚区推送
        if (getIntent().getStringExtra("parameters")!=null&&getIntent().getStringExtra("parameters").equals("5")){
            pushJGQ(getIntent().getStringExtra("url"),getIntent().getStringExtra("name"),getIntent().getStringExtra("labeltype"),getIntent().getStringExtra("needlogin"));
            doGeTuiNotifition();
        }
    }

    //轮播图推送点击事件 强制登录
    public void BannerClick(String advertisementlink, String jumpType,String needLogin){
        Utils.getJumpType(this,jumpType,advertisementlink,needLogin,"");
    }

    //金刚区推送点击事件  要登录
    public void pushJGQ(String url, String name,String labeltype,String needLogin){
           HomePage.JGQAppIcon jgqAppIcon=new HomePage.JGQAppIcon();
           jgqAppIcon.setUrl(url);
           jgqAppIcon.setName(name);
           jgqAppIcon.setNeedlogin(needLogin);
           jgqAppIcon.setLabeltype(labeltype);
           Utils.doJgqClick(MainActivity.this,jgqAppIcon);
    }


    //获取信用卡token
    public void httpgetCreditToken(){
        paramMap.clear();
        paramMap.put("userid",login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "CreditCardToken", HttpCommon.getCreditToken);
    }

    //卡多分SDK接入creditcard
    //获取encrypt成功之后执行登录
    private void loginCreditCard(){
        if (!Variable.encrypt.equals("")) {
            KDFInterface.getInstance().login(this, Variable.encrypt, bonusRate, showType, isShare, shareRate, new OnComplete() {
                @Override
                public void onSuccess(JSONObject o) {
                    //登录成功去办卡首页
                    KDFInterface.getInstance().toCardStoreActivity(MainActivity.this, null);
                }
                @Override
                public void onError(String error) {
                    toast(error);
                }
            });
        }else{
            toast("网络异常请稍后重试！");
        }
    }


    //查看个推获取积分
    private void doGeTuiNotifition(){
        paramMap.clear();
        paramMap.put("type", "push");
        NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
    }

    //穿山甲普通的配置
//    private TTAdConfig buildConfig(Context context) {
//        return new TTAdConfig.Builder()
//                .appId(Variable.CSJAppID)
//                .appName(Variable.CSJAppName)
//                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
//                .allowShowNotify(true) //是否允许sdk展示通知栏提示
//                .debug(false) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
//                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_MOBILE) //允许直接下载的网络状态集合
//                .supportMultiProcess(true)//是否支持多进程
//                .needClearTaskReset()
//                .customController(new TTCustomController() {//是否允许SDK主动使用地理位置信息
//                    @Override
//                    public boolean isCanUseLocation() {
//                        return false;
//                    }
//                })
//                .build();
//    }
    //穿山甲OPPO配置
    private TTVfConfig buildConfig(Context context) {
        return new TTVfConfig.Builder()
                .appId(Variable.CSJAppID)
                .appName(Variable.CSJAppName)
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .debug(false) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(TTVfConstant.NETWORK_STATE_WIFI, TTVfConstant.NETWORK_STATE_MOBILE) //允许直接下载的网络状态集合
                .supportMultiProcess(true)//是否支持多进程
                .needClearTaskReset()
                .customController(new TTCustomController() {//是否允许SDK主动使用地理位置信息
                    @Override
                    public boolean isCanUseLocation() {
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void click(int i) {
        switch (i) {
            case 1:
                tab_one.performClick();
                break;
            case 2:
                tab_two.performClick();
                break;
            case 3:
                tab_middle.performClick();
                break;
            case 4:
                tab_four.performClick();
                break;
            case 5:
                tab_five.performClick();
                break;
        }
    }
}
