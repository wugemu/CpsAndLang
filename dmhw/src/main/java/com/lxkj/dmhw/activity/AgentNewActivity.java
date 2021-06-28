package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.fragment.TeamNewFragment;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的团名列表页面
public class AgentNewActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.select)
    LinearLayout select;
    @BindView(R.id.phone_edit)
    EditText phone_edit;

    // Fragment装载器
    private Fragment currentFragment = new Fragment();

    private int  index; //接口请求索引
    private String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_agent);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        title=getIntent().getStringExtra("title");
        index=getIntent().getExtras().getInt("index");
        phone_edit.setHint("请输入"+title+"的昵称或手机号码");
        switchFragment(TeamNewFragment.getInstance(index));

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

    @OnClick({R.id.back,R.id.select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.select:
                if (phone_edit.getText().toString().length()<=0){
                   toast("请输入昵称或手机号");
                    return;
                }
                //我的团队搜索条件
                List<String> searchList=new ArrayList<>();
                ChooseDataActivity.key=phone_edit.getText().toString();
                searchList.add(0,ChooseDataActivity.key);
                searchList.add(1,ChooseDataActivity.startdate);
                searchList.add(2,ChooseDataActivity.enddate);
                searchList.add(3,ChooseDataActivity.startcnt);
                searchList.add(4,ChooseDataActivity.endcnt);
                searchList.add(5,ChooseDataActivity.startpre);
                searchList.add(6,ChooseDataActivity.endpre);
                searchList.add(7,ChooseDataActivity.startmonth);
                searchList.add(8,ChooseDataActivity.endmonth);
                searchList.add(9,ChooseDataActivity.startprofit);
                searchList.add(10,ChooseDataActivity.endprofit);
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ChooseData"), searchList, 0);
                break;
        }
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
