package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 新热卖接口
 */

public class FourFragmentAdapter340 extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private boolean isCheck;
    private Activity context;
    TextPaint tp;
    public FourFragmentAdapter340(Activity activity, boolean isCheck) {
        super(R.layout.adapter_four_fragment);
        this.isCheck = isCheck;
        this.context = activity;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, CommodityDetails290 item) {
        Utils.displayImageRounded(context, item.getImageUrl(), viewHolder.getView(R.id.adapter_four_fragment_image), 5);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(item.getCpsType());
            Utils.displayImage(context, jsonObject.optString("logo"), viewHolder.getView(R.id.adapter_four_fragment_check));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView adapter_four_fragment_title = viewHolder.getView(R.id.adapter_four_fragment_title);
        tp = adapter_four_fragment_title.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_four_fragment_moneys = viewHolder.getView(R.id.adapter_four_fragment_moneys);
        tp = adapter_four_fragment_moneys.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_four_fragment_estimate = viewHolder.getView(R.id.adapter_four_fragment_estimate);
        tp = adapter_four_fragment_estimate.getPaint();
        tp.setFakeBoldText(true);
        TextView adapter_four_fragment_money = viewHolder.getView(R.id.adapter_four_fragment_money);
        tp = adapter_four_fragment_money.getPaint();
        tp.setFakeBoldText(true);
        viewHolder.setText(R.id.adapter_four_fragment_title, item.getName());
        viewHolder.setText(R.id.adapter_four_fragment_moneys,item.getPrice());
        viewHolder.setText(R.id.adapter_four_fragment_money,  item.getCoupon()+"元");
        viewHolder.setText(R.id.adapter_four_fragment_number_two, "爆卖 " + item.getSales()+" 件");
        viewHolder.setText(R.id.adapter_four_fragment_estimate, "奖 "+item.getNormalCommission()+"元");
        viewHolder.setBackgroundColor(R.id.transparent_one, Color.parseColor("#f2f2f2"));
        switch (viewHolder.getAdapterPosition()) {
            case 0:
                viewHolder.setBackgroundColor(R.id.transparent_one, Color.parseColor("#00000000"));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_one);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 1:
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params1.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params1);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_two);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 2:
                RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params3.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.getView(R.id.adapter_four_fragment_ranking).setLayoutParams(params3);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_three);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 3:
                RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params4.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_four);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 4:
                RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params5.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_five);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 5:
                RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params6.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_six);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 6:
                RelativeLayout.LayoutParams params7 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params7.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_seven);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 7:
                RelativeLayout.LayoutParams params8 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params8.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_eight);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 8:
                RelativeLayout.LayoutParams params9 = (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params9.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_nine);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            case 9:
                RelativeLayout.LayoutParams params10= (RelativeLayout.LayoutParams)viewHolder.getView(R.id.adapter_four_fragment_ranking).getLayoutParams();
                params10.setMargins(Utils.dipToPixel(R.dimen.dp_20),Utils.dipToPixel(R.dimen.dp_10),0,0);
                viewHolder.setImageResource(R.id.adapter_four_fragment_ranking, R.mipmap.four_fragment_ten);
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, true);
                break;
            default:
                viewHolder.setVisible(R.id.adapter_four_fragment_ranking, false);
                break;
        }
        viewHolder.getView(R.id.adapter_four_fragment_layout).setOnClickListener(v -> context. startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", item.getId()).putExtra("source", item.getSource()).putExtra("sourceId",item.getSourceId())));
    }
}
