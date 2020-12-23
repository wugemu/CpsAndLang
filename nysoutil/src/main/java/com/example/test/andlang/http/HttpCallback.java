package com.example.test.andlang.http;

import okhttp3.Request;

/**
 * Created by 1 on 2016/1/20.
 */
public abstract class HttpCallback {

    /**
     * UI Thread
     *
     */
    public void onCacheData(String cache) {

    }

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {

    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress) {

    }

    //code=-1 时code不存在
    public void onError(Request request, Exception e,int code,boolean haveLocalCache){

    }

    public void errorCode(int code,String response){

    }


    /*response 网络返回报文
     *useLocalCache 是否使用本地缓存
     *haveLocalCache 本地是否有缓存
     *cacheUrl 缓存的key
     */
    public abstract void onResponse(String response,boolean useLocalCache,boolean haveLocalCache,String cacheUrl);
}
