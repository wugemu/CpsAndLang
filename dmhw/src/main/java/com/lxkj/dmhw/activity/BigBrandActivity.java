package com.lxkj.dmhw.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.BigBrand;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleViewNew;
import com.lxkj.dmhw.fragment.BigBrandFragment;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BigBrandActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.bigbrand_magic)
    MagicIndicator bigbrand_magic;
    @BindView(R.id.bigbrand_content)
    ViewPager bigbrand_content;
    @BindView(R.id.big_title)
    TextView big_title;
    TextPaint tp;
    private int position = 0;
    // 声明Fragment对象
    private BigBrandFragment bigBrandFragment;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // Fragment页面
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbrand);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        tp= big_title.getPaint();
        tp.setFakeBoldText(true);

        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "BigBrandCategorys", HttpCommon.BigBrandCategorys);
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.BigBrandCategorysSuccess) {
            magic((ArrayList<BigBrand>) message.obj);
        }
    }

    private void magic(ArrayList<BigBrand> list) {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            bigBrandFragment =BigBrandFragment.getInstance(list.get(i).getId());
            fragments.add(bigBrandFragment);
        }
        // 声明FragmentAdapter对象
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        bigbrand_content.setAdapter(fragmentAdapter);
        bigbrand_content.setOffscreenPageLimit(4);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleViewNew simplePagerTitleView = new ScaleTransitionPagerTitleViewNew(context);
                simplePagerTitleView.setText(list.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#eeeeee"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setTextSize(17);
                ((ScaleTransitionPagerTitleViewNew) simplePagerTitleView).setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bigbrand_content.setCurrentItem(index);
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
                indicator.setColors(Color.parseColor("#FCBF54"));
                return indicator;
            }
        });
        bigbrand_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(bigbrand_magic, bigbrand_content);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
