package com.example.test.andlang.http;

import java.util.ArrayList;
import java.util.List;

public class HttpConfig {
    public static String tmpImageDir="nyso";//配置文件下载sd文件夹
    public static String imgUpDefName="pic";//图片上传后台定义的接收文件的参数名称，由后台提供
    public static String cookieName="JSSIONID";//有效的cookie名称，用户登录标识，由后台提供
    public static boolean NetSpeedListener=false;//是否开启网速监控
    public static boolean openDns=false;//是否开启DNS服务解析
    public static List<CodeDefBean> codeDefBeanList;

    public static void addCodeConfig(int code,String dec){
        if(codeDefBeanList==null){
            codeDefBeanList=new ArrayList<CodeDefBean>();
        }
        CodeDefBean codeDefBean=new CodeDefBean();
        codeDefBean.setCode(code);
        codeDefBean.setDec(dec);
        codeDefBeanList.add(codeDefBean);
    }

    public static boolean isErrorCode(int code){
        boolean isError=false;
        if(codeDefBeanList!=null){
            for (CodeDefBean bean:codeDefBeanList
                 ) {
                if(code==bean.getCode()){
                    isError=true;
                    break;
                }
            }
        }
        return isError;
    }
}
