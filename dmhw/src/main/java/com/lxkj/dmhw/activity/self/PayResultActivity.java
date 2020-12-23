package com.lxkj.dmhw.activity.self;

import android.content.Intent;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.order.OrderDetialActivity;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.bean.self.ADBean;
import com.lxkj.dmhw.bean.self.BreakUpBean;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.model.MineModel;
import com.lxkj.dmhw.myinterface.ClickDialogI;
import com.lxkj.dmhw.presenter.MinePresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;
import com.lxkj.dmhw.widget.banner.CommonShareDialog;
import com.lxkj.dmhw.widget.dialog.ADImgDialog;

import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class PayResultActivity extends BaseLangActivity<MinePresenter> {

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
    //    private PayResultReturnCashDetial tradeReturnCashDetail;//订单返现信息
//    private PayResultReturnCashDialog payResultReturnCashDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    public void initView() {
        initTitleBar(true, "付款成功");

        initLoading();
//        LocalBroadcastManager.getInstance(this).registerReceiver(timeReceiver, new IntentFilter(Constants.TIME_TASK));
    }

//    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (tradeReturnCashDetail != null) {
//                long endTime = tradeReturnCashDetail.getEndTime();
//                String timeStr = TimeCalculate.getTime3(SuDianApp.NOW_TIME, endTime);
//                if (!BBCUtil.isEmpty(timeStr)) {//倒计时未结束
//                    tvTime.setVisibility(View.VISIBLE);
//                    tvTime.setText("仅剩:" + timeStr);
//                    if (payResultReturnCashDialog!=null&&payResultReturnCashDialog.isShowing()){
//                        payResultReturnCashDialog.settime();
//                    }
//                } else {
//                    showWaitDialog();
//                    if (payResultReturnCashDialog!=null&&payResultReturnCashDialog.isShowing()){
//                        payResultReturnCashDialog.cancelDialog();
//                    }
//                   presenter.reqTradeShareReturnCashDetial(SuDianApp.PAY_RESULT_TRADE_NO);
//                }
//
//            }
//        }
//    };

    @Override
    public void initPresenter() {
        presenter = new MinePresenter(PayResultActivity.this, MineModel.class);
    }

    @Override
    public void initData() {
        //查询更新用户角色信息
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getUserAccountInfo();
            }
        }, 1000);
//        presenter.reqShareInfo(SuDianApp.PAY_RESULT_TRADE_NO,"16");//16-下单成功页
        presenter.getAndCreateTradeReturnCash(MyApplication.PAY_RESULT_TRADE_NO);
        //刷新首页
