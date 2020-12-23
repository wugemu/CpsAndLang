package com.lxkj.dmhw.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  跳我们自己H5链接（内链）
 */
public class WebViewOtherActivity extends BaseActivity implements View.OnClickListener{

    LinearLayout back;
    LinearLayout web_title;
     WebView webView;
    ProgressBar webProgress;
    TextView mTitle;
    View bar;
    TextView close;
    private String url;
    private int hasTop;
    private JSONObject tempJson=new JSONObject();
    private int type;

    //信用卡参数设置返佣比例等
    private String bonusRate="12";
    private String shareRate="10";
    private String showType="A";
    private String isShare="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_web_view);
        web_title=findViewById(R.id.web_title);
        webProgress=findViewById(R.id.center_progress);
        bar=findViewById(R.id.bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }else{
            bar.setVisibility(View.VISIBLE);
        }
        hasTop=getIntent().getIntExtra("hideTop",0);
        if (hasTop==1){
            webProgress.setVisibility(View.GONE);
           web_title.setVisibility(View.GONE);
        }else{
            web_title.setVisibility(View.VISIBLE);
        }
        initView();
    }

    private void initView() {
        webView=findViewById(R.id.web);
        back=findViewById(R.id.back);
        mTitle=findViewById(R.id.mTitle);
        close=findViewById(R.id.close);
        back.setOnClickListener(this);
        close.setOnClickListener(this);
        url = getIntent().getExtras().getString(Variable.webUrl);
        if(BuildConfig.DEBUG){
            Log.d("0.0","加载H5页面:"+url);
        }
//声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.addJavascriptInterface(new JavascriptHandler(this,3), "live");
        webView.setWebChromeClient(webChromeClient);

        webView.post(() -> {
            if (url.toLowerCase().startsWith("dmj://")) {
                webView.loadUrl(url.replace("dmj://", "http://"));
            }  else {
                webView.loadUrl(getIntent().getExtras().getString(Variable.webUrl));
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                try {
                    if (url.toLowerCase().startsWith("alipays://")) {
                        if (!getIntent().getBooleanExtra("isCheck", false)) {
                            if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            } else {
                                view.loadUrl(url.replace("alipays://", "http://"));
                            }
                        }
                    }
                    if (url.toLowerCase().startsWith("taobao://")) {
                        Utils.Alibc(WebViewOtherActivity.this, url.replace("taobao://", "https://"), null, null);
                    } else if (url.toLowerCase().startsWith("tmall://")) {
                        if (Utils.checkApkExist("com.tmall.wireless")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }
                    } else if (url.toLowerCase().startsWith("dmj://")) {
                        view.loadUrl(url.replace("dmj://", "http://"));
                    } else {
                        if (!url.toLowerCase().startsWith("alipays://")) {
                            if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
                                /**
                                 * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
                                 */
                                final PayTask task = new PayTask(WebViewOtherActivity.this);
                                boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                                    @Override
                                    public void onPayResult(final H5PayResultModel result) {
                                        WebViewOtherActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.loadUrl("javascript:h5payresult(" + JSON.toJSONString(result) + ")");
                                            }
                                        });
                                    }
                                });
                                if (!isIntercepted) {
                                    view.loadUrl(url);
                                }
                            }
                        }
                    }
                }catch (Exception e){

                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webProgress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewOtherActivity.this,description,Toast.LENGTH_LONG);
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

    };

    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressedSupport() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.TaoBaoAuthResultToH5Success) {
            try{
                webView.loadUrl("javascript:isTbAuthSuccess(" + message.obj.toString()+ ")");
            }catch (Exception e){

            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.CreditCardTokenSuccess) {
            JSONObject jsonObject=(JSONObject) message.obj;
            try {
                Variable.encrypt=jsonObject.getString("token");
                bonusRate=jsonObject.getString("bonusRate");
                shareRate=jsonObject.getString("shareRate");
                showType=jsonObject.getString("showType");
                isShare=jsonObject.getString("isShare");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loginCreditCard();
        }
    }

    public void isClose() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    isFinish();
                }
            }
        });

    }
    /**
     * 关闭页面
     */
    @Override
    public void isFinish() {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.close:
                isFinish();
                break;
        }
    }

    private String imgurl;

    public  void  downLoad(String url){
        imgurl=url;
        Glide.with(this).asBitmap()
                .load(imgurl).into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                ToastUtil.showImageToast(WebViewOtherActivity.this,"图片已保存至相册",R.mipmap.toast_img);
            }
        });
    }

    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(WebViewOtherActivity.this, title,
                    message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }

        progressDialog.show();

    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    //获取信用卡token
    public void httpgetCreditToken(){
        paramMap.clear();
        type=0;//办卡进度
        paramMap.put("userid",login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "CreditCardToken", HttpCommon.getCreditToken);
    }
    //获取信用卡token
    public void httpgetCreditToken(JSONObject jsonObject){
        tempJson=jsonObject;
        type=1;//办卡进度
        paramMap.clear();
        paramMap.put("userid",login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "CreditCardToken", HttpCommon.getCreditToken);
    }
    //卡多分SDK接入creditcard
    //获取encrypt成功之后执行登录
    private void loginCreditCard(){
        if (!Variable.encrypt.equals("")) {
            KDFInterface.getInstance().login(this, Variable.encrypt, bonusRate, showType, isShare, shareRate, new OnComplete() {
                @Override
                public void onSuccess(JSONObject o) {
                    if (type==0){
                        KDFInterface.getInstance().toCardStoreActivity(WebViewOtherActivity.this, null);
                    }else {
                        try{
                            //查询办卡进度
                            KDFInterface.getInstance().toOrderProgressActivity(WebViewOtherActivity.this, tempJson.getString("bankCode"), tempJson.getString("id"), tempJson.getString("custName"), tempJson.has("crawId") ? tempJson.getString("crawId") : "", null);
                        }catch (Exception e){
                        }
                    }
                }
                @Override
                public void onError(String error) {
                    toast(error);
                }
            });
        }else{
            toast("网络异常请稍后重试！");
        }
    }

}
