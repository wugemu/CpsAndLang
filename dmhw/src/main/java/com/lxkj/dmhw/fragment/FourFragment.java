package com.lxkj.dmhw.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.RankingType;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.NetStateUtils;
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

/**
 * 首页第四页
 * Created by Android on 2018/6/9.
 */

public class FourFragment extends BaseFragment {

    @BindView(R.id.back)
    View back;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.fragment_four_content)
    ViewPager fragmentFourContent;
    @BindView(R.id.fragment_four_magic)
    MagicIndicator fragmentFourMagic;
    @BindView(R.id.network_mask)
    LinearLayout rl_network_mask;
    public static int currPos=0;
//    public static FourFragment getInstance() {
//        FourFragment fragment = new FourFragment();
//        return fragment;
//    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            statusBar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            statusBar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        rl_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomized();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             isFinish();
            }
        });
    }

    @Override
    public void onCustomized() {
        if (NetStateUtils.isNetworkConnected(getActivity())) {
            rl_network_mask.setVisibility(View.GONE);
            // 加载一级类目
               paramMap.clear();
               NetworkRequest.getInstance().POST(handler, paramMap, "RankingType", HttpCommon.RankingType);
        }else{
            toast(getResources().getString(R.string.net_work_unconnect));
            rl_network_mask.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.RankingTypeSuccess) {
            ArrayList<RankingType> categoryOnes = (ArrayList<RankingType>) message.obj;
            magic(categoryOnes);
        }
    }

    /**
     * 设置导航和viewpager
     */
    ArrayList<Fragment> fragments;
    FragmentManager fragmentManager;
    private void magic(final ArrayList<RankingType> list) {
        // 初始化FragmentManager对象
        fragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        // 声明Fragment对象
        for (int i = 0; i < list.size(); i++) {
            FourFragment_One fourFragment_one = FourFragment_One.getInstance(i,list.get(i).getId());
            fragments.add(fourFragment_one);
        }
        // 声明FragmentAdapter对象
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragmentFourContent.setAdapter(fragmentAdapter);
        fragmentFourContent.setOffscreenPageLimit(list.size());
        fragmentFourContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int index) {
                currPos=index;
                FourFragment_One fourFragment_one = (FourFragment_One) fragments.get(index);
                fourFragment_one.onResume();
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
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(list.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#eeeeee"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setTextSize(17);
                ((ScaleTransitionPagerTitleView) simplePagerTitleView).setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currPos=index;
                        fragmentFourContent.setCurrentItem(index);
                        FourFragment_One fourFragment_one = (FourFragment_One) fragments.get(index);
                        fourFragment_one.onResume();
                    }
                });

                return simplePagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_20));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(5);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        fragmentFourMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragmentFourMagic, fragmentFourContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void refresh(){
        if (fragments!=null&&fragments.size()>0) {
            FourFragment_One fourFragment_one = (FourFragment_One) fragments.get(currPos);
            fourFragment_one.onResume();
        }
    }
}
