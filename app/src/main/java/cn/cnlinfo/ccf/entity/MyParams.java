package cn.cnlinfo.ccf.entity;

/**
 * Created by JP on 2017/12/21 0021.
 */

public class MyParams {

    /**
     * CCF : 799005.100000
     * Circle : 7
     * CarbonNum : 0.000000
     * CircleTicket : 997034
     * P : 10.278
     * CircleTicketScore : 100.00
     * ConsumeIntegral : 270.00
     * ReleaseConsume : 0.00
     * CarbonIntegral : 0.00
     * YesterdayE :
     * TotalStep : 0
     * TodayStep : 9
     * TotalMealWeight : 36
     * NowE : 0.800636756765025
     * F : 0
     * LimitCCF : -1
     * AuctionNum : 0
     * Avgdifficulty : 36.852344890874
     *
     * private string cCF;						//碳控因子
     private string circle;					//循环包
     private string carbonNum;					//待激活碳控因子
     private string circleTicket;				//循环卷
     private string p;							//实时价格P
     private string circleTicketScore;			//待释放循环卷积分
     private string consumeIntegral;			//消费积分
     private string releaseConsume;			//待释放消费积分
     private string carbonIntegral;			//碳控积分
     private string yesterdayE;				//昨天贡献值
     private string nowE;						//当前贡献值
     private string f;							//基础贡献率
     private string totalStep;					//累计步数
     private string todayStep;					//今日步数
     private string limitCCF;					//限制碳控因子量
     private string auctionNum;				//挂卖阀值
     private string avgdifficulty;				//平均难度系数
     private string totalMealWeight;			//累计购买套餐

     */

    private String CCF;
    private String Circle;
    private String CarbonNum;
    private String CircleTicket;
    private String P;
    private String CircleTicketScore;
    private String ConsumeIntegral;
    private String ReleaseConsume;
    private String CarbonIntegral;
    private String YesterdayE;
    private String TotalStep;
    private String TodayStep;
    private String TotalMealWeight;
    private String NowE;
    private String F;
    private String LimitCCF;
    private String AuctionNum;
    private String Avgdifficulty;
    /**
     * LimitCircle : 1
     * LimitCarbonNum : 6000
     * LimitE : 50
     */

    private String LimitCircle;//限持循环包
    private String LimitCarbonNum;//限持碳控因子
    private String LimitE;//贡献值封顶


    public MyParams() {
    }

    public MyParams(String CCF, String circle, String carbonNum, String circleTicket, String p, String circleTicketScore, String consumeIntegral, String releaseConsume, String carbonIntegral, String yesterdayE, String totalStep, String todayStep, String totalMealWeight, String nowE, String f, String limitCCF, String auctionNum, String avgdifficulty) {
        this.CCF = CCF;
        Circle = circle;
        CarbonNum = carbonNum;
        CircleTicket = circleTicket;
        P = p;
        CircleTicketScore = circleTicketScore;
        ConsumeIntegral = consumeIntegral;
        ReleaseConsume = releaseConsume;
        CarbonIntegral = carbonIntegral;
        YesterdayE = yesterdayE;
        TotalStep = totalStep;
        TodayStep = todayStep;
        TotalMealWeight = totalMealWeight;
        NowE = nowE;
        F = f;
        LimitCCF = limitCCF;
        AuctionNum = auctionNum;
        Avgdifficulty = avgdifficulty;
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

    public void setCircle(String Circle) {
        this.Circle = Circle;
    }

    public String getCarbonNum() {
        return CarbonNum;
    }

    public void setCarbonNum(String CarbonNum) {
        this.CarbonNum = CarbonNum;
    }

    public String getCircleTicket() {
        return CircleTicket;
    }

    public void setCircleTicket(String CircleTicket) {
        this.CircleTicket = CircleTicket;
    }

    public String getP() {
        return P;
    }

    public void setP(String P) {
        this.P = P;
    }

    public String getCircleTicketScore() {
        return CircleTicketScore;
    }

    public void setCircleTicketScore(String CircleTicketScore) {
        this.CircleTicketScore = CircleTicketScore;
    }

    public String getConsumeIntegral() {
        return ConsumeIntegral;
    }

    public void setConsumeIntegral(String ConsumeIntegral) {
        this.ConsumeIntegral = ConsumeIntegral;
    }

    public String getReleaseConsume() {
        return ReleaseConsume;
    }

    public void setReleaseConsume(String ReleaseConsume) {
        this.ReleaseConsume = ReleaseConsume;
    }

    public String getCarbonIntegral() {
        return CarbonIntegral;
    }

    public void setCarbonIntegral(String CarbonIntegral) {
        this.CarbonIntegral = CarbonIntegral;
    }

    public String getYesterdayE() {
        return YesterdayE;
    }

    public void setYesterdayE(String YesterdayE) {
        this.YesterdayE = YesterdayE;
    }

    public String getTotalStep() {
        return TotalStep;
    }

    public void setTotalStep(String TotalStep) {
        this.TotalStep = TotalStep;
    }

    public String getTodayStep() {
        return TodayStep;
    }

    public void setTodayStep(String TodayStep) {
        this.TodayStep = TodayStep;
    }

    public String getTotalMealWeight() {
        return TotalMealWeight;
    }

    public void setTotalMealWeight(String TotalMealWeight) {
        this.TotalMealWeight = TotalMealWeight;
    }

    public String getNowE() {
        return NowE;
    }

    public void setNowE(String NowE) {
        this.NowE = NowE;
    }

    public String getF() {
        return F;
    }

    public void setF(String F) {
        this.F = F;
    }

    public String getLimitCCF() {
        return LimitCCF;
    }

    public void setLimitCCF(String LimitCCF) {
        this.LimitCCF = LimitCCF;
    }

    public String getAuctionNum() {
        return AuctionNum;
    }

    public void setAuctionNum(String AuctionNum) {
        this.AuctionNum = AuctionNum;
    }

    public String getAvgdifficulty() {
        return Avgdifficulty;
    }

    public void setAvgdifficulty(String Avgdifficulty) {
        this.Avgdifficulty = Avgdifficulty;
    }

    public String getLimitCircle() {
        return LimitCircle;
    }

    public void setLimitCircle(String LimitCircle) {
        this.LimitCircle = LimitCircle;
    }

    public String getLimitCarbonNum() {
        return LimitCarbonNum;
    }

    public void setLimitCarbonNum(String LimitCarbonNum) {
        this.LimitCarbonNum = LimitCarbonNum;
    }

    public String getLimitE() {
        return LimitE;
    }

    public void setLimitE(String LimitE) {
        this.LimitE = LimitE;
    }
}
