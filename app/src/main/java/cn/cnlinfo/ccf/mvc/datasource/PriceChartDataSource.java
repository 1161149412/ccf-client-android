package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.entity.ItemPriceListEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class PriceChartDataSource implements IAsyncDataSource<List<ItemPriceListEntity>> {
    private int number = 31;
    private int page = 1;
    private int maxPage = 0;

    private String timeQueryString = "where 1=1 AND AddTime BETWEEN %s AND %s";
    private String startTiem;
    private String endTime;

    public PriceChartDataSource(String startTiem,String endTime){
        this.startTiem = startTiem;
        this.endTime = endTime;
    }
    @Override
    public RequestHandle refresh(ResponseSender<List<ItemPriceListEntity>> sender) throws Exception {
        return loadPriceListItem(sender,page);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ItemPriceListEntity>> sender) throws Exception {
        return loadPriceListItem(sender,page);
    }

    private RequestHandle loadPriceListItem(final ResponseSender<List<ItemPriceListEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("SearchItems", String.format(timeQueryString, startTiem, endTime));
        params.addFormDataPart("Orderby","Order by AddTime asc");
        HttpRequest.post(Constant.PRICE_LIST_HOST + API.PRICELIST, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
//                Logger.d(data.toJSONString());
                List<ItemPriceListEntity> list = JSONObject.parseArray(data.getJSONArray("PriceList").toJSONString(),ItemPriceListEntity.class);
                PriceChartDataSource.this.page = page+1;
                PriceChartDataSource.this.maxPage = data.getIntValue("PageCount");
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
