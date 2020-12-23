package com.lxkj.dmhw.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.PermissionsCheckerUtil;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.logic.Constants;

import java.util.Date;

import static android.Manifest.permission.WAKE_LOCK;

/**
 * Created by 1 on 2015/12/26.
 */
public class TimeService extends Service {
    //    private boolean timeFlag;
    private static final String TAG = "TimeService";
    PowerManager.WakeLock mWakeLock;
    //    private Context context;
    private Handler handler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        context = this;
        LogUtil.i("0.0", "TimeService:onCreate()");
        handler = new Handler();
//        timeFlag = false;
//        SuDianApp.updateTimeFlag=true;
        handler.post(timerTask);
//        new Thread().start();
        acquireWakeLock();

    }


    @Override
    public void onDestroy() {
        LogUtil.i("0.0", "TimeService is Destory");
//        ToastUtil.show(context, "定时任务被终止");
        super.onDestroy();
//        timeFlag = true;
        if (handler != null) {
            handler.removeCallbacks(timerTask);
            handler = null;
        }
        releaseWakeLock();
//        context = null;
//        if(FarmApplication.getInstance().getSpUtil().getBoolean(this,Constants.EXIT,false)){
//            MyActivityManager.getInstance().finishAllActivity();
//            FarmApplication.getInstance().getSpUtil().putBoolean(this, PreferencesUtil.PREFERENCES_DEFAULT,Constants.EXIT,false);
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("0.0", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable timerTask = new Runnable() {
        @Override
        public void run() {
//            while (!timeFlag) {
//                Log.i("mihui","倒计时任务进行中");
//                if (context == null || context.getApplicationContext() == null) {
//                    break;
//                }
            Intent i = new Intent();
            i.setAction(Constants.TIME_TASK);
            LocalBroadcastManager.getInstance(TimeService.this).sendBroadcast(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            handler.postDelayed(timerTask, 1000);
            MyApplication.NOW_TIME += 1;
//                LogUtil.i("0.0","倒计时任务进行中:"+ SuDianApp.NOW_TIME);
//                handler.sendEmptyMessage(1);
//            }
        }
    };

//
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            Log.i("lhp", DateUtil.getDateTime(new Date(SuDianApp.NOW_TIME)));
//            ToastUtil.show(TimeService.this, DateUtil.getDateTime(new Date(SuDianApp.NOW_TIME)));
//        }
//    };

    //申请设备电源锁
    @SuppressLint("InvalidWakeLockTag")
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    //释放设备电源锁
    private void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

}
