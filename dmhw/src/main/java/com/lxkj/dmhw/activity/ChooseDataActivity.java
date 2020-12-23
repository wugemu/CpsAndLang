package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseDataActivity extends BaseActivity implements OnTimeSelectListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.cancel_layout)
    LinearLayout cancel_layout;
    @BindView(R.id.phone_layout)
    LinearLayout phone_layout;
    @BindView(R.id.phone_edit)
    EditText phone_edit;

    @BindView(R.id.start_time_edit_layout)
    LinearLayout start_time_edit_layout;
    @BindView(R.id.start_time_edit)
    TextView start_time_edit;
    @BindView(R.id.end_time_edit_layout)
    LinearLayout end_time_edit_layout;
    @BindView(R.id.end_time_edit)
    TextView end_time_edit;

    @BindView(R.id.start_menber_edit_layout)
    LinearLayout start_menber_edit_layout;
    @BindView(R.id.start_menber_edit)
    EditText start_menber_edit;
    @BindView(R.id.end_menber_edit_layout)
    LinearLayout end_menber_edit_layout;
    @BindView(R.id.end_menber_edit)
    EditText end_menber_edit;

    @BindView(R.id.start_month_edit_layout)
    LinearLayout start_month_edit_layout;
    @BindView(R.id.start_month_edit)
    EditText start_month_edit;
    @BindView(R.id.end_month_edit_layout)
    LinearLayout end_month_edit_layout;
    @BindView(R.id.end_month_edit)
    EditText end_month_edit;

    @BindView(R.id.start_lastmonth_edit_layout)
    LinearLayout start_lastmonth_edit_layout;
    @BindView(R.id.start_lastmonth_edit)
    EditText start_lastmonth_edit;
    @BindView(R.id.end_lastmonth_edit_layout)
    LinearLayout end_lastmonth_edit_layout;
    @BindView(R.id.end_lastmonth_edit)
    EditText end_lastmonth_edit;

    @BindView(R.id.start_profile_edit_layout)
    LinearLayout start_profile_edit_layout;
    @BindView(R.id.start_profile_edit)
    EditText start_profile_edit;
    @BindView(R.id.end_profile_edit_layout)
    LinearLayout end_profile_edit_layout;
    @BindView(R.id.end_profile_edit)
    EditText end_profile_edit;

    @BindView(R.id.reset_btn)
    Button reset_btn;
    @BindView(R.id.check_btn)
    Button check_btn;

    public static String startdate="";//加入起始时间
    public static String enddate="";//加入截止时间
    public static  String  key="";//手机号码或者昵称
    public static String  startcnt="";//起始会员数量
    public static String  endcnt="";//截止会员数量
    public static String  startpre="";//起始本月预估
    public static String  endpre="";//截至本月预估
    public static String  startmonth="";//起始上月预估
    public static String  endmonth="";//起始上月预估
    public static String  startprofit="";//起始累计收益
    public static String  endprofit="";//截止累计收益


    private boolean isStartorEnd;

    private String limitEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        limitEndTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1);
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

    @OnClick({R.id.back,R.id.reset_btn,R.id.check_btn,R.id.start_time_edit_layout,R.id.end_time_edit_layout})
    public void onViewClicked(View view) {
        Calendar selectedDate,startDate;
        Calendar endDate = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.reset_btn:
                phone_edit.setText("");
                start_time_edit.setText("");
                end_time_edit.setText("");
                start_month_edit.setText("");
                end_month_edit.setText("");
                start_menber_edit.setText("");
                end_menber_edit.setText("");
                start_lastmonth_edit.setText("");
                end_lastmonth_edit.setText("");
                start_profile_edit.setText("");
                end_profile_edit.setText("");
                break;
            case R.id.check_btn:
                key=phone_edit.getText().toString();
                startdate=start_time_edit.getText().toString();
                enddate=end_time_edit.getText().toString();
                startcnt=start_menber_edit.getText().toString();
                endcnt=end_menber_edit.getText().toString();
                startpre=start_month_edit.getText().toString();
                endpre=end_month_edit.getText().toString();
                startmonth=start_lastmonth_edit.getText().toString();
                endmonth=end_lastmonth_edit.getText().toString();
                startprofit=start_profile_edit.getText().toString();
                endprofit=end_profile_edit.getText().toString();
                if (key.equals("")&&startdate.equals("")&&enddate.equals("")&&startcnt.equals("")&&endcnt.equals("")&&startpre.equals("")&&endpre.equals("")&&startmonth.equals("")&&endmonth.equals("")&&startprofit.equals("")&&endprofit.equals("")){
                    isFinish();
                    return;
                }
                if (startcnt.length()>0&&startcnt.length()>0&&Integer.parseInt(startcnt)>Integer.parseInt(endcnt)){
                   toast("会员数前面的数要小于后面的数");
                   return;
                }

                if (startpre.length()>0&&endpre.length()>0&&Integer.parseInt(startpre)>Integer.parseInt(endpre)){
                    toast("本月预估前面的数要小于后面的数");
                    return;
                }

                if (startmonth.length()>0&&endmonth.length()>0&&Integer.parseInt(startmonth)>Integer.parseInt(endmonth)){
                    toast("上月预估前面的数要小于后面的数");
                    return;
                }


                if (startprofit.length()>0&&endprofit.length()>0&&Integer.parseInt(startprofit)>Integer.parseInt(endprofit)){
                    toast("累计收益前面的数要小于后面的数");
                    return;
                }
                List<String> searchList=new ArrayList<>();
                searchList.add(0,key);
                searchList.add(1,startdate);
                searchList.add(2,enddate);
                searchList.add(3,startcnt);
                searchList.add(4,endcnt);
                searchList.add(5,startpre);
                searchList.add(6,endpre);
                searchList.add(7,startmonth);
                searchList.add(8,endmonth);
                searchList.add(9,startprofit);
                searchList.add(10,endprofit);
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ChooseData"),  searchList, 0);
                isFinish();
                break;
            case R.id.start_time_edit_layout:
                hideInput();
                isStartorEnd=true;
                selectedDate = Calendar.getInstance();
                if (start_time_edit.getText().toString().length()>0){
                    selectedDate.set(Integer.parseInt(start_time_edit.getText().toString().substring(0, 4)), Integer.parseInt(start_time_edit.getText().toString().substring(5, 7)) - 1, Integer.parseInt(start_time_edit.getText().toString().substring(8)));
                }
                endDate=Calendar.getInstance();
                if (end_time_edit.getText().toString().length()>0) {
                    endDate.set(Integer.parseInt(end_time_edit.getText().toString().substring(0, 4)), Integer.parseInt(end_time_edit.getText().toString().substring(5, 7)) - 1, Integer.parseInt(end_time_edit.getText().toString().substring(8)));
                }else{
                    endDate.set(Integer.parseInt( limitEndTime .substring(0, 4)), Integer.parseInt( limitEndTime .substring(5, 7)) - 1, Integer.parseInt( limitEndTime .substring(8)));

                }
                startDate = Calendar.getInstance();
                startDate.set(2017,0,1);
                new TimePickerBuilder(this, this)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setCancelColor(0xff666666)
                        .setSubmitColor(getResources().getColor(R.color.black))
                        .setSubCalSize(15)
                        .setContentTextSize(15)
                        .setTitleText("起始时间")
                        .setTitleColor(Color.BLACK)
                        .setTitleSize(15)
                        .setOutSideCancelable(true)
                        .isCyclic(false)
                        .setTitleBgColor(Color.WHITE)
                        .setBgColor(Color.WHITE)
                        .setRangDate(startDate, endDate)
                        .setDate(selectedDate)
                        .build()
                        .show();
                break;
            case R.id.end_time_edit_layout:
                hideInput();
                isStartorEnd=false;
                selectedDate = Calendar.getInstance();
                if (end_time_edit.getText().toString().length()>0) {
                    selectedDate.set(Integer.parseInt(end_time_edit.getText().toString().substring(0, 4)), Integer.parseInt(end_time_edit.getText().toString().substring(5, 7)) - 1, Integer.parseInt(end_time_edit.getText().toString().substring(8)));
                }
                startDate = Calendar.getInstance();
                if (start_time_edit.getText().toString().length()>0) {
                    startDate.set(Integer.parseInt(startdate.substring(0, 4)), Integer.parseInt(startdate.substring(5, 7)) - 1, Integer.parseInt(startdate.substring(8)));
                }else{
                    startDate.set(2017,0,1);
                }
                new TimePickerBuilder(this, this)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setCancelColor(0xff666666)
                        .setSubmitColor(getResources().getColor(R.color.black))
                        .setSubCalSize(15)
                        .setContentTextSize(15)
                        .setTitleText("结束时间")
                        .setTitleColor(Color.BLACK)
                        .setTitleSize(15)
                        .setOutSideCancelable(true)
                        .isCyclic(false)
                        .setTitleBgColor(Color.WHITE)
                        .setBgColor(Color.WHITE)
                        .setRangDate(startDate, endDate)
                        .setDate(selectedDate)
                        .build()
                        .show();
                break;

        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        if (isStartorEnd) {
            startdate = Utils.getDateToString(date.getTime(), "yyyy-MM-dd");
            start_time_edit.setText(startdate);
        } else {
            enddate = Utils.getDateToString(date.getTime(), "yyyy-MM-dd");
            end_time_edit.setText(enddate);
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
