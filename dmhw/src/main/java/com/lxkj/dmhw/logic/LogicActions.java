package com.lxkj.dmhw.logic;

import com.lxkj.dmhw.bean.JDBanner;

/**
 * 回调值
 * Created by Administrator on 2017/12/15 0015.
 */

public class LogicActions {

    // 初始值
    private static int Base = 0;

    // 失败回调

    public static final int Failed = Base++;

    // 失败回调

    // 页面内部回调

    /**
     * 提现弹窗回调
     */
    public static final int WithdrawalsDialogSuccess = Base++;
    /**
     * 图片分享设置回调
     */
    public static final int ImageShareDialogSuccess = Base++;
    /**
     * 填写邀请码
     */
    public static final int InvitationDialogSuccess = Base++;
    /**
     * 图片查看器
     */
    public static final int PictureViewerSuccess = Base++;
    /**
     * 登录成功修改我的页面
     */
    public static final int LoginStatusSuccess = Base++;
    /**
     * 微信登录注册
     */
    public static final int WeChatRegisterSuccess = Base++;
    /**
     * 分享状态
     */
    public static final int ShareDialogSuccess = Base++;
    /**
     * 兑换积分
     */
    public static final int ScoreDialogSuccess = Base++;
    /**
     * 商品已下架
     */
    public static final int UndercarriageSuccess = Base++;
    /**
     * 签到更新用户资料
     */
    public static final int SignUserInfoSuccess = Base++;
    /**
     * 修改资料更新
     */
    public static final int ModifyUserInfoSuccess = Base++;
    /**
     * 收藏选择
     */
    public static final int CollectionCheckSuccess = Base++;
    /**
     * 时间选择
     */
    public static final int CalendarSuccess = Base++;
    /**
     * 升级成功刷新等级
     */
    public static final int RefreshStatusSuccess = Base++;
    /**
     * 申请状态
     */
    public static final int ApplyDialogSuccess = Base++;
    /**
     * 店铺不存在
     */
    public static final int ShopInfoNoSuccess = Base++;
    /**
     * 用户信息页面绑定/解绑微信
     */
    public static final int UserInfoActivitySuccess = Base++;
    /**
     * 微信绑定成功
     */
    public static final int UserInfoActivityBindingSuccess = Base++;
    /**
     * 品牌筛选
     */
    public static final int RightMenuDialogSuccess = Base++;
    /**
     * 昵称设置
     */
    public static final int NameSettingDialogSuccess = Base++;
    /**
     * 商品下架
     */
    public static final int CommodityActivitySuccess = Base++;
    /**
     * 分享图片调用
     */
    public static final int ShareSuccess = Base++;
    /**
     * 支付宝修改
     */
    public static final int AmendAliPaySuccess = Base++;
    /**
     * 中间页下载显示开关
     */
    public static final int DownSwitchDialogSuccess = Base++;

    // 页面内部回调

    // 服务器接口回调

    /**
     * 获取access_token
     */
    public static final int AccessTokenSuccess = Base++;
    /**
     * 获取微信用户信息
     */
    public static final int WeChatUserInfoSuccess = Base++;
    /**
     * 全网搜索
     */
    public static final int SearchAllSuccess = Base++;
    /**
     * 判断是否绑定过微信公众号
     */
    public static final int WeChatPublicSuccess = Base++;
    /**
     * 提现-微信
     */
    public static final int SetWithdrawalsWeChatSuccess = Base++;
    /**
     * 获取公众号信息
     */
    public static final int GetWeChatAboutSuccess = Base++;
    /**
     * 邀请码登录成功回调
     */
    public static final int ExtensionSuccess = Base++;
    /**
     * 获取验证码成功回调
     */
    public static final int RegisterCodeSuccess = Base++;
    /**
     * 注册成功回调
     */
    public static final int RegisterSuccess = Base++;
    /**
     * 登录成功回调
     */
    public static final int LoginSuccess = Base++;
    /**
     * 验证码登录成功
     */
    public static final int LoginCodeSuccess = Base++;
    /**
     * 获取商品一级类目成功回调
     */
    public static final int CategoryOneSuccess = Base++;
    /**
     * 获取商品二级类目成功回调
     */
    public static final int CategoryTwoSuccess = Base++;
    /**
     * 广告成功回调
     */
    public static final int BannerSuccess = Base++;
    /**
     * 商品搜索（类目）成功回调
     */
    public static final int ShopCategorySuccess = Base++;
    /**
     * 商品搜索（标签）成功回调
     */
    public static final int ShopLabelSuccess = Base++;
    /**
     * 商品搜索（ID）成功回调
     */
    public static final int ShopIdSuccess = Base++;
    /**
     * 商品搜索（猜你喜欢）成功回调
     */
    public static final int ShopLikeSuccess = Base++;
    /**
     * 商品搜索（名称）成功回调
     */
    public static final int ShopNameSuccess = Base++;
    /**
     * 热搜成功回调
     */
    public static final int SearchHotSuccess = Base++;
    /**
     * 修改用户资料成功回调
     */
    public static final int UserInfoSuccess = Base++;
    /**
     * 每日推荐成功回调
     */
    public static final int RecommendSuccess = Base++;
    /**
     * 营销素材成功回调
     */
    public static final int MarketingSuccess = Base++;
    /**
     * 直播地址
     */
    public static final int LiveSuccess = Base++;
    /**
     * 获取用户信息
     */
    public static final int GetUserInfoSuccess = Base++;
    /**
     * 我的团队
     */
    public static final int MyTeamSuccess = Base++;

