package com.lxkj.dmhw.activity.self.coupon;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ahtrun.mytablayout.CouponTabLayout;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.Constants;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponActivity extends BaseLangActivity {
    @BindView(R.id.tab_coupon)
    CouponTabLayout tabCoupon;
    private Fragment[] fragments;
    private TextView tvRight;
    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        initLoading();
        tvRight=findViewById(R.id.lang_tv_right);
        tvRight.setTextSize(14);
        initTitleBar(true, "优惠券", "使用说明", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(CouponActivity.this, WebViewActivity.class);
//                intent.putExtra("url", Constants.H5HOST+Constants.YOUHUIQUANSHUOMING);
//                ActivityUtil.getInstance().start(CouponActivity.this,intent);
            }
        });
        fragments = new Fragment[3];
        String[] mTitles = new String[]{"未使用", "已使用", "已过期"};
        for (int i = 0; i < 3; i++) {
            fragments[i] = new CouponFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("type",i);
            fragments[i].setArguments(bundle);
        }

        tabCoupon.init(TabLayout.MODE_FIXED,fragments,mTitles,getSupportFragmentManager());
        tabCoupon.reflex(this);
        tabCoupon.select(0);


    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        if(fragments!=null&&fragments.length>0){
            for (int i=0;i<fragments.length;i++){
                CouponFragment fragment=(CouponFragment)fragments[i];
                if(fragment!=null){
                    fragment.refreshData();
                }
            }
        }
    }
}