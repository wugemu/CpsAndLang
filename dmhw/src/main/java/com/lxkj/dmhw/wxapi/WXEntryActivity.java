package com.lxkj.dmhw.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.lxkj.dmhw.activity.InvitationActivity;
import com.lxkj.dmhw.activity.SignInActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.BindingActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.bean.AccessToken;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.ConfigUtils;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    // 用户信息
    private UserInfo weChatLogin;
    // 微信api
    private IWXAPI api;
    // 微信用户信息
    private WeChatUserInfo weChatInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        // 设置动画
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        if (Variable.weChatCheck) {
            api = Utils.weChat(this, false);
        } else {
            api = Utils.weChat(this, true);
        }
        api.handleIntent(getIntent(), this);

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.Failed) {
            Failed(message.arg1, message.obj + "",0);
            dismissDialog();
            isFinish();
        }
        if (message.what == LogicActions.AccessTokenSuccess) {
            AccessToken token = (AccessToken) message.obj;
            paramMap.clear();
            paramMap.put("access_token", token.getAccess_token());
            paramMap.put("openid", token.getOpenid());
            paramMap.put("lang", "zh_CN");
            //(必须用GET)
            NetworkRequest.getInstance().GET(handler, paramMap, "WeChatUserInfo", HttpCommon.WeChatUserInfo);
            showDialog();
        }
        if (message.what == LogicActions.WeChatUserInfoSuccess) {
            weChatInfo = (WeChatUserInfo) message.obj;
            if (Variable.BindingWeChat) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                if (Variable.isBindingWeChatFromSetting){
                    paramMap.put("type", "1");//操作类型（0：绑定 1：解绑）
                }else{
                    paramMap.put("type", "0");//操作类型（0：绑定 1：解绑）
                }
                paramMap.put("wxuuid", weChatInfo.getUnionid());
                NetworkRequest.getInstance().POST(handler, paramMap, "UserInfoBindingWeChat", HttpCommon.UserInfoBindingWeChat);
            } else {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("wxuuid", weChatInfo.getUnionid());
                paramMap.put("wxmc", weChatInfo.getNickname());
                paramMap.put("wxhead", weChatInfo.getHeadimgurl());
                if (Variable.isHasDialog){
                 paramMap.put("type", "0");
                }else{
                paramMap.put("type", "1");
                }
                NetworkRequest.getInstance().POST(handler, paramMap, "LoginWeChat", HttpCommon.LoginWeChat);
            }
        }
        if (message.what == LogicActions.LoginWeChatSuccess) {
            dismissDialog();
            weChatLogin = (UserInfo) message.obj;
            if (Objects.equals(weChatLogin.getIsbinding(), "0")) {
                if(Variable.loginByCode){
                    if (!weChatLogin.getExtensionid().equals("")){
                        showDialog();
                        // 请求注册
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("extensionid",weChatLogin.getExtensionid());
                        paramMap.put("userphone", Variable.userPhone);
                        paramMap.put("userpwd", Utils.getMD5("123456"));
                        paramMap.put("wxuuid",  weChatInfo.getUnionid());
                        paramMap.put("wxmc", weChatInfo.getNickname());
                        paramMap.put("wxhead", weChatInfo.getHeadimgurl());
                        NetworkRequest.getInstance().POST(handler, paramMap, "RegisterWeChat", HttpCommon.RegisterWeChat);
                    }else{
                    startActivity(new Intent(this, InvitationActivity.class).putExtra("wechat", weChatInfo));
                        // 关闭页面
                        isFinish();
                    }

                }else {
                    //修改为本机一键绑定  唤醒一键绑定页面
                    if (LoginActivity.stateCode==1022) {
                        showDialog();
                        ConfigUtils.weChatInfo=weChatInfo;
                        ConfigUtils.extensionid=weChatLogin.getExtensionid();
                        OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getWXCJSConfig(getApplicationContext()), null);
                        openLoginActivity();
                    }else{
                        startActivity(new Intent(this, BindingActivity.class).putExtra("wechat", weChatInfo).putExtra("invitation", weChatLogin.getExtensionid()));
                        // 关闭页面
                        isFinish();
                    }
                }
                } else {
                if(Variable.loginByCode){
                  ToastUtil.showImageToast(this,"该微信号已被其他用户绑定",R.mipmap.toast_error);
                    // 关闭页面
                    isFinish();
                }else{
                    // 储存用户信息
                    DateStorage.setInformation(weChatLogin);
                    if (Objects.equals(weChatLogin.getUserphone(), "")) {
                        //修改为本机一键绑定  唤醒一键绑定页面
                        if (LoginActivity.stateCode==1022) {
                            showDialog();
                            ConfigUtils.weChatInfo=weChatInfo;
                            ConfigUtils.extensionid=weChatLogin.getExtensionid();
                            OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getWXCJSConfig(getApplicationContext()), null);
                            openLoginActivity();
                        }else{
                            startActivity(new Intent(this, BindingActivity.class).putExtra("wechat", weChatInfo).putExtra("invitation", weChatLogin.getExtensionid()));
                            // 关闭页面
                            isFinish();
                        }
                    } else {
                        // 设置登录状态
                        DateStorage.setLoginStatus(true);
                        // 通知登录成功
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
                        ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
                        // 关闭页面
                        isFinish();
                    }
                }
            }
        }
        if (message.what == LogicActions.UserInfoBindingWeChatSuccess) {
            toast(message.obj + "");
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("UserInfoActivityBinding"), "", 0);
            // 关闭页面
            isFinish();
        }


        if (message.what == LogicActions.RegisterWeChatSuccess) {
            // 隐藏弹窗
            dismissDialog();
            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
            // 设置登录状态
            DateStorage.setLoginStatus(true);
            // 通知登录成功
            Variable.AuthShowStatus=true;//不让授权登录窗口显示
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
            //注册成功，奖励金币弹框
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RegisterStatus"), true, 0);
            // 关闭登录页面
            ActivityManagerDefine.getInstance().popClass(InvitationActivity.class);
            ActivityManagerDefine.getInstance().popClass(BindingActivity.class);
            ActivityManagerDefine.getInstance().popClass(SignInActivity.class);
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
        }

        //微信调用一键登录绑定手机号接口成功返回
        if (message.what == LogicActions.WxBindPhonebySySuccess) {
                dismissDialog();
                UserInfo login = (UserInfo) message.obj;
                Variable.userPhone=login.getUserphone();
                if (login.getIsbinding().equals("0")) {
                    if (weChatLogin.getExtensionid().equals("")){
                        startActivity(new Intent(this, InvitationActivity.class).putExtra("wechat", weChatInfo));
                        isFinish();
                    }else{
                        showDialog();
                        // 请求注册
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("extensionid",weChatLogin.getExtensionid());
                        paramMap.put("userphone", login.getUserphone());
                        paramMap.put("userpwd", Utils.getMD5("123456"));
                        paramMap.put("wxuuid",  weChatInfo.getUnionid());
                        paramMap.put("wxmc", weChatInfo.getNickname());
                        paramMap.put("wxhead", weChatInfo.getHeadimgurl());
                        NetworkRequest.getInstance().POST(handler, paramMap, "RegisterWeChat", HttpCommon.RegisterWeChat);
                    }
                } else {
                    // 储存用户信息
                    DateStorage.setInformation(login);
                    // 设置登录状态
                    DateStorage.setLoginStatus(true);
                    // 通知登录成功
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 0);
                    ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
                    // 关闭页面
                    isFinish();
                }

            }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        // 微信发送请求到第三方应用时，会回调到该方法
    }

    @Override
    public void onResp(BaseResp baseResp) {
        // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
//            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//            this.startActivity(intent);
        }else {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:// 成功
                    if (Variable.weChatCheck) {
                        if (((SendAuth.Resp) baseResp).state.equals(Variable.weChatState)) {
                            String code = ((SendAuth.Resp) baseResp).code;
                            paramMap.clear();
                            paramMap.put("appid", Variable.weChatAppId);
                            paramMap.put("secret", Variable.weChatAppSecret);
                            paramMap.put("code", code);
                            paramMap.put("grant_type", "authorization_code");
                            //(必须用GET)
                            NetworkRequest.getInstance().GET(handler, paramMap, "AccessToken", HttpCommon.AccessToken);
                        }
                    } else {
                        ToastUtil.showImageToast(this,"分享成功",R.mipmap.toast_img);
                        isFinish();
                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// 用户拒绝授权
                    if (Variable.weChatCheck) {
                        toast("登录失败");
                    }
                    isFinish();
                    break;
                default:// 其他
                    isFinish();
                    break;
            }
        }
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressedSupport() {
        // 设置动画
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        isFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //微信一键登录拉取授权页方法
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
                    startActivity(new Intent(WXEntryActivity.this, BindingActivity.class).putExtra("wechat", weChatInfo).putExtra("invitation", weChatLogin.getExtensionid()));
                    // 关闭页面
                    isFinish();
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {
                if (1011 == code) {
                    Log.e("VVV", "用户点击授权页返回： _code==" + code + "   _result==" + result);
                    // 关闭页面
                    isFinish();
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
                        paramMap.put("wxuuid", weChatInfo.getUnionid());
                        paramMap.put("wxmc", weChatInfo.getNickname());
                        paramMap.put("wxhead", weChatInfo.getHeadimgurl());
                        NetworkRequest.getInstance().POST(handler, paramMap, "WxBindPhonebySy", HttpCommon.WxBindPhonebySy);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("VVV", "用户点击登录获取token失败： _code==" + code + "   _result==" + result);
                    startActivity(new Intent(WXEntryActivity.this, BindingActivity.class).putExtra("wechat", weChatInfo).putExtra("invitation", weChatLogin.getExtensionid()));
                    // 关闭页面
                    isFinish();
                }
            }
        });
    }
}
