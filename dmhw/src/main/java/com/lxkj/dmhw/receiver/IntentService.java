package com.lxkj.dmhw.receiver;

import android.content.Context;
import android.content.Intent;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息
 * onReceiveMessageData 处理透传消息
 * onReceiveClientId 接收 cid
 * onReceiveOnlineState cid 离线上线通知
 * onReceiveCommandResult 各种事件处理回执
 */
public class IntentService extends GTIntentService {

    public IntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    /**
     * 处理透传消息
     * @param context
     * @param msg
     *这里是处理透传消息。注意注意 离线的消息厂商通道的要在后端服务器配置Intent（按照个推的规则）
     */
//    @Override
//    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
//        String content = new String(msg.getPayload());
//        try {
//            JSONObject object = new JSONObject(content);
//            Intent intent = null;
//            boolean useBrocast=false;
//            switch (object.getString("parameters")) {
//                case "1":
//                    intent = new Intent(context, Class.forName(object.getString("activity")));
//                    intent.putExtra(Variable.webUrl, object.getString("link"));
//                    intent.putExtra("isFinish", true);
//                    break;
//                case "2":
//                    intent = new Intent(context, Class.forName(object.getString("activity")));
//                    intent.putExtra("shopUrl", object.getString("link"));
//                    intent.putExtra("isPlay", false);
//                    intent.putExtra("isFinish", true);
//                    break;
//                case "4"://功能和轮播图一样
//                    String advertisementlink=object.getString("advertisementlink");
//                    switch (object.getString("jumptype")) {
//                        case "00":
//                            intent = new Intent(context, Class.forName("com.lxkj.dmhw.activity.AliAuthWebViewActivity"));
//                            intent.putExtra(Variable.webUrl,advertisementlink);
//                            intent.putExtra("isFinish", true);
//                            break;
//                        case "01":
//                            intent = new Intent(context,Class.forName("com.lxkj.dmhw.activity.CommodityActivity"));
//                            intent.putExtra("id", advertisementlink);
//                            intent.putExtra("isPlay", true);
//                            intent.putExtra("isFinish", true);
//                            break;
//                        case "03":
//                            intent = new Intent(context, Class.forName("com.lxkj.dmhw.activity.AliAuthWebViewActivity"));
//                            intent.putExtra(Variable.webUrl,advertisementlink);
//                            intent.putExtra("isTitle" , true);
//                            intent.putExtra("isFinish", true);
//                            break;
//                        case "04":
//                            intent = new Intent(context, Class.forName("com.lxkj.dmhw.activity.NewActivity"));
//                            intent.putExtra("labelType", "09");
//                            intent.putExtra("topicId", advertisementlink);
//                            intent.putExtra("isFinish", true);
//                            break;
//                         case "14":
//                           useBrocast = true;
//                           intent = new Intent(context, NotificationClickReceiver.class); //点击通知之后要发送的广播
//                           intent.putExtra("url", advertisementlink);
//                           intent.putExtra("jumptype", "14");
//                            break;
//                        //官方活动
//                        case "11":
//                            // 请求活动链接
//                            if (DateStorage.getLoginStatus()){
//                                if (MainActivity.mainActivity!=null){
//                                    //使用通知
//                                     useBrocast=true;
//                                     intent= new Intent(context, NotificationClickReceiver.class); //点击通知之后要发送的广播
//                                     intent.putExtra("advertisementlink", advertisementlink);
//                                     intent.putExtra("jumptype","11");
//                                }else{
//                                    intent =new Intent(context, Class.forName("com.lxkj.dmhw.activity.MainActivity"));
//                                    intent.putExtra("advertisementlink", advertisementlink);
//                                    intent.putExtra("jumptype","11");
//                                }
//                            }else{
//                                intent =new Intent(context,Class.forName("com.lxkj.dmhw.activity.LoginActivity"));
//                                intent.putExtra("isFinish", true);
//                            }
//                            break;
//                        case "12":
//                            intent = new Intent(context, Class.forName("com.lxkj.dmhw.activity.AliAuthWebViewActivity"));
//                            intent.putExtra(Variable.webUrl, advertisementlink);
//                            intent.putExtra("isTitle" , false);
//                            intent.putExtra("noGoTaoBaoH5",12);
//                            intent.putExtra("isFinish", true);
//                            break;
//                    }
//                    intent.putExtra("push","4");
//                    break;
//                case "5"://金刚区
//                    if (DateStorage.getLoginStatus()){
//                        if (MainActivity.mainActivity!=null){
//                            //使用通知
//                            useBrocast=true;
//                            intent= new Intent(context, NotificationClickReceiver.class); //点击通知之后要发送的广播
//                            intent.putExtra("url", object.getString("url"));
//                            intent.putExtra("name",object.getString("name"));
//                            intent.putExtra("labeltype",object.getString("labeltype"));
//                        }else{
//                            intent =new Intent(context, Class.forName("com.lxkj.dmhw.activity.MainActivity"));
//                            intent.putExtra("url", object.getString("url"));
//                            intent.putExtra("name",object.getString("name"));
//                            intent.putExtra("labeltype",object.getString("labeltype"));
//                        }
//                        intent.putExtra("push","5");
//                    }else{
//                        intent =new Intent(context,Class.forName("com.lxkj.dmhw.activity.LoginActivity"));
//                        intent.putExtra("isFinish", true);
//                    }
//                    break;
//                default:
//                    intent = new Intent(context, Class.forName(object.getString("activity")));
//                    intent.putExtra("isFinish", true);
//                    break;
//            }
//            if (intent!=null){
//                //查看推送任务标志
//                intent.putExtra("fromGetui",true);
//            }
//            Utils.Notification(context, object.getString("title"), object.getString("content"), intent, R.mipmap.push, R.mipmap.push_small,useBrocast);
//        } catch (JSONException | ClassNotFoundException e) {
//            Logger.e(e, "处理透传消息");
//        }
//    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String content = new String(msg.getPayload());
        try {
            JSONObject object = new JSONObject(content);
            Intent intent = null;
            boolean useBrocast=true;
            intent= new Intent(context, NotificationClickReceiver.class); //点击通知之后要发送的广播
            intent.putExtra("GetuiPayload", content);
            Utils.Notification(context, object.getString("title"), object.getString("content"), intent, R.mipmap.push, R.mipmap.push_small,useBrocast);
        } catch (Exception e) {
            Logger.e(e, "处理透传消息");
        }
    }



    /**
     * 接收CID
     * @param context
     * @param clientid
     */
    @Override
    public void onReceiveClientId(Context context, String clientid) {
//        Logger.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    /**
     * CID离线上线通知
     * @param context
     * @param online
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {

    }

    /**
     * 各种事件处理回执
     * @param context
     * @param cmdMessage
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}
