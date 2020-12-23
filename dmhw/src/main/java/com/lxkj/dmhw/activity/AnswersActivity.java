package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;

import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xrichtext.XRichText;

public class AnswersActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.answers_q)
    TextView answersQ;
    @BindView(R.id.answers_a)
    XRichText answersA;
    private String problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        problem = getIntent().getExtras().getString("problem");
        // Q&A
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("problemtype", getIntent().getExtras().getString("classify"));
        paramMap.put("problemid", getIntent().getExtras().getString("id"));
        NetworkRequest.getInstance().POST(handler, paramMap, "GetAnswers", HttpCommon.GetAnswers);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetAnswersSuccess) {
            answersQ.setText(problem);
            answersA.callback(new XRichText.BaseClickCallback() {
                @Override
                public boolean onLinkClick(String url) {
                    // 当点击超链接时触发
                    return true;
                }

                @Override
                public void onImageClick(List<String> urlList, int position) {
                    super.onImageClick(urlList, position);
                    // 当点击图片时触发
                }

                @Override
                public void onFix(XRichText.ImageHolder holder) {
                    super.onFix(holder);
                    // 当图片加载前回调此方法
                }
            }).text(message.obj + "");
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

}
