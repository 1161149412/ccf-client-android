package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.manager.AppManage;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.ObtainVerificationCode;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class LoginRegisterActivity extends BaseActivity {
    @BindView(R.id.et_username)
    CleanEditText etUsername;
    @BindView(R.id.et_password)
    CleanEditText etPassword;
    @BindView(R.id.et_verification_code)
    CleanEditText etVerificationCode;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_forget_pass)
    Button btnForgetPass;
    private Unbinder unbinder;
    private long exitTime = 0;
    private long currentTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVerificationCode();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            currentTime = System.currentTimeMillis();
            if ((currentTime - exitTime) > 2000) {
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = currentTime;
            } else {
                AppManage.getInstance().finishOther();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解决activity销毁后自定义view持有activity的引用发生内存泄漏
        etUsername.removeTextChangedListener(etUsername);
        etPassword.removeTextChangedListener(etPassword);
        etVerificationCode.removeTextChangedListener(etVerificationCode);
        HttpRequest.cancel(Constant.getHost() + API.CCFLOGIN);
        unbinder.unbind();//解绑后对象自动为空 Logger.d(etUsername==null);true
    }

    public void toLogin(View view) {
        startLogin();
    }

    public void toRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void gainVerificationCode(View view) {
        setVerificationCode();
    }

    private void setVerificationCode() {
        String str = ObtainVerificationCode.createNumVerificationCode();
        tvGetVerificationCode.setText(str);
    }

    public void toForgetPass(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    /**
     * 开始登陆
     */
    private void startLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String verificationCode = etVerificationCode.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            toast("用户名或密码不能为空");
        } else {
            if (verificationCode != null && verificationCode.toLowerCase().equals(tvGetVerificationCode.getText().toString().toLowerCase())) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("username", username);
                params.addFormDataPart("password", password);
                HttpRequest.post(Constant.getHost() + API.CCFLOGIN, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        toast("登录成功");
                        UserSharedPreference.getInstance().setJwtToken("1");
                        UserSharedPreference.getInstance().setIsFirstLogin(true);
                        JSONObject userinfoJsonobject = data.getJSONObject("userinfo");
                        String jsonString = userinfoJsonobject.toJSONString();
                        UserSharedPreference.getInstance().setUserInfo(jsonString);
                        UserSharedPreference.getInstance().setPhoneAndPassword(username,password);
                        Intent intent = new Intent(LoginRegisterActivity.this, MainPageActivity.class);
                        startActivity(intent);
                        LoginRegisterActivity.this.finish();
                    }
                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showMessage(code,msg);
//                        Logger.d(code + "  " + flag + "  " + msg);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        showMessage(errorCode,msg);
                    }
                });
            } else {
                toast("验证码不正确，请重新输入");
            }
        }
    }

}
