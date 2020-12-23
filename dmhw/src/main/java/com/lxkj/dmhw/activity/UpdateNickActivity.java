package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNickActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.dialog_name_setting_confirm)
    LinearLayout dialog_name_setting_confirm;
    @BindView(R.id.nick_name_setting_edit)
    EditText nick_name_setting_edit;
    @BindView(R.id.title_id)
    TextView title_id;
    @BindView(R.id.hint_id)
    TextView alert_id;
    @BindView(R.id.setting_confirm_save_txt)
    TextView setting_confirm_save_txt;

    private String  name;
    private String title;
    private String hint;
    private String alert;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenick);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        Intent intent =getIntent();
        type=intent.getIntExtra("type",0);
        title=intent.getStringExtra("title");
        hint=intent.getStringExtra("hint");
        alert=intent.getStringExtra("alert");
        title_id.setText(title);
        alert_id.setText(alert);
        nick_name_setting_edit.setHint(hint);
        if (type==111) {
            name=intent.getStringExtra("nickname");
            nick_name_setting_edit.setText(name);
        }else{
            //获取微信号
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetService", HttpCommon.GetService);
            showDialog();

        }

        if (nick_name_setting_edit.getText().toString().length()>0) {
            dialog_name_setting_confirm.setBackgroundResource(R.drawable.logout_btn_bg);
            setting_confirm_save_txt.setTextColor(Color.parseColor("#ffffff"));
            dialog_name_setting_confirm.setEnabled(true);
        }else{
            dialog_name_setting_confirm.setBackgroundResource(R.drawable.nocheck_btn_bg);
            setting_confirm_save_txt.setTextColor(Color.parseColor("#999999"));
            dialog_name_setting_confirm.setEnabled(false);
        }


        nick_name_setting_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nick_name_setting_edit.getText().toString().length()>0) {
                    dialog_name_setting_confirm.setBackgroundResource(R.drawable.logout_btn_bg);
                    setting_confirm_save_txt.setTextColor(Color.parseColor("#ffffff"));
                    dialog_name_setting_confirm.setEnabled(true);
                }else{
                    dialog_name_setting_confirm.setBackgroundResource(R.drawable.nocheck_btn_bg);
                    setting_confirm_save_txt.setTextColor(Color.parseColor("#999999"));
                    dialog_name_setting_confirm.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        dismissDialog();
        if (message.what == LogicActions.GetServiceSuccess) {
            Service service = (Service) message.obj;
            nick_name_setting_edit.setText(service.getWxcode());
            name=service.getWxcode();
        }
        if (message.what == LogicActions.SetServiceSuccess) {
            toast(message.obj + "");
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("UpdateUserInfo"), "", 0);
            isFinish();
        }
    }

    @OnClick({R.id.dialog_name_setting_confirm,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.dialog_name_setting_confirm: {
                if (type==111) {
                    UserInfo login = DateStorage.getInformation();
                    if (nick_name_setting_edit.getText().toString().length() < 1) {
                        toast("昵称至少要输入一个字！");
                    } else {
                        if (Objects.equals(login.getUsername(), nick_name_setting_edit.getText().toString())) {
                            isFinish();
                        } else {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("NameSettingDialog"), nick_name_setting_edit.getText().toString(), 0);
                            Intent intent = new Intent();
                            // 获取用户计算后的结果
                            String text = nick_name_setting_edit.getText().toString();
                            intent.putExtra("nickname", text); //将计算的值回传回去
                            //通过intent对象返回结果，必须要调用一个setResult方法，
                            //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                            setResult(2, intent);
                            isFinish();
                        }
                    }
                }else{//设置客服处理
                    if (nick_name_setting_edit.getText().toString().equals(name)) {
                      toast("没有修改，无需保存");
                    }else{
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("wxcode", nick_name_setting_edit.getText().toString());
                        NetworkRequest.getInstance().POST(handler, paramMap, "SetService", HttpCommon.SetService);
                        showDialog();
                    }
                    }
            }


            }


        }



}
