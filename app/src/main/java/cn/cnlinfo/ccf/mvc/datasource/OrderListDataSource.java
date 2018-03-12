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
import cn.cnlinfo.ccf.entity.OrderListItem;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class OrderListDataSource implements IAsyncDataSource<List<OrderListItem>> {
    private int page = 1 ;
    private int maxPage;
    private int number = 5;
    private String tradType = null;

    public OrderListDataSource(String tradType) {
        this.tradType = tradType;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<OrderListItem>> sender) throws Exception {
        return loadOrderList(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<OrderListItem>> sender) throws Exception {
        return loadOrderList(sender,page+1);
    }
    private RequestHandle loadOrderList(final ResponseSender<List<OrderListItem>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("SearchItems", tradType);
        params.addFormDataPart("Orderby","Order by CreateTime desc");
        HttpRequest.post(Constant.GET_DATA_HOST + API.ORDERLISTRECORD, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                Logger.d(data.toJSONString());
                List<OrderListItem> list = JSONObject.parseArray(data.getJSONArray("AuctionOrderList").toJSONString(),OrderListItem.class);
                OrderListDataSource.this.page = page;
                OrderListDataSource.this.maxPage = data.getIntValue("PageCount");
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
