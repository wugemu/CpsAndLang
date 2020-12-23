package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.BigBrandDetailActivity;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class ComCollAdapter extends BaseQuickAdapter<ComCollegeData.NewHand, BaseViewHolder> {

    private Context context;

    public ComCollAdapter(Context context) {
        super(R.layout.adapter_comcoll);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComCollegeData.NewHand item) {
        try {
            if (item.getType().equals("1")){
                helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.vedio_logo);
            }else {
                helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.txt_logo);
            }
            Utils.displayImageRoundedAll(context, item.getImgurl(), helper.getView(R.id.adapter_parttwo_image),8);
            helper.setText(R.id.adapter_parttwo_title, "      "+item.getTitle());
            helper.setText(R.id.adapter_parttwo_number,  item.getClicknum()+"人已学");
            helper.setText(R.id.adapter_parttwo_type,  item.getTypetitle());

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
