package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
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
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.MyParams;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class MyParameterActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.tv_ccf)
    TextView tvCcf;
    @BindView(R.id.tv_cycle_package)
    TextView tvCyclePackage;
    @BindView(R.id.tv_wait_alive_ccf)
    TextView tvWaitAliveCcf;
    @BindView(R.id.tv_cycle_coupon)
    TextView tvCycleCoupon;
    @BindView(R.id.tv_current_price)
    TextView tvCurrentPrice;
    @BindView(R.id.tv_wait_free_cycle_points)
    TextView tvWaitFreeCyclePoints;
    @BindView(R.id.tv_bonus_points)
    TextView tvBonusPoints;
    @BindView(R.id.tv_wait_release_points)
    TextView tvWaitReleasePoints;
    @BindView(R.id.tv_cc_points)
    TextView tvCcPoints;
    @BindView(R.id.tv_yesterday_contribute_value)
    TextView tvYesterdayContributeValue;
    @BindView(R.id.tv_current_contribution_value)
    TextView tvCurrentContributionValue;
    @BindView(R.id.tv_basic_contribution_rate)
    TextView tvBasicContributionRate;
    @BindView(R.id.tv_total_step)
    TextView tvTotalStep;
    @BindView(R.id.tv_today_step)
    TextView tvTodayStep;
    @BindView(R.id.tv_ccf_limit)
    TextView tvCcfLimit;
    @BindView(R.id.tv_out_of_line)
    TextView tvOutOfLine;
    @BindView(R.id.tv_average_difficulty_coefficient)
    TextView tvAverageDifficultyCoefficient;
    @BindView(R.id.tv_acc_pur_meal)
    TextView tvAccPurMeal;
    @BindView(R.id.tv_wait_ccf_limit)
    TextView tvWaitCcfLimit;
    @BindView(R.id.tv_cycle_pack_limit)
    TextView tvCyclePackLimit;//循环包限制
    @BindView(R.id.tv_contribute_value_limit)
    TextView tvContributeValueLimit;//贡献封顶值
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parameter);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("我的参数");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETMYPARAMETER);
                finish();
            }
        });
        setMyParameter();
    }

    /**
     * 设置我的参数数据
     */
    private void setMyParameter() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETMYPARAMETER, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                MyParams myParams = JSONObject.parseObject(data.getJSONObject("Mycanshu").toJSONString(), MyParams.class);
                if (myParams != null) {
                    tvCcf.setText(myParams.getCCF());
                    tvCyclePackage.setText(myParams.getCircle());
                    tvWaitAliveCcf.setText(myParams.getCarbonNum());
                    tvCycleCoupon.setText(myParams.getCircleTicket());
                    tvWaitCcfLimit.setText(myParams.getLimitCarbonNum());
                    tvCyclePackLimit.setText(myParams.getLimitCircle());
                    tvContributeValueLimit.setText(myParams.getLimitE());
                    tvCurrentPrice.setText(myParams.getP());
                    tvWaitFreeCyclePoints.setText(myParams.getCircleTicketScore());
                    tvBonusPoints.setText(myParams.getConsumeIntegral());
                    tvWaitReleasePoints.setText(myParams.getReleaseConsume());
                    tvCcPoints.setText(myParams.getCarbonIntegral());
                    tvYesterdayContributeValue.setText(myParams.getYesterdayE());
                    tvCurrentContributionValue.setText(myParams.getNowE());
                    tvBasicContributionRate.setText(myParams.getF());
                    tvTotalStep.setText(myParams.getTotalStep());
                    tvTodayStep.setText(myParams.getTodayStep());
                    tvCcfLimit.setText(myParams.getLimitCCF());
                    tvOutOfLine.setText(myParams.getAuctionNum());
                    tvAverageDifficultyCoefficient.setText(myParams.getAvgdifficulty());
                    tvAccPurMeal.setText(myParams.getTotalMealWeight());
                }
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                showMessage(code, msg);
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
