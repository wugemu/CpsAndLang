package com.lxkj.dmhw.defined;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON处理父类
 * Created by Android on 2018/8/15.
 */

public class BaseJson {

    /**
     * 获取String类型
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    public String getString(JSONObject object, String key) throws JSONException {
        if (object.has(key)) {
            return object.getString(key);
        } else {
            return "";
        }
    }

    /**
     * 获取int类型
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    public int getInt(JSONObject object, String key) throws JSONException {
        if (object.has(key)) {
            return object.getInt(key);
        } else {
            return 0;
        }
    }

    /**
     * 获取JSONArray类型
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    public JSONArray getArray(JSONObject object, String key) throws JSONException {
        if (object.has(key)) {
            return object.getJSONArray(key);
        } else {
            return new JSONArray();
        }
    }

    /**
     * 获取JSONObject类型
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    public JSONObject getObject(JSONObject object, String key) throws JSONException {
        if (object.has(key)) {
            return object.getJSONObject(key);
        } else {
            return null;
        }
    }

}
