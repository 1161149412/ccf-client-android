package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class UpdatePasswordActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_login_pass)
    CleanEditText etLoginPass;
    @BindView(R.id.et_new_login_pass)
    CleanEditText etNewLoginPass;
    @BindView(R.id.et_verify_login_pass)
    CleanEditText etVerifyLoginPass;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.et_new_safe_pass)
    CleanEditText etNewSafePass;
    @BindView(R.id.et_verify_safe_pass)
    CleanEditText etVerifySafePass;
    @BindView(R.id.btn_affirm_update)
    Button btnAffirmUpdate;
    private Unbinder unbinder;
    private String loginPass;
    private String newLoginPass;
    private String verifyLoginPass;
    private String safePass;
    private String newSafePass;
    private String verifySafePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("修改密码");
    }

    @OnClick({R.id.ibt_back,R.id.btn_affirm_update})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ibt_back:
                finish();
                break;
                case R.id.btn_affirm_update:
                   toUpdateNewPass();
                    break;
                    default:
                        break;
        }
    }
    private void gainData() {
        loginPass = etLoginPass.getText().toString();
        newLoginPass = etNewLoginPass.getText().toString();
        verifyLoginPass = etVerifyLoginPass.getText().toString();
        safePass = etSafePass.getText().toString();
        newSafePass = etNewSafePass.getText().toString();
        verifySafePass = etVerifySafePass.getText().toString();
    }
    //更新login密码和safe密码
    private void toUpdateNewPass() {
        gainData();
        if ( !TextUtils.isEmpty(loginPass)&&!TextUtils.isEmpty(newLoginPass) && !TextUtils.isEmpty(verifyLoginPass) &&!TextUtils.isEmpty(safePass)&&!TextUtils.isEmpty(newSafePass) && !TextUtils.isEmpty(verifySafePass)) {
            if (newLoginPass.equals(verifyLoginPass) && newSafePass.equals(verifySafePass)) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
                params.addFormDataPart("oldpwd", loginPass);
                params.addFormDataPart("oldpwd2", safePass);
                params.addFormDataPart("pwd", newLoginPass);
                params.addFormDataPart("pwd2", newSafePass);
                HttpRequest.post(Constant.getHost() + API.UPDATEPASSWORD, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        showMessage("设置成功");
                        UpdatePasswordActivity.this.finish();
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showMessage(code, msg);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        Logger.d(errorCode+""+msg);
                        showMessage(errorCode,msg);
                    }
                });
            } else {
                showMessage("密码输入不一致");
            }

        } else {
            showMessage("输入框不能为空");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
