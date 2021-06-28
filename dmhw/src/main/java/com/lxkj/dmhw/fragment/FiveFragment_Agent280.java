package com.lxkj.dmhw.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AboutActivity340;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.BusinessActivity;
import com.lxkj.dmhw.activity.CollectionActivity310;
import com.lxkj.dmhw.activity.FootPrintActivity360;
import com.lxkj.dmhw.activity.HelpActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.MyIncomeActivity;
import com.lxkj.dmhw.activity.MyOrderActivity;
import com.lxkj.dmhw.activity.MyTaskFragmentActivity;
import com.lxkj.dmhw.activity.NewsActivity;
import com.lxkj.dmhw.activity.NewsExamActivity;
import com.lxkj.dmhw.activity.OneKeyChainActivity;
import com.lxkj.dmhw.activity.OnlyUrlWebViewActivity;
import com.lxkj.dmhw.activity.PosterActivity;
import com.lxkj.dmhw.activity.ServiceofMyActivity;
import com.lxkj.dmhw.activity.SettingActivity340;
import com.lxkj.dmhw.activity.TeamNewActivity;
import com.lxkj.dmhw.activity.TempDataSetActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.WebViewOtherActivity;
import com.lxkj.dmhw.activity.WithdrawalsNewActivity;
import com.lxkj.dmhw.adapter.BannerM1Adapter;
import com.lxkj.dmhw.adapter.FiveFragmentItemAdapter;
import com.lxkj.dmhw.adapter.PersonalAppIconAdapter;
import com.lxkj.dmhw.adapter.PersonalGridFourAdapter;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.bean.UserAccount;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.defined.ObserveScrollView;
import com.lxkj.dmhw.defined.ObserveWebView;
import com.lxkj.dmhw.dialog.MyInviteCodeDialog;
import com.lxkj.dmhw.dialog.ServiceDialog;
import com.lxkj.dmhw.dialog.ShoperDialog;
import com.lxkj.dmhw.dialog.WeixinAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScaleLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 代理个人中心  已被替换
 * Created by Android on 2018/5/11.
 */

public class FiveFragment_Agent280 extends BaseFragment implements ObserveScrollView.OnScrollListener, PersonalAppIconAdapter.OnPersonalAppIconClickListener,PersonalGridFourAdapter.OnPersonalAppIconClickListener, PtrHandler {

    @BindView(R.id.fragment_five_image)
    ImageView fragmentFiveImage;
    @BindView(R.id.fragment_five_name)
    TextView fragmentFiveName;
    @BindView(R.id.fragment_five_agent_today_text)
    TextView fragmentFiveAgentTodayText;
    @BindView(R.id.fragment_five_agent_current_text)
    TextView fragmentFiveAgentCurrentText;
    @BindView(R.id.fragment_five_agent_last_text)
    TextView fragmentFiveAgentLastText;
    @BindView(R.id.fragment_five_news)
    ImageView fragmentFiveNews;
    @BindView(R.id.fragment_five_news_layout)
    LinearLayout fragment_five_news_layout;
    @BindView(R.id.fragment_five_unread)
    View fragmentFiveUnread;
    @BindView(R.id.fragment_five_unread1)
    View fragmentFiveUnread1;
    @BindView(R.id.fragment_five_news1)
    ImageView fragmentFiveNews1;
    @BindView(R.id.fragment_five_agent_setting1)
    ImageView fragmentFiveAgentSetting1;
    @BindView(R.id.fragment_five_setting)
    ImageView fragment_five_setting;

    @BindView(R.id.fragment_five_scroll)
    ObserveScrollView fragmentFiveScroll;
    @BindView(R.id.fragment_five_title)
    LinearLayout fragmentFiveTitle;
    @BindView(R.id.change_gb1)
    LinearLayout change_gb1;
    @BindView(R.id.change_gb2)
    LinearLayout change_gb2;
//    @BindView(R.id.fragment_five_top_unlogin)
//    LinearLayout fragment_five_top_unlogin;

    @BindView(R.id.rank_layout)
    RelativeLayout rank_layout;
    @BindView(R.id.fragment_five_invitation)
    TextView fragmentFiveInvitation;
    @BindView(R.id.fragment_five_agent_type_image)
    ImageView fragmentFiveAgentTypeImage;
    @BindView(R.id.fragment_five_agent_type_text)
    TextView fragmentFiveAgentTypeText;
    @BindView(R.id.fragment_five_tourist_vip_up)
    LinearLayout fragmentFiveTouristVipUp;
    @BindView(R.id.fragment_five_tourist_vip_up_one)
    ImageView fragmentFiveTouristVipUpOne;
    @BindView(R.id.fragment_five_tourist_vip_up_two)
    TextView fragmentFiveTouristVipUpTwo;
    @BindView(R.id.fragment_five_copy)
    ImageView fragmentFiveCopy;
    @BindView(R.id.invite_code_layout)
    RelativeLayout invite_code_layout;
    @BindView(R.id.fragment_five_agent_user)
    LinearLayout fragmentFiveAgentUser;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.fragment_five_news_top)
    LinearLayout fragment_five_news_top;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.status_bar1)
    View statusBar1;
    @BindView(R.id.yaoqingma)
    TextView yaoqingma;
    @BindView(R.id.copy_txt)
    ImageView copy_txt;
    @BindView(R.id.fragment_five_title_background)
    View fragmentFiveTitleBackground;
    @BindView(R.id.view_split)
    View view_split;

    @BindView(R.id.fragment_five_agent_title_avatar)
    ImageView fragmentFiveAgentTitleAvatar;
//    @BindView(R.id.fragment_five_agent_title_text)
//    TextView fragmentFiveAgentTitleText;
    @BindView(R.id.fragment_five_agent_title_text_name)
    TextView fragment_five_agent_title_text_name;
    @BindView(R.id.fragment_can_draw)
    TextView fragment_can_draw;
    @BindView(R.id.location_layout)
    LinearLayout locationLayout;
    @BindView(R.id.withdraw_money_layout)
    LinearLayout withdraw_money_layout;
    @BindView(R.id.fragment_five_image_bg)
    LinearLayout fragment_five_image_bg;
    @BindView(R.id.fragment_five_agent_user_layout)
    LinearLayout fragment_five_agent_user_layout;
    @BindView(R.id.fragment_five_tourist_vip_up_layout)
    LinearLayout fragment_five_tourist_vip_up_layout;
    @BindView(R.id.fragment_five_tourist_vip_up_layout_pp)
    RelativeLayout fragment_five_tourist_vip_up_layout_pp;
    @BindView(R.id.icon_list_firstlayout)
    LinearLayout icon_list_firstlayout;
    @BindView(R.id.icon_list_secondlayout)
    LinearLayout icon_list_secondlayout;

