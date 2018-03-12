package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    @BindView(R.id.tv_basic_info)
    TextView tvBasicInfo;
    @BindView(R.id.tv_encrypted)
    TextView tvEncrypted;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_leave_message)
    TextView tvLeaveMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        tvTitle.setText("设置中心");
        ibtAdd.setVisibility(View.INVISIBLE);
        setClickListener();
    }

    private void setClickListener(){
        ibtBack.setOnClickListener(this);
        tvBasicInfo.setOnClickListener(this);
        tvPassword.setOnClickListener(this);
        tvLeaveMessage.setOnClickListener(this);
        tvEncrypted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ibt_back:
                finish();
                break;
                case R.id.tv_basic_info:
                    startActivity(new Intent(this,SetBasicInfoActivity.class));
                        break;
                    case R.id.tv_password:
                        startActivity(new Intent(this,UpdatePasswordActivity.class));
                            break;
                        case R.id.tv_leave_message:
                            startActivity(new Intent(this,LeaveMessageActivity.class));
                              break;
                            case R.id.tv_encrypted:
                                startActivity(new Intent(this,SetEncryptedActivity.class));
                                  break;
        }
    }
}
