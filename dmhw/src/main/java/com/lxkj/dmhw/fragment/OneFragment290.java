package com.lxkj.dmhw.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.HotSellFragmentActivity;
import com.lxkj.dmhw.activity.LimitActivity;
import com.lxkj.dmhw.activity.LiveInspectListActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.MorePlSearchNewActivity;
import com.lxkj.dmhw.activity.MyTaskFragmentActivity;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.activity.NewsActivity;
import com.lxkj.dmhw.activity.OnlyUrlWebViewActivity;
import com.lxkj.dmhw.activity.SortThreeFragmentActivity;
import com.lxkj.dmhw.adapter.BannerMAdapter;
import com.lxkj.dmhw.adapter.BannerMainAdapter;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.adapter.HotSellAdapter;
import com.lxkj.dmhw.adapter.HotTopicThreeAdapter290;
import com.lxkj.dmhw.adapter.HotTopicTwoAdapter290;
import com.lxkj.dmhw.adapter.JinGangQuIconAdapter;
import com.lxkj.dmhw.adapter.LimitTimeAdapter;
import com.lxkj.dmhw.adapter.MainRoomAdapter;
import com.lxkj.dmhw.adapter.OneFragmentVerticalBannerAdapter;
import com.lxkj.dmhw.bean.Advertising;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.bean.Template;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.AppBarLayoutBehaviorNew;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleViewNew;
import com.lxkj.dmhw.dialog.ServiceDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ArcBackgroundView;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.view.CountDownTimerView;
import com.lxkj.dmhw.view.RoundLayoutNew;
import com.lxkj.dmhw.view.ScaleLayout;
import com.lxkj.dmhw.view.SlidingAroundFrameLayout;
import com.lxkj.dmhw.view.StrongerHorizontalScrollView;
import com.taobao.library.VerticalBannerView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 首页
 * Created by Administrator on 2019/12/25.
 */

public class OneFragment290 extends BaseFragment implements PtrHandler{

    //下拉刷新
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;

    @BindView(R.id.onefragment_head_content)
    LinearLayout onefragment_head_content;


    //返回顶部
    @BindView(R.id.return_top)
    ImageView returnTop;

    //侧边栏收缩广告
    @BindView(R.id.sliding_adv_layout)
    SlidingAroundFrameLayout sliding_adv_layout;
    @BindView(R.id.sliding_adv_close)
    ImageView sliding_adv_close;
    @BindView(R.id.sliding_adv_img)
    ImageView sliding_adv_img;
    private Advertising  advertising;
    @BindView(R.id.fragment_one_message_img)
    ImageView fragment_one_message_img;

     //无活动下的背景
//    @BindView(R.id.coloranimationview)
//    ColorAnimationView coloranimationview;
//    @BindView(R.id.colorargbg)
//    ImageView colorargbg;

   // 搜索以及状态栏控件声明
    @BindView(R.id.fragment_one_serarch_layout)
    LinearLayout serarch_layout;
    @BindView(R.id.fragment_one_search)
    LinearLayout fragmentOneSearch;
    @BindView(R.id.fragment_one_service)
    RelativeLayout fragmentOneService;
    @BindView(R.id.fragment_one_message)
    RelativeLayout fragmentOneMessage;
    @BindView(R.id.fragment_one_left_iv)
    CircleImageView fragment_one_left_iv;
    @BindView(R.id.fragment_one_message_num)
    LinearLayout fragment_one_message_num;
    @BindView(R.id.tv_one_left_name)
    TextView tv_one_left_name;

    //无网络遮罩层
    @BindView(R.id.network_mask)
    LinearLayout main_network_mask;

    //超级分类控件声明
    @BindView(R.id.more_sort_layout)
    LinearLayout more_sort_layout;
    @BindView(R.id.fragment_one_magic)
    MagicIndicator fragmentOneMagic;

    private int lastPos=0;

    //轮播图控件声明
    @BindView(R.id.header_fragment_one_new_banner_layout)
    RoundLayoutNew header_fragment_one_new_banner_layout;
    @BindView(R.id.header_fragment_one_new_banner)
    ConvenientBanner headerFragmentOneNewBanner;
    @BindView(R.id.lunbo_dotlayout)
    RelativeLayout lunbo_dotlayout;
    @BindView(R.id.ArcBack)
    ArcBackgroundView ArcBack;
    @BindView(R.id.currentpos)
    TextView currentpos;
    @BindView(R.id.totalsize)
    TextView totalsize;


    //轮播图的点
//    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
//    private int[] page_indicatorId;

    //轮播图底部的广告
    @BindView(R.id.onframgent_part03)
    ScaleLayout onframgent_part03;
    @BindView(R.id.onefragment_adv01)
    ImageView onefragment_adv01;

    //金刚区
    @BindView(R.id.onframgent_part04)
    ScaleLayout onframgent_part04;
    @BindView(R.id.onframgent_part04_Bg)
    ImageView onframgent_part04_Bg;
    @BindView(R.id.jingang_recycler01)
    RecyclerView jingang_recycler01;
    @BindView(R.id.jingang_recycler02)
    RecyclerView jingang_recycler02;
    @BindView(R.id.jingang_layout_hs)
    StrongerHorizontalScrollView jingang_layout_hs;
    @BindView(R.id.slide_indicator_point)
    SeekBar  slide_indicator_point;
    JinGangQuIconAdapter jinGangQuIconAdapterOne,jinGangQuIconAdapterTwo;

    //零元购以及活动热区和背景
    @BindView(R.id.topic_hot_layout_ll)
    LinearLayout topic_hot_layout_ll;
    @BindView(R.id.header_fragment_one_new_six_banner)
    ConvenientBanner headerFragmentOneNewSixBanner;
    @BindView(R.id.header_fragment_topic_hot_rv_layout)
    RelativeLayout header_fragment_topic_hot_rv_layout;
    @BindView(R.id.header_fragment_topic_hot_grid_layout)
    RelativeLayout header_fragment_topic_hot_grid_layout;
    @BindView(R.id.header_fragment_topic_hot_rv)
    RecyclerView header_fragment_topic_hot_rv;
    @BindView(R.id.header_fragment_topic_hot_grid)
    RecyclerView  header_fragment_topic_hot_grid;
    @BindView(R.id.topic_hot_layout_bg_iv)
    ImageView topic_hot_layout_bg_iv;
    private HotTopicTwoAdapter290 hotTopicTwoAdapter;
    private HotTopicThreeAdapter290 hotTopicThreeAdapter;

    //限时抢购
    LimitTimeAdapter limitTimeAdapter;
    @BindView(R.id.limit_buy_content)
    ScaleLayout limit_buy_content;
    @BindView(R.id.TimerView)
    CountDownTimerView TimerView;
    @BindView(R.id.limitbuy_viewpager)
    ViewPager limitbuy_viewpager;
    @BindView(R.id.limitbuy_magic)
    MagicIndicator limitbuy_magic;


    //精选 人气宝贝
    @BindView(R.id.header_fragment_one_new_vertical_banner)
    VerticalBannerView limitScrollerView01;
    @BindView(R.id.header_fragment_two_new_vertical_banner)
    VerticalBannerView limitScrollerView02;
    @BindView(R.id.header_fragment_three_new_vertical_banner)
    VerticalBannerView limitScrollerView03;
    @BindView(R.id.header_fragment_four_new_vertical_banner)
    VerticalBannerView limitScrollerView04;
    @BindView(R.id.big_brand_layout)
    ScaleLayout big_brand_layout;
    @BindView(R.id.renqi_layout)
    ScaleLayout renqi_layout;

    @BindView(R.id.select_one_title)
    TextView select_one_title;
    @BindView(R.id.select_one_subtitle)
    TextView select_one_subtitle;
    @BindView(R.id.select_two_title)
    TextView select_two_title;
    @BindView(R.id.select_two_subtitle)
    TextView select_two_subtitle;


    OneFragmentVerticalBannerAdapter marqueeAdapter01;
    OneFragmentVerticalBannerAdapter marqueeAdapter02;
    OneFragmentVerticalBannerAdapter marqueeAdapter03;
    OneFragmentVerticalBannerAdapter marqueeAdapter04;

