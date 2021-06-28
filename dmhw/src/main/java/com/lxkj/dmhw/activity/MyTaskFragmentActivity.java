package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.MyTaskFragment;

import butterknife.ButterKnife;

//任务 赚萌币
public class MyTaskFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_fragment);
        ButterKnife.bind(this);
        switchFragment(MyTaskFragment.getInstance());
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
