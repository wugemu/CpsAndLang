package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.SaveDialog;
import com.lxkj.dmhw.dialog.TaskDMBDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeDMBActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_right_btn)
    TextView title_right_btn;
    @BindView(R.id.hint_title2)
    TextView hint_title2;
    @BindView(R.id.question_iv)
    ImageView question_iv;
    @BindView(R.id.all_exchange_layout)
    RelativeLayout all_exchange_layout;
    @BindView(R.id.dmb_count_edit)
    EditText dmb_count_edit;
    @BindView(R.id.dialog_name_setting_confirm)
    LinearLayout dialog_name_setting_confirm;
    @BindView(R.id.hint_title2_layout)
    LinearLayout hint_title2_layout;
    @BindView(R.id.toast_text)
    TextView toast_text;
    private String totalDmbCount="0";
    private String exchangeRatio="100";
    private float canChangeMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_dmb);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        totalDmbCount=getIntent().getStringExtra("totalDmbCount");
        exchangeRatio=getIntent().getStringExtra("exchangeRatio");
        if (totalDmbCount!=null&&exchangeRatio!=null) {
            canChangeMoney = Float.parseFloat(totalDmbCount) / Float.parseFloat(exchangeRatio);
        }
        hint_title2.setText("可兑换的NN币"+totalDmbCount+"个价值共"+canChangeMoney+"元");
        dialog_name_setting_confirm.setEnabled(false);
        dmb_count_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    toast_text.setVisibility(View.GONE);
                    hint_title2_layout.setVisibility(View.VISIBLE);
                    dialog_name_setting_confirm.setEnabled(false);
                    dialog_name_setting_confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_btn_style));
                    return;
                }
                if (totalDmbCount==null){
                    return;
                }
                if (Integer.parseInt(dmb_count_edit.getText().toString())>Integer.parseInt(totalDmbCount)){
                    toast_text.setVisibility(View.VISIBLE);
                    toast_text.setText("超过可兑换的NN币");
                    hint_title2_layout.setVisibility(View.GONE);
                    dialog_name_setting_confirm.setEnabled(false);
                    dialog_name_setting_confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_btn_style));
                    return;
                }
                toast_text.setVisibility(View.GONE);
                hint_title2_layout.setVisibility(View.VISIBLE);
                dialog_name_setting_confirm.setEnabled(true);
                dialog_name_setting_confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_check_btn_style));
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
        if (message.what==LogicActions.ExchangeScoreSuccess){
         toast("兑换成功");
         InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshTaskList"), "", 0);
         finish();
        }
        dismissDialog();

    }

    @OnClick({R.id.back,R.id.title_right_btn,R.id.question_iv,R.id.all_exchange_layout,R.id.dialog_name_setting_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.title_right_btn://NN币明细
                Intent intent=new Intent(this, ScoreActivity.class).putExtra("positionname","dmb");
                startActivity(intent);
                break;
            case R.id.question_iv://说明弹框
                TaskDMBDialog dialog = new TaskDMBDialog(this);
                dialog.showDialog("兑换须知","1、人民币与NN币换算比率为1:"+exchangeRatio+"\n"+"2、最低兑换个数为100个");
                break;
            case R.id.all_exchange_layout:// 全部兑换
               if (totalDmbCount!=null){
                   if (Integer.parseInt(totalDmbCount)>=100){
                       toast_text.setVisibility(View.GONE);
                       dmb_count_edit.setText(totalDmbCount);
                       hint_title2_layout.setVisibility(View.VISIBLE);
                   }else{
                       hint_title2_layout.setVisibility(View.GONE);
                       toast_text.setVisibility(View.VISIBLE);
                       toast_text.setText("兑换个数至少要100个");
                   }
               }
                break;
            case R.id.dialog_name_setting_confirm:// NN币兑换  exchangescore
                if (dmb_count_edit.getText().toString().length()>0&&Integer.parseInt(dmb_count_edit.getText().toString())<100){
                    toast_text.setVisibility(View.VISIBLE);
                    toast_text.setText("兑换个数至少要100个");
                    hint_title2_layout.setVisibility(View.GONE);
                    toast("兑换个数至少要100个");
                return;
                }
                if (Integer.parseInt(dmb_count_edit.getText().toString())>Integer.parseInt(totalDmbCount)){
                    toast("超过可兑换的NN币");
                    return;
                }
                paramMap = new HashMap<>();
                paramMap.put("score", dmb_count_edit.getText().toString());
                NetworkRequest.getInstance().POST(handler, paramMap, "ExchangeScore", HttpCommon.ExchangeScore);
                break;
        }
    }


}
