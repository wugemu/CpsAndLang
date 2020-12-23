package com.ahtrun.mytablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by 1 on 2018/3/21.
 */

public class LiveHomeTablayout extends FrameLayout {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter adapter;
    public LiveHomeTablayout(Context context) {
        this(context,null);
    }

    public LiveHomeTablayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LiveHomeTablayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.livehome_tablayout,null);
        addView(view);
        tabLayout=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.pager_content);
    }
    public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener listener){
        if(tabLayout!=null){
            tabLayout.setOnTabSelectedListener(listener);
        }
    }
    public void init(int mode, Fragment[] fragments, String[] mTitles, FragmentManager fragmentManager){
        setTabMode(mode);
        if(tabLayout!=null&&viewPager!=null&&fragments!=null&&fragments.length>0&&mTitles!=null&&mTitles.length>0){
            for (int i=0;i<fragments.length;i++){
                tabLayout.addTab(tabLayout.newTab());
            }
            adapter=new MyFragmentPagerAdapter(fragmentManager,fragments);
            viewPager.setAdapter(adapter);
            adapter.setmTitles(mTitles);
            viewPager.addOnPageChangeListener(pageChangeListener);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeDisplay(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void select(int position){
        if(tabLayout!=null&&position<tabLayout.getTabCount()){
            tabLayout.getTabAt(position).select();
            changeDisplay(position);
        }
    }

    public void reflex(final Activity activity) {
        if(tabLayout!=null){
            //了解源码得知 线的宽度是根据 tabView的宽度来设置的
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        //拿到tabLayout的mTabStrip属性
                        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                        mTabStrip.setBackgroundDrawable(new LiveProxyDrawable(mTabStrip));
//                    int dp10 = (int) getResources().getDimension(R.dimen.view_toview_two);

                        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                            View tabView = mTabStrip.getChildAt(i);

                            //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                            Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                            mTextViewField.setAccessible(true);

                            TextView mTextView = (TextView) mTextViewField.get(tabView);
                            mTextView.getPaint().setFakeBoldText(true);
                            tabView.setPadding(0, 0, 0, 0);

                            //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                            int width=0;
                            width = mTextView.getWidth();
                            if (width == 0) {
                                mTextView.measure(0, 0);
                                width = mTextView.getMeasuredWidth();
                            }

                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        int screenWidth= BBCUtil.getDisplayWidth(OrderListActivity.this);
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                            params.width= width;
                            int margin=0;
                            if(tabLayout.getTabMode()==TabLayout.MODE_FIXED){
                                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                                int screenWidth=getDisplayWidth(activity);
                                margin=(int)((screenWidth/mTabStrip.getChildCount()-width)/2.0);
                            }else{
                                margin = (int) getContext().getResources().getDimension(R.dimen.value_20dp);
                            }
                            params.leftMargin = margin;
                            params.rightMargin = margin;
                            tabView.setLayoutParams(params);

                            tabView.invalidate();
                        }

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
    public  int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    public void changeDisplay(final int postion) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

//                    int dp10 = (int) getResources().getDimension(R.dimen.view_toview_two);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//                     int margin= (int) ((params.width- mTextView.getWidth())/2.0);
//                        params.leftMargin = margin;
//                        params.rightMargin = margin;
//                        tabView.setLayoutParams(params);
                        if(i==postion){
                            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        }else{
                            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void setTabMode(int mode){
        if(tabLayout!=null){
            tabLayout.setTabMode(mode);
        }
    }

}
