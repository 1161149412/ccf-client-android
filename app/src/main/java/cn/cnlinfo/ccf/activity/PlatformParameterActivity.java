package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.CCFApplication;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.PlatformInfo;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;

public class PlatformParameterActivity extends BaseActivity {
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_total_ccf)
    TextView tvTotalCcf;
    @BindView(R.id.tv_alive_total)
    TextView tvAliveTotal;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_total_tba)
    TextView tvTotalTba;
    @BindView(R.id.tv_cc_points)
    TextView tvCcPoints;
    @BindView(R.id.tv_cycle_coupon)
    TextView tvCycleCoupon;
    @BindView(R.id.tv_wait_free_cycle_points)
    TextView tvWaitFreeCyclePoints;
    @BindView(R.id.tv_available_consume_points)
    TextView tvAvailableConsumePoints;
    @BindView(R.id.tv_wait_free_consume_points)
    TextView tvWaitFreeConsumePoints;
    @BindView(R.id.tv_current_account_amount)
    TextView tvCurrentAccountAmount;
    @BindView(R.id.tv_current_cycle_package_amount)
    TextView tvTotalPack;
    @BindView(R.id.tv_frozen_amount)
    TextView tvFrozenAmount;
    @BindView(R.id.tv_init_total_amount)
    TextView tvInitTotalAmount;
    @BindView(R.id.tv_current_difficult_point)
    TextView tvCurrentDifficultPoint;
    @BindView(R.id.tv_province_total)
    TextView tvProvinceTotal;
    @BindView(R.id.tv_city_total)
    TextView tvCityTotal;
    @BindView(R.id.tv_county_total)
    TextView tvCountyTotal;
    @BindView(R.id.tv_service_center)
    TextView tvServiceCenter;
    @BindView(R.id.tv_user_alive_total)
    TextView tvUserAliveTotal;
    @BindView(R.id.tv_user_wait_alive_total)
    TextView tvUserWaitAliveTotal;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_parameter);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("平台参数");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETPLATFORMINFO);
                finish();
            }
        });
        setPlateformParamer();
    }

    /**
     * 设置平台参数
     */
    private void setPlateformParamer() {
        HttpRequest.get(Constant.GET_DATA_HOST + API.GETPLATFORMINFO, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                JSONObject jsonObject = data.getJSONObject("platforminfo");
                PlatformInfo platformInfo = JSONObject.parseObject(jsonObject.toJSONString(), PlatformInfo.class);
                if (platformInfo != null) {
                    tvAliveTotal.setText(platformInfo.getActiveCCF());
                    tvTotalTba.setText(platformInfo.getTotalInertiaCCF());
                    tvUserAliveTotal.setText(platformInfo.getUserCCF());
                    tvUserWaitAliveTotal.setText(platformInfo.getUserDCCF());
                    tvCurrentAccountAmount.setText(platformInfo.getTotalAccount());
                    tvTotalCcf.setText(platformInfo.getTotalCCF());
                    tvInitTotalAmount.setText(platformInfo.getInitActiveCCF());
                    tvCurrentDifficultPoint.setText(platformInfo.getCurrent_u());
                    tvAvailableConsumePoints.setText(platformInfo.getActiveConsumeScore());
                    tvWaitFreeCyclePoints.setText(platformInfo.getInertiaCCScore());
                    tvWaitFreeConsumePoints.setText(platformInfo.getInertiaConsumeScore());
                    tvCycleCoupon.setText(platformInfo.getCircleTicket());
                    tvCcPoints.setText(platformInfo.getCCScore());
                    tvProvinceTotal.setText(platformInfo.getProvinceProxy());
                    tvCityTotal.setText(platformInfo.getCityProxy());
                    tvCountyTotal.setText(platformInfo.getCountyProxy());
                    tvTotalPack.setText(platformInfo.getTotalPack());
                }
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                showMessage(code, msg);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                showMessage(errorCode, msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        CCFApplication.getRefWatcher().watch(this);
    }
}
