package com.lxkj.dmhw.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.RecommendSort;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 爆款推荐
 */

public class TwoFragment_RecommendAll extends BaseFragment {

    @BindView(R.id.recommend_content)
    ViewPager recommend_content;
    @BindView(R.id.recommend_magic)
    MagicIndicator recommend_magic;
    private FragmentManager limitFragmentManager;
    private ArrayList<Fragment> limitfragments;
    private FragmentAdapter limitfragmentAdapter;
    public static int currentPos=0;
    public static String currentFragmentName="";
    public static TwoFragment_RecommendAll getInstance() {
        TwoFragment_RecommendAll fragment = new TwoFragment_RecommendAll();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_recommendall, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        //请求爆款分类
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "RecommmendType", HttpCommon.RecommmendType);
    }

    @Override
    public void onCustomized() {
    }

    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.RecommmendTypeSuccess) {
            ArrayList<RecommendSort> list=(ArrayList<RecommendSort>)message.obj;
            if (list!=null&&list.size()>0){
                RecommendMagic(list);
            }
        }

    }

    private void RecommendMagic(ArrayList<RecommendSort> tempList) {
        // 初始化FragmentManager对象
        limitFragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        limitfragments = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            TwoFragment_Recommend twoFragment_recommend = TwoFragment_Recommend.getInstance(i,tempList.get(i).getItemtype());
            limitfragments.add(twoFragment_recommend);
        }
        limitfragmentAdapter = new FragmentAdapter(limitFragmentManager, limitfragments);
        recommend_content.setAdapter(limitfragmentAdapter);
        recommend_content.setOffscreenPageLimit(tempList.size());
        recommend_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int index) {
                currentPos=index;
                currentFragmentName=tempList.get(index).getItemtype();
                TwoFragment_Recommend twoFragment_recommend = (TwoFragment_Recommend) limitfragments.get(index);
                twoFragment_recommend.onResume();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tempList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.recommend_sort);
                TextView limitMagicName = commonPagerTitleView.findViewById(R.id.limit_magic_name);
                LinearLayout limit_magic_content_layout=commonPagerTitleView.findViewById(R.id.limit_magic_content_layout);
                limitMagicName.setText(tempList.get(index).getItemname());
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        currentFragmentName=tempList.get(index).getItemtype();
                        limit_magic_content_layout.setBackgroundResource(R.drawable.recommend_sort_check_bg);
                        limitMagicName.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        limit_magic_content_layout.setBackgroundResource(0);
                        limitMagicName.setTextColor(Color.parseColor("#666666"));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPos=index;
                        recommend_content.setCurrentItem(index);
                        currentFragmentName=tempList.get(index).getItemtype();
                        TwoFragment_Recommend twoFragment_recommend = (TwoFragment_Recommend) limitfragments.get(index);
                        twoFragment_recommend.onResume();
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        recommend_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(recommend_magic, recommend_content);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void refresh(){
        if (limitfragments!=null&&limitfragments.size()>0) {
            TwoFragment_Recommend twoFragment_recommend = (TwoFragment_Recommend) limitfragments.get(currentPos);
            twoFragment_recommend.onResume();
        }
    }
}