//    @BindView(R.id.fragment_five_unloginnews)
//    ImageView fragment_five_unloginnews;
    @BindView(R.id.fragment_five_tourist_login)
    LinearLayout fragment_five_tourist_login;
    @BindView(R.id.app_icon_layout)
    LinearLayout app_icon_layout;
    @BindView(R.id.rank_iv)
    ImageView rank_iv;
    @BindView(R.id.vip_layout)
    LinearLayout vip_layout;
    @BindView(R.id.fragment_five_arrow)
    ImageView fragment_five_arrow;
    @BindView(R.id.fragment_five_agent_title_money_total)
    TextView fragment_five_agent_title_money_total;
    @BindView(R.id.personal_bg_iv_layout)
    RelativeLayout personal_bg_iv_layout;
    @BindView(R.id.personal_bg_iv_unloginlayout)
    RelativeLayout personal_bg_iv_unloginlayout;
    @BindView(R.id.personal_bg_iv)
    ImageView personal_bg_iv;
    //第一个功能图标列表
    @BindView(R.id.fragment_five_agent_grid_one)
    ObserveGridView fragment_five_agent_grid_one;
    //第二个功能图标列表
    @BindView(R.id.fragment_five_agent_grid_two)
    ObserveGridView fragment_five_agent_grid_two;
    //第三个功能图标列表
    @BindView(R.id.fragment_five_agent_grid_three)
    ObserveGridView fragment_five_agent_grid_three;
    @BindView(R.id.fragment_five_agent_web)
    ObserveWebView fragmentFiveAgentWeb;
    @BindView(R.id.up_shop_menber)
    Button up_shop_menber;
    @BindView(R.id.my_fragment_one_new_vertical_banner)
    ConvenientBanner headerFragmentOneNewSixBanner;
    @BindView(R.id.header_fragment_one_new_banner_layout)
    ScaleLayout header_fragment_one_new_banner_layout;
    private PersonalAppIconAdapter adapter_one,adapter_two,adapter_three;
    private PersonalGridFourAdapter adapter_four;
    @BindView(R.id.tuiguang)
    TextView tuiguang;
    @BindView(R.id.yugu01)
    TextView yugu01;
    @BindView(R.id.yugu02)
    TextView yugu02;
    @BindView(R.id.yugu03)
    TextView yugu03;
    @BindView(R.id.click_login_txt)
    TextView click_login_txt;

    //新功能图标 找回订单
    @BindView(R.id.fragment_grid_four)
    ObserveGridView fragment_grid_four;

    @BindView(R.id.service_tv)
    TextView service_tv;
    TextPaint tp;
    private  FiveFragmentItemAdapter fiveFragmentItemAdapter;
    private ArrayList<PersonalAppIcon> list;
    public static String  UserType="";
    @BindView(R.id.fragment_five_agent_list_two)
    RecyclerView fragment_five_agent_list_two;

    ArrayList<PersonalAppIcon> Templist_Two;

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_agent280, null);
        ButterKnife.bind(this, view);
        //是否登录 隐藏个人信息
        InitLoginView();
        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            statusBar.setVisibility(View.GONE);
            statusBar1.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            statusBar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            statusBar1.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        webSetting();
        // 设置滑动事件监听
        fragmentFiveScroll.setOnScrollListener(this);
        adapter_one = new PersonalAppIconAdapter(getActivity());
//        adapter_two = new PersonalAppIconAdapter(getActivity());
        adapter_three = new PersonalAppIconAdapter(getActivity());
        fragment_five_agent_grid_one.setAdapter(adapter_one);
//        fragment_five_agent_grid_two.setAdapter(adapter_two);
        fragment_five_agent_grid_three.setAdapter(adapter_three);
        adapter_one.setOnPersonalAppIconClickListener(this);
//        adapter_two.setOnPersonalAppIconClickListener(this);
        adapter_three.setOnPersonalAppIconClickListener(this);

        adapter_four = new PersonalGridFourAdapter(getActivity());
        fragment_grid_four.setAdapter(adapter_four);
        adapter_four.setOnPersonalAppIconClickListener(this);


        //中间banner 轮播图适配 宽高比例是340:66
