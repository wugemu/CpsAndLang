package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.lxkj.dmhw.presenter.CouponPresenter;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

/**
 * Created by lenovo on 2018/9/22.
 */

public class CouponAdapter extends BaseLangAdapter<Coupon> {
    private int type;//0=未使用，1=已使用，2=已过期  选择优惠券type 3=可使用，4=不可使用
    private int selectPostion=-1;//选择优惠券使用
    private CouponPresenter presenter;
    public CouponAdapter(Activity context, List<Coupon> data,CouponPresenter presenter) {
        super(context, R.layout.adapter_coupon, data);
        this.presenter=presenter;
    }
    public CouponAdapter(Activity context, List<Coupon> data,int type) {
        super(context, R.layout.adapter_coupon, data);
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, final Coupon item) {
        TextView tvScope=helper.getView(R.id.tv_coupon_scope);
        TextView tvDiscount=helper.getView(R.id.tv_discount);
        TextView tvAmount=helper.getView(R.id.tv_amount);
        TextView tvName=helper.getView(R.id.tv_coupon_name);
        TextView tvTag=helper.getView(R.id.tv_tag);
        TextView tvDeadLine=helper.getView(R.id.tv_deadline);
        Button btnUse=helper.getView(R.id.btn_use);
        ImageView ivState=helper.getView(R.id.iv_coupon_state);
        TextView tv_yuan_tag=helper.getView(R.id.tv_yuan_tag);
        TextView tv_dicount_tag=helper.getView(R.id.tv_dicount_tag);
        CheckBox cb_dialog_coupon=helper.getView(R.id.cb_dialog_coupon);
        TextView tv_coupon_nousetip=helper.getView(R.id.tv_coupon_nousetip);
        RelativeLayout rl_coupon_content=helper.getView(R.id.rl_coupon_content);

        //测试
//        item.setUseOccasion(5);
//        item.setCouponCategory(8);

        switch (type){
            case 0:
                tvDiscount.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvAmount.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tvName.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tv_yuan_tag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tv_dicount_tag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvDeadLine.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tvScope.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tv_coupon_nousetip.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tvTag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvTag.setBackgroundResource(R.drawable.tag_coupon_pink_7dp);

                ivState.setVisibility(View.GONE);
                btnUse.setVisibility(View.VISIBLE);
                cb_dialog_coupon.setVisibility(View.GONE);
                btnUse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.getIfCanUse()!=1){
                            if(!BBCUtil.isEmpty(item.getCanUseTip())) {
                                ToastUtil.show(context, item.getCanUseTip());
                            }else {
                                ToastUtil.show(context, "优惠券暂不可以使用");
                            }
                            return;
                        }
                        //立即使用
//                        if(item.getUseOccasion()==0||item.getUseOccasion()==4){
//                            //全场
//                            SDJumpUtil.goHomeActivity(context,HomeActivity.GO_HOME);
//                        }else if(item.getUseOccasion()==1){
//                            //指定商品
//                            Intent intent=new Intent(context, ProductListActivity.class);
//                            intent.putExtra("couponId",item.getCouponId());
//                            intent.putExtra("title",item.getCouponName());
//                            ActivityUtil.getInstance().start(context,intent);
//                        }else if(item.getUseOccasion()==2){
//                            //指定分类
//                            Intent intent=new Intent(context, CategoryProductLsitActivity.class);
//                            intent.putExtra("oneId",item.getTopCategory());
//                            intent.putExtra("categoryName","");
//                            ActivityUtil.getInstance().start(context,intent);
//                        }else if(item.getUseOccasion()==5){
//                            //跳转我的专柜商品列表
//                            String adrUrl="?path=/my/shoppe&gotype=10";
//                            String shoppeJsHost=Constants.WEEX_HOST.replace("index.js","shoppe.js");
//                            if(shoppeJsHost.contains("?")&&adrUrl.startsWith("?")){
//                                adrUrl=adrUrl.replace("?","&");
//                            }
//                            adrUrl= shoppeJsHost+adrUrl;
//                            SDJumpUtil.goWhere(context,adrUrl);
//                        }
                    }
                });
                break;
            case 1:
                tvDiscount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvAmount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvName.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvDeadLine.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvScope.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_coupon_nousetip.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_yuan_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tv_dicount_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setBackgroundResource(R.drawable.tag_coupon_black_7dp);
                btnUse.setVisibility(View.GONE);
                ivState.setVisibility(View.VISIBLE);
                ivState.setImageResource(R.mipmap.ysy_new);
                cb_dialog_coupon.setVisibility(View.GONE);
                break;
            case 2:
                tvDiscount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvAmount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvName.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvDeadLine.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvScope.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_coupon_nousetip.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_yuan_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tv_dicount_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setBackgroundResource(R.drawable.tag_coupon_black_7dp);
                btnUse.setVisibility(View.GONE);
                ivState.setVisibility(View.VISIBLE);
                ivState.setImageResource(R.mipmap.ygq_new);
                cb_dialog_coupon.setVisibility(View.GONE);
                break;
            case 3:
                //选择列表 可用
                tvDiscount.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvAmount.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tvName.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tv_yuan_tag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tv_dicount_tag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvDeadLine.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tvScope.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tv_coupon_nousetip.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tvTag.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvTag.setBackgroundResource(R.drawable.tag_coupon_pink_7dp);

                ivState.setVisibility(View.GONE);
                btnUse.setVisibility(View.GONE);
                cb_dialog_coupon.setVisibility(View.VISIBLE);
                if(selectPostion==postion) {
                    cb_dialog_coupon.setChecked(true);
                }else {
                    cb_dialog_coupon.setChecked(false);
                }
                break;
            case 4:
                //优惠券列表 不可用
                tvDiscount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvAmount.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvName.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvDeadLine.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvScope.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_coupon_nousetip.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                tv_yuan_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tv_dicount_tag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                tvTag.setBackgroundResource(R.drawable.tag_coupon_black_7dp);

                ivState.setVisibility(View.GONE);
                btnUse.setVisibility(View.GONE);
                cb_dialog_coupon.setVisibility(View.GONE);
                break;
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
        tvAmount.setText("满"+BBCUtil.getDoubleFormat(item.getAmount())+"元可用");
        tvName.setText(item.getDisplayName());
        tvDeadLine.setText(item.getStartTime()+"-"+item.getEndTime());
        tvScope.setText(item.getCouponTag());
        TextPaint tp = tvDiscount.getPaint();
        tp.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        String discountStr;
        switch (item.getCouponCategory())
        {
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
                }else if(discountStr.length()>3) {
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
                discountStr=BBCUtil.getDoubleFormat(item.getDiscount()*10);
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


        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以选择优惠券
                if(type==3){
                    if(selectPostion != postion) {
                        selectPostion = postion;
                    }else {
                        selectPostion = -1;
                    }
                    notifyDataSetChanged();
                }
            }
        });
        cb_dialog_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以选择优惠券
                if(type==3){
                    if(selectPostion != postion) {
                        selectPostion = postion;
                    }else {
                        selectPostion = -1;
                    }
                    notifyDataSetChanged();
                }
            }
        });

        if(postion==getCount()-2&&presenter!=null&&presenter.haveMore){
            presenter.reqCouponList(type,true);
        }

    }

    public int getSelectPostion() {
        return selectPostion;
    }

    public void setSelectPostion(int selectPostion) {
        this.selectPostion = selectPostion;
        notifyDataSetChanged();
    }
}
