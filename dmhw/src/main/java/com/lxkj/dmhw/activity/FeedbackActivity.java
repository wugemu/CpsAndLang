package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.RewardAdapter;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.MultiImage.MultiImageSelector;
import com.lxkj.dmhw.defined.MultiImage.MultiImageSelectorActivity;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements RewardAdapter.OnDeleteClickListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.feedback_content)
    EditText feedbackContent;
    @BindView(R.id.feedback_position)
    TextView feedbackPosition;
    @BindView(R.id.feedback_image)
    LinearLayout feedbackImage;
    @BindView(R.id.feedback_grid)
    ObserveGridView feedbackGrid;
    @BindView(R.id.feedback_btn)
    LinearLayout feedbackBtn;
    private int REQUEST_IMAGE = 3072;
    private int position = 0;
    private ArrayList<String> list = new ArrayList<>();
    private RewardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        adapter = new RewardAdapter(this);
        feedbackGrid.setAdapter(adapter);
        adapter.setOnDeleteClickListener(this);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.FeedbackSuccess) {
            toast(message.obj + "");
            isFinish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                position = path.size();
                feedbackPosition.setText(position + "");
                for (int i = 0; i < position; i++) {
                    list.add(path.get(i));
                }
                adapter.setArrayList(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.back, R.id.feedback_btn, R.id.feedback_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.feedback_btn:
                if (feedbackContent.getText().toString().equals("")) {
                    toast("请填写反馈内容");
                } else {
                    paramMap = new HashMap<>();
                    paramMap.put("userid", login.getUserid());
                    paramMap.put("title", "");
                    paramMap.put("repcontent", feedbackContent.getText().toString());
                    JSONArray array = new JSONArray();
                    JSONObject object = new JSONObject();
                    if (list.size() > 0) {
                        try {
                            for (int i = 0; i < list.size(); i++) {
                                object.put("imgurl", Utils.getImageStr(list.get(i)));
                                array.put(object);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        paramMap.put("imglist", array.toString());
                    } else {
                        paramMap.put("imglist", "");
                    }
                    NetworkRequest.getInstance().POST(handler, paramMap, "Feedback", HttpCommon.Feedback);
                }
                break;
            case R.id.feedback_image:
                if (!Utils.checkPermision(this,1003,false)) {
                    return;
                }
                if (position >= 3) {
                    toast("已到达上限");
                } else {
                    MultiImageSelector.create().showCamera(false).count(3 - position).multi().start(this, REQUEST_IMAGE);
                }
                break;
        }
    }

    @Override
    public void onDeleteClick(int position) {
        list.remove(position);
        this.position = list.size();
        feedbackPosition.setText(this.position + "");
        adapter.notifyDataSetChanged();
    }

}
