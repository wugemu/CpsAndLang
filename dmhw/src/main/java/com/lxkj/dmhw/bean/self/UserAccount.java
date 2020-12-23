package com.lxkj.dmhw.bean.self;


import java.io.Serializable;
import java.util.List;

public class UserAccount implements Serializable{

	private String nickName;//昵称
	private String realName;	// 真实姓名
	private String userName;//用户名
	private String cardNo;	// 身份证号
	private String hideCardNo;	// 隐藏身份证号
	private String credentialsUrl;	// 身份证地址
	private String headUrl;//头像url
	private boolean ifBindMobile;//是否绑定手机
	private String mobile;//手机号
	private String companyName;
	private String randomCode;
	private boolean needBindParentUser;//true-则需要跳到邀请码绑定页面 进行后面的步骤 false-跳过后面注册步骤直接登录成功
	private boolean ifHaveSetRead;//设置我的小红点 true是
	private boolean ifWxBind;//是否绑定微信 true是
	private int sex;//性别 0女1男
	private String signature;//个性签名
	private boolean ifAuth;//true已认证
	private String address;//常住地
	private boolean ifFlag;//是否默认
	private boolean ifFirst;//是否是账号实名认证
	private String id;//实名认证id
	private boolean ifTags;//是否有标签 true是
	private int ifBillVip;//会员等级 1玩客 2玩主 3金牌玩主 4 钻石玩主
	private int cardType;//1-身份证 2-护照 3-港澳通信证 4-港澳居民来往内地通信证 5-台湾居民来往内地通信证
	private String cardTypeName;
	private boolean ifRedCoupon;//优惠券是否显示小红点
	private List<MineItemBean> mineList;
	private boolean ifHavSetPwd;//是否已经设置密码
	private boolean iFHavePassword;//是否设置过密码
	private boolean ifNeedSmsCheck;//是否需要安全校验

	private int addType;//-1-授权给其他用户成为vip 1-销售积分兑换 2-内购会达标 3-培养直属钻石 4-培养直
	private String addTypeDesc;//授权类型原因描述
	private long createTime;
	private String createTimeStr;
	private long grantedUserId;//被授予人userid
	private int num;//名额数
	private String message;//查询不到可授权用户提示
	private boolean ifSuperVip;//是否是先锋玩主

	private boolean hasPreWithinBuy;//是否有往期内购;
	private boolean ifWatch;//是否已关注
	private boolean isSelf;//是否自己

	private String applyUserId;
	private int state;
	private String targetUserId;
	private long updateTime;

	private String regGiveAmount;//注册成功赠送金额
	private String regWithdrawAmount;//注册成功提现金额
	private String code;
	private String sign;
	private String connectType;//联类型 0-正常登录 1-qq 2-新浪微博 3-微信 4-支付宝

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRegGiveAmount() {
		return regGiveAmount;
	}

	public void setRegGiveAmount(String regGiveAmount) {
		this.regGiveAmount = regGiveAmount;
	}

	public String getRegWithdrawAmount() {
		return regWithdrawAmount;
	}

	public void setRegWithdrawAmount(String regWithdrawAmount) {
		this.regWithdrawAmount = regWithdrawAmount;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isHasPreWithinBuy() {
		return hasPreWithinBuy;
	}

	public void setHasPreWithinBuy(boolean hasPreWithinBuy) {
		this.hasPreWithinBuy = hasPreWithinBuy;
	}

	public boolean isIfWatch() {
		return ifWatch;
	}

	public void setIfWatch(boolean ifWatch) {
		this.ifWatch = ifWatch;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public boolean isIfSuperVip() {
		return ifSuperVip;
	}

	public void setIfSuperVip(boolean ifSuperVip) {
		this.ifSuperVip = ifSuperVip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAddType() {
		return addType;
	}

	public void setAddType(int addType) {
		this.addType = addType;
	}

	public String getAddTypeDesc() {
		return addTypeDesc;
	}

	public void setAddTypeDesc(String addTypeDesc) {
		this.addTypeDesc = addTypeDesc;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getGrantedUserId() {
		return grantedUserId;
	}

	public void setGrantedUserId(long grantedUserId) {
		this.grantedUserId = grantedUserId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isiFHavePassword() {
		return iFHavePassword;
	}

	public void setiFHavePassword(boolean iFHavePassword) {
		this.iFHavePassword = iFHavePassword;
	}

	public boolean isIfNeedSmsCheck() {
		return ifNeedSmsCheck;
	}

	public void setIfNeedSmsCheck(boolean ifNeedSmsCheck) {
		this.ifNeedSmsCheck = ifNeedSmsCheck;
	}

	public boolean isIfHavSetPwd() {
		return ifHavSetPwd;
	}

	public void setIfHavSetPwd(boolean ifHavSetPwd) {
		this.ifHavSetPwd = ifHavSetPwd;
	}

	public List<MineItemBean> getMineList() {
		return mineList;
	}

	public void setMineList(List<MineItemBean> mineList) {
		this.mineList = mineList;
	}

	public boolean isIfRedCoupon() {
		return ifRedCoupon;
	}

	public void setIfRedCoupon(boolean ifRedCoupon) {
		this.ifRedCoupon = ifRedCoupon;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getIfBillVip() {
		return ifBillVip;
	}

	public void setIfBillVip(int ifBillVip) {
		this.ifBillVip = ifBillVip;
	}

	public boolean isIfTags() {
		return ifTags;
	}

	public void setIfTags(boolean ifTags) {
		this.ifTags = ifTags;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIfFirst() {
		return ifFirst;
	}

	public void setIfFirst(boolean ifFirst) {
		this.ifFirst = ifFirst;
	}

	public boolean isIfFlag() {
		return ifFlag;
	}

	public void setIfFlag(boolean ifFlag) {
		this.ifFlag = ifFlag;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isIfAuth() {
		return ifAuth;
	}

	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	public boolean isIfHaveSetRead() {
		return ifHaveSetRead;
	}

	public void setIfHaveSetRead(boolean ifHaveSetRead) {
		this.ifHaveSetRead = ifHaveSetRead;
	}

	public boolean isIfWxBind() {
		return ifWxBind;
	}

	public void setIfWxBind(boolean ifWxBind) {
		this.ifWxBind = ifWxBind;
	}

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isNeedBindParentUser() {
		return needBindParentUser;
	}

	public void setNeedBindParentUser(boolean needBindParentUser) {
		this.needBindParentUser = needBindParentUser;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public UserAccount() {
	}

	public boolean isIfBindMobile() {
		return ifBindMobile;
	}

	public void setIfBindMobile(boolean ifBindMobile) {
		this.ifBindMobile = ifBindMobile;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getHideCardNo() {
		return hideCardNo;
	}

	public void setHideCardNo(String hideCardNo) {
		this.hideCardNo = hideCardNo;
	}

	public String getCredentialsUrl() {
		return credentialsUrl;
	}

	public void setCredentialsUrl(String credentialsUrl) {
		this.credentialsUrl = credentialsUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}