package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class UserIntegralRecordEntity {

    /**
     * UserID : 1001
     * Type : 注册积分
     * OldValue : 40000000.000000
     * Delta : -10000.000000
     * Msg : 注册代理
     * CreateTime : 2018/1/29 15:06:39
     */

    private String UserID;
    private String Type;
    private String OldValue;
    private String Delta;
    private String Msg;
    private String CreateTime;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getOldValue() {
        return OldValue;
    }

    public void setOldValue(String OldValue) {
        this.OldValue = OldValue;
    }

    public String getDelta() {
        return Delta;
    }

    public void setDelta(String Delta) {
        this.Delta = Delta;
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
}
