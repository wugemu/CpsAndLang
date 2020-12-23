package com.lxkj.dmhw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.test.andlang.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lxkj.dmhw.bean.self.PayOrder;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

public class JsonParseUtil {
    public static Gson gson3 = new GsonBuilder()
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong()*1000);
                }
            })
            .registerTypeAdapter(int.class, new JsonDeserializer<Integer>() {
                @Override
                public Integer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    if (BBCUtil.isEmpty(jsonElement.getAsString())) {
                        return 0;
                    }
                    return jsonElement.getAsInt();
                }
            })
            .registerTypeAdapter(double.class, new JsonDeserializer<Double>() {
                @Override
                public Double deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    if (BBCUtil.isEmpty(jsonElement.getAsString())) {
                        return 0d;
                    }
                    return jsonElement.getAsDouble();
                }
            })
            .registerTypeAdapter(long.class, new JsonDeserializer<Long>() {
                @Override
                public Long deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    if (BBCUtil.isEmpty(jsonElement.getAsString())) {
                        return 0l;
                    }
                    return jsonElement.getAsLong();
                }
            })
            .create();

    private static String rvZeroAndDot(String s){
        if (s.isEmpty()) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if(value instanceof BigDecimal || value instanceof Double || value instanceof Float){
                return new BigDecimal(rvZeroAndDot(value.toString()));
            }
            return value;
        }
    };
    /**
     * 解析返回信息是否成功
     */
    public static boolean isSuccessResponse(String jsonStr) {
        boolean isSuccess = false;
//        JSONObject jsonObj = new JSONObject(jsonStr);
//        int success = jsonObj.getInt("code");
        JSONObject jb = JSON.parseObject(jsonStr);
        int success =jb.getIntValue("code");
        if (success == 200) {
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 解析服务器返回的message字段信息
     *
     * @throws Exception
     */
    public static String getStringValue(String jsonStr, String key) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject == null) {
                return "";
            }
            String val=JSON.toJSONString(jsonObject, filter, new SerializerFeature[0]);
            if(BBCUtil.isEmpty(val)){
                return "";
            }
            JSONObject valObj = JSON.parseObject(val);
            String result = valObj.getString(key);
            if (BBCUtil.isEmpty(result)) {
                return "";
            }
            if ("null".equals(result)) {
                return "";
            }
            return result;
        }catch (Exception e){
            if(e!=null) {
                LogUtil.e("0.0", e.toString());
            }
            return "";
        }
//        JsonElement element=jsonObject.get(key);
//
//        String value=jsonObject.get(key).toString();
//        if("\"\"".equals(value)){
//            return "";
//        }
//        if("null".equals(value)){
//            return "";
//        }
//        return value;
//        String value = null;
//        try {
//            JSONObject jsonObj = new JSONObject(jsonStr);
//            value = jsonObj.getString(key);
//            if("null".equals(value)){
//                return "";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            value = null;
//        }
//        return value;
    }

    /**
     * 解析服务器返回的msg字段信息
     *
     * @throws Exception
     */
    public static String getMsgValue(String jsonStr) throws Exception {
        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonStr);
        return jsonObj.getString("message");
    }

    /**
     * 解析微信支付信息
     *
     * @throws Exception
     */
    public static PayOrder getPayOrder(String jsonStr)
            throws Exception {
        return gson3.fromJson(jsonStr, PayOrder.class);

    }
}
