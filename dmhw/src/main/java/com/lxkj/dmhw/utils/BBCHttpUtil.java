package com.lxkj.dmhw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.example.test.andlang.andlangutil.LangImageUpInterface;
import com.example.test.andlang.cockroach.CrashLog;
import com.example.test.andlang.http.CacheUtil;
import com.example.test.andlang.http.EncryptUtil;
import com.example.test.andlang.http.HttpCallback;
import com.example.test.andlang.http.HttpU;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.MMKVUtil;
import com.example.test.andlang.util.PicSelUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.VersionUtil;
import com.google.gson.Gson;
import com.lxkj.dmhw.logic.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BBCHttpUtil {
    public static OkHttpClient mOkHttpClient;
    public static Gson gson=new Gson();
    public static void postHttp(final Activity context, final String url, Map<String, Object> params, final Type type, final LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        HashMap<String,Object> copyMap=new HashMap<>();
        copyMap.putAll(params);

        params.put("version", VersionUtil.getVersionCode(context));
        params.put("clientType", "3");
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        params.put("mobileModel", SystemUtil.getSystemModel());//手机型号
        params.put("systemVersion", SystemUtil.getSystemVersion());//手机Android系统版本
        params.put("imei",BBCUtil.getIMEI(context));//手机设备号
        params.put("appType",Constants.appType);//app类型
        HttpU.getInstance().post(context, Constants.HOST+url, params, context, new HttpCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                if(filterLoadingUrl(url)) {
                    //排除部分链接不取消加载
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

            @Override
            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
                try {
                    //网络正常
                    if(BaseLangUtil.isEmpty(response)){
                        if(listener!=null) {
                            listener.empty();
                        }
                    } else{
                        if (JsonParseUtil.isSuccessResponse(response)) {
                            String result=JsonParseUtil.getStringValue(response,"result");
                            if(listener!=null) {
                                if(type==String.class){
                                    listener.success(result);
                                }else {
                                    listener.success(gson.fromJson(result, type));
                                }
                            }
                        }else {
                            listener.fail();
                        }
                    }
                }catch (Exception e){
                    if(!BBCUtil.isEmpty(e.getMessage())) {
                        LogUtil.e("0.0", e.getMessage());
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e,int code,boolean haveLocalCache) {
                super.onError(request, e,code,haveLocalCache);

                //暂时不显示 网络异常相关
                if (code == -1) {
                    //网络异常
                    //重新加载
                    if(filterNetUrl(url)) {
                    }else if(filterNetNoToastUrl(url)){
                        //过滤掉不需要toast的接口
                        ToastUtil.show(context,"网络开小差啦～");
                    }
                } else {
                    //小素开小差了
                    //排除部分链接不展示重新加载页面
                    if(filterCodeUrl(url)) {
                    }
                }

                if(listener!=null){
                    listener.error();
                }
            }

            @Override
            public void errorCode(int code,String response) {
                super.errorCode(code,response);
                hanlderErrorCode(code,context,response,url,"",copyMap);
            }
        });
    }

    public static void postDesHttp(final Activity context, final String url, Map<String, Object> params, final Type type,final String tag, final LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        HashMap<String,Object> copyMap=new HashMap<>();
        copyMap.putAll(params);

        params.put("timestamp",System.currentTimeMillis());
        params.put("version", VersionUtil.getVersionCode(context));
        params.put("clientType", "3");
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        params.put("mobileModel", SystemUtil.getSystemModel());//手机型号
        params.put("systemVersion", SystemUtil.getSystemVersion());//手机Android系统版本
        params.put("imei",BBCUtil.getIMEI(context));//手机设备号
        params.put("appType",Constants.appType);//app类型
//        params.put("signData",BBCUtil.getDesValue(params));//des加密
        HttpU.getInstance().post(context, Constants.HOST+url, params, context, new HttpCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                if(filterLoadingUrl(url)) {
                    //排除部分链接不取消加载
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

            @Override
            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
                try {
                    //网络正常
                    if(BaseLangUtil.isEmpty(response)){
                        if(listener!=null) {
                            listener.empty();
                        }
                    } else{
                        if (JsonParseUtil.isSuccessResponse(response)) {
                            String result=JsonParseUtil.getStringValue(response,"result");
                            if(listener!=null) {
                                if(type==String.class){
                                    listener.success(result);
                                }else {
                                    listener.success(gson.fromJson(result, type));
                                }
                            }
                        }else {
                            listener.fail();
                        }
                    }
                }catch (Exception e){
                    if(!BBCUtil.isEmpty(e.getMessage())) {
                        LogUtil.e("0.0", e.getMessage());
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e,int code,boolean haveLocalCache) {
                super.onError(request, e,code,haveLocalCache);

                //暂时不显示 网络异常相关
                if (code == -1) {
                    //网络异常
                    //重新加载
                    if(filterNetUrl(url)) {
                    }else if(filterNetNoToastUrl(url)){
                        //过滤掉不需要toast的接口
                        ToastUtil.show(context,"网络开小差啦～");
                    }
                } else {
                    //小素开小差了
                    //排除部分链接不展示重新加载页面
                    if(filterCodeUrl(url)) {
                    }
                }

                if(listener!=null){
                    listener.error();
                }
            }

            @Override
            public void errorCode(int code,String response) {
                if(listener!=null){
                    listener.error();
                }
                super.errorCode(code,response);
                hanlderErrorCode(code,context,response,url,tag,copyMap);
            }
        });
    }

    //不依赖BaseLangActivity的网络请求
    public static void postHttpContext(Context context, final String url, Map<String, Object> params, final Type type, final LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("version", VersionUtil.getVersionCode(context));
        params.put("clientType", "3");
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        params.put("mobileModel", SystemUtil.getSystemModel());//手机型号
        params.put("systemVersion", SystemUtil.getSystemVersion());//手机Android系统版本
        params.put("imei",BBCUtil.getIMEI(context));//手机设备号
        params.put("appType",Constants.appType);//app类型
        HttpU.getInstance().post(context, Constants.HOST+url, params, context, new HttpCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();

            }

            @Override
            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
                try {
                    //网络正常
                    if(BaseLangUtil.isEmpty(response)){
                        if(listener!=null) {
                            listener.empty();
                        }
                    } else{
                        if (JsonParseUtil.isSuccessResponse(response)) {
                            String result=JsonParseUtil.getStringValue(response,"result");
                            if(listener!=null) {
                                if(type==String.class){
                                    listener.success(result);
                                }else {
                                    listener.success(gson.fromJson(result, type));
                                }
                            }
                        }else {
                            listener.fail();
                        }
                    }
                }catch (Exception e){
                    if(!BBCUtil.isEmpty(e.getMessage())) {
                        LogUtil.e("0.0", e.getMessage());
                    }
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e,int code,boolean haveLocalCache) {
                super.onError(request, e,code,haveLocalCache);
                if(listener!=null){
                    listener.error();
                }
            }

            @Override
            public void errorCode(int code,String response) {
                super.errorCode(code,response);
                if(listener!=null){
                    listener.error();
                }
            }
        });
    }

    public static void getHttp(final Activity context, final String url, Map<String, Object> params,final Type type, final LangHttpInterface listener){
        getHttp(context,url,false,params,type,listener);
    }
    public static void getHttp(final Activity context, final String url,boolean useCache, Map<String, Object> params,final Type type, final LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        HashMap<String,Object> copyMap=new HashMap<>();
        copyMap.putAll(params);

        params.put("version", VersionUtil.getVersionCode(context));
        params.put("clientType", "3");
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        params.put("mobileModel", SystemUtil.getSystemModel());//手机型号
        params.put("systemVersion", SystemUtil.getSystemVersion());//手机Android系统版本
        params.put("imei",BBCUtil.getIMEI(context));//手机设备号
        params.put("appType",Constants.appType);//app类型
        //weexVersion 参数变动影响缓存
//        params.put("weexVersion",SuDianApp.getInstance().getSpUtil().getInt(context,Constants.WEEX_VERSION));//weex 版本

        boolean useLocalCache=false;
        if(filterCacheUrl(url,params)||useCache){
            //使用本地缓存的接口
            useLocalCache=true;
        }

        HttpU.getInstance().get(context, Constants.HOST+url, params,useLocalCache, new HttpCallback() {
            @Override
            public void onCacheData(final String cache) {
                super.onCacheData(cache);
                if(filterLoadingUrl(url)) {
                    //排除部分链接不取消加载
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
                try {
                    //网络正常
                    if(!BaseLangUtil.isEmpty(cache)){
                        if (JsonParseUtil.isSuccessResponse(cache)) {
                            String result=JsonParseUtil.getStringValue(cache,"result");
                            if(listener!=null) {
                                if(type==String.class){
                                    listener.success(result);
                                }else {
                                    listener.success(gson.fromJson(result, type));
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                if(filterLoadingUrl(url)) {
                    //排除部分链接不取消加载
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

            @Override
            public void onResponse(final String response,final boolean useLocalCache,final boolean haveLocalCache,final String cacheUrl) {

                try {
                    //网络正常
                    if(BaseLangUtil.isEmpty(response)){
                        if(listener!=null) {
                            listener.empty();
                        }
                    } else{
                        if (JsonParseUtil.isSuccessResponse(response)) {
                            String result=JsonParseUtil.getStringValue(response,"result");
                            boolean equalCache=false;
                            if(useLocalCache){
                                if(haveLocalCache){
                                    String cache= CacheUtil.getCacheData(context,cacheUrl);
                                    String resultCache=JsonParseUtil.getStringValue(cache,"result");
                                    if(result.equals(resultCache)){
                                        equalCache=true;
                                        LogUtil.w("0.0","本地缓存和网络返回数据相同---url："+cacheUrl);
                                    }
                                }
                            }

                            //测试
//                            equalCache=false;
                            //网络返回报文和本地缓存报文不一致时 网络请求进行回调
                            if(listener!=null&&!equalCache) {
                                if(type==String.class){
                                    listener.success(result);
                                }else {
                                    listener.success(gson.fromJson(result, type));
                                }
                            }
                        }else {
                            listener.fail();
                        }
                    }
                }catch (Exception e){
                    if(!BBCUtil.isEmpty(e.getMessage())) {
                        LogUtil.e("0.0", e.getMessage());
                    }
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e,int code,boolean haveLocalCache) {
                super.onError(request, e,code,haveLocalCache);
                if(!BBCUtil.isEmpty(url)&& haveLocalCache){
                    //未命中网络缓存 但是 有本地缓存时 不显示网络异常
                    return;
                }
                //暂时不显示 网络异常相关
                if (code == -1) {
                    //网络异常
                    //重新加载
                    if(filterNetUrl(url)) {
                    }else if(filterNetNoToastUrl(url)){
                        //过滤掉不需要toast的接口
                        ToastUtil.show(context,"网络开小差啦～");
                    }
                } else {
                    //小素开小差了
                    //排除部分链接不展示重新加载页面
                    if(filterCodeUrl(url)) {
                    }
                }

                if(listener!=null){
                    listener.error();
                }
            }

            @Override
            public void errorCode(int code,String response) {
                super.errorCode(code,response);
                hanlderErrorCode(code,context,response,url,"",copyMap);
            }

        });
    }

    public static void hanlderErrorCode(int code,Activity context,String response,String url,String tag,HashMap<String,Object> copyMap){

        //测试
//        if(code==4006||code==4010) {
//            code = 7001;
//        }

//        if(code==401){
//            BBCUtil.clearData(context);
//            Intent intent=new Intent(context, LoginActivity.class);
//            intent.putExtra("goHome","1");
//            ActivityUtil.getInstance().start((Activity)context, intent);
//        }else if(code==20001){
//            Intent intent=new Intent(context, ProductEmptyActivity.class);
//            ActivityUtil.getInstance().start((Activity)context, intent);
//        }else if(code==200002){
//            Intent intent=new Intent(context, BaseEmptyActivity.class);
//            ActivityUtil.getInstance().start((Activity)context, intent);
//        }else if(code==4901){
//            //触发更新逻辑
//            OtherHttpUtil.reqVersion(context);
//        }else if(code==7001){
//            ActivityUtil.getInstance().exitActivity(SafeImgPwdActivity.class);
//            Intent intent=new Intent(context, SafeImgPwdActivity.class);
//            intent.putExtra("url",url);
//            intent.putExtra("tag",tag);
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("copyMap",copyMap);
//            intent.putExtras(bundle);
//            ActivityUtil.getInstance().startResult((Activity)context, intent,SafeImgPwdActivity.REQ_SAFEIMG);
//        }

        //异常报文日志上传日志
        CrashLog.postBuglyException(false,new Exception(url+":"+response),url);
    }

    /**
     * 获取网络图片资源
     *
     * @return
     */
//    public static void reqHttpBitmap(final String urlString, Handler handler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                try {
//                    URL url = new URL(urlString);
//                    connection = (HttpURLConnection) url.openConnection();
//
//                    connection.setRequestMethod("GET");
//
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    Bitmap bitmap = null;
//                    final InputStream is = connection.getInputStream();
//                    if (is == null) {
//                        LogUtil.d("0.0","stream is null");
//                        throw new RuntimeException("stream is null");
//                    } else {
//                        try {
//                            byte[] data = BBCUtil.readStream(is);
//                            if (data != null) {
//                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            }
//                        } catch (Exception e) {
//                            LogUtil.d("0.0",e.toString());
//                            e.printStackTrace();
//                        }
//
//                        is.close();
//                    }
//                    int count=4;
//                    String title="";
//                    Map headers = connection.getHeaderFields();
//                    Set<String> keys = headers.keySet();
//                    for( String key : keys ){
//                        if("count".equals(key)) {
//                            String countStr = connection.getHeaderField(key);
//                            if(BaseLangUtil.isNumericStr(countStr)){
//                                count=Integer.parseInt(countStr);
//                            }
//                        }
//                        if("sb".equals(key)){
//                            title=connection.getHeaderField(key);
//                            title= URLDecoder.decode(title,"utf-8");
//                        }
//                    }
//                    Message message1 = new Message();
//                    // 将服务器返回的结果存放到Message中
//                    message1.arg1=count;
//                    message1.obj = bitmap;
//                    message1.what=SafeImgPwdActivity.REQ_HANDLE_CODE_100;
//                    handler.sendMessage(message1);
//
//                    Message message2 = new Message();
//                    // 将服务器返回的结果存放到Message中
//                    message2.obj=title;
//                    message2.what=SafeImgPwdActivity.REQ_HANDLE_CODE_101;
//                    handler.sendMessage(message2);
//
//                } catch (Exception e) {
//                    LogUtil.d("0.0",e.toString());
//                    e.printStackTrace();
//
//                } finally {
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//
//    }

//    public static void upImage(final BaseLangActivity context, File file, String serverUrl, final boolean isUpHeadImg, String type, final LangImageUpInterface upInterface) {
//        HttpU.getInstance().uploadImage(context, file, serverUrl, type, new LangImageUpInterface() {
//            @Override
//            public void success(String result) {
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        context.dismissWaitDialog();
//                    }
//                });
//                try {
//                    //保存上传成功后的图片路径
//                    if (JsonParseUtil.isSuccessResponse(result)) {
//                        String imgStr = JsonParseUtil.getStringValue(result, "result");
//                        if (!TextUtils.isEmpty(imgStr) && isUpHeadImg) {
//                            MMKVUtil.putString(Constants.USER_HEADIMG, imgStr);
//                        }
//                        if (upInterface != null) {
//                            upInterface.success(imgStr);
//                        }
//                    } else {
//                        ToastUtil.show(context, JsonParseUtil.getMsgValue(result));
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public static void upImage(final BaseLangActivity context, File file, String serverUrl, final boolean isUpHeadImg, String type, boolean dissLoading, final LangImageUpInterface upInterface){
        HttpU.getInstance().uploadImage(context, file, serverUrl, type, new LangImageUpInterface() {
            @Override
            public void success(String result) {
                if(dissLoading) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.dismissWaitDialog();
                        }
                    });
                }
                try {
                    //保存上传成功后的图片路径
                    if (JsonParseUtil.isSuccessResponse(result)) {
                        String imgStr = JsonParseUtil.getStringValue(result,"result");
                        if(upInterface!=null){
                            upInterface.success(imgStr);
                        }
                    } else {
                        ToastUtil.show(context, JsonParseUtil.getMsgValue(result));
                    }

                }catch (Exception e){
                    if(e!=null) {
                        LogUtil.e("0.0", e.toString());
                    }
                }
            }
        });
    }

    //图片下载
    public static Bitmap downImage(String url){
        return HttpU.getInstance().downImage(url);
    }
    public static void download9Picture(Context context,String url){
        HttpU.getInstance().download9Picture(context,url);
    }

    public static File download9PictureResultFile(Context context,String url,Handler handler){
        String fileName;
        try {
            fileName= EncryptUtil.md5(url);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            fileName=System.currentTimeMillis()+"";
        }
        final File saveFile = PicSelUtil.getImageFile(fileName + ".jpg");
        Request request = new Request.Builder().url(url).build();
        if(mOkHttpClient==null){
            mOkHttpClient=HttpU.getInstance().newOkHttpClient();
        }
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(saveFile);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    // 下载完成
                    if (saveFile != null) {
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.EMPTY.fromFile(saveFile)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }

                    Message message=new Message();
                    message.what=0;
                    message.obj=Uri.fromFile(saveFile);
                    handler.sendMessage(message);
                }
            }

        });
        return saveFile;
    }

    //视频下载
    public static void downVideo(Context context,String url){
         HttpU.getInstance().downVideo(context,url);
    }

    //apk下载
    public static long downAPK(Context context,String url,String apkName,ContentObserver contentObserver){
        return HttpU.getInstance().downAPK(context,url,apkName,contentObserver);
    }

//    public static void downloadFile(final Context context,String url,File saveFile,final int wxVerNo){
//        HttpU.getInstance().downloadFile(url, saveFile, new HttpCallback() {
//            @Override
//            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
//                LogUtil.d("weex 缓存成功");
//                SuDianApp.getInstance().getSpUtil().putInt(context,Constants.WEEX_VERSION,wxVerNo);
//            }
//        });
//    }
//
//    public static void downloadFileZip(final Context context,String url,File saveFile,String zipFileName,final int wxVerNo){
//        HttpU.getInstance().downloadFile(url, saveFile, new HttpCallback() {
//            @Override
//            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
//                LogUtil.d("weex 缓存成功");
//                //进行解压
//                ZipManager.debug(BuildConfig.DEBUG);
//                ZipManager.unzip(saveFile.getAbsolutePath(), BBCUtil.getZipJsDir(zipFileName), new IZipCallback() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onProgress(int percentDone) {
//
//                    }
//
//                    @Override
//                    public void onFinish(boolean success) {
//                        LogUtil.e("0.0","解压："+String.valueOf(success));
//                    }
//                });
//                SuDianApp.getInstance().getSpUtil().putInt(context,Constants.WEEX_VERSION,wxVerNo);
//            }
//        });
//    }
//
//    public static void OssUpFile(Context context, OssTokenBean ossTokenBean, File file, VideoUploadI videoUploadI){
//        if(file==null){
//            return;
//        }
//        if(ossTokenBean==null){
//            return;
//        }
//        String endpoint = ossTokenBean.getOssEndpoint();
//        String fileName=file.getName();
//        if(BBCUtil.isEmpty(fileName)){
//           return;
//        }
//        String prefix=fileName.substring(fileName.lastIndexOf("."));
//        final String objectKey=ossTokenBean.getObjectKey()+prefix;
//
//        // 在移动端建议使用STS的方式初始化OSSClient。
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ossTokenBean.getAccessKeyId(), ossTokenBean.getAccessKeySecret(), ossTokenBean.getSecurityToken());
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
//
//        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
//        // 构造上传请求。
//        PutObjectRequest put = new PutObjectRequest(ossTokenBean.getBucketName(), objectKey, file.getAbsolutePath());
//        // 异步上传。
//        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                LogUtil.d( "PutObject：UploadSuccess");
//                LogUtil.d( "ETag："+result.getETag());
//                LogUtil.d("RequestId："+result.getRequestId());
//                if(videoUploadI!=null){
//                    videoUploadI.onSuccess(ossTokenBean.getOssDomain()+objectKey);
//                }
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常。
//                if (clientExcepion != null) {
//                    // 本地异常，如网络异常等。
//                    LogUtil.e("ErrorMessage："+ clientExcepion.getMessage());
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常。
//                    LogUtil.e("ErrorCode："+ serviceException.getErrorCode());
//                    LogUtil.e("RequestId："+ serviceException.getRequestId());
//                    LogUtil.e("HostId："+serviceException.getHostId());
//                    LogUtil.e("RawMessage："+ serviceException.getRawMessage());
//                }
//                if(videoUploadI!=null){
//                    videoUploadI.onSuccess("");
//                }
//            }
//        });
//    }

    //过滤掉不需要展示开小差的接口
    //购物车数量查询接口
    //购物车 确定订单顶部 通知
    //查询分享信息接口
    //channalid更新接口
    //版本号接口查询接口
    //过滤到首页的接口
    //开小差filter
    public static String[] filterCodeList=new String[]{};
    public static boolean filterCodeUrl(String url){
        if(BBCUtil.isEmpty(url)){
            return true;
        }
        for (String filterUrl:filterCodeList
             ) {
            if(url.contains(filterUrl)){
                return false;
            }
        }
        return true;
    }

    //过滤掉不需要展示网络重新加载的接口
    //购物车数量查询接口
    //购物车 确定订单顶部 通知
    //查询分享信息接口
    //channalid更新接口
    //登录接口
    //获取版本号接口
    //检测是否收藏接口
    //订单状态数量接口
    //网络 filter
    public static String[] filterNetList=new String[]{};
    public static boolean filterNetUrl(String url){
        if(BBCUtil.isEmpty(url)){
            return false;
        }
        for (String filterUrl:filterNetList
                ) {
            if(url.contains(filterUrl)){
                return false;
            }
        }
        return true;
    }

    //过滤掉网络不正常不需要toast的接口
    //首页红点
    //购物车数量查询接口
    //购物车 确定订单顶部 通知
    //channalid更新接口
    //版本号接口
    //网络 filter
    //付款后查询订单交易情况
    public static String[] filterNetNoToastList=new String[]{};
    public static boolean filterNetNoToastUrl(String url){
        if(BBCUtil.isEmpty(url)){
            return false;
        }
        for (String filterUrl:filterNetNoToastList
                ) {
            if(url.contains(filterUrl)){
                return false;
            }
        }
        return true;
    }

    //过滤掉不需要展示重新加载的接口
    //购物车数量查询接口
    //购物车 确定订单顶部 通知
    //channalid更新接口
    //检测是否收藏接口
    //主题场详情页
    //爆品类别
    //爆品详情
    //OSS token获取
    //付款后查询订单交易情况
    //loading
    public static String[] filterLoadingList=new String[]{};
    public static boolean filterLoadingUrl(String url){
        if(BBCUtil.isEmpty(url)){
            return false;
        }
        for (String filterUrl:filterLoadingList
                ) {
            if(url.contains(filterUrl)){
                return false;
            }
        }
        return true;
    }


    /*
    *get请求中 走本地缓存的接口
    *首页接口
    *用户信息接口 不再走缓存 弹框有问题 不该弹框的情况弹了
    *热搜词
    *查询主题场banner详情
    *商品搜索第一页数据
    *一级分类
    *品牌墙
    *根据一级分类搜索二三级分类，品牌
    *分类品牌搜索
    *品牌详情
    *清仓主题场
    * */
    public static String[] filterCacheList=new String[]{};
    public static boolean filterCacheUrl(String url,Map<String, Object> params){
        if(BBCUtil.isEmpty(url)){
            return false;
        }
//        try {
//            for (String filterUrl : filterCacheList
//                    ) {
//                if (url.contains(filterUrl)) {
//                    if (url.contains(Constants.REQ_SOLRBYPARAM)) {
//                        //商品搜索页接口只缓存第一页数据
//                        if (params.get("page") != null && (int) params.get("page") == 1) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                    return true;
//                }
//            }
//        }catch (Exception e){
//
//        }
        return false;
    }
}
