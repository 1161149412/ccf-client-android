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
import cn.cnlinfo.ccf.entity.StepRecordEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class StepRecordDataSource implements IAsyncDataSource<List<StepRecordEntity>> {
    private int page = 1 ;
    private int maxPage = Integer.MAX_VALUE;
    private int number = 5;

    @Override
    public RequestHandle refresh(ResponseSender<List<StepRecordEntity>> sender) throws Exception {
        return loadOutTransferRecord(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<StepRecordEntity>> sender) throws Exception {
        Logger.d("loadmore");
        return loadOutTransferRecord(sender,page+1);
    }
    private RequestHandle loadOutTransferRecord(final ResponseSender<List<StepRecordEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("Orderby","Order by Date desc");
        HttpRequest.post(Constant.RECORD_CENTER_HOST + API.STEPRECORD, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<StepRecordEntity> list = JSONObject.parseArray(data.getJSONArray("UserStepDayRecordList").toJSONString(),StepRecordEntity.class);
                StepRecordDataSource.this.page = page;
                StepRecordDataSource.this.maxPage = data.getIntValue("PageCount");
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
