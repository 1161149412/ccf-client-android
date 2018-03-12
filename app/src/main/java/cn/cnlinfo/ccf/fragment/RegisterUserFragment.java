package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import cn.cnlinfo.ccf.activity.MainPageActivity;
import cn.cnlinfo.ccf.activity.WebActivity;
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
 * Created by Administrator on 2017/11/6 0006.
 */

public class RegisterUserFragment extends BaseFragment {

    @BindView(R.id.et_invitation_code)
    CleanEditText mEtInvitationCode;
    @BindView(R.id.et_user_name)
    CleanEditText mEtUserName;
    @BindView(R.id.et_contact_person)
    CleanEditText mEtContactPerson;
    @BindView(R.id.et_login_pass)
    CleanEditText mEtLoginPass;
    @BindView(R.id.et_verify_pass)
    CleanEditText mEtVerifyPass;
    @BindView(R.id.et_phone_num)
    CleanEditText mEtPhoneNum;
    @BindView(R.id.et_verification_code_register)
    CleanEditText mEtVerificationCodeRegister;
    @BindView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R.id.btn_register_user)
    Button btnRegisterUser;
    @BindView(R.id.cb_is_read)
    CheckBox cbIsRead;
    @BindView(R.id.tv_upgrade_agency_link)
    TextView tvUpgradeAgencyLink;
    private Unbinder unbinder;
    private String invitationCode;
    private String userName;
    private String contactPerson;
    private String loginPass;
    private String verifyPass;
    private String phoneNum;
    private String verificationCode;
    private String code = null;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_register_user);
        unbinder = ButterKnife.bind(this, getContentView());
        //默认设置邀请码
        mEtInvitationCode.setText(UserSharedPreference.getInstance().getUser().getInvitationCode());
        CharSequence charSequence = Html.fromHtml("已同意并愿意接受:<a href=\"http://www.ccfcc.cc/XY.aspx\">用户协议");
        tvUpgradeAgencyLink.setText(charSequence);
        tvUpgradeAgencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", "http://www.ccfcc.cc/XY.aspx");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }


    //点击注册获取编辑框数据
    private void getEditString() {
        invitationCode = mEtInvitationCode.getText().toString();
        userName = mEtUserName.getText().toString();
        contactPerson = mEtContactPerson.getText().toString();
        loginPass = mEtLoginPass.getText().toString();
        verifyPass = mEtVerifyPass.getText().toString();
        verificationCode = mEtVerificationCodeRegister.getText().toString();
    }


    @OnClick({R.id.btn_get_verification_code, R.id.btn_register_user})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_get_verification_code:
                phoneNum = mEtPhoneNum.getText().toString();
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
                getEditString();
                if (!TextUtils.isEmpty(invitationCode) && !TextUtils.isEmpty(contactPerson) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(loginPass) && !TextUtils.isEmpty(verifyPass) && !TextUtils.isEmpty(verificationCode) && !TextUtils.isEmpty(phoneNum)) {
                    if (loginPass.equals(verifyPass)) {
                        if (cbIsRead.isChecked()){
                            startToRegister(userName, invitationCode, phoneNum, contactPerson, loginPass);
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
        }
    }

    /**
     * 开始注册
     */
    private void startToRegister(String userName, String invitationCode, String phoneNum, String contactPerson, String pwd) {
        OkHttpPostRequestBuilder okHttpPostRequestBuilder = new OkHttpPostRequestBuilder(Constant.getHost() + API.CCFREGISTERS);
        okHttpPostRequestBuilder.put("strAccounts", userName);
        okHttpPostRequestBuilder.put("strDirectAccounts", invitationCode);
        okHttpPostRequestBuilder.put("Parentcode", contactPerson);
        okHttpPostRequestBuilder.put("telephone", phoneNum);
        okHttpPostRequestBuilder.put("pwd", pwd);
        okHttpPostRequestBuilder.put("code", code);
        OKHttpManager.post(okHttpPostRequestBuilder, "registerUser", new UiHandlerCallBack() {
            @Override
            public void success(JSONObject data) {
                Logger.json(data.toJSONString());
                toast("注册成功");
                startActivity(new Intent(getContext(), MainPageActivity.class));
                getActivity().finish();
            }

            @Override
            public void error(int status, String message) {
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

    }
}
