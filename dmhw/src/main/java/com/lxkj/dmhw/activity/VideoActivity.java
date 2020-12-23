package com.lxkj.dmhw.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JzvdStdShowShareButtonAfterFullscreen;
import com.lxkj.dmhw.utils.AlbumNotifyHelper;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends BaseActivity {

    @BindView(R.id.video_player)
    JzvdStdShowShareButtonAfterFullscreen videoPlayer;
    @BindView(R.id.video_save)
    ImageView videoSave;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.back)
    LinearLayout back;
    private String url, title;
    private boolean isPlayResume=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            Utils.isTranslucentOrFloating(this);
        }
        url = getIntent().getExtras().getString("videoUrl");
        title = getIntent().getExtras().getString("title");
        if(title.equals("")){
            title_tv.setText("视频教程");
        }else{
            title_tv.setText(title);
        }
        JzvdStd.releaseAllVideos();
        JzvdStd.clearSavedProgress(this,url);
        JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
        //权限全部获取
        if (Utils.checkPermision(this,1006, true)) {
            play();
        }else{
//            videoPlayer.bottomLayout.setVisibility(View.GONE);
        }
    }

    private void play(){
        videoPlayer.setUp(url, title, JzvdStd.SCREEN_WINDOW_NORMAL);
        videoPlayer.startButton.performClick();
        videoPlayer.fullscreenButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayResume) {
            JzvdStd.goOnPlayOnResume();
            isPlayResume = false;
        }
    }

    @Override
    protected void onPause() {
        try{
            super.onPause();
            JzvdStd.goOnPlayOnPause();
        }catch (Exception e){
        }
        isPlayResume = true;
    }

    @OnClick({R.id.video_save,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.video_save:
                if (!Utils.checkPermision(this,1006,true)) {
                    return;
                }
                videoPlayer.startButton.performClick();
                showProgressDialog("提示","视频下载中...");
                int end = url.lastIndexOf(".");
                String Suffix = url.substring(end);
                String path="";
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    path = Variable.videoPath1;
                }else{
                    path = Variable.videoPath;
                }
                FileDownloader.getImpl().create(url).setPath(path + "/" + System.currentTimeMillis() + Suffix).setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        AlbumNotifyHelper.insertVideoToMediaStore(VideoActivity.this, task.getPath(), 0, 20000);
                        //scanFile不可少，才能插入视频数据库
                        AlbumNotifyHelper.scanFile(VideoActivity.this,task.getPath());
                        ToastUtil.showImageToast(VideoActivity.this,"视频下载成功",R.mipmap.toast_img);
                        hideProgressDialog();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        hideProgressDialog();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
                break;
        }
    }
    /*
     * 提示加载
     */
    private ProgressDialog progressDialog;
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(VideoActivity.this, title,
                    message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }

        progressDialog.show();

    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return;
        }
        isFinish();
    }
//    /**
//     * 处理权限返回状态
//     * @param requestCode 请求状态
//     * @param permissions
//     * @param grantResults 授权结果
//     */
//    String[] permissions = new String[]{
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE};
//    List<String> mPermissionList = new ArrayList<>();
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        try {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            if (requestCode == Utils.WRITE_EXTERNAL_STORAGE_REQUEST) {
//                mPermissionList.clear();
//                for (int i = 0; i < permissions.length; i++) {
//                    if (ActivityCompat.checkSelfPermission(VideoActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
//                        mPermissionList.add(permissions[i]);
//                    }
//                }
//                if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//                    // 有权限了b
//                    Utils.haspermission=true;
//                    videoPlayer.bottomLayout.setVisibility(View.VISIBLE);
//                    play();
//                  return;
//                }
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        // 授权成功
//                    } else {
//                        Utils.haspermission=false;
//                        // 授权失败
//                        switch (permissions[i]) {
//                            case Manifest.permission.READ_PHONE_STATE:// 手机状态
//                                permissionsPrompt("获取手机信息");
//                                break;
//                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:// 储存空间
//                                permissionsPrompt("读写手机存储");
//                                break;
//                        }
//                        return;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Logger.e(e, "");
//        }
//    }
//
//    /**
//     * 没有权限提醒
//     * @param permissions
//     */
//    private void permissionsPrompt(String permissions) {
//        JurisdictionDialog dialog = new JurisdictionDialog(this, permissions);
//        dialog.showDialog();
//    }
}
