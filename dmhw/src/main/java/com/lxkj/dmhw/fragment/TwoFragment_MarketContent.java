package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.adapter.MarketTypeAdapter;
import com.lxkj.dmhw.adapter.MarketingAdapter;
import com.lxkj.dmhw.bean.MarketSort;
import com.lxkj.dmhw.bean.Marketing;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.OneKeyShareDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 营销素材
 */

public class TwoFragment_MarketContent extends BaseFragment {

    @BindView(R.id.market_content)
    ViewPager market_content;
    @BindView(R.id.slide_seek)
    SeekBar slide_seek;
    @BindView(R.id.market_head_recycler)
    RecyclerView market_head_recycler;
    @BindView(R.id.fragment_other_content)
    FrameLayout fragmentOtherContent;
    @BindView(R.id.fragment_one_content_layout)
    RelativeLayout fragment_one_content_layout;

    private MarketingAdapter adapter;
    private Marketing marketing = new Marketing();
    private String type = "";
    public static String currentFragmentName="";
    public static int currentPos=100;
    public static TwoFragment_MarketContent getInstance() {
        TwoFragment_MarketContent fragment = new TwoFragment_MarketContent();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_marketcontent, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        market_head_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        market_head_recycler.setNestedScrollingEnabled(false);
        //请求分类
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "MarketingType", HttpCommon.getmarketingtype);
    }

    @Override
    public void onCustomized() {
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.MarketingTypeSuccess) {
            ArrayList<MarketSort> list=(ArrayList<MarketSort>)message.obj;
            if (list!=null&&list.size()>0){
                MarketMagic(list);
            }
        }

    }



    private FragmentManager limitFragmentManager;
    private ArrayList<Fragment> limitfragments;
    private FragmentAdapter limitfragmentAdapter;
    TwoFragment_MarketingAll mainLimitBuyFragmentAll=null;
    private void MarketMagic(ArrayList<MarketSort> tempList){
        if (tempList.size()>4){
            slide_seek.setVisibility(View.VISIBLE);
        }
        //全部
        mainLimitBuyFragmentAll = TwoFragment_MarketingAll.getInstance(100,"");
        switchFragment(mainLimitBuyFragmentAll);

        MarketTypeAdapter marketTypeAdapter =new MarketTypeAdapter(getActivity(),width/4);
        market_head_recycler.setAdapter(marketTypeAdapter);
        marketTypeAdapter.addData(tempList);
        // 初始化FragmentManager对象
        limitFragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        limitfragments = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            TwoFragment_Marketing  mainLimitBuyFragment = TwoFragment_Marketing.getInstance(i,tempList.get(i).getKey());
            limitfragments.add(mainLimitBuyFragment);
        }
        limitfragmentAdapter = new FragmentAdapter(limitFragmentManager, limitfragments);
        market_content.setAdapter(limitfragmentAdapter);

        market_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < marketTypeAdapter.getData().size(); j++) {
                    if (i==j){
                        marketTypeAdapter.getData().get(i).setIsSelect(1);
                    }else{
                        marketTypeAdapter.getData().get(j).setIsSelect(0);
                    }
                }
                marketTypeAdapter.notifyDataSetChanged();
                if (i>4){
                    market_head_recycler.scrollToPosition(i);
                }
                currentFragmentName=marketTypeAdapter.getData().get(i).getKey();
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        marketTypeAdapter.setOnItemClickListener(new MarketTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MarketSort data, int pos) {
                fragment_one_content_layout.setVisibility(View.GONE);
                market_content.setVisibility(View.VISIBLE);
                for (int j = 0; j < marketTypeAdapter.getData().size(); j++) {
                    if (pos==j){
                        marketTypeAdapter.getData().get(pos).setIsSelect(1);
                    }else{
                        marketTypeAdapter.getData().get(j).setIsSelect(0);
                    }
                }
                marketTypeAdapter.notifyDataSetChanged();
                market_content.setCurrentItem(pos);
                currentPos=pos;
                TwoFragment_Marketing twoFragment_marketing = (TwoFragment_Marketing) limitfragments.get(pos);
                twoFragment_marketing.onResume();
            }
        });

        slide_seek.setPadding(0, 0, 0, 0);
        slide_seek.setThumbOffset(0);
        market_head_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //显示区域的高度。
                int extent = recyclerView.computeHorizontalScrollExtent();
                //整体的高度，注意是整体，包括在显示区域之外的。
                int range = recyclerView.computeHorizontalScrollRange();
                //已经向下滚动的距离，为0时表示已处于顶部。
                int offset = recyclerView.computeHorizontalScrollOffset();
                //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
//                GradientDrawable gradientDrawable =(GradientDrawable) slide_seek.getThumb();
                //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
//                gradientDrawable.setSize(Utils.dipToPixel(R.dimen.dp_14),Utils.dipToPixel(R.dimen.dp_3));
                //设置可滚动区域
                slide_seek.setMax((int)(range-extent));
                if (dx==0){
                    slide_seek.setProgress(0);
                }else if (dx>0){
                    slide_seek.setProgress(offset);
                }else if (dx<0){
                    slide_seek.setProgress(offset);
                }
            }
        });
    }

    /**
     * 切换fragment
     * @param targetFragment
     */
    // Fragment装载器
    private Fragment currentFragment = new Fragment();
    private void switchFragment(Fragment targetFragment) {
        if (currentFragment != targetFragment) {
            FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.fragment_other_content, targetFragment).commitAllowingStateLoss();
            } else {
                transaction.hide(currentFragment).show(targetFragment).commitAllowingStateLoss();
            }
            currentFragment = targetFragment;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void refresh(){
        if (mainLimitBuyFragmentAll!=null&&currentPos==100) {
            mainLimitBuyFragmentAll.onResume();
        }else{
            if (limitfragments!=null&&limitfragments.size()>0) {
                TwoFragment_Marketing twoFragment_marketing = (TwoFragment_Marketing) limitfragments.get(currentPos);
                twoFragment_marketing.onResume();
            }
        }
    }
}