    /**
     * 我的粉丝
     */
    public static final int MyFansSuccess = Base++;
    /**
     * 个人销售记录
     */
    public static final int UserAccountSuccess = Base++;
    /**
     * 微信登录
     */
    public static final int LoginWeChatSuccess = Base++;
    /**
     * 获取客服
     */
    public static final int GetServiceSuccess = Base++;
    /**
     * 设置客服
     */
    public static final int SetServiceSuccess = Base++;
    /**
     * 修改密码
     */
    public static final int ModifyPasswordSuccess = Base++;
    /**
     * 获取图片分享模式
     */
    public static final int GetShareModelSuccess = Base++;
    /**
     * 设置图片分享模式
     */
    public static final int SetShareModelSuccess = Base++;
    /**
     * 获取分享模式
     */
    public static final int GetShareSuccess = Base++;
    /**
     * 设置分享模式
     */
    public static final int SetShareSuccess = Base++;
    /**
     * 绑定微信
     */
    public static final int BindingWeChatSuccess = Base++;
    /**
     * 判断手机号是否存在
     */
    public static final int ExistencePhoneSuccess = Base++;
    /**
     * 重置密码
     */
    public static final int ResetPasswordSuccess = Base++;
    /**
     * 版本查询
     */
    public static final int VersionSuccess = Base++;
    /**
     * 绑定/解绑支付宝账户
     */
    public static final int BindingAlipaySuccess = Base++;
    /**
     * 获取提现信息
     */
    public static final int WithdrawalsSuccess = Base++;
    /**
     * 提现记录
     */
    public static final int WithdrawalsStatusSuccess = Base++;
    /**
     * 申请提现
     */
    public static final int SetWithdrawalsSuccess = Base++;
    /**
     * 推广数据
     */
    public static final int PopularizeSuccess = Base++;
    /**
     * 推广订单获取
     */
    public static final int GetOrderSuccess = Base++;
    /**
     * 获取APP分享地址
     */
    public static final int AppShareSuccess = Base++;
    /**
     * 获取上级客服
     */
    public static final int GetUpServiceSuccess = Base++;
    /**
     * 获取帮助列表
     */
    public static final int GetHelpSuccess = Base++;
    /**
     * 获取分类帮助列表
     */
    public static final int GetClassifyHelpSuccess = Base++;
    /**
     * Q&A
     */
    public static final int GetAnswersSuccess = Base++;
    /**
     * 签到
     */
    public static final int SignSuccess = Base++;
    /**
     * 申请代理
     */
    public static final int SetAgentSuccess = Base++;
    /**
     * 获取未读消息
     */
    public static final int GetUnreadSuccess = Base++;
    /**
     * 获取最新消息
     */
    public static final int GetNewsSuccess = Base++;
    /**
     * 获取消息详情
     */
    public static final int GetNewsDetailsSuccess = Base++;
    /**
     * 确认收藏
     */
    public static final int ConfirmCollectionSuccess = Base++;
    /**
     * 消息状态更新
     */
    public static final int SetNewsDetailsSuccess = Base++;
    /**
     * 获取用户收藏
     */
    public static final int GetCollectionSuccess = Base++;
    /**
     * 取消收藏
     */
    public static final int CancelCollectionSuccess = Base++;
    /**
     * 获取足迹
     */
    public static final int GetFootprintSuccess = Base++;
    /**
     * 留言反馈
     */
    public static final int FeedbackSuccess = Base++;
    /**
     * 积分总览
     */
    public static final int ScoreOverviewSuccess = Base++;
    /**
     * 积分兑换
     */
    public static final int ScoreExchangeSuccess = Base++;
    /**
     * 积分明细
     */
    public static final int ScoreDetailsSuccess = Base++;
    /**
     * 注册（免短验）
     */
    public static final int RegisterWeChatSuccess = Base++;
    /**
     * 获取QQ客服
     */
    public static final int QQServiceSuccess = Base++;
    /**
     * 获取收入比例
     */
    public static final int GetRatioSuccess = Base++;
    /**
     * 获取高佣链接
     */
    public static final int GetCommissionSuccess = Base++;
    /**
     * 批量取消收藏
     */
    public static final int CancelListCollectionSuccess = Base++;
    /**
     * 获取下级代理数量
     */
    public static final int GetAgentQuantitySuccess = Base++;
    /**
     * 获取升级加盟商条件
     */
    public static final int GetFranchiserSuccess = Base++;
    /**
     * 升级为加盟商
     */
    public static final int SetFranchiserSuccess = Base++;
    /**
     * 获取下级加盟商数量
     */
    public static final int GetFranchiserQuantitySuccess = Base++;
    /**
     * 获取升级合伙人条件
     */
    public static final int GetPartnerSuccess = Base++;
    /**
     * 升级为合伙人
     */
    public static final int SetPartnerSuccess = Base++;
    /**
     * 获取合伙人申请状态查询
     */
    public static final int GetPartnerStatusSuccess = Base++;
    /**
     * A级代理人数
     */
    public static final int AgentOneSuccess = Base++;
    /**
     * B级代理人数
     */
    public static final int AgentTwoSuccess = Base++;
    /**
     * 疯狂榜单
     */
    public static final int BillboardSuccess = Base++;
    /**
     * 首页模块
     */
    public static final int MainModuleSuccess = Base++;
    /**
     * 限时抢购时间段
     */
    public static final int LimitCatalogSuccess = Base++;
    /**
     * 抢购商品列表
     */
    public static final int LimitListSuccess = Base++;
    /**
     * 抢购商品详情
     */
    public static final int LimitCommoditySuccess = Base++;
    /**
     * 领券购买
     */
    public static final int PurchaseSuccess = Base++;
    /**
     * 推送商品
     */
    public static final int CommodityPushSuccess = Base++;
    /**
     * 获取店铺信息
     */
    public static final int ShopInfoSuccess = Base++;
    /**
     * 超级分类一级类目
     */
    public static final int ClassificationOneSuccess = Base++;
    /**
     * 超级分类二级类目
     */
    public static final int ClassificationTwoSuccess = Base++;
    /**
     * 修改支付宝
     */
    public static final int SetAlipaySuccess = Base++;
    /**
     * 判断过是否绑定过支付宝
     */
    public static final int GetAlipaySuccess = Base++;
    /**
     * 搜索联想
     */
    public static final int AssociationSuccess = Base++;
    /**
     * 多商品分享数据
     */
    public static final int RecommendShopSuccess = Base++;
    /**
     * 获取导航栏
     */
    public static final int GetMenuSuccess = Base++;
    /**
     * 获取闪屏广告
     */
    public static final int GetScreenSuccess = Base++;
    /**
     * 查询推广码
     */
    public static final int GetInvitationSuccess = Base++;
    /**
     * 获取爆款推荐列表
     */
    public static final int GetShopSuccess = Base++;
    /**
     * 获取呆萌头条
     */
    public static final int HeadlinesSuccess = Base++;
    /**
     * 首页广告
     */
    public static final int OneFragmentBannerSuccess = Base++;
    /**
     * 大家都在领
     */
    public static final int VoucherSuccess = Base++;
    /**
     * 节省金额
     */
    public static final int SaveSuccess = Base++;
    /**
     * 首页广告
     */
    public static final int AdvertisingSuccess = Base++;
    /**
     * 绑定cid
     */
    public static final int SetCidSuccess = Base++;
    /**
     * 是否绑定微信
     */
    public static final int GetWeChatSuccess = Base++;
    /**
     * 用户信息绑定/解绑微信
     */
    public static final int UserInfoBindingWeChatSuccess = Base++;
    /**
     * 验证旧手机号
     */
    public static final int VerificationOldPhoneSuccess = Base++;
    /**
     * 修改手机号
     */
    public static final int ModificationPhoneSuccess = Base++;
    /**
     * 查券
     */
    public static final int SearchDiscountSuccess = Base++;
    /**
     * 滚动消息
     */
    public static final int RollingMessageSuccess = Base++;
    /**
     * 商品详情页佣金比例
     */
    public static final int CommodityRatioSuccess = Base++;
    /**
     * 获取推广图标
     */
    public static final int PromotionSuccess = Base++;
    /**
     * 获取我的服务
     */
    public static final int MyServiceSuccess = Base++;
    /**
     * 获取品牌列表
     */
    public static final int GetBrandSuccess = Base++;
    /**
     * 分享模板
     */
    public static final int TemplateSuccess = Base++;
    /**
     * 获取H5地址
     */
    public static final int GetH5Success = Base++;
    /**
     * 中间页APP下载开关
     */
    public static final int GetDownSuccess = Base++;
    /**
     * 设置中间页下载开关
     */
    public static final int SetDownSuccess = Base++;
    /**
     * 获取上级用户推荐人的信息
     */
    public static final int ParentUserInfoSuccess = Base++;
    /**
     * 获取新增数据
     */
    public static final int MyTeamTodaySuccess = Base++;

