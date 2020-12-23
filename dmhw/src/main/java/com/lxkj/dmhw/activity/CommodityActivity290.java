package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.wireless.security.open.middletier.fc.IFCActionCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.BannerAdapter;
import com.lxkj.dmhw.adapter.CommodityAdapter290;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.bean.ShopInfo290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ObserveScrollView;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.BiJiaDialog;
import com.lxkj.dmhw.dialog.CouponLinkDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.dialog.TipsDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScaleLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.internal.FastBlur;

/**
 * 商品详情页
 */
public class CommodityActivity290 extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, ObserveScrollView.OnScrollListener, AlibcTradeCallback {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.goods_title_01)
    LinearLayout goods_title_01;
    @BindView(R.id.title_01)
    TextView title_01;
    @BindView(R.id.title_spilt_01)
    TextView title_spilt_01;

    @BindView(R.id.goods_title_02)
    LinearLayout goods_title_02;
    @BindView(R.id.title_02)
    TextView title_02;
    @BindView(R.id.title_spilt_02)
    TextView title_spilt_02;

    @BindView(R.id.goods_title_03)
    LinearLayout goods_title_03;
    @BindView(R.id.title_03)
    TextView title_03;
    @BindView(R.id.title_spilt_03)
    TextView title_spilt_03;

    @BindView(R.id.goods_title_layout)
    LinearLayout goods_title_layout;

    @BindView(R.id.goods_detail_layout)
    LinearLayout goods_detail_layout;



    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.title_block)
    LinearLayout titleBlock;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.commodity_banner)
    ConvenientBanner commodityBanner;
    @BindView(R.id.commodity_video)
    ImageView commodityVideo;
    @BindView(R.id.commodity_check_iv)
    ImageView commodity_check_iv;
    @BindView(R.id.commodity_title)
    TextView commodityTitle;
    @BindView(R.id.commodity_discount)
    TextView commodityDiscount;
    @BindView(R.id.commodity_money)
    TextView commodityMoney;
    @BindView(R.id.commodity_sales)
    TextView commoditySales;
    @BindView(R.id.commodity_discount_text)
    TextView commodityDiscountText;
    @BindView(R.id.commodity_start_time)
    TextView commodityStartTime;
    @BindView(R.id.commodity_end_time)
    TextView commodityEndTime;
    @BindView(R.id.commodity_discount_btn)
    LinearLayout commodityDiscountBtn;
    @BindView(R.id.commodity_recommend_text)
    TextView commodityRecommendText;
    @BindView(R.id.commodity_recommend)
    LinearLayout commodityRecommend;
    @BindView(R.id.commodity_shop_avatar)
    ImageView commodityShopAvatar;
    @BindView(R.id.commodity_shop_name)
    TextView commodityShopName;
    @BindView(R.id.commodity_shop_one_text)
    TextView commodityShopOneText;
    @BindView(R.id.commodity_shop_one_evaluate)
    TextView commodityShopOneEvaluate;
    @BindView(R.id.commodity_shop_two_text)
    TextView commodityShopTwoText;
    @BindView(R.id.commodity_shop_two_evaluate)
    TextView commodityShopTwoEvaluate;
    @BindView(R.id.commodity_shop_three_text)
    TextView commodityShopThreeText;
    @BindView(R.id.commodity_shop_three_evaluate)
    TextView commodityShopThreeEvaluate;

    @BindView(R.id.commodity_shop_one_title)
    TextView commodity_shop_one_title;
    @BindView(R.id.commodity_shop_two_title)
    TextView commodity_shop_two_title;
    @BindView(R.id.commodity_shop_three_title)
    TextView commodity_shop_three_title;




    @BindView(R.id.commodity_shop_layout)
    LinearLayout commodityShopLayout;
    @BindView(R.id.comment_layout)
    RelativeLayout comment_layout;

    @BindView(R.id.recomend_goods_layout)
    LinearLayout recomend_goods_layout;
    @BindView(R.id.commodity_shop_recycler)
    RecyclerView commodityShopRecycler;
    @BindView(R.id.commodity_collection_text)
    TextView commodityCollectionText;
    @BindView(R.id.commodity_collection)
    LinearLayout commodityCollection;
    @BindView(R.id.commodity_main)
    LinearLayout commodity_main;
    @BindView(R.id.commodity_share)
    LinearLayout commodityShare;
    @BindView(R.id.commodity_btn)
    LinearLayout commodityBtn;
    @BindView(R.id.commodity_table)
    LinearLayout commodityTable;

    @BindView(R.id. coupon_layout)
    ScaleLayout coupon_layout;

    @BindView(R.id.return_top)
    ImageView returnTop;
    @BindView(R.id.commodity_scroll)
    ObserveScrollView commodityScroll;
    @BindView(R.id.commodity_collection_image)
    ImageView commodityCollectionImage;
    @BindView(R.id.commodity_estimate_two)
    TextView commodityEstimateTwo;
    @BindView(R.id.commodity_tips_text)
    TextView commodityTipsText;
    @BindView(R.id.commodity_tips_layout)
    LinearLayout commodityTipsLayout;
    @BindView(R.id.commodity_discount_btn_text)
    TextView commodityDiscountBtnText;
    @BindView(R.id.commodity_btn_text)
    TextView commodityBtnText;
    @BindView(R.id.commodity_undercarriage)
    View commodityUndercarriage;
    @BindView(R.id.commodity_save_money)
    TextView commodity_save_money;
    @BindView(R.id.commodity_estimate_two_sub)
    TextView commodity_estimate_two_sub;
    @BindView(R.id.commodity_btn_text_sub)
    TextView commodity_btn_text_sub;
    private CommodityDetails290 details;
    // 判断是否收藏
    private boolean isCheck;
    // 推荐商品adapter
    private CommodityAdapter290 adapter;
    //猜你喜欢商品数据
    private ArrayList<MainBottomListItem> list;
    // 全网搜数据
    private String type = "0";


    @BindView(R.id.commodity_mask)
    RelativeLayout commodity_mask;
    @BindView(R.id.back_image_iv)
    ImageView back_image_iv;
    @BindView(R.id.commodity_save_money_layout)
    LinearLayout commodity_save_money_layout;

    @BindView(R.id.look_more_layout)
    LinearLayout look_more_layout;

    @BindView(R.id.commodity_save_money_title)
    TextView commodity_save_money_title;
    @BindView(R.id.quanhou_title)
    TextView quanhou_title;
    @BindView(R.id.goshop_title)
    TextView goshop_title;
    @BindView(R.id.commodity_sales_title)
    TextView commodity_sales_title;
    @BindView(R.id.recommendtv)
    TextView recommendtv;
    @BindView(R.id.look_more_title)
    TextView look_more_title;
    @BindView(R.id.goods_detail_tv)
    TextView goods_detail_tv;
    @BindView(R.id.recommend_title)
    TextView recommend_title;
    @BindView(R.id.upgrade_title)
    TextView upgrade_title;
    @BindView(R.id.lijiupgrade)
    TextView lijiupgrade;


    @BindView(R.id.goshop)
    LinearLayout goshop;
    TextPaint tp;
    //同一个商品页面 点击“我知道了” 不再显示弹框
    private Boolean isshowDialog=false;

    //店铺实体
   private ShopInfo290 shopInfo= new ShopInfo290();
   //平台类型
    private CpsType cpsType= new CpsType();

    //优惠券
    private Coupon coupon= new Coupon();

    //轮播图数据
    private ArrayList<String> ImageBanerList=new ArrayList<>();

    public static CommodityActivity290 commodityActivity290=null;

    //登录刷新标志
    private  boolean loginRefreshFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity310);
        ButterKnife.bind(this);
        commodityActivity290=this;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (DateStorage.getLoginStatus()) {
            commodity_save_money_layout.setVisibility(View.VISIBLE);
        }else{
            commodity_save_money_layout.setVisibility(View.GONE);
        }
        webSetting();
        Variable.AuthShowStatus=true;//控制推荐授权弹窗

        // 设置Recycler
        commodityShopRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, 3));
        commodityShopRecycler.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dipToPixel(R.dimen.dp_15), false));
        commodityShopRecycler.setNestedScrollingEnabled(false);//禁止滑动
        // 初始化adapter
        adapter = new CommodityAdapter290(this);
        commodityShopRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        commodityScroll.setOnScrollListener(this);
        title_01.setTextColor(Color.parseColor("#000000"));
        title_spilt_01.setBackgroundColor(Color.parseColor("#000000"));
        title_02.setTextColor(Color.parseColor("#666666"));
        title_spilt_02.setBackgroundColor(Color.parseColor("#000000"));
        title_03.setTextColor(Color.parseColor("#666666"));
        title_spilt_03.setBackgroundColor(Color.parseColor("#000000"));

        tp= commodityTitle.getPaint();
        tp.setFakeBoldText(true);

        tp= commodityMoney.getPaint();
        tp.setFakeBoldText(true);

        tp= commodity_save_money_title.getPaint();
        tp.setFakeBoldText(true);

        tp= commodity_save_money.getPaint();
        tp.setFakeBoldText(true);

        tp= commodityDiscountText.getPaint();
        tp.setFakeBoldText(true);

        tp= commoditySales.getPaint();
        tp.setFakeBoldText(true);

        tp= commodity_estimate_two_sub.getPaint();
        tp.setFakeBoldText(true);

        tp= commodity_btn_text_sub.getPaint();
        tp.setFakeBoldText(true);

        tp= quanhou_title.getPaint();
        tp.setFakeBoldText(true);

        tp= commodityShopName.getPaint();
        tp.setFakeBoldText(true);

        tp= goshop_title.getPaint();
        tp.setFakeBoldText(true);

        tp= commodity_sales_title.getPaint();
        tp.setFakeBoldText(true);

        tp= recommendtv.getPaint();
        tp.setFakeBoldText(true);

        tp= look_more_title.getPaint();
        tp.setFakeBoldText(true);

        tp= goods_detail_tv.getPaint();
        tp.setFakeBoldText(true);

        tp= recommend_title.getPaint();
        tp.setFakeBoldText(true);

        tp= commodityRecommendText.getPaint();
        tp.setFakeBoldText(true);

        tp= upgrade_title.getPaint();
        tp.setFakeBoldText(true);

        tp= commodityTipsText.getPaint();
        tp.setFakeBoldText(true);

        tp= lijiupgrade.getPaint();
        tp.setFakeBoldText(true);

        commodityRecommendText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utils.copyText(commodityRecommendText.getText().toString());
                ToastUtil.showImageToast(CommodityActivity290.this,"复制成功",R.mipmap.toast_img);
                return false;
            }
        });
        commodityTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utils.copyText(commodityTitle.getText().toString().trim());
                ToastUtil.showImageToast(CommodityActivity290.this,"复制成功",R.mipmap.toast_img);
                return false;
            }
        });
        //新的商品详情接口 不分站内站外 统一一个接口
        showDialog();
        initData();
    }

    private void initData(){
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("shopid", getIntent().getExtras().getString("shopId"));
        if (getIntent().getExtras().getString("source")!=null){
            paramMap.put("source", getIntent().getExtras().getString("source"));
        }else{
            paramMap.put("source","");
        }
        if (getIntent().getExtras().getString("sourceId")!=null) {
            paramMap.put("sourceId", getIntent().getExtras().getString("sourceId"));
        }else{
            paramMap.put("sourceId", "");
        }
        NetworkRequest.getInstance().POST(handler, paramMap, "CommodityDetail", HttpCommon.CommodityDetail);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (loginRefreshFlag){
          showDialog();
          loginRefreshFlag=false;
          initData();
        }
    }


    //判断是否收藏
    private void isCollection(String id){
        if (DateStorage.getLoginStatus()) {
            paramMap.clear();
            paramMap.put("shopid", id);
            NetworkRequest.getInstance().POST(handler, paramMap, "IsCollection", HttpCommon.GetAllNetGoodsCollection);
        }
    }


    TaobaoAuthLoginDialog Tdialog;
    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            loginRefreshFlag=true;
        }

        if (message.what == LogicActions.UndercarriageSuccess) {
            commodityUndercarriage.setVisibility(View.VISIBLE);
            titleBlock.setBackgroundColor(Color.argb(255, 255, 255, 255));
            backImage.setImageAlpha(0);
            title_01.setTextColor(Color.parseColor("#000000"));
            title_02.setTextColor(Color.parseColor("#666666"));
            title_03.setTextColor(Color.parseColor("#666666"));
            TipsDialog dialog = new TipsDialog(this);
            dialog.showDialog("CommodityActivity", "温馨提示", "该商品已下架", "知道了");
        }
        if (message.what == LogicActions.CommodityActivitySuccess) {
            isFinish();
        }

