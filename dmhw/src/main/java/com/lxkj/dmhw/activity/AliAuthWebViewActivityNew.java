package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.SearchAll;
import com.lxkj.dmhw.bean.ShareList;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JavascriptHandler;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.CouponLinkDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//专门用于淘宝授权webview
public class AliAuthWebViewActivityNew extends BaseActivity implements AlibcTradeCallback {

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
    WebView web;
    @BindView(R.id.network_mask)
    LinearLayout main_network_mask;
    private String url;
    private String imgurl;
    private SearchAll searchAll;
    // 支付宝跳转
    AlipayDialog dialog;

    private JSONObject tempJson=new JSONObject();
    private int type;

    //是否跳转淘宝
    private boolean isGoTaobao=true;
    //是否跳转淘宝自己的商品详情页  天猫国际 一键查劵要用
    private int noGoTaoBaoH5=0;
    private boolean isneedchangelink=false;
    //信用卡参数设置返佣比例等
    private String bonusRate="12";
    private String shareRate="10";
    private String showType="A";
    private String isShare="false";
    //同一个商品页面 点击“我知道了” 不再显示弹框
    private Boolean isshowDialog=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_web_view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        Variable.AuthShowStatus=true;
        url = getIntent().getExtras().getString(Variable.webUrl);
        dialog = new AlipayDialog(this);
        isGoTaobao=getIntent().getBooleanExtra("isGoTaobao",true);
        noGoTaoBaoH5=getIntent().getIntExtra("noGoTaoBaoH5",0);
//        if (DateStorage.getIsAuth()){
//            //淘宝授权
//          LoginService  loginService = MemberSDK.getService(LoginService.class);
//            loginService.auth(new LoginCallback(){
//                @Override
//                public void onFailure(int i, String s) {
//                    toast(s);
//                }
//                @Override
//                public void onSuccess(Session session) {
//                    //获取登录状态
//                }
//            });
//        }
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
        if (getIntent().getBooleanExtra("isTitle", false)) {
            webTitle.setVisibility(View.GONE);
        } else {
            webTitle.setVisibility(View.VISIBLE);
        }
        WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        web.clearCache(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);//解决视频白屏
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
        web.addJavascriptInterface(new JavascriptHandler(this,6), "live");
        AlibcTrade.openByUrl(AliAuthWebViewActivityNew.this,"", url, web,
                new WebViewClient(), new WebChromeClient(), null,
                null, null, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                    }
                    @Override
                    public void onFailure(int code, String msg) {
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
                            ToastUtil.showImageToast(AliAuthWebViewActivityNew.this,"图片已保存至相册",R.mipmap.toast_img);
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
                if (noGoTaoBaoH5==12){
                    isneedchangelink=true;
                    if(!url.startsWith("tbopen://")){
                        convertshopid(url);
                    }
                    return true;
                }
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
                if (url.toLowerCase().startsWith("taobao://")&&isGoTaobao) {
                    if (!getIntent().getBooleanExtra("isCheck", false)) {
                        Utils.Alibc(AliAuthWebViewActivityNew.this, url.replace("taobao://", "https://"), null, null);
                    }
                } else if (url.toLowerCase().startsWith("tmall://")&&isGoTaobao) {
                    if (!getIntent().getBooleanExtra("isCheck", false)) {
                        if (Utils.checkApkExist("com.tmall.wireless")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }else{
                            if (url.toLowerCase().startsWith("https://")){
                                view.loadUrl(url.replace("tmall://", "https://"));
                            }else{
                                view.loadUrl(url.replace("tmall://", "http://"));
                            }
                        }
                    }
                } else if (url.toLowerCase().startsWith("dmj://")) {
                    view.loadUrl(url.replace("dmj://", "http://"));
                } else {
                    if (!url.toLowerCase().startsWith("alipays://")) {
                        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
                            /**
                             * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
                             */
                            final PayTask task = new PayTask(AliAuthWebViewActivityNew.this);
                            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                                @Override
                                public void onPayResult(final H5PayResultModel result) {
                                    AliAuthWebViewActivityNew.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            view.loadUrl("javascript:h5payresult("+ JSON.toJSONString(result) +")");
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
            if (NetStateUtils.isNetworkConnected(AliAuthWebViewActivityNew.this)) {
                main_network_mask.setVisibility(View.GONE);
                webProgress.setVisibility(View.VISIBLE);
                if (noGoTaoBaoH5 != 12) {
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
            } else {
                main_network_mask.setVisibility(View.VISIBLE);
                toast(getResources().getString(R.string.net_work_unconnect));
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
            isneedchangelink=false;
            dismissDialog();
             if(url.toLowerCase().contains("mlapp/cart")){
                //编写 javaScript方法
                String javascript =  "javascript:function hideOther() {" +
                        "var headers = document.getElementsByTagName('header');" +
                        "var lastHeader = headers[headers.length-1];" +
                        "lastHeader.remove();" +
                        "}";
                //创建方法
                view.loadUrl(javascript);
                //加载方法
                view.loadUrl("javascript:hideOther();");
                view.loadUrl("javascript:function setTop(){document.querySelector('.toolbar-footer').style.display=\"none\";}setTop();");
             }

        }

    };

    @OnClick({R.id.back, R.id.close, R.id.web_discount_btn, R.id.web_discount_layout, R.id.web_tips, R.id.web_share_btn, R.id.web_buy_btn})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isCanClose();
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


    @Override
    public void isFinish() {
        super.isFinish();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        web.reload();
    }
    public void goToUrl(String url){
        //fragment 使用getActivity()

    }


    public void isCanClose() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (web.canGoBack()) {
                    web.goBack();
                } else {
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("CloseTbaoAuthDialog"), false, 0);
                    setResult(0);
                    isFinish();
                }
            }
        });
    }



    TaobaoAuthLoginDialog Tdialog;
    @Override
    public void mainMessage(Message message) {
//        if (message.what == LogicActions.noAuthSuccessfulSuccess) {
//            dismissDialog();
//            Tdialog = new TaobaoAuthLoginDialog(this, message.obj.toString());
//            Tdialog.showDialog();
//        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
////            if (Tdialog!=null){
////                Tdialog.dismiss();
////            }
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
        //分享成功
        if (message.what == LogicActions.ShareStatusSuccess){
             InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
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
            if(noGoTaoBaoH5==12){
            }else{
                String current = web.getUrl();
                current = current.substring(current.indexOf("id=") + 3);
                String id;
                if (current.contains("&")) {
                    id = current.substring(0, current.indexOf("&"));
                } else {
                    id = current;
                }
                SearchDiscount(id);
            }
            dismissDialog();
        }
        if (message.what == LogicActions.SearchDiscountSuccess) {
            dismissDialog();
            if(noGoTaoBaoH5==12){
                searchAll=(SearchAll) message.obj;
                //获取收入比例
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "GetRatio", HttpCommon.GetRatio);

            }else {
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
            dismissDialog();
        }
        if (message.what == LogicActions.GetCommissionSuccess) {
            ShareList shareList = (ShareList) message.obj;
            if (shareList.getTips().getKey().equals("")){
                DateStorage.setIsKey("");
                GetCommissionSuccess(shareList);
            }else {
                if (shareList.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                    //已经点击了不再提醒
                    GetCommissionSuccess(shareList);
                } else {
                    try{
                        CouponLinkDialog couponLinkDialog = new CouponLinkDialog(AliAuthWebViewActivityNew.this,shareList.getTips());
                        couponLinkDialog.showDialog();
                        couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                            @Override
                            public void onClick(int pos) {
                                if (pos == 0) {//不再提醒
                                    DateStorage.setIsKey(shareList.getTips().getKey());
                                }else{
                                    isshowDialog=true;
                                }
                                GetCommissionSuccess(shareList);
                            }
                        });
                    }catch(Exception e){

                    }

                }
            }
            dismissDialog();
        }
        if (message.what == LogicActions.PurchaseSuccess) {
            dismissDialog();
            Alibc alibc = (Alibc) message.obj;
            String Couponlink=alibc.getCouponlink();
            String pid=alibc.getPid();
            if (alibc.getTips().getKey().equals("")){
                DateStorage.setIsKey("");
                dialog.showDialog();
                Utils.Alibc(this, Couponlink, pid, this);
                new Handler().postDelayed(dialog::hideDialog, 3500);
            }else {
                if (alibc.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                    //已经点击了不再提醒
                    dialog.showDialog();
                    Utils.Alibc(this, Couponlink, pid, this);
                    new Handler().postDelayed(dialog::hideDialog, 3500);
                } else {
                    try{
                        CouponLinkDialog couponLinkDialog = new CouponLinkDialog(this,alibc.getTips());
                        couponLinkDialog.showDialog();
                        couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                            @Override
                            public void onClick(int pos) {
                                if (pos == 0) {//不再提醒
                                    DateStorage.setIsKey(alibc.getTips().getKey());
                                }else{
                                    isshowDialog=true;
                                }
                                dialog.showDialog();
                                Utils.Alibc(AliAuthWebViewActivityNew.this, Couponlink, pid, AliAuthWebViewActivityNew.this);
                                new Handler().postDelayed(dialog::hideDialog, 3500);
                            }
                        });
                    }catch(Exception e){

                    }

                }
            }
            dismissDialog();
        }
        if (message.what == LogicActions.CreditCardTokenSuccess) {
            Variable.encrypt=message.obj.toString();
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
            dismissDialog();
        }


        //需要转链的页面混着 需要转链和不需要转链的
        if (noGoTaoBaoH5==12&&message.what == LogicActions.Failed&&isneedchangelink){
            web.loadUrl(message.obj+"");
        }


        // 获取APP分享地址
        if (message.what == LogicActions.AppShareSuccess) {
            poster = (Poster) message.obj;
            shareAppDialog();
            dismissDialog();
        }
    }


    public  void  downLoad(String url){
        imgurl=url;
        Glide.with(this).asBitmap()
                .load(imgurl).into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                ToastUtil.showImageToast(AliAuthWebViewActivityNew.this,"图片已保存至相册",R.mipmap.toast_img);
            }
        });
    }

    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(AliAuthWebViewActivityNew.this, title,
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Variable.AuthShowStatus=false;
//        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshTaskList"), "", 0);
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
            KDFInterface.getInstance().login(this,Variable.encrypt, bonusRate, showType, isShare, shareRate, new OnComplete() {
                @Override
                public void onSuccess(JSONObject o) {
                    if (type==0){
                        KDFInterface.getInstance().toCardStoreActivity(AliAuthWebViewActivityNew.this, null);
                    }else {
                        try{
                        //查询办卡进度
                        KDFInterface.getInstance().toOrderProgressActivity(AliAuthWebViewActivityNew.this, tempJson.getString("bankCode"), tempJson.getString("id"), tempJson.getString("custName"), tempJson.has("crawId") ? tempJson.getString("crawId") : "", null);
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
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime <500) {
              isFinish();
            } else {
                isCanClose();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //查找商品信息
    private void SearchDiscount(String id) {
        paramMap.clear();
        paramMap.put("pageSize", "1");
        paramMap.put("pageNo", "1");
        paramMap.put("search", "http://item.taobao.com/item.htm?id=" + id);
        NetworkRequest.getInstance().POST(handler, paramMap, "SearchDiscount", HttpCommon.SearchDiscount);
    }


    //链接转商品ID
    private void convertshopid(String url){
//        showDialog();
        paramMap.clear();
        paramMap.put("url",url);
        NetworkRequest.getInstance().POST(handler, paramMap, "ConvertShopid", HttpCommon.ConvertShopid);

    }

    @Override
    public void onBackPressedSupport() {
        isCanClose();
    }


    //获取高佣之后的执行动作
    private void GetCommissionSuccess(ShareList shareList){
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
                .putExtra("isCheck", searchAll.isCheck())
                .putExtra("commission", searchAll.getEstimate())
        );
    }

    //分享App
    private Poster poster = new Poster();
    public void  shareAppTask(){
        // 获取分享地址
        if (!DateStorage.getLoginStatus())
        {
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }
        if (poster.getAppsharelink().equals("")){
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "AppShare", HttpCommon.AppShare);
        }else{
            shareAppDialog();
        }

    }

    //调出分享窗口
    private void shareAppDialog(){
        ShareParams params = new ShareParams();
        params.setUrl(poster.getAppsharelink());
        Share.getInstance(0).initialize(params,1);
    }
}
