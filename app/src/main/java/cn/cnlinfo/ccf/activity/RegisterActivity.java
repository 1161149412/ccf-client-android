package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.net_okhttp.OKHttpManager;
import cn.cnlinfo.ccf.net_okhttp.OkHttpPostRequestBuilder;
import cn.cnlinfo.ccf.net_okhttp.UiHandlerCallBack;
import cn.cnlinfo.ccf.utils.EditTextInputFormatUtil;
import cn.cnlinfo.ccf.utils.GetMessageCode;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by JP on 2017/10/16 0016.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_invitation_code)
    CleanEditText etInvitationCode;
    @BindView(R.id.et_user_name)
    CleanEditText etUserName;
    @BindView(R.id.et_login_pass)
    CleanEditText etLoginPass;
    @BindView(R.id.et_verify_pass)
    CleanEditText etVerifyPass;
    @BindView(R.id.et_phone_num)
    CleanEditText etPhoneNum;
    @BindView(R.id.et_verification_code_register)
    CleanEditText etVerificationCode;
    @BindView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R.id.btn_register_user)
    Button btnRegisterUser;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    @BindView(R.id.cb_is_read)
    CheckBox cbIsRead;
    @BindView(R.id.tv_upgrade_agency_link)
    TextView tvUpgradeAgencyLink;
    private String invitationCode;
    private String userName;
    private String loginPass;
    private String verifyPass;
    private String phoneNum;
    private String verificationCode;
    private String code = null;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("注册");
        ibtAdd.setVisibility(View.INVISIBLE);
        setClickListener();
        CharSequence charSequence = Html.fromHtml("已同意并愿意接受:<a href=\"http://www.ccfcc.cc/XY.aspx\">用户协议");
        tvUpgradeAgencyLink.setText(charSequence);
        tvUpgradeAgencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, WebActivity.class);
                intent.putExtra("url", "http://www.ccfcc.cc/XY.aspx");
                startActivity(intent);
            }
        });
    }

    //设置监听器
    private void setClickListener() {
        btnGetVerificationCode.setOnClickListener(this);
        btnRegisterUser.setOnClickListener(this);
        ibtBack.setOnClickListener(this);
    }


    //点击注册初始化
    private void init() {
        invitationCode = etInvitationCode.getText().toString();
        userName = etUserName.getText().toString();
        loginPass = etLoginPass.getText().toString();
        verifyPass = etVerifyPass.getText().toString();
        verificationCode = etVerificationCode.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verification_code:
                phoneNum = etPhoneNum.getText().toString();
                if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11 && EditTextInputFormatUtil.isLegalPhoneNum(phoneNum)) {
                    GetMessageCode.startTimer(btnGetVerificationCode);
                    RequestParams params = new RequestParams();
                    params.addFormDataPart("phone", phoneNum);
                    params.addFormDataPart("codeid", 3126312);//短信平台模板id
                    HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.SENDCODE, params, new BaseHttpRequestCallback() {
                        @Override
                        public void onResponse(String response, Headers headers) {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            code = (String) jsonObject.get("obj");
                        }

                        @Override
                        public void onFailure(int errorCode, String msg) {
                            super.onFailure(errorCode, msg);
                            showMessage(errorCode, msg);
                        }
                    });
                } else {
                    toast("电话号码不能为空或格式有误!");
                }
                break;
            case R.id.btn_register_user:
                init();
                if (!TextUtils.isEmpty(invitationCode) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(loginPass) && !TextUtils.isEmpty(verifyPass) && !TextUtils.isEmpty(verificationCode) && !TextUtils.isEmpty(phoneNum)) {
                    if (loginPass.equals(verifyPass)) {
                        if (cbIsRead.isChecked()){
                            startToRegister(userName, invitationCode, phoneNum, loginPass);
                        }else {
                            toast("请仔细阅读并同意代理协议");
                        }
                    } else {
                        toast("两次输入的密码不一致,请重新输入!");
                        return;
                    }
                } else {
                    toast("输入框不能为空!");
                }
                break;
            case R.id.ibt_back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 开始注册
     */
    private void startToRegister(String userName, String invitationCode, String phoneNum, String pwd) {
        OkHttpPostRequestBuilder okHttpPostRequestBuilder = new OkHttpPostRequestBuilder(Constant.getHost() + API.CCFREGISTER);
        okHttpPostRequestBuilder.put("strAccounts", userName);
        okHttpPostRequestBuilder.put("strDirectAccounts", invitationCode);
        okHttpPostRequestBuilder.put("telephone", phoneNum);
        okHttpPostRequestBuilder.put("pwd", pwd);
        okHttpPostRequestBuilder.put("code", code);
        OKHttpManager.post(okHttpPostRequestBuilder, "register", new UiHandlerCallBack() {
            @Override
            public void success(JSONObject data) {
                Logger.json(data.toJSONString());
                toast("注册成功");
                startActivity(new Intent(RegisterActivity.this, LoginRegisterActivity.class));
                RegisterActivity.this.finish();
            }

            @Override
            public void error(int status, String message) {
                Logger.d(status + "   " + message);
                showMessage(message);
            }

            @Override
            public void progress(int progress) {

            }

            @Override
            public void failed(int code, String msg) {
                showMessage(msg);
            }
        });

 /*       RequestParams params = new RequestParams();
        params.addFormDataPart("strAccounts",userName);
        params.addFormDataPart("strDirectAccounts",invitationCode);
        params.addFormDataPart("telephone",phoneNum);
        params.addFormDataPart("pwd",pwd);

        HttpRequest.post(Constant.getHost()+API.CCFREGISTER,params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                Logger.json(data.toJSONString());
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                Logger.d(code+"   "+flag+"   "+msg);
            }
        });*/
    }

}
