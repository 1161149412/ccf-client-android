package cn.cnlinfo.ccf.step_count.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.activity.MainPageActivity;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.UpdateStepEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.step_count.UpdateUiCallBack;
import cn.cnlinfo.ccf.step_count.accelerometer.StepCount;
import cn.cnlinfo.ccf.step_count.accelerometer.StepValuePassListener;
import cn.cnlinfo.ccf.step_count.bean.StepData;
import cn.cnlinfo.ccf.step_count.utils.DatabaseManager;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 17/11/2
 */
public class StepService extends Service implements SensorEventListener {
    /**
     * 默认为30秒进行一次存储
     */
    private static int duration = 3 * 1000;
    /**
     * 当前的日期
     */
    private static String CURRENT_DATE = "";
    /**
     * 传感器管理对象
     */
    private SensorManager sensorManager;
    /**
     * 广播接受者
     */
    private BroadcastReceiver mBatInfoReceiver;
    /**
     * 保存计步计时器
     */
    private TimeCount time;
    /**
     * 当前所走的步数
     */
    private int CURRENT_STEP;
    /**
     * 计步传感器类型  Sensor.TYPE_STEP_COUNTER或者Sensor.TYPE_STEP_DETECTOR
     */
    private static int stepSensorType = -1;
    /**
     * 每次第一次启动记步服务时是否从系统中获取了已有的步数记录
     */
    private boolean hasRecord = false;
    /**
     * 系统中获取到的已有的步数
     */
    private int hasStepCount = 0;
    /**
     * 上一次的步数
     */
    private int previousStepCount = 0;
    /**
     * 通知管理对象
     */
    private NotificationManager mNotificationManager;
    /**
     * 加速度传感器中获取的步数
     */
    private StepCount mStepCount;
    /**
     * IBinder对象，向Activity传递数据的桥梁
     */
    private StepBinder stepBinder = new StepBinder();
    /**
     * 通知构建者
     */
    private NotificationCompat.Builder mBuilder;
    private DatabaseManager databaseManager;
    private Sensor countSensor;
    private Sensor detectorSensor;
    private User user;


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 初始化通知栏
     */
    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("今日步数" + CURRENT_STEP + " 步")
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(R.mipmap.ccf);
        Notification notification = mBuilder.build();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startForeground(notify_step_id, notification);
//        Logger.d("initNotification");
    }

    /**
     * 初始化当天的步数
     */
    private void initTodayData() {

        user = UserSharedPreference.getInstance().getUser();
        CURRENT_DATE = getTodayDate();

        databaseManager = DatabaseManager.createTableAndInstance("DylanStepCount");

        //获取当天的数据，用于展示
        List<StepData> list = databaseManager.getQueryByWhere(StepData.class, new String[]{"username", "today"}, new String[]{user.getUserCode(), getTodayDate()});
        Logger.d("initTodayData  " + user.getUserCode() + "  " + list.size());
        if (list.size() == 0 || list.isEmpty()) {
            RequestParams params = new RequestParams();
            params.addFormDataPart("username", user.getUserCode());
            params.addFormDataPart("password", UserSharedPreference.getInstance().getPhoneAndPassword().substring(UserSharedPreference.getInstance().getPhoneAndPassword().indexOf('/') + 1));
            HttpRequest.post(Constant.getHost() + API.CCFLOGIN, params, new CCFHttpRequestCallback() {
                @Override
                protected void onDataSuccess(JSONObject data) {
                    user = JSONObject.parseObject(data.getJSONObject("userinfo").toJSONString(), User.class);
                    //上传一次后，步数累加到第二天的步数再上传，每天只能上传一次
                    CURRENT_STEP = user.getTodayStep()+UserSharedPreference.getInstance().getStep(user.getUserCode());
                    updateNotification();
                    Logger.d(CURRENT_STEP);
                }

                @Override
                protected void onDataError(int code, boolean flag, String msg) {
                    EventBus.getDefault().post(new ErrorMessageEvent(code, msg));
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    super.onFailure(errorCode, msg);
                    EventBus.getDefault().post(new ErrorMessageEvent(errorCode, msg));
                }
            });
        } else if (list!=null&&list.size() == 1) {
            Logger.d("StepData=" + list.get(0).toString());
            CURRENT_STEP = list.get(0).getStepNum();
        } else {
            Logger.d("error");
        }
        if (mStepCount != null) {
            mStepCount.setSteps(CURRENT_STEP);
        }
        updateNotification();
    }

    /**
     * 注册广播
     */
    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
