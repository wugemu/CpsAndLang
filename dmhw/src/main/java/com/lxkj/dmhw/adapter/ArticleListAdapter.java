package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollArticleList;
import com.lxkj.dmhw.bean.InspectionRoom;
import com.lxkj.dmhw.utils.Utils;

/**
 *领券直播
 */

public class ArticleListAdapter extends BaseQuickAdapter<ComCollArticleList, BaseViewHolder>{

    private Context context;
    private String aid;
    private int cctype;
    public ArticleListAdapter(Context context,String id,int type) {
        super(R.layout.article_list);
        this.context=context;
        this.aid=id;
        this.cctype=type;
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ComCollArticleList item) {
        if (cctype==1){
        //视频
            viewHolder.setGone(R.id.arraw,false);
            viewHolder.setGone(R.id.video_btn,true);
            if (item.getId().equals(aid)){
                viewHolder.setTextColor(R.id.adapter_article_title, Color.parseColor("#F84942"));
                viewHolder.setImageResource(R.id.video_btn,R.mipmap.comcoll_play);
            }else{
                viewHolder.setTextColor(R.id.adapter_article_title, Color.parseColor("#333333"));
                viewHolder.setImageResource(R.id.video_btn,R.mipmap.comcoll_unplay);
            }
        }else{
            if (item.getId().equals(aid)){
                viewHolder.setTextColor(R.id.adapter_article_title, Color.parseColor("#F84942"));
                viewHolder.setImageResource(R.id.arraw,R.mipmap.comcoll_select_arrow);
            }else{
                viewHolder.setTextColor(R.id.adapter_article_title, Color.parseColor("#333333"));
                viewHolder.setImageResource(R.id.arraw,R.mipmap.comcoll_arrow);
            }
            viewHolder.setGone(R.id.arraw,true);
            viewHolder.setGone(R.id.video_btn,false);
        }
        viewHolder.setText(R.id.adapter_article_title,item.getTitle());
        viewHolder.setText(R.id.adapter_parttwo_quesnumber, item.getClicknum()+"人已学");
        viewHolder.setText(R.id.likenum, item.getLikenum());
        viewHolder.setText(R.id.sharenum, item.getSharenum());
        viewHolder.setText(R.id.adapter_parttwo_questime, item.getDate());
    }
}
