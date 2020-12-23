package com.lxkj.dmhw.adapter.self.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.activity.self.order.ApplyRefundActivity2;
import com.lxkj.dmhw.activity.self.order.OrderDetialActivity;
import com.lxkj.dmhw.activity.self.order.RefundDetail2Activity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.GroupOrder;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.OrderGoodBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.CenterAlignImageSpan;
import com.lxkj.dmhw.widget.dialog.CommonTipDialogNew;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/10/8.
 */

public class OrderDetialGoods implements OrderContent {
    private OrderGoodBean goodBean;
    private OrderBean orderBean;
    private int orderType;

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public int getLayout() {
        return R.layout.adapter_order_detial_goods;
    }

    public void setGoodBean(OrderGoodBean goodBean) {
        this.goodBean = goodBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public OrderGoodBean getGoodBean() {
        return goodBean;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public View getView(final Context context, View convertView, final LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if ((Double)orderBean.getIfVipOrder()>0) {
            holder.tvWzProfit.setText("(已省" + BBCUtil.getDoubleFormat(goodBean.getGoodsAmount()) + "元)");
            holder.tvWzProfit.setVisibility(View.VISIBLE);
            if(goodBean.getGoodsAmount()<=0){
                //是团订单 已省金额为0元时隐藏
                holder.tvWzProfit.setVisibility(View.GONE);
            }
        } else {
            holder.tvWzProfit.setVisibility(View.GONE);
        }
//        if (goodBean.getGoodsTax() > 0) {
//            holder.tvTaxFee.setText("税率：" + BBCUtil.getBFB(goodBean.getGoodsTax()));
//            holder.tvTaxFee.setVisibility(View.VISIBLE);
//        } else {
//            holder.tvTaxFee.setVisibility(View.GONE);
//        }
        String nametip = goodBean.getUnit();
        if (goodBean.getGoodsTax() > 0) {
            if(!BBCUtil.isEmpty(nametip)){
                nametip = nametip + "\u3000";
            }
            nametip = nametip + "税率：" + BBCUtil.getBFB(goodBean.getGoodsTax());
        }
        holder.tvUnit.setText(nametip);

        if (orderBean.getTradeProperty()==1){
            //对礼包订单的处理
            if ("2".equals(goodBean.getRefundState())) {
                holder.btnReturn.setVisibility(View.VISIBLE);
                holder.btnReturn.setText("退款成功");
            }else{
                holder.btnReturn.setVisibility(View.GONE);
            }
        }else{
            if ("0".equals(goodBean.getRefundState())) {
                //V1.1.6 申请售后按钮上移商品后面
                if(orderType==1&&(orderBean.getTradeStatus()==6||orderBean.getTradeStatus()==7)){
                    //自己的订单 且 待收货 交易完成
                    holder.btnReturn.setVisibility(View.VISIBLE);
                    holder.btnReturn.setText("申请售后");
                }else {
                    holder.btnReturn.setVisibility(View.GONE);
                }
            } else if ("1".equals(goodBean.getRefundState())) {
                holder.btnReturn.setVisibility(View.VISIBLE);
                holder.btnReturn.setText("等待退款");
            } else if ("2".equals(goodBean.getRefundState())) {
                holder.btnReturn.setVisibility(View.VISIBLE);
                holder.btnReturn.setText("退款成功");
            } else if ("3".equals(goodBean.getRefundState())) {
                holder.btnReturn.setVisibility(View.VISIBLE);
                holder.btnReturn.setText("退款驳回");
            } else if ("4".equals(goodBean.getRefundState())) {
                holder.btnReturn.setVisibility(View.VISIBLE);
                holder.btnReturn.setText("退款关闭");
            }
        }
        holder.llReturnMoney.setVisibility(View.GONE);
        if (goodBean.getReturnAmount() > 0) {
            if(orderBean.getTradeProperty() == 1){
                //礼包订单 返券
                holder.tv_return_tip.setText("返券");
                holder.tvReturnCoupon.setText(goodBean.getReturnTitle());
                holder.llReturnMoney.setVisibility(View.VISIBLE);
                holder.llReturnMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CommonTipDialogNew((Activity) context, "返券说明", goodBean.getReturnDesc(), null);
                    }
                });
            }else if(orderBean.getTradeProperty() == 0){
                //普通订单 反额
                holder.tv_return_tip.setText("返额");
                holder.tvReturnCoupon.setText(goodBean.getReturnTitle());
                holder.llReturnMoney.setVisibility(View.VISIBLE);
                holder.llReturnMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CommonTipDialogNew((Activity) context, "返额说明", goodBean.getReturnDesc(), null);
                    }
                });
            }

        }

        holder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType==1&&!BBCUtil.isEmpty(goodBean.getRefundId())&&!"0".equals(goodBean.getRefundId())){
                    //我的订单 售后详情页
                    Intent intent = new Intent(context, RefundDetail2Activity.class);
                    intent.putExtra("id", goodBean.getRefundId());
                    ActivityUtil.getInstance().startResult((Activity) context, intent, OrderDetialActivity.REQ_REFUND);
                }else if(orderType==1&&"0".equals(goodBean.getRefundState())&&(orderBean.getTradeStatus()==6||orderBean.getTradeStatus()==7)){
                    //待收货  交易完成 点击申请售后
                    // 6：待收货 7：交易完成
                    if (orderBean!=null) {
                        if (orderBean.isIfServenAfter()) {
                            //超过7天不可以申请售后
                            ConfirmDialog confirmDialog = new ConfirmDialog((Activity)context, "此订单已超过售后期限", "", "知道了", null);
                            confirmDialog.hiddenOkBtn();
                            return;
                        }

                        if(!BBCUtil.isEmpty(orderBean.getCanNotAfterSalesTips())){
                            ConfirmDialog confirmDialog = new ConfirmDialog((Activity)context, orderBean.getCanNotAfterSalesTips(), "", "知道了", null);
                            confirmDialog.hiddenOkBtn();
                            return;
                        }
                    }
                    GroupOrder groupOrder = orderBean.getTradeGroupCreate();
                    String tipStr="";
                    if (!BBCUtil.isEmpty(orderBean.getRefundCopywriting())){
                        tipStr=orderBean.getRefundCopywriting();
                    }else if(groupOrder!=null&&!BBCUtil.isEmpty(groupOrder.getFreeGroupRefundStr())){
                        //团购订单申请售后 提示文案
                        tipStr=groupOrder.getFreeGroupRefundStr();
                    }
                    if(!BBCUtil.isEmpty(tipStr)){
                        ConfirmDialog confirmDialog = new ConfirmDialog((Activity)context, tipStr, new ConfirmOKI() {
                            @Override
                            public void executeOk() {
                                Intent intent = new Intent(context, ApplyRefundActivity2.class);
                                intent.putExtra("tradeStatus", orderBean.getTradeStatus());
                                intent.putExtra("tradeId", orderBean.getTradeId());
                                Bundle bundle = new Bundle();
                                ArrayList<OrderGoodBean> orderGoodBeanList=new ArrayList<OrderGoodBean>();
                                goodBean.setNum(goodBean.getSellCount());
                                orderGoodBeanList.add(goodBean);
                                bundle.putSerializable("orderGoodBeanList", orderGoodBeanList);
                                intent.putExtras(bundle);
                                ActivityUtil.getInstance().startResult((Activity) context, intent, OrderDetialActivity.REQ_REFUND);
                            }
                            @Override
                            public void executeCancel() {

                            }
                        });
                        if(tipStr.length()>15){
                            //礼包订单
                            confirmDialog.textLeft();
                        }
                    }else {
                        Intent intent = new Intent(context, ApplyRefundActivity2.class);
                        intent.putExtra("tradeStatus", orderBean.getTradeStatus());
                        intent.putExtra("tradeId", orderBean.getTradeId());
                        Bundle bundle = new Bundle();
                        ArrayList<OrderGoodBean> orderGoodBeanList=new ArrayList<OrderGoodBean>();
                        goodBean.setNum(goodBean.getSellCount());
                        orderGoodBeanList.add(goodBean);
                        bundle.putSerializable("orderGoodBeanList", orderGoodBeanList);
                        intent.putExtras(bundle);
                        ActivityUtil.getInstance().startResult((Activity) context, intent, OrderDetialActivity.REQ_REFUND);
                    }
                }

            }
        });
        holder.tvNum.setText("x" + goodBean.getSellCount());

        if(orderBean.getGroupType()==2&&orderBean.isIfCreateGroup()) {
            //团长免费领
            SpannableString spannableString = new SpannableString("  " + goodBean.getTradeName());
            Drawable d = context.getResources().getDrawable(R.mipmap.group_free);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(spannableString);
        }else if(orderBean.isIfBackPrice()&&orderBean.isIfCreateGroup()){
            //团长开团返额
            SpannableString spannableString = new SpannableString("  " + goodBean.getTradeName());
            Drawable d = context.getResources().getDrawable(R.mipmap.icon_group_backfee);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(spannableString);
        }else if (orderBean.getTradeGroupCreateId()>0){//拼团标
            SpannableString spannableString = new SpannableString("  " + goodBean.getTradeName());
            Drawable d = context.getResources().getDrawable(R.mipmap.group_tag);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_22);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_13);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(spannableString);
        }else{
            holder.tvProductName.setText(goodBean.getTradeName());
        }
