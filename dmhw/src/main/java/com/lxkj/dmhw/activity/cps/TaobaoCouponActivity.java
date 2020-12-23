package com.lxkj.dmhw.activity.cps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.MorePlSearchNewActivity;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.LimitCatalog;
import com.lxkj.dmhw.bean.TaobaoBean;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.LoadMoreFooterView;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.fragment.MainBottomFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class TaobaoCouponActivity extends BaseActivity implements PtrHandler {
    @BindView(R.id.bar)
    View bar;
    //底部导航 悬停
    @BindView(R.id.bottom_navigate_magic)
    MagicIndicator bottom_navigate_magic;
    @BindView(R.id.navigate_content)
    ViewPager navigate_content;
    @BindView(R.id.tv_coupon_num)
    TextView tv_coupon_num;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;
    @BindView(R.id.AppBar_Layout)
    AppBarLayout AppBar_Layout;

    //首页底部导航数据填充
    // Fragment页面
    private ArrayList<Fragment> fragments;
    // 声明Fragment对象
    private MainBottomFragment mainBottomFragment;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    private  boolean ishasdata=false;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    private boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobao_coupon);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        ptrFrameLayout();//下拉刷新初始化
        showDialog();
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "taoBaoInit", HttpCommon.TaobaoCouponInit);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.TaoBaoInit) {
            TaobaoBean taobaoBean=(TaobaoBean)message.obj;
            tv_coupon_num.setText("当前有 "+taobaoBean.getCouponNum()+" 张优惠券");
            if (taobaoBean.getNavList()!=null&&taobaoBean.getNavList().size()>0) {
                BottomNavMagic(taobaoBean.getNavList());
            }
        }
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
    }


    private void BottomNavMagic(ArrayList<TaobaoBean.BottomNavigation> list) {
        ishasdata=true;
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mainBottomFragment = MainBottomFragment.getInstance(list.get(i).getMaterial());
            fragments.add(mainBottomFragment);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        navigate_content.setAdapter(fragmentAdapter);
        navigate_content.setOffscreenPageLimit(list.size());
        CommonNavigator commonNavigator = new CommonNavigator(TaobaoCouponActivity.this);
        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.main_nav_magic);
                View split= commonPagerTitleView.findViewById(R.id.split);
                LinearLayout.LayoutParams linearParamssplit =(LinearLayout.LayoutParams)split.getLayoutParams();
                LinearLayout limitMagicLayout = (LinearLayout) commonPagerTitleView.findViewById(R.id.nav_magic_layout);
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)limitMagicLayout.getLayoutParams();
                linearParams.width=(width-linearParamssplit.width)/4;
                limitMagicLayout.setLayoutParams(linearParams);
                if (index==list.size()-1){
                    split.setVisibility(View.GONE);
                }else{
                    split.setVisibility(View.VISIBLE);
                }
                TextView limitMagicTitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_magic_title);
                TextPaint tp=new TextPaint();
                tp= limitMagicTitle.getPaint();
                tp.setFakeBoldText(true);
                TextView limitMagicSubtitle = (TextView) commonPagerTitleView.findViewById(R.id.nav_magic_subtitle);
                limitMagicTitle.setText(list.get(index).getTitle());
                limitMagicSubtitle.setText(list.get(index).getDesc());
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        limitMagicTitle.setTextColor(Color.parseColor("#000000"));
                        limitMagicSubtitle.setTextColor(Color.parseColor("#FFFFFF"));
                        limitMagicSubtitle.setBackgroundResource(R.drawable.bg_rect_black_05dp);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        limitMagicTitle.setTextColor(Color.parseColor("#6A6A6A"));
                        limitMagicSubtitle.setTextColor(Color.parseColor("#6A6A6A"));
                        limitMagicSubtitle.setBackgroundResource(0);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                if (navigate_content!=null){
                    commonPagerTitleView.setOnClickListener(v -> navigate_content.setCurrentItem(index));
                }
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        bottom_navigate_magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(bottom_navigate_magic, navigate_content);
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.WHITE);
        loadView.setTextColor(Color.parseColor("#333333"));
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) AppBar_Layout.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset == 0) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        isFirst=false;
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "taoBaoInit", HttpCommon.TaobaoCouponInit);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    @OnClick(R.id.ll_search_coupon)
    public void clickSearchCoupon(){
        Intent intent = new Intent(TaobaoCouponActivity.this, MorePlSearchNewActivity.class);
        intent.putExtra("isCheck", false);
        intent.putExtra("platType", "0");
        startActivity(intent);
    }
}