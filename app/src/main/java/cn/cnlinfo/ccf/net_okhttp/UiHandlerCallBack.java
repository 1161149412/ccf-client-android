package cn.cnlinfo.ccf.net_okhttp;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by ouarea on 16/2/29.
 */

public abstract class UiHandlerCallBack extends Handler implements Callback{

    public final static int SUCCESS = 10001;
    public final static int ERROR = 10002;
    public final static int PROGRESS = 10003;
    public final static int FAILED = 10004;

    public final static int FAILED_NETWORK = -11;
    public final static int ERROR_JSON = -12;
    public final static int ERROR_RESPONSE = -13;
    public final static int ERROR_RESPONSE_CODE = -14;
    protected boolean auto10000 = true;

    public UiHandlerCallBack() {
        this.auto10000 = true;
    }

    public UiHandlerCallBack(boolean auto10000) {
        this.auto10000 = auto10000;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        sendFailedMessage(FAILED_NETWORK, "无法连接，请检查网络设置");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Logger.d(response.message());
        if (response != null && response.isSuccessful()) {
            try {
                CCFResponse ccfResponse = ResponseChecker.explainResponse(response);
                switch (ccfResponse.getStatus()) {
                    case 0://请求成功
                        sendSuccessData(ccfResponse.getData());
                        call.cancel();
                        break;
                    default:
                        sendErrorMessage(ccfResponse.getStatus(), ccfResponse.getStatusInfo());
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                sendFailedMessage(ERROR_JSON, "数据格式错误");
            } catch (Exception e) {
                e.printStackTrace();
                sendFailedMessage(ERROR_RESPONSE, "请求响应异常");
            }
        } else {
            sendFailedMessage(ERROR_RESPONSE_CODE, "请求失败，请重试");
        }
    }

    public void sendSuccessData(JSONObject data) {
        Message message = new Message();
        message.what = SUCCESS;
        message.obj = data;
        sendMessage(message);
    }

    public void sendErrorMessage(int status, String statusInfo) {
        Message message = new Message();
        message.what = ERROR;
        message.arg1 = status;
        message.obj = statusInfo;
        sendMessage(message);
    }

    public void sendProgressMessage(int progress) {
        Message message = new Message();
        message.what = PROGRESS;
        message.arg1 = progress;
        sendMessage(message);
    }

    public void sendFailedMessage(int code, String msg) {
        Message message = new Message();
        message.what = FAILED;
        message.arg1 = code;
        message.obj = msg;
        sendMessage(message);
    }


    public abstract void success(JSONObject data);

    public abstract void error(int status, String message);

    public abstract void progress(int progress);

    public abstract void failed(int code, String msg);

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SUCCESS:
                success((JSONObject) msg.obj);
                break;
            case ERROR:
                String message = "";
                if (null != msg.obj) {
                    message = (String) msg.obj;
                }
                error(msg.arg1, message);
                break;
            case PROGRESS:
                progress(msg.arg1);
                break;
            case FAILED:
                failed(msg.arg1, (String) msg.obj);
                break;
        }
    }
}
