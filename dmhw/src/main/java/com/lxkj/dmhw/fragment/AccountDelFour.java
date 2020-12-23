package com.lxkj.dmhw.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AccountDeleteActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户注销提交页
 */

public class AccountDelFour extends BaseFragment {

    @BindView(R.id.del_phone)
    EditText del_phone;
    @BindView(R.id.del_code)
    EditText del_code;
    @BindView(R.id.binding_btn_tv)
    TextView binding_btn_tv;
    @BindView(R.id.login_access_text)
    TextView loginAccessText;
    @BindView(R.id.login_access)
    LinearLayout loginAccess;
    @BindView(R.id.submit_btn)
    LinearLayout submit_btn;

    public static AccountDelFour getInstance() {
        AccountDelFour fragment = new AccountDelFour();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accout_del_four, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        AccountDeleteActivity accountDeleteActivity= (AccountDeleteActivity)getActivity();
        accountDeleteActivity.setDel_title("注销验证");
        del_phone.setText(AccountDelOne.accountDel.getUserphone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        del_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    submit_btn.setEnabled(true);
                    binding_btn_tv.setTextColor(Color.WHITE);
                    submit_btn.setBackgroundResource(R.drawable.accountdel_btn_style);
                }else{
                    submit_btn.setEnabled(false);
                    binding_btn_tv.setTextColor(Color.parseColor("#999999"));
                    submit_btn.setBackgroundResource(R.drawable.account_delete_one_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        // 获取验证码成功回调
        if (message.what == LogicActions.RegisterCodeSuccess) {
            toast(message.obj + "");
        }
        if (message.what == LogicActions.UserCancelSuccess) {
            ToastUtil.showImageToast(getActivity(),message.obj + "",R.mipmap.toast_img);
            DateStorage.setLoginStatus(false);
            DateStorage.setMyToken("");
            DateStorage.setMicroAppId("");
            DateStorage.setLittleAppId("");
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 100);
            logoutCreditCard();
            Unicorn.logout();//退出七鱼登录
            isFinish();
        }
        dismissDialog();
    }

    @OnClick({R.id.submit_btn,R.id.login_access})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_access:
                if (TextUtils.isEmpty(AccountDelOne.accountDel.getUserphone())) {
                    toast("手机号不能为空");
                } else {
                    // 隐藏输入法
                    Utils.hideSoftInput(getActivity(), null);
                    // 倒计时
                    countDown();
                    // 请求获取验证码
                    paramMap.clear();
                    paramMap.put("userphone", AccountDelOne.accountDel.getUserphone());
                    paramMap.put("reqsource", "00");
                    NetworkRequest.getInstance().POST(handler, paramMap, "RegisterCode", HttpCommon.RegisterCode);
                }
                break;
            case R.id.submit_btn:
                showDialog();
                paramMap.clear();
                paramMap.put("userphone", AccountDelOne.accountDel.getUserphone());
                paramMap.put("smscode", del_code.getText().toString());
                paramMap.put("reason", AccountDelTwo.reason);
                paramMap.put("reasonother", AccountDelTwo.otherreason);
                NetworkRequest.getInstance().POST(handler, paramMap, "UserCancel", HttpCommon.UserCancel);
                break;
        }
    }
    /**
     * 倒计时
     */
    private void countDown() {
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    // 设置获取验证码按钮不可点击
                    if (loginAccess==null){
                        this.cancel();
                        return;
                    }
                    loginAccess.setEnabled(false);
                    loginAccess.setBackground(getResources().getDrawable((R.drawable.banding_wechat_count_style)));
                    loginAccessText.setText(millisUntilFinished / 1000 + "s");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    if (loginAccessText==null){
                        this.cancel();
                        return;
                    }
                    loginAccessText.setText("重新发送");
                    loginAccess.setEnabled(true);
                    loginAccess.setBackground(getResources().getDrawable((R.drawable.banding_wechat_code_style)));
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }

    private void  logoutCreditCard(){
        KDFInterface.getInstance().logout(getActivity(), new OnComplete() {
            @Override
            public void onSuccess(JSONObject o) {
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
