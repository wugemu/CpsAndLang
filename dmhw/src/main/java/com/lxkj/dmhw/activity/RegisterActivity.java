package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.fragment.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注册新用户页
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    // Fragment装载器
    private Fragment currentFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        WeChatUserInfo info = (WeChatUserInfo) getIntent().getSerializableExtra("wechat");
        String phone= getIntent().getStringExtra("phone");
        switchFragment(RegisterFragment.getInstance(info,phone));
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
        if (currentFragment != targetFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.fragment, targetFragment).commit();
            } else {
                transaction.hide(currentFragment).show(targetFragment).commit();
            }
            currentFragment = targetFragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Variable.AuthShowStatus=false;
    }
}