//        header_fragment_one_new_banner_layout.setCornerRadius(0);
//        RelativeLayout.LayoutParams linearParamsOne =(RelativeLayout.LayoutParams)headerFragmentOneNewSixBanner.getLayoutParams();
//        linearParamsOne.width=width;
//        linearParamsOne.height=width*66/340;
//        headerFragmentOneNewSixBanner.setLayoutParams(linearParamsOne);

        tp= fragmentFiveName.getPaint();
        tp.setFakeBoldText(true);
        tp= fragment_can_draw.getPaint();
        tp.setFakeBoldText(true);

        tp= fragmentFiveAgentTodayText.getPaint();
        tp.setFakeBoldText(true);
        tp= fragmentFiveAgentCurrentText.getPaint();
        tp.setFakeBoldText(true);
        tp= fragmentFiveAgentLastText.getPaint();
        tp.setFakeBoldText(true);

        tp= fragment_five_agent_title_money_total.getPaint();
        tp.setFakeBoldText(true);

        tp= click_login_txt.getPaint();
        tp.setFakeBoldText(true);

        tp= tuiguang.getPaint();
        tp.setFakeBoldText(true);

        tp= service_tv.getPaint();
        tp.setFakeBoldText(true);
        UserType=login.getUsertype();
    }

    @Override
    public void onCustomized() {
        ptrFrameLayout();
        // 数据查询
        dataRequest();
    }

    //初始化登录界面
    private void InitLoginView(){
        //是否登录 隐藏个人信息
        if (DateStorage.getLoginStatus()){
            switch (login.getUsertype()) {
                case "1":// 运营商
                    resetBgHeight(1);
                    break;
                case "2":// 呆萌会员
                    resetBgHeight(0);
                    break;
                case "4"://呆萌店主
                    resetBgHeight(0);
                    break;
                case "5":// 合伙人
                    resetBgHeight(1);
                    break;
            }
            fragment_five_tourist_login.setVisibility(View.GONE);
            fragment_five_agent_user_layout.setVisibility(View.VISIBLE);
            fragment_five_tourist_vip_up_layout_pp.setVisibility(View.VISIBLE);
            change_gb1.setBackgroundColor(Color.parseColor("#f4f2f2"));
        }else{
            resetBgHeight(2);
            fragmentFiveAgentTitleAvatar.setImageResource(R.mipmap.personal_default_img);
            fragment_five_agent_title_text_name.setText("登录账号");
            fragmentFiveUnread.setVisibility(View.GONE);
            fragment_five_tourist_login.setVisibility(View.VISIBLE);
            personal_bg_iv_unloginlayout.setVisibility(View.VISIBLE);
            personal_bg_iv_layout.setVisibility(View.GONE);
            fragment_five_agent_user_layout.setVisibility(View.GONE);
            fragment_five_tourist_vip_up_layout_pp.setVisibility(View.GONE);
            change_gb1.setBackgroundColor(Color.TRANSPARENT);
            change_gb2.setBackgroundColor(Color.parseColor("#f4f2f2"));
            loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg1));
        }
    }

    @Override
    public void mainMessage(Message message) {
        // 修改资料更新
        if (message.what == LogicActions.ModifyUserInfoSuccess) {
            UserInfo login = DateStorage.getInformation();
            Utils.displayImageCircular(getActivity(), login.getUserpicurl(), fragmentFiveImage);
            fragmentFiveName.setText(login.getUsername());
            fragmentFiveInvitation.setText(login.getExtensionid());
        }
        if (message.what == LogicActions.LoginStatusSuccess) {
            // 重刷数据
            InitLoginView();
            dataRequest();
        }
        if (message.what == LogicActions.WithdrawalsRefreshUserInfoSuccess) {
            // 获取用户信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);
            // 个人销售信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "UserAccount", HttpCommon.UserAccount);
        }
        if (message.what == LogicActions.UpdateNewsDetailsSuccess) {
            // 获取未读消息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetUnread", HttpCommon.GetUnread);
        }

        //特殊数据的处理
        if (message.what == LogicActions.TempDataSetActivitySuccess) {
            // 特殊数据处理响应
            HashMap<String, String> tempMap=TempDataSetActivity.userCenterData;
            if(tempMap!=null){
              if(!tempMap.get("userImage").equals("")){
                  Utils.displayImageCircularWithBitmap(getActivity(), Utils.stringtoBitmap(tempMap.get("userImage")), fragmentFiveImage);
              }
                fragmentFiveAgentTypeText.setTextColor(Color.parseColor("#432E10"));
                fragmentFiveName.setTextColor(Color.parseColor("#252525"));
                yaoqingma.setTextColor(Color.parseColor("#EFCCA0"));
//                copy_txt.setTextColor(Color.parseColor("#432E10"));
                fragmentFiveInvitation.setTextColor(Color.parseColor("#EFCCA0"));
//                invite_code_layout.setBackgroundResource(R.mipmap.five_invite_background1);
//                vip_layout.setBackgroundResource(R.drawable.vip_background1);
                personal_bg_iv_unloginlayout.setVisibility(View.GONE);
                personal_bg_iv_layout.setVisibility(View.VISIBLE);
//                personal_bg_iv.setImageResource(R.mipmap.personal_shop_bg280);
//                copy_txt.setBackgroundResource(R.drawable.five_copy_bg1);
                yugu01.setTextColor(Color.parseColor("#B58630"));
                yugu02.setTextColor(Color.parseColor("#B58630"));
                yugu03.setTextColor(Color.parseColor("#B58630"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragment_five_arrow.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.color_B58630)));
                }
                fragment_can_draw.setTextColor(Color.parseColor("#B58630"));
                fragment_five_agent_title_money_total.setTextColor(Color.parseColor("#432E10"));
                fragmentFiveAgentTodayText.setTextColor(Color.parseColor("#432E10"));
                fragmentFiveAgentCurrentText.setTextColor(Color.parseColor("#432E10"));
                fragmentFiveAgentLastText.setTextColor(Color.parseColor("#432E10"));
              if (!tempMap.get("userType").equals("")){
                  switch (tempMap.get("userType")) {
                          case "1":// 运营商
                              rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                              fragmentFiveAgentTypeText.setText("运营商");
//                              fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_yun);
                              rank_iv.setImageResource(R.mipmap.rank_yunyingshang);
                              resetBgHeight(1);
                              fragmentFiveTouristVipUp.setVisibility(View.GONE);
                              loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                              break;
                          case "2":// 呆萌会员
                              fragmentFiveAgentTypeText.setTextColor(Color.parseColor("#FFFFFF"));
                              fragmentFiveName.setTextColor(Color.parseColor("#FFFFFF"));
                              yaoqingma.setTextColor(Color.parseColor("#FFD8E1"));
//                              copy_txt.setTextColor(Color.parseColor("#FFFFFF"));
                              fragmentFiveInvitation.setTextColor(Color.parseColor("#FFD8E1"));
//                              invite_code_layout.setBackgroundResource(R.drawable.five_invite_background);
//                              vip_layout.setBackgroundResource(R.drawable.vip_background);
//                              personal_bg_iv.setImageResource(R.mipmap.personal_bg280);
//                              copy_txt.setBackgroundResource(R.drawable.five_copy_bg);
                              fragment_can_draw.setTextColor(Color.parseColor("#666666"));
                              yugu01.setTextColor(Color.parseColor("#666666"));
                              yugu02.setTextColor(Color.parseColor("#666666"));
                              yugu03.setTextColor(Color.parseColor("#666666"));
                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                  fragment_five_arrow.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.color_666666)));
                              }
                              fragment_five_agent_title_money_total.setTextColor(Color.parseColor("#333333"));
                              fragmentFiveAgentTodayText.setTextColor(Color.parseColor("#333333"));
                              fragmentFiveAgentCurrentText.setTextColor(Color.parseColor("#333333"));
                              fragmentFiveAgentLastText.setTextColor(Color.parseColor("#333333"));
                              rank_layout.setBackgroundResource(R.drawable.personal_grid_bg);
                              fragmentFiveAgentTypeText.setText("VIP会员");