//        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //监听日期变化
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                //屏幕开启广播
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Logger.d("screen on");
                    //屏幕关闭广播
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Logger.d("screen off");
                    //改为60秒一存储
                    duration = 60000;
                    //手机锁屏
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Logger.d("screen unlock");
//                    save();
                    //改为30秒一存储
                    duration = 30000;
                    //系统弹窗
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Logger.d("receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                    //保存一次
                    save();
                    //手机关机
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    Logger.d("receive ACTION_SHUTDOWN");
                    save();
                    //手机日期变化
                } else if (Intent.ACTION_DATE_CHANGED.equals(action)) {//日期变化步数重置为0
//                    Logger.d("重置步数" + StepDcretor.CURRENT_STEP);
                    save();
                    isNewDay();
                    //手机日期变化
                } else if (Intent.ACTION_TIME_CHANGED.equals(action)) {
                    //时间变化步数重置为0
                    isCall();
                    save();
                    isNewDay();
                } else if (Intent.ACTION_TIME_TICK.equals(action)) {//日期变化步数重置为0
                    isCall();
//                    Logger.d("重置步数" + StepDcretor.CURRENT_STEP);
                    save();
                    isNewDay();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }


    /**
     * 监听晚上0点变化初始化数据
     */
    private boolean isNewDay() {
        String time = "00:00";
        if (time.equals(new SimpleDateFormat("HH:mm").format(new Date())) || !CURRENT_DATE.equals(getTodayDate())) {
            //晚上12点app开启中存储当天认证后的剩余步数
            UserSharedPreference.getInstance().setStep(user.getUserCode(),CURRENT_STEP);
            initTodayData();
            return true;
        }
        return false;
    }


    /**
     * 监听时间变化提醒用户锻炼
     */
    private void isCall() {
        String time = this.getSharedPreferences("share_date", Context.MODE_MULTI_PROCESS).getString("achieveTime", "21:00");
        String plan = this.getSharedPreferences("share_date", Context.MODE_MULTI_PROCESS).getString("planWalk_QTY", "10000");
        String remind = this.getSharedPreferences("share_date", Context.MODE_MULTI_PROCESS).getString("remind", "1");
        // Logger.d("time=" + time + "\n" + "new SimpleDateFormat(\"HH: mm\").format(new Date()))=" + new SimpleDateFormat("HH:mm").format(new Date()));
        if (("1".equals(remind)) &&
                (CURRENT_STEP < Integer.parseInt(plan)) &&
                (time.equals(new SimpleDateFormat("HH:mm").format(new Date())))
                ) {
            remindNotify();
        }
    }

    /**
     * 开始保存计步数据
     */
    private void startTimeCount() {
        if (time == null) {
            time = new TimeCount(duration, 1000);
        }
        time.start();
    }

    /**
     * 更新步数通知
     */
    private void updateNotification() {
        //设置点击跳转
        Intent hangIntent = new Intent(this, MainPageActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("今日步数" + CURRENT_STEP + " 步")
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setContentIntent(hangPendingIntent)
                .build();
        mNotificationManager.notify(notify_step_id, notification);
        if (mCallback != null) {
            mCallback.updateUi(CURRENT_STEP);
        }
//            Logger.d("updateNotification()");
    }

    /**
     * UI监听器对象
     */
    private UpdateUiCallBack mCallback;

    /**
     * 注册UI更新监听
     *
     * @param paramICallback
     */
    public void registerCallback(UpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }

    /**
     * 记步Notification的ID
     */
    int notify_step_id = 100;
    /**
     * 提醒锻炼的Notification的ID
     */
    int notify_remind_id = 200;

    /**
     * 提醒锻炼通知栏
     */
    private void remindNotify() {
        //设置点击跳转
        Intent hangIntent = new Intent(this, MainPageActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        String plan = this.getSharedPreferences("share_date", Context.MODE_MULTI_PROCESS).getString("planWalk_QTY", "70000");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("今日步数" + CURRENT_STEP + " 步")
                .setContentText("距离目标还差" + (Integer.valueOf(plan) - CURRENT_STEP) + "步，加油！")
                .setContentIntent(hangPendingIntent)
                .setTicker(getResources().getString(R.string.app_name) + "提醒您开始锻炼了")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ccf);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(notify_remind_id, mBuilder.build());
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stepBinder;
    }

    /**
     * 向Activity传递数据的纽带
     */
    public class StepBinder extends Binder {

        /**
         * 获取当前service对象
         *
         * @return StepService
         */
        public StepService getService() {
            return StepService.this;
        }
    }

    /**
     * 获取当前步数
     *
     * @return
     */
    public int getStepCount() {
        return CURRENT_STEP;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                /**
                 * 获取传感器实例
                 */
                gainStepDetectorInstance();

            }
        }).start();
        startTimeCount();
