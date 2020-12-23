package com.lxkj.dmhw.defined;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


/**
 * Created by Zhaoqingzhi  wx:qingzhi_zhao on 2017/10/17.
 * 此类主要是与js交互的
 */

public class WebPageNavigationJsObject {
    private Activity activity;
    private String key, value;

    public WebPageNavigationJsObject(Activity activity) {
        this.activity = activity;
    }


    //拿到设置webView的属性
    @JavascriptInterface
    public void setExtraInfoHead(String key, String value) {
        setKey(key);
        setValue(value);
        Log.e("添加头信息", key + "," + value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
