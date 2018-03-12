package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

public class ConversionCenterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ccf_conversion)
    TextView tvCcfConversion;
    @BindView(R.id.tv_consumption_points_conversion)
    TextView tvConsumptionPointsConversion;
    @BindView(R.id.tv_production_points_conversion)
    TextView tvProductionPointsConversion;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_center);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("兑换中心");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setClickListener();
    }

    public void setClickListener() {
        tvCcfConversion.setOnClickListener(this);
        tvProductionPointsConversion.setOnClickListener(this);
        tvConsumptionPointsConversion.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ccf_conversion:
                startActivity(new Intent(this, CCFConversionActivity.class));
                break;
            //产品积分兑换
            case R.id.tv_production_points_conversion:
                startActivity(new Intent(this,ProductionPointsConversionActivity.class));
                break;
            //消费积分兑换
            case R.id.tv_consumption_points_conversion:
                startActivity(new Intent(this, ConsumptionPointsConversionActivity.class));
                break;
        }
    }
}
