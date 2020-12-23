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

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.CategoryOne;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.BrandFragment;
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

public class BrandActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.brand_magic)
    MagicIndicator brandMagic;
    @BindView(R.id.brand_content)
    ViewPager brandContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 加载一级类目
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "CategoryOne", HttpCommon.CategoryOne);
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.CategoryOneSuccess) {
            ArrayList<CategoryOne> categoryOnes = (ArrayList<CategoryOne>) message.obj;
            ArrayList<CategoryOne> list = new ArrayList<>();
            CategoryOne one = new CategoryOne();
            one.setShopclassname("全部");
            list.add(one);
            for (int i = 1; i < categoryOnes.size(); i++) {
                one = new CategoryOne();
                one.setShopclassone(categoryOnes.get(i).getShopclassone());
                one.setShopclassname(categoryOnes.get(i).getShopclassname());
                list.add(one);
            }
            magic(list);
        }
    }

    private void magic(ArrayList<CategoryOne> list) {
        // 初始化FragmentManager对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 声明Fragment对象
        for (int i = 0; i < (list.size()); i++) {
            if (i == 0) {
                BrandFragment brandFragment = BrandFragment.getInstance("");
                fragments.add(brandFragment);
            } else {
                BrandFragment brandFragment = BrandFragment.getInstance(list.get(i).getShopclassone());
                fragments.add(brandFragment);
            }
        }
        // 声明FragmentAdapter对象
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        brandContent.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(1f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(list.get(index).getShopclassname());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.mainColor));
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setOnClickListener(v -> brandContent.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_40));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_2));
                indicator.setColors(getResources().getColor(R.color.mainColor));
                return indicator;
            }
        });
        brandMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(brandMagic, brandContent);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }
}
