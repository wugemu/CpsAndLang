package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

import static android.graphics.Color.WHITE;

/**
 * 商品列表
 * Created by Zyhant on 2018/1/12.
 */

public class FootPrintListAdapter extends BaseQuickAdapter<CommodityList.CommodityData, BaseViewHolder> {

    private Activity activity;
    private TextPaint tp;
    public FootPrintListAdapter(Activity activity) {
        super(R.layout.adapter_foot_list);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityList.CommodityData item) {
        try {
            LinearLayout adapter_new_one_fragment_layout = helper.getView(R.id.adapter_new_one_fragment_layout);
            LinearLayout.LayoutParams linearParamschild = (LinearLayout.LayoutParams) adapter_new_one_fragment_layout.getLayoutParams();
            LinearLayout total_layout = helper.getView(R.id.total_layout);
            RecyclerView.LayoutParams linearParamstotal = (RecyclerView.LayoutParams) total_layout.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            if (helper.getAdapterPosition()== 0) {//第一项
                total_layout.setBackground(activity.getResources().getDrawable(R.drawable.personal_grid_bgtop));
                linearParamstotal.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                total_layout.setLayoutParams(linearParamstotal); //使设置好的布局参数应用到控件
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                adapter_new_one_fragment_layout.setLayoutParams(linearParamschild);
            }else if(helper.getAdapterPosition()==getItemCount()-2){
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10));
                adapter_new_one_fragment_layout.setLayoutParams(linearParamschild);
                total_layout.setBackground(activity.getResources().getDrawable(R.drawable.personal_grid_bg01));
            }else{
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                adapter_new_one_fragment_layout.setLayoutParams(linearParamschild);
                total_layout.setBackgroundColor(WHITE);
                linearParamstotal.setMargins(0,0,0,0);
                total_layout.setLayoutParams(linearParamstotal); //使设置好的布局参数应用到控件
            }
            TextView adapter_new_one_fragment_price=helper.getView(R.id.adapter_new_one_fragment_price);
            tp= adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            TextView quanhou11= helper.getView(R.id.quanhou11);
            tp=  quanhou11.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_new_one_fragment_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_new_one_fragment_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(activity, item.getShopmainpic(), helper.getView(R.id.adapter_new_one_fragment_image), 5);
            helper.setText(R.id.adapter_new_one_fragment_title, item.getTitle());
            helper.setText(R.id.adapter_new_one_fragment_discount, item.getDiscount()+"元");
            helper.setText(R.id.adapter_new_one_fragment_number, "已售" + Utils.getNumber(item.getShopmonthlysales()));
            helper.setText(R.id.adapter_new_one_fragment_estimate_text,  "奖 ¥"+item.getPrecommission());
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
            if (item.isCheck()) {
                helper.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.tmall_icon);
            } else {
                helper.setImageResource(R.id.adapter_new_one_fragment_check, R.mipmap.taobao_icon);
            }
            helper.setText(R.id.adapter_new_one_fragment_price, item.getMoney());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
