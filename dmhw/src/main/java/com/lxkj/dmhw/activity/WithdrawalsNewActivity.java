package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
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
import com.lxkj.dmhw.fragment.WithdrawalsFragment_Alipay;
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
import butterknife.OnClick;

public class WithdrawalsNewActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.right_btn)
    LinearLayout right_btn;
    @BindView(R.id.widthdraw_magic)
    MagicIndicator widthdraw_magic;
    @BindView(R.id.widthdraw_content)
    ViewPager widthdraw_content;
    // 声明Fragment对象
    private WithdrawalsFragment_Alipay WithdrawalsFragment_Alipay0;
    private WithdrawalsFragment_Alipay WithdrawalsFragment_Alipay1;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    private String[] magicName = {"淘宝平台佣金", "其他平台佣金"};
    private int indexPos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals_new);
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

        if (getIntent().getStringExtra("widthdrawpos").equals("0")){
            indexPos=0;
            widthdraw_content.setCurrentItem(0);
        }else{
            indexPos=1;
            widthdraw_content.setCurrentItem(1);
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
    }

    private void magic() {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        WithdrawalsFragment_Alipay0 = WithdrawalsFragment_Alipay.getInstance(0);
        WithdrawalsFragment_Alipay1 = WithdrawalsFragment_Alipay.getInstance(1);

        fragments.add(WithdrawalsFragment_Alipay0);
        fragments.add(WithdrawalsFragment_Alipay1);

        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        widthdraw_content.setAdapter(fragmentAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(magicName[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setTextSize(17);
                ((ScaleTransitionPagerTitleView) simplePagerTitleView).setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexPos=index;
                        widthdraw_content.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_25));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_2));
                indicator.setRoundRadius(R.dimen.dp_1);
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        widthdraw_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(widthdraw_magic, widthdraw_content);
    }

    @OnClick({R.id.back,R.id.right_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.right_btn:
                startActivity(new Intent(this, WithdrawalsListActivity.class).putExtra("type",indexPos));
                break;
        }
        }


}
