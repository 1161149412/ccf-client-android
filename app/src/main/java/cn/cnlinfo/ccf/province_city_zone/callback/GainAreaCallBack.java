package cn.cnlinfo.ccf.province_city_zone.callback;

import cn.cnlinfo.ccf.province_city_zone.entity.City;
import cn.cnlinfo.ccf.province_city_zone.entity.Districts;
import cn.cnlinfo.ccf.province_city_zone.entity.Province;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public interface GainAreaCallBack {
    void gainArea(Province province, City city, Districts districts);
}
