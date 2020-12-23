package com.lxkj.dmhw.receiver;

/**
 * 推送服务运行的载体
 */
public class GTPushService  extends com.igexin.sdk.PushService {
//    private static Handler handler = new Handler();
//    private Runnable sRunnable = new RequestNotificationRunnable();
//
//    protected static class RequestNotificationRunnable implements Runnable {
//        @Override
//        public void run() {
//            String regId = com.heytap.mcssdk.PushManager.getInstance().getRegisterID();
//            if (TextUtils.isEmpty(regId)) {
//                handler.postDelayed(this, 2000);
//            } else {
//                com.heytap.mcssdk.PushManager.getInstance().requestNotificationPermission();
//            }
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (com.heytap.mcssdk.PushManager.isSupportPush(this)) {
//            handler.postDelayed(sRunnable, 1500);
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//        }
//    }
}