     Handler VBhandler;
    //热销榜单
    @BindView(R.id.hotsell_recycler)
    RecyclerView hotsell_recycler;
    @BindView(R.id.hotsell_recycler_layout)
    LinearLayout hotsell_recycler_layout;
    @BindView(R.id.hotsell_button_layout)
    LinearLayout hotsell_button_layout;
    HotSellAdapter hotSellAdapter;

    //验货直播
    @BindView(R.id.room_inspect_layout )
    LinearLayout room_inspect_layout;
    @BindView(R.id.select_room_more_layout )
    LinearLayout select_room_more_layout;
    @BindView(R.id.header_fragment_room_recycler)
    RecyclerView header_fragment_room_recycler;
    MainRoomAdapter mainRoomListAdapter;



    //底部导航 悬停
    @BindView(R.id.bottom_navigate_magic)
    MagicIndicator bottom_navigate_magic;
    @BindView(R.id.navigate_content)
    ViewPager navigate_content;


    //新金刚区
    @BindView(R.id.onframgent_part05)
    ScaleLayout onframgent_part05;
    @BindView(R.id.onframgent_part05_Bg)
    ImageView onframgent_part05_Bg;
    @BindView(R.id.jgq_new_magicindicator)
    MagicIndicator jgq_new_magicindicator;
    @BindView(R.id.jgq_new_content)
    ViewPager jgq_new_content;
    @BindView(R.id.progress_bar)
    SeekBar progress_bar;
    @BindView(R.id.jgq_new_magicindicator_layout)
    LinearLayout jgq_new_magicindicator_layout;

    //首页数据实体
    private HomePage homePage = new HomePage();

    @BindView(R.id.Coordinator_Layout)
    CoordinatorLayout Coordinator_Layout;

    @BindView(R.id.AppBar_Layout)
    AppBarLayout AppBar_Layout;


    @BindView(R.id.onframgent_part01)
    ScaleLayout onframgent_part01;
    @BindView(R.id.onframgent_part02)
    ScaleLayout onframgent_part02;

    @BindView(R.id.onframgent_part01_Bg)
    ImageView onframgent_part01_Bg;
    @BindView(R.id.onframgent_part01_bg_layout)
    ScaleLayout onframgent_part01_bg_layout;
    @BindView(R.id.onframgent_part02_Bg)
    ImageView onframgent_part02_Bg;

    @BindView(R.id.fragment_other_content)
    FrameLayout fragmentOtherContent;
    @BindView(R.id.fragment_one_content_layout)
    RelativeLayout fragment_one_content_layout;
    // 声明Fragment对象
    private OneFragment_main_Other290 oneFragmentOther290=null;
    private MainYouLikeFragment mainBottomFragment290=null;

    // Fragment装载器
    private Fragment currentFragment = new Fragment();
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;

    // 下拉刷新样式
    public  static int isBar=1;

    private boolean isJGQBg=false;

    //判断头部是否有背景 有背景背景色就不要随着轮播图颜色变化
    public static boolean isTopBg=true;
    //切换其他选项卡 隐藏返回按钮
    private boolean isHidereturnTop=true;
    //隐藏广告
    private int isHasSlidingMenu=1;

    TextPaint tp;

    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    //当前选项卡的索引
    public static int currPos=0;

    //超级分类以及轮播图是否有背景
    Boolean part02HasBg=false;

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one290, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 设置下拉刷新样式
            isBar=0;
        }
        if (Variable.statusBarHeight>0) {
            isBar=1;
        }
        VBhandler= new Handler();
        //清除图片缓存
        Utils.deleteDir(Variable.clipImagePath);
        Utils.deleteDir(Variable.sharePath);
        main_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainActivity.getMenuMagic();
                onCustomized();
            }
        });
//        fragment_one_left_iv.setBackgroundResource(R.drawable.meng);
//        AnimationDrawable animaition = (AnimationDrawable)fragment_one_left_iv.getBackground();
//        animaition.start();
        Utils.displayImage(getActivity(),Variable.mengbi_url,fragment_one_left_iv);
        ptrFrameLayout();
        Variable.isSameColor = true;
        Variable.isStartChangeBgColor = true;
        initSubGroup();
        tp=select_one_title.getPaint();
        tp.setFakeBoldText(true);
        tp=select_two_title.getPaint();
        tp.setFakeBoldText(true);

    }
    //初始化组件
    @TargetApi(Build.VERSION_CODES.M)
    private void  initSubGroup(){
       AppBar_Layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
           @Override
           public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//               if (isTopBg) {
//                   //上滑 轮播图不可视
//                   if (i < -Utils.dipToPixel(R.dimen.dp_175)) {
//                           coloranimationview.setVisibility(View.GONE);
//                           colorargbg.setVisibility(View.VISIBLE);
//                   } else {
//                       coloranimationview.setVisibility(View.VISIBLE);
//                       colorargbg.setVisibility(View.GONE);
//                   }
//               }else{
//                   coloranimationview.setVisibility(View.GONE);
//                   colorargbg.setVisibility(View.VISIBLE);
//               }

            //控制返回顶部按钮
            if (i <= -appBarLayout.getTotalScrollRange()&&isHidereturnTop){
                returnTop.setVisibility(View.VISIBLE);
            }else{
                returnTop.setVisibility(View.GONE);
            }
           }
       });

        header_fragment_topic_hot_rv.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), 2));
        header_fragment_topic_hot_rv.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dipToPixel(R.dimen.dp_8), false));
        header_fragment_topic_hot_rv.setNestedScrollingEnabled(false);//禁止滑动
        hotTopicTwoAdapter=new HotTopicTwoAdapter290(getActivity());
        header_fragment_topic_hot_rv.setAdapter(hotTopicTwoAdapter);
        hotTopicTwoAdapter.setOnItemClickListener(new HotTopicTwoAdapter290.OnItemClickListener() {
            @Override
            public void onItemClick(int position, HomePage.Banner data) {
                Utils.getJumpType(getActivity(),data.getJumptype(),data.getAdvertisementlink(),data.getNeedlogin(),data.getAdvertisemenid());
            }
        });
        //中间热区域广告第3部分（底部）
        header_fragment_topic_hot_grid.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), 3));
        header_fragment_topic_hot_grid.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dipToPixel(R.dimen.dp_8), false));
        header_fragment_topic_hot_grid.setNestedScrollingEnabled(false);//禁止滑动
        hotTopicThreeAdapter=new HotTopicThreeAdapter290(getActivity());
        header_fragment_topic_hot_grid.setAdapter(hotTopicThreeAdapter);
        hotTopicThreeAdapter.setOnItemClickListener(new HotTopicThreeAdapter290.OnItemClickListener() {
            @Override
            public void onItemClick(int position, HomePage.Banner data) {
                Utils.getJumpType(getActivity(),data.getJumptype(),data.getAdvertisementlink(),data.getNeedlogin(),data.getAdvertisemenid());
            }
        });


        //精选 人气
        big_brand_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homePage.getTopicListOne()!=null&&homePage.getTopicListOne().size() > 0) {
                    doJgqClick(homePage.getTopicListOne().get(0));
                }
            }
        });

        renqi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homePage.getTopicListThree()!=null&&homePage.getTopicListThree().size() > 0) {
                    doJgqClick(homePage.getTopicListThree().get(0));
                }
            }
        });
        //首页轮播图适配 宽高比例是355:132
        header_fragment_one_new_banner_layout.setCornerRadius(Utils.dipToPixel(R.dimen.dp_10));
        RelativeLayout.LayoutParams linearParamsOne =(RelativeLayout.LayoutParams)header_fragment_one_new_banner_layout.getLayoutParams();
        linearParamsOne.width=width-Utils.dipToPixel(R.dimen.dp_20);
        linearParamsOne.height=width*132/355-Utils.dipToPixel(R.dimen.dp_8);
        header_fragment_one_new_banner_layout.setLayoutParams(linearParamsOne);


        //金刚区设置
        //金刚区
        slide_indicator_point.setPadding(0, 0, 0, 0);
        slide_indicator_point.setThumbOffset(0);
        jingang_recycler01.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        jingang_recycler02.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        jingang_recycler01.setNestedScrollingEnabled(false);
        jingang_recycler02.setNestedScrollingEnabled(false);

        //新金刚区
        progress_bar.setPadding(0, 0, 0, 0);
        progress_bar.setThumbOffset(0);

        //验货直播
        mainRoomListAdapter=new MainRoomAdapter(getActivity(),0);
        header_fragment_room_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        header_fragment_room_recycler.setNestedScrollingEnabled(false);//禁止滑动
        header_fragment_room_recycler.setAdapter(mainRoomListAdapter);

        //热销榜单
        hotsell_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        hotsell_recycler.setNestedScrollingEnabled(false);
        hotSellAdapter =new HotSellAdapter(getActivity(),(width-Utils.dipToPixel(R.dimen.dp_60))/3);
        hotsell_recycler.setAdapter(hotSellAdapter);
    }

    //设置颜色
