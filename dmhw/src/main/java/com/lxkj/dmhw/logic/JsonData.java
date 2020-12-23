package com.lxkj.dmhw.logic;

import com.alibaba.fastjson.JSON;
import com.example.test.andlang.util.LogUtil;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.AccessToken;
import com.lxkj.dmhw.bean.AccountDel;
import com.lxkj.dmhw.bean.ActivtyChain;
import com.lxkj.dmhw.bean.Advertising;
import com.lxkj.dmhw.bean.AgentList;
import com.lxkj.dmhw.bean.AgentQuantity;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.bean.BigBrand;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.bean.Billboard;
import com.lxkj.dmhw.bean.BrandList;
import com.lxkj.dmhw.bean.CPSHomePage;
import com.lxkj.dmhw.bean.CategoryOne;
import com.lxkj.dmhw.bean.CategoryTwo;
import com.lxkj.dmhw.bean.ClassificationOne;
import com.lxkj.dmhw.bean.ClassificationTwo;
import com.lxkj.dmhw.bean.ClassifyHelp;
import com.lxkj.dmhw.bean.Collection;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.bean.CommodityDetails;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.bean.DMBDetail;
import com.lxkj.dmhw.bean.FansInfo;
import com.lxkj.dmhw.bean.FranchiserUpgrade;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.Headlines;
import com.lxkj.dmhw.bean.Help;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.bean.InspectionRoom;
import com.lxkj.dmhw.bean.Invitation;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.JDGoodsBean;
import com.lxkj.dmhw.bean.JDSort;
import com.lxkj.dmhw.bean.Limit;
import com.lxkj.dmhw.bean.LimitCatalog;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.bean.MainModule;
import com.lxkj.dmhw.bean.MarketSort;
import com.lxkj.dmhw.bean.Marketing;
import com.lxkj.dmhw.bean.Menu;
import com.lxkj.dmhw.bean.MyIncome;
import com.lxkj.dmhw.bean.MyService;
import com.lxkj.dmhw.bean.MyTaskList;
import com.lxkj.dmhw.bean.MyTeamTodayInfo;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.bean.News;
import com.lxkj.dmhw.bean.NewsDetails;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.bean.OneKeyItem;
import com.lxkj.dmhw.bean.Order;
import com.lxkj.dmhw.bean.OrderMorePl;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.PartnerUpgrade;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.bean.Popular;
import com.lxkj.dmhw.bean.Popularize;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ProfitEveryPL;
import com.lxkj.dmhw.bean.ProfitMorePl;
import com.lxkj.dmhw.bean.Promotion;
import com.lxkj.dmhw.bean.RankingType;
import com.lxkj.dmhw.bean.Recommend;
import com.lxkj.dmhw.bean.RecommendSort;
import com.lxkj.dmhw.bean.RollingMessage;
import com.lxkj.dmhw.bean.RoomList;
import com.lxkj.dmhw.bean.SaveMoneyCartInfo;
import com.lxkj.dmhw.bean.ScoreDetails;
import com.lxkj.dmhw.bean.ScoreOverview;
import com.lxkj.dmhw.bean.Screen;
import com.lxkj.dmhw.bean.SearchAll;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.bean.ShareList;
import com.lxkj.dmhw.bean.ShopInfo;
import com.lxkj.dmhw.bean.Sign;
import com.lxkj.dmhw.bean.SuperSort;
import com.lxkj.dmhw.bean.TaobaoBean;
import com.lxkj.dmhw.bean.TeamInfo;
import com.lxkj.dmhw.bean.TeamUpdataInfo;
import com.lxkj.dmhw.bean.Template;
import com.lxkj.dmhw.bean.UserAccount;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.bean.Version;
import com.lxkj.dmhw.bean.Voucher;
import com.lxkj.dmhw.bean.WeChatAbout;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.bean.WithdrawalDMB;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.bean.WithdrawalsList;
import com.lxkj.dmhw.bean.WithdrawalsListMorePl;
import com.lxkj.dmhw.bean.WithdrawalsMorePl;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.data.OtherOpen;
import com.lxkj.dmhw.defined.BaseJson;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * JSON数据处理
 * Created by Administrator on 2017/12/25 0025.
 */

public class JsonData extends BaseJson {

    private static volatile JsonData instance;

    /**
     * 创建单例类，提供静态方法调用
     *
     * @return
     */
    public static JsonData getInstance() {
        if (instance == null) {
            instance = new JsonData();
        }
        return instance;
    }

