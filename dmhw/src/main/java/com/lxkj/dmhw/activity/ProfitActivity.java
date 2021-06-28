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
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.Popularize;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.ProfitFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.RiseNumberTextView;
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

public class ProfitActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.profit_accumulative)
    RiseNumberTextView profitAccumulative;
    @BindView(R.id.profit_help)
    LinearLayout profitHelp;
    @BindView(R.id.profit_magic)
    MagicIndicator profitMagic;
    @BindView(R.id.profit_content)
    ViewPager profitContent;
    @BindView(R.id.money_01)
    TextView money_01;
    @BindView(R.id.money_02)
    TextView money_02;
    @BindView(R.id.money_03)
    TextView money_03;
    @BindView(R.id.withdraw_btn)
    TextView  withdraw_btn;

    // 帮助点击状态
    private boolean isCheck = false;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private ProfitFragment profitFragmentOne;
    private ProfitFragment profitFragmentTwo;
    private ProfitFragment profitFragmentThree;
    private ProfitFragment profitFragmentFour;
    private ProfitFragment profitFragmentFive;
    // Tabs名字
    private String[] magicName = {"今日", "昨日", "近7日", "本月", "上月"};
    // 收益数据
    private ArrayList<Popularize.PopularizeList> popularizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        showDialog();
        // 获取提现信息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
        // 推广数据
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "Popularize", HttpCommon.Popularize);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.WithdrawalsSuccess) {
            Withdrawals withdrawals = (Withdrawals) message.obj;
            if (withdrawals!=null&&!withdrawals.getWithdrawalamount().equals("")) {
                if (!TempDataSetActivity.isCanEdit&&TempDataSetActivity.ProfitTaobaoDetailData==null) {
                    profitAccumulative.withNumber(Float.parseFloat(withdrawals.getWithdrawalamount()), 0).start();
                }else{
                     if (!TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailCandraw").equals("")){
                         profitAccumulative.withNumber(Float.parseFloat(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailCandraw")), 0).start();
                 }
                }
            }
        }
        if (message.what == LogicActions.PopularizeSuccess) {
            Popularize listData = (Popularize) message.obj;
            popularizes = listData.getStatisticsdata();
            if (!TempDataSetActivity.isTaoCanEdit) {
                money_01.setText(listData.getAccumulatedincome());
                money_02.setText(listData.getWithdrawalsmoney());
                money_03.setText(listData.getWaitsettleamount());
            }else{
                if (TempDataSetActivity.ProfitTaobaoDetailData!=null) {
                    money_01.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailTotal"));
                    money_02.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailHasdraw"));
                    money_03.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailNoCal"));
                }
            }
            magic();
            dismissDialog();
        }

    }

    @OnClick({R.id.back, R.id.profit_help,R.id.withdraw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.withdraw_btn:
              Intent  intent = new Intent(this, WithdrawalsNewActivity.class);
              intent.putExtra("widthdrawpos", "0");//淘宝
              startActivity(intent);
                break;
            case R.id.profit_help:
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(profitHelp.getLayoutParams());
                if (isCheck) {
                    params.setMargins(0, 0, -Utils.dipToPixel(R.dimen.dp_105), 0);
                    isCheck = false;
                } else {
                    params.setMargins(0, 0, 0, 0);
                    isCheck = true;
                }
                profitHelp.setLayoutParams(params);
                break;
        }
    }

    /**
     * 设置导航和viewpager
     */
    private void magic() {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (Popularize.PopularizeList popularize : popularizes) {
            switch (popularize.getSection()) {
                case "00":// 今日
                    profitFragmentOne = ProfitFragment.getInstance(popularize);
                    break;
                case "01":// 昨日
                    profitFragmentTwo = ProfitFragment.getInstance(popularize);
                    break;
                case "02":// 近7日
                    profitFragmentThree = ProfitFragment.getInstance(popularize);
                    break;
                case "03":// 上月
                    profitFragmentFive = ProfitFragment.getInstance(popularize);
                    break;
                case "04":// 本月
                    profitFragmentFour = ProfitFragment.getInstance(popularize);
                    break;
            }
        }
        fragments.add(profitFragmentOne);
        fragments.add(profitFragmentTwo);
        fragments.add(profitFragmentThree);
        fragments.add(profitFragmentFour);
        fragments.add(profitFragmentFive);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        profitContent.setAdapter(fragmentAdapter);
        profitMagic.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
//                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                ScaleTransitionPagerTitleView scaleTransitionPagerTitleView=new ScaleTransitionPagerTitleView(context);
                scaleTransitionPagerTitleView.setText(magicName[index]);
                scaleTransitionPagerTitleView.setNormalColor(Color.parseColor("#333333"));
                scaleTransitionPagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                scaleTransitionPagerTitleView.setTextSize(16);
                scaleTransitionPagerTitleView.setMinScale(0.95f);
                scaleTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profitContent.setCurrentItem(index);
                    }
                });
                return scaleTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_29));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                indicator.setRoundRadius(5);
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        profitMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(profitMagic, profitContent);
    }
}
