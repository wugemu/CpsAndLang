package com.lxkj.dmhw.logic;

import com.lxkj.dmhw.BuildConfig;

/**
 * 接口静态类
 * Created by Administrator on 2017/12/25 0025.
 */

public class HttpCommon {
    //呆萌价统一域名
    //正式地址
    public static String host = "http://cps.17gouba.com/TAOBAO/apps/";

    //三合一平台接口统一域名
    //正式地址
    public static String hostMorePl ="http://cps.17gouba.com/CPS/";



    public static String hostUser = hostMorePl + "cps/user/";
    public static String hostJD = hostMorePl + "cps/business/jd/";
    public static String hostPDD = hostMorePl + "cps/business/pdd/";
    public static String hostWPH = hostMorePl + "cps/business/wph/";
    public static String hostSN = hostMorePl + "cps/business/sn/";
    public static String hostKL = hostMorePl + "cps/business/kl/";
    public static String hostMT = hostMorePl + "cps/business/mt/";
    public static String hostLittleApp= hostMorePl + "wxa/getImageBase64";


    /**
     * 获取access_token (必须用GET)
     */
    public static String AccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    /**
     * 获取微信用户信息 (必须用GET)
     */
    public static String WeChatUserInfo = "https://api.weixin.qq.com/sns/userinfo?";
    /**
     * 全网搜索
     */
    public static String SearchAll = host + "optional";
    /**
     * 判断是否绑定微信公众号
     */
    public static String WeChatPublic = host + "subscribe";
    /**
     * 提现-微信
     */
    public static String SetWithdrawalsWeChat = host + "transferApply";
    /**
     * 获取公众号信息
     */
    public static String GetWeChatAbout = host + "getWechetAbout";
    /**
     * 推广码登录
     */
    public static String Extension = host + "loginbyextengid";
    /**
     * 获取短信验证码
     */
    public static String RegisterCode = host + "getsmscode";
    /**
     * 注册
     */
    public static String Register = host + "userregister";
    /**
     * 登录
     */
    public static String Login = host + "loginbyphone";
    /**
     * 验证码登录
     */
    public static String LoginCode = host + "loginbysmscode";
    /**
     * 商品一级类目
     */
    public static String CategoryOne = host + "getshopclassone";
    /**
     * 商品二级类目
     */
    public static String CategoryTwo = host + "getshopclasstwo";
    /**
     * 广告
     */
    public static String Banner = host + "getadvertisement";
    /**
     * 商品搜索（类目）
     */
    public static String ShopCategory = host + "shopsearchbyclass";
    /**
     * 商品搜索（标签）
     */
    public static String ShopLabel = host + "shopsearchbylable";
    /**
     * 商品搜索（ID）
     */
    public static String ShopId = host + "shopsearchbyid";
    /**
     * 商品搜索（猜你喜欢）
     */
    public static String ShopLike = host + "shopyoulike";
    /**
     * 商品搜索（名称）
     */
    public static String ShopName = host + "shopsearchbykeyword";
    /**
     * 热搜
     */
    public static String SearchHot = host + "getkeyword";
    /**
     * 修改用户信息
     */
    public static String UserInfo = host + "userinfoedit";
    /**
     * 商品推荐
     */
    public static String Recommend = host + "getrecommment";
    /**
     * 营销素材
     */
    public static String Marketing = host + "getmarketing";
    /**
     * 获取直播地址
     */
    public static String Live = host + "getonlineaddr";
    /**
     * 获取用户信息
     */
    public static String GetUserInfo = host + "getuserinfo";
    /**
     * 获取我的团队
     */
    public static String MyTeam = host + "getmyteam";

