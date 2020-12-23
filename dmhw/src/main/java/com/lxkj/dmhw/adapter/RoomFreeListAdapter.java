package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.InspectionRoom;
import com.lxkj.dmhw.utils.Utils;

/**
 *领券直播
 */

public class RoomFreeListAdapter extends BaseQuickAdapter<InspectionRoom.Free, BaseViewHolder>{

    private Context context;
    public RoomFreeListAdapter(Context context) {
        super(R.layout.room_free_list);
        this.context=context;
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, InspectionRoom.Free item) {
        Utils.displayImageCircular(context, item.getUserpicurl(), viewHolder.getView(R.id.free_user_iv));
        viewHolder.setText(R.id.free_username,item.getUsername());
        viewHolder.setText(R.id.free_userphone, item.getUserphone());
    }
}
