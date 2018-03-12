package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

/**
 * 购买碳控因子和出售碳控因子的详细信息
 */
public class JobberInfoCCFEntity {

    /**
     * UserName : 1002
     * BusinessLev : 普通用户
     * CCF : 10.00
     * Price : 0.102
     * TranType : 挂买
     * ID : 37
     * AliPay : 暂无
     * WebChat : 暂无
     * BankCode : 暂无
     */

    private String UserName;
    private String BusinessLev;
    private String CCF;
    private String Price;
    private String TranType;
    private String ID;
    private String AliPay;
    private String WebChat;
    private String BankCode;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getBusinessLev() {
        return BusinessLev;
    }

    public void setBusinessLev(String BusinessLev) {
        this.BusinessLev = BusinessLev;
    }

    public String getCCF() {
        return CCF;
    }

    public void setCCF(String CCF) {
        this.CCF = CCF;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getTranType() {
        return TranType;
    }

    public void setTranType(String TranType) {
        this.TranType = TranType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAliPay() {
        return AliPay;
    }

    public void setAliPay(String AliPay) {
        this.AliPay = AliPay;
    }

    public String getWebChat() {
        return WebChat;
    }

    public void setWebChat(String WebChat) {
        this.WebChat = WebChat;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String BankCode) {
        this.BankCode = BankCode;
    }
}
