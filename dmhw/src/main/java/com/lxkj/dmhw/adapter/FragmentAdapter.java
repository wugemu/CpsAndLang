package com.lxkj.dmhw.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
/**
 * Fragment适配器
 * Created by HAO on 2016/8/10.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter{

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();


    public FragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
}
