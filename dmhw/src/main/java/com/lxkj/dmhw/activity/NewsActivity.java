package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.NewsItemAdapter;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.News;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.news_list)
    RecyclerView news_list;
    private NewsItemAdapter adapter;
    private ArrayList<News> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        adapter=new NewsItemAdapter(this);
        news_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        news_list.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = null;
                switch (list.get(position).getType()) {
                    case "0":
                        intent = new Intent(NewsActivity.this, ChoiceActivity.class);
                        break;
                    case "1":
                        intent = new Intent(NewsActivity.this, AssetsActivity.class);
                        break;
                    case "2":
                        intent = new Intent(NewsActivity.this, SystemActivity.class);
                        break;
                    case "3":
                        intent = new Intent(NewsActivity.this, PopularActivity.class);
                        break;
                    case "4":
                        intent = new Intent(NewsActivity.this, NewsExamActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

        // 获取最新消息
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetNews", HttpCommon.GetNews);
        showDialog();
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
        if (message.what == LogicActions.GetNewsSuccess) {
            list = (ArrayList<News>) message.obj;
            adapter.setNewData(list);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
        }
    }
}
