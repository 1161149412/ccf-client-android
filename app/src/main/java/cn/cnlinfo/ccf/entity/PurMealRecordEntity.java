package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class PurMealRecordEntity {

    /**
     * Usercode : 1001
     * SetMealID :
     * Msg : 购买起航套餐A,1套,消耗注册积分150,消耗消费积分0
     * CreateTime : 2017/12/28 10:28:16
     * Status : 配送中
     * CCFPrice : 10.280
     */

    private String Usercode;
    private String SetMealID;
    private String Msg;
    private String CreateTime;
    private String Status;
    private String CCFPrice;

    public String getUsercode() {
        return Usercode;
    }

    public void setUsercode(String Usercode) {
        this.Usercode = Usercode;
    }

    public String getSetMealID() {
        return SetMealID;
    }

    public void setSetMealID(String SetMealID) {
        this.SetMealID = SetMealID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCCFPrice() {
        return CCFPrice;
    }

    public void setCCFPrice(String CCFPrice) {
        this.CCFPrice = CCFPrice;
    }
}