//        if(!BBCUtil.isEmpty(goodBean.getUnit())){
//            holder.tvUnit.setText(goodBean.getUnit()+"\u3000"+);
//            holder.tvUnit.setVisibility(View.VISIBLE);
//        }else {
//            holder.tvUnit.setVisibility(View.GONE);
//        }
        holder.tvPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getSellPrice()));
        ImageLoadUtils.doLoadImageUrl(holder.ivProductImg, goodBean.getGoodsImgUrl());
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderBean != null) {
                    Intent intent;
                    intent = new Intent(context, ProductInfoActivity.class);
                    intent.putExtra("withinbuyId",goodBean.getWithinBuyId());
                    intent.putExtra("goodsId", goodBean.getGoodsId());
                    ActivityUtil.getInstance().start((Activity) context, intent);
                }
            }
        });
       RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) holder.llReturnMoney.getLayoutParams();
        if (orderType==1&&!BBCUtil.isEmpty(goodBean.getDelayedReminder())){
            //自己的订单才显示慢的提示
            params.addRule(RelativeLayout.BELOW,R.id.tv_tip);
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText(goodBean.getDelayedReminder());
        }else{
            params.addRule(RelativeLayout.BELOW,R.id.iv_product_img);
            holder.tvTip.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;
        @BindView(R.id.tv_return_coupon)
        TextView tvReturnCoupon;
        @BindView(R.id.tv_return_tip)
        TextView tv_return_tip;
        @BindView(R.id.ll_return_money)
        LinearLayout llReturnMoney;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
//        @BindView(R.id.z)
//        TextView tvTaxFee;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_wz_profit)
        TextView tvWzProfit;
        @BindView(R.id.btn_return)
        TextView btnReturn;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
