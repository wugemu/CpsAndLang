-optimizationpasses 7                              # 指定代码的压缩级别
-dontusemixedcaseclassnames                       # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                 # 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclassmembers           # 指定不去忽略包可见的库类的成员
-dontpreverify                                      # 混淆时是否做预校验
-verbose                                             # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*      # 混淆时所采用的算法
-ignorewarning
-keep public class * extends android.app.Activity                               # 保持哪些类不被混淆
-keep public class * extends android.app.Application                            # 保持哪些类不被混淆
-keep public class * extends android.app.Service                                # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider                    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference                      # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter     # 保持哪些类不被混淆
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder       # 保持哪些类不被混淆
-keep public class com.tencent.bugly.**{*;}                                     # 保持哪些类不被混淆
-keep class com.lxkj.dmhw.defined.JavascriptHandler                             # 保持哪些类不被混淆
-keep class android.support.v4.** { *; }                                        # 保持哪些类不被混淆
-keep interface android.support.v4.app.** { *; }                                # 保持哪些类不被混淆
-keep class com.loopj.android.http.** { *;}                                     # 保持哪些类不被混淆
-keep class android.net.compatibility.** { *;}                                  # 保持哪些类不被混淆
-keep class android.net.http.** { *;}                                           # 保持哪些类不被混淆
-keep class com.android.internal.http.multipart.** { *;}                        # 保持哪些类不被混淆
-keep class org.apache.http.** { *;}                                            # 保持哪些类不被混淆
-keep class org.json.** { *; }                                                  # 保持哪些类不被混淆
-keep class butterknife.** { *; }                                               # 保持哪些类不被混淆
-keep class **$$ViewBinder { *; }                                               # 保持哪些类不被混淆
-keep class com.alibaba.** { *; }                                               # 保持哪些类不被混淆
-keep class com.alipay.** { *; }                                                # 保持哪些类不被混淆
-keep class com.alipay.android.app.IAlixPay{*;}                                 # 保持哪些类不被混淆
-keep class com.alipay.android.app.IAlixPay$Stub{*;}                            # 保持哪些类不被混淆
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}                   # 保持哪些类不被混淆
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}              # 保持哪些类不被混淆
-keep class com.alipay.sdk.app.PayTask{ public *;}                              # 保持哪些类不被混淆
-keep class com.alipay.sdk.app.AuthTask{ public *;}                             # 保持哪些类不被混淆
-keep class com.alipay.sdk.app.H5PayCallback {                                  # 保持哪些类不被混淆
    <fields>;
    <methods>;
}
#--------------------阿里组件混淆配置start-------------------
-keepattributes Signature
-ignorewarnings
-keep class javax.ws.rs.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.alibaba.fastjson.**
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
-keep class com.taobao.** {*;}
-dontwarn com.ali.auth.**
-keep class com.taobao.securityjni.** {*;}
-keep class com.taobao.wireless.security.** {*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep interface mtopsdk.mtop.global.init.IMtopInitTask {*;}
-keep class * implements mtopsdk.mtop.global.init.IMtopInitTask {*;}
#--------------------阿里组件混淆配置end-------------------
-keep class com.alipay.android.phone.mrpc.core.** { *; }                        # 保持哪些类不被混淆
-keep class com.alipay.apmobilesecuritysdk.** { *; }                            # 保持哪些类不被混淆
-keep class com.alipay.mobile.framework.service.annotation.** { *; }            # 保持哪些类不被混淆
-keep class com.alipay.mobilesecuritysdk.face.** { *; }                         # 保持哪些类不被混淆
-keep class com.alipay.tscenter.biz.rpc.** { *; }                               # 保持哪些类不被混淆
-keep class org.json.alipay.** { *; }                                           # 保持哪些类不被混淆
-keep class com.alipay.tscenter.** { *; }                                       # 保持哪些类不被混淆
#-keep class com.ta.utdid2.** { *;}                                              # 保持哪些类不被混淆
-keep class com.ut.device.** { *;}                                              # 保持哪些类不被混淆
-keep class com.ut.** { *; }                                                    # 保持哪些类不被混淆
-keep class com.ta.** { *; }                                                    # 保持哪些类不被混淆
#-keep class mtopsdk.** { *; }                                                   # 保持哪些类不被混淆
-keep class com.alimama.** {*;}                                                 # 保持哪些类不被混淆
-keep class com.ali.auth.** { *; }                                              # 保持哪些类不被混淆
-keep class com.chad.library.adapter.** { *; }                                  # 保持哪些类不被混淆
-keep enum org.greenrobot.eventbus.ThreadMode { *; }                            # 保持哪些类不被混淆
-keep class com.tencent.mm.opensdk.** { *; }                                    # 保持哪些类不被混淆
-keep class com.tencent.wxop.** { *; }                                          # 保持哪些类不被混淆
-keep class com.tencent.mm.sdk.** { *; }                                        # 保持哪些类不被混淆
-keep class com.tencent.smtt.** { *; }                                          # 保持哪些类不被混淆
-keep class com.tencent.tbs.video.interfaces.** { *; }                          # 保持哪些类不被混淆
-keep class cn.finalteam.galleryfinal.widget.*{*;}                              # 保持哪些类不被混淆
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}                         # 保持哪些类不被混淆
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}                     # 保持哪些类不被混淆
-keep class com.squareup.okhttp3.** { *;}                                       # 保持哪些类不被混淆
-keep class com.lxkj.dmhw.bean.**{*; }                                          # 保持哪些类不被混淆
-keep class com.wang.avi.** { *; }                                              # 保持哪些类不被混淆
-keep class com.wang.avi.indicators.** { *; }                                   # 保持哪些类不被混淆
-keep class **.*_SnakeProxy                                                     # 保持哪些类不被混淆
-keep class com.huawei.hms.** { *; }                                            # 保持哪些类不被混淆
-keep class com.huawei.android.** { *; }                                        # 保持哪些类不被混淆
-keep class com.hianalytics.android.** { *; }                                   # 保持哪些类不被混淆
-keep class com.huawei.updatesdk.** { *; }                                      # 保持哪些类不被混淆
-keep class com.meizu.** { *; }                                                 # 保持哪些类不被混淆
-keep class com.xiaomi.** { *; }                                                # 保持哪些类不被混淆
-keep class org.apache.thrift.** { *; }                                         # 保持哪些类不被混淆
#-keep @com.youngfeng.snake.annotations.EnableDragToClose public class *         # 保持哪些类不被混淆

