package cn.cnlinfo.ccf.entity;

public class User{

    /**
     * UserID : 4
     * UserCode : 1001
     * NickName : 1001
     * Mobile : 15982013088
     */

    private int UserID;
    private String UserCode;
    private String NickName;
    private String Mobile;
    /**
     * UserID : 4
     * InvitationCode : A7E3C79EB9C4BD07
     */

    private String InvitationCode;
    /**
     * UserID : 4
     * TodayStep : 23
     */

    private int TodayStep;

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", UserCode='" + UserCode + '\'' +
                ", NickName='" + NickName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", InvitationCode='" + InvitationCode + '\'' +
                ", TodayStep=" + TodayStep +
                '}';
    }

    public User(int userID, String userCode, String nickName, String mobile, String invitationCode, int todayStep) {
        UserID = userID;
        UserCode = userCode;
        NickName = nickName;
        Mobile = mobile;
        InvitationCode = invitationCode;
        TodayStep = todayStep;
    }

    public User(int userID, String userCode, String nickName, String mobile, String invitationCode) {
        UserID = userID;
        UserCode = userCode;
        NickName = nickName;
        Mobile = mobile;
        InvitationCode = invitationCode;
    }

    public User() {
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String InvitationCode) {
        this.InvitationCode = InvitationCode;
    }

    public int getTodayStep() {
        return TodayStep;
    }

    public void setTodayStep(int TodayStep) {
        this.TodayStep = TodayStep;
    }
}