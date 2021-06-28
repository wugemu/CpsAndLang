package com.lxkj.dmhw.activity.self.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.http.ExecutorServiceUtil;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.AddrListActivity;
import com.lxkj.dmhw.activity.self.CartActivity;
import com.lxkj.dmhw.activity.self.GroupInfoActivity;
import com.lxkj.dmhw.activity.self.PayResultActivity;
import com.lxkj.dmhw.activity.self.PayTypeActivity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.adapter.self.order.OrderDetialAdapter;
import com.lxkj.dmhw.adapter.self.order.OrderDetialGoods;
import com.lxkj.dmhw.adapter.self.order.OrderDetialTop;
import com.lxkj.dmhw.adapter.self.order.OrderGoodsSeparate;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.bean.self.GroupOrder;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.OrderGoodBean;
import com.lxkj.dmhw.bean.self.PayResultReturnCash;
import com.lxkj.dmhw.bean.self.PayResultReturnCashDetial;
import com.lxkj.dmhw.bean.self.PhoneLTBean;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.TaxDetailBean;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.dialog.SalePayDialog;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.OrderModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.myinterface.SalePayI;
import com.lxkj.dmhw.presenter.OrderPresenter;
import com.lxkj.dmhw.service.TimeService;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.UMShareUtil;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.banner.CommonShareDialog;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.lxkj.dmhw.widget.dialog.OrderDelayTipPopupwindow;
import com.lxkj.dmhw.widget.dialog.OrderDownProductDialog;
import com.lxkj.dmhw.widget.dialog.ProductEditPopupwindow;
import com.lxkj.dmhw.widget.dialog.TaxListDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetialActivity extends BaseLangActivity<OrderPresenter> {
    public final int REQ_GONGDAN = 400;
    public static final int REQ_SELECTADDR = 300;
    public static final int REQ_REFUND = 100;
    public static final int REQ_PAYRESULT = 200;
    @BindView(R.id.lv_order_detial)
    ListView lvOrderDetial;
    @BindView(R.id.bt_order_cancel)
    TextView btOrderCancel;
    @BindView(R.id.bt_order_del)
    TextView btOrderDel;
    @BindView(R.id.bt_order_refund)
    TextView btOrderRefund;
    @BindView(R.id.bt_order_ckwl)
    TextView btOrderCkwl;
    @BindView(R.id.bt_order_qrsh)
    TextView btOrderQrsh;
    @BindView(R.id.bt_order_ddwj)
    TextView bt_order_ddwj;
    @BindView(R.id.bt_order_pay_again)
    TextView btOrderPayAgain;
    @BindView(R.id.bt_order_pay)
    TextView btOrderPay;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_gift_refund_tip)
    LinearLayout llGiftRefundTip;
    @BindView(R.id.bt_order_group_share)
    TextView bt_order_group_share;


    private View headView, footView;
    private int orderType;//type 1自己訂單 2分享訂單
    private long tradeId;//
    private OrderBean orderBean;//订单状态 0：取消 1：待付款 2 待审核 5：待发货 6：待收货 7：交易完成
    private OrderDetialAdapter adapter;
    private HeadViewHolder headViewHolder;
    private FootViewHolder footViewHolder;
    private ProductEditPopupwindow popupWindow;
    private SalePayDialog salePayDialog;//支付校验
    private TaxListDialog taxTipDialog;
    private ConfirmDialog freeTipDialog;
    private ShareBean shareBean;//订单分享数据
    private ShareBean shareBeanZL;//助力分享数据
    private List<TaxDetailBean> taxDetailBeanList;//税费说明明细
    private boolean isNeedRefresh;//是否需要刷新订单列表页
    private OrderDelayTipPopupwindow dealyPopupWindow;
    private boolean flag = true;//是否在十秒倒计时内
    private String freePopStr;//完全返点弹框文案

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detial;
    }


    @Override
    public void initView() {
        initLoading();

        initTitleBar("订单详情");
        headView = LayoutInflater.from(this).inflate(R.layout.head_order_detial, null);
        headViewHolder = new HeadViewHolder(headView);
        footView = LayoutInflater.from(this).inflate(R.layout.foot_order_detial, null);
        footViewHolder = new FootViewHolder(footView);
        LocalBroadcastManager.getInstance(this).registerReceiver(timeReceiver, new IntentFilter(Constants.TIME_TASK));

        //邀请新人 图片尺寸修改316*69
        int w = (int) (BBCUtil.getDisplayWidth(OrderDetialActivity.this) - getResources().getDimension(R.dimen.view_toview_two) * 2);
        int h = (int) (w * Constants.COUPON_ORDERGIFT_SIZE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        footViewHolder.iv_order_coupon_share.setLayoutParams(params);
    }

    @Override
    public void initPresenter() {
        presenter = new OrderPresenter(this, OrderModel.class);
    }

    @Override
    public void initData() {
        showWaitDialog();
        orderType = getIntent().getIntExtra("orderType", 1);
        tradeId = getIntent().getLongExtra("tradeId", 0);
        presenter.reqOrderDetial(tradeId, orderType);

    }

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (orderBean != null) {

                if (orderBean.getTradeReturnCash()!=null&&headViewHolder!=null&&orderBean.getTradeReturnCash().getState()!=9&&orderBean.getTradeReturnCash().getEndTime()>0){

                    long endTime = orderBean.getTradeReturnCash().getEndTime();
                    String timeStr = TimeCalculate.getTime3(MyApplication.NOW_TIME, endTime);
                    if (!BBCUtil.isEmpty(timeStr)) {//倒计时未结束
                        headViewHolder.tvTime.setVisibility(View.VISIBLE);
                        headViewHolder.tvTime.setText("仅剩:" + timeStr);
                    } else {
                        showWaitDialog();
                        presenter.reqTradeShareReturnCashDetial(orderBean.getTradeNo());
                    }

                }


                if (orderBean.isIfGroupTrade()) {
                    String groupdeadline = "";
                    if (orderBean.getTradeGroupCreate().getLessGroupEndTime() > 0) {
                        orderBean.getTradeGroupCreate().setLessGroupEndTime(orderBean.getTradeGroupCreate().getLessGroupEndTime() - 1);
                        groupdeadline = TimeCalculate.getTime3(0, orderBean.getTradeGroupCreate().getLessGroupEndTime());
                        bt_order_group_share.setText("邀请好友" + groupdeadline);
                    } else {
                        //刷新列表

                        if (presenter != null&&bt_order_group_share.getVisibility()==View.VISIBLE) {
                            presenter.reqOrderDetial(tradeId, orderType);
                        }
                    }

                } else if (orderBean.getTradeStatus() == 1) {
                    String deadline = "";
                    if (orderBean.getLessSecond() > 0) {
                        orderBean.setLessSecond(orderBean.getLessSecond() - 1);
                        deadline = TimeCalculate.getTime2(0, orderBean.getLessSecond());

                        headViewHolder.tvOrderStatusDesc.setText("订单已提交，请在00:" + deadline + "内完成支付，超时订单将自动取消");
                    } else {
                        //取消订单
                        if (presenter != null) {
                            presenter.reqOrderDetial(tradeId, orderType);
                        }
                    }
                }

                //Ver1.0.9 版本助力订单隐藏
                if (orderBean.getHelpTradeLessSecond() > 0) {
                    orderBean.setHelpTradeLessSecond(orderBean.getHelpTradeLessSecond() - 1);
                    long targetTime = orderBean.getHelpTradeLessSecond();
                    Map<String, String> time = TimeCalculate.getTimeMap(0, targetTime);
                    if (time != null && time.size() > 0) {
                        headViewHolder.tv_zltime_order.setText(time.get("hour") + ":" + time.get("min") + ":" + time.get("sec") + " 助力关闭");
                    }
                } else {
                    //助力倒计时结束
                    if (presenter != null &&!BBCUtil.isEmpty(orderBean.getHelpTradeStr())&&headViewHolder.ll_zl_order.getVisibility() == View.VISIBLE && headViewHolder.ll_zling_order.getVisibility() == View.VISIBLE) {
                        showWaitDialog();
                        presenter.reqOrderDetial(tradeId, orderType);
                    }
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        try {
            Intent i = new Intent(OrderDetialActivity.this, TimeService.class);
            startService(i);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeReceiver);
    }

    private View createGroupHeadView(String url, int number) {
        View view = LayoutInflater.from(this).inflate(R.layout.child_group_head, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_44), (int) getResources().getDimension(R.dimen.dp_44));
        view.setLayoutParams(params);
        CircleImageView head = view.findViewById(R.id.civ_head);
//        head.setBorderColor(Color.TRANSPARENT);
        TextView tv_shadow = view.findViewById(R.id.tv_shadow);
        if (number > 0) {
            tv_shadow.setVisibility(View.VISIBLE);
            tv_shadow.setText("+"+(number + 1));
        }
        TextView tv_label = view.findViewById(R.id.tv_label);
        ImageLoadUtils.doLoadCircleImageUrl(head,url);
//        GlideUtil.getInstance().displayNoBg(this, url, head);
        tv_label.setVisibility(View.GONE);
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
        p.setMargins((int) getResources().getDimension(R.dimen.design_10dp), 0, 0, 0);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        dismissWaitDialog();
        if ("reqOrderDetial".equals(arg)) {
            orderBean = presenter.model.getOrderBean();
            if (orderBean != null) {
                //测试
//                orderBean.setHelpTradeStr("助力中，差一人");
//                orderBean.setHelpTradeLessSecond(1601);
//                orderBean.setTradeStatus(7);
//                orderBean.setIfShowLogistic(true);
//                orderBean.setHelpTradeType(2);
                GroupOrder groupOrder = orderBean.getTradeGroupCreate();
                headViewHolder.llGroupInfo.setVisibility(View.GONE);
                headViewHolder.tvStockNervous.setVisibility(View.GONE);
                bt_order_group_share.setVisibility(View.GONE);//团分享默认隐藏

                if (orderBean.isIfGroupTrade()&&orderType != 2) {
                    //团订单 我的订单显示团信息 头像模块
                    headViewHolder.llGroupInfo.setVisibility(View.VISIBLE);
                    if (groupOrder.getGroupStatus() == 2) {//成功
                        headViewHolder.tvGroupStatus.setText("拼团成功");
                        if(groupOrder.getGroupType()==2&&!groupOrder.isIfCreateGroup()) {
                            //团长免费团 团员显示参团成功
                            headViewHolder.tvGroupStatus.setText("参团成功");
                        }
                    } else if (groupOrder.getGroupStatus() == 1) {//进行中
                        if (groupOrder.isStockNervous()) {
                            headViewHolder.tvStockNervous.setVisibility(View.VISIBLE);
                            headViewHolder.tvStockNervous.setText("库存告急，手快有，手慢无哦");
                        }
                        SpannableString ss = new SpannableString("还差" + groupOrder.getLessGroupUserCount() + "人拼团成功");
                        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1341")), 2, ss.length() - 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        headViewHolder.tvGroupStatus.setText(ss);
                    } else if (groupOrder.getGroupFailType() == 1) {//时间到了
                        SpannableString ss = new SpannableString("拼团失败，还差" + groupOrder.getLessGroupUserCount() + "人拼团成功");
                        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1341")), 7, ss.length() - 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        headViewHolder.tvGroupStatus.setText(ss);
                    } else if (groupOrder.getGroupFailType() == 2) {//库存没了
                        headViewHolder.tvGroupStatus.setText("拼团失败，商品已抢光");
                    } else {
                        headViewHolder.tvGroupStatus.setText("拼团失败");
                    }

                    //头像显示
//                    boolean isVisShadow=false;//是否大于2个
//                    if (groupOrder.getHeardUrls().size()>2){
//                        isVisShadow=true;
//                    }
                    headViewHolder.llGroupHead.removeAllViews();
                    for (int i = 0; i < groupOrder.getHeardUrls().size(); i++) {
                        if (i == 0) {
                            headViewHolder.llGroupHead.addView(createGroupHeadView(groupOrder.getHeardUrls().get(i), 0));
                        } else {
                            headViewHolder.llGroupHead.addView(createGroupHeadView(groupOrder.getHeardUrls().get(i),groupOrder.getUseGroupCount()-groupOrder.getHeardUrls().size()));
                        }
                    }

                    headViewHolder.llGroupInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到拼团详情
                            Intent intent = new Intent(OrderDetialActivity.this, GroupInfoActivity.class);
                            intent.putExtra("tradeNo", orderBean.getTradeNo());
                            ActivityUtil.getInstance().start(OrderDetialActivity.this, intent);
                        }
                    });

                }

                presenter.reqShareInfo(orderBean.getTradeNo(), "15");//15-订单详情页

                if (orderBean.getPostalTax() > 0) {//税费
                    footViewHolder.llTaxFee.setVisibility(View.VISIBLE);
                    footViewHolder.tvTaxFee.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getPostalTax()));
                } else {
                    footViewHolder.llTaxFee.setVisibility(View.GONE);
                }

                taxDetailBeanList = orderBean.getTaxDetails();

                if (taxDetailBeanList != null && taxDetailBeanList.size() > 0) {
                    footViewHolder.iv_tax_help.setVisibility(View.VISIBLE);
                } else {
                    footViewHolder.iv_tax_help.setVisibility(View.GONE);
                }

                headViewHolder.tvUserInfo.setText(orderBean.getSndTo() + "\u3000" + orderBean.getTel());
                headViewHolder.tvUserAddr.setText(orderBean.getProvince() + " " + orderBean.getCity() + " " + orderBean.getTown() + " " + orderBean.getAdr());
                if (!BBCUtil.isEmpty(orderBean.getCustomerRemark())) {
                    headViewHolder.tvRemark.setText(orderBean.getCustomerRemark());
                    headViewHolder.lineRemark.setVisibility(View.VISIBLE);
                    headViewHolder.llRemark.setVisibility(View.VISIBLE);
                } else {
                    headViewHolder.lineRemark.setVisibility(View.GONE);
                    headViewHolder.llRemark.setVisibility(View.GONE);
                }

                //Ver1.0.9 版本助力订单隐藏
                //自己订单 助力订单信息显示
                if (orderType == 1 && !BBCUtil.isEmpty(orderBean.getHelpTradeStr())) {
                    headViewHolder.ll_zl_order.setVisibility(View.VISIBLE);
                    headViewHolder.line_remark2.setVisibility(View.VISIBLE);

                    if (orderBean.getHelpTradeLessSecond() > 0) {
                        if (orderBean.getHelpTradeType() == 2) {
                            headViewHolder.tv_zl_order.setText("手快免单进行中，手快有，手慢无哦");
                            headViewHolder.tv_zling_sharetip.setText(orderBean.getHelpTradeStr());
                        } else {
                            headViewHolder.tv_zl_order.setText(orderBean.getHelpTradeStr());
                            headViewHolder.tv_zling_sharetip.setText("邀请好友助力");
                        }
                        headViewHolder.ll_zl_order.setBackgroundResource(R.mipmap.icon_zlbg_high);
                        headViewHolder.ll_zling_order.setVisibility(View.VISIBLE);
                        long targetTime = orderBean.getHelpTradeLessSecond();
                        Map<String, String> time = TimeCalculate.getTimeMap(0, targetTime);
                        if (time != null && time.size() > 0) {
                            headViewHolder.tv_zltime_order.setText(time.get("hour") + ":" + time.get("min") + ":" + time.get("sec") + " 助力关闭");
                        }
                    } else {
                        headViewHolder.tv_zl_order.setText(orderBean.getHelpTradeStr());
                        headViewHolder.ll_zl_order.setBackgroundResource(R.mipmap.icon_zlbg_low);
                        headViewHolder.ll_zling_order.setVisibility(View.GONE);
                    }
                } else {
                    headViewHolder.ll_zl_order.setVisibility(View.GONE);
                    headViewHolder.line_remark2.setVisibility(View.GONE);
                }

                if (orderBean.getCouponPolicyAmount() > 0) {//活动优惠
                    footViewHolder.tvActivityDiscount.setText("-¥" + BBCUtil.getDoubleFormat2(orderBean.getCouponPolicyAmount()));
                    footViewHolder.llActivityDiscount.setVisibility(View.VISIBLE);
                } else {
                    footViewHolder.llActivityDiscount.setVisibility(View.GONE);
                }

                if (orderBean.getCouponAmount() > 0) {//优惠券优惠
                    footViewHolder.tvCouponDiscount.setText("-¥" + BBCUtil.getDoubleFormat2(orderBean.getCouponAmount()));
                    footViewHolder.llCouponDiscount.setVisibility(View.VISIBLE);
                } else {
                    footViewHolder.llCouponDiscount.setVisibility(View.GONE);
                }

                if (orderBean.getPostageTotal() > 0) {//运费
                    footViewHolder.llPostFee.setVisibility(View.VISIBLE);
                    footViewHolder.tvPostFee.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getPostageTotal()));
                } else {
                    footViewHolder.llPostFee.setVisibility(View.GONE);
                }

                if (orderBean.getGiftRebateAmount() > 0) {//礼包优惠金额
                    footViewHolder.tvExchangeGiftFee.setText("-¥" + BBCUtil.getDoubleFormat2(orderBean.getGiftRebateAmount()));
                    footViewHolder.llExchangeGiftFee.setVisibility(View.VISIBLE);
                    //显示支付明细
                    footViewHolder.tvPayDetial.setVisibility(View.VISIBLE);
                } else {
                    footViewHolder.llExchangeGiftFee.setVisibility(View.GONE);
                }
                if (orderBean.getBalanceAmount() > 0) {//可用余额
                    footViewHolder.tvWithdrawBalance.setText("-¥" + BBCUtil.getDoubleFormat2(orderBean.getBalanceAmount()));
                    footViewHolder.llWithdrawBalance.setVisibility(View.VISIBLE);
                    //显示支付明细
                    footViewHolder.tvPayDetial.setVisibility(View.VISIBLE);
                } else {
                    footViewHolder.llWithdrawBalance.setVisibility(View.GONE);
                }
                footViewHolder.tvTotalFee.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getTotalAmount()));
                footViewHolder.tvMustTotalFee.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getPayAmount()));
                footViewHolder.tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getGoodsTotal()));
                //完全返点应付总额说明icon
                if(orderBean.isOnlyReturn()&&orderType==1){
                    //且是自己的订单
                    freePopStr=orderBean.getFreePop();
                    footViewHolder.iv_yfze_help.setVisibility(View.VISIBLE);
                    footViewHolder.tv_fandian_tip.setVisibility(View.VISIBLE);
                }else {
                    footViewHolder.iv_yfze_help.setVisibility(View.GONE);
                }

                //积分
                if (orderBean.getAddUserScoreNum() > 0) {
                    footViewHolder.tv_order_jifen.setVisibility(View.VISIBLE);
                    footViewHolder.tv_order_jifen.setText("素店积分：获得" + BBCUtil.getDoubleFormat(orderBean.getAddUserScoreNum()) + "积分");
                } else {
                    footViewHolder.tv_order_jifen.setVisibility(View.GONE);
                }

                footViewHolder.tvOrderNo.setText("订单编号：" + orderBean.getTradeNo());
                footViewHolder.tvOrderCreateTime.setText("下单时间：" + DateUtil.datetime(new Date(orderBean.getRegTime() * 1000), "yyyy-MM-dd HH:mm:ss"));

                if (groupOrder!=null&&!BBCUtil.isEmpty(groupOrder.getGroupSuccessTimeStr())){
                    footViewHolder.tvGroupComplateTime.setVisibility(View.VISIBLE);
                    footViewHolder.tvGroupComplateTime.setText("拼单成功：" + groupOrder.getGroupSuccessTimeStr());
                }

                if (orderType == 1) {//自己的订单才可以跳转
                    headViewHolder.llGoPost.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (orderBean.getTradeStatus() == 6 || orderBean.getTradeStatus() == 7 || orderBean.isIfShowLogistic()) {
                                //可以跳转 或后台返回可以查看物流状态
                                if(orderBean.isOnlyReturn()){
                                    //团购返点的不可以跳
                                    return;
                                }
                                goPostDetial();
                            }
                        }
                    });

                } else {
                    headViewHolder.llGoPost.setOnClickListener(null);
                }

