package com.lxkj.dmhw.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
//限时抢购无限轮播
public class FragmentDefineAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentList;
    public FragmentDefineAdapter(FragmentManager fm)
    {
        super(fm);
    }
    public FragmentDefineAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList)
    {
        super(fm);
        this.fragmentList=fragmentList;
    }
    @Override
    public Fragment getItem(int position)
    {
        //在这里不处理position的原因是因为getItem方法在
        //instantiateItem方法中调用。只要在调用前处理
        //position即可，以免重复处理
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        //处理position。让数组下标落在[0,fragmentList.size)中，防止越界
        position = position % fragmentList.size();

        return super.instantiateItem(container, position);
    }

}

