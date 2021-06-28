package com.lxkj.dmhw.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.ADActivity;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.BigBrandActivity;
import com.lxkj.dmhw.activity.BrandActivity;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.HotSellFragmentActivity;
import com.lxkj.dmhw.activity.LimitActivity;
import com.lxkj.dmhw.activity.LiveInspectListActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.MainBottomActivity;
import com.lxkj.dmhw.activity.MorePlSearchNewActivity;
import com.lxkj.dmhw.activity.MorePlatformNewActivity;
import com.lxkj.dmhw.activity.MorePlatformNewActivity340;
import com.lxkj.dmhw.activity.MyTaskFragmentActivity;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.activity.OnlyUrlWebViewActivity;
import com.lxkj.dmhw.activity.SortThreeFragmentActivity;
import com.lxkj.dmhw.activity.WebPagePJWActivity;
import com.lxkj.dmhw.activity.cps.TaobaoCouponActivity;
import com.lxkj.dmhw.bean.AppInfo;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.JDGoodsBean;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.GlideCircleTransform;
import com.lxkj.dmhw.defined.GlideRoundTransform;
import com.lxkj.dmhw.defined.GlideRoundTransformNew;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ali.auth.third.core.util.CommonUtils.toast;


/**
 * 处理型工具类
 * Created by Zyhant on 2016/10/24.
 */

public class Utils extends DataCleanManager {

    /**
     * 获取设备唯一标识
     * @return
     */
    public static String deviceId() {
        String deviceId = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        return deviceId;
    }

    /**
     * 处理Bitmap图片宽高
     * @param bm 所要转换的bitmap
     * @param newWidth 新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        try {
            if (bm == null) {
                return null;
            }
            // 获得图片的宽高
            int width = bm.getWidth();
            int height = bm.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        } catch (Exception e) {

        }
        return null;
    }
    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreeHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 根据宽度计算缩放图片
     * @param bitmap 所要转换的bitmap
     * @param newWidth 新的宽
     * @return 缩放完成的bitmap
     */
    public static Bitmap scaleWidth(Bitmap bitmap, int newWidth) {
        // 获取图片的宽高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 计算图片比例
        float scale = height / width;
        // 计算缩放高度
        int newHeight = (int) (newWidth * scale);
        return zoomImg(bitmap, newWidth, newHeight);
    }

