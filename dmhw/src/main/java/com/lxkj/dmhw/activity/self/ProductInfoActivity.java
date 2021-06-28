package com.lxkj.dmhw.activity.self;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahtrun.mytablayout.ProxyDrawable2;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlang.widget.PopTipWindow;

import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.ServiceofMyActivity;
import com.lxkj.dmhw.adapter.self.CouponGetAdapter;
import com.lxkj.dmhw.bean.self.ActivityNewBean;
import com.lxkj.dmhw.bean.self.ActivityitemitemBean;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.GroupUser;
import com.lxkj.dmhw.bean.self.NewsBean;
import com.lxkj.dmhw.bean.self.PostTipBean;
import com.lxkj.dmhw.bean.self.Product;
import com.lxkj.dmhw.bean.self.SecondKill;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.SkuPrice;
import com.lxkj.dmhw.bean.self.SucaiBean;
import com.lxkj.dmhw.bean.self.SucaiDbean;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.ProductModel;
import com.lxkj.dmhw.presenter.ProductPresenter;
import com.lxkj.dmhw.service.TimeService;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.UMShareUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.CenterAlignImageSpan;
import com.lxkj.dmhw.widget.MyScrollView;
import com.lxkj.dmhw.widget.PredicateLayout;
import com.lxkj.dmhw.widget.TextPrograssBar;
import com.lxkj.dmhw.widget.banner.CommonShareDialog;
import com.lxkj.dmhw.widget.banner.ProductSimpleImageBanner;
import com.lxkj.dmhw.widget.dialog.BaseProductDialog;
import com.lxkj.dmhw.widget.dialog.CommonTipDialogNew;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.lxkj.dmhw.widget.dialog.DHTZDialog;
import com.lxkj.dmhw.widget.dialog.GetCouponDialog;
import com.lxkj.dmhw.widget.dialog.GiftReturnRoleDialog;
import com.lxkj.dmhw.widget.dialog.JoinGroupDialog;
import com.lxkj.dmhw.widget.dialog.MarqueeGroupAdapter;
import com.lxkj.dmhw.widget.dialog.ProductDialog;
import com.lxkj.dmhw.widget.dialog.ProductInfoDialog;
import com.lxkj.dmhw.widget.dialog.ProductMultipeSkuDialog;
import com.lxkj.dmhw.widget.dialog.StockTipPopupwindow;
import com.lxkj.dmhw.widget.mytab.TabLayout;
import com.lxkj.dmhw.widget.xmarquee.XMarqueeView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductInfoActivity extends BaseLangActivity<ProductPresenter> {
    public static boolean GO_MAINPLAY = false;//是否需要定位到礼包列表
    public static final int REQ_COLLECTI = 1001;
    public static final int REQ_CART = 1002;
    public static final int REQ_JINGXUAN = 1003;
    public static final int REQ_WZ_PAY = 1004;
    public static final int REQ_BUY = 1005;
    public static final int REQ_ADD_TO_CART = 1006;
    public static final int REQ_DHTZ = 1007;
    public static final int REQ_GET_COUPON = 1008;
    public static final int REQ_XIANGMAI = 1009;
    public static final int REQ_LIANXIKEFU = 1010;//联系客服
    public static final int REQ_GROUPZ_PAY = 1011;
    public static final int REQ_GROUPZ_PAY_COMPLATE = 1012;//支付完成
    public static final int REQ_INBUY_ADD = 1013;//加入内购库
    public static final int REQ_WANZHU = 1014;
    public final int REQ_REFRESH = 1020;//刷新
    public final static int REQ_REFRESH_ZGCART_INFO = 1020;//刷新
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.m_statusBar)
    View mStatusBar;
    @BindView(R.id.lang_iv_back)
    ImageView langIvBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;

    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_is_empty)
    TextView tvIsEmpty;
    @BindView(R.id.view_cart)
    View viewCart;
    @BindView(R.id.menu_dot)
    TextView menuDot;
    @BindView(R.id.view_button_line)
    View viewButtonLine;
    @BindView(R.id.ll_jingxuan)
    LinearLayout llJingxuan;
    @BindView(R.id.tv_dhtz)
    TextView tvDhtz;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_economize)
    TextView tvEconomize;
    @BindView(R.id.ll_wanzhu_pay)
    LinearLayout llWanzhuPay;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.ll_wanzhu_share)
    LinearLayout llWanzhuShare;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_add_to_cart)
    TextView tvAddToCart;
    @BindView(R.id.iv_wanzhu)
    ImageView ivWanzhu;
    @BindView(R.id.view_top_line)
    View viewTopLine;
    @BindView(R.id.tv_upgrade_wanzhu)
    TextView tvUpgradeWanzhu;
    @BindView(R.id.iv_jingxuan)
    ImageView ivJingxuan;
    @BindView(R.id.tv_jingxuan)
    TextView tv_jingxuan;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.sib)
    ProductSimpleImageBanner sibSimpleUsage;
    @BindView(R.id.tv_second_skill_start)
    TextView tvSecondSkillStart;
    @BindView(R.id.ll_pre_second_skill)
    LinearLayout llPreSecondSkill;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_sec)
    TextView tvSec;
    @BindView(R.id.tv_skill_tag)
    TextView tv_skill_tag;
    @BindView(R.id.ll_second_skill)
    LinearLayout llSecondSkill;
    @BindView(R.id.tv_second_skill_stock)
    TextView tvSecondSkillStock;
    @BindView(R.id.tv_product_sale_price2)
    TextView tvProductSalePrice2;
    @BindView(R.id.tv_product_price2)
    TextView tvProductPrice2;
    @BindView(R.id.tv_profit2)
    TextView tvProfit2;
    @BindView(R.id.ll_guest_price2)
    LinearLayout llGuestPrice2;
    @BindView(R.id.ll_second_skill_left)
    LinearLayout llSecondSkillLeft;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_price_label)
    TextView tvPriceLabel;
    @BindView(R.id.tv_product_sale_price)
    TextView tvProductSalePrice;
    @BindView(R.id.tv_top_profit)
    TextView tvTopProfit;
    @BindView(R.id.ll_guest_price)
    LinearLayout llGuestPrice;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_inbuy_price)
    TextView tv_inbuy_price;
    @BindView(R.id.ll_price_inbuy)
    LinearLayout ll_price_inbuy;
    @BindView(R.id.progress_bar)
    TextPrograssBar progressBar;
    @BindView(R.id.tv_sale_volume)
    TextView tvSaleVolume;
    @BindView(R.id.tv_sale_volume2)
    TextView tvSaleVolume2;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_desc)
    TextView tvProductDesc;
    @BindView(R.id.tv_return_money)
    TextView tvReturnMoney;
    @BindView(R.id.ll_return_money)
    LinearLayout llReturnMoney;
    @BindView(R.id.ll_coupon_content)
    LinearLayout llCouponContent;
    @BindView(R.id.ll_get_coupon)
    LinearLayout llGetCoupon;
    @BindView(R.id.ll_activity_content)
    LinearLayout llActivityContent;
    @BindView(R.id.rl_activity)
    RelativeLayout rlActivity;
    @BindView(R.id.ll_postfee_new)
    LinearLayout ll_postfee_new;
    @BindView(R.id.rl_posttip)
    RelativeLayout rl_posttip;
    @BindView(R.id.iv_good_brand)
    CircleImageView iv_good_brand;
    @BindView(R.id.tv_post_type)
    TextView tv_post_type;
    @BindView(R.id.ll_send_house)
    LinearLayout ll_send_house;
    @BindView(R.id.tv_post_type_new)
    TextView tv_post_type_new;
    @BindView(R.id.tv_postfee_tip)
    TextView tv_postfee_tip;
    @BindView(R.id.tv_product_info)
    TextView tvProductInfo;
    @BindView(R.id.product_info)
    LinearLayout productInfo;
    @BindView(R.id.iv_brand_logo)
    ImageView ivBrandLogo;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_sale_num)
    TextView tvSaleNum;
    @BindView(R.id.ll_brand)
    LinearLayout llBrand;
    @BindView(R.id.ll_twxq_images)
    LinearLayout ll_twxq_images;
    @BindView(R.id.iv_notes)
    ImageView ivNotes;
    @BindView(R.id.sv_main)
    MyScrollView svMain;
    @BindView(R.id.tv_twxq_title)
    TextView tvTwxqTitle;
    @BindView(R.id.ll_goodinfo_tip)
    LinearLayout ll_goodinfo_tip;
    @BindView(R.id.ll_bzq_row)
    LinearLayout ll_bzq_row;
    @BindView(R.id.ll_syff_row)
    LinearLayout ll_syff_row;
    @BindView(R.id.tv_goodinfo_bzq)
    TextView tv_goodinfo_bzq;
    @BindView(R.id.tv_goodinfo_syff)
    TextView tv_goodinfo_syff;
    @BindView(R.id.ll_twxq_title)
    LinearLayout llTwxqTitle;
    @BindView(R.id.tv_natura_title)
    TextView tvNaturaTitle;
    @BindView(R.id.ll_natura_title)
    LinearLayout llNaturaTitle;
    @BindView(R.id.ll_natura_images)
    LinearLayout ll_natura_images;
    @BindView(R.id.ll_qa_title)
    LinearLayout ll_qa_title;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.ll_brand_country)
    LinearLayout ll_brand_country;
    @BindView(R.id.iv_good_country)
    CircleImageView iv_good_country;
    @BindView(R.id.tv_good_country)
    TextView tv_good_country;
    @BindView(R.id.ll_skill_layout1)
    LinearLayout ll_skill_layout1;
    @BindView(R.id.ll_skill_layout2)
    LinearLayout ll_skill_layout2;
    @BindView(R.id.tv_skill_md)
    TextView tv_skill_md;
    @BindView(R.id.tv_skill_hm)
    TextView tv_skill_hm;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;
    @BindView(R.id.iv_back_to_top)
    ImageView ivBackTop;
    //    @BindView(R.id.video_item_player)
//    SampleCoverVideo player;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_picture)
    TextView tvPicture;
    @BindView(R.id.ll_banner_switch)
    LinearLayout ll_banner_switch;
    @BindView(R.id.tv_banner_index)
    TextView tvIndex;
    @BindView(R.id.tv_banner_length)
    TextView tvLength;
    @BindView(R.id.ll_indicator)
    LinearLayout ll_indicator;
    @BindView(R.id.rl_jifen)
    RelativeLayout rl_jifen;
    @BindView(R.id.tv_jifen_tip)
    TextView tv_jifen_tip;
    @BindView(R.id.iv_jifen_right)
    ImageView iv_jifen_right;
    @BindView(R.id.iv_xiangmai)
    ImageView iv_xiangmai;
    @BindView(R.id.ll_zizhi)
    LinearLayout ll_zizhi;
    @BindView(R.id.ll_group_buy)
    LinearLayout ll_group_buy;
    @BindView(R.id.tv_group_buy_price)
    TextView tv_group_buy_price;
    @BindView(R.id.ll_no_group_buy)
    LinearLayout ll_no_group_buy;
    @BindView(R.id.tv_no_group_buy_price)
    TextView tv_no_group_buy_price;
    //    @BindView(R.id.tv_up_wanzhu_zhuan)
//    TextView tvUpWanzhuZhuan;
//    @BindView(R.id.tv_up_wanzhu_yinian1)
//    TextView tvUpWanzhuYinian1;
//    @BindView(R.id.tv_up_wanzhu_yinian_jine1)
//    TextView tvUpWanzhuYinianJine1;
//    @BindView(R.id.ll_up_wanzhu_info1)
//    LinearLayout llUpWanzhuInfo1;
//    @BindView(R.id.tv_up_wanzhu_yinian2)
//    TextView tvUpWanzhuYinian2;
//    @BindView(R.id.tv_up_wanzhu_yinian_jine2)
//    TextView tvUpWanzhuYinianJine2;
//    @BindView(R.id.ll_up_wanzhu_info2)
//    LinearLayout llUpWanzhuInfo2;
//    @BindView(R.id.tv_up_wanzhu)
//    TextView tvUpWanzhu;
//    @BindView(R.id.ll_up_wanzhu)
//    LinearLayout llUpWanzhu;
    @BindView(R.id.tv_xiangmai)
    TextView tv_xiangmai;
    @BindView(R.id.iv_wanzhu_up)
    ImageView iv_wanzhu_up;
    @BindView(R.id.pop_stock_tip)
    LinearLayout pop_stock_tip;
    @BindView(R.id.tv_group_status)
    TextView tvGroupStatus;
    @BindView(R.id.xmv_group_list)
    XMarqueeView xmvGroupList;
    @BindView(R.id.ll_group_status)
    LinearLayout llGroupStatus;
    @BindView(R.id.ll_group)
    LinearLayout llGroup;
    @BindView(R.id.rl_price_progress)
    RelativeLayout rl_price_progress;
    @BindView(R.id.ll_qipao)
    LinearLayout ll_qipao;
    @BindView(R.id.civ_user_head)
    CircleImageView civ_user_head;
    @BindView(R.id.tv_qipao_info)
    TextView tv_qipao_info;
    @BindView(R.id.ll_look_more)
    LinearLayout ll_look_more;
    @BindView(R.id.rl_inbuy)
    RelativeLayout rl_inbuy;
    @BindView(R.id.tv_inbuy_tip)
    TextView tv_inbuy_tip;

    @BindView(R.id.ll_add_inbuy)
    LinearLayout ll_add_inbuy;
    @BindView(R.id.iv_add_inbuy)
    ImageView iv_add_inbuy;
    @BindView(R.id.tv_add_inbuy)
    TextView tv_add_inbuy;
    @BindView(R.id.tv_inbuy_label)
    TextView tv_inbuy_label;
    @BindView(R.id.ll_skill_tag)
    LinearLayout ll_skill_tag;

    @BindView(R.id.ll_sucai)
    LinearLayout ll_sucai;
    @BindView(R.id.iv_sucai_headimg)
    CircleImageView iv_sucai_headimg;//素材模块
    @BindView(R.id.tv_sucai_num)
    TextView tv_sucai_num;
    @BindView(R.id.ll_look_more_sucai)
    LinearLayout ll_look_more_sucai;
    @BindView(R.id.tv_sucai_name)
    TextView tv_sucai_name;
    @BindView(R.id.tv_sucai_time)
    TextView tv_sucai_time;
    @BindView(R.id.tv_sucai_title)
    TextView tv_sucai_title;
    @BindView(R.id.tv_sucai_content)
    TextView tv_sucai_content;
    @BindView(R.id.ll_sucai_imgs)
    LinearLayout ll_sucai_imgs;
    @BindView(R.id.ll_newgift_buy)
    LinearLayout ll_newgift_buy;
    @BindView(R.id.tv_newgift_buy)
    TextView tv_newgift_buy;
    @BindView(R.id.tv_newgift_buy_tip)
    TextView tv_newgift_buy_tip;
    @BindView(R.id.ll_living)
    LinearLayout ll_living;
    @BindView(R.id.ll_gift_share)
    LinearLayout ll_gift_share;
    @BindView(R.id.tv_share_profit)
    TextView tv_share_profit;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;
    @BindView(R.id.btn_zg_buy)
    TextView btnZgBuy;
    @BindView(R.id.tv_zg_goods_num)
    TextView tvZgGoodsNum;
    @BindView(R.id.rl_zg_add_to_cart)
    RelativeLayout rlZgAddToCart;
    @BindView(R.id.ll_zg_price)
    LinearLayout llZgPrice;
    @BindView(R.id.pl_zg_sku_price)
    PredicateLayout pl_zg_sku_price;


    //    private ProductInfoAdapter adapter;