//        if (message.what == LogicActions.noAuthSuccessfulSuccess) {
//            dismissDialog();
//            Tdialog = new TaobaoAuthLoginDialog(this, message.obj.toString());
//            Tdialog.showDialog();
//        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
////            if (Tdialog!=null){
////                Tdialog.dismiss();
////            }
//            if(Tdialog != null) {
//                //判断dialog是否正在显示
//                if(Tdialog.isShowing()) {
//                    //get the Context object that was used to great the dialog
//                    Context context = ((ContextWrapper)Tdialog.getContext()).getBaseContext();
//                    //判断是所在Activity是否已经销毁
//                    if(context instanceof Activity) {
//                        if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
//                            Tdialog.dismiss();
//                    } else
//                        Tdialog.dismiss();
//                }
//                Tdialog = null;
//            }
//        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.CommodityDetailSuccess) {
            details = (CommodityDetails290) message.obj;
            //获取轮播图
            JSONArray ImageArray = JSON.parseArray(details.getImageList());
            if (ImageArray!=null&&ImageArray.size()>0){
                for (int i=0;i<ImageArray.size();i++) {
                    ImageBanerList.add((String) ImageArray.get(i));
                }
            }else{
                ImageBanerList.add(details.getImageUrl());
            }
            bannerImage(ImageBanerList);
            //获取优惠券信息
            Object CouponObject = JSON.parseObject(details.getCouponInfo(), Coupon.class);
            coupon=(Coupon)CouponObject;
            //获取平台类型
            Object CpsTypeObject = JSON.parseObject(details.getCpsType(), CpsType.class);
            cpsType=(CpsType) CpsTypeObject;
            // 获取店铺信息
            Object ShopInfoObject = JSON.parseObject(details.getShopInfo(), ShopInfo290.class);
            shopInfo = (ShopInfo290)ShopInfoObject;

            //填充详情信息
            setData();
            //获取猜你喜欢数据
            httpPost();
            //是否收藏
            isCollection(details.getId());
        }


        if (message.what == LogicActions.MerchantWebSuccess) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            if (h5Links.size()>0){
                String url=h5Links.get(0).getUrl()+details.getId();
                    web.post(() -> {
                        if (web!=null) {
                            web.loadUrl(url);
                        }
                    });
            }
        }
        //购买
        if (message.what == LogicActions.GoodsPromotionSuccess) {
            dismissDialog();
            Alibc alibc = (Alibc) message.obj;
            String Couponlink=alibc.getCouponlink();
            String pid=alibc.getPid();
            //分享
            if (clickEvent==1){
                ArrayList<ShareCheck> list = new ArrayList<>();
                for (String item : ImageBanerList) {
                    ShareCheck share = new ShareCheck();
                    share.setImage(item);
                    share.setCheck(0);
                    list.add(share);
                }
                list.get(0).setCheck(1);
                dismissDialog();
                boolean istb=false;
                if (cpsType.getCode().equals("tm")){
                istb=true;
                }
                startActivity(new Intent(this, ShareActivity330.class)
                        .putExtra("image", list)
                        .putExtra("name", details.getName())
                        .putExtra("sales", details.getSales())
                        .putExtra("money", details.getPrice())
                        .putExtra("commission", details.getNormalCommission())
                        .putExtra("shopprice", details.getCost())
                        .putExtra("discount", details.getCoupon())
                        .putExtra("shortLink", alibc.getShareshortlink())
                        .putExtra("recommend", details.getDesc())
                        .putExtra("countersign", alibc.getPwd())
                        .putExtra("isCheck", istb));
            }else {
                if (alibc.getTips().getType().equals("hb")){
                   //红包
                    if (alibc.getTips().getKey().equals("")) {
                        DateStorage.setIsKey("");
                        Utils.Alibc(this, Couponlink, pid, this);
                    } else {
                        if (alibc.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                            //已经点击了不再提醒
                            Utils.Alibc(this, Couponlink, pid, this);
                        } else {
                            try {
                                CouponLinkDialog couponLinkDialog = new CouponLinkDialog(this, alibc.getTips());
                                couponLinkDialog.showDialog();
                                couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        if (pos == 0) {//不再提醒
                                            DateStorage.setIsKey(alibc.getTips().getKey());
                                        } else {
                                            isshowDialog = true;
                                        }
                                        Utils.Alibc(CommodityActivity290.this, Couponlink, pid, CommodityActivity290.this);
                                    }
                                });
                            } catch (Exception e) {

                            }

                        }
                    }
                }else{
                 //比价
                    if (!DateStorage.getHasBj().equals("")) {
                        Utils.Alibc(this, Couponlink, pid, this);
                    } else {
                        try {
                            BiJiaDialog biJiaDialog = new BiJiaDialog(this, alibc.getTips());
                            biJiaDialog.showDialog();
                            biJiaDialog.setOnClickListener(new BiJiaDialog.OnBtnClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    if (pos == 0) {//不再提醒
                                        DateStorage.setHasBj("bj");
                                        Utils.Alibc(CommodityActivity290.this, Couponlink, pid, CommodityActivity290.this);
                                    } else if(pos==1){
                                        Utils.Alibc(CommodityActivity290.this, Couponlink, pid, CommodityActivity290.this);
                                    }else{
                                        Intent intent = new Intent(CommodityActivity290.this, AliAuthWebViewActivity.class);
                                        intent.putExtra(Variable.webUrl, alibc.getTips().getRuleurl());
                                        intent.putExtra("isTitle", true);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } catch (Exception e) {

                        }
                    }

                }

            }

        }

        //商品收藏状态 0-未收藏 1-已收藏
        if (message.what == LogicActions.IsCollectionSuccess) {
            JSONObject object=(JSONObject) message.obj;
            details.setCollectid(object.optString("collectid"));
            details.setIscollection(object.optString("iscollection"));
            if (details.getIscollection().equals("0")){
                isCheck = false;
                commodityCollectionImage.setImageResource(R.mipmap.pjw_like);
                commodityCollectionText.setTextColor(0xff444444);
            } else {
                isCheck = true;
                commodityCollectionImage.setImageResource(R.mipmap.pjw_haslike);
                commodityCollectionText.setTextColor(getResources().getColor(R.color.mainColor));
            }
        }


        if (message.what == LogicActions.ShopLike290Success) {
              list =(ArrayList<MainBottomListItem>) message.obj;
            if (list!=null&&list.size()>0){
                recomend_goods_layout.setVisibility(View.VISIBLE);
                adapter.setNewData(list);
            }else{
                noRecommend=true;
                goods_title_02.setVisibility(View.GONE);
                recomend_goods_layout.setVisibility(View.GONE);
            }
        }
        if (message.what == LogicActions.ConfirmCollectionSuccess) {
            // 收藏成功
            toast("收藏成功");
        }
        if (message.what == LogicActions.CancelCollectionSuccess) {
            // 取消收藏
            toast("取消收藏");
        }
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            switch (type) {
                case "0":
                    web.post(() -> web.loadUrl(h5Links.get(0).getUrl()));
                    break;
                default:
                    if (h5Links.size() == 0) {
                        startActivity(new Intent(this, ApplyActivity.class));
                    } else {
                        startActivity(new Intent(this, WebViewActivity.class).putExtra("isTitle" , true).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
                    }
                    break;
            }
        }
        if (message.what == LogicActions.GetUserInfoSuccess) {
             UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            if (Objects.equals(login.getUsertype(), "2")) {
                commodityTipsLayout.setVisibility(View.VISIBLE);
            } else {
                commodityTipsLayout.setVisibility(View.GONE);
            }
        }


        dismissDialog();
    }

    /**
     * 设置商品信息
     */
    private void setData() {
        commodity_mask.setVisibility(View.GONE);
        titleBlock.setVisibility(View.VISIBLE);
        if (DateStorage.getLoginStatus()) {
            commodity_save_money_layout.setVisibility(View.VISIBLE);
        }else{
            commodity_save_money_layout.setVisibility(View.GONE);
        }
        getWebUrl();
          commodityTipsText.setText(details.getUpCommission()+"元");
          if (DateStorage.getLoginStatus()) {
            commodity_estimate_two_sub.setVisibility(View.VISIBLE);
            commodity_btn_text_sub.setVisibility(View.VISIBLE);
            commodityEstimateTwo.setText("立即分享");
            commodityBtnText.setText("马上购买");
            commodityEstimateTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_10));
            commodityBtnText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_10));
            if (Objects.equals(login.getUsertype(), "3")) {
                commodity_save_money.setVisibility(View.GONE);
            } else {
                commodity_save_money.setVisibility(View.VISIBLE);
                commodity_save_money.setText("¥" + details.getNormalCommission());
            }
            if (Objects.equals(login.getUsertype(), "2")) {
                commodityTipsLayout.setVisibility(View.VISIBLE);
            } else {
                commodityTipsLayout.setVisibility(View.GONE);
            }
              if (details.getBuySave().equals("") || details.getBuySave().equals("0")) {
                  commodity_btn_text_sub.setText("省¥0");
              } else {
                  commodity_btn_text_sub.setText("省¥" + details.getBuySave());
              }
              commodity_estimate_two_sub.setText("奖¥" + details.getNormalCommission());
        } else {
              commodity_estimate_two_sub.setVisibility(View.GONE);
              commodity_btn_text_sub.setVisibility(View.GONE);
              commodityEstimateTwo.setText("立即分享");
              commodityBtnText.setText("马上购买");
              commodityEstimateTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_15));
              commodityBtnText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_15));
              commodityTipsLayout.setVisibility(View.GONE);
        }
        commodityDiscountText.setText(details.getCoupon());
        if (details.getCoupon().equals("0")) {
            commodityDiscountBtnText.setText("立即购买");
            commodityBtnText.setText("立即购买");
        }
        if (coupon!=null) {
            coupon_layout.setVisibility(View.VISIBLE);
            commodityStartTime.setText(coupon.getStartTime());
            commodityEndTime.setText(coupon.getEndTime());
        }else{
            coupon_layout.setVisibility(View.GONE);
        }
        if (Objects.equals(details.getVideoUrl(), "")) {
            commodityVideo.setVisibility(View.GONE);
        } else {
            commodityVideo.setVisibility(View.VISIBLE);
        }
        commodityMoney.setText(details.getPrice());
        commodityDiscount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        commodityDiscount.setText(getString(R.string.money) + details.getCost());
        commoditySales.setText("月销"+Utils.getNumber(details.getSales()));
        if (cpsType.getLogo()==null){
            switch (cpsType.getCode()){
                case "tb":
                    commodity_check_iv.setImageResource(R.mipmap.tmall_shop_icon);
                    break;
                case "tm":
                    commodity_check_iv.setImageResource(R.mipmap.tbao_shop_icon);
                    break;
            }
        }else {
            Utils.displayImage(this, cpsType.getLogo(), commodity_check_iv);
        }
        commodityTitle.setText("     "+details.getName());
        if (TextUtils.isEmpty(details.getDesc())) {
            commodityRecommend.setVisibility(View.GONE);
        } else {
            commodityRecommend.setVisibility(View.VISIBLE);
            commodityRecommendText.setText(details.getDesc());
        }

        if (shopInfo!=null) {
            setShopInfo(shopInfo, cpsType.getCode());
        }else{
            commodityShopLayout.setVisibility(View.GONE);
        }
        if(details.isAllow()){
            commodityDiscountBtn.setEnabled(true);
            commodityTable.setVisibility(View.GONE);
        }else{
            commodityDiscountBtn.setEnabled(false);
            commodityTable.setVisibility(View.VISIBLE);
            commodityTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    //通过接口返回的weburl来显示商品详情
    private void getWebUrl(){
        paramMap.clear();
        paramMap.put("type", "5");
        NetworkRequest.getInstance().POST(handler, paramMap, "MerchantWeb", HttpCommon.GetMerchantWeb);
    }

    /**
     * 设置店铺信息
     *
     * @param shopInfo
     */
    private void setShopInfo(ShopInfo290 shopInfo,String plat) {
        ArrayList<ShopInfo290.ShopInfoData> list = shopInfo.getEvaluates();
        commodityShopName.setText(shopInfo.getName());
        if (!shopInfo.getUrl().equals("")){
         goshop.setVisibility(View.VISIBLE);
        }else{
            goshop.setVisibility(View.GONE);
        }
        if (shopInfo.getLogo()!=null&&!shopInfo.getLogo().equals("")){
            commodityShopAvatar.setVisibility(View.VISIBLE);
             Utils.displayImageRounded(CommodityActivity290.this, shopInfo.getLogo(),commodityShopAvatar,5);
        }else{
            commodityShopAvatar.setVisibility(View.GONE);
        }
        if (list!=null&&list.size()>=3) {
            comment_layout.setVisibility(View.VISIBLE);
            commodityShopOneText.setText(list.get(0).getScore());
            commodity_shop_one_title.setText(list.get(0).getProject() + "  ");
            commodityShopOneEvaluate.setText(list.get(0).getContrastStr());
            commodityShopTwoText.setText(list.get(1).getScore());
            commodity_shop_two_title.setText(list.get(1).getProject() + "  ");
            commodityShopTwoEvaluate.setText(list.get(1).getContrastStr());
            commodityShopThreeText.setText(list.get(2).getScore());
            commodity_shop_three_title.setText(list.get(2).getProject() + "  ");
            commodityShopThreeEvaluate.setText(list.get(2).getContrastStr());
        }else{
            comment_layout.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webSetting() {
        WebSettings settings = web.getSettings();
        //从Android5.0开始，WebView默认不支持同时加载Https和Http混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.clearCache(false);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
    }
    /**
     * 设置轮播图
     *
     * @param list
     */
    private void bannerImage(ArrayList<String> list) {
        commodityBanner.setPages(() -> new BannerAdapter(), list)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setOnItemClickListener(position -> startActivity(new Intent(this, ImageActivity.class).putStringArrayListExtra("imageList", list).putExtra("position", position)));
        if (list.size() > 1) {
            commodityBanner.setPointViewVisible(true);
        } else {
            commodityBanner.setPointViewVisible(false);
            commodityBanner.setCanLoop(false);
        }
    }

    @OnClick({R.id.back, R.id.commodity_share, R.id.commodity_btn, R.id.commodity_collection,
            R.id.commodity_video, R.id.commodity_discount_btn, R.id.return_top, R.id.commodity_tips_layout,
            R.id.commodity_undercarriage, R.id.commodity_main,R.id.back_image_iv,R.id.commodity_mask,R.id.goshop,R.id.look_more_layout,
            R.id.goods_title_01,R.id.goods_title_02,R.id.goods_title_03,R.id.goods_title_layout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
            case R.id.back_image_iv:
                isFinish();
                break;
            case R.id.commodity_mask:
                break;
            case R.id.commodity_share:// 分享
                if (NetStateUtils.isNetworkConnected(this)) {
                    if (DateStorage.getLoginStatus()) {
                        clickEvent=1;
                        goodsPromotion();
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
                break;
            case R.id.commodity_btn:// 购买
            case R.id.commodity_discount_btn:// 优惠券
                if (NetStateUtils.isNetworkConnected(this)) {
                    if (DateStorage.getLoginStatus()) {
                        clickEvent=0;
                        goodsPromotion();
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
                break;
            case R.id.commodity_collection:// 收藏
                if (NetStateUtils.isNetworkConnected(this)) {
                if (DateStorage.getLoginStatus()) {
                    if (isCheck) {
                        isCheck = false;
                        commodityCollectionImage.setImageResource(R.mipmap.pjw_like);
                        commodityCollectionText.setTextColor(0xff444444);
                    } else {
                        isCheck = true;
                        commodityCollectionImage.setImageResource(R.mipmap.pjw_haslike);
                        commodityCollectionText.setTextColor(getResources().getColor(R.color.mainColor));

                    }
                    collection();
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
                }else{
                    toast("当前网络已断开，请连接网络");
                }

                break;
            case R.id.commodity_video:// 跳转视频播放
                intent = new Intent(this, VideoActivity.class);
                intent.putExtra("videoUrl", details.getVideoUrl());
                intent.putExtra("title", details.getName());
                break;
            case R.id.return_top:// 返回顶部
                commodityScroll.smoothScrollTo(0, 0);
                break;
            case R.id.commodity_tips_layout:// 立即升级
                if (Variable.FastClickTime()) {
                    return;
                }
                switch (login.getUsertype()) {
                    case "2":
                        // 获取H5地址
                        paramMap.clear();
                        type = "2";
                        paramMap.put("type", type);
                        NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                        break;
                    case "4":
                        // 获取H5地址
                        paramMap.clear();
                        type = "3";
                        paramMap.put("type", type);
                        NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                        break;
                    default:
                        intent = new Intent(CommodityActivity290.this, ApplyActivity.class);
                        break;

                }
                break;
            case R.id.commodity_undercarriage:
                // 防止点击
                break;
            case R.id.commodity_main:// 跳转首页
//                collection();
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
                break;
            //进入店铺
            case R.id.goshop:
                if (!shopInfo.getUrl().equals("")){
                    intent = new Intent(this, AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl,shopInfo.getUrl());
                    intent.putExtra("isGoTaobao" , false);//不跳淘宝天猫
                }
                break;
            //查看更多
            case R.id.look_more_layout:
              intent = new Intent(this, MoreRecommendGoodsActivity.class);
              intent.putExtra("platType",cpsType.getCode());
              intent.putExtra("material","");
              intent.putExtra("shopid",details.getId());
              break;

            case R.id.goods_title_01:
                tab="tab01";
                commodityScroll.smoothScrollTo(0, Utils.dipToPixel(R.dimen.dp_80));
                setTopTabColor();
                break;
            case R.id.goods_title_02:
                tab="tab02";
                commodityScroll.smoothScrollTo(0, goods_detail_layout.getHeight()-Utils.dipToPixel(R.dimen.dp_60));
                setTopTabColor();
                break;
            case R.id.goods_title_03:
                tab="tab03";
                if (noRecommend){
                    commodityScroll.smoothScrollTo(0, goods_detail_layout.getHeight()-Utils.dipToPixel(R.dimen.dp_60));
                }else{
                    commodityScroll.smoothScrollTo(0, goods_detail_layout.getHeight()+recomend_goods_layout.getHeight()-Utils.dipToPixel(R.dimen.dp_60));
                }
                setTopTabColor();
                break;
            case R.id.goods_title_layout:
                break;


        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressedSupport() {
//        collection();
        isFinish();
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        /**
         * 通知应用程序当前网页加载的进度
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            view.requestFocus();
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
            dismissDialog();
        }

    };

    private void collection() {
          if (details==null){
           return;
          }
            if (isCheck) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopid", details.getId());
                NetworkRequest.getInstance().POST(handler, paramMap, "ConfirmCollection", HttpCommon.ConfirmCollection);
            } else {
                if (details != null) {
                    if (!details.getCollectid().equals("")) {
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("shopid", details.getId());
                        NetworkRequest.getInstance().POST(handler, paramMap, "CancelCollection", HttpCommon.CancelCollection);
                    }
                }
            }

    }

    private void httpPost() {
        // 商品搜索（猜你喜欢）
        if (NetStateUtils.isNetworkConnected(this)) {
            // 首页底部列表请求商品搜索（标签）
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("startindex", page + "");
            paramMap.put("pagesize", pageSize + "");
            paramMap.put("material", type);
            paramMap.put("shopid", details.getId());
            NetworkRequest.getInstance().POST(handler, paramMap, "ShopLike290", HttpCommon.MainBottomList);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(this, CommodityActivity290.class).putExtra("shopId", list.get(position).getId()).putExtra("source", list.get(position).getSource()).putExtra("sourceId", list.get(position).getSourceId()));
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY < Utils.dipToPixel(R.dimen.dp_190)) {
            returnTop.setVisibility(View.GONE);
        } else {
            returnTop.setVisibility(View.VISIBLE);
        }
        if (scrollY >= 0 && scrollY <=Utils.dipToPixel(R.dimen.dp_80)) {
            alpha = (((float) scrollY) / (Utils.dipToPixel(R.dimen.dp_80))) * 255;
            isHeightEnouch=false;
        } else if (scrollY >Utils.dipToPixel(R.dimen.dp_80)) {
           isHeightEnouch=true;
            alpha=255;
        }
        int  goods_detail_height=goods_detail_layout.getHeight()-Utils.dipToPixel(R.dimen.dp_70);
        int  recommend_detail_height=0;
        if (!noRecommend){
            recommend_detail_height=goods_detail_layout.getHeight()+recomend_goods_layout.getHeight()-Utils.dipToPixel(R.dimen.dp_70);
            if (scrollY<goods_detail_height){
                tab="tab01";
            }else if(scrollY>=goods_detail_height&&scrollY<recommend_detail_height){
                tab="tab02";
            }else{
                tab="tab03";
            }
        }else{
            if (scrollY<goods_detail_height){
                tab="tab01";
            }else{
                tab="tab03";
            }
        }

        setTopTabColor();
    }
    float alpha=0;
    boolean isHeightEnouch=false;
    boolean noRecommend= false;
    String tab="tab01";
    //设置头部tab颜色
    private void setTopTabColor(){
       if (isHeightEnouch){
           titleBlock.setBackgroundColor(Color.argb(((int)alpha), 245, 245, 245));
           backImage.setImageAlpha(0);
       }else {
           titleBlock.setBackgroundColor(Color.argb(((int) alpha), 245, 245, 245));
           backImage.setImageAlpha(255 - ((int) alpha));
       }
        switch (tab) {
                case "tab01":
                    title_01.setTextColor(Color.parseColor("#000000"));
                    title_02.setTextColor(Color.parseColor("#666666"));
                    title_03.setTextColor(Color.parseColor("#666666"));
                    title_spilt_01.setVisibility(View.VISIBLE);
                    title_spilt_02.setVisibility(View.GONE);
                    title_spilt_03.setVisibility(View.GONE);
                break;
                case "tab02":
                    title_01.setTextColor(Color.parseColor("#666666"));
                    title_02.setTextColor(Color.parseColor("#000000"));
                    title_03.setTextColor(Color.parseColor("#666666"));
                    title_spilt_02.setVisibility(View.VISIBLE);
                    title_spilt_01.setVisibility(View.GONE);
                    title_spilt_03.setVisibility(View.GONE);
                break;
                case "tab03":
                    title_01.setTextColor(Color.parseColor("#666666"));
                    title_02.setTextColor(Color.parseColor("#666666"));
                    title_03.setTextColor(Color.parseColor("#000000"));
                    title_spilt_02.setVisibility(View.GONE);
                    title_spilt_01.setVisibility(View.GONE);
                    title_spilt_03.setVisibility(View.VISIBLE);
                break;
        }
    }
   /*
     * 详情页 领券、购买、分享 公用一个
    */
   private int clickEvent=0;
   private void goodsPromotion(){
       showDialog();
       paramMap.clear();
       paramMap.put("userid", login.getUserid());
       paramMap.put("shopid", details.getId());
       paramMap.put("source", details.getSource());
       paramMap.put("sourceId",details.getSourceId());
       if (coupon!=null) {
           paramMap.put("couponid", coupon.getId());
       }
       if (clickEvent==1){//分享
           paramMap.put("tpwd", "1");
           paramMap.put("sharelink","1");
       }else{//领券购买
           paramMap.put("tpwd", "");
           paramMap.put("sharelink","");
       }
       NetworkRequest.getInstance().POST(handler, paramMap, "GoodsPromotion", HttpCommon.GoodsPromotion);
   }



    @Override
    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
        // 用户操作中成功信息回调
    }

    @Override
    public void onFailure(int code, String msg) {
        // 用户操作中错误信息回调
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Variable.AuthShowStatus=false;
        if(web!=null){
            web.clearCache(true);
            web.removeAllViews();
            web.destroy();
            web=null;
        }
    }


}
