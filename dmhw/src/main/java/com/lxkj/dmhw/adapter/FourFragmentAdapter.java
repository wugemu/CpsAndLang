package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Billboard;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Objects;

/**
 * 榜单
 * Created by Android on 2018/6/9.
 */

public class FourFragmentAdapter extends BaseQuickAdapter<Billboard.BillboardData> {

    private boolean isCheck;

    public FourFragmentAdapter(Context context, boolean isCheck) {
        super(context, R.layout.adapter_four_fragment);
        this.isCheck = isCheck;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Billboard.BillboardData item, int position) {
        Utils.displayImageRounded(context, item.getShopmainpic(), viewHolder.getView(R.id.adapter_four_fragment_image), 5);
        if (item.isCheck()) {
            viewHolder.setImageResource(R.id.adapter_four_fragment_check, R.mipmap.shop_list_tmall);
        } else {
            viewHolder.setImageResource(R.id.adapter_four_fragment_check, R.mipmap.shop_list_taobao);
        }
        TextView adapter_four_fragment_title = viewHolder.getView(R.id.adapter_four_fragment_title);
        TextPaint tp;
        tp = adapter_four_fragment_title.getPaint();
        tp.setFakeBoldText(true);
        viewHolder.setText(R.id.adapter_four_fragment_title, item.getShopname());
        viewHolder.setText(R.id.adapter_four_fragment_moneys, item.getMoney());
        viewHolder.setText(R.id.adapter_four_fragment_money, "券 " + item.getDiscount() + "元");
        if (isCheck) {
            viewHolder.setText(R.id.adapter_four_fragment_number_two,"爆卖 "+ Utils.Conversion(item.getTwohournum(), "万")+" 件");
        } else {
        viewHolder.setText(R.id.adapter_four_fragment_number_two, "爆卖 " + Utils.Conversion(item.getDaynum(), "万")+" 件");
        }
        viewHolder.setText(R.id.adapter_four_fragment_estimate, "奖 "+item.getPrecommission() +"元");
        UserInfo login = DateStorage.getInformation();
        if (DateStorage.getLoginStatus()) {
            if (Objects.equals(login.getUsertype(), "3")){
                viewHolder.setVisible(R.id.adapter_four_fragment_estimate_layout, false);
            } else {
                viewHolder.setVisible(R.id.adapter_four_fragment_estimate_layout, true);
            }
        } else {
            viewHolder.setVisible(R.id.adapter_four_fragment_estimate_layout, false);
        }
        viewHolder.setBackgroundColor(R.id.transparent_one, Color.parseColor("#f2f2f2"));
        switch (position) {
            case 0:
                viewHolder.setBackgroundColor(R.id.transparent_one, Color.parseColor("#00000000"));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params.setMargins(Utils.dipToPixel(R.dimen.dp_16),Utils.dipToPixel(R.dimen.dp_5),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FC4C0F"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_one);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 1:
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params1.setMargins(Utils.dipToPixel(R.dimen.dp_16),Utils.dipToPixel(R.dimen.dp_5),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params1);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FF2E55"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_two);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 2:
                RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params3.setMargins(Utils.dipToPixel(R.dimen.dp_16),Utils.dipToPixel(R.dimen.dp_5),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params3);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#F6557B"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_three);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 3:
                RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params4.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA5AA9"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_four);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 4:
                RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params5.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA5CAA"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_five);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 5:
                RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params6.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA5EAB"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_six);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 6:
                RelativeLayout.LayoutParams params7 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params7.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA60AC"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_seven);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 7:
                RelativeLayout.LayoutParams params8 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params8.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA62AD"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_eight);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 8:
                RelativeLayout.LayoutParams params9 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params9.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA64AE"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_nine);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 9:
                RelativeLayout.LayoutParams params10= (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params10.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA64AE"));
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_ten);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            default:
                viewHolder.setBackgroundColor(R.id.adapter_four_fragment_number_two, Color.parseColor("#FA64AE"));
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, false);
                break;
        }
        viewHolder.getView(R.id.adapter_four_fragment_layout).setOnClickListener(v -> context. startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getShopid()).putExtra("source", "dmj").putExtra("sourceId","")));
    }
}
