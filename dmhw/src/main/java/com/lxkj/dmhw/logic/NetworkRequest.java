package com.lxkj.dmhw.logic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.example.test.andlang.util.VersionUtil;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.ImageDispose;
import com.lxkj.dmhw.utils.ImageProgressUtil.ProgressInterceptor;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * HTTP请求
 * Created by Zyhant on 2017/2/10.
 */

public class NetworkRequest {

    private static NetworkRequest instance;
    /**
     * 请求成功
     */
    private static int SUCCESS = 0;
    /**
     * 请求失败
     */
    public static int FAILED = 1;
    /**
     * 超时时间
     */
    private int initialTimeoutMs = 30;

    private int ismore=0;

    private NetworkRequest() {

    }

    public static NetworkRequest getInstance() {
        if (instance == null) {
            instance = new NetworkRequest();
        }
        return instance;
    }

    /**
     * post请求
     * @param paramMap 键值对
     * @param name 调用值
     * @param url 请求地址
     */
    public void POST(Handler handler, HashMap<String, String> paramMap, String name, String url) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LogInterceptor(name)).addInterceptor(new ProgressInterceptor()).connectTimeout(initialTimeoutMs, TimeUnit.SECONDS).readTimeout(initialTimeoutMs, TimeUnit.SECONDS).writeTimeout(initialTimeoutMs, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();
        client.dispatcher().setMaxRequestsPerHost(8);
        okhttp3.Request request = new okhttp3.Request.Builder().addHeader("CPSTOKEN",DateStorage.getMyToken()).url(url).post(getFormBody(paramMap)).build();
        if(BuildConfig.DEBUG){
            Log.d("0.0","post请求的接口："+url);
            Log.d("0.0","post请求的报文："+paramMap);
        }
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, e.getMessage(), FAILED,0);
            }
            @Override
            public void onResponse(Call call, Response response) {
                //response.code=200
                try {
                    String content = response.body().string();
                    if(BuildConfig.DEBUG){
                        Log.d("0.0","返回的接口："+url);
                        Log.d("0.0","返回的报文："+content);
                    }
                    JSONObject object = new JSONObject(content);
                    if (object.has("result")) {
                        switch (object.optString("result")) {
                            case "1":// 成功
                                InternalMessage.getInstance().sendMessage(handler, LogicActions.getActionsSuccess(name), JsonData.getInstance().analysis(name, content), SUCCESS,0);
                                break;
                            case "2":// 失败
                                if(name.equals("Register")||name.equals("RegisterWeChat")||name.equals("Login")||name.equals("Withdrawal")||name.equals("ExchangeScore")||name.equals("LoginCode")){//登录注册页面要每次都弹错误信息
                                  ismore=1;
                                }
                                if (name.equals("ConVertTextToGoods")){
                                 InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, "111", 3,ismore);
                                }else if(name.equals("ConvertShopid")){
                                 InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object.optString("url"), 2,ismore);
                                }else if(name.equals("SYChangePhoneNum")){
                                  InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object, 6,ismore);
                                }else{
                                 InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object.optString("msgstr"), FAILED,ismore);
                                }
                                break;
                            case "3":
                                if (name.equals("ActivityChain")){
                                    InternalMessage.getInstance().sendMessage(handler, LogicActions.getActionsSuccess(name), JsonData.getInstance().analysis(name, content), SUCCESS,0);
                                }else if(name.equals("SendCircle")){//一键发圈通知
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("oneKeyShare"), object.toString(), 0);
                                }else if(name.equals("SendCircleMarketing")){
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("oneKeyShare"), object.toString(), 1);
                                }else if(name.equals("SendCircleMarketingAll")) {
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("oneKeyShare"), object.toString(), 2);
                                }else if(name.equals("SYChangePhoneNum")){
                                    InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object, 5,ismore);
                                }else{
                                    // 淘宝未授权返回
                                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("noAuthSuccessful"), object.toString(), 0);
                                }
                                break;
                        }
                    } else {
                        InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object.optString("msgstr"), FAILED,0);
                    }
                } catch (Exception e) {
                    Logger.e(e, "POST");
                }
            }
        });
    }

    /**
     * get请求 【微信专用方法】
     * @param paramMap 键值对
     * @param name 调用值
     * @param url 请求地址
     *
     */
    public void GET(Handler handler, HashMap<String, String> paramMap, String name, String url) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LogInterceptor(name)).connectTimeout(initialTimeoutMs, TimeUnit.SECONDS).readTimeout(initialTimeoutMs, TimeUnit.SECONDS).writeTimeout(initialTimeoutMs, TimeUnit.SECONDS).build();
        StringBuilder urlBuilder = new StringBuilder(url);
        for (String key : paramMap.keySet()) {
            try {
                urlBuilder.append(key).append("=").append(URLEncoder.encode(paramMap.get(key), "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                Logger.e(e, "get方法utf-8转码");
            }
        }
        url = urlBuilder.toString().substring(0, urlBuilder.toString().length() - 1);
        okhttp3.Request request = new okhttp3.Request.Builder().addHeader("CPSTOKEN",DateStorage.getMyToken()).get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, e.getMessage(), FAILED,0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InternalMessage.getInstance().sendMessage(handler, LogicActions.getActionsSuccess(name), JsonData.getInstance().analysis(name, response.body().string()), SUCCESS,0);
            }
        });
    }

    /**
     * get请求  自己的平台自定义公共参数
     * @param paramMap 键值对
     * @param name 调用值
     * @param url 请求地址
     */
    public void GETNew(Handler handler, HashMap<String, String> paramMap, String name,String url) {
        //上传公共参数
        final String reqUrl=url;
        HashMap<String, String> tempParamMap= getHashMapData(paramMap);
        if(BuildConfig.DEBUG){
            Log.d("0.0","get请求的接口："+url);
            Log.d("0.0","get请求的报文："+tempParamMap);
        }
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LogInterceptor(name)).connectTimeout(initialTimeoutMs, TimeUnit.SECONDS).readTimeout(initialTimeoutMs, TimeUnit.SECONDS).writeTimeout(initialTimeoutMs, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        for (String key : tempParamMap.keySet()) {
            try {
                urlBuilder.append(key).append("=").append(URLEncoder.encode(tempParamMap.get(key), "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                Logger.e(e, "get方法utf-8转码");
            }
        }
        url = urlBuilder.toString().substring(0, urlBuilder.toString().length() - 1);
        okhttp3.Request request = new okhttp3.Request.Builder().addHeader("Authorization",DateStorage.getMyToken()).get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, e.getMessage(), FAILED,0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String content = response.body().string();
                    if(BuildConfig.DEBUG){
                        Log.d("0.0","返回的接口："+reqUrl);
                        Log.d("0.0","返回的报文："+content);
                    }
                    JSONObject object = new JSONObject(content);
                    if (object.optString("code").equals("200")){
                        InternalMessage.getInstance().sendMessage(handler, LogicActions.getActionsSuccess(name), JsonData.getInstance().analysis(name, content), SUCCESS, 0);
                    }else if(object.optString("code").equals("501")) {
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("noLogin"), object.toString(), 0);
                     }else if(object.optString("code").equals("601")){
                        // 拼多多未授权
                        if(name.equals("PDDIsBindAuth")){
                            InternalMessage.getInstance().sendMessage(handler, LogicActions.getActionsSuccess(name), JsonData.getInstance().analysis(name, content), SUCCESS, 0);
                        }else{
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("noPddAuth"), object.toString(), 0);
                        }
                    }else{
                        InternalMessage.getInstance().sendMessage(handler, LogicActions.Failed, object.optString("msgstr"), FAILED, 1);
                     }
                }catch (Exception e){
                    Log.e("0.0", "GetNew:"+e.toString());
                }
                }
        });
    }


    private FormBody getFormBody(HashMap<String, String> paramMap) {
        UserInfo login = DateStorage.getInformation();
        if (!DateStorage.getLoginStatus()) {
            if (paramMap.get("userid") != null) {
                if (paramMap.get("userid").equals("")) {
                    paramMap.remove("userid");
                    paramMap.put("userid", "43");
                }
            }
        } else {
            paramMap.put("userid", login.getUserid());
        }
        paramMap.put("islogin", DateStorage.getLoginStatus() ? "1" : "0");
        paramMap.put("devversion", Utils.getAppVersionCode() + "");
        paramMap.put("devtype", "00");
        paramMap.put("merchantid", login.getMerchantid());
        paramMap.put("apiversion", Variable.ApiVersion);
        String imei=Utils.getIMEI();
        paramMap.put("device_value", Utils.getMD5(imei));
        paramMap.put("device_encrypt", "MD5");
        paramMap.put("device_type", "IMEI");
        paramMap.put("version", Utils.getAppVersionCode() + "");
        paramMap.put("versionAndr", Utils.getAppVersionCode() + "");
        paramMap.put("appType",Constants.appType+"");//app类型

        paramMap.put("reqttime", Utils.getStringToDate(Utils.getCurrentDate("yyyy/MM/dd HH:mm:ss.SSS"), "yyyy/MM/dd HH:mm:ss.SSS") + "");
        paramMap.put("sign", Utils.getMD5(Utils.getMapAscii(paramMap) + "1999@#dhjdxa"));
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (String key : paramMap.keySet()) {
            formBodyBuilder.add(key, paramMap.get(key));
        }
        return formBodyBuilder.build();
    }

    //http 请求方式Get的参数获取
    private HashMap getHashMapData(HashMap<String, String> paramMap) {
        UserInfo login = DateStorage.getInformation();
        if (!DateStorage.getLoginStatus()) {
            if (paramMap.get("userid") != null) {
                if (paramMap.get("userid").equals("")) {
                    paramMap.remove("userid");
                    paramMap.put("userid", "43");
                }
            }
        } else {
            paramMap.put("userid", login.getUserid());
        }
        paramMap.put("islogin", DateStorage.getLoginStatus() ? "1" : "0");
        paramMap.put("devversion", Utils.getAppVersionCode() + "");
        paramMap.put("devtype", "00");
        paramMap.put("merchantid", login.getMerchantid());
        paramMap.put("apiversion", Variable.ApiVersion);
        String imei=Utils.getIMEI();
        paramMap.put("device_value", Utils.getMD5(imei));
        paramMap.put("device_encrypt", "MD5");
        paramMap.put("device_type", "IMEI");
        paramMap.put("reqttime", Utils.getStringToDate(Utils.getCurrentDate("yyyy/MM/dd HH:mm:ss.SSS"), "yyyy/MM/dd HH:mm:ss.SSS") + "");
      
        paramMap.put("sign", Utils.getMD5(Utils.getMapAscii(paramMap) + "1999@#dhjdxa"));
        
        return paramMap;
    }

}
