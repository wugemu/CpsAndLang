package com.lxkj.dmhw.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.dialog.SaveDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.wxapi.IuiListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.io.File;
import java.util.ArrayList;

/**
 * 分享功能
 */
public class Share {

    @SuppressLint("StaticFieldLeak")
    private static Share instance;
    /**
     * 页面
     */
    private Activity activity;
    /**
     * 不拼接信息
     */
    public static int SPLICE_NO = 0;
    /**
     * 拼接一张信息
     */
    public static int SPLICE_ONE = 1;
    /**
     * 拼接全部信息
     */
    public static int SPLICE_ALL = 2;
    /**
     * 微信好友
     */
    private final int WE_CHAT = 0;
    /**
     * 微信朋友圈
     */
    private final int FRIENDS_CIRCLE = 1;
    /**
     * QQ好友
     */
    private final int QQ = 2;
    /**
     * QQ空间
     */
    private final int QQ_ZONE = 3;
    /**
     * 微信SDK
     */
    private IWXAPI api;
    /**
     * QQSDK
     */
    private Tencent tencent;
    /**
     * 加载中
     */
    private LoadDialog dialog;
    /**
     * 分享参数
     */
    private ShareParams params;

    private int mpositin;
    /**
     * 分享图片信息
     */
    private ArrayList<Uri> uris;

    private ShareDialog shareDialog;


    private int isChange;//替换文字
     //传 0-正常情况 2-改变营销素材 批量存图替换为保存视频
    public static Share getInstance(int isChangeTxt) {
        instance = new Share(isChangeTxt);
        return instance;
    }

    /**
     * 销毁
     */
    public static void destroy() {
        instance = null;
    }

    private Share(int changeTxt) {
        activity = ActivityManagerDefine.getInstance().getActivity();
        if (activity==null){
         return;
        }
        isChange=changeTxt;
        shareDialog = new ShareDialog();
        dialog = new LoadDialog();
        new Handler().postDelayed(dialog::hideDialog, 10000);
    }
    public Share() {
        activity = ActivityManagerDefine.getInstance().getActivity();
        if (activity==null){
           return;
        }
        dialog = new LoadDialog();
        new Handler().postDelayed(dialog::hideDialog, 10000);
    }
    /**
     * 打开分享
     */
    public void initialize(@NonNull ShareParams params,int pos) {
        this.mpositin=pos;
        this.params = params;
        shareDialog.showDialog(params);
    }
    public void shareBitmap(@NonNull ShareParams shareParams,Bitmap bitmap) {
        this.params = shareParams;
        shareDialog.showDialog(params,false,bitmap);
    }
    /**
     * 海报分享
     */
    public void poster(Poster.PosterList posterList, Bitmap QrBitmap,String qrCode) {
        ShareParams params = new ShareParams();
        params.setShareTag(0);
        shareDialog.showDialog(posterList, QrBitmap, qrCode,params);
    }

