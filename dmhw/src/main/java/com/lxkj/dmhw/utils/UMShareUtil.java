package com.lxkj.dmhw.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.andlang.util.FileUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.NativeHelper;
import com.example.test.andlang.util.StorageUtils;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.logic.Constants;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2016/3/12.
 */
public class UMShareUtil {
    private static String dataId;
    private static String type;

    //多图分享
    private ArrayList<Uri> shareImgPaths;
    private int shareImgCount;

    private UMShareUtil() {

    }

    private static UMShareUtil mInstance;
    private WeakReference<Activity> mActivityReference;
    private Map<String, Object> params;

    //    private UMSocialFinishListener listener;
    public static UMShareUtil getInstance() {
            if (mInstance == null) {
                mInstance = new UMShareUtil();
        }
        return mInstance;
    }


    public interface UMSocialFinishListener {
        void work();
    }


    /**
     * 分享到单个平台
     *
     * @param activity 上下文
     * @param umWeb    分享网址链接
     * @param listener 分享回调监听
     * @param platform 分享的平台（单数）
     */
    public void umengSharePlatform(Activity activity, UMWeb umWeb, UMSocialFinishListener listener, SHARE_MEDIA platform) {
        mActivityReference = new WeakReference<Activity>(activity);
        if (platform == SHARE_MEDIA.SINA) {
            new ShareAction(activity)
                    .withText(umWeb.getTitle())
                    .withMedia(umWeb.getThumbImage())
                    .setPlatform(SHARE_MEDIA.SINA)
                    .setCallback(umShareListener)
                    .share();
        } else {
            new ShareAction(activity)
                    .withText(umWeb.getTitle())
                    .withMedia(umWeb)
                    .setPlatform(platform)
                    .setCallback(umShareListener)
                    .share();
        }
    }

    /**
     * 分享到小程序
     *
     * @param activity 上下文
     * @param platform 分享的平台（单数）
     * 1下单返现 2免费领取
     */
    public void umengShareMin(Activity activity, ShareBean shareBean, SHARE_MEDIA platform, int helpTradeType) {
        mActivityReference = new WeakReference<>(activity);
        if(BBCUtil.isEmpty(shareBean.getMinAppId())) {
            //接口返回为空时 设置默认小程序id
            shareBean.setMinAppId("gh_566bfcb97796");//接口返回不要写死 小程序appID gh_4f81951fb618
        }
        if (BBCUtil.isEmpty(shareBean.getMinPath())){
            shareBean.setMinPath(shareBean.getLinkUrl());//小程序 路由
        }


        //友盟小程序分享方法
//        UMMin umMin = new UMMin(shareBean.getLinkUrl());
//        umMin.setTitle(shareBean.getTitle());
//        umMin.setDescription(shareBean.getDes());
//        umMin.setPath(shareBean.getMinPath());
//        umMin.setUserName(shareBean.getMinAppId());
//        if (BBCUtil.isEmpty(shareBean.getImgUrl())) {
//            umMin.setThumb(new UMImage(activity, R.mipmap.push_small));
//            new ShareAction(activity)
//                    .withMedia(umMin)
//                    .setPlatform(platform)
//                    .setCallback(umShareListener).share();
//        } else {
//            String imgUrl=shareBean.getImgUrl();
//            umMin.setThumb(new UMImage(activity, imgUrl));
//            new ShareAction(activity)
//                    .withMedia(umMin)
//                    .setPlatform(platform)
//                    .setCallback(umShareListener).share();
//        }

        //微信sdk小程序分享方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                IWXAPI mShareAPI = WXAPIFactory.createWXAPI(activity, Constants.WXAPPID);
                WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                miniProgramObj.webpageUrl = shareBean.getLinkUrl(); // 兼容低版本的网页链接
                miniProgramObj.miniprogramType = 0;// 正式版:0，测试版:1，体验版:2
                miniProgramObj.userName = shareBean.getMinAppId();     // 小程序原始id
                miniProgramObj.path = shareBean.getMinPath();            //小程序页面路径
                final WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                msg.title = shareBean.getTitle();                    // 小程序消息title
                msg.description = shareBean.getDes();               // 小程序消息desc
                msg.thumbData = getThumb(activity,shareBean.getImgUrl()); // 小程序消息封面图片，小于128k(必传)
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                mShareAPI.sendReq(req);
            }
        }).start();
    }

    private static byte[] getThumb(Context context,String imgUrl){
        byte[] thumb;
        if (BBCUtil.isEmpty(imgUrl)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.push_small);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            thumb = BBCUtil.bmpToByteArray(sendBitmap, true);
            bitmap.recycle();
        }else {
            thumb=BBCUtil.getByteByUrl(imgUrl);
        }
        return thumb;
    }


    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void umengShareSina(Activity activity, ShareBean shareBean) {
        mActivityReference = new WeakReference<Activity>(activity);
        String image = shareBean.getImgUrl();
        new ShareAction(activity)
                .withText(shareBean.getTitle() + shareBean.getLinkUrl())
                .withMedia(new UMImage(activity, image))
                .setPlatform(SHARE_MEDIA.SINA)
                .setCallback(umShareListener)
                .share();
    }