    /**
     * 获取我的粉丝
     */
    public static String MyFans = host + "getmyfans";
    /**
     * 个人账户金额信息
     */
      public static String UserAccount = host + "getuseracountex";
    /**
     * 微信登录
     */
    public static String LoginWeChat = host + "loginbywx";
    /**
     * 获取APP下载显隐
     */
    public static String GetDownloadSwitch = host + "getdownloadmodel";
    /**
     * 设置APP下载显隐
     */
    public static String SetDownloadSwitch = host + "setdownloadmodel";
    /**
     * 获取客服
     */
    public static String GetService = host + "getcustomserver";
    /**
     * 设置客服
     */
    public static String SetService = host + "setcustomserver";
    /**
     * 修改密码
     */
    public static String ModifyPassword = host + "userpwdedit";
    /**
     * 获取图片分享模式
     */
    public static String GetShareModel = host + "getsharemodel";
    /**
     * 设置图片分享模式
     */
    public static String SetShareModel = host + "setsharemodel";
    /**
     * 设置分享模式
     */
    public static String SetShare = host + "setshopshare";
    /**
     * 获取分享模式
     */
    public static String GetShare = host + "getshopshare";
    /**
     * 绑定微信
     */
    public static String BindingWeChat = host + "wxbindphone";
    /**
     * 判断手机号是否存在
     */
    public static String ExistencePhone = host + "userbyphone";
    /**
     * 重置密码
     */
    public static String ResetPassword = host + "userpasswordreset";
    /**
     * 版本查询
     */
    public static String Version = host + "getversion";
    /**
     * 绑定/解绑支付宝账户
     */
    public static String BindingAlipay = host + "usersetalipay";
    /**
     * 获取可提现信息
     */
    public static String Withdrawals = host + "getwithdrawals";
    /**
     * 提现记录
     */
    public static String WithdrawalsStatus = host + "getwithdrawalstatus";
    /**
     * 申请提现
     */
    public static String SetWithdrawals = host + "withdrawalsreq";
    /**
     * 获取推广数据
     */
    public static String Popularize = host + "getteamstatistics";
    /**
     * 推广订单获取
     */
    public static String GetOrder = host + "getshoporderid";
    /**
     * APP分享链接
     */
    public static String AppShare = host + "appshare";
    /**
     * 获取上级客服
     */
    public static String GetUpService = host + "getupcustomserver";
    /**
     * 获取帮助列表
     */
    public static String GetHelp = host + "gethelplist";
    /**
     * 获取分类帮助列表
     */
    public static String GetClassifyHelp = host + "gethelplistbytype";
    /**
     * Q&A
     */
    public static String GetAnswers = host + "gethelpdetail";
    /**
     * 申请代理
     */
    public static String SetAgent = host + "agentreq";
    /**
     * 获取未读消息
     */
    public static String GetUnread = host + "getnewmessagecnt";
    /**
     * 获取最新消息
     */
    public static String GetNews = host + "getnewmessage";
    /**
     * 获取消息详情
     */
    public static String GetNewsDetails = host + "getmessagedetail";
    /**
     * 确认收藏
     */
    public static String ConfirmCollection = host + "usercollection";
    /**
     * 消息状态更新
     */
    public static String SetNewsDetails = host + "setmessagestatusbytype";
    /**
     * 获取用户收藏
     */
    public static String GetCollection = host + "getusercollection";
    /**
     * 取消收藏
     */
    public static String CancelCollection = host + "usercanclecollection";
    /**
     * 获取足迹
     */
    public static String GetFootprint = host + "getuserfootprint";
    /**
     * 留言反馈
     */
    public static String Feedback = host + "userfeedback";
    /**
     * 积分总览
     */
    public static String ScoreOverview = host + "scoreoverview";
    /**
     * 积分兑换
     */
    public static String ScoreExchange = host + "convertibilitysocre";
    /**
     * 积分明细
     */
    public static String ScoreDetails = host + "scoredetail";
    /**
     * 注册（免短验）
     */
    public static String RegisterWeChat = host + "usersetpwd";
    /**
     * 获取QQ客服
     */
    public static String QQService = host + "contactqq";
    /**
     * 获取收入比例
     */
    public static String GetRatio = host + "getratio";
    /**
     * 获取高佣链接
     */
    public static String GetCommission = host + "gethighcomm";
    /**
     * 获取一键发圈(爆款)
     */
    public static String SendCircle = host + "sendCircle";
    /**
     * 获取一键发圈（营销素材）
     */
    public static String SendCircleMarketing = host + "sendCircleMarketing";
    /**
     * 批量取消收藏
     */
    public static String CancelListCollection = host + "userbatcanclecollection";
    /**
     * 获取下级代理数量
     */
    public static String GetAgentQuantity = host + "getagentcnt";
    /**
     * 获取升级加盟商条件
     */
    public static String GetFranchiser = host + "getupfranchiserct";
    /**
     * 升级为加盟商
     */
    public static String SetFranchiser = host + "upfranchiser";
    /**
     * 获取下级加盟商数量
     */
    public static String GetFranchiserQuantity = host + "getfranchisercnt";
    /**
     * 获取升级合伙人条件
     */
    public static String GetPartner = host + "getuppartnerct";
    /**
     * 升级为合伙人
     */
    public static String SetPartner = host + "uppartner";
    /**
     * 合伙人申请状态查询
     */
    public static String GetPartnerStatus = host + "getpartner";
    /**
     * A级代理人数
     */
    public static String AgentOne = host + "getateam";
    /**
     * B级代理人数
     */
    public static String AgentTwo = host + "getbteam";
    /**
     * 首页模块
     */
    public static String MainModule = host + "gethomepage";
    /**
     * 限时抢购时间段
     */
    public static String LimitCatalog = host + "getflashsaletime";
    /**
     * 抢购商品列表
     */
    public static String LimitList = host + "shopflashsale";
    /**
     * 抢购商品详情
     */
    public static String LimitCommodity = host + "shopflashsalebyid";
    /**
     * 领券购买
     */
    public static String Purchase = host + "getcouponlink";
    /**
     * 获取店铺信息
     */
    public static String ShopInfo = host + "getsellinfo";
    /**
     * 超级分类一级类目
     */
    public static String ClassificationOne = host + "getshopclassoneall";
    /**
     * 超级分类二级类目
     */
    public static String ClassificationTwo = host + "getshopclasstwoall";
    /**
     * 修改支付宝账户
     */
    public static String SetAlipay = host + "updatealipay";
    /**
     * 判断是否绑定过支付宝
     */
    public static String GetAlipay = host + "alipayisbind";
    /**
     * 搜索联想
     */
    public static String Association = host + "getSearchThinkList";
    /**
     * 多商品分享数据
     */
    public static String RecommendShop = host + "getShopShareByIds";
    /**
     * 获取导航
     */
    public static String GetMenu = host + "getmenu";
    /**
     * 获取闪屏广告
     */
    public static String GetScreen = host + "getframeadv";
    /**
     * 查询推广码
     */
    public static String GetInvitation = host + "findbyextensionid";
    /**
     * 爆款推荐列表
     */
    public static String GetShop = host + "getburstingshop";
    /**
     * 获取呆萌头条
     */
    public static String Headlines = host + "getdmjhot";
    /**
     * 大家都在领
     */
    public static String Voucher = host + "getvouchers";
    /**
     * 节省金额
     */
    public static String Save = host + "getsavemny";
    /**
     * 绑定cid
     */
    public static String SetCid = host + "setcid";
    /**
     * 是否绑定微信
     */
    public static String GetWeChat = host + "isbindwx";
    /**
     * 用户信息绑定/解绑微信
     */
    public static String UserInfoBindingWeChat = host + "usersetwx";
    /**
     * 验证旧手机号
     */
    public static String VerificationOldPhone = host + "oldphoneverifica";
    /**
     * 修改手机号
     */
    public static String ModificationPhone = host + "setnewphone";
    /**
     * 滚动消息
     */
    public static String RollingMessage = host + "getsysmessage";
    /**
     * 获取推广图标
     */
    public static String Promotion = host + "getpromotionico";
    /**
     * 获取我的服务
     */
    public static String MyService = host + "getserviceico";
    /**
     * 获取品牌列表
     */
    public static String GetBrand = host + "getshopbrand";
    /**
     * 一键查券
     */
    public static String SearchDiscount = host + "optionalForApp?";
    /**
     * 分享模板
     */
    public static String Template = host + "getappset";
    /**
     * 获取H5地址
     */
    public static String GetH5 = host + "getmerchantweb";
    /**
     * 中间页app下载开关
     */
    public static String GetDown = host + "getdomainapp";
    /**
     * 设置中间页下载开关
     */
    public static String SetDown = host + "setdomainapp";
    /**
     * 上报异常
     */
    public static String SetCatch = host + "androidbug";
    /**
     * 获取我的上级用户推荐人的信息
     */
    public static String ParentUserInfo = host + "getparentuserinfo";
    /**
     * 今日新增数据
     */
    public static String MyTeamToday = host + "getmyteamtoday";
    /**
     * 团队总数据
     */
    public static String MyTeamTotal = host + "getmyteamhistory";
    /**
     * 团队数据明细
     */
    public static String MyTeamHistoryDetail = host + "getmyteamhistorydetail";

