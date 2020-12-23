package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.FansInfo;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 我的粉丝
 * Created by Android on 2018/5/16.
 */

public class FansAdapter extends BaseQuickAdapter<FansInfo.FansList, BaseViewHolder> {

    private Context context;

    public FansAdapter(Context context) {
        super(R.layout.adapter_fans);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FansInfo.FansList item) {
        try {
            if (helper.getLayoutPosition()%2 == 0) {
                helper.setBackgroundColor(R.id.adapter_fans_layout, 0xfff0f0f0);
            } else {
                helper.setBackgroundColor(R.id.adapter_fans_layout, 0xffffffff);
            }
            helper.setText(R.id.adapter_fans_time, item.getJointime());
            Utils.displayImageCircular(context, item.getUserpicurl(), helper.getView(R.id.adapter_fans_avatar));
            helper.setText(R.id.adapter_fans_name, item.getUsername());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