//    public void setViewPageColor(ViewPager viewPager,ViewPager.OnPageChangeListener onPageChangeListener,int count,int [] colors){
//            coloranimationview.setmViewPager(viewPager,count,colors);
//            coloranimationview.setOnPageChangeListener(onPageChangeListener);
//    }


    @Override
    public void onCustomized() {
        if (NetStateUtils.isNetworkConnected(getActivity())){
            main_network_mask.setVisibility(View.GONE);
            showDialog();
            //获取首页接口
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "HomePageInit", HttpCommon.HomePageInit);

            //首页精选的右侧小广告
            paramMap.clear();
            paramMap.put("position", "3");
            NetworkRequest.getInstance().POST(handler, paramMap, "AdvertisingFragmentMain", HttpCommon.GetScreen);
            // 滚动消息
            if (DateStorage.getLoginStatus()){
                //获取token
                if (DateStorage.getMyToken().equals("")||DateStorage.getMicroAppId().equals("")||DateStorage.getLittleAppId().equals("")){
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "LoginToken", HttpCommon.LoginToken);
                }
                paramMap.clear();
                paramMap.put("userid",login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "RollingMessage", HttpCommon.RollingMessage);
                getUnreadNum();
            }
            // 分享模板
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "Template", HttpCommon.Template);
        }else{
            toast(getResources().getString(R.string.net_work_unconnect));
            main_network_mask.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&currPos==0&&MainActivity.currentPos==0){
            loginRefreshFlag=false;
            onCustomized();
        }
    }

    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            loginRefreshFlag=true;
        }