    /**
     * 团队数据今日明细
     */
    public static String MyTeamTodayDetail = host + "getmyteamtodaydetail";

    /**
     * 购物车优惠券查询
     */
    public static String SaveMoneyCart = host + "getcartprivilege";

    /**
     * 验货直播列表数据
     */
    public static String RoomList = host + "getroom";

    /**
     * 验货直播详情
     */
    public static String RoomDetailData = host + "getroomdetail";
    /**
     * 直播点赞
     */
    public static String RoomLike = host + "addroomlike";

    /**
     * 获取信用卡token
     */
    public static String getCreditToken = host + "getCreditToken";

    /**
     * 获取官方活动链接
     */
    public static String ActivtyChain = host + "activitychain";
    /**
     * 获取多商品主题图片和标题
     */
    public static String getapptopic = host + "getapptopic";

    /**
     * 链接转ID
     */
    public static String ConvertShopid = host + "convertshop";

    /**
     *获取商品详情的web地址
     */
    public static String GetMerchantWeb = host + "getmerchantweb";
    /**
     *获取全网商品是否收藏
     */
    public static String GetAllNetGoodsCollection = host + "iscollection";

    /**
     *获取个人中心所有功能图标
     */
    public static String GetTotalAppIcon = host + "getappico";

    /**
     *初始化任务列表数据
     */
    public static String GetInitTaskList = host + "initscore";
    /**
     *完成任务接口 传类型
     */
    public static String finishTask = host + "finishscore";
    /**
     *兑换金币
     */
    public static String ExchangeScore = host + "exchangescore";