//    private ProductInfoAdapter naturaAdapter;
    private float minHeaderHeight;//顶部最低高度，即Banner的高度
    private float headerHeight;//顶部图片高度
    private LinearLayout mTabStrip;
    private String goodsId;
    private int type = 0;//玩客0，玩主1
    private GoodBean goodBean;
    private boolean isCollect;//是否收藏
    private boolean isJingxuan;//是否精选
    private GetCouponDialog getCouponDialog;
    private BaseProductDialog productDialog;
    private BaseProductDialog groupDialog;
    private int requestCode;//
    private ArrayList<GoodSku> buySkuList;//购买商品列表
    private int bannerHeight;
    private boolean isScrolling;
    private Product product;
    private Activity activity;
    private PopTipWindow popTipWindow;
    private int twxqTop, naturaTop, qaTop;
    private boolean hasVideo;//banner中是否包含视频
    private int currentP;//当前显示图片pos
    private boolean canJump;
    private boolean canLeft;
    private List<NewsBean> list = new ArrayList<>();
    private boolean clickVideo;//是否点击视频按钮
    private StockTipPopupwindow popupwindow;
    private boolean flag = true;//是否在十秒倒计时内

    private String noteTip;//记录显示价格和赚相关 用户客服商品消息

    private MarqueeGroupAdapter marqueeGroupAdapter;
    private JoinGroupDialog joinGroupDialog;
    private CommonTipDialogNew wanfaDialog;

    private ConfirmDialog noGroupStockDialog;

    private boolean isJihuo;//是否集货团
    private Group joinGroup;//要参的团，为null代表开团
    private Group shareGroup;//需要分享的团
    private boolean isDestory;//是否被销毁
    private boolean needShowDialog;//是否需要显示查看全部团

    //内购id
    private long withinbuyId;

    private boolean isAddInbuy;//是否已加内购
    private SucaiBean sucaiBean;//商品素材
    private boolean ifNewUserGoodsAdd;//是否是新人领取
    private String videoId;//短视频id


//    private boolean isLive;//是否直播过来
//    private String videoUrl;

//    private int zgState;//0=普通商品 1专柜可单独购买 2专柜不可单独购买

    @Override

    public int getLayoutId() {
        return R.layout.activity_product_info;
    }

    @Override
    public void initView() {
        svMain.setVisibility(View.GONE);
        activity = this;
        goodsId = getIntent().getStringExtra("goodsId");
        withinbuyId = getIntent().getLongExtra("withinbuyId", 0);
        videoId = getIntent().getStringExtra("videoId");
        String newGift = getIntent().getStringExtra("newGift");

        //测试
//        newGift="1";

        if ("1".equals(newGift)) {
            ifNewUserGoodsAdd = true;
        }
//        isLive=getIntent().getBooleanExtra("isLive",false);
//        videoUrl = getIntent().getStringExtra("videoUrl");

//        if (!BBCUtil.isEmpty(videoUrl)){
//            initWindown();
//        }

        //测试
//        goodsId = "256595020031834";
//        goodsId="256595020029523";
//        withinbuyId=263049170000128l;

        //点击埋点普通商品详情
        MobclickAgent.onEvent(ProductInfoActivity.this, "good_normal", goodsId);

        initLoading();
        initStatusBar();
        minHeaderHeight = getResources().getDimension(R.dimen.dp_50);
        int width = BBCUtil.getDisplayWidth(this);
        headerHeight = (int) (width * 470.0 / 640);

        bannerHeight = (int) (width * 600.0 / 640 - minHeaderHeight);


//        svMain.setFocusable(true);
//        svMain.setFocusableInTouchMode(true);
//        svMain.requestFocus();
//        lvProductImages.setFocusable(false);
        svMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isScrolling = false;
                return false;
            }
        });
        tabLayout.setVisibility(View.GONE);
        rlTop.setBackgroundColor(Color.argb((int) (0 * 255), 255, 255, 255));
        viewTopLine.setBackgroundColor(Color.argb((int) (0 * 255), 237, 237, 237));
        svMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (product != null) {
                    if (product.getDetailImgs() != null && product.getDetailImgs().size() > 0) {
                        twxqTop = llTwxqTitle.getTop();
                    }
                    if (product.getNaturalImgs() != null && product.getNaturalImgs().size() > 0) {
                        naturaTop = llNaturaTitle.getTop();
                    }
                    qaTop = ll_qa_title.getTop();
                }
            }
        });

        svMain.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                Rect globalRect = new Rect();
                boolean visibile = tv_xiangmai.getGlobalVisibleRect(globalRect);
                if (flag && popupwindow != null) {
                    if (visibile) {
                        // 标签显示时
                        if (!popupwindow.isShowing()) {
                            popupwindow.showEditPopup(tv_xiangmai);
                        } else {
                            popupwindow.updateView(tv_xiangmai);
                        }
                    } else {
                        // 标签隐藏时
                        popupwindow.dismiss();
                    }
                }
                //Y轴偏移量
                if (Math.abs(scrollY) > BBCUtil.getDisplayHeight(ProductInfoActivity.this)) {
                    ivBackTop.setVisibility(View.VISIBLE);
                } else {
                    ivBackTop.setVisibility(View.GONE);
                }
                if (!isScrolling && tabLayout.getTabCount() > 0) {
                    if (scrollY <= twxqTop - minHeaderHeight) {
                        tabLayout.getTabAt(0).select();
                    } else if (scrollY <= naturaTop - minHeaderHeight || (naturaTop == 0 && scrollY <= qaTop - minHeaderHeight)) {
                        tabLayout.getTabAt(1).select();
                    } else if (naturaTop > 0 && scrollY <= qaTop - minHeaderHeight) {
                        tabLayout.getTabAt(2).select();
                    } else {
                        tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
                    }
                }


                if (scrollY >= bannerHeight) {
                    GSYVideoManager.onPause();
                }
                //变化率
                float headerBarOffsetY = minHeaderHeight;//Toolbar与header高度的差值
                float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
                rlTop.setBackgroundColor(Color.argb((int) (offset * 255), 255, 255, 255));
                viewTopLine.setBackgroundColor(Color.argb((int) (offset * 255), 237, 237, 237));
                tabLayout.setAlpha(offset * 255);
                tabLayout.setVisibility(View.VISIBLE);
                if (scrollY > minHeaderHeight) {

                    langIvBack.setImageResource(R.mipmap.good_back);
                    if (isCollect) {
                        ivCollection.setImageResource(R.mipmap.red_collection2);
                    } else {
                        ivCollection.setImageResource(R.mipmap.black_collection2);
                    }
                    ivShare.setImageResource(R.mipmap.share2);
                } else {
//                    tabLayout.setVisibility(View.GONE);
                    langIvBack.setImageResource(R.mipmap.icon_fanhui_grye);
                    if (isCollect) {
                        ivCollection.setImageResource(R.mipmap.red_collection_grey);
                    } else {
                        ivCollection.setImageResource(R.mipmap.black_collection_black);
                    }
                    ivShare.setImageResource(R.mipmap.icon_share_grey);
                }
            }
        });

        menuDot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuDot.getLayoutParams();
                params.height = (int) getResources().getDimension(R.dimen.dp_12);
                params.width = (int) getResources().getDimension(R.dimen.dp_12);
                menuDot.setLayoutParams(params);
                menuDot.setGravity(Gravity.CENTER);
                if (s == null || s.length() == 0 || "0".equals(s)) {
                    menuDot.setVisibility(View.GONE);
                } else if (s.toString().contains("+")) {
                    params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                }
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(timeReceiver, new IntentFilter(Constants.TIME_TASK));

        //隐藏素材区域
        ll_sucai.setVisibility(View.GONE);

        //测试
        //{\"goodsId\":" + goodsId + ",\"IfCanDirtBuy\":1,\"forAnd\":1}
//        weexDialog = new WeexDialog(ProductInfoActivity.this, BBCUtil.getWeexUrl("shoppe.js", "path=/common/sku&query="
//                + ParamToJsonUtil.getInstance().putParam("goodsId",goodsId).putParam("IfCanDirtBuy",1).putParam("forAnd",1).toJson()), true);
//        if (weexDialog != null) {
//            weexDialog.showDialog();
//        }
//        weexDialog = new WeexDialog(ProductInfoActivity.this, BBCUtil.getWeexUrl("shoppe.js", "path=/common/sku&query="
//                + ParamToJsonUtil.getInstance().putParam("goodsId", goodsId).putParam("IfCanDirtBuy", 1).putParam("forAnd", 1).toJson()), true);
//        findViewById(R.id.btn_sss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               String url= BBCUtil.getWeexUrl("shoppe.js", "path=/comfirm/order&query="+ParamToJsonUtil.getInstance().putParam("tradeSkuVO",getGoodsList()).toJson());
//                if(!BBCUtil.isEmpty(url)&&url.contains("?")) {
//                    //只有 ?参数
//                    String before = url.substring(0, url.indexOf("?"));
//                    if(BBCUtil.isEmpty(before)){
//                        if(Constants.WEEX_HOST.contains("?")&&url.startsWith("?")){
//                            url=url.replace("?","&");
//                        }
//                        url= Constants.WEEX_HOST+url;
//                    }
//                }
//                if(BBCUtil.isEmpty(url)){
//                    url= Constants.WEEX_HOST;
//                }
//                Intent intent = new Intent(activity, WeexWebActivity.class);
//                intent.putExtra("url", url);//传空默认加载 WEEX_HOST
//                ActivityUtil.getInstance().start(activity, intent);
//            }
//        });
    }

    private  List<Map<String,Object>> getGoodsList() {
        List<Map<String,Object>> goodSkuList=new ArrayList<>();

        Map<String,Object> params=new HashMap<>();
        params.put("num",5);
        params.put("skuId",263151520012918l);
        goodSkuList.add(params);
        params=new HashMap<>();
        params.put("num",6);
        params.put("skuId",256615440008742l);
        goodSkuList.add(params);
        return goodSkuList;
    }


    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (presenter.model.getProduct() != null) {
                //秒杀信息
                SecondKill secondKill = presenter.model.getProduct().getSeckillActivity();
                GoodBean goodDetail = presenter.model.getProduct().getGoodsDetail();
                //特价信息
                SecondKill specialPriceInfo = product.getSpecialPriceInfo();

                if (secondKill != null && goodDetail != null && goodDetail.getActivityState() == 1) {
                    setSecondKillTime(secondKill);
                } else if (secondKill != null && goodDetail != null && goodDetail.getActivityState() == 2) {
                    //秒杀预热中
                    long targetTime = secondKill.getStartTime();
                    if (MyApplication.NOW_TIME >= targetTime) {
                        handler.sendEmptyMessage(6);
                    }
                } else if (specialPriceInfo != null && specialPriceInfo.getState() == 1) {
                    setspecialPriceTime(specialPriceInfo);
                } else if (specialPriceInfo != null && specialPriceInfo.getState() == 0) {
                    //特价预热中
                    long targetTime = specialPriceInfo.getStartTime();
                    if (MyApplication.NOW_TIME >= targetTime) {
                        handler.sendEmptyMessage(6);
                    }
                }
                //团购商品时
                if (product.getGroupGoodsDetailDto() != null) {
                    if (product.getGroupGoodsDetailDto().isOnline()) {
                        product.getGroupGoodsDetailDto().setGroupLessTime(product.getGroupGoodsDetailDto().getGroupLessTime() - 1);
                        setGroupTime(product.getGroupGoodsDetailDto().getGroupLessTime());
                    } else {
                        //团开始了就刷新
                        long targetTime = product.getGroupGoodsDetailDto().getGroupStartTime();
                        if (MyApplication.NOW_TIME >= targetTime) {
                            handler.sendEmptyMessage(6);
                        }
                    }
                }
                //刷新可参团的时间
                if (marqueeGroupAdapter != null) {
                    marqueeGroupAdapter.settime();
                }
                if (joinGroupDialog != null) {
                    joinGroupDialog.settime();
                }
            }

        }
    };

    private void setGroupTime(long lessSecond) {
        long targetTime = lessSecond;
        Map<String, String> time = TimeCalculate.getTimeMap(0, lessSecond);
        if (time != null && time.size() > 0) {
            rlTime.setVisibility(View.VISIBLE);
            String hour = time.get("hour");
            int hourNum = 0;
            if (!BBCUtil.isEmpty(hour)) {
                hourNum = Integer.parseInt(hour);
            }
            if (hourNum < 48) {
                tvHour.setText(time.get("hour"));
                tvMin.setText(time.get("min"));
                tvSec.setText(time.get("sec"));
                ll_skill_layout1.setVisibility(View.VISIBLE);
                ll_skill_layout2.setVisibility(View.GONE);
            } else {
                String mouthdayStr = DateUtil.timeStamp2Date((MyApplication.NOW_TIME + targetTime) * 1000 + "", "MM月dd日");
                String shifenStr = DateUtil.timeStamp2Date((MyApplication.NOW_TIME + targetTime) * 1000 + "", "HH:mm");
                tv_skill_md.setText(mouthdayStr);
                tv_skill_hm.setText(shifenStr + "结束");
                ll_skill_layout1.setVisibility(View.GONE);
                ll_skill_layout2.setVisibility(View.VISIBLE);
            }

        } else {
            rlTime.setVisibility(View.GONE);
            handler.sendEmptyMessage(6);
        }

    }

    private void setSecondKillTime(SecondKill secondKill) {
        //秒杀中
        long targetTime = secondKill.getEndTime();
        Map<String, String> time = TimeCalculate.getTimeMap(MyApplication.NOW_TIME, targetTime);
        if (time != null && time.size() > 0) {
            rlTime.setVisibility(View.VISIBLE);
            String hour = time.get("hour");
            int hourNum = 0;
            if (!BBCUtil.isEmpty(hour)) {
                hourNum = Integer.parseInt(hour);
            }
            if (hourNum < 48) {
                tvHour.setText(time.get("hour"));
                tvMin.setText(time.get("min"));
                tvSec.setText(time.get("sec"));
                ll_skill_layout1.setVisibility(View.VISIBLE);
                ll_skill_layout2.setVisibility(View.GONE);
            } else {
                String mouthdayStr = DateUtil.timeStamp2Date(targetTime * 1000 + "", "MM月dd日");
                String shifenStr = DateUtil.timeStamp2Date(targetTime * 1000 + "", "HH:mm");
                tv_skill_md.setText(mouthdayStr);
                tv_skill_hm.setText(shifenStr + "结束");
                ll_skill_layout1.setVisibility(View.GONE);
                ll_skill_layout2.setVisibility(View.VISIBLE);
            }
        } else {
            rlTime.setVisibility(View.GONE);
            handler.sendEmptyMessage(6);
        }
    }


    private void setspecialPriceTime(SecondKill specialPriceInfo) {
        //特价中
        long targetTime = specialPriceInfo.getEndTime();
        Map<String, String> time = TimeCalculate.getTimeMap(MyApplication.NOW_TIME, targetTime);
        if (time != null && time.size() > 0) {
            rlTime.setVisibility(View.VISIBLE);
            String hour = time.get("hour");
            int hourNum = 0;
            if (!BBCUtil.isEmpty(hour)) {
                hourNum = Integer.parseInt(hour);
            }
            if (hourNum < 48) {
                tvHour.setText(time.get("hour"));
                tvMin.setText(time.get("min"));
                tvSec.setText(time.get("sec"));
                ll_skill_layout1.setVisibility(View.VISIBLE);
                ll_skill_layout2.setVisibility(View.GONE);
            } else {
                String mouthdayStr = DateUtil.timeStamp2Date(targetTime * 1000 + "", "MM月dd日");
                String shifenStr = DateUtil.timeStamp2Date(targetTime * 1000 + "", "HH:mm");
                tv_skill_md.setText(mouthdayStr);
                tv_skill_hm.setText(shifenStr + "结束");
                ll_skill_layout1.setVisibility(View.GONE);
                ll_skill_layout2.setVisibility(View.VISIBLE);
            }
        } else {
            rlTime.setVisibility(View.GONE);
            handler.sendEmptyMessage(6);
        }
    }

    private void initStatusBar() {
        StatusBarUtils.translateStatusBar(this);
        mStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtils.getStatusHeight(this);
        mStatusBar.setLayoutParams(layoutParams);
        mStatusBar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        StatusBarUtils.setTextColorStatusBar(this, true);
    }

//    /**
//     * 得到ListView在Y轴上的偏移
//     */
//    public float getScrollY(AbsListView view) {
//        View c = view.getChildAt(0);
//        if (c == null)
//            return 0;
//        int firstVisiblePosition = view.getFirstVisiblePosition();
//        int top = c.getTop();
//        float headerHeight = 0;
//        if (firstVisiblePosition >= 1)
//            headerHeight = this.headerHeight;
//        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
//    }

    private void initTab() {
        tabLayout.removeAllTabs();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText("商品"));
        tabLayout.addTab(tabLayout.newTab().setText("详情"));
        if (presenter.model.getProduct() != null && presenter.model.getProduct().getNaturalImgs() != null && presenter.model.getProduct().getNaturalImgs().size() > 0) {
            tabLayout.addTab(tabLayout.newTab().setText("资质"));
        }
