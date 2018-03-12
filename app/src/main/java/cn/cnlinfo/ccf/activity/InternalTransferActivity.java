package cn.cnlinfo.ccf.activity;

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
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.UserMoneyEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.SpinnerUtils;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class InternalTransferActivity extends BaseActivity {
    /**
     * :[3:注册积分转产品积分,4:注册积分转消费积分,5:消费积分转产品积分,6:消费积分转碳控因子,
     * --,7:碳控因子转产品积分,8:碳控因子转消费积分,9:碳控积分转产品积分,10:产品积分转消费积分]
     */
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sp_transfer_type)
    Spinner spTransferType;
    @BindView(R.id.et_transfer_number)
    CleanEditText etTransferNumber;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_register_integral)
    TextView tvRegisterIntegral;
    @BindView(R.id.tv_consume_integral)
    TextView tvConsumeIntegral;
    @BindView(R.id.tv_cycle_integral)
    TextView tvCycleIntegral;
    @BindView(R.id.tv_ccf_number)
    TextView tvCcfNumber;
    @BindView(R.id.tv_product_points)
    TextView tvProductPoints;
    private String num;
    private String safePass;
    private int typeId;
    private Unbinder unbinder;
    private String[] transferType = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_transfer);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("内部互转");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTransfer();
            }
        });

    }


    //获取当前用户的ccf数量和实时价格,积分
    private void getCurrentUserIntegral() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETUSERINTEGAL, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                UserMoneyEvent userMoney = JSONObject.parseObject(data.getJSONObject("UserMoney").toJSONString(), UserMoneyEvent.class);
                tvRegisterIntegral.setText(userMoney.getRegisterIntegral());//注册积分
                tvCcfNumber.setText(userMoney.getCCF());//碳控因子
                tvCycleIntegral.setText(userMoney.getCircleTicketScore());//循环积分
                tvConsumeIntegral.setText(userMoney.getConsumeIntegral());//碳控积分
                tvProductPoints.setText(userMoney.getProductScore());//产品积分
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

    //进行内部转换
    private void toTransfer() {
        num = etTransferNumber.getText().toString();
        safePass = etSafePass.getText().toString();
        if (!TextUtils.isEmpty(num) && !TextUtils.isEmpty(safePass)) {
            int number = Integer.valueOf(num);
            if (number > 0) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("type", typeId);
                params.addFormDataPart("sendID", UserSharedPreference.getInstance().getUser().getUserID());
                params.addFormDataPart("receivecode", UserSharedPreference.getInstance().getUser().getUserCode());
                params.addFormDataPart("num", num);
                params.addFormDataPart("pass", safePass);
                HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.USERTRANSFER, params, new CCFHttpRequestCallback() {

                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        showMessage(0, "互转成功");
                        finish();
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showMessage(code, msg);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        showMessage(errorCode, msg);
                    }
                });
            } else {
                toast("数量不能小于0");
            }
        } else {
            toast("输入框不能为空");
        }
    }

    private void init() {
        getCurrentUserIntegral();
        transferType = getResources().getStringArray(R.array.spinner_internal_transfer);
        spTransferType.setAdapter(SpinnerUtils.getArrayAdapter(this, transferType));
        spTransferType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (transferType[position]) {
                    case "注册积分转产品积分":
                        typeId = 3;
                        break;
                    case "注册积分转消费积分":
                        typeId = 4;
                        break;
                    case "消费积分转产品积分":
                        typeId = 5;
                        break;
                    case "消费积分转碳控因子":
                        typeId = 6;
                        break;
                    case "碳控因子转产品积分":
                        typeId = 7;
                        break;
                    case "碳控因子转消费积分":
                        typeId = 8;
                        break;
                    case "碳控积分转产品积分":
                        typeId = 9;
                        break;
                    case "产品积分转消费积分":
                        typeId = 10;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST + API.USERTRANSFER);
        HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETUSERINTEGAL);
    }
}
