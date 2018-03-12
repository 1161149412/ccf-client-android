package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

public class CCServiceActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_game_top_up)
    TextView tvTopUp;
    @BindView(R.id.tv_game_center)
    TextView tvGameCenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_service);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("碳控服务");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_game_top_up, R.id.tv_game_center})
    public void onClick(View v) {
        switch (v.getId()) {
            //游戏充值
            case R.id.tv_game_top_up:
                startActivity(new Intent(this,GameTopUpActivity.class));
                break;
            //游戏中心
            case R.id.tv_game_center:
                startActivity(new Intent(this,GameCenterActivity.class));
                break;
            default:
                break;
        }
    }
}
