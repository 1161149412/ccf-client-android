package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.UserMoneyEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.EditTextInputFormatUtil;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class ForeignTransferActivity extends BaseActivity {
    /**
     * [1:碳控因子,2:注册积分,11:消费积分]  定义转换积分的类型编码
     */
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sp_transfer_type)
    Spinner spTransferType;
    @BindView(R.id.et_user_account)
    CleanEditText etUserAccount;
    @BindView(R.id.et_transfer_number)
    CleanEditText etTransferNumber;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_register_integral)
    TextView tvRegisterIntegral;
    @BindView(R.id.tv_ccf_number)
    TextView tvCcfNumber;
    @BindView(R.id.tv_consumption_points_number)
    TextView tvConsumptionPointsNumber;

    private Unbinder unbinder;
    private User user;
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_transfer);
        unbinder = ButterKnife.bind(this);
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("对外互转");
        initData();
    }

    //获取当前用户的ccf数量和实时价格,积分
    private void getCurrentUserIntegral() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETUSERINTEGAL, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                UserMoneyEvent userMoney = JSONObject.parseObject(data.getJSONObject("UserMoney").toJSONString(), UserMoneyEvent.class);
                tvRegisterIntegral.setText(userMoney.getRegisterIntegral());
                tvCcfNumber.setText(userMoney.getCCF());
                tvConsumptionPointsNumber.setText(userMoney.getConsumeIntegral());
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

    //初始化操作
    private void initData() {
        getCurrentUserIntegral();
        user = UserSharedPreference.getInstance().getUser();
        final String[] foreignTransferArray = getResources().getStringArray(R.array.spinner_foreign_transfer);
        spTransferType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (foreignTransferArray[position]) {
                    case "碳控因子":
                        typeId = 1;
                        break;
                    case "注册积分":
                        typeId = 2;
                        break;
                    case "消费积分":
                        typeId = 11;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTransfer();
            }
        });
    }

    /**
     * 检查输入框是否为空 对外互转
     *
     * @return
     */
    private void toTransfer() {
        showWaitingDialog(true);
        String accounrNum = etUserAccount.getText().toString();
        String transNum = etTransferNumber.getText().toString();
        String safePass = etSafePass.getText().toString();
        if (accounrNum.length() == 11) {
            if (!EditTextInputFormatUtil.isLegalPhoneNum(accounrNum)) {
                showWaitingDialog(false);
                return;
            }
        }
        if (!TextUtils.isEmpty(accounrNum) && !TextUtils.isEmpty(transNum) && !TextUtils.isEmpty(safePass)) {
            if (accounrNum.equals(user.getUserCode()) || accounrNum.equals(user.getMobile())) {
                toast("不能是自己的用户名或电话号码，请重新输入!!!");
                showWaitingDialog(false);
                return;
            }
            int transferNum = Integer.valueOf(transNum);
            if (transferNum > 0) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("type", typeId);
                params.addFormDataPart("sendID", user.getUserID());
                params.addFormDataPart("receivecode", accounrNum);
                params.addFormDataPart("num", transferNum);
                params.addFormDataPart("pass", safePass);
                HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.USERTRANSFER, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        showMessage(0, "转出成功");
                        showWaitingDialog(false);
                        startActivity(new Intent(ForeignTransferActivity.this, ExternalTransferRecordActivity.class));
                        finish();
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showWaitingDialog(false);
                        showMessage(code, msg);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        showMessage(errorCode, msg);
                        showWaitingDialog(false);
                    }
                });
            } else {
                showWaitingDialog(false);
                showMessage("数量不能小于0");
            }
        } else {
            showWaitingDialog(false);
            showEditTextNoNull();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST + API.USERTRANSFER);
        HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETUSERINTEGAL);
    }
}
