package com.ahtrun.mytablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class HomeTablayout extends FrameLayout {
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomePageAdapter adapter;
    private TabHolder holder;
    public HomeTablayout(Context context) {
        this(context,null);
    }

    public HomeTablayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeTablayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView(context);

    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.home_tablayout,null);
        addView(view);
        tabLayout=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.pager_content);

    }


    public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener listener){
        if(tabLayout!=null){
            tabLayout.addOnTabSelectedListener(listener);
        }
    }

    public void init(int mode, List<Fragment> fragments, List<String> mTitles, FragmentManager fragmentManager){
        if(adapter!=null) {
            return;
        }
        setTabMode(mode);
        if(tabLayout!=null&&viewPager!=null&&fragments!=null&&fragments.size()>0&&mTitles!=null&&mTitles.size()>0){
            holder = null;
            for (int i=0;i<fragments.size();i++){
                tabLayout.addTab(tabLayout.newTab());
            }
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                //依次获取标签
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                //为每个标签设置布局
                tab.setCustomView(R.layout.tab_coustom);
                holder = new TabHolder(tab.getCustomView());
                //为标签填充数据
                holder.tvName.setText(mTitles.get(i));
                final int p=i;
                holder.tvName.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewPager!=null){
                            viewPager.setCurrentItem(p);
                        }
                    }
                });
            }
            adapter = new HomePageAdapter(fragmentManager, fragments);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tabLayout.getTabAt(position).select();
                    changeDisplay(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setAdapter(adapter);
        }
    }

    public void changeDisplay(int postion){//只改变颜色
        for (int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            holder = new TabHolder(tab.getCustomView());
            if (postion==i){
                holder.tvName.setSelected(true);
                holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                //恢复为默认字体大小
                holder.tvName.setTextSize(18);
            }else{
                holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.tvName.setSelected(false);
                //恢复为默认字体大小
                holder.tvName.setTextSize(15);
            }

        }
    }
    public void setPos(int pos){//设置paper
        if (viewPager!=null){
            viewPager.setCurrentItem(pos);
            if (pos==0){
                changeDisplay(pos);
            }
        }
    }

    public  int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void setTabMode(int mode){
        if(tabLayout!=null){
            tabLayout.setTabMode(mode);
        }
    }

}
