package com.example.test.andlang.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.widget.progressweb.ProgressView;

/**
 * Created by 1 on 2017/2/13.
 */

public class MyWebChromeClient extends WebChromeClient {
    private ProgressView progressView;
    private WebCall webCall;

    public void initProgress(Context context,WebView webView){
        //初始化进度条
        progressView = new ProgressView(context);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BaseLangUtil.dp2px(context, 4)));
        progressView.setColor(Color.BLUE);
        progressView.setProgress(10);
        //把进度条加到Webview中
        webView.addView(progressView);
    }
    public void setWebCall(WebCall webCall) {
        this.webCall = webCall;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(progressView!=null) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(newProgress);
            }
        }
        super.onProgressChanged(view, newProgress);
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (webCall != null)
            webCall.fileChose(uploadMsg);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        if (webCall != null)
            webCall.fileChose5(filePathCallback);
        return true;
    }

    public interface WebCall {
        void fileChose(ValueCallback<Uri> uploadMsg);

        void fileChose5(ValueCallback<Uri[]> uploadMsg);
    }

}
