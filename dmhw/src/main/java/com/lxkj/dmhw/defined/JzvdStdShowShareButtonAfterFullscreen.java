package com.lxkj.dmhw.defined;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.dialog.VideoShareDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

/**
 * 视频全屏分享
 */
public class JzvdStdShowShareButtonAfterFullscreen extends JzvdStd {
    ImageView shareButton;
    VideoShareDialog dialog;
    ImageView start;
    public static String title;
    public static String url;
    public static String imgurl;
    public JzvdStdShowShareButtonAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdShowShareButtonAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void init(Context context) {
        super.init(context);
        start= findViewById(R.id.start);
        shareButton = findViewById(R.id.fullscreen_share);
        shareButton.setOnClickListener(this);
        replayTextView.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_std_with_share;
    }

    @Override
    public void onClick(View v) {
        if (jzDataSource!=null){
            try {
                super.onClick(v);
            }catch (Exception e){

            }
        }
//        if (v.getId() == R.id.start) {
//            Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
//            if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
//                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (currentState == CURRENT_STATE_NORMAL) {
//                if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
//                        jzDataSource.getCurrentUrl().toString().startsWith("/") &&
//                        !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
//                    showWifiDialog();
//                    return;
//                }
//                startVideo();
//                onEvent(JZUserAction.ON_CLICK_START_ICON);//开始的事件应该在播放之后，此处特殊
//            } else if (currentState == CURRENT_STATE_PLAYING) {
//                onEvent(JZUserAction.ON_CLICK_PAUSE);
//                Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
//                JZMediaManager.pause();
//                onStatePause();
//            } else if (currentState == CURRENT_STATE_PAUSE) {
//                onEvent(JZUserAction.ON_CLICK_RESUME);
//                JZMediaManager.start();
////                onStatePlaying();
//                currentState = CURRENT_STATE_PLAYING;
//                startProgressTimer();
//                startDismissControlViewTimer();
//            } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
//                onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
//                startVideo();
//            }
//        }
        if (v.getId() == R.id.fullscreen_share) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareVideo"), false, 0);
        }
        if (v.getId() == R.id.replay_text) {
            startVideo();
        }
    }
    @Override
    public void startVideo() {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
        initTextureView();
        addTextureView();
        AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        JZMediaManager.setDataSource(jzDataSource);
        JZMediaManager.instance().positionInList = positionInList;
        onStatePreparing();
    } else {
      super.startVideo();
    }
    }

    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            shareButton.setVisibility(View.VISIBLE);
            batteryTimeLayout.setVisibility(GONE);
            titleTextView.setVisibility(VISIBLE);
        } else {
            if (dialog!=null){
                dialog.hideDialog();
            }
            titleTextView.setVisibility(GONE);
            shareButton.setVisibility(View.INVISIBLE);
        }
        ViewGroup.LayoutParams lp = loadingProgressBar.getLayoutParams();
        lp.height =(int) getResources().getDimension(R.dimen.dp_28);
        lp.width =(int) getResources().getDimension(R.dimen.dp_28);
    }

    @Override
    public void onAutoCompletion() {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            onStateAutoComplete();
        } else {
            super.onAutoCompletion();
        }
    }

    public void setdata(ComCollArticle comCollArticle,String shareurl,String thumurl){
        title=comCollArticle.getTitle();
        url=shareurl;
        imgurl=thumurl;
        dialog = new VideoShareDialog(getContext(), comCollArticle);
        dialog.showDialog();
    }

 @Override
 public boolean onTouch(View v, MotionEvent event) {
     float x = event.getX();
     float y = event.getY();
     int id = v.getId();
     if (id == R.id.surface_container) {
         switch (event.getAction()) {
             case MotionEvent.ACTION_DOWN:
                 break;
             case MotionEvent.ACTION_MOVE:
                 Log.i(TAG, "onTouch surfaceContainer actionMove [" + this.hashCode() + "] ");
                 float deltaX = x - mDownX;
                 float deltaY = y - mDownY;
                 float absDeltaX = Math.abs(deltaX);
                 float absDeltaY = Math.abs(deltaY);
                     if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                         if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                             cancelProgressTimer();
                             if (absDeltaX >= THRESHOLD) {
                                 // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                                 // 否则会因为mediaplayer的状态非法导致App Crash
                                 if (currentState != CURRENT_STATE_ERROR) {
                                     mChangePosition = true;
                                     mGestureDownPosition = getCurrentPositionWhenPlaying();
                                 }
                             } else {
                                 //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                                 if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                                     mChangeBrightness = true;
                                     WindowManager.LayoutParams lp = JZUtils.getWindow(getContext()).getAttributes();
                                     if (lp.screenBrightness < 0) {
                                         try {
                                             mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                             Log.i(TAG, "current system brightness: " + mGestureDownBrightness);
                                         } catch (Settings.SettingNotFoundException e) {
                                             e.printStackTrace();
                                         }
                                     } else {
                                         mGestureDownBrightness = lp.screenBrightness * 255;
                                         Log.i(TAG, "current activity brightness: " + mGestureDownBrightness);
                                     }
                                 } else {//右侧改变声音
                                     mChangeVolume = true;
                                     mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                 }
                             }
                         }
                     }
                 if (mChangePosition) {
                     long totalTimeDuration = getDuration();
                     mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                     if (mSeekTimePosition > totalTimeDuration)
                         mSeekTimePosition = totalTimeDuration;
                     String seekTime = JZUtils.stringForTime(mSeekTimePosition);
                     String totalTime = JZUtils.stringForTime(totalTimeDuration);

                     showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                 }
                 if (mChangeVolume) {
                     deltaY = -deltaY;
                     int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                     int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                     mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                     //dialog中显示百分比
                     int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
                     showVolumeDialog(-deltaY, volumePercent);
                 }

                 if (mChangeBrightness) {
                     deltaY = -deltaY;
                     int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
                     WindowManager.LayoutParams params = JZUtils.getWindow(getContext()).getAttributes();
                     if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                         params.screenBrightness = 1;
                     } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                         params.screenBrightness = 0.01f;
                     } else {
                         params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                     }
                     JZUtils.getWindow(getContext()).setAttributes(params);
                     //dialog中显示百分比
                     int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
                     showBrightnessDialog(brightnessPercent);
//                        mDownY = y;
                 }
                 break;
             case MotionEvent.ACTION_UP:
                 break;
         }
     }
     return super.onTouch(v, event);
 }
}