    /**
     *呆萌明细
     */
    public static String ScorereCord = host + "scorerecord";

    /**
     *获取冻结资金
     */
    public static String GetFrozenMny = host + "getfrozenmny";
    /**
     *获取奖金明细
     */
    public static String GetScoreaccount = host + "scoreaccount";

    /**
     *呆萌币提现
     */
    public static String Withdrawal = host + "withdrawal";

    /**
     *自身首购领取
     */
    public static String GetFristBuy = host + "getfristbuy";

    /**
     *好友首购领取
     */
    public static String ChildFristBuy = host + "getchildfristbuy";
    /**
     *好友首购领取
     */
    public static String UploadAndroidBug = host + "androidbug";

    /**
     *统计轮播图点击事件
     */
    public static String AdvertisementClick = host + "advertisementclick";
    /**
     *获取审批
     */
    public static String BusinessCooperation = host + "businessCooperation";

    /**
     *获取审批列表数据
     */
    public static String MerchantReqList = host + "merchantReqList";
    /**
     *获取审批列表数据
     */
    public static String MerchantReqApprove = host + "merchantReqApprove";
    /**
     *获取已开通列表数据
     */
    public static String ExtensionMerchantList = host + "extensionMerchantList";

    /**
     *获取我的收益
     */
    public static String ProfitSum = host + "getProfitSum";


    /**
     * 新的首页接口（合并）
     */
    public static String  HomePageInit= host + "homeInit";

    /**
     * 首页猜你喜欢
     */
    public static String MainBottomList = host + "goodsList/tbMaterial";


    /**
     * 商品详情290
     */
    public static String CommodityDetail = host + "goodsDetail";
    /**
     * 购买 分享新接口
     */
    public static String GoodsPromotion = host + "goodsPromotion";

    /**
     * 大牌精选 分类导航
     */
    public static String BigBrandCategorys= host + "categorys";

    /**
     * 大牌精选 分类之下的品牌
     */
    public static String BrandInfoList= host + "brandInfoList";

    /**
     *  品牌以及商品列表
     */
    public static String BrandAndGoodsList= host + "brandAndGoodsList";

    /**
     *  单品牌详情页
     */
    public static String SingleBrand= host + "singleBrand";

    /**
     *  单品牌详情页
     */
    public static String getmarketingtype= host + "getmarketingtype";

    /**
     *  商学院一级界面
     */
    public static String ComCollegeInit= host + "getCollegeInit";

    /**
     *  商学院获取列表
     */
    public static String ArticleContentDataList= host + "getArticleContentData";
    /**
     *  浏览记录
     */
    public static String ArticleContentRecord= host + "getArticleContentRecord";