    /**
     * 获取团队总数据
     */
    public static final int  MyTeamTotalSuccess = Base++;
    /**
     * 获取团队数据明细
     */
    public static final int  MyTeamHistoryDetailSuccess = Base++;

    /**
     * 获取条件选择内容
     */
    public static final int  ChooseDataSuccess = Base++;

    /**
     * 淘宝授权失败
     */
    public static final int  noAuthSuccessfulSuccess = Base++;

    /**
     * 团队今日明细数据
     */
    public static final int  MyTeamTodayDetailSuccess = Base++;

    /**
     * 关闭
     */
    public static final int  CloseTbaoAuthDialogSuccess = Base++;

    /**
     * 购物车优惠券查询
     */
    public static final int  SaveMoneyCartSuccess = Base++;

    /**
     * 跳转首页通知
     */
    public static final int  GoMainSuccess = Base++;

    /**
     * 搜索框释放后再弹广告
     */
    public static final int  ShowAdvertisementSuccess= Base++;


    /**
     * 获取验货直播列表
     */
    public static final int RoomListSuccess = Base++;
    /**
     * 获取验货直播详情
     */
    public static final int RoomDetailDataSuccess = Base++;

    /**
     * 获取直播点赞
     */
    public static final int RoomLikeSuccess = Base++;

    /**
     * 获取天猫超市
     */
    public static final int TmallMarketSuccess = Base++;

    /**
     * 获取天猫国际
     */
    public static final int TmallChannelSuccess = Base++;
    /**
     * 获取信用卡token
     */
    public static final int CreditCardTokenSuccess = Base++;

    /**
     * 获取官方活动链接
     */
    public static final int ActivtyChainSuccess = Base++;
    /**
     * 获取多商品图片和标题
     */
    public static final int GetAppTopicSuccess = Base++;

    /**
     * 链接转商品ID
     */
    public static final int ConvertShopidSuccess = Base++;
    /**
     * 首页中间热区广告1数据
     */
    public static final int MainMiddleHotTopicOneSuccess = Base++;
    /**
     * 首页中间热区广告2数据
     */
    public static final int MainMiddleHotTopicTwoSuccess = Base++;
    /**
     * 首页中间热区广告3数据
     */
    public static final int MainMiddleHotTopicThreeSuccess = Base++;

    /**
     * 首页中间热区广告3数据背景图片
     */
    public static final int MainMiddleHotTopicBgSuccess = Base++;

    /**
     * 获取商品详情页面的URL
     */
    public static final int MerchantWebSuccess = Base++;

    /**
     * 获取商品是否收藏
     */
    public static final int IsCollectionSuccess = Base++;

    /**
     * 获取商品分享成功
     */
    public static final int ShareStatusSuccess = Base++;
    /**
     * 获取商品分享成功 调用接口成功之后刷新 萌币列表
     */
    public static final int ShareFinishSuccess = Base++;
    /**
     * 获取个人中心的功能图标
     */
    public static final int TotalAppIconSuccess = Base++;

    /**
     * 获取任务列表数据
     */
    public static final int InitTaskListSuccess = Base++;

    /**
     * 每日签到
     */
    public static final int DailySignSuccess = Base++;

    /**
     * 兑换呆萌币
     */
    public static final int ExchangeScoreSuccess = Base++;
    /**
     * 呆萌明细表
     */
    public static final int ScorereCordSuccess = Base++;

    /**
     * 获取冻结资金
     */
    public static final int GetFrozenMnySuccess = Base++;

    /**
     * 呆萌币提现
     */
    public static final int WithdrawalSuccess = Base++;

    /**
     *每日首购奖励
     */
    public static final int DayBuySuccess = Base++;

    /**
     *搜索弹框任务
     */
    public static final int SearchTaskSuccess = Base++;
    /**
     *注册成功奖励弹框
     */
    public static final int RegisterStatusSuccess = Base++;

    /**
     *自身首购奖励领取
     */
    public static final int FristBuySuccess = Base++;

    /**
     *好友首购奖励领取
     */
    public static final int  ChildFristBuySuccess = Base++;
    /**
     *上传全局异常
     */
    public static final int  UploadBugSuccess = Base++;
    /**
     *刷新任务页面通知
     */
    public static final int  RefreshTaskListSuccess = Base++;


    /**
     *改变首页背景颜色
     */
    public static final int  ChangeViewPagerCurrentColorSuccess = Base++;

    /**
     *轮播图统计事件
     */
    public static final int  AdvertisementClickSuccess = Base++;

    /**
     *特殊处理全网搜的数据返回
     */
    public static final int  SearchAllNewSuccess = Base++;

    /**
     *微信授权窗口
     */
    public static final int  WxAuthDialogSuccess = Base++;

    /**
     *提现成功刷新个人中心提现金额
     */
    public static final int  WithdrawalsRefreshUserInfoSuccess = Base++;


    /**
     *验证码登录判断未绑定微信弹窗
     */
    public static final int  LoginByCodeNoBindSuccess = Base++;

    /**
     *商务合作
     */
    public static final int  BusinessCooperationSuccess = Base++;

    /**
     *更新用户信息通知
     */
    public static final int  UpdateUserInfoSuccess = Base++;
    /**
     *获取审核列表
     */
    public static final int  MerchantReqListSuccess = Base++;
    /**
     *运营推广审核
     */
    public static final int  MerchantReqApproveSuccess = Base++;
    /**
     *操作之后刷新列表
     */
    public static final int  MerchantReqApproveStatusSuccess = Base++;

    /**
     *运营推广已开通
     */
    public static final int  ExtensionMerchantListSuccess = Base++;
    /**
     *刷新消息
     */
    public static final int  UpdateNewsDetailsSuccess = Base++;

    /**
     *获取token
     */
    public static final int  LoginTokenSuccess = Base++;


    /**
     *获取京东分类列表
     */
    public static final int  JDSortListSuccess = Base++;

    /**
     *获取京东轮播图
     */
    public static final int  JDBannerSuccess = Base++;
    /**
     *获取京东分类商品列表
     */
    public static final int  JDGoodsSuccess = Base++;

    /**
     *获取京东分类商品列表
     */
    public static final int  ProfitSumSuccess = Base++;

    /**
     *获取搜索商品
     */
    public static final int  JDSearchGoodsSuccess = Base++;
    /**
     *通知刷新
     */
    public static final int  RefreshSortListSuccess = Base++;
    /**
     *通知加载更多
     */
    public static final int  LoadMoreSortListSuccess = Base++;
    /**
     *获取平台提现信息
     */
    public static final int  WithdrawalsMorePlSuccess = Base++;

