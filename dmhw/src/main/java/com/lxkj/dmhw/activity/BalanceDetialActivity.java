package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.ahtrun.mytablayout.DetialListTablayout;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceDetialActivity extends BaseLangActivity {
    @BindView(R.id.tab_layout)
    DetialListTablayout tablayout;
    private boolean[] isNeedReArr;//记录每个fragment刷新状态
    @Override
    public int getLayoutId() {
        return R.layout.activity_balance_detial;
    }

    @Override
    public void initView() {
        initTitleBar("余额明细");
//        tablayout.init(TabLayout.MODE_FIXED, mFragments, new String[]{"全部", "待付款", "待发货", "待收货", "已完成"}, getSupportFragmentManager());
//        tablayout.reflex(this);
//        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                flag = tablayout.getTabLayout().getSelectedTabPosition();
//                if (isNeedReArr != null && isNeedReArr[flag]) {
//                    refreshFragment(flag);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}