//                orderBean.setTradeStatus(2);//测试

                //修改地址按钮
                headViewHolder.iv_order_editaddr.setVisibility(View.GONE);

                switch (orderBean.getTradeStatus()) {
                    case 0://取消
                        btOrderCancel.setVisibility(View.GONE);
                        btOrderCkwl.setVisibility(View.GONE);
                        btOrderDel.setVisibility(View.VISIBLE);
                        if (isRefundState1or2()) {
                            //不可以删除订单
                            btOrderDel.setVisibility(View.GONE);
                        }
                        btOrderPay.setVisibility(View.GONE);
                        btOrderPayAgain.setVisibility(View.VISIBLE);
                        btOrderQrsh.setVisibility(View.GONE);
                        bt_order_ddwj.setVisibility(View.GONE);
                        btOrderRefund.setVisibility(View.GONE);
                        tvMore.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("交易关闭");
//                        if (orderBean.isIfGroupTrade()) {
//                            if (orderBean.getTradeGroupCreate().getGroupFailType() == 2) {
//                                headViewHolder.tvOrderStatusDesc.setText("关闭原因：商品已抢光");
//                            } else {
//                                headViewHolder.tvOrderStatusDesc.setText("关闭原因：拼团失败，退款将在1-3个工作日内原路返回");
//                            }
//                        } else {
                        headViewHolder.tvOrderStatusDesc.setText("关闭原因：" + orderBean.getRejected());
//                        }

                        headViewHolder.ivRightArrow.setVisibility(View.GONE);

                        //Ver1.0.9 版本助力订单隐藏
                        if (orderBean.getHelpTradeType() == 1 || orderBean.getHelpTradeType() == 2 ) {
                            // 1下单返现订单 2免费领取订单 隐藏再次购买 和 删除订单
                            btOrderDel.setVisibility(View.GONE);
                            btOrderPayAgain.setVisibility(View.GONE);
                        }
                        if(orderBean.getTradeGroupCreateId()>0){
                            //团订单 隐藏再次购买
                            btOrderPayAgain.setVisibility(View.GONE);
                        }
                        break;
                    case 1://待付款
                        btOrderCancel.setVisibility(View.VISIBLE);
                        btOrderCkwl.setVisibility(View.GONE);
                        btOrderDel.setVisibility(View.GONE);
                        btOrderPay.setVisibility(View.VISIBLE);
                        btOrderPayAgain.setVisibility(View.GONE);
                        btOrderQrsh.setVisibility(View.GONE);
                        bt_order_ddwj.setVisibility(View.GONE);
                        btOrderRefund.setVisibility(View.GONE);
                        tvMore.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("等待付款");
                        headViewHolder.tvOrderStatusDesc.setText("订单已提交，请在00:00:00内完成支付，超时订单将自动取消");
                        headViewHolder.ivRightArrow.setVisibility(View.GONE);
                        break;
                    case 2://待审核
                        if (orderBean.isIfGroupTrade()) {
                            btOrderCancel.setVisibility(View.GONE);
                            if(groupOrder.getGroupType()==1&&groupOrder.getGroupStatus() == 1) {
                                //普通团待成团时显示倒计时
                                String groupdeadline = "";
                                if (groupOrder.getLessGroupEndTime() > 0) {
                                    bt_order_group_share.setVisibility(View.VISIBLE);
                                    groupdeadline = TimeCalculate.getTime3(0, groupOrder.getLessGroupEndTime());
                                }
                                bt_order_group_share.setText("邀请好友" + groupdeadline);
                            }

                        } else if (orderBean.getTradeProperty() == 1) {
                            //礼包订单 待审核 又可以取消了
                            btOrderCancel.setVisibility(View.VISIBLE);
                        } else {
                            btOrderCancel.setVisibility(View.VISIBLE);
                        }
                        //可以修改地址
                        headViewHolder.iv_order_editaddr.setVisibility(View.VISIBLE);
                        if (orderBean.isIfGroupTrade() && orderBean.getTradeGroupCreate()!=null && orderBean.getTradeGroupCreate().isEqualAddress() && !orderBean.getTradeGroupCreate().isIfCreateGroup()) {
                            headViewHolder.iv_order_editaddr.setVisibility(View.GONE);//集货参团用户不能修改地址
                        }

                        btOrderCkwl.setVisibility(View.GONE);
                        btOrderDel.setVisibility(View.GONE);
                        btOrderPay.setVisibility(View.GONE);
                        btOrderPayAgain.setVisibility(View.GONE);
                        btOrderQrsh.setVisibility(View.GONE);
                        bt_order_ddwj.setVisibility(View.GONE);
                        btOrderRefund.setVisibility(View.GONE);
                        tvMore.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("等待审核");
                        headViewHolder.tvOrderStatusDesc.setText("付款成功，宝贝正在打包中，请耐心等待");
                        headViewHolder.ivRightArrow.setVisibility(View.GONE);

                        if (orderType == 1 && orderBean.getHelpTradeType() == 2) {
                            //Ver1.1.2 版本 自己的订单 助力订单文案不同
                            // 1下单返现订单 2免费领取订单
                            if (orderBean.isHelpSuccess()) {
                                headViewHolder.tvOrderStatusDesc.setText("宝贝正在打包中，请耐心等待");
                            } else {
                                headViewHolder.tvOrderStatusDesc.setText("免单任务助力中，快去邀请好友吧");
                            }
                        }
                        if (orderBean.isIfGroupTrade()) {
                            //团订单
                            if(groupOrder.getGroupStatus()==1){
                                //拼团中
                                headViewHolder.tvOrderStatusDesc.setText("拼团中，还差 " + orderBean.getTradeGroupCreate().getLessGroupUserCount() + " 人拼团成功");
                            }else if(groupOrder.getGroupStatus()==2){
                                //拼团成功
                                headViewHolder.tvOrderStatusDesc.setText("宝贝正在打包中，请耐心等待");
                            }
                        }
                        break;
                    case 5://待发货
                        //可以修改地址
                        headViewHolder.iv_order_editaddr.setVisibility(View.VISIBLE);

                        btOrderCancel.setVisibility(View.GONE);
                        if (orderBean.isIfShowLogistic()&&!orderBean.isOnlyReturn()) {
                            //是否显示查看物流按钮 显示物流按钮 且不是团购返点订单
                            btOrderCkwl.setVisibility(View.VISIBLE);
                            //顶部显示箭头
                            headViewHolder.ivRightArrow.setVisibility(View.VISIBLE);
                            //显示物流单号和物流名称
                            if (!BBCUtil.isEmpty(orderBean.getLogisticsName())) {
                                footViewHolder.tvLogisticCompany.setVisibility(View.VISIBLE);
                                footViewHolder.tvLogisticCompany.setText("物流公司：" + orderBean.getLogisticsName());
                            }
                            if (!BBCUtil.isEmpty(orderBean.getPostId())) {
                                footViewHolder.tvLogisticNo.setVisibility(View.VISIBLE);
                                footViewHolder.tvLogisticNo.setText("物流单号：" + orderBean.getPostId());
                                footViewHolder.btnCopyLogisticNo.setVisibility(View.VISIBLE);
                            }
                        } else {
                            btOrderCkwl.setVisibility(View.GONE);
                            headViewHolder.ivRightArrow.setVisibility(View.GONE);
                        }
                        btOrderDel.setVisibility(View.GONE);
                        btOrderPay.setVisibility(View.GONE);
                        btOrderPayAgain.setVisibility(View.GONE);
                        btOrderQrsh.setVisibility(View.GONE);
                        bt_order_ddwj.setVisibility(View.GONE);
                        btOrderRefund.setVisibility(View.VISIBLE);
                        tvMore.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("等待发货");
                        headViewHolder.tvOrderStatusDesc.setText("付款成功，宝贝正在打包中，请耐心等待");
                        //Ver1.1.2 自己的订单 版本助力订单文案不同
                        if (orderType == 1 && orderBean.getHelpTradeType() == 2) {
                            // 1下单返现订单 2免费领取订单
                            headViewHolder.tvOrderStatusDesc.setText("宝贝正在打包中，请耐心等待");
                        }
                        break;
                    case 6://待收货
                        btOrderCancel.setVisibility(View.GONE);
                        if(orderBean.isOnlyReturn()) {
                            //团购返点订单隐藏查看物流
                            btOrderCkwl.setVisibility(View.GONE);
                            headViewHolder.ivRightArrow.setVisibility(View.GONE);
                        }else {
                            btOrderCkwl.setVisibility(View.VISIBLE);
                            headViewHolder.ivRightArrow.setVisibility(View.VISIBLE);
                        }
                        btOrderDel.setVisibility(View.GONE);
                        btOrderPay.setVisibility(View.GONE);
                        btOrderPayAgain.setVisibility(View.GONE);

                        //V1.1.6 申请售后按钮上移商品后面
                        //不显示申请退款
                        btOrderRefund.setVisibility(View.GONE);
//                        if (!isRefundState1and2()) {
//                            //不显示申请退款
//                            btOrderRefund.setVisibility(View.GONE);
//                        } else {
//                            btOrderRefund.setVisibility(View.VISIBLE);
//                        }

                        tvMore.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("等待收货");
                        headViewHolder.tvOrderStatusDesc.setText("宝贝正在向你飞来，请准备好接驾～");

                        if (!BBCUtil.isEmpty(orderBean.getLogisticsName())) {
                            footViewHolder.tvLogisticCompany.setVisibility(View.VISIBLE);
                            footViewHolder.tvLogisticCompany.setText("物流公司：" + orderBean.getLogisticsName());
                        }
                        if (!BBCUtil.isEmpty(orderBean.getPostId())) {
                            footViewHolder.tvLogisticNo.setVisibility(View.VISIBLE);
                            footViewHolder.tvLogisticNo.setText("物流单号：" + orderBean.getPostId());
                            footViewHolder.btnCopyLogisticNo.setVisibility(View.VISIBLE);
                        }

                        if (orderBean.getTradeProperty() == 1) {
                            btOrderQrsh.setVisibility(View.GONE);
                            if (orderBean.isIfOverOder()) {
                                //礼包订单 显示完结订单
                                //未完结
                                bt_order_ddwj.setVisibility(View.VISIBLE);
                            } else {
                                //已未完结
                                bt_order_ddwj.setVisibility(View.GONE);
                            }
                        } else {
                            //普通订单 显示确认收货
                            bt_order_ddwj.setVisibility(View.GONE);
                            btOrderQrsh.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 7://交易完成
                        btOrderCancel.setVisibility(View.GONE);
                        if(orderBean.isOnlyReturn()) {
                            //团购返点订单隐藏查看物流
                            btOrderCkwl.setVisibility(View.GONE);
                            headViewHolder.ivRightArrow.setVisibility(View.GONE);
                        }else {
                            btOrderCkwl.setVisibility(View.VISIBLE);
                            headViewHolder.ivRightArrow.setVisibility(View.VISIBLE);
                        }
                        btOrderRefund.setVisibility(View.GONE);
                        tvMore.setVisibility(View.GONE);
                        if (isRefundState1or2()) {
                            //不可以删除订单
                            btOrderDel.setVisibility(View.GONE);

                            //V1.1.6 申请售后按钮上移商品后面
//                            if (!isRefundState1and2()) {
//                                //不显示申请售后按钮
//                                btOrderRefund.setVisibility(View.GONE);
//                            } else {
//                                btOrderRefund.setVisibility(View.VISIBLE);
//                            }

                        } else {
                            btOrderDel.setVisibility(View.VISIBLE);
                            if (orderBean.getTradeProperty() == 1) {
                                //礼包订单 完成订单 不可删除
                                btOrderDel.setVisibility(View.GONE);
                            }
                            //V1.1.6 申请售后按钮上移商品后面
//                            if (!isRefundState1and2()) {
//                                //不显示更多按钮
//                                tvMore.setVisibility(View.GONE);
//                            } else {
//                                if(orderBean.getIfUserRealVip()==1){
//                                    //二次购买礼包的普通订单 没有再次购买按钮 显示申请售后按钮
//                                    btOrderRefund.setVisibility(View.VISIBLE);
//                                }else {
//                                    tvMore.setVisibility(View.VISIBLE);
//                                }
//                            }

                        }

                        btOrderPay.setVisibility(View.GONE);
                        btOrderPayAgain.setVisibility(View.VISIBLE);
                        btOrderQrsh.setVisibility(View.GONE);
                        headViewHolder.tvOrderStatus.setText("交易成功");
                        if(orderBean.isOnlyReturn()) {
                            //团长免费团 团长 且 选择的返点 文案修改 待确认如何判断选择返点的订单
                            //groupOrder.getGroupType()==2&&groupOrder.isIfCreateGroup()
                            //完全返点的订单
                            //确定后匿名订单也加下
                            headViewHolder.tvOrderStatusDesc.setText("拼团成功，收益已发放至您的账户");
                        }else {
                            headViewHolder.tvOrderStatusDesc.setText("宝贝已签收");
                        }

                        if (!BBCUtil.isEmpty(orderBean.getLogisticsName())) {
                            footViewHolder.tvLogisticCompany.setVisibility(View.VISIBLE);
                            footViewHolder.tvLogisticCompany.setText("物流公司：" + orderBean.getLogisticsName());
                        }
                        if (!BBCUtil.isEmpty(orderBean.getPostId())) {
                            footViewHolder.tvLogisticNo.setVisibility(View.VISIBLE);
                            footViewHolder.tvLogisticNo.setText("物流单号：" + orderBean.getPostId());
                            footViewHolder.btnCopyLogisticNo.setVisibility(View.VISIBLE);
                        }

                        if (orderBean.getTradeProperty() == 1) {
                            //礼包订单 完成状态 显示完结订单
                            if (orderBean.isIfOverOder() && !orderBean.isIfServenAfter()) {
                                //未完结 不超过7天
                                bt_order_ddwj.setVisibility(View.VISIBLE);
                            } else {
                                //已完结或超过7天
                                bt_order_ddwj.setVisibility(View.GONE);
                            }
                        } else {
                            bt_order_ddwj.setVisibility(View.GONE);
                        }

                        //Ver1.0.9 版本助力订单隐藏
                        if (orderBean.getHelpTradeType() == 1 || orderBean.getHelpTradeType() == 2
                                ||orderBean.getIfUserRealVip() == 1) {
                            // 1下单返现订单 2免费领取订单 隐藏再次购买 和 删除订单
                            //二次购买礼包订单的普通订单
                            btOrderDel.setVisibility(View.GONE);
                            btOrderPayAgain.setVisibility(View.GONE);

                            //V1.1.6 申请售后按钮上移商品后面
//                            if(tvMore.getVisibility()==View.VISIBLE){
//                                //没有再次购买按钮和删除按钮  隐藏更多按钮显示申请售后按钮
//                                tvMore.setVisibility(View.GONE);
//                                btOrderRefund.setVisibility(View.VISIBLE);
//                            }

                        }
                        if(orderBean.getTradeGroupCreateId()>0){
                            //团购订单 不能再次购买
                            btOrderPayAgain.setVisibility(View.GONE);
                        }
                        break;
                }
                footViewHolder.btnCopyLogisticNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BBCUtil.copy(orderBean.getPostId(), OrderDetialActivity.this);
                        ToastUtil.show(OrderDetialActivity.this, "已经复制到剪切板");
                    }
                });
                footViewHolder.btnCopyOrderNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BBCUtil.copy(orderBean.getTradeNo(), OrderDetialActivity.this);
                        ToastUtil.show(OrderDetialActivity.this, "已经复制到剪切板");
                    }
                });

                if (adapter == null) {
                    adapter = new OrderDetialAdapter(this, parseContentList(orderBean.getGoodsList()));
                    lvOrderDetial.addHeaderView(headView);
                    lvOrderDetial.addFooterView(footView);
                    lvOrderDetial.setAdapter(adapter);
                } else {
                    adapter.upateList(parseContentList(orderBean.getGoodsList()));
                    adapter.notifyDataSetChanged();
                }
                if ((Double)orderBean.getIfVipOrder()>0) {//玩主
                    footViewHolder.tvWzProfit.setVisibility(View.VISIBLE);
                    footViewHolder.tvWzProfit.setText("(已省" + BBCUtil.getDoubleFormat(orderBean.getVipDiscountBaseAmount()) + "元)");
                    if(orderBean.getVipDiscountBaseAmount()<=0){
                        //是团订单 已省金额为0元时隐藏
                        footViewHolder.tvWzProfit.setVisibility(View.GONE);
                    }
                } else {
                    footViewHolder.tvWzProfit.setVisibility(View.GONE);
                }

                //修改地址状态显示
                if (!BBCUtil.isEmpty(orderBean.getUpdateAdrStr())) {
                    headViewHolder.iv_order_editaddr.setVisibility(View.GONE);
                    headViewHolder.tv_editaddr_state.setVisibility(View.VISIBLE);
                    headViewHolder.tv_editaddr_state.setText(orderBean.getUpdateAdrStr());
                } else {
                    headViewHolder.tv_editaddr_state.setVisibility(View.GONE);
                }

                //入网信息
                PhoneLTBean phoneLTBean = orderBean.getTradeMobileNet();
                if (phoneLTBean != null && !BBCUtil.isEmpty(phoneLTBean.getMobile())) {
                    headViewHolder.ll_selectphone.setVisibility(View.VISIBLE);
                    headViewHolder.tv_choice_phone.setText("已选新手机号码：" + phoneLTBean.getMobile());
                } else {
                    headViewHolder.ll_selectphone.setVisibility(View.GONE);
                }

                if (orderType == 2) {
                    //分享订单 顶部物流不可以点击  底部按钮不显示  修改地址按钮和状态全部隐藏 入网信息隐藏
                    headViewHolder.iv_order_editaddr.setVisibility(View.GONE);
                    headViewHolder.tv_editaddr_state.setVisibility(View.GONE);
                    headViewHolder.ivRightArrow.setVisibility(View.GONE);
                    llRight.setVisibility(View.GONE);
                    tvMore.setVisibility(View.GONE);
                    headViewHolder.ll_selectphone.setVisibility(View.GONE);
                }

                //对礼包订单的处理
                if (orderBean.getTradeProperty() == 1 && orderBean.getTradeStatus() >= 2) {
                    btOrderRefund.setVisibility(View.GONE);
                    llGiftRefundTip.setVisibility(View.VISIBLE);
                    tvMore.setVisibility(View.GONE);
                } else {
                    llGiftRefundTip.setVisibility(View.GONE);
                }
                if (orderBean.getTradeProperty() == 1) {
                    //对礼包订单的处理
                    btOrderPayAgain.setVisibility(View.GONE);
                } else {
                    if (orderBean.getIfUserRealVip() == 1) {
                        //二次购买礼包的普通订单
                        btOrderPayAgain.setVisibility(View.GONE);
                    }
                }

                //超时发货补偿信息判断
                if (orderType == 1 && !BBCUtil.isEmpty(orderBean.getDeliveryTimeOutCompensateInfo()) && dealyPopupWindow == null) {
                    //自己订单才显示慢
                    lvOrderDetial.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            Rect globalRect = new Rect();
                            boolean visibile = footViewHolder.tv_yuan.getGlobalVisibleRect(globalRect);
                            if (flag && dealyPopupWindow != null) {
                                if (visibile) {
                                    //慢 标签显示时
                                    if (!dealyPopupWindow.isShowing()) {
                                        dealyPopupWindow.showEditPopup(footViewHolder.tv_yuan);
                                    } else {
                                        dealyPopupWindow.updateView(footViewHolder.tv_yuan);
                                    }
                                } else {
                                    //慢 标签隐藏时
                                    dealyPopupWindow.dismiss();
                                }
                            }
                        }
                    });
                    footViewHolder.tv_yuan.setVisibility(View.VISIBLE);
                    dealyPopupWindow = new OrderDelayTipPopupwindow(this, orderBean.getDeliveryTimeOutCompensateInfo());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Rect globalRect = new Rect();
                            boolean visibile = footViewHolder.tv_yuan.getGlobalVisibleRect(globalRect);
                            ExecutorServiceUtil.getInstence().execute(timeService);
                            if (visibile) {
                                dealyPopupWindow.showEditPopup(footViewHolder.tv_yuan);
                            }
                        }
                    }, 500);
                } else {
                    footViewHolder.tv_yuan.setVisibility(View.GONE);
                }
                if(groupOrder!=null&&groupOrder.getGroupType()==2&&groupOrder.isIfCreateGroup()&&groupOrder.getLessGroupEndTime() > 0 && groupOrder.getGroupStatus() != 3){
                    // 团长免费团 且是团长  倒计时没有结束 且团状态不是失败的状态 显示邀请好友
                    bt_order_group_share.setVisibility(View.VISIBLE);
                    String groupdeadline = "";
                    groupdeadline = TimeCalculate.getTime3(0, groupOrder.getLessGroupEndTime());
                    bt_order_group_share.setText("邀请好友" + groupdeadline);
                }

