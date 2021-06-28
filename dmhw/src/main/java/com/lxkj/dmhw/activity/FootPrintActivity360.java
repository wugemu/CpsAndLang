package com.lxkj.dmhw.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.FootprintOtherFragment;
import com.lxkj.dmhw.fragment.FootprintTaobaoFragment;
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
import butterknife.OnClick;

public class FootPrintActivity360 extends BaseActivity  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.fragment_footprint_magic)
    MagicIndicator fragment_footprint_magic;

    @BindView(R.id.fragment_footprint_content)
    ViewPager fragment_footprint_content;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;

    // 声明Fragment对象
    private FootprintTaobaoFragment footprintTaobaoFragment;
    private FootprintOtherFragment footprintOtherFragment;

    private String[] magicName = {"淘宝平台", "其他平台"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_360);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }


    /**
     * 设置导航和viewpager
     */
    private void magic() {
         //初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        footprintTaobaoFragment = new FootprintTaobaoFragment();
        footprintOtherFragment =new FootprintOtherFragment();
        fragments.add(footprintTaobaoFragment);
        fragments.add(footprintOtherFragment);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragment_footprint_content.setOffscreenPageLimit(2);
        fragment_footprint_content.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(magicName[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setMinScale(1.0f);
                simplePagerTitleView.setOnClickListener(v -> fragment_footprint_content.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_20));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        fragment_footprint_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragment_footprint_magic, fragment_footprint_content);
    }
}
