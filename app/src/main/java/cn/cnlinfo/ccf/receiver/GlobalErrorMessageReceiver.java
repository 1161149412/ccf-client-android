package cn.cnlinfo.ccf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.cnlinfo.ccf.activity.BaseActivity;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class GlobalErrorMessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()== BaseActivity.BROADCAST_FLAG){
            int num = intent.getIntExtra("TYPE",0);
            switch(num){
                case 0:
                    break;
                case 10000:
                    break;
                case 40000:
                    break;
                case 40001:
                    break;
                case 40004:
                    break;
                case 40005:
                    break;
                case 40006:
                    break;
            }
        }
    }
}