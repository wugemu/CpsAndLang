package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.TeamNewAdapter;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.TeamMenberDetailDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamTodayListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.mTitle)
    TextView mTitle;

    @BindView(R.id.fragment_team_recycler)
    RecyclerView fragmentTeamRecycler;
    // Fragment装载器
    private Fragment currentFragment = new Fragment();
    private ArrayList<MyTeamTotalDetailnfo> menberList;
    private String id = ""; //接口请求索引
    private String title="";
    private TeamNewAdapter adapter;
    private View emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_list_team);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        title=getIntent().getStringExtra("title");
        mTitle.setText(title);
        id=getIntent().getStringExtra("id");
        // 设置Recycler
        emptyView=getLayoutInflater().inflate(R.layout.view_no_agent_empty, null);
        ImageView image=emptyView.findViewById(R.id.an_img);
        TextView textView1=emptyView.findViewById(R.id.an_txt);
        Button btn=emptyView.findViewById(R.id.refresh_btn);
        image.setImageResource(R.mipmap.no_agent);
        textView1.setText(getString(R.string.agnet_empty_txt1));
        btn.setText(getString(R.string.agnet_empty_txt2));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeamTodayListActivity.this, PosterActivity.class));
            }
        });


        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        fragmentTeamRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        adapter = new TeamNewAdapter(this);
        fragmentTeamRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 取消第一次加载回调
        adapter.setOnItemClickListener(this);
        httpPost();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.MyTeamTodayDetailSuccess) {
            menberList = (ArrayList<MyTeamTotalDetailnfo>) message.obj;
            if (menberList.size() > 0) {
                    adapter.setNewData(menberList);
                    adapter.notifyDataSetChanged();
            }
        }
        adapter.setEmptyView(emptyView);
        dismissDialog();
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;

        }
    }

    //请求团队数据明细
    private void httpPost() {
        showDialog();
        paramMap = new HashMap<>();
        paramMap.put("userids", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "MyTeamTodayDetail", HttpCommon.MyTeamTodayDetail);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TeamMenberDetailDialog dialog = new TeamMenberDetailDialog(this,menberList.get(position));
        dialog.showDialog();
    }

}
