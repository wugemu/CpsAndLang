package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.sdklibrary.view.HomeActivity;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.lxkj.dmhw.bean.JDSort;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;

import com.lxkj.dmhw.dialog.WeixinLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.ConfigUtils;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.login_register)
    LinearLayout loginRegister;
    @BindView(R.id.login_phone)
    RelativeLayout loginPhone;
    @BindView(R.id.login_wechat)
    RelativeLayout loginWechat;
    @BindView(R.id.register_agreement)
    TextView register_agreement;

    private IWXAPI api;

    public static boolean relocate=false;
    WeixinLoginDialog wxdialog;
    public static int stateCode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        api = Utils.weChat(this, false);
        Variable.weChatCheck = true;
        relocate=getIntent().getBooleanExtra("relocation",false);
        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
            @Override
            public void getPhoneInfoStatus(int code, String result) {
                //预取号回调
                Log.e("VVV", "预取号： code==" + code + "   result==" + result);
//                if(code==200027){
//                 toast("检查是否打开移动数据流量");
//                }
                stateCode=code;
            }
        });
    }


    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        //手机未注册
        if (message.what == LogicActions.Failed) {
            dismissDialog();
            //闪验一键验证失败 result==2
            if (message.arg1 == 6) {
                JSONObject jsonObject = (JSONObject) message.obj;
                if (jsonObject.optString("msgstr").equals("您的手机还未注册，请重试！")) {
                    //执行注册接口
                    Variable.userPhone = jsonObject.optString("userphone");
                    wxdialog = new WeixinLoginDialog(this, "");
                    wxdialog.show();
                }else{
                    toast(jsonObject.optString("msgstr"));
                }
            }
            //result==3
            if (message.arg1 == 5) {
                toast(message.obj.toString());
            }
        }

        if (message.what == LogicActions.SYChangePhoneNumSuccess) {
            // 隐藏弹窗
            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
            isFinish();
        }
    }

    @OnClick({R.id.back, R.id.login_register, R.id.login_phone, R.id.login_wechat,R.id.register_agreement})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
                break;
            case R.id.login_phone:// 手机登录
                //预取号
                Variable.loginByCode=true;
                if(stateCode==1022) {
                    showDialog();
                    OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getCJSConfig(getApplicationContext()), null);
                    openLoginActivity();
                }else{
                    intent = new Intent(this, SignInActivity.class);
                }
                break;
            case R.id.login_wechat:// 微信登录
                Variable.loginByCode=false;
                if (!api.isWXAppInstalled()) {
                    toast("请先安装微信");
                } else {
                    Variable.BindingWeChat = false;
                    Variable.isHasDialog= true;
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = Variable.weChatState;
                    api.sendReq(req);
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressedSupport() {
        isFinish();
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //拉取授权页方法
    private void openLoginActivity() {
        OneKeyLoginManager.getInstance().openLoginAuth(false, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {
                dismissDialog();
                if (1000 == code) {
                    //拉起授权页成功
                    Log.e("VVV", "拉起授权页成功： _code==" + code + "   _result==" + result);
                } else {
                    //拉起授权页失败
                  Log.e("VVV", "拉起授权页失败： _code==" + code + "   _result==" + result);
                  Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                  startActivity(intent);
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {
                if (1011 == code) {
                    Log.e("VVV", "用户点击授权页返回： _code==" + code + "   _result==" + result);
                    return;
                } else if (1000 == code) {
                    Log.e("VVV", "用户点击登录获取token成功： _code==" + code + "   _result==" + result);
                    try {
                        OneKeyLoginManager.getInstance().finishAuthActivity();
                        OneKeyLoginManager.getInstance().removeAllListener();
                        showDialog();
                        JSONObject jsonObject = new JSONObject(result);
                        String token = jsonObject.optString("token");
                        paramMap.clear();
                        paramMap.put("token", token);
                        NetworkRequest.getInstance().POST(handler, paramMap, "SYChangePhoneNum", HttpCommon.SYChangePhoneNum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("VVV", "用户点击登录获取token失败： _code==" + code + "   _result==" + result);
                    Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
