package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class UserDetail {


    /**
     * Name :
     * UserCode : 1001
     * InvitationCode :
     * NickName : 1001
     * Utype : 普通用户
     * DirectID :
     * ServantID :
     * Mobile : 15982013088
     * IDtype : 身份证
     * IDcard :
     * BankName :
     * BankAddress :
     * BankCode :
     * AliPay :
     * AliPayName :
     * WebChat :
     * WebChatName :
     */

    private String Name;
    private String UserCode;
    private String InvitationCode;
    private String NickName;
    private String Utype;
    private String DirectID;
    private String ServantID;
    private String Mobile;
    private String IDtype;
    private String IDcard;
    private String BankName;
    private String BankAddress;
    private String BankCode;
    private String AliPay;
    private String AliPayName;
    private String WebChat;
    private String WebChatName;
    /**
     * UserType :
     */

    private String UserType;

    public UserDetail() {
    }

    public UserDetail(String name, String userCode, String invitationCode, String nickName, String utype, String directID, String servantID, String mobile, String IDtype, String IDcard, String bankName, String bankAddress, String bankCode, String aliPay, String aliPayName, String webChat, String webChatName) {
        Name = name;
        UserCode = userCode;
        InvitationCode = invitationCode;
        NickName = nickName;
        Utype = utype;
        DirectID = directID;
        ServantID = servantID;
        Mobile = mobile;
        this.IDtype = IDtype;
        this.IDcard = IDcard;
        BankName = bankName;
        BankAddress = bankAddress;
        BankCode = bankCode;
        AliPay = aliPay;
        AliPayName = aliPayName;
        WebChat = webChat;
        WebChatName = webChatName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String InvitationCode) {
        this.InvitationCode = InvitationCode;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getUtype() {
        return Utype;
    }

    public void setUtype(String Utype) {
        this.Utype = Utype;
    }

    public String getDirectID() {
        return DirectID;
    }

    public void setDirectID(String DirectID) {
        this.DirectID = DirectID;
    }

    public String getServantID() {
        return ServantID;
    }

    public void setServantID(String ServantID) {
        this.ServantID = ServantID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getIDtype() {
        return IDtype;
    }

    public void setIDtype(String IDtype) {
        this.IDtype = IDtype;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getBankAddress() {
        return BankAddress;
    }

    public void setBankAddress(String BankAddress) {
        this.BankAddress = BankAddress;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String BankCode) {
        this.BankCode = BankCode;
    }

    public String getAliPay() {
        return AliPay;
    }

    public void setAliPay(String AliPay) {
        this.AliPay = AliPay;
    }

    public String getAliPayName() {
        return AliPayName;
    }

    public void setAliPayName(String AliPayName) {
        this.AliPayName = AliPayName;
    }

    public String getWebChat() {
        return WebChat;
    }

    public void setWebChat(String WebChat) {
        this.WebChat = WebChat;
    }

    public String getWebChatName() {
        return WebChatName;
    }

    public void setWebChatName(String WebChatName) {
        this.WebChatName = WebChatName;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }
}
