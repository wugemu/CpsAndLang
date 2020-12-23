package com.lxkj.dmhw.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.LimitCatalog;
import com.lxkj.dmhw.fragment.LimitFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LimitActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.limit_magic)
    MagicIndicator limitMagic;
    @BindView(R.id.limit_content)
    ViewPager limitContent;
    public static int position = 0;
    // 声明Fragment对象
    private LimitFragment limitFragment;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // Fragment页面
    private ArrayList<Fragment> fragments;

    private String intentTime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        intentTime=getIntent().getStringExtra("time");
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "LimitCatalog", HttpCommon.LimitCatalog);
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.LimitCatalogSuccess) {
            magic((ArrayList<LimitCatalog>) message.obj);
        }
    }

    private void magic(ArrayList<LimitCatalog> list) {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            limitFragment = LimitFragment.getInstance(i,list.get(i).getCouponstart());
            fragments.add(limitFragment);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        limitContent.setAdapter(fragmentAdapter);
        limitContent.setOffscreenPageLimit(4);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.limit_magic);
                LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.limit_magic_layout);
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) limitMagicLayout.getLayoutParams();
                linearParams.width = width/5;
                limitMagicLayout.setLayoutParams(linearParams);
                TextView limitMagicTime = (TextView) commonPagerTitleView.findViewById(R.id.limit_magic_time);
                TextView limitMagicName = (TextView) commonPagerTitleView.findViewById(R.id.limit_magic_name);
                limitMagicTime.setText(Utils.getDateToString(Utils.getStringToDate(list.get(index).getCouponstart(), "yyyy-MM-dd HH:mm"), "HH:mm"));
                if (Utils.getStringToDate(list.get(index).getCouponstart(), "yyyy-MM-dd HH:mm") > Utils.getStringToDate(Utils.getCurrentDate("yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm")) {
                    if (Utils.getStringToDate(list.get(index).getCouponstart(), "yyyy-MM-dd HH:mm") >= Utils.getStringToDate(Utils.getNextDate("yyyy-MM-dd"), "yyyy-MM-dd")) {
                        limitMagicName.setText("明日开始");
                    } else {
                        limitMagicName.setText("即将开始");
                    }
                } else {
                    if (Utils.getStringToDate(list.get(index + 1).getCouponstart(), "yyyy-MM-dd HH:mm") > Utils.getStringToDate(Utils.getCurrentDate("yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm")) {
                        limitMagicName.setText("抢购中");
                        if(intentTime==null){
                            position=index;
                        }
                    } else {
                        limitMagicName.setText("已开抢");
                    }
                }

                if(intentTime!=null&&intentTime.equals(list.get(index).getCouponstart())){
                    position=index;
                }

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        limitMagicTime.setBackgroundColor(Color.parseColor("#F9D5AD"));
                        limitMagicTime.setTextColor(Color.parseColor("#4A392A"));
                        limitMagicName.setTextColor(Color.parseColor("#F9D5AD"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        limitMagicTime.setBackground(null);
                        limitMagicTime.setTextColor(Color.parseColor("#FFFFFF"));
                        limitMagicName.setTextColor(Color.parseColor("#FFFFFF"));
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
                        position=index;
                        limitContent.setCurrentItem(index);
                        LimitFragment limitFragment= (LimitFragment) fragments.get(index);
                        limitFragment.refresh();
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        limitMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(limitMagic, limitContent);
        limitContent.setCurrentItem(position);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }
}
