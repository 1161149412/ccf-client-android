package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class ItemHangSellAndBuyEntity {


    /**
     * TranType : 挂买
     * UserID : 1001
     * AuctionRoomID : 35
     * Status : 挂买中
     * Msg : 挂买碳控因子3.00个
     * CreateTime : 2018/1/4 12:34:07
     */

    private String TranType;
    private String UserID;
    private String AuctionRoomID;
    private String Status;
    private String Msg;
    private String CreateTime;
    /**
     * ID : 6
     */

    private String ID;

    public String getTranType() {
        return TranType;
    }

    public void setTranType(String TranType) {
        this.TranType = TranType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getAuctionRoomID() {
        return AuctionRoomID;
    }

    public void setAuctionRoomID(String AuctionRoomID) {
        this.AuctionRoomID = AuctionRoomID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