    /**
     *获取平台提现信息明细
     */
    public static final int  WithdrawalsInfoListSuccess = Base++;
    /**
     *获取多平台个人订单
     */
    public static final int  MorePlGetOrderSuccess = Base++;
    /**
     *获取多平台团队订单
     */
    public static final int  MorePlTeamGetOrderSuccess = Base++;
    /**
     *获取多平台收益信息
     */
    public static final int  PlatformProfitDetailSuccess = Base++;
    /**
     *获取各个平台收益信息
     */
    public static final int  ProfitCpsInfoSuccess = Base++;
    /**
     *获取小程序码图
     */
    public static final int  LittleAppImageSuccess = Base++;

    /**
     *获取主题和频道商品
     */
    public static final int  ThemeGoodsSuccess = Base++;

    /**
     *获取全网搜转化站内
     */
    public static final int  SearchAllChangeCommoritySuccess = Base++;
    /**
     *获取多平台提现
     */
    public static final int  SetWithdrawalsMorePLSuccess = Base++;

    /**
     *特殊数据处理 通知刷新
     */
    public static final int  TempDataSetActivitySuccess = Base++;

    /**
     *刷新历史记录
     */
    public static final int  RefreshSearchContentSuccess = Base++;

    /**
     *首页右侧小广告
     */
    public static final int  AdvertisingFragmentMainSuccess = Base++;

    /**
     *搜索读取数据库
     */
    public static final int  ReadHistoryContentSuccess = Base++;

    /**
     *升级刷新
     */
    public static final int  RefreshUserTypeLayoutSuccess = Base++;

    /**
     *首页新接口
     */
    public static final int  HomePageInitSuccess = Base++;

    /**
     *首页底部列表
     */
    public static final int  MainBottomListSuccess = Base++;

    /**
     *首页底部列表刷新通知
     */
    public static final int  mainBottomRefreshDataSuccess = Base++;

    /**
     *290商品详情接口
     */
    public static final int  CommodityDetailSuccess = Base++;

    /**
     *290商品详情猜你喜欢
     */
    public static final int  ShopLike290Success = Base++;

    /**
     *290商品详情猜你喜欢
     */
    public static final int  GoodsPromotionSuccess = Base++;

    /**
     *大牌精选导航
     */
    public static final int  BigBrandCategorysSuccess = Base++;

    /**
     *大牌精选导航
     */
    public static final int  BrandInfoListSuccess = Base++;

    /**
     *品牌以及商品列表
     */
    public static final int  BrandAndGoodsListSuccess = Base++;

    /**
     *品牌详情页面
     */
    public static final int  SingleBrandSuccess = Base++;

    /**
     *猜你喜欢
     */
    public static final int  ShopLikeListSuccess = Base++;

    /**
     *滚动头部
     */
    public static final int  ListScrollToTopSuccess = Base++;

    /**
     *隐藏侧边栏广告 通知
     */
    public static final int  HideSlidingMenuSuccess = Base++;

    /**
     *营销素材分类
     */
    public static final int  MarketingTypeSuccess = Base++;

    /**
     *商学院首页
     */
    public static final int  ComCollegeInitSuccess = Base++;

    /**
     *商学院获取列表
     */
    public static final int  ArticleContentDataListSuccess = Base++;

    /**
     *商学院获取文章
     */
    public static final int  ArticleContentDetailSuccess = Base++;

    /**
     *商学院获取文章分享
     */
    public static final int  ShareArticleContentSuccess = Base++;

    /**
     *商学院获取赞
     */
    public static final int  LikeArticleContentSuccess = Base++;

    /**
     *商学院取消赞
     */
    public static final int  UnlikeArticleContentSuccess = Base++;
    /**
     *轮播图判断文章还是视频
     */
    public static final int  ComCollContentDetailSuccess = Base++;

    /**
     *打开详情通知
     */
    public static final int  OpenArticleOrVideoSuccess = Base++;

    /**
     *记录
     */
    public static final int  ArticleContentRecordSuccess = Base++;

    /**
     *全屏分享通知
     */
    public static final int  ShareVideoSuccess = Base++;

    /**
     *京东商品详情
     */
    public static final int  PJWByGoodsIdSuccess = Base++;

    /**
     *京东商品转链
     */
    public static final int  GenByGoodsIdSuccess = Base++;

    /**
     *cps商品收藏
     */
    public static final int  CpsGoodsCollectSuccess = Base++;

    /**
     *cps商品取消
     */
    public static final int  DelCpsGoodsCollectSuccess = Base++;

    /**
     *领券通知
     */
    public static final int  PJWGetCouponSuccess = Base++;

    /**
     *领券转链接口
     */
    public static final int  GetGenByUrlSuccess = Base++;

    /**
     *一键发圈
     */
    public static final int  SendCircleSuccess = Base++;
    /**
     *一键发圈
     */
    public static final int   SendCircleMarketingSuccess = Base++;
    /**
     *一键发圈
     */
    public static final int   SendCircleMarketingAllSuccess = Base++;


    /**
     *一键发圈通知
     */
    public static final int  oneKeyShareSuccess = Base++;

    /**
     *转链
     */
    public static final int  HomeImgClickSuccess = Base++;

    /**
     *信用卡通知
     */
    public static final int  CreditTokenBrocastSuccess = Base++;

    /**
     *生活券
     */
    public static final int  HomeImgClickBrocastSuccess = Base++;

    /**
     *一键转链
     */
    public static final int  ConVertTextToGoodsSuccess = Base++;

    /**
     *一键转链通知
     */
    public static final int  GetMainOnkeyChainSuccess = Base++;

    /**
     *爆款推荐类型
     */
    public static final int  RecommmendTypeSuccess = Base++;

    /**
     *淘宝授权
     */
    public static final int  GotbAuthSuccess = Base++;
    /**
     *新热卖接口 商品以及类目
     */
    public static final int  RankingTypeSuccess = Base++;
    public static final int  RankingListSuccess = Base++;

    /**
     *闪验置换号码
     */
    public static final int  SYChangePhoneNumSuccess = Base++;

    /**
     *闪验绑定手机号码
     */
    public static final int  WxBindPhonebySySuccess = Base++;

    /**
     *京东专区
     */
    public static final int  JDInitMainSuccess = Base++;

    /**
     *通知H5淘宝授权结果
     */
    public static final int  TaoBaoAuthResultToH5Success = Base++;

    /**
     *用户注销初始化
     */
    public static final int  UserCancelInitSuccess = Base++;

    /**
     *用户注销
     */
    public static final int  UserCancelSuccess = Base++;

    /**
     * 拼多多授权请求
     */
    public static final int  noPddAuthSuccess = Base++;

    /**
     *获取pdd授权状态
     */
    public static final int  PDDIsBindAuthSuccess = Base++;

    /**
     *获取CPS足迹
     */
    public static final int  CpsGoodsFootprintSuccess = Base++;

    /**
     * 状态码 501 未登录状态
     */
    public static final int  noLoginSuccess = Base++;

    /**
     * 超级分类
     */
    public static final int  ClassificationTotalSuccess = Base++;

    /**
     * 超级分类跳转
     */
    public static final int  DtkSearchGoodsListSuccess = Base++;

    /**
     * 淘宝优惠券跳转
     */
    public static final int  TaoBaoInit = Base++;

