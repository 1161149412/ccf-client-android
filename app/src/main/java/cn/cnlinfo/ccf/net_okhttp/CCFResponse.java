package cn.cnlinfo.ccf.net_okhttp;

import com.alibaba.fastjson.JSONObject;

public class CCFResponse {

    public final static String RETURN_STATUS = "MessageID";
    public final static String RETURN_STATUSINFO = "Content";
    public final static String RETURN_DATA = "Data";


    private int status;

    private JSONObject data;

    private String statusInfo;

    public CCFResponse(int status, JSONObject data, String statusInfo) {
        this.status = status;
        this.data = data;
        this.statusInfo = statusInfo;
    }

    public boolean isSuccess() {
        return 0 == this.status;
    }

    public int getStatus() {
        return this.status;
    }

    public JSONObject getData() {
        return this.data;
    }

    public String getStatusInfo() {
        return this.statusInfo;
    }
}
