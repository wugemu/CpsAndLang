package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.tabs.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahtrun.mytablayout.ProxyDrawable2;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.HaoWuClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassTabAdatper {
    private TabLayout tb_limittime;
    private Context context;
    private int selectPostition;//选中的场
    private List<HaoWuClass> categoryList;
    private Handler handler;

    public ClassTabAdatper(Context context, TabLayout tb_limittime, Handler handler) {
        this.tb_limittime = tb_limittime;
        this.context = context;
        this.handler = handler;
        tb_limittime.setTabMode(TabLayout.MODE_FIXED);
    }

    public void creatTimeTab(List<HaoWuClass> categoryList) {
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        this.categoryList = categoryList;
        tb_limittime.removeAllTabs();
        for (int position = 0; position < categoryList.size(); position++) {
            tb_limittime.addTab(tb_limittime.newTab());
            //依次获取标签
            TabLayout.Tab tab = tb_limittime.getTabAt(position);
            //为每个标签设置布局
            tab.setCustomView(R.layout.child_classtab);
        }
//        reflex();
    }


    public void refreshTimeTab() {
        for (int position = 0; position < tb_limittime.getTabCount(); position++) {
            HaoWuClass category = categoryList.get(position);

            //依次获取标签
            TabLayout.Tab tab = tb_limittime.getTabAt(position);
            HomeTimeTabHolder limitTimeVH = (HomeTimeTabHolder) tab.getCustomView().getTag();
            if (limitTimeVH == null) {
                limitTimeVH = new HomeTimeTabHolder(tab.getCustomView());
                tab.getCustomView().setTag(limitTimeVH);
            }
            limitTimeVH.tvTab.setText(category.getName());
//            if (position%2==0){
//                limitTimeVH.llClassTab.setBackgroundResource(R.color.colorGreenGg);
//            }else{
//                limitTimeVH.llClassTab.setBackgroundResource(R.color.red);
//            }

//            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) limitTimeVH.tvTab.getLayoutParams();

            //为标签填充数据
            if (selectPostition == position) {
                limitTimeVH.tvTab.getPaint().setFakeBoldText(true);
            } else {
                limitTimeVH.tvTab.getPaint().setFakeBoldText(false);
            }

//            limitTimeVH.tv_limite_time.setText(category.getShowTime());
//            if (limitTimeBean.getActiveState() == -1) {
//                limitTimeVH.tv_limite_tip.setText("已结束");
//            } else if (limitTimeBean.getActiveState() == 1) {
//                limitTimeVH.tv_limite_tip.setText("秒杀中");
//            } else if (limitTimeBean.getActiveState() == 2) {
//                limitTimeVH.tv_limite_tip.setText("预热中");
//            }
            final int nowPosition = position;
            tab.getCustomView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectPostition(nowPosition);
                    if (handler != null) {
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = nowPosition;
                        handler.sendMessage(message);
                    }
                }
            });
        }

//        reflex();
    }

    public int getSelectPostition() {
        return selectPostition;
    }

    public HaoWuClass getItemSelect(int positon) {
        if (categoryList != null && categoryList.size() > positon) {
            return categoryList.get(positon);
        }
        return null;
    }

    public int getItemCount() {
        if (categoryList == null) {
            return 0;
        }
        return categoryList.size();
    }

    public void refershPos(int selectPostition){
        this.selectPostition = selectPostition;
        refreshTimeTab();
    }

    public void setSelectPostition(final int selectPostition) {
       refershPos(selectPostition);
        tb_limittime.postDelayed(new Runnable() {
            @Override
            public void run() {
                TabLayout.Tab selectTab = tb_limittime.getTabAt(selectPostition);
                if (selectTab != null) {
                    selectTab.select(); //默认选中某项放在加载viewpager之后
                }
//              tb_limittime.setScrollPosition(selectPostition,0,true);

            }
        }, 100);
    }

    static class HomeTimeTabHolder {
        @BindView(R.id.tv_tab_name)
        TextView tvTab;

        HomeTimeTabHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void reflex() {
        if(tb_limittime!=null){
            //了解源码得知 线的宽度是根据 tabView的宽度来设置的
            tb_limittime.post(new Runnable() {
                @Override
                public void run() {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tb_limittime.getChildAt(0);
                    //拿到tabLayout的mTabStrip属性
                    mTabStrip.setBackgroundDrawable(new ProxyDrawable2(mTabStrip));
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        tabView.setPadding(0, 0, 0, 0);
//                        int width = (int)context.getResources().getDimension(R.dimen.home_time_width);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width= LinearLayout.LayoutParams.WRAP_CONTENT;
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
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }
                }
            });

        }
    }

}
