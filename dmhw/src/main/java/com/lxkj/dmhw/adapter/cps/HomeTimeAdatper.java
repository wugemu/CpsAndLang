package com.lxkj.dmhw.adapter.cps;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.LimitTimeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTimeAdatper {
    private TabLayout tb_limittime;
    private Context context;
    private int selectPostition;//选中的场
    private List<LimitTimeBean> limitTimeBeans;
    private Handler handler;
    public HomeTimeAdatper(Context context, TabLayout tb_limittime, Handler handler){
        this.tb_limittime=tb_limittime;
        this.context=context;
        this.handler=handler;
        tb_limittime.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void creatTimeTab(List<LimitTimeBean> limitTimeBeans){
        if(limitTimeBeans==null){
            limitTimeBeans=new ArrayList<>();
        }
        this.limitTimeBeans=limitTimeBeans;
        tb_limittime.removeAllTabs();
        for (int position=0;position<limitTimeBeans.size();position++){
            tb_limittime.addTab(tb_limittime.newTab());
            //依次获取标签
            TabLayout.Tab tab = tb_limittime.getTabAt(position);
            //为每个标签设置布局
            tab.setCustomView(R.layout.child_limittime_new);
        }
//        reflex();
    }


    public void refreshTimeTab(){
        for (int position = 0; position < tb_limittime.getTabCount(); position++) {
            //依次获取标签
            TabLayout.Tab tab = tb_limittime.getTabAt(position);
            HomeTimeTabHolder limitTimeVH = (HomeTimeTabHolder) tab.getCustomView().getTag();
            if(limitTimeVH==null){
                limitTimeVH=new HomeTimeTabHolder(tab.getCustomView());
                tab.getCustomView().setTag(limitTimeVH);
            }
            //为标签填充数据
            if(selectPostition==position){
                limitTimeVH.tv_limite_time.setTextColor(context.getResources().getColor(R.color.colorGoldMain));
                limitTimeVH.tv_limite_tip.setTextColor(context.getResources().getColor(R.color.colorWhite));
                limitTimeVH.tv_limite_time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                limitTimeVH.tv_limite_tip.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                limitTimeVH.view_indicator.setVisibility(View.INVISIBLE);
                limitTimeVH.tv_limite_tip.setBackgroundResource(R.drawable.bg_rect_gold_9dp);
            }else {
                limitTimeVH.tv_limite_time.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                limitTimeVH.tv_limite_tip.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
                limitTimeVH.tv_limite_time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                limitTimeVH.tv_limite_tip.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                limitTimeVH.view_indicator.setVisibility(View.INVISIBLE);
                limitTimeVH.tv_limite_tip.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            }
            LimitTimeBean limitTimeBean=limitTimeBeans.get(position);
            limitTimeVH.tv_limite_time.setText(limitTimeBean.getShowTime());
            if(limitTimeBean.getActiveState()==-1){
                limitTimeVH.tv_limite_tip.setText("已结束");
            }else if(limitTimeBean.getActiveState()==1){
                limitTimeVH.tv_limite_tip.setText("秒杀中");
            }else if(limitTimeBean.getActiveState()==2){
                limitTimeVH.tv_limite_tip.setText("预热中");
            }
            final int nowPosition=position;
            tab.getCustomView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectPostition(nowPosition);
                    if(handler!=null) {
                        Message message=new Message();
                        message.what=1;
                        message.arg1=nowPosition;
                        handler.sendMessage(message);
                    }
                }
            });
        }
    }

    public int getSelectPostition() {
        return selectPostition;
    }

    public LimitTimeBean getItemSelect(int positon){
        if(limitTimeBeans!=null&&limitTimeBeans.size()>positon){
            return limitTimeBeans.get(positon);
        }
        return null;
    }

    public int getItemCount(){
        if(limitTimeBeans==null){
            return 0;
        }
        return limitTimeBeans.size();
    }

    public void setSelectPostition(final int selectPostition) {
        this.selectPostition = selectPostition;
        refreshTimeTab();
        tb_limittime.postDelayed(new Runnable() {
            @Override
            public void run() {
                TabLayout.Tab selectTab=tb_limittime.getTabAt(selectPostition);
                if(selectTab!=null){
                    selectTab.select(); //默认选中某项放在加载viewpager之后
                }
//              tb_limittime.setScrollPosition(selectPostition,0,true);

            }
        }, 100);
    }

    class HomeTimeTabHolder {
        @BindView(R.id.ll_saletime_time)
        LinearLayout ll_saletime_time;
        @BindView(R.id.tv_limite_time)
        TextView tv_limite_time;
        @BindView(R.id.tv_limite_tip)
        TextView tv_limite_tip;
        @BindView(R.id.view_indicator)
        View view_indicator;
        public HomeTimeTabHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


//    public void reflex() {
//        if(tb_limittime!=null){
//            //了解源码得知 线的宽度是根据 tabView的宽度来设置的
//            tb_limittime.post(new Runnable() {
//                @Override
//                public void run() {
//                    //拿到tabLayout的mTabStrip属性
//                    LinearLayout mTabStrip = (LinearLayout) tb_limittime.getChildAt(0);
//                    mTabStrip.setBackgroundDrawable(new ProxyDrawable(mTabStrip));
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//                        tabView.setPadding(0, 0, 0, 0);
//                        int width = (int)context.getResources().getDimension(R.dimen.home_time_width);
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width= LinearLayout.LayoutParams.WRAP_CONTENT;
//                        int margin1 = (int) context.getResources().getDimension(R.dimen.fab_margin);
//                        int margin2 = (int) context.getResources().getDimension(R.dimen.view_toview);
//                        if(i==0){
//                            params.leftMargin = margin1;
//                        }else {
//                            params.leftMargin = margin2;
//                        }
//                        if(i==mTabStrip.getChildCount()-1){
//                            params.rightMargin = margin1;
//                        }else {
//                            params.rightMargin = margin2;
//                        }
//                        tabView.setLayoutParams(params);
//
//                        tabView.invalidate();
//                    }
//                }
//            });
//
//        }
//    }
}