    /**
     * 调用时传入数据集合名
     *
     * @param name
     */
    Object analysis(String name, String content) {
        Object object = null;
        String info = "";
        try {
            JSONObject jsonObject = new JSONObject(content);
            switch (name) {
                case "AccessToken":// 获取access_token
                    object = JSON.parseObject(content, AccessToken.class);
                    info = "获取access_token";
                    break;
                case "WeChatUserInfo":// 获取微信用户信息
                    object = JSON.parseObject(content, WeChatUserInfo.class);
                    info = "获取微信用户信息";
                    break;
                case "SearchAll":// 全网搜索
                    object = JSON.parseArray(jsonObject.getJSONArray("classdata").toString(), SearchAll.class);
                    info = "全网搜索";
                    break;
                case "SearchAllNew":// 特殊处理全网搜索
                    object = jsonObject;
                    info = "全网搜索";
                    break;
                case "SearchAllChangeCommority":// 实体转化
                    object = SearchAllChangeCommority(jsonObject);
                    info = "全网搜索转化为站内商品";
                    break;
                case "WeChatPublic":// 判断是否绑定微信公众号
                    if (!getString(jsonObject, "classdata").equals("")) {
                        object = getString(jsonObject, "classdata");
                    } else {
                        object = "未绑定微信公众号";
                    }
                    info = "判断是否绑定微信公众号";
                    break;
                case "SetWithdrawalsWeChat":// 提现-微信
                    object = getString(jsonObject, "msgstr");
                    info = "提现-微信";
                    break;
                case "GetWeChatAbout":// 获取微信信息
                    object = JSON.parseObject(content, WeChatAbout.class);
                    info = "获取微信信息";
                    break;
                case "Extension":// 邀请码登录
                    OtherOpen open = new OtherOpen();
//                    open.updateInvitation("1");
                    UserInfo userInfo = JSON.parseObject(content, UserInfo.class);
                    DateStorage.setInformation(userInfo);
                    object = "";
                    info = "邀请码登录";
                    break;
                case "RegisterCode":// 获取验证码
                    object = getString(jsonObject, "msgstr");
                    info = "获取验证码";
                    break;
                case "Register":// 注册
                    object = getString(jsonObject, "msgstr");
                    Variable.registScore = getString(jsonObject, "registScore");
                    info = "注册";
                    break;
                case "Login":// 登录
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "登录";
                    break;
                case "LoginCode":// 验证码登录
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "验证码登录";
                    break;
                case "CategoryOne":// 商品一级类目
                    object = JSON.parseArray(jsonObject.getJSONArray("classdata").toString(), CategoryOne.class);
//                    JsonDataUtil.updataJsonDataJsonarray(MyApplication.getContext(), JsonDataKey.CATEGORYONE_CACHE_KEY,jsonObject.getJSONArray("classdata"));
                    info = "商品一级类目";
                    break;
                case "CategoryTwo":// 商品二级类目
                    object = JSON.parseArray(jsonObject.getJSONArray("classdata").toString(), CategoryTwo.class);
                    info = "商品二级类目";
                    break;
                case "Banner":// 广告
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "广告";
                    break;
                case "ShopCategory":// 商品搜索（类目）
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "商品搜索（类目）";
                    break;
                case "ShopLabel":// 商品搜索（标签）
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "商品搜索（标签）";
                    break;
                case "ShopId":// 商品搜索（ID）
                    object = JSON.parseObject(content, CommodityDetails.class);
                    info = "商品搜索（ID）";
                    break;
                case "ShopLike":// 商品搜索（猜你喜欢）
                    object = JSON.parseArray(jsonObject.getJSONArray("classdata").toString(), SearchAll.class);
                    Variable.ration = jsonObject.optString("ratio");
                    info = "商品搜索（猜你喜欢）";
                    break;
                case "ShopName":// 商品搜索（姓名）
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "商品搜索（姓名）";
                    break;
                case "SearchHot":// 热搜
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "热搜";
                    break;
                case "UserInfo":// 修改用户信息
                    object = getString(jsonObject, "msgstr");
                    info = "修改用户信息";
                    break;
                case "Recommend":// 每日推荐
                    object = Recommend(jsonObject);
                    info = "每日推荐";
                    break;
                case "Marketing":// 营销素材
                    object = JSON.parseObject(content, Marketing.class);
                    info = "营销素材";
                    break;
                case "Live":// 直播地址
                    object = getString(jsonObject, "onlineaddr");
                    info = "直播地址";
                    break;
                case "GetUserInfo":// 获取用户信息RegisterWeChat
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "获取用户信息";
                    break;
                case "MyTeam":// 我的团队
                    object = JSON.parseObject(content, TeamInfo.class);
                    info = "我的团队";
                    break;
                case "MyFans":// 我的粉丝
                    object = JSON.parseObject(content, FansInfo.class);
                    info = "我的粉丝";
                    break;
                case "UserAccount":// 个人销售记录
                    object = JSON.parseObject(content, UserAccount.class);
                    info = "个人销售记录";
                    break;
                case "LoginWeChat":// 微信登录
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "微信登录";
                    break;
                case "GetService":// 获取客服
                    object = JSON.parseObject(content, Service.class);
                    info = "获取客服";
                    break;
                case "SetService":// 设置客服
                    object = getString(jsonObject, "msgstr");
                    info = "设置客服";
                    break;
                case "ModifyPassword":// 修改密码
                    object = getString(jsonObject, "msgstr");
                    info = "修改密码";
                    break;
                case "GetShareModel":// 获取图片分享模式
                    object = !getString(jsonObject, "sharemodel").equals("0");
                    info = "获取图片分享模式";
                    break;
                case "SetShareModel":// 设置图片分享模式
                    object = getString(jsonObject, "msgstr");
                    info = "设置图片分享模式";
                    break;
                case "GetShare":// 获取分享设置
                    object = getString(jsonObject, "shopshare");
                    info = "获取分享设置";
                    break;
                case "SetShare":// 设置分享模式
                    object = getString(jsonObject, "msgstr");
                    info = "设置分享模式";
                    break;
                case "BindingWeChat":// 绑定微信
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "绑定微信";
                    break;
                case "ExistencePhone":// 判断手机号是否存在
                    object = !getString(jsonObject, "isexist").equals("0");
                    info = "判断手机号是否存在";
                    break;
                case "ResetPassword":// 重置密码
                    object = getString(jsonObject, "msgstr");
                    info = "重置密码";
                    break;
                case "Version":// 版本查询
                    object = JSON.parseObject(content, Version.class);
                    info = "版本查询";
                    break;
                case "BindingAlipay":// 绑定/解绑支付宝账户
                    object = getString(jsonObject, "msgstr");
                    info = "绑定/解绑支付宝账户";
                    break;
                case "Withdrawals":// 获取提现信息
                    object = JSON.parseObject(content, Withdrawals.class);
                    info = "获取提现信息";
                    break;
                case "WithdrawalsStatus":// 提现记录
                    object = JSON.parseArray(jsonObject.getJSONArray("withdrawalsdata").toString(), WithdrawalsList.class);
                    info = "提现记录";
                    break;
                case "SetWithdrawals":// 申请提现
                    object = getString(jsonObject, "msgstr");
                    info = "申请提现";
                    break;
                case "SetWithdrawalsMorePL":// 多平台申请提现
                    object = getString(jsonObject, "message");
                    info = "多平台申请提现";
                    break;
                case "Popularize":// 推广数据
                    object = JSON.parseObject(content, Popularize.class);
                    info = "推广数据";
                    break;
                case "GetOrder":// 推广订单
                    object = JSON.parseObject(content, Order.class);
                    info = "推广订单";
                    break;
                case "AppShare":// 获取APP分享地址
                    object = JSON.parseObject(content, Poster.class);
                    info = "获取APP分享地址";
                    break;
                case "GetUpService":// 获取上级客服
                    object = JSON.parseObject(content, Service.class);
                    info = "获取上级客服";
                    break;
                case "GetHelp":// 获取帮助列表
                    object = JSON.parseArray(jsonObject.getJSONArray("helpdata").toString(), ClassifyHelp.class);
                    info = "获取帮助列表";
                    break;
                case "GetClassifyHelp":// 获取分类帮助列表
                    object = JSON.parseArray(jsonObject.getJSONArray("problemdata").toString(), Help.class);
                    info = "获取分类帮助列表";
                    break;
                case "GetAnswers":// Q&A
                    object = getString(jsonObject, "problemdesc");
                    info = "Q&A";
                    break;
                case "Sign":// 签到
                    object = JSON.parseObject(content, Sign.class);
                    info = "签到";
                    break;
                case "SetAgent":// 申请代理
                    object = getString(jsonObject, "msgstr");
                    info = "申请代理";
                    break;
                case "GetUnread":// 获取未读消息
                    object = getString(jsonObject, "messagecnt");
                    info = "获取未读消息";
                    break;
                case "GetNews":// 获取最新消息
                    object = GetNews(jsonObject);
                    info = "获取最新消息";
                    break;
                case "GetNewsDetails":// 获取消息详情
                    object = JSON.parseObject(content, NewsDetails.class);
                    info = "获取消息详情";
                    break;
                case "ConfirmCollection":// 确认收藏
                    object = getString(jsonObject, "msgstr");
                    info = "确认收藏";
                    break;
                case "SetNewsDetails":// 消息状态更新
                    object = getString(jsonObject, "msgstr");
                    info = "消息状态更新";
                    break;
                case "GetCollection":// 获取用户收藏
                    object = GetCollection(jsonObject);
                    info = "获取用户收藏";
                    break;
                case "CancelCollection":// 取消收藏
                    object = getString(jsonObject, "msgstr");
                    info = "取消收藏";
                    break;
                case "GetFootprint":// 获取足迹
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "获取足迹";
                    break;
                case "Feedback":// 留言反馈
                    object = getString(jsonObject, "msgstr");
                    info = "留言反馈";
                    break;
                case "ScoreOverview":// 积分总览
                    object = JSON.parseObject(content, ScoreOverview.class);
                    info = "积分总览";
                    break;
                case "ScoreExchange":// 积分兑换
                    object = getString(jsonObject, "msgstr");
                    info = "积分兑换";
                    break;
                case "ScoreDetails":// 积分明细
                    object = JSON.parseObject(content, ScoreDetails.class);
                    info = "积分明细";
                    break;
                case "RegisterWeChat":// 注册（免短验）
                    object = JSON.parseObject(content, UserInfo.class);
                    Variable.registScore = getString(jsonObject, "registScore");
                    info = "注册（免短验）";
                    break;
                case "QQService":// 获取QQ客服
                    object = getString(jsonObject, "qqid");
                    info = "获取QQ客服";
                    break;
                case "GetRatio":// 获取收入比例
                    object = JSON.parseObject(content, CommodityRatio.class);
                    info = "获取收入比例";
                    break;
                case "GetCommission":// 获取高佣链接
                    object = JSON.parseObject(content, ShareList.class);
                    info = "获取高佣链接";
                    break;
                case "CancelListCollection":// 批量取消收藏
                    object = getString(jsonObject, "msgstr");
                    info = "批量取消收藏";
                    break;
                case "GetAgentQuantity":// 获取下级代理数量
                    object = JSON.parseObject(content, AgentQuantity.class);
                    info = "获取下级代理数量";
                    break;
                case "GetFranchiser":// 获取升级加盟商条件
                    object = JSON.parseObject(content, FranchiserUpgrade.class);
                    info = "获取升级加盟商条件";
                    break;
                case "SetFranchiser":// 升级为加盟商
                    object = getString(jsonObject, "msgstr");
                    info = "升级为加盟商";
                    break;
                case "GetFranchiserQuantity":// 获取下级加盟商数量
                    object = getString(jsonObject, "franchisercnt");
                    info = "获取下级加盟商数量";
                    break;
                case "GetPartner":// 获取升级合伙人条件
                    object = JSON.parseObject(content, PartnerUpgrade.class);
                    info = "获取升级合伙人条件";
                    break;
                case "SetPartner":// 升级为合伙人
                    object = getString(jsonObject, "msgstr");
                    info = "升级为合伙人";
                    break;
                case "GetPartnerStatus":// 获取合伙人申请状态查询
                    object = GetPartnerStatus(jsonObject);
                    info = "获取合伙人申请状态查询";
                    break;
                case "AgentOne":// A级代理人数
                    object = JSON.parseObject(content, AgentList.class);
                    info = "A级代理人数";
                    break;
                case "AgentTwo":// B级代理人数
                    object = JSON.parseObject(content, AgentList.class);
                    info = "B级代理人数";
                    break;
                case "Billboard":// 疯狂榜单
                    object = JSON.parseObject(content, Billboard.class);
                    info = "疯狂榜单";
                    break;
                case "MainModule":// 首页模块
                    object = JSON.parseArray(jsonObject.getJSONArray("homepagedata").toString(), MainModule.class);
                    info = "首页模块";
//                    JsonDataUtil.updataJsonDataJsonarray(MyApplication.getContext(), JsonDataKey.MAINMODULE_CACHE_KEY,jsonObject.getJSONArray("homepagedata"));
                    break;
                case "LimitCatalog":// 限时抢购时间段
                    object = JSON.parseArray(jsonObject.getJSONArray("flashsaletime").toString(), LimitCatalog.class);
                    info = "限时抢购时间段";
                    break;
                case "LimitList":// 抢购商品列表
                    object = JSON.parseObject(content, Limit.class);
                    info = "抢购商品列表";
                    break;
                case "LimitCommodity":// 抢购商品详情
                    object = JSON.parseObject(content, CommodityDetails.class);
                    info = "抢购商品详情";
                    break;
                case "Purchase":// 领券购买
                    object = JSON.parseObject(content, Alibc.class);
                    info = "领券购买";
                    break;
                case "CommodityPush":// 推送商品
                    if (getString(jsonObject, "result").equals("1")) {
                        object = JSON.parseObject(getArray(jsonObject, "classdata").getJSONObject(0).toString(), SearchAll.class);
                    } else {
                        object = getString(jsonObject, "message");
                    }
                    info = "推送商品";
                    break;
                case "ShopInfo":// 获取店铺信息
                    object = JSON.parseObject(content, ShopInfo.class);
                    info = "获取店铺信息";
                    break;
                case "ClassificationOne":// 超级分类一级类目
                    object = JSON.parseArray(getArray(jsonObject, "classdata").toString(), ClassificationOne.class);
                    info = "超级分类一级类目";
                    break;
                case "ClassificationTwo":// 超级分类二级类目
                    object = JSON.parseArray(getArray(jsonObject, "classdata").toString(), ClassificationTwo.class);
                    info = "超级分类二级类目";
                    break;
                case "SetAlipay":// 修改支付宝
                    object = getString(jsonObject, "msgstr");
                    info = "修改支付宝";
                    break;
                case "GetAlipay":// 判断是否绑定过支付宝
                    object = getString(jsonObject, "isbind");
                    info = "判断是否绑定过支付宝";
                    break;
                case "Association":// 搜索联想
                    object = Association(jsonObject);
                    info = "搜索联想";
                    break;
                case "RecommendShop":// 多商品分享数据
                    object = RecommendShop(jsonObject);
                    info = "多商品分享数据";
                    break;
                case "GetMenu":// 获取导航栏
                    object = JSON.parseArray(jsonObject.getJSONArray("appmenulist").toString(), Menu.class);
                    info = "获取导航栏";
//                    JsonDataUtil.updataJsonDataJsonarray(MyApplication.getContext(), JsonDataKey.MAIN_MENU_CACHE_KEY,jsonObject.getJSONArray("appmenulist"));
                    break;
                case "GetScreen":// 获取闪屏广告
                    object = JSON.parseObject(content, Screen.class);
                    info = "获取闪屏广告";
                    break;
                case "GetInvitation":// 查询推广码
                    object = JSON.parseObject(content, Invitation.class);
                    info = "查询推广码";
                    break;
                case "GetShop":// 获取爆款推荐列表
                    object = JSON.parseObject(content, Popular.class);
                    info = "获取爆款推荐列表";
                    break;
                case "Headlines":// 获取呆萌头条
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), Headlines.class);
                    info = "获取呆萌头条";
                    break;
                case "OneFragmentBanner":// 首页广告
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "首页广告";
                    break;
                case "Voucher":// 大家都在领
                    object = JSON.parseObject(content, Voucher.class);
                    info = "大家都在领";
                    break;
                case "Save":// 节省金额
                    object = getString(jsonObject, "savemny");
                    info = "节省金额";
                    break;
                case "Advertising":// 首页广告
                    object = JSON.parseObject(content, Advertising.class);
                    info = "首页广告";
                    break;
                case "AdvertisingFragmentMain":// 首页右侧小广告
                    object = JSON.parseObject(content, Advertising.class);
                    info = "首页右侧小广告";
                    break;
                case "SetCid":// 绑定cid
                    object = getString(jsonObject, "msgstr");
                    info = "绑定cid";
                    break;
                case "GetWeChat":// 是否绑定微信
//                    object = getString(jsonObject, "isbind");
                    object = jsonObject;
                    info = "是否绑定微信";
                    break;
                case "UserInfoBindingWeChat":// 用户信息绑定/解绑微信
                    object = getString(jsonObject, "msgstr");
                    info = "用户信息绑定/解绑微信";
                    break;
                case "VerificationOldPhone":// 验证旧手机号
                    object = getString(jsonObject, "msgstr");
                    info = "验证旧手机号";
                    break;
                case "ModificationPhone":// 修改手机号
                    object = getString(jsonObject, "msgstr");
                    info = "修改手机号";
                    break;
                case "SearchDiscount":// 推送商品
                    if (getString(jsonObject, "result").equals("1")) {
                        if (getString(jsonObject, "msgstr").equals("没有更多数据！")) {
                            object = getString(jsonObject, "失败");
                        } else {
                            object = JSON.parseObject(getArray(jsonObject, "classdata").getJSONObject(0).toString(), SearchAll.class);
                        }
                    } else {
                        object = getString(jsonObject, "失败");
                    }
                    info = "推送商品";
                    break;
                case "RollingMessage":// 滚动消息
                    object = JSON.parseObject(content, RollingMessage.class);
                    info = "滚动消息";
                    break;
                case "CommodityRatio":// 商品详情佣金比例
                    object = JSON.parseObject(content, CommodityRatio.class);
                    info = "滚动消息";
                    break;
                case "Promotion":// 获取推广图标
                    object = JSON.parseArray(jsonObject.getJSONArray("promotionlist").toString(), Promotion.class);
                    info = "获取推广图标";
                    break;
                case "MyService":// 获取我的服务
                    object = JSON.parseArray(jsonObject.getJSONArray("servicelist").toString(), MyService.class);
                    info = "获取我的服务";
                    break;
                case "GetBrand":// 获取品牌列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), BrandList.class);
                    info = "获取品牌列表";
                    break;
                case "Template":// 分享模板
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), Template.class);
                    info = "分享模板";
                    break;
                case "GetH5":// 获取H5地址
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), H5Link.class);
                    info = "获取H5地址";
