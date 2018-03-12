package cn.cnlinfo.ccf.view;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class DataValueFormatter implements ValueFormatter {

    protected DecimalFormat mFormat;

    public DataValueFormatter() {
        //保留三位小数点
        mFormat = new DecimalFormat("###,###,##0.000$");
        //mFormat = new DecimalFormat("#.###E0$");//科学计数法

    }
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return mFormat.format(value);
    }
}
