package cn.cnlinfo.ccf.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class SpinnerUtils {
    @NonNull
    public static ArrayAdapter getArrayAdapter(Context context,String []agencyTypeArray) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, agencyTypeArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }
    @NonNull
    public static ArrayAdapter getArrayAdapter(Context context,List<String> agencyTypeArray) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, agencyTypeArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }
}
