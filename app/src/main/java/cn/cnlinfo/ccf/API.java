package cn.cnlinfo.ccf;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class API {

    /**
     * 测试get方法地址
     * https://api.douban.com/v2/movie/top250?start=0&count=10
     */

    /**
     * 测试post方法地址
     * http://www.ccfcc.cc/RegUser.asmx/Login
     * username
     * password
     */

    /**
     * 登录
     */
    public static final String CCFLOGIN = "Login";
    /**
     * 商城的登录地址
     */
    public static final String CCMALLLOGIN = "index.aspx?param=%s&cmd=%s";
    /**
     * 注册
     */
    public static final String CCFREGISTER = "Register";
    /**
     * 注册成员
     */
    public static final String CCFREGISTERS = "Registers";

    /**
     * 设置密保
     */
    public static final String CCFSETENCRYPTED = "SetSafeQuestion";
    /**
     * 验证密保
     */
    public static final String CCFVERFYENCRYPTED = "SecurityQuestionIsTrue";

    /**
     * 找回密码
     */
    public static final String CCFRESETPASSWORD = "ChangePwdByQuestion";
    /**
     * 更新密码
     */
    public static final String UPDATEPASSWORD = "ChangePassword";
    /**
     * 得到当前用户的贡献图
     */
    public static final String USERCONTRIBUTEMAP = "GetAtlasByUserID ";
    /**
     * 得到用户的子节点数据
     */
    public static final String USERSHARELIST = "GetShareList";

    /**
     * 用户兑换循环包
     */
    public static final String CONVERSIONCYCLEPACKAGE = "ExchangePackage";

    /**
     *得到跑步排名
     */
    public static final String GETRUNNINGRANK = "GetTop10UserS";

    /**
     * 得到我的参数
     */
    public static final String GETMYPARAMETER = "GetMyCanshu";
    /**
     * 买家在拍卖行中点击购买
     */

    public static final String AUCTIONHOUSE = "BuyerClickToBuy";

    /**
     * 用户挂买操作(不扣手续费)
     */

    public static final String SAILOPERATION = "HangBuyOperation";

    /**
     * PersonalAuction 卖家挂卖到拍卖行(新)
     */

    public static final String SELLHANGTOAUCTIONHOUSE = "PersonalAuction";

    /**
     * SP_UserLargess 向他人转碳控因子、循环包、循环卷
     */
    public static final String EXCHANGECCF = "SP_UserLargess";

    /**
     * BuyerClickToBuy 买家在拍卖行中点击购买
     */
    public static final String CLICKBUYCCF = "BuyerClickToBuy";

    /**
     * SellerClickToSale 卖家在拍卖行中点击出售
     */
    public static final String CLICKSELL = "SellerClickToSale";

    /**
     * SellerSendCCF 订单产生后,卖家手动确认打款金额并拨币
     */
    public static final String SELLERSENDCCF = "SellerSendCCF";

    /**
     * SellersComplainBuyer 卖家投诉买家
     */

    public static final String SELLERCOMPLAINSBUYER = "SellersComplainBuyer";

    /**
     *获取套餐列表
     */

    public static final String GETPACKAGELIST = "GetMealList";

    /**
     *获取购买ccf页面的详细信息
     */

    public static final String GETJOBBERINFO = "GetJobberInfo";

    /**
     * 上传步数
     */
    public static final String UPLOADSTEP = "UpStep";
    /**
     * 对内外互转
     */
    public static final String USERTRANSFER = "UserTransfer";
    /**
     *获取新闻公告列表
     */

    public static final String GETNEWSLIST = "GetNewlist";
    /**
     * 获取平台信息
     */
    public static final String GETPLATFORMINFO = "Getplatforminfo";
    /**
     * 获取个人信息
     */
    public static final String GETPERSONINFO = "GetUserInfo";
    /**
     * 获取个人信息
     */
    public static final String GETUSERINFO = "GetUserinfo";
    /**
     * 获取兑换循环包参数
     */

    public static final String GETCIRCLE = "GetCircle";
    /**
     * 用户购买套餐
     */

    public static final String PURCHASEMEAL = "UpgradeToUserStar";

    /**
     * 发送短信验证码
     * codeid:   3143331  密码找回
     3126312 注册
     */
    public static final String SENDCODE = "SendCode";

    /**
     * 电话号码短信验证找回密码
     */
    public static final String RETRIEVEPASSWORD = "RetrievePassword";

    /**
     * 购买注册积分
     */
    public static final String PURCHASEREGISTERPOINTS = "UserBuyCredits";
    /**
     * 上传图片
     */
    public static final String UPLOADIMAGE = "uploadingImg";

    /**
     * 内外转账记录
     *
     * 类型type 1 对外转账,2内部互转
     */

    public static final String INOUTTRANSFERRECORD = "GetUserLargess";

    /**
     * 获取用户循环包兑换记录
     */
    public static final String CONVERSIONCYCLEPACK = "GetBuyPackRecord";

    /**
     * 获取用户购买套餐记录
     */

    public static final String PURCHASEMEALRECORD = "GetSetMealRecord";

    /**
     * 获取步行记录
     */
    public static final String STEPRECORD = "GetStepDayRecord";
    /**
     * 解压循环包
     */
    public static final String EXTEACTCYCLEPACK = "ExtractCircle";
    /**
     * 用户挂卖
     */
    public static final String HANGSELL = "PersonalAuction";
    /**
     * 用户挂买
     */
    public static final String HANGBUY = "HangBuyOperation";
    /**
     * 交易列表
     */
    public static final String TRADINGLIST = "GetJobberList";
    /**
     * 挂卖/挂买记录
     */
    public static final String HANGBYSELLANDBUY = "GetHangBuyRecord";
    /**
     * 订单记录
     */
    public static final String ORDERLISTRECORD = "GetAuctionOrderList";
    /**
     * ccf价格浮动列表地址
     */
    public static final String PRICELIST = "GetPriceList";
    /**
     * 认证循环包
     */
    public static final String APPROVECYCLEPACK = "ManualAuthenticatLoopPack";
    /**
     * 获取设置个人信息处的基本个人信息
     */
    public static final String GETSETPERSONINFO = "GetPersonalinfo";
    /**
     * 更新个人基本信息
     */
    public static final String UPDATEPERSIONINFO = "UpdataUserinfo";
    /**
     * 用户留言
     */
    public static final String USERLEAVEMESSAGE = "UserLeave";
    /**
     * 用户积分
     */
    public static final String GETUSERINTEGAL = "GetUserMoney";
    /**
     * 升级成为代理
     */
    public static final String UPDATETOAGENCY = "UpdateToAgent";
    /**
     * 撤销挂卖挂买
     */
    public static final String CANCELHANGBUYANDSELL = "UserRevocation";
    /**
     * 获取积分记录
     */
    public static final String GAININTEGRALRECORD = "GetScoreRecordList";
    /**
     * 升级成为交易商
     */
    public static final String UPGRADETOBETRADER = "UpdateToBusiness";
    /**
     * 获取用户当前的步数
     */
    public static final String GETCURRENTSTEP = "getUserCurStep";

    /**
     * 获取用户的留言记录
     */
    public static final String USERLEAVEMESSAGERECORD = "GetsystemMsgList";
}
