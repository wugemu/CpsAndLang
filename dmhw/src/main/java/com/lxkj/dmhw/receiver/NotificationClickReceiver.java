package com.lxkj.dmhw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lxkj.dmhw.activity.MainActivity;

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
//        String pushType=intent.getStringExtra("push");
//        if (MainActivity.mainActivity!=null) {
//            if(pushType.equals("5")){
//                String url=intent.getStringExtra("url");
//                String name=intent.getStringExtra("name");
//                String labeltype=intent.getStringExtra("labeltype");
//                MainActivity.mainActivity.pushJGQ(url,name,labeltype);
//            }else if(pushType.equals("4")){
//                String url=intent.getStringExtra("advertisementlink");
//                String jumptype=intent.getStringExtra("jumptype");
//                MainActivity.mainActivity.BannerClick(url,jumptype);
//            }
//        }
        String content=intent.getStringExtra("GetuiPayload");
        if (MainActivity.mainActivity!=null){
         MainActivity.mainActivity.doGetui(content);
        }
    }
}