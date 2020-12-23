package com.lxkj.dmhw.activity.self;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.self.CartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity {
    @BindView(R.id.m_statusBar)
    View mStatusBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        setStatusBar(1,R.color.colorWhite);
        CartFragment cartFragment=new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ifCanBack",true);
        cartFragment.setArguments(bundle);
        switchFragment(cartFragment);
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


    /**
     * 切换fragment
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, targetFragment).commit();
    }

    public void setStatusBar(int flag, int colorId) {
        View mStatusBar = ButterKnife.findById(this, R.id.m_statusBar);
        if (flag == 1) {
            if (mStatusBar != null) {
                StatusBarUtils.translateStatusBar(this);
                mStatusBar.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
                layoutParams.height = StatusBarUtils.getStatusHeight(this);
                mStatusBar.setLayoutParams(layoutParams);
                mStatusBar.setBackgroundResource(colorId);
                StatusBarUtils.setTextColorStatusBar(this, !BaseLangUtil.isLightColor(BaseLangUtil.getViewBackground(mStatusBar)));
            } else {
                StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
            }
        } else {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
        }

    }
}