//        tabLayout.addTab(tabLayout.newTab().setText("须知"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    mTabStrip.setBackgroundDrawable(new ProxyDrawable2(mTabStrip));
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {

                        View tabView = mTabStrip.getChildAt(i);
                        tabView.setId(i);
                        tabView.setOnClickListener(btnClick);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        int margin = (int) getResources().getDimension(R.dimen.view_toview_two);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width= (int) (getResources().getDimension(R.dimen.tag_text_size)*2);
                        int width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        if (tabLayout.getTabMode() == TabLayout.MODE_FIXED) {
                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            tabLayout.measure(w, h);
                            int screenWidth = tabLayout.getMeasuredWidth();
//                            margin = (int) ((screenWidth / mTabStrip.getChildCount()) / 2.0);
//                            margin = (int) ((screenWidth / mTabStrip.getChildCount() - width) / 2.0);
                        }
                        params.leftMargin = margin;
                        params.rightMargin = margin;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tabLayout.getTabAt(0).select();
    }

    @OnClick(R.id.iv_back_to_top)
    public void backTop() {
        svMain.smoothScrollTo(0, 0);
//        ivBackTop.setVisibility(View.GONE);
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case 0:
                    isScrolling = true;
                    svMain.smoothScrollTo(0, 0);
//                    lvProduct.smoothScrollToPosition(0);
                    break;
                case 1:
                    isScrolling = true;
                    svMain.smoothScrollTo(0, twxqTop - (int) minHeaderHeight);
//                    lvProduct.setSelectionFromTop(1, (int) minHeaderHeight);

                    break;
                case 2:
                    isScrolling = true;
                    if (tabLayout.getTabCount() == 3) {
                        svMain.smoothScrollTo(0, qaTop - (int) minHeaderHeight);
//                        lvProduct.setSelectionFromTop(adapter.getCount() - 1, (int) minHeaderHeight);
                    } else {
                        svMain.smoothScrollTo(0, naturaTop - (int) minHeaderHeight);
//                        lvProduct.setSelectionFromTop(adapter.getCount() - 1 - presenter.model.getProduct().getNaturalImgs().size(), (int) minHeaderHeight);
                    }
                    break;
                case 3:
                    isScrolling = true;
                    svMain.smoothScrollTo(0, qaTop - (int) minHeaderHeight);
//                    lvProduct.setSelectionFromTop(adapter.getCount() - 1, (int) minHeaderHeight);
                    break;
            }
        }
    };

    @Override
    public void initPresenter() {
        presenter = new ProductPresenter(this, ProductModel.class);
    }

    @Override
    public void initData() {
//        showWaitDialog();
        presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
        presenter.getCollection(goodsId);
        presenter.getLastMaterial(goodsId);
        //测试
//        presenter.getGroupJoinInfo(goodsId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DateStorage.getLoginStatus() && presenter != null) {
            presenter.getCartCount();
        } else {
            menuDot.setVisibility(View.GONE);
        }
//        if (presenter.model.getGroupUserList() != null && presenter.model.getGroupUserList().size() > 0) {
//            int index = currP % presenter.model.getGroupUserList().size();
//            GroupUser user = presenter.model.getGroupUserList().get(index);
//        }

        //返回时不自动播放视频
