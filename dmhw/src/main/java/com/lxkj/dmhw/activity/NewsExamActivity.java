package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FootPrintListAdapter;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.News;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.NewsExamFragment;
import com.lxkj.dmhw.fragment.NewsExamOtherFragment;
import com.lxkj.dmhw.fragment.OrderFragment_MyFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
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

public class NewsExamActivity extends BaseActivity  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.fragment_news_exam_magic)
    MagicIndicator fragment_news_exam_magic;

    @BindView(R.id.fragment_news_exam_content)
    ViewPager fragment_news_exam_content;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;

    // 声明Fragment对象
    private NewsExamFragment NewsExamFragmentOne;
    private NewsExamFragment NewsExamFragmentTwo;
    private NewsExamFragment NewsExamFragmentThree;
    private NewsExamOtherFragment NewsExamFragmentFour;

    private String[] magicName = {"待审批", "已通过", "已拒绝","已开通"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_exam);
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
        NewsExamFragmentOne = NewsExamFragment.getInstance(1);
        NewsExamFragmentTwo = NewsExamFragment.getInstance(2);
        NewsExamFragmentThree = NewsExamFragment.getInstance(3);
        NewsExamFragmentFour= NewsExamOtherFragment.getInstance();
        fragments.add(NewsExamFragmentOne);
        fragments.add(NewsExamFragmentTwo);
        fragments.add(NewsExamFragmentThree);
        fragments.add(NewsExamFragmentFour);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragment_news_exam_content.setOffscreenPageLimit(4);
        fragment_news_exam_content.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(magicName[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#FF666666"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFC2628"));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setOnClickListener(v -> fragment_news_exam_content.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_30));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_2));
                indicator.setRoundRadius(5);
                indicator.setYOffset(13);
                indicator.setColors(Color.parseColor("#FFFC2628"));
                return indicator;
            }
        });
        fragment_news_exam_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(fragment_news_exam_magic, fragment_news_exam_content);
    }
}
