package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.fragment.HelpFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帮助中心页
 */
public class HelpActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    // Fragment装载器
    private Fragment currentFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        switchFragment(HelpFragment.getInstance());
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
}