//        GSYVideoManager.onResume();

        try {
            Intent i = new Intent(ProductInfoActivity.this, TimeService.class);
            startService(i);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (product != null && product.getGroupGoodsDetailDto() != null) {
            //团商品特殊处理 每次进入商品详情页刷新
            LogUtil.d("0.0", "---团商品详情页 onRestart");
//            showWaitDialog();
            presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
        }
        if (joinGroupDialog != null && joinGroupDialog.isShow() && product != null && product.getGroupGoodsDetailDto() != null) {
            presenter.reqJoinGroupList(goodsId, product.getGroupGoodsDetailDto().getGroupId(), product.getGroupGoodsDetailDto().getGroupType(), false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {


        if ("getProductInfo".equals(arg)) {//商品信息
            if (presenter.model.getProduct() != null && presenter.model.getProduct().getGoodsDetail() != null) {
                tvIsEmpty.setVisibility(View.GONE);
                initTab();
                product = presenter.model.getProduct();
                goodBean = presenter.model.getProduct().getGoodsDetail();
                if (BBCUtil.isEmpty(product.getLiveId())) {
                    ll_living.setVisibility(View.GONE);
                } else {
                    ll_living.setVisibility(View.VISIBLE);
                    ll_living.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
                //测试
//                goodBean.setActivityState(0);
//                goodBean.setRemainStock(10);
//                product.setGoodsDetail(goodBean);
//                presenter.model.setProduct(product);
//                    InBuyBean inBuyBean=new InBuyBean();
//                    inBuyBean.setEndTime(1578379083);
//                    inBuyBean.setMinPrice(90);
//                    inBuyBean.setMaxPrice(90);
//                    inBuyBean.setMaxIncome(10);
//                    inBuyBean.setMinIncome(10);
//                    product.setWithinPriceInfo(inBuyBean);
//                    product.setIfHavWithinBuyLibRight(true);
//                    product.setIfInWithinBuyLib(true);


                if (presenter.model.getProduct().isIfVipPutOn()) {
                    ivJingxuan.setImageResource(R.mipmap.jingxuan);
                    isJingxuan = true;
                    tv_jingxuan.setText("已精选");
                } else {
                    ivJingxuan.setImageResource(R.mipmap.un_jingxuan);
                    isJingxuan = false;
                    tv_jingxuan.setText("精选");
                }

                tv_inbuy_label.setVisibility(View.GONE);
                ll_group_buy.setVisibility(View.GONE);
                ll_no_group_buy.setVisibility(View.GONE);
                llWanzhuPay.setVisibility(View.GONE);
                llWanzhuShare.setVisibility(View.GONE);
                tvBuy.setVisibility(View.GONE);
                ll_newgift_buy.setVisibility(View.GONE);
                tvAddToCart.setVisibility(View.GONE);
                tvDown.setVisibility(View.GONE);
                tvDhtz.setVisibility(View.GONE);
                rl_inbuy.setVisibility(View.GONE);
                ll_add_inbuy.setVisibility(View.GONE);

                if (type == 1) {
                    llJingxuan.setVisibility(View.VISIBLE);
                }


                Group group = product.getGroupGoodsDetailDto();
                if (group != null) {//团购商品

                    //想买按钮隐藏
                    iv_xiangmai.setVisibility(View.GONE);
                    //底部按钮
                    llGroup.setVisibility(View.VISIBLE);
                    ll_group_buy.setVisibility(View.VISIBLE);
                    ll_group_buy.setBackgroundResource(R.drawable.bg_gradient_red2);
                    ll_no_group_buy.setVisibility(View.VISIBLE);
                    ll_no_group_buy.setBackgroundResource(R.drawable.bg_gradient_red3);
                    SpannableString ss1 = new SpannableString("¥" + BBCUtil.getDoubleFormat(group.getGroupMinPrice()) + "起");
                    ss1.setSpan(new AbsoluteSizeSpan((int) getResources().getDimension(R.dimen.ysf_text_size_10)), ss1.length() - 1, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_group_buy_price.setText(ss1);
                    SpannableString ss2 = new SpannableString("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice()) + "起");
                    ss2.setSpan(new AbsoluteSizeSpan((int) getResources().getDimension(R.dimen.ysf_text_size_10)), ss2.length() - 1, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_no_group_buy_price.setText(ss2);

                    if (goodBean.getRemainStock() <= 0) {//商品库存售罄
                        tvIsEmpty.setVisibility(View.VISIBLE);
                        tvIsEmpty.setText("商品被抢完啦，正在补货中～");
                        ll_no_group_buy.setBackgroundResource(R.color.colorBlackText2);
                    }

                    if (group.isStockNervous()) {
                        tvIsEmpty.setVisibility(View.VISIBLE);
                        tvIsEmpty.setText("该商品库存告急！！！");
                    }

                    if (group.getGroupLessStock() <= 0) {//团购库存售罄
                        tvIsEmpty.setVisibility(View.VISIBLE);
                        tvIsEmpty.setText("来晚了，拼团商品被抢完啦～");
                        ll_group_buy.setBackgroundResource(R.color.colorBlackText2);
                    }


                    if (group.getGroupLessStock() <= 0 && goodBean.getRemainStock() <= 0) {
                        //团购库存售罄 商品库存售罄 同时售罄时 显示到货通知
                        ll_no_group_buy.setVisibility(View.GONE);
                        ll_group_buy.setVisibility(View.GONE);
                        tvDhtz.setVisibility(View.VISIBLE);
                        rl_inbuy.setVisibility(View.GONE);
                        ll_add_inbuy.setVisibility(View.GONE);
                        llJingxuan.setVisibility(View.GONE);
                    }


                    if (type == 0) {
                        if (!BBCUtil.isEmpty(product.getUpdateVipTsImg())) {
                            int w = (int) (BBCUtil.getDisplayWidth(this) - getResources().getDimension(R.dimen.dp_28));
                            int h = (int) (w * 50.0 / 345);
                            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
                            p.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.view_toview_two));
                            iv_wanzhu_up.setLayoutParams(p);
                            iv_wanzhu_up.setVisibility(View.VISIBLE);
                            ImageLoadUtils.doLoadImageUrl(iv_wanzhu_up, product.getUpdateVipTsImg());
                        }
                    }


                    if (!group.isOnline() || (group.getGroupType() == 2 && DateStorage.getLoginStatus() && type == 0)) {//团未上线或团长免费团玩客登录要不亮
                        ll_group_buy.setBackgroundResource(R.color.colorBlackText2);
                    }

                } else {
                    tvProfit.setText("立赚¥" + BBCUtil.getDoubleFormat(goodBean.getProfitAmount()) + "起");
                    tvEconomize.setText("立省¥" + BBCUtil.getDoubleFormat(goodBean.getMinIncome()) + "起");
                    llGroup.setVisibility(View.GONE);

                    if (type == 1) {//玩主
                        iv_wanzhu_up.setVisibility(View.GONE);
                        llWanzhuPay.setVisibility(View.VISIBLE);
                        llWanzhuShare.setVisibility(View.VISIBLE);
                        tvBuy.setVisibility(View.GONE);
                        tvAddToCart.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llWanzhuPay.getLayoutParams();
                        params.weight = 2;
                        llWanzhuShare.setVisibility(View.VISIBLE);
                        tvEconomize.setVisibility(View.VISIBLE);
                    } else {//玩客
                        if (!BBCUtil.isEmpty(product.getUpdateVipTsImg())) {
                            int w = (int) (BBCUtil.getDisplayWidth(this) - getResources().getDimension(R.dimen.dp_28));
                            int h = (int) (w * 50.0 / 345);
                            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
                            p.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.view_toview_two));
                            iv_wanzhu_up.setLayoutParams(p);
                            iv_wanzhu_up.setVisibility(View.VISIBLE);
                            ImageLoadUtils.doLoadImageUrl(iv_wanzhu_up, product.getUpdateVipTsImg());
                        }
                        tvUpgradeWanzhu.setVisibility(View.GONE);
                        tvBuy.setVisibility(View.VISIBLE);
                        tvAddToCart.setVisibility(View.VISIBLE);
                        llWanzhuPay.setVisibility(View.GONE);
                        llWanzhuShare.setVisibility(View.GONE);
                        llJingxuan.setVisibility(View.GONE);
                    }
                    if (goodBean.getActivityState() != 1 && goodBean.getRemainStock() == 0) {//普通商品状态库存售罄
                        tvIsEmpty.setVisibility(View.VISIBLE);
                        tvDhtz.setVisibility(View.VISIBLE);
                        tvBuy.setVisibility(View.GONE);
                        tvAddToCart.setVisibility(View.GONE);
                        llWanzhuPay.setVisibility(View.GONE);
                        llWanzhuShare.setVisibility(View.GONE);
                        tvDown.setVisibility(View.GONE);
                        llJingxuan.setVisibility(View.GONE);
                        rl_inbuy.setVisibility(View.GONE);
                        ll_add_inbuy.setVisibility(View.GONE);
                    }
                    if (goodBean.getActivityState() == 1 && presenter.model.getProduct().getSeckillActivity().getRemainStock() == 0) {//普通商品状态库存售罄
                        tvIsEmpty.setVisibility(View.VISIBLE);
                        tvDhtz.setVisibility(View.GONE);
                        tvBuy.setVisibility(View.GONE);
                        tvAddToCart.setVisibility(View.GONE);
                        llWanzhuPay.setVisibility(View.GONE);
                        llWanzhuShare.setVisibility(View.GONE);
                        tvDown.setVisibility(View.VISIBLE);
                        tvDown.setText("已抢光");
                        tvUpgradeWanzhu.setVisibility(View.GONE);
                    }
                }

                if (goodBean.getActivityState() == -1) {//已下架
                    tvDown.setText("已下架");
                    tvIsEmpty.setVisibility(View.GONE);
                    tvDhtz.setVisibility(View.GONE);
                    tvBuy.setVisibility(View.GONE);
                    tvAddToCart.setVisibility(View.GONE);
                    llWanzhuPay.setVisibility(View.GONE);
                    llWanzhuShare.setVisibility(View.GONE);
                    tvDown.setVisibility(View.VISIBLE);
                    llJingxuan.setVisibility(View.GONE);
                    rl_inbuy.setVisibility(View.GONE);
                    ll_add_inbuy.setVisibility(View.GONE);
                }

                initProductInfo(group);

                //是否是新人领取
                if (ifNewUserGoodsAdd) {
                    tvUpgradeWanzhu.setVisibility(View.GONE);
                    tvDhtz.setVisibility(View.GONE);
                    tvDown.setVisibility(View.GONE);
                    llWanzhuPay.setVisibility(View.GONE);
                    llWanzhuShare.setVisibility(View.GONE);
                    tvAddToCart.setVisibility(View.GONE);
                    tvBuy.setVisibility(View.GONE);
                    ll_no_group_buy.setVisibility(View.GONE);
                    ll_group_buy.setVisibility(View.GONE);
                    ll_newgift_buy.setVisibility(View.VISIBLE);

                    if (goodBean.isSellOut() || goodBean.getRemainStock() <= 0) {
                        //已领完
                        ll_newgift_buy.setClickable(false);
                        ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                        tv_newgift_buy.setText("已领完");
                        tv_newgift_buy_tip.setText("再看看其他商品吧");
                    } else if (product.getNewUserBuySate() == 0) {
                        //未领取的状态
                        ll_newgift_buy.setClickable(true);
                        ll_newgift_buy.setBackgroundResource(R.drawable.bg_btn_gradient);
                        tv_newgift_buy.setText("立即领取");
                        tv_newgift_buy_tip.setText("放入购物车");

                        if (!product.isIfNewUser() && DateStorage.getLoginStatus()) {
                            //不是新人
                            ll_newgift_buy.setClickable(false);
                            ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                            tv_newgift_buy.setText("已失效");
                            tv_newgift_buy_tip.setText("超时未领取");
                        }

                    } else if (product.getNewUserBuySate() == 1) {
                        //当前商品已购买
                        ll_newgift_buy.setClickable(false);
                        ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                        tv_newgift_buy.setText("无法领取");
                        tv_newgift_buy_tip.setText("已购买过新人礼商品");
                    } else if (product.getNewUserBuySate() == 2) {
                        //当前商品已加购
                        ll_newgift_buy.setClickable(false);
                        ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                        tv_newgift_buy.setText("领取成功");
                        tv_newgift_buy_tip.setText("已放入购物车");
                    } else if (product.getNewUserBuySate() == 3) {
                        //其他商品已购买
                        ll_newgift_buy.setClickable(false);
                        ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                        tv_newgift_buy.setText("无法领取");
                        tv_newgift_buy_tip.setText("已购买过新人礼商品");
                    } else if (product.getNewUserBuySate() == 4) {
                        //其他商品已加购
                        ll_newgift_buy.setClickable(false);
                        ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                        tv_newgift_buy.setText("无法领取");
                        tv_newgift_buy_tip.setText("购物车已有新人礼商品");
                    }
                }


                ImageLoadUtils.doLoadImageUrl(ivNotes, product.getBuyInfoImgUrl());
                ivNotes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!BBCUtil.isEmpty(product.getBuyInfoImgUrl())) {
                            List<String> imglist = new ArrayList<String>();
                            imglist.add(product.getBuyInfoImgUrl());
                            BBCUtil.openImgPreview(activity, 0, imglist, false);
                        }
                    }
                });
                tvTwxqTitle.setText("图文详情");
                tvNaturaTitle.setText("资质详情");

                //测试
//                product.setShelf("36个月");
//                product.setGoodsUsage("开启即食");
                GoodBean goodDetail = product.getGoodsDetail();
                if ((product.getDetailImgs() != null && product.getDetailImgs().size() > 0)
                        || (goodDetail != null && !BBCUtil.isEmpty(goodDetail.getShelf()))
                        || (goodDetail != null && !BBCUtil.isEmpty(goodDetail.getGoodsUsage()))) {
                    llTwxqTitle.setVisibility(View.VISIBLE);
                } else {
                    llTwxqTitle.setVisibility(View.GONE);
                }
                if (goodDetail != null && (!BBCUtil.isEmpty(goodDetail.getShelf()) || !BBCUtil.isEmpty(goodDetail.getGoodsUsage()))) {
                    //显示保质期 和 使用方法
                    ll_goodinfo_tip.setVisibility(View.VISIBLE);
                    if (!BBCUtil.isEmpty(goodDetail.getShelf())) {
                        ll_bzq_row.setVisibility(View.VISIBLE);
                        tv_goodinfo_bzq.setText(goodDetail.getShelf());
                    } else {
                        ll_bzq_row.setVisibility(View.GONE);
                    }
                    if (!BBCUtil.isEmpty(goodDetail.getGoodsUsage())) {
                        ll_syff_row.setVisibility(View.VISIBLE);
                        tv_goodinfo_syff.setText(goodDetail.getGoodsUsage());
                    } else {
                        ll_syff_row.setVisibility(View.GONE);
                    }

                } else {
                    ll_goodinfo_tip.setVisibility(View.GONE);
                }

                if (product.getDetailImgs() != null && product.getDetailImgs().size() > 0) {
                    ll_twxq_images.setVisibility(View.VISIBLE);
                    List<ImageView> imageViewList = BBCUtil.addImages(ProductInfoActivity.this, product.getDetailImgs(), ll_twxq_images);
                    if (imageViewList.size() > 0) {
                        for (ImageView img : imageViewList) {
                            img.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    return false;
                                }
                            });
                        }
                    }

                } else {
                    ll_twxq_images.setVisibility(View.GONE);
                }
                if (BBCUtil.isEmpty(product.getAptitudeWeexUrl())) {
                    ll_zizhi.setVisibility(View.GONE);
                } else {
                    ll_zizhi.setVisibility(View.VISIBLE);

                }


                if (product.getNaturalImgs() != null && product.getNaturalImgs().size() > 0) {
                    llNaturaTitle.setVisibility(View.VISIBLE);
                    ll_natura_images.setVisibility(View.VISIBLE);
                    BBCUtil.addImages(ProductInfoActivity.this, product.getNaturalImgs(), ll_natura_images);
                } else {
                    ll_natura_images.setVisibility(View.GONE);
                    llNaturaTitle.setVisibility(View.GONE);
                }


                if (goodBean.getType() == 3) {
                    //批零商品
                    llBottom.setVisibility(View.GONE);
                    ivCollection.setVisibility(View.GONE);
                    tvIsEmpty.setVisibility(View.GONE);//售罄tip隐藏
                } else {
                    llBottom.setVisibility(View.VISIBLE);
                    ivCollection.setVisibility(View.VISIBLE);
                }

                if (!product.isIfCanBuy() && type == 1) {
                    //玩主不可购买的商品
                    viewButtonLine.setVisibility(View.GONE);
                    rl_cart.setVisibility(View.GONE);
                    llJingxuan.setVisibility(View.GONE);
                    ll_add_inbuy.setVisibility(View.GONE);
                    ll_gift_share.setVisibility(View.VISIBLE);
                    tv_share_profit.setText("立赚¥" + BBCUtil.getDoubleFormat(goodBean.getProfitAmount()));
                    if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
                        //秒杀赚
                        tv_share_profit.setText("立赚¥" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getProfitAmount()));
                    } else if (product.getSpecialPriceInfo() != null && product.getSpecialPriceInfo().getState() == 1) {
                        //特价赚
                        tv_share_profit.setText("立赚¥" + BBCUtil.getDoubleFormat(product.getSpecialPriceInfo().getProfitAmount()));
                    }
                    llWanzhuPay.setVisibility(View.GONE);
                    tvDown.setVisibility(View.GONE);
                    tvDhtz.setVisibility(View.GONE);
                    tvUpgradeWanzhu.setVisibility(View.GONE);
                    llWanzhuShare.setVisibility(View.GONE);
                    tvAddToCart.setVisibility(View.GONE);
                    tvBuy.setVisibility(View.GONE);
                    ll_no_group_buy.setVisibility(View.GONE);
                    ll_group_buy.setVisibility(View.GONE);
                    ll_newgift_buy.setVisibility(View.GONE);

                } else {
                    //登录成功后的处理
                    if (requestCode == REQ_COLLECTI) {
                        if (!isCollect) {
                            presenter.addToCollection(goodsId);
                        } else {
                            presenter.delToCollection(goodsId);
                        }
                    }
                    if (requestCode == REQ_CART) {
                        Intent intent = new Intent(this, CartActivity.class);
                        ActivityUtil.getInstance().startResult(this, intent, REQ_REFRESH);
                    }
                    if (requestCode == REQ_JINGXUAN) {
                        if (!isJingxuan) {
                            presenter.addToJingxuan(goodsId);
                        } else {
                            presenter.delToJingxuan(goodsId);
                        }
                    }
                    if (requestCode == REQ_WZ_PAY) {
                        showProductDialog(0);
                    }
                    if (requestCode == REQ_GROUPZ_PAY) {
                        if (group != null) {
                            if (!group.isOnline() || group.getGroupLessStock() <= 0 || (DateStorage.getLoginStatus() && type != 1 && group.getGroupType() == 2)) {
                                //未上线、团购库存已售罄、非玩主进入团长免费团不能开团、已登录的玩客用户不能开团长免费团

                            } else {
                                showGroupDialog(joinGroup == null);
                                if (joinGroup == null) {
                                    groupDialog.setIsJoin(false);
                                } else {
                                    groupDialog.setIsJoin(true);
                                }
                            }
                        }
                    }
                    if (requestCode == REQ_BUY) {
                        if (isInbuyProduct()) {
                            showProductDialog(-3);
                        } else {
                            showProductDialog(type == 1 ? 0 : 2);
                        }
                    }
                    if (requestCode == REQ_ADD_TO_CART) {
                        if (isInbuyProduct()) {
                            showProductDialog(-2);
                        } else {
                            showProductDialog(type == 1 ? 0 : 1);
                        }
                    }
                    if (requestCode == REQ_DHTZ) {
                        //登录成功后 到货通知请求
//                        showWaitDialog();
                        presenter.reqFindNotifyTel(goodsId);
                    }
                    //状态回执
                    requestCode = -1;
                }

                if (product.getGroupGoodsDetailDto() == null) {
                    //没有团购或者是团长免费团不需要请求接口显示轮播模块
                    svMain.setVisibility(View.VISIBLE);
                    dismissWaitDialog();
                } else {
                    presenter.reqJoinGroupList(goodsId, product.getGroupGoodsDetailDto().getGroupId(), product.getGroupGoodsDetailDto().getGroupType(), true);
                }

                if (product.getSpecialShoppePrices() != null && product.getSpecialShoppePrices().size() > 0) {//专柜
                    tvSaleVolume2.setVisibility(View.VISIBLE);
                    tvSaleVolume2.setTextColor(getResources().getColor(R.color.colorBlackText2));
                    llBottom.setVisibility(View.GONE);
                    rlTime.setVisibility(View.GONE);
                    rl_price_progress.setVisibility(View.GONE);
                    llZgPrice.setVisibility(View.VISIBLE);

                    //设置价格显示
                    initZgPrice(product.getSpecialShoppePrices());

                    if (goodBean.isSellOut()|| goodBean.getRemainStock() <= 0||goodBean.getActivityState() == -1){
                        rlZgAddToCart.setVisibility(View.GONE);
                        btnZgBuy.setVisibility(View.VISIBLE);
                        if (goodBean.getActivityState() == -1){
                            btnZgBuy.setText("已下架");
                        }else{
                            btnZgBuy.setText("售罄");
                        }
                        btnZgBuy.setBackgroundResource(R.drawable.tv_key_normal_bg);
                        btnZgBuy.setTextColor(getResources().getColor(R.color.colorBlackText2));

                    }else if (product.isIfCanDirtBuy()) {
                        rlZgAddToCart.setVisibility(View.GONE);
                        btnZgBuy.setVisibility(View.VISIBLE);
                        btnZgBuy.setText("购买");
                        btnZgBuy.setBackgroundResource(R.drawable.bg_rect_red_12dp);
                        btnZgBuy.setTextColor(getResources().getColor(R.color.colorWhite));
                        btnZgBuy.setVisibility(View.VISIBLE);

                    } else {
                        btnZgBuy.setVisibility(View.GONE);
                        rlZgAddToCart.setVisibility(View.VISIBLE);
                    }
                }


            }
        } else if ("getCartCount".equals(arg)) {//购物车数量
            if (presenter.model.getCartCount() != null && presenter.model.getCartCount() > 0) {
                if (presenter.model.getCartCount() > 99) {
                    menuDot.setText("99+");
                } else {
                    menuDot.setText("" + presenter.model.getCartCount());
                }
                menuDot.setVisibility(View.VISIBLE);
            } else {
                menuDot.setVisibility(View.GONE);
            }

        } else if ("addToCollection".equals(arg)) {//添加收藏
            ToastUtil.show(ProductInfoActivity.this, "收藏成功");
            isCollect = true;
            if (tabLayout.getVisibility() == View.VISIBLE) {
                ivCollection.setImageResource(R.mipmap.red_collection2);
            } else {
                ivCollection.setImageResource(R.mipmap.red_collection_grey);
            }
        } else if ("delToCollection".equals(arg)) {
            ToastUtil.show(ProductInfoActivity.this, "已取消收藏");
            isCollect = false;
            if (tabLayout.getVisibility() == View.VISIBLE) {
                ivCollection.setImageResource(R.mipmap.black_collection2);
            } else {
                ivCollection.setImageResource(R.mipmap.black_collection_black);
            }
        } else if ("reqShareInfo".equals(arg)) {
            dismissWaitDialog();
            if (presenter.model.getShareBean() != null) {
                if (type == 1) {
                    //玩主显示赚
                    presenter.model.getShareBean().setProfit(goodBean.getProfitAmount());
                    if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
                        //秒杀赚
                        presenter.model.getShareBean().setProfit(product.getSeckillActivity().getProfitAmount());
                    } else if (product.getSpecialPriceInfo() != null && product.getSpecialPriceInfo().getState() == 1) {
                        //特价赚
                        presenter.model.getShareBean().setProfit(product.getSpecialPriceInfo().getProfitAmount());
                    }
                }

                if (sibSimpleUsage == null) {
                    return;
                }
                List<NewsBean> newsBeanList = sibSimpleUsage.getNewsBean();
                if (goodBean.getActivityState() == 1) {
                    //-1商品已下架 0正常 无活动 1秒杀活动中
                    new CommonShareDialog(this, presenter.model.getShareBean(), type, true, newsBeanList, false);
                } else {
                    new CommonShareDialog(this, presenter.model.getShareBean(), type, false, newsBeanList, false);
                }
            }
        } else if ("getCollection".equals(arg)) {
            isCollect = presenter.model.isCollection();
            if (isCollect) {
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    ivCollection.setImageResource(R.mipmap.red_collection2);
                } else {
                    ivCollection.setImageResource(R.mipmap.red_collection_grey);
                }
            } else {
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    ivCollection.setImageResource(R.mipmap.black_collection2);
                } else {
                    ivCollection.setImageResource(R.mipmap.black_collection_black);
                }
            }
        } else if ("reqCanGetCouponList".equals(arg)) {
            getCouponDialog = new GetCouponDialog(this, presenter.model.getCouponList(), handler);
        } else if ("reqGetCoupon".equals(arg)) {
            ToastUtil.show(this, "领取成功");
            getCouponDialog.refershData(presenter.model.getCouponId());
        } else if ("addNoticePhoneNumber".equals(arg)) {
            ToastUtil.show(this, "设置成功");
        } else if ("addToCart".equals(arg)) {//加入购物车成功
            if (ifNewUserGoodsAdd) {
                //领取新人商品到购物车
                product.setNewUserBuySate(2);
                ll_newgift_buy.setClickable(false);
                ll_newgift_buy.setBackgroundColor(getResources().getColor(R.color.hitTxt));
                tv_newgift_buy.setText("领取成功");
                tv_newgift_buy_tip.setText("已放入购物车");
            }
            ToastUtil.show(this, "加入购物车成功");
            productDialog.cancelDialog();
            presenter.getCartCount();
//            SDJumpUtil.refreshHomeDataIndex(HomeActivity.GO_CAR);//刷新购物车
        } else if ("addToJingxuan".equals(arg)) {
            isJingxuan = true;
            ToastUtil.show(this, "精选成功");
            ivJingxuan.setImageResource(R.mipmap.jingxuan);
            tv_jingxuan.setText("已精选");
        } else if ("delToJingxuan".equals(arg)) {
            ToastUtil.show(this, "已取消精选");
            isJingxuan = false;
            ivJingxuan.setImageResource(R.mipmap.un_jingxuan);
            tv_jingxuan.setText("精选");
        } else if ("reqProductInfo".equals(arg)) {
            goodBean.setInfo(presenter.model.getInfos());
            new ProductInfoDialog(this, goodBean);
        } else if ("reqFindNotifyTel".equals(arg)) {
            dismissWaitDialog();
            String notifyTel = presenter.model.getNotifyPhone();
            new DHTZDialog(this, handler, notifyTel);
        } else if ("reqCheckStock".equals(arg)) {
            dismissWaitDialog();
            //库存校验成功
            productDialog.cancelDialog();
            Intent intent = new Intent(ProductInfoActivity.this, SettlementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("skuList", buySkuList);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().start(ProductInfoActivity.this, intent);
        } else if ("addRealLoveNum".equals(arg)) {
            //想买点击成功
            tv_xiangmai.setText(goodBean.getActivityLoveNum() + 1 + "人想买");
            iv_xiangmai.setVisibility(View.GONE);
        } else if ("reqAllGroupList".equals(arg)) {
            if (joinGroupDialog == null) {
                joinGroupDialog = new JoinGroupDialog(this, presenter.model.getAllGroupList(), handler);
            } else {
                if (presenter.model.getAllGroupList() != null && presenter.model.getAllGroupList().size() > 0) {
                    joinGroupDialog.setGroupList(presenter.model.getAllGroupList());
                    if (needShowDialog) {
                        joinGroupDialog.showDialog();
                    }
                } else {
                    joinGroupDialog.cancelDialog();
                }
            }
            needShowDialog = false;
            dismissWaitDialog();
        } else if ("reqGroupCheckStock".equals(arg)) {
            dismissWaitDialog();
            //团购库存校验成功
            groupDialog.cancelDialog();
            Intent intent = new Intent(ProductInfoActivity.this, SettlementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("jihuo", isJihuo);//是否集货团
            bundle.putSerializable("skuList", buySkuList);
            Group group = presenter.model.getProduct().getGroupGoodsDetailDto();
            if (joinGroup != null) {//参团
                group.setId(joinGroup.getId());
            } else {
                group.setId("");
            }
            bundle.putSerializable("group", group);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().startResult(ProductInfoActivity.this, intent, REQ_GROUPZ_PAY_COMPLATE);
        } else if ("reqGroupShareInfo".equals(arg)) {
            ShareBean shareBean = presenter.model.getShareBean();
            if (shareBean != null) {
                UMShareUtil.getInstance().umengShareMin(this, shareBean, SHARE_MEDIA.WEIXIN, 0);
            }
        } else if ("getGroupJoinInfo".equals(arg)) {
            isDestory = false;
            if (presenter.model.getGroupUserList() != null && presenter.model.getGroupUserList().size() > 0) {
                handler.removeCallbacks(dmService);
                int index = currP % presenter.model.getGroupUserList().size();

                GroupUser user = presenter.model.getGroupUserList().get(index);
                ImageLoadUtils.doLoadCircleImageUrl(civ_user_head, user.getHeardUrl());
//                ImageLoadUtils.doLoadImageUrl(civ_user_head, user.getHeardUrl());
                handler.postDelayed(dmService, 5000);
            }
        } else if ("reqJoinGroupList".equals(arg)) {
            //测试代码
//            presenter.model.setJoinGroupList(getGroupData());

            if (presenter.model.getJoinGroupList() != null && presenter.model.getJoinGroupList().size() > 0) {
                List<Group> datas = presenter.model.getJoinGroupList();
                setFlipping(datas);
            } else {
                llGroupStatus.setVisibility(View.GONE);
                xmvGroupList.stopFlipping();
            }

            svMain.setVisibility(View.VISIBLE);
            dismissWaitDialog();
        } else if ("getLastMaterial".equals(arg)) {
            sucaiBean = presenter.model.getSucaiBean();
            if (sucaiBean != null && sucaiBean.getMaterialCount() > 0) {
                ll_sucai.setVisibility(View.VISIBLE);
                initSucai();
            } else {
                ll_sucai.setVisibility(View.GONE);
            }
        }
    }

    private void initZgPrice(List<SkuPrice> skuPriceList) {
        pl_zg_sku_price.removeAllViews();
        for (int i = 0; i < skuPriceList.size(); i++) {
            SkuPrice skuPrice = skuPriceList.get(i);
            LinearLayout content = new LinearLayout(activity);
            content.setOrientation(LinearLayout.VERTICAL);
            PredicateLayout.LayoutParams params = new PredicateLayout.LayoutParams(
                    PredicateLayout.LayoutParams.WRAP_CONTENT, PredicateLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, (int) activity.getResources().getDimension(R.dimen.design_15dp), 0);
            params.gravity = Gravity.CENTER;
            content.setLayoutParams(params);

            TextView tvNum = new TextView(activity);
            tvNum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvNum.setTextSize(11);
            tvNum.setTextColor(activity.getResources().getColor(R.color.colorBlackText2));
            content.addView(tvNum);
            if (i != skuPriceList.size() - 1) {
                tvNum.setText(skuPrice.getMinNum() + "-" + skuPrice.getMaxNum() + "件");
            } else {
                tvNum.setText(skuPrice.getMinNum() + "件以上");
            }

            TextView tvPrice = new TextView(activity);
            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p2.setMargins(0, (int) activity.getResources().getDimension(R.dimen.design_5dp), 0, 0);
            p2.gravity = Gravity.CENTER;
            tvPrice.setLayoutParams(p2);
            tvPrice.setTextSize(13);
            tvPrice.setTextColor(activity.getResources().getColor(R.color.colorBlackText));
            SpannableString ss = new SpannableString("¥" + skuPrice.getPrice());
            ss.setSpan(new AbsoluteSizeSpan((int) activity.getResources().getDimension(R.dimen.sp_9)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPrice.setText(ss);
            content.addView(tvPrice);
            pl_zg_sku_price.addView(content);
        }
    }

    private void setFlipping(List<Group> datas) {
        if (datas != null && datas.size() > 0) {
            if (datas.size() > 2) {
                //拼图列表和我的团列表 >2显示查看全部
                ll_look_more.setVisibility(View.VISIBLE);
            } else {
                ll_look_more.setVisibility(View.GONE);
            }

            if (datas.size() > 1 && datas.size() % 2 != 0) {//大于1个的奇数情况需要把最后一个去掉变偶数
                datas.remove(datas.size() - 1);
            }
            llGroupStatus.setVisibility(View.VISIBLE);
            if (datas.size() == 1) {//只有一个的时候不轮播
                xmvGroupList.setItemCount(1);
            } else {
                xmvGroupList.setItemCount(2);
            }
            if (marqueeGroupAdapter == null) {
                //测试代码
//                datas.get(0).setLessGroupEndTime(10);
                //测试结束
                marqueeGroupAdapter = new MarqueeGroupAdapter(datas, this, handler);
                xmvGroupList.setAdapter(marqueeGroupAdapter);
            } else {
                marqueeGroupAdapter.setData(datas);
            }
            //测试代码
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    datas.remove(datas.size()-1);
//                    setFlipping(datas);
//                }
//            },20000);
            if (marqueeGroupAdapter.getItemCount() <= 2) {//小于两个不轮播
                xmvGroupList.stopFlipping();
            } else {
                xmvGroupList.startFlipping();
            }

        } else {
            llGroupStatus.setVisibility(View.GONE);
        }

    }



    private Runnable dmService = new Runnable() {
        @Override
        public void run() {
            if (!isDestory) {
                ll_qipao.setVisibility(View.GONE);
                int index = currP % presenter.model.getGroupUserList().size();
                GroupUser user = presenter.model.getGroupUserList().get(index);
                ImageLoadUtils.doLoadCircleImageUrl(civ_user_head, user.getHeardUrl());
                tv_qipao_info.setText(user.getContent());
//                tv_qipao_info.setText("你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗你好吗");
                ll_qipao.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                startDM();
                            }
                        });
            } else {
                handler.removeCallbacks(this);
            }
        }
    };
    private int currP;

    private void startDM() {
        ll_qipao.setVisibility(View.VISIBLE);
        //淡入
        ll_qipao.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        currP++;
                        handler.postDelayed(dmService, 5000);
                    }
                }); //很关键，去掉之前隐藏设置的属性
    }

    private List<Group> getGroupData() {
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Group group = new Group();
            List<String> names = new ArrayList<>();
            List<String> heards = new ArrayList<>();
            names.add("我是用户" + i);
            group.setLessGroupEndTime(50000);
            heards.add("http://pic1.zhimg.com/50/v2-6444e641d0235006e81bc4210b5da89b_hd.jpg");
            if (i == 3) {
                names.add("我是新用户" + i);
                heards.add("http://pic3.zhimg.com/50/v2-ed3df8233f628be769436ffed300a917_hd.jpg");
                group.setLessGroupEndTime(10);
            }
            group.setLessGroupUserCount(2);

            group.setNickNames(names);
            group.setHeardUrls(heards);
            groupList.add(group);
        }

        return groupList;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (marqueeGroupAdapter != null && marqueeGroupAdapter.getItemCount() >= 4) {
            xmvGroupList.startFlipping();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        xmvGroupList.stopFlipping();
    }

    private void initBanner() {
        int width1 = BBCUtil.getDisplayWidth(activity);
        int height = (int) (width1 * 600.0 / 640);

        if (presenter.model.getProduct().getBannerImgs() == null || presenter.model.getProduct().getBannerImgs().size() == 0) {//没有banner图时
            ll_indicator.setVisibility(View.GONE);
            ll_banner_switch.setVisibility(View.GONE);
            hasVideo = false;
            if (presenter.model.getProduct().getBannerImgs() == null) {//banners列表为null需要创建一个空list
                presenter.model.getProduct().setBannerImgs(new ArrayList<>());
            }
            if (presenter.model.getProduct().getVideoImgs() != null && presenter.model.getProduct().getVideoImgs().size() > 0) {//有视频的时候
                presenter.model.getProduct().getBannerImgs().add(0, presenter.model.getProduct().getVideoImgs().get(0));
            }
        } else if (presenter.model.getProduct().getVideoImgs() != null && presenter.model.getProduct().getVideoImgs().size() > 0) {//有图有视频
            ll_banner_switch.setVisibility(View.VISIBLE);
            ll_indicator.setVisibility(View.GONE);
            hasVideo = true;
            presenter.model.getProduct().getBannerImgs().add(0, presenter.model.getProduct().getVideoImgs().get(0));
        } else {//有图无视频
            ll_indicator.setVisibility(View.VISIBLE);
            hasVideo = false;
            //无视频
            ll_banner_switch.setVisibility(View.GONE);
//            player.setVisibility(View.GONE);
        }

        if (product.getBannerImgs() != null && product.getBannerImgs().size() > 0) {
            sibSimpleUsage.setIndicatorShow(false);
            sibSimpleUsage.setIndicatorSelectColor(activity.getResources().getColor(R.color.colorRedMain));
            sibSimpleUsage.setIndicatorUnselectColor(activity.getResources().getColor(R.color.colorBlackText2));
            sibSimpleUsage.setAutoScrollEnable(false);
            sibSimpleUsage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //滑动结束需要把视频点击状态取消
                    clickVideo = false;
                    if (position == (list.size() - 2)) {
                        if (positionOffset > 0.25) {
                            //偏移超过4分之一可以跳转
                            canJump = true;
                        } else if (positionOffset <= 0.35 && positionOffset > 0) {
                            canJump = false;
                        }

                        canLeft = false;
                    } else {
                        canLeft = true;
                    }
                }

                @Override
                public void onPageSelected(int position) {

                    currentP = position;
                    if (position != 0) {
                        sibSimpleUsage.pause();
                    }

                    if (hasVideo) {
                        ll_banner_switch.setVisibility(View.VISIBLE);
                        if (position == 0) {
                            ll_indicator.setVisibility(View.GONE);
                            checkVideo();
                        } else {
                            ll_indicator.setVisibility(View.VISIBLE);
                            tvIndex.setText(String.valueOf(position));
                            checkPicture();
                        }
                    } else {
                        ll_banner_switch.setVisibility(View.GONE);
                        tvIndex.setText(String.valueOf(position + 1));
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                    if (currentP == (list.size() - 2) && !canLeft) {
                        if (state == ViewPager.SCROLL_STATE_SETTLING) {
                            if (canJump) {
                                isScrolling = true;
                                svMain.smoothScrollTo(0, twxqTop - (int) minHeaderHeight);
                                tabLayout.getTabAt(1).select();
                            }
                            if (!clickVideo) {//不是视频点击的切换
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 在handler里调用setCurrentItem才有效
                                        sibSimpleUsage.getViewPager().setCurrentItem(list.size() - 2);
                                    }
                                });
                            }
                            //跳转标记重置
                            canJump = false;
                        }
                    }

                }
            });

            RelativeLayout.LayoutParams p1 = (RelativeLayout.LayoutParams) rlTime.getLayoutParams();
            int top = (int) (height - getResources().getDimension(R.dimen.dp_5));
            p1.setMargins(0, top, 0, 0);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width1, height);
            sibSimpleUsage.setLayoutParams(params);

            if (product.getBannerImgs() != null && product.getBannerImgs().size() > 0) {
                list.clear();
//                if((hasVideo&&product.getBannerImgs().size() > 6)||product.getBannerImgs().size() > 5){//超过五个图片
//                    tvLength.setText("5");
//                    int length=5;//banners的长度
//                    if (hasVideo){//如果有视频，需要加一个长度的限制
//                        length=6;
//                    }
//                    for (int i = 0; i < length; i++) {
//                        list.add(product.getBannerImgs().get(i));
//                    }
//                    list.add(new NewsBean());
//                    sibSimpleUsage.setSource(list).startScroll();
//                }else{
                if (hasVideo) {
                    tvLength.setText(String.valueOf(presenter.model.getProduct().getBannerImgs().size() - 1));
                } else {
                    tvLength.setText(String.valueOf(presenter.model.getProduct().getBannerImgs().size()));
                }
                list.addAll(product.getBannerImgs());
                list.add(new NewsBean());
                sibSimpleUsage.setSource(list).startScroll();

            }

