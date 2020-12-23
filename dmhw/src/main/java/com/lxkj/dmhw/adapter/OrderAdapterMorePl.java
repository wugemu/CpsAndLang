package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Order;
import com.lxkj.dmhw.bean.OrderMorePl;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 推广订单  多平台
 */

public class OrderAdapterMorePl extends BaseQuickAdapter<OrderMorePl.OrderList, BaseViewHolder> {

    private int position;
    private Context context;
    private int plattype;
    private int isMyOrder;
    private int hasNoData=0;
    private String isRate="0";
    TextPaint tp;
    private OrderAdapterMorePl.OnItemClickListener mListener;
    public void setOnItemClickListener(OrderAdapterMorePl.OnItemClickListener li) {
        mListener = li;
    }
    public OrderAdapterMorePl(Context context, int position,int plat,int ismy) {
        super(R.layout.adapter_order);
        this.context = context;
        this.position = position;
        this.plattype = plat;
        this.isMyOrder=ismy;
    }
    public void setHasNoData(int isHas){
        this.hasNoData=isHas;
    }
    @Override
    protected void convert(BaseViewHolder helper, OrderMorePl.OrderList item) {
        try {
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
             if(helper.getLayoutPosition()==getData().size()-1&&hasNoData==1){
                 helper.setBackgroundRes(R.id.order_item_layout,R.drawable.personal_grid_bg01);
                helper.setGone(R.id.order_item_split_layout,false);
             }else{
                 helper.setBackgroundColor(R.id.order_item_layout, Color.WHITE);
                 helper.setGone(R.id.order_item_split_layout,true);
             }
            helper.setGone(R.id.adapter_new_one_fragment_check,true);
            helper.setGone(R.id.bijia,false);

             if (item.getCpsType()!=null) {
                 try {
                     JSONObject cpsType = (JSONObject) item.getCpsType();
                     Utils.displayImage(context, cpsType.getString("logo"), helper.getView(R.id.adapter_new_one_fragment_check));
                 }catch (Exception e){

                 }
             }

//            if (isMyOrder==1){
//                helper.setGone(R.id.my_award,false);
//                helper.setGone(R.id.adapter_order_my_layout,true);
//                helper.getView(R.id.adapter_order_source_layout).setVisibility(View.INVISIBLE);
//            }else{
//                helper.setGone(R.id.adapter_order_source_layout,true);
//                helper.setGone(R.id.adapter_order_my_layout,false);
//                helper.setGone(R.id.my_award,true);
//                helper.setText(R.id.my_award, "我的奖励: ¥"+item.getCommission());
//            }

            if (isRate.equals("1")){
                helper.setGone(R.id.my_award_layout,false);
                if (isMyOrder==1) {//我的订单
                    helper.setGone(R.id.adapter_order_my_layout, true);
                    helper.setGone(R.id.adapter_order_team_layout, false);
                } else {//团队订单
                    helper.setGone(R.id.adapter_order_my_layout, false);
                    helper.setGone(R.id.adapter_order_team_layout, true);
                }
            }else{
                helper.setGone(R.id.my_award_layout,true);
                helper.setGone(R.id.adapter_order_team_layout,false);
                helper.setGone(R.id.adapter_order_my_layout, false);
                if(isMyOrder==1){
                    helper.setTextColor(R.id.my_award_title,Color.parseColor("#F10606"));
                    helper.setTextColor(R.id.my_award,Color.parseColor("#F10606"));
                    helper.setText(R.id.my_award_title,"预估佣金  ¥ ");
                    helper.setText(R.id.my_award,item.getCommission());
                }else{
                    helper.setTextColor(R.id.my_award_title,Color.parseColor("#FF5000"));
                    helper.setTextColor(R.id.my_award,Color.parseColor("#FF5000"));
                    helper.setText(R.id.my_award_title,"我的奖励  ¥ ");
                    helper.setText(R.id.my_award,item.getCommission());
                }
            }

            //订单来源
            if (isMyOrder==1) {
                helper.getView(R.id.adapter_order_source_layout).setVisibility(View.INVISIBLE);
            }else {
                helper.setGone(R.id.adapter_order_source_layout,true);
            }

            Utils.displayImageRounded(context, item.getThumbnailUrl(), helper.getView(R.id.adapter_order_image),10);
            helper.setText(R.id.adapter_order_title, item.getGoodsName());
            helper.setText(R.id.adapter_order_id, item.getOrderSn());
            helper.setText(R.id.adapter_order_payment, item.getOrderAmount());
            helper.setText(R.id.adapter_order_my_estimate, item.getCommission());
            helper.setText(R.id.goodsnum, "共"+item.getGoodsQuantity()+"件商品 合计:");
            helper.setText(R.id.adapter_order_source, "订单来源: "+item.getSourceName());
            helper.setText(R.id.adapter_order_settlement, item.getAgentCommission());
            helper.setText(R.id.adapter_order_settlement_estimate, item.getCommissionRate());
            helper.setText(R.id.adapter_order_my_settlement_estimate, item.getCommissionRate());
            helper.setText(R.id.adapter_order_estimate, item.getCommission());
            helper.setText(R.id.adapter_order_creation_time, "创建时间:  "+item.getOrderTime());
            helper.setText(R.id.adapter_order_status_text, item.getOrderStatusName());
            helper.setGone(R.id.adapter_daozhang_source, false);
            switch (position) {
                case 0:// 全部
                    switch (item.getOrderStatusName()) {
                        case "已付款":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_pink);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            break;
                        case "已收货":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_pink);
                            helper.setText(R.id.adapter_order_settlement_time, "收货时间: "+item.getReceiveTime());
                            helper.setGone(R.id.adapter_order_settlement_time, true);
                            helper.setGone(R.id.adapter_daozhang_source, true);
                            helper.setText(R.id.adapter_daozhang_source,"预计到账："+item.getAccountTime());
                            break;
                        case "已结算":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_yellow);
                            helper.setText(R.id.adapter_order_settlement_time, "结算时间: "+item.getSettleTime());
                            helper.setGone(R.id.adapter_order_settlement_time, true);
                            break;
                        case "已失效":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_gray);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            break;
                        case "已退款":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_gray);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            break;
                    }
                    break;
                case 1:// 已付款
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_pink);
                    helper.setGone(R.id.adapter_order_settlement_time, false);
                    break;
                case 2:// 已收货
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_pink);
                    helper.setText(R.id.adapter_order_settlement_time, "收货时间: "+item.getReceiveTime());
                    helper.setGone(R.id.adapter_order_settlement_time, true);
                    helper.setGone(R.id.adapter_daozhang_source, true);
                    helper.setText(R.id.adapter_daozhang_source,"预计到账："+item.getAccountTime());
                    break;
                case 3:// 已结算
                    helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_yellow);
                    helper.setText(R.id.adapter_order_settlement_time, "结算时间: "+item.getSettleTime());
                    helper.setGone(R.id.adapter_order_settlement_time, true);
                    break;
                case 4:// 已退款
                    switch (item.getOrderStatusName()) {
                        case "已失效":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_gray);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            break;
                        case "已退款":
                            helper.setBackgroundRes(R.id.adapter_order_status, R.drawable.order_status_background_style_gray);
                            helper.setGone(R.id.adapter_order_settlement_time, false);
                            break;
                    }
                    break;
            }
            if (item.getSettleTime()!=null&&item.getSettleTime().equals("")){
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
        void onItemClick( OrderMorePl.OrderList data, int type);
    }

    public void setIsRate(String rate){
        this.isRate=rate;
    }
}
