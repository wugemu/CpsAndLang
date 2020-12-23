package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.NewsExamDetailActivity;
import com.lxkj.dmhw.bean.MyTaskList;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.bean.Order;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.io.Serializable;

/**
 * 运营推广
 */

public class NewsExamAdapter extends BaseQuickAdapter<NewsExam, BaseViewHolder> {

    private int position;
    private Context context;
    private boolean isCheck;
    private OnItemClickListener mListener;
    public void setOnItemClickListener(NewsExamAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public NewsExamAdapter(Context context, int position, boolean isCheck) {
        super(R.layout.adapter_news_exam);
        this.context = context;
        this.position = position;
        this.isCheck = isCheck;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewsExam item) {
        try {
            Utils.displayImageCircular(context, item.getUserpicurl(), helper.getView(R.id.exam_image));
            helper.setText(R.id.exam_name,item.getUsername());
            helper.setText(R.id.exam_time,"申请时间： "+item.getReqtime());
            helper.setText(R.id.adapter_team_num,item.getTeamcnt());
            helper.setText(R.id.adapter_team_estimate,item.getProfit());
            helper.setText(R.id.adapter_team_order,item.getOrdercnt());
            switch (position){
                //待审批页面
                case 1:
                    helper.setGone(R.id.examing,true);
                    helper.setGone(R.id.examrefuse,false);
                    helper.setGone(R.id.exampass,false);
                    break;
                //已通过
                case 2:
                    helper.setGone(R.id.examing,false);
                    helper.setGone(R.id.examrefuse,false);
                    helper.setGone(R.id.exampass,true);
                    break;
                 //已拒绝
                case 3:
                    helper.setGone(R.id.examing,false);
                    helper.setGone(R.id.examrefuse,true);
                    helper.setGone(R.id.exampass,false);
                    break;

            }
            helper.getView(R.id.exam_message_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(helper.getAdapterPosition(), item,0);
                }
            });
            helper.getView(R.id.news_pass).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(helper.getAdapterPosition(), item,1);
                }
            });
            helper.getView(R.id.news_refuse).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(helper.getAdapterPosition(), item,2);
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position, NewsExam data,int clickType);
    }

}
