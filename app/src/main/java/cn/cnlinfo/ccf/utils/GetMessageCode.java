package cn.cnlinfo.ccf.utils;

import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JP on 2017/12/18 0018.
 */

public class GetMessageCode {
    public static void startTimer(final Button btn){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 60;
            @Override
            public void run() {
                if (i>1){
                    i--;
                    btn.post(new Runnable() {
                        @Override
                        public void run() {
                            btn.setClickable(false);
                            btn.setText(i+"s");
                        }
                    });
                }else {
                    btn.post(new Runnable() {
                        @Override
                        public void run() {
                            btn.setText("获取");
                            btn.setClickable(true);
                        }
                    });
                    timer.cancel();
                }
            }
        },500,1000);
    }
}
