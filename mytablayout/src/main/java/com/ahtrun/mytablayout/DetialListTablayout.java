package com.ahtrun.mytablayout;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
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

public class DetialListTablayout extends FrameLayout {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter adapter;
    private ImageView ivBack;
    private View emptyView;
    private int resId;
    private int postion;
    public DetialListTablayout(Context context) {
        this(context,null);
    }

    public DetialListTablayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DetialListTablayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       initView(context);

    }

    /**
     * 设置tablayout字体颜色
     * @param unSelect
     * @param select
     */
    public void setTextColor(int unSelect,int select){
        if (tabLayout!=null){
            tabLayout.setTabTextColors(unSelect,select);
        }
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.custome_tablayout,null);
        addView(view);
        tabLayout=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.pager_content);
        ivBack=view.findViewById(R.id.iv_back);
        emptyView=view.findViewById(R.id.view);


    }
    public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener listener){
        if(tabLayout!=null){
            tabLayout.addOnTabSelectedListener(listener);
        }
    }
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        if(viewPager!=null){
            viewPager.addOnPageChangeListener(listener);
        }
    }
    public void init(int mode, Fragment[] fragments, String[] mTitles, FragmentManager fragmentManager,int resId,OnClickListener listener){
        refreshTab(tabLayout,adapter,fragmentManager);
        setTabMode(mode);
        if(tabLayout!=null&&viewPager!=null&&fragments!=null&&fragments.length>0&&mTitles!=null&&mTitles.length>0){
            for (int i=0;i<fragments.length;i++){
                tabLayout.addTab(tabLayout.newTab());
            }
            adapter=new MyFragmentPagerAdapter(fragmentManager,fragments);
            adapter.setmTitles(mTitles);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(pageChangeListener);
            tabLayout.setupWithViewPager(viewPager);
            this.resId=resId;
            if(resId!=0){
                ivBack.setImageResource(R.mipmap.back2);
                ivBack.setVisibility(VISIBLE);
                emptyView.setVisibility(VISIBLE);
                ivBack.setOnClickListener(listener);
            }
        }
    }


    public void init(int mode, Fragment[] fragments, String[] mTitles, FragmentManager fragmentManager){
        this.init(mode,fragments,mTitles,fragmentManager,0,null);
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
//            viewPager.setCurrentItem(position);
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
                        Field tabIndicator = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                        //API28以下为mTabStrip
//              Field tabIndicator = tabLayout.getClass().getDeclaredField("mTabStrip");
                        tabIndicator.setAccessible(true);
                        LinearLayout mTabStrip = (LinearLayout) tabIndicator.get(tabLayout);

//                        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                        mTabStrip.setBackgroundDrawable(new ProxyDrawable2(mTabStrip));
//                    int dp10 = (int) getResources().getDimension(R.dimen.view_toview_two);

                        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                            View tabView = mTabStrip.getChildAt(i);

                            //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                            Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                            mTextViewField.setAccessible(true);

                            TextView mTextView = (TextView) mTextViewField.get(tabView);
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
                            if(resId!=0){
                                int w=ivBack.getWidth();
                                if(w==0){
                                    ivBack.measure(0, 0);
                                    w = ivBack.getMeasuredWidth();
                                }
                                int screenWidth=getDisplayWidth(activity)-w*2;
                                margin=(int)((screenWidth/mTabStrip.getChildCount()-width)/2.0);
                            }else if(tabLayout.getTabMode()==TabLayout.MODE_FIXED){
                                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                                int screenWidth=getDisplayWidth(activity);
                                margin=(int)((screenWidth/mTabStrip.getChildCount()-width)/2.0);
                            }else{
                                margin = (int) getContext().getResources().getDimension(R.dimen.view_toview_two);
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


    public void reflex2(final Activity activity) {
        if(tabLayout!=null){
            //了解源码得知 线的宽度是根据 tabView的宽度来设置的
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        //拿到tabLayout的mTabStrip属性
                        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                        mTabStrip.setBackgroundDrawable(new NewRedProxyDrawable(mTabStrip));
//                    int dp10 = (int) getResources().getDimension(R.dimen.view_toview_two);

                        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                            View tabView = mTabStrip.getChildAt(i);

                            //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                            Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                            mTextViewField.setAccessible(true);

                            TextView mTextView = (TextView) mTextViewField.get(tabView);

                            mTextView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                            mTextView.setSingleLine(true);

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
                            if(resId!=0){
                                int w=ivBack.getWidth();
                                if(w==0){
                                    ivBack.measure(0, 0);
                                    w = ivBack.getMeasuredWidth();
                                }
                                int screenWidth=getDisplayWidth(activity)-w*2;
                                margin=(int)((screenWidth/mTabStrip.getChildCount()-width)/2.0);
                            }else if(tabLayout.getTabMode()==TabLayout.MODE_FIXED){
                                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                                int screenWidth=getDisplayWidth(activity);
                                margin=(int)((screenWidth/mTabStrip.getChildCount()-width)/2.0);
                            }else{
                                margin = (int) getContext().getResources().getDimension(R.dimen.view_toview_two);
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

    public int getPostion() {
        return postion;
    }

    public void changeDisplay(final int postion) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        this.postion=postion;
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field tabIndicator = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                    //API28以下为mTabStrip
//              Field tabIndicator = tabLayout.getClass().getDeclaredField("mTabStrip");
                    tabIndicator.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) tabIndicator.get(tabLayout);

//                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

//                    int dp10 = (int) getResources().getDimension(R.dimen.view_toview_two);
                    mTabStrip.setBackgroundDrawable(new NewRedProxyDrawable(mTabStrip));
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                        mTextViewField.setAccessible(true);
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//                     int margin= (int) ((params.width- mTextView.getWidth())/2.0);
//                        params.leftMargin = margin;
//                        params.rightMargin = margin;
//                        tabView.setLayoutParams(params);
//                        if(i==postion){
//                            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                        }else{
//                            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        }
                        tabView.invalidate();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
            }
        });

    }
    private void setTabMode(int mode){
        if(tabLayout!=null){
            tabLayout.setTabMode(mode);
        }
    }

    public static void refreshTab(TabLayout tabLayout, FragmentPagerAdapter adapter,FragmentManager fragmentManager){
        if(tabLayout!=null) {
            //刷新tablayout数据
            tabLayout.removeAllTabs();
        }
        if(adapter!=null){
            //刷新viewpage的adapter数据
            //获取FragmentManager实现类的class对象,这里指的就是FragmentManagerImpl
            refreshFragment(fragmentManager);
        }
    }
    public static void refreshFragment(FragmentManager fragmentManager){
        if(fragmentManager!=null) {
            Class<? extends FragmentManager> aClass = fragmentManager.getClass();
            try {
                //1.获取其mAdded字段
                Field f = aClass.getDeclaredField("mAdded");
                f.setAccessible(true);
                //强转成ArrayList
                ArrayList<Fragment> list = (ArrayList) f.get(fragmentManager);
                //清空缓存
                list.clear();

                //2.获取mActive字段
                f = aClass.getDeclaredField("mActive");
                f.setAccessible(true);
                //强转成SparseArray
                SparseArray<Fragment> array = (SparseArray) f.get(fragmentManager);
                //清空缓存
                array.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
