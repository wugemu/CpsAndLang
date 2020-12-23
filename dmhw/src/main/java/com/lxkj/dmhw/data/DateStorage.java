package com.lxkj.dmhw.data;

import com.example.test.andlang.util.MMKVUtil;
import com.lxkj.dmhw.activity.MyOrderActivity;
import com.lxkj.dmhw.bean.Template;
import com.lxkj.dmhw.bean.UserInfo;
import com.tencent.mmkv.MMKV;

public class DateStorage {

    private static MMKV mmkv;
    private static String INFORMATION = "Information";
    private static String LOGIN_STATUS = "LoginStatus";
    private static String FRANCHISER_STATUS = "FranchiserStatus";
    private static String PUSH_STATUS = "PushStatus";
    private static String TEMPLATE = "Template";
    private static String CLIP = "Clip";
    private static String Tbao = "Tbao";
    private static String Push = "push";
    private static String IgnoreVersion="IgnoreVersion";
    private static String IsAuth ="IsAuth";
    //节日红包弹框提醒缓存
    private static String IsKey ="IsKey";
    //比价不再打扰
    private static String hasBj ="hasBj";

    //缓存token
    private static String  MyToken ="token";
    //小程序用户ID
    private static String LittleAppId="LAId";
    //小程序原始ID
    private static String MicroAppId="MicroId";

    //分享页面文案类型
    private static String shareTextType="shareTextType";

    //品京味分享页面文案类型
    private static String shareTextTypePJW="shareTextTypePJW";

    //保存首页引导功能标记
    private static String isShowLead="isShowLead";
    private static String isShowLead01="isShowLead01";
    /**
     * 存入用户信息
     * @param userInfo 用户信息Bean
     */
    public static void setInformation(UserInfo userInfo) {
        mmkv = MMKV.mmkvWithID(INFORMATION);
        mmkv.encode("userId", userInfo.getUserid());// 用户ID
        mmkv.encode("userName", userInfo.getUsername());// 用户昵称
        mmkv.encode("userType", userInfo.getUsertype());// 用户标识
        mmkv.encode("phone", userInfo.getUserphone());// 手机号
        mmkv.encode("avatar", userInfo.getUserpicurl());// 头像
        mmkv.encode("score", userInfo.getUserscore());// 积分
        mmkv.encode("gold", userInfo.getUsergold());// 金币
        mmkv.encode("commission", userInfo.getUsercommission());// 佣金
        mmkv.encode("extension", userInfo.getExtensionid());// 推广码
        mmkv.encode("merchant", userInfo.getMerchantid());// 商户号
        mmkv.encode("userwx", userInfo.getUserwx());// 自身绑定微信
        mmkv.encode("wxcode", userInfo.getWxcode());// 用户绑定客服
        mmkv.encode("shopkeeperalert", userInfo.getShopkeeperalert());// 店主弹窗
        mmkv.encode("rate", userInfo.getRate());// 订单显示比例
        mmkv.encode("isauth", userInfo.getIsauth());// 是否授权
        mmkv.encode("authurl", userInfo.getAuthurl());// 授权URL
    }

    /**
     * 读取用户信息
     * @return 用户信息Bean
     */
    public static UserInfo getInformation() {
        mmkv = MMKV.mmkvWithID(INFORMATION);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(mmkv.decodeString("userId", ""));// 用户ID
        userInfo.setUsername(mmkv.decodeString("userName", ""));// 用户昵称
        userInfo.setUsertype(mmkv.decodeString("userType", ""));// 用户标识
        userInfo.setUserphone(mmkv.decodeString("phone", ""));// 手机号
        userInfo.setUserpicurl(mmkv.decodeString("avatar", ""));// 头像
        userInfo.setUserscore(mmkv.decodeString("score", ""));// 积分
        userInfo.setUsergold(mmkv.decodeString("gold", ""));// 金币
        userInfo.setUsercommission(mmkv.decodeString("commission", ""));// 佣金
        userInfo.setExtensionid(mmkv.decodeString("extension", ""));// 推广码
        userInfo.setMerchantid(mmkv.decodeString("merchant", ""));// 商户号
        userInfo.setUserwx(mmkv.decodeString("userwx", ""));// 自身绑定微信
        userInfo.setWxcode(mmkv.decodeString("wxcode", ""));// 微信客服
        userInfo.setShopkeeperalert(mmkv.decodeString("shopkeeperalert", ""));// 店主弹窗
        userInfo.setRate(mmkv.decodeString("rate", "0"));// 显示比例
        userInfo.setIsauth(mmkv.decodeString("isauth", ""));// 是否授权
        userInfo.setAuthurl(mmkv.decodeString("authurl", ""));// 授权地址
        return userInfo;
    }

