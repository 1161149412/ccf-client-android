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
import cn.cnlinfo.ccf.entity.LeaveMessageRecordEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 用户留言记录数据
 */

public class UserLeaveMessageRecordDataSource implements IAsyncDataSource<List<LeaveMessageRecordEntity>> {
    private int page = 1;
    private int maxPage;
    private int number = 5;

    private RequestHandle loadUserLeaveMessageRecordList(ResponseSender<List<LeaveMessageRecordEntity>> sender, final int page){
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex",page);
        params.addFormDataPart("PageSize",number);
        params.addFormDataPart("SearchItems","where userID = "+ UserSharedPreference.getInstance().getUser().getUserID());
        params.addFormDataPart("Orderby","order by Addtime desc");
        HttpRequest.post(Constant.RECORD_CENTER_HOST + API.USERLEAVEMESSAGERECORD, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                List<LeaveMessageRecordEntity> leaveMessageRecordEntityList = JSONArray.parseArray(data.getJSONArray("systemMsgList").toJSONString(),LeaveMessageRecordEntity.class);
                maxPage = data.getIntValue("PageCount");
                UserLeaveMessageRecordDataSource.this.page = page+1;
                sender.sendData(leaveMessageRecordEntityList);
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
    public RequestHandle refresh(ResponseSender<List<LeaveMessageRecordEntity>> sender) throws Exception {
        return loadUserLeaveMessageRecordList(sender,1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<LeaveMessageRecordEntity>> sender) throws Exception {
        return loadUserLeaveMessageRecordList(sender,page);
    }

    @Override
    public boolean hasMore() {
        return page<maxPage;
    }
}
