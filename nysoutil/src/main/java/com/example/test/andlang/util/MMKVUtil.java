package com.example.test.andlang.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

public class MMKVUtil {

    private static MMKV mmkv= MMKV.defaultMMKV();

    public static void changeMMKV(Context context){
        mmkv = MMKV.mmkvWithID(PreferencesUtil.PREFERENCES_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences preferences = context.getSharedPreferences(PreferencesUtil.PREFERENCES_DEFAULT, Context.MODE_PRIVATE);
        mmkv.importFromSharedPreferences(preferences);
        preferences.edit().clear().commit();
    }
    /**
     * 设置int类型的数�?
     *
     * @param name
     * @param value
     */
    public static void putInt(String name,
                              int value) {
        mmkv.encode(name, value);
    }

    /**
     * 设置String 类型的数�?
     *
     * @param name
     * @param value
     */
    public static void putString(String name,
                                 String value) {
        mmkv.encode(name, value);
    }

    /**
     * 设置boolean类型的数�?
     *
     * @param name
     * @param value
     */
    public static void putBoolean(
            String name, Boolean value) {
        mmkv.encode(name, value);
    }

    /**
     * 设置Long 类型的数�?
     *
     * @param name
     * @param value
     */
    public static void putLong(String name,
                               Long value) {
        mmkv.encode(name,value);
    }

    /**
     * 设置float类型，默认返回�?0
     *
     * @param name
     * @return
     */
    public static void putFloat(String name,
                                Float value) {
        mmkv.encode(name,value);
    }

    /**
     * 获取int类型，默认返回�?-1
     *
     * @param name
     * @return
     */
    public static int getInt(String name) {
        return mmkv.decodeInt(name,-1);
    }

    /**
     * 获取String 类型，默认返回�?""
     *
     * @param name
     * @return
     */
    public static String getString(String name) {
        return mmkv.decodeString(name,"");
    }

    /**
     * 获取String 类型，默认返回�?""
     *
     * @param name
     * @return
     */
    public static String getString2(String name, String defaultstr) {
        return mmkv.decodeString(name,defaultstr);
    }

    /**
     * 获取boolean类型，默认返回�?false
     *
     * @param name
     * @return
     */
    public static boolean getBoolean(
            String name, boolean defaultvalue) {
        return mmkv.decodeBool(name,defaultvalue);
    }

    /**
     * 获取Long类型，默认返回�?-1
     *
     * @param name
     * @return
     */
    public static Long getLong(String name) {
        return mmkv.decodeLong(name,-1);
    }

    /**
     * 获取float类型，默认返回�?0
     *
     * @param name
     * @return
     */
    public static Float getFloat(String name) {
        return mmkv.decodeFloat(name,0);
    }


    public static String[] getAllData(){
        return mmkv.allKeys() ;
    }
}
