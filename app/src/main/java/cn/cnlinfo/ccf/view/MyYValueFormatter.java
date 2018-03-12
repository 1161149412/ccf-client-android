package cn.cnlinfo.ccf.view;


import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class MyYValueFormatter implements YAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return DecimalFormat.getInstance().format(value)+"$";
    }
}
