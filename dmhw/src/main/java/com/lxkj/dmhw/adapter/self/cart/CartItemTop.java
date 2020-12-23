package com.lxkj.dmhw.adapter.self.cart;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.CartResult;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/9/20.
 */

public class CartItemTop implements OrderContent {
    private Handler handler;
    private CartResult cartResult;
    private boolean isCheckAll=true;
    public CartItemTop(CartResult cartResult,Handler handler) {
        this.handler = handler;
        this.cartResult = cartResult;
    }
    public void setCheckAll(boolean checkAll) {
        isCheckAll = checkAll;
    }

    public boolean isCheckAll() {
        return isCheckAll;
    }
    @Override
    public int getLayout() {
        return R.layout.child_cart_top;
    }

    public CartResult getCartResult() {
        return cartResult;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater)
    {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvHeadtitle.setText(cartResult.getWarehouseName());
        holder.cbCheckitemall.setChecked(isCheckAll);
        if(cartResult.isIfCanReceiveCoupon()){
            holder.tv_coupon_get.setVisibility(View.VISIBLE);
            holder.tv_coupon_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击领券
                    if (ButtonUtil.isFastDoubleClick(R.id.tv_coupon_get)) {
                        ToastUtil.show(context, R.string.tip_btn_fast);
                        return;
                    }

                    Message msg=handler.obtainMessage();
                    msg.what=6;
                    msg.arg1=Integer.parseInt(cartResult.getDeliveryType());
                    handler.sendMessage(msg);
                }
            });
        }else {
            holder.tv_coupon_get.setVisibility(View.GONE);
        }
        final ViewHolder finalHolder=holder;
        holder.cbCheckitemall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckAll=!isCheckAll;
                if(isCheckAll){//反选
                    finalHolder.cbCheckitemall.setChecked(false);
                }else{//全选
                    finalHolder.cbCheckitemall.setChecked(true);
                }
                Message msg=handler.obtainMessage();
                msg.what=4;
                msg.obj=isCheckAll;
                msg.arg1=Integer.parseInt(cartResult.getDeliveryType());
                handler.sendMessage(msg);

            }
        });
        return convertView;
    }

    public Integer getTotal() {
        int sumcount = 0;
        if (!"0".equals(cartResult.getDeliveryType())){
            for (TradeGoodsCar cart : cartResult.getShopCarDtos()) {
                if ( cart.isIfPickOn()) {
                    sumcount += cart.getNum();
                }
            }
        }
        return sumcount;
    }

    public Double getAllTaxFee() {
        double fee = 0;
        if (!"0".equals(cartResult.getDeliveryType())) {
            for (TradeGoodsCar cart : cartResult.getShopCarDtos()) {
                if (cart.isIfPickOn() && cart.getTaxRate() > 0) {
                    fee += cart.getTaxRate() * cart.getAppPrice() * cart.getNum();
                }
            }
        }
        return fee;
    }

    public Double getAllProductFee() {
        double fee = 0;
        if (!"0".equals(cartResult.getDeliveryType())) {
            for (TradeGoodsCar cart : cartResult.getShopCarDtos()) {
                if (cart.isIfPickOn()) {
                    fee += cart.getAppPrice() * cart.getNum();
                }
            }
        }
        return fee;
    }

    public Double getAllFee() {
        return getAllProductFee() + getAllTaxFee();
    }


    static class ViewHolder {
        @BindView(R.id.cb_checkitemall)
        CheckBox cbCheckitemall;
        @BindView(R.id.tv_headtitle)
        TextView tvHeadtitle;
        @BindView(R.id.tv_coupon_get)
        TextView tv_coupon_get;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
