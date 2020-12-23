package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.bean.NewsExamOther;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 运营推广
 */

public class NewsExamOtherAdapter extends BaseQuickAdapter<NewsExamOther, BaseViewHolder> {

    private int position;
    private Context context;
    private boolean isCheck;
    private OnItemClickListener mListener;
    public void setOnItemClickListener(NewsExamOtherAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public NewsExamOtherAdapter(Context context, boolean isCheck) {
        super(R.layout.adapter_news_other);
        this.context = context;
        this.position = position;
        this.isCheck = isCheck;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewsExamOther item) {
        try {
            Utils.displayImageCircular(context, item.getUserpicurl(), helper.getView(R.id.exam_image));
            helper.setText(R.id.name,item.getUsername());
            helper.setText(R.id.phone,item.getUserphone());
            helper.setText(R.id.profit,item.getMny());
            helper.setText(R.id.time,item.getCreatetime());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position, NewsExam data, int clickType);
    }

}
