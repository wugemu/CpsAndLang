package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.fragment.WithdrawalsFragment_Alipay;
import com.lxkj.dmhw.fragment.WithdrawalsTaskDMBFragment_Alipay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现页
 */
public class WithdrawalsActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.withdrawals_content)
    ViewPager withdrawalsContent;
    @BindView(R.id.radio_one)
    RadioButton radioOne;
    @BindView(R.id.radio_two)
    RadioButton radioTwo;
    @BindView(R.id.withdrawals_group)
    RadioGroup withdrawalsGroup;
    @BindView(R.id.withdrawals_tabs)
    RelativeLayout withdrawalsTabs;
    @BindView(R.id.radio_one_image)
    ImageView radioOneImage;
    @BindView(R.id.radio_two_image)
    ImageView radioTwoImage;
    @BindView(R.id.pos_zero)
    ImageView pos_zero;
    @BindView(R.id.pos_one)
    TextView pos_one;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
//    private int[] magicImage = {R.mipmap.withdrawals_alipay, R.mipmap.withdrawals_wechat};
    // 声明Fragment对象
    private WithdrawalsFragment_Alipay withdrawalsFragmentAlipay;
    private WithdrawalsTaskDMBFragment_Alipay withdrawalsTaskDMBFragment_alipay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment集合
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 初始化Fragment页面
        // 装入Fragment页面
        if (getIntent().getStringExtra("widthdrawpos").equals("0")){
            //个人中心的提现
            pos_zero.setVisibility(View.VISIBLE);
            pos_one.setVisibility(View.GONE);
            withdrawalsFragmentAlipay = WithdrawalsFragment_Alipay.getInstance(0);
            fragments.add(withdrawalsFragmentAlipay);
        }else{
            //呆萌币的提现
            pos_zero.setVisibility(View.GONE);
            pos_one.setVisibility(View.VISIBLE);
            withdrawalsTaskDMBFragment_alipay=WithdrawalsTaskDMBFragment_Alipay.getInstance();
            fragments.add(withdrawalsTaskDMBFragment_alipay);
        }
        // 初始化FragmentAdapter
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        // 装入FragmentAdapter
        withdrawalsContent.setAdapter(fragmentAdapter);
        // 监听ViewPager
        withdrawalsContent.addOnPageChangeListener(this);
        // 监听RadioGroup事件
        withdrawalsGroup.setOnCheckedChangeListener(this);
        radioBitmap();
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

    private void radioBitmap() {
        if (radioOne.isChecked()) {
            Drawable drawableOne = ContextCompat.getDrawable(this, R.mipmap.withdrawals_alipay);
            Drawable tintOne = DrawableCompat.wrap(drawableOne);
            DrawableCompat.setTint(tintOne, getResources().getColor(R.color.white));
            radioOneImage.setImageDrawable(tintOne);
            Drawable drawableTwo = ContextCompat.getDrawable(this, R.mipmap.withdrawals_wechat);
            Drawable tintTwo = DrawableCompat.wrap(drawableTwo);
            DrawableCompat.setTint(tintTwo, getResources().getColor(R.color.mainColor));
            radioTwoImage.setImageDrawable(tintTwo);
        } else {
            Drawable drawableOne = ContextCompat.getDrawable(this, R.mipmap.withdrawals_alipay);
            Drawable tintOne = DrawableCompat.wrap(drawableOne);
            DrawableCompat.setTint(tintOne, getResources().getColor(R.color.mainColor));
            radioOneImage.setImageDrawable(tintOne);
            Drawable drawableTwo = ContextCompat.getDrawable(this, R.mipmap.withdrawals_wechat);
            Drawable tintTwo = DrawableCompat.wrap(drawableTwo);
            DrawableCompat.setTint(tintTwo, getResources().getColor(R.color.white));
            radioTwoImage.setImageDrawable(tintTwo);
        }
    }

    @OnClick({R.id.back, R.id.right_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.right_btn:
                if (getIntent().getStringExtra("widthdrawpos").equals("0")) {
                    startActivity(new Intent(this, WithdrawalsListActivity.class));
                }else{
                    startActivity(new Intent(this, ScoreActivity.class).putExtra("positionname","money"));
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                if (withdrawalsGroup.getCheckedRadioButtonId() != R.id.radio_one) {
                    withdrawalsGroup.check(R.id.radio_one);
                }
                break;
            case 1:
                if (withdrawalsGroup.getCheckedRadioButtonId() != R.id.radio_two) {
                    withdrawalsGroup.check(R.id.radio_two);
                }
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_one:
                if (withdrawalsContent.getCurrentItem() != 0) {
                    withdrawalsContent.setCurrentItem(0);
                    radioBitmap();
                }
                break;
            case R.id.radio_two:
                if (withdrawalsContent.getCurrentItem() != 1) {
                    withdrawalsContent.setCurrentItem(1);
                    radioBitmap();
                }
                break;
        }
    }

}
