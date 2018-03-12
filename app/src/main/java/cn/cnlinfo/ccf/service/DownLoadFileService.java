package cn.cnlinfo.ccf.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.File;

import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.net_okhttp.DownLoadFileCallBack;
import cn.cnlinfo.ccf.net_okhttp.OkHttp3Utils;

public class DownLoadFileService extends Service {

    private NotificationManager notificationManager;
    private Notification.Builder builder;
    private  Intent it;
    private PendingIntent pendingIntent;
    public DownLoadFileService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(this);
        builder.setContentTitle("正在下载").setContentText("下载中...").setSmallIcon(R.mipmap.ccf);
        it = new Intent(Intent.ACTION_VIEW);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        OkHttp3Utils.downloadFile("http://gyxz.exmmw.cn/a3/rj_sp1/yysjdwfd.apk", new DownLoadFileCallBack() {
            @Override
            public void progress(int progress) {


                /**
                 * indeterminate 这是显示进度条
                 */
                builder.setProgress(100,progress,false);
                builder.setContentText(progress+"%");
                Logger.d(progress);
                if (progress==100){
                    Logger.d(progress);
                    File targetFile = new File(Constant.DOWNLOAD_DIR_PATH,"ccf.apk");
                    if (targetFile.exists()){
                        Uri apkUri = FileProvider.getUriForFile(DownLoadFileService.this, "cn.cnlinfo.ccf.fileprovider", targetFile);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        it.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        it.setDataAndType(apkUri,"application/vnd.android.package-archive");
                        pendingIntent = PendingIntent.getActivity(DownLoadFileService.this,101,it,PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    builder.setContentTitle("下载完成")
                            .setContentText("点击安装")
                            .setContentInfo("下载完成")
                            .setContentIntent(pendingIntent);
                    notificationManager.cancel(1);
                    notificationManager.notify(2,builder.build());
                    return;
                }
                notificationManager.notify(1,builder.build());
            }
            @Override
            public void failed(int code, String msg) {
                notificationManager.cancel(1);
            }

            @Override
            public void error(int code, String msg) {
                notificationManager.cancel(1);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
