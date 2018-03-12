package cn.cnlinfo.ccf.mvc.datasource;

import com.shizhefei.mvc.RequestHandle;

/**
 *
 * Created by JP on 2017/2/7.
 */
public class OkHttpRequestHandler implements RequestHandle {
    @Override
    public void cancle() {}

    @Override
    public boolean isRunning() {
        return true;
    }
}
