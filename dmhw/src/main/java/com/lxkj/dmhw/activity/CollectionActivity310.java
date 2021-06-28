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
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.CollectionFragment;
import com.lxkj.dmhw.fragment.CollectionFragmentPJW;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity310 extends BaseActivity  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.fragment_collect_magic)
    MagicIndicator fragment_collect_magic;

    @BindView(R.id.fragment_collect_content)
    ViewPager fragment_collect_content;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;

    // 声明Fragment对象
    private CollectionFragment collectionFragment;
    private CollectionFragmentPJW collectionFragmentPJW;

    private String[] magicName = {"淘宝平台", "其他平台"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_310);
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
        collectionFragment = new CollectionFragment();
        collectionFragmentPJW =new CollectionFragmentPJW();
        fragments.add(collectionFragment);
        fragments.add(collectionFragmentPJW);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragment_collect_content.setOffscreenPageLimit(2);
        fragment_collect_content.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.collect_magic);

                TextView simplePagerTitleView = (TextView) commonPagerTitleView.findViewById(R.id.collect_magic_time);
                LinearLayout collect_magic_layout=(LinearLayout)commonPagerTitleView.findViewById(R.id.collect_magic_layout);
                simplePagerTitleView.setText(magicName[index]);
                commonPagerTitleView.setOnClickListener(v -> fragment_collect_content.setCurrentItem(index));
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        collect_magic_layout.setBackgroundResource(R.drawable.bg_rect_black_20dp);
                        simplePagerTitleView.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        collect_magic_layout.setBackground(null);
                        simplePagerTitleView.setTextColor(Color.parseColor("#333333"));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_20));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setColors(Color.parseColor("#00000000"));
                return indicator;
            }
        });
        fragment_collect_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragment_collect_magic, fragment_collect_content);
    }
}
