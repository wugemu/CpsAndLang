package com.lxkj.dmhw.activity.self;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.alipay.sdk.app.PayResultActivity;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.adapter.self.settle.SettlementAdapter;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.AmountSettleBean;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.CouponSettleBean;
import com.lxkj.dmhw.bean.self.CreateOrderBean;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.PhoneLTBean;
import com.lxkj.dmhw.bean.self.TaxDetailBean;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.bean.self.WarehouseBean;
import com.lxkj.dmhw.dialog.DelayedShipmentDialog;
import com.lxkj.dmhw.dialog.SalePayDialog;
import com.lxkj.dmhw.model.PayModel;
import com.lxkj.dmhw.myinterface.Confirm3OKI;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.myinterface.CouponSelectI;
import com.lxkj.dmhw.myinterface.SalePayI;
import com.lxkj.dmhw.myinterface.TimerI;
import com.lxkj.dmhw.presenter.PayPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.lxkj.dmhw.widget.dialog.CouponSelectDialog;
import com.lxkj.dmhw.widget.dialog.TaxListDialog;
import com.lxkj.dmhw.widget.dialog.TimerDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class SettlementActivity extends BaseLangActivity<PayPresenter> {

    private final int REQ_SELECTPHONENUM = 300;//选择电话号码
    private final int REQ_PAYRESULT = 200;//支付结果
    private final int REQ_SELECTADDR = 100;//选择地址
    private final int REQ_ADD_ADDR = 102;//选择地址
    private final int REQ_SELECTAUTH = 101;//选择实名认证
    @BindView(R.id.lv_list)
    ListView lv_list;
    @BindView(R.id.sv_settlement)
    ScrollView sv_settlement;
    @BindView(R.id.iv_topnotify)
    ImageView iv_topnotify;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
    @BindView(R.id.ll_address_info)
    LinearLayout ll_address_info;
    @BindView(R.id.tv_name_phone)
    TextView tv_name_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.ll_auth)
    LinearLayout ll_auth;
    @BindView(R.id.tv_auth)
    TextView tv_auth;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.sw_niming)
    Switch sw_niming;

    @BindView(R.id.sw_dikou)
    Switch sw_dikou;
    @BindView(R.id.sw_getfandian)
    Switch sw_getfandian;
    @BindView(R.id.tv_goodprice)
    TextView tv_goodprice;
    @BindView(R.id.tv_activityprice)
    TextView tv_activityprice;
    @BindView(R.id.ll_activityprice)
    LinearLayout ll_activityprice;
    @BindView(R.id.ll_couponprice)
    LinearLayout ll_couponprice;
    @BindView(R.id.tv_couponprice)
    TextView tv_couponprice;
    @BindView(R.id.tv_postprice)
    TextView tv_postprice;
    @BindView(R.id.tv_taxprice)
    TextView tv_taxprice;
    @BindView(R.id.tv_showprice)
    TextView tv_showprice;
    @BindView(R.id.tv_sum_price)
    TextView tv_sum_price;
    @BindView(R.id.ll_crash_tip)
    LinearLayout ll_crash_tip;
    @BindView(R.id.tv_carsh_tip)
    TextView tv_carsh_tip;
    @BindView(R.id.ll_dikou)
    RelativeLayout ll_dikou;
    @BindView(R.id.tv_crash_value)
    TextView tv_crash_value;
    @BindView(R.id.et_remark)
    CleanableEditText et_remark;
    @BindView(R.id.tv_bottom_caretip)
    TextView tv_bottom_caretip;
    @BindView(R.id.ll_choice_phone)
    LinearLayout ll_choice_phone;
    @BindView(R.id.tv_choice_phone)
    TextView tv_choice_phone;
    @BindView(R.id.view_top_line)
    View view_top_line;
    @BindView(R.id.view_line_split)
    View view_line_split;
    @BindView(R.id.iv_tax_help)
    ImageView iv_tax_help;
    @BindView(R.id.ll_buy_tip)
    LinearLayout ll_buy_tip;
    @BindView(R.id.ll_jifen)
    LinearLayout ll_jifen;
    @BindView(R.id.tv_jifen)
    TextView tv_jifen;
    @BindView(R.id.tv_jhpt_postfee_tip)
    TextView tv_jhpt_postfee_tip;

    //集货相关view
    @BindView(R.id.ll_jihuo)
    LinearLayout ll_jihuo;
    @BindView(R.id.iv_jihuo_check)
    ImageView iv_jihuo_check;
    @BindView(R.id.tv_jihuo)
    TextView tv_jihuo;
    @BindView(R.id.iv_address_arrow)
    ImageView iv_address_arrow;

    @BindView(R.id.ll_jihuo_tip)
    LinearLayout ll_jihuo_tip;
    @BindView(R.id.tv_jihuo_tip)
    TextView tv_jihuo_tip;

    //团购头像相关
    @BindView(R.id.ll_group_head)
    LinearLayout ll_group_head;
    @BindView(R.id.tv_group_status)
    TextView tv_group_status;
    @BindView(R.id.ll_heads)
    LinearLayout ll_heads;

    @BindView(R.id.iv_coupon_arrow)
    ImageView iv_coupon_arrow;
    @BindView(R.id.ll_getfandian)
    LinearLayout ll_getfandian;//仅获得返点
    @BindView(R.id.tv_bottom_price_tip)
    TextView tv_bottom_price_tip;//底部价格返点文案

    @BindView(R.id.ll_return_money)
    LinearLayout ll_return_money;//返现
    @BindView(R.id.tv_return_money_tip)
    TextView tv_return_money_tip;
    @BindView(R.id.tv_return_money)
    TextView tv_return_money;
    @BindView(R.id.cb_get_type1)
    CheckBox cb_get_type1;
    @BindView(R.id.cb_get_type2)
    CheckBox cb_get_type2;

    private SettlementAdapter adapter;

    private TimerDialog timerDialog;
    private CouponSelectDialog couponSelectDialog;
    private ConfirmDialog addrTipDialog;
    private ConfirmDialog authTipDialog;
    private ConfirmDialog nimingTipDialog;
    private ConfirmDialog fandianTipDialog;
    private ConfirmDialog jihuoTipDialog;
    private ConfirmDialog postTipDialog;
    private TaxListDialog taxTipDialog;
    private ConfirmDialog payTipDialog;
    private SalePayDialog salePayDialog;
    private ConfirmDialog useCashDialog;//不可以使用提现抵扣弹框
    private int type;//1=玩主,0=玩客
    private TopNotifyBean topNotifyBean;//顶部通知信息
    private AddrBean addrBean;//地址信息
    private UserAccount userAccount;//实名认证信息
    private PhoneLTBean phoneLTBean;//联通手机号信息
    private boolean ifNeedCardId;//是否需要身份证（实名认证）
    private boolean ifLiantongCard = false;//默认false 是否是电话卡需要填写身份资料信息
    private Coupon useCoupon;//选择的优惠券
    private boolean ifUseSystemCash;//是否使用余额抵扣
    private List<Coupon> keyongList;//可用优惠券列表
    private List<Coupon> bukeyongList;//不可用优惠券列表
    private double realPayAmount = 0.0;//实付款金额
    private List<GoodSku> goodSkuList;//携带过来的数据
    private boolean ifAnonymous;//是否匿名购买
    private boolean isCanUseCash;//是否可以使用余额 ture可以使用 false不可以使用
    private String balancePayMsg;//不能使用余额的原因
    private boolean ifNoDeliveryAran;//是否是偏远地区 要弹框提示
    private List<TaxDetailBean> taxDetailBeanList;//税费说明明细
    private boolean isJihuo;//是否集货团
    private boolean isCheckJihuo = true;//是否选中集货按钮
    private Group group;//团对象
    private List<String> groupUserList;
    private boolean isOnlyReturn;//是否选中 仅获得返点
    private String freePopStr;//仅获得返点弹框文案
    private String addressPopStr;//集货地址弹框文案
    private int takeType;//收货方式 1立即发货 2云仓发货
    private int userGrade;//购物等级

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement;
    }

    @Override
    public void initView() {
        initTitleBar(true, "确认订单");
        initLoading();
        int w = BBCUtil.getDisplayWidth(SettlementActivity.this);
        int h = (int) (w * 60.0 / 375);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        iv_topnotify.setLayoutParams(params);

        sw_niming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ifAnonymous = isChecked;
                if (isChecked) {
                    if (nimingTipDialog == null) {
                        nimingTipDialog = new ConfirmDialog(SettlementActivity.this, "如果开启匿名购买，您的服务商将不能获得您的订单信息，为提供精准服务，请谨慎开启", "", "知道了", null);
                        nimingTipDialog.hiddenOkBtn();
                        nimingTipDialog.textLeft();
                    } else {
                        nimingTipDialog.showDialog();
                    }
                }
            }
        });
        sw_dikou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //切换成使用抵扣时
                    if (!isCanUseCash) {
                        //不可以使用抵扣
                        sw_dikou.setChecked(false);
                        if (useCashDialog == null) {
                            if (BBCUtil.isEmpty(balancePayMsg)) {
                                return;
                            }
                            useCashDialog = new ConfirmDialog(SettlementActivity.this, balancePayMsg, "", "知道了", null);
                            useCashDialog.hiddenOkBtn();
                            useCashDialog.textLeft();
                        } else {
                            useCashDialog.showDialog();
                        }
                        return;
                    }
                }
                if (presenter.model.getAmountSettleBean() != null) {
                    refreshDikou(presenter.model.getAmountSettleBean(), isChecked,isOnlyReturn);
                }
            }
        });

        sw_getfandian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (presenter.model.getAmountSettleBean() != null) {
                    refreshDikou(presenter.model.getAmountSettleBean(), ifUseSystemCash,isChecked);
                }
                if(isChecked){
                    if(BBCUtil.isEmpty(freePopStr)){
                        return;
                    }
                    //获得返点弹框说明
                    if (fandianTipDialog == null) {
                        fandianTipDialog = new ConfirmDialog(SettlementActivity.this, freePopStr, "", "知道了", null);
                        fandianTipDialog.hiddenOkBtn();
                    } else {
                        fandianTipDialog.showDialog();
                    }
                }
            }
        });

        tv_jhpt_postfee_tip.setVisibility(View.GONE);
        cb_get_type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_get_type2.setChecked(false);
                }
            }
        });
        cb_get_type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_get_type1.setChecked(false);
                }
            }
        });
    }

    @Override
    public void initPresenter() {
        presenter = new PayPresenter(SettlementActivity.this, PayModel.class);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            goodSkuList = (List<GoodSku>) intent.getSerializableExtra("skuList");
            group = (Group) intent.getExtras().get("group");
//            videoId=intent.getStringExtra("videoId");
//            isJihuo=getIntent().getBooleanExtra("isJihuo",false);
        }
        if (BBCUtil.ifBillVip(SettlementActivity.this)) {
            type = 1;
        } else {
            type = 0;
        }
        showWaitDialog();
        if (group == null) {
            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", "", "", "");
        } else {
            if (!BBCUtil.isEmpty(group.getId())) {
                presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", "", "", group.getId());
            } else {
                presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", "", group.getGroupId(), "");
            }
        }

        presenter.reqParCode();

    }

    @OnClick(R.id.iv_topnotify)
    public void goTopNotify() {
        //顶部通知点击
        if (topNotifyBean != null && !BBCUtil.isEmpty(topNotifyBean.getLinkUrl())) {
            Intent intent = new Intent(SettlementActivity.this, WebViewActivity.class);
            intent.putExtra(Variable.webUrl, topNotifyBean.getLinkUrl());
            ActivityUtil.getInstance().start(SettlementActivity.this, intent);
        }
    }

    @OnClick({R.id.tv_add_address, R.id.ll_address_info})
    public void clickAddAddr() {
        if (isJihuo && !BBCUtil.isEmpty(group.getId())) {
            //集货参团点击地址切换不做处理
            return;
        }
        if(addrBean!=null) {
            //点击选择地址
            Intent intent = new Intent(SettlementActivity.this, AddrListActivity.class);
            intent.putExtra("selectAddr", true);
            ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTADDR);
        }else {
            //点击添加地址
            Intent intent = new Intent(SettlementActivity.this, AddrDetailActivity.class);
            intent.putExtra("selectAddr", true);
            ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_ADD_ADDR);
        }
    }

    @OnClick(R.id.ll_auth)
    public void clickAuth() {
        //实名认证点击
        if (userAccount != null) {
            Intent intent = new Intent(SettlementActivity.this, AuthListActivity.class);
            intent.putExtra("selectAuth", true);
            ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTAUTH);
        } else {
            //未认证时
            Intent intent = new Intent(SettlementActivity.this, AuthActivity.class);
            intent.putExtra("showDialog", true);//弹出说明弹框
            intent.putExtra("isFirst", true);//第一次认证
            intent.putExtra("selectAuth", true);
            ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTAUTH);
        }
    }

    @OnClick(R.id.ll_select_coupon)
    public void selectCoupon() {
        //选择优惠券弹框
        if (group != null && (keyongList == null || keyongList.size() == 0) && (bukeyongList == null || bukeyongList.size() == 0)) {
            return;
        }
        if (couponSelectDialog == null) {
            couponSelectDialog = new CouponSelectDialog(SettlementActivity.this, keyongList, bukeyongList, new CouponSelectI() {
                @Override
                public void selectCoupon(Coupon coupon) {
                    refreshCoupon(coupon);
                    if (group == null) {
                        presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, "", false, "");
                    } else {
                        if (!BBCUtil.isEmpty(group.getId())) {
                            presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, "", isJihuo, group.getId());
                        } else {
                            presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, group.getGroupId(), isJihuo, "");
                        }
                    }

                }
            });
        } else {
            couponSelectDialog.showDialog();
        }
    }

    @OnClick(R.id.iv_post_help)
    public void showPostTip() {
        //运费说明弹框
        if (postTipDialog == null) {
            postTipDialog = new ConfirmDialog(SettlementActivity.this, "新疆、西藏、内蒙古等偏远地区配送包邮商品需要加收运费，具体加收金额以提交订单页给出的金额为准", "", "知道了", null);
            postTipDialog.hiddenOkBtn();
            postTipDialog.textLeft();
        } else {
            postTipDialog.showDialog();
        }
    }

    @OnClick(R.id.iv_tax_help)
    public void showTaxTip() {
        //税费说明弹框
        if (taxTipDialog == null) {
            taxTipDialog = new TaxListDialog(SettlementActivity.this, "税费明细", taxDetailBeanList, "根据国家政策规定，跨境商品订单总税费以实际交易价格（包括商品售价、运费）为基础进行计算", null);
        } else {
            taxTipDialog.updateData(taxDetailBeanList);
            taxTipDialog.showDialog();
        }
    }

    @OnClick(R.id.iv_getfandian_tip)
    public void clickFanDianTip(){
        if(BBCUtil.isEmpty(freePopStr)){
            return;
        }
        //获得返点弹框说明
        if (fandianTipDialog == null) {
            fandianTipDialog = new ConfirmDialog(SettlementActivity.this, freePopStr, "", "知道了", null);
            fandianTipDialog.hiddenOkBtn();
        } else {
            fandianTipDialog.showDialog();
        }
    }
    @OnClick({R.id.iv_jihuo_help,R.id.iv_jihuo_can_help,R.id.tv_jihuo,R.id.tv_jihuo_tip})
    public void clickJihuoHelp(){
        if(BBCUtil.isEmpty(addressPopStr)){
            return;
        }
        //获得返点弹框说明
        if (jihuoTipDialog == null) {
            jihuoTipDialog = new ConfirmDialog(SettlementActivity.this, addressPopStr, "", "知道了", null);
            jihuoTipDialog.hiddenOkBtn();
        } else {
            jihuoTipDialog.showDialog();
        }
    }

    @OnClick(R.id.iv_niming_tip)
    public void showNimingTip() {
        //匿名说明弹框
        if (nimingTipDialog == null) {
            nimingTipDialog = new ConfirmDialog(SettlementActivity.this, "如果开启匿名购买，您的服务商将不能获得您的订单信息，为提供精准服务，请谨慎开启", "", "知道了", null);
            nimingTipDialog.hiddenOkBtn();
            nimingTipDialog.textLeft();
        } else {
            nimingTipDialog.showDialog();
        }
    }

