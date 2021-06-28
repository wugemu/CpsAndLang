package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.GuideFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.guide_content)
    ViewPager guideContent;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private GuideFragment guideFragmentOne;
    private GuideFragment guideFragmentTwo;
    private GuideFragment guideFragmentThree;
    private GuideFragment guideFragmentFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        guideFragmentOne = GuideFragment.getInstance(1);
        guideFragmentTwo = GuideFragment.getInstance(2);
        guideFragmentThree = GuideFragment.getInstance(3);
        guideFragmentFour = GuideFragment.getInstance(4);
        fragments.add(guideFragmentOne);
        fragments.add(guideFragmentTwo);
        fragments.add(guideFragmentThree);
        fragments.add(guideFragmentFour);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        guideContent.setAdapter(fragmentAdapter);
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
}