//    public void umengShareSina2(Activity activity, CloudStoreInfo info){
//        this.activity=activity;
//
//        new ShareAction(activity)
//                .withText(info.getStoreName()+info.getShareLink())
//                .withMedia(new UMImage(activity,info.getStoreHeadUrl()))
//                .setPlatform(SHARE_MEDIA.SINA)
//                .setCallback(umShareListener)
//                .share();
//    }

//    public void umengShareSina3(Activity activity, GoodsShareLink shareLink){
//        this.activity=activity;
//        new ShareAction(activity)
//                .withText(shareLink.getTitle()+shareLink.getShareLink())
//                .withMedia(new UMImage(activity,shareLink.getImgUrl()))
//                .setPlatform(SHARE_MEDIA.SINA)
//                .setCallback(umShareListener)
//                .share();
//    }

//    /**
//     * 分享到单个平台
//     *
//     * @param activity 上下文
//     * @param params   分享内容集合
//     * @param listener 分享回调监听
//     * @param platform 分享的平台（单数）
//     * @param t 分享内容权重 t=标题，c=内容
//     */
//    public void umengSharePlatform(Activity activity, Map<String, Object> params, UMSocialFinishListener listener, SHARE_MEDIA platform, String t) {
//        try {
//            this.activity = activity;
//            this.listener = listener;
//            if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform)) {
//                if("t".equals(t)){
//                    new ShareAction(activity).setPlatform(platform).setCallback(umShareListener)
//                            .withTitle((String) params.get("title"))
//                            .withTargetUrl((String) params.get("url"))
//                            .withText((String) params.get("title"))
//                            .withMedia((UMImage) params.get("image"))
//                            .share();
//                }else{
//                    new ShareAction(activity).setPlatform(platform).setCallback(umShareListener)
//                            .withTitle((String) params.get("content"))
//                            .withTargetUrl((String) params.get("url"))
//                            .withText((String) params.get("content"))
//                            .withMedia((UMImage) params.get("image"))
//                            .share();
//                }
//
//            } else if (SHARE_MEDIA.SINA.equals(platform)) {
//                String content = (String) params.get("content");
//                String title = (String) params.get("title");
//
//                content = content.length() + title.length() > 120 ? content.substring(0, 120 - title.length()) : content;
//                new ShareAction(activity).setPlatform(platform).setCallback(umShareListener)
//                        .withTitle((String) params.get("title"))
//                        .withTargetUrl((String) params.get("url"))
//                        .withText(content)
//                        .withMedia((UMImage) params.get("image"))
//                        .share();
//
//            } else {
//                new ShareAction(activity).setPlatform(platform).setCallback(umShareListener)
//                        .withTitle((String) params.get("title"))
//                        .withTargetUrl((String) params.get("url"))
//                        .withText((String) params.get("content"))
//                        .withMedia((UMImage) params.get("image"))
//                        .share();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//

    /**
     * 分享
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

        }
    };

    public static void setShareParams(String mydataId,String mytype){
        dataId=mydataId;
        type=mytype;
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Activity activity = mActivityReference.get();
            if(activity!=null) {
                ToastUtil.show(activity,"分享成功");
                //分享成功 增加活跃值接口
                if(BBCUtil.isEmpty(dataId)||BBCUtil.isEmpty(type)){
                    return;
                }
            }
        }


        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Activity activity = mActivityReference.get();
            if(activity!=null) {
                ToastUtil.show(activity,"分享失败");
            }
            LogUtil.e("0.0","分享失败："+t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Activity activity = mActivityReference.get();
            if(activity!=null) {
                ToastUtil.show(activity,"分享取消");
            }
        }
    };

    //纯图片分享
    public void umengShareImage(Activity activity, SHARE_MEDIA media, Bitmap bitmap,boolean isRecycle) {
        if(media!=SHARE_MEDIA.WEIXIN) {
            mActivityReference = new WeakReference<Activity>(activity);
            UMImage image = new UMImage(activity, bitmap);
            new ShareAction(activity).setPlatform(media).setCallback(umShareListener).withMedia(image).share();
        }else {
            //微信大图分享 走微信自己的sdk
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IWXAPI mShareAPI = WXAPIFactory.createWXAPI(activity, Constants.WXAPPID);
                        //初始化 WXImageObject 和 WXMediaMessage 对象
                        WXImageObject imgObj = new WXImageObject(bitmap);
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = imgObj;

                        Bitmap thumbBmp = null;
                        try {
                            //设置缩略图
                            thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 205, true);
                        } catch (Exception e) {
                            Bitmap defaultBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.push_small);
                            thumbBmp = Bitmap.createScaledBitmap(defaultBitmap, 150, 150, true);
                            defaultBitmap.recycle();
                        }
                        if(isRecycle) {
                            bitmap.recycle();//不能回收 回收后glide缓存会被释放
                        }
                        msg.thumbData = BBCUtil.bmpToByteArray(thumbBmp, true);
                        //构造一个Req
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("img");
                        req.message = msg;
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                        //                req.userOpenId = getOpenId();
                        //调用api接口，发送数据到微信
                        mShareAPI.sendReq(req);
                    }catch (Exception e){

                    }
                }
            }).start();
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //多图分享下载成功回掉
                    // 构建文件 Uri 列表
                    shareImgPaths.add((Uri) msg.obj);
                    if(shareImgPaths.size()==shareImgCount){
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        intent.setType("image/*");
                        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");

                        intent.setComponent(comp);
                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, shareImgPaths);
                        Activity activity = mActivityReference.get();
                        if(activity!=null) {
                            activity.startActivity(intent);
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /**
     * 微信分享（多图片）
     */
    public void shareWeChatByImgList(Activity activity,List<String> img_path_list) {
        if (!BBCUtil.isWXAppInstalledAndSupported(activity)) {
            ToastUtil.show(activity, "未检测到微信客户端，请安装");
            return;
        }
        if(shareImgPaths==null){
            shareImgPaths=new ArrayList<>();
        }else {
            shareImgPaths.clear();
        }
        mActivityReference = new WeakReference<Activity>(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (img_path_list == null || img_path_list.size() <= 0) {
                        ToastUtil.show(activity,"分享的图片获取失败");
                        return;
                    }
                    shareImgCount=img_path_list.size();
                    for (String imgUrl:img_path_list) {
                        if (!BBCUtil.isEmpty(imgUrl) && imgUrl.startsWith("http")) {
                            BBCHttpUtil.download9PictureResultFile(activity, imgUrl,handler);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public boolean isWXAppInstalledAndSupported(Context context) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(Constants.WXAPPID);
        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled();
        return sIsWXAppInstalledAndSupported;
    }

    /**
     * 分享9图到朋友圈
     *
     * @param context
     * @param Kdescription 9图上边输入框中的文案
     * @param paths        本地图片的路径
     */
    public void share9PicsToWXCircle(Context context, String Kdescription,String shareLink, List<String> paths) {
        if (!isWXAppInstalledAndSupported(context)) {
            ToastUtil.show(context,"您没有安装微信");
            return;
        }
        if(!BBCUtil.isEmpty(Kdescription)&&(Kdescription+shareLink).length()>=200){
            int length=(Kdescription+shareLink).length()-200;
            Kdescription=Kdescription.substring(0,Kdescription.length()-length-3)+"...";
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        intent.setAction("android.intent.action.SEND_MULTIPLE");

        ArrayList<Uri> imageList = new ArrayList<Uri>();
        for (int i=0;i<paths.size();i++) {
            File f = new File(paths.get(i));
            if (f.exists()) {
                Uri uri=FileUtil.file2Uri(context,f);
                imageList.add(uri);
            }
        }
        if(imageList.size() == 0){
            ToastUtil.show(context,"图片不存在");
            return;
        }
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
        intent.putExtra("Kdescription", Kdescription+shareLink); //微信分享页面，图片上边的描述
        context.startActivity(intent);
    }
}
