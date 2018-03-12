package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class TransferRecordEntity {
    /**
     * SendCode : 1001
     * ReceiveCode : 1002
     * Num : 1.00
     * DateTime : 2017/12/26 10:39:32
     * Status : 成功
     * Type : 碳控因子
     */

    private String SendCode;
    private String ReceiveCode;
    private String Num;
    private String DateTime;
    private String Status;
    private String Type;

    public TransferRecordEntity(String sendCode, String receiveCode, String num, String dateTime, String status, String type) {
        SendCode = sendCode;
        ReceiveCode = receiveCode;
        Num = num;
        DateTime = dateTime;
        Status = status;
        Type = type;
    }

    public TransferRecordEntity() {
    }

    @Override
    public String toString() {
        return "TransferRecordEntity{" +
                "SendCode='" + SendCode + '\'' +
                ", ReceiveCode='" + ReceiveCode + '\'' +
                ", Num='" + Num + '\'' +
                ", DateTime='" + DateTime + '\'' +
                ", Status='" + Status + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    public String getSendCode() {
        return SendCode;
    }

    public void setSendCode(String SendCode) {
        this.SendCode = SendCode;
    }

    public String getReceiveCode() {
        return ReceiveCode;
    }

    public void setReceiveCode(String ReceiveCode) {
        this.ReceiveCode = ReceiveCode;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String Num) {
        this.Num = Num;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * private string m_sendCode;				//转出账号
     private string m_receiveCode;			//转入账号
     private string m_num;					//数量
     private string m_dateTime;				//时间
     private string m_status;				//状态
     private string m_type;					//类型
     */


}
