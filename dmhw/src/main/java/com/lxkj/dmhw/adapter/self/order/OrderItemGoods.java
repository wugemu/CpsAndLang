package com.lxkj.dmhw.adapter.self.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.order.AnonymousOrderActivity;
import com.lxkj.dmhw.activity.self.order.OrderDetialActivity;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.OrderGoodBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.CenterAlignImageSpan;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 1 on 2017/4/18.
 */

public class OrderItemGoods implements OrderContent {
    private OrderGoodBean goodSku;
    private OrderBean orderBean;
    private int hideCount;//隐藏的数量
    private boolean isLast;//当前仓最后一个

    public OrderItemGoods(OrderBean orderBean, OrderGoodBean goodSku, int hideCount) {
        this.goodSku = goodSku;
        this.orderBean = orderBean;
        this.hideCount = hideCount;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    @Override
    public int getLayout() {
        return R.layout.listview_order_item_item;
    }

    @Override
    public boolean isClickable() {
        return true;
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
        holder.tv_refund_state.setVisibility(View.GONE);
        if (!orderBean.isIfHideBuy()) {
            //非匿名信息
            holder.ll_order_good.setVisibility(View.VISIBLE);
            holder.ll_order_hidegood.setVisibility(View.GONE);
            ImageLoadUtils.doLoadImageUrl(holder.iv_goods_image, goodSku.getGoodsImgUrl());

            if(orderBean.getTradeGroupCreateId()>0&&orderBean.getGroupType()==2&&orderBean.isIfCreateGroup()) {
                //团长免费领
                SpannableString spannableString = new SpannableString("  " + goodSku.getTradeName());
                Drawable d = context.getResources().getDrawable(R.mipmap.group_free);
                int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_38);//27
                int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
                d.setBounds(0, 0, iconwidth, iconHeight);
                //居中对齐imageSpan
                CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
                spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
                holder.tv_orderitemitem_name.setText(spannableString);
            }else if(orderBean.getTradeGroupCreateId()>0&&orderBean.isIfBackPrice()&&orderBean.isIfCreateGroup()){
                //团长开团返额
                SpannableString spannableString = new SpannableString("  " + goodSku.getTradeName());
                Drawable d = context.getResources().getDrawable(R.mipmap.icon_group_backfee);
                int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_27);//27
                int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
                d.setBounds(0, 0, iconwidth, iconHeight);
                //居中对齐imageSpan
                CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
                spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
                holder.tv_orderitemitem_name.setText(spannableString);
            }else if (orderBean.getTradeGroupCreateId()>0){//拼团标
                SpannableString spannableString = new SpannableString("  " + goodSku.getTradeName());
                Drawable d = context.getResources().getDrawable(R.mipmap.group_tag);
                int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_22);//27
                int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_13);//15
                d.setBounds(0, 0, iconwidth, iconHeight);
                //居中对齐imageSpan
                CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
                spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
                holder.tv_orderitemitem_name.setText(spannableString);
            }else{
                holder.tv_orderitemitem_name.setText(goodSku.getTradeName());
            }

            String nametip = goodSku.getUnit();
            if (goodSku.getGoodsTax() > 0) {
                if(!BBCUtil.isEmpty(nametip)){
                    nametip = nametip + "  ";
                }
                nametip = nametip + "税率：" + BBCUtil.getBFB(goodSku.getGoodsTax());
            }
            holder.tv_orderitemitem_nametip.setText(nametip);
            holder.tv_goodprice.setText("¥" + BBCUtil.getDoubleFormat(goodSku.getSellPrice()));
            if (Boolean.parseBoolean(orderBean.getIfVipOrder().toString())) {
                //是玩主
                holder.tv_ysprice.setVisibility(View.VISIBLE);
                holder.tv_ysprice.setText("（已省" + BBCUtil.getDoubleFormat(goodSku.getGoodsAmount()) + "元）");
                if(goodSku.getGoodsAmount()<=0){
                    //是团订单 已省金额为0元时隐藏
                    holder.tv_ysprice.setVisibility(View.GONE);
                }
            } else {
                holder.tv_ysprice.setVisibility(View.GONE);
            }
            holder.tv_orderitemitem_num.setText("X" + goodSku.getSellCount());
            if(!BBCUtil.isEmpty(goodSku.getRefundState())){
                holder.tv_refund_state.setVisibility(View.VISIBLE);
                switch (Integer.parseInt(goodSku.getRefundState())){
                    case 0:
                        //正常状态
                        holder.tv_refund_state.setText("");
                        holder.tv_refund_state.setVisibility(View.GONE);
                        break;
                    case 1:
                        //申请售后中
                        holder.tv_refund_state.setText("等待退款");
                        break;
                    case 2:
                        //退款成功
                        holder.tv_refund_state.setText("退款成功");
                        break;
                    case 3:
                        //退款驳回
                        holder.tv_refund_state.setText("退款驳回");
                        break;
                    case 4:
                        //退款关闭
                        holder.tv_refund_state.setText("退款关闭");
                        break;
                }
            }
        } else {
            //匿名
            holder.ll_order_good.setVisibility(View.GONE);
            holder.ll_order_hidegood.setVisibility(View.VISIBLE);
            if (orderBean.getTradeGroupCreateId()>0){
                //匿名团购标签显示
                holder.tv_group_tag.setVisibility(View.VISIBLE);
            }else{
                holder.tv_group_tag.setVisibility(View.GONE);
            }
            holder.tv_goods_hidenum.setText("共" + hideCount + "件商品");
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.viewLine.getLayoutParams();
        if (isLast) {
            params.setMargins(0, 0, 0, 0);
        } else {
            params.setMargins((int) context.getResources().getDimension(R.dimen.fab_margin), 0, 0, 0);
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

    static class ViewHolder {
        @BindView(R.id.ll_order_good)
        LinearLayout ll_order_good;
        @BindView(R.id.ll_order_hidegood)
        LinearLayout ll_order_hidegood;
        @BindView(R.id.iv_goods_image)
        ImageView iv_goods_image;
        @BindView(R.id.tv_orderitemitem_name)
        TextView tv_orderitemitem_name;
        @BindView(R.id.tv_orderitemitem_nametip)
        TextView tv_orderitemitem_nametip;
        @BindView(R.id.tv_goodprice)
        TextView tv_goodprice;
        @BindView(R.id.tv_ysprice)
        TextView tv_ysprice;
        @BindView(R.id.tv_orderitemitem_num)
        TextView tv_orderitemitem_num;
        @BindView(R.id.tv_goods_hidenum)
        TextView tv_goods_hidenum;
        @BindView(R.id.tv_refund_state)
        TextView tv_refund_state;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_group_tag)
        TextView tv_group_tag;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
