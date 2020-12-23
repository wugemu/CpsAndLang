package com.lxkj.dmhw.adapter.self.cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlang.widget.RoundedImageView;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.CartResult;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.ButtonUtil2;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/9/20.
 */

public class CartItemGoods implements OrderContent, ConfirmOKI {
    private CartResult cartResult;
    private TradeGoodsCar tradeGoodsCar;
    private Handler handler;
//    private boolean isRefershImage;//是否刷新图片
    private int type;//1=玩主，0=玩客
    private Map<Long, ViewHolder> holderMap;
    public CartItemGoods(CartResult cartResult, TradeGoodsCar tradeGoodsCar, Handler handler) {
        this.cartResult = cartResult;
        this.tradeGoodsCar = tradeGoodsCar;
        this.handler = handler;
        holderMap = new HashMap<>();
    }

    public TradeGoodsCar getTradeGoodsCar() {
        return tradeGoodsCar;
    }

    public String getDeliveryType() {
        return cartResult.getDeliveryType();
    }

//    public void setRefershImage(boolean refershImage) {
//        isRefershImage = refershImage;
//    }

    public CartResult getCartResult() {
        return cartResult;
    }

    //判断是否选中，无效商品算选中
    public boolean isCheck() {
        if (tradeGoodsCar.getStockCnt() <= 0 || "0".equals(cartResult.getDeliveryType()) || tradeGoodsCar.isIfPickOn()) {
            return true;
        }
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.child_cart_goods;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public View getView(final Context context, View convertView, LayoutInflater inflater) {
        if (BBCUtil.ifBillVip(context)) {
            type = 1;
        } else {
            type = 0;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int goodsW= (int) context.getResources().getDimension(R.dimen.dp_90);
//        ImageLoadUtils.doLoadImageRound(holder.ivProductImg,tradeGoodsCar.getImgUrl(),goodsW,goodsW,context.getResources().getDimension(R.dimen.dp7));
        ImageLoadUtils.doLoadImageRound(holder.ivProductImg,tradeGoodsCar.getImgUrl(),context.getResources().getDimension(R.dimen.dp_7),R.drawable.bg_rect_grey_7dp2);
//        ImageLoadUtils.doLoadImageUrl(holder.ivProductImg, tradeGoodsCar.getImgUrl());
//        GlideUtil.getInstance().displayRoundImg(context,tradeGoodsCar.getImgUrl(),holder.ivProductImg,context.getResources().getDimension(R.dimen.dp7));

        //测试
//        tradeGoodsCar.setIfLivePrice(1);

        if(tradeGoodsCar.getIfLivePrice()==1){
            //直播中
            holder.iv_zb_tag_top2.setVisibility(View.VISIBLE);
            GlideUtil.getInstance().displayLocGif(context,R.mipmap.tag_live,holder.iv_zb_tag_top2);
        }else {
            holder.iv_zb_tag_top2.setVisibility(View.GONE);
        }

        if (type == 1) {//玩主玩客
            holder.tvDiscount.setText("已省" + BBCUtil.getDoubleFormat(tradeGoodsCar.getProfit()) + "元");
            holder.tvDiscount.setVisibility(View.VISIBLE);
            if(tradeGoodsCar.getProfit()<=0){
                holder.tvDiscount.setVisibility(View.GONE);
            }
        } else {
            holder.tvDiscount.setText("");
            holder.tvDiscount.setVisibility(View.GONE);
        }
        if (!BBCUtil.isEmpty(tradeGoodsCar.getCouponPolicyName())) {//满减活动
            holder.llPolicy.setVisibility(View.GONE);//单个商品的满减 不要了 修改成总的优惠活动
            holder.tvPolicyDesc.setText(tradeGoodsCar.getCouponPolicyName());
        } else {
            holder.llPolicy.setVisibility(View.GONE);
        }
        holder.rbCheckone.setChecked(tradeGoodsCar.isIfPickOn());

        holder.llReturnMoney.setVisibility(View.GONE);
        holder.llReturnMoney.setOnClickListener(null);

        holder.tv_inbuy_deadline.setTag(tradeGoodsCar.getCartId());
        if ("0".equals(cartResult.getDeliveryType())) {//失效商品
            holder.rl_coudan.setVisibility(View.GONE);
            holder.llPolicy.setVisibility(View.GONE);
            holder.tvDiscount.setVisibility(View.GONE);
            holder.llAddsub.setVisibility(View.GONE);
            holder.rbCheckone.setVisibility(View.GONE);
            holder.ll_checkone.setVisibility(View.GONE);
            holder.tvInvalid.setVisibility(View.VISIBLE);
            holder.tvTag.setVisibility(View.GONE);
            holder.ivTaxArrow.setVisibility(View.GONE);
            holder.ivProductStatus.setVisibility(View.VISIBLE);
            holder.viewShadow.setVisibility(View.VISIBLE);
            holder.tvTaxFee.setVisibility(View.GONE);
            if (tradeGoodsCar.isSellOut()) {//已售罄
                holder.ivProductStatus.setImageResource(R.mipmap.cart_is_empty);
            }
            if (tradeGoodsCar.isStatus()) {//已下架
                holder.ivProductStatus.setImageResource(R.mipmap.cart_is_down);
            }
            holder.tvProductName.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
            holder.tvPrice.getPaint().setFakeBoldText(false);

            holderMap.remove(tradeGoodsCar.getCartId());
            holder.ll_inbuy_tag.setVisibility(View.GONE);
        } else {//正常商品
            if(tradeGoodsCar.getReachState()!=0&&!BBCUtil.isEmpty(tradeGoodsCar.getCouponPolicyTag())){
                //有活动
                if(tradeGoodsCar.getReachState()==1){
                    holder.tv_coudan_right.setText("去凑单");
                }else {
                    holder.tv_coudan_right.setText("再逛逛");
                }
                holder.rl_coudan.setVisibility(View.VISIBLE);
                holder.tv_coudan_tag.setText(tradeGoodsCar.getCouponPolicyTag());
                holder.tv_coudan_title.setText(tradeGoodsCar.getReachTitle());
                holder.rl_coudan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去凑单
                        if (ButtonUtil.isFastDoubleClick(R.id.rl_coudan)) {
                            ToastUtil.show(context, R.string.tip_btn_fast);
                            return;
                        }

//                        Intent intent = new Intent(context, ProductListActivity.class);
//                        intent.putExtra("couponPolicyId", tradeGoodsCar.getCouponPolicyId());
//                        intent.putExtra("title", tradeGoodsCar.getCouponPolicyName());
//                        ActivityUtil.getInstance().start((BaseLangActivity)context, intent);
                    }
                });
            }else {
                holder.rl_coudan.setVisibility(View.GONE);
            }
            if(tradeGoodsCar.getReachState()!=0){
                //有活动
                holder.line_coudan_top.setVisibility(View.VISIBLE);
                holder.line_coudan_bottom.setVisibility(View.VISIBLE);
                if(tradeGoodsCar.isIfShowLine()){
                    //最后一个活动商品
                    holder.line_coudan_bottom.setVisibility(View.GONE);
                }
            }else {
                holder.line_coudan_top.setVisibility(View.INVISIBLE);
                holder.line_coudan_bottom.setVisibility(View.GONE);
            }
            holder.tvProductName.setTextColor(context.getResources().getColor(R.color.colorBlackText));
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
            holder.tvPrice.getPaint().setFakeBoldText(true);
            holder.viewShadow.setVisibility(View.GONE);
            holder.ivProductStatus.setVisibility(View.GONE);
            holder.llAddsub.setVisibility(View.VISIBLE);
            holder.tvSum.setText(String.valueOf(tradeGoodsCar.getNum()));
            holder.rbCheckone.setVisibility(View.VISIBLE);
            holder.ll_checkone.setVisibility(View.VISIBLE);
            holder.tvInvalid.setVisibility(View.GONE);
            if (tradeGoodsCar.getTaxRate() > 0) {//税率显示判断
                holder.tvTag.setVisibility(View.VISIBLE);
                holder.ivTaxArrow.setVisibility(View.VISIBLE);
                holder.tvTag.setText("税率" + BBCUtil.getBFB(tradeGoodsCar.getTaxRate()) + ",结算税费以提交订单时应付总额明细为准");
                holder.tvTaxFee.setText("税费预计:¥" + BBCUtil.getDoubleFormat2(tradeGoodsCar.getAppPrice() * tradeGoodsCar.getTaxRate()));
                holder.tvTaxFee.setVisibility(View.VISIBLE);
            } else {
                holder.tvTag.setVisibility(View.GONE);
                holder.ivTaxArrow.setVisibility(View.GONE);
                holder.tvTaxFee.setVisibility(View.GONE);
            }

            if (tradeGoodsCar.getNum() > 1) {
                holder.ivMinus.setImageResource(R.mipmap.cart_minus_black);
            } else {
                holder.ivMinus.setImageResource(R.mipmap.cart_minus_grey);
            }
            if (tradeGoodsCar.isIfNewUserGoodsAdd()||(tradeGoodsCar.getLimitNum() > 0 && tradeGoodsCar.getNum() >= tradeGoodsCar.getLimitNum())) {
                //新人领取商品不可加减
                holder.ivPlus.setImageResource(R.mipmap.cart_add_grey);
            } else {
                holder.ivPlus.setImageResource(R.mipmap.cart_add_black);
            }

            if (tradeGoodsCar.getWithinbuyEndTime()>0){
                holderMap.put(tradeGoodsCar.getCartId(), holder);//倒计时队列
                holder.ll_inbuy_tag.setVisibility(View.VISIBLE);
                holder.tvDiscount.setVisibility(View.GONE);
            }else{
                holderMap.remove(tradeGoodsCar.getCartId());
                holder.ll_inbuy_tag.setVisibility(View.GONE);
            }

            if (tradeGoodsCar.getReturnAmount() > 0) {//返券
                holder.llReturnMoney.setVisibility(View.VISIBLE);
                holder.tvReturnCoupon.setText(tradeGoodsCar.getReturnTitle());
                holder.llReturnMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        new CommonTipDialog((Activity) context, "返额说明", tradeGoodsCar.getReturnDesc(), null);
                    }
                });
            }

        }
        holder.llAddsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tvPrice.setText("¥" + BBCUtil.getDoubleFormat(BBCUtil.getDoubleRoundOf(tradeGoodsCar.getAppPrice())));
        holder.tvProductName.setText(tradeGoodsCar.getGoodsName());
        holder.tvUnit.setText(tradeGoodsCar.getSkuName());
        if(!BBCUtil.isEmpty(tradeGoodsCar.getReducePriceTitle())){
            //比加入时降价文案
            holder.tv_discount_price.setVisibility(View.VISIBLE);
            holder.tv_discount_price.setText(tradeGoodsCar.getReducePriceTitle());
        }else {
            holder.tv_discount_price.setVisibility(View.GONE);
        }

        setOnListener(holder, context);

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductInfoActivity.class);
                intent.putExtra("goodsId", tradeGoodsCar.getGoodsId() + "");
                if(tradeGoodsCar.isIfNewUserGoodsAdd()){
                    //新人领取的商品
                    intent.putExtra("newGift","1");
                }
                ActivityUtil.getInstance().start((Activity) context, intent);

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmDialog((Activity) context, "确认要删除所选商品吗？", CartItemGoods.this);
            }
        });
        return convertView;
    }

    public void setOnListener(final ViewHolder holder, final Context context) {
        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //减少商品数量
                if(ButtonUtil2.isFastDoubleClick(R.id.iv_minus,1000)){
                    ToastUtil.show(context, R.string.tip_btn_fast);
                    return;
                }
                int num = tradeGoodsCar.getNum();
                if (num > 1) {
                    num--;
                    //发送减少商品的消息
                    Message msg = handler.obtainMessage();
                    msg.what = 3;
                    msg.obj = tradeGoodsCar;
                    msg.arg2 = -1;
                    handler.sendMessage(msg);
                }
                if (num <= 1) {
                    holder.tvSum.setText("1");
                    holder.ivMinus.setImageResource(R.mipmap.cart_minus_grey);
                } else {
                    holder.tvSum.setText(String.valueOf(num));
                    holder.ivMinus.setImageResource(R.mipmap.cart_minus_black);
                }


            }
        });
        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //增加商品数量
                if(ButtonUtil2.isFastDoubleClick(R.id.iv_plus,1000)){
                    ToastUtil.show(context, R.string.tip_btn_fast);
                    return;
                }
                if(tradeGoodsCar.isIfNewUserGoodsAdd()){
                    //新人领取商品 不可以加
                    return;
                }
                int num = tradeGoodsCar.getNum();
                num++;
                if (num > tradeGoodsCar.getStockCnt())//数量大于库存 显示库存不足
                {
                    ToastUtil.show(context, "商品库存仅剩" + tradeGoodsCar.getStockCnt() + "件");
                    holder.ivPlus.setImageResource(R.mipmap.cart_add_grey);
                    return;
                } else if (tradeGoodsCar.getLimitNum() > 0 && num > tradeGoodsCar.getLimitNum()) {
                    holder.ivPlus.setImageResource(R.mipmap.cart_add_grey);
                    ToastUtil.show(context, "商品单笔限购" + tradeGoodsCar.getLimitNum() + "件");
                    return;
                }
                holder.ivMinus.setImageResource(R.mipmap.cart_minus_black);
                holder.ivPlus.setImageResource(R.mipmap.cart_add_black);
                Message msg = handler.obtainMessage();
                msg.what = 3;
                msg.arg2 = 1;
                msg.obj = tradeGoodsCar;
                handler.sendMessage(msg);

            }
        });
        final CheckBox checkOne = holder.rbCheckone;
        holder.rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"0".equals(cartResult.getDeliveryType())) {
                    tradeGoodsCar.setIfPickOn(!tradeGoodsCar.isIfPickOn());
                    checkOne.setChecked(tradeGoodsCar.isIfPickOn());
                    if (checkOne.isChecked()) {
                        //检测是否全选
                        Message msg = handler.obtainMessage();
                        msg.what = 5;
                        msg.arg1 = Integer.parseInt(cartResult.getDeliveryType());
                        msg.obj = true;
                        Bundle bundle=new Bundle();
                        bundle.putString("cartId",tradeGoodsCar.getCartId()+"");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } else {
                        //取消全选
                        Message msg = handler.obtainMessage();
                        msg.what = 5;
                        msg.arg1 = Integer.parseInt(cartResult.getDeliveryType());
                        msg.obj = false;
                        Bundle bundle=new Bundle();
                        bundle.putString("cartId",tradeGoodsCar.getCartId()+"");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
//                    isRefershImage = false;
                }
            }
        });
        checkOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tradeGoodsCar.setIfPickOn(!tradeGoodsCar.isIfPickOn());
                checkOne.setChecked(tradeGoodsCar.isIfPickOn());
                if (checkOne.isChecked()) {
                    //检测是否全选
                    Message msg = handler.obtainMessage();
                    msg.what = 5;
                    msg.arg1 = Integer.parseInt(cartResult.getDeliveryType());
                    msg.obj = true;
                    Bundle bundle=new Bundle();
                    bundle.putString("cartId",tradeGoodsCar.getCartId()+"");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } else {
                    //取消全选
                    Message msg = handler.obtainMessage();
                    msg.what = 5;
                    msg.arg1 = Integer.parseInt(cartResult.getDeliveryType());
                    msg.obj = false;
                    Bundle bundle=new Bundle();
                    bundle.putString("cartId",tradeGoodsCar.getCartId()+"");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
//                isRefershImage = false;
//                notifyDataSetChanged();
//                handler.sendEmptyMessage(2);
            }
        });

