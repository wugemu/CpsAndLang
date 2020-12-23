package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Collection;
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

public class CollectionAdapter extends BaseQuickAdapter<Collection.CollectionList, BaseViewHolder> {

    private Activity activity;
    TextPaint tp;
    public CollectionAdapter(Activity activity) {
        super(R.layout.adapter_collection);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Collection.CollectionList item) {
        try {



            Utils.displayImageRounded(activity, item.getImage(), helper.getView(R.id.adapter_collection_image), 5);
            helper.setText(R.id.adapter_collection_title, item.getTitle());
            if (!item.getDiscount().equals("")){
                helper.setText(R.id.adapter_collection_discount, item.getDiscount()+"元");
            }else {
                helper.setText(R.id.adapter_collection_discount, "0元");
            }
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_collection_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_collection_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            helper.setText(R.id.adapter_collection_number, "已售" + Utils.getNumber(item.getSales()) + "件");
            helper.setImageResource(R.id.adapter_collection_check, item.isCheck() ? R.mipmap.tmall_icon : R.mipmap.taobao_icon);
            TextView adapter_collection_price= helper.getView(R.id.adapter_collection_price);
            tp=adapter_collection_price.getPaint();
            tp.setFakeBoldText(true);
            TextView quanhou11= helper.getView(R.id.quanhou11);
            tp=  quanhou11.getPaint();
            tp.setFakeBoldText(true);
            helper.setText(R.id.adapter_collection_price, item.getMoney());
            helper.setText(R.id.adapter_collection_estimate_text, "奖 ¥"+item.getEstimate());
            helper.setText(R.id.adapter_collection_shop, item.getShopName());
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
            if (!item.isEffect()){
                helper.setGone(R.id.noeffect, true);
//                helper.setTextColor(R.id.quanhou11, Color.parseColor("#C6C6C6"));
                helper.setTextColor(R.id.adapter_collection_title, Color.parseColor("#C6C6C6"));
//                helper.setTextColor(R.id.adapter_collection_price, Color.parseColor("#C6C6C6"));
                helper.setTextColor(R.id.adapter_collection_number, Color.parseColor("#C6C6C6"));
            }else{
                helper.setGone(R.id.noeffect, false);
                helper.setTextColor(R.id.adapter_collection_title, Color.parseColor("#333333"));
//                helper.setTextColor(R.id.adapter_collection_price, Color.parseColor("#EE2325"));
//                helper.setTextColor(R.id.quanhou11, Color.parseColor("#EE2325"));
                helper.setTextColor(R.id.adapter_collection_number, Color.parseColor("#999999"));
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
            for (Collection.CollectionList list : getData()) {
                list.setPlay(isCheck);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
