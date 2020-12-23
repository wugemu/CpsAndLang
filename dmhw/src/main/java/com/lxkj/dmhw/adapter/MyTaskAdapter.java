package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MyTaskList;
import com.orhanobut.logger.Logger;

/**
 * 我的任务适配器
 */

public class MyTaskAdapter extends BaseQuickAdapter<MyTaskList.dailyItem, BaseViewHolder> {
    String color0="#FFFFFF";//未完成
    String color1="#FFFFFF";//已完成
    String color2="#FFFFFF";//可领取
    private  Context con;
    private MyTaskAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(MyTaskAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public MyTaskAdapter(Context context) {
        super(R.layout.adapter_my_task);
        this.con=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, MyTaskList.dailyItem item) {
        try {
            TextPaint tp;
            TextView  main_title_txt =  helper.getView(R.id.main_title_txt);
            tp=main_title_txt.getPaint();
            tp.setFakeBoldText(true);
         helper.setText(R.id.main_title_txt,item.getTitle());
         helper.setText(R.id.main_subtitle_txt,item.getRemark());
         helper.setText(R.id.task_get_count,"+"+item.getValue());
        //regist：注册，search：标题搜索，fristbuy：自身首购，sign：签到，childfristbuy：好友首购，
        // shareshop：商品分享，invite：好友邀请，daybuy：每日首购，push：查看推送
            //分享商品和分享好友 使用cnt?=max,来判断任务已完成 其他任务就是按照 cnt=0未完成 cnt=1完成
         TextView do_some_txt= helper.getView(R.id.do_some_txt);
          String cnt = item.getCnt();
          helper.setGone(R.id.comm_layout,true);
          helper.setGone(R.id.shougou_money_layout,false);
            switch (item.getType()) {
                case "regist":
                    if (cnt.equals("0")) {
                        helper.setText(R.id.do_some_txt, "去注册");
                        helper.setTextColor(R.id.do_some_txt, Color.parseColor(color0));
                        helper.setBackgroundRes(R.id.do_some_txt, R.drawable.task_btn_bg);
                    } else {
                        helper.setText(R.id.do_some_txt, "已完成");
                        helper.setTextColor(R.id.do_some_txt, Color.parseColor(color1));
                        helper.setBackgroundRes(R.id.do_some_txt, R.drawable.task_finish_bg);
                    }
                    break;
                case "search":
                    if (cnt.equals("0")) {
                        do_some_txt.setText("去完成");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "fristbuy":
                    helper.setGone(R.id.comm_layout,false);
                    helper.setGone(R.id.shougou_money_layout,true);
                    helper.setText(R.id.taoli_save_money,item.getValue());
                    if (cnt.equals("0")) {
                        do_some_txt.setText("去购物");
                        do_some_txt.setTextColor(Color.WHITE);
                        do_some_txt.setBackgroundResource(R.drawable.friend_firstbuy_bg);
                    } else if(cnt.equals("2")) {
                        do_some_txt.setText("可领取");
                        do_some_txt.setTextColor(Color.parseColor(color2));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_btn_bg));
                    }else{
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "invite":
                    if (Integer.parseInt(cnt) < Integer.parseInt(item.getMax())) {
                        int count=Integer.parseInt(item.getMax())-Integer.parseInt(cnt);
                        do_some_txt.setText("剩"+count+"次");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "shareshop":
                    if (Integer.parseInt(cnt)<=0){
                        do_some_txt.setText("看示例");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    }
                    else if(Integer.parseInt(cnt) < Integer.parseInt(item.getMax())) {
                        int count=Integer.parseInt(item.getMax())-Integer.parseInt(cnt);
                        do_some_txt.setText("剩"+count+"次");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "daybuy":
                    if (cnt.equals("0")) {
                        do_some_txt.setText("去购物");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "childfristbuy":
                    helper.setGone(R.id.comm_layout,false);
                    helper.setGone(R.id.shougou_money_layout,true);
                    helper.setText(R.id.taoli_save_money,item.getValue());
                    if (Integer.parseInt(cnt) <= 0) {
                        do_some_txt.setText("去邀请");
                        do_some_txt.setTextColor(Color.WHITE);
                        do_some_txt.setBackgroundResource(R.drawable.friend_firstbuy_bg);
                    } else {
                        do_some_txt.setText("可领取");
                        do_some_txt.setTextColor(Color.parseColor(color2));
                        helper.setText(R.id.taoli_save_money,item.getValue());
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_btn_bg));
                    }
                    break;
                case "push":
                    if (cnt.equals("0")) {
                        do_some_txt.setText("去查看");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
                case "sign":
                    if (cnt.equals("0")) {
                        do_some_txt.setText("去签到");
                        do_some_txt.setTextColor(Color.parseColor(color0));
                        do_some_txt.setBackgroundResource(R.drawable.task_btn_bg);
                    } else {
                        do_some_txt.setText("已完成");
                        do_some_txt.setTextColor(Color.parseColor(color1));
                        do_some_txt.setBackground(con.getResources().getDrawable(R.drawable.task_finish_bg));
                    }
                    break;
            }
            do_some_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(helper.getAdapterPosition(), item);
                }
            });
        } catch (Exception e) {
            Logger.e(e, "我的任务");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position, MyTaskList.dailyItem data);
    }
}
