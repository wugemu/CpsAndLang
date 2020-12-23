package com.lxkj.dmhw;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.dialog.WeixinAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.qiyukf.unicorn.api.YSFOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局公共静态常量
 * updated by LTH on 2019/04/01.
 */

public class Variable {

    //接口版本 公共参数
    public static String ApiVersion="20";

    /**
     * dialog显示状态
     */
    public static boolean dialogStatus = true;

    //图片路径的根路径
    public static String HomePath = Environment.getExternalStorageDirectory().toString()+ "/DMHW/";
    /**
     * 图片路径
     */
    public static String picturePath = HomePath + "picture";
    /**
     * 图片保存路径
     */
    public static String imagesPath =HomePath + "images";
    /**
     * 图片截取路径
     */
    public static String clipImagePath = HomePath + "clip";
    /**
     * 图片分享路径
     */
    public static String sharePath = HomePath + "share";
    /**
     * 日志保存路径
     */
    public static String crashPath = HomePath + "crash";
    /**
     * 视频保存路径
     */
    public static String videoPath1 = HomePath + "video";
    public static String videoPath = HomePath + "/DCIM/Camera";

    //SQLite数据库名称
    public static String SQLiteName = "DMHW.db";
    /**
     * 能分享多图到朋友圈的微信版本号versioncode
     */
    public static int weChatVersion = 620824064;

    /**
     * 网络异常
     */
    public static String connectFailed = "java.net.ConnectException:";
    /**
     * 主机解析失败
     */
    public static String unableResolve = "java.net.UnknownHostException:";
    /**
     * url
     */
    public static String webUrl = "a861f8cd62fb1d0806a8319facd09447";

    // key的配置-------------------start--------------------------
    /**
     * 微信
     */
    //AppId
    public static String weChatAppId = "111";
    //AppSecret
    public static String weChatAppSecret = "222";

    /**
     * QQ
     */
    //AppId
    public static String QQAppId = "111";


    /**
     *友盟
     */
    //测试UmengAppKey
    public static String UmengDebugAppKey = "111";
    //正式UmengAppKey
    public static String UmengReleaseAppKey = "222";


    /**
     * 京东联盟
     */
    //AppKey  京东SDK要配置返回把手<data android:scheme="sdkbackea6662ccf341a06c4331fb313b3d8f41"/>
    public static String JDAppKey = "b9b4c0dc447d017492b4f1da79354463";
    //AppSecret
    public static String JDAppSecret = "ce7132284fd64a7b84f7fbe661916533";

    /**
     * 七鱼管理系统appKey
     */
    public static String QYAppKey = "222";


    /**
     * 信用卡appKey
     */
    public static String appKey = "111"; //appkey 请替换为自己的appkey


    /**
     * 广告
     */
    public static String ADAppkey = "111";
    public static String ADsecretKey = "222";
    /**
     * 穿山甲
     */
    public static String CSJAppID = "111";
    public static String CSJAppName = "222";
    /**
     * 广点通
     */
    public static String GDTAppID = "111";


    /**
     * 闪验 本机一键登录的key
     */
    public static String SYAppID = "222";



    // key的配置-------------------end--------------------------

    //公司名称
    public static String ComponyName = "江苏省尛犇网络科技有限公司 版权所有";

  //淘宝渠道授权是否成功  0-超时 1-成功 2-失败（有内容提示）
    public static int tbAuthsuccess =0;
    /**
     * 微信调用标识
     */
    public static String weChatState = "32489553c4dac1ac4f5cec692cb7be5f";
    /**
     * 分享标题
     */
    public static String shareTitle = "傲娇好物，NN价格！";
    /**
     * 分享内容
     */
    public static String shareContent = "精选全网优质好物，放心买的舒心、用的安心！海量内部优惠券让你“自购省钱，推广赚钱”，优惠力度不亚于双十一，在这里，1元商品都包邮，1折爆款天天有，更有会员免单福利！";


    /**
     * 微信界面是否透明
     */
    public static boolean weChatCheck = false;
    /**
     * 是否是绑定微信
     */
    public static boolean BindingWeChat = false;

    /**
     * 手机验证码登录true 微信登录false
     */
    public static boolean loginByCode = true;

