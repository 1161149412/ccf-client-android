package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.entity.TradingListItem;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class TradingListDataSource implements IAsyncDataSource<List<TradingListItem>> {
    private int num = 10;
    private int page = 1;
    private int maxPage;
    private String tradingStatus;
    public TradingListDataSource(String type){
        this.tradingStatus = type;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<TradingListItem>> sender) throws Exception {
        page = 1;
        return loadGainTradingList(sender,page);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<TradingListItem>> sender) throws Exception {
        return loadGainTradingList(sender,page+1);
    }

    private RequestHandle loadGainTradingList(final ResponseSender<List<TradingListItem>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",num);
        params.addFormDataPart("SearchItems",tradingStatus);
        params.addFormDataPart("Orderby","order by CreateTime desc");
        HttpRequest.post(Constant.GET_DATA_HOST + API.TRADINGLIST, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<TradingListItem> listItems = JSONObject.parseArray(data.getJSONArray("JobberList").toJSONString(),TradingListItem.class);
                TradingListDataSource.this.page = page;
                //maxPage = data.getIntValue("PageCount");
                sender.sendData(listItems);
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
