package cn.cnlinfo.ccf.net_okhttp;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.manager.PhoneManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public abstract class DownLoadFileCallBack extends Handler implements Callback {

    public final static int SUCCESS = 10001;
    public final static int ERROR = 10002;
    public final static int PROGRESS = 10003;
    public final static int FAILED = 10004;

    public final static int FAILED_NETWORK = -11;
    public final static int ERROR_JSON = -12;
    public final static int ERROR_RESPONSE = -13;
    public final static int ERROR_RESPONSE_CODE = -14;
    public final static int ERROR_SD_CARD_CODE = -15;
    protected boolean auto10000 = true;

    public DownLoadFileCallBack() {
        this.auto10000 = true;
    }

    public DownLoadFileCallBack(boolean auto10000) {
        this.auto10000 = auto10000;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        sendFailedMessage(FAILED_NETWORK, "无法连接，请检查网络设置");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null && response.isSuccessful()) {
            long total = response.body().contentLength();
            InputStream inputStream = response.body().byteStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,2048);
            FileOutputStream outputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            if (!PhoneManager.isSdCardExit()){
                PhoneManager.createPath();
                File apkFile = new File(Constant.DOWNLOAD_DIR_PATH,"ccf.apk");
                outputStream = new FileOutputStream(apkFile);
                bufferedOutputStream = new BufferedOutputStream(outputStream,2048);
            }else {
                sendErrorMessage(ERROR_SD_CARD_CODE,"SD卡不可用，请插入SD卡");
                return;
            }
            byte []buffer = new byte[2048];
            int i = 0;
            long progress = 0;
            try {
                while  ((i = bufferedInputStream.read(buffer))!=-1){
                    bufferedOutputStream.write(buffer,0,i);
                    progress += i;
                    sendProgressMessage((int) (progress*100/(float)total));
                }
                bufferedOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                    outputStream.close();
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sendFailedMessage(ERROR_RESPONSE_CODE, "请求失败，请重试");
        }
    }


    public void sendProgressMessage(int progress) {
        Message message = new Message();
        message.what = PROGRESS;
        message.obj = progress;
        sendMessage(message);
    }

    public void sendFailedMessage(int code, String msg) {
        Message message = new Message();
        message.what = FAILED;
        message.arg1 = code;
        message.obj = msg;
        sendMessage(message);
    }

    public void sendErrorMessage(int code ,String msg){
        Message message = new Message();
        message.what = ERROR;
        message.arg1 = code;
        message.obj = msg;
        sendMessage(message);
    }

    public abstract void progress(int progress);

    public abstract void failed(int code, String msg);

    public abstract void error(int code,String msg);

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ERROR:
                error(msg.arg1, (String) msg.obj);
            case PROGRESS:
                progress((Integer) msg.obj);
                break;
            case FAILED:
                failed(msg.arg1, (String) msg.obj);
                break;
        }
    }
}
