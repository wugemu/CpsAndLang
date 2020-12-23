package com.lxkj.dmhw.adapter.self.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.DateUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.order.AnonymousOrderActivity;
import com.lxkj.dmhw.activity.self.order.OrderDetialActivity;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.utils.BBCUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1 on 2017/4/18.
 */

public class OrderItemTop implements OrderContent {
    private OrderBean orderBean;

    public OrderItemTop(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    @Override
    public int getLayout() {
        return R.layout.child_order_item_top;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    @Override
    public View getView(final Context context, View convertView, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //测试
//        orderBean.setHelpTradeType(2);
//        orderBean.setTradeStatus(2);
//        orderBean.setHelpTradeStr("还差1任助力");

        if (!orderBean.isIfShareTrade()) {
            //我的订单
            holder.tv_lefttag.setText("买");
            holder.tv_lefttag.setBackgroundResource(R.drawable.bg_circle_black);
        } else {
            //分享订单
            holder.tv_lefttag.setText("卖");
            holder.tv_lefttag.setBackgroundResource(R.drawable.bg_circle_red);
        }
        holder.tvOrderNo.setText("订单编号：" + orderBean.getTradeNo());

        String status = "";

        switch (orderBean.getTradeStatus()) {
            case 0:
                //团订单和普通订单文案 需求是要显示一致
                status = "交易关闭";
                break;
            case 1:
                status = "等待付款";
                break;
            case 2:
                if (!orderBean.isIfGroupTrade()) {
                    status = "等待审核";
                } else {
                    if (orderBean.getGroupStatus() == 1) {
                        //1参团中 显示文案
                        status = "拼团中，差" + orderBean.getLessGroupUserCount() + "人";
                    }else {
                        status = "等待审核";
                    }
                }

//                if(orderBean.getHelpTradeType()==2){
//                    //2免费领取订单
//                    if(!orderBean.isHelpSuccess()){
//                        //助力中状态
//                        status="邀请助力中";
//                    }
//                }


                break;
            case 5:
                status = "等待发货";
                break;
            case 6:
                status = "等待收货";
                break;
            case 7:
                status = "交易成功";
                break;
        }
        holder.tvStatus.setText(status);

        //Ver1.0.9 版本助力订单隐藏
        if (!BBCUtil.isEmpty(orderBean.getHelpTradeStr())) {
            //助力订单文案
            holder.tv_zhuli_status.setText(orderBean.getHelpTradeStr());
            holder.tv_zhuli_status.setVisibility(View.VISIBLE);
        } else {
            holder.tv_zhuli_status.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderType = 1;//我的订单
                if (orderBean.isIfShareTrade()) {
                    //分享订单
                    orderType = 2;
                }

                if (!orderBean.isIfHideBuy()) {
                    //非匿名订单
                    Intent intent = new Intent(context, OrderDetialActivity.class);
                    intent.putExtra("orderType", orderType);
                    intent.putExtra("tradeId", orderBean.getTradeId());
                    ActivityUtil.getInstance().startResult((Activity) context, intent, OrderListActivity.REQ_ORDERDETAIL);
                } else {
                    //匿名订单
                    Intent intent = new Intent(context, AnonymousOrderActivity.class);
                    intent.putExtra("tradeId", orderBean.getTradeId());
                    ActivityUtil.getInstance().start((Activity) context, intent);
                }

            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_lefttag)
        TextView tv_lefttag;
        @BindView(R.id.tv_orderitem_orderno)
        TextView tvOrderNo;
        @BindView(R.id.tv_orderitem_status)
        TextView tvStatus;
        @BindView(R.id.tv_zhuli_status)
        TextView tv_zhuli_status;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


