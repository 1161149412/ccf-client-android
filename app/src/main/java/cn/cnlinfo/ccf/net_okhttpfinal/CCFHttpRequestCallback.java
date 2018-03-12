package cn.cnlinfo.ccf.net_okhttpfinal;

import com.alibaba.fastjson.JSONObject;

import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import okhttp3.Headers;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public abstract  class CCFHttpRequestCallback extends JsonHttpRequestCallback {

    private  final String status = "MessageID";
    private  final String statusInfo = "Content";
    private  final String isSuccess = "Success";
    private  final String data = "Data";

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    protected void onSuccess(Headers headers, JSONObject jsonObject) {
        super.onSuccess(headers, jsonObject);
        int code = -1;
        String msg = null;
        boolean flag = false;
        JSONObject json = null;
        if (jsonObject.containsKey(status)){
            code = jsonObject.getIntValue(status);
        }

        if (jsonObject.containsKey(statusInfo)){
            msg = jsonObject.getString(statusInfo);
        }

        if (jsonObject.containsKey(isSuccess)){
            flag = Boolean.valueOf(isSuccess);
        }

        if (jsonObject.containsKey(data)){
            json = jsonObject.getJSONObject(data);
        }
        switch (code){
            case 0:
                onDataSuccess(json);
                break;
            default:
                onDataError(code,flag,msg);
                break;
        }
    }


    @Override
    protected void onSuccess(JSONObject jsonObject) {
        super.onSuccess(jsonObject);
    }

    protected abstract void onDataSuccess(JSONObject data);

    protected abstract void onDataError(int code,boolean flag,String msg);

    @Override
    public void onFailure(int errorCode, String msg) {
        super.onFailure(errorCode, msg);
    }
}
