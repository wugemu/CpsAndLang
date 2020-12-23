package com.example.test.andlang.util;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.andlang.http.DownCallBackI;
import com.example.test.andlang.http.HttpCallback;
import com.example.test.andlang.http.HttpU;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class VideoUtils {
    public static LinkedHashMap<String,String> videoUrlMap=new LinkedHashMap<>();//视频链接映射
    private static final String cacheDirName="videocache";
    private static final int maxSize=5;//最大缓存视频数量 超过则删除最早的视频
    private static final int maxPlaySize=10;//播放中最大缓存数量
    //获取视频的宽高,和时长
    public static void getImgUrlWidthHeight(Context context,final String imgUrl,final VideoInformations videoInformations) {
        if(BaseLangUtil.isEmpty(imgUrl)){
            return;
        }
        try {
            //获取图片真正的宽高
            Glide.with(context)
                    .load(imgUrl)
                    .asBitmap()//强制Glide返回一个Bitmap对象
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            LogUtil.e("0.0","封面图链接："+imgUrl);
                            LogUtil.e("0.0", "封面图宽，高：  " + width+"，"+height);
                            videoInformations.dealWithVideoInformation(width, height);
                            bitmap.recycle();
                        }
                    });
        } catch (Exception e) {
            LogUtil.e("0.0",e.toString());
        }
    }

    public interface VideoInformations {
        void dealWithVideoInformation(float w, float h);
    }

    //缓存短视频
    public static void cacheVideo(final Context context,final String videoUrl){
        if (BaseLangUtil.isEmpty(videoUrl)) {
            return;
        }
        new ThreadTask<Object>(){
            @Override
            public Object onDoInBackground() {
                String localVideoUrl = getLocalVideoUrl(videoUrl);
                if(BaseLangUtil.isEmpty(localVideoUrl)){
                    //没有缓存此视频时则进行缓存
                    if(BaseLangUtil.isHttpUrl(videoUrl)) {

                       downloadVideoFile(videoUrl, getVideoFile(getVideoName(videoUrl)), new DownCallBackI() {
                            @Override
                            public void cacheSuccess(String url) {
                                videoUrlMap.put(videoUrl, getLocalVideoUrl(videoUrl));
                                Intent intent = new Intent("DOWN_VIDEO_NOTI");
                                intent.putExtra("downVideoUrl", videoUrl);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }
                        });
                    }
                }else {
                    videoUrlMap.put(videoUrl,localVideoUrl);
                }
                return null;
            }
        }.execute();
    }

    private static void downloadVideoFile(final String url, File saveFile, final DownCallBackI downCallBackI){
        HttpU.getInstance().downloadFile(url, saveFile, new HttpCallback() {
            @Override
            public void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl) {
                LogUtil.e("视频缓存成功："+url);
                if(downCallBackI!=null){
                    downCallBackI.cacheSuccess(url);
                }
            }
        });

    }

    //获取真正播放视频的地址
    public static String getRealPlayUrl(String videoUrl,boolean useCache){
        if(useCache) {
            String localVideoUrl = videoUrlMap.get(videoUrl);
            if (!BaseLangUtil.isEmpty(localVideoUrl)) {
                //切换成本地视频地址
                videoUrl = localVideoUrl;
                LogUtil.e("0.0", "使用缓存视频地址：" + videoUrl);
            }
        }
        return videoUrl;
    }

    //判断是否有缓存了
    public static boolean isHaveCache(String videoUrl){
        String localVideoUrl = videoUrlMap.get(videoUrl);
        return !BaseLangUtil.isEmpty(localVideoUrl);
    }

    //获取本地视频路径
    public static String getLocalVideoUrl(String videoUrl){
        String fileName=getVideoName(videoUrl);
        File file=getVideoFile(fileName);
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return "";
    }
    public static File getVideoFile(String fileName){
        if (!PicSelUtil.getImgFileDir().exists()) {
            PicSelUtil.getImgFileDir().mkdir();
        }
        File cacheDir = new File(PicSelUtil.getImgFileDir(), cacheDirName);
        if(!cacheDir.exists()){
            cacheDir.mkdir();
        }
        return  new File(cacheDir, fileName);
    }

    public static String getVideoName(String videoUrl){
        if (videoUrl.contains("?")) {
            videoUrl = videoUrl.substring(0, videoUrl.indexOf("?"));
        }
        String fileName = videoUrl.substring(videoUrl.lastIndexOf("/") + 1);
        return fileName;
    }

    //清除视频缓存
    public static void clearVideoCache(){
        // 如果不存在目录，则创建
        if (!PicSelUtil.getImgFileDir().exists()) {
            PicSelUtil.getImgFileDir().mkdir();
        }
        File cacheFile = new File(PicSelUtil.getImgFileDir(), cacheDirName);
        //删除文件夹
        PicSelUtil.delFile(cacheFile);
    }

    //短视频缓存初始化
    public static void initVideoCache(){
        videoUrlMap.clear();
    }

    public static void checkPlayMaxCache(){
        new ThreadTask<Object>(){
            @Override
            public Object onDoInBackground() {
                try {
                    LogUtil.e("0.0","videoUrlMap大小："+videoUrlMap.size());
                    while (videoUrlMap.size()>maxPlaySize){
                        String videoUrl=getHead(videoUrlMap).getKey();
                        videoUrlMap.remove(videoUrl);
                        delCache(videoUrl);
                    }
                }catch (Exception e){

                }
                return null;
            }
        }.execute();
    }

    //限制最大缓存视频数量
    public static void checkMaxCache(){
        new ThreadTask<Object>(){
            @Override
            public Object onDoInBackground() {
                try {
                    File cacheDir = new File(PicSelUtil.getImgFileDir(), cacheDirName);
                    if(cacheDir.exists()) {
                        File[] cacheFiles = cacheDir.listFiles();
                        int length=cacheFiles.length;
                        int delposi=0;
                        while (length>maxSize){
                            File file=cacheFiles[delposi];
                            if(file.exists()) {
                                file.delete();
                            }
                            delposi++;
                            length--;
                        }
                    }
                }catch (Exception e){

                }
                return null;
            }
        }.execute();
    }

    public static <K, V> Map.Entry<K, V> getHead(LinkedHashMap<K, V> map) {
        return map.entrySet().iterator().next();
    }

    //指定删除视频文件
    public static void delCache(String videoUrl){
        String fileName=getVideoName(videoUrl);
        File file=getVideoFile(fileName);
        if(file.exists()&&file.isFile()){
            file.delete();
        }
    }

}
