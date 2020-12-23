package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Collection;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.ShopInfo290;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SmoothCheckBox;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 收藏适配器
 * Created by Android on 2018/4/16.
 */

public class CollectionAdapterPJW extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private Activity activity;
    TextPaint tp;
    public CollectionAdapterPJW(Activity activity) {
        super(R.layout.adapter_collection_pjw);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetails290 item) {
        try {
            TextView textView=helper.getView(R.id.adapter_collection_price);
            tp= textView.getPaint();
            tp.setFakeBoldText(true);
            TextView quanhou11=helper.getView(R.id.quanhou11);
            tp= quanhou11.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_collection_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_collection_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(activity, item.getImageUrl(), helper.getView(R.id.adapter_collection_image), 5);
            helper.setGone(R.id.adapter_collection_overdue, !item.getIsEffective());
            helper.setText(R.id.adapter_collection_title, item.getName());
            helper.setText(R.id.adapter_collection_price, item.getPrice());
            // 设置商品类型图片
            Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
            CpsType objtype=(CpsType) CpsTypeObject;
            String code=objtype.getCode();
            Utils.displayImage(activity, objtype.getLogo(), helper.getView(R.id.adapter_collection_check));
            switch (code){
                case "jd":
                case "pdd":
                case "sn":
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,true);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,false);
                    if (item.getHasCoupon()){
                        Object CouponObject = JSON.parseObject(item.getCouponInfo(), Coupon.class);
                        if ((Coupon) CouponObject!=null){
                            helper.setText(R.id.adapter_collection_discount, ((Coupon) CouponObject).getPrice()+"元");
                        }
                    }else{
                        helper.setText(R.id.adapter_collection_discount, "¥0");
                    }
                    break;
                case "wph":
                case "kl":
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,false);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,true);
                    helper.setText(R.id.adapter_new_one_text_discount_wph, item.getDiscount()+"折");
                    break;
            }
            helper.setText(R.id.adapter_collection_estimate_text, "奖 ¥"+item.getNormalCommission());
            TextView adapter_collection_original_price =helper.getView(R.id.adapter_collection_original_price);
            helper.setText(R.id.adapter_collection_original_price, "原价¥"+item.getCost());
            ((SmoothCheckBox) helper.getView(R.id.adapter_collection_checkbox)).setChecked(item.isPlay());
            helper.getView(R.id.adapter_collection_checkbox_layout).setOnClickListener(v -> helper.getView(R.id.adapter_collection_checkbox).performClick());
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")){
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
                } else {
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, true);
                }
            } else {
                helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
            }
            ((SmoothCheckBox) helper.getView(R.id.adapter_collection_checkbox)).setOnCheckedChangeListener((checkBox, isChecked) -> {
                item.setPlay(isChecked);
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("CollectionCheck"), "", 0);
            });
            if (!item.getIsEffective()){
                helper.setGone(R.id.noeffect, true);
//                helper.setTextColor(R.id.quanhou11, Color.parseColor("#C6C6C6"));
//                helper.setTextColor(R.id.adapter_collection_title, Color.parseColor("#C6C6C6"));
//                helper.setTextColor(R.id.adapter_collection_price, Color.parseColor("#C6C6C6"));
            }else{
                helper.setGone(R.id.noeffect, false);
//                helper.setTextColor(R.id.quanhou11, Color.parseColor("#EE2325"));
//                helper.setTextColor(R.id.adapter_collection_title, Color.parseColor("#333333"));
//                helper.setTextColor(R.id.adapter_collection_price, Color.parseColor("#EE2325"));
            }

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 全选
     * @param isCheck
     */
    public void setCheckAll(boolean isCheck) {
        try {
            for (CommodityDetails290 list : getData()) {
                list.setPlay(isCheck);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
