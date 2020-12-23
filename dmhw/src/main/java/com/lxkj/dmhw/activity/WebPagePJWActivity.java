package com.lxkj.dmhw.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.WebPageNavigationJsObject;
import com.lxkj.dmhw.dialog.JurisdictionDialog1;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.PJWWebView;
import com.lxkj.dmhw.view.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 跳转拼京唯苏宁的H5
 */

public class WebPagePJWActivity extends BaseActivity {
    private PJWWebView x5webView;//这里的webview集成腾讯TBS服务，可到官网查看api
    private ProgressBar progressBar;//进度条进度

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.mTitle)
    TextView mTitle;
    String referer = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_pjw);
        ButterKnife.bind(this);
        x5webView = findViewById(R.id.x5Webview);
        progressBar = findViewById(R.id.progress);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        setWebViewListener();
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


    private void setWebViewListener() {
        //加载的url按规定的使用即可
        if (getIntent().getStringExtra("type")!=null)
        {
            if (getIntent().getStringExtra("type").equals("wph")){
                referer="https://mcheckout.vip.com";
            }else if(getIntent().getStringExtra("type").equals("pdd")){
                referer="https://mobile.yangkeduo.com";
            }
        }
        x5webView.loadUrl(getIntent().getStringExtra("url"));
        x5webView.setWebChromeClient(new WebChromeClient() {
            @Override//进度条
            public void onProgressChanged(WebView webView, int progress) {
                super.onProgressChanged(webView, progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(progress);//设置进度值
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                mTitle.setText(s);
            }
        });



        x5webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("weixin://dl/business")){
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPagePJWActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                    }
                  return true;
                }
                if (url.startsWith("wtloginmqq://")){
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPagePJWActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                if (url.startsWith("pinduoduo://")) {
                    return true;
                }
                //微信支付
                if (url.startsWith("weixin://")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPagePJWActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                if (url.startsWith("https://wx.tenpay.com")) {
                    try {
                        Map<String,String> extras=new HashMap<>();
                        extras.put("Referer",referer);
                        x5webView.loadUrl(url,extras);
                    } catch (Exception e) {
                        Toast.makeText(WebPagePJWActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                /**支付宝支付
                 * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
                 */
                if (url.toLowerCase().startsWith("alipays://")) {
                        if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }
                    return true;
                }

                //跳转下载页（跳外部浏览器）
                if (url.toLowerCase().contains(".apk")){
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }

                //拦截打开非网页链接
                if (!url.startsWith("http://")&&!url.startsWith("https://")){
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPagePJWActivity.this, "未安装该应用", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回上一级
    public void goBack() {
        if (x5webView.canGoBack()) {
            x5webView.goBack();
        } else {
           isFinish();
        }
    }

    @OnClick({R.id.back,R.id.close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                goBack();
                break;
            case R.id.close:
                isFinish();
                break;
        }
    }
}