    /**
     * 微信分享
     */
    public void WeChat(@NonNull ShareParams params, boolean check) {
        // 这里就算分享任务操作成功
        if (params!=null) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
        }
        load();
        this.params = params;
        uris = new ArrayList<>();
        Variable.weChatCheck = false;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(activity, Variable.weChatAppId, false);
        // 将应用的APP_ID注册到微信
        api.registerApp(Variable.weChatAppId);
        if (api.isWXAppInstalled()) {
            switch (params.getImage().size()) {
                case 0:
                    if (TextUtils.isEmpty(params.getUrl())) {
                        //初始化一个 WXTextObject 对象，填写分享的文本内容
                        WXTextObject textObj = new WXTextObject();
                        textObj.text = params.getContent();
                        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = textObj;
                        msg.description = params.getContent();
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("text");
                        req.message = msg;
                        if (check){
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                        }else{
                            req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        }
                        //调用api接口，发送数据到微信
                        api.sendReq(req);
                        load();
                    } else {
                        if (TextUtils.isEmpty(params.getThumbData())) {
                            // 初始化一个WXWebpageObject对象，填写url
                            WXWebpageObject object = new WXWebpageObject();
                            object.webpageUrl = params.getUrl();
                            // 用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
                            WXMediaMessage msg = new WXMediaMessage(object);
                            msg.title = params.getTitle();
                            msg.description = params.getContent();
                            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.sharelogo);
                            msg.thumbData = Utils.bmpToByteArray(bitmap, true);
                            openWeChat(msg, "webpage", check);
                        } else {
                            makeBitmap(params.getThumbData(), check ? 20 : 21, 0);
                        }
                    }
                    break;
                case 1:
                    makeBitmap(params.getImage().get(0), check ? 1 : 2, 0);
                    break;
                default:
                    if (params.getShareInfo().size() == 1) {
                        switch (params.getSplice()) {
                            case 0:
                                for (int i = 0; i < params.getImage().size(); i++) {
                                    makeBitmap(params.getImage().get(i), check ? 3 : 4, i);
                                }
                                break;
                            case 1:
                                for (int i = 0; i < params.getImage().size(); i++) {
                                    makeBitmap(params.getImage().get(i), check ? 5 : 6, i);
                                }
                                break;
                            case 2:
                                for (int i = 0; i < params.getImage().size(); i++) {
                                    makeBitmap(params.getImage().get(i), check ? 7 : 8, i);
                                }
                                break;
                        }
                    } else {
                        switch (params.getSplice()) {
                            case 0:
                                for (int i = 0; i < params.getImage().size(); i++) {
                                    makeBitmap(params.getImage().get(i), check ? 3 : 4, i);
                                }
                                break;
                            default:
                                for (int i = 0; i < params.getImage().size(); i++) {
                                    makeBitmap(params.getImage().get(i), check ? 9 : 10, i);
                                }
                                break;
                        }
                    }
                    break;
            }
        } else {
            ToastUtil.showImageToast(activity,"请安装微信",R.mipmap.toast_error);
            load();
        }
    }
    /**
     * 小程序单张图片微信分享朋友圈
     */
    public void WeChatLittleApp(Context con,@NonNull Bitmap bitmap, boolean check) {
        load();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(activity, Variable.weChatAppId, false);
        // 将应用的APP_ID注册到微信
        api.registerApp(Variable.weChatAppId);
        if (api.isWXAppInstalled()) {
            sendHandlerMsg(handler, LogicActions.getActionsSuccess("Share"), bitmap, 22, 0);
        } else {
            ToastUtil.showImageToast(con,"请安装微信",R.mipmap.toast_error);
            load();
        }
    }
    /**
     * QQ分享
     */
    public void QQ(@NonNull ShareParams params, boolean check) {
        if (params!=null) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
        }else{
          return;
        }
        // 这里就算分享任务操作成功
        load();
        this.params = params;
        uris = new ArrayList<>();
        try{
            tencent = Tencent.createInstance(Variable.QQAppId, activity);
        }catch (Exception e){
            return;
        }
        Variable.QQCheck = false;
        // QQ好友
        if (check) {
            if (Utils.checkApkExist("com.tencent.mobileqq")) {
                switch (params.getImage().size()) {
                    case 0:
                        if (TextUtils.isEmpty(params.getUrl())) {//文字
                            //分享纯文字到qq好友
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "NN俱乐部");
                            intent.putExtra(Intent.EXTRA_TEXT,params.getContent());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity"));
                            activity.startActivity(intent);
                            load();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, params.getTitle());
                            bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, params.getContent());
                            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, params.getUrl());
                            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, TextUtils.isEmpty(params.getThumbData()) ? Variable.shareImagepath : params.getThumbData());
                            bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
                            bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                            tencent.shareToQQ(activity, bundle, new IuiListener());
                            load();
                        }
                        break;
                    case 1:
                        switch (params.getSplice()) {
                            case 0:
                                makeBitmap(params.getImage().get(0), 11, 0);
                                break;
                            default:
                                makeBitmap(params.getImage().get(0), 13, 0);
                                break;
                        }
                        break;
                    default:
                        if (params.getShareInfo().size() == 1) {
                            switch (params.getSplice()) {
                                case 0:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 15, i);
                                    }
                                    break;
                                case 1:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 16, i);
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 13, i);
                                    }
                                    break;
                            }
                        } else {
                            switch (params.getSplice()) {
                                case 0:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 15, i);
                                    }
                                    break;
                                default:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 18, i);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            } else {
                ToastUtil.showImageToast(activity,"请安装QQ客户端",R.mipmap.toast_error);
                load();
            }
        } else {// QQ空间
            switch (params.getImage().size()) {
                case 0:
                    if (TextUtils.isEmpty(params.getUrl())) {
                        if (Utils.checkApkExist("com.qzone")) {
                            //分享纯文字到qq的qq空间
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "NN俱乐部");
                            intent.putExtra(Intent.EXTRA_TEXT, params.getContent());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity"));
                            activity.startActivity(intent);
                        }else{
                            ToastUtil.showImageToast(activity,"请安装QQ空间客户端",R.mipmap.toast_error);
                        }
                        load();
                    } else {
                        if (Utils.checkApkExist("com.tencent.mobileqq")) {
                            new Thread(() -> {
//                                Looper.prepare();
                                Bundle bundle = new Bundle();
                                bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                                bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, params.getTitle());
                                bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, params.getContent());
                                bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, params.getUrl());
                                ArrayList<String> imgUrlList = new ArrayList<>();
                                imgUrlList.add(TextUtils.isEmpty(params.getThumbData()) ? Variable.shareImagepath : params.getThumbData());
                                bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);
                                tencent.shareToQzone(activity, bundle, new IuiListener());
                                load();
//                                Looper.loop();
                            }).start();
                        } else {
                            ToastUtil.showImageToast(activity,"请安装QQ客户端",R.mipmap.toast_error);
                            load();
                        }
                    }
                    break;
                case 1:
                    if (Utils.checkApkExist("com.qzone")) {
                        switch (params.getSplice()) {
                            case 0:
                                makeBitmap(params.getImage().get(0), 12, 0);
                                break;
                            default:
                                makeBitmap(params.getImage().get(0), 14, 0);
                                break;
                        }
                    } else {
                        ToastUtil.showImageToast(activity,"请安装QQ空间客户端",R.mipmap.toast_error);
                        load();
                    }
                    break;
                default:
                    if (Utils.checkApkExist("com.qzone")) {
                        if (params.getShareInfo().size() == 1) {
                            switch (params.getSplice()) {
                                case 0:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 12, i);
                                    }
                                    break;
                                case 1:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 17, i);
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 14, i);
                                    }
                                    break;
                            }
                        } else {
                            switch (params.getSplice()) {
                                case 0:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 12, i);
                                    }
                                    break;
                                default:
                                    for (int i = 0; i < params.getImage().size(); i++) {
                                        makeBitmap(params.getImage().get(i), 19, i);
                                    }
                                    break;
                            }
                        }
                    } else {
                        ToastUtil.showImageToast(activity,"请安装QQ空间客户端",R.mipmap.toast_error);
                        load();
                    }
                    break;
            }
        }
    }

    /**
     * true(微信好友) : false(朋友圈)
     */
    private void openWeChat(WXMediaMessage message, String type, boolean check) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(type);
        req.message = message;
        req.scene = check ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
        load();
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private void load() {
        if (dialog.isDialog()) {
            // 隐藏弹窗
            dialog.hideDialog();
        } else {
            // 启动弹窗
            dialog.showDialog();
        }
    }

    /**
     * 多图分享
     */
    private void excessive(int type, ArrayList<Uri> uris) {
            if (api!=null&&api.getWXAppSupportAPI() >=Variable.weChatVersion &&params!=null&&params.getImage().size() > 1 && type == FRIENDS_CIRCLE) {
                SaveDialog saveDialog = new SaveDialog(activity);
                saveDialog.showDialog();
                load();
        }
        else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

            ComponentName name = null;
            switch (type) {
                case WE_CHAT:// 微信好友
                    try {
                        name = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                        intent.setComponent(name);
                    }catch (Exception e){

                    }
                    break;
                case FRIENDS_CIRCLE:// 微信朋友圈
                    if (params!=null) {
                        try {
                            // 文字
                            intent.putExtra("Kdescription", params.getContent() != null ? params.getContent() : "");
                            // 指定选择微信
                            name = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(name);
                        }catch (Exception e){

                        }
                    }
                    break;
                case QQ:// QQ好友
                    // 指定选择QQ
                    try {
                    name = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                    intent.setComponent(name);
                    }catch (Exception e){

                    }
                    break;
                case QQ_ZONE:// QQ空间
                    // 文字
                    if (params!=null) {
                        try {
                        intent.putExtra(Intent.EXTRA_TEXT, params.getContent() != null ? params.getContent() : "");
                        // 指定选择QQ空间
                        name = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
                        intent.setComponent(name);
                    }catch (Exception e){

                   }
                    }
                    break;
            }
               ComponentName finalName = name;
                new Handler().postDelayed(() -> {
                if (finalName !=null) {
                    activity.startActivity(Intent.createChooser(intent, "Share"));
                }
                load();
            },2000);
        }
    }

    /**
     * 保存图片
     */
    private Uri save(int type, Bitmap resource, int position) {
        Uri uri;
        switch (type) {
            case 1:// 拼接图片
                if (resource!=null) {
                    if (params.getIsPJW()==0) {
                        resource = Utils.jointBitmapNew(activity, resource, params.getShareInfo().get(0));
                    }else{
                        resource = Utils.jointBitmapPJW(activity, resource, params.getShareInfo().get(0));
                    }
                }
                break;
            case 2:// 拼接不同信息图片
                if (params.getShareInfo().get(position)!= null&&resource!=null) {
                    if (params.getIsPJW()==0) {
                        resource = Utils.jointBitmapNew(activity, resource, params.getShareInfo().get(position));
                    }else{
                        resource = Utils.jointBitmapPJW(activity, resource, params.getShareInfo().get(position));
                    }
                }
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          uri = Utils.getContentUri(activity, new File(Utils.saveFile(Variable.sharePath, resource, 100, true)));
        } else {
          uri = Uri.fromFile(new File(Utils.saveFile(Variable.sharePath, resource, 100, true)));
        }
        return uri;
    }

    /**
     * 网络图片加载为Bitmap
     */
    private void makeBitmap(String path, int type, int position) {
        new Thread(() -> {
//            Looper.prepare();
            Glide.with(activity).asBitmap()
                    .load(path)
                    .into(new SimpleTarget<Bitmap>()  {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            Object object = null;
                            switch (type) {
                                case 0:// 批量存图
                                    object = bitmap;
                                    break;
                                case 1:// 微信好友单图分享
                                    object = bitmap;
                                    break;
                                case 2:// 微信朋友圈单图分享
                                    object = bitmap;
                                    break;
                                case 3:// 多图微信好友不拼接分享
                                    object = save(0, bitmap, position);
                                    break;
                                case 4:// 多图微信朋友圈不拼接分享
                                    object = save(0, bitmap, position);
                                    break;
                                case 5:// 多图微信好友拼接一张分享
                                    if (position == 0) {
                                        object = save(1, bitmap, position);
                                    } else {
                                        object = save(0, bitmap, position);
                                    }
                                    break;
                                case 6:// 多图微信朋友圈拼接一张分享
                                    if (position == 0) {
                                        object = save(1, bitmap, position);
                                    } else {
                                        object = save(0, bitmap, position);
                                    }
                                    break;
                                case 7:// 多图微信好友全拼接分享
                                    object = save(1, bitmap, position);
                                    break;
                                case 8:// 多图微信朋友圈全拼接分享
                                    object = save(1, bitmap, position);
                                    break;
                                case 9:// 多图微信好友全不同信息拼接分享
                                    object = save(2, bitmap, position);
                                    break;
                                case 10:// 多图微信朋友圈全不同信息拼接分享
                                    object = save(2, bitmap, position);
                                    break;
                                case 11:// 单图QQ好友不拼接分享
                                    object = bitmap;
                                    break;
                                case 12:// 单图、多图QQ空间不拼接分享
                                    object = save(0, bitmap, position);
                                    break;
                                case 13:// 多图QQ好友全拼接分享
                                    object = save(1, bitmap, position);
                                    break;
                                case 14:// 单图、多图QQ空间全拼接分享
                                    object = save(1, bitmap, position);
                                    break;
                                case 15:// 多图QQ好友不拼接分享
                                    object = save(0, bitmap, position);
                                    break;
                                case 16:// 多图QQ好友拼接一张分享
                                    if (position == 0) {
                                        object = save(1, bitmap, position);
                                    } else {
                                        object = save(0, bitmap, position);
                                    }
                                    break;
                                case 17:// 多图QQ空间拼接一张分享
                                    if (position == 0) {
                                        object = save(1, bitmap, position);
                                    } else {
                                        object = save(0, bitmap, position);
                                    }
                                    break;
                                case 18:// 多图QQ好友全不同信息拼接分享
                                    object = save(2, bitmap, position);
                                    break;
                                case 19:// 多图QQ空间全不同信息拼接分享
                                    object = save(2, bitmap, position);
                                    break;
                                case 20:// 自定义卡片分享(好友)
                                    object = bitmap;
                                    break;
                                case 21:// 自定义卡片分享(朋友圈)
                                    object = bitmap;
                                    break;
                            }
                            sendHandlerMsg(handler, LogicActions.getActionsSuccess("Share"), object, type, position);
                        }
                    });
//    Looper.loop();
}).start();
        }

