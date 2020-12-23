package com.lxkj.dmhw.utils.jsonUtil;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lintenghui on 2015/5/16.
 */
public class JsonDataUtil {

    public static JSONObject getJson(Context context,String key){
        DataCache mCache = DataCache.get(context);
        JSONObject obj=mCache.getAsJSONObject(key);
        return obj;
    }
    public static JSONArray getJsonArray(Context context,String key){
        DataCache mCache = DataCache.get(context);
        JSONArray obj=mCache.getAsJSONArray(key);
        return obj;
    }
    public  static void savaJsonData(Context context,String key,JSONObject jsonObject){
        DataCache mCache = DataCache.get(context);
        mCache.put(key,jsonObject);
    }

    public static void updataJsonData(Context context,String key,JSONObject jsonObject){
        DataCache mCache = DataCache.get(context);
        mCache.remove(key);
        mCache.put(key,jsonObject);
    }
    public static void updataJsonDataJsonarray(Context context,String key,JSONArray jsonObject){
        DataCache mCache = DataCache.get(context);
        mCache.remove(key);
        mCache.put(key,jsonObject);
    }
    public static void clean(Context context){
        DataCache mCache = DataCache.get(context);
        mCache.clear();
    }
}