//    @OnClick(R.id.ll_choice_phone)
//    public void goChoicePhone() {
//        //选择联通手机号
//        Intent intent = new Intent(SettlementActivity.this, RwzlActivity.class);
//        intent.putExtra("isSelectPhone", true);
//        if (phoneLTBean != null) {
//            intent.putExtra("id", phoneLTBean.getId());
//        }
//        ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTPHONENUM);
//    }

    private void realGoPay(Map<String, Object> groupParma2) {
        //提交订单
        if (addrBean == null) {
            //地址为添加
            if (addrTipDialog == null) {
                addrTipDialog = new ConfirmDialog(SettlementActivity.this, "您还没有收货地址，请点击添加", "添加", "取消", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        //点击添加
                        Intent intent = new Intent(SettlementActivity.this, AddrListActivity.class);
                        intent.putExtra("selectAddr", true);
                        ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTADDR);
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
            } else {
                addrTipDialog.showDialog();
            }
            return;
        }
        if (ifNeedCardId && userAccount == null) {
            //实名未认证
            if (authTipDialog == null) {
                authTipDialog = new ConfirmDialog(SettlementActivity.this, "购买保税、直邮商品需要提供您的实名信息，请点击添加", "添加", "取消", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        //点击添加
                        Intent intent = new Intent(SettlementActivity.this, AuthActivity.class);
                        intent.putExtra("showDialog", true);//弹出说明弹框
                        intent.putExtra("isFirst", true);//第一次认证
                        intent.putExtra("selectAuth", true);
                        ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_SELECTAUTH);
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                authTipDialog.textLeft();
            } else {
                authTipDialog.showDialog();
            }
            return;
        }
        if (ifLiantongCard && phoneLTBean == null) {
            ToastUtil.show(SettlementActivity.this, "请选择新手机号码");
            return;
        }

        if (ifNoDeliveryAran) {
            //偏远地区不发货提示
            if (payTipDialog == null) {
                payTipDialog = new ConfirmDialog(SettlementActivity.this, "订单中存在商品超出配送范围\n请修改地址或移除该商品", "", "知道了", null);
                payTipDialog.hiddenOkBtn();
                payTipDialog.textLeft();
            } else {
                payTipDialog.showDialog();
            }
            return;
        }
        String message = et_remark.getText().toString().trim();
        String userAddrId = "";
        String userIdCardId = "";
        String mobileNetId = "";
        if (addrBean != null) {
            userAddrId = addrBean.getId();
        }
        if (userAccount != null) {
            userIdCardId = userAccount.getId();
        }
        if (phoneLTBean != null) {
            mobileNetId = phoneLTBean.getId();
        }

        if(cb_get_type1.isChecked()){
            takeType=1;
        }
        if(cb_get_type2.isChecked()){
            takeType=2;
        }

        //是否报备商品
        if (presenter.model.getOrderDelayGoodsReminderList() != null && presenter.model.getOrderDelayGoodsReminderList().size() > 0) {
            //需要报备弹框提示
//            ToastUtil.show(this,"有商品需要延迟报备！");
            final String mUserAddrId = userAddrId;
            final String mUserIdCardId = userIdCardId;
            final String mMobileNetId = mobileNetId;
            new DelayedShipmentDialog(this, presenter.model.getOrderDelayGoodsReminderList(), new ConfirmOKI() {
                @Override
                public void executeOk() {
                    Map<String, Object> groupParam = new HashMap<>();
                    if (group != null) {
//                        if (!BBCUtil.isEmpty(group.getId())) {
//                            if (isJihuo && !isCheckJihuo) {
//                                ToastUtil.show(SettlementActivity.this, "集货团必须勾选集货拼团地址");
//                                return;
//                            }
//                        }
                        if (!BBCUtil.isEmpty(group.getId())) {
                            groupParam.put("tradeGroupCreateId", group.getId());
                        } else {
                            groupParam.put("groupId", group.getGroupId());
                        }
                        groupParam.put("isEqualAddress", isJihuo);
                        if (groupParma2 != null) {
                            groupParam.clear();
                            groupParam.putAll(groupParma2);
                        }
                    }
                    //免费团是否只要返点
                    groupParam.put("isOnlyReturn",isOnlyReturn);
                    showWaitDialog();
                    presenter.createOrder(useCoupon, ifAnonymous, ifUseSystemCash, message, getTradeSkuVOList(goodSkuList), mUserAddrId, mUserIdCardId, mMobileNetId, groupParam,takeType,userGrade);
                }

                @Override
                public void executeCancel() {

                }
            });
            return;
        }

        Map<String, Object> groupParam = new HashMap<>();
        if (group != null) {
//            if (!BBCUtil.isEmpty(group.getId())) {
//                if (isJihuo && !isCheckJihuo) {
//                    ToastUtil.show(SettlementActivity.this, "集货团必须勾选集货拼团地址");
//                    return;
//                }
//            }
            if (!BBCUtil.isEmpty(group.getId())) {
                groupParam.put("tradeGroupCreateId", group.getId());
            } else {
                groupParam.put("groupId", group.getGroupId());
            }
            groupParam.put("isEqualAddress", isJihuo);
            if (groupParma2 != null) {
                groupParam.clear();
                groupParam.putAll(groupParma2);
            }
        }

        //免费团是否只要返点
        groupParam.put("isOnlyReturn",isOnlyReturn);

        showWaitDialog();
        presenter.createOrder(useCoupon, ifAnonymous, ifUseSystemCash, message, getTradeSkuVOList(goodSkuList), userAddrId, userIdCardId, mobileNetId, groupParam,takeType,userGrade);

    }

    @OnClick(R.id.tv_submit)
    public void goPay() {
        if (ButtonUtil.isFastDoubleClick(R.id.tv_submit)) {
            ToastUtil.show(SettlementActivity.this, R.string.tip_btn_fast);
            return;
        }
        realGoPay(null);
    }


    //跳转到协议和消费告知书
    @OnClick(R.id.tv_buytip)
    public void goXYAndXF() {
//        SDJumpUtil.goWhere(this, Constants.H5HOST + Constants.PAY_SERVICE_AGREEMENT);
    }

    @OnClick(R.id.iv_jihuo_check)
    public void clickIvJihuoCheck() {
        isCheckJihuo = !isCheckJihuo;
        if (isCheckJihuo) {
            iv_jihuo_check.setImageResource(R.mipmap.share_check_selected);
        } else {
            iv_jihuo_check.setImageResource(R.mipmap.share_check_unchecked);
        }
        if (BBCUtil.isEmpty(group.getId())) {//开团用户
            isJihuo = isCheckJihuo;
            showWaitDialog();
            presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, group.getGroupId(), isJihuo, "");
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        dismissWaitDialog();
        if ("reqSettlementInfo".equals(arg)) {
            //确认订单信息
            PayModel payModel = presenter.model;
            refreshView(payModel);

            if (group != null) {//团购购买
                tv_coupon.setText("拼团商品不支持各类优惠券");
                iv_coupon_arrow.setVisibility(View.INVISIBLE);
                if (!BBCUtil.isEmpty(group.getId())) {//参团用户
                    isJihuo = payModel.isEqualAddress();
                    if (isJihuo) {//显示集货选择
                        ll_jihuo_tip.setVisibility(View.VISIBLE);
                        iv_address_arrow.setVisibility(View.GONE);
                        tv_jihuo_tip.setText(payModel.getAddressContent());
                    } else {
                        ll_jihuo.setVisibility(View.GONE);
                    }
                } else if (group.getGroupType() == 1) {//普通团开团
                    if (payModel.isEqualAddressShow()){
                        ll_jihuo.setVisibility(View.VISIBLE);
                        tv_jihuo.setText(payModel.getAddressContent());
                        isCheckJihuo = false;
                        iv_jihuo_check.setImageResource(R.mipmap.cart_uncheck);
                    }else{
                        ll_jihuo.setVisibility(View.GONE);
                    }

                }
            } else {//普通商品购买
                //查询可用优惠券数量
                presenter.countCouponsForBuy(getTradeSkuVOList(goodSkuList));
                presenter.getCouponsForBuy(getTradeSkuVOList(goodSkuList));
                iv_coupon_arrow.setVisibility(View.VISIBLE);
            }

            //确认订单金额获取
            if (group == null) {
                presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, "", false, "");
            } else {
                if (!BBCUtil.isEmpty(group.getId())) {
                    presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, "", isJihuo, group.getId());
                } else {
                    presenter.reqSettlementAmount(getTradeSkuVOList(goodSkuList), addrBean, useCoupon, group.getGroupId(), isJihuo, "");
                }
            }
            //返现
