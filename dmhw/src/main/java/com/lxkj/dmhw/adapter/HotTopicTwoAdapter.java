package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


/**
 * 首页中间活动热区2
 */

public class HotTopicTwoAdapter extends BaseQuickAdapter<Banner,BaseViewHolder> {

    private Context context;
    private HotTopicTwoAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(HotTopicTwoAdapter.OnItemClickListener li) {
        mListener = li;
    }

    public HotTopicTwoAdapter(Context context) {
        super(R.layout.view_main_topic_two_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Banner item) {
        try {
            Utils.displayImageRoundedAll(context, item.getAdvertisementpic(), viewHolder.getView(R.id.image_iv), 5);
            if(mListener != null) {
                viewHolder.getView(R.id.image_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(viewHolder.getAdapterPosition(), item);
                    }
                });
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position, Banner data);
    }
}
