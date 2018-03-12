package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class CyclePackRecordEntity {


    /**
     * UserCode : 1001
     * PackID : 79
     * CreateTime : 2017/12/21 11:06:08
     * IsSuccess : 兑换成功
     * Status : 未解压
     * Msg : 消耗碳控因子:10 循环卷:1 平台手续费:0.40
     */

    private String UserCode;
    private String PackID;
    private String CreateTime;
    private String IsSuccess;
    private String Status;
    private String Msg;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getPackID() {
        return PackID;
    }

    public void setPackID(String PackID) {
        this.PackID = PackID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String IsSuccess) {
        this.IsSuccess = IsSuccess;
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
}
