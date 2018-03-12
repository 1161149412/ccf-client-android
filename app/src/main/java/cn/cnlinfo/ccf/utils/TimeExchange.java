package cn.cnlinfo.ccf.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class TimeExchange {

    public static String timestampIntoDate(String time){
        final String str = time.substring(6,time.length()-7);
        Date date = new Date(Long.valueOf(str));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(date);
        return result;
    }

    public static String timestampIntoDate(JSONObject data){
        JSONObject jsonObject = data.getJSONObject("userinfo");
        String date = jsonObject.getString("RegDate");
        return timestampIntoDate(date);
    }
}