    /**
     * 根据宽度计算缩放图片
     * @param bitmap 所要转换的bitmap
     * @param newHeight 新的宽
     * @return 缩放完成的bitmap
     */
    public static Bitmap scaleHeight(Bitmap bitmap, int newHeight) {
        // 获取图片的宽高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 计算图片比例
        float scale = width / height;
        // 计算缩放高度
        int newWidth = (int) (newHeight * scale);
        return zoomImg(bitmap, newWidth, newHeight);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 pixel(像素)
     * @param dpValue DP
     * @return PX
     */
    public static int dipToPixel(@DimenRes int dpValue) {
        return (int) MyApplication.getContext().getResources().getDimension(dpValue);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 pixel(像素)
     * @param spValue
     * @return
     */
    public static int spToPixel(@DimenRes int spValue) {
        return (int) MyApplication.getContext().getResources().getDimension(spValue);
    }

    /**
     * 复制文字到剪贴板
     * @param content
     * @return
     */
    public static void copyText(String content) {
        if (content == null || content.length() == 0) {
            return;
        }
        DateStorage.setClip(content);
        ClipboardManager manager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        assert manager != null;
        manager.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    /**
     * 调用QQ聊天
     * @param qq
     */
    public static void chatQQ(String qq) {
        if (checkApkExist("com.tencent.mqq") || checkApkExist("com.tencent.mobileqq")) {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
            MyApplication.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            Toast.makeText(MyApplication.getContext(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证手机格式
     * @param mobile 手机号
     * @return false为手机号格式不正确
     */
    public static boolean isMobile(String mobile) {
        /*
                移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、147
		        联通：130、131、132、152、155、156、185、186
		        电信：133、153、180、189、（1349卫通）
		        虚拟：170
		        总结起来就是第一位必定为1，第二位必定为3、4、5、6、7、8、9，其他位置的可以为0-9
        */
        //"[1]"代表第1位为数字1，"[3456789]"代表第二位可以为3、4、5、6、7、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        String telRegex = "[1][3456789]\\d{9}";
        return !TextUtils.isEmpty(mobile) && mobile.matches(telRegex);
    }

    /**
     * 手机号加密
     * @return
     */
    public static String phoneEncryption(String mobile) {
        if (isMobile(mobile)) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        } else {
            return mobile;
        }
    }

    /**
     * 验证微信号格式
     * @param weChat
     * @return
     */
    public static String isWeChat(String weChat) {
        String telRegex = "[A-Za-z]";
        if (TextUtils.isEmpty(weChat)) {
            return "微信号不能为空";
        } else {
            if (weChat.length() < 6) {
                return "微信号不能小于6位";
            } else {
                if (weChat.length() > 20) {
                    return "微信号不能大于20位";
                } else {
                    if (!weChat.substring(0, 1).matches(telRegex)) {
                        return "微信号开头必须为字母";
                    } else {
                        return "成功";
                    }
                }
            }
        }
    }

    /**
     * 判断内容只允许中文、数字、英文、下划线
     * @param content
     * @return
     */
    public static boolean isEdit(String content) {
        String telRegex = "[a-zA-Z0-9\\u4E00-\\u9FA5]";
        return !TextUtils.isEmpty(content) && content.matches(telRegex);
    }

    /**
     * 判断app是否存在
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        } else {
            PackageManager packageManager = MyApplication.getContext().getPackageManager();
            List<PackageInfo> info = packageManager.getInstalledPackages(0);
            List<String> name = new ArrayList<>();
            if (info != null) {
                for (int i = 0; i < info.size(); i++) {
                    String pn = info.get(i).packageName;
                    name.add(pn);
                }
            }
            return name.contains(packageName);
        }
    }

    /**
     * 截取小数点后两位
     * @param f
     * @return
     */
    public static Float getFloat(float f) {
        if (f<0){
            return (float)0;
        }
        return (float) (Math.round(f * 100)) / 100;
    }
    /**
     * 截取小数点后1位
     * @param s
     * @return
     */
    public static String getFormatePrice(String s) {
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    /**
     * 删除文件
     * @param path 文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            file.delete();
        }
    }

    /**
     * 删除文件夹
     * @param path
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        deleteDirWihtFile(file);

    }

    /**
     * 返回版本号
     * @return 版本号
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager packageManager = MyApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 返回版本名称
     * @return 版本号
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = MyApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
    /**
     * 返回包名
     * @return 版本号
     */
    public static String getAppPakageName() {
        return  MyApplication.getContext().getPackageName();
    }
    /**
     * 转换SD卡图片为Bitmap
     * @param imageFile 图片地址
     * @param newWidth 打开图片宽度
     * @return 打开的图片
     */
    public static Bitmap getLocalBitmap(String imageFile, int newWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        // 获取图片的宽高
        float width = options.outWidth;
        float height = options.outHeight;
        // 计算图片比例
        float scale = height / width;
        // 计算缩放高度
        int newHeight = (int) (newWidth * scale);
        //设置图片宽高
        options.inSampleSize = computeSampleSize(options, -1, newWidth * newHeight);
        options.inJustDecodeBounds = false;
        //打开压缩图片
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile, options);
        //根据图片的filepath获取到一个ExifInterface的对象
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        int digree = 0;
        if (exifInterface != null) {
            //度取图片中相机方向
            int ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            //计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
            if (digree != 0) {
                //旋转图片
                Matrix matrix = new Matrix();
                matrix.postRotate(digree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        }
        return bitmap;
    }

    /**
     * 保存bitmap
     * @param path 保存地址
     * @param bm 图像
     * @param size 图像质量1-100
     * @return
     */
    public static String saveFile(String path, Bitmap bm, int size, boolean isCheck) {
        try {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File myCaptureFile = new File(path + "/" + "shareImage" + System.currentTimeMillis()+getRandom(0, 9) + getRandom(0, 9) + getRandom(0, 9) + getRandom(0, 9) + getRandom(0, 9) + getRandom(0, 9) + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, size, bos);
            bos.flush();
            bos.close();
            if (isCheck) {
                MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + myCaptureFile.getPath())));
            }
            return myCaptureFile.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 保存View为Bitmap
     * @param view
     * @return
     */
    public static Bitmap createViewBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);
        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * 绘制分享海报
     * @param bitmap
     * @param poster
     * @param QrBitmap
     * @return
     */
    public static Bitmap posterBitmap(Bitmap bitmap, Poster.PosterList poster, Bitmap QrBitmap) {
        // 创建一个空的Bitmap(内存区域)
        Bitmap bmp = Bitmap.createBitmap(poster.getBasewidth(), poster.getBaselength(), Bitmap.Config.ARGB_8888);
        // 将bitmap放置到绘制区域,并将要拼接的图片绘制到指定内存区域
        Canvas canvas = new Canvas(bmp);
        // 设置背景颜色
        canvas.drawColor(Color.WHITE);
        // 绘制背景图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        // 绘制二维码
        canvas.drawBitmap(zoomImg(QrBitmap, poster.getWidth(), poster.getLength()), poster.getXposition(), poster.getYposition(), null);
        // 保存Canvas的状态
        canvas.save();
        return bmp;
    }
    /**
     * 绘制分享海报 2.6.0 version
     * @param poster
     * @return
     */
    public static Bitmap posterBitmapNew(Context context,Bitmap Qrbitmap, Poster.PosterList poster, Bitmap ImageBitmap,String code) {
        View view = LayoutInflater.from(context).inflate(R.layout.invite_share_layout, null);
        ((ImageView) view.findViewById(R.id.invite_img)).setImageBitmap(ImageBitmap);
        TextView inviteCode = ((TextView) view.findViewById(R.id.invite_code));
        inviteCode.setText(code);
        // 设置二维码
        ((ImageView) view.findViewById(R.id.invite_share_qr)).setImageBitmap(Qrbitmap);
        // 启用绘图缓存
        view.setDrawingCacheEnabled(true);
        // 调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
        view.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY));
        // 这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 创建位图
        Bitmap resource = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建画板
        Canvas canvas = new Canvas(resource);
        // 绘制图形
        view.draw(canvas);
        // 关闭绘图缓存
        view.setDrawingCacheEnabled(false);
        // 清理内存
        view.destroyDrawingCache();
        return resource;
    }

    /**
     * 绘制分享图片
     * @param bitmap 图片
     * @param info 分享信息
     * @return
     */
    public static Bitmap jointBitmapNew(Context context, Bitmap bitmap, ShareInfo info) {
        try{
        View view = LayoutInflater.from(context).inflate(R.layout.view_share_layout_new, null);
        if (!info.getRecommended().equals("")){
            //设置推荐理由
           ((TextView) view.findViewById(R.id.view_share_recommend)).setText(info.getRecommended());
            view.findViewById(R.id.recomend_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.norecomend_layout).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.recomend_layout).setVisibility(View.GONE);
            view.findViewById(R.id.norecomend_layout).setVisibility(View.VISIBLE);
        }
        // 设置平台logo
        ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(info.isCheck() ? R.mipmap.shop_list_tmall : R.mipmap.shop_list_taobao);
        // 设置商品标题
         TextView view_share_title=view.findViewById(R.id.view_share_title);
         TextPaint tp=view_share_title.getPaint();
         tp.setFakeBoldText(true);
         view_share_title.setText("     " + info.getName());
        //设置原价加下划线
        TextView yuanjia = ((TextView) view.findViewById(R.id.view_share_yuanjia));
        yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        yuanjia.setText("原价 ¥" + info.getShopprice());

        if (!info.getMoney().equals("")&&Float.parseFloat(info.getMoney())>0){
           // 设置价格
            ((TextView) view.findViewById(R.id.view_share_money)).setText(info.getMoney());
            // 设置价格
            ((TextView) view.findViewById(R.id.view_share_moneys)).setText(info.getMoney());
        }else{
            ((TextView) view.findViewById(R.id.view_share_money)).setText("0");
            // 设置价格
            ((TextView) view.findViewById(R.id.view_share_moneys)).setText("0");
        }

        ((TextView) view.findViewById(R.id.zhijiang)).setText("¥"+info.getDiscount());
        // 设置销量
        ((TextView) view.findViewById(R.id.view_share_sales)).setText(getNumber(info.getSales()) + "人已购买");
        // 设置优惠券
        ((TextView) view.findViewById(R.id.view_share_discount)).setText(info.getDiscount()+"元");
        // 设置图片
        if (bitmap!=null) {
            ((ImageView) view.findViewById(R.id.view_share_image)).setImageBitmap(zoomImg(bitmap, 1020, 1020));
        }
            // 设置二维码
        Bitmap logobitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        ((ImageView) view.findViewById(R.id.view_share_qr)).setImageBitmap(createQRCodeBitmap(info.getShortLink(), 300, "UTF-8", "H", "0", Color.BLACK, Color.WHITE, null, logobitmap, 0.2F));
        // 启用绘图缓存
        view.setDrawingCacheEnabled(true);
        // 调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
        view.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY));
        // 这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 创建位图
        Bitmap resource = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建画板
        Canvas canvas = new Canvas(resource);
        // 绘制图形
        view.draw(canvas);
        // 关闭绘图缓存
        view.setDrawingCacheEnabled(false);
        // 清理内存
        view.destroyDrawingCache();
        return resource;
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 绘制分享图片
     * @param bitmap 图片
     * @param info 分享信息
     * @return
     */
    public static Bitmap jointBitmapPJW(Context context, Bitmap bitmap, ShareInfo info) {
        try{
            View view = LayoutInflater.from(context).inflate(R.layout.view_share_layout_pjw, null);
            LinearLayout youhuiquan = ((LinearLayout) view.findViewById(R.id.youhuiquan));
            ((LinearLayout) view.findViewById(R.id.quanhoujia)).setVisibility(View.VISIBLE);
            ((LinearLayout) view.findViewById(R.id.quanhoujia_layout02)).setVisibility(View.VISIBLE);
            switch (info.getType()){
                case "pdd":
                    // 设置优惠券
                    ((LinearLayout) view.findViewById(R.id.quanhoujia_layout02)).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.view_share_discount)).setText(info.getDiscount()+"元");
                    ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.pdd_icon);
                    break;
                case "jd":
                    // 设置优惠券
                    ((LinearLayout) view.findViewById(R.id.quanhoujia_layout02)).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.view_share_discount)).setText(info.getDiscount()+"元");
                    ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.jd_icon);
                    break;
                case "wph":
                    ((LinearLayout) view.findViewById(R.id.quanhoujia)).setVisibility(View.GONE);
//                    ((TextView)view.findViewById(R.id.top_title)).setText("折后价");
                    ((TextView)view.findViewById(R.id.quanhoujia_title02)).setText("折后价:  ¥ ");
                    youhuiquan.setVisibility(View.GONE);
                    ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.wei_icon);
                    break;
                case "sn":
                    // 设置优惠券
                    ((LinearLayout) view.findViewById(R.id.quanhoujia_layout02)).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.view_share_discount)).setText(info.getDiscount()+"元");
                    ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.sn_icon);
                    break;
                case "kl":
                    ((LinearLayout) view.findViewById(R.id.quanhoujia)).setVisibility(View.GONE);