    /**
     * 写入登录状态
     * @param status 状态
     */
    public static void setLoginStatus(boolean status) {
        mmkv = MMKV.mmkvWithID(LOGIN_STATUS);
        mmkv.encode("loginStatus", status);
    }

    /**
     * 读取登录状态
     * @return 状态
     */
    public static boolean getLoginStatus() {
        mmkv = MMKV.mmkvWithID(LOGIN_STATUS);
        return mmkv.decodeBool("loginStatus");
    }

    /**
     * 写入开通加盟商状态
     * @param status 状态
     */
    public static void setFranchiserStatus(boolean status) {
        mmkv = MMKV.mmkvWithID(FRANCHISER_STATUS);
        mmkv.encode("franchiserStatus", status);
    }

    /**
     * 读取开通加盟商状态
     * @return 状态
     */
    public static boolean getFranchiserStatus() {
        mmkv = MMKV.mmkvWithID(FRANCHISER_STATUS);
        return mmkv.decodeBool("franchiserStatus");
    }

    /**
     * 写入推送弹出状态
     * @param status 状态
     */
    public static void setPushStatus(boolean status) {
        mmkv = MMKV.mmkvWithID(PUSH_STATUS);
        mmkv.encode("pushStatus", status);
    }

    /**
     * 读取推送弹出状态
     * @return 状态
     */
    public static boolean getPushStatus() {
        mmkv = MMKV.mmkvWithID(PUSH_STATUS);
        return mmkv.decodeBool("pushStatus");
    }

    /**
     * 模板数据
     * @param template 内容
     */
    public static void setTemplate(Template template) {
        mmkv = MMKV.mmkvWithID(TEMPLATE);
        mmkv.encode("copyWriting", template.getSharetextnew());// 文案分享模板
        mmkv.encode("command", template.getSharetpwdnew());// 淘口令分享模板
        mmkv.encode("link", template.getSharelinknew());// 短链接分享模板
        mmkv.encode("search", template.getSpecifiedsearch());// 超级搜索跳转开关
        mmkv.encode("monitor", template.getSearchjump());// 弹窗跳转方式
    }

    /**
     * 模板数据
     * @return 内容
     */
    public static Template getTemplate() {
        mmkv = MMKV.mmkvWithID(TEMPLATE);
        Template template = new Template();
        template.setSharetextnew(mmkv.decodeString("copyWriting", ""));
        template.setSharetpwdnew(mmkv.decodeString("command", ""));
        template.setSharelinknew(mmkv.decodeString("link", ""));
        template.setSpecifiedsearch(mmkv.decodeInt("search", 0));
        template.setSearchjump(mmkv.decodeInt("monitor", 0));
        return template;
    }

    /**
     * 插入剪贴板内容
     * @param content 内容
     */
    public static void setClip(String content) {
        mmkv = MMKV.mmkvWithID(CLIP);
        mmkv.encode("clip", content);
    }

    /**
     * 读取剪贴板内容
     * @return 内容
     */
    public static String getClip() {
        mmkv = MMKV.mmkvWithID(CLIP);
        return mmkv.decodeString("clip", "");
    }

    /**
     * 保存淘宝授权昵称
     * @param content 内容
     */
    public static void setnick(String content) {
        mmkv = MMKV.mmkvWithID(Tbao);
        mmkv.encode("Tbao", content);
    }
    public static String getNick() {
        mmkv = MMKV.mmkvWithID(Tbao);
        return mmkv.decodeString("Tbao", "");
    }

    /**
     * 保存推送状态
     * @param content 内容
     */
    public static void setPush(String content) {
        mmkv = MMKV.mmkvWithID(Push);
        mmkv.encode("Push", content);
    }
    public static String getPush() {
        mmkv = MMKV.mmkvWithID(Push);
        return mmkv.decodeString("Push", "");
    }


