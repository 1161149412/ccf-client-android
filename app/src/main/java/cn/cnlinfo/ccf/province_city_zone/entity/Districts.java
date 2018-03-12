package cn.cnlinfo.ccf.province_city_zone.entity;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class Districts {

    private String districtsId;
    private String districtsName;
    private String cId;

    public Districts(String districtsId, String districtsName, String cId) {
        this.districtsId = districtsId;
        this.districtsName = districtsName;
        this.cId = cId;
    }



    public Districts() {
    }

    public String getDistrictsId() {
        return districtsId;
    }


    public void setDistrictsId(String districtsId) {
        this.districtsId = districtsId;
    }

    public String getDistrictsName() {
        return districtsName;
    }

    public void setDistrictsName(String districtsName) {
        this.districtsName = districtsName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "Districts{" +
                "districtsId='" + districtsId + '\'' +
                ", districtsName='" + districtsName + '\'' +
                ", cId='" + cId + '\'' +
                '}';
    }
}
