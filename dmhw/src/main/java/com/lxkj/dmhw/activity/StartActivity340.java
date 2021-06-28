package com.lxkj.dmhw.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.WindowManager;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.data.OtherOpen;
import com.lxkj.dmhw.data.ScreenOpen;
import com.lxkj.dmhw.dialog.UserPermissionDialog;
import com.lxkj.dmhw.utils.ActivityManagerDefine;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 启动页
 */
public class StartActivity340 extends SupportActivity {
    // 请求权限返回值
    private int WRITE_EXTERNAL_STORAGE_REQUEST = 1;
    //  尽管我们在AndroidManifest里面申请了权限加载，但是在使用过称中，发现这些权限还是没有，什么原因呢？因为android在6.0版本以后，我们需要动态加载它，今天带领小伙伴们看看怎么在android6.0程序的入口处写动态加载权限吧。
//    首先我们定义一个Avtivity作为程序的第一个入口WelcomeActivity：
//    定义一个权限数组：
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    List<String> mPermissionList = new ArrayList<>();
    boolean mShowRequestPermission = true;//用户是否禁止权限然后写一个方法checkPermision()检查程序是否已开启本权限
    OtherOpen open;
    ScreenOpen screenOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            //解决部分手机 小米8se等 第一次安装，程序回退后台，再次打开会重启startactivity的问题
            if(!isTaskRoot()){
                isFinish();
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                try{
                    Variable.statusBarHeight = getResources().getDimensionPixelSize(resourceId);
                }catch (Exception e){
                }
            }

            // 栈管理
            ActivityManagerDefine.getInstance().pushActivity(this);
            open = new OtherOpen();
            screenOpen = new ScreenOpen();
            if (open.findStatus()) {
                UserPermissionDialog userPermissionDialog=new UserPermissionDialog(this,"");
                userPermissionDialog.showDialog();
                userPermissionDialog.setOnClickListener(new UserPermissionDialog.OnBtnClickListener() {
                    @Override
                    public void onClick(int pos) {
                      if (pos==1){
                      init();
                      }else{
                       finish();
                      }
                    }
                });
            }else{
              init();
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 跳转
     */
    private void init() {
            if (open.findStatus()) {
                startActivity(new Intent(StartActivity340.this, GuideActivity.class));
            } else {
                if (screenOpen.find()!= null){
                 File file =new File(screenOpen.find().getPath());
                 if (file.exists()&&ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                     startActivity(new Intent(StartActivity340.this, ScreenActivity.class));
                 }else{
                     startActivity(new Intent(StartActivity340.this, MainActivity.class));
                 }
                }else{
                    startActivity(new Intent(StartActivity340.this, MainActivity.class));
                }
            }
            isFinish();
    }


    /**
     * 关闭页面
     */
    public void isFinish() {
        try {
            finish();
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