//                    JsonDataUtil.updataJsonDataJsonarray(MyApplication.getContext(), JsonDataKey.MAIN_RECOMMEND_CACHE_KEY,jsonObject.getJSONArray("data"));
                    break;
                case "GetDown":// 中间页app下载开关
                    object = getString(jsonObject, "domainapp");
                    info = "中间页app下载开关";
                    break;
                case "SetDown":// 设置中间页下载开关
                    object = getString(jsonObject, "msgstr");
                    info = "设置中间页下载开关";
                    break;
                case "ParentUserInfo":// 获取上级用户推荐人的信息
                    object = JSON.parseObject(content, TeamUpdataInfo.class);
                    info = "获取上级用户推荐人的信息";
                    break;
                case "MyTeamToday":// 获取新增数据
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MyTeamTodayInfo.class);
                    info = "获取新增数据";
                    break;
                case "MyTeamTotal":// 获取团队总数据
                    object = jsonObject;
                    info = "获取团队总数据";
                    break;
                case "MyTeamHistoryDetail":// 获取团队明细数据
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MyTeamTotalDetailnfo.class);
                    info = "获取团队数据明细";
                    break;
                case "MyTeamTodayDetail":// 获取团队今日明细数据
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MyTeamTotalDetailnfo.class);
                    info = "获取团队今日数据明细";
                    break;
                case "SaveMoneyCart":// 购物车查券
                    object = GetSaveCart(jsonObject);
                    info = "获取购物车优惠券内容";
                    break;
                case "RoomList"://验货直播列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), RoomList.class);
                    info = "获取验货直播列表";
                    break;
                case "RoomDetailData"://验货直播详情
                    object = getRoomDetailData(jsonObject);
                    info = "验货直播详情";
                    break;
                case "RoomLike"://验货直播点赞
                    object = 1;
                    info = "验货直播点赞";
                    break;
                case "TmallMarket":// 首页天猫超市数据
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "首页天猫超市数据";
                    break;
                case "TmallChannel":// 首页天猫国际数据
                    object = JSON.parseObject(content, CommodityList.class);
                    info = "首页天猫国际数据";
                    break;
                case "CreditCardToken":// 获取信用卡token
                    object = jsonObject.getJSONObject("data");
                    info = "获取信用卡token";
                    break;
                case "ActivityChain":// 获取官方活动链接
                    object = JSON.parseObject(jsonObject.toString());
                    info = "获取官方活动链接";
                    break;
                case "GetAppTopic":// 获取多商品图片和标题
                    object = jsonObject.getJSONObject("data");
                    info = "获取多商品图片和标题";
                    break;
                case "ConvertShopid"://链接转商品ID
                    object = jsonObject;
                    info = "链接转商品ID";
                    break;
                case "MainMiddleHotTopicOne":
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "获取首页中间活动热区广告1数据";
                    break;
                case "MainMiddleHotTopicTwo":
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "获取首页中间活动热区广告2数据";
                    break;
                case "MainMiddleHotTopicThree":
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "获取首页中间活动热区广告3数据";
                    break;
                case "MainMiddleHotTopicBg":
                    object = JSON.parseArray(jsonObject.getJSONArray("advertisementdata").toString(), Banner.class);
                    info = "获取首页中间活动热区广告背景图片";
                    break;
                case "MerchantWeb":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), H5Link.class);
                    info = "获取商品详情的web地址";
                    break;
                case "IsCollection":
                    object = jsonObject;
                    info = "获取全网商品是否收藏";
                    break;
                case "TotalAppIcon":
                    object = JSON.parseArray(jsonObject.getJSONArray("servicelist").toString(), PersonalAppIcon.class);
                    info = "获取个人中心的功能图标";
                    break;
                case "InitTaskList":
                    object = InitTaskList(jsonObject);
                    info = "获取任务列表数据";
                    break;
                case "DailySign":
                    object = jsonObject;
                    info = "获取每日签到";
                    break;
                case "ExchangeScore":
                    object = jsonObject;
                    info = "NN币兑换";
                    break;
                case "ScorereCord":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), DMBDetail.class);
                    info = "NN币明细";
                    break;
                case "GetFrozenMny":
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), WithdrawalDMB.class);
                    info = "获取冻结资金";
                    break;
                case "Withdrawal":
                    object = jsonObject;
                    info = "提现";
                    break;
                case "DayBuy":
                    object = jsonObject;
                    info = "每日首购";
                    break;
                case "FirstBuy":
                    object = jsonObject;
                    info = "自身首购";
                    break;
                case "ChildFristBuy":
                    object = jsonObject;
                    info = "好友首购";
                    break;
                case "UploadAndroidBug":
                    object = jsonObject;
                    info = "上传异常";
                    break;
                case "SearchTask":
                    object = jsonObject;
                    info = "搜索标题任务";
                    break;
                case "AdvertisementClick":
                    object = jsonObject;
                    info = "统计";
                    break;
                case "BusinessCooperation":
                    object = jsonObject;
                    info = "商务合作";
                    break;
                case "MerchantReqList":
                    object = JSON.parseArray(jsonObject.getJSONArray("messagedata").toString(), NewsExam.class);
                    info = "获取审批数据";
                    break;
                case "MerchantReqApprove":
                    object = jsonObject;
                    info = "运营审核";
                    break;
                case "ExtensionMerchantList":
                    object = jsonObject;
                    info = "运营审核已开通";
                    break;
                case "LoginToken":
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), LoginToken.class);
                    info = "获取token";
                    break;
                case "JDSortList":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), JDSort.class);
                    info = "获取京东分类列表";
                    break;
                case "JDBanner":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), JDBanner.class);
                    info = "获取京东轮播图";
                    break;
                case "JDGoods":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), JDGoodsBean.class);
                    info = "获取京东商品列表";
                    break;
                case "ProfitSum":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MyIncome.class);
                    info = "获取我的收益列表";
                    break;
                case "JDSearchGoods":
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), JDGoodsBean.class);
                    info = "获取关键字搜索返回的商品列表";
                    break;
                case "WithdrawalsMorePl":
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), WithdrawalsMorePl.class);
                    info = "获取平台提现";
                    break;
                case "WithdrawalsInfoList":// 平台提现记录明细
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), WithdrawalsListMorePl.class);
                    info = "提现平台记录";
                    break;
                case "MorePlGetOrder"://多平台订单列表
                    object = JSON.parseObject(content, OrderMorePl.class);
                    info = "多平台订单列表";
                    break;
                case "MorePlTeamGetOrder"://多平台团队订单列表
                    object = JSON.parseObject(content, OrderMorePl.class);
                    info = "多平台团队订单列表";
                    break;
                case "PlatformProfitDetail"://多平台收益详情信息总览
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), ProfitMorePl.class);
                    info = "多平台收益信息";
                    break;
                case "ProfitCpsInfo"://各平台的收益信息
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), ProfitEveryPL.class);
                    info = "各平台的收益信息";
                    break;
                case "LittleAppImage"://获取小程序码图
                    object = getString(jsonObject, "data");
                    info = "获取小程序码图";
                    break;
                case "ThemeGoods"://获取主题和频道商品
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), JDGoodsBean.class);
                    info = "获取主题和频道商品";
                    break;
                case "HomePageInit"://新首页接口
                    object = HomePageInit(jsonObject.getJSONObject("data"));
                    info = "新首页接口";
                    break;
                case "MainBottomList"://首页底部列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MainBottomListItem.class);
                    info = "首页底部列表";
                    break;
                case "ShopLikeList"://猜你喜欢
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MainBottomListItem.class);
                    info = "猜你喜欢";
                    break;
                case "CommodityDetail":// 商品详情290
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), CommodityDetails290.class);
                    info = "商品详情290";
                    break;
                case "ShopLike290"://商品详情猜你喜欢
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MainBottomListItem.class);
                    info = "商品详情猜你喜欢";
                    break;
                case "GoodsPromotion"://购买分享
                    object = JSON.parseObject(content, Alibc.class);
                    info = "商品详情购买分享";
                    break;
                case "BigBrandCategorys"://大牌精选分类
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), BigBrand.class);
                    info = "大牌精选分类";
                    break;
                case "BrandInfoList"://品牌列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), BigBrand.class);
                    info = "品牌列表";
                    break;
                case "BrandAndGoodsList"://品牌商品列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), BigBrandList.class);
                    info = "品牌商品列表";
                    break;
                case "SingleBrand"://品牌详情
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), BigBrandList.class);
                    info = "品牌详情";
                    break;
                case "MarketingType"://营销素材分类
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), MarketSort.class);
                    info = "营销素材分类";
                    break;
                case "RecommmendType"://爆款分类
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), RecommendSort.class);
                    info = "爆款分类";
                    break;
                case "ComCollegeInit"://商学院首页接口
                    object = ComCollegeInit(jsonObject);
                    info = "商学院首页接口";
                    break;
                case "ArticleContentDataList"://商学院列表接口
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), ComCollegeData.NewHand.class);
                    info = "商学院列表接口";
                    break;
                case "ArticleContentDetail"://商学院文章详情包括视频
                    object = jsonObject;
                    info = "商学院文章详情";
                    break;
                case "ShareArticleContent"://商学院文章分享包括视频
                    object = jsonObject;
                    info = "商学院文章详情分享";
                    break;
                case "LikeArticleContent"://商学院文章赞包括视频
                    object = jsonObject;
                    info = "商学院文章赞";
                    break;
                case "UnlikeArticleContent"://商学院文章取消赞包括视频
                    object = jsonObject;
                    info = "商学院文章取消赞";
                    break;
                case "ComCollContentDetail"://请求类型
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), ComCollArticle.class);
                    info = "商学院文章详情";
                    break;
                case "ArticleContentRecord"://记录
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), ComCollArticle.class);
                    info = "记录";
                    break;
                case "PJWByGoodsId":// 京东商品详情
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), CommodityDetails290.class);
                    info = "京东商品详情";
                    break;
                case "GenByGoodsId":// 京东商品转链
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), PJWLink.class);
                    info = "京东商品转链";
                    break;
                case "CpsGoodsCollect":// CPS商品收藏
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), CommodityDetails290.class);
                    info = "CPS商品收藏";
                    break;
                case "DelCpsGoodsCollect":// CPS批量取消商品收藏
                    object = getString(jsonObject, "msgstr");
                    info = "CPS批量取消商品收藏";
                    break;
                case "GetGenByUrl":// 拼京唯url转链
                    object = JSON.parseObject(jsonObject.getJSONObject("data").toString(), PJWLink.class);
                    info = "拼京唯url转链";
                    break;
                case "SendCircle":// 一键发圈（爆款）
                    object = getString(jsonObject, "msgstr");
                    info = "一键发圈";
                    break;
                case "SendCircleMarketing":// 一键发圈(营销素材)
                    object = getString(jsonObject, "msgstr");
                    info = "一键发圈";
                    break;
                case "SendCircleMarketingAll":// 一键发圈(营销素材)
                    object = getString(jsonObject, "msgstr");
                    info = "一键发圈";
                    break;
                case "HomeImgClick"://转链
                    object = getString(jsonObject, "url");
                    info = "转链";
                    break;
                case "ConVertTextToGoods"://一键转链
                    object = OnekeyChainObj(jsonObject);
                    info = "一键转链";
                    break;
                case "RankingType":// 商品一级类目
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), RankingType.class);
                    info = "商品一级类目";
                    break;
                case "RankingList":// 热卖商品列表
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), CommodityDetails290.class);
                    info = "热卖商品列表";
                    break;
                case "SYChangePhoneNum":// 闪验号码置换
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "闪验号码置换";
                    break;
                case "WxBindPhonebySy":// 闪验绑定手机号码
                    object = JSON.parseObject(content, UserInfo.class);
                    info = "闪验绑定手机号码";
                    break;
                case "JDInitMain":// 京东专区
                    object = PddandJDdata(JSON.parseObject(jsonObject.getJSONObject("data").toString()));
                    info = "京东拼多多专区";
                    break;
                case "UserCancelInit":// 注销账号数据初始化
                    object = AccountDelInit(JSON.parseObject(jsonObject.getJSONObject("data").toString()));
                    info = "注销账号数据初始化";
                    break;
                case "UserCancel":// 账号注销
                    object = getString(jsonObject, "msgstr");
                    info = "账号注销";
                    break;
                case "PDDIsBindAuth":// pdd授权状态
                    object = jsonObject.toString();
                    info = "pdd授权状态";
                    break;
                case "CpsGoodsFootprint":// CPS足迹
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), CommodityDetails290.class);
                    info = "CPS足迹";
                    break;
                case "ClassificationTotal":// 超级分类
                    object = SuperSortData(jsonObject);
                    info = "超级分类";
                    break;
                case "DtkSearchGoodsList":// 超级分类跳转
                    object = JSON.parseArray(jsonObject.getJSONArray("data").toString(), CommodityDetails290.class);
                    info = "超级分类跳转";
                    break;
                case "taoBaoInit"://淘宝接口
                    object = TaobaoInit(jsonObject);
                    info = "新首页接口";
                    break;
            }
        } catch (JSONException e) {
            Logger.e(e, "Json错误数据");
        }
        if (BuildConfig.DEBUG) {
            LogUtil.d(info);
            Logger.json(content);
        }
        return object;
    }

    /**
     * 商学院接口3.0.0
     *
     * @param jsonObject
     * @return
     */
    private Object ComCollegeInit(JSONObject jsonObject) {
        try {
            ComCollegeData comCollegeData = new ComCollegeData();
            //获取轮播图
            ArrayList<ComCollegeData.Banner> BannerList = new ArrayList<>();
            if (jsonObject.has("adv")) {
                JSONArray bannerArray = jsonObject.getJSONArray("adv");
                if (bannerArray != null && bannerArray.length() > 0) {
                    for (int i = 0; i < bannerArray.length(); i++) {
                        ComCollegeData.Banner banner = new ComCollegeData.Banner();
                        JSONObject bannerObject = bannerArray.getJSONObject(i);
                        banner.setAdvertisemenid(bannerObject.optString("advertisemenid"));
                        banner.setAdvertisementlink(bannerObject.optString("advertisementlink"));
                        banner.setAdvertisementpic(bannerObject.optString("advertisementpic"));
                        banner.setJumptype(bannerObject.optString("jumptype"));
                        banner.setNeedlogin(bannerObject.optString("needlogin"));
                        banner.setTopicid(bannerObject.optString("topicid"));
                        BannerList.add(banner);
                    }
                    comCollegeData.setBannerList(BannerList);
                }
            }

            //获取轮播图下面的数据
            ArrayList<ComCollegeData.PartOne> PartOneList = new ArrayList<>();
            if (jsonObject.has("type")) {
                JSONArray partOneArray = jsonObject.getJSONArray("type");
                if (partOneArray != null && partOneArray.length() > 0) {
                    for (int i = 0; i < partOneArray.length(); i++) {
                        ComCollegeData.PartOne partOne = new ComCollegeData.PartOne();
                        JSONObject partOneObject = partOneArray.getJSONObject(i);
                        partOne.setId(partOneObject.optString("id"));
                        partOne.setImgurl(partOneObject.optString("imgurl"));
                        partOne.setRemark(partOneObject.optString("remark"));
                        partOne.setSort(partOneObject.optString("sort"));
                        partOne.setTitle(partOneObject.optString("title"));
                        PartOneList.add(partOne);
                    }
                    comCollegeData.setPartOneList(PartOneList);
                }
            }

            if (jsonObject.has("list")) {
                JSONObject listObject = jsonObject.getJSONObject("list");
                //获取常见问题的数据
                ArrayList<ComCollegeData.Question> QuesList = new ArrayList<>();
                if (listObject.has("0")) {
                    JSONArray QuesArray = listObject.getJSONArray("0");
                    if (QuesArray != null && QuesArray.length() > 0) {
                        for (int i = 0; i < QuesArray.length(); i++) {
                            ComCollegeData.Question question = new ComCollegeData.Question();
                            JSONObject partOneObject = QuesArray.getJSONObject(i);
                            question.setId(partOneObject.optString("id"));
                            question.setImgurl(partOneObject.optString("imgurl"));
                            question.setRemark(partOneObject.optString("remark"));
                            question.setClicknum(partOneObject.optString("clicknum"));
                            question.setTitle(partOneObject.optString("title"));
                            QuesList.add(question);
                        }
                        comCollegeData.setQuestionList(QuesList);
                    }

                }

                //获取新手视频的数据
                ArrayList<ComCollegeData.NewHand> newHandList = new ArrayList<>();
                if (listObject.has("1")) {
                    JSONArray newHandArray = listObject.getJSONArray("1");
                    if (newHandArray != null && newHandArray.length() > 0) {
                        for (int i = 0; i < newHandArray.length(); i++) {
                            ComCollegeData.NewHand newHand = new ComCollegeData.NewHand();
                            JSONObject partOneObject = newHandArray.getJSONObject(i);
                            newHand.setId(partOneObject.optString("id"));
                            newHand.setImgurl(partOneObject.optString("imgurl"));
                            newHand.setRemark(partOneObject.optString("remark"));
                            newHand.setClicknum(partOneObject.optString("clicknum"));
                            newHand.setTitle(partOneObject.optString("title"));
                            newHand.setType(partOneObject.optString("type"));
                            newHand.setVideourl(partOneObject.optString("videourl"));
                            newHandList.add(newHand);
                        }
                        comCollegeData.setNewHandList(newHandList);
                    }
                }

                //获取热门教程的数据
                ArrayList<ComCollegeData.NewHand> hotList = new ArrayList<>();
                if (listObject.has("2")) {
                    JSONArray newHandArray = listObject.getJSONArray("2");
                    if (newHandArray != null && newHandArray.length() > 0) {
                        for (int i = 0; i < newHandArray.length(); i++) {
                            ComCollegeData.NewHand newHand = new ComCollegeData.NewHand();
                            JSONObject partOneObject = newHandArray.getJSONObject(i);
                            newHand.setId(partOneObject.optString("id"));
                            newHand.setImgurl(partOneObject.optString("imgurl"));
                            newHand.setRemark(partOneObject.optString("remark"));
                            newHand.setClicknum(partOneObject.optString("clicknum"));
                            newHand.setTitle(partOneObject.optString("title"));
                            newHand.setType(partOneObject.optString("type"));
                            newHand.setVideourl(partOneObject.optString("videourl"));
                            newHand.setTypetitle(partOneObject.optString("typetitle"));
                            hotList.add(newHand);
                        }
                        comCollegeData.setHotList(hotList);
                    }
                }

            }


            return comCollegeData;
        } catch (JSONException e) {
            return null;
        }

    }


    /**
     * 新首页接口2.9.0
     *
     * @param jsonObject
     * @return
     */
    private Object HomePageInit(JSONObject jsonObject) {
        try {
            HomePage homePage = new HomePage();
            //获取超级分类数据
//            ArrayList<HomePage.CategoryOne> categoryOneList = new ArrayList<>();
//            if (jsonObject.has("classOne")) {
//                JSONArray array = getArray(jsonObject, "classOne");
//                if (array != null && array.length() > 0) {
//                    for (int i = 0; i < array.length(); i++) {
//                        HomePage.CategoryOne categoryOne = new HomePage.CategoryOne();
//                        JSONObject categoryOneObject = array.getJSONObject(i);
//                        categoryOne.setShopclassname(categoryOneObject.optString("shopclassname"));
//                        categoryOne.setShopclassone(categoryOneObject.optString("shopclassone"));
//                        categoryOne.setUrl(categoryOneObject.optString("url"));
//                        categoryOneList.add(categoryOne);
//                    }
//                    homePage.setCategoryOneList(categoryOneList);
//                }
//            }

            //获取轮播图数据
            if (jsonObject.has("adv")) {
                JSONObject ADVObject = jsonObject.getJSONObject("adv");
                ArrayList<HomePage.Banner> BannerList = new ArrayList<>();
                if (ADVObject.has("11")) {
                    JSONArray bannerArray = ADVObject.getJSONArray("11");
                    if (bannerArray != null && bannerArray.length() > 0) {
                        for (int i = 0; i < bannerArray.length(); i++) {
                            HomePage.Banner banner = new HomePage.Banner();
                            JSONObject bannerObject = bannerArray.getJSONObject(i);
                            banner.setAdvertisemenid(bannerObject.optString("advertisemenid"));
                            banner.setAdvertisementlink(bannerObject.optString("advertisementlink"));
                            banner.setAdvertisementpic(bannerObject.optString("advertisementpic"));
                            banner.setBgcolor(bannerObject.optString("bgcolor"));
                            banner.setJumptype(bannerObject.optString("jumptype"));
                            banner.setNeedlogin(bannerObject.optString("needlogin"));
                            banner.setTopicid(bannerObject.optString("topicid"));
                            BannerList.add(banner);
                        }
                        homePage.setBannerList(BannerList);
                    }
                }

                //轮播图底部的广告
                ArrayList<HomePage.Banner> advList = new ArrayList<>();
                if (ADVObject.has("57")) {
                    JSONArray advListArray = ADVObject.getJSONArray("57");
                    if (advListArray != null && advListArray.length() > 0) {
                        HomePage.Banner advListBanner = new HomePage.Banner();
                        JSONObject advListObject = advListArray.getJSONObject(0);
                        advListBanner.setAdvertisemenid(advListObject.optString("advertisemenid"));
                        advListBanner.setAdvertisementlink(advListObject.optString("advertisementlink"));
                        advListBanner.setAdvertisementpic(advListObject.optString("advertisementpic"));
                        advListBanner.setBgcolor(advListObject.optString("bgcolor"));
                        advListBanner.setJumptype(advListObject.optString("jumptype"));
                        advListBanner.setNeedlogin(advListObject.optString("needlogin"));
                        advListBanner.setTopicid(advListObject.optString("topicid"));
                        advList.add(advListBanner);
                        homePage.setAdvList(advList);
                    }
                }

                //搜索背景
                ArrayList<HomePage.Banner> searchBgList = new ArrayList<>();
                if (ADVObject.has("55")) {
                    JSONArray advListArray = ADVObject.getJSONArray("55");
                    if (advListArray != null && advListArray.length() > 0) {
                        HomePage.Banner advListBanner = new HomePage.Banner();
                        JSONObject advListObject = advListArray.getJSONObject(0);
                        advListBanner.setAdvertisemenid(advListObject.optString("advertisemenid"));
                        advListBanner.setAdvertisementlink(advListObject.optString("advertisementlink"));
                        advListBanner.setAdvertisementpic(advListObject.optString("advertisementpic"));
                        advListBanner.setBgcolor(advListObject.optString("bgcolor"));
                        advListBanner.setJumptype(advListObject.optString("jumptype"));
                        advListBanner.setNeedlogin(advListObject.optString("needlogin"));
                        advListBanner.setTopicid(advListObject.optString("topicid"));
                        searchBgList.add(advListBanner);
                        homePage.setSearchBgList(searchBgList);
                    }
                }

                //轮播广告背景
                ArrayList<HomePage.Banner> bannerBgList = new ArrayList<>();
                if (ADVObject.has("56")) {
                    JSONArray advListArray = ADVObject.getJSONArray("56");
                    if (advListArray != null && advListArray.length() > 0) {
                        HomePage.Banner advListBanner = new HomePage.Banner();
                        JSONObject advListObject = advListArray.getJSONObject(0);
                        advListBanner.setAdvertisemenid(advListObject.optString("advertisemenid"));
                        advListBanner.setAdvertisementlink(advListObject.optString("advertisementlink"));
                        advListBanner.setAdvertisementpic(advListObject.optString("advertisementpic"));
                        advListBanner.setBgcolor(advListObject.optString("bgcolor"));
                        advListBanner.setJumptype(advListObject.optString("jumptype"));
                        advListBanner.setNeedlogin(advListObject.optString("needlogin"));
                        advListBanner.setTopicid(advListObject.optString("topicid"));
                        bannerBgList.add(advListBanner);
                        homePage.setBannerBgList(bannerBgList);
                    }
                }

                //金刚区背景
                ArrayList<HomePage.Banner> JGQBgList = new ArrayList<>();
                if (ADVObject.has("58")) {
                    JSONArray advListArray = ADVObject.getJSONArray("58");
                    if (advListArray != null && advListArray.length() > 0) {
                        HomePage.Banner advListBanner = new HomePage.Banner();
                        JSONObject advListObject = advListArray.getJSONObject(0);
                        advListBanner.setAdvertisemenid(advListObject.optString("advertisemenid"));
                        advListBanner.setAdvertisementlink(advListObject.optString("advertisementlink"));
                        advListBanner.setAdvertisementpic(advListObject.optString("advertisementpic"));
                        advListBanner.setBgcolor(advListObject.optString("bgcolor"));
                        advListBanner.setJumptype(advListObject.optString("jumptype"));
                        advListBanner.setNeedlogin(advListObject.optString("needlogin"));
                        advListBanner.setTopicid(advListObject.optString("topicid"));
                        JGQBgList.add(advListBanner);
                        homePage.setJGQBgList(JGQBgList);
                    }
                }


                //先请求是否有活动背景 来判断要不要显示活动
                ArrayList<HomePage.Banner> HotBgList = new ArrayList<>();
                if (ADVObject.has("54")) {
                    JSONArray hotBgArray = ADVObject.getJSONArray("54");
                    if (hotBgArray != null && hotBgArray.length() > 0) {
                        for (int i = 0; i < hotBgArray.length(); i++) {
                            HomePage.Banner hotBg = new HomePage.Banner();
                            JSONObject hotBgObject = hotBgArray.getJSONObject(i);
                            hotBg.setAdvertisemenid(hotBgObject.optString("advertisemenid"));
                            hotBg.setAdvertisementlink(hotBgObject.optString("advertisementlink"));
                            hotBg.setAdvertisementpic(hotBgObject.optString("advertisementpic"));
                            hotBg.setBgcolor(hotBgObject.optString("bgcolor"));
                            hotBg.setJumptype(hotBgObject.optString("jumptype"));
                            hotBg.setNeedlogin(hotBgObject.optString("needlogin"));
                            hotBg.setTopicid(hotBgObject.optString("topicid"));
                            HotBgList.add(hotBg);
                        }
                        homePage.setHotBgList(HotBgList);

                        //有活动请求热区广告第一部分
                        ArrayList<HomePage.Banner> HotOneList = new ArrayList<>();
                        if (ADVObject.has("51")) {
                            JSONArray hotOneArray = ADVObject.getJSONArray("51");
                            if (hotOneArray != null && hotOneArray.length() > 0) {
                                for (int i = 0; i < hotOneArray.length(); i++) {
                                    HomePage.Banner hotOne = new HomePage.Banner();
                                    JSONObject hotOneObject = hotOneArray.getJSONObject(i);
                                    hotOne.setAdvertisemenid(hotOneObject.optString("advertisemenid"));
                                    hotOne.setAdvertisementlink(hotOneObject.optString("advertisementlink"));
                                    hotOne.setAdvertisementpic(hotOneObject.optString("advertisementpic"));
                                    hotOne.setBgcolor(hotOneObject.optString("bgcolor"));
                                    hotOne.setJumptype(hotOneObject.optString("jumptype"));
                                    hotOne.setNeedlogin(hotOneObject.optString("needlogin"));
                                    hotOne.setTopicid(hotOneObject.optString("topicid"));
                                    HotOneList.add(hotOne);
                                }
                                homePage.setHotPartOneList(HotOneList);
                            }
                        }
                        //有活动请求热区广告第二部分
                        ArrayList<HomePage.Banner> HotTwoList = new ArrayList<>();
                        if (ADVObject.has("52")) {
                            JSONArray hotTwoArray = ADVObject.getJSONArray("52");
                            if (hotTwoArray != null && hotTwoArray.length() > 0) {
                                for (int i = 0; i < hotTwoArray.length(); i++) {
                                    HomePage.Banner hotTwo = new HomePage.Banner();
                                    JSONObject hotTwoObject = hotTwoArray.getJSONObject(i);
                                    hotTwo.setAdvertisemenid(hotTwoObject.optString("advertisemenid"));
                                    hotTwo.setAdvertisementlink(hotTwoObject.optString("advertisementlink"));
                                    hotTwo.setAdvertisementpic(hotTwoObject.optString("advertisementpic"));
                                    hotTwo.setBgcolor(hotTwoObject.optString("bgcolor"));
                                    hotTwo.setJumptype(hotTwoObject.optString("jumptype"));
                                    hotTwo.setNeedlogin(hotTwoObject.optString("needlogin"));
                                    hotTwo.setTopicid(hotTwoObject.optString("topicid"));
                                    HotTwoList.add(hotTwo);
                                }
                                homePage.setHotPartTwoList(HotTwoList);
                            }
                        }
                        //有活动请求热区广告第三部分
                        ArrayList<HomePage.Banner> HotThreeList = new ArrayList<>();
                        if (ADVObject.has("53")) {
                            JSONArray hotThreeArray = ADVObject.getJSONArray("53");
                            if (hotThreeArray != null && hotThreeArray.length() > 0) {
                                for (int i = 0; i < hotThreeArray.length(); i++) {
                                    HomePage.Banner hotThree = new HomePage.Banner();
                                    JSONObject hotThreeObject = hotThreeArray.getJSONObject(i);
                                    hotThree.setAdvertisemenid(hotThreeObject.optString("advertisemenid"));
                                    hotThree.setAdvertisementlink(hotThreeObject.optString("advertisementlink"));
                                    hotThree.setAdvertisementpic(hotThreeObject.optString("advertisementpic"));
                                    hotThree.setBgcolor(hotThreeObject.optString("bgcolor"));
                                    hotThree.setJumptype(hotThreeObject.optString("jumptype"));
                                    hotThree.setNeedlogin(hotThreeObject.optString("needlogin"));
                                    hotThree.setTopicid(hotThreeObject.optString("topicid"));
                                    HotThreeList.add(hotThree);
                                }
                                homePage.setHotPartTthreeList(HotThreeList);
                            }
                        }

                    } else {
                        //没有活动请求零元购活动数据
                        ArrayList<HomePage.Banner> HotBannerList = new ArrayList<>();
                        if (ADVObject.has("41")) {
                            JSONArray hotBannerArray = ADVObject.getJSONArray("41");
                            if (hotBannerArray != null && hotBannerArray.length() > 0) {
                                for (int i = 0; i < hotBannerArray.length(); i++) {
                                    HomePage.Banner hotBanner = new HomePage.Banner();
                                    JSONObject hotBannerObject = hotBannerArray.getJSONObject(i);
                                    hotBanner.setAdvertisemenid(hotBannerObject.optString("advertisemenid"));
                                    hotBanner.setAdvertisementlink(hotBannerObject.optString("advertisementlink"));
                                    hotBanner.setAdvertisementpic(hotBannerObject.optString("advertisementpic"));
                                    hotBanner.setBgcolor(hotBannerObject.optString("bgcolor"));
                                    hotBanner.setJumptype(hotBannerObject.optString("jumptype"));
                                    hotBanner.setNeedlogin(hotBannerObject.optString("needlogin"));
                                    hotBanner.setTopicid(hotBannerObject.optString("topicid"));
                                    HotBannerList.add(hotBanner);
                                }
                                homePage.setMiddleBannerList(HotBannerList);
                            }
                        }
                    }

                }

            }

            //新金刚区分类数据
            if (jsonObject.has("vajra")) {
                ArrayList<HomePage.JGQSort> JGQSortList = new ArrayList<>();
                JSONArray jgqSortArray = jsonObject.getJSONArray("vajra");
                if (jgqSortArray != null && jgqSortArray.length() > 0) {
                    for (int i = 0; i < jgqSortArray.length(); i++) {
                        HomePage.JGQSort jgqSort = new HomePage.JGQSort();
                        JSONObject jgqJSONObject = jgqSortArray.getJSONObject(i);
                        jgqSort.setName(jgqJSONObject.optString("name"));
                        jgqSort.setKey(jgqJSONObject.optString("key"));
                        if (jsonObject.has("img")) {
                            JSONObject JGQNewObject = jsonObject.getJSONObject("img");
                            if (JGQNewObject.has(jgqJSONObject.optString("key"))) {
                                jgqSort.setKeyjson(JGQNewObject.optString(jgqJSONObject.optString("key")));
                            }
                        }
                        JGQSortList.add(jgqSort);
                    }
                    homePage.setJGQSortList(JGQSortList);
                }
            }

            //金刚区数据一
            ArrayList<HomePage.JGQAppIcon> JGQListOne = new ArrayList<>();
            if (jsonObject.has("img")) {
                JSONObject JGQObject = jsonObject.getJSONObject("img");
                if (JGQObject.has("0")) {
                    JSONArray jgqArray = JGQObject.getJSONArray("0");
                    if (jgqArray != null && jgqArray.length() > 0) {
                        for (int i = 0; i < jgqArray.length(); i++) {
                            HomePage.JGQAppIcon JGQOne = new HomePage.JGQAppIcon();
                            JSONObject jgqJSONObject = jgqArray.getJSONObject(i);
                            JGQOne.setImageurl(jgqJSONObject.optString("imageurl"));
                            JGQOne.setName(jgqJSONObject.optString("name"));
                            JGQOne.setLabeltype(jgqJSONObject.optString("labeltype"));
                            JGQOne.setUrl(jgqJSONObject.optString("url"));
                            JGQOne.setNeedlogin(jgqJSONObject.optString("needlogin"));
                            JGQListOne.add(JGQOne);
                        }
                        homePage.setJGQListOne(JGQListOne);
                    }
                }

                //金刚区数据二
                ArrayList<HomePage.JGQAppIcon> JGQListTwo = new ArrayList<>();
                if (JGQObject.has("1")) {
                    JSONArray jgqArrayTwo = JGQObject.getJSONArray("1");
                    if (jgqArrayTwo != null && jgqArrayTwo.length() > 0) {
                        for (int i = 0; i < jgqArrayTwo.length(); i++) {
                            HomePage.JGQAppIcon JGQTwo = new HomePage.JGQAppIcon();
                            JSONObject jgqJSONObjectTwo = jgqArrayTwo.getJSONObject(i);
                            JGQTwo.setImageurl(jgqJSONObjectTwo.optString("imageurl"));
                            JGQTwo.setName(jgqJSONObjectTwo.optString("name"));
                            JGQTwo.setLabeltype(jgqJSONObjectTwo.optString("labeltype"));
                            JGQTwo.setUrl(jgqJSONObjectTwo.optString("url"));
                            JGQTwo.setNeedlogin(jgqJSONObjectTwo.optString("needlogin"));
                            JGQListTwo.add(JGQTwo);
                        }
                        homePage.setJGQListTwo(JGQListTwo);
                    }
                }


                //活动精选一
                ArrayList<HomePage.JGQAppIcon> TopicListOne = new ArrayList<>();
                if (JGQObject.has("2")) {
                    JSONArray TopicArrayOne = JGQObject.getJSONArray("2");
                    if (TopicArrayOne != null && TopicArrayOne.length() > 0) {
                        for (int i = 0; i < TopicArrayOne.length(); i++) {
                            HomePage.JGQAppIcon TopicOne = new HomePage.JGQAppIcon();
                            JSONObject TopicOneJSONObject = TopicArrayOne.getJSONObject(i);
                            TopicOne.setImageurl(TopicOneJSONObject.optString("imageurl"));
                            TopicOne.setName(TopicOneJSONObject.optString("name"));
                            TopicOne.setLabeltype(TopicOneJSONObject.optString("labeltype"));
                            TopicOne.setUrl(TopicOneJSONObject.optString("url"));
                            TopicOne.setNeedlogin(TopicOneJSONObject.optString("needlogin"));
                            TopicListOne.add(TopicOne);
                        }
                        homePage.setTopicListOne(TopicListOne);
                    }
                }
                //活动精选二
                ArrayList<HomePage.JGQAppIcon> TopicListTwo = new ArrayList<>();
                if (JGQObject.has("3")) {
                    JSONArray TopicArrayTwo = JGQObject.getJSONArray("3");
                    if (TopicArrayTwo != null && TopicArrayTwo.length() > 0) {
                        for (int i = 0; i < TopicArrayTwo.length(); i++) {
                            HomePage.JGQAppIcon Topictwo = new HomePage.JGQAppIcon();
                            JSONObject TopictTwoJSONObject = TopicArrayTwo.getJSONObject(i);
                            Topictwo.setImageurl(TopictTwoJSONObject.optString("imageurl"));
                            Topictwo.setName(TopictTwoJSONObject.optString("name"));
                            Topictwo.setLabeltype(TopictTwoJSONObject.optString("labeltype"));
                            Topictwo.setUrl(TopictTwoJSONObject.optString("url"));
                            Topictwo.setNeedlogin(TopictTwoJSONObject.optString("needlogin"));
                            TopicListTwo.add(Topictwo);
                        }
                        homePage.setTopicListTwo(TopicListTwo);
                    }
                }

                //人气宝贝一
                ArrayList<HomePage.JGQAppIcon> TopicListThree = new ArrayList<>();
                if (JGQObject.has("4")) {
                    JSONArray TopicArrayThree = JGQObject.getJSONArray("4");
                    if (TopicArrayThree != null && TopicArrayThree.length() > 0) {
                        for (int i = 0; i < TopicArrayThree.length(); i++) {
                            HomePage.JGQAppIcon Topicthree = new HomePage.JGQAppIcon();
                            JSONObject TopictThreeJSONObject = TopicArrayThree.getJSONObject(i);
                            Topicthree.setImageurl(TopictThreeJSONObject.optString("imageurl"));
                            Topicthree.setName(TopictThreeJSONObject.optString("name"));
                            Topicthree.setLabeltype(TopictThreeJSONObject.optString("labeltype"));
                            Topicthree.setUrl(TopictThreeJSONObject.optString("url"));
                            Topicthree.setNeedlogin(TopictThreeJSONObject.optString("needlogin"));
                            TopicListThree.add(Topicthree);
                        }
                        homePage.setTopicListThree(TopicListThree);
                    }
                }

                //人气宝贝二
                ArrayList<HomePage.JGQAppIcon> TopicListFour = new ArrayList<>();
                if (JGQObject.has("5")) {
                    JSONArray TopicArrayFour = JGQObject.getJSONArray("5");
                    if (TopicArrayFour != null && TopicArrayFour.length() > 0) {
                        for (int i = 0; i < TopicArrayFour.length(); i++) {
                            HomePage.JGQAppIcon TopicFour = new HomePage.JGQAppIcon();
                            JSONObject TopictFourJSONObject = TopicArrayFour.getJSONObject(i);
                            TopicFour.setImageurl(TopictFourJSONObject.optString("imageurl"));
                            TopicFour.setName(TopictFourJSONObject.optString("name"));
                            TopicFour.setLabeltype(TopictFourJSONObject.optString("labeltype"));
                            TopicFour.setUrl(TopictFourJSONObject.optString("url"));
                            TopicFour.setNeedlogin(TopictFourJSONObject.optString("needlogin"));
                            TopicListFour.add(TopicFour);
                        }
                        homePage.setTopicListFour(TopicListFour);
                    }
                }

            }

            //限时抢购
            ArrayList<HomePage.LimitTimeList> limitTimeParentGoods = new ArrayList<>();
            if (jsonObject.has("flashsaleList")) {
                JSONArray limitParentArray = jsonObject.getJSONArray("flashsaleList");
                if (limitParentArray != null && limitParentArray.length() > 0) {
                    for (int i = 0; i < limitParentArray.length(); i++) {
                        HomePage.LimitTimeList limitTimeList = new HomePage.LimitTimeList();
                        JSONObject limitParentObject = limitParentArray.getJSONObject(i);
                        limitTimeList.setTime(limitParentObject.optString("time"));
                        limitTimeList.setTitle(limitParentObject.optString("title"));
                        limitTimeList.setRemark(limitParentObject.optString("remark"));
                        if (limitParentObject.has("shopList")) {
                            ArrayList<HomePage.LimitTimeList.LimitTimeGoods> limitTimeChildGoods = new ArrayList<>();
                            JSONArray limitChildArray = limitParentObject.getJSONArray("shopList");
                            for (int j = 0; j < limitChildArray.length(); j++) {
                                HomePage.LimitTimeList.LimitTimeGoods limitTimeGoods = new HomePage.LimitTimeList.LimitTimeGoods();
                                JSONObject limitChildObject = limitChildArray.getJSONObject(j);
                                limitTimeGoods.setShopmainpic(limitChildObject.optString("shopmainpic"));
                                limitTimeGoods.setShopname(limitChildObject.optString("shopname"));
                                limitTimeGoods.setShopid(limitChildObject.optString("shopid"));
                                limitTimeGoods.setDisprice(limitChildObject.optString("disprice"));
                                limitTimeChildGoods.add(limitTimeGoods);
                            }
                            limitTimeList.setLimitTimeChildList(limitTimeChildGoods);
                        }
                        limitTimeParentGoods.add(limitTimeList);
                    }
                    homePage.setLimitTimeParentList(limitTimeParentGoods);
                }
            }

            //获取底部导航栏数据
            ArrayList<HomePage.BottomNavigation> navList = new ArrayList<>();
            if (jsonObject.has("tabList")) {
                JSONArray navArray = getArray(jsonObject, "tabList");
                if (navArray != null && navArray.length() > 0) {
                    for (int i = 0; i < navArray.length(); i++) {
                        HomePage.BottomNavigation navigation = new HomePage.BottomNavigation();
                        JSONObject navObject = navArray.getJSONObject(i);
                        navigation.setMaterial(navObject.optString("material"));
                        navigation.setDesc(navObject.optString("desc"));
                        navigation.setType(navObject.optString("type"));
                        navigation.setTitle(navObject.optString("title"));
                        navList.add(navigation);
                    }
                    homePage.setNavList(navList);
                }
            }

            //获取热销榜单
            ArrayList<HomePage.HotSell> HotSellList = new ArrayList<>();
            if (jsonObject.has("hot")) {
                JSONArray hotSellArray = getArray(jsonObject, "hot");
                if (hotSellArray != null && hotSellArray.length() > 0) {
                    for (int i = 0; i < hotSellArray.length(); i++) {
                        HomePage.HotSell hotSell = new HomePage.HotSell();
                        JSONObject hotSellObject = hotSellArray.getJSONObject(i);
                        hotSell.setCoupondenomination(hotSellObject.optString("coupondenomination"));
                        hotSell.setPrice(hotSellObject.optString("price"));
                        hotSell.setShopmainpic(hotSellObject.optString("shopmainpic"));
                        HotSellList.add(hotSell);
                    }
                    homePage.setHotSellList(HotSellList);
                }
            }

            //获取验货直播
            ArrayList<RoomList> roomList = new ArrayList<>();
            if (jsonObject.has("room")) {
                roomList = (ArrayList<RoomList>) JSON.parseArray(jsonObject.getJSONArray("room").toString(), RoomList.class);
            }
            homePage.setRoomList(roomList);

            if (jsonObject.has("heardUrl")) {
                homePage.setHeadUrl(jsonObject.getString("heardUrl"));
            }
            if (jsonObject.has("userLevel")) {
                homePage.setUserLevel(jsonObject.getString("userLevel"));
            }
            return homePage;
        } catch (JSONException e) {
            Logger.e(e, "首页接口");
            return null;
        }
    }


    /**
     * 每日推荐
     *
     * @param jsonObject
     * @return
     */
    private Object Recommend(JSONObject jsonObject) {
        try {
            Recommend recommend = new Recommend();
            ArrayList<Recommend.RecommendList> list = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "commmentdata");
            if (array.length() > 0) {
                recommend.setSearchtime(getString(jsonObject, "searchtime"));
                for (int i = 0; i < array.length(); i++) {
                    Recommend.RecommendList recommendList = new Recommend.RecommendList();
                    JSONObject object = array.getJSONObject(i);
                    recommendList.setId(getString(object, "recommentid"));
                    recommendList.setShopId(getString(object, "shopid"));
                    recommendList.setAvatar(getString(object, "imgpic"));
                    recommendList.setName(getString(object, "title"));
                    recommendList.setContentall(getString(object, "recommentcontent"));
                    recommendList.setContent(getString(object, "content"));
                    recommendList.setReason(getString(object, "reasons"));
                    recommendList.setTime(getString(object, "sendtime"));
                    recommendList.setDiscount(getString(object, "couponpromotionlink"));
                    recommendList.setTopicId(getString(object, "topicid"));
                    recommendList.setTopicName(getString(object, "topicname"));
                    recommendList.setPosition(getString(object, "sharenum"));
                    recommendList.setShopinfoid(getString(object, "shopinfoid"));
                    recommendList.setCouponid(getString(object, "couponid"));
                    recommendList.setShopname(getString(object, "shopname"));
                    recommendList.setImgpic1(getString(object, "imgpic1"));
                    recommendList.setCoupondenomination(getString(object, "coupondenomination"));
                    recommendList.setTitles(getString(object, "titles"));
                    recommendList.setPostcouponprice(getString(object, "postcouponprice"));
                    recommendList.setShopprice(getString(object, "shopprice"));
                    recommendList.setPrecommission(getString(object, "precommission"));

                    ShareInfo info = new ShareInfo();
                    info.setName(getString(object, "titles"));
                    info.setSales(getString(object, "shopmonthlysales"));
                    info.setMoney(getString(object, "postcouponprice"));
                    info.setShopprice(getString(object, "shopprice"));
                    info.setRecommended(getString(object, "recommended"));
                    info.setDiscount(getString(object, "coupondenomination"));
                    info.setCommission(getString(object, "commission"));
                    info.setShortLink(getString(object, "shareshortlink"));
                    info.setCheck(getString(object, "platformtype").equals("天猫"));
                    recommendList.setInfo(info);
                    recommendList.setType(getString(object, "type"));
                    ArrayList<Recommend.RecommendList.RecommendData> shops = new ArrayList<>();
                    JSONArray jsonArray;
                    if (Objects.equals(recommendList.getType(), "0")) {
                        jsonArray = getArray(object, "imglist");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            Recommend.RecommendList.RecommendData shop = new Recommend.RecommendList.RecommendData();
                            JSONObject json = jsonArray.getJSONObject(j);
                            shop.setId("");
                            shop.setMoney("");
                            shop.setStatus("");
                            shop.setImage(getString(json, "imgurl"));
                            shops.add(shop);
                        }
                    } else {
                        jsonArray = getArray(object, "shoplist");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            Recommend.RecommendList.RecommendData shop = new Recommend.RecommendList.RecommendData();
                            JSONObject json = jsonArray.getJSONObject(j);
                            shop.setId(getString(json, "id"));
                            shop.setShopid(getString(json, "shopid"));
                            shop.setMoney(getString(json, "disprice"));
                            shop.setStatus(getString(json, "states"));
                            shop.setImage(getString(json, "shopmainpic"));
                            shops.add(shop);
                        }
                    }
                    recommendList.setShopList(shops);
                    list.add(recommendList);
                }
            }
            recommend.setRecommendList(list);
            return recommend;
        } catch (JSONException e) {
            Logger.e(e, "每日推荐");
            return "";
        }
    }

    /**
     * 验货直播详情
     *
     * @param jsonData
     * @return
     */
    private Object getRoomDetailData(JSONObject jsonData) {
        try {
            JSONObject jsonObject = jsonData.getJSONObject("data");
            JSONObject shopjsonObject = jsonData.getJSONObject("shop");
            InspectionRoom inspectionRoom = new InspectionRoom();
            //聊天详情数据解析
            ArrayList<InspectionRoom.Detail> detailList = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "detail");
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    InspectionRoom.Detail detail = new InspectionRoom.Detail();
                    JSONObject object = array.getJSONObject(i);
                    detail.setContent(getString(object, "content"));
                    detail.setType(getString(object, "type"));
                    detail.setImgurl(getString(object, "imgurl"));
                    detail.setVideourl(getString(object, "videourl"));
                    detail.setImgwidth(getString(object, "imgwidth"));
                    detail.setImgheight(getString(object, "imgheight"));
                    detailList.add(detail);
                }
            }
            inspectionRoom.setDetailList(detailList);
            //免单数据解析
            ArrayList<InspectionRoom.Free> freeList = new ArrayList<>();
            JSONArray freeArray = getArray(jsonObject, "free");
            if (freeArray.length() > 0) {
                for (int i = 0; i < freeArray.length(); i++) {
                    InspectionRoom.Free free = new InspectionRoom.Free();
                    JSONObject object = freeArray.getJSONObject(i);
                    free.setUsername(getString(object, "username"));
                    free.setUserphone(getString(object, "userphone"));
                    free.setUserpicurl(getString(object, "userpicurl"));
                    freeList.add(free);
                }
            }
            inspectionRoom.setFreeList(freeList);

            ArrayList<InspectionRoom.userInfo> userList = new ArrayList<>();
            JSONArray userArray = getArray(jsonObject, "user");
            if (userArray.length() > 0) {
                for (int i = 0; i < userArray.length(); i++) {
                    InspectionRoom.userInfo user = new InspectionRoom.userInfo();
                    user.setName(userArray.get(i).toString());
                    userList.add(user);
                }
            }
            inspectionRoom.setUserList(userList);

            inspectionRoom.setShopname(jsonObject.optString("shopname"));
            inspectionRoom.setIslike(jsonObject.optInt("islike") + "");
            inspectionRoom.setNotice(jsonObject.optString("notice"));
            inspectionRoom.setShopid(jsonObject.optString("shopid"));
            inspectionRoom.setShopinfoid(jsonObject.optString("shopinfoid"));
            inspectionRoom.setShopmainpic(jsonObject.optString("shopmainpic"));
            inspectionRoom.setShopprice(jsonObject.optString("shopprice"));
            inspectionRoom.setPrecommission(jsonObject.optString("precommission"));
            inspectionRoom.setCoupondenomination(jsonObject.optString("coupondenomination"));
            //分享图片集合
            ArrayList<InspectionRoom.imageUrl> imageUrls = new ArrayList<>();
            JSONArray shareImagesarray = getArray(shopjsonObject, "shoppiclist");
            if (shareImagesarray.length() > 0) {
                for (int i = 0; i < shareImagesarray.length(); i++) {
                    InspectionRoom.imageUrl url = new InspectionRoom.imageUrl();
                    JSONObject object = shareImagesarray.getJSONObject(i);
                    url.setShoppicurl(object.optString("shoppicurl"));
                    imageUrls.add(url);
                }
            }
            inspectionRoom.setShoppiclist(imageUrls);
            inspectionRoom.setCouponid(shopjsonObject.optString("couponid"));

            inspectionRoom.setRecommended(shopjsonObject.optString("recommended"));

            return inspectionRoom;
        } catch (JSONException e) {
            Logger.e(e, "验货直播详情");
            return null;
        }
    }

    /**
     * 获取最新消息
     *
     * @param jsonObject
     * @return
     */
    private Object GetNews(JSONObject jsonObject) {
        try {
            ArrayList<News> list = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "messagedata");
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    News news = new News();
                    JSONObject object = array.getJSONObject(i);
                    news.setType(getString(object, "messagetype"));
                    news.setContent(getString(object, "messagecontent"));
                    news.setTime(getString(object, "createtime"));
                    list.add(news);
                }
            } else {
                News news = new News();
                news.setType("0");
                news.setContent("暂无消息");
                news.setTime("");
                list.add(news);
                news = new News();
                news.setType("1");
                news.setContent("暂无消息");
                news.setTime("");
                list.add(news);
                news = new News();
                news.setType("2");
                news.setContent("暂无消息");
                news.setTime("");
                list.add(news);
            }
            return list;
        } catch (JSONException e) {
            Logger.e(e, "获取最新消息");
            return "";
        }
    }

    /**
     * 获取用户收藏
     *
     * @param jsonObject
     * @return
     */
    private Object GetCollection(JSONObject jsonObject) {
        try {
            Collection collection = new Collection();
            ArrayList<Collection.CollectionList> list = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "shopdata");
            if (array.length() > 0) {
                collection.setSearchtime(getString(jsonObject, "searchtime"));
                for (int i = 0; i < array.length(); i++) {
                    Collection.CollectionList collectionList = new Collection.CollectionList();
                    JSONObject object = array.getJSONObject(i);
                    collectionList.setId(getString(object, "id"));
                    collectionList.setCollectionId(getString(object, "collectid"));
                    collectionList.setCheck(getString(object, "platformtype").equals("天猫"));
                    collectionList.setShopId(getString(object, "shopid"));
                    collectionList.setTitle(getString(object, "shopname"));
                    collectionList.setShopprice(getString(object, "shopprice"));
                    collectionList.setImage(getString(object, "shopmainpic"));
                    collectionList.setDiscount(getInt(object, "coupondenomination") + "");
                    collectionList.setSales(getString(object, "shopmonthlysales"));
                    float money = Utils.getFloat(Float.parseFloat(getString(object, "shopprice")) - getInt(object, "coupondenomination"));
                    collectionList.setMoney(money + "");
                    collectionList.setShortLink(getString(object, "shareshortlink"));
                    collectionList.setRecommend(getString(object, "recommended"));
                    collectionList.setEstimate(getString(object, "precommission"));
                    collectionList.setShopName(getString(object, "sellername"));
                    collectionList.setEffect(getString(object, "iseffective").equals("1"));
                    list.add(collectionList);
                }
                collection.setShopdata(list);
            }
            return collection;
        } catch (JSONException e) {
            Logger.e(e, "获取用户收藏");
            return "";
        }
    }

    /**
     * 获取合伙人申请状态查询
     *
     * @param jsonObject
     * @return
     */
    private Object GetPartnerStatus(JSONObject jsonObject) {
        try {
            JSONArray array = getArray(jsonObject, "partnerdata");
            if (array.length() > 0) {
                return getString(array.getJSONObject(0), "reqstatus");
            } else {
                return "";
            }
        } catch (Exception e) {
            Logger.e(e, "合伙人申请状态查询");
            return "";
        }
    }

    /**
     * 搜索联想
     *
     * @param jsonObject
     * @return
     */
    private Object Association(JSONObject jsonObject) {
        try {
            ArrayList<String> list = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "classdata");
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            return list;
        } catch (Exception e) {
            Logger.e(e, "搜索联想");
            return "";
        }
    }

    /**
     * 多商品分享数据
     *
     * @param jsonObject
     * @return
     */
    private Object RecommendShop(JSONObject jsonObject) {
        try {
            HashMap<String, ShareInfo> map = new HashMap<>();
            JSONArray array = getArray(jsonObject, "data");
            for (int i = 0; i < array.length(); i++) {
                ShareInfo info = new ShareInfo();
                JSONObject object = array.getJSONObject(i);
                info.setName(getString(object, "shopname"));
                info.setSales(getString(object, "shopmonthlysales"));
                float money = Utils.getFloat(Float.parseFloat(getString(object, "shopprice")) - getInt(object, "coupondenomination"));
                info.setMoney(money + "");
                info.setDiscount(getInt(object, "coupondenomination") + "");
                info.setShortLink(getString(object, "shareshortlink"));
                info.setCheck(getString(object, "platformtype").equals("天猫"));
                map.put(getString(object, "id"), info);
            }
            return map;
        } catch (Exception e) {
            Logger.e(e, "");
            return "";
        }
    }

    /**
     * 获取购物车优惠券
     *
     * @param jsonObject
     * @return
     */
    private Object GetSaveCart(JSONObject jsonObject) {
        try {
            SaveMoneyCartInfo saveMoneyCartInfo = new SaveMoneyCartInfo();
            ArrayList<SaveMoneyCartInfo.saveMoneyCartInfodata> list = new ArrayList<>();
            JSONArray array = getArray(jsonObject, "data");
            saveMoneyCartInfo.setCnt(jsonObject.optString("cnt"));
            saveMoneyCartInfo.setSave(jsonObject.optString("save"));
            saveMoneyCartInfo.setComm(jsonObject.optString("comm"));
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    SaveMoneyCartInfo.saveMoneyCartInfodata saveMcid = new SaveMoneyCartInfo.saveMoneyCartInfodata();
                    JSONObject object = array.getJSONObject(i);
                    saveMcid.setPid(getString(object, "pid"));
                    saveMcid.setCoupondenomination(getString(object, "coupondenomination"));
                    saveMcid.setPlatformtype(getString(object, "platformtype"));
                    saveMcid.setShopid(getString(object, "shopid"));
                    saveMcid.setCouponpromotionlink(getString(object, "couponpromotionlink"));
                    saveMcid.setPostcouponprice(getString(object, "postcouponprice"));
                    saveMcid.setPrecommission(getString(object, "precommission"));
                    saveMcid.setSellername(getString(object, "sellername"));
                    saveMcid.setShopmainpic(getString(object, "shopmainpic"));
                    saveMcid.setShopmonthlysales(getString(object, "shopmonthlysales"));
                    saveMcid.setShopname(getString(object, "shopname"));
                    saveMcid.setShopprice(getString(object, "shopprice"));
                    list.add(saveMcid);
                }
                saveMoneyCartInfo.setSaveMoneyCartdata(list);
            }
            return saveMoneyCartInfo;
        } catch (Exception e) {
            Logger.e(e, "");
            return "";
        }

    }

    /**
     * 获取任务类表数据
     *
     * @param jsonData
     * @return
     */
    private Object InitTaskList(JSONObject jsonData) {
        try {
            JSONObject jsonObject = jsonData.getJSONObject("data");
            MyTaskList myTaskList = new MyTaskList();
            myTaskList.setScoremny(jsonObject.optString("scoremny"));
            myTaskList.setUserscore(jsonObject.optString("userscore"));
            myTaskList.setExchange(jsonObject.optString("exchange"));
            myTaskList.setUsertype(jsonObject.optString("usertype"));
            myTaskList.setDailycnt(jsonObject.optString("dailycnt"));
            myTaskList.setFreshcnt(jsonObject.optString("freshcnt"));
            //新手任务解析
            ArrayList<MyTaskList.dailyItem> newHandList = new ArrayList<>();
            JSONArray newHandArray = getArray(jsonObject, "fresh");
            if (newHandArray.length() > 0) {
                for (int i = 0; i < newHandArray.length(); i++) {
                    MyTaskList.dailyItem newItem = new MyTaskList.dailyItem();
                    JSONObject object = newHandArray.getJSONObject(i);
                    newItem.setScore(object.optString("score"));
                    newItem.setCnt(object.optString("cnt"));
                    newItem.setMny(object.optString("mny"));
                    newItem.setModel(object.optString("model"));
                    newItem.setRemark(object.optString("remark"));
                    newItem.setTitle(object.optString("title"));
                    newItem.setValue(object.optString("value"));
                    newItem.setUrl(object.optString("url"));
                    newItem.setSsid(object.optString("ssid"));
                    newItem.setType(object.optString("type"));
                    newItem.setUsertype(myTaskList.getUsertype());
                    newItem.setMax(object.optString("max"));
                    newHandList.add(newItem);
                }
            }
            myTaskList.setFreshtotalcnt("/" + newHandList.size());
            myTaskList.setNewHandList(newHandList);

            //日常任务解析
            ArrayList<MyTaskList.dailyItem> dailyList = new ArrayList<>();
            JSONArray dailyArray = getArray(jsonObject, "daily");
            if (dailyArray.length() > 0) {
                for (int i = 0; i < dailyArray.length(); i++) {
                    MyTaskList.dailyItem dailyItem = new MyTaskList.dailyItem();
                    JSONObject object = dailyArray.getJSONObject(i);
                    dailyItem.setCnt(object.optString("cnt"));
                    dailyItem.setMny(object.optString("mny"));
                    dailyItem.setModel(object.optString("model"));
                    dailyItem.setTitle(object.optString("title"));
                    dailyItem.setRemark(object.optString("remark"));
                    dailyItem.setValue(object.optString("value"));
                    dailyItem.setType(object.optString("type"));
                    dailyItem.setSsid(object.optString("ssid"));
                    dailyItem.setUrl(object.optString("url"));
                    dailyItem.setUsertype(myTaskList.getUsertype());
                    dailyItem.setMax(object.optString("max"));
                    dailyList.add(dailyItem);
                }
            }
            myTaskList.setDailytotalcnt("/" + dailyList.size());
            myTaskList.setDailyList(dailyList);
            return myTaskList;
        } catch (JSONException e) {
            Logger.e(e, "任务");
            return null;
        }
    }


    //全网搜的实体类转化站内商品
    private Object SearchAllChangeCommority(JSONObject jsonObject) {

        Object jsonArray = null;
        try {
            jsonArray = JSON.parseArray(jsonObject.getJSONArray("classdata").toString(), SearchAll.class);
            ArrayList<SearchAll> list = (ArrayList<SearchAll>) jsonArray;
            if (list.size() > 0) {
                ArrayList<CommodityList.CommodityData> commodityDataArrayList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    CommodityList.CommodityData commodityData = new CommodityList.CommodityData();
                    commodityData.setIsNetwork(1);
                    commodityData.setShopid(list.get(i).getItemId());
                    commodityData.setShopmainpic(list.get(i).getPictUrl());
                    commodityData.setShopprice(list.get(i).getZkFinalPrice());
                    commodityData.setPrecommission(list.get(i).getEstimate());
                    commodityData.setSellername(list.get(i).getShopTitle());
                    commodityData.setShopname(list.get(i).getShopName());
                    commodityData.setShopmonthlysales(list.get(i).getVolume() + "");
                    commodityData.setCoupondenomination(Float.parseFloat(list.get(i).getDiscount()));
                    commodityData.setPlatformtype(list.get(i).getUserType());
                    commodityData.isCheck();
                    commodityDataArrayList.add(commodityData);
                }
                return commodityDataArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //一键转链
    private Object OnekeyChainObj(JSONObject jsonObject) {
        try {
            OneKeyItem oneKeyItem = new OneKeyItem();
            JSONObject dataObject;
            dataObject = jsonObject.getJSONObject("data");
            oneKeyItem.setText(dataObject.getString("text"));
            JSONArray jsonArray = dataObject.getJSONArray("goodsList");
            ArrayList<CommodityDetails290> goodsList = new ArrayList<>();
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject goodsListItem = jsonArray.getJSONObject(i);
                    CommodityDetails290 commodityObj = JSON.parseObject(goodsListItem.getJSONObject("goods").toString(), CommodityDetails290.class);
                    commodityObj.setType(goodsListItem.getString("type"));
                    goodsList.add(commodityObj);
                }
                oneKeyItem.setGoodsList(goodsList);
            }
            return oneKeyItem;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    //京东 拼多多 专区数据
    private Object PddandJDdata(com.alibaba.fastjson.JSONObject jsonObject) {
        try {
            CPSHomePage cpsHomePage = new CPSHomePage();
            com.alibaba.fastjson.JSONArray advListArray = JSON.parseArray(jsonObject.getJSONArray("advList").toString());
            ArrayList<CPSHomePage.CPSdata> list = new ArrayList<>();
            if (advListArray != null && advListArray.size() > 0) {
                for (int i = 0; i < advListArray.size(); i++) {
                    com.alibaba.fastjson.JSONObject advListObjOne = advListArray.getJSONObject(i);
                    CPSHomePage.CPSdata cpSdata = new CPSHomePage.CPSdata();
                    cpSdata.setStyle(advListObjOne.getString("style"));
                    ArrayList<JDBanner> bannersList = new ArrayList<>();
                    bannersList = (ArrayList<JDBanner>) JSON.parseArray(advListObjOne.getJSONArray("advList").toString(), JDBanner.class);
                    cpSdata.setBannerList(bannersList);
                    list.add(cpSdata);
                }
                cpsHomePage.setBannerParentList(list);
            }

            //分类数据
            ArrayList<JDSort> sortList = new ArrayList<>();
            sortList = (ArrayList<JDSort>) JSON.parseArray(jsonObject.getJSONArray("catList").toString(), JDSort.class);
            cpsHomePage.setCategoryOneList(sortList);

            return cpsHomePage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //账号注销数据初始化
    private Object AccountDelInit(com.alibaba.fastjson.JSONObject jsonObject) {
        try {
            AccountDel accountDel = new AccountDel();
            //账户列表
            ArrayList<AccountDel.Accontdata> loseList = new ArrayList<>();
            loseList = (ArrayList<AccountDel.Accontdata>) JSON.parseArray(jsonObject.getJSONArray("loseList").toString(), AccountDel.Accontdata.class);
            accountDel.setLoseList(loseList);
            //注销原因
            ArrayList<AccountDel.reasonData> reasonList = new ArrayList<>();
            com.alibaba.fastjson.JSONArray reasonListArray = JSON.parseArray(jsonObject.getJSONArray("reason").toString());
            for (int i = 0; i < reasonListArray.size(); i++) {
                AccountDel.reasonData data = new AccountDel.reasonData();
                data.setName(reasonListArray.get(i).toString());
                reasonList.add(data);
            }
            accountDel.setReasonList(reasonList);
            //其他数据
            accountDel.setUserphone(jsonObject.getString("userphone"));
            accountDel.setRuleUrl(jsonObject.getString("ruleUrl"));
            accountDel.setAftermath(jsonObject.getString("aftermath"));
            return accountDel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //超级分类解析
    private Object SuperSortData(JSONObject jsonObject) {
        try {
         SuperSort superSort=new SuperSort();
         ArrayList<SuperSort.dataTotal> list = new ArrayList<>();
         JSONArray array = getArray(jsonObject, "data");
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    SuperSort.dataTotal dataList = new SuperSort.dataTotal();
                    JSONObject object = array.getJSONObject(i);
                    dataList.setId(getString(object, "id"));
                    dataList.setName(getString(object, "name"));
                    ArrayList<SuperSort.dataTotal.Datatwo> dataTwoList = new ArrayList<>();
                    JSONArray twoArray = getArray(object, "subVoList");
                    for (int j = 0; j < twoArray.length(); j++) {
                        SuperSort.dataTotal.Datatwo datatwo = new SuperSort.dataTotal.Datatwo();
                        JSONObject json = twoArray.getJSONObject(j);
                        datatwo.setId(getString(json, "id"));
                        datatwo.setName(getString(json, "name"));
                        datatwo.setLogo(getString(json, "logo"));
                        dataTwoList.add(datatwo);
                    }
                    dataList.setTwoList(dataTwoList);
                    list.add(dataList);
                }

                superSort.setSubVoList(list);
                return superSort;
            }

        } catch (Exception e) {

        }
        return null;
    }

    private Object TaobaoInit(JSONObject jsonObject){
        //获取底部导航栏数据
        TaobaoBean taobaoBean = new TaobaoBean();
        try {
            taobaoBean.setCouponNum(jsonObject.getInt("couponNum"));
            ArrayList<TaobaoBean.BottomNavigation> navList = new ArrayList<>();
            if (jsonObject.has("tabList")) {
                JSONArray navArray = getArray(jsonObject, "tabList");
                if (navArray != null && navArray.length() > 0) {
                    for (int i = 0; i < navArray.length(); i++) {
                        TaobaoBean.BottomNavigation navigation = new TaobaoBean.BottomNavigation();
                        JSONObject navObject = navArray.getJSONObject(i);
                        navigation.setMaterial(navObject.optString("material"));
                        navigation.setDesc(navObject.optString("desc"));
                        navigation.setType(navObject.optString("type"));
                        navigation.setTitle(navObject.optString("title"));
                        navList.add(navigation);
                    }
                    taobaoBean.setNavList(navList);
                }
            }
            return taobaoBean;
        }catch (Exception e){

        }

        return null;
    }

}
