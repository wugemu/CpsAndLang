package com.example.test.andlang.cockroach;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wanjian on 2018/5/21.
 */

public class DebugSafeModeTipActivity extends AppCompatActivity {

    @BindView(R2.id.lang_tv_title)
    TextView lang_tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_mode_warning);
        ActivityUtil.getInstance().pushOneActivity(this);
        ButterKnife.bind(this);
        setStatusBar(1, R.color.lang_colorWhite);
        lang_tv_title.setText("crash页面");
    }


    @OnClick(R2.id.log)
    public void clickLogItem(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CrashLogFragment.class.getName());
        if (fragment == null) {
            fragment = new CrashLogFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, CrashLogFragment.class.getName())
                .commit();
    }

    /**  设置状态栏高度  */
    protected void setStatusBar(int flag,int colorId) {
        View mStatusBar=findViewById(R.id.m_statusBar);
        LinearLayout llBack = findViewById(R.id.lang_ll_back);
        ImageView lang_iv_back=findViewById(R.id.lang_iv_back);
        if(flag==1){
            if(mStatusBar != null) {
                StatusBarUtils.translateStatusBar(this);
                mStatusBar.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
                layoutParams.height = StatusBarUtils.getStatusHeight(this);
                mStatusBar.setLayoutParams(layoutParams);
                mStatusBar.setBackgroundResource(colorId);
                StatusBarUtils.setTextColorStatusBar(this, true);
            } else {
                StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
            }
        } else{
            StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
        }
        if(llBack!=null){
            llBack.setVisibility(View.VISIBLE);
            lang_iv_back.getDrawable().setAlpha(255);
            llBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    protected void goBack() {
        ActivityUtil.getInstance().exit(this);
    }
}