//                    ((TextView)view.findViewById(R.id.top_title)).setText("折后价");
                    ((TextView)view.findViewById(R.id.quanhoujia_title02)).setText("折后价:  ¥ ");
                    youhuiquan.setVisibility(View.GONE);
                    ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.kl_icon);
                    break;
            }
            if (!info.getRecommended().equals("")){
                //设置推荐理由
                ((TextView) view.findViewById(R.id.view_share_recommend)).setText(info.getRecommended());
                view.findViewById(R.id.recomend_layout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.norecomend_layout).setVisibility(View.GONE);
            }else{
                view.findViewById(R.id.recomend_layout).setVisibility(View.GONE);
                view.findViewById(R.id.norecomend_layout).setVisibility(View.VISIBLE);
            }
            // 设置商品标题
            TextView view_share_title=view.findViewById(R.id.view_share_title);
            TextPaint tp=view_share_title.getPaint();
            tp.setFakeBoldText(true);
            view_share_title.setText("      " + info.getName());
            //设置原价加下划线
            TextView yuanjia = ((TextView) view.findViewById(R.id.view_share_yuanjia));
            yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia.setText("原价 ¥" + info.getShopprice() );

            TextView yuanjia1 = ((TextView) view.findViewById(R.id.view_share_yuanjia02));
            yuanjia1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia1.setText("原价 ¥" + info.getShopprice() );

            if (!info.getMoney().equals("")&&Float.parseFloat(info.getMoney())>0){
                // 设置价格
                ((TextView) view.findViewById(R.id.view_share_money)).setText(info.getMoney());
                ((TextView) view.findViewById(R.id.view_share_money02)).setText(info.getMoney());
                // 设置价格
//                ((TextView) view.findViewById(R.id.view_share_moneys)).setText(info.getMoney());
            }else{
                ((TextView) view.findViewById(R.id.view_share_money)).setText("0");
                ((TextView) view.findViewById(R.id.view_share_money02)).setText("0");
                // 设置价格
//                ((TextView) view.findViewById(R.id.view_share_moneys)).setText("0");
            }

            ((TextView) view.findViewById(R.id.zhijiang)).setText("¥"+info.getDiscount());
            // 设置销量
            if (info.getSales().equals("")){
                ((TextView) view.findViewById(R.id.view_share_sales)).setVisibility(View.GONE);
            }else {
                ((TextView) view.findViewById(R.id.view_share_sales)).setText(info.getSales() + "人已购买");
            }
            // 设置图片
            if (bitmap!=null) {
                ((ImageView) view.findViewById(R.id.view_share_image)).setImageBitmap(zoomImg(bitmap, 1020, 1020));
            }
            // 设置二维码
            Bitmap logobitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            ((ImageView) view.findViewById(R.id.view_share_qr)).setImageBitmap(createQRCodeBitmap(info.getShortLink(), 300, "UTF-8", "H", "0", Color.BLACK, Color.WHITE, null, logobitmap, 0.2F));
            // 启用绘图缓存
            view.setDrawingCacheEnabled(true);
            // 调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
            view.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY));
            // 这个方法也非常重要，设置布局的尺寸和位置
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            // 创建位图
            Bitmap resource = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            // 创建画板
            Canvas canvas = new Canvas(resource);
            // 绘制图形
            view.draw(canvas);
            // 关闭绘图缓存
            view.setDrawingCacheEnabled(false);
            // 清理内存
            view.destroyDrawingCache();
            return resource;
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 绘制海报
     * @param bitmap
     * @param content
     * @return
     */
    public static Bitmap getPoster(Bitmap bitmap, String content, int position) {
        // 创建一个空的Bitmap(内存区域)
        Bitmap bmp = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        // 将bitmap放置到绘制区域,并将要拼接的图片绘制到指定内存区域
        Canvas canvas = new Canvas(bmp);
        // 绘制背景图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        // 绘制二维码
        switch (position) {
            case 0:
                canvas.drawBitmap(generateBitmap(content, 300, 300), 169, 1358, null);
                break;
            case 1:
                canvas.drawBitmap(generateBitmap(content, 260, 260), 230, 1380, null);
                break;
            case 2:
                canvas.drawBitmap(generateBitmap(content, 300, 300), 169, 1345, null);
                break;
        }
        return bmp;
    }

    /**
     * 绘制分享图片
     * @param littleApp 图片
     * @param info 分享信息
     * @return
     */
    public static Bitmap jointBitmapLittleApp(Context context,Bitmap littleApp,Bitmap goods,Bitmap user,JDGoodsBean info,int platform) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_share_layout_littleapp, null);
        UserInfo login = DateStorage.getInformation();
        ((ImageView) view.findViewById(R.id.user_image)).setImageBitmap(user);
        ((TextView) view.findViewById(R.id.user_name)).setText(login.getUsername());
        switch (login.getUsertype()){
            case "1"://运营商
            ((ImageView) view.findViewById(R.id.rank_iv)).setVisibility(View.GONE);
                break;
            case "2"://会员
             ((ImageView) view.findViewById(R.id.rank_iv)).setImageResource(R.mipmap.huiyuan_little);
                break;
            case "5":
            case "4"://店主
             ((ImageView) view.findViewById(R.id.rank_iv)).setImageResource(R.mipmap.dianzhu_little);
                break;
        }
        switch (platform){
            case 1:
                // 设置优惠券
                ((TextView) view.findViewById(R.id.commodity_discount_text)).setText(info.getSave());
                ((TextView) view.findViewById(R.id.weipinhui_explain)).setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.share_pdd);
                break;
            case 2:
                // 设置优惠券
                ((TextView) view.findViewById(R.id.commodity_discount_text)).setText(info.getSave());
                ((TextView) view.findViewById(R.id.weipinhui_explain)).setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.share_jd);
                break;
            case 5:
                // 设置优惠券
                ((TextView) view.findViewById(R.id.commodity_discount_text)).setText(info.getSave());
                ((TextView) view.findViewById(R.id.weipinhui_explain)).setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.order_sn_icon);
                break;
            case 3:
                ((TextView) view.findViewById(R.id.view_share_sales)).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.commodity_discount_text01)).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.commodity_discount_text)).setText("限时优惠");
                ((TextView) view.findViewById(R.id.weipinhui_explain)).setVisibility(View.VISIBLE);
                ((LinearLayout) view.findViewById(R.id.time_layout)).setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.view_share_type)).setImageResource(R.mipmap.share_wei);
                break;
        }
        if (info.getCouponInfo()!=null){
            JSONObject obj=(JSONObject) info.getCouponInfo();
            ((TextView) view.findViewById(R.id.commodity_start_time)).setText(obj.getString("startTime"));
            ((TextView) view.findViewById(R.id.commodity_end_time)).setText(obj.getString("endTime"));
        }
        // 设置商品标题
        ((TextView) view.findViewById(R.id.view_share_title)).setText("     " + info.getName());
        ((TextView) view.findViewById(R.id.view_share_money)).setText(info.getPrice());
        //设置原价加下划线
        TextView yuanjia = ((TextView) view.findViewById(R.id.view_share_yuanjia));
        yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        yuanjia.setText("原价 ¥" + info.getCost() );
        // 设置销量
        if (info.getSales()!=null){
            ((TextView) view.findViewById(R.id.view_share_sales)).setText(getNumber(info.getSales()) + "人已购买");
        }
        // 设置图片
        ((ImageView) view.findViewById(R.id.view_share_image)).setImageBitmap(zoomImg(goods, 1020, 1020));
        // 设置小程序码
      ((ImageView) view.findViewById(R.id.view_share_qr)).setImageBitmap(littleApp);
        // 启用绘图缓存
        view.setDrawingCacheEnabled(true);
        // 调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
        view.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY));
        // 这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 创建位图
        Bitmap resource = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建画板
        Canvas canvas = new Canvas(resource);
        // 绘制图形
        view.draw(canvas);
        // 关闭绘图缓存
        view.setDrawingCacheEnabled(false);
        // 清理内存
        view.destroyDrawingCache();
        return resource;
    }


    /**
     * 生成二维码
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap generateBitmap(String content, int width, int height) {
        if (content.equals("")){
            return null;
        }
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        map.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, map);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (bitMatrix.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建二维码位图 (支持自定义配置和自定义样式)
     *
     * @param content 字符串内容
     * @param size 位图宽&高(单位:px)
     * @param character_set 字符集/字符转码格式 (支持格式:{@link CharacterSetECI })。传null时,zxing源码默认使用 "ISO-8859-1"
     * @param error_correction 容错级别 (支持级别:{@link ErrorCorrectionLevel })。传null时,zxing源码默认使用 "L"
     * @param margin 空白边距 (可修改,要求:整型且>=0), 传null时,zxing源码默认使用"4"。
     * @param color_black 黑色色块的自定义颜色值
     * @param color_white 白色色块的自定义颜色值
     * @param targetBitmap 目标图片 (如果targetBitmap != null, 黑色色块将会被该图片像素色值替代)
     * @param logoBitmap logo小图片
     * @param logoPercent logo小图片在二维码图片中的占比大小,范围[0F,1F],超出范围->默认使用0.2F。
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(@Nullable String content, int size,
                                            @Nullable String character_set, @Nullable String error_correction, @Nullable String margin,
                                            @ColorInt int color_black, @ColorInt int color_white, @Nullable Bitmap targetBitmap,
                                            @Nullable Bitmap logoBitmap, float logoPercent){

        /** 1.参数合法性判断 */
        if(TextUtils.isEmpty(content)){ // 字符串内容判空
            return null;
        }

        if(size <= 0){ // 宽&高都需要>0
            return null;
        }

        try {
            /** 2.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();

            if(!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set); // 字符转码格式设置
            }

            if(!TextUtils.isEmpty(error_correction)){
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction); // 容错级别设置
            }

            if(!TextUtils.isEmpty(margin)){
                hints.put(EncodeHintType.MARGIN, margin); // 空白边距设置
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            /** 3.根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            if(targetBitmap != null){
                targetBitmap = Bitmap.createScaledBitmap(targetBitmap, size, size, false);
            }
            int[] pixels = new int[size * size];
            for(int y = 0; y < size; y++){
                for(int x = 0; x < size; x++){
                    if(bitMatrix.get(x, y)){ // 黑色色块像素设置
                        if(targetBitmap != null) {
                            pixels[y * size + x] = targetBitmap.getPixel(x, y);
                        } else {
                            pixels[y * size + x] = color_black;
                        }
                    } else { // 白色色块像素设置
                        pixels[y * size + x] = color_white;
                    }
                }
            }

            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

            /** 5.为二维码添加logo小图标 */
            if(logoBitmap != null){
                return addLogo(bitmap, logoBitmap, logoPercent);
            }

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 向一张图片中间添加logo小图片(图片合成)
     *
     * @param srcBitmap 原图片
     * @param logoBitmap logo图片
     * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1], 传值不合法时使用0.2F)
     *                    原图片是二维码时,建议使用0.2F,百分比过大可能导致二维码扫描失败。
     * @return
     */
    @Nullable
    private static Bitmap addLogo(@Nullable Bitmap srcBitmap, @Nullable Bitmap logoBitmap, float logoPercent){

        /** 1. 参数合法性判断 */
        if(srcBitmap == null){
            return null;
        }

        if(logoBitmap == null){
            return srcBitmap;
        }

        if(logoPercent < 0F || logoPercent > 1F){
            logoPercent = 0.2F;
        }

        /** 2. 获取原图片和Logo图片各自的宽、高值 */
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        /** 3. 计算画布缩放的宽高比 */
        float scaleWidth = srcWidth * logoPercent / logoWidth;
        float scaleHeight = srcHeight * logoPercent / logoHeight;

        /** 4. 使用Canvas绘制,合成图片 */
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.scale(scaleWidth, scaleHeight, srcWidth/2, srcHeight/2);
        canvas.drawBitmap(logoBitmap, srcWidth/2 - logoWidth/2, srcHeight/2 - logoHeight/2, null);

        return bitmap;
    }

    /**
     * 判断程序是否在后台
     * @return true为切换到后台
     */
    public static boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(MyApplication.getContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取进程号对应的进程名
     * @param pid
     * @return
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            Logger.e(throwable, "获取进程号对应的进程名");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Logger.e(e, "获取进程号对应的进程名");
            }
        }
        return null;
    }

    /**
     * 创建通知栏消息
     * @param context
     * @param title 标题
     * @param content 内容
     * @param intent 点击要跳转的页面
     * @param icon 通知图标
     * @param smallIcon 状态栏图标
     */
    public static void Notification(Context context, String title, String content, Intent intent, int icon, int smallIcon,boolean isBrocast) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 判断是否大于8.0
        Notification.Builder builder = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? new Notification.Builder(context) : new Notification.Builder(context, Variable.CHANNEL_ID);
        PendingIntent pendingIntent=null;
        int id = (int) (System.currentTimeMillis() / 1000);
        // 设置点击事件
        if (!isBrocast){
             pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }else{
            pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        // 转换图标为Bitmap
        Bitmap bitmapLarge = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), icon);
        // 设置标题
        builder.setContentTitle(title);
        // 设置内容
        builder.setContentText(content);
        // 设置状态栏显示时的图标
        builder.setSmallIcon(smallIcon);
        // 设置显示的大图标
        builder.setLargeIcon(bitmapLarge);
        // 设置点击时的意图
        builder.setContentIntent(pendingIntent);
        // 设置是否自动按下过后取消
        builder.setAutoCancel(true);
        // 铃声、闪光、震动均系统默认
        builder.setDefaults(Notification.DEFAULT_ALL);
        // 设置优先级
        builder.setPriority(Notification.PRIORITY_HIGH);
        // 创建Notification
        Notification notification = builder.build();
        // 设置提醒标识符Flags
        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        Bundle bundle = intent.getExtras();
