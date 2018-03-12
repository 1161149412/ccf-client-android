package cn.cnlinfo.ccf.activity;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.MVCHelper;

import org.greenrobot.eventbus.EventBus;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.cnlinfo.ccf.CCFApplication;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.inter.IActivityFinish;
import cn.cnlinfo.ccf.inter.IComponentContainer;
import cn.cnlinfo.ccf.inter.ILifeCycleComponent;
import cn.cnlinfo.ccf.manager.AppManage;
import cn.cnlinfo.ccf.manager.LifeCycleComponentManager;
import cn.cnlinfo.ccf.manager.PhoneManager;
import cn.cnlinfo.ccf.manager.SystemBarTintManager;
import cn.cnlinfo.ccf.mvc.factory.NormalLoadViewFactory;
import cn.cnlinfo.ccf.net_okhttp.OKHttpManager;
import cn.cnlinfo.ccf.receiver.GlobalErrorMessageReceiver;
import cn.cnlinfo.ccf.receiver.NetworkConnectChangedReceiver;
import cn.cnlinfo.ccf.view.RefreshHeaderView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class BaseActivity extends AppCompatActivity implements IComponentContainer, IActivityFinish {

    public static final String BROADCAST_FLAG = "cn.cnlinfo.ccf.response.message";
    public static final String BROADCAST_NETWORK_FLAG = "cn.cnlinfo.ccf.net";
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected ACProgressFlower waitingDialog;
    private GlobalErrorMessageReceiver messageReceiver;
    private NetworkConnectChangedReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManage.getInstance().addActivity(this);
        messageReceiver = new GlobalErrorMessageReceiver();
        receiver = new NetworkConnectChangedReceiver();
        registerNetworkConnectChangedReceiver();
        registerGlobalErrorMessageReceiver();
        MVCHelper.setLoadViewFractory(new NormalLoadViewFactory());
    }

    private void registerNetworkConnectChangedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction(BROADCAST_NETWORK_FLAG);
        registerReceiver(receiver, intentFilter);
    }

    private void registerGlobalErrorMessageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_FLAG);
        registerReceiver(messageReceiver, intentFilter);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色
        }
    }

    protected void showWaitingDialog(boolean show) {
        showWaitingDialog(show, getString(R.string.please_wait));
    }

    protected void toLogin(){
        startActivity(new Intent(this,LoginRegisterActivity.class));
    }

    protected void showMessage(int status, String message) {
        EventBus.getDefault().post(new ErrorMessageEvent(status,message));
    }

    protected void showMessage(String message) {
        EventBus.getDefault().post(new ErrorMessageEvent(message));
    }

    protected void showEditTextNoNull(){
        showMessage("输入框不能为空");
    }
    protected void showWaitingDialog(boolean show, String waitingNotice) {
        if (!show) {
            waitingDialog.dismiss();
            return;
        }
        waitingDialog = DialogCreater.createProgressDialog(this, waitingNotice);
        waitingDialog.show();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    public void finish() {
        AppManage.getInstance().finishActivity(this);
    }

    @Override
    public void finishActivity() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
        Intent intent = new Intent();
        intent.setAction(BROADCAST_NETWORK_FLAG);
        sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mComponentContainer.onBecomesPartiallyInvisible();
    }


    @Override
    public void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mComponentContainer.onDestroy();
        unregisterReceiver(messageReceiver);
        unregisterReceiver(receiver);
        OKHttpManager.cancelAndRemoveAllCalls();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始执行contentView动画
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void startContentViewAnimation(View contentView, AnimatorListenerAdapter onAnimationEnd) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(contentView, "alpha", 1),
                ObjectAnimator.ofFloat(contentView, "translationY", 0)
        );
        set.setDuration(400).start();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(onAnimationEnd);
    }

    /**
     * toast message
     *
     * @param text
     */
    protected void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast message
     *
     * @param resource
     */
    protected void toast(int resource) {
        Toast.makeText(getApplicationContext(), resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addComponent(ILifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }


    protected void setMaterialHeader(PtrClassicFrameLayout ptr) {
        RefreshHeaderView ptrHeader = new RefreshHeaderView(getApplicationContext());
        ptrHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrHeader.setPtrFrameLayout(ptr);
        ptr.setLoadingMinTime(800);
        ptr.setDurationToCloseHeader(800);
        ptr.setHeaderView(ptrHeader);
        ptr.addPtrUIHandler(ptrHeader);
    }

    /**
     * 验证是否登录,如果未登录,则跳转登录页面
     *
     * @return
     */
    protected boolean validLogin() {
        if (!UserSharedPreference.getInstance().hasLogined()) {
            startActivity(new Intent(BaseActivity.this, LoginRegisterActivity.class));
            AppManage.getInstance().finishOther();
            return false;
        }
        return true;
    }

    protected boolean validNewVersionByVersionCode() {
        int newVersionCode = PhoneManager.getVersionInfo().versionCode;
        UserSharedPreference userSharedPreference = UserSharedPreference.getInstance();
        if (userSharedPreference.isNewVersionCode(newVersionCode)) {
            userSharedPreference.setLatestVersionCode(newVersionCode);
            startActivity(new Intent(BaseActivity.this, GuideActivity.class));
            finish();
            return true;
        }
        return false;
    }

    protected boolean validNewVersionByVersionName() {
        String newVersionName = PhoneManager.getVersionInfo().versionName;
        Logger.d(newVersionName);
        UserSharedPreference userSharedPreference = UserSharedPreference.getInstance();
        if (userSharedPreference.isNewVersionName(newVersionName)) {
            userSharedPreference.setLatestVersionName(newVersionName);
            startActivity(new Intent(BaseActivity.this, GuideActivity.class));
            finish();
            return true;
        }
        return false;
    }


    protected String getRunningActivityName() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName(); //类名
        String className = info.topActivity.getClassName(); //完整类名
        String packageName = info.topActivity.getPackageName();//包名
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return shortClassName;
    }

    /**
     * Override this function when you need control whether you will cancel OkHttpFinal after Destroy
     *
     * @return boolean
     */
    protected boolean cancelOkHttpFinalAfterDestory() {
        return true;
    }
    protected void exit(){
        UserSharedPreference.getInstance().logout();
        AppManage.getInstance().finishOther();
        CCFApplication.getInstance().jumpToLogin();
    }

}
