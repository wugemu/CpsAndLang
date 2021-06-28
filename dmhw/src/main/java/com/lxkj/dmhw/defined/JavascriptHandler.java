package com.lxkj.dmhw.defined;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.AliAuthWebViewActivityNew;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.PosterActivity;
import com.lxkj.dmhw.activity.VideoActivity;
import com.lxkj.dmhw.activity.WebPageNavigationActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.WebViewOtherActivity;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.AlbumNotifyHelper;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网页接口
 */
public class JavascriptHandler {

    private Activity activity;
    private int position;

    public static Boolean autoLink=false;
    public static String linkUrl="";
    com.lxkj.dmhw.dialog.ProgressDialog Pddialog;
    public JavascriptHandler(Activity activity,int pos) {
        this.activity = activity;
        this.position=pos;
    }

    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void isClose() {
        if (position==0){
            ((WebViewActivity) activity).isFinish();
        }else if (position==3){
            ((WebViewOtherActivity) activity).isFinish();
        }else if (position==4){
            ((AliAuthWebViewActivity) activity).isFinish();
        }else if (position==6){
            ((AliAuthWebViewActivityNew) activity).isFinish();
        }
        else{
        }

    }

    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void isBack() {
        if (position==0){
            ((WebViewActivity) activity).isClose();
        }else if (position==3){
            ((WebViewOtherActivity) activity).isClose();
        }
        else if(position==4){
            ((AliAuthWebViewActivity) activity).isCanClose();
        }else if (position==6){
            ((AliAuthWebViewActivityNew) activity).isCanClose();
        }
        else{
        }

    }

    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(activity, title,
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
    /**
     * 隐藏输入法
     */
    @JavascriptInterface
    public void isHideInput() {
        Utils.hideSoftInput(activity, null);
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
                case "baseimage":// base64图片 逗号隔开去字符流
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
        if (!Utils.checkPermision(activity,1008,true)) {
            return;
        }
        Pddialog=new com.lxkj.dmhw.dialog.ProgressDialog(activity, "");
        Pddialog.showDialog();
        ArrayList<String> images = new ArrayList<>(Arrays.asList(path.split(",")));
        for (int i = 0; i < images.size(); i++) {
            Glide.with(activity).asBitmap()
                    .load(images.get(i)).into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                     Utils.saveFile(Variable.picturePath, resource, 100, true);
                }
            });
        }
        Pddialog.hideDialog();
         ToastUtil.showImageToast(activity,"图片已保存至相册",R.mipmap.toast_img);

    }
    /**
     * 保存图片
     */
    @JavascriptInterface
    public void saveimgae(String path) {
        if (!Utils.checkPermision(activity,1008,true)) {
            return;
        }
        Pddialog=new com.lxkj.dmhw.dialog.ProgressDialog(activity, "");
        Pddialog.showDialog();
        ArrayList<String> images = new ArrayList<>(Arrays.asList(path.split(",")));
        for (int i = 0; i < images.size(); i++) {
            Glide.with(activity).asBitmap()
                    .load(images.get(i)).into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Utils.saveFile(Variable.picturePath, resource, 100, true);
                }
            });
        }
        Pddialog.hideDialog();
        ToastUtil.showImageToast(activity,"图片已保存至相册",R.mipmap.toast_img);
    }
    /**
     * 保存视频
     */
    @JavascriptInterface
    public void savevideo(String path) {
        if (!Utils.checkPermision(activity,1008,true)) {
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
                AlbumNotifyHelper.insertVideoToMediaStore(activity, task.getPath(), 0, 20000);
                //scanFile不可少，才能插入视频数据库
                AlbumNotifyHelper.scanFile(activity,task.getPath());
                ToastUtil.showImageToast(activity,"视频下载成功",R.mipmap.toast_img);
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
            object.put("apiversion", Variable.ApiVersion);
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
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(Variable.webUrl, url);
        intent.putExtra("isTitle" , true);
        activity.startActivity(intent);
    }

    /**
     * 打开新页面
     * @param url
     */
    @JavascriptInterface
    public void jumpToNativeNext(String url) {
        Intent intent = new Intent(activity, WebViewOtherActivity.class);
        intent.putExtra(Variable.webUrl, url);
        intent.putExtra("hideTop" , 0);
        activity.startActivity(intent);
    }

    /**
     * 弹出通知
     */
    private void toast(String content) {
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
    }
    /**
     * 弹出通知
     */
    private void toastLong(String content) {
        Toast.makeText(activity, content, Toast.LENGTH_LONG).show();
    }

   //信用卡回到首页
    @JavascriptInterface
    public void openCreditHome(){
        if (position==3){
            ((WebViewOtherActivity) activity).httpgetCreditToken();
        } else if(position==4){
            ((AliAuthWebViewActivity) activity).httpgetCreditToken();
        }
    }

    //信用卡查询进度
    @JavascriptInterface
    public void openOrderProgress(String json){
        if (position==3){
           try {
                JSONObject object = new JSONObject(json);
                ((WebViewOtherActivity) activity).httpgetCreditToken(object);
            } catch (JSONException e) {
                Logger.e(e, "返回头部数据");
            }
        } else if(position==4){
            try {
                JSONObject object = new JSONObject(json);
                ((AliAuthWebViewActivity) activity).httpgetCreditToken(object);
            } catch (JSONException e) {
                Logger.e(e, "返回头部数据");
            }

        }
    }

    //字符数组保存图片
    @JavascriptInterface
    public void saveimgaebyte(String bytes){
        if (!Utils.checkPermision(activity,1008,true)) {
            return;
        }
        String str2=bytes.replace(" ", "");//去掉所用空格
        List<String> list= Arrays.asList(str2.split(","));
        Bitmap bitmap=Utils.stringtoBitmap(list.get(1));
       Utils.saveFile(Variable.imagesPath, bitmap, 100, true);
        ToastUtil.showImageToast(activity,"图片已保存至相册", R.mipmap.toast_img);
    }
    //字符数组分享图片
    @JavascriptInterface
    public void shareimagebybyte(String bytes){
        ShareParams params = new ShareParams();
        Bitmap bitmap=Utils.stringtoBitmap(bytes);
        Share.getInstance(0).shareBitmap(params,bitmap);//直接分享bitmap
    }

    @JavascriptInterface
    public void toLogin(String url) {
    //跳转登录 自动跳转url
     autoLink=true;
     linkUrl=url;
     activity.startActivity(new Intent(activity, LoginActivity.class));
     isClose();
    }

    @JavascriptInterface
    public void toAppShop(String id) {
    //站内商品
        activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "all").putExtra("sourceId",""));
    }

    @JavascriptInterface
    public void toTbShop(String id) {
    //全网商品
        activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "tb").putExtra("sourceId",""));
    }

    @android.webkit.JavascriptInterface
    public void toMain(){
    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
    }

    @android.webkit.JavascriptInterface
    public void shareApp() {
    ((AliAuthWebViewActivity) activity).shareAppTask();
    }

    //视频播放
    @android.webkit.JavascriptInterface
    public void play(String url) {
        Intent intent = new Intent(activity,VideoActivity .class);
        intent.putExtra("videoUrl",url);
        intent.putExtra("title","");
        activity.startActivity(intent);
    }

    /*
     *=======================================2.8.0新开放JS接口==========================================================
     */

    /*
     *唤醒微信
     */
    @JavascriptInterface
   public void openWechat(){
        Utils.openWechat(activity);
   }

    /*
     *打开呆萌价小程序
     */
    @JavascriptInterface
    public void openDmjMini(String path){
        String url=path+"?referId="+ DateStorage.getLittleAppId();
        Utils.launchLittleApp(activity,url);
    }

    /*
     *打开站内商品详情
     */
    @JavascriptInterface
    public void openTbShopApp(String id){
        activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "all").putExtra("sourceId",""));
    }

    /*
     *打开全网搜商品详情
     */
    @JavascriptInterface
    public void openTbShopNet(String id){
        activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "tb").putExtra("sourceId",""));
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
    public void openTbAuthEx(String url,String message){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("url",url);
            jsonObject.put("msgstr",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TaobaoAuthLoginDialog  Tdialog = new TaobaoAuthLoginDialog(activity, jsonObject.toString());
        Tdialog.showDialog();
    }

    /*
     *打开拼多多商品详情
     */
    @JavascriptInterface
    public void openPddShop(String id){
//     String path="pagesGoods/detail/detail?type=pdd&id="+id+"&referId="+ DateStorage.getLittleAppId();
//     Utils.launchLittleApp(activity,path);
        activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "pdd"));
    }

    /*
     *打开京东商品详情
     */
    @JavascriptInterface
    public void openJdShop(String id){
//        String path="pagesGoods/detail/detail?type=jd&id="+id+"&referId="+ DateStorage.getLittleAppId();
//        Utils.launchLittleApp(activity,path);
        activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "jd"));
    }

    /*
     *打开唯品会商品详情
     */
    @JavascriptInterface
    public void openWphShop(String id){
//        String path="pagesGoods/detail/detail?type=wph&id="+id+"&referId="+ DateStorage.getLittleAppId();
//        Utils.launchLittleApp(activity,path);
        activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", id).putExtra("type", "wph"));
    }

    /*
     *分享小程序消息
     */
    @JavascriptInterface
    public void shareMicroWechat(String path,String imageUrl){
        Glide.with(activity).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Utils.shareLittleAppContentToWechat(activity,path,"","",resource);
            }
        });
    }

    /*
     *进入邀请页面
     */
    @JavascriptInterface
    public void toShare(){
        Intent intent = new Intent(activity, PosterActivity.class);
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public int getStatusBarHeight(){//提供状态栏高度给H5
        return Variable.statusBarHeight;
    }

    //升级店主刷新回调
   @JavascriptInterface
   public void refreshMain(int index){
    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshUserTypeLayout"), false, index);
   }


    //唤醒七鱼客服
    @JavascriptInterface
    public void openkf(String userId,String data,String goods){
        Utils.openMyService(activity,userId,data,goods);
    }

    //商品详情（新）
    @JavascriptInterface
    public void openTbShop(String id, String source,String sourceId){
       activity.startActivity(new Intent(activity, CommodityActivity290.class).putExtra("shopId",id).putExtra("source", source).putExtra("sourceId", sourceId));
    }

    //打开淘宝活动
    @JavascriptInterface
    public void openTbUrl(String url){
        Intent intent = new Intent(activity, AliAuthWebViewActivity.class);
        intent.putExtra(Variable.webUrl, url);
        intent.putExtra("isTitle", false);
        intent.putExtra("noGoTaoBaoH5", 12);
    }

    //======================================3.1.0=======================================
    //打开轮播图广告
    @JavascriptInterface
    public void clickAdv(String adv){
        if (adv!=null&&!adv.equals("")) {
            Object bannerObject = JSON.parseObject(adv, HomePage.Banner.class);
            HomePage.Banner banner = (HomePage.Banner) bannerObject;
            Utils.getJumpType(activity, banner.getJumptype(), banner.getAdvertisementlink(), banner.getNeedlogin(), banner.getAdvertisemenid());
        }
    }

    //打开拼多多客户端
    @JavascriptInterface
    public void toPddApp(String content,String url){
        Utils.callMorePlatFrom(activity,1,content,url);
    }

    //打开京东客户端
    @JavascriptInterface
    public void toJdApp(String content,String url){
        Utils.callMorePlatFrom(activity,2,content,url);
    }
    //打开唯品会客户端
    @JavascriptInterface
    public void toWphApp(String content,String url){
        Utils.callMorePlatFrom(activity,3,content,url);
    }


    //打开优惠加油
    @JavascriptInterface
    public void toOil(String url){
        if (url!=null&&!url.equals("")) {
            activity.startActivity(new Intent(activity, WebPageNavigationActivity.class).putExtra("url",url).putExtra("title","优惠加油"));
        }
    }


    //金刚区点击事件
    @JavascriptInterface
    public void homeImgClick(String labeltype,String url,String name,String needlogin){
        HomePage.JGQAppIcon jgqAppIcon=new HomePage.JGQAppIcon();
        jgqAppIcon.setLabeltype(labeltype);
        jgqAppIcon.setNeedlogin(needlogin);
        jgqAppIcon.setName(name);
        jgqAppIcon.setUrl(url);
        Utils.doJgqClick(activity,jgqAppIcon);
    }

    //渠道授权是否成功  授权position
    @JavascriptInterface
    public void isTbAuthSuccess(String msg) {
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("CloseTbaoAuthDialog"), msg, position);
    }

    //打开CPS商品详情
    @JavascriptInterface
    public void openCpsShop(String type,String id){
        int platform=1;
        switch (type){
            case "pdd":
                platform=1;
                break;
            case "jd":
                platform=2;
                break;
            case "wph":
                platform=3;
                break;
            case "sn":
                platform=5;
                break;
            case "kl":
                platform=6;
                break;
        }
      Utils.getJumpTypeForMorePl(activity,"01",id,platform,true,"","");
    }

    //打开CPS对应平台
    @JavascriptInterface
    public void toCpsApp(String type,String schemaUrl,String url){
        int platform=1;
        switch (type){
            case "pdd":
                platform=1;
                break;
            case "jd":
                platform=2;
                break;
            case "wph":
                platform=3;
                break;
            case "sn":
                platform=5;
                break;
            case "kl":
                platform=6;
                break;
        }
       Utils.callMorePlatFrom(activity,platform,schemaUrl,url);
    }

    //复制文案
    @JavascriptInterface
    public void copyText(String txt){
        Utils.copyText(txt);
        ToastUtil.showImageToast(activity,"复制成功",R.mipmap.toast_img);
    }

}
