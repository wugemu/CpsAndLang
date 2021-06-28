package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.ProblemAdapter;
import com.lxkj.dmhw.bean.Help;
import com.lxkj.dmhw.defined.LinearItemDecoration;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProblemActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.problem_recycler)
    RecyclerView problemRecycler;

    private ProblemAdapter adapter;
    private int number;
    private String classify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        title.setText(getIntent().getExtras().getString("title"));
        number = getIntent().getExtras().getInt("number");
        // 设置Recycler
        problemRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        problemRecycler.addItemDecoration(new LinearItemDecoration(0, 5, 0, 0));
        adapter = new ProblemAdapter();
        problemRecycler.setAdapter(adapter);
        // 监听item点击事件
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
        if (message.what == LogicActions.GetClassifyHelpSuccess) {
            adapter.setNewData((List<Help>) message.obj);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    private void httpPost() {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        switch (number) {
            case 1:
                classify = "00";
                paramMap.put("problemtype", "00");
                break;
            case 2:
                classify = "01";
                paramMap.put("problemtype", "01");
                break;
            case 3:
                classify = "02";
                paramMap.put("problemtype", "02");
                break;
            case 4:
                classify = "03";
                paramMap.put("problemtype", "03");
                break;
            case 5:
                classify = "04";
                paramMap.put("problemtype", "04");
                break;
        }
        NetworkRequest.getInstance().POST(handler, paramMap, "GetClassifyHelp", HttpCommon.GetClassifyHelp);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Help help = (Help) adapter.getData().get(position);
        startActivity(new Intent(this, AnswersActivity.class).putExtra("id", help.getProblemid()).putExtra("problem", help.getProblemname()).putExtra("classify", classify));
    }
}
