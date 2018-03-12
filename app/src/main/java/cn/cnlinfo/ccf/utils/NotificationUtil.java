package cn.cnlinfo.ccf.utils;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.cnlinfo.ccf.R;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class NotificationUtil {
    private NotificationManager notificationManager;
    private static Context context;
    private static  NotificationUtil notificationUtil;
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";


    private NotificationUtil(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static synchronized NotificationUtil getInstance(Context context){
        if (notificationUtil==null){
            notificationUtil = new NotificationUtil(context);
        }
        return notificationUtil;
    }

    /**
     * 点击一次出现一条
     * 修改PendingIntent.getActivity(context,Integer.valueOf(code),intent,PendingIntent.FLAG_CANCEL_CURRENT);二随机值、四两个参数
     *  notificationManager.notify(Integer.valueOf(code),notification)修改第一个参数，随机值
     * @param code
     */
    public void sendNormalNotification(String code){
        Notification .Builder builder = new Notification.Builder(context);
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,Integer.valueOf(code),intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ccf)
                .setContentTitle("手机验证")
                .setContentText("【碳控因子】欢迎您,您的验证码是"+code)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setOngoing(false)
                .setTicker("碳控因子")
                .setVibrate(new long[]{500,500,500,500})
                .setDefaults(Notification.DEFAULT_SOUND);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(Integer.valueOf(code),notification);
    }

    /**
     * 判断通知是否开启
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
