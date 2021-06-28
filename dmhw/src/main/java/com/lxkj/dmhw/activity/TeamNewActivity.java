package com.lxkj.dmhw.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.TotalDataAdapter;
import com.lxkj.dmhw.bean.MyTeamTodayInfo;
import com.lxkj.dmhw.bean.MyTeamTotalInfo;
import com.lxkj.dmhw.bean.TeamUpdataInfo;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的团队页
 */
public class TeamNewActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.team_image)
    ImageView teamImage;

    @BindView(R.id.tuijain_layout)
    LinearLayout tuijain_layout;

    @BindView(R.id.myteamtoday_layout)
    LinearLayout  myteamtoday_layout;

    @BindView(R.id.team_time_layout)
    LinearLayout  team_time_layout;

    @BindView(R.id.today_layout_01)
    LinearLayout today_layout_01;
    @BindView(R.id.today_layout_02)
    LinearLayout today_layout_02;
    @BindView(R.id.today_layout_03)
    LinearLayout today_layout_03;


    @BindView(R.id.team_today_cnt_01)
    TextView team_today_cnt_01;
    @BindView(R.id.team_today_cnt_02)
    TextView team_today_cnt_02;
    @BindView(R.id.team_today_cnt_03)
    TextView team_today_cnt_03;

    @BindView(R.id.team_today_title_01)
    TextView  team_today_title_01;
    @BindView(R.id.team_today_title_02)
    TextView  team_today_title_02;
    @BindView(R.id.team_today_title_03)
    TextView  team_today_title_03;
    @BindView(R.id.team_start_time)
    TextView StartTime_tv;
    @BindView(R.id.team__end_time)
    TextView EndTime_tv;
    @BindView(R.id.team_calendar)
    ImageView team_calendar;
    @BindView(R.id.split_tv)
    View split_tv;

    @BindView(R.id.fragment_my_team_recycler)
    RecyclerView fragment_my_team_recycler;
    private TotalDataAdapter adapter;
    // 筛选时间
    private ArrayList<String> list = new ArrayList<>();

    //起始时间
    private String StartTime;
    //截止时间
    private String EndTime;

    //限制起始时间
    private String LimitStartTime;
    //限制截止时间
    private String LimitEndTime;
    private ArrayList<MyTeamTodayInfo> dataList=new ArrayList<>();
    private ArrayList<MyTeamTotalInfo> totalDataList=new ArrayList<>();

    @BindView(R.id.total_number)
    TextView  total_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        StartTime="";
        EndTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1);
        EndTime_tv.setText(EndTime);
        split_tv.setVisibility(View.GONE);
        StartTime_tv.setText("截止至");

        LimitStartTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 30);
        LimitEndTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1);

        GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        fragment_my_team_recycler.setLayoutManager(layoutManage);
        httppost();
    }

    //http请求
    private void httppost(){
          showDialog();
        // 获取上级用户信息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "ParentUserInfo", HttpCommon.ParentUserInfo);
        // 获取新增数据
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("date", Utils.getCurrentDate("yyyy-MM-dd"));//获取今日日期
        NetworkRequest.getInstance().POST(handler, paramMap, "MyTeamToday", HttpCommon.MyTeamToday);
        // 获取团队总数据
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startdate",StartTime);//加入时间选择
        paramMap.put("enddate",  EndTime);
        NetworkRequest.getInstance().POST(handler, paramMap, "MyTeamTotal", HttpCommon.MyTeamTotal);

    }


    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.CalendarSuccess) {
            list = (ArrayList<String>) message.obj;
            StartTime=list.get(0);
            EndTime=list.get(1);
            if (StartTime.equals("")&&!EndTime.equals("")){
             split_tv.setVisibility(View.GONE);
             StartTime_tv.setText("截止至");
             EndTime_tv.setText(EndTime);
            }
            if (!StartTime.equals("")&&EndTime.equals("")){
                split_tv.setVisibility(View.VISIBLE);
                StartTime_tv.setText(StartTime);
                EndTime_tv.setText(Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1));
                EndTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1);
            }
            if (!StartTime.equals("")&&!EndTime.equals("")){
                split_tv.setVisibility(View.VISIBLE);
                StartTime_tv.setText(StartTime);
                EndTime_tv.setText(EndTime);
            }
            if (StartTime.equals("")&&EndTime.equals("")){
                split_tv.setVisibility(View.GONE);
                StartTime_tv.setText("截止至");
                EndTime_tv.setText(Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1));
                EndTime=Utils.getDateBefore(Utils.getCurrentDate("yyyy-MM-dd"), 1);
            }
            showDialog();
            //执行获取总数据
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("startdate", StartTime);//加入时间选择
            paramMap.put("enddate",  EndTime);
            NetworkRequest.getInstance().POST(handler, paramMap, "MyTeamTotal", HttpCommon.MyTeamTotal);
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    private TeamUpdataInfo data;
    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ParentUserInfoSuccess) {
             data = (TeamUpdataInfo) message.obj;
            Utils.displayImageCircular(this, data.getUserpicurl(), teamImage);
        }
        if (message.what == LogicActions.MyTeamTodaySuccess){
            if (!TempDataSetActivity.isTeamCanEdit) {
                dataList = (ArrayList<MyTeamTodayInfo>) message.obj;
                setDataList();
            }else{
                if (TempDataSetActivity.TeamTodayDataList!=null){
                    dataList=TempDataSetActivity.TeamTodayDataList;
                    setDataList();
                }

            }
        }
        if (message.what == LogicActions.MyTeamTotalSuccess){
            JSONObject jsonObject= (JSONObject) message.obj;
            if (!TempDataSetActivity.isTeamCanEdit){
                try {
                    totalDataList= (ArrayList<MyTeamTotalInfo>) JSON.parseArray(jsonObject.getJSONArray("data").toString(), MyTeamTotalInfo.class);
                    total_number.setText(jsonObject.optString("total"));
                    adapter = new TotalDataAdapter(this,jsonObject.optString("type"));
                    adapter.setOnItemClickListener(this);
                    fragment_my_team_recycler.setAdapter(adapter);
                    adapter.setNewData(totalDataList);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                    if (TempDataSetActivity.TeamTotalDataList!=null)
                    totalDataList=TempDataSetActivity.TeamTotalDataList;
                    total_number.setText(TempDataSetActivity.teamToalNum);
                    adapter = new TotalDataAdapter(this,"1");
                    adapter.setOnItemClickListener(this);
                    fragment_my_team_recycler.setAdapter(adapter);
                    adapter.setNewData(totalDataList);
                    adapter.notifyDataSetChanged();
            }

            dismissDialog();
        }

    }

    //填充数据
    private void setDataList() {

        if (dataList != null && dataList.size() > 0) {
         int size=dataList.size();
         switch (size){
             case 0:
                 myteamtoday_layout.setVisibility(View.GONE);
                 break;
             case 1:
                 today_layout_02.setVisibility(View.GONE);
                 today_layout_03.setVisibility(View.GONE);
                 team_today_cnt_01.setText(dataList.get(0).getCnt());
                 team_today_title_01.setText(dataList.get(0).getTitle());
                 break;
             case 2:
                 today_layout_03.setVisibility(View.GONE);
                 team_today_cnt_01.setText(dataList.get(0).getCnt());
                 team_today_cnt_02.setText(dataList.get(1).getCnt());
                 team_today_title_01.setText(dataList.get(0).getTitle());
                 team_today_title_02.setText(dataList.get(1).getTitle());
                 break;
             case 3:
                 team_today_cnt_01.setText(dataList.get(0).getCnt());
                 team_today_cnt_02.setText(dataList.get(1).getCnt());
                 team_today_cnt_03.setText(dataList.get(2).getCnt());
                 team_today_title_01.setText(dataList.get(0).getTitle());
                 team_today_title_02.setText(dataList.get(1).getTitle());
                 team_today_title_03.setText(dataList.get(2).getTitle());
                 break;
         }
        }

    }

    @OnClick({R.id.back, R.id.team_calendar,R.id.tuijain_layout,R.id.team_time_layout,R.id.today_layout_01,R.id.today_layout_02,R.id.today_layout_03})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.tuijain_layout:
               if (data!=null){
                   showRecoomendDialog(data);
               }
                break;
        case R.id.team_time_layout:
        case R.id.team_calendar:
            startActivity(new Intent(this, CalendarNewActivity.class).putExtra("startTime", "").putExtra("endTime", "").putExtra("LimitStartTime", LimitStartTime).putExtra("LimitEndTime", LimitEndTime));
                break;
            case R.id.today_layout_01:
                if (dataList.size()>0)
                startActivity(new Intent(this, TeamTodayListActivity.class).putExtra("id", dataList.get(0).getUserids()).putExtra("title", dataList.get(0).getTitle()));
                break;
            case R.id.today_layout_02:
                if (dataList.size()>1)
                startActivity(new Intent(this, TeamTodayListActivity.class).putExtra("id", dataList.get(1).getUserids()).putExtra("title", dataList.get(1).getTitle()));
                break;
            case R.id.today_layout_03:
                if (dataList.size()>2)
                startActivity(new Intent(this, TeamTodayListActivity.class).putExtra("id", dataList.get(2).getUserids()).putExtra("title", dataList.get(2).getTitle()));
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(this, AgentNewActivity.class).putExtra("index", position).putExtra("title",totalDataList.get(position).getTitle()));
    }
    //推荐人弹框
    private void showRecoomendDialog(TeamUpdataInfo data){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_team_recommed,null);
        dialog.setContentView(view);
        TextView wxcode_txt=dialog.findViewById(R.id.team_wechat_text);
        TextView teamName=dialog.findViewById(R.id.team_name);
        TextPaint tp=teamName.getPaint();
        tp.setFakeBoldText(true);
        TextView teamRecommend=dialog.findViewById(R.id.team_recommend);
        ImageView teamImage=dialog.findViewById(R.id.team_image);
        Utils.displayImageCircular(this, data.getUserpicurl(), teamImage);
        teamName.setText(data.getUsername());
        teamRecommend.setText(data.getExtensionid());
        wxcode_txt.setText(data.getWxcode());
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.search_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.colorTra99);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

        dialog.findViewById(R.id.copy_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copyText(wxcode_txt.getText().toString());
                ToastUtil.showImageToast(TeamNewActivity.this,"复制成功",R.mipmap.toast_img);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dialog_re_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}
