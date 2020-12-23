package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.NewsDetails;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 消息详情
 * Created by zyhant on 18-3-3.
 */

public class NewsDetailsAdapter extends BaseQuickAdapter<NewsDetails.NewsDetailsList, BaseViewHolder> {

    private Context context;

    public NewsDetailsAdapter(Context context) {
        super(R.layout.adapter_news_details);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsDetails.NewsDetailsList item) {
        try {
            helper.setText(R.id.adapter_news_details_time, item.getCreatetime());
            helper.setText(R.id.adapter_news_details_title, item.getMessagetitle());
            Utils.displayImage(context, item.getMessageimge(), helper.getView(R.id.adapter_news_details_image));
            helper.setText(R.id.adapter_news_details_content, item.getMessagecontent());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
