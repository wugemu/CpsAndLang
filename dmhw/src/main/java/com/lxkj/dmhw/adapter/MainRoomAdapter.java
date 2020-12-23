package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LiveInspectionRoomActivity;
import com.lxkj.dmhw.bean.RoomList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;

import com.lxkj.dmhw.utils.Utils;

import java.util.Objects;

import lombok.experimental.Helper;

/**
 *领券直播
 */

public class MainRoomAdapter extends BaseQuickAdapter<RoomList, BaseViewHolder>{

    private Context context;
    private int isType=0;//首页还是列表
    TextPaint tp;
    public MainRoomAdapter(Context context, int setType) {
        super(R.layout.adapter_list_room_item);
        this.context=context;
        this.isType=setType;
    }


    @Override
    public int getItemCount() {
//        if (countNumSetting==0){
//         return 3;
//        }else{
//            return super.getItemCount();
//        }
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, RoomList item) {
        Utils.displayImageRounded(context, item.getShopmainpic(), viewHolder.getView(R.id.adapter_new_one_fragment_image), 5);
        viewHolder.getView(R.id.icon1).setBackgroundResource(R.drawable.live_bg);
        AnimationDrawable animaition = (AnimationDrawable)viewHolder.getView(R.id.icon1).getBackground();
        animaition.start();

        TextView textView=viewHolder.getView(R.id.adapter_new_one_fragment_title);
        tp= textView.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_new_one_fragment_price=viewHolder.getView(R.id.adapter_new_one_fragment_price);
        tp= adapter_new_one_fragment_price.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_new_one_fragment_discount=viewHolder.getView(R.id.adapter_new_one_fragment_discount);
        tp=adapter_new_one_fragment_discount.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_new_one_fragment_estimate_text=viewHolder.getView(R.id.adapter_new_one_fragment_estimate_text);
        tp=adapter_new_one_fragment_estimate_text.getPaint();
        tp.setFakeBoldText(true);
        viewHolder.setText(R.id.adapter_new_room_num,Utils.getNumber(item.getNum())+"观看");
        viewHolder.setText(R.id.adapter_new_one_fragment_title, item.getTitle());
        viewHolder.setText(R.id.adapter_new_one_fragment_discount, item.getDiscount()+"元");
        viewHolder.setText(R.id.adapter_new_one_fragment_estimate_text, "奖"+item.getPrecommission() + "元");
        viewHolder.setText(R.id.adapter_new_one_fragment_price, item.getPostcouponprice());
        viewHolder.setText(R.id.adapter_new_one_fragment_shop, item.getRecommended());
        viewHolder.setText(R.id.adapter_new_one_fragment_original_price,  context.getResources().getString(R.string.money) +item.getShopprice());
        TextView yuanjia =viewHolder.getView(R.id.adapter_new_one_fragment_original_price);
        yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        UserInfo login = DateStorage.getInformation();
        if (DateStorage.getLoginStatus()) {
            if (Objects.equals(login.getUsertype(), "3")) {
                viewHolder.setVisible(R.id.adapter_new_one_fragment_estimate, false);
            } else {
                viewHolder.setVisible(R.id.adapter_new_one_fragment_estimate, true);
            }
        } else {
            viewHolder.setVisible(R.id.adapter_new_one_fragment_estimate, false);
        }
//        if (item.isCheck()) {
//            viewHolder.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.shop_list_tmall);
//        } else {
//            viewHolder.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.shop_list_taobao);
//        }
        if (viewHolder.getAdapterPosition()==getItemCount()-1){
            viewHolder.setVisible(R.id.spilt,false);
        }else{
            viewHolder.setVisible(R.id.spilt,true);
        }
        viewHolder.setOnClickListener(R.id.adapter_new_one_fragment_layout, v -> context.startActivity(new Intent(context, LiveInspectionRoomActivity.class)
                .putExtra("id", item.getId()).putExtra("goodsPic", item.getShopmainpic()).putExtra("lookNum",  Utils.getNumber(item.getNum())).putExtra("goodsTitle", item.getTitle()).putExtra("shopid", item.getShopid())
                .putExtra("afterCouponPrice", item.getMoney()).putExtra("yongjin", item.getPrecommission()).putExtra("couponPrice", item.getDiscount()).putExtra("shopPirce", Utils.getFloat(Float.parseFloat(item.getShopprice()))+"")));
    }
}