/**
 * 数据回调UI
 */
private void sendHandlerMsg(Handler handler, int what, Object object, int type, int position) {
        Message message = new Message();
        message.what = what;
        message.obj = object;
        message.arg1 = type;
        message.arg2 = position;
        handler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LogicActions.ShareSuccess) {
                if (msg.obj != null) {
                    WXImageObject object;
                    WXWebpageObject wxWebpageObject;
                    WXMediaMessage message;
                    Bitmap thumbBmp;
                    Bitmap resource = null;
                    switch (msg.arg1) {
                        case 0:// 批量存图
                           String  tempPath= "";
                            if(params.getIssave()==1){
                                tempPath= Variable.imagesPath;
                            }else{
                                tempPath= Variable.sharePath;
                            }
                            if (params.getShareInfo().size() == 1) {
                                switch (params.getSplice()) {
                                    case 0:
                                        Utils.saveFile(tempPath, (Bitmap) msg.obj, 100, true);
                                        break;
                                    case 1:
                                        if (msg.arg2 == 0) {
                                            if (params.getIsPJW()==0) {
                                                resource = Utils.jointBitmapNew(activity, (Bitmap) msg.obj, params.getShareInfo().get(0));
                                            }else{
                                                resource = Utils.jointBitmapPJW(activity, (Bitmap) msg.obj, params.getShareInfo().get(0));
                                            }
                                            Utils.saveFile(tempPath, resource, 100, true);
                                        } else {
                                            Utils.saveFile(tempPath, (Bitmap) msg.obj, 100, true);
                                        }
                                        break;
                                    case 2:
                                        if (params.getIsPJW()==0) {
                                            resource = Utils.jointBitmapNew(activity, (Bitmap) msg.obj, params.getShareInfo().get(0));
                                        }else{
                                            resource = Utils.jointBitmapPJW(activity, (Bitmap) msg.obj, params.getShareInfo().get(0));
                                        }
                                        Utils.saveFile(tempPath, resource, 100, true);
                                        break;
                                }
                            } else {
                                if (params.getShareInfo().get(msg.arg2) != null) {
                                    if (params.getIsPJW()==0) {
                                        resource = Utils.jointBitmapNew(activity, (Bitmap) msg.obj, params.getShareInfo().get(msg.arg2));
                                    }else{
                                        resource = Utils.jointBitmapPJW(activity, (Bitmap) msg.obj, params.getShareInfo().get(msg.arg2));
                                    }
                                }
                                Utils.saveFile(tempPath, resource, 100, true);
                            }
                            if (msg.arg2 == params.getImage().size() - 1) {
                                load();
                                ToastUtil.showImageToast(activity,"图片已保存至相册",R.mipmap.toast_img);
                            }
                            break;
                        case 1:// 微信好友单图分享
                            resource = (Bitmap) msg.obj;
                            if (params.getSplice() != 0) {
                                if (params.getShareInfo().get(0) != null) {
                                    if (params.getIsPJW()==0) {
                                        resource = Utils.jointBitmapNew(activity, resource, params.getShareInfo().get(0));
                                    }else{
                                        resource = Utils.jointBitmapPJW(activity, resource, params.getShareInfo().get(0));
                                    }
                                }
                            }
                            // 初始化 WXImageObject 和 WXMediaMessage 对象
                            object = new WXImageObject(resource);
                            message = new WXMediaMessage();
                            message.mediaObject = object;
                            thumbBmp = Utils.zoomImg(resource, 120, 120);
                            message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                            openWeChat(message, "img", true);
                            break;
                        case 2:// 微信朋友圈单图分享
                            resource = (Bitmap) msg.obj;
                            if (params.getSplice() != 0) {
                                if (params.getShareInfo().get(0) != null) {
                                    if (params.getIsPJW()==0) {
                                        resource = Utils.jointBitmapNew(activity, resource, params.getShareInfo().get(0));
                                    }else{
                                        resource = Utils.jointBitmapPJW(activity, resource, params.getShareInfo().get(0));
                                    }
                                }
                            }
                            // 初始化 WXImageObject 和 WXMediaMessage 对象
                            object = new WXImageObject(resource);
                            message = new WXMediaMessage();
                            message.mediaObject = object;
                            thumbBmp = Utils.zoomImg(resource, 120, 120);
                            message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                            openWeChat(message, "img", false);
                            break;
                        case 3:// 多图微信好友不拼接分享
                        case 5:// 多图微信好友拼接一张分享
                        case 7:// 多图微信好友全拼接分享
                        case 9:// 多图微信好友全不同信息拼接分享
                            uris.add((Uri) msg.obj);
                            if (msg.arg2 == params.getImage().size() - 1) {
                                excessive(WE_CHAT, uris);
                            }
                            break;
                        case 4:// 多图微信朋友圈不拼接分享
                        case 6:// 多图微信朋友圈拼接一张分享
                        case 8:// 多图微信朋友圈全拼接分享
                        case 10:// 多图微信朋友圈全不同信息拼接分享
                            uris.add((Uri) msg.obj);
                            if (msg.arg2 == params.getImage().size() - 1) {
                                excessive(FRIENDS_CIRCLE, uris);
                            }
                            break;
                        case 11:// 单图QQ好友不拼接分享
                            Bundle bundle = new Bundle();
                            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Utils.saveFile(Variable.sharePath, (Bitmap) msg.obj, 100, true));
                            bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
                            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                            bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                            tencent.shareToQQ(activity, bundle, new IuiListener());
                            load();
                            break;
                        case 12:// 单图、多图QQ空间不拼接分享
                        case 14:// 单图、多图QQ空间全拼接分享
                        case 17:// 多图QQ空间拼接一张分享
                        case 19:// 多图QQ空间全不同信息拼接分享
                            uris.add((Uri) msg.obj);
                            if (msg.arg2 == params.getImage().size() - 1) {
                                excessive(QQ_ZONE, uris);
                            }
                            break;
                        case 13:// 多图QQ好友全拼接分享
                        case 15:// 多图QQ好友不拼接分享
                        case 16:// 多图QQ好友拼接一张分享
                        case 18:// 多图QQ好友全不同信息拼接分享
                            uris.add((Uri) msg.obj);
                            if (msg.arg2 == params.getImage().size() - 1) {
                                excessive(QQ, uris);
                            }
                            break;
                        case 20:// 自定义卡片分享(好友)
                            // 初始化一个WXWebpageObject对象，填写url
                            wxWebpageObject = new WXWebpageObject();
                            wxWebpageObject.webpageUrl = params.getUrl();
                            // 用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
                            message = new WXMediaMessage(wxWebpageObject);
                            message.title = params.getTitle();
                            message.description = params.getContent();
                            message.thumbData = Utils.bmpToByteArray((Bitmap) msg.obj, true);
                            openWeChat(message, "webpage", true);
                            break;
                        case 21:// 自定义卡片分享(朋友圈)
                            // 初始化一个WXWebpageObject对象，填写url
                            wxWebpageObject = new WXWebpageObject();
                            wxWebpageObject.webpageUrl = params.getUrl();
                            // 用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
                            message = new WXMediaMessage(wxWebpageObject);
                            message.title = params.getTitle();
                            message.description = params.getContent();
                            message.thumbData = Utils.bmpToByteArray((Bitmap) msg.obj, true);
                            openWeChat(message, "webpage", false);
                            break;
                        case 22:// 小程序单图分享
                            object = new WXImageObject((Bitmap) msg.obj);
                            message = new WXMediaMessage();
                            message.mediaObject = object;
                            thumbBmp = Utils.zoomImg((Bitmap) msg.obj, 192, 108);
                            message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                            openWeChat(message, "img", false);
                            break;
                    }
                } else {
                    ToastUtil.showImageToast(activity,"分享失败",R.mipmap.toast_error);
                    load();
                }
            }
        }

    };

    /**
     * 分享弹窗
     */
    private class ShareDialog extends SimpleDialog<String> {

        private ShareParams params;
        private Poster.PosterList posterList;
        private Bitmap QrBitmap = null;
        private boolean isposter=true;
        private String Qrcode;
        private LinearLayout dialogAppShareSaveLayout;
        private TextView save_image_vedio_txt;

        /**
         * 初始化
         */
        ShareDialog() {
            super(activity, R.layout.dialog_app_share, "", true, false);
        }

        @Override
        protected void convert(ViewHolder helper) {
            dialogAppShareSaveLayout = helper.getView(R.id.dialog_app_share_save_layout);
            save_image_vedio_txt= helper.getView(R.id.save_image_vedio_txt);
            if (Variable.isVisible==0){
                helper.setVisible(R.id.dialog_app_share_save_layout,false);
            }else{
                helper.setVisible(R.id.dialog_app_share_save_layout,true);
            }
            if (isChange==2){
                save_image_vedio_txt.setText("保存视频");
            }else{
                save_image_vedio_txt.setText("批量存图");
            }
            helper.setOnClickListener(R.id.dialog_app_share_save, this);
            helper.setOnClickListener(R.id.dialog_app_share_wechat, this);
            helper.setOnClickListener(R.id.dialog_app_share_wechat_friends, this);
            helper.setOnClickListener(R.id.dialog_app_share_qq, this);
            helper.setOnClickListener(R.id.dialog_app_share_qq_zone, this);
            helper.setOnClickListener(R.id.dialog_app_share_clean, this);
            helper.setOnClickListener(R.id.dialog_app_share_default, this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_app_share_save:// 0-批量存图，2-营销素材保存视频
                    load();
                    params.setIssave(1);//改变存储路径到images
                    if (mpositin==0)//营销素材批量存图强制mpositin=0
                    {
                        for (int i = 0; i < params.getImage().size(); i++) {
                            final int ii=i;
                            new Thread(() -> {
//                                Looper.prepare();
                                Bitmap bitmap = ImageLoader.getInstance().loadImageSync(params.getImage().get(ii));
                                Utils.saveFile(Variable.imagesPath, bitmap, 100, true);
//                                Looper.loop();
                            }).start();

                        }
                        load();
                        ToastUtil.showImageToast(context,"图片已保存至相册",R.mipmap.toast_img);
                    }else if(mpositin==2){//营销素材保存视频强制mpositin=2
                        load();
                        savevideo(activity,params.getVediourl());
                    }else{
                        for (int i = 0; i < params.getImage().size(); i++) {
                            makeBitmap(params.getImage().get(i), 0, i);
                        }
                    }

                    break;
                case R.id.dialog_app_share_wechat:// 微信好友
                    if (QrBitmap != null) {
                        if (params!=null) {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
                        }
                        if (api.isWXAppInstalled()) {
                            load();
                            new Thread(() -> {
//                                Looper.prepare();
                                if (isposter) {
                                    if (posterList==null) {
                                        load();
                                        return;
                                    }
                                    Bitmap bitmap= Utils.posterBitmapNew(context,QrBitmap,posterList,ImageLoader.getInstance().loadImageSync(posterList.getPosterurl()),Qrcode);
                                    // 初始化 WXImageObject 和 WXMediaMessage 对象
                                    WXImageObject object = new WXImageObject(bitmap);
                                    WXMediaMessage message = new WXMediaMessage();
                                    message.mediaObject = object;
                                    Bitmap thumbBmp = Utils.zoomImg(bitmap, 120, 120);
                                    message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                                    openWeChat(message, "img", true);
                                }else{
                                    WXImageObject object = new WXImageObject(QrBitmap);
                                    WXMediaMessage message = new WXMediaMessage();
                                    message.mediaObject = object;
                                    Bitmap thumbBmp = Utils.zoomImg(QrBitmap, 120, 120);
                                    message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                                    openWeChat(message, "img", true);
                                }
//                                Looper.loop();
                            }).start();
                        } else {
                            toast("请先安装微信");
                        }
                    } else {
                        WeChat(params, true);
                    }
                    break;
                case R.id.dialog_app_share_wechat_friends:// 微信朋友圈
                    if (QrBitmap != null) {
                        if (params!=null) {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
                        }
                        if (api.isWXAppInstalled()) {
                            load();
                            new Thread(() -> {
                                if (isposter) {
//                                    Looper.prepare();
                                    Bitmap bitmap= Utils.posterBitmapNew(context,QrBitmap,posterList,ImageLoader.getInstance().loadImageSync(posterList.getPosterurl()),Qrcode);
                                    // 初始化 WXImageObject 和 WXMediaMessage 对象
                                    WXImageObject object = new WXImageObject(bitmap);
                                    WXMediaMessage message = new WXMediaMessage();
                                    message.mediaObject = object;
                                    Bitmap thumbBmp = Utils.zoomImg(bitmap, 120, 120);
                                    message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                                    openWeChat(message, "img", false);
                                }else{
                                    // 初始化 WXImageObject 和 WXMediaMessage 对象
                                    WXImageObject object = new WXImageObject(QrBitmap);
                                    WXMediaMessage message = new WXMediaMessage();
                                    message.mediaObject = object;
                                    Bitmap thumbBmp = Utils.zoomImg(QrBitmap, 120, 120);
                                    message.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                                    openWeChat(message, "img", false);
                                }
//                                Looper.loop();
                            }).start();
                        } else {
                            toast("请先安装微信");
                        }
                    } else {
                        WeChat(params, false);
                    }
                    break;
                case R.id.dialog_app_share_qq:// QQ
                    if (QrBitmap != null) {
                        if (params!=null) {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
                        }
                        if (Utils.checkApkExist("com.tencent.mobileqq")) {
                            load();
                            new Thread(() -> {
                                load();
//                                Looper.prepare();
                                if (isposter) {
                                    Bitmap bitmap= Utils.posterBitmapNew(context,QrBitmap,posterList,ImageLoader.getInstance().loadImageSync(posterList.getPosterurl()),Qrcode);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Utils.saveFile(Variable.sharePath, bitmap, 100, true));
                                    bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
                                    bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                                    bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                                    tencent.shareToQQ(activity, bundle, new IuiListener());
                                }else{
                                    Bundle bundle = new Bundle();
                                    bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Utils.saveFile(Variable.sharePath, QrBitmap, 100, true));
                                    bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
                                    bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                                    bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                                    tencent.shareToQQ(activity, bundle, new IuiListener());
                                }
//                               Looper.loop();//增加
                            }).start();
                        } else {
                            toast("请先安装QQ客户端");
                        }
                    } else {
                        QQ(params, true);
                    }
                    break;
                case R.id.dialog_app_share_qq_zone:// QQ空间
                    if (QrBitmap != null) {
                        if (params!=null) {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, params.getShareTag());
                        }
                        if (Utils.checkApkExist("com.qzone")) {
                            load();
                            new Thread(() -> {
//                                Looper.prepare();
                                if (isposter) {
                                    Bitmap bitmap= Utils.posterBitmapNew(context,QrBitmap,posterList,ImageLoader.getInstance().loadImageSync(posterList.getPosterurl()),Qrcode);
                                    uris = new ArrayList<>();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        uris.add(Utils.getContentUri(activity, new File(Utils.saveFile(Variable.sharePath, bitmap, 100, true))));
                                    } else {
                                        uris.add(Uri.fromFile(new File(Utils.saveFile(Variable.sharePath, bitmap, 100, true))));
                                    }
                                    excessive(QQ_ZONE, uris);
                                }else{
                                    uris = new ArrayList<>();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        Uri uri=Utils.getContentUri(activity, new File(Utils.saveFile(Variable.sharePath, QrBitmap, 100, true)));
                                        if(uri!=null){
                                        uris.add(uri);
                                        }
                                    } else {
                                        Uri uri = Uri.fromFile(new File(Utils.saveFile(Variable.sharePath, QrBitmap, 100, true)));
                                        if(uri!=null){
                                            uris.add(uri);
                                        }
                                    }
                                    excessive(QQ_ZONE, uris);
                                }
//                                Looper.loop();
                            }).start();
                        } else {
                            QQ(params, true);
                            ToastUtil.showImageToast(context, "请先安装QQ空间客户端", R.mipmap.toast_error);
                        }
                    } else {
                        QQ(params, false);
                    }
                    break;
                case R.id.dialog_app_share_clean:
                    hideDialog();
                    break;
                case R.id.dialog_app_share_default:
                    hideDialog();
                    break;
            }
            if(shareDialog!=null&&shareDialog.isShowing()){
              hideDialog();
            }
        }

        /**
         * 显示弹窗
         */
        public void showDialog(ShareParams params) {
            this.params = params;
            if (params.getImage().size() == 0) {
                dialogAppShareSaveLayout.setVisibility(View.GONE);
            }
            showDialog();
        }

        /**
         * 显示弹窗
         */
        public void showDialog(Poster.PosterList posterList, Bitmap QrBitmap) {
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(activity, Variable.weChatAppId, false);
            // 将应用的APP_ID注册到微信
            api.registerApp(Variable.weChatAppId);
            tencent = Tencent.createInstance(Variable.QQAppId, activity);
            Variable.QQCheck = false;
            Variable.weChatCheck = false;
            this.posterList = posterList;
            this.QrBitmap = QrBitmap;
            showDialog();
        }
        /**
         * 显示弹窗 分享海报 2.6.0
         */
        public void showDialog(Poster.PosterList posterList, Bitmap QrBitmap,String qrcode,ShareParams shareParams) {
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(activity, Variable.weChatAppId, false);
            // 将应用的APP_ID注册到微信
            api.registerApp(Variable.weChatAppId);
            tencent = Tencent.createInstance(Variable.QQAppId, activity);
            Variable.QQCheck = false;
            Variable.weChatCheck = false;
            this.Qrcode=qrcode;
            this.posterList = posterList;
            this.QrBitmap = QrBitmap;
            this.params=shareParams;
            showDialog();
        }

        /**
         * 分享bitmap
         */
        public void showDialog(ShareParams params,boolean isposter, Bitmap bitmap) {
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(activity, Variable.weChatAppId, false);
            // 将应用的APP_ID注册到微信
            api.registerApp(Variable.weChatAppId);
            tencent = Tencent.createInstance(Variable.QQAppId, activity);
            Variable.QQCheck = false;
            Variable.weChatCheck = false;
            this.isposter=isposter;
            this.QrBitmap = bitmap;
            dialogAppShareSaveLayout.setVisibility(View.GONE);
            showDialog();
        }

    }

    /**
     * 加载中
     */
    private class LoadDialog extends SimpleDialog<String> {
        private ImageView loadingImg;
        /**
         * 初始化
         */
        LoadDialog() {
            super(activity, R.layout.dialog_progress, "", false, true);
        }

        @Override
        protected void convert(ViewHolder helper) {
            loadingImg = helper.getView(R.id.gifimage);
            helper.setText(R.id.loadingtxt,"请稍候...");
            loadingImg.setBackgroundResource(R.drawable.loading);
            AnimationDrawable animaition = (AnimationDrawable)loadingImg.getBackground();
            animaition.start();
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void savevideo(Activity activity,String path) {
        showProgressDialog(activity,"提示", "视频保存中...");
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
                ToastUtil.showImageToast(activity,"下载出现错误",R.mipmap.toast_error);
                hideProgressDialog();
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        }).start();
    }

    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    private void showProgressDialog(Activity activity,String title, String message) {
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
}
