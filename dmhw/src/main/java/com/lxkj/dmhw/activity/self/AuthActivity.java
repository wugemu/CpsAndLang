package com.lxkj.dmhw.activity.self;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.http.ExecutorServiceUtil;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BitmapUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.FileUtil;
import com.example.test.andlang.util.ImageUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.PermissionsCheckerUtil;
import com.example.test.andlang.util.PicSelUtil;
import com.example.test.andlang.util.StorageUtils;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.MineModel;
import com.lxkj.dmhw.presenter.MinePresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;
import com.lxkj.dmhw.widget.dialog.SelectPicPopupWindow;

import java.io.File;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthActivity extends BaseLangActivity<MinePresenter> {
    private  final int REQUESTCODE_TAKE=100;//拍照
    private  final int REQUESTCODE_PICK=101;//本地
    private final int CROP_PHOTO = 103;// 返回码：剪裁

    @BindView(R.id.et_real_name)
    CleanableEditText et_real_name;
    @BindView(R.id.et_idcard)
    CleanableEditText et_idcard;
    @BindView(R.id.iv_idcard_1)
    ImageView iv_idcard_1;
    @BindView(R.id.iv_idcard_2)
    ImageView iv_idcard_2;
    @BindView(R.id.iv_delete1)
    ImageView iv_delete1;
    @BindView(R.id.iv_delete2)
    ImageView iv_delete2;
    @BindView(R.id.ll_yrz_tip)
    LinearLayout ll_yrz_tip;
    @BindView(R.id.tv_cardtip)
    TextView tv_cardtip;
    @BindView(R.id.tv_lxkf)
    TextView tv_lxkf;
    @BindView(R.id.lang_tv_right)
    TextView lang_tv_right;

    private String image1, image2;
    private int space;
    private int imageW;

    private int flag;//flag代表需要拍照的图片，1=身份证正面，2=身份证反面
    // 图片格式的URI对象
    private Uri imageUri;
    // 临时保存头像的缓存文件
    private File imageCache;
    private boolean checkFlg = true;//是否需要校验
    private boolean isUpImg;
    private boolean isFirst;//是否第一次认证
    private boolean isNoEdit;//true不可以修改  false可以修改
    private UserAccount userAccount;
    private boolean selectAuth;//是否是选择实名认证


    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public void initView() {
        initLoading();
//        et_real_name.setFilters(new InputFilter[]{new MyInputFilter.LengthFilter(20),new MyInputFilter()});
        space = (int) (3 * getResources().getDimension(R.dimen.dp_30));
        imageW = (BBCUtil.getDisplayWidth(this) - space) / 2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imageW, (int) (imageW * 84.0 / 133));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_idcard_1.setLayoutParams(params);
        iv_idcard_2.setLayoutParams(params);

        tv_lxkf.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_lxkf.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void initPresenter() {
        presenter=new MinePresenter(AuthActivity.this, MineModel.class);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            userAccount=(UserAccount) intent.getSerializableExtra("userAccount");
            isNoEdit=intent.getBooleanExtra("isNoEdit",false);
            isFirst=intent.getBooleanExtra("isFirst",false);
            selectAuth=intent.getBooleanExtra("selectAuth",false);
            initTitleBar(true, "购物实名认证", "保存", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reqSave();
                }
            });
            tv_cardtip.setText("身份证件照（选填）");

            if(isNoEdit) {
                //不可以修改
                lang_tv_right.setVisibility(View.GONE);
                ll_yrz_tip.setVisibility(View.VISIBLE);
            }else {
                //可以修改
                lang_tv_right.setVisibility(View.VISIBLE);
                ll_yrz_tip.setVisibility(View.GONE);
            }

            if(userAccount!=null){
                checkFlg = false;
                et_real_name.setText(userAccount.getRealName());
                et_real_name.setTextColor(getResources().getColor(R.color.colorBlackText2));
                et_real_name.hiddenRightDraw();
                et_real_name.setEnabled(false);
                et_real_name.mRightDrawable=null;
                et_idcard.setText(userAccount.getCardNo().substring(0,3)+"**********"+userAccount.getCardNo().substring(userAccount.getCardNo().length()-4));
                et_idcard.setTextColor(getResources().getColor(R.color.colorBlackText2));
                et_idcard.hiddenRightDraw();
                et_idcard.setEnabled(false);
                if(!BBCUtil.isEmpty(userAccount.getCredentialsUrl())) {
                    String imgstr=userAccount.getCredentialsUrl();
                    if(imgstr.startsWith(",")){
                        imgstr=imgstr.substring(1);
                    }
                    String[] imgs = imgstr.split(",");
                    if(imgs.length==2){
                        image1=imgs[0];
                        image2=imgs[1];
                        ImageLoadUtils.doLoadImageUrl(iv_idcard_1,image1);
                        ImageLoadUtils.doLoadImageUrl(iv_idcard_2,image2);
                        if(isNoEdit){
                            iv_delete1.setVisibility(View.GONE);
                            iv_delete2.setVisibility(View.GONE);
                        }else {
                            iv_delete1.setVisibility(View.VISIBLE);
                            iv_delete2.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }else {
                et_real_name.setTextColor(getResources().getColor(R.color.colorBlackText));
                et_idcard.setTextColor(getResources().getColor(R.color.colorBlackText));
            }
        }
    }

    @OnClick(R.id.tv_lxkf)
    public void goLXKF(){
        //联系客服
//        SDJumpUtil.goZXKF(AuthActivity.this);
    }


    @OnClick({R.id.iv_idcard_1, R.id.iv_idcard_2,R.id.iv_delete1,R.id.iv_delete2})
    public void onClick(View view) {
        if(isNoEdit){
            //不可以修改
            return;
        }
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
        switch (view.getId()) {
            case R.id.iv_delete1:
            case R.id.iv_idcard_1:
                //图片弹框
                if(isUpImg){
                    ToastUtil.show(AuthActivity.this,"图片上传中，请稍后");
                    return;
                }
                flag = 1;
                SelectPicPopupWindow menuWindow1 = new SelectPicPopupWindow(AuthActivity.this,handler,REQUESTCODE_TAKE,REQUESTCODE_PICK);
                //显示窗口
                menuWindow1.showAtLocation(iv_idcard_1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.iv_delete2:
            case R.id.iv_idcard_2:
                //图片弹框
                if(isUpImg){
                    ToastUtil.show(AuthActivity.this,"图片上传中，请稍后");
                    return;
                }
                flag = 2;
                SelectPicPopupWindow menuWindow2 = new SelectPicPopupWindow(AuthActivity.this,handler,REQUESTCODE_TAKE,REQUESTCODE_PICK);
                //显示窗口
                menuWindow2.showAtLocation(iv_idcard_1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

                break;


        }
    }

    public void reqSave(){
        //保存
        if(ButtonUtil.isFastDoubleClick(R.id.lang_tv_right)){
            ToastUtil.show(AuthActivity.this,R.string.tip_btn_fast);
            return;
        }
        if (checkInput())
        {
            String cardNo="";
            if (et_idcard.getText().toString().trim().contains("*")&&userAccount!=null) {
                cardNo = userAccount.getCardNo();
            } else {
                cardNo = et_idcard.getText().toString().trim();
            }
            String realName=et_real_name.getText().toString().trim();

            String credentialsUrl="";
            if(!BBCUtil.isEmpty(image1)&&!BBCUtil.isEmpty(image2)){
                credentialsUrl=image1+","+image2;
            }
            boolean ifFlag=false;
            if(isFirst) {
                //第一次购物认证默认
                ifFlag=true;
            }else {
                if(userAccount!=null){
                    ifFlag=userAccount.isIfFlag();
                }else {
                    ifFlag=false;
                }
            }
            showWaitDialog();
            if(userAccount==null) {
                presenter.reqAddAuth(realName, cardNo, credentialsUrl, ifFlag,"1",false);
            }else {
                presenter.reqEditAuth(credentialsUrl,userAccount.getId(),3);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if("uploadImage".equals(arg)){
            //图片上传成功
            String upImgUrl=presenter.model.getUpImgUrl();
            if(flag==1){
                image1=upImgUrl;
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        ImageLoadUtils.doLoadImageUrl(iv_idcard_1,image1);
                        iv_delete1.setVisibility(View.VISIBLE);
                    }
                });
            }else if(flag==2){
                image2=upImgUrl;
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        ImageLoadUtils.doLoadImageUrl(iv_idcard_2,image2);
                        iv_delete2.setVisibility(View.VISIBLE);
                    }
                });
            }
            isUpImg=false;
        }else if("reqAddAuth".equals(arg)){

            //保存实名认证成功
            if(!selectAuth) {
                ActivityUtil.getInstance().exitResult(AuthActivity.this, null, RESULT_OK);
            }else {
                String cardNo=et_idcard.getText().toString().trim();
                userAccount=new UserAccount();
                userAccount.setCardNo(cardNo.substring(0,3)+"**********"+cardNo.substring(cardNo.length()-4));
                userAccount.setRealName(et_real_name.getText().toString().trim());
                if(!BBCUtil.isEmpty(presenter.model.getAuthId())) {
                    userAccount.setId(presenter.model.getAuthId());
                }
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("userAccount",userAccount);
                intent.putExtras(bundle);
                ActivityUtil.getInstance().exitResult(AuthActivity.this,intent,RESULT_OK);

            }
        }else if("reqEditAuth".equals(arg)){
            //修改成功
            ActivityUtil.getInstance().exitResult(AuthActivity.this,null,RESULT_OK);
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent=null;
            switch (msg.what) {
                case REQUESTCODE_TAKE:
                    // 系统相机

                    // 缺少权限时, 进入权限配置页面
                    if (PermissionsCheckerUtil.lacksPermissions(AuthActivity.this, PicSelUtil.TAKE_PHOTO_PERMISSIONS)) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(AuthActivity.this, PicSelUtil.TAKE_PHOTO_PERMISSIONS,
                                PicSelUtil.TAKE_PHOTO_CODE);
                    } else {
                        startCemara();
                    }
                    break;

                case REQUESTCODE_PICK:

                    // 缺少权限时, 进入权限配置页面
                    if (PermissionsCheckerUtil.lacksPermissions(AuthActivity.this,PicSelUtil.WRITE_EXTERNAL_STORAGE_PERMISSIONS)) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(AuthActivity.this, PicSelUtil.WRITE_EXTERNAL_STORAGE_PERMISSIONS,
                                PicSelUtil.SELECT_PHOTO_CODE);
                    } else {
                        selectPhoto();
                    }

                    break;
                case CROP_PHOTO:
                    // 调用剪裁程序活动
                    intent = new Intent("com.android.camera.action.CROP");
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setDataAndType(imageUri, "image/*");
//                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 300);
                    intent.putExtra("outputY", 300);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                    break;

            }
        }
    };

    private void selectPhoto(){
        // 本地图册 消除No Activity found to handle Intent 错误
        try {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, null);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(intent, REQUESTCODE_PICK);
        }catch (Exception e){

        }
    }

    private void startCemara() {
        // 系统相机
        try {
            Intent intent2 = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = PicSelUtil.getImageCacheUri(this, PicSelUtil.CACHE_FILE_NAME[0]);
            intent2.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);
            startActivityForResult(intent2, REQUESTCODE_TAKE);
        }catch (Exception e){
            LogUtil.e("0.0",e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                // 相册
                try {
                    if (resultCode == RESULT_OK) {
                        Uri uri = ImageUtil.getUri(data, AuthActivity.this.getContentResolver());//解决小米手机上获取图片路径为null的情况\
                        if(uri != null) {
                            // 取得SDCard图片路径做显示
                            String path = BBCUtil.getFilePathFromContentUri(uri, this.getContentResolver());
                            File imgSrc = new File(path);
                            // 获得uri
                            imageUri = FileUtil.file2Uri(this, imgSrc);
                            imageCache = FileUtil.getFileFromUri(this,imageUri);
                            if (imageCache != null) {
                                showWaitDialog();
                                ExecutorServiceUtil.getInstence().execute(uploadImageRunnable);
                            }
                            // 打开剪裁程序
//                            Message message = new Message();
//                            message.what = CROP_PHOTO;
//                            message.obj = path;
//                            handler.sendMessage(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();// 用户点击取消操作，其他错误
                }
                break;
            case REQUESTCODE_TAKE:
                // 相机
                if (resultCode == RESULT_OK) {
                    try {
                        imageCache = FileUtil.getFileFromUri(this,imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (imageCache != null) {
                        showWaitDialog();
                        ExecutorServiceUtil.getInstence().execute(uploadImageRunnable);
                    }
//                    handler.sendEmptyMessage(CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                // 剪裁程序返回
                // 当剪裁程序返回的结果是正确的时候
                if (resultCode == RESULT_OK) {
                    // 获取图片
                    try {
                        imageCache = FileUtil.getFileFromUri(this,imageUri);
                        if (imageCache != null) {
                            showWaitDialog();
                            ExecutorServiceUtil.getInstence().execute(uploadImageRunnable);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    Runnable uploadImageRunnable = new Runnable() {
        public void run() {
            // 要上传的图片文件
            uploadFileInThreadByOkHttp(Constants.HOST + Constants.UP_MINE_HEADIMG,imageCache,"2");
        }
    };

    // 压缩图片并上传
    private  void uploadFileInThreadByOkHttp(final String actionUrl, final File tempPic,String type) {
        //图片开始上传标示
        isUpImg=true;

        String pic_path = tempPic.getAbsolutePath();
        File targetFile=new File(StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), Constants.imageDir),tempPic.getName());
        final String compressImage = BitmapUtil.compressImage(pic_path, targetFile.getAbsolutePath(), 100);
        final File compressedPic = new File(compressImage);
        //调用压缩图片的方法，返回压缩后的图片path
        presenter.uploadImage(AuthActivity.this, compressedPic,actionUrl,false,type);
    }

    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    //根据requestCode和grantResults(授权结果)做相应的后续处理
    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == PicSelUtil.SELECT_PHOTO_CODE) {
            if(grantResults==null||grantResults.length==0){
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 开启读写权限
                selectPhoto();
            } else {
                ToastUtil.show(this, "请开启SD卡读写权限");
            }
        }
        if (requestCode == PicSelUtil.TAKE_PHOTO_CODE) {
            if(grantResults==null||grantResults.length<2){
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 开启拍照
                startCemara();
            } else {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.show(this, "请前往设置->应用->素店开启拍照权限");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.show(this, "请前往设置->应用->素店开启SD卡读写权限");
                } else {
                    ToastUtil.show(this, "请前往设置->应用->素店开启拍照和SD卡读写权限");
                }
            }
        }

    }

    private boolean checkInput() {
        if (BBCUtil.isEmpty(et_real_name.getText().toString().trim())) {
            ToastUtil.show(this, "请输入正确中文姓名");
            return false;
        }
        if(et_real_name.getText().toString().trim().length()<2){
            ToastUtil.show(this,"中文姓名不能少于两个字");
            return false;
        }
        if (BBCUtil.isEmpty(et_idcard.getText().toString().trim())) {
            ToastUtil.show(this, "请输入您的身份证号码");
            return false;
        }
//        if (checkFlg && !IDCardUtil.IDCardValidate(et_idcard.getText().toString().trim())) {
//            ToastUtil.show(this, "身份证格式不正确，请输入正确身份证号");
//            return false;
//        }
        if (!BBCUtil.isEmpty(image1) && BBCUtil.isEmpty(image2)) {
            ToastUtil.show(this, "请上传身份证件照");
            return false;
        }
        if (!BBCUtil.isEmpty(image2) && BBCUtil.isEmpty(image1)) {
            ToastUtil.show(this, "请上传身份证件照");
            return false;
        }
        return true;
    }
}