    /**
     * 手机验证码登录 多传一个参数 微信登录传0  判断微信是否被绑定传1
     */
    public static boolean isHasDialog = false;

    /**
     * 手机号码
     */
    public static String userPhone ="";


    /**
     * 点击设置界面去解绑微信
     */
    public static boolean isBindingWeChatFromSetting=false;

    /**
     * QQ分享状态
     */
    public static boolean QQCheck = false;
    /**
     * 截图回调
     */
    public static int CLIP_IMAGE = 4096;
    /**
     * 分佣比例
     */
    public static String ration="0";
    /**
     * 店主分佣比例
     */
    public static String superRatio="";
    /**
     * Activity状态
     */
    public static boolean isActive = true;
    /**
     * 通知渠道的ID
     */
    public static String CHANNEL_ID = "dmhw_push_one";



    //域名文件头
    private static String commonhttp="http://cps.17gouba.com/";
    //阿里云资源图片域名
    private static String alihttp="https://test-cps.oss-accelerate.aliyuncs.com/";

    /***
     *关于里面的协议以及注册协议
     */
    public static String protocl_Agreement_Secret=commonhttp+"privacyAgreement ";
    public static String protocl_Agreement_User=commonhttp+"userAgreement";
    /**
     * 分享默认图片
     */
    public static String shareImagepath = commonhttp+"download/system/ic_launcher.png";

    /***
     *呆萌店主网络协议
     */
    public static String protocl_Franchiser=commonhttp+"agreement/franchiser.html";

    /***
     *呆萌合伙人网络协议
     */
    public static String protocl_Partner=commonhttp+"agreement/partner.html";
    /**
     * 默认头像
     */
    public static String VatarImagepath = alihttp+"dmj/defaultHead.png";

    //底部tab 图标
    public static String tab_url = alihttp+"dmj/appmenu/";

    //萌币图标
    public static String mengbi_url = alihttp+"dmj/mengbi.gif";


    /**
     *  //计数弹框，防止弹出多次
     */
    public static int countConnect=0;
    public static int countTimeout=0;
    public static int countOther=0;


    /**
     *  //批量存图的显示隐藏 0-不可见 1-可见
     */
    public static int isVisible=0;

    /**
     *  1=运营商 2=呆萌会员 3=游客 4=呆萌店主 5=合伙人
     */
    public static String getUserTypeName(String  type){
       String  typename="";
       if (type.equals("1"))
           typename="运营商";
       else if(type.equals("2"))
           typename="呆萌会员";
       else if(type.equals("3"))
           typename="游客";
       else if(type.equals("4"))
           typename="呆萌店主";
       else if(type.equals("5"))
           typename="合伙人";

     return typename;
    }

    /**
     *  屏幕宽高
     */
    public static int  ScreenWidth=0;
    public static int  ScreenwHeight=0;


    /**
     * 授权弹框状态
     */
    public static boolean AuthShowStatus = false;

    /**
     * 广告弹窗显示，每次启动显示一下
     */
    public static boolean AdvertiseShowStatus = true;
    public static boolean AdvertiseHasShow = false;

    //信用卡后台Token
    public static String encrypt ="";//我们后台获取

    /**
     * 首页切换到其他页面 轮播图背景颜色不变，使用app主题颜色
     */
    public static boolean noChangeBg = false;
    /**
     * 控制颜色一致
     */
    public static boolean isSameColor = true;
    //控制颜色开始变化范围
    public static boolean isStartChangeBgColor=true;

    //注册获取金币
    public static String registScore="";

    //系统状态栏高度 用于适配刘海屏
    public static  int statusBarHeight=0;


    //小程序各个页面的配置
    //自营订单路径：
    public static  String selfOrderPath="pagesOrder/youxuanOrders/youxuanOrders?referId="+ DateStorage.getLittleAppId();


    public static YSFOptions ysfOptions; // 七鱼配置选项，设置后如果实在需要中途更换，可以通过此处修改



    //处理快速点击事件
    public static long LastClickTime = 0L;
    public static final int FAST_CLICK_DELAY_TIME = 500;  // 快速点击间隔

    public static boolean FastClickTime(){
        boolean flag = false;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - LastClickTime) <= FAST_CLICK_DELAY_TIME ) {
            flag = true;
        }
        LastClickTime = currentClickTime;
        return flag;
    }

}
