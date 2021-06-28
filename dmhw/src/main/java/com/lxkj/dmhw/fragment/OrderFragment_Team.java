package com.lxkj.dmhw.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 团队推广订单
 * Created by Administrator on 2017/12/22 0022.
 */

public class OrderFragment_Team extends BaseFragment {

    @BindView(R.id.fragment_order_team_content)
    ViewPager fragmentOrderTeamContent;
    @BindView(R.id.fragment_order_team_magic)
    MagicIndicator fragmentOrderTeamMagic;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private OrderFragment_TeamFragment orderFragmentTeamFragmentOne;
    private OrderFragment_TeamFragment orderFragmentTeamFragmentTwo;
    private OrderFragment_TeamFragment orderFragmentTeamFragmentThree;
    private OrderFragment_TeamFragment orderFragmentTeamFragmentFour;
    private OrderFragment_TeamFragment orderFragmentTeamFragmentFive;
    private String[] magicName = {"全部", "已付款", "已收货", "已结算", "已退款"};
    String platform;
    public static OrderFragment_Team getInstance() {
        OrderFragment_Team fragment = new OrderFragment_Team();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_team, null);
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
        switch (fragmentOrderTeamContent.getCurrentItem()) {
            case 0:
                orderFragmentTeamFragmentOne.calendar(startTime, endTime);
                break;
            case 1:
                orderFragmentTeamFragmentTwo.calendar(startTime, endTime);
                break;
            case 2:
                orderFragmentTeamFragmentThree.calendar(startTime, endTime);
                break;
            case 3:
                orderFragmentTeamFragmentFour.calendar(startTime, endTime);
                break;
            case 4:
                orderFragmentTeamFragmentFive.calendar(startTime, endTime);
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
        orderFragmentTeamFragmentOne = OrderFragment_TeamFragment.getInstance(0);
        orderFragmentTeamFragmentTwo = OrderFragment_TeamFragment.getInstance(1);
        orderFragmentTeamFragmentThree = OrderFragment_TeamFragment.getInstance(2);
        orderFragmentTeamFragmentFour = OrderFragment_TeamFragment.getInstance(3);
        orderFragmentTeamFragmentFive = OrderFragment_TeamFragment.getInstance(4);
        fragments.add(orderFragmentTeamFragmentOne);
        fragments.add(orderFragmentTeamFragmentTwo);
        fragments.add(orderFragmentTeamFragmentThree);
        fragments.add(orderFragmentTeamFragmentFour);
        fragments.add(orderFragmentTeamFragmentFive);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragmentOrderTeamContent.setOffscreenPageLimit(4);
        fragmentOrderTeamContent.setAdapter(fragmentAdapter);
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
                scaleTransitionPagerTitleView.setOnClickListener(v -> fragmentOrderTeamContent.setCurrentItem(index));
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
        fragmentOrderTeamMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragmentOrderTeamMagic, fragmentOrderTeamContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
