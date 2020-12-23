package com.lxkj.dmhw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Zhaoqingzhi wx:qingzhi_zhao on 2017/10/17.
 */

public class X5WebView extends WebView{
    Context context;
    public X5WebView(Context context) {
        super(context);
        this.context=context;
        initWebView(this);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context=context;
        initWebView(this);
    }





    private   void initWebView(WebView webView){
//        if (webView.getX5WebViewExtension() != null) {
//            Toast.makeText(context, "已安装x5 core", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "没有安装x5 core", Toast.LENGTH_SHORT).show();
//        }
        final WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);//启用地理定位设置
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setDefaultTextEncodingName("utf-8");
//        webSetting.setUserAgent("czbAndroid");
        webSetting.setUserAgent("dmjAndroid");
        webView.clearCache(true);

    }
}
