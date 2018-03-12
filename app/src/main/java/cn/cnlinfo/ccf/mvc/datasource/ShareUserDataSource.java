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
import cn.cnlinfo.ccf.entity.ShareUserEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ShareUserDataSource implements IAsyncDataSource<List<ShareUserEntity>> {
    private int page = 1;
    private int maxPage = 1;
    private int number = 5;

    @Override
    public RequestHandle refresh(ResponseSender<List<ShareUserEntity>> sender) throws Exception {
            return loadShareUserList(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ShareUserEntity>> sender) throws Exception {
        return loadShareUserList(sender,page+1);
    }

    private RequestHandle loadShareUserList(final ResponseSender<List<ShareUserEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("Orderby","Order by RegDate desc");
        HttpRequest.post(Constant.GET_DATA_HOST + API.USERSHARELIST, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<ShareUserEntity> shareUserEntityList = JSONArray.parseArray(data.getJSONArray("ShareList").toJSONString(),ShareUserEntity.class);
                ShareUserDataSource.this.page = page;
                ShareUserDataSource.this.maxPage = data.getIntValue("PageCount");
                sender.sendData(shareUserEntityList);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
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
