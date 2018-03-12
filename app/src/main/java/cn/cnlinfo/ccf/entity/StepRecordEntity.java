package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class StepRecordEntity {


    /**
     * UserCode : 1001
     * CCF : 10.000000
     * CarbonIntegral : 2.00
     * Step : 3000
     * F : 0.070
     * E : 2.000
     * Msg : 碳控因子增加10.000000个,碳控积分增加2.00
     * Date : 2017-12-27
     */

    private String UserCode;
    private String CCF;
    private String CarbonIntegral;
    private String Step;
    private String F;
    private String E;
    private String Msg;
    private String Date;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getCCF() {
        return CCF;
    }

    public void setCCF(String CCF) {
        this.CCF = CCF;
    }

    public String getCarbonIntegral() {
        return CarbonIntegral;
    }

    public void setCarbonIntegral(String CarbonIntegral) {
        this.CarbonIntegral = CarbonIntegral;
    }

    public String getStep() {
        return Step;
    }

    public void setStep(String Step) {
        this.Step = Step;
    }

    public String getF() {
        return F;
    }

    public void setF(String F) {
        this.F = F;
    }

    public String getE() {
        return E;
    }

    public void setE(String E) {
        this.E = E;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
