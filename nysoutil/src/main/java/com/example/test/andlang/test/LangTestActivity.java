package com.example.test.andlang.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.cockroach.DebugSafeModeTipActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.Constants;
import com.example.test.andlang.util.MMKVUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.example.test.andlang.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class LangTestActivity extends AppCompatActivity {
    public static final String HOST_DEBUG="SplashActivity_debug";//测试环境
    public static final String HOST_PRERE="SplashActivity_prere";//预发环境
    public static final String HOST_LOCAL="SplashActivity_local";//开发环境
    public static final String HOST_USERLOCAL="SplashActivity_userlocal";//本地环境
    public static final String HOST_PRESS="SplashActivity_press";//压测环境
    @BindView(R2.id.lv_lang_test)
    ListView lv_lang_test;
    @BindView(R2.id.tv_text_host)
    TextView tv_text_host;
    @BindView(R2.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R2.id.lang_tv_title)
    TextView lang_tv_title;
    @BindView(R2.id.m_statusBar)
    View m_statusBar;
    @BindView(R2.id.tv_text_mintype)
    TextView tv_text_mintype;

    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_test);
        ActivityUtil.getInstance().pushOneActivity(this);
        ButterKnife.bind(this);
        setStatusBar(1, R.color.lang_colorWhite);
        rl_top.setBackgroundColor(getResources().getColor(R.color.lang_colorWhite));
        lang_tv_title.setText("app测试人员配置页面");
        initData();
    }



    public void initData() {
//        String str=BaseLangApplication.getInstance().getSpUtil().getString(LangTestActivity.this,Constants.TEST_HOST_TAG);
        String str=MMKVUtil.getString(Constants.TEST_HOST_TAG);
        int minType=MMKVUtil.getInt(Constants.TEST_MINITYPE_TAG);
//        if (BaseLangUtil.isApkInDebug()&&BaseLangUtil.isEmpty(str)){
//            str=MMKVUtil.getString(Constants.TEST_HOST_TAG);
//        }

        if(HOST_USERLOCAL.equals(str)){
            tv_text_host.setText("本地环境");
        }else if(HOST_LOCAL.equals(str)){
            tv_text_host.setText("开发环境");
        }else if(HOST_DEBUG.equals(str)){
            tv_text_host.setText("测试环境");
        }else if(HOST_PRERE.equals(str)){
            tv_text_host.setText("预发环境");
        }else if(HOST_PRESS.equals(str)){
            tv_text_host.setText("压测环境");
        }else {
            tv_text_host.setText("正式环境");
        }

        if(minType==0){
            tv_text_mintype.setText("正式版本");
        }else if(minType==1){
            tv_text_mintype.setText("开发版本");
        }

        list.add("本地环境");
        list.add("开发环境");
        list.add("测试环境");
        list.add("预发环境");
        list.add("正式环境");
        list.add("压测环境");
        list.add("小程序开发版本");
        list.add("小程序正式版本");
        list.add("");
        list.add("查看本地日志");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv_lang_test.setAdapter(adapter);
    }

    @OnItemClick(R2.id.lv_lang_test)
    public void clickItem(int postion){
        String str=list.get(postion);
        if("本地环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_USERLOCAL);
            MMKVUtil.putString(Constants.TEST_HOST_TAG,HOST_USERLOCAL);
            restartApp();
        }else if("开发环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_LOCAL);
            MMKVUtil.putString(Constants.TEST_HOST_TAG,HOST_LOCAL);
            restartApp();
        }else if("测试环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_DEBUG);
            MMKVUtil.putString(Constants.TEST_HOST_TAG,HOST_DEBUG);
            restartApp();
        }else if("预发环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_PRERE);
            MMKVUtil.putString(Constants.TEST_HOST_TAG,HOST_PRERE);
            restartApp();
        }else if("正式环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,"");
            MMKVUtil.putString(Constants.TEST_HOST_TAG,"");
            restartApp();
        }else if("压测环境".equals(str)){
//            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_PRESS);
            MMKVUtil.putString(Constants.TEST_HOST_TAG,HOST_PRESS);
            restartApp();
        }else if("查看本地日志".equals(str)){
            Intent intent = new Intent(LangTestActivity.this, DebugSafeModeTipActivity.class);
            ActivityUtil.getInstance().start(LangTestActivity.this,intent);
        }else if("小程序开发版本".equals(str)){
            MMKVUtil.putInt(Constants.TEST_MINITYPE_TAG,1);
            restartApp();
        }else if("小程序正式版本".equals(str)){
            MMKVUtil.putInt(Constants.TEST_MINITYPE_TAG,0);
            restartApp();
        }
    }


    //重新启动
    public void restartApp() {
        ToastUtil.show(LangTestActivity.this,"app重新启动中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = BaseLangApplication.getInstance().getPackageManager()
                        .getLaunchIntentForPackage(BaseLangApplication.getInstance().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                BaseLangApplication.getInstance().startActivity(intent);
            }
        },500);
    }

    /**  设置状态栏高度  */
    protected void setStatusBar(int flag,int colorId) {
        View mStatusBar=ButterKnife.findById(this,R.id.m_statusBar);
        LinearLayout llBack = ButterKnife.findById(this,R.id.lang_ll_back);
        ImageView lang_iv_back=ButterKnife.findById(this,R.id.lang_iv_back);
        if(flag==1){
            if(mStatusBar != null) {
                StatusBarUtils.translateStatusBar(this);
                mStatusBar.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
                layoutParams.height = StatusBarUtils.getStatusHeight(this);
                mStatusBar.setLayoutParams(layoutParams);
                mStatusBar.setBackgroundResource(colorId);
                StatusBarUtils.setTextColorStatusBar(this, true);
            } else {
                StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
            }
        } else{
            StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
        }
        if(llBack!=null){
            llBack.setVisibility(View.VISIBLE);
            lang_iv_back.getDrawable().setAlpha(255);
            llBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);
    }

    protected void goBack() {
        ActivityUtil.getInstance().exit(this);
    }
}
