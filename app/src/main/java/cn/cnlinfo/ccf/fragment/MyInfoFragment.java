package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.entity.UserDetail;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class MyInfoFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_user_type)
    TextView tvUserType;
    @BindView(R.id.tv_share_user)
    TextView tvShareUser;
    @BindView(R.id.tv_service_user)
    TextView tvServiceUser;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_paper_type)
    TextView tvPaperType;
    @BindView(R.id.tv_paper_num)
    TextView tvPaperNum;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_account_address)
    TextView tvAccountAddress;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.tv_alipay_account)
    TextView tvAlipayAccount;
    @BindView(R.id.tv_alipay_name)
    TextView tvAlipayName;
    @BindView(R.id.tv_wx_nickname)
    TextView tvWxNickname;
    @BindView(R.id.tv_wx_num)
    TextView tvWxNum;
    Unbinder unbinder;
    @BindView(R.id.tv_agency_type)
    TextView tvAgencyType;
    private User user;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_my_info);
        unbinder = ButterKnife.bind(this, getContentView());
        initData();
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }*/

    /**
     * 初始化个人信息数据
     */
    private void initData() {
        showWaitingDialog(true);
        user = UserSharedPreference.getInstance().getUser();
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", user.getUserID());
        HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.GETPERSONINFO, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                UserDetail userDetail = JSONObject.parseObject(data.getJSONObject("userdetail").toJSONString(), UserDetail.class);
                if (userDetail != null) {
                    if (!TextUtils.isEmpty(userDetail.getName())) {
                        tvName.setText(userDetail.getName());
                    }
                    if (!TextUtils.isEmpty(userDetail.getUserCode())) {
                        tvUsername.setText(userDetail.getUserCode());
                    }
                    if (!TextUtils.isEmpty(userDetail.getInvitationCode())) {
                        tvInvitationCode.setText(userDetail.getInvitationCode());
                    }
                    if (!TextUtils.isEmpty(userDetail.getNickName())) {
                        tvNickName.setText(userDetail.getNickName());
                    }
                    if (!TextUtils.isEmpty(userDetail.getUtype())) {
                        tvUserType.setText(userDetail.getUtype());
                    }
                    if (!TextUtils.isEmpty(userDetail.getUserType())){
                        tvAgencyType.setText(userDetail.getUserType());
                    }
                    if (!TextUtils.isEmpty(userDetail.getDirectID())) {
                        tvShareUser.setText(userDetail.getDirectID());
                    }
                    if (!TextUtils.isEmpty(userDetail.getServantID())) {
                        tvServiceUser.setText(userDetail.getServantID());
                    }
                    if (!TextUtils.isEmpty(userDetail.getMobile())) {
                        tvPhoneNum.setText(userDetail.getMobile());
                    }
                    if (!TextUtils.isEmpty(userDetail.getIDtype())) {
                        tvPaperType.setText(userDetail.getIDtype());
                    }
                    if (!TextUtils.isEmpty(userDetail.getIDcard())) {
                        tvPaperNum.setText(userDetail.getIDcard());
                    }
                    if (!TextUtils.isEmpty(userDetail.getBankName())) {
                        tvBankName.setText(userDetail.getBankName());
                    }
                    if (!TextUtils.isEmpty(userDetail.getBankAddress())) {
                        tvAccountAddress.setText(userDetail.getBankAddress());
                    }
                    if (!TextUtils.isEmpty(userDetail.getBankCode())) {
                        tvBankNum.setText(userDetail.getBankCode());
                    }
                    if (!TextUtils.isEmpty(userDetail.getAliPayName())) {
                        tvAlipayName.setText(userDetail.getAliPayName());
                    }
                    if (!TextUtils.isEmpty(userDetail.getAliPay())) {
                        tvAlipayAccount.setText(userDetail.getAliPay());
                    }
                    if (!TextUtils.isEmpty(userDetail.getWebChatName())) {
                        tvWxNickname.setText(userDetail.getWebChatName());
                    }
                    if (!TextUtils.isEmpty(userDetail.getWebChat())) {
                        tvWxNum.setText(userDetail.getWebChat());
                    }
                }
                showWaitingDialog(false);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                showMessage(code, msg);
                showWaitingDialog(false);
            }
        });
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        showWaitingDialog(false);
    }

}
