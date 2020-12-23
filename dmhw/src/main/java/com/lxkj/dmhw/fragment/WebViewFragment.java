package com.lxkj.dmhw.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;

import com.bumptech.glide.util.Util;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.defined.ProgressView;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.center_progress)
    ProgressBar center_progress;
    @BindView(R.id.web)
    WebView web;
    private String url;
    public static Boolean isTouchPager=false;
    class PagerDesc {
        private int top;
        private int bottom;
        private int top01;
        private int bottom01;
        private boolean isleft;
        private boolean isleft01;

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public int getTop01() {
            return top01;
        }

        public void setTop01(int top01) {
            this.top01 = top01;
        }

        public int getBottom01() {
            return bottom01;
        }

        public void setBottom01(int bottom01) {
            this.bottom01 = bottom01;
        }


        public PagerDesc(int top, int bottom, int top01, int bottom01, boolean isleft, boolean isleft01) {
            this.top = top;
            this.bottom = bottom;
            this.top01 = top01;
            this.bottom01 = bottom01;
            this.isleft=isleft;
            this.isleft01=isleft01;
        }
    }
    public static WebViewFragment getInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, null);
        ButterKnife.bind(this, view);
        web.addJavascriptInterface(new JsHObject(),"JsHObject");
        web.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取y轴坐标
                float y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (null != mPagerDesc) {
                            //将css像素转换为android设备像素并考虑通知栏高度
                            DisplayMetrics metric = getActivity().getResources().getDisplayMetrics();
                            float density = metric.density;// 屏幕密度（0.75 / 1.0 / 1.5）
                            int top = (int) ((mPagerDesc.getTop()*density)+Utils.dipToPixel(R.dimen.dp_90));
                            int bottom =(int) (mPagerDesc.getBottom()*density)+Utils.dipToPixel(R.dimen.dp_90);
                            int top01 = (int) (mPagerDesc.getTop01()*density)+Utils.dipToPixel(R.dimen.dp_90);
                            int bottom01 =(int) (mPagerDesc.getBottom01()*density)+Utils.dipToPixel(R.dimen.dp_90);
                            //如果触摸点的坐标在轮播区域内，则由webview来处理事件，否则由viewpager来处理
                            if ((y>top&& y<bottom)||(y > top01&& y<bottom01)){
                                web.requestDisallowInterceptTouchEvent(true);
                            }else{
                                web.requestDisallowInterceptTouchEvent(false);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });

        return view;
    }
    private  PagerDesc mPagerDesc;
    public class JsHObject {
        @JavascriptInterface
        public void getMessage(String top,String bottom ,String top01, String bottom01,boolean isSlideLeft,boolean isSlideLeft01){
            int bottomValue = (int) Double.parseDouble(bottom);
            int topValue = (int) Double.parseDouble(top);
            int bottomValue01 = (int) Double.parseDouble(bottom01);
            int topValue01 = (int) Double.parseDouble(top01);
            mPagerDesc = new PagerDesc(topValue,bottomValue,topValue01,bottomValue01,isSlideLeft,isSlideLeft01);
        }
}
    @Override
    public void onEvent() {
        doInit();
    }

    @Override
    public void onCustomized() {

    }

    /**
     * 刷新页面
     */
    public void refresh() {
//        web.reload();
    }

    /**
     * 设置WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void doInit() {
        WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        web.clearCache(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
        web.addJavascriptInterface(new JavascriptHandler(getActivity(),0), "live");
        web.post(() -> {
            if (url.toLowerCase().startsWith("dmj://")) {
                web.loadUrl(url.replace("dmj://", "http://"));
            } else {
                web.loadUrl(url);
            }
        });
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
//            if (webProgress!=null)
//            webProgress.setProgress(newProgress);
        }

        /**
         * 获取网页title标题
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
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
            return true;
        }

        /**
         * 载入页面调用
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (center_progress!=null)
            center_progress.setVisibility(View.VISIBLE);
        }

        /**
         * 页面加载结束时调用
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            if (center_progress!=null)
            center_progress.setVisibility(View.GONE);
        }
    };

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