    /**
     * 保存忽略版本号
     * @param content 内容
     */
    public static void setIgnoreVersion(String content) {
        mmkv = MMKV.mmkvWithID(IgnoreVersion);
        mmkv.encode("IgnoreVersion", content);
    }
    public static String getIgnoreVersion() {
        mmkv = MMKV.mmkvWithID(IgnoreVersion);
        return mmkv.decodeString("IgnoreVersion", "");
    }

    /**
     * 保存淘宝授权状态
     * @param content 内容
     */
    public static void setIsAuth(Boolean content) {
        mmkv = MMKV.mmkvWithID(IsAuth);
        mmkv.encode("IsAuth", content);
    }
    public static Boolean getIsAuth() {
        mmkv = MMKV.mmkvWithID(IsAuth);
        return mmkv.decodeBool("IsAuth", false);
    }

    /**
     * 不再提示比价
     */
    public static void setHasBj(String hascontent ) {
        mmkv = MMKV.mmkvWithID(hasBj);
        mmkv.encode("hasBj", hascontent);
    }
    public static String getHasBj() {
        mmkv = MMKV.mmkvWithID(hasBj);
        return mmkv.decodeString("hasBj", "");
    }

    /**
     * 红包弹框提醒
     */
    public static void setIsKey(String key ) {
        mmkv = MMKV.mmkvWithID(IsKey);
        mmkv.encode("IsKey", key);
    }
    public static String getIsKey() {
        mmkv = MMKV.mmkvWithID(IsKey);
        return mmkv.decodeString("IsKey", "");
    }

    /**
     * token 缓存本地
     */
    public static void setMyToken(String token) {
        mmkv = MMKV.mmkvWithID(MyToken);
        mmkv.encode(MyToken, token);
        MMKVUtil.putString(MyToken,token);
    }
    public static String getMyToken() {
        mmkv = MMKV.mmkvWithID(MyToken);
        return mmkv.decodeString(MyToken, "");
    }
    /**
     * 小程序【用户】ID缓存
     */
    public static void setLittleAppId(String id) {
        mmkv = MMKV.mmkvWithID(LittleAppId);
        mmkv.encode(LittleAppId, id);
    }
    public static String getLittleAppId() {
        mmkv = MMKV.mmkvWithID(LittleAppId);
        return mmkv.decodeString(LittleAppId, "");
    }

    /**
     * 小程序ID缓存
     */
    public static void setMicroAppId(String microid) {
        mmkv = MMKV.mmkvWithID(MicroAppId);
        mmkv.encode(MicroAppId, microid);
    }
    public static String getMicroAppId() {
        mmkv = MMKV.mmkvWithID(MicroAppId);
        return mmkv.decodeString(MicroAppId, "");
    }

    /**
     * 分享文案类型的缓存
     */
    public static void setShareTextType(int TextType) {
        mmkv = MMKV.mmkvWithID(shareTextType);
        mmkv.encode(shareTextType, TextType);
    }
    public static int getShareTextType() {
        mmkv = MMKV.mmkvWithID(shareTextType);
        return mmkv.decodeInt(shareTextType, 2);
    }

    /**
     * 拼京唯分享文案类型的缓存
     */
    public static void setShareTextTypePJW(int TextType) {
        mmkv = MMKV.mmkvWithID(shareTextTypePJW);
        mmkv.encode(shareTextTypePJW, TextType);
    }
    public static int getShareTextTypePJW() {
        mmkv = MMKV.mmkvWithID(shareTextTypePJW);
        return mmkv.decodeInt(shareTextTypePJW, 2);
    }

    //保存个人中心引导功能标记
    public static void setIsShowLead320(String isshow) {
        mmkv = MMKV.mmkvWithID(isShowLead);
        mmkv.encode(isShowLead, isshow);
    }
    public static String getIsShowLead320() {
        mmkv = MMKV.mmkvWithID(isShowLead);
        return mmkv.decodeString(isShowLead, "");
    }
    //保存达人说功能引导
    public static void setIsShowLead01320(String isshow) {
        mmkv = MMKV.mmkvWithID(isShowLead01);
        mmkv.encode(isShowLead01, isshow);
    }
    public static String getIsShowLead01320() {
        mmkv = MMKV.mmkvWithID(isShowLead01);
        return mmkv.decodeString(isShowLead01, "");
    }
}
