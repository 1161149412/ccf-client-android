package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.SetPersonInfo;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.EditTextInputFormatUtil;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class SetBasicInfoActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.et_phone_num)
    TextView etPhoneNum;
    @BindView(R.id.et_id_number)
    CleanEditText etIdNumber;
    @BindView(R.id.et_choose_bank)
    CleanEditText etChooseBank;
    @BindView(R.id.et_account_address)
    CleanEditText etAccountAddress;
    @BindView(R.id.et_bank_card_number)
    CleanEditText etBankCardNumber;
    @BindView(R.id.et_alipay_account_number)
    CleanEditText etAlipayAccountNumber;
    @BindView(R.id.et_alipay_nickname)
    CleanEditText etAlipayNickname;
    @BindView(R.id.et_wechat_nickname)
    CleanEditText etWechatNickname;
    @BindView(R.id.et_wechat_number)
    CleanEditText etWechatNumber;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.sp_certificate_type)
    Spinner spCertificateType;
    private Unbinder unbinder;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("设置个人基本信息");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gainAndSetInfo();
        setSpinnerItemListener();

    }

    //设置spinner监听器
    private void setSpinnerItemListener() {
        spCertificateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SetBasicInfoActivity.this.position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //获取并设置个人信息
    private void gainAndSetInfo() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETSETPERSONINFO, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                SetPersonInfo setPersonInfo = JSONObject.parseObject(data.getJSONObject("Personalinfos").toJSONString(), SetPersonInfo.class);
                if (setPersonInfo != null) {
                    etName.setText(setPersonInfo.getUserName());
                    etPhoneNum.setText(setPersonInfo.getPhone());
                    spCertificateType.setSelection(Integer.parseInt(setPersonInfo.getIDtype()));
                    etIdNumber.setText(setPersonInfo.getIDcard());
                    etChooseBank.setText(setPersonInfo.getBankName());
                    etAccountAddress.setText(setPersonInfo.getBankAddress());
                    etBankCardNumber.setText(setPersonInfo.getBankCode());
                    etAlipayAccountNumber.setText(setPersonInfo.getAliPay());
                    etAlipayNickname.setText(setPersonInfo.getAliPayName());
                    etWechatNumber.setText(setPersonInfo.getWebChat());
                    etWechatNickname.setText(setPersonInfo.getWebChatName());
                }
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                EventBus.getDefault().post(new ErrorMessageEvent(code, msg));
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                EventBus.getDefault().post(new ErrorMessageEvent(errorCode, msg));
            }
        });
    }

    //设置个人基本信息
    @OnClick(R.id.btn_setting)
    public void onClick(View view){
        if (!TextUtils.isEmpty(etName.getText().toString())&&!TextUtils.isEmpty(etPhoneNum.getText().toString())&&
                !TextUtils.isEmpty(etIdNumber.getText().toString())&&!TextUtils.isEmpty(etChooseBank.getText().toString()) &&
                !TextUtils.isEmpty(etAccountAddress.getText().toString())&& !TextUtils.isEmpty(etBankCardNumber.getText().toString())&&
                !TextUtils.isEmpty(etAlipayAccountNumber.getText().toString()) &&!TextUtils.isEmpty(etAlipayNickname.getText().toString())&&
                !TextUtils.isEmpty(etWechatNumber.getText().toString())&& !TextUtils.isEmpty(etWechatNickname.getText().toString())){
            if (!EditTextInputFormatUtil.isLegalPhoneNum(etPhoneNum.getText().toString())){
                toast(getResources().getString(R.string.id_tip));
                return;
            }
            if (!EditTextInputFormatUtil.isLegalId(etIdNumber.getText().toString())){
                toast(getResources().getString(R.string.id_tip));
                return;
            }
            if (!EditTextInputFormatUtil.isBankCard(etBankCardNumber.getText().toString())){
                toast(getResources().getString(R.string.bank_num_tip));
                return;
            }
            RequestParams params = new RequestParams();
            params.addFormDataPart("userid",UserSharedPreference.getInstance().getUser().getUserID());
            params.addFormDataPart("username",etName.getText().toString());
            params.addFormDataPart("phone",etPhoneNum.getText().toString());
            params.addFormDataPart("idtype",position);
            params.addFormDataPart("idcard",etIdNumber.getText().toString());
            params.addFormDataPart("bankname",etChooseBank.getText().toString());
            params.addFormDataPart("bankaddress",etAccountAddress.getText().toString());
            params.addFormDataPart("bankcode",etBankCardNumber.getText().toString());
            params.addFormDataPart("alipay",etAlipayAccountNumber.getText().toString());
            params.addFormDataPart("alipayname",etAlipayNickname.getText().toString());
            params.addFormDataPart("webchat",etWechatNumber.getText().toString());
            params.addFormDataPart("webchatname",etWechatNickname.getText().toString());
            HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.UPDATEPERSIONINFO, params, new CCFHttpRequestCallback() {
                @Override
                protected void onDataSuccess(JSONObject data) {
                    toast("更新成功");
                    finish();
                }

                @Override
                protected void onDataError(int code, boolean flag, String msg) {
                    EventBus.getDefault().post(new ErrorMessageEvent(code, msg));
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    super.onFailure(errorCode, msg);
                    EventBus.getDefault().post(new ErrorMessageEvent(errorCode, msg));
                }
            });

        }else {
            showEditTextNoNull();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETSETPERSONINFO);
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST + API.UPDATEPERSIONINFO);
        unbinder.unbind();
    }
}
