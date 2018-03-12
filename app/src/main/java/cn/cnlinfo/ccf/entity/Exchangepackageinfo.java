package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Exchangepackageinfo {
    /**
     * CCF : 799676.2  碳控因子
     * CircleTicket : 997098  循环卷
     * Circle : 6 循环包
     * TotalPack : 3000  系统当前总循环包数量
     * PackTime : 1024  包生成时间
     * Haschange : 15  已兑换循环包数量
     * Residue : 2985 剩余可兑换
     * UpperLimit : 10  上限
     * Convertible : 4  剩余可兑换
     */


    private double CCF;
    private double CircleTicket;
    private int Circle;
    private int TotalPack;
    private int PackTime;
    private int Haschange;
    private int Residue;
    private int UpperLimit;
    private int Convertible;
    /**
     * DCCF : 14
     */

    private int DCCF;

    public Exchangepackageinfo(double CCF, double circleTicket, int circle, int totalPack, int packTime, int haschange, int residue, int upperLimit, int convertible) {
        this.CCF = CCF;
        CircleTicket = circleTicket;
        Circle = circle;
        TotalPack = totalPack;
        PackTime = packTime;
        Haschange = haschange;
        Residue = residue;
        UpperLimit = upperLimit;
        Convertible = convertible;
    }

    @Override
    public String toString() {
        return "Exchangepackageinfo{" +
                "CCF=" + CCF +
                ", CircleTicket=" + CircleTicket +
                ", Circle=" + Circle +
                ", TotalPack=" + TotalPack +
                ", PackTime=" + PackTime +
                ", Haschange=" + Haschange +
                ", Residue=" + Residue +
                ", UpperLimit=" + UpperLimit +
                ", Convertible=" + Convertible +
                '}';
    }

    public Exchangepackageinfo() {
    }

    public double getCCF() {
        return CCF;
    }

    public void setCCF(double CCF) {
        this.CCF = CCF;
    }

    public double getCircleTicket() {
        return CircleTicket;
    }

    public void setCircleTicket(double CircleTicket) {
        this.CircleTicket = CircleTicket;
    }

    public int getCircle() {
        return Circle;
    }

    public void setCircle(int Circle) {
        this.Circle = Circle;
    }

    public int getTotalPack() {
        return TotalPack;
    }

    public void setTotalPack(int TotalPack) {
        this.TotalPack = TotalPack;
    }

    public int getPackTime() {
        return PackTime;
    }

    public void setPackTime(int PackTime) {
        this.PackTime = PackTime;
    }

    public int getHaschange() {
        return Haschange;
    }

    public void setHaschange(int Haschange) {
        this.Haschange = Haschange;
    }

    public int getResidue() {
        return Residue;
    }

    public void setResidue(int Residue) {
        this.Residue = Residue;
    }

    public int getUpperLimit() {
        return UpperLimit;
    }

    public void setUpperLimit(int UpperLimit) {
        this.UpperLimit = UpperLimit;
    }

    public int getConvertible() {
        return Convertible;
    }

    public void setConvertible(int Convertible) {
        this.Convertible = Convertible;
    }

    public int getDCCF() {
        return DCCF;
    }

    public void setDCCF(int DCCF) {
        this.DCCF = DCCF;
    }
}
