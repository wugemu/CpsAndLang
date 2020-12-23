package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.MyIncome;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyIncomeActivity extends BaseActivity {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.income_detail_btn)
    View income_detail_btn;
    @BindView(R.id.pl_income_detail_btn)
    View pl_income_detail_btn;

    @BindView(R.id.income_01)
    TextView income_01;
    @BindView(R.id.income_02)
    TextView income_02;
    @BindView(R.id.pl_income_01)
    TextView pl_income_01;
    @BindView(R.id.pl_income_02)
    TextView pl_income_02;
    private ArrayList<MyIncome> list;

    private  HashMap<String,String> ProfitData=null;//收益总览数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }

        if (TempDataSetActivity.isProfitTotalCanEdit){
                ProfitData=TempDataSetActivity.ProfitTotalData;
                income_01.setText(ProfitData.get("taobaowithdraw"));
                income_02.setText(ProfitData.get("taobaototal"));
                pl_income_01.setText(ProfitData.get("moreplwithdraw"));
                pl_income_02.setText(ProfitData.get("morepltotal"));
        }else{
            showDialog();
            paramMap = new HashMap<>();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "ProfitSum", HttpCommon.ProfitSum);
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
     if (message.what== LogicActions.ProfitSumSuccess){
       dismissDialog();
       list=(ArrayList<MyIncome>)message.obj;
        if (list!=null&&list.size()>0){
            income_01.setText(list.get(0).getUsercommission());
            income_02.setText(list.get(0).getAccumulatedincome());
            pl_income_01.setText(list.get(1).getUsercommission());
            pl_income_02.setText(list.get(1).getAccumulatedincome());
        }
     }
    }

    @OnClick({R.id.back,R.id.income_detail_btn,R.id.pl_income_detail_btn})
    public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.back:
                    isFinish();
                    break;
                case R.id.income_detail_btn:
                   Intent intent = new Intent(this, ProfitActivity.class);
                   startActivity(intent);
                    break;
                case R.id.pl_income_detail_btn:
                    Intent intent1 = new Intent(this, PlatformProfitActivity.class);
                    startActivity(intent1);
                    break;
        }

    }
}
