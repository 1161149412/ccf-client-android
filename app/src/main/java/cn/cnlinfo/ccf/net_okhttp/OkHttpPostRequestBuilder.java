package cn.cnlinfo.ccf.net_okhttp;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by JP on 2017/10/16 0016.
 */

public class OkHttpPostRequestBuilder extends OkHttpRequestBuilder {
    public OkHttpPostRequestBuilder(String url) {
        super(url);
        this.addHeader("Content-Type","application/x-www-form-urlencoded");
        this.addHeader("cache-control", "no-cache");
    }

    private FormBody buildRequestBody(){
        Map<String,String> params = this.getParams();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> map:params.entrySet() ){
            builder.addEncoded(map.getKey(),map.getValue());
        }
        return builder.build();
    }

    @Override
    protected Request build(String tag) {
        return this.buildHeaders().url(getUrl()).post(buildRequestBody()).tag(tag).build();
    }
}
