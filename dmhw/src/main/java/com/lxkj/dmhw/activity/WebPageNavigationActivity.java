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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.WebPageNavigationJsObject;
import com.lxkj.dmhw.dialog.JurisdictionDialog1;
import com.lxkj.dmhw.view.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加油优惠的单独webview
 */

public class WebPageNavigationActivity extends BaseActivity {
    private X5WebView x5webView;//这里的webview集成腾讯TBS服务，可到官网查看api
    private ProgressBar progressBar;//进度条进度
    private String aMapUri="";

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.mTitle)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
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
        mTitle.setText(getIntent().getStringExtra("title"));
        GpsLocation();
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
        x5webView.loadUrl(getIntent().getStringExtra("url"));
        final WebPageNavigationJsObject webPageNavigationJsObject = new WebPageNavigationJsObject(this);
        x5webView.addJavascriptInterface(webPageNavigationJsObject, "czb");//第二个参数czb不可更改，
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
                Log.e("标题",s);
            }
        });
//        androidamap://route?sourceApplication



        x5webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("weixin://") || url.contains("alipays://platformapi")) {//如果微信或者支付宝，跳转到相应的app界面,
                    x5webView.goBack();
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPageNavigationActivity.this, "未安装相应的客户端", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                if (url.startsWith("http://m.amap.com")) {// http://m.amap.com
                    webView.loadUrl(url);
                    return true;
                }
                if (url.startsWith("androidamap://route")) {//打开高德app
                    x5webView.goBack();
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebPageNavigationActivity.this, "未安装高德地图", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

                if (url.startsWith("http://ditu.amap.com")||url.startsWith("https://ditu.amap.com")){
                    return true;
                }

                /**
                 *
                 * 设置 Header 头方法
                 * window.czb.extraHeaders(String key, String value)
                 */
                if (webPageNavigationJsObject != null && webPageNavigationJsObject.getKey() != null) {
                    Map extraHeaders = new HashMap();
                    extraHeaders.put(webPageNavigationJsObject.getKey(), webPageNavigationJsObject.getValue());
                    webView.loadUrl(url, extraHeaders);
                } else {
                    webView.loadUrl(url);
                }
                return true;

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
            if (x5webView.getUrl().startsWith("http://m.amap.com")||x5webView.getUrl().startsWith("http://ditu.amap.com/")||
                    x5webView.getUrl().startsWith("https://m.amap.com")||x5webView.getUrl().startsWith("https://ditu.amap.com/")) {
                x5webView.goBack();
            }
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
    private static final int LOCATION_CODE = 1;
    private static final int LOCATION_CODE2 = 2;
    private LocationManager lm;//【位置管理】

    public void GpsLocation(){
       if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                   != PackageManager.PERMISSION_GRANTED) {
               // 申请授权。
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE2);
           } else {
               //已经有权限了
               setWebViewListener();
           }
       }else {
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                   != PackageManager.PERMISSION_GRANTED) {
               // 申请授权。
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
           } else {
               //已经有权限了
               setWebViewListener();
           }
       }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    setWebViewListener();
                } else {
                    // 权限被用户拒绝了。
                    JurisdictionDialog1 dialog = new JurisdictionDialog1(WebPageNavigationActivity.this, "定位权限");
                    dialog.setShareClickListener(new JurisdictionDialog1.OnSettingClickListener() {
                        @Override
                        public void onSettingClick() {
                            Intent intent =  new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivityForResult(intent,15568);
                        }
                    });
                    dialog.showDialog();
//                    Toast.makeText(this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_LONG).show();
                }

            }
            break;
            case LOCATION_CODE2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    setWebViewListener();
                } else {
                    // 权限被用户拒绝了。
                    JurisdictionDialog1 dialog = new JurisdictionDialog1(WebPageNavigationActivity.this, "定位权限");
                    dialog.setShareClickListener(new JurisdictionDialog1.OnSettingClickListener() {
                        @Override
                        public void onSettingClick() {
                            Intent intent =  new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivityForResult(intent,15568);
                        }
                    });
                    dialog.showDialog();
//                    Toast.makeText(this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_LONG).show();
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==15568){
         GpsLocation();
        }
    }
}
