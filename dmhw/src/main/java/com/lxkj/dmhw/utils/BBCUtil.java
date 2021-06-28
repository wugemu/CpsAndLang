package com.lxkj.dmhw.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.FileUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.MMKVUtil;
import com.example.test.andlang.util.PicSelUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.VersionUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.NewsBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.widget.PredicateLayout;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bill56 on 2018-1-11.
 */

public class BBCUtil {
    public static final int REQUEST_PERMISSION = 0;
    private static StringBuilder sb = new StringBuilder();

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        if(context==null){
            return ;
        }
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("sd_copytext", content);//text是内容
        cmb.setPrimaryClip(myClip);
    }

    /**
     * 获取粘贴板里的文本功能
     * add by wangqianzhou
     *
     */
    public static String getCopyText(Context context) {
        // 得到剪贴板管理器
        if(context==null){
            return "";
        }
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = cmb.getPrimaryClip();
        //  ClipData 里保存了一个ArryList 的 Item 序列， 可以用 getItemCount() 来获取个数
        String text="";
        if(data!=null&&data.getItemCount()>0) {
            ClipDescription description=data.getDescription();
            if(!(description!=null&&description.getLabel()!=null&&"sd_copytext".equals(description.getLabel().toString()))){
                ClipData.Item item = data.getItemAt(0);
                if(item!=null&&item.getText()!=null) {
                    text = item.getText().toString();// 注意 item.getText 可能为空
                }
            }
        }
        LogUtil.d("0.0","获取剪切板中的内容:"+text);
        return text;
    }

    //获取屏幕宽度
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    // android获取一个用于打开浏览器的intent
    public static void openWebBrowser(Activity activity, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isColorStr(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.startsWith("#") && (str.length() == 7 || str.length() == 9)) {
            return true;
        }

        return false;
    }


    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public static Map<String, String> getClientHeader() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Client-Type", "MihuiAndroid");
        return header;
    }

    public static String getUrlParma(String url, String key) {

        if (url != null && key != null && url.contains(key)) {
            String paramUrl = url.substring(url.indexOf("?") + 1);
            if (paramUrl != null && paramUrl.indexOf("=") > -1) {
                String[] arrTemp = paramUrl.split("&");
                if (arrTemp == null) {
                    return "";
                }
                for (String str : arrTemp) {
                    String[] qs = str.split("=");
                    if (qs != null && qs.length >= 2 && key.equals(qs[0])) {
                        return qs[1];
                    }
                }
            }
        }
        return "";
    }



    public static String getHideMobile(String mobile) {
        if (mobile.length() > 4) {
            return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
        }
        return mobile;
    }

    public static byte[] getAssertsFile(Context context, String fileName) {
        InputStream inputStream = null;
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open(fileName);
            if (inputStream == null) {
                return null;
            }

            BufferedInputStream bis = null;
            int length;
            try {
                bis = new BufferedInputStream(inputStream);
                length = bis.available();
                byte[] data = new byte[length];
                bis.read(data);

                return data;
            } catch (IOException e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {

                    }
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo packInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }


    /**
     * 手机号码验证
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (isEmpty(str) || str.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static int calculateCharCount(String str, char target) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == target) {
                count++;
            }
        }
        return count;

    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int size) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int showCount = listAdapter.getCount();
        if (showCount > size) {
            showCount = size;  // 展示的位置只显示size个
        }
        for (int i = 0; i < showCount; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (showCount - 1));
        listView.setLayoutParams(params);
    }

    public static int sp2px(Context context, int defaultSize) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (defaultSize * dm.density);
    }

    /**
     * 将double类型数据转换2位小数的税费价格
     *
     * @param d
     * @return
     */
    public static String getTaxFormat(double d) {

        return String.format("%.2f", getDoubleRoundOf(d));
//        DecimalFormat df = new DecimalFormat("##.#%");
    }

    /**
     * 四舍五入
     *
     * @param d
     * @return //先保留三位小数，再保留两位小数
     */
    public static double getDoubleRoundOf(Double d) {
        BigDecimal bg = new BigDecimal(String.valueOf(d));
        d = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        d = new BigDecimal(String.valueOf(d)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

    /**
     * 获取百分比
     */
    public static String getBFB(double d) {
        d *= 100;
        d = getDoubleRoundOf(d, 2);
        return String.format("%.2f", d) + "%";
    }

    /**
     * 四舍五入
     *
     * @param d
     * @return //先保留三位小数，再保留两位小数
     */
    public static double getDoubleRoundOf(Double d, int round) {
        BigDecimal bg = new BigDecimal(String.valueOf(d));
        d = bg.setScale(round + 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        d = new BigDecimal(String.valueOf(d)).setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

    public static String getString(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    public static boolean isPhoneNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.length() != 11) {
            return false;
        }
        if (!str.startsWith("1")) {
            return false;
        }
        return true;
    }

    /**
     * 将double类型数据转换2位小数的文本
     *
     * @param d
     * @return
     */
    public static String getDoubleFormat2(double d) {
        return String.format("%.2f", getDoubleRoundOf(d));
    }

    /**
     * 去除小数点尾部0
     *
     * @return
     */
    public static String getDoubleFormat(Double price) {
        String s = price.toString();
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String urlDecode(String url) {
        url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 版本检查
     *
     * @return
     */
//    public static boolean checkUpdate(Context context, SysVer version) {
//        int verNo = BBCUtil.getVersionCode(context);
//        if (version != null && version.getVerValue() > verNo) {
//            return true;
//        }
//        return false;
//    }

    //获取链接中的host
    public static String getUrlHost(String weexUrl) {
        try {
            URL url = new URL(weexUrl);
            String hostStr = url.getHost();
            if (!BBCUtil.isEmpty(hostStr)) {
                return hostStr;
            }
        } catch (Exception e) {

        }
        return "weex";
    }

    /**
     * @param arr    选择列表数组
     * @param str    当前选择的字符串
     * @param offset 返回type与数组下标的偏移量
     * @return
     */
    public static String getRefundValue(String[] arr, String str, int offset) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(str)) {
                return i + offset + "";
            }
        }
        return "";
    }

    /**
     * 替换星号返回
     *
     * @return
     */
    public static String encryption(String str, int start, int end) {
        str = str.substring(0, start);
        for (int i = 0; i < end - start; i++) {
            str = str + "*";
        }
        return str;
    }

    /**
     * 替换星号返回
     *
     * @return
     */
    public static String encryption(String str, int start) {
        return encryption(str, start, str.length());
    }


    //获取本地weex js文件路径
    public static String getLocalJs(String filename) {
        return FileUtil.getFilePath(filename);
    }

    //获取本地zip中js文件路径
    public static String getLocalZipJs(String weexUrl) {
        if (BBCUtil.isEmpty(weexUrl)) {
            return "";
        }
        String host = getUrlHost(weexUrl);
        if (BBCUtil.isEmpty(host)) {
            return "";
        }
        File weexdir = PicSelUtil.getFile(host);
        if (!weexdir.exists()) {
            return "";
        }
        if (weexUrl.contains("?")) {
            weexUrl = weexUrl.substring(0, weexUrl.indexOf("?"));
        }
        String fileName = weexUrl.substring(weexUrl.lastIndexOf("/") + 1);
        File jsFile = new File(weexdir.getAbsolutePath(), fileName);
        return jsFile.exists() ? "file://" + jsFile.getAbsolutePath() : "";
    }

    //获取解压的js文件夹地址
    public static String getZipJsDir(String zipJsDir) {
        File file = PicSelUtil.getAndCreateDir(zipJsDir);
        if(file!=null) {
            return file.getAbsolutePath();
        }else {
            return "";
        }
    }


    public static String getFileAddSpace(String replace) {
        String regex = "(.{4})";
        replace = replace.replaceAll(regex, "$1 ");
        return replace;
    }

    public static Map<String, Object> getMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("value", value);

        return map;
    }

    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null
                && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //色值过渡
    public static int colorOffset(String fromColor, String toColor, float offset, boolean isLeft) {
        if (!isColorStr(fromColor) || !isColorStr(toColor)) {
            return Color.parseColor("#FFFFFF");
        }

        int fromColorInt = Color.parseColor(fromColor);
        int formred = (fromColorInt & 0xff0000) >> 16;
        int formgreen = (fromColorInt & 0x00ff00) >> 8;
        int formblue = (fromColorInt & 0x0000ff);

        int toColorInt = Color.parseColor(toColor);
        int tored = (toColorInt & 0xff0000) >> 16;
        int togreen = (toColorInt & 0x00ff00) >> 8;
        int toblue = (toColorInt & 0x0000ff);

        if (!isLeft) {
            tored = (int) (formred + (tored - formred) * offset);
            togreen = (int) (formgreen + (togreen - formgreen) * offset);
            toblue = (int) (formblue + (toblue - formblue) * offset);
        } else {
            tored = (int) (formred + (tored - formred) * (1 - offset));
            togreen = (int) (formgreen + (togreen - formgreen) * (1 - offset));
            toblue = (int) (formblue + (toblue - formblue) * (1 - offset));
        }

        toColorInt = Color.rgb(tored, togreen, toblue);
//        LogUtil.e("0.0","过渡色最后toColorInt:"+toColorInt);
        return toColorInt;
    }


    /**
     * 通过scheme拉起指定app
     *
     * @param context
     * @param scheme
     */
    public static void openApp(Context context, String scheme) {
        try {
            if (!scheme.contains("?")) {
                scheme += "?r=" + System.currentTimeMillis();
            } else {
                scheme += "&r=" + System.currentTimeMillis();
            }
            LogUtil.d("0.0","拉起指定app:"+scheme);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //商品列表标签添加方法
    public static void addGoodTag(Context context, PredicateLayout pl_taglist, GoodBean goodBean) {
        if (context == null) {
            return;
        }
        if (pl_taglist == null) {
            return;
        }
        if (goodBean == null) {
            return;
        }
        pl_taglist.removeAllViews();
        List<String> tagList = new ArrayList<String>();
        if (goodBean.isIfSpecOfApp()) {
            tagList.add("秒杀");
        } else if (!BBCUtil.isEmpty(goodBean.getSpecialTag())) {
            //特价标签
            tagList.add(goodBean.getSpecialTag());
        }
        if (goodBean.isIfNew()) {
            tagList.add("新品");
        }
        //V1.0.9去除清仓标签
//        if(goodBean.isIfDead()){
//            tagList.add("清仓");
//        }
        if (goodBean.isIfFullReduc() || goodBean.isIfActivity()) {
            tagList.add("满减");
        }
        if (goodBean.isIfPackageMail()) {
            tagList.add("包邮");
        }
        if (goodBean.isIfTaxFree()) {
            tagList.add("包税");
        }
        if (goodBean.isIfHot()) {
            tagList.add("爆款");
        }
        if (goodBean.getDeliveryType() == 1) {
            tagList.add("保税");
        } else if (goodBean.getDeliveryType() == 5) {
            tagList.add("国内");
        } else if (goodBean.getDeliveryType() == 2 || goodBean.getDeliveryType() == 4) {
            tagList.add("直邮");
        }
        if (goodBean.isIfCompensate()) {
            tagList.add("假一赔十");
        }

        for (int i = 0; i < tagList.size(); i++) {
            String key = tagList.get(i);
            TextView txt = new TextView(context);
            txt.setSingleLine(true);
            txt.setTextColor(context.getResources().getColor(R.color.colorGoldMain));
            txt.setBackgroundResource(R.drawable.bg_rect_stroke_gold_2dp);
//            switch (key) {
//                case "爆款":
//                case "满减":
//                case "秒杀":
//                case "假一赔十":
//                case "包税":
//                case "包邮":
//                    txt.setBackgroundResource(R.drawable.tag_bg_red);
//                    txt.setTextColor(context.getResources().getColor(R.color.colorRedMain));
//                    break;
//                case "新品":
//                    txt.setBackgroundResource(R.drawable.tag_bg_orange);
//                    txt.setTextColor(context.getResources().getColor(R.color.colorOrageText));
//                    break;
//                case "保税":
//                case "国内":
//                case "直邮":
//                    txt.setBackgroundResource(R.drawable.tag_bg_purple);
//                    txt.setTextColor(context.getResources().getColor(R.color.purple));
//                    break;
//                case "清仓":
//                    txt.setBackgroundResource(R.drawable.tag_bg_green);
//                    txt.setTextColor(context.getResources().getColor(R.color.colorGreenText));
//                    break;
//                default:
//                    txt.setBackgroundResource(R.drawable.tag_bg_red);
//                    txt.setTextColor(context.getResources().getColor(R.color.colorRedMain));
//                    break;
//            }

            txt.setText(key);
            txt.setClickable(true);
            txt.setTextSize(10);
            txt.setGravity(Gravity.CENTER);
//            int padding1 = (int) context.getResources().getDimension(R.dimen.padding_2dp);
            int padding2 = (int) context.getResources().getDimension(R.dimen.dp_4);
            int margin1 = (int) context.getResources().getDimension(R.dimen.dp_5);
            int margin2 = (int) context.getResources().getDimension(R.dimen.dp_4);
            pl_taglist.setDividerLine(margin1);
            pl_taglist.setDividerCol(margin2);
            txt.setPadding(padding2, 0, padding2, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, (int) context.getResources().getDimension(R.dimen.dp_14));
            txt.setLayoutParams(params);
            pl_taglist.addView(txt);
        }
    }



    //百度转高德
    public static String[] bd_decrypt(double bd_lat, double bd_lon) {
        String[] gdLonLat = new String[2];
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        gdLonLat[0] = gg_lon + "";
        gdLonLat[1] = gg_lat + "";
        return gdLonLat;
    }

    //本地文件存储权限判断
    public static boolean checkStoragePer(Activity context) {
        //检测本地存储权限
        PackageManager pkgManager = context.getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
            //权限申请
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        return sdCardWritePermission;
    }


    public static String logSignature(PackageInfo packageInfo) {
        String sha1Str = null;
        try {
            Signature[] signaturesArr = packageInfo.signatures;
            Signature sign = signaturesArr[0];
            String md5Str = encryptionMD5(sign.toByteArray());
            sha1Str = encryptionSHA1(sign.toByteArray());
            String sha256Str = encryptionSHA256(sign.toByteArray());
            LogUtil.e("0.0", "md5--" + md5Str);
            LogUtil.e("0.0", "sha1--" + sha1Str);
            LogUtil.e("0.0", "sha256--" + sha256Str);

        } catch (Exception e) {

        }
        return sha1Str;
    }

    /**
     * MD5加密
     *
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        String md5Str = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            md5Str = byte2HexFormatted(byteArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    //这个是获取SHA1的方法
    public static String encryptionSHA1(byte[] byteStr) {
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(byteStr);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * SHA256
     */
    public static String encryptionSHA256(byte[] byteStr) {
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(byteStr);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        return min;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getByteByUrl(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            int options = 100;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            byte[] data = baos.toByteArray();//10代表压缩90%
            while (data.length > 127 * 1024) {
                //超过32k字节
                baos.reset();//重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);     //10代表压缩90%
                data = baos.toByteArray();
                options = options - 10;
               if (options<=0){
                   options=1;
                   baos.reset();//重置baos即清空baos
                   bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);     //10代表压缩90%
                   data = baos.toByteArray();
                   break;
               }
            }
            bitmap.recycle();
            return data;
        } catch (Exception e) {
            LogUtil.e("0.0", e.toString());
        }

        return null;
    }

    public static byte[] inputStreamToByte(InputStream is, Bitmap bitmap) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isBigVer121(Context context) {
        //大于121版本 V121团购版本 V122内购版本
        int verCode = VersionUtil.getVersionCode(context);
        if (verCode <= 121) {
            return false;
        }
        return true;
    }

    public static double getAspectRatio(Activity activity) {
        return getRealWidth(activity) * 1.0 / getRealHeight(activity);
    }

    public static int getRealWidth(Activity activity) {
        Point outSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        return outSize.x;
    }

    public static int getRealHeight(Activity activity) {
        Point outSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        return outSize.y;
    }

    public static int getHeight(int widthProportion, int heightProportion, int width) {
        int temp = width / widthProportion;

        return temp * heightProportion;
    }


    public static String getStrFromMap(Map<String,Object> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public static boolean isValidIMEI(String imei){
        if(BaseLangUtil.isEmpty(imei)){
            return false;
        }
        for (int i=0;i<imei.length();i++){
            //判断全为0的情况 视为无效imei
            if(!"0".equals(imei.charAt(i)+"")){
                return true;
            }
        }
        return false;
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * view 为输入框
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFromWindow(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //判断当前是否在UI线程
    public static boolean isMainThread(String tag){
        boolean isMain= Thread.currentThread() == Looper.getMainLooper().getThread();
        if(isMain){
            LogUtil.e("0.0","主主主主线程处理"+tag);
        }else {
            LogUtil.e("0.0","子子子子线程处理"+tag);
        }
        return isMain;
    }

    public static boolean isHttpUrl(String url){
        if(BBCUtil.isEmpty(url)){
            return false;
        }
        if(url.startsWith("http")
                || url.startsWith("https")){
            return true;
        }
        return false;
    }

    public static String getIMEI(Context context) {
        String imei = MMKVUtil.getString("imei");
        if (BaseLangUtil.isEmpty(imei)) {
            try {
                TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = mTelephonyMgr.getDeviceId();
                MMKVUtil.putString("imei", imei);
            } catch (SecurityException e) {
                imei = Settings.Secure.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
                MMKVUtil.putString("imei", imei);
            }
        }
        if (!BBCUtil.isValidIMEI(imei)) {
            //Android 10 获取不到设备号处理
            imei = UUID.randomUUID().toString();
            MMKVUtil.putString("imei", imei);
        }
        return imei;
    }

    //图片浏览
    public static void openImgPreview(Context context, int postion, List<String> imgList, boolean hiddenTopBar) {
        if (imgList == null) {
            return;
        }
        if (postion >= imgList.size()) {
            postion = 0;
        }
        List<LocalMedia> selectionMedia = new ArrayList<LocalMedia>();
        for (String imgStr : imgList) {
            LocalMedia media = new LocalMedia();
            media.setCompressed(false);
            media.setCut(false);
            media.setPath(imgStr);
            selectionMedia.add(media);
            LogUtil.d("0.0", "图片：" + imgStr);
        }
        PictureSelector.create((Activity) context).themeStyle(R.style.picture_default_style).openExternalPreview(postion, selectionMedia, hiddenTopBar);
    }

    public static List<ImageView> addImages(Context context, List<NewsBean> images, LinearLayout ll_images) {
        List<ImageView> imageViewList = new ArrayList<>();
        //图文详情
        if (images != null && images.size() > 0) {
            ll_images.setVisibility(View.VISIBLE);
            if (ll_images.getChildCount() == 0) {
                try {
                    List<String> imgList = new ArrayList<String>();
                    for (NewsBean newsBean : images) {
                        imgList.add(newsBean.getImgUrl());
                    }
                    for (int i = 0; i < images.size(); i++) {
                        NewsBean newsBean = images.get(i);
                        final ImageView imageView = new ImageView(ll_images.getContext());
                        imageView.setAdjustViewBounds(true);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                        GlideUtil.getInstance().displayNoBg(ll_images.getContext(), newsBean.getImgUrl(),imageView);
                        ImageLoadUtils.doLoadImageUrl(imageView, newsBean.getImgUrl());
                        ll_images.addView(imageView);
                        //点击图片
                        final int postion = i;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BBCUtil.openImgPreview(context, postion, imgList, false);
                            }
                        });
                        imageView.setTag(newsBean.getImgUrl());
                        imageViewList.add(imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ll_images.setVisibility(View.GONE);
        }

        return imageViewList;
    }

    public static boolean isWXAppInstalledAndSupported(Context context) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(Constants.WXAPPID);
        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled();
        return sIsWXAppInstalledAndSupported;
    }

    //保存网络视频到本地
    public static void saveVideo(Context context, String videoStr) {
        if (BaseLangUtil.isEmpty(videoStr)) {
            return;
        }
        //非ui线程进行图片下载
        new Thread() {
            @Override
            public void run() {
                super.run();
                //进行自己的操作
                BBCHttpUtil.downVideo(context, videoStr);
            }
        }.start();

    }

    //保存网络图片到本地
    public static void save9Img(Context context, List<String> imageList) {
        if (imageList == null || imageList.size() == 0) {
            return;
        }

        //非ui线程进行图片下载
        new Thread() {
            @Override
            public void run() {
                super.run();
                //进行自己的操作
                for (String img : imageList) {
                    //内存溢出
//                    Bitmap bitmap=BBCHttpUtil.downImage(img);
//                    String path=FileUtil.saveHttpImageToLocal(context,bitmap, System.currentTimeMillis()+ ".jpg");

                    if (!BBCUtil.isEmpty(img) && img.startsWith("http")) {
                        BBCHttpUtil.download9Picture(context, img);
                    } else {

                    }
                }
            }
        }.start();
    }
    //打开微信
    public static void openWX(Context context) {
        if (BBCUtil.isWXAppInstalledAndSupported(context)) {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } else {
            ToastUtil.show(context, "未检测到微信客户端，请安装");
        }
    }

    public static boolean ifBillVip(Context context) {
        int ifBillVip = MMKVUtil.getInt(Constants.IFBILLVIP);
        if (ifBillVip > 1) {
            return true;
        } else {
            return false;
        }
    }
}
