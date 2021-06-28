package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.BannerAdapter;
import com.lxkj.dmhw.adapter.CommodityAdapterPJW;
import com.lxkj.dmhw.adapter.CommodityAdapterPJW290;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.ShopInfo290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ObserveScrollView;
import com.lxkj.dmhw.dialog.PDDExplainDialog;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 拼多多 京东 唯品会 商品详情页
 */
public class CommodityActivityPJW extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, ObserveScrollView.OnScrollListener, AlibcTradeCallback {

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
    @BindView(R.id.month_sales)
    LinearLayout month_sales;
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
    @BindView(R.id.commodity_shop_layout)
    LinearLayout commodityShopLayout;
    @BindView(R.id.coupon_layout)
    ScaleLayout commodity_discount_layout;
    @BindView(R.id.recomend_goods_layout)
    LinearLayout recomend_goods_layout;
    //推荐宝贝
    @BindView(R.id.commodity_recommend_recycler)
    RecyclerView commodity_recommend_recycler;
    //宝贝详情
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


    @BindView(R.id.comment_layout)
    RelativeLayout comment_layout;
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
    @BindView(R.id.recommend_title)
    TextView recommend_title;
    @BindView(R.id.upgrade_title)
    TextView upgrade_title;
    @BindView(R.id.lijiupgrade)
    TextView lijiupgrade;

    private CommodityDetails290 details;
    // 判断是否收藏
    private boolean isCheck;
    // 判断事件是否是分享
    private boolean isShare=false;

    // 推荐商品adapter
    private CommodityAdapterPJW290 adapter290;

    // 宝贝详情
    private CommodityAdapterPJW adapter;
    //猜你喜欢商品数据
    private ArrayList<MainBottomListItem> list;
    // 全网搜数据
    private String type = "0";
    @BindView(R.id.goods_detail_tv)
    TextView goods_detail_tv;


    @BindView(R.id.quanhou_title)
    TextView quanhou_title;

    @BindView(R.id.look_more_layout)
    LinearLayout look_more_layout;

    @BindView(R.id.commodity_mask)
    RelativeLayout commodity_mask;
    @BindView(R.id.back_image_iv)
    ImageView back_image_iv;
    @BindView(R.id.commodity_save_money_layout)
    LinearLayout commodity_save_money_layout;

    @BindView(R.id.commodity_save_money_title)
    TextView commodity_save_money_title;
    @BindView(R.id.commodity_sales_title)
    TextView commodity_sales_title;
    @BindView(R.id.recommendtv)
    TextView recommendtv;
    @BindView(R.id.look_more_title)
    TextView look_more_title;




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

    //详情图片
    private ArrayList<String> DescImageList=new ArrayList<>();

    JSONArray descImagesArray=null;

    public static CommodityActivityPJW commodityActivityPJW=null;

    //登录刷新标志
    private  boolean loginRefreshFlag=false;

    PDDExplainDialog pddExplainDialog=null;
    private  String paltType="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_pjw);
        ButterKnife.bind(this);
        commodityActivityPJW=this;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (DateStorage.getLoginStatus()){
            commodity_save_money_layout.setVisibility(View.VISIBLE);
        }else{
            commodity_save_money_layout.setVisibility(View.GONE);
        }
        Variable.AuthShowStatus=true;//控制推荐授权弹窗

        // 设置Recycler
        commodityShopRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        // 初始化adapter
        adapter = new CommodityAdapterPJW(this);
        commodityShopRecycler.setAdapter(adapter);
        commodityShopRecycler.setNestedScrollingEnabled(false);

