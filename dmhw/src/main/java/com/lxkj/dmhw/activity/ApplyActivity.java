package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.fragment.ApplyFragment_Agent;
import com.lxkj.dmhw.fragment.ApplyFragment_Franchiser;
import com.lxkj.dmhw.fragment.ApplyFragment_Partner;
import com.lxkj.dmhw.fragment.ApplyFragment_Tourist;

import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class ApplyActivity extends BaseActivity {

    @BindView(R.id.apply_title)
    TextView applyTitle;
    @BindView(R.id.apply_icon)
    ImageView applyIcon;
    @BindView(R.id.apply_name)
    TextView applyName;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.bar)
    View bar;
    private SupportFragment[] fragments = {ApplyFragment_Agent.getInstance(), ApplyFragment_Franchiser.getInstance(), ApplyFragment_Partner.getInstance(), ApplyFragment_Tourist.getInstance()};
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 设置状态栏透明,5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ViewGroup.LayoutParams layoutParams = applyIcon.getLayoutParams();
        if (DateStorage.getLoginStatus()) {
            switch (login.getUsertype()) {
                case "3":
                    position = 0;
                    applyTitle.setText("注册为呆萌会员");
                    applyIcon.setImageResource(R.mipmap.apply_agent);
                    layoutParams.height = Utils.dipToPixel(R.dimen.dp_43);
                    layoutParams.width = Utils.dipToPixel(R.dimen.dp_59);
                    applyIcon.setLayoutParams(layoutParams);
                    applyName.setText(login.getUsername());
                    break;
                case "2":
                    position = 1;
                    applyTitle.setText("升级为店主");
                    applyIcon.setImageResource(R.mipmap.apply_franchiser);
                    layoutParams.height = Utils.dipToPixel(R.dimen.dp_55);
                    layoutParams.width = Utils.dipToPixel(R.dimen.dp_61);
                    applyIcon.setLayoutParams(layoutParams);
                    applyName.setText(login.getUsername());
                    break;
                case "4":
                    position = 2;
                    applyTitle.setText("升级为合伙人");
                    applyIcon.setImageResource(R.mipmap.apply_partner);
                    layoutParams.height = Utils.dipToPixel(R.dimen.dp_54);
                    layoutParams.width = Utils.dipToPixel(R.dimen.dp_71);
                    applyIcon.setLayoutParams(layoutParams);
                    applyName.setText(login.getUsername());
                    break;
            }
        } else {
            position = 3;
            applyTitle.setText("注册为呆萌会员");
            applyIcon.setImageResource(R.mipmap.apply_agent);
            layoutParams.height = Utils.dipToPixel(R.dimen.dp_43);
            layoutParams.width = Utils.dipToPixel(R.dimen.dp_59);
            applyIcon.setLayoutParams(layoutParams);
            applyName.setVisibility(View.GONE);
        }
        loadRootFragment(R.id.fragment, fragments[position]);
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }
}
