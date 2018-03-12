package cn.cnlinfo.ccf.net_okhttp;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/16 0016.
 */

public class OkHttpGetRequestBuilder extends OkHttpRequestBuilder{


    public OkHttpGetRequestBuilder(String url) {
        super(url);
        this.addHeader("cache-control", "no-cache");
    }

    private String buildParams() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.getParams().entrySet()) {
            if (entry.getValue() instanceof String) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey()), urlEncodeUTF8(entry.getValue())));
            }
        }
        String params = sb.toString();
        if (params.length() > 0) {
            if (getUrl().contains("?")) {
                return "&" + params;
            } else {
                return "?" + params;
            }
        }
        return "";
    }


    @Override
    protected Request build(String tag) {
         return this.buildHeaders().url(getUrl() + buildParams()).get().tag(tag).build();
    }
}
