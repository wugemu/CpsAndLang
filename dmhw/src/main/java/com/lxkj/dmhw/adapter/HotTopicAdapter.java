package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MainHotTopic;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 首页活动热区
 */

public class HotTopicAdapter extends BaseQuickAdapter<MainHotTopic, BaseViewHolder> {

    private Context context;

    public HotTopicAdapter(Context context) {
        super(R.layout.view_main_topic_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHotTopic item) {
        try {
           switch (item.getLayoutStyle()){
               case "0":
                   helper.setGone(R.id.mian_topic_layout_one, true);
                   helper.setGone(R.id.mian_topic_layout_two, false);
                   helper.setGone(R.id.mian_topic_layout_three, false);
                   Utils.displayImageRounded(context,item.getImglist().get(0).getImgurl(), helper.getView(R.id.img01), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(1).getImgurl(), helper.getView(R.id.img02), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(2).getImgurl(), helper.getView(R.id.img03), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(3).getImgurl(), helper.getView(R.id.img04), 0);
                   break;

               case "1":
                   helper.setGone(R.id.mian_topic_layout_one, false);
                   helper.setGone(R.id.mian_topic_layout_two, true);
                   helper.setGone(R.id.mian_topic_layout_three, false);
                   Utils.displayImageRounded(context,item.getImglist().get(0).getImgurl(), helper.getView(R.id.img05), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(1).getImgurl(), helper.getView(R.id.img06), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(2).getImgurl(), helper.getView(R.id.img07), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(3).getImgurl(), helper.getView(R.id.img08), 0);
                   break;

               case "2":
                   helper.setGone(R.id.mian_topic_layout_one, false);
                   helper.setGone(R.id.mian_topic_layout_two, false);
                   helper.setGone(R.id.mian_topic_layout_three, true);
                   Utils.displayImageRounded(context,item.getImglist().get(0).getImgurl(), helper.getView(R.id.img09), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(1).getImgurl(), helper.getView(R.id.img10), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(2).getImgurl(), helper.getView(R.id.img11), 0);
                   Utils.displayImageRounded(context,item.getImglist().get(3).getImgurl(), helper.getView(R.id.img12), 0);
                   break;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }



}
