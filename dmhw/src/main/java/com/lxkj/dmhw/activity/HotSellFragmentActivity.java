package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.FourFragment;
import com.lxkj.dmhw.fragment.MyTaskFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

//热销榜单
public class HotSellFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_fragment);
        ButterKnife.bind(this);
        switchFragment(new FourFragment());
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
}
