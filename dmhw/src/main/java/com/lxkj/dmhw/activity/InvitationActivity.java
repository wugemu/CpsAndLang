package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Invitation;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitationActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.invitation_edit)
    EditText invitationEdit;
    @BindView(R.id.invitation_btn_text)
    TextView invitationBtnText;
    @BindView(R.id.invitation_btn)
    LinearLayout invitationBtn;
    @BindView(R.id.invitation_info_avatar)
    ImageView invitationInfoAvatar;
    @BindView(R.id.invitation_info_name)
    TextView invitationInfoName;
    @BindView(R.id.invitation_info)
    LinearLayout invitationInfo;
    @BindView(R.id.wechat_name_layout)
    LinearLayout wechat_name_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        invitationEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_selected)));
                }else{
                    wechat_name_layout.setBackground(getResources().getDrawable((R.drawable.banding_wechat_name_unselect)));
                }
            }
        });
        invitationEdit.addTextChangedListener(this);
        invitationBtn.setEnabled(false);

    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetInvitationSuccess) {
            Invitation invitation = (Invitation) message.obj;
            if (!invitation.getMsgstr().equals("")){
                toast(invitation.getMsgstr());
            }
            if (invitation.getIsexist().equals("0")) {
                invitationInfo.setVisibility(View.GONE);
                invitationBtn.setBackgroundResource(R.drawable.banding_wechat_btn_style);
                invitationBtnText.setText("请输入正确的邀请码");
                invitationBtnText.setTextColor(0xffffffff);
                invitationBtn.setEnabled(false);
            } else {
                invitationInfo.setVisibility(View.VISIBLE);
                invitationBtn.setBackgroundColor(Color.parseColor("#000000"));
                if (Variable.loginByCode){
                    invitationBtnText.setText("登录");
                }else{
                    invitationBtnText.setText("登录");
                }
                invitationBtnText.setTextColor(Color.WHITE);
                Utils.displayImageCircular(this, invitation.getUserpicurl(), invitationInfoAvatar);
                invitationInfoName.setText(invitation.getUsername());
                invitationBtn.setEnabled(true);
            }
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
            ActivityManagerDefine.getInstance().popClass(SignInActivity.class);
            ActivityManagerDefine.getInstance().popClass(BindingActivity.class);
            ActivityManagerDefine.getInstance().popClass(LoginActivity.class);
            // 关闭页面
            isFinish();
        }
    }

    @OnClick({R.id.back, R.id.invitation_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.invitation_btn:
                   showDialog();
                    WeChatUserInfo info = (WeChatUserInfo) getIntent().getSerializableExtra("wechat");
                    // 请求微信注册
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    paramMap.put("extensionid",invitationEdit.getText().toString());
                    paramMap.put("userphone", Variable.userPhone);
                    paramMap.put("userpwd", Utils.getMD5("123456"));
                    paramMap.put("wxuuid",  info.getUnionid());
                    paramMap.put("wxmc", info.getNickname());
                    paramMap.put("wxhead", info.getHeadimgurl());
                    NetworkRequest.getInstance().POST(handler, paramMap, "RegisterWeChat", HttpCommon.RegisterWeChat);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 输入前的监听
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 输入的内容变化的监听
        if (s.toString().length() == 6) {
            paramMap.clear();
            paramMap.put("extensionid", s.toString());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetInvitation", HttpCommon.GetInvitation);
        } else {
            invitationInfo.setVisibility(View.GONE);
            invitationBtn.setBackgroundResource(R.drawable.banding_wechat_btn_style);
            invitationBtnText.setText("请输入正确的邀请码");
            invitationBtnText.setTextColor(0xffffffff);
            invitationBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 输入后的监听
    }
}