        //宝贝推荐
        commodity_recommend_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, 3));
        commodity_recommend_recycler.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dipToPixel(R.dimen.dp_15), false));
        adapter290 = new CommodityAdapterPJW290(this);
        commodity_recommend_recycler.setAdapter(adapter290);
        adapter290.setOnItemClickListener(this);
        commodity_recommend_recycler.setNestedScrollingEnabled(false);

        commodityScroll.setOnScrollListener(this);
        title_01.setTextColor(Color.parseColor("#000000"));
        title_spilt_01.setBackgroundColor(Color.parseColor("#000000"));
        title_02.setTextColor(Color.parseColor("#666666"));
        title_spilt_02.setBackgroundColor(Color.parseColor("#000000"));
        title_03.setTextColor(Color.parseColor("#666666"));
        title_spilt_03.setBackgroundColor(Color.parseColor("#000000"));

        TextPaint tp= commodityTitle.getPaint();
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
                ToastUtil.showImageToast(CommodityActivityPJW.this,"复制成功",R.mipmap.toast_img);
                return false;
            }
        });

        commodityTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utils.copyText(commodityTitle.getText().toString().trim());
                ToastUtil.showImageToast(CommodityActivityPJW.this,"复制成功",R.mipmap.toast_img);
                return false;
            }
        });
        initData();
    }

     private void initData(){
        //拼京唯商品详情接口
         paltType=getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("GoodsId")!=null) {
            showDialog();
            paramMap.clear();
            paramMap.put("goodsId", getIntent().getStringExtra("GoodsId"));
            if (getIntent().getStringExtra("type").equals("jd")) {
                NetworkRequest.getInstance().GETNew(handler, paramMap, "PJWByGoodsId", HttpCommon.JDByGoodsId);
            }else if(getIntent().getStringExtra("type").equals("pdd")){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "PJWByGoodsId", HttpCommon.PDDByGoodsId);
            }else if(getIntent().getStringExtra("type").equals("wph")){
                quanhou_title.setText("折后");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "PJWByGoodsId", HttpCommon.WPHByGoodsId);
            }else if(getIntent().getStringExtra("type").equals("sn")){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "PJWByGoodsId", HttpCommon.SNByGoodsId);
            }else{
                quanhou_title.setText("折后");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "PJWByGoodsId", HttpCommon.KLByGoodsId);
            }
        }
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
        if (message.what == LogicActions.ShopInfoNoSuccess) {
            commodityShopLayout.setVisibility(View.GONE);
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
        if (message.what == LogicActions.PJWByGoodsIdSuccess) {
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
            if (details.getHasCoupon()) {
                commodity_discount_layout.setVisibility(View.VISIBLE);
                Object CouponObject = JSON.parseObject(details.getCouponInfo(), Coupon.class);
                coupon = (Coupon) CouponObject;
            }else{
                commodity_discount_layout.setVisibility(View.GONE);
            }
            //获取平台类型
            Object CpsTypeObject = JSON.parseObject(details.getCpsType(), CpsType.class);
            cpsType=(CpsType) CpsTypeObject;

            // 获取店铺信息
            Object ShopInfoObject = JSON.parseObject(details.getShopInfo(), ShopInfo290.class);
            shopInfo = (ShopInfo290)ShopInfoObject;

           descImagesArray = JSON.parseArray(details.getDescImages());

            //填充详情信息
            setData();
            //宝贝推荐
            httpPost();
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

        //呼起PJWAPP
        if (message.what == LogicActions.GenByGoodsIdSuccess) {
            PJWLink link = (PJWLink) message.obj;
            if (isShare){
                if (details==null||coupon==null||cpsType==null){
                    return;
                }
                ArrayList<ShareCheck> list = new ArrayList<>();
                if (ImageBanerList==null||ImageBanerList.size()==0){
                    return;
                }
                for (String item : ImageBanerList) {
                    ShareCheck share = new ShareCheck();
                    share.setImage(item);
                    share.setCheck(0);
                    list.add(share);
                }
                list.get(0).setCheck(1);
                if (getIntent().getStringExtra("type").equals("wph")) {
                 coupon.setPrice(details.getSave());
                }
                startActivity(new Intent(this, SharePJWActivity330.class)
                        .putExtra("image", list)
                        .putExtra("name", details.getName())
                        .putExtra("sales", details.getSales())
                        .putExtra("money", details.getPrice())
                        .putExtra("commission", details.getNormalCommission())
                        .putExtra("shopprice", details.getCost())
                        .putExtra("discount", details.getSave())
                        .putExtra("shortLink",link.getShortUrl())
                        .putExtra("recommend", details.getDesc())
                        .putExtra("platLogo", cpsType.getLogo())
                        .putExtra("type", getIntent().getStringExtra("type")));
            }else {
                if (Utils.isNullOrEmpty(link.getShortUrl())) {
                    Toast.makeText(CommodityActivityPJW.this, "URL为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                int plat=1;
                if (getIntent().getStringExtra("type").equals("jd")) {
                  plat=2;
                } else if (getIntent().getStringExtra("type").equals("pdd")) {
                  plat=1;
                } else if(getIntent().getStringExtra("type").equals("wph")) {
                    //唯品会
                  plat=3;
                }else if(getIntent().getStringExtra("type").equals("sn")){
                    //苏宁易购
                    plat=5;
                }else{
                    plat=6;
                }
             Utils.callMorePlatFrom(this,plat,link.getSchemaUrl(),link.getUrl());
            }
            dismissDialog();
        }
        // 获取H5
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            switch (type) {
                case "0":
                    startActivity(new Intent(this, AliAuthWebViewActivity.class).putExtra("isTitle" , false).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
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

        if (message.what == LogicActions.ShopLike290Success) {
            list =(ArrayList<MainBottomListItem>) message.obj;
            if (list!=null&&list.size()>0){
                recomend_goods_layout.setVisibility(View.VISIBLE);
                adapter290.setNewData(list);
            }else{
                noRecommend=true;
                goods_title_02.setVisibility(View.GONE);
                recomend_goods_layout.setVisibility(View.GONE);
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
         commodityTipsText.setText(details.getUpCommission()+"元");
          if (DateStorage.getLoginStatus()) {
            commodityEstimateTwo.setText("立即分享");
                commodityBtnText.setText("马上购买");
            if (Objects.equals(login.getUsertype(), "3")) {
                commodity_save_money.setVisibility(View.GONE);
            } else {
                commodity_save_money.setVisibility(View.VISIBLE);
                commodity_save_money.setText( getResources().getString(R.string.money) + details.getNormalCommission());
            }
            if (Objects.equals(login.getUsertype(), "2")) {
                commodityTipsLayout.setVisibility(View.VISIBLE);
            } else {
                commodityTipsLayout.setVisibility(View.GONE);
            }
        } else {
            commodityEstimateTwo.setText("立即分享");
            commodityBtnText.setText("马上购买");
            commodityTipsLayout.setVisibility(View.GONE);
        }
        if (coupon!=null) {
            if (coupon.getPrice().equals("0")) {
                commodityDiscountBtnText.setText("马上购买");
                commodityBtnText.setText("马上购买");
            }
            commodityDiscountText.setText(coupon.getPrice());
            commodityStartTime.setText(coupon.getStartTime());
            commodityEndTime.setText(coupon.getEndTime());
        }
        commodityVideo.setVisibility(View.GONE);
        commodityMoney.setText(details.getPrice());
        commodityDiscount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        commodityDiscount.setText(getString(R.string.money) + details.getCost());
        if(details.getSales().equals("")){
            month_sales.setVisibility(View.GONE);
        }else{
            commoditySales.setText("月销"+details.getSales());
        }
        if (cpsType!=null){
            if (cpsType.getLogo()==null){
            switch (cpsType.getCode()){
                case "pdd":
                    commodity_check_iv.setImageResource(R.mipmap.pdd_shop_icon);
                    break;
                case "jd":
                    commodity_check_iv.setImageResource(R.mipmap.jd_shop_icon);
                    break;
                case "wph":
                    commodity_check_iv.setImageResource(R.mipmap.wph_shop_icon);
                    break;
                case "sn":
                    commodity_check_iv.setImageResource(R.mipmap.order_sn_icon);
                    break;
                case "kl":
                    commodity_check_iv.setImageResource(R.mipmap.kl_icon);
                    break;
            }
            }else{
                Utils.displayImage(this,cpsType.getLogo(),commodity_check_iv);
            }
        }
        commodityTitle.setText("     "+details.getName());

        if (TextUtils.isEmpty(details.getDesc())) {
            commodityRecommend.setVisibility(View.GONE);
        } else {
            commodityRecommend.setVisibility(View.VISIBLE);
            commodityRecommendText.setText(details.getDesc());
        }

        if (!DateStorage.getLoginStatus()) {
            commodityEstimateTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_15));
            commodityBtnText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_15));
            commodity_estimate_two_sub.setVisibility(View.GONE);
            commodity_btn_text_sub.setVisibility(View.GONE);
        }else{
            commodity_estimate_two_sub.setText("奖¥"+details.getNormalCommission());
            if (details.getBuySave().equals("")||details.getBuySave().equals("0")){
                commodity_btn_text_sub.setText("省¥0");
            }else{
                commodity_btn_text_sub.setText("省¥"+details.getBuySave());
            }
        }
        if (descImagesArray!=null&&descImagesArray.size()>0){
            for (int i=0;i<descImagesArray.size();i++) {
                DescImageList.add((String) descImagesArray.get(i));
            }
            adapter.setNewData(DescImageList);
        }else{
            if (ImageBanerList!=null&&ImageBanerList.size()>0) {
                adapter.setNewData(ImageBanerList);
            }
        }

        if (shopInfo!=null) {
            setShopInfo(shopInfo, cpsType.getCode());
        }else{
            commodityShopLayout.setVisibility(View.GONE);
        }

        if (!details.getIsCollect()){
            isCheck = false;
            commodityCollectionImage.setImageResource(R.mipmap.pjw_like);
            commodityCollectionText.setTextColor(0xff444444);
        } else {
            isCheck = true;
            commodityCollectionImage.setImageResource(R.mipmap.pjw_haslike);
            commodityCollectionText.setTextColor(getResources().getColor(R.color.mainColor));
        }

        //弹出拼多多的低佣风险提示 details.getIsLowerPrice()
        if (paltType.equals("pdd")&&details.getIsLowerPrice()){
            pddExplainDialog=new PDDExplainDialog(this);
            pddExplainDialog.showDialog(details.getLowerPriceRuleUrl());
        }
    }

    /**
     * 设置店铺信息
     *
     * @param shopInfo
     */
    private void setShopInfo(ShopInfo290 shopInfo,String plat) {
        commodityShopName.setText(shopInfo.getName());
        if (shopInfo.getLogo()!=null&&!shopInfo.getLogo().equals("")) {
            commodityShopAvatar.setVisibility(View.VISIBLE);
            Utils.displayImageRounded(this, shopInfo.getLogo(), commodityShopAvatar, 5);
        }else{
            commodityShopAvatar.setVisibility(View.GONE);
        }
        ArrayList<ShopInfo290.ShopInfoData> list = shopInfo.getEvaluates();
        if (list!=null&&list.size()>=3) {
            comment_layout.setVisibility(View.VISIBLE);
            commodityShopOneText.setText(list.get(0).getScore());
            commodity_shop_one_title.setText(list.get(0).getProject());
            commodityShopOneEvaluate.setText(list.get(0).getContrastStr());
            commodityShopTwoText.setText(list.get(1).getScore());
            commodity_shop_two_title.setText(list.get(1).getProject());
            commodityShopTwoEvaluate.setText(list.get(1).getContrastStr());
            commodityShopThreeText.setText(list.get(2).getScore());
            commodity_shop_three_title.setText(list.get(2).getProject());
            commodityShopThreeEvaluate.setText(list.get(2).getContrastStr());
        }else{
            comment_layout.setVisibility(View.GONE);
        }
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
            R.id.commodity_undercarriage, R.id.commodity_main,R.id.back_image_iv,R.id.commodity_mask,R.id.look_more_layout, R.id.goods_title_01,R.id.goods_title_02,R.id.goods_title_03,R.id.goods_title_layout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                collection();
                isFinish();
                break;
            case R.id.back_image_iv:
                isFinish();
                break;
            case R.id.commodity_mask:
                break;
            case R.id.commodity_share:// 分享
                if (NetStateUtils.isNetworkConnected(this)) {
                    if (DateStorage.getLoginStatus()) {
                        isShare=true;
                        getGenByGoodsId(details.getId());
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
                break;
            case R.id.commodity_discount_btn:// 优惠券
            case R.id.commodity_btn:// 购买
                if (NetStateUtils.isNetworkConnected(this)) {
                    if (DateStorage.getLoginStatus()) {
                        isShare=false;
                        getGenByGoodsId(details.getId());
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
//                        toast("取消收藏");
                    } else {
                        isCheck = true;
                        commodityCollectionImage.setImageResource(R.mipmap.pjw_haslike);
                        commodityCollectionText.setTextColor(getResources().getColor(R.color.mainColor));
//                        toast("收藏成功");
                    }
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
                        intent = new Intent(CommodityActivityPJW.this, ApplyActivity.class);
                        break;

                }
                break;
            case R.id.commodity_undercarriage:
                // 防止点击
                break;
            case R.id.commodity_main:// 跳转首页
                collection();
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
                break;
            case R.id.look_more_layout:// 查看更多
                intent = new Intent(this, MoreRecommendGoodsActivity.class);
                intent.putExtra("platType",cpsType.getCode());
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
        collection();
        isFinish();
    }


    private void collection() {
          if (details==null){
           return;
          }
            paramMap.clear();
          if (login!=null){
              paramMap.put("userid", login.getUserid());
           }
            paramMap.put("goodsId", details.getId());
            if (isCheck) {
                if (getIntent().getStringExtra("type").equals("jd")) {
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.JDCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("pdd")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.PDDCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("wph")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.WPHCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("sn")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.SNCollectGoodsById);
                }else{
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.KLCollectGoodsById);
                }
            } else {
                if (getIntent().getStringExtra("type").equals("jd")) {
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "CancelCollection", HttpCommon.JDUnCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("pdd")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "CancelCollection", HttpCommon.PDDUnCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("wph")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.WPHUnCollectGoodsById);
                }else if(getIntent().getStringExtra("type").equals("sn")){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.SNUnCollectGoodsById);
                }else{
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ConfirmCollection", HttpCommon.KLUnCollectGoodsById);
                }

            }

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
    }


    //pjw转链
    private void getGenByGoodsId(String id){
            showDialog();
            paramMap.clear();
            paramMap.put("goodsId", id);
            if (getIntent().getStringExtra("type").equals("jd")) {
                if (coupon != null) {
                    paramMap.put("couponLink", coupon.getLink());
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
                }else{
                    paramMap.put("couponLink", "");
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
                }
            }else if(getIntent().getStringExtra("type").equals("pdd")){
                if (!isShare){
                 paramMap.put("needAuth", true+"");
                }
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.PDDGenByGoodsId);
            }else if(getIntent().getStringExtra("type").equals("wph")){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.WPHGenByGoodsId);
            }else if(getIntent().getStringExtra("type").equals("sn")){
                if (coupon != null) {
                    paramMap.put("couponLink", coupon.getLink());
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }else{
                    paramMap.put("couponLink", "");
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }
            }else{
              NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.KLGenByGoodsId);
            }

    }


    private void httpPost() {
        // 商品搜索（猜你喜欢）
        if (NetStateUtils.isNetworkConnected(this)) {
            // 首页底部列表请求商品搜索（标签）
            paramMap.clear();
            paramMap.put("startindex", page + "");
            paramMap.put("pagesize", pageSize + "");
            paramMap.put("goodsId", details.getId());
            switch (cpsType.getCode()){
                case "pdd":
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.PDDRecommendByGoodsId);
                    break;
                case "jd":
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.JDRecommendByGoodsId);
                    break;
                case "wph":
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.WPHRecommendByGoodsId);
                    break;
                case "sn":
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.SNRecommendByGoodsId);
                    break;
                case "kl":
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.KLRecommendByGoodsId);
                    break;
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent=null;
        MainBottomListItem Cdetails=(MainBottomListItem)adapter.getItem(position);
        switch (cpsType.getCode()) {
            case "pdd":
                intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", Cdetails.getId()).putExtra("type", "pdd");
                break;
            case "jd":
                intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", Cdetails.getId()).putExtra("type", "jd");
                break;
            case "wph":
                intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", Cdetails.getId()).putExtra("type", "wph");
                break;
            case "sn":
                intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", Cdetails.getId()).putExtra("type", "sn");
                break;
            case "kl":
                intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", Cdetails.getId()).putExtra("type", "kl");
                break;
        }
        if (intent!=null){
         startActivity(intent);
        }
    }
}