/**
 * 值得注意的是在onStartCommand中返回值，常用的返回值有：START_NOT_STICKY、START_SICKY和START_REDELIVER_INTENT,这三个都是静态常理值。

 START_NOT_STICKY：表示当Service运行的进程被Android系统强制杀掉之后，不会重新创建该Service，如果想重新实例化该Service，就必须重新调用startService来启动。

 使用场景：表示当Service在执行工作中被中断几次无关紧要或者对Android内存紧张的情况下需要被杀掉且不会立即重新创建这种行为也可接受的话，这是可以在onStartCommand返回值中设置该值。如在Service中定时从服务器中获取最新数据

 START_STICKY：表示Service运行的进程被Android系统强制杀掉之后，Android系统会将该Service依然设置为started状态（即运行状态），但是不再保存onStartCommand方法传入的intent对象，然后Android系统会尝试再次重新创建该Service，并执行onStartCommand回调方法，这时onStartCommand回调方法的Intent参数为null，也就是onStartCommand方法虽然会执行但是获取不到intent信息。

 使用场景：如果你的Service可以在任意时刻运行或结束都没什么问题，而且不需要intent信息，那么就可以在onStartCommand方法中返回START_STICKY，比如一个用来播放背景音乐功能的Service就适合返回该值。

 START_REDELIVER_INTENT：表示Service运行的进程被Android系统强制杀掉之后，与返回START_STICKY的情况类似，Android系统会将再次重新创建该Service，并执行onStartCommand回调方法，但是不同的是，Android系统会再次将Service在被杀掉之前最后一次传入onStartCommand方法中的Intent再次保留下来并再次传入到重新创建后的Service的onStartCommand方法中，这样我们就能读取到intent参数。

 使用场景：如果我们的Service需要依赖具体的Intent才能运行（需要从Intent中读取相关数据信息等），并且在强制销毁后有必要重新创建运行，那么这样的Service就适合返回START_REDELIVER_INTENT。
 */
        return START_STICKY;
    }

    /**
     * 获取传感器实例
     */
    private void gainStepDetectorInstance() {
        if (sensorManager != null) {
            sensorManager = null;
        }

        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        //android4.4以后可以使用计步传感器
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) {
            addCountStepListener();
        } else {
            addBasePedometerListener();
        }
    }

    /**
     * 添加传感器监听
     * 1. TYPE_STEP_COUNTER API的解释说返回从开机被激活后统计的步数，当重启手机后该数据归零，
     * 该传感器是一个硬件传感器所以它是低功耗的。
     * 为了能持续的计步，请不要反注册事件，就算手机处于休眠状态它依然会计步。
     * 当激活的时候依然会上报步数。该sensor适合在长时间的计步需求。
     * <p>
     * 2.TYPE_STEP_DETECTOR翻译过来就是走路检测，
     * API文档也确实是这样说的，该sensor只用来监监测走步，每次返回数字1.0。
     * 如果需要长事件的计步请使用TYPE_STEP_COUNTER。
     */
    private void addCountStepListener() {
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_COUNTER;
            Logger.d("Sensor.TYPE_STEP_COUNTER");
            sensorManager.registerListener(StepService.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (detectorSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_DETECTOR;
            Logger.d("Sensor.TYPE_STEP_DETECTOR");
            sensorManager.registerListener(StepService.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Logger.d("Count sensor not available!");
            addBasePedometerListener();
        }
    }

    /**
     * 通过加速度传感器来计步
     */
    private void addBasePedometerListener() {
        mStepCount = new StepCount();
        mStepCount.setSteps(CURRENT_STEP);
        // 获得传感器的类型，这里获得的类型是加速度传感器
        // 此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable = sensorManager.registerListener(mStepCount.getStepDetector(), sensor,
                SensorManager.SENSOR_DELAY_UI);
        mStepCount.initListener(new StepValuePassListener() {
            @Override
            public void stepChanged(int steps) {
                if (UserSharedPreference.getInstance().getUser() != null) {
                    CURRENT_STEP = steps;
                    updateNotification();
                }
            }
        });
        if (isAvailable) {
            Logger.d("加速度传感器可以使用");
        } else {
            Logger.d("加速度传感器无法使用");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Logger.d("onAccuracyChanged = " + accuracy + "");
    }


    /**
     * 传感器监听回调
     * 记步的关键代码
     * 1. TYPE_STEP_COUNTER API的解释说返回从开机被激活后统计的步数，当重启手机后该数据归零，
     * 该传感器是一个硬件传感器所以它是低功耗的。
     * 为了能持续的计步，请不要反注册事件，就算手机处于休眠状态它依然会计步。
     * 当激活的时候依然会上报步数。该sensor适合在长时间的计步需求。
     * <p>
     * 2.TYPE_STEP_DETECTOR翻译过来就是走路检测，
     * API文档也确实是这样说的，该sensor只用来监监测走步，每次返回数字1.0。
     * 如果需要长事件的计步请使用TYPE_STEP_COUNTER。
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (stepSensorType == Sensor.TYPE_STEP_COUNTER) {
            //获取当前传感器返回的临时步数
            int tempStep = (int) event.values[0];
            //首次如果没有获取手机系统中已有的步数则获取一次系统中APP还未开始记步的步数
            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = tempStep;
            } else {
                //获取APP打开到现在的总步数=本次系统回调的总步数-APP打开之前已有的步数
                int thisStepCount = tempStep - hasStepCount;
                //本次有效步数=（APP打开后所记录的总步数-上一次APP打开后所记录的总步数）
                int thisStep = thisStepCount - previousStepCount;
                if (UserSharedPreference.getInstance().getUser() != null) {
                    //总步数=现有的步数+本次有效步数
                    CURRENT_STEP += (thisStep);
                }
                //记录最后一次APP打开到现在的总步数
                previousStepCount = thisStepCount;
            }
//            Logger.d("历史总步数tempStep = " + tempStep+"\n"+"当前的步数thisStepCount = "+previousStepCount+"当前用户的步数 = "+CURRENT_STEP);
        } else if (stepSensorType == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0) {
                if (UserSharedPreference.getInstance().getUser() != null) {
                    CURRENT_STEP++;
                }
                Logger.d(" Sensor.TYPE_STEP_DETECTOR");
            }
        }
        updateNotification();
    }

    //点击认证后，对当前步数进行赋值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearStep(UpdateStepEvent event){
        CURRENT_STEP = event.getStep();
    }

    /**
     * 保存计步数据
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 如果计时器正常结束，则开始计步
            time.cancel();
            save();
            startTimeCount();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }

    /**
     * 保存计步数据
     */
    private void save() {
        int tempStep = CURRENT_STEP;
        if (user != null) {
            List<StepData> list = databaseManager.getQueryByWhere(StepData.class, new String[]{"username", "today"}, new String[]{user.getUserCode(), getTodayDate()});
            if (list.size() == 0 || list.isEmpty()) {
                StepData data = new StepData();
                data.setUsername(user.getUserCode());
                data.setDate(CURRENT_DATE);
                data.setStepNum(tempStep);
                databaseManager.insert(data);
            } else if (list.size() == 1) {
                StepData data = list.get(0);
                data.setStepNum(tempStep);
                databaseManager.update(data);
            } else {
                Logger.d("error");
            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消前台notification进程
        stopForeground(true);
        //在用户手动关闭app杀死service前保存步数
        UserSharedPreference.getInstance().setStep(user.getUserCode(),CURRENT_STEP);
        databaseManager.closeDatabase();
        databaseManager = null;
        unregisterReceiver(mBatInfoReceiver);
        mNotificationManager.cancel(notify_step_id);
        Logger.d("onDestroy");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        //Logger.d("onUnbind");
        EventBus.getDefault().unregister(this);
        return super.onUnbind(intent);
    }
}
