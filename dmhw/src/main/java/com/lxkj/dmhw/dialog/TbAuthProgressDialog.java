package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.ToastUtil;

/**
 * 淘宝渠道授权弹框
 */

public class TbAuthProgressDialog extends SimpleDialog<String> {

    private ImageView loadingImg;
    private TextView loadingtxt;
    private RelativeLayout webLayout;
    WebView tbAuthWeb;
    UserInfo login;
    Handler handler;
    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public TbAuthProgressDialog(Context context, String data) {
        super(context, R.layout.dialog_progress_tb, data, false, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        login = DateStorage.getInformation();
        loadingImg = helper.getView(R.id.gifimage);
        webLayout= helper.getView(R.id.webLayout);
        tbAuthWeb= helper.getView(R.id.tbAuthWeb);
        helper.setText(R.id.loadingtxt, "授权中...");
        try{
            handler=new Handler();
            handler.postDelayed(() -> {
             if (Variable.tbAuthsuccess==0){
             this.dismiss();
             ToastUtil.showImageToast(ActivityManagerDefine.getInstance().getActivity(),"授权超时，请重试",R.mipmap.toast_error);
             }
             },6000);
            loadingImg.setBackgroundResource(R.drawable.loading);
            AnimationDrawable animaition = (AnimationDrawable)loadingImg.getBackground();
            animaition.start();
            tbAuthWeb.getSettings().setJavaScriptEnabled(true);
            tbAuthWeb.getSettings().setDomStorageEnabled(true);
//            tbAuthWeb.setWebViewClient(webViewClient);
            tbAuthWeb.addJavascriptInterface(new JavascriptHandler((Activity) context, 9),"live");
            AlibcTrade.openByUrl((Activity) context, "", data, tbAuthWeb,
                        new WebViewClient(), new WebChromeClient(), null,
                        null, null, new AlibcTradeCallback() {
                            @Override
                            public void onTradeSuccess(AlibcTradeResult tradeResult) {
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                            }
                        });
            new Handler().postDelayed(() -> {
            tbAuthWeb.loadUrl("javascript:document.getElementById(\"J_Submit\").click();");
            },1000);
            }catch (Exception e){

            }
    }

//        private WebViewClient webViewClient = new WebViewClient() {
//        /**
//         * 页面加载结束时调用
//         * @param view
//         * @param url
//         */
//        @Override
//        public void onPageFinished(WebView view, String url) {
//             Log.e("ttttttttttttt",url);
//             tbAuthWeb.loadUrl("javascript:document.getElementById(\"J_Submit\").click();");
//        }
//    };
    @Override
    public void onClick(View v) {

    }


}