//            }

        }

    }

    private void initProductInfo(Group group) {
        withinbuyId = 0;//下面代码 会从接口重新赋值内购id 这里需要重置下
        initBanner();
        tvProductDesc.setSingleLine(false);
        rl_price_progress.setVisibility(View.VISIBLE);
        ll_price_inbuy.setVisibility(View.GONE);
        if (group != null) {
            //销量
            if (group.getGroupType() == 2) {
                tvSaleVolume2.setVisibility(View.VISIBLE);
                tvSaleVolume2.setTextColor(getResources().getColor(R.color.colorRedMain));
                tvSaleVolume2.setText("仅限玩主开团");
                tvGroupStatus.setText("我的拼团");
            } else {
                tvGroupStatus.setText("进行中的拼团");
                tvSaleVolume2.setVisibility(View.GONE);
            }
//            tvSaleVolume2.setVisibility(View.GONE);
            //玩主赚相关的价格
            llGuestPrice.setVisibility(View.GONE);
            llGuestPrice2.setVisibility(View.GONE);
            //普通购买商品的阶梯价格
            tvProductPrice2.setVisibility(View.VISIBLE);
            //不知道是啥
            rl_price_progress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            //秒杀时间
            rlTime.setVisibility(View.VISIBLE);
            //秒杀等的库存
            tvSecondSkillStock.setVisibility(View.GONE);
            //团购价格
            if (group.getGroupMinPrice() != group.getGroupMaxPrice()) {
                tvProductSalePrice.setText(BBCUtil.getDoubleFormat(group.getGroupMinPrice()) + "~" + BBCUtil.getDoubleFormat(group.getGroupMaxPrice()));
                tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(group.getGroupMinPrice()) + "~" + BBCUtil.getDoubleFormat(group.getGroupMaxPrice()));
            } else {
                tvProductSalePrice.setText(BBCUtil.getDoubleFormat(group.getGroupMinPrice()));
                tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(group.getGroupMinPrice()));
            }
            //划线价格
            if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                tvProductPrice2.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice())
                        + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));//普通价格
            } else {
                tvProductPrice2.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice()));//普通价格
            }
            tv_skill_tag.setBackgroundResource(R.drawable.bg_rect_white_9dp);

            tvProductPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvProductPrice2.getPaint().setAntiAlias(true);// 抗锯齿
            tvProductPrice2.setTextColor(Color.parseColor("#7fffffff"));
            tv_skill_tag.setText(group.getGroupCount() + "人拼团");
            tv_skill_tag.setTextColor(getResources().getColor(R.color.colorRedMain));

            //团购状态处理
            if (group.isOnline()) {//进行中的团购
                tv_skill_tag.setCompoundDrawables(null, null, null, null);
                llSecondSkillLeft.setBackgroundResource(R.mipmap.group_time_left);
                llPreSecondSkill.setVisibility(View.GONE);
                llSecondSkill.setVisibility(View.VISIBLE);
                llSecondSkill.setBackgroundResource(R.drawable.bg_gradient_red2);
                setGroupTime(group.getGroupLessTime());
                tv_xiangmai.setVisibility(View.VISIBLE);
                tv_xiangmai.setTextColor(Color.WHITE);
                tv_xiangmai.setText("已拼" + group.getUserGroupStock() + "件");
                //进行中的团购需要显示弹幕提示
                presenter.getGroupJoinInfo(goodsId);
            } else {//即将开始的团购
                tv_skill_tag.setCompoundDrawables(null, null, null, null);
                iv_xiangmai.setVisibility(View.GONE);
                llSecondSkillLeft.setBackgroundColor(getResources().getColor(R.color.transparent));
                llPreSecondSkill.setVisibility(View.VISIBLE);
                llPreSecondSkill.setBackgroundResource(R.drawable.bg_gradient_red2);
                llSecondSkill.setVisibility(View.GONE);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String time = dateFormat.format(new Date(group.getGroupStartTime() * 1000)); //可以把日期转换转指定格式的字符串
                tvSecondSkillStart.setText(time);
                tvDay.setText(group.getTimeDayStr());
            }

            //团购客服商品消息价格记录
            noteTip = "¥" + tvProductSalePrice.getText().toString();

        } else {
            tvSaleVolume.setVisibility(View.VISIBLE);
            tvSaleVolume2.setVisibility(View.GONE);
            //普通状态商品
            if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice())
                        + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));//普通价格
            } else {
                tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice()));//普通价格
            }
            if (this.type == 1) {//玩主

                llGuestPrice.setVisibility(View.VISIBLE);
                llGuestPrice2.setVisibility(View.VISIBLE);
                tvProductPrice.setVisibility(View.GONE);
                tvProductPrice2.setVisibility(View.GONE);
                if (goodBean.getMinIncome() != goodBean.getMaxIncome()) {
                    tvTopProfit.setText(BBCUtil.getDoubleFormat(goodBean.getMinIncome())
                            + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxIncome()));
//                tvProfit2.setText(BBCUtil.getDoubleFormat(goodBean.getMinIncome())
//                        + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxIncome()));
                } else {
                    tvTopProfit.setText(BBCUtil.getDoubleFormat(goodBean.getMinIncome()));
//                tvProfit2.setText(BBCUtil.getDoubleFormat(goodBean.getMinIncome()));
                }

                tvProductSalePrice.setTextColor(activity.getResources().getColor(R.color.colorBlackText));
                tvPriceLabel.setTextColor(activity.getResources().getColor(R.color.colorBlackText));

            } else {//玩客

                llGuestPrice.setVisibility(View.GONE);
                llGuestPrice2.setVisibility(View.GONE);
                tvProductPrice.setVisibility(View.VISIBLE);
                tvProductPrice2.setVisibility(View.VISIBLE);

                tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMarketPrice().doubleValue()));
                tvProductPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvProductPrice.getPaint().setAntiAlias(true);// 抗锯齿

                tvProductPrice2.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMarketPrice().doubleValue()));
                tvProductPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvProductPrice2.getPaint().setAntiAlias(true);// 抗锯齿

                tvProductSalePrice.setTextColor(activity.getResources().getColor(R.color.colorRedMain));
                tvPriceLabel.setTextColor(activity.getResources().getColor(R.color.colorRedMain));
            }


            //特价信息
            SecondKill specialPriceInfo = product.getSpecialPriceInfo();

            //测试数据
