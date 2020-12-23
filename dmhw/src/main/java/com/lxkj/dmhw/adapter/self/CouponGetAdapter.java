package com.lxkj.dmhw.adapter.self;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

/**
 * Created by lenovo on 2018/9/22.
 */

public class CouponGetAdapter extends BaseLangAdapter<Coupon> {
    public static final int HANDLER_CODE_GET=99;
    public static final int HANDLER_CODE_USERGOHOME=100;
    private Handler handler;

    public CouponGetAdapter(Activity context, List<Coupon> data, Handler handler) {
        super(context, R.layout.adapter_coupon, data);
        this.handler = handler;
    }

    @Override
    public void convert(final BaseLangViewHolder helper, int postion, final Coupon item) {
        TextView tvScope = helper.getView(R.id.tv_coupon_scope);
        TextView tvDiscount = helper.getView(R.id.tv_discount);
        TextView tvAmount = helper.getView(R.id.tv_amount);
        TextView tvName = helper.getView(R.id.tv_coupon_name);
        TextView tvTag = helper.getView(R.id.tv_tag);
        TextView tvDeadLine = helper.getView(R.id.tv_deadline);
        Button btnUse = helper.getView(R.id.btn_use);
        ImageView ivState = helper.getView(R.id.iv_coupon_state);
        TextView tv_yuan_tag = helper.getView(R.id.tv_yuan_tag);
        TextView tv_dicount_tag = helper.getView(R.id.tv_dicount_tag);
        TextView tv_coupon_nousetip=helper.getView(R.id.tv_coupon_nousetip);
        RelativeLayout rl_coupon_content=helper.getView(R.id.rl_coupon_content);

        btnUse.setVisibility(View.VISIBLE);
        ivState.setVisibility(View.GONE);

        if (item.getState()==0){
            btnUse.setText("立即领取");
            btnUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg=handler.obtainMessage();
                    msg.what=HANDLER_CODE_GET;
                    msg.obj=item.getId();
                    handler.sendMessage(msg);
                }
            });
        }else if (item.getState()==1){
            btnUse.setText("立即使用");
            btnUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去使用
                    if(item.getIfCanUse()!=1){
                        if(!BBCUtil.isEmpty(item.getCanUseTip())) {
                            ToastUtil.show(context, item.getCanUseTip());
                        }else {
                            ToastUtil.show(context, "优惠券暂不可以使用");
                        }
                        return;
                    }
                    if(item.getUseOccasion()==0||item.getUseOccasion()==4){
                        //全场
                        if(handler!=null){
                            handler.sendEmptyMessage(HANDLER_CODE_USERGOHOME);
                        }
                    }else if(item.getUseOccasion()==1){
                        //指定商品
                    }else if(item.getUseOccasion()==2){
                        //指定分类
                    }else if(item.getUseOccasion()==5){
                        //跳转我的专柜商品列表
                    }
                }
            });
        }

        if(!BBCUtil.isEmpty(item.getCanUseTip())){
            tv_coupon_nousetip.setVisibility(View.VISIBLE);
            tv_coupon_nousetip.setText(item.getCanUseTip());
            if(!BBCUtil.isEmpty(item.getCouponTag())){
                tvScope.setVisibility(View.VISIBLE);
                rl_coupon_content.setBackgroundResource(R.mipmap.coupon_bg2);
            }else {
                tvScope.setVisibility(View.GONE);
                rl_coupon_content.setBackgroundResource(R.mipmap.coupon_bg);
            }
        }else {
            tv_coupon_nousetip.setVisibility(View.GONE);
            tvScope.setVisibility(View.VISIBLE);
            rl_coupon_content.setBackgroundResource(R.mipmap.coupon_bg);
        }

        tvAmount.setVisibility(View.VISIBLE);
        tvAmount.setText("满" + BBCUtil.getDoubleFormat(item.getAmount()) + "元可用");
        tvName.setText(item.getDisplayName());
        tvDeadLine.setText(item.getStartTime() + "-" + item.getEndTime());
        tvScope.setText(item.getCouponTag());
        TextPaint tp = tvDiscount.getPaint();
        tp.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        String discountStr;
        switch (item.getCouponCategory()) {
            case 1:
                //满减券
                tv_yuan_tag.setVisibility(View.VISIBLE);
                tv_dicount_tag.setVisibility(View.GONE);
                tvTag.setText("满减券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜满减券");
                }
                discountStr=BBCUtil.getDoubleFormat(item.getDiscount());
                tvDiscount.setText(discountStr);
                if(discountStr.length()>5){
                    tvDiscount.setTextSize(22);
                } else if(discountStr.length()>3) {
                    tvDiscount.setTextSize(28);
                }else if(discountStr.length()>2){
                    tvDiscount.setTextSize(34);
                }else{
                    tvDiscount.setTextSize(40);
                }
                break;
            case 2:
                //免税券
                tv_yuan_tag.setVisibility(View.GONE);
                tv_dicount_tag.setVisibility(View.GONE);
                tvTag.setText("免税券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜免税券");
                }
                tvDiscount.setText("免税");
                tvDiscount.setTextSize(30);
                break;
            case 3:
                //折扣券
                tv_yuan_tag.setVisibility(View.GONE);
                tv_dicount_tag.setVisibility(View.VISIBLE);
                tvTag.setText("折扣券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜折扣券");
                }
                discountStr=BBCUtil.getDoubleFormat(item.getDiscount() * 10);
                tvDiscount.setText(discountStr);
                if(discountStr.length()>3) {
                    tvDiscount.setTextSize(28);
                }else if(discountStr.length()>2){
                    tvDiscount.setTextSize(34);
                }else{
                    tvDiscount.setTextSize(40);
                }
                break;
            case 4:
                //包邮券
                tv_yuan_tag.setVisibility(View.GONE);
                tv_dicount_tag.setVisibility(View.GONE);
                tvTag.setText("包邮券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜包邮券");
                }
                tvDiscount.setText("包邮");
                tvDiscount.setTextSize(30);
                break;
            case 7:
                tv_yuan_tag.setVisibility(View.GONE);
                tv_dicount_tag.setVisibility(View.GONE);
                tvDiscount.setText("免单");
                tvDiscount.setTextSize(30);
                tvTag.setText("免单券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜免单券");
                }
                tvAmount.setText("最高限"+BBCUtil.getDoubleFormat(item.getDiscount())+"元");
                break;
            case 8:
                tvAmount.setVisibility(View.GONE);
                tv_yuan_tag.setVisibility(View.GONE);
                tv_dicount_tag.setVisibility(View.GONE);
                tvDiscount.setText("免单");
                tvDiscount.setTextSize(30);
                tvTag.setText("免单券");
                if(item.getUseOccasion()==5){
                    //专柜优惠券
                    tvTag.setText("专柜免单券");
                }
                break;
        }


    }

    public void refershData(String couponId) {
        for (Coupon coupon : data) {
            if (couponId.equals(coupon.getId() + "")) {
                coupon.setState(1);
            }
        }
        notifyDataSetChanged();
    }
}
