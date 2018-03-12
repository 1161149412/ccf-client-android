package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Userstep {
    /**
     * DayStep : 0  步数
     * Ranking : 31 跑步排名
     * Praise : 0  获得的赞
     * Carbonnum : 0 待激活碳控因子量
     * F : 0 基础贡献值
     * E : 0.880700432441527  贡献值
     */


    private String DayStep;
    private String Ranking;
    private String Praise;
    private String Carbonnum;
    private String F;
    private String E;

    public String getDayStep() {
        return DayStep;
    }

    public void setDayStep(String DayStep) {
        this.DayStep = DayStep;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String Ranking) {
        this.Ranking = Ranking;
    }

    public String getPraise() {
        return Praise;
    }

    public void setPraise(String Praise) {
        this.Praise = Praise;
    }

    public String getCarbonnum() {
        return Carbonnum;
    }

    public void setCarbonnum(String Carbonnum) {
        this.Carbonnum = Carbonnum;
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


}
