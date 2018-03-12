package cn.cnlinfo.ccf;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.cnlinfo.ccf.activity.LoginRegisterActivity;
import cn.cnlinfo.ccf.entity.ExitLoginEvent;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.manager.ACache;
import cn.cnlinfo.ccf.manager.AppManage;
import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;


/**
 * Created by JP on 2017/10/11 0011.
 */

public class CCFApplication extends Application {

    private static Context mContext;
    private static CCFApplication INSTANCE;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
        INSTANCE = this;
        mContext = getApplicationContext();

        if (!BuildConfig.DEBUG) {
            TCAgent.LOG_ON = true;
            TCAgent.init(this);
            TCAgent.setReportUncaughtExceptions(true);
        }
        EventBus.getDefault().register(mContext);
        Fresco.initialize(mContext);

        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
        builder.setDebug(true);
        OkHttpFinal.getInstance().updateCommonHeader("Accept","application/json");
    }

    public static RefWatcher getRefWatcher() {
        return INSTANCE.refWatcher;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(mContext);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showErrorMessage(ErrorMessageEvent errorMessageEvent){
        int code = errorMessageEvent.getErrorCode();
        String msg = errorMessageEvent.getMsg();
        switch (code){
            case 500:
                toast("服务器正在维护，请稍后再试!!!");
                break;
            case -1:
                toast(msg);
                break;
            case -2:
                toast(msg);
                break;
                default:
                    toast(msg);
                    break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivedExitLogin(ExitLoginEvent exitLoginEvent){
        UserSharedPreference.getInstance().logout();
        AppManage.getInstance().finishOther();
        jumpToLogin();
    }
    //分包的application添加的方法
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void toast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    protected boolean allowLogin(){
        return null==ACache.get(mContext).getAsString("isLogged");
    }

    public void jumpToLogin(){
        if (!allowLogin()) return;
        ACache.get(mContext).put("isLogged",true,5);
        Intent intent = new Intent(mContext, LoginRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public static synchronized CCFApplication getInstance() {
        return INSTANCE;
    }

    public static synchronized Context getContext(){return mContext;}


}
