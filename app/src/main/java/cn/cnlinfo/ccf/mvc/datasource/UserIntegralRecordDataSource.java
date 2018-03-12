package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.UserIntegralRecordEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 用户积分记录数据
 */

public class UserIntegralRecordDataSource implements IAsyncDataSource<List<UserIntegralRecordEntity>> {
    private int page = 1;
    private int maxPage;
    private int number = 5;

    private RequestHandle loadUserIntegralRecordList(ResponseSender<List<UserIntegralRecordEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("SearchItems","where userid = "+ UserSharedPreference.getInstance().getUser().getUserID());
        params.addFormDataPart("Orderby","Order by CreateTime desc");
        HttpRequest.post(Constant.RECORD_CENTER_HOST + API.GAININTEGRALRECORD, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<UserIntegralRecordEntity> userIntegralRecordEntityList = JSONArray.parseArray(data.getJSONArray("ScoreRecordList").toJSONString(),UserIntegralRecordEntity.class);
                maxPage = data.getIntValue("PageCount");
                UserIntegralRecordDataSource.this.page = page+1;
                sender.sendData(userIntegralRecordEntityList);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                EventBus.getDefault().post(new ErrorMessageEvent(errorCode,msg));
            }
        });
        return new OkHttpRequestHandler();
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<UserIntegralRecordEntity>> sender) throws Exception {
        return loadUserIntegralRecordList(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<UserIntegralRecordEntity>> sender) throws Exception {
        return loadUserIntegralRecordList(sender,page);
    }

    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
