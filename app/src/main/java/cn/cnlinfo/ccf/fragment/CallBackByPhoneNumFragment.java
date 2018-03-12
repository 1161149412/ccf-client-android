package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.activity.ResetPasswordActivity;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.EditTextInputFormatUtil;
import cn.cnlinfo.ccf.utils.GetMessageCode;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by JP on 2017/10/25 0025.
 */

public class CallBackByPhoneNumFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.et_phone_num)
    CleanEditText etPhoneNum;
    @BindView(R.id.et_verification_code_callback)
    CleanEditText etVerificationCodeCallback;
    @BindView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.et_user_code)
    CleanEditText etUserCode;
    private Unbinder unbinder;
    private String phoneNum;
    private String verifyCode;
    private String code;
    private String userCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_back_by_phone_num, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerOnClickListener();
        return view;
    }

    /**
     * 获取短信验证码
     */
    private void gainMessageCode() {
        phoneNum = etPhoneNum.getText().toString();
        if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11 && EditTextInputFormatUtil.isLegalPhoneNum(phoneNum)) {
                GetMessageCode.startTimer(btnGetVerificationCode);
                RequestParams params = new RequestParams();
                params.addFormDataPart("phone", phoneNum);
                params.addFormDataPart("codeid", 3143331);
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
    }

    /**
     * 去找回密码
     */
    private void toCallBackPassword() {
        initEditData();
        if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(verifyCode)&&!TextUtils.isEmpty(userCode)) {
            RequestParams params = new RequestParams();
            params.addFormDataPart("phone", phoneNum);
            params.addFormDataPart("code", code);
            params.addFormDataPart("usercode",userCode);
            HttpRequest.post(Constant.getHost() + API.RETRIEVEPASSWORD, params, new CCFHttpRequestCallback() {
                        @Override
                        protected void onDataSuccess(JSONObject data) {
                            String username = data.getString("uCode");
                            Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        protected void onDataError(int code, boolean flag, String msg) {
                            showMessage(code, msg);
                        }
                    }
            );
        } else {
            toast("输入框不能为空!");
        }
    }

    private void initEditData() {
        phoneNum = etPhoneNum.getText().toString();
        verifyCode = etVerificationCodeCallback.getText().toString();
        userCode = etUserCode.getText().toString();
    }

    private void registerOnClickListener() {
        btnGetVerificationCode.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verification_code:
                gainMessageCode();
                break;
            case R.id.btn_ok:
                toCallBackPassword();
                break;
            default:
                break;
        }
    }
}
