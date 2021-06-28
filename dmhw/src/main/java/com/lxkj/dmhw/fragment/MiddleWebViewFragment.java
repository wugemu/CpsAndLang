package com.lxkj.dmhw.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.PosterActivity;
import com.lxkj.dmhw.activity.VideoActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.WebViewOtherActivity;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.AlbumNotifyHelper;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MiddleWebViewFragment extends BaseFragment {

    @BindView(R.id.center_progress)
    ProgressBar webProgress;
    @BindView(R.id.web)
    WebView web;
    private String url;
    private String imgurl;
    @BindView(R.id.network_mask)
    LinearLayout rl_network_mask;
    public static String  UserType="";
    public static MiddleWebViewFragment getInstance(String url) {
        MiddleWebViewFragment fragment = new MiddleWebViewFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (NetStateUtils.isNetworkConnected(getActivity())) {
            rl_network_mask.setVisibility(View.GONE);
            // 获取H5地址
            paramMap.clear();
            paramMap.put("type", "8");
            NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
        }else{
            rl_network_mask.setVisibility(View.VISIBLE);
        }
        rl_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_network_mask.setVisibility(View.GONE);
                // 获取H5地址
                paramMap.clear();
                paramMap.put("type", "8");
                NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
            }
        });
        UserType=login.getUsertype();
    }

    @Override
    public void onCustomized() {

    }

    /**
     * 刷新页面
     */
    public void refresh() {
        web.reload();
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
        web.getSettings().setCacheMode(android.webkit.WebSettings.LOAD_DEFAULT);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);//解决视频白屏
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(webViewClient);
        web.setOnCreateContextMenuListener(this);
        web.addJavascriptInterface(new Jshandler(),"live");
        web.post(() -> {
            if (url.toLowerCase().startsWith("dmj://")) {
                web.loadUrl(url.replace("dmj://", "http://"));
            } else {
                web.loadUrl(url);
            }
        });
    }

    public class Jshandler {
        @JavascriptInterface
        public void isBack(){
           isCanClose();
        }
        /**
         * 单图分享
         */
        @JavascriptInterface
        public void share(String data) {
            try {
                JSONObject object = new JSONObject(data);
                ShareParams params=null;
                switch (object.getString("type")) {
                    case "image":// 图片
                        params = new ShareParams();
                        ArrayList<String> images = new ArrayList<>(Arrays.asList(object.getString("url").split(",")));
                        params.setImage(images);
                        break;
                    case "baseimage":// bitmap图片 逗号隔开去字符流
                        String str2=object.getString("imagedata").replace(" ", "");//去掉所用空格
                        List<String> list= Arrays.asList(str2.split(","));
                        shareimagebybyte(list.get(1));
                        break;
                    default:
                        params = new ShareParams();
                        if (object.getString("title").equals("")){
                            params.setTitle(Variable.shareTitle);
                        }else{
                            params.setTitle(object.getString("title"));
                        }
                        params.setContent(object.getString("description"));
                        params.setUrl(object.getString("url"));
                        params.setThumbData(object.getString("thumbData"));
                        break;
                }
                Share.getInstance(0).initialize(params,1);
            } catch (JSONException e) {
                Logger.e(e, "分享功能");
            }
        }

        /**
         * 链接分享
         */
        @JavascriptInterface
        public void shareLink(String title, String content, String link) {
            ShareParams params = new ShareParams();
            params.setTitle(title);
            params.setContent(content);
            params.setUrl(link);
            Share.getInstance(0).initialize(params,1);
        }

        /**
         * 保存图片
         */
        @JavascriptInterface
        public void save(String path) {
            if (!Utils.checkPermision(getActivity(),1009,true)) {
                return;
            }
            Glide.with(getActivity()).asBitmap()
                    .load(path).into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                    ToastUtil.showImageToast(getActivity(),"图片已保存至相册",R.mipmap.toast_img);
                }
            });

        }
        /**
         * 保存图片
         */
        @JavascriptInterface
        public void saveimgae(String path) {
            if (!Utils.checkPermision(getActivity(),1009,true)) {
                return;
            }
            Glide.with(getActivity()).asBitmap()
                    .load(path).into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                    ToastUtil.showImageToast(getActivity(),"图片已保存至相册",R.mipmap.toast_img);
                }
            });
        }
        /**
         * 保存视频
         */
        @JavascriptInterface
        public void savevideo(String path) {
            if (!Utils.checkPermision(getActivity(),1009,true)) {
                return;
            }
            showProgressDialog("提示","视频下载中...");
            int start = path.lastIndexOf("/");
            int end = path.lastIndexOf(".");
            String name = path.substring(start + 1, end);
            String Suffix = path.substring(end);
            FileDownloader.getImpl().create(path).setPath(Variable.videoPath + "/" + name + Suffix).setListener(new FileDownloadListener() {
                @Override
                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                }

                @Override
                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                }

                @Override
                protected void completed(BaseDownloadTask task) {
                    AlbumNotifyHelper.insertVideoToMediaStore(getActivity(), task.getPath(), 0, 20000);
                    //scanFile不可少，才能插入视频数据库
                    AlbumNotifyHelper.scanFile(getActivity(),task.getPath());
                    ToastUtil.showImageToast(getActivity(),"视频下载成功",R.mipmap.toast_img);
                    hideProgressDialog();
                }

                @Override
                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                }

                @Override
                protected void error(BaseDownloadTask task, Throwable e) {
                    toast("下载出现错误，请稍后重试");
                   hideProgressDialog();

                }

                @Override
                protected void warn(BaseDownloadTask task) {

                }
            }).start();
        }
        /**
         * 返回头部数据
         */
        @JavascriptInterface
        public String pubrep() {
            UserInfo info = DateStorage.getInformation();
            JSONObject object = new JSONObject();
            try {
                object.put("islogin", DateStorage.getLoginStatus() ? "1" : "0");
                object.put("userid", info.getUserid());
                object.put("merchantid", info.getMerchantid());
                object.put("devversion", Utils.getAppVersionCode() + "");
                object.put("devtype", "00");
            } catch (JSONException e) {
                Logger.e(e, "返回头部数据");
            }
            return object.toString();
        }

        /**
         * 打开新页面
         * @param url
         */
        @JavascriptInterface
        public void jumpToNext(String url) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(Variable.webUrl, url);
            intent.putExtra("isTitle" , true);
            getActivity().startActivity(intent);
        }

        /**
         * 打开新页面
         * @param url
         */
        @JavascriptInterface
        public void jumpToNativeNext(String url) {
            Intent intent = new Intent(getActivity(), WebViewOtherActivity.class);
            intent.putExtra(Variable.webUrl, url);
            intent.putExtra("hideTop" , 0);
            getActivity().startActivity(intent);
        }

        /**
         * 弹出通知
         */
        private void toast(String content) {
            Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
        }
        /**
         * 弹出通知
         */
        private void toastLong(String content) {
            Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
        }



        //字符数组保存图片
        @JavascriptInterface
        public void saveimgaebyte(String bytes){
            if (!Utils.checkPermision(getActivity(),1009,true)) {
                return;
            }
            String str2=bytes.replace(" ", "");//去掉所用空格
            List<String> list= Arrays.asList(str2.split(","));
            Bitmap bitmap=Utils.stringtoBitmap(list.get(1));
            Utils.saveFile(Variable.imagesPath, bitmap, 100, true);
            ToastUtil.showImageToast(getActivity(),"图片已保存至相册", R.mipmap.toast_img);
        }
        //字符数组分享图片
        @JavascriptInterface
        public void shareimagebybyte(String bytes){
            ShareParams params = new ShareParams();
            Bitmap bitmap=Utils.stringtoBitmap(bytes);
            Share.getInstance(0).shareBitmap(params,bitmap);//直接分享bitmap
        }



        @JavascriptInterface
        public void toAppShop(String id) {
            //站内商品
           startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "all").putExtra("sourceId",""));
        }

        @JavascriptInterface
        public void toTbShop(String id) {
            //全网商品
            startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "tb").putExtra("sourceId",""));
        }

        @android.webkit.JavascriptInterface
        public void toMain(){
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
        }

        @android.webkit.JavascriptInterface
        public void shareApp() {
            shareAppTask();
        }

        //视频播放
        @android.webkit.JavascriptInterface
        public void play(String url) {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            intent.putExtra("videoUrl",url);
            intent.putExtra("title","");
            getActivity().startActivity(intent);
        }
        //分享App
        private Poster poster = new Poster();
        public void  shareAppTask(){
            // 获取分享地址
            if (!DateStorage.getLoginStatus())
            {
                startActivity(new Intent(getActivity(),LoginActivity.class));
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
        /*
         *=======================================2.8.0新开放JS接口==========================================================
         */

        /*
         *唤醒微信
         */
        @JavascriptInterface
        public void openWechat(){
            Utils.openWechat(getActivity());
        }

        /*
         *打开呆萌价小程序
         */
        @JavascriptInterface
        public void openDmjMini(String path){
            String url=path+"?referId="+ DateStorage.getLittleAppId();
            Utils.launchLittleApp(getActivity(),url);
        }

        /*
         *打开站内商品详情
         */
        @JavascriptInterface
        public void openTbShopApp(String id){
           startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "all").putExtra("sourceId",""));
        }

        /*
         *打开全网搜商品详情
         */
        @JavascriptInterface
        public void openTbShopNet(String id){
          startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "tb").putExtra("sourceId",""));
        }

        /*
         *跳转APP个人中心
         */
        @JavascriptInterface
        public void toCenter(){
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 2);
        }

        /*
         *弹出淘宝渠道授权
         */
        @JavascriptInterface
        public void openTbAuth(String url,String message){
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("url",url);
                jsonObject.put("msgstr",message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TaobaoAuthLoginDialog Tdialog = new TaobaoAuthLoginDialog(getActivity(), jsonObject.toString());
            Tdialog.showDialog();
        }

        /*
         *打开拼多多商品详情
         */
        @JavascriptInterface
        public void openPddShop(String id){
//            String path="pagesGoods/detail/detail?type=pdd&id="+id+"&referId="+ DateStorage.getLittleAppId();
//            Utils.launchLittleApp(getActivity(),path);
            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "pdd"));
        }

        /*
         *打开京东商品详情
         */
        @JavascriptInterface
        public void openJdShop(String id){
//            String path="pagesGoods/detail/detail?type=jd&id="+id+"&referId="+ DateStorage.getLittleAppId();
//            Utils.launchLittleApp(getActivity(),path);
            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "jd"));
        }

        /*
         *打开唯品会商品详情
         */
        @JavascriptInterface
        public void openWphShop(String id){
//            String path="pagesGoods/detail/detail?type=wph&id="+id+"&referId="+ DateStorage.getLittleAppId();
//            Utils.launchLittleApp(getActivity(),path);
            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "wph"));
        }

        /*
         *分享小程序消息
         */
        @JavascriptInterface
        public void shareMicroWechat(String path,String imageUrl){
            Glide.with(getActivity()).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>(){
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Utils.shareLittleAppContentToWechat(getActivity(),path,"","",resource);
                }
            });
        }

        /*
         *进入邀请页面
         */
        @JavascriptInterface
        public void toShare(){
            Intent intent = new Intent(getActivity(), PosterActivity.class);
            getActivity().startActivity(intent);
        }

        //升级店主刷新回调
        @JavascriptInterface
        public void refreshMain(int index){
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshUserTypeLayout"), false, index);
        }

        @JavascriptInterface
        public int getStatusBarHeight(){
          return Variable.statusBarHeight;
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

            if (getActivity().getIntent().getBooleanExtra("isTitle", false)) {
                if (url.toLowerCase().startsWith("alipays://")) {
                    if (!getActivity().getIntent().getBooleanExtra("isCheck", false)) {
                        if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        } else {
                            view.loadUrl(url.replace("alipays://", "http://"));
                        }
                    }
                }
            }
            if (url.toLowerCase().startsWith("taobao://")) {
                if (!getActivity().getIntent().getBooleanExtra("isCheck", false)) {
                    Utils.Alibc(getActivity(), url.replace("taobao://", "https://"), null, null);
                }
            } else if (url.toLowerCase().startsWith("tmall://")) {
                if (!getActivity().getIntent().getBooleanExtra("isCheck", false)) {
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
                        final PayTask task = new PayTask(getActivity());
                        boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                            @Override
                            public void onPayResult(final H5PayResultModel result) {
                                getActivity().runOnUiThread(new Runnable() {
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
            webProgress.setVisibility(View.VISIBLE);
        }

        /**
         * 页面加载结束时调用
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            webProgress.setVisibility(View.GONE);
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
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            if (h5Links.size()>0){
                url=h5Links.get(0).getUrl();
                doInit();
            }
        }
        if (message.what == LogicActions.GetUserInfoSuccess) {
            UserInfo login = (UserInfo) message.obj;
            // 储存用户信息
            DateStorage.setInformation(login);
           UserType=login.getUsertype();
            // 获取H5地址
            paramMap.clear();
            paramMap.put("type", "8");
            NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
        }
    }

    public void isCanClose() {
        getActivity().runOnUiThread(new Runnable() {
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
    public void onDestroyView() {
        super.onDestroyView();

    }

    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), title,
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
}