//            if (payModel.isIfReturnCash()){
//                ll_return_money.setVisibility(View.VISIBLE);
//                tv_return_money_tip.setText(payModel.getReturnCashTitle());
//                tv_return_money.setText("¥"+payModel.getReturnAbleCashAmount());
//            }else{
//                ll_return_money.setVisibility(View.GONE);
//            }

        } else if ("reqParCode".equals(arg)) {
            //顶部通知消息
            topNotifyBean = presenter.model.getTopNotifyBean();
            if (topNotifyBean != null && topNotifyBean.getIfShow() == 1) {
                if (!BBCUtil.isEmpty(topNotifyBean.getPicUrl())) {
                    ImageLoadUtils.doLoadImageUrl(iv_topnotify, topNotifyBean.getPicUrl());
                    iv_topnotify.setVisibility(View.VISIBLE);
                } else {
                    iv_topnotify.setVisibility(View.GONE);
                }
            } else {
                iv_topnotify.setVisibility(View.GONE);
            }
        } else if ("countCouponsForBuy".equals(arg)) {
            //获取可用优惠券数量
            refreshCoupon(useCoupon);
        } else if ("getCouponsForBuy".equals(arg)) {
            //可用优惠券列表
            CouponSettleBean couponSettleBean = presenter.model.getCouponSettleBean();
            if (couponSettleBean != null) {
                keyongList = couponSettleBean.getCanUse();
                bukeyongList = couponSettleBean.getCannotUse();
            }
        } else if ("reqSettlementAmount".equals(arg)) {
            //获取确认订单金额
            AmountSettleBean amountSettleBean = presenter.model.getAmountSettleBean();
            if (amountSettleBean != null) {
                userGrade=amountSettleBean.getUserGrade();
                isCanUseCash = amountSettleBean.getCanUseCash();
                balancePayMsg = amountSettleBean.getBalancePayMsg();
                //温馨提示
                if (!BBCUtil.isEmpty(amountSettleBean.getBottomPrompt())) {
                    tv_bottom_caretip.setVisibility(View.VISIBLE);
                    tv_bottom_caretip.setText(amountSettleBean.getBottomPrompt());//后台返回温馨提示
                } else {
                    tv_bottom_caretip.setVisibility(View.GONE);
                }

                //是否可用余额
                if (isCanUseCash) {
                    tv_crash_value.setTextColor(getResources().getColor(R.color.colorBlackText));
                } else {
                    tv_crash_value.setTextColor(getResources().getColor(R.color.colorBlackText2));
                }
                taxDetailBeanList = amountSettleBean.getTaxDetails();

                //测试
//                taxDetailBeanList=getTaxDetailBeanList();

                if (taxDetailBeanList != null && taxDetailBeanList.size() > 0) {
                    iv_tax_help.setVisibility(View.VISIBLE);
                } else {
                    iv_tax_help.setVisibility(View.GONE);
                }
            }

            refreshAmount(amountSettleBean);
            sv_settlement.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sv_settlement.scrollTo(0, 0);
                }
            }, 100);
        } else if ("createOrder".equals(arg)) {
            //创建订单结果
            final CreateOrderBean createOrderBean = presenter.model.getCreateOrderBean();
            if (createOrderBean == null) {
                return;
            }
            //倒计时弹框
//            createOrderBean.setShowTimer(true); //测试
//            createOrderBean.setWaitSecond(10);//测试
            if (createOrderBean.getNotJoinGroupType() != 0) {
                String content = createOrderBean.getNotJoinGroupTitle();
                Map<String, Object> parmas = new HashMap<>();
                parmas.put("groupId", group.getGroupId());//开团只传groupId

                if (isJihuo) {//重开一集货团 不存在集货参团情况
                    new ConfirmDialog(this, content, true, "新开团为集团拼团，参团用户也使用该地址为收货地址", new Confirm3OKI() {
                        @Override
                        public void executeOk(boolean isCheck) {
                            parmas.put("isEqualAddress", isCheck);
                            showWaitDialog();
                            realGoPay(parmas);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                } else {//重开一普通团
                    new ConfirmDialog(this, content, new ConfirmOKI() {
                        @Override
                        public void executeOk() {
                            parmas.put("isEqualAddress", false);
                            showWaitDialog();
                            realGoPay(parmas);
                        }

                        @Override
                        public void executeCancel() {

                        }
                    });
                }
            } else {
                if (createOrderBean.isShowTimer() && createOrderBean.getWaitSecond() > 0) {
                    timerDialog = new TimerDialog(SettlementActivity.this, createOrderBean.getWaitSecond(), new TimerI() {
                        @Override
                        public void timeOver() {
                            SettlementActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //此时已在主线程中，可以更新UI了
                                    //获取订单数据
                                    presenter.reqOrderToPay(createOrderBean.getTradeNo());
                                }
                            });

                        }
                    });
                } else {
                    //获取订单数据
                    presenter.reqOrderToPay(createOrderBean.getTradeNo());
                }
            }

        } else if ("sendSafeCode".equals(arg)) {
            //支付发送短信成功
            if (salePayDialog != null) {
                salePayDialog.startDownTime();
            }
        } else if ("checkSmsForCachOrder".equals(arg)) {
            //短信支付校验成功
            CreateOrderBean createOrderBean = presenter.model.getCreateOrderBean();
            if (createOrderBean == null) {
                return;
            }
            MyApplication.PAY_RESULT_TRADE_NO = createOrderBean.getTradeNo();

            if (group != null) {
                Intent intent = new Intent(SettlementActivity.this, GroupInfoActivity.class);
                intent.putExtra("tradeNo", createOrderBean.getTradeNo());
                intent.putExtra("isPayResult", true);
                ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_PAYRESULT);
            } else {
                Intent intent = new Intent(SettlementActivity.this, PayResultActivity.class);
                ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_PAYRESULT);
            }
        } else if ("reqOrderToPay".equals(arg)) {
            //根据订单号去订单支付信息
            CreateOrderBean createOrderBean = presenter.model.getCreateOrderBean();
            if (createOrderBean == null) {
                return;
            }
            goPaying(createOrderBean);

        }
    }

    public void goPaying(final CreateOrderBean createOrderBean) {
        //余额支付
        if (createOrderBean.getIfCheckSms() == 1) {
            //需要短信验证
            if (salePayDialog == null) {
                salePayDialog = new SalePayDialog(SettlementActivity.this, createOrderBean.getMobile(), new SalePayI() {
                    @Override
                    public void sendCode() {
                        if (ButtonUtil.isFastDoubleClick(R.id.btn_pay_send_code)) {
                            ToastUtil.show(SettlementActivity.this, R.string.tip_btn_fast);
                            return;
                        }
                        showWaitDialog();
                        presenter.sendSafeCode(createOrderBean.getTradeNo());
                    }

                    @Override
                    public void commit(String captcha) {
                        if (ButtonUtil.isFastDoubleClick(R.id.btn_safephone)) {
                            ToastUtil.show(SettlementActivity.this, R.string.tip_btn_fast);
                            return;
                        }
                        showWaitDialog();
                        presenter.checkSmsForCachOrder(captcha, createOrderBean.getTradeNo());
                    }

                    @Override
                    public void clickClose() {
                        Intent intent = new Intent(SettlementActivity.this, OrderListActivity.class);
                        ActivityUtil.getInstance().start(SettlementActivity.this, intent);
                        ActivityUtil.getInstance().exit(SettlementActivity.this);
                    }
                });
            } else {
                salePayDialog.showDialog();
            }
        }else if(createOrderBean.getIfCheckSms() == 3){
            //3-不需要，虚拟货币已覆盖商品金额，并且不需要进行短信校验
            MyApplication.PAY_RESULT_TRADE_NO = createOrderBean.getTradeNo();
            if (group != null) {
                Intent intent = new Intent(SettlementActivity.this, GroupInfoActivity.class);
                intent.putExtra("tradeNo", createOrderBean.getTradeNo());
                intent.putExtra("isPayResult", true);
                ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_PAYRESULT);
            }else {
                Intent intent = new Intent(SettlementActivity.this, PayResultActivity.class);
                ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_PAYRESULT);
            }
        }else {
            //不需要短信验证
            if (createOrderBean.getAmount() > 0) {
                //需要第三方支付
                Intent intent = new Intent(SettlementActivity.this, PayTypeActivity.class);
                intent.putExtra("realPayAmount", BBCUtil.getDoubleFormat2(createOrderBean.getAmount()));
                intent.putExtra("tradeNo", createOrderBean.getTradeNo());
                intent.putExtra("payCountDown", createOrderBean.getPayCountDown());
                if (group != null) {
                    intent.putExtra("isGroupOrder", true);
                }
                ActivityUtil.getInstance().startResult(SettlementActivity.this, intent, REQ_PAYRESULT);
            }
        }
    }

    private View createGroupHeadView(int pos) {
        View view = LayoutInflater.from(this).inflate(R.layout.child_group_head, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_44), (int) getResources().getDimension(R.dimen.dp_53));
        view.setLayoutParams(params);
        CircleImageView head = view.findViewById(R.id.civ_head);
        TextView tv_label = view.findViewById(R.id.tv_label);
        ImageLoadUtils.doLoadCircleImageUrl(head, groupUserList.get(pos));
