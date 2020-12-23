package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class ComCollHistoryAdapter extends BaseQuickAdapter<ComCollArticle, BaseViewHolder> {

    private Context context;
    private String fromType;
    public ComCollHistoryAdapter(Context context, String from) {
        super(R.layout.adapter_comcoll_history);
        this.context = context;
        this.fromType=from;
    }

    @Override
    protected void convert(BaseViewHolder helper,ComCollArticle item) {
        try {
                Utils.displayImageRoundedAll(context, item.getImgurl(), helper.getView(R.id.adapter_parttwo_image),8);
                helper.setText(R.id.adapter_parttwo_title, item.getTitle());
                helper.setText(R.id.adapter_parttwo_number,  item.getClicknum()+"人已学");
                helper.setText(R.id.adapter_share_number,  item.getSharenum());

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
