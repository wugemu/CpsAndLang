package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarNewActivity extends BaseActivity implements OnTimeSelectListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.calendar_start_btn_text)
    TextView calendarStartBtnText;
    @BindView(R.id.calendar_start_btn)
    LinearLayout calendarStartBtn;
    @BindView(R.id.calendar_end_btn_text)
    TextView calendarEndBtnText;
    @BindView(R.id.calendar_end_btn)
    LinearLayout calendarEndBtn;
    @BindView(R.id.calendar_today)
    RadioButton calendarToday;
    @BindView(R.id.calendar_week)
    RadioButton calendarWeek;
    @BindView(R.id.calendar_month_one)
    RadioButton calendarMonthOne;
    @BindView(R.id.calendar_month_two)
    RadioButton calendarMonthTwo;
    @BindView(R.id.calendar_month_three)
    RadioButton calendarMonthThree;
    @BindView(R.id.calendar_btn)
    LinearLayout calendarBtn;
    private String startTime, endTime,LimitStartTime,LimitEndTime;
    private boolean isCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calendar);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        startTime = getIntent().getExtras().getString("startTime");
        endTime = getIntent().getExtras().getString("endTime");
        LimitStartTime = getIntent().getExtras().getString("LimitStartTime");
        LimitEndTime = getIntent().getExtras().getString("LimitEndTime");
        calendarStartBtnText.setText(startTime);
        calendarEndBtnText.setText(endTime);

        if (startTime.equals("")){
        calendarStartBtnText.setText(Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"),1));
        }
       if (endTime.equals("")){
        calendarEndBtnText.setText(Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"),1));
       }
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

    @OnClick({R.id.back, R.id.calendar_today, R.id.calendar_week, R.id.calendar_month_one, R.id.calendar_month_two, R.id.calendar_month_three, R.id.calendar_start_btn, R.id.calendar_end_btn, R.id.calendar_btn})
    public void onViewClicked(View view) {
        Calendar selectedDate,startDate;
        Calendar endDate = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.calendar_today:
                startTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarStartBtnText.setText(startTime);
                endTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarEndBtnText.setText(endTime);
                break;
            case R.id.calendar_week:
                startTime = Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 7);
                calendarStartBtnText.setText(startTime);
                endTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarEndBtnText.setText(endTime);
                break;
            case R.id.calendar_month_one:
                startTime = Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 30);
                calendarStartBtnText.setText(startTime);
                endTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarEndBtnText.setText(endTime);
                break;
            case R.id.calendar_month_two:
                startTime = Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 90);
                calendarStartBtnText.setText(startTime);
                endTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarEndBtnText.setText(endTime);
                break;
            case R.id.calendar_month_three:
                startTime = Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 180);
                calendarStartBtnText.setText(startTime);
                endTime = Utils.getCurrentDate("yyyy-MM-dd");
                calendarEndBtnText.setText(endTime);
                break;
            case R.id.calendar_start_btn:
                isCheck = true;
                selectedDate = Calendar.getInstance();
                if (!startTime.equals(""))
                selectedDate.set(Integer.parseInt(startTime.substring(0, 4)), Integer.parseInt(startTime.substring(5, 7)) - 1, Integer.parseInt(startTime.substring(8)));
                else
                selectedDate.set(Integer.parseInt(LimitStartTime.substring(0, 4)), Integer.parseInt(LimitStartTime.substring(5, 7)) - 1, Integer.parseInt(LimitStartTime.substring(8)));
                startDate = Calendar.getInstance();
                startDate.set(Integer.parseInt(LimitStartTime.substring(0, 4)), Integer.parseInt(LimitStartTime.substring(5, 7)) - 1, Integer.parseInt(LimitStartTime.substring(8)));
                endDate = Calendar.getInstance();
                if (!endTime.equals(""))
                endDate.set(Integer.parseInt(endTime.substring(0, 4)), Integer.parseInt(endTime.substring(5, 7)) - 1, Integer.parseInt(endTime.substring(8)));
                else
                endDate.set(Integer.parseInt(LimitEndTime.substring(0, 4)), Integer.parseInt(LimitEndTime.substring(5, 7)) - 1, Integer.parseInt(LimitEndTime.substring(8)));
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
            case R.id.calendar_end_btn:
                isCheck = false;
                selectedDate = Calendar.getInstance();
                if (!endTime.equals(""))
                selectedDate.set(Integer.parseInt(endTime.substring(0, 4)), Integer.parseInt(endTime.substring(5, 7)) - 1, Integer.parseInt(endTime.substring(8)));
                else{
                selectedDate.set(Integer.parseInt(LimitEndTime.substring(0, 4)), Integer.parseInt(LimitEndTime.substring(5, 7)) - 1, Integer.parseInt(LimitEndTime.substring(8)));
                }
                startDate = Calendar.getInstance();
                if (!startTime.equals(""))
                startDate.set(Integer.parseInt(startTime.substring(0, 4)), Integer.parseInt(startTime.substring(5, 7)) - 1, Integer.parseInt(startTime.substring(8)));
                else
                startDate.set(Integer.parseInt(LimitStartTime.substring(0, 4)), Integer.parseInt(LimitStartTime.substring(5, 7)) - 1, Integer.parseInt(LimitStartTime.substring(8)));
                endDate = Calendar.getInstance();
                endDate.set(Integer.parseInt(LimitEndTime.substring(0, 4)), Integer.parseInt(LimitEndTime.substring(5, 7)) - 1, Integer.parseInt(LimitEndTime.substring(8)));
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
            case R.id.calendar_btn:
                ArrayList<String> list = new ArrayList<>();
                list.add(startTime);
                list.add(endTime);
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("Calendar"), list, 0);
                isFinish();
                break;
        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        if (isCheck) {
            startTime = Utils.getDateToString(date.getTime(), "yyyy-MM-dd");
            calendarStartBtnText.setText(startTime);
        } else {
            endTime = Utils.getDateToString(date.getTime(), "yyyy-MM-dd");
            calendarEndBtnText.setText(endTime);
        }
    }
}
