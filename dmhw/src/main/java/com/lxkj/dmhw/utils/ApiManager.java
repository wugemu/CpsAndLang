package com.lxkj.dmhw.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方api工具类
 * Created by zyhant on 18-2-2.
 */

public class ApiManager {

    /**
     * 初始化微信SDK
     */
    public static IWXAPI weChat(Context context, boolean isCheck) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        IWXAPI api = WXAPIFactory.createWXAPI(context, Variable.weChatAppId, isCheck);
        // 将应用的APP_ID注册到微信
        api.registerApp(Variable.weChatAppId);
        return api;
    }

    /**
     * 调用淘宝
     */
    public static void Alibc(Context context, String url, String pid, AlibcTradeCallback alibcTradeCallback) {
      try {
     if (Utils.checkApkExist("com.taobao.taobao")) {
         //自定义参数
         Map<String, String> trackParams = new HashMap<>();
         Map<String, String> extraParams = new HashMap<>();
         // 设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams();
         showParams.setOpenType(OpenType.Native);
         showParams.setClientType("taobao");
         //淘宝领券界面返回按钮，可以返回自己的app
         showParams.setBackUrl("alisdk://");
         showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);
        // 设置淘客参数
         AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
         if (pid != null) {
            String adzoneid = pid.substring(pid.indexOf("_") + 1);
            adzoneid = adzoneid.substring(adzoneid.indexOf("_") + 1);
            adzoneid = adzoneid.substring(adzoneid.indexOf("_") + 1);
            taokeParams.setPid(pid);
            taokeParams.setAdzoneid(adzoneid);
            taokeParams.setSubPid(pid);
        }
         // 以显示传入url的方式打开页面（第二个参数是套件名称）
         AlibcTrade.openByUrl((Activity) context, "", url, null,
                 new WebViewClient(), new WebChromeClient(), showParams,
                 taokeParams, trackParams, new AlibcTradeCallback() {
                     @Override
                     public void onTradeSuccess(AlibcTradeResult tradeResult) {
                     }
                     @Override
                     public void onFailure(int code, String msg) {
                     }
                 });
    } else {
        context.startActivity(new Intent(context, AliAuthWebViewActivity.class).putExtra(Variable.webUrl, url));
    }
          ClipboardManager manager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
          assert manager != null;
          manager.setPrimaryClip(ClipData.newPlainText(null, ""));
          DateStorage.setClip("");
        }catch (Exception e){

        }
}
}
