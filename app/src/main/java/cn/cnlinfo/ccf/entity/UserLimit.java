package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class UserLimit {
    /**
     * {"CCf":-1,"Circle":10,"CarbonNum":150}
     */
    //碳控因子
    private String CCF;
    //循环包数
    private String Circle;
    //待激活碳控因子
    private String CarbonNum;

    public UserLimit(String CCF, String circle, String carbonNum) {
        this.CCF = CCF;
        Circle = circle;
        CarbonNum = carbonNum;
    }

    public UserLimit() {
    }

    public String getCCF() {
        return CCF;
    }

    public void setCCF(String CCF) {
        this.CCF = CCF;
    }

    public String getCircle() {
        return Circle;
    }

    public void setCircle(String circle) {
        Circle = circle;
    }

    public String getCarbonNum() {
        return CarbonNum;
    }

    public void setCarbonNum(String carbonNum) {
        CarbonNum = carbonNum;
    }

    @Override
    public String toString() {
        return "UserLimit{" +
                "CCF='" + CCF + '\'' +
                ", Circle='" + Circle + '\'' +
                ", CarbonNum='" + CarbonNum + '\'' +
                '}';
    }
}
