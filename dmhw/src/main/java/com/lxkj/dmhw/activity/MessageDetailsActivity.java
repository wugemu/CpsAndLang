package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xrichtext.XRichText;

public class MessageDetailsActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.message_details_title)
    TextView messageDetailsTitle;
//    @BindView(R.id.message_details_content)
//    XRichText messageDetailsContent;\
    @BindView(R.id.message_details_content)
     TextView messageDetailsContent;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.web)
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
//        if (getIntent().getStringExtra("title") == null) {
//            messageDetailsTitle.setVisibility(View.GONE);
//        } else {
//            if (TextUtils.isEmpty(getIntent().getExtras().getString("title"))) {
//                messageDetailsTitle.setVisibility(View.GONE);
//            } else {
//                messageDetailsTitle.setText(getIntent().getExtras().getString("title"));
//            }
//        }
        if (getIntent().getStringExtra("mTitle") != null) {
            mTitle.setText(getIntent().getExtras().getString("mTitle"));
        }

        if (!TextUtils.isEmpty(getIntent().getExtras().getString("content"))){
//          messageDetailsContent.setText(Html.fromHtml(getIntent().getExtras().getString("content")));
            doInit(getIntent().getExtras().getString("content"));
        }

//        messageDetailsContent.callback(new XRichText.BaseClickCallback() {
//            @Override
//            public boolean onLinkClick(String url) {
//                // 当点击超链接时触发
//                return true;
//            }
//
//            @Override
//            public void onImageClick(List<String> urlList, int position) {
//                super.onImageClick(urlList, position);
//                // 当点击图片时触发
//            }
//
//            @Override
//            public void onFix(XRichText.ImageHolder holder) {
//                super.onFix(holder);
//                // 当图片加载前回调此方法
//            }
//        }).text(getIntent().getExtras().getString("content"));
    }
    /**
     * 设置WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void doInit(String content) {
        WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        web.clearCache(true);
        web.getSettings().setCacheMode(android.webkit.WebSettings.LOAD_DEFAULT);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);//解决视频白屏
        web.setOnCreateContextMenuListener(this);
        web.loadData(content, "text/html;charset=utf-8","utf-8");
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }
}
