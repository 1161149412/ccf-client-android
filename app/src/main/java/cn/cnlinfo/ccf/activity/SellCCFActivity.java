package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.JobberInfoCCFEntity;
import cn.cnlinfo.ccf.event.CloseActivityEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//出售ccf
public class SellCCFActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_order_info)
    TextView tvOrderInfo;
    @BindView(R.id.ct_safe_pass)
    CleanEditText ctSafePass;
    private Unbinder unbinder;
    private String roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_ccf);
        unbinder = ButterKnife.bind(this);
        roomId = getIntent().getStringExtra("roomId");
        Logger.d(roomId);
        tvTitle.setText("出售碳控因子");
        getPurCCFInfo();

    }

    /**
     * 出售碳控因子
     */
    private void sellCCF() {
        if (!TextUtils.isEmpty(ctSafePass.getText().toString())) {
            String pass = ctSafePass.getText().toString();
                    RequestParams params = new RequestParams();
                    params.addFormDataPart("sellerID", UserSharedPreference.getInstance().getUser().getUserID());
                    params.addFormDataPart("roomID", roomId);
                    params.addFormDataPart("pwd", pass);
                    HttpRequest.post(Constant.OPERATE_CCF_HOST + API.CLICKSELL, params, new CCFHttpRequestCallback() {
                        @Override
                        protected void onDataSuccess(JSONObject data) {
                            showMessage(0, "订单提交成功,请确认收款!");
                            startActivity(new Intent(SellCCFActivity.this, OrderCenterActivity.class));
                            EventBus.getDefault().post(new CloseActivityEvent(0));
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
            //显示编辑框为空
            showEditTextNoNull();
        }
    }

    /**
     * 获取该订单详细展示的信息
     */
    private void getPurCCFInfo() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("JobberId", roomId);
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETJOBBERINFO, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                JobberInfoCCFEntity jobberInfo = JSONObject.parseObject(data.getJSONObject("JobberInfo").toJSONString(), JobberInfoCCFEntity.class);
                if (jobberInfo!=null){
                    tvUserLevel.setText(jobberInfo.getBusinessLev());
                    tvUserName.setText(jobberInfo.getUserName());
                    tvOrderInfo.setText(String.format(tvOrderInfo.getText().toString(),jobberInfo.getCCF(),jobberInfo.getPrice()));
                }
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
    }

    @OnClick({R.id.ibt_back, R.id.btn_confirm_sell})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ibt_back:
                finish();
                break;
            case R.id.btn_confirm_sell:
                sellCCF();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETJOBBERINFO);
    }
}
