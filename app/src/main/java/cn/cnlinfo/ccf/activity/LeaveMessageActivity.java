package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
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
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class LeaveMessageActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_seller_complains_buyer)
    TextView tvSellerComplainsBuyer;
    @BindView(R.id.et_help_feedback)
    CleanEditText mEtHelpFeedback;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_leave_message);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("留言中心");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //提交意见建议
    private void submitAdvise() {
        if (!TextUtils.isEmpty(mEtHelpFeedback.getText().toString())){
            RequestParams params = new RequestParams();
            params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
            params.addFormDataPart("msg",mEtHelpFeedback.getText().toString());
            HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.USERLEAVEMESSAGE, params, new CCFHttpRequestCallback() {
                @Override
                protected void onDataSuccess(JSONObject data) {
                    toast("留言成功");
                    finish();
                }

                @Override
                protected void onDataError(int code, boolean flag, String msg) {
                    EventBus.getDefault().post(new ErrorMessageEvent(code,msg));
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    super.onFailure(errorCode, msg);
                    EventBus.getDefault().post(new ErrorMessageEvent(errorCode,msg));
                }
            });
        }else {
            showEditTextNoNull();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST+ API.USERLEAVEMESSAGE);
        unbinder.unbind();
    }

    @OnClick({R.id.btn_pro_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_pro_submit:
                submitAdvise();
                break;
        }
    }
}
