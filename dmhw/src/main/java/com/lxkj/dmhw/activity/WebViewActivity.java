package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.bean.SearchAll;
import com.lxkj.dmhw.bean.ShareList;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//号称比较全能的腾讯开源浏览器 （其实问题也一堆）
public class WebViewActivity extends BaseActivity implements AlibcTradeCallback {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.center_progress)
    ProgressBar webProgress;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.web_title)
    LinearLayout webTitle;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.web_tips)
    LinearLayout webTips;
    @BindView(R.id.web_discount_btn)
    LinearLayout webDiscountBtn;
    @BindView(R.id.web_discount_layout)
    LinearLayout webDiscountLayout;
    @BindView(R.id.web_discount_btn_text)
    TextView webDiscountBtnText;
    @BindView(R.id.web_share_btn_text)
    TextView webShareBtnText;
    @BindView(R.id.web_share_btn)
    LinearLayout webShareBtn;
    @BindView(R.id.web_buy_btn_text)
    TextView webBuyBtnText;
    @BindView(R.id.web_buy_btn)
    LinearLayout webBuyBtn;
    @BindView(R.id.web_buy_layout)
    LinearLayout webBuyLayout;
    @BindView(R.id.web)
    com.tencent.smtt.sdk.WebView web;
    private String url;
    private String imgurl;
    private SearchAll searchAll;
    // 支付宝跳转
    AlipayDialog dialog;
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private  IX5WebChromeClient.CustomViewCallback xCustomViewCallback;
    private View xCustomView;
    private FrameLayout fullscreen_view;

    private ArrayList<String> urlList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        url = getIntent().getExtras().getString(Variable.webUrl);
        dialog = new AlipayDialog(this);
        Variable.AuthShowStatus=true;
        if (getIntent().getBooleanExtra("isTitle", false)) {
            webTitle.setVisibility(View.GONE);
        } else {
            webTitle.setVisibility(View.VISIBLE);
        }
        doInit();
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
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true); // 允许访问文件
        web.clearCache(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
        web.addJavascriptInterface(new JavascriptHandler(this,0), "live");
//        removeCookie();
//        synCookie(url);
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
            if (url.toLowerCase().startsWith("dmj://")) {
                web.loadUrl(url.replace("dmj://", "http://"));
            } else {
                web.loadUrl(url);
            }
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        MenuItem.OnMenuItemClickListener listener = item -> {
            boolean isCheck;
            switch (item.getTitle().toString()) {
                case "保存图片":
                    isCheck = true;
                    Glide.with(this).asBitmap()
                            .load(imgurl).into(new SimpleTarget<Bitmap>()
                    {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                            ToastUtil.showImageToast(WebViewActivity.this,"图片已保存至相册",R.mipmap.toast_img);
                        }
                    });
                    break;
                default:
                    isCheck = false;
                    break;
            }
            return isCheck;
        };
        if (view instanceof WebView) {
            WebView.HitTestResult result = ((WebView) view).getHitTestResult();
            if (result != null) {
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgurl = result.getExtra();
                    menu.add(0, view.getId(), 0, "保存图片").setOnMenuItemClickListener(listener);
                }
            }
        }
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
            FrameLayout frameLayout = new FrameLayout(WebViewActivity.this);
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
            WebViewActivity.this.getWindow().getDecorView();
            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            fullscreen_view = new FullscreenHolder(WebViewActivity.this);
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
            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            decor.removeView(fullscreen_view);
            fullscreen_view = null;
            xCustomView = null;
            xCustomViewCallback.onCustomViewHidden();