-keepattributes *Annotation*                # 保留Activity的被@Override注释的方法onCreate、onDestroy方法等
-keepattributes *JavascriptInterface*       # 保持JavascriptInterface不被混淆
-keepattributes Exceptions,InnerClasses     # 保留sdk系统自带的一些内容
-keepattributes SourceFile,LineNumberTable  # 保留SourceFile,LineNumberTable

-keeppackagenames com.alimama.tunion.sdk.**                                                         # 保持 alimama 方法不被混淆
-keepclasseswithmembernames class * {                                                               # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembernames class * {                                                               # 保持 butterknife 方法不被混淆
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {                                                               # 保持 butterknife 方法不被混淆
    @butterknife.* <methods>;
}
-keepclasseswithmembers class * {                                                                   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {                                                                   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {                                            # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {               # 保持自定义控件类不被混淆
     <init>(...);
}
-keepclassmembers class * {                                                                         # 保留JavascriptInterface中的方法
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class ** {                                                                        # 保留Subscribe中的方法
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keepclassmembers public class com.lxkj.dmhw.defined.JavascriptHandler {                            # 保持自定义控件类不被混淆
       <fields>;
       <methods>;
       public *;
       private *;
}
-keepclassmembers class com.zky.medical.WebViewActivity {                                           # 保持自定义控件类不被混淆
       <fields>;
}
-keepclassmembers class com.zky.medical.ThreeFragment {                                             # 保持自定义控件类不被混淆
       <fields>;
}
-keepclassmembers class com.zky.medical.CommodityWebViewFragment {                                  # 保持自定义控件类不被混淆
       <fields>;
}
-keepclassmembers enum * {                                                                          # 保持枚举 enum 类不被混淆
    public static **[] values();    
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {                                                    # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.lxkj.dmhw.R$*{                                                               # 保持 R 不被混淆
    public static final int *;
}
-keep class com.alimama.tunion.sdk.** {                                                             # 保持 alimama 方法不被混淆
    public <fields>;
    public <methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {                                  # 保持 webkit 不被混淆
    public void openFileChooser(...);
}
-keepclassmembers class **.R$* {                                                                    # 保持 R 不被混淆
    public static <fields>;
}


-dontwarn com.tencent.bugly.**                   # 表示对com.tencent.bugly包下的代码不警告
-dontwarn android.support.**                     # 表示对android.support包下的代码不警告
-dontwarn android.net.compatibility.**           # 表示对android.net.compatibility.包下的代码不警告
-dontwarn android.net.http.**                    # 表示对android.net.http包下的代码不警告
-dontwarn com.android.internal.http.multipart.** # 表示对com.android.internal.http.multipart包下的代码不警告
-dontwarn butterknife.internal.**                # 表示对butterknife.internal包下的代码不警告
-dontwarn org.apache.http.**                     # 表示对com.hyphenate包下的代码不警告
-dontwarn okio.**                                # 表示对okio包下的代码不警告
-dontwarn com.squareup.picasso.**                # 表示对com.squareup.picasso包下的代码不警告
-dontwarn in.srain.cube.**                       # 表示对in.srain.cube包下的代码不警告
-dontwarn com.taobao.**                          # 表示对com.taobao包下的代码不警告
-dontwarn com.alibaba.**                         # 表示对com.alibaba包下的代码不警告
-dontwarn com.alipay.**                          # 表示对com.alipay包下的代码不警告
-dontwarn com.ut.**                              # 表示对com.ut包下的代码不警告
-dontwarn com.ta.**                              # 表示对com.ta包下的代码不警告
-dontwarn mtopsdk.**                             # 表示对mtopsdk包下的代码不警告
-dontwarn com.alimama.**                         # 表示对com.alimama包下的代码不警告
-dontwarn com.squareup.okhttp3.**                # 表示对com.squareup.okhttp3包下的代码不警告
-dontwarn okio.**                                # 表示对okio包下的代码不警告
-dontwarn com.huawei.hms.**                      # 表示对com.huawei.hms包下的代码不警告
-dontwarn com.huawei.android.**                  # 表示对com.huawei.android包下的代码不警告
-dontwarn com.hianalytics.android.**             # 表示对com.hianalytics.android包下的代码不警告
-dontwarn com.huawei.updatesdk.**                # 表示对com.huawei.updatesdk包下的代码不警告
-dontwarn com.meizu.**                           # 表示对com.meizu包下的代码不警告
-dontwarn com.xiaomi.push.**                     # 表示对com.meizu包下的代码不警告

#信用卡混淆
-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao{
 public static java.lang.String TABLENAME;
 }
-keep class **$Properties
-keep class data.db.dao.*$Properties{
 public static <fields>;
 }
-keepclassmembers class data.db.dao.**{
public static final <fields>;
 }

 #友盟混淆
 -keep class com.umeng.** {*;}
 -keepclassmembers class * {
    public <init> (org.json.JSONObject);
 }
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#-keep public class javax.**
#-keep public class android.webkit.**
#-dontwarn android.support.v4.**
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#-keep public class com.umeng.socialize.* {*;}
#-keep class com.facebook.**
#-keep class com.facebook.** { *; }
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#-keep class com.umeng.socialize.handler.**
#-keep class com.umeng.socialize.handler.*
#-keep class com.umeng.weixin.handler.**
#-keep class com.umeng.weixin.handler.*
#-keep class com.umeng.qq.handler.**
#-keep class com.umeng.qq.handler.*
#-keep class UMMoreHandler{*;}
#-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-keep class com.tencent.mm.opensdk.** {
#   *;
#}
#-keep class com.tencent.wxop.** {
#   *;
#}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-keep class com.twitter.** { *; }
#-keep class com.tencent.** {*;}
#-dontwarn com.tencent.**
#-keep class com.kakao.** {*;}
#-dontwarn com.kakao.**
#-keep public class com.umeng.com.umeng.soexample.R$*{
#    public static final int *;
#}
#-keep public class com.linkedin.android.mobilesdk.R$*{
#    public static final int *;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep class com.tencent.open.TDialog$*
#-keep class com.tencent.open.TDialog$* {*;}
#-keep class com.tencent.open.PKDialog
#-keep class com.tencent.open.PKDialog {*;}
#-keep class com.tencent.open.PKDialog$*
#-keep class com.tencent.open.PKDialog$* {*;}
#-keep class com.umeng.socialize.impl.ImageImpl {*;}
#-keep class com.sina.** {*;}
#-dontwarn com.sina.**
#-keep class  com.alipay.share.sdk.** {
#   *;
#}
#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#-keep class com.linkedin.** { *; }
#-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#-keepattributes Signature

 #网易七鱼
 -dontwarn com.qiyukf.**
 -keep class com.qiyukf.** {*;}

#饺子视频
-keepclassmembers class * extends cn.jzvd.Jzvd{
 public <init>(android.content.Context);
}
-keepclassmembers class * extends cn.jzvd.Jzvd{
     public <init>(android.content.Context, android.util.AttributeSet);
 }
#京东CPS
-keep class com.kepler.jd.**{ public <fields>; public <methods>; public *; }

#闪验本机一键登录
-dontwarn com.cmic.sso.sdk.**
-dontwarn com.unicom.xiaowo.account.shield.**
-dontwarn com.sdk.**
-keep class com.cmic.sso.sdk.**{*;}
-keep class com.sdk.** { *;}
-keep class com.unicom.xiaowo.account.shield.** {*;}
-keep class cn.com.chinatelecom.account.api.**{*;}

#穿山甲
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

