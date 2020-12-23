package com.lxkj.dmhw.activity.self.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahtrun.mytablayout.CustomeTablayout;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.OrderModel;
import com.lxkj.dmhw.myinterface.OrderI;
import com.lxkj.dmhw.presenter.OrderPresenter;
import com.lxkj.dmhw.service.TimeService;

import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderListActivity extends BaseLangActivity<OrderPresenter> implements OrderI {
    public static final int REQ_PAYRESULT = 200;
    public static final int REQ_ORDERDETAIL = 300;
    public static final int REQ_UPDATAADDR=400;//修改地址

    @BindView(R.id.ct_order_layout)
    CustomeTablayout ct_order_layout;
    @BindView(R.id.ll_select_order)
    LinearLayout ll_select_order;
    @BindView(R.id.lang_tv_my)
    TextView lang_tv_my;
    @BindView(R.id.lang_tv_share)
    TextView lang_tv_share;
    @BindView(R.id.tv_search_hint)
    TextView tv_search_hint;

    private OrderFragment[] mFragments;
    private int flag; //0全部 1待付款 2待发货 3待收货 4已完成
    private int type = 2;//2我的订单 ;3分享订单
    private boolean[] isNeedReArr;//记录每个fragment刷新状态
    private int initType = 2;//activity活动杀死时 订单类型保存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
            initType = savedInstanceState.getInt("type");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        initTitleBar(true, "全部订单");
        initLoading();
        initFragment();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(Constants.TIME_TASK));

        //状态恢复设置
        if (initType == 3) {
            selectShare();
        } else {
            selectMy();
        }
    }

    private void initFragment() {
        mFragments = new OrderFragment[5];
        isNeedReArr = new boolean[5];
        for (int i = 0; i < 5; i++) {
            mFragments[i] = new OrderFragment();
            Bundle bundle = new Bundle();
//            bundle.putInt("orderType",type);
            if (i == 0) {
                //全部
                bundle.putInt("status", 0);
            } else if (i == 1) {
                //待付款
                bundle.putInt("status", 1);
            } else if (i == 2) {
                //待发货
                bundle.putInt("status", 5);
            } else if (i == 3) {
                //待收货
                bundle.putInt("status", 6);
            } else if (i == 4) {
                //交易完成
                bundle.putInt("status", 7);
            }
            mFragments[i].setArguments(bundle);
            mFragments[i].setOrderI(this);
        }
        ct_order_layout.init(TabLayout.MODE_FIXED, mFragments, new String[]{"全部", "待付款", "待发货", "待收货", "已完成"}, getSupportFragmentManager());
        ct_order_layout.reflex(OrderListActivity.this);
        ct_order_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                flag = ct_order_layout.getTabLayout().getSelectedTabPosition();
                if (isNeedReArr != null && isNeedReArr[flag]) {
                    refreshFragment(flag);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("type", type);
        super.onSaveInstanceState(outState);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mFragments != null) {
                if (mFragments.length > 0 && mFragments[0] != null) {
                    mFragments[0].updateTime();
                }
                if (mFragments.length > 1 && mFragments[1] != null) {
                    mFragments[1].updateTime();
                }
            }
        }
    };

    @Override
    public void initPresenter() {
        presenter = new OrderPresenter(OrderListActivity.this, OrderModel.class);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra("flag", 0);
        }

        ct_order_layout.select(flag);
    }


    @OnClick(R.id.lang_tv_my)
    public void selectMy() {
        //我的订单
        if (type != 2) {
            type = 2;
            lang_tv_my.setTextColor(getResources().getColor(R.color.colorWhite));
            lang_tv_share.setTextColor(getResources().getColor(R.color.colorRedMain));
            ll_select_order.setBackgroundResource(R.mipmap.order_top_my);
            //fragment 是否需要刷新状态置为true
            setRefreshState();
            refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
            tv_search_hint.setText("搜索我的订单");
        }
    }


    @OnClick(R.id.lang_tv_share)
    public void selectShare() {
        //分享订单
        if (type != 3) {
            type = 3;
            lang_tv_my.setTextColor(getResources().getColor(R.color.colorRedMain));
            lang_tv_share.setTextColor(getResources().getColor(R.color.colorWhite));
            ll_select_order.setBackgroundResource(R.mipmap.order_top_share);
            //fragment 是否需要刷新状态置为true
            setRefreshState();
            refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
            tv_search_hint.setText("搜索分享订单");
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    //全部订单
//                    lang_tv_title.setText("全部订单");
//                    break;
//                case 2:
//                    //我的订单
//                    lang_tv_title.setText("我的订单");
//                    break;
//                case 3:
//                    //分享订单
//                    lang_tv_title.setText("分享订单");
//                    break;
//            }
//            if (type!=msg.what) {
//                type = msg.what;
//                //fragment 是否需要刷新状态置为true
//                setRefreshState();
//                refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
//            }
//            super.handleMessage(msg);
//        }
//    };

    @Override
    public void refreshFragment(int position) {
        if (position == -1) {
            //刷新当前页面
            position = ct_order_layout.getTabLayout().getSelectedTabPosition();
        }
        isNeedReArr[position] = false;
        if (mFragments != null && mFragments.length > position) {
            mFragments[position].refresh(type);
        }
    }

    @Override
    public void setRefreshState() {
        if (isNeedReArr != null) {
            for (int i = 0; i < isNeedReArr.length; i++) {
                isNeedReArr[i] = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_PAYRESULT) {
                //fragment 是否需要刷新状态置为true
                //支付结果返回
                if (isNeedReArr != null) {
                    for (int i = 0; i < isNeedReArr.length; i++) {
                        isNeedReArr[i] = true;
                    }
                }
                refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
            } else if (requestCode == REQ_ORDERDETAIL) {
                //去商品详情操作 返回订单列表需要刷新
                if (isNeedReArr != null) {
                    for (int i = 0; i < isNeedReArr.length; i++) {
                        isNeedReArr[i] = true;
                    }
                }
                refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
            }else if(requestCode==REQ_UPDATAADDR){
                //修改地址 返回订单列表需要刷新
                if (isNeedReArr != null) {
                    for (int i = 0; i < isNeedReArr.length; i++) {
                        isNeedReArr[i] = true;
                    }
                }
                refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
            }
        }

        if (mFragments!=null&&flag<mFragments.length&&mFragments[flag]!=null){
            mFragments[flag].notifyDataSetChanged();
            mFragments[flag].onActivityFragmentResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Intent i = new Intent(OrderListActivity.this, TimeService.class);
            startService(i);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        if (isNeedReArr != null) {
            setRefreshState();
            refreshFragment(ct_order_layout.getTabLayout().getSelectedTabPosition());
        }
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        //去搜索
        Intent intent = new Intent(this, OrderSearchActivity.class);
        intent.putExtra("type",type);
        ActivityUtil.getInstance().start(this, intent);
    }
}