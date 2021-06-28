package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.fragment.OrderFragment_My;
import com.lxkj.dmhw.fragment.OrderFragment_Team;
import com.lxkj.dmhw.logic.LogicActions;

import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScaleLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单页
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.order_calendar)
    ImageView orderCalendar;
    @BindView(R.id.order_content)
    ViewPager orderContent;
    @BindView(R.id.order_start_time)
    TextView orderStartTime;
    @BindView(R.id.order_end_time)
    TextView orderEndTime;
    @BindView(R.id.order_tips)
    TextView order_tips;


    @BindView(R.id.myorder_btn)
    ScaleLayout myorder_btn;
    @BindView(R.id.teamorder_btn)
    ScaleLayout teamorder_btn;
    @BindView(R.id.myorder_btn_txt)
    TextView myorder_btn_txt;
    @BindView(R.id.teamorder_btn_txt)
    TextView teamorder_btn_txt;
    @BindView(R.id.order_sort_title)
    TextView order_sort_title;


    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private OrderFragment_My my;
    private OrderFragment_Team team;
    // 筛选时间
    private ArrayList<String> list = new ArrayList<>();


    public static OrderActivity orderActivity;

    public static int platForm=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        orderActivity=this;
        platForm=getIntent().getIntExtra("platform",0);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        switch(platForm){
            case 0:
                order_sort_title.setText("淘宝订单");
                break;
            case 1:
                order_sort_title.setText("拼多多");
                break;
            case 2:
                order_sort_title.setText("京东");
                break;
            case 3:
                order_sort_title.setText("唯品会");
                break;
            case 4:
                order_sort_title.setText("美团外卖");
                break;
            case 5:
                order_sort_title.setText("苏宁易购");
                break;
            case 6:
                order_sort_title.setText("考拉海购");
                break;
        }



        orderStartTime.setText(Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 30));
        orderEndTime.setText(Utils.getCurrentDate("yyyy-MM-dd"));

        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        my = OrderFragment_My.getInstance();
        team = OrderFragment_Team.getInstance();
        fragments.add(my);
        fragments.add(team);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        orderContent.setAdapter(fragmentAdapter);
        orderContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            changeTopTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.CalendarSuccess) {
            list = (ArrayList<String>) message.obj;
            orderStartTime.setText(list.get(0));
            orderEndTime.setText(list.get(1));
            my.calendar(list.get(0), list.get(1));
            team.calendar(list.get(0), list.get(1));
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            startActivity(new Intent(this, WebViewActivity.class).putExtra("isTitle" , true).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
        }
    }

    @OnClick({R.id.back, R.id.order_calendar,R.id.myorder_btn,R.id.teamorder_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.order_calendar:
                startActivity(new Intent(this, CalendarActivity.class).putExtra("startTime", orderStartTime.getText().toString()).putExtra("endTime", orderEndTime.getText().toString()));
                break;
            case R.id.myorder_btn:
                changeTopTab(0);
                break;
            case R.id.teamorder_btn:
                changeTopTab(1);
                break;

        }
    }

    public String getStart() {
        return orderStartTime.getText().toString();
    }

    public String getEnd() {
        return orderEndTime.getText().toString();
    }

    public void gettip(String tip){
        order_tips.setText(tip);
    }


    private void  changeTopTab(int pos) {
        switch (pos) {
            case 0:
                orderContent.setCurrentItem(pos);
                myorder_btn.setBackgroundResource(R.drawable.bg_rect_black_20dp);
                teamorder_btn.setBackgroundResource(0);
                teamorder_btn_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                myorder_btn_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                myorder_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                teamorder_btn_txt.setTextColor(Color.parseColor("#666666"));
                break;
            case 1:
                orderContent.setCurrentItem(pos);
                myorder_btn.setBackgroundResource(0);
                teamorder_btn.setBackgroundResource(R.drawable.bg_rect_black_20dp);
                myorder_btn_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                teamorder_btn_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                myorder_btn_txt.setTextColor(Color.parseColor("#666666"));
                teamorder_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
