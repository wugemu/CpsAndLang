package com.lxkj.dmhw.fragment.cps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heytap.mcssdk.utils.LogUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FourFragmentAdapter340;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.adapter.cps.SpecilAdapter;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.RankingType;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.FourFragment_One;
import com.lxkj.dmhw.fragment.MainBottomFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecilFragment extends BaseFragment {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.AppBar_Layout)
    AppBarLayout AppBar_Layout;
    @BindView(R.id.bottom_navigate_magic)
    MagicIndicator jgq_new_magicindicator;
    @BindView(R.id.fragment_four_one_list)
    RecyclerView fragment_four_one_list;
    @BindView(R.id.navigate_content)
    ViewPager navigate_content;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // Fragment页面
    private ArrayList<Fragment> fragments;
    // 声明Fragment对象
    private MainBottomFragment mainBottomFragment;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;

    private SpecilAdapter specilAdapter;
    TextPaint tp;
    private HomePage homePage = new HomePage();
    private boolean ifClickTab;
    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specil, null);
        ButterKnife.bind(this, view);

        //获取首页接口
        showDialog();
        paramMap.clear();
        NetworkRequest.getInstance().POST(handler, paramMap, "HomePageInit", HttpCommon.HomePageInit);

        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
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
        if (message.what == LogicActions.HomePageInitSuccess) {
            //填充新金刚区数据  有新金刚区就不显示旧金刚区
            homePage = (HomePage) message.obj;
            if (homePage.getJGQSortList()!=null&&homePage.getJGQSortList().size()>0) {
                //新金刚区背景
                JGQNewMagic(homePage.getJGQSortList());
            }
        }
        dismissDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void refresh(){

    }
    private void JGQNewMagic(ArrayList<HomePage.JGQSort> list) {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.jgq_nav_magic);
                View split= commonPagerTitleView.findViewById(R.id.split);
                RelativeLayout.LayoutParams linearParamssplit =(RelativeLayout.LayoutParams)split.getLayoutParams();
                LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.nav_jgq_layout);
                RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams)limitMagicLayout.getLayoutParams();
                linearParams.width=(width-Utils.dipToPixel(R.dimen.dp_20))/5;
                limitMagicLayout.setLayoutParams(linearParams);
                if (index==0){
                    split.setVisibility(View.GONE);
                }else{
                    split.setVisibility(View.GONE);
                }
                TextView limitMagicTitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_jgq_title);
                ImageView sortImg = commonPagerTitleView.findViewById(R.id.sort_img);
                limitMagicTitle.setText(list.get(index).getName());
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        tp= limitMagicTitle.getPaint();
                        tp.setFakeBoldText(true);
                        limitMagicTitle.setTextColor(Color.parseColor("#151D23"));
                        limitMagicTitle .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        sortImg.setBackgroundColor(Color.parseColor("#151D23"));
                        sortImg.setImageResource(0);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        tp= limitMagicTitle.getPaint();
                        tp.setFakeBoldText(false);
                        limitMagicTitle.setTextColor(Color.parseColor("#151D23"));
                        limitMagicTitle .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        sortImg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        sortImg.setImageResource(0);
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
//                        ifClickTab=true;
//                        navigate_content.setCurrentItem(index);
//                        fragment_four_one_list.scrollToPosition(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        jgq_new_magicindicator.setNavigator(commonNavigator);

        // 初始化FragmentManager对象 没有实际显示作用 为了触发tab点击切换效果
        fragmentManager = getChildFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mainBottomFragment = new MainBottomFragment();
            fragments.add(mainBottomFragment);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        navigate_content.setAdapter(fragmentAdapter);
        navigate_content.setOffscreenPageLimit(list.size());
        //

        ViewPagerHelper.bind(jgq_new_magicindicator, navigate_content);




        specilAdapter=new SpecilAdapter(getActivity(),(int)(width-getResources().getDimension(R.dimen.dp_20)),list);
        fragment_four_one_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        fragment_four_one_list.setAdapter(specilAdapter);
        specilAdapter.setNewData(list);


        fragment_four_one_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.d("0.0","onScrollStateChanged:"+newState);
                if(newState==0){
                    ifClickTab=false;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!ifClickTab) {
                    if (fragment_four_one_list.getLayoutManager() != null) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) fragment_four_one_list.getLayoutManager();
                        int lastItem = layoutManager.findFirstVisibleItemPosition();
                        navigate_content.setCurrentItem(lastItem);
                    }
                }
            }
        });
    }
}