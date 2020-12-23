package com.lxkj.dmhw.logic;

import android.util.Log;

import com.lxkj.dmhw.BuildConfig;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";
    //获取接口的名称，方便调试
    private String  name="";

    public LogInterceptor(String name) {
        this.name=name;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        if (BuildConfig.DEBUG){
            Log.e(TAG,"\n");
            Log.e(TAG,"----------Start-----接口名称："+name);
            Log.e(TAG, "| "+request.toString());
        }
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "| RequestParams:{" + sb.toString() + "}");
                }
            }
        }
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "| Response:" + content);
            Log.e(TAG, "----------End:" + duration + "毫秒----------");
        }
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }


}
