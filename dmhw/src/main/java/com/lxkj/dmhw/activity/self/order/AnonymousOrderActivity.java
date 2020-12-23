package com.lxkj.dmhw.activity.self.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GroupOrder;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.OrderModel;
import com.lxkj.dmhw.presenter.OrderPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;

import java.util.Date;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class AnonymousOrderActivity extends BaseLangActivity<OrderPresenter> {

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_status_desc)
    TextView tvOrderStatusDesc;
    @BindView(R.id.tv_user_info)
    TextView tvUserInfo;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_order_create_time)
    TextView tvOrderCreateTime;
    @BindView(R.id.tv_logistic_company)
    TextView tvLogisticCompany;
    @BindView(R.id.tv_logistic_no)
    TextView tvLogisticNo;
    @BindView(R.id.tv_group_complate_time)
    TextView tvGroupComplateTime;
    @BindView(R.id.btn_copy_logistic_no)
    Button btnCopyLogisticNo;
    @BindView(R.id.tv_order_jifen)
    TextView tv_order_jifen;
    @BindView(R.id.tv_group_tag)
    TextView tv_group_tag;


    private long tradeId;//
    private OrderBean orderBean;//订单状态 0：取消 1：待付款 2 待审核 5：待发货 6：待收货 7：交易完成
    @Override
    public int getLayoutId() {
        return R.layout.activity_anonymous_order;
    }

    @Override
    public void initView() {
        initTitleBar("订单详情");
        initLoading();
        LocalBroadcastManager.getInstance(this).registerReceiver(timeReceiver, new IntentFilter(Constants.TIME_TASK));

    }

    @Override
    public void initPresenter() {
        presenter = new OrderPresenter(this, OrderModel.class);
    }

    @Override
    public void initData() {
        tradeId = getIntent().getLongExtra("tradeId", 0);
        showWaitDialog();
        presenter.reqOrderDetial(tradeId, 2);
    }
    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (orderBean != null && orderBean.getTradeStatus() == 1) {
                String deadline = "";
                if (orderBean.getLessSecond() > 0) {
                    orderBean.setLessSecond(orderBean.getLessSecond() - 1);
                    deadline = TimeCalculate.getTime2(0, orderBean.getLessSecond());

                    tvOrderStatusDesc.setText("订单已提交，请在00:" + deadline + "内完成支付，超时订单将自动取消");
                } else {
                    //重新获取订单
                    if (presenter != null) {
                        presenter.reqOrderList(orderBean.getTradeStatus(),2,false);
                    }
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeReceiver);
    }
    @Override
    public void update(Observable o, Object arg) {
        if ("reqOrderDetial".equals(arg)){
            orderBean = presenter.model.getOrderBean();
            if (orderBean!=null){
                GroupOrder groupOrder = orderBean.getTradeGroupCreate();
                if (orderBean.getTradeGroupCreateId()>0){
                    //团购标签
                    tv_group_tag.setVisibility(View.VISIBLE);
                }else{
                    tv_group_tag.setVisibility(View.GONE);
                }

                tvUserInfo.setText(BBCUtil.encryption(orderBean.getSndTo(),1) + "  " + BBCUtil.encryption(orderBean.getTel(),3));
                tvSumPrice.setText("¥"+BBCUtil.getDoubleFormat2(orderBean.getTotalAmount()));
                tvSum.setText("共"+orderBean.getHideCount()+"件商品");
                //积分
                if(orderBean.getAddUserScoreNum()>0) {
                    tv_order_jifen.setVisibility(View.VISIBLE);
                    tv_order_jifen.setText("素店积分：获得" + BBCUtil.getDoubleFormat(orderBean.getAddUserScoreNum()) + "积分");
                }else {
                    tv_order_jifen.setVisibility(View.GONE);
                }
                tvOrderNo.setText("订单编号：" + orderBean.getTradeNo());
                tvOrderCreateTime.setText("下单时间：" + DateUtil.datetime(new Date(orderBean.getRegTime() * 1000), "yyyy-MM-dd HH:mm:ss"));
                tvLogisticCompany.setVisibility(View.GONE);
                tvLogisticNo.setVisibility(View.GONE);
                btnCopyLogisticNo.setVisibility(View.GONE);
                if (groupOrder!=null&&!BBCUtil.isEmpty(groupOrder.getGroupSuccessTimeStr())){
                    tvGroupComplateTime.setVisibility(View.VISIBLE);
                    tvGroupComplateTime.setText("拼单成功：" + groupOrder.getGroupSuccessTimeStr());
                }else {
                    tvGroupComplateTime.setVisibility(View.GONE);
                }
//                orderBean.setTradeStatus(0);//测试

                switch (orderBean.getTradeStatus()) {
                    case 0://取消
                        tvOrderStatus.setText("交易关闭");
                        tvOrderStatusDesc.setText("关闭原因：" + orderBean.getRejected());
                        break;
                    case 1://待付款
                        tvOrderStatus.setText("等待付款");
                        tvOrderStatusDesc.setText("订单已提交，请在00:00:00内完成支付，超时订单将自动取消");
                        break;
                    case 2://待审核
                        tvOrderStatus.setText("等待审核");
                        tvOrderStatusDesc.setText("付款成功，宝贝正在打包中，请耐心等待");
                        if (orderBean.isIfGroupTrade()) {
                            //团订单
                            if(groupOrder.getGroupStatus()==1){
                                //拼团中
                                tvOrderStatusDesc.setText("拼团中，还差 " + orderBean.getTradeGroupCreate().getLessGroupUserCount() + " 人拼团成功");
                            }else if(groupOrder.getGroupStatus()==2){
                                //拼团成功
                                tvOrderStatusDesc.setText("宝贝正在打包中，请耐心等待");
                            }
                        }
                        break;
                    case 5://待发货
                        tvOrderStatus.setText("等待发货");
                        tvOrderStatusDesc.setText("付款成功，宝贝正在打包中，请耐心等待");
                        break;
                    case 6://待收货
                        tvOrderStatus.setText("等待收货");
                        tvOrderStatusDesc.setText("宝贝正在向你飞来，请准备好接驾～");
                        if(!BBCUtil.isEmpty(orderBean.getLogisticsName())) {
                            tvLogisticCompany.setVisibility(View.VISIBLE);
                            tvLogisticCompany.setText("物流公司：" + orderBean.getLogisticsName());
                        }
                        if(!BBCUtil.isEmpty(orderBean.getPostId())){
                            tvLogisticNo.setVisibility(View.VISIBLE);
                            tvLogisticNo.setText("物流单号：" + orderBean.getPostId());
                            btnCopyLogisticNo.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 7://交易完成
                        tvOrderStatus.setText("交易成功");
                        if(orderBean.isOnlyReturn()) {
                            //团长免费团 团长 且 选择的返点 文案修改 待确认如何判断选择返点的订单
                            //groupOrder.getGroupType()==2&&groupOrder.isIfCreateGroup()
                            //完全返点的订单
                            //确定后匿名订单也加下
                            tvOrderStatusDesc.setText("拼团成功，收益已发放至您的账户");
                        }else {
                            tvOrderStatusDesc.setText("宝贝已签收");
                        }
                        if(!BBCUtil.isEmpty(orderBean.getLogisticsName())) {
                            tvLogisticCompany.setVisibility(View.VISIBLE);
                            tvLogisticCompany.setText("物流公司：" + orderBean.getLogisticsName());
                        }
                        if(!BBCUtil.isEmpty(orderBean.getPostId())) {
                            tvLogisticNo.setVisibility(View.VISIBLE);
                            btnCopyLogisticNo.setVisibility(View.VISIBLE);
                            tvLogisticNo.setText("物流单号：" + orderBean.getPostId());
                        }
                        break;
                }
            }

        }

    }


    @OnClick({R.id.btn_copy_order_no, R.id.btn_copy_logistic_no, R.id.ll_order_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_copy_order_no:
                BBCUtil.copy(orderBean.getTradeNo(), this);
                ToastUtil.show(this, "已经复制到剪切板");
                break;
            case R.id.btn_copy_logistic_no:
                BBCUtil.copy(orderBean.getPostId(), this);
                ToastUtil.show(this, "已经复制到剪切板");
                break;
            case R.id.ll_order_service:
                //联系客服
//                SDJumpUtil.goZXKF(this);
                break;
        }
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        showWaitDialog();
        presenter.reqOrderDetial(tradeId, 2);
    }
}