    /**
     *  商学院获取文章详情
     */
    public static String ArticleContentDetail= host + "getArticleContent";

    /**
     *  商学院文章分享内容请求
     */
    public static String ShareArticleContent= host + "shareArticleContent";

    /**
     *  商学院文章赞操作
     */
    public static String LikeArticleContent= host + "likeArticleContent";

    /**
     *  商学院文章取消赞操作
     */
    public static String UnlikeArticleContent= host + "unlikeArticleContent";

    /**
     * 生活券转链
     */
    public static String HomeImgClick= host + "homeImgClick";

    /**
     * 一键转链
     */
    public static String ConVertTextToGoods= host + "convertTextToGoods";


    /**
     * 爆款推荐头部类型
     */
    public static String RecommmendType= host + "getrecommmenttype";

    /**
     * 新热卖接口
     * dtk/getRankingType  热销榜单上面的类目接口  返回对象data数组，字段id和name(都是字符串）
     * dtk/getRankingList 热销榜单商品数据接口（不分页）参数cid(上面选中类目的id,初始化的时候可以为空默认第一个选项)    返回对象是新的商品列表数据格式一致
     */
    public static String RankingType= host + "hdk/getRankingType";
    public static String RankingList= host + "hdk/getRankingList";

    /**
     * 闪验置换手机号码
     */
    public static String SYChangePhoneNum = host + "loginbysy";


    /**
     * 闪验绑定手机号码
     */
    public static String WxBindPhonebySy = host + "wxbindphonebysy";


    /**
     * 用户注销账号数据初始化
     */
    public static String UserCancelInit = host + "userCancelInit";

    /**
     * 用户注销账号动作
     */
    public static String UserCancel = host + "userCancel";

    /**
     * 超级分类获取接口dtk/getSuperCategory （没参数）
     */
    public static String ClassificationTotal = host + "dtk/getSuperCategory";

    /**
     * 超级分类获取接口dtk/getSuperCategory （没参数）
     */
    public static String DTKSearchGoodsList = host + "dtk/getSearchGoodsList";


    /**
     * ====================================三合一新品台接口====版本2.7.0=======================================================
     */
    /**
     *获取小程序码图
     */
    public static String  LittleAppImage = hostLittleApp;

    /**
     *获取登录凭证 token
     */
    public static String LoginToken = hostUser + "dmjLogin";

    /**
     *获取提现信息
     */
    public static String  WithdrawalsInfo= hostUser + "getExtractInfo";
    /**
     * 提现记录
     */
    public static String WithdrawalsInfoList = hostUser + "getExtractList";
    /**
     * 用户提现操作
     */
    public static String WithdrawalsExtract = hostUser + "extract";

    /**
     * 平台收益详情总览
     */
    public static String PlatformProfitDetail = hostUser + "getProfitViewInfo";
    /**
     * 平台收益各平台收益详情
     */
    public static String  ProfitCpsInfo = hostUser + "getProfitCpsInfo";

    /**
     * 获取用户收藏
     */
    public static String CpsGoodsCollect = hostUser + "getCpsGoodsCollect";

    /**
     * 批量删除用户收藏
     */
    public static String DelCpsGoodsCollect = hostUser + "delCpsGoodsCollect";

    /**
     * 足迹
     */
    public static String CpsGoodsFootprint = hostUser + "getCpsGoodsFootprint";


    //====================================以下是京东================================================

    /**
     *获取京东头部分类列表
     */
    public static String JDSortList = hostJD + "getCatList";

    /**
     *获取京东头部轮播图
     */
    public static String JDBanner = hostJD + "getMainAdvList";

    /**
     *获取京东商品列表
     */
    public static String JDGoods = hostJD + "cat";

    /**
     *获取京东关键字商品查询列表
     */
    public static String JDSearchGoods = hostJD + "query";

    /**
     *获取京东订单
     */
    public static String JDOrderList = hostJD + "getSelfOrderList";

    /**
     *获取京东团队订单
     */
    public static String JDTeamOrderList = hostJD + "getTeamOrderList";

    /**
     *获取京东频道商品
     */
    public static String JDChannelGoods = hostJD + "channel";
    /**
     *获取京东主题商品
     */
    public static String JDThemeGoods = hostJD + "theme";