//        specialPriceInfo=product.getSeckillActivity();
//        specialPriceInfo.setSpecialTag("特价立减");
//        if(goodBean.getActivityState()==1) {
//            specialPriceInfo.setState(1);
//        }else if(goodBean.getActivityState()==2){
//            specialPriceInfo.setState(0);
//        }
//        goodBean.setActivityState(0);
//        product.setSpecialPriceInfo(specialPriceInfo);
            //end 测试数据

            iv_xiangmai.setVisibility(View.GONE);
            if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {

                //秒杀进行中
                tv_skill_tag.setText("限时秒杀");  //此为必须写的
                tv_skill_tag.setCompoundDrawables(null, null, null, null);

                llPrice.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                llGuestPrice.setVisibility(View.GONE);
                llSecondSkillLeft.setBackgroundResource(R.mipmap.second_skill);
                llPreSecondSkill.setVisibility(View.GONE);
                llSecondSkill.setVisibility(View.VISIBLE);
                llSecondSkill.setBackgroundColor(getResources().getColor(R.color.colorRed));
                SecondKill secondKill = product.getSeckillActivity();
                setSecondKillTime(secondKill);
                rlTime.setVisibility(View.VISIBLE);
                progressBar.setPrograss(secondKill.getBuyStock() * 1.0f / secondKill.getSumStock() * 100);
                if (secondKill.getRemainStock() <= 0) {
                    progressBar.setLeftText("");
                    progressBar.setRightText("");
                    progressBar.setCenterText("已抢光");
                } else {
                    progressBar.setLeftText("已抢" + secondKill.getBuyStock());
                    progressBar.setRightText("仅剩" + secondKill.getRemainStock());
                    progressBar.setCenterText("");
                }

                //秒杀价格
                if (secondKill.getMinPrice() != secondKill.getMaxPrice()) {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxPrice()));
                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxPrice()));
                } else {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()));
                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()));
                }
                if (type == 1) {//玩主
                    if (secondKill.getMinIncome() != secondKill.getMaxIncome()) {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(secondKill.getMinIncome())
                                + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxIncome()));
                    } else {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(secondKill.getMinIncome()));
                    }
                    tvProfit.setText("立赚¥" + BBCUtil.getDoubleFormat(secondKill.getProfitAmount()) + "起");
                    tvEconomize.setText("立省¥" + BBCUtil.getDoubleFormat(secondKill.getMinIncome()) + "起");
                }

                tvSecondSkillStock.setVisibility(View.GONE);
            } else if (goodBean.getActivityState() == 2 && product.getSeckillActivity() != null) {
                //秒杀预热中
                tv_xiangmai.setText(goodBean.getActivityLoveNum() + "人想买");
                if (!goodBean.isActivityLoveIfSet()) {
                    iv_xiangmai.setVisibility(View.VISIBLE);
                }
                //想买人数大于设定的活动库存、特价商品想买人数大于该商品总库存时
                if (goodBean.getActivityLoveNum() > product.getSeckillActivity().getRemainStock() && popupwindow == null) {
                    showQipao();
                }
                tv_skill_tag.setText("限时秒杀");
                tv_skill_tag.setCompoundDrawables(null, null, null, null);
                tvSecondSkillStock.setVisibility(View.VISIBLE);
                llPrice.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rlTime.setVisibility(View.VISIBLE);
                SecondKill secondKill = product.getSeckillActivity();

                if (type == 1) {//玩主
                    if (secondKill.getMinIncome() != secondKill.getMaxIncome()) {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(secondKill.getMinIncome())
                                + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxIncome()));
                    } else {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(secondKill.getMinIncome()));
                    }
                }
                tvSecondSkillStock.setText("限量" + secondKill.getSumStock() + "件");
                llSecondSkillLeft.setBackgroundResource(R.mipmap.pre_second_skill);
                llPreSecondSkill.setVisibility(View.VISIBLE);
                llPreSecondSkill.setBackgroundColor(getResources().getColor(R.color.colorBlueText5));
                llSecondSkill.setVisibility(View.GONE);
                tvSecondSkillStart.setText(DateUtil.date(new Date(secondKill.getStartTime() * 1000), "HH:mm"));
                tvDay.setText(secondKill.getTimeDes());
                //秒杀价格
                if (secondKill.getMinPrice() != secondKill.getMaxPrice()) {

                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxPrice()));
                } else {

                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(secondKill.getMinPrice()));
                }

                if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));
                } else {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice()));
                }

            } else if (specialPriceInfo != null && specialPriceInfo.getState() == 1) {
                setspecialPriceTime(specialPriceInfo);
                tvSaleVolume.setVisibility(View.GONE);
                tvSaleVolume2.setVisibility(View.VISIBLE);

                tvProductDesc.setSingleLine(true);
                tvProductDesc.setEllipsize(TextUtils.TruncateAt.END);

                //特价进行中
                if (!BBCUtil.isEmpty(specialPriceInfo.getSpecialTag())) {
                    tv_skill_tag.setText(specialPriceInfo.getSpecialTag());
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.special_right_arrow);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    tv_skill_tag.setCompoundDrawables(null, null, drawable_n, null);
                }
                llPrice.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                llGuestPrice.setVisibility(View.GONE);
                llSecondSkillLeft.setBackgroundColor(getResources().getColor(R.color.transparent));
                llPreSecondSkill.setVisibility(View.GONE);
                llSecondSkill.setVisibility(View.VISIBLE);
                llSecondSkill.setBackgroundColor(getResources().getColor(R.color.colorRed));
                rlTime.setVisibility(View.VISIBLE);

                //特价价格
                if (specialPriceInfo.getMinPrice() != specialPriceInfo.getMaxPrice()) {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(specialPriceInfo.getMaxPrice()));
                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(specialPriceInfo.getMaxPrice()));
                } else {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()));
                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()));
                }
                if (type == 1) {//玩主
                    if (specialPriceInfo.getMinIncome() != specialPriceInfo.getMaxIncome()) {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinIncome())
                                + "~" + BBCUtil.getDoubleFormat(specialPriceInfo.getMaxIncome()));
                    } else {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinIncome()));
                    }
                    tvProfit.setText("立赚¥" + BBCUtil.getDoubleFormat(specialPriceInfo.getProfitAmount()) + "起");
                    tvEconomize.setText("立省¥" + BBCUtil.getDoubleFormat(specialPriceInfo.getMinIncome()) + "起");
                }

                tvSecondSkillStock.setVisibility(View.GONE);

//                if(product.getWithinPriceInfo() != null&& type == 1){
//                    //特价中 存在内购 玩主 存在内购 显示 加入购物车 立即购买
//                    tvBuy.setVisibility(View.VISIBLE);
//                    tvAddToCart.setVisibility(View.VISIBLE);
//                    llWanzhuPay.setVisibility(View.GONE);
//                    llWanzhuShare.setVisibility(View.GONE);
//                }

            } else if (specialPriceInfo != null && specialPriceInfo.getState() == 0 && popupwindow == null) {
                //特价预热中
                tv_xiangmai.setText(goodBean.getActivityLoveNum() + "人想买");
                if (goodBean.getActivityLoveNum() > goodBean.getRemainStock()) {
                    showQipao();
                }
                if (!goodBean.isActivityLoveIfSet()) {
                    iv_xiangmai.setVisibility(View.VISIBLE);
                } else {
                    iv_xiangmai.setVisibility(View.GONE);
                }
                if (!BBCUtil.isEmpty(specialPriceInfo.getSpecialTag())) {
                    tv_skill_tag.setText(specialPriceInfo.getSpecialTag());
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.special_right_arrow);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    tv_skill_tag.setCompoundDrawables(null, null, drawable_n, null);
                }
                llPrice.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rlTime.setVisibility(View.VISIBLE);

                if (type == 1) {//玩主
                    if (specialPriceInfo.getMinIncome() != specialPriceInfo.getMaxIncome()) {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinIncome())
                                + "~" + BBCUtil.getDoubleFormat(specialPriceInfo.getMaxIncome()));
                    } else {
                        tvProfit2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinIncome()));
                    }
                }
                rlTime.setVisibility(View.VISIBLE);
//            tvSecondSkillStock.setText("限量" + specialPriceInfo.getSumStock() + "件");//特价不显示限量
                llSecondSkillLeft.setBackgroundColor(getResources().getColor(R.color.transparent));
                llPreSecondSkill.setVisibility(View.VISIBLE);
                llPreSecondSkill.setBackgroundColor(getResources().getColor(R.color.colorBlueText4));
                llSecondSkill.setVisibility(View.GONE);
                tvSecondSkillStart.setText(DateUtil.date(new Date(specialPriceInfo.getStartTime() * 1000), "HH:mm"));
                tvDay.setText(specialPriceInfo.getTimeDes());
                //秒杀价格
                if (specialPriceInfo.getMinPrice() != specialPriceInfo.getMaxPrice()) {

                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(specialPriceInfo.getMaxPrice()));
                } else {

                    tvProductSalePrice2.setText(BBCUtil.getDoubleFormat(specialPriceInfo.getMinPrice()));
                }

                if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice()) + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));
                } else {
                    tvProductSalePrice.setText(BBCUtil.getDoubleFormat(goodBean.getMinPrice()));
                }
                tvSecondSkillStock.setVisibility(View.GONE);


            } else {

                llPrice.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rlTime.setVisibility(View.GONE);
            }


            tvSaleVolume.setText("已售" + goodBean.getSaleNum() + "件");
            tvSaleVolume2.setText("已售" + goodBean.getSaleNum() + "件");


            //客服商品消息价格记录
            noteTip = "¥" + tvProductSalePrice.getText().toString();//非内购状态商品价格
            if (type == 1) {
                //玩主 要显示赚
                if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {//秒杀商品
                    //秒杀进行中
                    noteTip = noteTip + " / 赚" + tvProfit2.getText().toString();
                } else if (specialPriceInfo != null && specialPriceInfo.getState() == 1) {//特价商品
                    //特价进行中
                    noteTip = noteTip + " / 赚" + tvProfit2.getText().toString();
                } else {//普通商品
                    noteTip = noteTip + " / 赚" + tvTopProfit.getText().toString();
                }
            }


        }
        if (goodBean.getActivityState() != 1 && goodBean.getReturnAmount() > 0 && !BBCUtil.isEmpty(goodBean.getReturnTitle()) && group == null) {
            //团购 秒杀 商品不显示反额一行
            llReturnMoney.setVisibility(View.VISIBLE);
            tvReturnMoney.setText(goodBean.getReturnTitle());
        } else {
            llReturnMoney.setVisibility(View.GONE);
        }

        //积分信息
        // ||(type == 1 && BBCUtil.isBigVer121(this) && product.getWithinPriceInfo() != null && product.getWithinPriceInfo().getState() != 2)
        if (goodBean.getType() == 3) {
            //批零商品或 V1.2.5内购商品当是玩主时改为显示积分
            rl_jifen.setVisibility(View.GONE);//不显示积分
        } else {
            rl_jifen.setVisibility(View.GONE);//显示积分
            if (type == 1) {
                iv_jifen_right.setVisibility(View.INVISIBLE);//玩主 积分隐藏箭头
            } else {
                iv_jifen_right.setVisibility(View.VISIBLE);//玩客 积分显示箭头
            }
        }
        String jifenStr = "";
        if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
            //秒杀进行中
            SecondKill secondKill = product.getSeckillActivity();
            if (secondKill.getMinIncome() != secondKill.getMaxIncome()) {
                jifenStr = BBCUtil.getDoubleFormat(secondKill.getMinIncome())
                        + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxIncome());
            } else {
                jifenStr = BBCUtil.getDoubleFormat(secondKill.getMinIncome());
            }
            if (secondKill.getMinIncome() == 0 && secondKill.getMaxIncome() == 0) {
                jifenStr = "该商品暂无可得积分";
            } else {
                jifenStr = "购买可得" + jifenStr + "积分";
            }
        } else {
            //普通商品 秒杀预热中 特价商品 特价预热中
            if (goodBean.getMinIncome() != goodBean.getMaxIncome()) {
                jifenStr = BBCUtil.getDoubleFormat(goodBean.getMinIncome())
                        + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxIncome());
            } else {
                jifenStr = BBCUtil.getDoubleFormat(goodBean.getMinIncome());
            }
            if (goodBean.getMinIncome() == 0 && goodBean.getMaxIncome() == 0) {
                jifenStr = "该商品暂无可得积分";
            } else {
                jifenStr = "购买可得" + jifenStr + "积分";
            }
        }
        tv_jifen_tip.setText(jifenStr);

        if (group != null && group.getGroupType() == 2) {
            //团长免费
            SpannableString spannableString = new SpannableString("  " + goodBean.getGoodsName());
            Drawable d = getResources().getDrawable(R.mipmap.group_free);
            int iconwidth = (int) getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            tvProductName.setText(spannableString);
        } else if (group != null && group.isIfBackPrice()) {
            //团长返额
            SpannableString spannableString = new SpannableString("  " + goodBean.getGoodsName());
            Drawable d = getResources().getDrawable(R.mipmap.icon_group_backfee);
            int iconwidth = (int) getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            tvProductName.setText(spannableString);
        } else {
            tvProductName.setText(goodBean.getGoodsName());
        }
//        tvProductName.setText("测试测试杀了接电话拉屎拉回复开始开发哈肯定会老客户打两件事打了哈师大会斯柯达是跨境的哈打呼噜是的哈砂带回来金沙江大会上了大量几乎是大家的拉升实打实啊啊杀菌的哗啦货收到了静安寺");
        tvProductName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按复制
                if (popTipWindow == null) {
                    popTipWindow = new PopTipWindow(activity, goodBean.getGoodsName());
                }
                popTipWindow.showWindow(v);
                return false;
            }
        });
        tvProductDesc.setText(goodBean.getDescription());

        tvProductInfo.setText("");
        if (goodBean.getTaxs() == 1) {
            tvProductInfo.append("商品税费");
        } else if (goodBean.getTaxs() == 2) {
            tvProductInfo.append("商品免税");
        }

