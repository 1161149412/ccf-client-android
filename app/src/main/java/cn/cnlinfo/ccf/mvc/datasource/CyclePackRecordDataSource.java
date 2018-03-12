package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.CyclePackRecordEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class CyclePackRecordDataSource implements IAsyncDataSource<List<CyclePackRecordEntity>> {
    private int page = 1 ;
    private int maxPage;
    private int number = 5;

    @Override
    public RequestHandle refresh(ResponseSender<List<CyclePackRecordEntity>> sender) throws Exception {
        return loadOutTransferRecord(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<CyclePackRecordEntity>> sender) throws Exception {
        Logger.d("loadmore");
        return loadOutTransferRecord(sender,page+1);
    }
    private RequestHandle loadOutTransferRecord(final ResponseSender<List<CyclePackRecordEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("Orderby","Order by Status asc,CreateTime desc");
        HttpRequest.post(Constant.RECORD_CENTER_HOST + API.CONVERSIONCYCLEPACK, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<CyclePackRecordEntity> list = JSONObject.parseArray(data.getJSONArray("BuyPackRecordList").toJSONString(),CyclePackRecordEntity.class);
                CyclePackRecordDataSource.this.page = page;
                CyclePackRecordDataSource.this.maxPage = data.getIntValue("PageCount");
                sender.sendData(list);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                sender.sendData(null);
                EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                sender.sendData(null);
                EventBus.getDefault().post(new ErrorMessageEvent(errorCode,msg));
            }
        });

        return new OkHttpRequestHandler();
    }

    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
