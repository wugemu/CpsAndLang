package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;

import java.util.Objects;

/**
 * 视频购物
 * Created by Android on 2018/7/20.
 */

public class ShopGridAdapter extends BaseQuickAdapter<CommodityList.CommodityData, BaseViewHolder> {

    private Activity activity;
    private int mType=0;
    public ShopGridAdapter(Activity activity,int type) {
        super(R.layout.adapter_grid_shop_linear);
        this.activity = activity;
        this.mType=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityList.CommodityData item) {
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) helper.getView(R.id.adapter_grid_shop_linear_layout).getLayoutParams();
            switch (helper.getLayoutPosition()) {
                case 0:
                    if (helper.getLayoutPosition() % 2 == 0) {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_5));
                    } else {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5));
                    }
                    break;
                case 1:
                    if (helper.getLayoutPosition() % 2 == 0) {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_5));
                    } else {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5));
                    }
                    break;
                default:
                    if (helper.getLayoutPosition() % 2 == 0) {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_5));
                    } else {
                        params.setMargins(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_10), Utils.dipToPixel(R.dimen.dp_5));
                    }
                    break;
            }
            helper.getView(R.id.adapter_grid_shop_linear_layout).setLayoutParams(params);
            Utils.displayImage(activity, item.getShopmainpic(), helper.getView(R.id.adapter_grid_shop_linear_image));
            helper.setText(R.id.adapter_grid_shop_linear_title, item.getTitle());
             if(mType==9){
              helper.setGone(R.id.commodity_video,false);
             }else{
                 helper.setGone(R.id.commodity_video,true);
             }
            if (item.isCheck()) {
                helper.setImageResource(R.id.adapter_grid_shop_linear_check, R.mipmap.shop_list_tmall);
            } else {
                helper.setImageResource(R.id.adapter_grid_shop_linear_check, R.mipmap.shop_list_taobao);
            }
            helper.setText(R.id.adapter_grid_shop_linear_price, item.getMoney());
            helper.setText(R.id.adapter_grid_shop_linear_discount, item.getDiscount());
            helper.setText(R.id.adapter_grid_shop_linear_number, Utils.getNumber(item.getShopmonthlysales()) + "人已购买");
            helper.setText(R.id.adapter_grid_shop_linear_estimate, "分享赚" + item.getPrecommission() + "元");
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(((NewActivity) activity).login.getUsertype(), "3")) {
                    helper.setGone(R.id.adapter_grid_shop_linear_estimate, false);
                } else {
                    helper.setGone(R.id.adapter_grid_shop_linear_estimate, true);
                }
            } else {
                helper.setGone(R.id.adapter_grid_shop_linear_estimate, false);
            }
            helper.getView(R.id.adapter_grid_shop_linear_layout).setOnClickListener(v -> activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId", item.getShopid()).putExtra("source", "dmj").putExtra("sourceId","")));
        } catch (Exception e) {
        }
    }
}
