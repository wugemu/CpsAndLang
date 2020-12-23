package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.AppInfo;
import com.lxkj.dmhw.utils.Utils;

/**
 *领券直播
 */

public class MarketListAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder>{

    private Context context;
    public MarketListAdapter(Context context) {
        super(R.layout.room_free_list);
        this.context=context;
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, AppInfo item) {
        viewHolder.setGone(R.id.free_user_layout,false);
        viewHolder.setGone(R.id.split,false);
        viewHolder.setGone(R.id.m_arrow,true);
        viewHolder.setText(R.id.free_username, Utils.getPkgName(item.getPackageName()));
    }

}