//                              fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_agent);
                              fragmentFiveTouristVipUpOne.setImageResource(R.mipmap.shopmenber);
                              rank_iv.setImageResource(R.mipmap.rank_commom);
                              up_shop_menber.setText("升级店主");
                              resetBgHeight(0);
                              fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                              loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg1));
                              break;
                          case "4"://呆萌店主
                              rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                              fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                              fragmentFiveAgentTypeText.setText("呆萌店主");
//                              fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_franchiser);
                              fragmentFiveTouristVipUpOne.setImageResource(R.mipmap.hehuoren);
                              up_shop_menber.setText("升级合伙人");
                              rank_iv.setImageResource(R.mipmap.rank_shop);
                              resetBgHeight(0);
                              fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                              loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                              break;
                          case "5":// 合伙人
                              rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                              fragmentFiveAgentTypeText.setText("合伙人");
//                              fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_partner);
                              rank_iv.setImageResource(R.mipmap.rank_hehuoren);
                              resetBgHeight(1);
                              fragmentFiveTouristVipUp.setVisibility(View.GONE);
                              loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                              break;
                          default:
                              //普通会员
                              break;
                      }
              }
              if (!tempMap.get("nickName").equals("")){
                  fragmentFiveName.setText(tempMap.get("nickName"));
              }
              if (!tempMap.get("inviteCode").equals("")){
                  fragmentFiveInvitation.setText(tempMap.get("inviteCode"));
                  fiveFragmentItemAdapter=new FiveFragmentItemAdapter(getActivity(),tempMap.get("inviteCode"));
                  fragment_five_agent_list_two.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
                  fragment_five_agent_list_two.setNestedScrollingEnabled(false);
                  fragment_five_agent_list_two.setAdapter(fiveFragmentItemAdapter);
                  fiveFragmentItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                      @Override
                      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                          onPersonalAppIconClick((PersonalAppIcon) adapter.getItem(position));
                      }
                  });
                  fiveFragmentItemAdapter.setNewData(Templist_Two);
              }
              if (!tempMap.get("canWithdraw").equals("")){
                  fragment_five_agent_title_money_total.setText(tempMap.get("canWithdraw"));
              }
              if (!tempMap.get("todayMoney").equals("")){
                  fragmentFiveAgentTodayText.setText(tempMap.get("todayMoney"));
              }
              if (!tempMap.get("monthMoney").equals("")){
                  fragmentFiveAgentCurrentText.setText(tempMap.get("monthMoney"));
              }
              if (!tempMap.get("lastMoney").equals("")){
                  fragmentFiveAgentLastText.setText(tempMap.get("lastMoney"));
              }

          }
        }
        //微信授权弹窗广播
        if (message.what == LogicActions.WxAuthDialogSuccess) {
            if (message.arg1==0){
                if (wxdialog==null||!wxdialog.isShowing()) {
                    showWxBindDialog(message.obj.toString());
                }
            }
        }
        //绑定微信
        if (message.what == LogicActions.UserInfoActivityBindingSuccess) {
            if (wxdialog!=null&&wxdialog.isShowing()){
                wxdialog.dismiss();
            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
        // 获取用户信息
        if (message.what == LogicActions.GetUserInfoSuccess) {
            UserInfo login = (UserInfo) message.obj;
            UserType=login.getUsertype();
            setActivityInfo(login);
        }
        // 个人销售信息
        if (message.what == LogicActions.UserAccountSuccess) {
            UserAccount userAccount = (UserAccount) message.obj;
            fragmentFiveAgentTodayText.setText(userAccount.getTodayincome());
            fragmentFiveAgentCurrentText.setText(userAccount.getThismonthincome());
            fragmentFiveAgentLastText.setText(userAccount.getLastmonthincome());
            fragment_five_agent_title_money_total.setText(userAccount.getUsercommission());
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
                fragmentFiveUnread1.setVisibility(View.VISIBLE);
                fragmentFiveUnread.setVisibility(View.VISIBLE);
            } else {
                fragmentFiveUnread1.setVisibility(View.GONE);
                fragmentFiveUnread.setVisibility(View.GONE);
            }
        }

        if (message.what == LogicActions.GetWeChatSuccess) {
            JSONObject jsonObject = (JSONObject) message.obj;
            String tempmsg = "";
            if (jsonObject.optString("isbind").equals("0")) {
                tempmsg = jsonObject.optString("alertmsg");
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("WxAuthDialog"), tempmsg, 0);
            }
        }
        // 我的服务
        if (message.what == LogicActions.TotalAppIconSuccess) {
            list = (ArrayList<PersonalAppIcon>) message.obj;
            if (list!=null&&list.size()>0){
            ArrayList<PersonalAppIcon> Templist_One=new ArrayList<>();
            Templist_Two=new ArrayList<>();
            ArrayList<PersonalAppIcon> Templist_Three=new ArrayList<>();

            ArrayList<PersonalAppIcon> Templist_four=new ArrayList<>();
                //根据type=1 填充 第一块图标
            for (int i=0;i<list.size();i++){
                switch (list.get(i).getType()){
                    case "1":
                        Templist_One.add(list.get(i));
                        break;
                    case "2":
                        Templist_Two.add(list.get(i));
                        break;
                    case "3":
                        Templist_Three.add(list.get(i));
                        break;
                    case "4":
                        Templist_four.add(list.get(i));
                        break;
                }
            }
            if (Templist_One!=null&&Templist_One.size()>0){
                adapter_one.setData(Templist_One);
            }
            if (Templist_Two!=null&&Templist_Two.size()>0){
                fiveFragmentItemAdapter=new FiveFragmentItemAdapter(getActivity(),"");
                fragment_five_agent_list_two.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
                fragment_five_agent_list_two.setNestedScrollingEnabled(false);
                fragment_five_agent_list_two.setAdapter(fiveFragmentItemAdapter);
                fiveFragmentItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        onPersonalAppIconClick((PersonalAppIcon) adapter.getItem(position));
                    }
                });
                fiveFragmentItemAdapter.setNewData(Templist_Two);
                }
            if (Templist_Three!=null&&Templist_Three.size()>0){
                    adapter_three.setData(Templist_Three);
                }

            if (Templist_four!=null&&Templist_four.size()>0){
                 adapter_four.setData(Templist_four);
                }else{
                 fragment_grid_four.setVisibility(View.GONE);
                 }
            }


        }


        //添加中间广告
        if (message.what == LogicActions.OneFragmentBannerSuccess) {
            if (message.obj!=null&&((ArrayList<Banner>) message.obj).size()>0){
                header_fragment_one_new_banner_layout.setVisibility(View.VISIBLE);
                OneFragmentBanner((ArrayList<Banner>) message.obj);
            }else{
                header_fragment_one_new_banner_layout.setVisibility(View.GONE);
            }
        }

        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("isTitle" , true).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
        }
        if (message.what == LogicActions.AppShareSuccess) {
            dismissDialog();
            Poster  poster = (Poster) message.obj;
            String [] tempData = {fragmentFiveInvitation.getText().toString(),poster.getAppsharelink()};
            MyInviteCodeDialog dialog = new MyInviteCodeDialog(getActivity(),tempData);
            dialog.showDialog();
        }

        //获取token 保存本地
        if (message.what == LogicActions.LoginTokenSuccess) {
            LoginToken loginToken= (LoginToken) message.obj;
            DateStorage.setMyToken(loginToken.getToken());
            DateStorage.setLittleAppId(loginToken.getMicroId());
            DateStorage.setMicroAppId(loginToken.getWechatMicroId());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // fragment隐藏时调用
        } else {
            // fragment显示时调用
            //是否绑定微信
            if (DateStorage.getLoginStatus()) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "GetWeChat", HttpCommon.GetWeChat);
            }
        }
    }

    @OnClick({R.id.fragment_five_news_layout,R.id.fragment_five_agent_setting1,R.id.fragment_five_news,R.id.fragment_five_news1,R.id.location_layout,R.id.fragment_five_setting,
            R.id.fragment_five_tourist_vip_up,R.id.fragment_five_tourist_login,R.id.fragment_five_image_bg,R.id.invite_code_layout, R.id.fragment_five_agent_title_avatar,R.id.up_shop_menber,R.id.withdraw_money_layout,R.id.fragment_five_agent_title_money_total})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.location_layout:
            case R.id.fragment_five_agent_setting1://设置按钮
            case R.id.fragment_five_setting://设置按钮
            case R.id.fragment_five_image_bg:// 点击头像
            case R.id.fragment_five_agent_title_avatar:// 标题栏头像
                if (DateStorage.getLoginStatus()) {
                    intent = new Intent(getActivity(), SettingActivity340.class);
                }else{
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.fragment_five_news1:// 消息
            case R.id.fragment_five_news:// 消息
                if (DateStorage.getLoginStatus()) {
                    intent = new Intent(getActivity(), NewsActivity.class);
                }else{
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.up_shop_menber:// 升级
                if (Variable.FastClickTime()) {
                    return;
                }
                login = DateStorage.getInformation();
                switch (login.getUsertype()) {
                    case "2":
                        // 获取H5地址
                        paramMap.clear();
                        paramMap.put("type", "2");
                        NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                        break;
                    case "4":
                        // 获取H5地址
                        paramMap.clear();
                        paramMap.put("type", "3");
                        NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                        break;
                }
                break;
            case R.id.invite_code_layout:// 邀请码
                // 获取分享地址
                showDialog();
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "AppShare", HttpCommon.AppShare);
                break;
            case R.id.fragment_five_agent_title_money_total:
            case R.id.withdraw_money_layout://提现
                intent = new Intent(getActivity(), WithdrawalsNewActivity.class);
                intent.putExtra("widthdrawpos","0");
                break;
            case R.id.fragment_five_tourist_login://登录
                intent = new Intent(getActivity(), LoginActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if ((scrollY >= 0) && (scrollY <= Utils.dipToPixel(R.dimen.dp_6))) {
            fragmentFiveTitleBackground.setAlpha(0f);
            view_split.setAlpha(0f);
            fragmentFiveNews1.setAlpha(0.1f);
            fragmentFiveAgentSetting1.setAlpha(0.1f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.1f);
            fragment_five_agent_title_text_name.setAlpha(0.1f);
            fragmentFiveUnread1.setAlpha(0.1f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_6)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_12))) {
            fragmentFiveTitleBackground.setAlpha(0.1f);
            view_split.setAlpha(0.1f);
            fragmentFiveNews1.setAlpha(0.2f);
            fragmentFiveAgentSetting1.setAlpha(0.2f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.2f);
            fragment_five_agent_title_text_name.setAlpha(0.2f);
            fragmentFiveUnread1.setAlpha(0.2f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_12)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_18))) {
            fragmentFiveTitleBackground.setAlpha(0.2f);
            view_split.setAlpha(0.2f);
            fragmentFiveNews1.setAlpha(0.3f);
            fragmentFiveAgentSetting1.setAlpha(0.3f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.3f);
            fragment_five_agent_title_text_name.setAlpha(0.3f);
            fragmentFiveUnread1.setAlpha(0.3f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_18)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_24))) {
            fragmentFiveTitleBackground.setAlpha(0.3f);
            view_split.setAlpha(0.3f);
            fragmentFiveNews1.setAlpha(0.4f);
            fragmentFiveAgentSetting1.setAlpha(0.4f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.4f);
            fragment_five_agent_title_text_name.setAlpha(0.4f);
            fragmentFiveUnread1.setAlpha(0.4f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_24)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_30))) {
            fragmentFiveTitleBackground.setAlpha(0.4f);
            view_split.setAlpha(0.4f);
            fragmentFiveNews1.setAlpha(0.5f);
            fragmentFiveAgentSetting1.setAlpha(0.5f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.5f);
            fragment_five_agent_title_text_name.setAlpha(0.5f);
            fragmentFiveUnread1.setAlpha(0.5f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_30)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_36))) {
            fragmentFiveTitleBackground.setAlpha(0.5f);
            view_split.setAlpha(0.5f);
            fragmentFiveNews1.setAlpha(0.6f);
            fragmentFiveAgentSetting1.setAlpha(0.6f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.6f);
            fragment_five_agent_title_text_name.setAlpha(0.6f);
            fragmentFiveUnread1.setAlpha(0.6f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_36)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_42))) {
            fragmentFiveTitleBackground.setAlpha(0.6f);
            view_split.setAlpha(0.6f);
            fragmentFiveNews1.setAlpha(0.7f);
            fragmentFiveAgentSetting1.setAlpha(0.7f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.7f);
            fragment_five_agent_title_text_name.setAlpha(0.7f);
            fragmentFiveUnread1.setAlpha(0.7f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_42)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_48))) {
            fragmentFiveTitleBackground.setAlpha(0.7f);
            view_split.setAlpha(0.7f);
            fragmentFiveNews1.setAlpha(0.8f);
            fragmentFiveAgentSetting1.setAlpha(0.8f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.8f);
            fragment_five_agent_title_text_name.setAlpha(0.8f);
            fragmentFiveUnread1.setAlpha(0.8f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_48)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_54))) {
            fragmentFiveTitleBackground.setAlpha(0.8f);
            view_split.setAlpha(0.8f);
            fragmentFiveNews1.setAlpha(0.9f);
            fragmentFiveAgentSetting1.setAlpha(0.9f);
            fragmentFiveAgentTitleAvatar.setAlpha(0.9f);
            fragment_five_agent_title_text_name.setAlpha(0.9f);
            fragmentFiveUnread1.setAlpha(0.9f);
        } else if ((scrollY > Utils.dipToPixel(R.dimen.dp_54)) && (scrollY <= Utils.dipToPixel(R.dimen.dp_60))) {
            fragmentFiveTitleBackground.setAlpha(0.9f);
            view_split.setAlpha(0.9f);
            fragmentFiveNews1.setAlpha(1.0f);
            fragmentFiveAgentSetting1.setAlpha(1.0f);
            fragmentFiveAgentTitleAvatar.setAlpha(1.0f);
            fragment_five_agent_title_text_name.setAlpha(1.0f);
            fragmentFiveUnread1.setAlpha(1.0f);
        } else {
            fragmentFiveTitleBackground.setAlpha(0.95f);
            view_split.setAlpha(0.95f);
            fragmentFiveNews1.setAlpha(1.0f);
            fragmentFiveAgentSetting1.setAlpha(1.0f);
            fragmentFiveAgentTitleAvatar.setAlpha(1.0f);
            fragment_five_agent_title_text_name.setAlpha(1.0f);
            fragmentFiveUnread1.setAlpha(1.0f);
        }

        if (scrollY >=Utils.dipToPixel(R.dimen.dp_3)) {
            fragment_five_news_layout.setVisibility(View.GONE);
            fragment_five_news_top.setVisibility(View.VISIBLE);
        }else {
            fragment_five_news_layout.setVisibility(View.VISIBLE);
            fragment_five_news_top.setVisibility(View.GONE);
        }
        if(scrollY >= Utils.dipToPixel(R.dimen.dp_3)){
            locationLayout.setVisibility(View.VISIBLE);
            }else{
            locationLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.WHITE);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
        //左右滑动时刷新控件禁止掉
        loadMorePtrFrame.disableWhenHorizontalMove(true);
    }

    /**
     * 设置页面信息
     *
     * @param login
     */
    private void setActivityInfo(UserInfo login) {
        login.setUserid(this.login.getUserid());
        // 储存用户信息
        DateStorage.setInformation(login);
        fragmentFiveName.setText(login.getUsername());
        fragmentFiveInvitation.setText(login.getExtensionid());
        fragment_five_agent_title_text_name.setText(login.getUsername());
//        fragment_five_agent_title_money_total.setText(login.getUsercommission());
        if (!login.getUserpicurl().equals("")){
            fragment_five_image_bg.setBackground(getResources().getDrawable(R.mipmap.personal_default_img));
            Utils.displayImageCircular(getActivity(), login.getUserpicurl(), fragmentFiveImage);
            Utils.displayImageCircular(getActivity(), login.getUserpicurl(), fragmentFiveAgentTitleAvatar);
        }else{
            fragment_five_image_bg.setBackground(null);
        }
        fragmentFiveAgentTypeText.setTextColor(Color.parseColor("#432E10"));
        fragmentFiveName.setTextColor(Color.parseColor("#252525"));
        yaoqingma.setTextColor(Color.parseColor("#EFCCA0"));
//        copy_txt.setTextColor(Color.parseColor("#432E10"));
        fragmentFiveInvitation.setTextColor(Color.parseColor("#EFCCA0"));
//        invite_code_layout.setBackgroundResource(R.mipmap.five_invite_background1);
//        vip_layout.setBackgroundResource(R.drawable.vip_background1);
        personal_bg_iv_unloginlayout.setVisibility(View.GONE);
        personal_bg_iv_layout.setVisibility(View.VISIBLE);
//        personal_bg_iv.setImageResource(R.mipmap.personal_shop_bg280);
//        copy_txt.setBackgroundResource(R.drawable.five_copy_bg1);
        yugu01.setTextColor(Color.parseColor("#B58630"));
        yugu02.setTextColor(Color.parseColor("#B58630"));
        yugu03.setTextColor(Color.parseColor("#B58630"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment_five_arrow.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.color_B58630)));
        }
        fragment_can_draw.setTextColor(Color.parseColor("#B58630"));
        fragment_five_agent_title_money_total.setTextColor(Color.parseColor("#432E10"));
        fragmentFiveAgentTodayText.setTextColor(Color.parseColor("#432E10"));
        fragmentFiveAgentCurrentText.setTextColor(Color.parseColor("#432E10"));
        fragmentFiveAgentLastText.setTextColor(Color.parseColor("#432E10"));
        change_gb1.setBackgroundColor(Color.parseColor("#f4f2f2"));
        switch (login.getUsertype()) {
            case "1":// 运营商
                rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                fragmentFiveAgentTypeText.setText("运营商");
//                fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_yun);
                rank_iv.setImageResource(R.mipmap.rank_yunyingshang);
                resetBgHeight(1);
                fragmentFiveTouristVipUp.setVisibility(View.GONE);
                loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                break;
            case "2":// 呆萌会员
                fragmentFiveAgentTypeText.setTextColor(Color.parseColor("#FFFFFF"));
                fragmentFiveName.setTextColor(Color.parseColor("#FFFFFF"));
                yaoqingma.setTextColor(Color.parseColor("#FFD8E1"));
//                copy_txt.setTextColor(Color.parseColor("#FFFFFF"));
                fragmentFiveInvitation.setTextColor(Color.parseColor("#FFD8E1"));
//                invite_code_layout.setBackgroundResource(R.drawable.five_invite_background);
//                vip_layout.setBackgroundResource(R.drawable.vip_background1);
//                personal_bg_iv.setImageResource(R.mipmap.personal_bg280);
//                copy_txt.setBackgroundResource(R.drawable.five_copy_bg);
                fragment_can_draw.setTextColor(Color.parseColor("#666666"));
                yugu01.setTextColor(Color.parseColor("#666666"));
                yugu02.setTextColor(Color.parseColor("#666666"));
                yugu03.setTextColor(Color.parseColor("#666666"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragment_five_arrow.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.color_666666)));
                }
                fragment_five_agent_title_money_total.setTextColor(Color.parseColor("#333333"));
                fragmentFiveAgentTodayText.setTextColor(Color.parseColor("#333333"));
                fragmentFiveAgentCurrentText.setTextColor(Color.parseColor("#333333"));
                fragmentFiveAgentLastText.setTextColor(Color.parseColor("#333333"));
                rank_layout.setBackgroundResource(R.drawable.personal_grid_bg);
                fragmentFiveAgentTypeText.setText("VIP会员");
