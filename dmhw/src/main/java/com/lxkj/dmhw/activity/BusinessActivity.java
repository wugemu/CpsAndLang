package com.lxkj.dmhw.activity;

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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessActivity extends BaseActivity  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.company_name)
    EditText company_name;
    @BindView(R.id.job_name)
    EditText job_name;
    @BindView(R.id.your_name)
    EditText your_name;
    @BindView(R.id.your_phone)
    EditText your_phone;
    @BindView(R.id.business_type)
    TextView business_type;
    @BindView(R.id.business_explain)
    TextView business_explain;
    @BindView(R.id.business_sumit_btn)
    LinearLayout business_sumit_btn;
    @BindView(R.id.business_sumit_txt)
    TextView business_sumit_txt;
    @BindView(R.id.business_type_layout)
    LinearLayout business_type_layout;

    private OptionsPickerView mPickerView;
    private List<String> list=new ArrayList<>();

    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        business_sumit_btn.setEnabled(false);
        company_name.setFocusable(true);
        company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (company_name.getText().toString().length()>0&&your_name.getText().toString().length()>0&&your_phone.getText().toString().length()>0&&business_type.getText().toString().length()>0){
                business_sumit_btn.setBackgroundResource(R.drawable.logout_btn_bg);
                business_sumit_txt.setTextColor(Color.parseColor("#ffffff"));
                business_sumit_btn.setEnabled(true);
            }else{
                business_sumit_btn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                business_sumit_txt.setTextColor(Color.parseColor("#999999"));
                business_sumit_btn.setEnabled(false);
            }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        your_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (company_name.getText().toString().length()>0&&your_name.getText().toString().length()>0&&your_phone.getText().toString().length()>0&&business_type.getText().toString().length()>0){
                    business_sumit_btn.setBackgroundResource(R.drawable.logout_btn_bg);
                    business_sumit_txt.setTextColor(Color.parseColor("#ffffff"));
                    business_sumit_btn.setEnabled(true);
                }else{
                    business_sumit_btn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                    business_sumit_txt.setTextColor(Color.parseColor("#999999"));
                    business_sumit_btn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        your_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (company_name.getText().toString().length()>0&&your_name.getText().toString().length()>0&&your_phone.getText().toString().length()>0&&business_type.getText().toString().length()>0){
                    business_sumit_btn.setBackgroundResource(R.drawable.logout_btn_bg);
                    business_sumit_txt.setTextColor(Color.parseColor("#ffffff"));
                    business_sumit_btn.setEnabled(true);
                }else{
                    business_sumit_btn.setBackgroundResource(R.drawable.nocheck_btn_bg);
                    business_sumit_txt.setTextColor(Color.parseColor("#999999"));
                    business_sumit_btn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        list.add("合作加盟");
        list.add("商品推广");
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
      if (message.what==LogicActions.BusinessCooperationSuccess){
          dismissDialog();
          ToastUtil.showImageToast(this,"提交成功",R.mipmap.toast_img);
          isFinish();
      }
    }

    @OnClick({R.id.back,R.id.business_sumit_btn,R.id.business_type_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.business_sumit_btn:
                showDialog();
                paramMap = new HashMap<>();
                paramMap.put("userid", login.getUserid());
                paramMap.put("company", company_name.getText().toString());
                paramMap.put("post", job_name.getText().toString());
                paramMap.put("fullname", your_name.getText().toString());
                paramMap.put("phone",your_phone.getText().toString() );
                paramMap.put("mode",business_type.getText().toString());
                NetworkRequest.getInstance().POST(handler, paramMap, "BusinessCooperation", HttpCommon.BusinessCooperation);
                break;
            case R.id.business_type_layout:
                 boolean isContinue=true;
                for (int i = 0; i < list.size() &&isContinue; i++) {
                    //设置选中项
                    if (business_type.getText().equals(list.get(i))) {
                        position = i;
                        isContinue = false;
                    }
                }
                getInstance(position).show();
                break;

        }
    }


    private OptionsPickerView getInstance(int selectPos) {
        mPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                business_type.setText(list.get(options1));
                switch (list.get(options1)){
                    case "团队入驻":
                        business_explain.setText(R.string.str_bussiness_team);
                        break;
                    case "商品推广":
                        business_explain.setText(R.string.str_bussiness_goods);
                        break;
                    case "技术合作&软件开发业务":
                        business_explain.setText(R.string.str_bussiness_tech);
                        break;
                }
            }
        })      .setCancelText("取消")
                .setSubmitText("确定")
                .setCancelColor(0xff666666)
                .setSubmitColor(0xFF000000)
                .setSubCalSize(17)
                .setTextColorCenter(0xff333333)
                .setContentTextSize(17)
                .setLineSpacingMultiplier(2.0f)
                .setDividerColor(Color.parseColor("#F2F2F2"))
                .setTitleColor(Color.BLACK)
                .setTitleSize(15)
                .setSelectOptions(position)  //设置默认选中项
                .setOutSideCancelable(true)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .build();
        mPickerView.setPicker(list);//一级选择器
        return mPickerView;
    }
}
