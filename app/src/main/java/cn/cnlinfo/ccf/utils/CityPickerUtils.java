package cn.cnlinfo.ccf.utils;

import android.content.Context;
import android.graphics.Color;

import com.lljjcoder.city_20170724.CityPickerView;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class CityPickerUtils {
    public static CityPickerView showCityPicker(Context context, final int flag){
        CityPickerView cityPicker = new CityPickerView.Builder(context)
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#C7C7C7")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("北京")
                .city("北京")
                .district("东城区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
                cityPicker.show();
                return cityPicker;
    }
}