//        if (tvProductInfo.length() > 0) {//是否包邮
//            if (goodBean.isIfPostFree()) {
//                tvProductInfo.append("\u3000|\u3000商品包邮");
//            } else {
//                tvProductInfo.append("\u3000|\u3000商品运费");
//            }
//        } else {
//            if (goodBean.isIfPostFree()) {
//                tvProductInfo.append("商品包邮");
//            } else {
//                tvProductInfo.append("商品运费");
//            }
//        }
        if (!goodBean.isIfCanUseCoupon()) {//是否可以用券
            if (tvProductInfo.length() == 0) {
                tvProductInfo.append("不可用券");
            } else {
                tvProductInfo.append("\u3000|\u3000不可用券");
            }
        }
        if (goodBean.isIfCompensate()) {
            if (tvProductInfo.length() == 0) {
                tvProductInfo.append("假一赔十");
            } else {
                tvProductInfo.append("\u3000|\u3000假一赔十");
            }
        }
        if (goodBean.isIfReturnWithoutReason()) {
            if (tvProductInfo.length() == 0) {
                tvProductInfo.append("七天无理由");
            } else {
                tvProductInfo.append("\u3000|\u3000七天无理由");
            }
        } else {
            if (tvProductInfo.length() == 0) {
                tvProductInfo.append("不支持七天无理由退换");
            } else {
                tvProductInfo.append("\u3000|\u3000不支持七天无理由退换");
            }
        }
        if (goodBean.isIfRealPrimise()) {
            tvProductInfo.append("\u3000|\u3000100%正品保证");
        }
        //品牌
        if (!BBCUtil.isEmpty(goodBean.getBrandName())) {
            llBrand.setVisibility(View.VISIBLE);
            Utils.displayImage(activity,goodBean.getBrandLogo(),ivBrandLogo);

            if (Build.VERSION.SDK_INT >= 21) {
                ivBrandLogo.setElevation(4);
            }
            tvBrandName.setText(goodBean.getBrandName());
            tvSaleNum.setText("在售商品 " + goodBean.getBrandGoodsNum());
            if (!BBCUtil.isEmpty(goodBean.getCountryBrandName())) {
                //国家品牌显示
                if (!BBCUtil.isEmpty(goodBean.getCountryIcon())) {
                    ImageLoadUtils.doLoadCircleImageUrl(iv_good_country, goodBean.getCountryIcon());
                }
                tv_good_country.setText(goodBean.getCountryBrandName());
                ll_brand_country.setVisibility(View.VISIBLE);
            } else {
                ll_brand_country.setVisibility(View.GONE);
            }
        } else {
            llBrand.setVisibility(View.GONE);
        }

        if (product.getActivitys() != null && product.getActivitys().size() > 0 && goodBean.getType() != 3 && group == null) {
            //有活动
            //不是批零商品type!=3
            rlActivity.setVisibility(View.VISIBLE);
            llActivityContent.removeAllViews();
            for (int i = 0; i < product.getActivitys().size(); i++) {
                if (i >= 3) {//最多显示个活动
                    break;
                }
                TextView textView = new TextView(activity);
                LinearLayout.LayoutParams paramsA = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsA.setMargins(0, 0, (int) activity.getResources().getDimension(R.dimen.dp_12), 0);
                textView.setLayoutParams(paramsA);
                textView.setTextSize(13);
                textView.setTextColor(activity.getResources().getColor(R.color.colorRedMain));
                textView.setText(product.getActivitys().get(i).getCouponPolicyName());
                textView.setSingleLine();
                llActivityContent.addView(textView);
            }
        } else {
            rlActivity.setVisibility(View.GONE);
        }

        if (product.getCoupons() != null && product.getCoupons().size() > 0 && goodBean.getType() != 3) {
            //优惠券
            //不是批零商品type!=3
            //不是团购商品
            llGetCoupon.setVisibility(View.VISIBLE);
            llCouponContent.removeAllViews();
            for (int i = 0; i < product.getCoupons().size(); i++) {
                if (i >= 3) {//最多显示3个优惠券
                    break;
                }
                View view = LayoutInflater.from(activity).inflate(R.layout.view_product_coupon, null);
                llCouponContent.addView(view);
                LinearLayout.LayoutParams paramsB = (LinearLayout.LayoutParams) view.getLayoutParams();
                paramsB.setMargins(0, 0, (int) activity.getResources().getDimension(R.dimen.dp_5), 0);
                paramsB.height = (int) activity.getResources().getDimension(R.dimen.fab_margin);
                TextView tvCouponName = view.findViewById(R.id.tv_coupon);
                tvCouponName.setText(product.getCoupons().get(i).getCouponName());
            }

        } else {
            llGetCoupon.setVisibility(View.GONE);
        }

        //运费说明
        //测试
        goodBean.setSendWarehouseName("杭州仓");

        ll_postfee_new.setVisibility(View.GONE);
        PostTipBean postTipBean = product.getGoodsPostageDTO();

        if (postTipBean != null) {
            if (!BBCUtil.isEmpty(postTipBean.getPostMessage())) {
                ll_postfee_new.setVisibility(View.VISIBLE);
                tv_postfee_tip.setText(postTipBean.getPostMessage());

                //显示新模块逻辑
                if (!BBCUtil.isEmpty(goodBean.getSendWarehouseName())) {
                    rl_posttip.setVisibility(View.VISIBLE);
                    ll_send_house.setVisibility(View.VISIBLE);
                    if (!BBCUtil.isEmpty(goodBean.getBrandLogo())) {
                        ImageLoadUtils.doLoadCircleImageUrl(iv_good_brand, goodBean.getBrandLogo());
                    }
                    tv_post_type.setText(goodBean.getSendWarehouseName());
                    tv_post_type_new.setText(goodBean.getSendWarehouseName());
                } else {
                    rl_posttip.setVisibility(View.GONE);
                    ll_send_house.setVisibility(View.GONE);
                }

            }
        }


    }

    private void checkVideo() {
        clickVideo = true;
        tvVideo.setTextColor(getResources().getColor(R.color.colorWhite));
        tvPicture.setTextColor(getResources().getColor(R.color.colorBlackText));
        tvVideo.setBackgroundResource(R.drawable.bg_rect_red_11dp);
        tvPicture.setBackgroundResource(R.drawable.bg_rect_white_red_stroke_11dp);
    }

    private void checkPicture() {
        tvPicture.setTextColor(getResources().getColor(R.color.colorWhite));
        tvVideo.setTextColor(getResources().getColor(R.color.colorBlackText));
        tvPicture.setBackgroundResource(R.drawable.bg_rect_red_11dp);
        tvVideo.setBackgroundResource(R.drawable.bg_rect_white_red_stroke_11dp);
    }


    @Override
    protected void onDestroy() {
        if (popTipWindow != null) {
            popTipWindow.dismiss();
        }
        isDestory = true;

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeReceiver);
        GSYVideoManager.releaseAllVideos();
    }


    @OnClick({R.id.ll_add_inbuy, R.id.rl_inbuy, R.id.ll_no_group_buy, R.id.ll_group_buy, R.id.ll_wanfa, R.id.ll_look_more, R.id.iv_wanzhu_up, R.id.iv_xiangmai, R.id.ll_zizhi, R.id.tv_picture, R.id.tv_video, R.id.ll_return_money, R.id.ll_get_coupon, R.id.rl_activity, R.id.product_info, R.id.ll_brand, R.id.tv_upgrade_wanzhu, R.id.lang_ll_back, R.id.iv_share, R.id.iv_collection, R.id.rl_cart, R.id.ll_jingxuan, R.id.ll_wanzhu, R.id.tv_dhtz, R.id.ll_wanzhu_pay, R.id.ll_wanzhu_share, R.id.tv_buy, R.id.tv_add_to_cart, R.id.rl_postfee, R.id.rl_time, R.id.ll_kefu,
            R.id.ll_newgift_buy, R.id.ll_gift_share, R.id.btn_zg_buy, R.id.iv_zg_add_to_cart})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_zg_buy:
                if (goodBean.isSellOut()|| goodBean.getRemainStock() <= 0){
                    if (goodBean.getActivityState()==-1){
                        ToastUtil.show(this,"该商品已下架");
                    }else{
                        ToastUtil.show(this,"该商品已售罄");
                    }
                    return;
                }
            case R.id.iv_zg_add_to_cart:

                break;
            case R.id.ll_add_inbuy:
                break;
            case R.id.rl_inbuy:
                //内购弹框显示

                break;
            case R.id.ll_no_group_buy://普通购买
                if (goodBean.getRemainStock() <= 0) {
                    //普通商品库存售罄
                    return;
                }
                if (DateStorage.getLoginStatus()) {
                    showProductDialog(2);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_BUY);
                }
                break;
            case R.id.ll_group_buy:
                //我要开团
                Group group = product.getGroupGoodsDetailDto();
                if (!group.isOnline() || group.getGroupLessStock() <= 0 || (DateStorage.getLoginStatus() && type != 1 && group.getGroupType() == 2)) {
                    //未上线、团购库存已售罄、非玩主进入团长免费团不能开团、已登录的玩客用户不能开团长免费团
                    return;
                }
                joinGroup = null;
                if (DateStorage.getLoginStatus()) {
                    showGroupDialog(true);
                    groupDialog.setIsJoin(false);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_GROUPZ_PAY);
                }
                break;
            case R.id.ll_wanfa:
                if (wanfaDialog == null) {
                    wanfaDialog = new CommonTipDialogNew(this, "拼团活动规则", presenter.model.getProduct().getGroupGoodsDetailDto().getGroupContent(), null);
                }
                wanfaDialog.showDialog();
                break;
            case R.id.ll_look_more:
//                if (joinGroupDialog == null) {
                if (product == null || product.getGroupGoodsDetailDto() == null) {
                    return;
                }
                needShowDialog = true;
//                showWaitDialog();
                presenter.reqJoinGroupList(goodsId, product.getGroupGoodsDetailDto().getGroupId(), product.getGroupGoodsDetailDto().getGroupType(), false);
//                } else {
//                    joinGroupDialog.setGroupList(presenter.model.getAllGroupList());
//                    joinGroupDialog.showDialog();
//                }
                break;
            case R.id.iv_xiangmai://想买按钮操作
                if (goodBean == null) {
                    return;
                }
                if (DateStorage.getLoginStatus()) {
                    if (ButtonUtil.isFastDoubleClick(R.id.iv_xiangmai)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    presenter.addRealLoveNum(goodBean.getActivityGoodsLoveId());
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_XIANGMAI);
                }
                break;
            case R.id.tv_picture:
//                player.setVisibility(View.GONE);
                checkPicture();
                sibSimpleUsage.getViewPager().setCurrentItem(1);

                break;
            case R.id.tv_video:
                checkVideo();
                sibSimpleUsage.getViewPager().setCurrentItem(0);
//                player.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_return_money:
                new GiftReturnRoleDialog(this, goodBean);

                break;
            case R.id.ll_get_coupon:
                handler.sendEmptyMessage(3);

                break;
            case R.id.rl_activity:


                break;
            case R.id.product_info:
                handler.sendEmptyMessage(7);

                break;
            case R.id.ll_brand:


                break;
            case R.id.iv_wanzhu_up:
                GO_MAINPLAY = true;
                break;
            case R.id.ll_wanzhu:
            case R.id.tv_upgrade_wanzhu://跳转到升级顽主

                break;
            case R.id.lang_ll_back:
                goBack();
                break;
            case R.id.ll_wanzhu_share:
            case R.id.iv_share:
            case R.id.ll_gift_share:
                if (ButtonUtil.isFastDoubleClick(R.id.ll_wanzhu_share)) {
                    ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                    return;
                }
