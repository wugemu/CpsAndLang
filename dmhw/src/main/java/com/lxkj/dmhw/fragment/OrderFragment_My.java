package com.lxkj.dmhw.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的推广订单
 * Created by Administrator on 2017/12/22 0022.
 */

public class OrderFragment_My extends BaseFragment {

    @BindView(R.id.fragment_order_my_content)
    ViewPager fragmentOrderMyContent;
    @BindView(R.id.fragment_order_my_magic)
    MagicIndicator fragmentOrderMyMagic;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private OrderFragment_MyFragment orderFragmentMyFragmentOne;
    private OrderFragment_MyFragment orderFragmentMyFragmentTwo;
    private OrderFragment_MyFragment orderFragmentMyFragmentThree;
    private OrderFragment_MyFragment orderFragmentMyFragmentFour;
    private OrderFragment_MyFragment orderFragmentMyFragmentFive;
    private String[] magicName = {"全部", "已付款", "已收货", "已结算", "已退款"};
    String platform;
    public static OrderFragment_My getInstance() {
        OrderFragment_My fragment = new OrderFragment_My();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_my, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {

    }

    @Override
    public void onCustomized() {
        magic();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    /**
     * 日期筛选
     * @param startTime
     * @param endTime
     */
    public void calendar(String startTime, String endTime) {
        switch (fragmentOrderMyContent.getCurrentItem()) {
            case 0:
                orderFragmentMyFragmentOne.calendar(startTime, endTime);
                break;
            case 1:
                orderFragmentMyFragmentTwo.calendar(startTime, endTime);
                break;
            case 2:
                orderFragmentMyFragmentThree.calendar(startTime, endTime);
                break;
            case 3:
                orderFragmentMyFragmentFour.calendar(startTime, endTime);
                break;
            case 4:
                orderFragmentMyFragmentFive.calendar(startTime, endTime);
                break;
        }
    }

    /**
     * 设置导航和viewpager
     */
    private void magic() {
        // 初始化FragmentManager对象
        fragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        orderFragmentMyFragmentOne = OrderFragment_MyFragment.getInstance(0);
        orderFragmentMyFragmentTwo = OrderFragment_MyFragment.getInstance(1);
        orderFragmentMyFragmentThree = OrderFragment_MyFragment.getInstance(2);
        orderFragmentMyFragmentFour = OrderFragment_MyFragment.getInstance(3);
        orderFragmentMyFragmentFive = OrderFragment_MyFragment.getInstance(4);
        fragments.add(orderFragmentMyFragmentOne);
        fragments.add(orderFragmentMyFragmentTwo);
        fragments.add(orderFragmentMyFragmentThree);
        fragments.add(orderFragmentMyFragmentFour);
        fragments.add(orderFragmentMyFragmentFive);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragmentOrderMyContent.setOffscreenPageLimit(4);
        fragmentOrderMyContent.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView scaleTransitionPagerTitleView=new ScaleTransitionPagerTitleView(context);
                scaleTransitionPagerTitleView.setText(magicName[index]);
                scaleTransitionPagerTitleView.setNormalColor(Color.parseColor("#333333"));
                scaleTransitionPagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                scaleTransitionPagerTitleView.setTextSize(15);
                scaleTransitionPagerTitleView.setMinScale(0.95f);
                scaleTransitionPagerTitleView.setOnClickListener(v -> fragmentOrderMyContent.setCurrentItem(index));
                return scaleTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_30));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(3);
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        fragmentOrderMyMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragmentOrderMyMagic, fragmentOrderMyContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