    /**
     *获取京东商品详情
     */
    public static String JDByGoodsId = hostJD + "getByGoodsId";

    /**
     *获取京东转链
     */
    public static String JDGenByGoodsId = hostJD + "genByGoodsId";

    /**
     *获取京东收藏商品
     */
    public static String JDCollectGoodsById = hostJD + "collectGoodsById";

    /**
     *获取京东取消收藏商品
     */
    public static String JDUnCollectGoodsById = hostJD + "unCollectGoodsById";

    /**
     *获取京东URL转链
     */
    public static String JDGetGenByUrl = hostJD + "genAdvUrl";

    /**
     *获取京东宝贝推荐
     */
    public static String JDRecommendByGoodsId = hostJD + "getRecommendByGoodsId";

    /**
     *获取京东专区
     */
    public static String JDInitMain = hostJD + "initMain";


    //====================================以下是拼多多==============================================

    /**
     *获取拼多多头部分类列表
     */
    public static String PDDSortList = hostPDD + "getCatList";

    /**
     *获取拼多多头部轮播图
     */
    public static String PDDBanner = hostPDD + "getMainAdvList";

    /**
     *获取拼多多商品列表
     */
    public static String PDDGoods = hostPDD + "cat";

    /**
     *获取拼多多关键字商品查询列表
     */
    public static String PDDSearchGoods = hostPDD + "query";

    /**
     *获取拼多多订单
     */
    public static String PDDOrderList = hostPDD + "getSelfOrderList";

    /**
     *获取拼多多团队订单
     */
    public static String PDDTeamOrderList = hostPDD + "getTeamOrderList";


    /**
     *获取拼多多频道商品
     */
    public static String PDDChannelGoods = hostPDD + "channel";
    /**
     *获取拼多多主题商品
     */
    public static String PDDThemeGoods = hostPDD + "theme";

    /**
     *获取拼多多商品详情
     */
    public static String PDDByGoodsId = hostPDD+ "getByGoodsId";

    /**
     *获取拼多多转链
     */
    public static String PDDGenByGoodsId = hostPDD + "genByGoodsId";

    /**
     *获取拼多多收藏商品
     */
    public static String PDDCollectGoodsById = hostPDD + "collectGoodsById";

    /**
     *获取拼多多取消收藏商品
     */
    public static String PDDUnCollectGoodsById = hostPDD + "unCollectGoodsById";


    /**
     *获取拼多多URL转链
     */
    public static String PDDGetGenByUrl = hostPDD+ "genAdvUrl";


    /**
     *获取拼多多宝贝推荐
     */
    public static String PDDRecommendByGoodsId = hostPDD + "getRecommendByGoodsId";


    /**
     *获取拼多多专区
     */
    public static String PDDInitMain = hostPDD + "initMain";


    /**
     *获取拼多多授权状态
     */
    public static String PDDIsBindAuth = hostPDD + "isBindAuth";

    //====================================以下是唯品会==============================================

    /**
     *获取唯品会头部分类列表
     */
    public static String WPHSortList = hostWPH + "getCatList";

    /**
     *获取唯品会头部轮播图
     */
    public static String WPHBanner = hostWPH + "getMainAdvList";

    /**
     *获取唯品会商品列表
     */
    public static String WPHGoods = hostWPH + "cat";

    /**
     *获取唯品会关键字商品查询列表
     */
    public static String WPHSearchGoods = hostWPH + "query";

    /**
     *获取唯品会订单
     */
    public static String WPHOrderList = hostWPH+ "getSelfOrderList";

    /**
     *获取唯品会团队订单
     */
    public static String WPHTeamOrderList = hostWPH + "getTeamOrderList";

    /**
     *获取唯品会频道商品
     */
    public static String WPHChannelGoods = hostWPH + "channel";
    /**
     *获取唯品会主题商品
     */
    public static String WPHThemeGoods = hostWPH + "theme";

    /**
     *获取唯品会商品详情
     */
    public static String WPHByGoodsId = hostWPH+ "getByGoodsId";

    /**
     *获取唯品会转链
     */
    public static String WPHGenByGoodsId = hostWPH + "genByGoodsId";

    /**
     *获取唯品会收藏商品
     */
    public static String WPHCollectGoodsById = hostWPH + "collectGoodsById";

    /**
     *获取唯品会取消收藏商品
     */
    public static String WPHUnCollectGoodsById = hostWPH + "unCollectGoodsById";

