package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.BigBrand;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;


public class BrandListAdapter extends BaseQuickAdapter<BigBrand, BaseViewHolder> {

    private Activity activity;
    private int layoutSize;
    public BrandListAdapter(Activity activity,int size) {
        super(R.layout.adapter_bigbrand);
        this.activity = activity;
        this.layoutSize=size;
    }

    @Override
    protected void convert(BaseViewHolder helper, BigBrand item) {
        try {
            LinearLayout adapter_icon_layout =helper.getView(R.id.adapter_icon_layout);
            RecyclerView.LayoutParams linearParams =(RecyclerView.LayoutParams)adapter_icon_layout.getLayoutParams();
            linearParams.width=layoutSize;
            adapter_icon_layout.setLayoutParams(linearParams);
            Utils.displayImage(activity, item.getLogo(), helper.getView(R.id.header_fragment_one_image));
            helper.setText(R.id.header_fragment_one__text, item.getName());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
