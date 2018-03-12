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
import cn.cnlinfo.ccf.entity.ItemNewsEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class SystemNewsDataSource implements IAsyncDataSource<List<ItemNewsEntity>> {
    private int page = 1;
    private int maxPage = 1;
    private int number = 5;

    @Override
    public RequestHandle refresh(ResponseSender<List<ItemNewsEntity>> sender) throws Exception {
            return loadShareUserList(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ItemNewsEntity>> sender) throws Exception {
        return loadShareUserList(sender,page+1);
    }

    private RequestHandle loadShareUserList(final ResponseSender<List<ItemNewsEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex", page);
        params.addFormDataPart("PageSize", number);
        /**
         * 1是公告 2是新闻,0是前两者之和
         */
        params.addFormDataPart("type", 0);
        params.addFormDataPart("Orderby", "ORDER BY IssueDate DESC");
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETNEWSLIST, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<ItemNewsEntity> itemNewsList = JSONArray.parseArray(data.getJSONArray("Newslist").toJSONString(), ItemNewsEntity.class);
                SystemNewsDataSource.this.page = page;
                SystemNewsDataSource.this.maxPage = data.getIntValue("PageCount");
                sender.sendData(itemNewsList);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
                sender.sendData(null);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                EventBus.getDefault().post(new ErrorMessageEvent(errorCode,msg));
                sender.sendData(null);
            }
        });
        return new OkHttpRequestHandler();
    }
    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