    /**
     *获取拼多多URL转链
     */
    public static String WPHGetGenByUrl = hostWPH+ "genAdvUrl";

    /**
     *获取唯品会宝贝推荐
     */
    public static String WPHRecommendByGoodsId = hostWPH + "getRecommendByGoodsId";

    //====================================以下是苏宁易购================================================

    /**
     *获取苏宁易购头部分类列表
     */
    public static String SNSortList = hostSN + "getCatList";

    /**
     *获取苏宁易购头部轮播图
     */
    public static String SNBanner = hostSN + "getMainAdvList";

    /**
     *获取苏宁易购商品列表
     */
    public static String SNGoods = hostSN + "cat";

    /**
     *获取苏宁易购关键字商品查询列表
     */
    public static String SNSearchGoods = hostSN + "query";

    /**
     *获取苏宁易购订单
     */
    public static String SNOrderList = hostSN + "getSelfOrderList";

    /**
     *获取苏宁易购团队订单
     */
    public static String SNTeamOrderList = hostSN + "getTeamOrderList";

    /**
     *获取苏宁易购频道商品
     */
    public static String SNChannelGoods = hostSN + "channel";
    /**
     *获取苏宁易购主题商品
     */
    public static String SNThemeGoods = hostSN + "theme";

    /**
     *获取苏宁易购商品详情
     */
    public static String SNByGoodsId = hostSN + "getByGoodsId";

    /**
     *获取苏宁易购转链
     */
    public static String SNGenByGoodsId = hostSN + "genByGoodsId";

    /**
     *获取苏宁易购收藏商品
     */
    public static String SNCollectGoodsById = hostSN + "collectGoodsById";

    /**
     *获取苏宁易购取消收藏商品
     */
    public static String SNUnCollectGoodsById = hostSN + "unCollectGoodsById";

    /**
     *获取苏宁易购URL转链
     */
    public static String SNGetGenByUrl = hostSN + "genAdvUrl";

    /**
     *获取苏宁易购宝贝推荐
     */
    public static String SNRecommendByGoodsId = hostSN + "getRecommendByGoodsId";


    //====================================以下是美团==============================================

    /**
     *获取美团订单
     */
    public static String MTOrderList = hostMT+ "getSelfOrderList";

    /**
     *获取美团团队订单
     */
    public static String MTTeamOrderList = hostMT + "getTeamOrderList";



    //====================================以下是考拉海购================================================

    /**
     *获取考拉海购头部分类列表
     */
    public static String KLSortList = hostKL + "getCatList";

    /**
     *获取考拉海购头部轮播图
     */
    public static String KLBanner = hostKL + "getMainAdvList";

    /**
     *获取考拉海购商品列表
     */
    public static String KLGoods = hostKL + "cat";

    /**
     *获取考拉海购关键字商品查询列表
     */
    public static String KLSearchGoods = hostKL + "query";

    /**
     *获取考拉海购订单
     */
    public static String KLOrderList = hostKL + "getSelfOrderList";

    /**
     *获取考拉海购团队订单
     */
    public static String KLTeamOrderList = hostKL + "getTeamOrderList";

    /**
     *获取考拉海购频道商品
     */
    public static String KLChannelGoods = hostKL + "channel";
    /**
     *获取考拉海购主题商品
     */
    public static String KLThemeGoods = hostKL + "theme";

    /**
     *获取考拉海购商品详情
     */
    public static String KLByGoodsId = hostKL + "getByGoodsId";

    /**
     *获取考拉海购转链
     */
    public static String KLGenByGoodsId = hostKL + "genByGoodsId";

    /**
     *获取考拉海购收藏商品
     */
    public static String KLCollectGoodsById = hostKL + "collectGoodsById";

    /**
     *获取考拉海购取消收藏商品
     */
    public static String KLUnCollectGoodsById = hostKL + "unCollectGoodsById";

    /**
     *获取考拉海购URL转链
     */
    public static String KLGetGenByUrl = hostKL + "genAdvUrl";

    /**
     *获取考拉海购宝贝推荐
     */
    public static String KLRecommendByGoodsId = hostKL + "getRecommendByGoodsId";

    //淘宝优惠券
    public static String TaobaoCouponInit = host + "taobao/taoBaoInit";
}
