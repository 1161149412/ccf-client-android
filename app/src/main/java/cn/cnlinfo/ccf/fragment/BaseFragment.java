package cn.cnlinfo.ccf.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.inter.IComponentContainer;
import cn.cnlinfo.ccf.inter.IFragment;
import cn.cnlinfo.ccf.inter.ILifeCycleComponent;
import cn.cnlinfo.ccf.manager.LifeCycleComponentManager;
import cn.cnlinfo.ccf.view.RefreshHeaderView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class BaseFragment extends LazyFragment implements IFragment, IComponentContainer {
    private boolean mFirstResume = true;
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected ACProgressFlower waitingDialog;
    @Override
    public void addComponent(ILifeCycleComponent component) {
        this.mComponentContainer.addComponent(component);
    }

    @Override
    public void onEnter(Object data) {

    }

    public void setEditTextFocus(final EditText editTextFocus){
        editTextFocus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editTextFocus.setFocusable(true);
                editTextFocus.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    @Override
    public void onLeave() {
        this.mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    public void onBack() {
        this.mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    public void onBackWithData(Object data) {
        this.mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    public boolean processBackPressed() {
        return false;
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        this.onLeave();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        if (!this.mFirstResume) {
            this.onBack();
        }

        if (this.mFirstResume) {
            this.mFirstResume = false;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mComponentContainer.onDestroy();
    }
    protected void showMessage(int status, String message) {
        EventBus.getDefault().post(new ErrorMessageEvent(status,message));
    }

    protected void showMessage(String message) {
        EventBus.getDefault().post(new ErrorMessageEvent(message));
    }

    protected void toast(int rsId) {
        toast(getString(rsId));
    }


    protected void toast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 从本地获取持久化的entity
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T getEntityFromLocalStorage(String storageKey, String key, Class<T> clazz) {
        SharedPreferences share = getContext().getSharedPreferences(storageKey, Context.MODE_PRIVATE);
        if (share.contains(key)) {
            String dealerJson = share.getString(key, null);
            return null == dealerJson ? null : JSON.parseObject(dealerJson, clazz);
        }
        return null;
    }

    /**
     * 将entity持久化存储到本地(异步,非及时)
     *
     * @param key
     * @param entity
     */
    protected void putEntityToLocalStorage(String storageKey, String key, Object entity) {
        putEntityToLocalStorage(storageKey, key, entity, false);
    }

    /**
     * 将entity持久化存储到本地(及时)
     *
     * @param key
     * @param entity
     */
    protected void putEntityToLocalStorageNow(String storageKey, String key, Object entity) {
        putEntityToLocalStorage(storageKey, key, entity, true);
    }

    private void putEntityToLocalStorage(String storageKey, String key, Object entity, boolean isNow) {
        SharedPreferences share = getContext().getSharedPreferences(storageKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        if (null == entity) {
            if (!share.contains(key)) {
                return;
            }
            editor.remove(key);
        } else {
            editor.putString(key, JSON.toJSONString(entity));
        }
        if (isNow) {
            editor.commit();
        } else {
            editor.apply();
        }
    }


    protected void showWaitingDialog(boolean show) {
        showWaitingDialog(show, getString(R.string.please_wait));
    }

    protected void showWaitingDialog(boolean show, String waitingNotice) {
        if (!show) {
            waitingDialog.dismiss();
            return;
        }
        waitingDialog = DialogCreater.createProgressDialog(getContext(), waitingNotice);
        waitingDialog.show();
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
     * Override this function when you need control whether you will cancel OkHttpFinal after Destroy
     *
     * @return boolean
     */
    protected boolean cancelOkHttpFinalAfterDestory() {
        return true;
    }
}