//                fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_agent);
                fragmentFiveTouristVipUpOne.setImageResource(R.mipmap.shopmenber);
                rank_iv.setImageResource(R.mipmap.rank_commom);
                up_shop_menber.setText("升级店主");
                resetBgHeight(0);
                fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg1));
                break;
            case "4"://呆萌店主
                rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                fragmentFiveAgentTypeText.setText("呆萌店主");
//                fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_franchiser);
                fragmentFiveTouristVipUpOne.setImageResource(R.mipmap.hehuoren);
                up_shop_menber.setText("升级合伙人");
                rank_iv.setImageResource(R.mipmap.rank_shop);
                resetBgHeight(0);
                fragmentFiveTouristVipUp.setVisibility(View.VISIBLE);
                loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                break;
            case "5":// 合伙人
                rank_layout.setBackgroundResource(R.mipmap.vip_withdraw_bg280);
                fragmentFiveAgentTypeText.setText("合伙人");
//                fragmentFiveAgentTypeImage.setImageResource(R.mipmap.vip_partner);
                rank_iv.setImageResource(R.mipmap.rank_hehuoren);
                resetBgHeight(1);
                fragmentFiveTouristVipUp.setVisibility(View.GONE);
                loadMorePtrFrame.setBackground(getResources().getDrawable(R.drawable.user_pullrefresh_bg));
                break;
            default:
               //普通会员
                break;
        }
        if (login.getShopkeeperalert().equals("")){
            ShoperDialog shoperDialog=new ShoperDialog(getActivity(),"");
            shoperDialog.showDialog();
        }