//            web.setVisibility(View.VISIBLE);
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
                if (!url.toLowerCase().startsWith("tbopen://")) {
                    if (getIntent().getBooleanExtra("isTitle", false)) {
                        if (url.toLowerCase().startsWith("alipays://")) {
                            if (!getIntent().getBooleanExtra("isCheck", false)) {
                                if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                } else {
                                    view.loadUrl(url.replace("alipays://", "http://"));
                                }
                            }
                        }
                    }
                    if (url.toLowerCase().startsWith("taobao://")) {
                        Utils.Alibc(WebViewActivity.this, url.replace("taobao://", "https://"), null, null);
                    } else if (url.toLowerCase().startsWith("tmall://")) {
                        if (!getIntent().getBooleanExtra("isCheck", false)) {
                            if (Utils.checkApkExist("com.tmall.wireless")) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            } else {
                                view.loadUrl(url.replace("tmall://", "https://"));
                            }
                        }
                    } else if (url.toLowerCase().startsWith("dmj://")) {
                        view.loadUrl(url.replace("dmj://", "http://"));
                    } else {
                        if (!url.toLowerCase().startsWith("alipays://")) {
                            if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
                                PayTask task = new PayTask(WebViewActivity.this);
                                boolean isIntercepted = task.payInterceptorWithUrl(url, false, h5PayResultModel -> {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            view.loadUrl("javascript:h5payresult(" + JSON.toJSONString(h5PayResultModel) + ")");
                                        }
                                    });
                                });
                                if (!isIntercepted) {
                                    view.loadUrl(url);
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
            }
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
//           showDialog();
            webProgress.setVisibility(View.VISIBLE);
            if (url.contains("tmall") || url.contains("taobao")) {
                if (url.contains("detail") && url.contains("id=")) {// 商品详情页
                    webTips.setVisibility(View.VISIBLE);
                    webDiscountLayout.setVisibility(View.VISIBLE);
                } else {// 隐藏查券
                    webTips.setVisibility(View.GONE);
                    webDiscountLayout.setVisibility(View.GONE);
                    webDiscountBtn.setVisibility(View.VISIBLE);
                    webBuyLayout.setVisibility(View.GONE);
                    webDiscountBtn.setBackgroundResource(R.drawable.login_btn_style);
                    webDiscountBtnText.setText("一键找券查佣金");
                    webDiscountBtn.setEnabled(true);
                }
            }
        }

        /**
         * 页面加载结束时调用
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            webProgress.setVisibility(View.GONE);
//          dismissDialog();
        }
    };

    public List<View> getAllChildViews(View view) {
        List<View> list = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View child = vp.getChildAt(i);
                list.add(child);
                list.addAll(getAllChildViews(child));
            }
        }
        return list;
    }


    @OnClick({R.id.back, R.id.close, R.id.web_discount_btn, R.id.web_discount_layout, R.id.web_tips, R.id.web_share_btn, R.id.web_buy_btn})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isClose();
                break;
            case R.id.close:
                setResult(0);
                isFinish();
                break;
            case R.id.web_discount_btn:// 查券
                if (DateStorage.getLoginStatus()) {
                    //获取收入比例
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    NetworkRequest.getInstance().POST(handler, paramMap, "GetRatio", HttpCommon.GetRatio);
                    showDialog();
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
                break;
            case R.id.web_share_btn:// 分享
                // 获取高佣链接
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopid", searchAll.getNumIid());
                paramMap.put("couponid", searchAll.getCouponId());
                paramMap.put("shopname", searchAll.getShopName());
                paramMap.put("shopmainpic", searchAll.getPictUrl());
                paramMap.put("reqsource", "1");
                NetworkRequest.getInstance().POST(handler, paramMap, "GetCommission", HttpCommon.GetCommission);
                showDialog();
                break;
            case R.id.web_buy_btn:// 购买
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopid", searchAll.getNumIid());
                paramMap.put("couponid", searchAll.getCouponId());
                paramMap.put("shopname", searchAll.getShopName());
                paramMap.put("shopmainpic", searchAll.getPictUrl());
                paramMap.put("reqsource", "1");
                NetworkRequest.getInstance().POST(handler, paramMap, "Purchase", HttpCommon.Purchase);
                showDialog();
                break;
            case R.id.web_discount_layout:
                // 防止点击到遮罩层下页面
                break;
            case R.id.web_tips:
                // 防止点击到遮罩层下页面
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void synCookie(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager manager = CookieManager.getInstance();
            manager.setAcceptCookie(true);
            manager.setCookie(url, "userId=" + login.getUserid());
            manager.flush();
        } else {
            CookieSyncManager.createInstance(this);
            CookieManager manager = CookieManager.getInstance();
            manager.setCookie(url, "userId=" + login.getUserid());
            CookieSyncManager.getInstance().sync();
        }
    }

    private void removeCookie() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager manager = CookieManager.getInstance();
            manager.removeAllCookies(null);
            manager.flush();
        } else {
            CookieSyncManager.createInstance(this);
            CookieManager manager = CookieManager.getInstance();
            manager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public void onBackPressedSupport() {
        isClose();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        web.reload();
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

    public void isClose() {
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
//                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("CloseTbaoAuthDialog"), false, 0);
                    setResult(0);
                    isFinish();
                }
            }
        });
    }

    TaobaoAuthLoginDialog Tdialog;
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.TaoBaoAuthResultToH5Success) {
            try{
                web.loadUrl("javascript:isTbAuthSuccess(" + message.obj.toString()+ ")");
            }catch (Exception e){

            }
        }
//        if (message.what == LogicActions.noAuthSuccessfulSuccess) {
//            dismissDialog();
//            Tdialog = new TaobaoAuthLoginDialog(this, message.obj.toString());
//            Tdialog.showDialog();
//        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
//            if(Tdialog != null) {
//                //判断dialog是否正在显示
//                if(Tdialog.isShowing()) {
//                    //get the Context object that was used to great the dialog
//                    Context context = ((ContextWrapper)Tdialog.getContext()).getBaseContext();
//                    //判断是所在Activity是否已经销毁
//                    if(context instanceof Activity) {
//                        if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
//                            Tdialog.dismiss();
//                    } else
//                        Tdialog.dismiss();
//                }
//                Tdialog = null;
//            }
//        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetRatioSuccess) {
            CommodityRatio ratio = (CommodityRatio) message.obj;
            Variable.ration = ratio.getRatio();
            Variable.superRatio = ratio.getSuperratio();
            String current = web.getUrl();
            current = current.substring(current.indexOf("id=") + 3);
            String id;
            if (current.contains("&")) {
                id = current.substring(0, current.indexOf("&"));
            } else {
                id = current;
            }
            paramMap.clear();
            paramMap.put("pageSize", "1");
            paramMap.put("pageNo", "1");
            paramMap.put("search", "http://item.taobao.com/item.htm?id=" + id);
            NetworkRequest.getInstance().POST(handler, paramMap, "SearchDiscount", HttpCommon.SearchDiscount);
        }
        if (message.what == LogicActions.SearchDiscountSuccess) {
            dismissDialog();
            if (message.obj instanceof String) {
                webDiscountBtn.setVisibility(View.VISIBLE);
                webBuyBtn.setVisibility(View.GONE);
                webDiscountBtn.setBackgroundResource(R.drawable.web_btn_background);
                webDiscountBtnText.setText("该商品未参与推广，换一个试试");
                webDiscountBtn.setEnabled(false);
            } else {
                webDiscountBtn.setVisibility(View.GONE);
                webBuyLayout.setVisibility(View.VISIBLE);
                webBuyBtn.setVisibility(View.VISIBLE);
                webDiscountBtn.setBackgroundResource(R.drawable.login_btn_style);
                webDiscountBtnText.setText("一键找券查佣金");
                webDiscountBtn.setEnabled(true);
                searchAll = (SearchAll) message.obj;
                webShareBtnText.setText("分享赚 " + getResources().getString(R.string.money) + searchAll.getEstimate());
                if (searchAll.getDiscount().equals("0")) {
                    webBuyBtnText.setText("立即购买");
                } else {
                    webBuyBtnText.setText("领券 " + getResources().getString(R.string.money) + searchAll.getDiscount());
                }
            }
        }
        if (message.what == LogicActions.GetCommissionSuccess) {
            ShareList shareList = (ShareList) message.obj;
            ArrayList<ShareCheck> list = new ArrayList<>();
            for (String item : searchAll.getSmallImages()) {
                ShareCheck share = new ShareCheck();
                share.setImage(item);
                share.setCheck(0);
                list.add(share);
            }
            list.get(0).setCheck(1);
            dismissDialog();
            startActivity(new Intent(this, ShareActivity330.class)
                    .putExtra("image", list)
                    .putExtra("name", searchAll.getShopName())
                    .putExtra("sales", searchAll.getVolume() + "")
                    .putExtra("money", searchAll.getMoney())
                    .putExtra("discount", searchAll.getDiscount())
                    .putExtra("shortLink", shareList.getShareshortlink())
                    .putExtra("recommend", "")
                    .putExtra("countersign", shareList.getTpwd())
                    .putExtra("isCheck", searchAll.isCheck()));
        }
        if (message.what == LogicActions.PurchaseSuccess) {
            dismissDialog();
            Alibc alibc = (Alibc) message.obj;
            dialog.showDialog();
            Utils.Alibc(this, alibc.getCouponlink(), alibc.getPid(), this);
            new Handler().postDelayed(dialog::hideDialog, 3500);
        }
//        if (message.what == LogicActions.CreditCardTokenSuccess) {
//            Variable.encrypt=message.obj.toString();
//            loginCreditCard();
//        }
    }


    public  void  downLoad(String url){
        imgurl=url;
        Glide.with(this).asBitmap()
                .load(imgurl).into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                ToastUtil.showImageToast(WebViewActivity.this,"图片已保存至相册",R.mipmap.toast_img);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 关闭页面
     */
    @Override
    public void isFinish() {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        web.clearCache(true);
        web.destroy();
        Variable.AuthShowStatus=false;
    }


}
