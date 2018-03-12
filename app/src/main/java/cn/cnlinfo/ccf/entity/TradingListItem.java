package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class TradingListItem {

    /**
     * TranType : 挂卖  状态
     * SellerID : 1001  挂卖id
     * BusinessLev : 普通用户  挂卖用户的等级
     * CCF : 10.00  挂卖碳控因子的数量
     * Price : 0.102  挂卖当前的价格
     * Status : 可交易  挂卖碳控因子的交易状态
     * CreateTime : 2018/1/4 10:35:04    该单子创建的时间
     * PurchaserID : 暂无  购买该碳控因子的用户的id
     */

    private String TranType;
    private String SellerID;
    private String BusinessLev;
    private String CCF;
    private String Price;
    private String Status;
    private String CreateTime;
    private String PurchaserID;
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTranType() {
        return TranType;
    }

    public void setTranType(String TranType) {
        this.TranType = TranType;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String SellerID) {
        this.SellerID = SellerID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getPurchaserID() {
        return PurchaserID;
    }

    public void setPurchaserID(String PurchaserID) {
        this.PurchaserID = PurchaserID;
    }
}