//        if (coloranimationview!=null&&message.what==LogicActions.ChangeViewPagerCurrentColorSuccess&&message.arg1==0){
//          //没活动颜色固定 有活动颜色变化
//          if (!isTopBg){
//           coloranimationview.setBackgroundResource(R.drawable.top_gradient_bg);
//          }else{
//           coloranimationview.setChangeBackgroundColor();
//          }
//      }else{
//          if (!Variable.isStartChangeBgColor) {
//              coloranimationview.setBackgroundResource(R.drawable.top_gradient_bg);
//          }
//      }

      if (message.what==LogicActions.HideSlidingMenuSuccess){
          HideslidingMenu(message.arg1);
      }

        // 接收改变状态广播
        if (message.what == LogicActions.UpdateNewsDetailsSuccess) {
            if (DateStorage.getLoginStatus()){
                getUnreadNum();
            }else{
                fragment_one_message_num.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        //获取token 保存本地
        if (message.what == LogicActions.LoginTokenSuccess) {
            LoginToken loginToken= (LoginToken) message.obj;
            DateStorage.setMyToken(loginToken.getToken());
            DateStorage.setLittleAppId(loginToken.getMicroId());
            DateStorage.setMicroAppId(loginToken.getWechatMicroId());
        }

        if (message.what == LogicActions.TemplateSuccess) {
            Template template = (Template) message.obj;
            DateStorage.setTemplate(template);
        }

        // 获取上级客服
        if (message.what == LogicActions.GetUpServiceSuccess) {
            Service service = (Service) message.obj;
            if (Objects.equals(service.getWxcode(), "")) {
                toast("暂无客服");
            } else {
                ServiceDialog dialog = new ServiceDialog(getActivity());
                dialog.showDialog(service.getWxcode(), service.getWxqrcode());
            }
        }
        // 获取未读消息
        if (message.what == LogicActions.GetUnreadSuccess) {
            int unread = Integer.parseInt(message.obj + "");
            if (unread >= 1) {
                fragment_one_message_num.setVisibility(View.VISIBLE);
            } else {
                fragment_one_message_num.setVisibility(View.GONE);
            }
        }
        if (message.what == LogicActions.AdvertisingFragmentMainSuccess) {
            //小广告
            advertising = (Advertising) message.obj;
            if (advertising.getIsexit().equals("1")){
                sliding_adv_layout.setVisibility(View.VISIBLE);
                isHasSlidingMenu=1;
                Utils.displayImage(getActivity(), advertising.getAdvimg(), sliding_adv_img);
            }else{
                isHasSlidingMenu=0;
                sliding_adv_layout.setVisibility(View.GONE);
            }
        }


        //合并接口统一加载首页
        if (message.what == LogicActions.HomePageInitSuccess) {
            homePage = (HomePage) message.obj;
            //搜索背景
            if (homePage.getSearchBgList()!=null&&homePage.getSearchBgList().size()>0){
                isTopBg=false;
                loadMorePtrFrame.setBackgroundResource(R.drawable.top_gradient_bg);
                loadViewnew.setTextColor(Color.WHITE);
                onframgent_part01_bg_layout.setVisibility(View.VISIBLE);
                fragment_one_message_img.setImageResource(R.mipmap.fragment_one_message_white);
                Utils.displayImage(getActivity(), homePage.getSearchBgList().get(0).getAdvertisementpic(), onframgent_part01_Bg);
            }else{
                isTopBg=true;
                loadMorePtrFrame.setBackgroundColor(Color.parseColor("#ffffff"));
                loadViewnew.setTextColor(Color.BLACK);
                fragment_one_message_img.setImageResource(R.mipmap.fragment_one_message_common);
                onframgent_part01_bg_layout.setVisibility(View.GONE);
            }

            //轮播图广告背景
            if (homePage.getBannerBgList()!=null&&homePage.getBannerBgList().size()>0){
                onframgent_part02_Bg.setVisibility(View.VISIBLE);
                part02HasBg=true;
//                ArcBack.setVisibility(View.GONE);
                Utils.displayImage(getActivity(), homePage.getBannerBgList().get(0).getAdvertisementpic(), onframgent_part02_Bg);
            }else{
                part02HasBg=false;
//                ArcBack.setVisibility(View.VISIBLE);
                onframgent_part02_Bg.setVisibility(View.GONE);
            }

            //填充超级分类数据
//            if (homePage.getCategoryOneList()!=null&&homePage.getCategoryOneList().size() > 0) {
//                magic(homePage.getCategoryOneList());
//            }
            //填充轮播图数据
            if (homePage.getBannerList()!=null&&homePage.getBannerList().size() > 0) {
                header_fragment_one_new_banner_layout.setVisibility(View.VISIBLE);
                bannerImage(homePage.getBannerList());
            } else {
                header_fragment_one_new_banner_layout.setVisibility(View.GONE);
            }
            //轮播图底部广告
            if (homePage.getAdvList()!=null&&homePage.getAdvList().size() > 0) {
                onframgent_part03.setVisibility(View.VISIBLE);
                advImage(homePage.getAdvList());
            } else {
                onframgent_part03.setVisibility(View.GONE);
            }

            //填充新金刚区数据  有新金刚区就不显示旧金刚区
            if (homePage.getJGQSortList()!=null&&homePage.getJGQSortList().size()>0) {
                //新金刚区背景
                progress_bar.setVisibility(View.VISIBLE);
                if (homePage.getJGQBgList()!=null&&homePage.getJGQBgList().size()>0){
                    isJGQBg=true;
                    onframgent_part05_Bg.setVisibility(View.VISIBLE);
                    jgq_new_magicindicator_layout.setBackgroundResource(R.drawable.personal_grid_bg);
                    Utils.displayImage(getActivity(), homePage.getJGQBgList().get(0).getAdvertisementpic(), onframgent_part05_Bg);
                }else{
                    jgq_new_magicindicator_layout.setBackgroundResource(0);
                    onframgent_part05_Bg.setVisibility(View.GONE);
                }
                onframgent_part05.setVisibility(View.VISIBLE);
                onframgent_part04.setVisibility(View.GONE);
                JGQNewMagic(homePage.getJGQSortList());
            }else{
                //旧金刚区背景
                if (homePage.getJGQBgList()!=null&&homePage.getJGQBgList().size()>0){
                    isJGQBg=true;
                    onframgent_part04_Bg.setVisibility(View.VISIBLE);
                    Utils.displayImage(getActivity(), homePage.getJGQBgList().get(0).getAdvertisementpic(), onframgent_part04_Bg);
                }else{
                    onframgent_part04_Bg.setVisibility(View.GONE);
                }
                onframgent_part05.setVisibility(View.GONE);
                onframgent_part04.setVisibility(View.VISIBLE);
                //金刚区数据一
                if (homePage.getJGQListOne()!=null&&homePage.getJGQListOne().size() > 0) {
                    jingang_recycler01.setVisibility(View.VISIBLE);
                    doJQG(homePage.getJGQListOne(), 1);
                } else {
                    jingang_recycler01.setVisibility(View.GONE);
                }
                //金刚区数据二
                if (homePage.getJGQListTwo()!=null&&homePage.getJGQListTwo().size() > 0) {
                    jingang_recycler02.setVisibility(View.VISIBLE);
                    doJQG(homePage.getJGQListTwo(), 2);
                } else {
                    jingang_recycler02.setVisibility(View.GONE);
                }
            }



            //中间热区广告背景图片
            if (homePage.getHotBgList()!=null&&homePage.getHotBgList().size() > 0) {
                int pubHeight=Utils.dipToPixel(R.dimen.dp_8);
                int mbannerheight=0;
                Double row1=0.0;
                Double row2=0.0;
                Double height1=0.0;
                Double height2=0.0;
                int height0=0;
                int partNum=0;
                int realWidth=width;
                // 请求中间热区第1块广告
                if (homePage.getHotPartOneList()!=null&&homePage.getHotPartOneList().size() > 0) {
                    headerFragmentOneNewSixBanner.setVisibility(View.VISIBLE);
                    OneFragmentBanner(homePage.getHotPartOneList());
                    //活动尺寸（比较大）每日推荐屏幕适配 宽高比例355:100
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) headerFragmentOneNewSixBanner.getLayoutParams();
                    int disWidth = Utils.dipToPixel(R.dimen.dp_20);//扣除边距
                    realWidth = width - disWidth;
                    linearParams.width = realWidth;
                    linearParams.height = realWidth * 100/355;
                    headerFragmentOneNewSixBanner.setLayoutParams(linearParams);
                    mbannerheight=linearParams.height;
                    height0=mbannerheight+Utils.dipToPixel(R.dimen.dp_10);
                } else {
                    headerFragmentOneNewSixBanner.setVisibility(View.GONE);
                }
                // 请求中间热区第2块广告
                if (homePage.getHotPartTwoList()!=null&&homePage.getHotPartTwoList().size()> 0) {
                    header_fragment_topic_hot_rv_layout.setVisibility(View.VISIBLE);
                    row1=Math.ceil((double) homePage.getHotPartTwoList().size()/2);
                    hotTopicTwoAdapter.setNewData(homePage.getHotPartTwoList());
                    partNum=1;
                    height1= ((realWidth-pubHeight)/2*0.413*row1)+(row1-1)*pubHeight+Utils.dipToPixel(R.dimen.dp_10);
                } else {
                    header_fragment_topic_hot_rv_layout.setVisibility(View.GONE);
                }
                // 请求中间热区第3块广告
                if (homePage.getHotPartTthreeList()!=null&&homePage.getHotPartTthreeList().size()>0) {
                    header_fragment_topic_hot_grid_layout.setVisibility(View.VISIBLE);
                    row2=Math.ceil((double)homePage.getHotPartTthreeList().size()/3);
                    hotTopicThreeAdapter.setNewData(homePage.getHotPartTthreeList());
                    partNum=1;
                    height2= ((realWidth-2*pubHeight)/3*1.2*row2)+(row2-1)*pubHeight+pubHeight;
                }else {
                    header_fragment_topic_hot_grid_layout.setVisibility(View.GONE);
                }
                RelativeLayout.LayoutParams linearParamsMargin = (RelativeLayout.LayoutParams)topic_hot_layout_ll.getLayoutParams();
                linearParamsMargin.setMargins(Utils.dipToPixel(R.dimen.dp_10),Utils.dipToPixel(R.dimen.dp_10),Utils.dipToPixel(R.dimen.dp_10),0);
                topic_hot_layout_ll.setLayoutParams(linearParamsMargin);
                topic_hot_layout_bg_iv.setVisibility(View.VISIBLE);
                //计算背景高度  宽高比  宽以手机宽度为主
                RelativeLayout.LayoutParams linearParamsbg = (RelativeLayout.LayoutParams) topic_hot_layout_bg_iv.getLayoutParams();
                linearParamsbg.width=width;
                linearParamsbg.height= (int)(height0+height1+height2)+partNum*(Utils.dipToPixel(R.dimen.dp_10));
                topic_hot_layout_bg_iv.setLayoutParams(linearParamsbg);
                Utils.displayImage(getActivity(), homePage.getHotBgList().get(0).getAdvertisementpic(), topic_hot_layout_bg_iv);
            } else {
                RelativeLayout.LayoutParams linearParamsMargin = (RelativeLayout.LayoutParams)topic_hot_layout_ll.getLayoutParams();
                linearParamsMargin.setMargins(Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10),0);
                topic_hot_layout_ll.setLayoutParams(linearParamsMargin);
                topic_hot_layout_bg_iv.setVisibility(View.GONE);
                header_fragment_topic_hot_grid_layout.setVisibility(View.GONE);
                header_fragment_topic_hot_rv_layout.setVisibility(View.GONE);
                headerFragmentOneNewSixBanner.setVisibility(View.VISIBLE);
                //活动尺寸（比较小）每日推荐屏幕适配 宽高比例361:105
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) headerFragmentOneNewSixBanner.getLayoutParams();
                int disWidth = Utils.dipToPixel(R.dimen.dp_16);//扣除边距
                int realWidth = width - disWidth;
                linearParams.width = realWidth;
                linearParams.height = realWidth * 96 / 340;
                headerFragmentOneNewSixBanner.setLayoutParams(linearParams);
                //0元购活动热区数据填充
                if (homePage.getMiddleBannerList()!=null&&homePage.getMiddleBannerList().size() > 0) {
                    headerFragmentOneNewSixBanner.setVisibility(View.VISIBLE);
                    OneFragmentBanner(homePage.getMiddleBannerList());
                } else {
                    headerFragmentOneNewSixBanner.setVisibility(View.GONE);
                }
            }

            //限时抢购数据填充
            ArrayList<HomePage.LimitTimeList> TemplimitTimeParentList;
            TemplimitTimeParentList = homePage.getLimitTimeParentList();
            if (TemplimitTimeParentList!=null&&TemplimitTimeParentList.size() > 2) {
                limit_buy_content.setVisibility(View.VISIBLE);
                // 填充限时抢购
                TimerView.stop();
                limitBuyMagic(TemplimitTimeParentList);
                CountDown(Utils.getCurrentDate("yyyy-MM-dd HH:mm:ss"),TemplimitTimeParentList.get(1).getTime()+": 00");
            } else {
                limit_buy_content.setVisibility(View.GONE);
            }

            //精选 人气宝贝
            if (homePage.getTopicListOne()!=null&&homePage.getTopicListOne().size() > 0) {
                select_one_title.setText(homePage.getTopicListOne().get(0).getName());
                if (limitScrollerView01!=null) {
                    limitScrollerView01.stop();
                    if (marqueeAdapter01 != null && marqueeAdapter01.getCount() > 0) {
                        marqueeAdapter01.setData(homePage.getTopicListOne());
                    } else {
                        marqueeAdapter01 = new OneFragmentVerticalBannerAdapter(getActivity(), homePage.getTopicListOne());
                        limitScrollerView01.setAdapter(marqueeAdapter01);
                        }
                    limitScrollerView01.start();
                }
            }
            if (homePage.getTopicListTwo()!=null&&homePage.getTopicListTwo().size() > 0) {
                select_one_subtitle.setText(homePage.getTopicListTwo().get(0).getName());
                if (limitScrollerView02!=null) {
                    limitScrollerView02.stop();
                    if (marqueeAdapter02 != null && marqueeAdapter02.getCount() > 0) {
                        marqueeAdapter02.setData(homePage.getTopicListTwo());
                    } else {
                        marqueeAdapter02 = new OneFragmentVerticalBannerAdapter(getActivity(), homePage.getTopicListTwo());
                        limitScrollerView02.setAdapter(marqueeAdapter02);
                    }
                    VBhandler.postDelayed(() -> {
                        try {
                            limitScrollerView02.start();
                        } catch (Exception e) {
                        }
                    }, 4000);
                }
            }
            if (homePage.getTopicListThree()!=null&&homePage.getTopicListThree().size() > 0) {
                select_two_title.setText(homePage.getTopicListThree().get(0).getName());
                if (limitScrollerView03!=null) {
                    limitScrollerView03.stop();
                if (marqueeAdapter03 != null && marqueeAdapter03.getCount() > 0) {
                    marqueeAdapter03.setData(homePage.getTopicListThree());
                } else {
                        marqueeAdapter03 = new OneFragmentVerticalBannerAdapter(getActivity(), homePage.getTopicListThree());
                        limitScrollerView03.setAdapter(marqueeAdapter03);
                    }
                    limitScrollerView03.start();
                }
            }
            if (homePage.getTopicListFour()!=null&&homePage.getTopicListFour().size() > 0) {
                select_two_subtitle.setText(homePage.getTopicListFour().get(0).getName());
                if (limitScrollerView04!=null) {
                    limitScrollerView04.stop();
                if (marqueeAdapter04 != null && marqueeAdapter04.getCount() > 0) {
                    marqueeAdapter04.setData(homePage.getTopicListFour());
                } else {
                    marqueeAdapter04 = new OneFragmentVerticalBannerAdapter(getActivity(), homePage.getTopicListFour());
                        limitScrollerView04.setAdapter(marqueeAdapter04);
                    }
                    VBhandler.postDelayed(() -> {
                        try {
                         limitScrollerView04.start();
                        } catch (Exception e) {
                        }
                    }, 4000);
                }
            }
            //填充bottomtab的数据
            if (homePage.getNavList()!=null&&homePage.getNavList().size()>0) {
                BottomNavMagic(homePage.getNavList());
            }
            //填充热销榜单的数据
            if (homePage.getHotSellList()!=null&&homePage.getHotSellList().size()>0) {
                hotsell_recycler_layout.setVisibility(View.VISIBLE);
                hotSellAdapter.setNewData(homePage.getHotSellList());
            }else{
               hotsell_recycler_layout.setVisibility(View.GONE);
            }

            //验货直播数据
            if (homePage.getRoomList()!=null&&homePage.getRoomList().size()>0) {
                room_inspect_layout.setVisibility(View.VISIBLE);
                mainRoomListAdapter.setNewData(homePage.getRoomList());
            }else{
                room_inspect_layout.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(homePage.getHeadUrl())) {
                Utils.displayImage(getActivity(), homePage.getHeadUrl(), fragment_one_left_iv);
            }
            if(!TextUtils.isEmpty(homePage.getUserLevel())) {
                tv_one_left_name.setText(homePage.getUserLevel());
            }

            dismissDialog();
        }
        loadMorePtrFrame.refreshComplete();
        initAppbar();
    }
    //防止AppBarLayout头部滑动不了，需要在数据加载出来后调用该方法
    public void initAppbar() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) AppBar_Layout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return true;
            }
        });
    }

    @OnClick({R.id.fragment_one_search, R.id.fragment_one_service, R.id.more_sort_layout,R.id.fragment_one_message,R.id.return_top,R.id.sliding_adv_close,R.id.sliding_adv_img,R.id.hotsell_recycler_layout,R.id.select_room_more_layout})
    public void onViewClicked(View view) {
       if (Variable.FastClickTime()){
           return;
       }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.sliding_adv_close:
                //隐藏
                isHasSlidingMenu=0;
                sliding_adv_layout.setVisibility(View.GONE);
                break;
            case R.id.sliding_adv_img:
                //点击跳转
                if (!DateStorage.getLoginStatus()) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                } else {
                    switch (advertising.getJumptype()) {
                        case "1":
                            startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", advertising.getJumpvaue()).putExtra("source", "all").putExtra("sourceId",""));
                            break;
                        case "2":
                            startActivity(new Intent(getActivity(), OnlyUrlWebViewActivity.class).putExtra(Variable.webUrl, advertising.getJumpvaue()));
                            break;
                        case "3":
                            startActivity(new Intent(getActivity(), AliAuthWebViewActivity.class).putExtra(Variable.webUrl, advertising.getJumpvaue()).putExtra("isTitle", true));
                            break;
                        case "4":
                            startActivity(new Intent(getActivity(), NewActivity.class).putExtra("topicId", advertising.getJumpvaue()).putExtra("labelType" , "09"));
                            break;
                        //官方活动
                        case "11":
                            // 请求活动链接
                            if (DateStorage.getLoginStatus()) {
                                UserInfo login = DateStorage.getInformation();
                                if (MainActivity.mainActivity != null) {
                                    MainActivity.mainActivity.getActivityChain(login.getUserid(), advertising.getJumpvaue());
                                }
                            } else {
                                intent = new Intent(getActivity(), LoginActivity.class);
                            }

                            break;
                        case "12":
                            intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                            intent.putExtra(Variable.webUrl, advertising.getJumpvaue());
                            intent.putExtra("isTitle", false);
                            intent.putExtra("noGoTaoBaoH5", 12);
                            break;
                        case "14":
                            if (advertising.getJumpvaue()!=null) {
                                Object bannerObject = JSON.parseObject(advertising.getJumpvaue(), JDBanner.class);
                                JDBanner banner = (JDBanner) bannerObject;
                                int platformType=1;
                                switch (banner.getCpsType()){
                                    case "pdd":
                                        platformType=1;
                                        break;
                                    case "jd":
                                        platformType=2;
                                        break;
                                    case "wph":
                                        platformType=3;
                                        break;
                                    case "sn":
                                        platformType=5;
                                        break;
                                    case "kl":
                                        platformType=6;
                                        break;
                                }
                                Utils.getJumpTypeForMorePl(getActivity(),banner.getClickType(),banner.getClickVaule(),platformType,banner.getNeedLogin(),banner.getName(),banner.getSysParam());
                            }
                            break;
                    }
                }
                break;
            case R.id.fragment_one_search:// 搜索
                  intent = new Intent(getActivity(), MorePlSearchNewActivity.class);
                  intent.putExtra("isCheck", false);
                  intent.putExtra("platType", "0");
                break;
            case R.id.fragment_one_service:
                //赚萌币
                if (DateStorage.getLoginStatus()) {
                    intent = new Intent(getActivity(), MyTaskFragmentActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
//                 intent = new Intent(getActivity(), OneKeyChainActivity.class);
                break;
            case R.id.fragment_one_message:// 消息
                if (DateStorage.getLoginStatus()) {
                    intent = new Intent(getActivity(), NewsActivity.class);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.more_sort_layout:
              intent=new Intent(getActivity(), SortThreeFragmentActivity.class);
                break;
            case R.id.return_top:
                scrollToTop();
                break;
            case R.id.hotsell_recycler_layout:
                //跳转热销榜单
                intent = new Intent(getActivity(), HotSellFragmentActivity.class);
                break;
            case R.id.select_room_more_layout:
                //跳验货直播列表
                intent = new Intent(getActivity(), LiveInspectListActivity.class);
                intent.putExtra("name", "验货直播");
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    //获取未读消息
    private void getUnreadNum(){
        // 获取未读消息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetUnread", HttpCommon.GetUnread);
    }

    /**
     * 设置导航和viewpager
     */
    private void magic(final ArrayList<HomePage.CategoryOne> list) {
        fragmentOneMagic.setBackgroundColor(Color.TRANSPARENT);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
//        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleViewNew simplePagerTitleView = new ScaleTransitionPagerTitleViewNew(context);
                simplePagerTitleView.setText(list.get(index).getShopclassname());
                if (part02HasBg){
                    simplePagerTitleView.setNormalColor(Color.parseColor("#FFFEFE"));
                    simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
                }else{
                    simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                    simplePagerTitleView.setSelectedColor(Color.parseColor("#FF0134"));
                }
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currPos=index;
                        if (index!=0){
                            //切换其他选项卡 头部禁止滑动 该隐藏隐藏
                            canScrollUp=false;
                            isHidereturnTop=false;
                            returnTop.setVisibility(View.GONE);
                            sliding_adv_layout.setVisibility(View.GONE);
//                            onframgent_part02_Bg.setVisibility(View.GONE);
                            header_fragment_one_new_banner_layout.setVisibility(View.GONE);
                            onefragment_head_content.setVisibility(View.GONE);
                            bottom_navigate_magic.setVisibility(View.GONE);
                            navigate_content.setVisibility(View.GONE);
                            //禁止applayout滑动
                            View mAppBarChildAt = AppBar_Layout.getChildAt(0);
                            AppBarLayout.LayoutParams  mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
                            mAppBarParams.setScrollFlags(0);
                            fragment_one_content_layout.setVisibility(View.VISIBLE);
                            if (list.get(index).getShopclassname().equals("猜你喜欢")) {
                                if (mainBottomFragment290 == null) {
                                    mainBottomFragment290 = MainYouLikeFragment.getInstance("");
                                }else{
                                    mainBottomFragment290.onCustomized();
                                }
                                switchFragment(mainBottomFragment290);
                            }else{
                                if (oneFragmentOther290==null) {
                                    oneFragmentOther290 = oneFragmentOther290.getInstance(Integer.parseInt(list.get(index).getShopclassone()));
                                }else{
                                    oneFragmentOther290.initData(Integer.parseInt(list.get(index).getShopclassone()));
                                }
                                switchFragment(oneFragmentOther290);
                            }
//                            Variable.noChangeBg=true;
//                            coloranimationview.setBackgroundResource(R.drawable.top_gradient_bg);
                        }else{
                            canScrollUp=true;
                            isHidereturnTop=true;
                            if (isHasSlidingMenu==1){
                                sliding_adv_layout.setVisibility(View.VISIBLE);
                            }else{
                                sliding_adv_layout.setVisibility(View.GONE);
                            }
//                            if (!isTopBg){
//                                onframgent_part01_Bg.setVisibility(View.VISIBLE);
//                                onframgent_part02_Bg.setVisibility(View.VISIBLE);
//                            }
                            header_fragment_one_new_banner_layout.setVisibility(View.VISIBLE);
                            onefragment_head_content.setVisibility(View.VISIBLE);
                            bottom_navigate_magic.setVisibility(View.VISIBLE);
                            navigate_content.setVisibility(View.VISIBLE);
                            //重新启用滑动
                            View mAppBarChildAt = AppBar_Layout.getChildAt(0);
                            AppBarLayout.LayoutParams  mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
                            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
                            mAppBarChildAt.setLayoutParams(mAppBarParams);
                            fragment_one_content_layout.setVisibility(View.GONE);
                            Variable.noChangeBg=false;
//                            if (Variable.isStartChangeBgColor) {
//                                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ChangeViewPagerCurrentColor"), false, 0);
//                            }
                        }

                        //上一个控件自然沉降状态
                        fragmentOneMagic.onPageScrolled(lastPos,0f,0);
                        fragmentOneMagic.onPageSelected(index);
                        fragmentOneMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                        //当前控件释放状态（空闲状态）
                        fragmentOneMagic.onPageScrolled(index,0f,0);
                        fragmentOneMagic.onPageSelected(index);
                        fragmentOneMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_SETTLING);
                        lastPos=index;

                        //刷新首页
                        if (loginRefreshFlag&&currPos==0){
                            loginRefreshFlag=false;
                            onCustomized();
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_18));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(5);
                if (part02HasBg) {
                    indicator.setColors(Color.parseColor("#FFFFFF"));
                }else{
                    indicator.setColors(Color.parseColor("#FF0134"));
                }
                return indicator;
            }
        });
        fragmentOneMagic.setNavigator(commonNavigator);
    }

    /**
     * 轮播图事件
     * @param list
     */
    private void bannerImage(final ArrayList<HomePage.Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }

