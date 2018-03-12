package cn.cnlinfo.ccf.province_city_zone.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class Province {
    private String provinceId;
    private String provinceName;
    private List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }

    public Province(String id, String provinceName, List<City> cityList) {
        this.provinceId = id;
        this.provinceName = provinceName;
        this.cityList = cityList;
    }


    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }


    public Province() {
    }

    public String getId() {
        return provinceId;
    }

    public void setId(String id) {
        this.provinceId = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceId='" + provinceId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityList=" + cityList +
                '}';
    }
}
