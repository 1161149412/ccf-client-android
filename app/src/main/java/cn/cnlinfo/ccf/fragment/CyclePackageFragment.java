package cn.cnlinfo.ccf.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.lljjcoder.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.entity.Exchangepackageinfo;
import cn.cnlinfo.ccf.entity.Userstep;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.RefreshCyclePackEvent;
import cn.cnlinfo.ccf.event.UpdateStepEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.step_count.UpdateUiCallBack;
import cn.cnlinfo.ccf.step_count.bean.StepData;
import cn.cnlinfo.ccf.step_count.service.StepService;
import cn.cnlinfo.ccf.step_count.utils.DatabaseManager;
import cn.cnlinfo.ccf.step_count.utils.SharedPreferencesUtils;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.cnlinfo.ccf.view.StepArcView;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/10/23 0023.
 */

public class CyclePackageFragment extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.tv_cc_num)
    TextView tvCcNum;
    @BindView(R.id.tv_cycle_stock)
    TextView tvCycleStock;
    @BindView(R.id.self_step_arc)
    StepArcView selfStepArc;
    @BindView(R.id.tv_basic_contribute_value)
    TextView tvBasicContributeValue;
    @BindView(R.id.tv_wait_act_value)
    TextView tvWaitActValue;
    @BindView(R.id.tv_contribute_value)
    TextView tvContributeValue;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.sdv_cycle)
    SimpleDraweeView sdvCycle;
    @BindView(R.id.tv_cycle_pack)
    TextView tvCyclePack;
    @BindView(R.id.fl_cycle_package)
    FrameLayout flCyclePackage;
    @BindView(R.id.btn_at_once_transform)
    Button btnAtOnceTransform;
    @BindView(R.id.tv_pack_time)
    TextView tvPackTime;
    @BindView(R.id.tv_conversion_cycle_pack)
    TextView tvConversionCyclePack;
    @BindView(R.id.tv_hold_cycle_pack)
    TextView tvHoldCyclePack;
    @BindView(R.id.et_conversion_cycle_pack)
    CleanEditText etConversionCyclePack;
    @BindView(R.id.btn_conversion_cycle_pack)
    Button btnConversionCyclePack;
    @BindView(R.id.tv_current_rank)
    TextView tvCurrentRank;
    @BindView(R.id.tv_praise_num)
    TextView tvPraiseNum;
    private DatabaseManager databaseManager;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean isBind = false;
    private Animatable animatable;
    private Intent intent;
    private NormalDialog dialog;//显示认证循环包的dialog
    private int currentStep;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_cycle_package);

     unbinder = ButterKnife.bind(this, getContentView());
     EventBus.getDefault().register(this);
     initData();
    }


    private void initData() {
        databaseManager = DatabaseManager.createTableAndInstance("DylanStepCount");
        showWaitingDialog(true);
        setEditTextFocus(etConversionCyclePack);
        setOnClickListener();
        setControllerIntoSdvCycle();
        sharedPreferencesUtils = new SharedPreferencesUtils(this.getApplicationContext());
        startUpService();
        //获取循环包数据
        gainConversionCyclePackData();
    }


    //解压循环包后刷新获取循环包
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCyclePack(RefreshCyclePackEvent refreshCyclePack){
        gainConversionCyclePackData();
    }
    /**
     * 注册监听事件
     */
    private void setOnClickListener() {
        btnConversionCyclePack.setOnClickListener(this);
        btnAtOnceTransform.setOnClickListener(this);
    }

    /**
     * 获取兑换循环包数据
     */
    private void gainConversionCyclePackData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.GETCIRCLE, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                Exchangepackageinfo exchangepackageinfo = JSONObject.parseObject(data.getJSONObject("Exchangepackageinfo").toJSONString(), Exchangepackageinfo.class);
                setCyclePackParams(exchangepackageinfo);
                showWaitingDialog(false);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                showMessage(code, msg);
                showWaitingDialog(false);
            }
        });
    }

    /**
     * 设置循环包参数
     */
    private void setCyclePackParams(Exchangepackageinfo exchangepackageinfo) {
        tvCcNum.setText(String.valueOf(exchangepackageinfo.getCCF()));
        tvCycleStock.setText(String.valueOf(exchangepackageinfo.getCircleTicket()));
        tvCyclePack.setText(String.valueOf(exchangepackageinfo.getCircle()));
        tvCenter.setText(String.valueOf(exchangepackageinfo.getTotalPack()));
        tvWaitActValue.setText(String.valueOf(exchangepackageinfo.getDCCF()));//设置待激活的量
        tvPackTime.setText(String.format(tvPackTime.getText().toString(), exchangepackageinfo.getPackTime()));
        tvConversionCyclePack.setText(String.format(tvConversionCyclePack.getText().toString(), exchangepackageinfo.getHaschange(), exchangepackageinfo.getResidue()));
        tvHoldCyclePack.setText(String.format(tvHoldCyclePack.getText().toString(), exchangepackageinfo.getUpperLimit()));//用户持有量，可兑换量
        etConversionCyclePack.setHint(String.valueOf(exchangepackageinfo.getConvertible()>0?exchangepackageinfo.getConvertible():0));//如果循环包为小于等于0则显示为0，大于0就显示原值
    }

    /**
     * 设置当前的步数
     *
     * @param currentStep
     */
    private void setCurrentStep(int currentStep) {
        //获取用户设置的计划锻炼步数，没有设置过的话默认2000
        String planWalk_QTY = (String) sharedPreferencesUtils.getParam("stepTotal", "2003");
        if (selfStepArc!=null){
            //设置当前步数为0
            selfStepArc.setCurrentCount(Integer.parseInt(planWalk_QTY), currentStep);
        }
        this.currentStep = currentStep;
        Logger.d(currentStep);
    }


    /**
     * 开启计步服务
     */
    private void startUpService() {
        intent = new Intent(getActivity(), StepService.class);
        if (conn!=null){
            isBind = getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
            getActivity().startService(intent);
        }
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService.StepBinder stepBinder = (StepService.StepBinder) service;
            StepService stepService = stepBinder.getService();
            int currentStep = stepService.getStepCount();
            setCurrentStep(currentStep);
            //设置步数监听回调
            stepService.registerCallback(callBack);
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    UpdateUiCallBack callBack = new UpdateUiCallBack() {
        @Override
        public void updateUi(int stepCount) {
            setCurrentStep(stepCount);
        }
    };

    /**
     * 设置控制器加载gif图片
     */
    private void setControllerIntoSdvCycle() {
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                //加载drawable里的一张gif图
                .setUri(Uri.parse("res://" + getApplicationContext().getPackageName() + "/" + R.drawable.icon_cycle))//设置uri
                .build();
        sdvCycle.setController(mDraweeController);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        animatable = sdvCycle.getController().getAnimatable();
        if (animatable != null) {
            animatable.start();
        }
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        if (animatable != null) {
            animatable.stop();
        }
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Logger.d("onFragmentStopLazy");

    }

    @Override
    public void onStop() {
        super.onStop();
//        Logger.d("onStop");
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Logger.d("onDestroyViewLazy");
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (conn!=null&&isBind) {
            getActivity().unbindService(conn);
        }
        showWaitingDialog(false);
        conn = null;
        callBack = null;
        //取消Http请求
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST + API.GETCIRCLE);
        HttpRequest.cancel(Constant.UPLOAD_STEP_HOST + API.UPLOADSTEP);
        HttpRequest.cancel(Constant.OPERATE_CCF_HOST + API.APPROVECYCLEPACK);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conversion_cycle_pack:
                toConversionCyclePack();
                break;
            case R.id.btn_at_once_transform:
                dialog = DialogCreater.createNormalDialog(getContext(), getContext().getResources().getString(R.string.approve_cycle_pack),getContext().getResources().getString(R.string.approve_tip_content), new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if (currentStep>=2018){
                            RequestParams params = new RequestParams();
                            params.addFormDataPart("userID",UserSharedPreference.getInstance().getUser().getUserID());
                            params.addFormDataPart("Step", currentStep);
                            HttpRequest.post(Constant.OPERATE_CCF_HOST + API.APPROVECYCLEPACK, params, new CCFHttpRequestCallback() {
                                @Override
                                protected void onDataSuccess(JSONObject data) {
                                    Userstep userstep = JSONObject.parseObject(data.getJSONObject("Userstep").toJSONString(), Userstep.class);
                                    if (userstep != null) {
                                        tvCurrentRank.setText("第"+userstep.getRanking()+"名");
                                        tvPraiseNum.setVisibility(View.GONE);//暂时把“赞”隐藏
                                        if (!TextUtils.isEmpty(userstep.getF())) {
                                            tvBasicContributeValue.setText(userstep.getF());
                                        }
                                        if (!TextUtils.isEmpty(userstep.getE())) {
                                            tvContributeValue.setText(userstep.getE());
                                        }
                                        tvWaitActValue.setText(userstep.getCarbonnum());
                                    }
                                    ToastUtils.showShortToast(getContext(),"认证成功");
                                    UserSharedPreference.getInstance().setStep(UserSharedPreference.getInstance().getUser().getUserCode(),0);
                                    List<StepData> list =  databaseManager.getQueryByWhere(StepData.class,new String[]{ "username","today"}, new String[]{UserSharedPreference.getInstance().getUser().getUserCode(),getTodayDate()});
                                    if (list!=null&&list.size()>0){
                                        currentStep = 0;//认证后步数置为0
                                        StepData stepData = list.get(0);
                                        stepData.setStepNum(currentStep);
                                        databaseManager.update(data);
                                        setCurrentStep(currentStep);
                                        //发送认证后的更新步数从0开始计数
                                        EventBus.getDefault().post(new UpdateStepEvent(currentStep));
                                    }
                                }

                                @Override
                                protected void onDataError(int code, boolean flag, String msg) {
                                    EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
                                }

                                @Override
                                public void onFailure(int errorCode, String msg) {
                                    super.onFailure(errorCode, msg);
                                    EventBus.getDefault().post(new ErrorMessageEvent(errorCode,msg));
                                }
                            });
                            dialog.cancel();
                        }else {
                            toast("当前步数未达到认证效能步数(2018步)!!!");
                        }
                    }
                });
                dialog.show();
                break;
        }
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
     * 去兑换循环包
     */
    private void toConversionCyclePack() {
        String conversionCyclePack = etConversionCyclePack.getText().toString();
        if (!TextUtils.isEmpty(conversionCyclePack)) {
            int num = Integer.valueOf(etConversionCyclePack.getText().toString());
            int limitNum = Integer.valueOf(etConversionCyclePack.getHint().toString());
            if (num <= limitNum) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("id", UserSharedPreference.getInstance().getUser().getUserID());
                params.addFormDataPart("num", num);
                HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.CONVERSIONCYCLEPACKAGE, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        toast("兑换成功");
                        Exchangepackageinfo exchangepackageinfo = JSONObject.parseObject(data.getJSONObject("Exchangepackageinfo").toJSONString(), Exchangepackageinfo.class);
                        setCyclePackParams(exchangepackageinfo);
                        etConversionCyclePack.setText("");
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showMessage(code, msg);
                    }
                });

            } else {
                toast("你的兑换量不足，请重新输入!!!");
            }
        } else {
            toast("请输入兑换循环包数量!!!");
        }
    }

}