//        //功能引导逻辑处理
//        if (DateStorage.getIsShowLead320().equals("")&&DateStorage.getLoginStatus()){
//            if (MainActivity.mainActivity!=null){
//                isShowLeadLocation();
//            }
//        }
    }

   //调节页面控件高度
   private void  resetBgHeight(int add){
//       RelativeLayout.LayoutParams linearParamsOne =(RelativeLayout.LayoutParams)personal_bg_iv.getLayoutParams();
       LinearLayout.LayoutParams linearParamsTwo =(LinearLayout.LayoutParams)app_icon_layout.getLayoutParams();
       if (add==1){//隐藏减少高度
//           linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_230);
           linearParamsTwo.setMargins(0,0,0,0);
       }else if(add==0){//显示增加高度
//           linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_260);
           linearParamsTwo.setMargins(0,0,0,0);
       }else{//未登录
//         linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_190);
         linearParamsTwo.setMargins(0,Utils.dipToPixel(R.dimen.dp_70),0,0);
       }
       app_icon_layout.setLayoutParams(linearParamsTwo);
//       personal_bg_iv.setLayoutParams(linearParamsOne);
   }


    /**
     * 数据请求
     */
     boolean isfirst = true;
    private void dataRequest() {
        if (isfirst){
            isfirst=false;
            showDialog();
        }
        // 请求Banner广告
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("advertisementposition", "42");
        NetworkRequest.getInstance().POST(handler, paramMap, "OneFragmentBanner", HttpCommon.Banner);
        if (NetStateUtils.isNetworkConnected(getActivity())) {
            // 获取所有功能图标
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "TotalAppIcon", HttpCommon.GetTotalAppIcon);
            if (DateStorage.getLoginStatus()) {
                //获取token
                if (DateStorage.getMyToken().equals("")||DateStorage.getMicroAppId().equals("")||DateStorage.getLittleAppId().equals("")){
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "LoginToken", HttpCommon.LoginToken);
                }
                // 获取用户信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);

                // 个人销售信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "UserAccount", HttpCommon.UserAccount);

                // 获取未读消息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "GetUnread", HttpCommon.GetUnread);

//                // 获取H5地址
//                paramMap.clear();
//                paramMap.put("type", type);
//                NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);

//                //是否绑定微信
                if (MainActivity.currentPos==4) {
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    NetworkRequest.getInstance().POST(handler, paramMap, "GetWeChat", HttpCommon.GetWeChat);
                }

            }
        }

    }

    private void OneFragmentBanner(ArrayList<Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }
        headerFragmentOneNewSixBanner.setPages(() -> new BannerM1Adapter(), netWorkImage);
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
            Utils.getJumpType(getActivity(),list.get(position).getJumptype(), list.get(position).getAdvertisementlink(),list.get(position).getNeedlogin(),list.get(position).getAdvertisemenid());
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void webSetting() {
        WebSettings settings = fragmentFiveAgentWeb.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        fragmentFiveAgentWeb.clearCache(true);
        fragmentFiveAgentWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        fragmentFiveAgentWeb.getSettings().setDomStorageEnabled(true);
        fragmentFiveAgentWeb.setInitialScale(100);
        fragmentFiveAgentWeb.setWebChromeClient(webChromeClient);
        fragmentFiveAgentWeb.setWebViewClient(webViewClient);
        fragmentFiveAgentWeb.setOnCreateContextMenuListener(this);
        fragmentFiveAgentWeb.addJavascriptInterface(new JavascriptHandler(getActivity(),0), "live");
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        /**
         * 通知应用程序当前网页加载的进度
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            view.requestFocus();
        }

        /**
         * 获取网页title标题
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    };

    private WebViewClient webViewClient = new WebViewClient() {

        /**
         * 回调该方法，处理未被WebView处理的事件
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        /**
         * 载入页面调用
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        /**
         * 页面加载结束时调用
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {

        }
    };

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentFiveScroll, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        // 数据查询
        dataRequest();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPersonalAppIconClick(PersonalAppIcon item) {
        Intent intent = null;
        if (item.getNeedlogin().equals("1") && !DateStorage.getLoginStatus()) {
            intent = new Intent(getActivity(), LoginActivity.class);
        } else {
            switch (item.getFuncid()) {
                case "1":// 提现
                    intent = new Intent(getActivity(), WithdrawalsNewActivity.class);
                    intent.putExtra("widthdrawpos", "0");//淘宝
                    break;
                case "2":// 我的收益
                    intent = new Intent(getActivity(), MyIncomeActivity.class);
                    break;
                case "3":// 订单
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    break;
                case "4":// 团队
                    intent = new Intent(getActivity(), TeamNewActivity.class);
                    break;
                case "5":// 邀请
                    intent = new Intent(getActivity(), PosterActivity.class);
                    break;
                case "6":// 足迹
                    intent = new Intent(getActivity(), FootPrintActivity360.class);
                    break;
                case "7":// 收藏
                    intent = new Intent(getActivity(), CollectionActivity310.class);
                    break;
                case "8":// 关于我们
                    intent = new Intent(getActivity(), AboutActivity340.class);
                    break;
                case "9":// 导师微信
                    // 获取上级客服
                    intent = new Intent(getActivity(), ServiceofMyActivity.class);
//                    Utils.openMyService(getActivity(),"我的客服","","我的",login.getUsername());
                    break;
                case "10":// 帮助
                    intent = new Intent(getActivity(), HelpActivity.class);
                    break;
                case "11":// 纯外链
                    intent = new Intent(getActivity(), OnlyUrlWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, item.getUrl());
                    break;
                case "21":// 商务合作
                    intent = new Intent(getActivity(), BusinessActivity.class);
                    break;
                case "22":// 设置
                    intent = new Intent(getActivity(), SettingActivity340.class);
                    break;
                case "23":// 推广
                    intent = new Intent(getActivity(), NewsExamActivity.class);
                    break;
                case "24":// 唤醒萌币
                    intent = new Intent(getActivity(), MyTaskFragmentActivity.class);
                    break;
                case "12":// 邀请码
                    if (!TextUtils.isEmpty(item.getUrl()) && item.getUrl().contains("dmjwebview=native")) {
                        intent = new Intent(getActivity(), WebViewOtherActivity.class);
                        intent.putExtra("hideTop", 1);
                        intent.putExtra(Variable.webUrl, item.getUrl());
                    } else {//视频教程
                        intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                        intent.putExtra(Variable.webUrl, item.getUrl());
                        if (item.getFuncid().equals("12")) {
                            intent.putExtra("isTitle", true);
                        }
                    }
                    break;
                case "66":// 即将上线 提示
                    ToastUtil.showImageToast(getActivity(),"即将上线，敬请期待",R.mipmap.toast_error);
                    break;
                case "51":// 唤醒小程序
                    Utils.launchLittleApp(getActivity(),item.getUrl());
                    break;
                case "52":// 一键转链
                    intent = new Intent(getActivity(), OneKeyChainActivity.class);
                    break;
                default:
                    if (!TextUtils.isEmpty(item.getUrl())) {
                        if (item.getUrl().contains("dmjwebview=native")) {
                            intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                            if (item.getFuncid().equals("12")) {
                                intent.putExtra("isTitle", true);
                            }
                            intent.putExtra(Variable.webUrl, item.getUrl());
                        }else{
                            intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                            intent.putExtra(Variable.webUrl, item.getUrl());
                            if (item.getFuncid().equals("12")) {
                                intent.putExtra("isTitle", true);
                            }
                        }
                    }
                    break;
            }

        }
            if (intent != null) {
                getActivity().startActivity(intent);
            }
        }


   //功能引导
   private  void isShowLeadLocation(){
       int marTopHeight=0;
            if (login.getUsertype().equals("2")||login.getUsertype().equals("4")){//会员和店主高度不一样
                marTopHeight = fragment_five_agent_user_layout.getHeight() + fragment_five_tourist_vip_up_layout.getHeight() + icon_list_firstlayout.getHeight() + header_fragment_one_new_banner_layout.getHeight()- Utils.dipToPixel(R.dimen.dp_10);
            }else {
                marTopHeight = fragment_five_agent_user_layout.getHeight() + fragment_five_tourist_vip_up_layout.getHeight() + icon_list_firstlayout.getHeight() + header_fragment_one_new_banner_layout.getHeight() - Utils.dipToPixel(R.dimen.dp_50);
            }
        MainActivity.mainActivity.isshowLeadUserCenterLayout(marTopHeight);

    }

    WeixinAuthLoginDialog wxdialog;
    public void showWxBindDialog(String message){
        if (wxdialog!=null&&wxdialog.isShowing()){
        }else{
            wxdialog = new WeixinAuthLoginDialog(getActivity(), message);
            wxdialog.show();
        }

    }
}