//        for (String key: bundle.keySet())
//        {
//            Log.e("Bundle Content", "Key=" + key + ", content=" +bundle.getString(key));
//        }
        // 创建通知
        Objects.requireNonNull(manager).notify((int) getStringToDate(getCurrentDate("yyyy/MM/dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss"), notification);
    }

    /**
     * 通知栏创建
     * @param context
     * @param builder
     * @param intent
     */
    public static void notifications(Context context, Notification.Builder builder, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent != null) {
            // 设置点击事件
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            // 设置点击时的意图
            builder.setContentIntent(pendingIntent);
        }
        // 转换图标为Bitmap
        Bitmap bitmapLarge = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.mipmap.push);
        // 设置状态栏显示时的图标
        builder.setSmallIcon(R.mipmap.push_small);
        // 设置显示的大图标
        builder.setLargeIcon(bitmapLarge);
        // 铃声、闪光、震动均系统默认
        builder.setDefaults(Notification.DEFAULT_ALL);
        // 设置优先级
        builder.setPriority(Notification.PRIORITY_HIGH);
        // 创建Notification
        Notification notification = builder.build();
        // 设置提醒标识符Flags
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        // 创建通知
        Objects.requireNonNull(manager).notify((int) getStringToDate(getCurrentDate("yyyy/MM/dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss"), notification);
    }

    /**
     * 根据版本返回Builder
     * @param context
     * @return
     */
    public static Notification.Builder getBuilder(Context context) {
        // 判断是否大于8.0
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? new Notification.Builder(context) : new Notification.Builder(context, Variable.CHANNEL_ID);
    }

    /**
     * 获取是否开启推送
     * @param context
     * @return
     */
    public static boolean isNotification(Context context) {
        try {
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // 获取渠道
                NotificationChannel channel = Objects.requireNonNull(manager).getNotificationChannel(Variable.CHANNEL_ID);
                // 判断渠道是否打开
                if (channel != null) {
                    return channel.getImportance() != NotificationManager.IMPORTANCE_NONE && managerCompat.areNotificationsEnabled();
                } else {
                    return false;
                }
            } else {
                return managerCompat.areNotificationsEnabled();
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 打开通知界面
     * @param context
     */
    public static void jumpNotification(Context context, int requestCode) {
        ApplicationInfo info = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = info.uid;
        try {
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                // 这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent = new Intent();
                // 这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra("app_package", pkg);
                intent.putExtra("app_uid", uid);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.fromParts("package", ((Activity) context).getApplication().getPackageName(), null));
            } else {
                intent = new Intent(Settings.ACTION_SETTINGS);
            }
            ((Activity) context).startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            ((Activity) context).startActivityForResult(intent, requestCode);
            Logger.e(e, "跳转通知");
        }
    }

    /**
     * 获取当前日期
     * @param data 日期格式：yyyy-MM-dd yyyy/MM/dd yyyy年MM月dd日
     * @return 日期
     */
    public static String getCurrentDate(String data) {
        SimpleDateFormat format = new SimpleDateFormat(data);
        return format.format(new Date());
    }

    /**
     * 获取明天日期
     * @param data
     * @return
     */
    public static String getNextDate(String data) {
        SimpleDateFormat format = new SimpleDateFormat(data);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return format.format(calendar.getTime());
    }

    /**
     * 装换时间戳为时间格式
     * @param time 时间戳
     * @param data 日期格式：yyyy-MM-dd yyyy/MM/dd yyyy年MM月dd日
     * @return 日期
     */
    public static String getDateToString(long time, String data) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(data);
        return format.format(date);
    }

    /**
     * 装换时间格式为时间戳
     * @param time 时间
     * @param data 日期格式：yyyy-MM-dd yyyy/MM/dd yyyy年MM月dd日
     * @return
     */
    public static long getStringToDate(String time, String data) {
        SimpleDateFormat format = new SimpleDateFormat(data);
        Date date = new Date();
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间减法
     * @param time
     * @param num
     * @return
     */
    public static String getDateBefore(String time, int num) {
        Date date = new Date(getStringToDate(time, "yyyy-MM-dd") - (long) num * 24 * 60 * 60 * 1000);
        return getDateToString(date.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取周几
     * @return
     */
    public static String getWeek() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    /**
     * 计算年龄
     * @param birthDay 出生日期
     * @return
     */
    public static String getAge(Date birthDay) {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age + "岁";
    }

    /**
     * 复制文件
     * @param in 数据
     * @param targetFile 地址
     * @throws IOException
     */
    public static void copyFile(InputStream in, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(in);

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 下载文件
     * @param urlPath 文件地址
     * @param path 保存地址
     */
    public static File downFile(String urlPath, String path) throws Exception {
        URL url = new URL(urlPath);
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        if (stream == null) {
            throw new RuntimeException("stream is null");
        }
        return writeSD(path, urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.lastIndexOf(".")) + "." + urlPath.substring(urlPath.lastIndexOf(".") + 1, urlPath.length()), stream);
    }

    /**
     * 写入数据
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    private static File writeSD(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream outputStream = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 10];
            int size;
            while ((size = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, size);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 隐藏输入法（根据activity当前焦点所在控件的WindowToken）
     */
    public static void hideSoftInput(Activity activity, View editText) {
        View view;
        if (editText == null) {
            view = activity.getCurrentFocus();
        } else {
            view = editText;
        }

        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘（根据焦点所在的控件）
     */
    public static void showSoftInput() {
        ((InputMethodManager) MyApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * MD5加密
     * @param values
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String values) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(values.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte offset : b) {
                i = offset;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断应用是否在前台
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = Objects.requireNonNull(am).getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return topActivity.getPackageName().equals(context.getPackageName());
        }
        return false;
    }

    /**
     * 将Map按照Ascii码从小到大排序，并把值拼接到一起
     * @param paraMap
     * @return
     */
    public static String getMapAscii(Map<String, String> paraMap) {
        StringBuilder buff = new StringBuilder();
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(paraMap.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, (lhs, rhs) -> (lhs.getKey()).compareTo(rhs.getKey()));
        for (Map.Entry<String, String> item : infoIds) {
            buff.append(item.getValue());
        }
        return buff.toString();
    }

    /**
     * 转换图片为Base64
     * @param imgFile 图片地址
     * @return
     */
    public static String getImageStr(String imgFile) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = getLocalBitmap(imgFile, 1080);
        if (bitmap==null){
          return null;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 获取随机数
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getRandom(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    /**
     * 微信分享图标转换为byte
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        if (bmp==null){
            return null;
        }
        //处理缩略图
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, output);
//        if (needRecycle) {
//            bmp.recycle();
//        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存报错日志
     * @param context
     * @param throwable
     */
    public static void saveException(Context context, Throwable throwable) {
        try {
            Map<String, String> map = new HashMap<>();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                UserInfo info = DateStorage.getInformation();
                map.put("用户ID", info.getUserid());
                map.put("APP版本号", packageInfo.versionName);
                map.put("手机品牌", Build.BRAND);
                map.put("手机型号", Build.MODEL);
                map.put("系统版本", Build.VERSION.SDK_INT + "");
                map.put("发生时间", getCurrentDate("yyyy/MM/dd HH:mm:ss"));
                map.put("报错内容", "");
            }
            StringBuilder stringBuffer = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                stringBuffer.append(key).append(":").append(value).append("\n");
            }
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            stringBuffer.append(result);
            String fileName = "crash-" + getStringToDate(getCurrentDate("yyyy/MM/dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss") + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Variable.crashPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(Variable.crashPath + "/" + fileName);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            Logger.e(e, "保存报错日志");
        }
    }

    /**
     * 加载图片 也可以支持gif
     * @param context
     * @param uri
     * @param imageView
     */
    public static void displayImage(Context context, String uri, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(uri).thumbnail(0.2f).into(imageView);
    }

    /**
     * 加载资源图片
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void displayImage(Context context, Integer resourceId, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(resourceId).thumbnail(0.2f).into(imageView);
    }

    public static void displayImage(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(bitmap).thumbnail(0.2f).into(imageView);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param uri
     * @param imageView
     */
    public static void displayImageCircular(Context context, String uri, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(uri).circleCrop().thumbnail(0.2f).transform(new GlideCircleTransform(context)).into(imageView);
    }
    public static void displayImageCircularWithBitmap(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(bitmap).circleCrop().thumbnail(0.2f).transform(new GlideCircleTransform(context)).into(imageView);
    }
    /**
     * 加载圆角图片
     * @param context
     * @param uri
     * @param imageView
     * @param dp
     */
    public static void displayImageRounded(Context context, String uri, ImageView imageView, int dp) {
        Glide.with(context.getApplicationContext()).load(uri).transform(new GlideRoundTransform(context, dp)).into(imageView);
    }

    /**
     * 加载圆角图片 可以自定义各个圆角 ALL,
     *         TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
     *         TOP, BOTTOM, LEFT, RIGHT,
     *         TOP_LEFT_BOTTOM_RIGHT,
     *         TOP_RIGHT_BOTTOM_LEFT,
     *         TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT,
     *         TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT,
     *         TOP_LEFT_TOP_RIGHT
     * @param context
     * @param uri 也可以支持gif
     * @param imageView
     * @param dp
     */
    public static void displayImageRoundedNew(Context context, String uri, ImageView imageView, int dp) {
        Glide.with(context.getApplicationContext()).load(uri).transform(new GlideRoundTransformNew(dp, GlideRoundTransformNew.CornerType.TOP_LEFT_TOP_RIGHT)).into(imageView);
    }

    public static void displayImageRoundedAll(Context context, String uri, ImageView imageView, int dp) {
        Glide.with(context.getApplicationContext()).load(uri).transform(new GlideRoundTransformNew(dp, GlideRoundTransformNew.CornerType.ALL)).into(imageView);
    }
    /**
     * 转换过万的数字
     * @param value
     * @return
     */
    public static String getNumber(String value) {
        try {
            double v = Double.parseDouble(value);
            if (v > 10000) {
                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                return decimalFormat.format(v / 10000) + "万";
            } else {
                return value;
            }
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * 转换过万的数字，保留2位
     * @param value
     * @return
     */
    public static String Conversion(String value, String name) {
        try {
            double v = Double.parseDouble(value);
            if (v > 10000) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                return decimalFormat.format(v / 10000) + name;
            } else {
                return value;
            }
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * 去除.0小数
     * @param value
     * @return
     */
    private static String removeLast(String value) {
        if (value == null || value.length() == 0) {
            return "0";
        }
        try {
            while (value.charAt(value.length() - 1) == '0') {
                value = value.substring(0, value.length() - 1);
            }
            if (value.charAt(value.length() - 1) == '.') {
                value = value.substring(0, value.length() - 1);
            }
        } catch (Exception e) {
            return "";
        }
        return value;
    }

    /**
     * 7.0以上本地路径获取
     * @param context
     * @param file
     * @return
     */
    public static Uri getContentUri(Context context, File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            return Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null));
        } catch (Exception e) {
            return null;
        }
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        } else {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDirWihtFile(file);
                }
            }
            dir.delete();
        }
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 获取当前App版本号
     * @param ctx
     * @return
     */
    public static String getLocalAppVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf(localVersion);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param
     * @param imgBase64
     * @return Bitmap
     */
    public static Bitmap stringtoBitmap(String imgBase64) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(imgBase64, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取手机IMEI
     * @return
     */
    public static final String getIMEI() {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for PlTimeItemAdapter details.
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * @param slotId  双卡双待 slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager)MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }



    /**
     * 跳转应用商店.
     * @param context   {@link Context}
     * @param appPkg    包名
     * @param marketPkg 应用商店包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean toMarket(Context context, String appPkg, String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
            intent.setPackage(marketPkg);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * 跳转三星应用商店
     * @param context {@link Context}
     * @param packageName 包名
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean goToSamsungMarket(Context context, String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
//        Uri uri = Uri.parse("http://apps.samsung.com/appquery/appDetail.as?appId=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.sec.android.app.samsungapps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 跳转索尼精选
     * @param context {@link Context}
     * @param appId 索尼精选中分配得appId
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static boolean goToSonyMarket(Context context, String appId) {
        Uri uri = Uri.parse("http://m.sonyselect.cn/" + appId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        Intent intent = new Intent();
//        intent.setAction("com.sonymobile.playnowchina.android.action.NOTIFICATION_APP_DETAIL_PAGE");
//        intent.setAction("com.sonymobile.playnowchina.android.action.APP_DETAIL_PAGE");
//        intent.putExtra("app_id", 9115);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * 获取已安装应用商店的包名列表
     * 获取有在AndroidManifest 里面注册<category android:name="android.intent.category.APP_MARKET" />的app
     * @param context
     * @return
     */
    public static ArrayList<String> getInstallAppMarkets(Context context) {
        //默认的应用市场列表，有些应用市场没有设置APP_MARKET通过隐式搜索不到
//        ArrayList<String>  pkgList = new ArrayList<>();
//        pkgList.add("com.xiaomi.market");
//        pkgList.add("com.meizu.mstore");
//        pkgList.add("com.huawei.appmarket");
//        pkgList.add("com.oppo.market");
//        pkgList.add("com.bbk.appstore");
//        pkgList.add("com.lenovo.leos.appstore");
//        pkgList.add("com.tencent.android.qqdownloader");
        ArrayList<String> pkgs = new ArrayList<String>();
        if (context == null)
            return pkgs;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        if (infos == null || infos.size() == 0)
            return pkgs;
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            String pkgName = "";
            try {
                ActivityInfo activityInfo = infos.get(i).activityInfo;
                pkgName = activityInfo.packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(pkgName))
                pkgs.add(pkgName);

        }
        //取两个list并集,去除重复
//        pkgList.removeAll(pkgs);
//        pkgs.addAll(pkgList);
        return pkgs;
    }
    /**
     * 过滤出已经安装的包名集合
     * @param context
     * @param pkgs 待过滤包名集合
     * @return 已安装的包名集合
     */
    public static ArrayList<AppInfo> getFilterInstallMarkets(Context context,ArrayList<String> pkgs) {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        if (context == null || pkgs == null || pkgs.size() == 0)
            return appList;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
        int li = installedPkgs.size();
        int lj = pkgs.size();
        for (int j = 0; j < lj; j++) {
            for (int i = 0; i < li; i++) {
                String installPkg = "";
                String checkPkg = pkgs.get(j);
                PackageInfo packageInfo = installedPkgs.get(i);
                try {
                    installPkg = packageInfo.packageName;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(installPkg))
                    continue;
                if (installPkg.equals(checkPkg)) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                    appInfo.setPackageName(installPkg);
                    // 如果非系统应用，则添加至appList,这个会过滤掉系统的应用商店，如果不需要过滤就不用这个判断
//                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //将应用相关信息缓存起来，用于自定义弹出应用列表信息相关用
//                        AppInfo appInfo = new AppInfo();
//                        appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
//                        appInfo.setAppIcon(String.valueOf(packageInfo.applicationInfo.loadIcon(pm)));
//                        appInfo.setPackageName(packageInfo.packageName);
//                        appInfos.add(appInfo);
                        appList.add(appInfo);
//                    }
                    break;
                }

            }
        }
        return appList;
    }

    /**
     * @param pkg
     * @return
     *  pkgList.add(" com.xiaomi.market ");
     *         pkgList.add("com.meizu.mstore");
     *         pkgList.add("com.huawei.appmarket");
     *         pkgList.add("com.oppo.market");
     *         pkgList.add("com.bbk.appstore");
     *         pkgList.add("com.lenovo.leos.appstore");
     *         pkgList.add("com.tencent.android.qqdownloader");
     */
  public static  String  getPkgName(String pkg){
      String marketName="";
     switch (pkg){
         case "com.xiaomi.market":
             marketName="小米应用商店";
             break;
         case "com.meizu.mstore":
             marketName="魅族应用商店";
             break;
         case "com.huawei.appmarket":
             marketName="华为应用商店";
             break;
         case "com.oppo.market":
             marketName="OPPO应用商店";
             break;
         case "com.bbk.appstore":
             marketName="VIVO应用商店";
             break;
         case "com.lenovo.leos.appstore":
             marketName="联想应用商店";
             break;
         case "com.tencent.android.qqdownloader":
             marketName="腾讯应用宝";
             break;

     }
        return marketName;
  }


  //轮播广告的类型跳转
  //公共跳转方法
  public static void getJumpType(Context context,String type,String advertisementlink,String isNeedLogin,String adid){
      if (!adid.equals("")) {
          InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("AdvertisementClick"), false, Integer.parseInt(adid));
      }
      Intent intent = null;
      if (isNeedLogin.equals("1")&&!DateStorage.getLoginStatus()){
              intent =new Intent(context, LoginActivity.class);
      }else{
          switch (type) {
              case "00":
                  if (!TextUtils.isEmpty(advertisementlink)) {
                      if (advertisementlink.contains("dmjwebview=native")) {
                          intent = new Intent(context, AliAuthWebViewActivity.class);
                          intent.putExtra(Variable.webUrl, advertisementlink);
                      } else {
                          intent = new Intent(context, OnlyUrlWebViewActivity.class);
                          intent.putExtra(Variable.webUrl, advertisementlink);
                      }
                  }
                  break;
              case "01":
                  intent = new Intent(context, CommodityActivity290.class);
                  intent.putExtra("shopId", advertisementlink);
                  intent.putExtra("source", "dmj");
                  intent.putExtra("sourceId", "");
                  break;
              case "03":
                  if (advertisementlink.toLowerCase().startsWith("alipays://")) {
                          if (Utils.checkApkExist("com.eg.android.AlipayGphone")) {
                              intent =new Intent(Intent.ACTION_VIEW, Uri.parse(advertisementlink));
                          }else{
                          ToastUtil.showImageToast(context,"请安装支付宝客户端",R.mipmap.toast_error);
                          }
                      }else{
                          intent = new Intent(context, AliAuthWebViewActivity.class);
                          intent.putExtra(Variable.webUrl, advertisementlink);
                          intent.putExtra("isTitle" , true);
                      }
                  break;
              case "04":
                  intent = new Intent(context, NewActivity.class);
                  intent.putExtra("labelType", "09");
                  intent.putExtra("topicId",advertisementlink);
                  break;
              //官方活动
              case "11":
                  // 请求活动链接
                  if (DateStorage.getLoginStatus()){
                      if(MainActivity.mainActivity!=null){
                          MainActivity.mainActivity.getActivityChain(DateStorage.getInformation().getUserid(),advertisementlink);
                      }
                  }else{
                      intent =new Intent(context, LoginActivity.class);
                  }
                  break;
              case "12":
                  intent = new Intent(context, AliAuthWebViewActivity.class);
                  intent.putExtra(Variable.webUrl, advertisementlink);
                  intent.putExtra("isTitle", false);
                  intent.putExtra("noGoTaoBaoH5", 12);
                  break;
              case "13"://商学院新增
                  InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("OpenArticleOrVideo"), false, Integer.parseInt(advertisementlink));
                  break;
              case "14":
                  if (advertisementlink!=null) {
                      Object bannerObject = JSON.parseObject(advertisementlink, JDBanner.class);
                      JDBanner banner = (JDBanner) bannerObject;
                      int platformType=1;
                      switch (banner.getCpsType()){
                          case "pdd":
                              platformType=1;
                              break;
                          case "jd":
                              platformType=2;
                              break;
                          case "wph":
                              platformType=3;
                              break;
                          case "sn":
                              platformType=5;
                              break;
                          case "kl":
                              platformType=6;
                              break;
                      }
                      Utils.getJumpTypeForMorePl(context,banner.getClickType(),banner.getClickVaule(),platformType,banner.getNeedLogin(),banner.getName(),banner.getSysParam());
                  }
                  break;
          }
      }
      if (intent != null) {
          context.startActivity(intent);
      }

  }

    //轮播广告的类型跳转 多平台
    //公共跳转方法
    public static void getJumpTypeForMorePl(Context context,String type,String value,int platform,boolean isNeedLogin,String name,String sysname){
        Intent intent = null;
        if (isNeedLogin&&!DateStorage.getLoginStatus()){
            intent =new Intent(context, LoginActivity.class);
        }else{
            switch (type) {
                case "00"://跳转搜索
                    intent = new Intent(context, MorePlSearchNewActivity.class);
                    intent.putExtra("isCheck", false);
                    intent.putExtra("platType",platform+"");
                    break;
                case "01":
                    switch (platform){
                        case 1:
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", value).putExtra("type", "pdd");
                            break;
                        case 2:
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", value).putExtra("type", "jd");
                            break;
                        case 3:
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", value).putExtra("type", "wph");
                            break;
                        case 5:
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", value).putExtra("type", "sn");
                            break;
                        case 6:
                            intent=new Intent(context, CommodityActivityPJW.class).putExtra("GoodsId", value).putExtra("type", "kl");
                            break;
                    }
                    break;
                case "03":
                case "04":
                    intent = new Intent(context, NewActivity.class);
                    intent.putExtra("themeId",value);
                    intent.putExtra("name",name);
                    intent.putExtra("notaobao",true);
                    intent.putExtra("platForm",platform);
                    intent.putExtra("type",type);
                    break;
                case "05"://跳外部
                    intent = new Intent(context, OnlyUrlWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, value);
                    break;
                case "06"://不跳外部
                    intent = new Intent(context, AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, value);
                    intent.putExtra("isTitle", false);
                    intent.putExtra("noGoTaoBaoH5", 12);
                    break;
                case "09"://领券
                   JSONObject obj=new JSONObject();
                   obj.put("clickValue",value);
                   obj.put("sysParam",sysname);
                   InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("PJWGetCoupon"), obj, platform);
                    break;
            }
        }
        if (intent != null) {
            context.startActivity(intent);
        }

    }

    //金刚区跳转
    public static void doJgqClick(Context context,HomePage.JGQAppIcon jgqAppIcon) {
        Intent intent=null;
        if (jgqAppIcon.getNeedlogin().equals("1")&&!DateStorage.getLoginStatus()){
            intent =new Intent(context, LoginActivity.class);
        }else {
            switch (jgqAppIcon.getLabeltype()) {
                case "88":
                    //淘宝优惠券
                    intent =new Intent(context, TaobaoCouponActivity.class);
                    break;
                case "99":// 限时抢购
                    intent = new Intent(context, LimitActivity.class);
                    break;
                case "09":// 品牌特卖
                    intent = new Intent(context, BrandActivity.class);
                    break;
                case "11":// 纯外链 不拦截
                    intent = new Intent(context, OnlyUrlWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, jgqAppIcon.getUrl());
                    break;
                case "12":// 无标题网页(我们平台自己的页面)
                    intent = new Intent(context, AliAuthWebViewActivity.class);
                    intent.putExtra("isTitle", true);
                    intent.putExtra("isGoTaobao", true);//跳淘宝天猫
                    intent.putExtra(Variable.webUrl, jgqAppIcon.getUrl());
                    break;
                case "13":// 有标题网页
                    intent = new Intent(context, AliAuthWebViewActivity.class);
                    intent.putExtra("isGoTaobao", false);//不跳淘宝天猫
                    intent.putExtra(Variable.webUrl, jgqAppIcon.getUrl());
                    break;
                case "22":// 信用卡
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("CreditTokenBrocast"), false, 0);
                    break;
                case "23":// 直播好货
                    intent = new Intent(context, LiveInspectListActivity.class);
                    intent.putExtra("name", jgqAppIcon.getName());
                    break;
                case "26":// 大牌精选
                    intent = new Intent(context, BigBrandActivity.class);
                    break;
                case "27":// 金刚区数据三
                    intent = new Intent(context, MainBottomActivity.class).putExtra("type",jgqAppIcon.getUrl()).putExtra("title",jgqAppIcon.getName());
                    break;
                case "31":// 拼多多
                    intent = new Intent(context, MorePlatformNewActivity340.class);
                    intent.putExtra("type", 1);
                    break;
                case "32":// 京东
                    intent = new Intent(context, MorePlatformNewActivity340.class);
                    intent.putExtra("type", 2);
                    break;
                case "33":// 唯品会
                    intent = new Intent(context, MorePlatformNewActivity.class);
                    intent.putExtra("type", 3);
                    break;
                case "34":// 苏宁易购
                    intent = new Intent(context, MorePlatformNewActivity.class);
                    intent.putExtra("type", 5);
                    break;
                case "35":// 考拉海购
                    intent = new Intent(context, MorePlatformNewActivity.class);
                    intent.putExtra("type", 6);
                    break;
                case "41":
                    // 请求活动链接
                    if (MainActivity.mainActivity != null) {
                        MainActivity.mainActivity.getActivityChain(DateStorage.getInformation().getUserid(), jgqAppIcon.getUrl());
                    }
                    break;
                case "42":
                    intent = new Intent(context, AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, jgqAppIcon.getUrl());
                    intent.putExtra("isTitle", false);
                    intent.putExtra("noGoTaoBaoH5", 12);
                    break;
                case "43":
                    intent = new Intent(context, MyTaskFragmentActivity.class);
                    break;
                case "44":
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("HomeImgClickBrocast"), jgqAppIcon, 44);
                    break;
                case "45":// 生活券
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("HomeImgClickBrocast"), jgqAppIcon, 45);
                    break;
                case "46":// 美团
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("HomeImgClickBrocast"), jgqAppIcon, 46);
                    break;
                case "14"://PJW
                    if (jgqAppIcon.getUrl()!=null) {
                        Object bannerObject = JSON.parseObject(jgqAppIcon.getUrl(), JDBanner.class);
                        JDBanner banner = (JDBanner) bannerObject;
                        int platformType=1;
                        switch (banner.getCpsType()){
                            case "pdd":
                                platformType=1;
                                break;
                            case "jd":
                                platformType=2;
                                break;
                            case "wph":
                                platformType=3;
                                break;
                            case "sn":
                                platformType=5;
                                break;
                            case "kl":
                                platformType=6;
                                break;
                        }
                        Utils.getJumpTypeForMorePl(context,banner.getClickType(),banner.getClickVaule(),platformType,banner.getNeedLogin(),banner.getName(),banner.getSysParam());
                    }
                    break;
                case "51":// 唤醒小程序
                    Utils.launchLittleApp((Activity) context,jgqAppIcon.getUrl());
                    break;
                case "52"://跳转到热卖榜单选项卡
