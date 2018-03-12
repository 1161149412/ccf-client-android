package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.ItemHangSellAndBuyEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class HangSellAndBuyRecordDataSource implements IAsyncDataSource<List<ItemHangSellAndBuyEntity>>{
    private int page = 1;
    private int maxPage;
    private int number = 5;
    private String queryCondition = null;
    public HangSellAndBuyRecordDataSource(int tradType){
        this.queryCondition = String.format("where UserID=%s and TranType=%s", UserSharedPreference.getInstance().getUser().getUserID(), tradType);
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<ItemHangSellAndBuyEntity>> sender) throws Exception {
        page = 1;
        return loadGainHangRecord(sender,page);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ItemHangSellAndBuyEntity>> sender) throws Exception {
        return loadGainHangRecord(sender,page+1);
    }

    private RequestHandle loadGainHangRecord(final ResponseSender<List<ItemHangSellAndBuyEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("SearchItems",queryCondition);
        params.addFormDataPart("Orderby","order by CreateTime desc");
        HttpRequest.post(Constant.RECORD_CENTER_HOST + API.HANGBYSELLANDBUY, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<ItemHangSellAndBuyEntity> list = JSONObject.parseArray(data.getJSONArray("HangBuyRecordList").toJSONString(),ItemHangSellAndBuyEntity.class);
                HangSellAndBuyRecordDataSource.this.page = page;
                HangSellAndBuyRecordDataSource.this.maxPage = data.getIntValue("PageCount");
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