//        SDJumpUtil.refreshHomeData();

    }

    @OnClick(R.id.btn_look_order)
    public void goOrder() {
        showWaitDialog();
        presenter.reqTradeBreakUp(MyApplication.PAY_RESULT_TRADE_NO);
    }

    @OnClick(R.id.btn_buy_again)
    public void bugAgain() {
        //继续购买
        SDJumpUtil.goHomeActivity(PayResultActivity.this, 0);
    }

    @Override
    public void update(Observable o, Object arg) {
        dismissWaitDialog();
        if ("getAndCreateTradeReturnCash".equals(arg)) {
//            PayResultReturnCash returnCash = presenter.model.getPayResultReturnCash();
//            if (returnCash != null && returnCash.isIfTradeReturnCash()) {
//                //弹出返现弹框
//                payResultReturnCashDialog= new PayResultReturnCashDialog(this, returnCash, new ConfirmOKI() {
//                    @Override
//                    public void executeOk() {
//                        presenter.reqTradeShareReturnCashDetial(SuDianApp.PAY_RESULT_TRADE_NO);
//                        presenter.reqShareInfo(SuDianApp.PAY_RESULT_TRADE_NO,"28");
//                    }
//
//                    @Override
//                    public void executeCancel() {
//
//                    }
//                });
//                setReturnDetial(returnCash.getTradeReturnCashDetail());
//            } else {
//                presenter.reqShareInfo(SuDianApp.PAY_RESULT_TRADE_NO, "16");//16-下单成功页
//            }
        } else if ("reqShareInfo".equals(arg)) {
            ShareBean shareBean = presenter.model.getShareBean();
            if (shareBean != null) {
                if (shareBean.isState()) {
                    ADBean adImgBean = new ADBean();
                    adImgBean.setFlag(2);//2展示
                    adImgBean.setImgUrl(shareBean.getShowImgUrl());
                    new ADImgDialog(this, adImgBean, new ClickDialogI() {
                        @Override
                        public void cancelDialog() {

                        }

                        @Override
                        public void clickImg() {
                            if (shareBean.getType() == 1) {
                                SDJumpUtil.goWhere(PayResultActivity.this, shareBean.getLinkUrl());
                            } else if (shareBean.getType() == 2) {
                                new CommonShareDialog(PayResultActivity.this, shareBean);
                            }
                        }
                    });
                }
            }
        } else if ("reqTradeBreakUp".equals(arg)) {
            BreakUpBean breakUpBean = presenter.model.getBreakUpBean();
            Intent intent;

            if (breakUpBean != null && !breakUpBean.isIfBreakUp()) {
                //不拆单  订单详情
                ActivityUtil.getInstance().exitActivity(OrderDetialActivity.class);
                intent = new Intent(PayResultActivity.this, OrderDetialActivity.class);
                intent.putExtra("orderType", 1);//默认是我的订单 不是分享订单
                if (!BBCUtil.isEmpty(breakUpBean.getTradeId())) {
                    intent.putExtra("tradeId", Long.parseLong(breakUpBean.getTradeId()));
                }
            } else {
                //拆单 订单列表
                ActivityUtil.getInstance().exitActivity(OrderListActivity.class);
                intent = new Intent(PayResultActivity.this, OrderListActivity.class);
            }

            ActivityUtil.getInstance().start(PayResultActivity.this, intent);
            ActivityUtil.getInstance().exitResult(PayResultActivity.this, null, RESULT_OK);
        } else if ("reqTradeBreakUpError".equals(arg)) {
            ActivityUtil.getInstance().exitActivity(OrderListActivity.class);
            Intent intent = new Intent(PayResultActivity.this, OrderListActivity.class);
            ActivityUtil.getInstance().start(PayResultActivity.this, intent);
            ActivityUtil.getInstance().exitResult(PayResultActivity.this, null, RESULT_OK);
        } else if ("reqTradeShareReturnCashDetial".equals(arg)) {

//            PayResultReturnCash returnCash = presenter.model.getPayResultReturnCash();
//            if (returnCash != null) {
//                setReturnDetial(returnCash.getTradeReturnCashDetail());
//            }
        }else if ("reqReturnShareInfo".equals(arg)){
            new CommonShareDialog(this,presenter.model.getShareBean());
        }
    }

//    private void setReturnDetial(PayResultReturnCashDetial tradeReturnCashDetail) {
//        if (tradeReturnCashDetail != null) {
//            this.tradeReturnCashDetail=tradeReturnCashDetail;
//            if (tradeReturnCashDetail.getReturnableAmount().doubleValue() > 0) {
//                llHasReturn.setVisibility(View.VISIBLE);
//                tvNoReturnCash.setVisibility(View.GONE);
//                tvReturnMoney.setText("¥" + tradeReturnCashDetail.getReturnableAmount());
//                long endTime = tradeReturnCashDetail.getEndTime();
//                String timeStr = TimeCalculate.getTime3(SuDianApp.NOW_TIME, endTime);
//                if (!BBCUtil.isEmpty(timeStr)) {//倒计时未结束
//                    btnReturnCashShare.setText("邀请好友");
//                    if (tradeReturnCashDetail.getSplitTradeCnt() > 1) {
//                        tvTradeInfo.setText(tradeReturnCashDetail.getSplitTradeCnt() + "单共可返");
//                    } else {
//                        tvTradeInfo.setText("本单可返");
//                    }
//                    tvTime.setVisibility(View.VISIBLE);
//                    tvTime.setText("仅剩:" + timeStr);
//                    tvShareText.setText("还有大额优惠券");
//                } else {//倒计时已结束
//                    btnReturnCashShare.setText("分享领券");
//                    if (tradeReturnCashDetail.getSplitTradeCnt() > 1) {
//                        tvTradeInfo.setText(tradeReturnCashDetail.getSplitTradeCnt() + "单共已返");
//                    } else {
//                        tvTradeInfo.setText("本单已返");
//                    }
//                    tvTime.setVisibility(View.GONE);
//                    tvShareText.setText("还有大额优惠券,快分享给好友吧");
//                }
//
//            } else {
//                llHasReturn.setVisibility(View.GONE);
//                tvNoReturnCash.setVisibility(View.VISIBLE);
//                btnReturnCashShare.setText("分享领券");
//            }
//
//        }
//    }

    @Override
    protected void goBack() {
        ActivityUtil.getInstance().exitResult(PayResultActivity.this, null, RESULT_OK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeReceiver);
    }

    @OnClick(R.id.btn_return_cash_share)
    public void onViewClicked() {
        showWaitDialog();
        presenter.reqShareInfo(MyApplication.PAY_RESULT_TRADE_NO,"28");
    }
}