//        GlideUtil.getInstance().displayHeadNoBg(this, groupUserList.get(pos), head);
        if (pos == groupUserList.size() - 1) {//当前用户是最后一个
            tv_label.setVisibility(View.VISIBLE);
            tv_label.setText("待支付");
        } else if (pos == 0) {
            tv_label.setVisibility(View.VISIBLE);
            tv_label.setText("团长");
        } else {
            tv_label.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (pos != 0) {
            p.setMargins((int) getResources().getDimension(R.dimen.design_10dp), 0, 0, 0);
        }
        return view;
    }

    private TextView createGroupHeadAdd(int add) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_44), (int) getResources().getDimension(R.dimen.dp_44));
        p.setMargins((int) getResources().getDimension(R.dimen.design_10dp), 0, 0, 0);
        textView.setLayoutParams(p);
        textView.setBackgroundResource(R.drawable.bg_dash_circle);
        textView.setTextColor(Color.parseColor("#CDCDCD"));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
        textView.setText("+" + add);
        return textView;
    }

    private TextView createGroupSLH() {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.dp_14), (int) getResources().getDimension(R.dimen.dp_14));
        p.setMargins((int) getResources().getDimension(R.dimen.design_15dp), (int) getResources().getDimension(R.dimen.design_15dp), (int) getResources().getDimension(R.dimen.design_5dp), 0);
        textView.setLayoutParams(p);
        textView.setTextColor(getResources().getColor(R.color.colorBlackText));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
        textView.setText("...");
        return textView;
    }

    public void refreshView(PayModel payModel) {
        groupUserList = payModel.getGroupUserInfo();
        //地址信息
        refreshAddr(payModel.getUserAddr());

        if (groupUserList != null && groupUserList.size() > 0) {//用户参团
            ll_group_head.setVisibility(View.VISIBLE);
            ll_heads.removeAllViews();
            if (groupUserList.size() == 1) {//团长开团
                tv_group_status.setText("立即支付，即可开团成功");
                for (int i = 0; i < groupUserList.size(); i++) {
                    ll_heads.addView(createGroupHeadView(i));
                }
                ll_heads.addView(createGroupHeadAdd(group.getGroupCount() - groupUserList.size()));
            } else if (group.getGroupCount() == groupUserList.size()) {//当前用户是最后一个参团成员
                SpannableString ss = new SpannableString("仅差1人成团，支付成功后即可拼团成功");
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRedMain)), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_group_status.setText(ss);
                if (group.getGroupCount() <= 5) {
                    for (int i = 0; i < groupUserList.size(); i++) {
                        ll_heads.addView(createGroupHeadView(i));
                    }
                } else {
                    ll_heads.addView(createGroupHeadView(0));
                    ll_heads.addView(createGroupSLH());
                    ll_heads.addView(createGroupHeadView(groupUserList.size() - 1));
                }
            } else {
                tv_group_status.setText("立即支付，即可加入拼团");
                if (groupUserList.size() <= 4) {
                    for (int i = 0; i < groupUserList.size(); i++) {
                        ll_heads.addView(createGroupHeadView(i));
                    }
                    ll_heads.addView(createGroupHeadAdd(group.getGroupCount() - groupUserList.size()));
                } else {
                    ll_heads.addView(createGroupHeadView(0));
                    ll_heads.addView(createGroupSLH());
                    ll_heads.addView(createGroupHeadView(groupUserList.size() - 1));
                    ll_heads.addView(createGroupHeadAdd(group.getGroupCount() - groupUserList.size()));
                }
            }

        } else {
            ll_group_head.setVisibility(View.GONE);
        }
        if (payModel.isIfNeedShowServiceAgreement()) {
            ll_buy_tip.setVisibility(View.VISIBLE);
        } else {
            ll_buy_tip.setVisibility(View.GONE);
        }
        view_top_line.setVisibility(View.GONE);
        //认证信息
        ifNeedCardId = payModel.isIfNeedCardId();
        if (ifNeedCardId) {
            view_top_line.setVisibility(View.VISIBLE);
            ll_auth.setVisibility(View.VISIBLE);
            refreshAuth(payModel.getUserIdcard());
        } else {
            ll_auth.setVisibility(View.GONE);
        }

        //联通卡信息
        ifLiantongCard = payModel.isIfLiantongCard();
        //测试
