package cn.cnlinfo.ccf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import cn.cnlinfo.ccf.activity.BaseActivity;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.manager.AppManage;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver{
    private NormalDialog normalDialog;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)|| BaseActivity.BROADCAST_NETWORK_FLAG.equals(action)){
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null){
                if (networkInfo.isConnected()){
                    if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                        if (normalDialog !=null&& normalDialog.isShowing()){
                            normalDialog.dismiss();
                        }
                    }
                    if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                        if (normalDialog !=null&& normalDialog.isShowing()){
                            normalDialog.dismiss();
                        }
                    }
                }else {
                    //showSetNetWorkDialog(context);
                }
            }else {
                showSetNetWorkDialog(context);
            }
        }
    }


    private void showSetNetWorkDialog(final Context context){
        normalDialog = DialogCreater.createTipsDialog(context, "温馨提示", "当前网络不可用，请检查是否连接了可用的wifi或移动网络!","确定",false, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                toSetNetWorkPage(context);
            }
        });
        normalDialog.show();
    }

    private void toSetNetWorkPage(Context context){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
        AppManage.getInstance().exit(context);
    }
}