    /**
     * 获取回调成功值
     * @param name 调用值
     * @return 回调值
     */
    public static int getActionsSuccess(String name) {
        int content = 0;
        switch (name) {
            // 页面内部回调
            case "WithdrawalsDialog":// 提现弹窗
                content = WithdrawalsDialogSuccess;
                break;
            case "ImageShareDialog":// 图片分享设置弹窗
                content = ImageShareDialogSuccess;
                break;
            case "InvitationDialog":// 填写邀请码弹窗
                content = InvitationDialogSuccess;
                break;
            case "PictureViewer":// 图片查看器
                content = PictureViewerSuccess;
                break;
            case "LoginStatus":// 登录成功修改我的页面
                content = LoginStatusSuccess;
                break;
            case "WeChatRegister":// 微信登录注册
                content = WeChatRegisterSuccess;
                break;
            case "ShareDialog":// 分享状态
                content = ShareDialogSuccess;
                break;
            case "ScoreDialog":// 兑换积分
                content = ScoreDialogSuccess;
                break;
            case "Undercarriage":// 商品下架
                content = UndercarriageSuccess;
                break;
            case "SignUserInfo":// 签到更新用户资料
                content = SignUserInfoSuccess;
                break;
            case "ModifyUserInfo":// 修改用户资料更新
                content = ModifyUserInfoSuccess;
                break;
            case "CollectionCheck":// 收藏选择
                content = CollectionCheckSuccess;
                break;
            case "Calendar":// 选择时间
                content = CalendarSuccess;
                break;
            case "RefreshStatus":// 刷新用户等级
                content = RefreshStatusSuccess;
                break;
            case "ApplyDialog":// 申请状态
                content = ApplyDialogSuccess;
                break;
            case "ShopInfoNo":// 店铺信息不存在
                content = ShopInfoNoSuccess;
                break;
            case "UserInfoActivity":// 用户信息页面绑定/解绑微信
                content = UserInfoActivitySuccess;
                break;
            case "UserInfoActivityBinding":// 微信绑定成功
                content = UserInfoActivityBindingSuccess;
                break;
            case "RightMenuDialog":// 品牌筛选
                content = RightMenuDialogSuccess;
                break;
            case "NameSettingDialog":// 昵称设置
                content = NameSettingDialogSuccess;
                break;
            case "CommodityActivity":// 商品下架
                content = CommodityActivitySuccess;
                break;
            case "Share":
                content = ShareSuccess;
                break;
            case "AmendAliPay":// 修改支付宝
                content = AmendAliPaySuccess;
                break;
            case "DownSwitchDialog":// 中间页显示开关
                content = DownSwitchDialogSuccess;
                break;
            // 服务器接口回调
            case "AccessToken":// 获取access_token
                content = AccessTokenSuccess;
                break;
            case "WeChatUserInfo":// 获取微信用户信息
                content = WeChatUserInfoSuccess;
                break;
            case "SearchAll":// 全网搜索
                content = SearchAllSuccess;
                break;
            case "WeChatPublic":// 判断是否绑定微信公众号
                content = WeChatPublicSuccess;
                break;
            case "SetWithdrawalsWeChat":// 微信-提现
                content = SetWithdrawalsWeChatSuccess;
                break;
            case "GetWeChatAbout":// 获取公众号信息
                content = GetWeChatAboutSuccess;
                break;
            case "Extension":// 邀请码登录
                content = ExtensionSuccess;
                break;
            case "RegisterCode":// 获取验证码
                content = RegisterCodeSuccess;
                break;
            case "Register":// 注册
                content = RegisterSuccess;
                break;
            case "Login":// 登录
                content = LoginSuccess;
                break;
            case "LoginCode":// 验证码登录
                content = LoginCodeSuccess;
                break;
            case "CategoryOne":// 商品一级类目
                content = CategoryOneSuccess;
                break;
            case "CategoryTwo":// 商品二级类目
                content = CategoryTwoSuccess;
                break;
            case "Banner":// 广告
                content = BannerSuccess;
                break;
            case "ShopCategory":// 商品搜索（类目）
                content = ShopCategorySuccess;
                break;
            case "ShopLabel":// 商品搜索（标签）
                content = ShopLabelSuccess;
                break;
            case "ShopId":// 商品搜索（ID）
                content = ShopIdSuccess;
                break;
            case "ShopLike":// 商品搜索（猜你喜欢）
                content = ShopLikeSuccess;
                break;
            case "ShopName":// 商品搜索（名称）
                content = ShopNameSuccess;
                break;
            case "SearchHot":// 热搜
                content = SearchHotSuccess;
                break;
            case "UserInfo":// 用户资料修改
                content = UserInfoSuccess;
                break;
            case "Recommend":// 每日推荐
                content = RecommendSuccess;
                break;
            case "Marketing":// 营销素材
                content = MarketingSuccess;
                break;
            case "Live":// 直播地址
                content = LiveSuccess;
                break;
            case "GetUserInfo":// 获取用户信息
                content = GetUserInfoSuccess;
                break;
            case "MyTeam":// 我的团队
                content = MyTeamSuccess;
                break;
            case "MyFans":// 我的粉丝
                content = MyFansSuccess;
                break;
            case "UserAccount":// 个人销售记录
                content = UserAccountSuccess;
                break;
            case "LoginWeChat":// 微信登录
                content = LoginWeChatSuccess;
                break;
            case "GetService":// 获取客服
                content = GetServiceSuccess;
                break;
            case "SetService":// 设置客服
                content = SetServiceSuccess;
                break;
            case "ModifyPassword":// 修改密码
                content = ModifyPasswordSuccess;
                break;
            case "GetShareModel":// 获取图片分享模式
                content = GetShareModelSuccess;
                break;
            case "SetShareModel":// 设置图片分享模式
                content = SetShareModelSuccess;
                break;
            case "GetShare":// 获取分享模式
                content = GetShareSuccess;
                break;
            case "SetShare":// 设置分享模式
                content = SetShareSuccess;
                break;
            case "BindingWeChat":// 绑定微信
                content = BindingWeChatSuccess;
                break;
            case "ExistencePhone":// 判断手机号是否存在
                content = ExistencePhoneSuccess;
                break;
            case "ResetPassword":// 重置密码
                content = ResetPasswordSuccess;
                break;
            case "Version":// 版本查询
                content = VersionSuccess;
                break;
            case "BindingAlipay":// 绑定/解绑支付宝账户
                content = BindingAlipaySuccess;
                break;
            case "Withdrawals":// 获取提现信息
                content = WithdrawalsSuccess;
                break;
            case "WithdrawalsStatus":// 提现记录
                content = WithdrawalsStatusSuccess;
                break;
            case "SetWithdrawals":// 申请提现
                content = SetWithdrawalsSuccess;
                break;
            case "Popularize":// 推广数据
                content = PopularizeSuccess;
                break;
            case "GetOrder":// 推广订单获取
                content = GetOrderSuccess;
                break;
            case "AppShare":// 获取APP分享地址
                content = AppShareSuccess;
                break;
            case "GetUpService":// 获取上级客服
                content = GetUpServiceSuccess;
                break;
            case "GetHelp":// 获取帮助列表
                content = GetHelpSuccess;
                break;
            case "GetClassifyHelp":// 获取分类帮助列表
                content = GetClassifyHelpSuccess;
                break;
            case "GetAnswers":// Q&A
                content = GetAnswersSuccess;
                break;
            case "Sign":// 签到
                content = SignSuccess;
                break;
            case "SetAgent":// 申请代理
                content = SetAgentSuccess;
                break;
            case "GetUnread":// 获取未读消息
                content = GetUnreadSuccess;
                break;
            case "GetNews":// 获取最新消息
                content = GetNewsSuccess;
                break;
            case "GetNewsDetails":// 获取消息详情
                content = GetNewsDetailsSuccess;
                break;
            case "ConfirmCollection":// 确认收藏
                content = ConfirmCollectionSuccess;
                break;
            case "SetNewsDetails":// 消息状态更新
                content = SetNewsDetailsSuccess;
                break;
            case "GetCollection":// 获取用户收藏
                content = GetCollectionSuccess;
                break;
            case "CancelCollection":// 取消收藏
                content = CancelCollectionSuccess;
                break;
            case "GetFootprint":// 获取足迹
                content = GetFootprintSuccess;
                break;
            case "Feedback":// 留言反馈
                content = FeedbackSuccess;
                break;
            case "ScoreOverview":// 积分总览
                content = ScoreOverviewSuccess;
                break;
            case "ScoreExchange":// 积分兑换
                content = ScoreExchangeSuccess;
                break;
            case "ScoreDetails":// 积分明细
                content = ScoreDetailsSuccess;
                break;
            case "RegisterWeChat":// 注册（免短验）
                content = RegisterWeChatSuccess;
                break;
            case "QQService":// 获取QQ客服
                content = QQServiceSuccess;
                break;
            case "GetRatio":// 获取收入比例
                content = GetRatioSuccess;
                break;
            case "GetCommission":// 获取高佣链接
                content = GetCommissionSuccess;
                break;
            case "CancelListCollection":// 批量取消收藏
                content = CancelListCollectionSuccess;
                break;
            case "GetAgentQuantity":// 获取下级代理数量
                content = GetAgentQuantitySuccess;
                break;
            case "GetFranchiser":// 获取升级加盟商条件
                content = GetFranchiserSuccess;
                break;
            case "SetFranchiser":// 升级为加盟商
                content = SetFranchiserSuccess;
                break;
            case "GetFranchiserQuantity":// 获取下级加盟商数量
                content = GetFranchiserQuantitySuccess;
                break;
            case "GetPartner":// 获取升级合伙人条件
                content = GetPartnerSuccess;
                break;
            case "SetPartner":// 升级为合伙人
                content = SetPartnerSuccess;
                break;
            case "GetPartnerStatus":// 获取合伙人申请状态查询
                content = GetPartnerStatusSuccess;
                break;
            case "AgentOne":// A级代理人数
                content = AgentOneSuccess;
                break;
            case "AgentTwo":// B级代理人数
                content = AgentTwoSuccess;
                break;
            case "Billboard":// 疯狂榜单
                content = BillboardSuccess;
                break;
            case "MainModule":// 首页模块
                content = MainModuleSuccess;
                break;
            case "LimitCatalog":// 限时抢购时间段
                content = LimitCatalogSuccess;
                break;
            case "LimitList":// 抢购商品列表
                content = LimitListSuccess;
                break;
            case "LimitCommodity":// 抢购商品详情
                content = LimitCommoditySuccess;
                break;
            case "Purchase":// 领券购买
                content = PurchaseSuccess;
                break;
            case "CommodityPush":// 推送商品
                content = CommodityPushSuccess;
                break;
            case "ShopInfo":// 获取店铺信息
                content = ShopInfoSuccess;
                break;
            case "ClassificationOne":// 超级分类一级类目
                content = ClassificationOneSuccess;
                break;
            case "ClassificationTwo":// 超级分类二级类目
                content = ClassificationTwoSuccess;
                break;
            case "SetAlipay":// 修改支付宝
                content = SetAlipaySuccess;
                break;
            case "GetAlipay":// 判断是否绑定过支付宝
                content = GetAlipaySuccess;
                break;
            case "Association":// 搜索联想
                content = AssociationSuccess;
                break;
            case "RecommendShop":// 多商品分享数据
                content = RecommendShopSuccess;
                break;
            case "GetMenu":// 获取导航栏
                content = GetMenuSuccess;
                break;
            case "GetScreen":// 获取闪屏广告
                content = GetScreenSuccess;
                break;
            case "GetInvitation":// 查询推广码
                content = GetInvitationSuccess;
                break;
            case "GetShop":// 获取爆款推荐列表
                content = GetShopSuccess;
                break;
            case "Headlines":// 获取呆萌头条
                content = HeadlinesSuccess;
                break;
            case "OneFragmentBanner":// 首页广告
                content = OneFragmentBannerSuccess;
                break;
            case "Voucher":// 大家都在领
                content = VoucherSuccess;
                break;
            case "Save":// 节省金额
                content = SaveSuccess;
                break;
            case "Advertising":// 首页广告
                content = AdvertisingSuccess;
                break;
            case "SetCid":// 绑定cid
                content = SetCidSuccess;
                break;
            case "GetWeChat":// 是否绑定微信
                content = GetWeChatSuccess;
                break;
            case "UserInfoBindingWeChat":// 用户信息绑定/解绑微信
                content = UserInfoBindingWeChatSuccess;
                break;
            case "VerificationOldPhone":// 验证旧手机号
                content = VerificationOldPhoneSuccess;
                break;
            case "ModificationPhone":// 修改手机号
                content = ModificationPhoneSuccess;
                break;
            case "SearchDiscount":// 查券
                content = SearchDiscountSuccess;
                break;
            case "RollingMessage":// 滚动消息
                content = RollingMessageSuccess;
                break;
            case "CommodityRatio":// 商品详情佣金比例
                content = CommodityRatioSuccess;
                break;
            case "Promotion":// 获取推广图标
                content = PromotionSuccess;
                break;
            case "MyService":// 获取我的服务
                content = MyServiceSuccess;
                break;
            case "GetBrand":// 获取品牌列表
                content = GetBrandSuccess;
                break;
            case "Template":// 分享模板
                content = TemplateSuccess;
                break;
            case "GetH5":// 获取H5地址
                content = GetH5Success;
                break;
            case "GetDown":// 中间页App下载开关
                content = GetDownSuccess;
                break;
            case "SetDown":// 设置中间页下载开关
                content = SetDownSuccess;
                break;
            case "ParentUserInfo":// 获取上级用户推荐人信息
                content = ParentUserInfoSuccess;
                break;
            case "MyTeamToday":// 获取新增数据
                content =  MyTeamTodaySuccess;
                break;
            case "MyTeamTotal":// 获取团队总数据
                content =  MyTeamTotalSuccess;
                break;
            case "MyTeamHistoryDetail":// 获取团队数据明细
                content =  MyTeamHistoryDetailSuccess;
                break;
            case "ChooseData":// 选择时间
                content = ChooseDataSuccess;
                break;
            case "noAuthSuccessful":// 授权失败
                content = noAuthSuccessfulSuccess;
                break;
            case "MyTeamTodayDetail"://团队今日明细
                content = MyTeamTodayDetailSuccess;
                break;
            case "CloseTbaoAuthDialog"://关闭授权窗口
                content = CloseTbaoAuthDialogSuccess;
                break;
            case "SaveMoneyCart"://购物车查券
                content =  SaveMoneyCartSuccess;
                break;
            case "GoMain"://跳转首页
                content =  GoMainSuccess;
                break;
            case "RoomList"://验货直播列表
                content =  RoomListSuccess;
                break;
            case "RoomDetailData"://验货直播详情
                content =  RoomDetailDataSuccess;
                break;
            case "RoomLike"://验货直播间点赞
                content =  RoomLikeSuccess;
                break;
            case "ShowAdvertisement"://弹广告
                content =  ShowAdvertisementSuccess;
                break;
            case "TmallMarket"://天猫超市
                content =  TmallMarketSuccess;
                break;
            case "TmallChannel"://天猫国际
                content =  TmallChannelSuccess;
                break;
            case "CreditCardToken"://天猫国际
                content =  CreditCardTokenSuccess;
                break;
            case "ActivityChain"://获取官方活动链接
                content =  ActivtyChainSuccess;
                break;
            case "GetAppTopic"://获取多商品图片和标题
                content =  GetAppTopicSuccess;
                break;
            case "ConvertShopid"://链接转ID
                content = ConvertShopidSuccess;
                break;
            case "MainMiddleHotTopicOne"://首页中间热区第1个广告数据
                content = MainMiddleHotTopicOneSuccess;
                break;
            case "MainMiddleHotTopicTwo"://首页中间热区第2个广告数据
                content = MainMiddleHotTopicTwoSuccess;
                break;
            case "MainMiddleHotTopicThree"://首页中间热区第3个广告数据
                content = MainMiddleHotTopicThreeSuccess;
                break;
            case "MainMiddleHotTopicBg"://首页中间热区第3个广告背景图片
                content =  MainMiddleHotTopicBgSuccess;
                break;
            case "MerchantWeb"://获取商品详情的web地址
                content =  MerchantWebSuccess;
                break;
            case "IsCollection"://获取全网商品是否收藏
                content =  IsCollectionSuccess;
                break;
            case "ShareStatus"://分享成功
                content =  ShareStatusSuccess;
                break;
            case "TotalAppIcon"://获取个人中心的功能图标
                content =  TotalAppIconSuccess;
                break;
            case "InitTaskList"://获取任务列表数据
                content =  InitTaskListSuccess;
                break;
            case "DailySign"://每日签到
                content =  DailySignSuccess;
                break;
            case "ExchangeScore"://兑换
                content =  ExchangeScoreSuccess;
                break;
            case "ScorereCord"://兑换明细
                content =  ScorereCordSuccess;
                break;
            case "GetFrozenMny"://获取冻结资金
                content =  GetFrozenMnySuccess;
                break;
            case "Withdrawal"://提现呆萌币模块
                content =  WithdrawalSuccess;
                break;
            case "DayBuy"://每日首购
                content =  DayBuySuccess;
                break;
            case "RegisterStatus":// 注册成功通知 奖励弹框
                content = RegisterStatusSuccess;
                break;
            case "FirstBuy":// 自身首购奖励领取
                content = FristBuySuccess;
                break;
            case "ChildFristBuy":// 好友首购奖励领取
                content = ChildFristBuySuccess;
                break;
            case "UploadBug":// 上传异常
                content = UploadBugSuccess;
                break;
            case "RefreshTaskList"://刷新任务界面
                content = RefreshTaskListSuccess;
                break;
            case "ChangeViewPagerCurrentColor"://viewpager出现就颜色一致
                content = ChangeViewPagerCurrentColorSuccess;
                break;
            case "ShareFinish"://调用接口后刷新通知
                content =  ShareFinishSuccess;
                break;
            case "SearchTask"://搜索标题任务
                content =  SearchTaskSuccess;
                break;
            case "AdvertisementClick"://统计轮播图事件
                content =  AdvertisementClickSuccess;
                break;
            case "SearchAllNew"://返回搜索名称
                content =  SearchAllNewSuccess;
                break;
            case "WxAuthDialog"://微信授权窗口
                content =  WxAuthDialogSuccess;
                break;
            case "WithdrawalsRefreshUserInfo"://微信授权窗口
                content =  WithdrawalsRefreshUserInfoSuccess;
                break;
            case "LoginByCodeNoBind"://验证码登录通知未绑定微信弹窗
                content =  LoginByCodeNoBindSuccess;
                break;
            case "BusinessCooperation"://商务合作
                content =  BusinessCooperationSuccess;
                break;
            case "UpdateUserInfo"://更新用户信息
                content =  UpdateUserInfoSuccess;
                break;
            case "MerchantReqList"://获取推广运营列表数据
                content =  MerchantReqListSuccess;
                break;
            case "MerchantReqApprove"://运营推广审核
                content = MerchantReqApproveSuccess;
                break;
            case "MerchantReqApproveStatus"://运营推广审核刷新通知
                content = MerchantReqApproveStatusSuccess;
                break;
            case "ExtensionMerchantList"://获取已开通列表
                content = ExtensionMerchantListSuccess;
                break;
            case "UpdateNewsDetails"://刷新消息通知
                content = UpdateNewsDetailsSuccess;
                break;
            case "LoginToken"://获取token
                content = LoginTokenSuccess;
                break;
            case "JDSortList"://获取分类列表
                content = JDSortListSuccess;
                break;
            case "JDBanner"://获取轮播图
                content = JDBannerSuccess;
                break;
            case "JDGoods"://获取商品列表
                content = JDGoodsSuccess;
                break;
            case "ProfitSum"://获取我的收益
                content = ProfitSumSuccess;
                break;
            case "JDSearchGoods"://多平台关键字搜索
                content = JDSearchGoodsSuccess;
                break;
            case "RefreshSortList"://通知不同类目下刷新
                content = RefreshSortListSuccess;
                break;
            case "LoadMoreSortList"://通知不同类目下加载更多
                content = LoadMoreSortListSuccess;
                break;
            case "WithdrawalsMorePl"://获取提现信息
                content = WithdrawalsMorePlSuccess;
                break;
            case "WithdrawalsInfoList"://获取提现信息明细
                content = WithdrawalsInfoListSuccess;
                break;
            case "MorePlGetOrder"://获取多平台订单列表
                content = MorePlGetOrderSuccess;
                break;
            case "MorePlTeamGetOrder"://获取多平台团队订单列表
                content = MorePlTeamGetOrderSuccess;
                break;
            case "PlatformProfitDetail"://获取多平台头部收益信息
                content = PlatformProfitDetailSuccess;
                break;
            case "ProfitCpsInfo"://获取各个平台收益信息
                content = ProfitCpsInfoSuccess;
                break;
            case "LittleAppImage"://获取小程序码图
                content = LittleAppImageSuccess;
                break;
            case "ThemeGoods"://主题商品页面
                content = ThemeGoodsSuccess;
                break;
            case "SearchAllChangeCommority"://实体转化
                content = SearchAllChangeCommoritySuccess;
                break;
            case "SetWithdrawalsMorePL"://多平台提现
                content = SetWithdrawalsMorePLSuccess;
                break;
            case "TempDataSetActivity"://特殊数据处理
                content = TempDataSetActivitySuccess;
                break;
            case "RefreshSearchContent"://刷新搜索记录
                content = RefreshSearchContentSuccess;
                break;
            case "AdvertisingFragmentMain"://首页右侧小广告
                content = AdvertisingFragmentMainSuccess;
                break;
            case "ReadHistoryContent"://刷新搜索记录
                content = ReadHistoryContentSuccess;
                break;
            case "RefreshUserTypeLayout"://升级后刷新
                content = RefreshUserTypeLayoutSuccess;
                break;
            case "HomePageInit"://首页新接口
                content = HomePageInitSuccess;
                break;
            case "MainBottomList"://首页底部列表新接口
                content = MainBottomListSuccess;
                break;
            case "mainBottomRefreshData"://首页底部列表刷新
                content = mainBottomRefreshDataSuccess;
                break;
            case "CommodityDetail"://290版本的商品详情接口
                content = CommodityDetailSuccess;
                break;
            case "ShopLike290"://新商品详情的猜你喜欢
                content = ShopLike290Success;
                break;
            case "GoodsPromotion"://商品详情购买分享新接口
                content = GoodsPromotionSuccess;
                break;
            case "BigBrandCategorys"://大牌精选导航
                content = BigBrandCategorysSuccess;
                break;
            case "BrandInfoList"://大牌精选品牌
                content = BrandInfoListSuccess;
                break;
            case "BrandAndGoodsList"://品牌以及商品列表
                content = BrandAndGoodsListSuccess;
                break;
            case "SingleBrand"://品牌详情页
                content = SingleBrandSuccess;
                break;
            case "ShopLikeList"://猜你喜欢
                content = ShopLikeListSuccess;
                break;
            case "ListScrollToTop"://底部列表滚动到顶部的通知
                content = ListScrollToTopSuccess;
                break;
            case "HideSlidingMenu"://底部列表滚动到顶部的通知
                content = HideSlidingMenuSuccess;
                break;
            case "MarketingType"://营销素材分类
                content = MarketingTypeSuccess;
                break;
            case "ComCollegeInit"://商学院首页
                content = ComCollegeInitSuccess;
                break;
            case "ArticleContentDataList"://商学院获取列表
                content = ArticleContentDataListSuccess;
                break;
            case "ArticleContentDetail"://商学院获取文章
                content = ArticleContentDetailSuccess;
                break;
            case "ShareArticleContent"://商学院获取文章分享
                content = ShareArticleContentSuccess;
                break;
            case "LikeArticleContent"://商学院获取赞
                content = LikeArticleContentSuccess;
                break;
            case "UnlikeArticleContent"://商学院获取取消赞
                content = UnlikeArticleContentSuccess;
                break;
            case "ComCollContentDetail"://轮播图判断是文章还是视频
                content = ComCollContentDetailSuccess;
                break;
            case "OpenArticleOrVideo"://轮播图判断是文章还是视频通知
                content = OpenArticleOrVideoSuccess;
                break;
            case "ArticleContentRecord"://记录
                content = ArticleContentRecordSuccess;
                break;
            case "ShareVideo"://全屏分享通知
                content = ShareVideoSuccess;
                break;
            case "PJWByGoodsId"://京东商品详情
                content = PJWByGoodsIdSuccess;
                break;
            case "GenByGoodsId"://京东商品转链
                content = GenByGoodsIdSuccess;
                break;
            case "CpsGoodsCollect"://获取CPS用户收藏
                content = CpsGoodsCollectSuccess;
                break;
            case "DelCpsGoodsCollect"://CPS批量取消用户收藏
                content = DelCpsGoodsCollectSuccess;
                break;
            case "PJWGetCoupon"://PJWbanner领券通知
                content = PJWGetCouponSuccess;
                break;
            case "GetGenByUrl"://PJWbanner领券接口
                content = GetGenByUrlSuccess;
                break;
            case "SendCircle"://一键发圈
                content = SendCircleSuccess;
                break;
            case "SendCircleMarketing"://一键发圈
                content = SendCircleMarketingSuccess;
                break;
            case "SendCircleMarketingAll"://一键发圈
                content = SendCircleMarketingAllSuccess;
                break;
            case "oneKeyShare"://一键发圈通知
                content = oneKeyShareSuccess;
                break;
            case "HomeImgClick"://生活券转链
                content = HomeImgClickSuccess;
                break;
            case "CreditTokenBrocast"://调用信用卡通知
                content = CreditTokenBrocastSuccess;
                break;
            case "HomeImgClickBrocast"://调用生活券通知
                content = HomeImgClickBrocastSuccess;
                break;
            case "ConVertTextToGoods"://一键转链
                content = ConVertTextToGoodsSuccess;
                break;
            case "GetMainOnkeyChain"://弹框一件转链
                content = GetMainOnkeyChainSuccess;
                break;
            case "RecommmendType"://爆款推荐类型
                content = RecommmendTypeSuccess;
                break;
            case "RankingType":// 热卖商品一级类目
                content = RankingTypeSuccess;
                break;
            case "RankingList":// 热卖商品列表
                content = RankingListSuccess;
                break;
            case "GotbAuth"://淘宝授权
                content = GotbAuthSuccess;
                break;
            case "SYChangePhoneNum"://闪验置换
                content = SYChangePhoneNumSuccess;
                break;
            case "JDInitMain"://京东专区
                content = JDInitMainSuccess;
                break;
            case "TaoBaoAuthResultToH5"://通知淘宝授权结果
                content = TaoBaoAuthResultToH5Success;
                break;
            case "WxBindPhonebySy"://闪验绑定手机
                content = WxBindPhonebySySuccess;
                break;
            case "UserCancelInit"://注销初始化
                content = UserCancelInitSuccess;
                break;
            case "UserCancel"://注销动作
                content = UserCancelSuccess;
                break;
            case "noPddAuth":// 拼多多授权
                content = noPddAuthSuccess;
                break;
            case "PDDIsBindAuth"://拼多多授权状态
                content = PDDIsBindAuthSuccess;
                break;
            case "CpsGoodsFootprint"://获取CPS足迹
                content = CpsGoodsFootprintSuccess;
                break;
            case "noLogin"://通知没登录 跳转登录页
                content = noLoginSuccess;
                break;
            case "ClassificationTotal"://超级分类
                content = ClassificationTotalSuccess;
                break;
            case "DtkSearchGoodsList"://超级分类二级跳转
                content = DtkSearchGoodsListSuccess;
                break;
            case "taoBaoInit":
                content=TaoBaoInit;
                break;

        }
        return content;
    }
}
