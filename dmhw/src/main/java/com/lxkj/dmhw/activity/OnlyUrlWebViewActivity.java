package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lxkj.dmhw.activity.WebViewActivity.COVER_SCREEN_PARAMS;

//纯粹打开一个URL 外链
public class OnlyUrlWebViewActivity extends BaseActivity implements AlibcTradeCallback {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.reload_layout)
    LinearLayout reload_layout;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.web_title)
    LinearLayout webTitle;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.network_mask)
    LinearLayout main_network_mask;
    private String url;
    private  IX5WebChromeClient.CustomViewCallback xCustomViewCallback;
    private View xCustomView;
    private FrameLayout fullscreen_view;
    String referer = "http://api.bjzc.ejiaofei.cn/";
    private int isneedReferer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_web_view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        url = getIntent().getExtras().getString(Variable.webUrl);
        main_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //重新加载
            web.reload();
            }
        });
        doInit();
    }

    /**
     * 设置WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void doInit() {
        com.tencent.smtt.sdk.WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON);
        settings.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        web.clearCache(true);
        web.setInitialScale(100);
        web.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
        //播放视频造成首页崩溃原因是 MianActivity 要设置  android:configChanges="orientation|keyboardHidden|keyboard|screenSize" 切记切记切
        //记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记切记
        if (web.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen",1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            web.getX5WebViewExtension().invokeMiscMethod("setVideoParams",data);
        }
        web.post(() -> {
                web.loadUrl(url);
        });
    }

    @Override
    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
        // 用户操作中成功信息回调
    }

    @Override
    public void onFailure(int i, String s) {
        // 用户操作中错误信息回调
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        /**
         * 通知应用程序当前网页加载的进度
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            view.requestFocus();
        }

        /**
         * 获取网页title标题
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitle.setText(title);
        }

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(OnlyUrlWebViewActivity.this);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            frameLayout.setBackgroundColor(Color.BLACK);
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
//            web.setVisibility(View.GONE);
            //12：缓存 13：小窗 14：分享 23：横竖屏
            //size等于26是先点播放，再点全屏
            //size等于29是先点全屏，再点播放
//            List<View> list = getAllChildViews(view);
//            if (list.size() == 26) {
//                list.get(12).setVisibility(View.INVISIBLE);
//                list.get(13).setVisibility(View.INVISIBLE);
//                list.get(14).setVisibility(View.INVISIBLE);
//                list.get(23).setVisibility(View.INVISIBLE);
//            } else if (list.size() == 29) {
//                list.get(15).setVisibility(View.INVISIBLE);
//                list.get(16).setVisibility(View.INVISIBLE);
//                list.get(17).setVisibility(View.INVISIBLE);
//                list.get(26).setVisibility(View.INVISIBLE);
//            }
            //如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            OnlyUrlWebViewActivity.this.getWindow().getDecorView();
            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            if (fullscreen_view!=null){
             fullscreen_view.removeAllViews();
             fullscreen_view=null;
            }
            fullscreen_view = new FullscreenHolder(OnlyUrlWebViewActivity.this);
            fullscreen_view.addView(view, COVER_SCREEN_PARAMS);
            decor.addView(fullscreen_view, COVER_SCREEN_PARAMS);
            xCustomView = view;
            setStatusBarVisibility(false);
            xCustomViewCallback = callback;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @Override
        //视频播放退出全屏会被调用的
        public void onHideCustomView() {
            if (xCustomView == null)//不是全屏播放状态
                return;
            // Hide the custom view.
            setStatusBarVisibility(true);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            decor.removeView(fullscreen_view);
            fullscreen_view = null;
            xCustomView = null;
            xCustomViewCallback.onCustomViewHidden();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {

        /**
         * 回调该方法，处理未被WebView处理的事件
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
            //微信支付
            if (url.startsWith("weixin://")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(OnlyUrlWebViewActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                }
                return true;
            }
            if (url.startsWith("https://wx.tenpay.com")) {
                try {
                        Map<String,String> extras=new HashMap<>();
                        extras.put("Referer",view.getUrl());
                        view.loadUrl(url,extras);
                } catch (Exception e) {
                    Toast.makeText(OnlyUrlWebViewActivity.this, "请安装微信App", Toast.LENGTH_LONG).show();
                }
                return true;
            }
                /*
                 *支付宝支付
                 */
                if (url.toLowerCase().startsWith("alipays://")) {
                    if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }else{
                        Toast.makeText(OnlyUrlWebViewActivity.this, "请安装支付宝App", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(OnlyUrlWebViewActivity.this, "未安装该应用", Toast.LENGTH_LONG).show();
                }
                return true;
            }
            }catch (Exception e){
                return true;
            }
            return false;
        }
        /**
         * 载入页面调用
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        /**
         * 页面加载结束时调用
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
        }
    };

    @OnClick({R.id.back, R.id.close, R.id.reload_layout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isCanClose();
                break;
            case R.id.reload_layout:
                if (web!=null)
                web.reload();
                break;
            case R.id.close:
                isFinish();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }



    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }
    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        webChromeClient.onHideCustomView();
    }

    public void isCanClose() {
        if (inCustomView()) {
            hideCustomView();
            return;
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (web.canGoBack()) {
                    web.goBack();
                } else {
                    isFinish();
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        isCanClose();
    }
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.TaoBaoAuthResultToH5Success) {
            try{
                web.loadUrl("javascript:isTbAuthSuccess(" + message.obj.toString()+ ")");
            }catch (Exception e){

            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
    }

}
