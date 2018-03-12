package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public class TraderUpgradeFragment extends BaseFragment {

    @BindView(R.id.tv_my_rank)
    TextView tvMyRank;
    @BindView(R.id.sp_trader_type)
    Spinner spTraderType;
    @BindView(R.id.btn_upgrade_trader)
    Button btnUpgradeTrader;
    Unbinder unbinder;
    private int traderType = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trader_upgrade, container, false);
        unbinder = ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init(){
        int level = UserSharedPreference.getInstance().getAccount().getInLevel();
        switch (level) {
            case 0:
                tvMyRank.setText("体验用户");
                break;
            case 1:
                tvMyRank.setText("1星用户");
                break;
            case 2:
                tvMyRank.setText("2星用户");
                break;
            case 3:
                tvMyRank.setText("3星用户");
                break;
            case 4:
                tvMyRank.setText("4星用户");
                break;
            case 5:
                tvMyRank.setText("5星用户");
                break;

        }
        if (level == 0 && UserSharedPreference.getInstance().getAccount().getTotalMealWeight() > 0) {
            tvMyRank.setText("普通用户");
        }
        spTraderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                traderType = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUpgradeTrader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    params.addFormDataPart("userID", UserSharedPreference.getInstance().getUser().getUserID());
                    params.addFormDataPart("businessLev", traderType);
                    HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.UPGRADETOBETRADER, params, new CCFHttpRequestCallback() {
                        @Override
                        protected void onDataSuccess(JSONObject data) {
                            toast("成功升级成为交易商");
                            getActivity().finish();
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
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
