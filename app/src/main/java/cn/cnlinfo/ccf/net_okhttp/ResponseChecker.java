package cn.cnlinfo.ccf.net_okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import java.io.IOException;

import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.UserSharedPreference;
import okhttp3.Response;


public class ResponseChecker {
    public static CCFResponse explainResponse(String content) {
        JSONObject rst = JSON.parseObject(content.trim());
        int status = rst.getInteger(CCFResponse.RETURN_STATUS);

        JSONObject data = null;
        if (rst.containsKey(CCFResponse.RETURN_DATA)) {
            data = rst.getJSONObject(CCFResponse.RETURN_DATA);
        }

        String statusInfo = null;
        if (rst.containsKey(CCFResponse.RETURN_STATUSINFO)) {
            statusInfo = rst.getString(CCFResponse.RETURN_STATUSINFO);
        }
        return new CCFResponse(status, data, statusInfo);
    }

    public static CCFResponse explainResponse(Response response) throws IOException {
        if (null == response || !response.isSuccessful()) return null;
        refreshLocalJwtTokenFromResponseHeader(response);

        String content = response.body().string();
        if (Constant.isDebug()) {
            Logger.d(response.request().toString());
            Logger.d(content);
            Logger.json(content);
        }
        return explainResponse(content);
    }

    private static void refreshLocalJwtTokenFromResponseHeader(Response response) {
        String authorization = response.header("Authorization", null);
        if (null != authorization && authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring("Bearer ".length());
            UserSharedPreference.getInstance().setJwtToken(jwtToken);
        }
    }
}