//                showWaitDialog();
                presenter.reqShareInfo(goodsId, "4");
                break;
            case R.id.iv_collection:
                if (DateStorage.getLoginStatus()) {
                    if (ButtonUtil.isFastDoubleClick(R.id.iv_collection)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    if (!isCollect) {
                        presenter.addToCollection(goodsId);
                    } else {
                        presenter.delToCollection(goodsId);
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_COLLECTI);
                }

                break;
            case R.id.rl_cart:
                if (DateStorage.getLoginStatus()) {
                    intent = new Intent(this, CartActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_REFRESH);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_CART);
                }
                break;
            case R.id.ll_jingxuan://加入顽主商店
                if (DateStorage.getLoginStatus()) {
                    if (ButtonUtil.isFastDoubleClick(R.id.ll_jingxuan)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    if (!isJingxuan) {
                        presenter.addToJingxuan(goodsId);
                    } else {
                        presenter.delToJingxuan(goodsId);
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_JINGXUAN);
                }
                break;
            case R.id.tv_dhtz:
                //到货通知
                if (DateStorage.getLoginStatus()) {
//                    showWaitDialog();
                    presenter.reqFindNotifyTel(goodsId);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_DHTZ);
                }
                break;
            case R.id.ll_wanzhu_pay:
                if (DateStorage.getLoginStatus()) {
//                    productDialog = new ProductDialog(this, handler, type, presenter.model.getProduct());
//                    productDialog.showDialog();
                    showProductDialog(0);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_WZ_PAY);
                }

                break;
            case R.id.tv_buy:
                if (DateStorage.getLoginStatus()) {
//                    productDialog = new ProductDialog(this, handler, 2, type, presenter.model.getProduct());
//                    productDialog.showDialog();
                    if (isInbuyProduct()) {
                        showProductDialog(-3);
                    } else {
                        showProductDialog(2);
                    }
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_BUY);
                }
                break;
            case R.id.tv_add_to_cart:
                if (DateStorage.getLoginStatus()) {
//                    productDialog = new ProductDialog(this, handler, 1, type, presenter.model.getProduct());
//                    productDialog.showDialog();
                    if (isInbuyProduct()) {
                        showProductDialog(-2);
                    } else {
                        showProductDialog(1);
                    }

                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_ADD_TO_CART);
                }
                break;
            case R.id.rl_postfee:
                //运费说明弹框
                if (product == null) {
                    return;
                }
                PostTipBean postTipBean = product.getGoodsPostageDTO();
                if (postTipBean != null && !BBCUtil.isEmpty(postTipBean.getPostMessageDetail())) {
                    ProductInfoDialog postTipDialog = new ProductInfoDialog(ProductInfoActivity.this, postTipBean.getPostMessageDetail());
                    postTipDialog.setTitle("运费说明");
                }
                break;
            case R.id.rl_time:
                //点击特价和秒杀
                if (product == null || goodBean == null) {
                    return;
                }
                SecondKill specialPriceInfo = product.getSpecialPriceInfo();
                if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
                    //秒杀进行中
                } else if (goodBean.getActivityState() == 2 && product.getSeckillActivity() != null) {
                    //秒杀预热中
                } else if (specialPriceInfo != null) {
                    //特价商品列表页
                }
                break;
            case R.id.ll_kefu:
                //联系客服
                if (goodBean == null) {
                    return;
                }
                if (DateStorage.getLoginStatus()) {
                    intent =new Intent(this, ServiceofMyActivity.class);
                    ActivityUtil.getInstance().start(this, intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_LIANXIKEFU);
                }
                break;
            case R.id.ll_newgift_buy:
                //点击新人领取
                if (DateStorage.getLoginStatus()) {
                    showProductDialog(3);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_REFRESH);
                }
                break;
        }
    }

    @OnClick(R.id.rl_jifen)
    public void clickJifen() {
        //点击积分
        if (this.type == 1) {//玩主
            return;
        }
        if (product == null) {
            return;
        }
        if (goodBean == null) {
            return;
        }
        String jifenStr = "";
        String allJifen = "200";//初始化200
        if (goodBean.getScore() > 0) {
            allJifen = BBCUtil.getDoubleFormat(goodBean.getScore());
        }
        if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
            //秒杀进行中
            SecondKill secondKill = product.getSeckillActivity();
            if (secondKill.getMinIncome() != secondKill.getMaxIncome()) {
                jifenStr = BBCUtil.getDoubleFormat(secondKill.getMinIncome())
                        + "~" + BBCUtil.getDoubleFormat(secondKill.getMaxIncome());
            } else {
                jifenStr = BBCUtil.getDoubleFormat(secondKill.getMinIncome());
            }
            if (secondKill.getMinIncome() == 0 && secondKill.getMaxIncome() == 0) {
                jifenStr = "该商品暂无可得积分，总积分达到" + allJifen + "后将自动升级为素店玩主";
            } else {
                jifenStr = "购买该商品可获得" + jifenStr + "积分，总积分达到" + allJifen + "后将自动升级为素店玩主";
            }
        } else {
            //普通商品 秒杀预热中 特价商品 特价预热中
            if (goodBean.getMinIncome() != goodBean.getMaxIncome()) {
                jifenStr = BBCUtil.getDoubleFormat(goodBean.getMinIncome())
                        + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxIncome());
            } else {
                jifenStr = BBCUtil.getDoubleFormat(goodBean.getMinIncome());
            }
            if (goodBean.getMinIncome() == 0 && goodBean.getMaxIncome() == 0) {
                jifenStr = "该商品暂无可得积分，总积分达到" + allJifen + "后将自动升级为素店玩主";
            } else {
                jifenStr = "购买该商品可获得" + jifenStr + "积分，总积分达到" + allJifen + "后将自动升级为素店玩主";
            }
        }

        ProductInfoDialog postTipDialog = new ProductInfoDialog(ProductInfoActivity.this, jifenStr);
        postTipDialog.setTitle("积分说明");
    }

    private void showProductDialog(int btnFlag) {
        if (product == null) {
            return;
        }
        if (productDialog == null) {
            if (product.getSpecificationList() != null && product.getSpecificationList().size() > 0) {
                productDialog = new ProductMultipeSkuDialog(this, handler, btnFlag, type, product, false, false);
                productDialog.showDialog();
            } else {
                productDialog = new ProductDialog(this, handler, btnFlag, type, product, false);
                productDialog.showDialog();
            }
        } else {
            productDialog.setBtnFlag(btnFlag);
            productDialog.showDialog();
        }

    }

    private void showGroupDialog(boolean isOpenGroup) {
        if (product == null) {
            return;
        }
        if (groupDialog == null) {
            if (product.getSpecificationList() != null && product.getSpecificationList().size() > 0) {
                groupDialog = new ProductMultipeSkuDialog(this, handler, -1, type, product, false, isOpenGroup);
                groupDialog.showDialog();
            } else {
                groupDialog = new ProductDialog(this, handler, -1, type, product, isOpenGroup);
                groupDialog.showDialog();
            }
        } else {
            groupDialog.showDialog();
        }

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://加入购物车
                    presenter.addToCart((GoodSku) msg.obj, msg.arg1, ifNewUserGoodsAdd, videoId);
                    break;
                case 2://立即购买
                    if (ButtonUtil.isFastDoubleClick(R.id.ll_group_buy + 2)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    buySkuList = new ArrayList<GoodSku>();
                    GoodSku sku = (GoodSku) msg.obj;
                    sku.setNum(msg.arg1);
                    sku.setVideoId(videoId);
                    buySkuList.add(sku);

//                    showWaitDialog();
                    presenter.reqCheckStock(buySkuList, videoId);//校验库存
                    break;
                case 3://请求优惠券数据并弹框
//                    new GetCouponDialog(this,product.getCoupons(),handler);
                    if (ButtonUtil.isFastDoubleClick(R.id.ll_get_coupon)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    presenter.reqCanGetCouponList(goodsId);
                    break;
                case CouponGetAdapter.HANDLER_CODE_GET://领取优惠券
                    if (ButtonUtil.isFastDoubleClick(CouponGetAdapter.HANDLER_CODE_GET)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    presenter.model.setCouponId(msg.obj.toString());
                    if (!DateStorage.getLoginStatus()) {
                        Intent intent = new Intent(ProductInfoActivity.this, LoginActivity.class);
                        ActivityUtil.getInstance().startResult(ProductInfoActivity.this, intent, REQ_GET_COUPON);
                    } else {
                        presenter.reqGetCoupon();
                    }

                    break;
                case 5://设置到货提醒
                    if (ButtonUtil.isFastDoubleClick(R.id.dialog_ok_btn)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    presenter.addNoticePhoneNumber(goodsId, msg.obj.toString());
                    break;
                case 6://秒杀倒计时结束
                    //刷新速度 间隔3s
                    if (ButtonUtil.isFastDoubleClick(R.id.ll_group_buy)) {
                        return;
                    }
                    LogUtil.i("linhanpeng", "真的刷新了");
                    rlTime.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //延迟刷新 数据还没有变更
                            presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
                        }
                    }, 500);

                    break;
                case 7:
                    if (ButtonUtil.isFastDoubleClick(R.id.product_info)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    presenter.reqProductInfo(goodsId);
                    break;
                case 8://可参团倒计时结束
                    //刷新速度 间隔3s
//                    if (ButtonUtil.isFastDoubleClick2()) {
//                        return;
//                    }


                    if ((Boolean) msg.obj) {
                        if (marqueeGroupAdapter != null) {
                            if (marqueeGroupAdapter.getItemCount() == 1) {//只有一个的时候不轮播
                                xmvGroupList.setItemCount(1);
                            } else {
                                xmvGroupList.setItemCount(2);
                            }
                            if (marqueeGroupAdapter.getItemCount() <= 2) {//小于两个不轮播
                                xmvGroupList.stopFlipping();
                            }
                            marqueeGroupAdapter.notifyDataChanged();
                        }
                    }
                    if (product != null && product.getGroupGoodsDetailDto() != null) {
                        presenter.reqJoinGroupList(goodsId, product.getGroupGoodsDetailDto().getGroupId(), product.getGroupGoodsDetailDto().getGroupType(), (Boolean) msg.obj);
                    }
                    break;
                case 10:
                    //团购购买
                    if (product == null) {
                        return;
                    }
                    if (product.getGroupGoodsDetailDto() == null) {
                        return;
                    }
                    if (ButtonUtil.isFastDoubleClick(R.id.ll_group_buy + 10)) {
                        ToastUtil.show(ProductInfoActivity.this, R.string.tip_btn_fast);
                        return;
                    }
                    buySkuList = new ArrayList<GoodSku>();
                    GoodSku sku2 = (GoodSku) msg.obj;
                    if (msg.arg1 == 1) {//集货团
                        isJihuo = true;
                    } else {
                        isJihuo = false;
                    }
                    sku2.setNum(1);
                    buySkuList.add(sku2);
//                    showWaitDialog();
                    Map<String, Object> groupP = new HashMap<>();
                    if (joinGroup == null) {
                        groupP.put("groupId", product.getGroupGoodsDetailDto().getGroupId());
                    } else {
                        groupP.put("tradeGroupCreateId", joinGroup.getId());
                    }
                    groupP.put("groupType", product.getGroupGoodsDetailDto().getGroupType());
                    presenter.reqCheckStock(buySkuList, groupP, videoId);//校验库存
                    break;
                case 11:
                    //参团
                    joinGroup = (Group) msg.obj;
                    if (!DateStorage.getLoginStatus()) {
                        Intent intent = new Intent(ProductInfoActivity.this, LoginActivity.class);
                        ActivityUtil.getInstance().startResult(ProductInfoActivity.this, intent, REQ_GROUPZ_PAY);
                    } else {
                        showGroupDialog(false);
                        groupDialog.setIsJoin(true);
                    }
                    if (joinGroupDialog != null) {
                        joinGroupDialog.cancelDialog();
                    }
                    break;
                case 12://团购分享
                    shareGroup = (Group) msg.obj;
                    if (shareGroup != null) {
//                        showWaitDialog();
                        presenter.reqShareInfo(shareGroup.getId(), "21");
                    }
                    if (joinGroupDialog != null) {
                        joinGroupDialog.cancelDialog();
                    }
                    break;
                case CouponGetAdapter.HANDLER_CODE_USERGOHOME:
                    //优惠券券去使用 全场通用类型
                    //去逛逛
                    if (getCouponDialog != null) {
                        getCouponDialog.cancelDialog();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GET_COUPON) {
            if (resultCode == 200) {
                presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
                presenter.reqGetCoupon();
            }
        } else if (resultCode == 200 || (requestCode == REQ_GROUPZ_PAY_COMPLATE && resultCode == RESULT_OK)) {//登录成功
//            showWaitDialog();
            this.requestCode = requestCode;
            presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
        } else if (resultCode == RESULT_OK && requestCode == REQ_REFRESH) {
//            showWaitDialog();
            this.requestCode = requestCode;
            presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void refreshNet() {
        super.refreshNet();
//        showWaitDialog();
        presenter.getProductInfo(goodsId, withinbuyId, ifNewUserGoodsAdd);
        presenter.getCollection(goodsId);
        if (DateStorage.getLoginStatus()) {
            presenter.getCartCount();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }


    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    //根据requestCode和grantResults(授权结果)做相应的后续处理
    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == BBCUtil.REQUEST_PERMISSION) {
            if (grantResults == null || grantResults.length == 0) {
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                ToastUtil.show(this, "请在设置中打开权限");
            }
        }
    }

    private void showQipao() {
//        popupwindow = new StockTipPopupwindow(this, "库存有限,不要错过哦");
//        //想买人数大于设定的活动库存、特价商品想买人数大于该商品总库存时
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Rect globalRect = new Rect();
//                boolean visibile = tv_xiangmai.getGlobalVisibleRect(globalRect);
//                if (visibile) {
//                    popupwindow.showEditPopup(tv_xiangmai);
//                }
//            }
//        }, 500);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //3秒后隐藏
//                if(popupwindow!=null) {
//                    popupwindow.dismiss();
//                }
//                flag=false;
//            }
//        }, 3500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pop_stock_tip.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth = pop_stock_tip.getMeasuredWidth();
                int[] location = new int[2];
                tv_xiangmai.getLocationOnScreen(location);
                int left = (location[0] + tv_xiangmai.getWidth() / 2) - popupWidth / 2;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pop_stock_tip.getLayoutParams();
                params.setMargins(left, 0, 0, (int) -getResources().getDimension(R.dimen.dp_7));
                pop_stock_tip.setLayoutParams(params);
                pop_stock_tip.setVisibility(View.VISIBLE);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //3秒后隐藏
                pop_stock_tip.setVisibility(View.GONE);
            }
        }, 3500);

    }


    private boolean isInbuyProduct() {
        if (type == 0) {
            return false;
        }
        if (!BBCUtil.isBigVer121(this)) {
            return false;
        }
        if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
            return false;
        }
        if (goodBean.getActivityState() == 2 && product.getSeckillActivity() != null) {
            return false;
        }
        if (product.getSpecialPriceInfo() != null && product.getSpecialPriceInfo().getState() == 1) {
            return false;
        }
        if (product.getSpecialPriceInfo() != null && product.getSpecialPriceInfo().getState() == 0) {
            //预热特价

            return false;
        }

        return false;
    }

    public void initSucai() {
        if (sucaiBean == null) {
            return;
        }
        SucaiDbean sucaiDbean = sucaiBean.getMaterial();
        if (sucaiDbean == null) {
            return;
        }
        UserAccount userAccount = sucaiDbean.getUserAccount();
        if (userAccount == null) {
            return;
        }
        if (sucaiBean.getMaterialCount() >= 2) {
            tv_sucai_num.setText("(" + sucaiBean.getMaterialCount() + ")");
            tv_sucai_num.setVisibility(View.VISIBLE);
            ll_look_more_sucai.setVisibility(View.VISIBLE);
        } else {
            tv_sucai_num.setVisibility(View.GONE);
            ll_look_more_sucai.setVisibility(View.GONE);
        }

        ImageLoadUtils.doLoadCircleImageUrl(iv_sucai_headimg, userAccount.getHeadUrl());
        tv_sucai_name.setText(userAccount.getNickName());
        tv_sucai_time.setText(sucaiDbean.getCreateTimeStr());
        tv_sucai_title.setText(sucaiDbean.getName());
        tv_sucai_content.setText(sucaiDbean.getDescription());

        List<String> imgList = sucaiDbean.getImgList();
        if (imgList != null && imgList.size() > 0) {
            int screenWidth = BBCUtil.getDisplayWidth(this);
            int leftright = (int) getResources().getDimension(R.dimen.dp_3);
            int topbottom = (int) getResources().getDimension(R.dimen.view_toview_two);
            int newWidth = (int) ((screenWidth - (int) getResources().getDimension(R.dimen.design_15dp) * 2 - leftright * 2) / 3);
            int newHeight = (int) newWidth;
            ll_sucai_imgs.removeAllViews();
            ll_sucai_imgs.setVisibility(View.VISIBLE);
            List<ActivityNewBean> activityNewBeanList = new ArrayList<>();
            List<String> imgPreList = new ArrayList<>();
            for (int i = 0; i < imgList.size(); i++) {
                if (i % 3 == 0) {
                    ActivityNewBean newBean = new ActivityNewBean();
                    activityNewBeanList.add(newBean);
                }
                ActivityNewBean nowBean = activityNewBeanList.get(activityNewBeanList.size() - 1);
                List<ActivityitemitemBean> detailList = nowBean.getDetailList();
                if (detailList == null) {
                    detailList = new ArrayList<>();
                    nowBean.setDetailList(detailList);
                }
                ActivityitemitemBean itemBean = new ActivityitemitemBean();
                itemBean.setImgUrl(imgList.get(i));
                imgPreList.add(imgList.get(i));
                detailList.add(itemBean);
            }
            for (int i = 0; i < activityNewBeanList.size(); i++) {
                View imgViewLayout = LayoutInflater.from(this).inflate(R.layout.gridview_atheme_item, null, false);
                LinearLayout ll_atheme_content = imgViewLayout.findViewById(R.id.ll_atheme_content);
                if (i == 0 && i == activityNewBeanList.size() - 1) {
                    ll_atheme_content.setPadding(0, 0, 0, 0);
                } else if (i == 0) {
                    ll_atheme_content.setPadding(0, 0, 0, topbottom);
                } else if (i == activityNewBeanList.size() - 1) {
                    ll_atheme_content.setPadding(0, 0, 0, 0);
                } else {
                    ll_atheme_content.setPadding(0, 0, 0, topbottom);
                }

                ActivityNewBean nowBean = activityNewBeanList.get(i);
                List<ActivityitemitemBean> detailList = nowBean.getDetailList();
                for (int postion = 0; postion < detailList.size(); postion++) {
                    ActivityitemitemBean itemBean = detailList.get(postion);
                    LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                            (int) newWidth, newHeight);
                    if (!BBCUtil.isEmpty(sucaiDbean.getVideo())) {//视频素材
                        RelativeLayout relativeLayout = new RelativeLayout(this);
                        ImageView imageView = new ImageView(this);
                        vlp.setMargins(leftright, 0, 0, 0);
                        relativeLayout.setLayoutParams(vlp);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(newWidth, newHeight);
                        imageView.setLayoutParams(params);
                        ImageLoadUtils.doLoadImageUrl(imageView, itemBean.getImgUrl());
                        relativeLayout.addView(imageView);

                        ImageView play = new ImageView(this);
                        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams((int) (newWidth / 3.5), (int) (newHeight / 3.5));
                        play.setImageResource(R.mipmap.short_video_play);
                        params2.addRule(RelativeLayout.CENTER_IN_PARENT);
                        play.setLayoutParams(params2);
                        relativeLayout.addView(play);

                        relativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (sucaiBean.getMaterialCount() >= 2) {
                                    clickLookmoreSucai();
                                }

                            }
                        });
                        ll_atheme_content.addView(relativeLayout);
                    } else {
                        ImageView imageView = new ImageView(this);
                        if (postion != 0) {
                            vlp.setMargins(leftright, 0, 0, 0);
                        }
                        imageView.setLayoutParams(vlp);//设置TextView的布局
                        ImageLoadUtils.doLoadImageUrl(imageView, itemBean.getImgUrl());
                        final int hang = i;
                        final int lie = postion;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BBCUtil.openImgPreview(activity, hang * 3 + lie, imgPreList, false);
                            }
                        });
                        ll_atheme_content.addView(imageView);
                    }
                }
                ll_sucai_imgs.addView(ll_atheme_content);
            }

        } else {
            ll_sucai_imgs.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ll_look_more_sucai)
    public void clickLookmoreSucai() {
        //查看更多素材

    }

    @OnClick(R.id.ll_sucai_content)
    public void clickSucaiDetail() {
        if (sucaiBean == null) {
            return;
        }

        if (sucaiBean.getMaterialCount() >= 2) {
            //进素材列表页
            clickLookmoreSucai();
            return;
        }

        if (!BBCUtil.isEmpty(sucaiBean.getMaterial().getVideo())) {
            return;
        }
        SucaiDbean sucaiDbean = sucaiBean.getMaterial();
        if (sucaiDbean == null) {
            return;
        }

    }



}
