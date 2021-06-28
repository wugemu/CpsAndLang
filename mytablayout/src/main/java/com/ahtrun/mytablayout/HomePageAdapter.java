package com.ahtrun.mytablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    private List<String> mTitles ;


    public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        if (fragments == null) {
            this.fragments = new ArrayList<Fragment>();
        } else {
            this.fragments = fragments;
        }
    }

    public List<String> getmTitles() {
        return mTitles;
    }

    public void setmTitles(List<String> mTitles) {
        if(this.mTitles==null){
            this.mTitles=new ArrayList<String>();
        }else {
            this.mTitles.clear();
        }
        this.mTitles.addAll(mTitles);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
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
        return mTitles.get(position);
    }

    public void setFragments(List<Fragment> fragments){
        this.fragments.clear();
        this.fragments.addAll(fragments);
    }

}
