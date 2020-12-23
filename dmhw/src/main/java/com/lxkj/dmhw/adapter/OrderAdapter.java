package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Order;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 推广订单
 * Created by zyhant on 18-2-24.
 */

public class OrderAdapter extends BaseQuickAdapter<Order.OrderList, BaseViewHolder> {

    private int position;
    private Context context;
    private boolean isCheck;
    private int hasNoData=0;
    private String isRate="0";
    TextPaint tp;
    private OrderAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(OrderAdapter.OnItemClickListener li) {
        mListener = li;
    }

    public OrderAdapter(Context context, int position, boolean isCheck) {
        super(R.layout.adapter_order);
        this.context = context;
        this.position = position;
        this.isCheck = isCheck;
    }
    public void setHasNoData(int isHas){
        this.hasNoData=isHas;
    }
    @Override
    protected void convert(BaseViewHolder helper, Order.OrderList item) {
        TextView adapter_order_settlement = helper.getView(R.id.adapter_order_settlement);
        tp= adapter_order_settlement.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_order_settlement_estimate = helper.getView(R.id.adapter_order_settlement_estimate);
        tp= adapter_order_settlement_estimate.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_order_estimate = helper.getView(R.id.adapter_order_estimate);
        tp= adapter_order_estimate.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_order_my_settlement_estimate = helper.getView(R.id.adapter_order_my_settlement_estimate);
        tp= adapter_order_my_settlement_estimate.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_order_my_estimate = helper.getView(R.id.adapter_order_my_estimate);
        tp= adapter_order_my_estimate.getPaint();
        tp.setFakeBoldText(true);
        TextView my_award = helper.getView(R.id.my_award);
        tp= my_award.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_order_payment = helper.getView(R.id.adapter_order_payment);
        tp= adapter_order_payment.getPaint();
        tp.setFakeBoldText(true);

        try {
            if(helper.getLayoutPosition()==getData().size()-1&&hasNoData==1){
                helper.setBackgroundRes(R.id.order_item_layout,R.drawable.personal_grid_bg01);
                helper.setGone(R.id.order_item_split_layout,false);
            }else{
                helper.setBackgroundColor(R.id.order_item_layout, Color.WHITE);
                helper.setGone(R.id.order_item_split_layout,true);
            }
            if (isRate.equals("1")){
                helper.setGone(R.id.my_award_layout,false);
                if (isCheck) {//我的订单
                    helper.setGone(R.id.adapter_order_my_layout, true);
                    helper.setGone(R.id.adapter_order_team_layout, false);
                } else {//团队订单
                    helper.setGone(R.id.adapter_order_my_layout, false);
                    helper.setGone(R.id.adapter_order_team_layout, true);
                }
            }else{
                helper.setGone(R.id.my_award_layout,true);
                if(isCheck){
                 helper.setTextColor(R.id.my_award_title,Color.parseColor("#000000"));
                 helper.setTextColor(R.id.my_award,Color.parseColor("#000000"));
                 helper.setText(R.id.my_award_title,"预估佣金  ¥ ");
                 helper.setText(R.id.my_award,item.getPrediction());
                }else{
                    helper.setTextColor(R.id.my_award_title,Color.parseColor("#000000"));
                    helper.setTextColor(R.id.my_award,Color.parseColor("#000000"));
                    helper.setText(R.id.my_award_title,"我的奖励  ¥ ");
                    helper.setText(R.id.my_award,item.getPrediction());
                }
                helper.setGone(R.id.adapter_order_team_layout,false);
                helper.setGone(R.id.adapter_order_my_layout, false);
            }
            if (isCheck) {
                helper.getView(R.id.adapter_order_source_layout).setVisibility(View.INVISIBLE);
            }else {
                helper.setGone(R.id.adapter_order_source_layout,true);
            }
            helper.setGone(R.id.adapter_new_one_fragment_check,false);
            //比价显示
            if (item.getIsbj().equals("1")){
                helper.setGone(R.id.bijia,true);
            }else{
               helper.setGone(R.id.bijia,false);
            }

            Utils.displayImageRounded(context, item.getShopmainpic(), helper.getView(R.id.adapter_order_image),10);
            helper.setText(R.id.adapter_order_title, item.getShopname());
            helper.setText(R.id.adapter_order_id, item.getOrderid());
            helper.setText(R.id.adapter_order_payment, item.getPaymentamount());
            helper.setText(R.id.adapter_order_estimate, item.getPrediction());
            helper.setText(R.id.adapter_order_my_estimate, item.getPrediction());
            helper.setText(R.id.goodsnum, "共"+item.getShopnum()+"件商品 合计:");
            helper.setText(R.id.adapter_order_source, "订单来源: "+item.getAdvertisingname());
            helper.setText(R.id.adapter_order_settlement, item.getCommission());
            helper.setText(R.id.adapter_order_settlement_estimate, item.getRatio());
            helper.setText(R.id.adapter_order_my_settlement_estimate, item.getRatio());
            helper.setText(R.id.adapter_order_creation_time, "创建时间:  "+item.getCreatetime());
            helper.setText(R.id.adapter_order_status_text, item.getOrderstatus());
            switch (position) {
                case 0:// 全部
                    switch (item.getOrderstatus()) {
                        case "已付款":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            helper.setGone(R.id.adapter_daozhang_source, false);
                            break;
                        case "已收货":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                            helper.setText(R.id.adapter_order_settlement_time, "收货时间: "+item.getSettletime());
                            helper.setGone(R.id.adapter_order_settlement_time, true);
                            helper.setGone(R.id.adapter_daozhang_source, true);
                            helper.setText(R.id.adapter_daozhang_source,"预计到账： "+item.getAccounttime());
                            break;
                        case "已结算":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                            helper.setText(R.id.adapter_order_settlement_time, "结算时间: "+item.getSettletime());
                            helper.setGone(R.id.adapter_order_settlement_time, true);
                            helper.setGone(R.id.adapter_daozhang_source, false);
                            break;
                        default://已失效 违规等
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            helper.setGone(R.id.adapter_daozhang_source, false);
                         break;
                    }
                    break;
                case 1:// 已付款
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                    helper.setGone(R.id.adapter_order_settlement_time, false);
                    helper.setGone(R.id.adapter_daozhang_source, false);
                    break;
                case 2:// 已收货
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                    helper.setText(R.id.adapter_order_settlement_time, "收货时间: "+item.getSettletime());
                    helper.setGone(R.id.adapter_order_settlement_time, true);
                    helper.setGone(R.id.adapter_daozhang_source, true);
                    helper.setText(R.id.adapter_daozhang_source,"预计到账："+item.getAccounttime());
                    break;
                case 3:// 已结算
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                    helper.setText(R.id.adapter_order_settlement_time, "结算时间: "+item.getSettletime());
                    helper.setGone(R.id.adapter_order_settlement_time, true);
                    helper.setGone(R.id.adapter_daozhang_source, false);
                    break;
                // 已退款 已失效  违规订单
                default:
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.bg_rect_black_20dp);
                    helper.setGone(R.id.adapter_order_settlement_time, false);
                    helper.setGone(R.id.adapter_daozhang_source, false);
                    break;
            }
            if (item.getSettletime()!=null&&item.getSettletime().equals("")){
                helper.setGone(R.id.adapter_order_settlement_time, false);
            }

            helper.getView(R.id.order_goods_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(item,0);
                }
            });

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(Order.OrderList data, int type);
    }

    public void setIsRate(String rate){
        this.isRate=rate;
    }
}