//        holder.rlCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                tradeGoodsCar.setCheck(!tradeGoodsCar.isCheck());
//                checkOne.setChecked(tradeGoodsCar.isCheck());
//                if (checkOne.isChecked()) {
//                    //检测是否全选
//                    Message msg=handler.obtainMessage();
//                    msg.what=5;
//                    msg.arg1=cartResult.getDeliveryType();
//                    msg.obj=true;
//                    handler.sendMessage(msg);
//                } else {
//                    //取消全选
//                    Message msg=handler.obtainMessage();
//                    msg.what=5;
//                    msg.arg1=cartResult.getDeliveryType();
//                    msg.obj=false;
//                    handler.sendMessage(msg);
//                }
//                isRefershImage=false;
////                notifyDataSetChanged();
////                handler.sendEmptyMessage(2);
//            }
//        });




    }

    public void setTime(Context context) {
       if (BBCUtil.isBigVer121(context)){
           if (holderMap.size() > 0) {
               ViewHolder holder = holderMap.get(tradeGoodsCar.getCartId());
               if (holder != null) {
                   if (tradeGoodsCar.getCartId()==(Long)holder.tv_inbuy_deadline.getTag()) {
                       if (tradeGoodsCar.getWithinbuyEndTime()>0){
                           Map<String, String> time = TimeCalculate.getTimeMap(MyApplication.NOW_TIME,tradeGoodsCar.getWithinbuyEndTime());
//                           String timeStr=TimeCalculate.getTime3(SuDianApp.NOW_TIME,tradeGoodsCar.getWithinbuyEndTime());
                           if (time != null && time.size() > 0) {
                               String hour = time.get("hour");
                               int hourNum = 0;
                               if (!BBCUtil.isEmpty(hour)) {
                                   hourNum = Integer.parseInt(hour);
                               }
                               holder.ll_inbuy_tag.setVisibility(View.VISIBLE);
                               if (hourNum < 24) {
                                   holder.tv_inbuy_deadline.setText("距离结束还剩"+TimeCalculate.getTime3(MyApplication.NOW_TIME,tradeGoodsCar.getWithinbuyEndTime()));
                               }else{
                                   holder.tv_inbuy_deadline.setText("距离结束还"+TimeCalculate.getTime(MyApplication.NOW_TIME,tradeGoodsCar.getWithinbuyEndTime()));
                               }
                           }else{
                               holderMap.remove(tradeGoodsCar.getCartId());
                               holder.ll_inbuy_tag.setVisibility(View.GONE);
                               if (handler!=null){
                                   handler.sendEmptyMessage(11);
                               }
                           }
                       }else{
                           holderMap.remove(tradeGoodsCar.getCartId());
                           holder.ll_inbuy_tag.setVisibility(View.GONE);
                           if (handler!=null){
                               handler.sendEmptyMessage(11);
                           }
                       }

                   }
               }
           }
       }
    }

    @Override
    public void executeOk() {
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.obj = tradeGoodsCar.getCartId();
        handler.sendMessage(msg);

    }

    @Override
    public void executeCancel() {

    }

    static class ViewHolder {
        @BindView(R.id.ll_checkone)
        LinearLayout ll_checkone;
        @BindView(R.id.rb_checkone)
        CheckBox rbCheckone;
        @BindView(R.id.line_coudan_top)
        View line_coudan_top;
        @BindView(R.id.line_coudan_bottom)
        View line_coudan_bottom;
//        @BindView(R.id.cb_incalid)
//        CheckBox cbInvalid;
        @BindView(R.id.tv_invalid)
        TextView tvInvalid;
        @BindView(R.id.iv_tax_arrow)
        ImageView ivTaxArrow;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;
        @BindView(R.id.view_shadow)
        View viewShadow;
        @BindView(R.id.iv_product_status)
        ImageView ivProductStatus;
        @BindView(R.id.tv_product_sale_price)
        TextView tvProductName;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        @BindView(R.id.tv_tax_fee)
        TextView tvTaxFee;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_discount)
        TextView tvDiscount;
        @BindView(R.id.iv_minus)
        ImageView ivMinus;
        @BindView(R.id.tv_sum)
        TextView tvSum;
        @BindView(R.id.iv_plus)
        ImageView ivPlus;
        @BindView(R.id.ll_addsub)
        LinearLayout llAddsub;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.ll_policy)
        LinearLayout llPolicy;
        @BindView(R.id.tv_policy_desc)
        TextView tvPolicyDesc;
        @BindView(R.id.ll_return_money)
        LinearLayout llReturnMoney;
        @BindView(R.id.tv_return_coupon)
        TextView tvReturnCoupon;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.rl_check)
        LinearLayout rlCheck;
        @BindView(R.id.ll_inbuy_tag)
        LinearLayout ll_inbuy_tag;
        @BindView(R.id.tv_inbuy_deadline)
        TextView tv_inbuy_deadline;
        @BindView(R.id.iv_zb_tag_top2)
        ImageView iv_zb_tag_top2;

        @BindView(R.id.rl_coudan)
        RelativeLayout rl_coudan;
        @BindView(R.id.tv_coudan_tag)
        TextView tv_coudan_tag;
        @BindView(R.id.tv_coudan_title)
        TextView tv_coudan_title;
        @BindView(R.id.tv_discount_price)
        TextView tv_discount_price;
        @BindView(R.id.tv_coudan_right)
        TextView tv_coudan_right;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
