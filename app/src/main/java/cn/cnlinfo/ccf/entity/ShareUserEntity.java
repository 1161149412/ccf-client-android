package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class ShareUserEntity {


    /**
     * UserCode : 664161
     * Name :
     * Mobile :
     * RegDate : 2017/10/23 15:48:41
     */

    private String UserCode;
    private String Name;
    private String Mobile;
    private String RegDate;

    public ShareUserEntity(String userCode, String name, String mobile, String regDate) {
        UserCode = userCode;
        Name = name;
        Mobile = mobile;
        RegDate = regDate;
    }

    public ShareUserEntity() {
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String RegDate) {
        this.RegDate = RegDate;
    }

    @Override
    public String toString() {
        return "ShareUserEntity{" +
                "UserCode='" + UserCode + '\'' +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", RegDate='" + RegDate + '\'' +
                '}';
    }
}
