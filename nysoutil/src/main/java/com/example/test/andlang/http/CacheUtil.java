package com.example.test.andlang.http;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.example.test.andlang.localcache.ACache;
import com.example.test.andlang.localcache.CacheResultListener;
import com.example.test.andlang.util.BaseLangUtil;

/**
 * Created by Bill56 on 2017/12/4.
 */

public class CacheUtil {
    /**
     * 方案1：根据key来判断是否有缓存数据，不做相应处理和回调，默认是携带版本号
     *
     * @param context 上下文环境
     * @param key     缓存key
     * @return
     */
    public static boolean isCacheData(Context context, String key) {
        return isCacheData(context,key,false);
    }

    /**
     * 方案1：根据key来判断是否有缓存数据，不做相应处理和回调
     *
     * @param context     上下文环境
     * @param key         缓存key
     * @param withVersion 是否携带版本号
     * @return
     */
    public static boolean isCacheData(Context context, String key, boolean withVersion) {
        String cacheKey = key;
        if(withVersion) {
            cacheKey = key + "_" + BaseLangUtil.getVersionCode(context);
        }
        ACache aCache=ACache.get(context);
        if(aCache!=null) {
            String cacheData = aCache.getAsString(cacheKey);
            if (!TextUtils.isEmpty(cacheData)) {
                return true;
            }
        }
        return false;
    }

    public static String getCacheData(Context context, String key) {
        ACache aCache=ACache.get(context);
        if(aCache!=null) {
            String cacheData = aCache.getAsString(key);
            return cacheData;
        }else {
            return "";
        }
    }

    public static void handleCache(Context context,String key,@NonNull CacheResultListener listener){
        handleCache(context,key,false,listener);
    }

    public static void handleCache(Context context,String key,boolean withVersion,@NonNull CacheResultListener listener) {
        String cacheKey = key;
        if(withVersion) {
            cacheKey = key + "_" + BaseLangUtil.getVersionCode(context);
        }
        ACache aCache=ACache.get(context);
        if(aCache!=null) {
            String cacheData = aCache.getAsString(cacheKey);
            if (!TextUtils.isEmpty(cacheData)) {
                listener.cacheResult(true, cacheData);
                return;
            }
        }
        listener.cacheResult(false,null);
    }

    /**
     * 默认携带版本号缓存
     * @param context
     * @param key
     * @param value
     */
    public static void cacheData(Context context,String key,String value) {
        cacheData(context,key,value,false);
    }

    public static void cacheData(Context context,String key,String value,boolean withVersion) {
        String cacheKey = key;
        if(withVersion) {
            cacheKey = key + "_" + BaseLangUtil.getVersionCode(context);
        }
        ACache aCache=ACache.get(context);
        if(aCache!=null) {
            aCache.put(cacheKey, value, 7 * ACache.TIME_DAY);
        }
    }

    /**
     * 清除旧缓存
     * @param context
     */
    public static void clearOldVersionCache(Context context){
        ACache aCache=ACache.get(context);
        if(aCache!=null) {
            aCache.clear();
        }
    }

}