//                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 4);
                    intent=new Intent(context, HotSellFragmentActivity.class);
                    break;
                case "53"://跳转超级分类
                    intent=new Intent(context, SortThreeFragmentActivity.class);
                    break;
                case "54"://跳转游戏类型
                    try {
                        org.json.JSONObject jsonObject = new org.json.JSONObject(jgqAppIcon.getUrl());
                        String ADplaceid=jsonObject.getString("android");
                        intent=new Intent(context, ADActivity.class).putExtra("title",jgqAppIcon.getName()).putExtra("ADplaceid",ADplaceid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    intent = new Intent(context, NewActivity.class);
                    intent.putExtra("name", jgqAppIcon.getName());
                    intent.putExtra("labelType", jgqAppIcon.getLabeltype());
                    break;
            }
        }
        if (intent!=null){
            context.startActivity(intent);
        }
    }




    /** 去掉指定字符串的开头的指定字符
     * @param stream 原始字符串
     * @param trim 要删除的字符串
     * @return
     */
    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end-1);
        }
        // 返回处理后的字符串
        return stream;
    }

    /**
     * 将异常信息转化成字符串
     * @param t
     * @return
     * @throws IOException
     */
    public static String exception(Throwable t) throws IOException {
        if(t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            t.printStackTrace(new PrintStream(baos));
        }finally{
            baos.close();
        }
        return baos.toString();
    }

    /**
     * 格式化
     */
    private static DecimalFormat dfs = null;
    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }

    //App拉起小程序
    public static void launchLittleApp(Activity context,String path) {
        try {
            String appId = Variable.weChatAppId; // 填应用AppId
            IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
            if (!api.isWXAppInstalled()) {
                ToastUtil.showImageToast(context, "请安装微信", R.mipmap.toast_error);
                return;
            }
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = DateStorage.getMicroAppId(); // 填小程序原始id
            req.path = path;//拉起小程序页面的可带参路径，不填默认拉起小程序首页
            if (BuildConfig.DEBUG) {
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
            } else {
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            }
            api.sendReq(req);
        }catch (Exception e){
          toast("需要打开悬浮窗权限");
        }
    }

    //小程序分享给好友
    public static void shareLittleAppContentToWechat(Activity context,String path,String title,String desc,Bitmap bm){
        String appId = Variable.weChatAppId; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
        if (!api.isWXAppInstalled()) {
            ToastUtil.showImageToast(context,"请安装微信",R.mipmap.toast_error);
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        if (BuildConfig.DEBUG) {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;// 正式版:0，测试版:1，体验版:2
        }else{
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
        }
        miniProgramObj.userName =DateStorage.getMicroAppId();     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        miniProgramObj.withShareTicket=true;
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;// 小程序消息title
        msg.description = desc;// 小程序消息desc
        try {
            msg.thumbData =ImageDispose.readStream(ImageDispose.Bitmap2InputStream(bm)); // 小程序消息封面图片，小于128k
        } catch (Exception e) {
            e.printStackTrace();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "miniProgram";
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        api.sendReq(req);
    }
    /**
     * 针对 Android 27 的情况进行处理
     * 横竖屏设置了方向会崩溃的问题
     *
     * @return
     */
    public static boolean isTranslucentOrFloating(Activity activity) {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            TypedArray ta = activity.obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    /**
     * 修复横竖屏 crash 的问题
     * @return
     */
    public static boolean fixOrientation(Activity activity){
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(activity);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /*
     *唤醒微信
     */
    public static void openWechat(Context context){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            toast("检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    //打开网易七鱼客服界面
    public static void openMyService(Context context,String userId,String dataJson,String goodsJson){
        String serviceName="呆萌客服";
        String sourceUrl="";
        String sourceTitle="呆萌客服";
        String userName="";
        YSFUserInfo userInfo = new YSFUserInfo();
// App 的用户 ID
        userInfo.userId = userId;
        userInfo.data=dataJson;
        Unicorn.setUserInfo(userInfo);
/**
 * 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入。
 * 三个参数分别为：来源页面的url，来源页面标题，来源页面额外信息（保留字段，暂时无用）。
 * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
 */
    ConsultSource source = new ConsultSource(sourceUrl, sourceTitle, userName);
        /**
         * 配置客服信息
         */
//        source.prompt = "连接客服成功的提示语";
//        source.VIPStaffAvatarUrl = "头像的 url";
//        source.vipStaffName = "客服的的名字";
//        source.vipStaffWelcomeMsg = "客服的欢迎语";

        /*
         *配置商品信息
         * title	商品标题	长度限制为 100 字符，超过自动截断。
           desc	商品详细描述信息。	长度限制为 300 字符，超过自动截断。
           note	商品备注信息（价格，套餐等）	长度限制为 100 字符，超过自动截断。
           picture	缩略图图片的 url。	该 url 需要没有跨域访问限制，否则在客服端会无法显示。
           长度限制为 1000 字符， 超长不会自动截断，但会发送失败。
           url	商品信息详情页 url。	长度限制为 1000 字符，超长不会自动截断，但会发送失败。
         */
       JSONObject object = JSON.parseObject(goodsJson);
        source.productDetail=new ProductDetail.Builder()
                .setTitle(object.getString("title"))
                .setDesc(object.getString("desc"))
                .setNote(object.getString("note"))
                .setPicture(object.getString("picture"))
                .setShow(object.getInteger("show"))
                .setSendByUser(object.getBoolean("sendByUser"))
                .build();

/**
 * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
 * 如果返回为false，该接口不会有任何动作
 *
 * @param context 上下文
 * @param title   聊天窗口的标题
 * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
 */
     Unicorn.openServiceActivity(context, serviceName, source);
    }


   //解决文本框自动换行 留白太大
    public static String autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }

    //判断是否安装拼多多
    public static boolean checkHasInstalledApp(@NonNull Context context, String pkgName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(pkgName, PackageManager.GET_GIDS);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        } catch (RuntimeException e) {
            app_installed = false;
        }
        return app_installed;
    }

    //唤醒对应的平台 逻辑处理
    public static boolean isNullOrEmpty(String str){
        if(str == null || str.length() == 0|| "".equals(str)){
            return true;
        }
        return false;
    }
    public static void callMorePlatFrom(Context con,int platType,String openAppContent,String H5Link){
        boolean hasInstalled=false;
     switch (platType) {
         case 1://拼多多
             hasInstalled = Utils.checkHasInstalledApp(con, "com.xunmeng.pinduoduo");
             if (hasInstalled){
                 Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(openAppContent));
                 con.startActivity(intent);
             }else{
                 con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","pdd"));
             }
         break;
         case 2://京东
             mKeplerAttachParameter = new KeplerAttachParameter();//这个是即时性参数  可以设置
             mHandler=new Handler();
             mKelperTask = KeplerApiManager.getWebViewService().openAppWebViewPage(con, H5Link, mKeplerAttachParameter, mOpenAppAction);
             break;
         case 3://唯品会
             hasInstalled = Utils.checkHasInstalledApp(con, "com.achievo.vipshop");
             if (hasInstalled){
                 if (openAppContent.equals("")){
                     con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","wph"));
                 }else{
                     Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(openAppContent));
                     con.startActivity(intent);
                 }
             }else{
                 con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","wph"));
             }
             break;
         case 5://苏宁易购
             hasInstalled = Utils.checkHasInstalledApp(con, "com.suning.mobile.ebuy");
             if (hasInstalled){
                 Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(openAppContent));
                 con.startActivity(intent);
             }else{
                 con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","sn"));
             }
             break;
         case 6://考拉海购
             hasInstalled = Utils.checkHasInstalledApp(con, "com.kaola");
             if (hasInstalled){
                 if (openAppContent.equals("")){
                     con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","kl"));
                 }else{
                     Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(openAppContent));
                     con.startActivity(intent);
//                     KaolaLaunchHelper.launchKaola(con, openAppContent);
                 }
             }else{
                 con.startActivity(new Intent(con,WebPagePJWActivity.class).putExtra("url",H5Link).putExtra("type","kl"));
             }
             break;

     }
    }
   public static KelperTask mKelperTask;
   private static Handler mHandler;
   public static KeplerAttachParameter mKeplerAttachParameter;//这个是即时性参数  可以设置
   public static OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(final int status, final String url) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，
                    }else {
                        mKelperTask = null;
                    }
                    if(status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
                        MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), WebPagePJWActivity.class).putExtra("url",url).putExtra("type","jd").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else if(status == OpenAppAction.OpenAppAction_result_BlackUrl){
                        MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(),WebPagePJWActivity.class).putExtra("url",url).putExtra("type","jd").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else if(status == OpenAppAction.OpenAppAction_result_ErrorScheme){
                        Toast.makeText(MyApplication.getContext(), "呼起协议异常"+" ,code="+status, Toast.LENGTH_SHORT).show();
                    }else if(status == OpenAppAction.OpenAppAction_result_APP){

                    }else if(status == OpenAppAction.OpenAppAction_result_NetError){
                    }
                }
            });
        }
    };


   //检查权限
    static String[] permissions = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    public static  boolean haspermission=false;
    static List<String> mPermissionList = new ArrayList<>();
    public static int WRITE_EXTERNAL_STORAGE_REQUEST = 1;
    public static boolean checkPermision(Activity context,int code,boolean isNeedCheck) {
        if (isNeedCheck||DateStorage.getLoginStatus()) {
            WRITE_EXTERNAL_STORAGE_REQUEST = code;
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                haspermission = true;
                File destDir = new File(Variable.HomePath);
                if (!destDir.exists()) {
                    // 创建SD卡路径
                    destDir.mkdir();
                }
            } else {//请求权限方法
                haspermission = false;
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(context, permissions, WRITE_EXTERNAL_STORAGE_REQUEST);
            }
            return haspermission;
        }
        return false;
    }
}
