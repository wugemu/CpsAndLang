package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MyTaskList;
import com.lxkj.dmhw.bean.News;
import com.orhanobut.logger.Logger;

/**
 * 我的任务适配器
 */

public class NewsItemAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    private  Context con;
    TextPaint tp;
    public NewsItemAdapter(Context context) {
        super(R.layout.adapter_news_item);
        this.con=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, News item) {
        try {
            TextView textView=helper.getView(R.id.news_item_title);
            tp= textView.getPaint();
            tp.setFakeBoldText(true);
         helper.setText(R.id.news_item_content,item.getContent());
         helper.setText(R.id.news_item_time,item.getTime());
            switch (item.getType()) {
                case "0"://活动精选
                    helper.setText(R.id.news_item_title,"活动精选");
                   helper.setImageResource(R.id.news_item_img,R.mipmap.news_selected);
                    break;
                case "1"://我的资产
                    helper.setText(R.id.news_item_title,"我的资产");
                    helper.setImageResource(R.id.news_item_img,R.mipmap.news_money);
                    break;
                case "2"://系统消息
                    helper.setText(R.id.news_item_title,"系统消息");
                    helper.setImageResource(R.id.news_item_img,R.mipmap.news_system);
                    break;
                case "3"://爆款推荐
                    helper.setText(R.id.news_item_title,"爆款推荐");
                    helper.setImageResource(R.id.news_item_img,R.mipmap.news_shop);
                    break;
                case "4"://运营推广
                    helper.setText(R.id.news_item_title,"运营推广");
                    helper.setImageResource(R.id.news_item_img,R.mipmap.news_examine);
                    break;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