//                if (orderBean.getTradeReturnCash()!=null){
//                    headViewHolder.ll_return_money.setVisibility(View.VISIBLE);
//                    setReturnDetial(orderBean.getTradeReturnCash());
//                }else{
//                    headViewHolder.ll_return_money.setVisibility(View.GONE);
//                }

            }
        } else if ("reqCancelOrder".equals(arg)) {
            //取消订单 刷新玩主玩客首页 余额相关
            isNeedRefresh = true;
//            SDJumpUtil.refreshHomeDataIndex(HomeActivity.GO_MAINPLAY);
            presenter.reqOrderDetial(tradeId, orderType);
            presenter.getUserAccountInfo();//更新用户身份
        } else if ("reqConfirmOrder".equals(arg)) {
            isNeedRefresh = true;
            presenter.reqOrderDetial(tradeId, orderType);
        } else if ("reqDelOrder".equals(arg)) {
            isNeedRefresh = true;
            ActivityUtil.getInstance().exitResult(this, getIntent(), RESULT_OK);
        } else if ("checkOrderBuy".equals(arg)) {
            //请求商品是否可以加入购物车
            againAddCar(presenter.model.getGoodBuyStateBeanList());
        } else if ("reqOrderToPay".equals(arg)) {
            //订单去支付
            if (presenter.model == null) {
                return;
            }
            //余额支付
            if (presenter.model.getIfCheckSms() == 1) {
                //需要短信验证
                if (salePayDialog == null) {
                    salePayDialog = new SalePayDialog(this, presenter.model.getMobile(), new SalePayI() {
                        @Override
                        public void sendCode() {
                            presenter.sendSafeCode(presenter.model.getTradeNo());
                        }

                        @Override
                        public void commit(String captcha) {
                            presenter.checkSmsForCachOrder(captcha, presenter.model.getTradeNo());
                        }

                        @Override
                        public void clickClose() {

                        }
                    });
                } else {
                    salePayDialog.showDialog();
                }
            } else {
                //不需要短信验证
                if (presenter.model.getAmount() > 0) {
                    //需要第三方支付
                    Intent intent = new Intent(this, PayTypeActivity.class);
                    intent.putExtra("realPayAmount", BBCUtil.getDoubleFormat2(presenter.model.getAmount()));
                    intent.putExtra("tradeNo", presenter.model.getTradeNo());
                    intent.putExtra("payCountDown", presenter.model.getPayCountDown());
                    intent.putExtra("isPayGift", presenter.model.isPayGift());
                    intent.putExtra("isGroupOrder", orderBean.getTradeGroupCreateId() > 0);
                    ActivityUtil.getInstance().startResult(this, intent, REQ_PAYRESULT);
                }
            }
        } else if ("sendSafeCode".equals(arg)) {
            //支付发送短信成功
            if (salePayDialog != null) {
                salePayDialog.startDownTime();
            }
        } else if ("checkSmsForCachOrder".equals(arg)) {
            //短信支付校验成功
            if (salePayDialog != null) {
                salePayDialog.cancelDialog();
            }

//            if (presenter.model.isPayGift()) {
//                //礼包支付结果
//                MyApplication.PAY_RESULT_TRADE_NO = orderBean.getTradeNo();
//                Intent intent = new Intent(this, PayResultGiftActivity.class);
//                ActivityUtil.getInstance().startResult(this, intent, REQ_PAYRESULT);
//            }else if(presenter.model.isPayGroup()){
//                //团订单支付结果
//                Intent intent = new Intent(this, GroupInfoActivity.class);
//                intent.putExtra("tradeNo",orderBean.getTradeNo());
//                intent.putExtra("isPayResult",true);
//                ActivityUtil.getInstance().startResult(this, intent, OrderListActivity.REQ_PAYRESULT);
//            } else {
//                //普通支付结果
//                SuDianApp.PAY_RESULT_TRADE_NO = orderBean.getTradeNo();
//                Intent intent = new Intent(this, PayResultActivity.class);
//                ActivityUtil.getInstance().startResult(this, intent, REQ_PAYRESULT);
//            }
            //普通支付结果
            MyApplication.PAY_RESULT_TRADE_NO = orderBean.getTradeNo();
            Intent intent = new Intent(this, PayResultActivity.class);
            ActivityUtil.getInstance().startResult(this, intent, REQ_PAYRESULT);
        } else if ("reqChangeCart".equals(arg)) {
            //再次购买
            ToastUtil.show(this, "加入购物车成功");
            Intent intent = new Intent(this, CartActivity.class);
            ActivityUtil.getInstance().start(this, intent);
        } else if ("confirmTradeClose".equals(arg)) {
            //订单完结
            isNeedRefresh = true;
            presenter.reqOrderDetial(tradeId, orderType);
        } else if ("reqShareInfo".equals(arg)) {
            //获取邀请好友分享数据
            shareBean = presenter.model.getShareBean();
            if (shareBean != null) {
                if (shareBean.isState()) {
//                    ImageLoadUtils.doLoadImageUrl(footViewHolder.iv_order_coupon_share, shareBean.getShowImgUrl());
                    footViewHolder.ll_order_coupon_share.setVisibility(View.GONE);
                    footViewHolder.iv_order_coupon_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击邀请用户
                            new CommonShareDialog(OrderDetialActivity.this, shareBean);
                        }
                    });
                }
            }
        } else if ("reqShareInfoZL".equals(arg)) {
            //助力分享数据
            shareBeanZL = presenter.model.getShareBean();
            if (shareBeanZL != null) {
                UMShareUtil.getInstance().umengShareMin(OrderDetialActivity.this, shareBeanZL, SHARE_MEDIA.WEIXIN, orderBean.getHelpTradeType());
            }
        } else if ("reqShareGroupInfo".equals(arg)) {
            ShareBean shareBean = presenter.model.getShareBean();
            if (shareBean != null) {
                UMShareUtil.getInstance().umengShareMin(this, shareBean, SHARE_MEDIA.WEIXIN, 0);
            }
        }else if ("reqTradeShareReturnCashDetial".equals(arg)) {
            PayResultReturnCash returnCash = presenter.model.getPayResultReturnCash();
            if (returnCash != null) {
                setReturnDetial(returnCash.getTradeReturnCashDetail());
            }
        }else if ("reqReturnShareInfo".equals(arg)){
            new CommonShareDialog(this,presenter.model.getShareBean());
        }
    }

    //去物流详情
    private void goPostDetial() {
        if (orderBean == null) {
            return;
        }
        Intent intent = new Intent(this, LogisticsDetailActivity.class);
        intent.putExtra("postid", orderBean.getPostId());
        intent.putExtra("tradeId", orderBean.getTradeId());
        ActivityUtil.getInstance().start(this, intent);
    }


    private Runnable timeService = new Runnable() {
        @Override
        public void run() {
            for (int i = 10; i >= 0; i--) {
                try {
                    Message msg = new Message();
                    msg.what = 101;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //去退款页面
    private void goRefund() {
        //需判断订单状态
//        orderBean.setIfServenAfter(false);//测试
        if (orderBean != null) {
            GroupOrder groupOrder = orderBean.getTradeGroupCreate();
            if (orderBean.isIfServenAfter()) {
                //超过7天不可以申请售后
                ConfirmDialog confirmDialog = new ConfirmDialog(OrderDetialActivity.this, "此订单已超过售后期限", "", "知道了", null);
                confirmDialog.hiddenOkBtn();
                return;
            }
            final ArrayList<OrderGoodBean> orderGoodBeanList = getCanRefundList();
            if (orderGoodBeanList.size() == 0) {
                //订单中不存在未申请售后的商品
                ConfirmDialog confirmDialog = new ConfirmDialog(OrderDetialActivity.this, "订单中不存在未申请售后的商品", "", "知道了", null);
                confirmDialog.hiddenOkBtn();
                return;
            }

            if(!BBCUtil.isEmpty(orderBean.getCanNotAfterSalesTips())){
                ConfirmDialog confirmDialog = new ConfirmDialog(OrderDetialActivity.this, orderBean.getCanNotAfterSalesTips(), "", "知道了", null);
                confirmDialog.hiddenOkBtn();
                return;
            }

            String tipStr="";
            if (!BBCUtil.isEmpty(orderBean.getRefundCopywriting())){
                tipStr=orderBean.getRefundCopywriting();
            }else if(groupOrder!=null&&!BBCUtil.isEmpty(groupOrder.getFreeGroupRefundStr())){
                //团购订单申请售后 提示文案
                tipStr=groupOrder.getFreeGroupRefundStr();
            }
            if (!BBCUtil.isEmpty(tipStr)) {
                ConfirmDialog confirmDialog = new ConfirmDialog(OrderDetialActivity.this, tipStr, new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        goRefund2(orderGoodBeanList);
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                if (tipStr.length() > 15) {
                    //礼包订单
                    confirmDialog.textLeft();
                }
            } else {
                goRefund2(orderGoodBeanList);
            }

        }

    }

    private void goRefund2(ArrayList<OrderGoodBean> orderGoodBeanList) {
        if (orderBean == null) {
            return;
        }
        if (orderBean.getTradeStatus() == 5) {
            //5待发货
            //V1.1.6
            Intent intent = new Intent(OrderDetialActivity.this, ApplyRefundActivity2.class);
            intent.putExtra("tradeStatus", orderBean.getTradeStatus());
//            intent.putExtra("refundType", ApplyRefundActivity.TYPE_ONLYMONEY);//仅退款
            intent.putExtra("tradeId", orderBean.getTradeId());
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderGoodBeanList", orderGoodBeanList);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().startResult(OrderDetialActivity.this, intent, REQ_REFUND);
        }
    }

    private List<OrderContent> parseContentList(List<OrderGoodBean> goodsList) {
        List<OrderContent> orderContents = new ArrayList<>();
        OrderDetialTop orderItemTop = new OrderDetialTop(orderBean);
        orderContents.add(orderItemTop);
        for (OrderGoodBean goods : goodsList) {
            OrderDetialGoods detialGoods = new OrderDetialGoods();
            detialGoods.setOrderType(orderType);
            detialGoods.setOrderBean(orderBean);
            detialGoods.setGoodBean(goods);
            orderContents.add(detialGoods);
            OrderGoodsSeparate separate = new OrderGoodsSeparate();
            orderContents.add(separate);
        }
        orderContents.remove(orderContents.size() - 1);
        return orderContents;
    }


    @OnClick({R.id.bt_order_group_share, R.id.ll_order_service, R.id.bt_order_cancel, R.id.bt_order_del, R.id.bt_order_refund, R.id.bt_order_ckwl, R.id.bt_order_qrsh, R.id.bt_order_pay_again, R.id.bt_order_pay, R.id.tv_more, R.id.bt_order_ddwj})
    public void onViewClicked(View view) {
        if (orderBean == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_order_group_share://团购分享
                showWaitDialog();
                presenter.reqShareInfo(orderBean.getTradeGroupCreate().getId(), "21");

                break;
            case R.id.ll_order_service:
                //联系客服
//                SDJumpUtil.goZXKF(OrderDetialActivity.this);
                break;
            case R.id.bt_order_cancel:
                String tipStr = "确定要取消此订单吗？";
                if (orderBean.getTradeStatus() == 2 && !BBCUtil.isEmpty(orderBean.getCancelCopywriting())) {
                    //待审核状态
                    tipStr = orderBean.getCancelCopywriting();
                }
                ConfirmDialog confirmDialog = new ConfirmDialog(this, tipStr, new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        showWaitDialog();
                        presenter.reqCancelOrder(tradeId + "");
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                if (tipStr.length() > 15) {
                    //礼包订单
                    confirmDialog.textLeft();
                }
                break;
            case R.id.bt_order_del:
                new ConfirmDialog(this, "确定要删除此订单吗？", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        showWaitDialog();
                        presenter.reqDelOrder(tradeId + "");
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                break;
            case R.id.bt_order_refund:
                goRefund();
                break;
            case R.id.bt_order_ckwl:
                goPostDetial();
                break;
            case R.id.bt_order_qrsh:
                //确认收货
                new ConfirmDialog(this, "确定要收货吗？", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        showWaitDialog();
                        presenter.reqConfirmOrder(tradeId + "");
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                break;
            case R.id.bt_order_ddwj:
                //礼包订单 完结订单
                ConfirmDialog dialog = new ConfirmDialog(this, "确认完结订单后，即表示您默认该商品没有质量问题，自愿放弃退换货权利，订单达成最终交易。", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        //完结接口
                        showWaitDialog();
                        presenter.confirmTradeClose(tradeId + "");
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                dialog.textLeft();
                break;
            case R.id.bt_order_pay_again:
                List<GoodBuyStateBean> goodBuyStateBeanList=new ArrayList<>();
                if(orderBean.getGoodsList()!=null){
                    for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
                        OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
                        GoodBuyStateBean goodBuyStateBean=new GoodBuyStateBean();
                        goodBuyStateBean.setState(true);
                        goodBuyStateBean.setNum(orderGoodBean.getSellCount());
                        goodBuyStateBean.setSkuId(orderGoodBean.getSkuId());
                        goodBuyStateBeanList.add(goodBuyStateBean);
                    }
                }
                presenter.checkOrderBuy(goodBuyStateBeanList);
                break;
            case R.id.bt_order_pay:
                //付款
                if (orderBean.getTradeProperty() == 1) {
                    //礼包订单
                    presenter.model.setPayGift(true);
                } else {
                    presenter.model.setPayGift(false);
                }
                if (orderBean.getTradeGroupCreateId() > 0) {
                    //团购订单
                    presenter.model.setPayGroup(true);
                } else {
                    presenter.model.setPayGroup(false);
                }
                presenter.reqOrderToPay(orderBean.getTradeNo());
                break;
            case R.id.tv_more:
                if (popupWindow == null) {
                    popupWindow = new ProductEditPopupwindow(this, handler);
                    popupWindow.showEditPopup(tvMore);
                } else {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showEditPopup(tvMore);
                    }
                }
                break;
        }
    }

    //再次购买
    public void againAddCar(List<GoodBuyStateBean> goodBuyStateBeanList) {
        if (goodBuyStateBeanList == null) {
            goodBuyStateBeanList = new ArrayList<GoodBuyStateBean>();
        }
        List<GoodBuyStateBean> shixiaoList = new ArrayList<GoodBuyStateBean>();
        final List<TradeSkuVO> tradeSkuVOList = new ArrayList<TradeSkuVO>();
        if (goodBuyStateBeanList != null) {
            for (GoodBuyStateBean stateBean : goodBuyStateBeanList
            ) {
                if (!stateBean.isState()) {
                    //已失效
                    shixiaoList.add(stateBean);
                    continue;
                }
                TradeSkuVO tradeSkuVO = new TradeSkuVO();
                tradeSkuVO.setSkuId(stateBean.getSkuId());
                tradeSkuVO.setNum(stateBean.getNum());//后台未返回商品数量 默认1
                tradeSkuVOList.add(tradeSkuVO);
            }
        }

        if (tradeSkuVOList.size() == 0) {
            //订单中的商品都失效了，再看看 其他商品吧～
            new ConfirmDialog(OrderDetialActivity.this, "订单中的商品都失效了，再看看 其他商品吧～", new ConfirmOKI() {
                @Override
                public void executeOk() {

                }

                @Override
                public void executeCancel() {

                }
            });
            return;
        }
        if (shixiaoList.size() > 0) {
            //部分商品失效
            new OrderDownProductDialog(OrderDetialActivity.this, shixiaoList, new ConfirmOKI() {
                @Override
                public void executeOk() {
                    presenter.reqChangeCart(tradeSkuVOList);
                }

                @Override
                public void executeCancel() {

                }
            });
            return;
        }

        presenter.reqChangeCart(tradeSkuVOList);

    }


    class HeadViewHolder {
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_order_status_desc)
        TextView tvOrderStatusDesc;
        @BindView(R.id.tv_user_info)
        TextView tvUserInfo;
        @BindView(R.id.tv_user_addr)
        TextView tvUserAddr;
        @BindView(R.id.tv_editaddr_state)
        TextView tv_editaddr_state;
        @BindView(R.id.iv_order_editaddr)
        ImageView iv_order_editaddr;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.iv_right_arrow)
        ImageView ivRightArrow;
        @BindView(R.id.ll_go_post)
        LinearLayout llGoPost;
        @BindView(R.id.ll_remark)
        LinearLayout llRemark;
        @BindView(R.id.line_remark)
        View lineRemark;
        @BindView(R.id.line_remark2)
        View line_remark2;
        @BindView(R.id.ll_selectphone)
        LinearLayout ll_selectphone;
        @BindView(R.id.tv_choice_phone)
        TextView tv_choice_phone;

        @BindView(R.id.ll_zl_order)
        LinearLayout ll_zl_order;
        @BindView(R.id.tv_zl_order)
        TextView tv_zl_order;
        @BindView(R.id.ll_zling_order)
        LinearLayout ll_zling_order;
        @BindView(R.id.tv_zling_sharetip)
        TextView tv_zling_sharetip;
        @BindView(R.id.tv_zltime_order)
        TextView tv_zltime_order;

        @BindView(R.id.tv_group_status)
        TextView tvGroupStatus;
        @BindView(R.id.tv_stock_nervous)
        TextView tvStockNervous;
        @BindView(R.id.ll_group_head)
        LinearLayout llGroupHead;
        @BindView(R.id.ll_group_info)
        LinearLayout llGroupInfo;

        @BindView(R.id.ll_return_money)
        LinearLayout ll_return_money;
        @BindView(R.id.tv_trade_info)
        TextView tvTradeInfo;
        @BindView(R.id.tv_return_money)
        TextView tvReturnMoney;
        @BindView(R.id.tv_share_text)
        TextView tvShareText;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_has_return)
        LinearLayout llHasReturn;
        @BindView(R.id.tv_no_return_cash)
        TextView tvNoReturnCash;
        @BindView(R.id.btn_return_cash_share)
        TextView btnReturnCashShare;
        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.btn_return_cash_share)
        public void onViewClicked() {
            showWaitDialog();
            presenter.reqShareInfo(orderBean.getTradeNo(),"28");
        }

        @OnClick(R.id.tv_editaddr_state)
        public void clickEditAddrState() {
            //跳转至修改地址工单详情
            if (orderBean == null) {
                return;
            }
            if (BBCUtil.isEmpty(orderBean.getProblemId())) {
                return;
            }
            Intent intent = new Intent(OrderDetialActivity.this, KFServiceDetialActivity.class);
            intent.putExtra("id", orderBean.getProblemId());
            ActivityUtil.getInstance().startResult(OrderDetialActivity.this, intent, REQ_GONGDAN);
        }

        @OnClick(R.id.iv_order_editaddr)
        public void clickEditAddr() {
            //修改地址
            if (orderBean == null) {
                return;
            }
            if(orderBean.isIfGroupTrade()&& orderBean.getTradeGroupCreate()!=null && orderBean.getTradeGroupCreate().isEqualAddress()){
                ToastUtil.show(OrderDetialActivity.this,"此拼团暂不支持修改地址");
                return;
            }
            Intent intent = new Intent(OrderDetialActivity.this, AddrListActivity.class);
            intent.putExtra("selectOrderAddr", true);
            intent.putExtra("tradeId", tradeId + "");
            ActivityUtil.getInstance().startResult(OrderDetialActivity.this, intent, REQ_SELECTADDR);
        }

        @OnClick(R.id.ll_select_phone)
        public void clickSelectPhone() {
            //选手机号码详情
//            if (orderBean == null) {
//                return;
//            }
//            PhoneLTBean phoneLTBean = orderBean.getTradeMobileNet();
//            if (phoneLTBean == null) {
//                return;
//            }
//            Intent intent = new Intent(OrderDetialActivity.this, RwzlActivity.class)
//            intent.putExtra("id", phoneLTBean.getId());
//            ActivityUtil.getInstance().start(OrderDetialActivity.this, intent);
        }

        @OnClick(R.id.ll_zling_order)
        public void clickZLShare() {
            //助力分享
            if (orderBean == null) {
                return;
            }

            if (shareBeanZL != null) {
                UMShareUtil.getInstance().umengShareMin(OrderDetialActivity.this, shareBeanZL, SHARE_MEDIA.WEIXIN, orderBean.getHelpTradeType());
            } else {
                showWaitDialog();
                presenter.reqShareInfo(orderBean.getTradeNo(), "20");//20-助力订单分享
            }
        }
    }

    class FootViewHolder {
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_wz_profit)
        TextView tvWzProfit;
        @BindView(R.id.ll_product_price)
        LinearLayout llProductPrice;
        @BindView(R.id.tv_activity_discount)
        TextView tvActivityDiscount;
        @BindView(R.id.ll_activity_discount)
        LinearLayout llActivityDiscount;
        @BindView(R.id.tv_coupon_discount)
        TextView tvCouponDiscount;
        @BindView(R.id.ll_coupon_discount)
        LinearLayout llCouponDiscount;
        @BindView(R.id.tv_post_fee)
        TextView tvPostFee;
        @BindView(R.id.ll_post_fee)
        LinearLayout llPostFee;
        @BindView(R.id.tv_tax_fee)
        TextView tvTaxFee;
        @BindView(R.id.ll_tax_fee)
        RelativeLayout llTaxFee;
        @BindView(R.id.iv_tax_help)
        ImageView iv_tax_help;
        @BindView(R.id.tv_total_fee)
        TextView tvTotalFee;
        @BindView(R.id.ll_total_fee)
        LinearLayout llTotalFee;
        @BindView(R.id.tv_pay_detial)
        TextView tvPayDetial;
        @BindView(R.id.tv_exchange_gift_fee)
        TextView tvExchangeGiftFee;
        @BindView(R.id.ll_exchange_gift_fee)
        LinearLayout llExchangeGiftFee;
        @BindView(R.id.tv_withdraw_balance)
        TextView tvWithdrawBalance;
        @BindView(R.id.ll_withdraw_balance)
        LinearLayout llWithdrawBalance;
        @BindView(R.id.tv_must_total_fee)
        TextView tvMustTotalFee;
        @BindView(R.id.tv_order_jifen)
        TextView tv_order_jifen;
        @BindView(R.id.tv_order_no)
        TextView tvOrderNo;
        @BindView(R.id.btn_copy_order_no)
        TextView btnCopyOrderNo;
        @BindView(R.id.tv_order_create_time)
        TextView tvOrderCreateTime;
        @BindView(R.id.tv_group_complate_time)
        TextView tvGroupComplateTime;
        @BindView(R.id.tv_logistic_company)
        TextView tvLogisticCompany;
        @BindView(R.id.tv_logistic_no)
        TextView tvLogisticNo;
        @BindView(R.id.btn_copy_logistic_no)
        TextView btnCopyLogisticNo;
        @BindView(R.id.ll_order_coupon_share)
        LinearLayout ll_order_coupon_share;
        @BindView(R.id.iv_order_coupon_share)
        ImageView iv_order_coupon_share;
        @BindView(R.id.tv_yuan)
        TextView tv_yuan;
        @BindView(R.id.iv_yfze_help)
        ImageView iv_yfze_help;//应付总额 说明icon
        @BindView(R.id.tv_fandian_tip)
        TextView tv_fandian_tip;//说明文案

        FootViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_tax_help)
        public void showTaxTip() {
            //税费说明弹框
            if (taxTipDialog == null) {
                taxTipDialog = new TaxListDialog(OrderDetialActivity.this, "税费明细", taxDetailBeanList, "根据国家政策规定，跨境商品订单总税费以实际交易价格（包括商品售价、运费）为基础进行计算", null);
            } else {
                taxTipDialog.updateData(taxDetailBeanList);
                taxTipDialog.showDialog();
            }
        }

        @OnClick(R.id.iv_yfze_help)
        public void showFreeTip(){
            if(BBCUtil.isEmpty(freePopStr)){
                return;
            }
            if (freeTipDialog == null) {
                freeTipDialog = new ConfirmDialog(OrderDetialActivity.this, freePopStr, "", "知道了", null);
                freeTipDialog.hiddenOkBtn();
            } else {
                freeTipDialog.showDialog();
            }
        }
    }

    public ArrayList<OrderGoodBean> getCanRefundList() {
        ArrayList<OrderGoodBean> orderGoodBeanList = new ArrayList<OrderGoodBean>();
        if (orderBean == null) {
            return orderGoodBeanList;
        }
        if (orderBean.getGoodsList() == null) {
            return orderGoodBeanList;
        }
        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
            if (!BBCUtil.isEmpty(orderGoodBean.getRefundState())) {
                if ("1".equals(orderGoodBean.getRefundState()) || "2".equals(orderGoodBean.getRefundState())) {
                    continue;
                }
                orderGoodBean.setNum(orderGoodBean.getSellCount());
                orderGoodBeanList.add(orderGoodBean);
            }
        }
        return orderGoodBeanList;
    }

    /**
     * 判断取消状态订单是否显示删除按钮
     *
     * @return
     */
    public boolean isRefundState1or2() {
        //判断每个商品是否处于售后状态  退款 0-正常,1-退款处理中,2-退款成功,3-退款驳回 4-售后关闭)
        if (orderBean == null) {
            return false;
        }
        if (orderBean.getGoodsList() == null) {
            return false;
        }
        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
            if (!BBCUtil.isEmpty(orderGoodBean.getRefundState())) {
                if ("1".equals(orderGoodBean.getRefundState()) || "2".equals(orderGoodBean.getRefundState())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断交易完成状态订单是否显示更多按钮
     *
     * @return
     */
    public boolean isRefundState1and2() {
        //判断每个商品是否处于售后状态  退款 0-正常,1-退款处理中,2-退款成功,3-退款驳回 4-售后关闭)
        if (orderBean == null) {
            return false;
        }
        if (orderBean.getGoodsList() == null) {
            return false;
        }
        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
            if (!BBCUtil.isEmpty(orderGoodBean.getRefundState())) {
                if ("0".equals(orderGoodBean.getRefundState()) || "3".equals(orderGoodBean.getRefundState()) || "4".equals(orderGoodBean.getRefundState())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    goRefund();
                    break;
                case 101:
                    if (msg.arg1 == 0) {
                        flag = false;
                        //隐藏气泡
                        if (!isFinishing()) {
                            dealyPopupWindow.dismiss();
                            dealyPopupWindow.setOutsideTouchable(true);
                            footViewHolder.tv_yuan.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (MotionEvent.ACTION_DOWN == event.getAction()) {
                                        dealyPopupWindow.showEditPopup(v);
                                    } else if (MotionEvent.ACTION_UP == event.getAction() || MotionEvent.ACTION_CANCEL == event.getAction()) {
                                        dealyPopupWindow.dismiss();
                                    }
                                    return true;
                                }
                            });
                        }
                    }
                    break;
            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_REFUND) {
                //申请售后回调
                showWaitDialog();
                presenter.reqOrderDetial(tradeId, orderType);
            } else if (requestCode == REQ_PAYRESULT) {
                //fragment 是否需要刷新状态置为true
                //支付结果返回
                showWaitDialog();
                presenter.reqOrderDetial(tradeId, orderType);
            } else if (requestCode == REQ_SELECTADDR) {
                //选中修改地址回调
                isNeedRefresh=true;
                ConfirmDialog confirmDialog = new ConfirmDialog(OrderDetialActivity.this, "处理进度可在我的-帮助与客服-服务进度中查看", "", "知道了", new ConfirmOKI() {
                    @Override
                    public void executeOk() {

                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                confirmDialog.hiddenOkBtn();
                confirmDialog.textLeft();
                presenter.reqOrderDetial(tradeId, orderType);
            }
//            else if(requestCode== SafeImgPwdActivity.REQ_SAFEIMG){
//                if(data!=null){
////                String url=data.getStringExtra("url");
//                    String tag=data.getStringExtra("tag");
//                    presenter.model.notifyData(tag);
//                }
//            }
        } else if (resultCode == 200) {
            if (requestCode == REQ_GONGDAN) {
                //工单页面返回
                showWaitDialog();
                presenter.reqOrderDetial(tradeId, orderType);
            }
        }

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        showWaitDialog();
        presenter.reqOrderDetial(tradeId, orderType);
    }

    @Override
    protected void goBack() {
        if (isNeedRefresh) {
            ActivityUtil.getInstance().exitResult(this, null, RESULT_OK);
        } else {
            ActivityUtil.getInstance().exit(this);
        }
    }

    public boolean isHaveOnlyWithInGood(List<OrderGoodBean> goodSkuList){
        //是否只包含内购商品
        boolean isHaveWihIn=true;
        if (goodSkuList != null) {
            for (OrderGoodBean goodBean : goodSkuList
            ) {
                if(BBCUtil.isBigVer121(OrderDetialActivity.this)&&goodBean.getWithinBuyId()>0){
                    //是内购商品
                }else {
                    //不是内购商品
                    isHaveWihIn=false;
                    break;
                }
            }
        }
        return isHaveWihIn;
    }

    public List<TaxDetailBean> getTaxDetailBeanList() {
        List<TaxDetailBean> taxDetailBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TaxDetailBean taxDetailBean = new TaxDetailBean();
            if (i == 0) {
                taxDetailBean.setTaxTitle("商品税费");
                taxDetailBean.setTaxAmount(100.0);
            } else {
                taxDetailBean.setTaxTitle("运费税费");
                taxDetailBean.setTaxAmount(8.02);
            }
            taxDetailBeans.add(taxDetailBean);
        }
        return taxDetailBeans;
    }

    private void setReturnDetial(PayResultReturnCashDetial tradeReturnCashDetail) {
        if (tradeReturnCashDetail != null) {
            if (tradeReturnCashDetail.getState()==9||tradeReturnCashDetail.getReturnableAmount()==null||tradeReturnCashDetail.getReturnableAmount().doubleValue()==0){
                headViewHolder.llHasReturn.setVisibility(View.GONE);
                headViewHolder.tvNoReturnCash.setVisibility(View.VISIBLE);
                headViewHolder.btnReturnCashShare.setText("分享领券");
            }else{
                headViewHolder.llHasReturn.setVisibility(View.VISIBLE);
                headViewHolder.tvNoReturnCash.setVisibility(View.GONE);
                headViewHolder.tvReturnMoney.setText("¥" + tradeReturnCashDetail.getReturnableAmount());
                long endTime = tradeReturnCashDetail.getEndTime();
                String timeStr = TimeCalculate.getTime3(MyApplication.NOW_TIME, endTime);
                if (!BBCUtil.isEmpty(timeStr)) {//倒计时未结束
                    headViewHolder.btnReturnCashShare.setText("邀请好友");
                    if (tradeReturnCashDetail.getSplitTradeCnt() > 1) {
                        headViewHolder.tvTradeInfo.setText(tradeReturnCashDetail.getSplitTradeCnt() + "单共可返");
                    } else {
                        headViewHolder.tvTradeInfo.setText("本单可返");
                    }
                    headViewHolder.tvTime.setVisibility(View.VISIBLE);
                    headViewHolder.tvTime.setText("仅剩:" + timeStr);
                    headViewHolder.tvShareText.setText("还有大额优惠券");
                } else {//倒计时已结束
                    headViewHolder.btnReturnCashShare.setText("分享领券");
                    if (tradeReturnCashDetail.getSplitTradeCnt() > 1) {
                        headViewHolder.tvTradeInfo.setText(tradeReturnCashDetail.getSplitTradeCnt() + "单共已返");
                    } else {
                        headViewHolder.tvTradeInfo.setText("本单已返");
                    }
                    headViewHolder.tvTime.setVisibility(View.GONE);
                    headViewHolder.tvShareText.setText("还有大额优惠券,快分享给好友吧");
                }
            }
        }
    }
}