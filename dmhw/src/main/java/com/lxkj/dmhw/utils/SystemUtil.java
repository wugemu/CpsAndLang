package com.lxkj.dmhw.utils;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Locale;

public class SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    //是否小米手机
    public static boolean isXiaomi() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    //是否华为手机
    public static boolean isHuawei() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer)) {
            String emuiVer=getEMUI();
            Log.e("0.0","EMUI:"+emuiVer);
            if(!isEmpty(emuiVer)&&emuiVer.startsWith("EmotionUI_")){
                String verNum=emuiVer.replace("EmotionUI_","");
                if(!isEmpty(emuiVer)&&verNum.length()>0){
                    if(verNum.contains(".")) {
                        verNum=verNum.substring(0,verNum.indexOf("."));
                    }
                    try {
                        int verCode=Integer.parseInt(verNum);
                        Log.e("0.0","EMUI处理版本:"+verCode);
                        if(verCode>=5){
                            return true;
                        }
                    }catch (Exception e){

                    }
                }
            }
        }
        return false;
    }

    public static String getEMUI() {
        Class<?> classType = null;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildVersion;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return false;
        }
        return true;
    }
    //判断是否是纯数字字符串
    public static boolean isNumericStr(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
