package com.ahtrun.mytablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by 1 on 2016/10/26.
 * ViewPager的适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    Fragment[] fragments;
    private String[] mTitles ;


    public MyFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        if (fragments == null) {
            this.fragments = new Fragment[]{};
        } else {
            this.fragments = fragments;
        }
    }

    public String[] getmTitles() {
        return mTitles;
    }

    public void setmTitles(String[] mTitles) {
        this.mTitles = mTitles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragments[arg0];
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
