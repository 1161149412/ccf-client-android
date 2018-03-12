package cn.cnlinfo.ccf.mvc.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.entity.RunningRankEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class RunningRankDataSource implements IAsyncDataSource<List<RunningRankEntity>> {

    private int page = 1;
    private int maxPage = Integer.MAX_VALUE;
    private List<RunningRankEntity> runningRankEntityList;

    private  RequestHandle loadRunningRank(final ResponseSender<List<RunningRankEntity>> sender, final int page) throws Exception{
        HttpRequest.get(Constant.GET_DATA_HOST+ API.GETRUNNINGRANK, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                runningRankEntityList = new ArrayList<>();
                runningRankEntityList = JSONArray.parseArray(data.getJSONArray("Ranking").toJSONString(),RunningRankEntity.class);
                RunningRankDataSource.this.page = page;
                sender.sendData(runningRankEntityList);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
            }
        });

        return new OkHttpRequestHandler();
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<RunningRankEntity>> sender) throws Exception {
        return loadRunningRank(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<RunningRankEntity>> sender) throws Exception {
        return loadRunningRank(sender,page+1);
    }

    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
