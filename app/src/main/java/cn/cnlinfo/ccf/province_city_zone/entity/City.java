package cn.cnlinfo.ccf.province_city_zone.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class City {
    private String cityId;
    private String cityName;
    private String pId;
    private String zipCode;
    private List<Districts> districtsList;

    public City(String cityId, String cityName, String pId, String zipCode, List<Districts> districtsList) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.pId = pId;
        this.zipCode = zipCode;
        this.districtsList = districtsList;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", pId='" + pId + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", districtsList=" + districtsList +
                '}';
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Districts> getDistrictsList() {
        return districtsList;
    }

    public void setDistrictsList(List<Districts> districtsList) {
        this.districtsList = districtsList;
    }
}
