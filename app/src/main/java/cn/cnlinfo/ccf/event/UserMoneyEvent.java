package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class UserMoneyEvent {
    /**
     * CCF : 49412.000001
     * RegisterIntegral : 9000.00
     * ConsumeIntegral : 0.00
     * CircleTicketScore : 0.00
     * Price : 0.100
     */

    private String CCF;
    private String RegisterIntegral;
    private String ConsumeIntegral;
    private String CircleTicketScore;
    private String Price;
    /**
     * ProductScore : 0.00
     */

    private String ProductScore;

    public String getCCF() {
        return CCF;
    }

    public void setCCF(String CCF) {
        this.CCF = CCF;
    }

    public String getRegisterIntegral() {
        return RegisterIntegral;
    }

    public void setRegisterIntegral(String RegisterIntegral) {
        this.RegisterIntegral = RegisterIntegral;
    }

    public String getConsumeIntegral() {
        return ConsumeIntegral;
    }

    public void setConsumeIntegral(String ConsumeIntegral) {
        this.ConsumeIntegral = ConsumeIntegral;
    }

    public String getCircleTicketScore() {
        return CircleTicketScore;
    }

    public void setCircleTicketScore(String CircleTicketScore) {
        this.CircleTicketScore = CircleTicketScore;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getProductScore() {
        return ProductScore;
    }

    public void setProductScore(String ProductScore) {
        this.ProductScore = ProductScore;
    }
}