//        ifLiantongCard=true;

        if (ifLiantongCard) {
            view_top_line.setVisibility(View.VISIBLE);
            ll_choice_phone.setVisibility(View.VISIBLE);
            if (ifNeedCardId) {
                view_line_split.setVisibility(View.VISIBLE);
            } else {
                view_line_split.setVisibility(View.GONE);
            }
        } else {
            ll_choice_phone.setVisibility(View.GONE);
        }

        //商品信息
        List<WarehouseBean> warehouseBeanList = presenter.model.getSkuList();
        adapter = new SettlementAdapter(SettlementActivity.this, getGoodBeanList(warehouseBeanList), type, SettlementAdapter.GOOD_NORMAL);
        adapter.setGroup(group);//设置团订单数据
        lv_list.setAdapter(adapter);
        BaseLangUtil.setListViewHeightBasedOnChildren(lv_list);
        sv_settlement.smoothScrollTo(0, 0);

        //是否显示团仅获得返点
        boolean isFreeReturn=payModel.isFreeReturn();
        freePopStr=payModel.getFreePop();
        addressPopStr=payModel.getAddressPop();
        if(isFreeReturn){
            //显示返点信息选项
            ll_getfandian.setVisibility(View.VISIBLE);
        }else {
            ll_getfandian.setVisibility(View.GONE);
        }
    }

    public void refreshAmount(AmountSettleBean amountSettleBean) {
        if (amountSettleBean == null) {
            return;
        }
        //商品金额
        String goodprice = "¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getSumAmount());
        if (type == 1) {
            //玩主
            if(amountSettleBean.getSumProfile()>0){
                goodprice = goodprice + "(已省" + BBCUtil.getDoubleFormat(amountSettleBean.getSumProfile()) + "元)";
            }
        }
        tv_goodprice.setText(goodprice);
        //活动优惠
        if (amountSettleBean.getActivityReduce() > 0) {
            ll_activityprice.setVisibility(View.VISIBLE);
            tv_activityprice.setText("-¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getActivityReduce()));
        } else {
            ll_activityprice.setVisibility(View.GONE);
        }
        //优惠券优惠
        //获取可用优惠券数量
        if (amountSettleBean.getCouponAmount() > 0) {
            ll_couponprice.setVisibility(View.VISIBLE);
            tv_couponprice.setText("-¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getCouponAmount()));
        } else {
            ll_couponprice.setVisibility(View.GONE);
        }
        //运费
        tv_postprice.setText("+¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getSumPostage()));
        //税费
        tv_taxprice.setText("+¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getSumTax()));
        //小计
        tv_showprice.setText("¥" + BBCUtil.getDoubleFormat2(amountSettleBean.getTotalAmount()));

        //积分
        if (amountSettleBean.getScore() > 0) {
            ll_jifen.setVisibility(View.VISIBLE);
            tv_jifen.setText("获得" + BBCUtil.getDoubleFormat(amountSettleBean.getScore()) + "积分");
        } else {
            ll_jifen.setVisibility(View.GONE);
        }

        //余额抵扣和实付款
        refreshDikou(amountSettleBean, ifUseSystemCash,isOnlyReturn);

        //原始运费为0时非集货后运费  是集货开团 隐藏集货功能
        if(amountSettleBean.getSumPostage()<=0){//判断运费字段要改下
            if(ll_jihuo.getVisibility()==View.VISIBLE){
                ll_jihuo.setVisibility(View.GONE);
            }
        }
        if(ll_jihuo.getVisibility()==View.VISIBLE&&!BBCUtil.isEmpty(amountSettleBean.getSumTotalPostageAddress())){
            //有集货开团集货功能时 显示运费tip
            tv_jhpt_postfee_tip.setVisibility(View.VISIBLE);
            tv_jhpt_postfee_tip.setText(amountSettleBean.getSumTotalPostageAddress());
//            if (isCheckJihuo) {
//                tv_jhpt_postfee_tip.setText("取消集货拼团时，运费为¥10.00");
//            } else {
//                tv_jhpt_postfee_tip.setText("集货拼团时，运费为¥2.00");
//            }
        }else if(ll_jihuo_tip.getVisibility()==View.VISIBLE&&!BBCUtil.isEmpty(amountSettleBean.getSumTotalPostageAddress())){
            //参团 集货团 显示运费tip
            tv_jhpt_postfee_tip.setVisibility(View.VISIBLE);
            tv_jhpt_postfee_tip.setText(amountSettleBean.getSumTotalPostageAddress());
        }else {
            tv_jhpt_postfee_tip.setVisibility(View.GONE);
        }
    }

    //余额抵扣和实付款
    public void refreshDikou(AmountSettleBean amountSettleBean, boolean isCheckCash, boolean isOnlyReturn) {
        this.ifUseSystemCash = isCheckCash;
        this.isOnlyReturn=isOnlyReturn;
        realPayAmount = amountSettleBean.getRealPayAmount();
        if(isOnlyReturn){
            //只获取返点 支付金额为0
            //付款价格变为0
            realPayAmount=0;
            //切换成是否选中 仅获得返点
            tv_bottom_price_tip.setText("已选择仅获得返点，将不会获得该商品");
            tv_bottom_price_tip.setVisibility(View.VISIBLE);
        }else {
            tv_bottom_price_tip.setVisibility(View.GONE);
        }
        //余额抵扣
        double cashAmount = amountSettleBean.getCashAmount();
        double kedikouAmount = cashAmount;
        if (cashAmount > 0) {
            if (cashAmount >= realPayAmount) {
                //余额大于等于付款金额
                kedikouAmount = realPayAmount;
                tv_crash_value.setText("可用余额抵扣¥" + BBCUtil.getDoubleFormat2(realPayAmount));
            } else {
                kedikouAmount = cashAmount;
                tv_crash_value.setText("可用余额抵扣¥" + BBCUtil.getDoubleFormat2(cashAmount));
            }
            ll_dikou.setVisibility(View.VISIBLE);

            if (isCheckCash) {
                //选择抵扣
                realPayAmount = realPayAmount - kedikouAmount;
                if (cashAmount > kedikouAmount) {
                    tv_carsh_tip.setText("可用余额还剩" + BBCUtil.getDoubleFormat2(cashAmount - kedikouAmount) + "元");
                    ll_crash_tip.setVisibility(View.VISIBLE);
                } else {
                    ll_crash_tip.setVisibility(View.GONE);
                }
            } else {
                ll_crash_tip.setVisibility(View.GONE);
            }
        } else {
            ll_dikou.setVisibility(View.GONE);
            ll_crash_tip.setVisibility(View.GONE);
        }
        //实付款
        tv_sum_price.setText("¥" + BBCUtil.getDoubleFormat2(realPayAmount));
    }

    public void refreshAddr(AddrBean addr) {
        iv_address_arrow.setVisibility(View.VISIBLE);
        sv_settlement.scrollBy(0, 0);
        //地址信息
        addrBean = addr;
        if (addrBean != null) {
            tv_add_address.setVisibility(View.GONE);
            ll_address_info.setVisibility(View.VISIBLE);
            tv_name_phone.setText(addrBean.getPersonName() + " " + addrBean.getServNum());
            tv_address.setText(addrBean.getProvince() + " " + addrBean.getCity() + " " + addrBean.getArea() + " " + addrBean.getAddress());
        } else {
            tv_add_address.setVisibility(View.VISIBLE);
            ll_address_info.setVisibility(View.GONE);
        }
    }

    public void refreshAuth(UserAccount userAcc) {
        //认证信息
        userAccount = userAcc;
        if (userAccount != null) {
            tv_auth.setText(userAccount.getRealName() + "  " + userAccount.getCardNo());
            tv_auth.setTextColor(getResources().getColor(R.color.colorBlackText));
        } else {
            tv_auth.setText("请输入报关实名认证信息（仅用于海关报关）");
            tv_auth.setTextColor(getResources().getColor(R.color.colorBlackHint));
        }
    }

    public void refreshCoupon(Coupon conpon) {
        //优惠券选择后刷新
        useCoupon = conpon;
        String couponNum = presenter.model.getCouponNum();
        if (!BBCUtil.isEmpty(couponNum) && Integer.parseInt(couponNum) > 0) {
            tv_coupon.setTextColor(getResources().getColor(R.color.colorBlackText));
            tv_coupon.setText(couponNum + "张可用");
        } else {
            tv_coupon.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tv_coupon.setText("暂无可用");
        }
        if (conpon != null) {
            //选择了优惠券
            tv_coupon.setTextColor(getResources().getColor(R.color.colorRedMain));
            switch (conpon.getCouponCategory()) {
                case 1:
                    //满减券
                    tv_coupon.setText("-¥" + BBCUtil.getDoubleFormat2(conpon.getDiscount()));
                    break;
                case 2:
                    //免税券
                    tv_coupon.setText("免税");
                    break;
                case 3:
                    //折扣券
                    tv_coupon.setText(BBCUtil.getDoubleFormat(conpon.getDiscount() * 10) + "折");
                    break;
                case 4:
                    //包邮券
                    tv_coupon.setText("包邮");
                    break;
                case 7:
                case 8:
                    //免单券
                    tv_coupon.setText("免单");
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_SELECTADDR) {
                //选择地址回调
                if (data != null) {
                    AddrBean addr = (AddrBean) data.getSerializableExtra("addrBean");
                    String idCardId = null;
                    if (userAccount != null) {
                        idCardId = userAccount.getId();
                    }
                    refreshAddr(addr);
                    if (group == null) {
                        presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addr.getId(), idCardId, "", "");
                    } else {
                        if (!BBCUtil.isEmpty(group.getId())) {
                            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addr.getId(), idCardId, "", group.getId());
                        } else {
                            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addr.getId(), idCardId, group.getGroupId(), "");
                        }
                    }

                }
            } else if (requestCode == REQ_SELECTAUTH) {
                if (data != null) {
                    UserAccount userAcc = (UserAccount) data.getSerializableExtra("userAccount");
                    refreshAuth(userAcc);
                    String addrId = null;
                    if (addrBean != null) {
                        addrId = addrBean.getId();
                    }
                    if (group == null) {
                        presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, userAcc.getId(), "", "");
                    } else {
                        if (!BBCUtil.isEmpty(group.getId())) {
                            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, userAcc.getId(), "", group.getId());
                        } else {
                            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, userAcc.getId(), group.getGroupId(), "");
                        }

                    }
                }
            } else if (requestCode == REQ_PAYRESULT) {
                //支付结果返回
                ActivityUtil.getInstance().exitResult(SettlementActivity.this,null,RESULT_OK);
            } else if (requestCode == REQ_SELECTPHONENUM) {
                //选择手机号回调
                if (data != null) {
                    phoneLTBean = (PhoneLTBean) data.getSerializableExtra("phoneLTBean");
                    String selectPhoneStr = phoneLTBean.getMobile();
                    tv_choice_phone.setText("已选新手机号码：" + selectPhoneStr);
                    tv_choice_phone.setTextColor(getResources().getColor(R.color.colorBlackText));
                }
            }else if(requestCode==REQ_ADD_ADDR){
                //没有地址添加地址回调
                String idCardId = null;
                if (userAccount != null) {
                    idCardId = userAccount.getId();
                }
                if (group == null) {
                    presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", idCardId, "", "");
                } else {
                    if (!BBCUtil.isEmpty(group.getId())) {
                        presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", idCardId, "", group.getId());
                    } else {
                        presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), "", idCardId, group.getGroupId(), "");
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public List<GoodSku> getGoodBeanList(List<WarehouseBean> warehouseBeanList) {
        List<GoodSku> goodSkuList = new ArrayList<GoodSku>();
        if (warehouseBeanList != null) {
            ifNoDeliveryAran = false;
            for (int i = 0; i < warehouseBeanList.size(); i++) {
                WarehouseBean warehouseBean = warehouseBeanList.get(i);
                if (warehouseBean.getIfNoDeliveryAran() == -1) {
                    //偏远地址不发货
                    ifNoDeliveryAran = true;
                }
                List<GoodSku> goodSkus = warehouseBean.getShopCarDtos();
                if (goodSkus != null && goodSkus.size() > 0) {
                    goodSkus.get(0).setSettlementTopPost(warehouseBean.getWarehouseName());
                    if (!BBCUtil.isEmpty(warehouseBean.getNoDeliveryAranTips())) {
                        goodSkus.get(0).setNoDeliveryAranTips(warehouseBean.getNoDeliveryAranTips());
                    }
                    goodSkuList.addAll(goodSkus);
                }
            }
        }
        return goodSkuList;
    }

    public List<TradeSkuVO> getTradeSkuVOList(List<GoodSku> goodSkuList) {
        List<TradeSkuVO> tradeSkuVOList = new ArrayList<TradeSkuVO>();
        if (goodSkuList != null) {
            for (GoodSku sku : goodSkuList) {
                TradeSkuVO tradeSkuVO = new TradeSkuVO();
                tradeSkuVO.setSkuId(sku.getSkuId() + "");
                tradeSkuVO.setVideoId(sku.getVideoId());
                tradeSkuVO.setNum(sku.getNum());
                if (sku.getCartId() > 0) {
                    //购物车来的
                    tradeSkuVO.setCartId(sku.getCartId() + "");
                }
                if(BBCUtil.isBigVer121(SettlementActivity.this)&&sku.getWithinBuyId()>0){
                    tradeSkuVO.setWithinBuyId(sku.getWithinBuyId());//内购id
                }
                tradeSkuVOList.add(tradeSkuVO);
            }
        }

        return tradeSkuVOList;
    }

    public boolean isHaveOnlyWithInGood(List<GoodSku> goodSkuList){
        //是否只包含内购商品
        boolean isHaveWihIn=true;
        if (goodSkuList != null) {
            for (GoodSku sku : goodSkuList
            ) {
                if(BBCUtil.isBigVer121(SettlementActivity.this)&&sku.getWithinBuyId()>0){
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

    @Override
    public void refreshNet() {
        super.refreshNet();
        showWaitDialog();
        String addrId = "";
        if (addrBean != null) {
            addrId = addrBean.getId();
        }
        String idCardId = "";
        if (userAccount != null) {
            idCardId = userAccount.getId();
        }
        if (group == null) {
            presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, idCardId, "", "");
        } else {
            if (!BBCUtil.isEmpty(group.getId())) {
                presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, idCardId, "", group.getId());
            } else {
                presenter.reqSettlementInfo(getTradeSkuVOList(goodSkuList), addrId, idCardId, group.getGroupId(), "");
            }
        }
        presenter.reqParCode();
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

}