package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.fragment.MorePlThemeFragment;
import com.lxkj.dmhw.fragment.NewFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 每日推荐
 */
public class NewActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    // Fragment装载器
    private Fragment currentFragment = new Fragment();
    private NewFragment newFragment=null;
    private  MorePlThemeFragment morePlThemeFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (getIntent().getBooleanExtra("notaobao",false)){
            int plat= getIntent().getIntExtra("platForm",0);
            String id=getIntent().getStringExtra("themeId");
            String type=getIntent().getStringExtra("type");
            String name=getIntent().getStringExtra("name");
            morePlThemeFragment=MorePlThemeFragment.getInstance(plat,type,id,name);
            switchFragment(morePlThemeFragment);
        }else{
            newFragment=NewFragment.getInstance();
            switchFragment(newFragment);
        }
    }

    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            //获取token
            if (DateStorage.getMyToken().equals("")||DateStorage.getMicroAppId().equals("")||DateStorage.getLittleAppId().equals("")){
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().GETNew(handler, paramMap, "LoginToken", HttpCommon.LoginToken);
            }else{
                if (newFragment!=null) {
                    newFragment.refresh();
                }
                if (morePlThemeFragment!=null){
                    morePlThemeFragment.refresh();
                }
            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        //获取token 保存本地
        if (message.what == LogicActions.LoginTokenSuccess) {
            LoginToken loginToken= (LoginToken) message.obj;
            DateStorage.setMyToken(loginToken.getToken());
            DateStorage.setLittleAppId(loginToken.getMicroId());
            DateStorage.setMicroAppId(loginToken.getWechatMicroId());
            if (newFragment!=null) {
                newFragment.refresh();
            }
            if (morePlThemeFragment!=null){
                morePlThemeFragment.refresh();
            }
        }
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
