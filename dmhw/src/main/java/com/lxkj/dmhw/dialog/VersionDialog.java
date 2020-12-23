package com.lxkj.dmhw.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.lxkj.dmhw.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 更新版本
 */
public class VersionDialog implements View.OnClickListener {

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载保存路径 */
    private String mSavePath = Variable.HomePath + Utils.getStringToDate(Utils.getCurrentDate("yyyy年MM月dd日"), "yyyy年MM月dd日") + ".apk";
    /* 下载url */
    private String url;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    /* 更新进度条 */
    private ProgressBar mProgress;
    private TextView progressText;

    private boolean isPlay;
    private Activity activity;
    private Dialog dialog = null;

    private Dialog mDownloadDialog;

    public Dialog getDialog() {
        return dialog;
    }

    private String VersionCode="";
    /**
     * 版本更新提示
     * @param activity 调用的页面
     * @param isPlay 是否强制更新，true为开启
     * @param version 最新版本
     * @param content 更新内容
     * @param url apk下载地址
     */
    public VersionDialog(Activity activity, boolean isPlay, String version, String content, String url) {
        this.url = url;
        this.activity = activity;
        this.isPlay = isPlay;
        this.VersionCode=version;
        init(version,content);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    progressText.setText(progress + "%");
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    try{
                     installApk();
                    }catch (Exception e){
                    }
                    break;
                default:
                    break;
            }
        };
    };

    public void init(String version,String content) {
        View view = LinearLayout.inflate(activity, R.layout.dialog_version, null);
        view.findViewById(R.id.dialog_assign).setOnClickListener(this);
        view.findViewById(R.id.dialog_cancel).setOnClickListener(this);
        dialog = new Dialog(activity, R.style.myTransparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = dialog.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            dialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (isPlay) {
            view.findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
            dialog.setCancelable(false);
            dialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false;
                }
            });
        } else {
            view.findViewById(R.id.dialog_cancel).setVisibility(View.VISIBLE);
            dialog.setCancelable(true);
        }
        ((TextView) dialog.getWindow().findViewById(R.id.dialog_version)).setText(version.substring(0, 1) + "." + version.substring(1, 2) + "." + version.substring(2, 3));
        ((TextView) dialog.getWindow().findViewById(R.id.dialog_content)).setText(content.replace("\\n","\n"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_assign:
                if (!Utils.checkPermision(activity,1012,true)) {
                    return;
                }
                showDownloadDialog();
                dialog.dismiss();
                break;
            case R.id.dialog_cancel:
                //记住忽略的版本，本地记录
                DateStorage.setIgnoreVersion(VersionCode);
                dialog.dismiss();
                break;
        }
    }

    /**
     * 显示软件下载对话框
     */
    public void showDownloadDialog() {
        View view = LinearLayout.inflate(activity, R.layout.softupdate_progress, null);
        mDownloadDialog = new Dialog(activity, R.style.myTransparent);
        mDownloadDialog.setCanceledOnTouchOutside(true);
        mDownloadDialog.setContentView(view);
        mDownloadDialog.setCancelable(false);
        mDownloadDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                return true;
            } else {
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = mDownloadDialog.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            mDownloadDialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mProgress = view.findViewById(R.id.update_progress);
        progressText = view.findViewById(R.id.ps);

        mDownloadDialog.show();
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 获得存储卡的路径
                URL url = new URL(VersionDialog.this.url);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                // 获取文件大小
                int length = conn.getContentLength();
                // 创建输入流
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(mSavePath);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWNLOAD);
                    if (numread <= 0) {
                        // 下载完成
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (!cancelUpdate);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 给目标应用一个临时授权
           intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           uri = FileProvider.getUriForFile(activity, "com.lxkj.dmhw.fileprovider",new File(mSavePath));
        } else {
            uri = Uri.fromFile(new File(mSavePath));
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        if (isPlay) {
            activity.finish();
            ActivityManagerDefine.getInstance().popActivity(activity);//退出页面时调用
        } else {
            mDownloadDialog.dismiss();
        }
    }
}
