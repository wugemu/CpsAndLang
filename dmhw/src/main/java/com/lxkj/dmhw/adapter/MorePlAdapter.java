package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.JDGoodsBean;
import com.lxkj.dmhw.bean.MyTaskList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 多平台商品列表
 */

public class MorePlAdapter extends BaseQuickAdapter<JDGoodsBean, BaseViewHolder> {

    private Activity activity;
    private MorePlAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(MorePlAdapter.OnItemClickListener li) {
        mListener = li;
    }
    private int platFrom=0;
    TextPaint tp;
    boolean showSales=false;
    public MorePlAdapter(Activity activity,int type,boolean issales) {
        super(R.layout.adapter_list_more_pl);
        this.activity = activity;
        this.platFrom=type;
        this.showSales=issales;
    }

    @Override
    protected void convert(BaseViewHolder helper, JDGoodsBean item) {
        try {
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_new_one_fragment_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_new_one_fragment_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRoundedAll(activity, item.getImageUrl(), helper.getView(R.id.adapter_new_one_fragment_image), 8);
            if (item.getShopInfo()!=null){
                helper.setText(R.id.adapter_new_one_fragment_title, item.getName());
                helper.setGone(R.id.shop_layout,true);
                helper.setGone(R.id.adapter_new_one_fragment_check,false);
                JSONObject shop=(JSONObject) item.getShopInfo();
                helper.setText(R.id.adapter_new_one_fragment_shop, shop.getString("name"));
                Utils.displayImage(activity, shop.getString("logo"), helper.getView(R.id.adapter_shop_check));
            }else{
                helper.setText(R.id.adapter_new_one_fragment_title, "     "+item.getName());
                helper.setGone(R.id.adapter_new_one_fragment_check,true);
                helper.setGone(R.id.shop_layout,false);
                if (item.getCpsType()!=null){
                    JSONObject cpsType=(JSONObject) item.getCpsType();
                    Utils.displayImage(activity, cpsType.getString("logo"), helper.getView(R.id.adapter_new_one_fragment_check));
                }
            }
            switch (platFrom){
                case 1:
                case 2:
                case 5:
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,true);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,false);
                    helper.setGone(R.id.adapter_new_one_fragment_original_price,true);
                    helper.setText(R.id.adapter_new_one_fragment_original_price, "原价¥"+item.getCost());
                    helper.setText(R.id.adapter_new_one_fragment_discount, item.getSave()+"元");
                    helper.setText(R.id.adapter_new_one_fragment_estimate_text,  "奖 ¥"+item.getNormalCommission());
                    break;
                case 3://唯品会
                case 6://考拉
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,false);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,true);
                    helper.setGone(R.id.adapter_new_one_fragment_original_price,true);
                    helper.setText(R.id.adapter_new_one_fragment_original_price, "原价¥"+item.getCost());
                    helper.setText(R.id.adapter_new_one_text_discount_wph,item.getDiscount()+"折");
                    helper.setText(R.id.adapter_new_one_fragment_estimate_text,"奖 ¥"+item.getNormalCommission());
                    break;
            }
            if (showSales){
                if (item.getSales().equals("")){
                    helper.setGone(R.id.adapter_new_one_fragment_sales,false);
                }else{
                    helper.setGone(R.id.adapter_new_one_fragment_sales,true);
                    helper.setText(R.id.adapter_new_one_fragment_sales,"已售"+Utils.getNumber(item.getSales()));
                }
            }else{
                helper.setGone(R.id.adapter_new_one_fragment_sales,false);
            }
            TextView adapter_new_one_fragment_price =helper.getView(R.id.adapter_new_one_fragment_price);
            TextView coupon_title =helper.getView(R.id.coupon_title);
            tp=adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            tp=coupon_title.getPaint();
            tp.setFakeBoldText(true);
            adapter_new_one_fragment_price.setText(item.getPrice());
            if (!DateStorage.getLoginStatus()) {
                helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
            }else{
                helper.setGone(R.id.adapter_new_one_fragment_estimate, true);
            }
            helper.getView(R.id.adapter_share_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(item,1);
                }
            });
            helper.getView(R.id.adapter_new_one_fragment_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(item,2);
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(JDGoodsBean data,int type);
    }
}