//        int [] colors=new int[list.size()];
//        for (int i =0; i <list.size(); i++) {
//            String itemColor=list.get(i).getLBgClolor();
//            if (itemColor.equals("")||itemColor.contains("#")){
//                try{
//                    colors[i]=Color.parseColor(itemColor);
//                }catch (Exception e){
//                    colors[i]=Color.parseColor("#ee2532");
//                }
//            }else{
//                try{
//                    colors[i]=Color.parseColor("#"+itemColor);
//                }catch (Exception e){
//                    colors[i]=Color.parseColor("#ee2532");
//                }
//
//            }
//        }
        headerFragmentOneNewBanner.setPages(() -> new BannerMainAdapter(), netWorkImage);
        totalsize.setText(netWorkImage.size()+"");
//        indicator_view_layout.removeAllViews();
//        mPointViews.clear();
//        page_indicatorId= new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
//        if (netWorkImage!=null&&netWorkImage.size()>0) {
//            for (int count = 0; count < netWorkImage.size(); count++) {
//                // 翻页指示的点
//                ImageView pointView = new ImageView(getContext());
//                pointView.setPadding(5, 0, 5, 0);
//                if (mPointViews.isEmpty())
//                    pointView.setImageResource(page_indicatorId[1]);
//                else
//                    pointView.setImageResource(page_indicatorId[0]);
//                mPointViews.add(pointView);
//                indicator_view_layout.addView(pointView);
//            }
//        }

        headerFragmentOneNewBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
               currentpos.setText(position+1+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        headerFragmentOneNewBanner.setPointViewVisible(false);
//        setViewPageColor(headerFragmentOneNewBanner.getViewPager(),headerFragmentOneNewBanner.getOnPageChangeListener(),list.size(),colors);

        if (netWorkImage.size() > 1) {
            lunbo_dotlayout.setVisibility(View.VISIBLE);
            headerFragmentOneNewBanner.startTurning(4000);
            headerFragmentOneNewBanner.setCanLoop(true);
        } else {
            lunbo_dotlayout.setVisibility(View.GONE);
            headerFragmentOneNewBanner.setCanLoop(false);
        }
        headerFragmentOneNewBanner.setOnItemClickListener(position -> {
            Utils.getJumpType(getActivity(),list.get(position).getJumptype(),list.get(position).getAdvertisementlink(),list.get(position).getNeedlogin(),list.get(position).getAdvertisemenid());
        });
    }

    /**
     * 轮播图底部广告事件
     * @param advlist
     */
    private void advImage(final ArrayList<HomePage.Banner> advlist) {
        Utils.displayImage(getActivity(), advlist.get(0).getAdvertisementpic(), onefragment_adv01);
        onefragment_adv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getJumpType(getActivity(),advlist.get(0).getJumptype(),advlist.get(0).getAdvertisementlink(),advlist.get(0).getNeedlogin(),advlist.get(0).getAdvertisemenid());
            }
        });
    }

    /**
     * 金刚区数据填充
     * @param jgqlist
     */
    private void doJQG(final ArrayList<HomePage.JGQAppIcon> jgqlist,int pos) {
       if (pos==1){
           // 初始化adapter
           jinGangQuIconAdapterOne = new JinGangQuIconAdapter(getActivity(),width/5,isJGQBg);
           jingang_recycler01.setAdapter(jinGangQuIconAdapterOne);
           jinGangQuIconAdapterOne.setNewData(jgqlist);
           jinGangQuIconAdapterOne.setOnItemClickListener(new JinGangQuIconAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(int position, HomePage.JGQAppIcon data) {
                   if (Variable.FastClickTime()) {
                      return;
                   }
                   doJgqClick(data);
               }
           });

           jingang_layout_hs.setScrollViewListener(new StrongerHorizontalScrollView.ScrollViewListener() {
               @Override
               public void onScrollChanged(StrongerHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
                   // 显示区域的高度。
                   int extent = scrollView.getWidth();
                   //整体的高度，注意是整体，包括在显示区域之外的。
                   int range = width/5*(jgqlist.size());
                   //已经向下滚动的距离，为0时表示已处于顶部。
                   int offset = scrollView.getScrollX();
                   //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
//                   GradientDrawable gradientDrawable =(GradientDrawable) slide_indicator_point.getThumb();
//                   //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
//                   gradientDrawable.setSize(Utils.dipToPixel(R.dimen.dp_14),Utils.dipToPixel(R.dimen.dp_4));
                   //设置可滚动区域
                   slide_indicator_point.setMax((int)(range-extent));
                   if (x==0){
                       slide_indicator_point.setProgress(0);
                   }else if (x>0){
                       slide_indicator_point.setProgress(offset);
                   }else if (x<0){
                       slide_indicator_point.setProgress(offset);
                   }
               }

           });
       }else{
           jinGangQuIconAdapterTwo= new JinGangQuIconAdapter(getActivity(),width/5,isJGQBg);
           jingang_recycler02.setAdapter(jinGangQuIconAdapterTwo);
           jinGangQuIconAdapterTwo.setNewData(jgqlist);
           jinGangQuIconAdapterTwo.setOnItemClickListener(new JinGangQuIconAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(int position, HomePage.JGQAppIcon data) {
                   if (Variable.FastClickTime()) {
                       return;
                   }
                       doJgqClick(data);
               }
           });
       }
        slide_indicator_point.setVisibility(View.VISIBLE);
    }

    //零元购活动数据
    private void OneFragmentBanner(ArrayList<HomePage.Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }
        headerFragmentOneNewSixBanner.setPages(() -> new BannerMAdapter(width), netWorkImage);
        headerFragmentOneNewSixBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        if (netWorkImage.size() > 1) {
            headerFragmentOneNewSixBanner.setPointViewVisible(true);
            headerFragmentOneNewSixBanner.startTurning(4000);
            headerFragmentOneNewSixBanner.setCanLoop(true);
        } else {
            headerFragmentOneNewSixBanner.setPointViewVisible(false);
            headerFragmentOneNewSixBanner.setCanLoop(false);
        }
        headerFragmentOneNewSixBanner.setOnItemClickListener(position -> {
            Utils.getJumpType(getActivity(),list.get(position).getJumptype(),list.get(position).getAdvertisementlink(),list.get(position).getNeedlogin(),list.get(position).getAdvertisemenid());
        });
    }

    //限时抢购
    Timer timer;
    LimitTimerTask limitTimerTask;
    private int limitInterval=5000;
    private int viewPgerPosition=0;
    /**
     * TimerTask类是一个抽象类
     */
    public class LimitTimerTask extends TimerTask {
        @Override
        public void run() {
            Message msgMsg = msgReceivHandle.obtainMessage();
            msgMsg.what = 1;
            msgReceivHandle.sendMessage(msgMsg);
        }

    }
    private void doAutoPlayLimitBuyData(){
        if (limitTimerTask!=null){
            limitTimerTask.cancel();
            limitTimerTask=null;
        }
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
        timer=new Timer();
        limitTimerTask=new LimitTimerTask();
        //程序运行后立刻执行任务，每隔limitInterval执行一次
        timer.schedule(limitTimerTask, 5000, limitInterval);
    }

    private final Handler msgReceivHandle = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            try{
            if(limitbuy_viewpager!=null) {
                if (msg.what == 1) {//限时轮播
                    if (viewPgerPosition == 2) {
                        try{
                          limitbuy_viewpager.setCurrentItem(0);
                        }catch (Exception e){
                        }
                    } else {
                        try{
                         limitbuy_viewpager.setCurrentItem(viewPgerPosition + 1);
                        }catch (Exception e){
                        }
                    }
                }
            }
            }catch (Exception e){

            }
        }
    };

   private FragmentManager limitFragmentManager;
    private ArrayList<Fragment> limitfragments;
   private FragmentAdapter limitfragmentAdapter;
   private void limitBuyMagic(ArrayList<HomePage.LimitTimeList> templimitTimeParentList){
       limitbuy_viewpager.removeAllViews();
       // 初始化FragmentManager对象
       limitFragmentManager = getChildFragmentManager();
       // 初始化Fragment页面
       limitfragments = new ArrayList<>();
       for (int i = 0; i < templimitTimeParentList.size(); i++) {
         MainLimitBuyFragment  mainLimitBuyFragment = MainLimitBuyFragment.getInstance(templimitTimeParentList.get(i));
           limitfragments.add(mainLimitBuyFragment);
       }
       limitfragmentAdapter = new FragmentAdapter(limitFragmentManager, limitfragments);
       limitbuy_viewpager.setAdapter(limitfragmentAdapter);
       CommonNavigator commonNavigator = new CommonNavigator(getActivity());
       commonNavigator.setScrollPivotX(0.5f);
       commonNavigator.setAdjustMode(true);
       commonNavigator.setAdapter(new CommonNavigatorAdapter() {
           @Override
           public int getCount() {
               return templimitTimeParentList.size();
           }

           @Override
           public IPagerTitleView getTitleView(Context context, int index) {
               CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
               commonPagerTitleView.setContentView(R.layout.limit_main_magic);
               LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.limit_magic_layout);
               TextView limitMagicTime = (TextView) commonPagerTitleView.findViewById(R.id.limit_magic_time);
               TextView limitMagicName = (TextView) commonPagerTitleView.findViewById(R.id.limit_magic_name);
               limitMagicTime.setText(templimitTimeParentList.get(index).getTitle());
               limitMagicName.setText(templimitTimeParentList.get(index).getRemark());
               commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                   @Override
                   public void onSelected(int index, int totalCount) {
                       viewPgerPosition=index;
                       limitMagicLayout.setBackgroundColor(Color.parseColor("#FFF6E1"));
                       limitMagicTime.setTextColor(Color.parseColor("#4A392A"));
                       limitMagicName.setTextColor(Color.parseColor("#4A392A"));
                   }

                   @Override
                   public void onDeselected(int index, int totalCount) {
                       limitMagicLayout.setBackgroundColor(getResources().getColor(R.color.Transparent00000000));
                       limitMagicTime.setTextColor(Color.parseColor("#4A392A"));
                       limitMagicName.setTextColor(Color.parseColor("#4A392A"));
                   }

                   @Override
                   public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                   }

                   @Override
                   public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                   }
               });
               commonPagerTitleView.setOnClickListener(v -> {
                   if (limitbuy_viewpager!=null){
                       limitbuy_viewpager.setCurrentItem(index);
                   }
                   viewPgerPosition=index;
                   Intent intent = new Intent(getActivity(), LimitActivity.class);
                   intent.putExtra("time",templimitTimeParentList.get(index).getTime());
                   startActivity(intent);

               });
               return commonPagerTitleView;
           }

           @Override
           public IPagerIndicator getIndicator(Context context) {
               return null;
           }
       });
       limitbuy_magic.setNavigator(commonNavigator);
       ViewPagerHelper.bind(limitbuy_magic, limitbuy_viewpager);
       doAutoPlayLimitBuyData();
       limitbuy_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int i, float v, int i1) {

           }

           @Override
           public void onPageSelected(int i) {

           }

           @Override
           public void onPageScrollStateChanged(int i) {
               if (i==1){//手指触摸和开始滑动
                   if (timer!=null){
                       timer.cancel();
                   }
               }
               if (i==2||i==0){//手指离开
                   doAutoPlayLimitBuyData();
               }
           }
       });

   }



    //首页底部导航数据填充
    // Fragment页面
    private ArrayList<Fragment> fragments;
    // 声明Fragment对象
    private MainBottomFragment mainBottomFragment;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    private int position = 0;
    private  boolean ishasdata=false;
    private void BottomNavMagic(ArrayList<HomePage.BottomNavigation> list) {
         ishasdata=true;
        // 初始化FragmentManager对象
        fragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mainBottomFragment = MainBottomFragment.getInstance(list.get(i).getMaterial());
            fragments.add(mainBottomFragment);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        navigate_content.setAdapter(fragmentAdapter);
        navigate_content.setOffscreenPageLimit(list.size());
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.main_nav_magic);
                View split= commonPagerTitleView.findViewById(R.id.split);
                LinearLayout.LayoutParams linearParamssplit =(LinearLayout.LayoutParams)split.getLayoutParams();
                LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.nav_magic_layout);
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)limitMagicLayout.getLayoutParams();
                linearParams.width=(width-linearParamssplit.width)/4;
                limitMagicLayout.setLayoutParams(linearParams);
                if (index==list.size()-1){
                    split.setVisibility(View.GONE);
                }else{
                    split.setVisibility(View.VISIBLE);
                }
                TextView limitMagicTitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_magic_title);
                TextPaint tp=new TextPaint();
                tp= limitMagicTitle.getPaint();
                tp.setFakeBoldText(true);
                TextView limitMagicSubtitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_magic_subtitle);
                limitMagicTitle.setText(list.get(index).getTitle());
                limitMagicSubtitle.setText(list.get(index).getDesc());
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        limitMagicTitle.setTextColor(Color.parseColor("#000000"));
                        limitMagicSubtitle.setTextColor(Color.parseColor("#FFFFFF"));
                        limitMagicSubtitle.setBackgroundResource(R.drawable.bg_rect_black_05dp);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        limitMagicTitle.setTextColor(Color.parseColor("#6A6A6A"));
                        limitMagicSubtitle.setTextColor(Color.parseColor("#6A6A6A"));
                        limitMagicSubtitle.setBackgroundResource(0);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                if (navigate_content!=null){
                commonPagerTitleView.setOnClickListener(v -> navigate_content.setCurrentItem(index));
                }
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        bottom_navigate_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(bottom_navigate_magic, navigate_content);
    }



    //新金刚区填充数据
    private void JGQNewMagic(ArrayList<HomePage.JGQSort> list) {
        // 初始化FragmentManager对象
        fragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JGQSortFragment jgqSortFragment= JGQSortFragment.getInstance(list.get(i).getKeyjson());
            fragments.add(jgqSortFragment);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        jgq_new_content.setAdapter(fragmentAdapter);
        jgq_new_content.setOffscreenPageLimit(list.size());
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.jgq_nav_magic);
                View split= commonPagerTitleView.findViewById(R.id.split);
                RelativeLayout.LayoutParams linearParamssplit =(RelativeLayout.LayoutParams)split.getLayoutParams();
                LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.nav_jgq_layout);
                RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams)limitMagicLayout.getLayoutParams();
                linearParams.width=(width-Utils.dipToPixel(R.dimen.dp_20))/5;
                limitMagicLayout.setLayoutParams(linearParams);
                if (index==0){
                    split.setVisibility(View.GONE);
                }else{
                    split.setVisibility(View.GONE);
                }
                TextView limitMagicTitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_jgq_title);
                ImageView sortImg = commonPagerTitleView.findViewById(R.id.sort_img);
                limitMagicTitle.setText(list.get(index).getName());
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                         tp= limitMagicTitle.getPaint();
                         tp.setFakeBoldText(true);
                        limitMagicTitle.setTextColor(Color.parseColor("#151D23"));
                        limitMagicTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        sortImg.setBackgroundColor(Color.parseColor("#151D23"));
                        sortImg.setImageResource(0);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        tp= limitMagicTitle.getPaint();
                        tp.setFakeBoldText(false);
                        limitMagicTitle.setTextColor(Color.parseColor("#151D23"));
                        limitMagicTitle .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        sortImg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        sortImg.setImageResource(0);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                if (jgq_new_content!=null){
                    commonPagerTitleView.setOnClickListener(v -> jgq_new_content.setCurrentItem(index));
                }
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        jgq_new_magicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(jgq_new_magicindicator, jgq_new_content);
        int a = width-Utils.dipToPixel(R.dimen.dp_20);
        jgq_new_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // 显示区域的宽度。
                int extent =a;
                //整体的宽度，注意是整体，包括在显示区域之外的。
                int range =list.size()*a;
                //已经向下滚动的距离，为0时表示已处于顶部。
                int offset = i*a;
                //设置可滚动区域
                progress_bar.setMax((int)(range-extent));
                progress_bar.setProgress(offset);
            }
            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadViewnew.setTextColor(Color.BLACK);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadViewnew);
        loadMorePtrFrame.addPtrUIHandler(loadViewnew);
        loadMorePtrFrame.setPtrHandler(this);
    }

   private Boolean canScrollUp=true;
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        if (canScrollUp) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) AppBar_Layout.getLayoutParams()).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                if (topAndBottomOffset == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    private Boolean isRefresh=false;
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (NetStateUtils.isNetworkConnected(getActivity())) {
            ishasdata=false;
            if (fragments!=null&&fragments.size()>0)
            {
                fragments=null;
            }
            if (limitfragments!=null&&limitfragments.size()>0)
            {
                limitfragments=null;
            }
            isRefresh=true;
            //获取首页接口
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "HomePageInit", HttpCommon.HomePageInit);
        }
    }



    /**
     * 切换fragment
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        if (currentFragment != targetFragment) {
            FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.fragment_other_content, targetFragment).commitAllowingStateLoss();
            } else {
                transaction.hide(currentFragment).show(targetFragment).commitAllowingStateLoss();
            }
            currentFragment = targetFragment;
        }
    }

    //一键滚动到顶部
    public void scrollToTop() {
        //拿到 appbar 的 behavior,让 appbar 滚动
       //底部列表要先滚到顶部
       InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ListScrollToTop"), false, 1);
        AppBarLayoutScrollToTop();
    }

    //底部列表先滚到底部 再调用此方法
    public void AppBarLayoutScrollToTop() {
        ViewGroup.LayoutParams layoutParams = AppBar_Layout.getLayoutParams();
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != 0) {
                appBarLayoutBehavior.setTopAndBottomOffset(0);
                AppBar_Layout.setExpanded(true, true);
            }
        }
    }

    //金刚区点击事件
    public void doJgqClick(HomePage.JGQAppIcon jgqAppIcon) {
        Utils.doJgqClick(getActivity(), jgqAppIcon);
    }

    //限时抢购倒计时方法
    private void CountDown(String startTime,String endTime){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            if (d2.getTime()> d1.getTime()) {
                long diff = d2.getTime() - d1.getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long second = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);

                hours += days * 24;

                TimerView.setTime(hours+ "", minutes+ "", second+ "");
                TimerView.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (timer!=null) {
            timer.cancel();
            timer=null;
        }
        if (limitTimerTask!=null){
            limitTimerTask.cancel();
            limitTimerTask=null;
        }
        if (VBhandler!=null){
            VBhandler=null;
        }
    }
    //列表滑动收起广告
   public void HideslidingMenu(int isFling){
       if (isFling==1){
           AppBarLayoutBehaviorNew.isSlidingMenuRight=false;
           AppBarLayoutBehaviorNew.isSlidingMenuLeft=true;
           sliding_adv_layout.setTranslateAnimation(SlidingAroundFrameLayout.TypeEnum.RIGHT);
       }else{
           AppBarLayoutBehaviorNew.isSlidingMenuRight=true;
           AppBarLayoutBehaviorNew.isSlidingMenuLeft=false;
           sliding_adv_layout.setTranslateAnimation(SlidingAroundFrameLayout.TypeEnum.LEFT);
       }
  }

}
