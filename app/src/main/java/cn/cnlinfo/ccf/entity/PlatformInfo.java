package cn.cnlinfo.ccf.entity;

/**
 * Created by JP on 2017/10/30 0030.
 *private int m_iD;								//
 private decimal m_totalCCF;						//碳控因子总量
 private int m_totalPack;						//系统当前总循环包数量
 private decimal m_currentPrice;					//当前价格(美刀)
 private decimal m_initActiveCCF;				//初始激活总量
 private decimal m_activeCCF;					//已激活碳控因子总量
 private decimal m_totalInertiaCCF;				//矿池(待激活碳控因子总量)
 private int m_totalAccount;						//当前账户数
 private decimal m_cCScore;						//碳控积分
 private decimal m_current_u;					//当前难度系数
 private decimal m_activeConsumeScore;			//可用消费积分
 private decimal m_inertiaConsumeScore;			//待释放消费积分
 private int m_circleTicket;						//循环卷
 private decimal m_inertiaCCScore;				//待释放循环积分
 private int m_provinceProxy;					//省代理数量
 private int m_cityProxy;						//市代理数量
 private int m_countyProxy;						//县代理数量
 private int m_packTime;							//包生成时间
 private int m_haschange;						//已兑换循环包数量
 private DateTime m_sysRunDate;					//系统开始运行日期
 private int m_runingDays;						//运行工作日
 private int m_curTrade;							//历史CCF成交量之和

 */

public class PlatformInfo {
    /**
     * ID : 1
     * TotalCCF : 120000000
     * TotalPack : 531
     * CurrentPrice : 0.1
     * InitActiveCCF : 960000
     * ActiveCCF : 959780.3
     * TotalInertiaCCF : 1.190399767E8
     * TotalAccount : 8
     * CCScore : 0
     * Current_u : 1.333090120655E7
     * ActiveConsumeScore : 0
     * InertiaConsumeScore : 0
     * CircleTicket : 0
     * InertiaCCScore : 0
     * ProvinceProxy : 0
     * CityProxy : 0
     * CountyProxy : 0
     * PackTime : 1024
     * Haschange : 6
     * SysRunDate : /Date(1517414400000+0800)/
     * RuningDays : 3
     * CurTrade : 0
     * UserCCF : 9
     * UserDCCF : 9
     */

    private String ID;
    private String TotalCCF;
    private String TotalPack;
    private String CurrentPrice;
    private String InitActiveCCF;
    private String ActiveCCF;
    private String TotalInertiaCCF;
    private String TotalAccount;
    private String CCScore;
    private String Current_u;
    private String ActiveConsumeScore;
    private String InertiaConsumeScore;
    private String CircleTicket;
    private String InertiaCCScore;
    private String ProvinceProxy;
    private String CityProxy;
    private String CountyProxy;
    private String PackTime;
    private String Haschange;
    private String SysRunDate;
    private String RuningDays;
    private String CurTrade;
    private String UserCCF;
    private String UserDCCF;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTotalCCF() {
        return TotalCCF;
    }

    public void setTotalCCF(String TotalCCF) {
        this.TotalCCF = TotalCCF;
    }

    public String getTotalPack() {
        return TotalPack;
    }

    public void setTotalPack(String TotalPack) {
        this.TotalPack = TotalPack;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String CurrentPrice) {
        this.CurrentPrice = CurrentPrice;
    }

    public String getInitActiveCCF() {
        return InitActiveCCF;
    }

    public void setInitActiveCCF(String InitActiveCCF) {
        this.InitActiveCCF = InitActiveCCF;
    }

    public String getActiveCCF() {
        return ActiveCCF;
    }

    public void setActiveCCF(String ActiveCCF) {
        this.ActiveCCF = ActiveCCF;
    }

    public String getTotalInertiaCCF() {
        return TotalInertiaCCF;
    }

    public void setTotalInertiaCCF(String TotalInertiaCCF) {
        this.TotalInertiaCCF = TotalInertiaCCF;
    }

    public String getTotalAccount() {
        return TotalAccount;
    }

    public void setTotalAccount(String TotalAccount) {
        this.TotalAccount = TotalAccount;
    }

    public String getCCScore() {
        return CCScore;
    }

    public void setCCScore(String CCScore) {
        this.CCScore = CCScore;
    }

    public String getCurrent_u() {
        return Current_u;
    }

    public void setCurrent_u(String Current_u) {
        this.Current_u = Current_u;
    }

    public String getActiveConsumeScore() {
        return ActiveConsumeScore;
    }

    public void setActiveConsumeScore(String ActiveConsumeScore) {
        this.ActiveConsumeScore = ActiveConsumeScore;
    }

    public String getInertiaConsumeScore() {
        return InertiaConsumeScore;
    }

    public void setInertiaConsumeScore(String InertiaConsumeScore) {
        this.InertiaConsumeScore = InertiaConsumeScore;
    }

    public String getCircleTicket() {
        return CircleTicket;
    }

    public void setCircleTicket(String CircleTicket) {
        this.CircleTicket = CircleTicket;
    }

    public String getInertiaCCScore() {
        return InertiaCCScore;
    }

    public void setInertiaCCScore(String InertiaCCScore) {
        this.InertiaCCScore = InertiaCCScore;
    }

    public String getProvinceProxy() {
        return ProvinceProxy;
    }

    public void setProvinceProxy(String ProvinceProxy) {
        this.ProvinceProxy = ProvinceProxy;
    }

    public String getCityProxy() {
        return CityProxy;
    }

    public void setCityProxy(String CityProxy) {
        this.CityProxy = CityProxy;
    }

    public String getCountyProxy() {
        return CountyProxy;
    }

    public void setCountyProxy(String CountyProxy) {
        this.CountyProxy = CountyProxy;
    }

    public String getPackTime() {
        return PackTime;
    }

    public void setPackTime(String PackTime) {
        this.PackTime = PackTime;
    }

    public String getHaschange() {
        return Haschange;
    }

    public void setHaschange(String Haschange) {
        this.Haschange = Haschange;
    }

    public String getSysRunDate() {
        return SysRunDate;
    }

    public void setSysRunDate(String SysRunDate) {
        this.SysRunDate = SysRunDate;
    }

    public String getRuningDays() {
        return RuningDays;
    }

    public void setRuningDays(String RuningDays) {
        this.RuningDays = RuningDays;
    }

    public String getCurTrade() {
        return CurTrade;
    }

    public void setCurTrade(String CurTrade) {
        this.CurTrade = CurTrade;
    }

    public String getUserCCF() {
        return UserCCF;
    }

    public void setUserCCF(String UserCCF) {
        this.UserCCF = UserCCF;
    }

    public String getUserDCCF() {
        return UserDCCF;
    }

    public void setUserDCCF(String UserDCCF) {
        this.UserDCCF = UserDCCF;
    }

}
