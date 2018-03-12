package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.activity.WebActivity;
import cn.cnlinfo.ccf.entity.AccountInfo;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.province_city_zone.callback.GainAreaCallBack;
import cn.cnlinfo.ccf.province_city_zone.entity.City;
import cn.cnlinfo.ccf.province_city_zone.entity.Districts;
import cn.cnlinfo.ccf.province_city_zone.entity.Province;
import cn.cnlinfo.ccf.province_city_zone.popup_window.ChooseAreaPopup;
import cn.cnlinfo.ccf.utils.SpinnerUtils;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/11/20 0020.
 */

public class AgencyUpgradeFragment extends BaseFragment {

    @BindView(R.id.tv_my_rank)
    TextView tvMyRank;
    @BindView(R.id.sp_agency_type)
    Spinner spAgencyType;
    @BindView(R.id.sp_service_type)
    Spinner spServiceType;
    @BindView(R.id.tv_agency_address)
    TextView tvAgencyAddress;
    @BindView(R.id.btn_upgrade_agency)
    Button btnUpgradeAgency;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.cb_is_read)
    CheckBox cbIsRead;
    @BindView(R.id.tv_upgrade_agency_link)
    TextView tvUpgradeAgencyLink;
    @BindView(R.id.et_service_user)
    CleanEditText etServiceUser;
    private Unbinder unbinder;
    private AccountInfo accountInfo;
    private String safePass;
    private ProvinceBean provinceBean;
    private CityBean cityBean;
    private DistrictBean districtBean;
    private Province province;
    private City city;
    private Districts districts;
    private String agencyType = "1";//代理服务默认为第一种类型
    private String serviceType = "1";
    private PopupWindow popupWindow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_upgrade, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        btnUpgradeAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUpgradeAgency();
            }
        });
        return view;
    }

    /**
     * 初始化操作
     */
    private void init() {
        CharSequence charSequence = Html.fromHtml("已同意并愿意接受:<a href=\"http://www.ccfcc.cc/XY.aspx\">用户协议");
        tvUpgradeAgencyLink.setText(charSequence);
        tvUpgradeAgencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", "http://www.ccfcc.cc/XY.aspx");
                startActivity(intent);
            }
        });
        accountInfo = UserSharedPreference.getInstance().getAccount();
        int level = accountInfo.getInLevel();
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
        if (level == 0 && accountInfo.getTotalMealWeight() > 0) {
            tvMyRank.setText("普通用户");
        }

        spAgencyType.setAdapter(SpinnerUtils.getArrayAdapter(getActivity(), getResources().getStringArray(R.array.agency_type)));
        spAgencyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                agencyType = String.valueOf(position + 1);
                tvAgencyAddress.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spServiceType.setAdapter(SpinnerUtils.getArrayAdapter(getActivity(), getResources().getStringArray(R.array.service_type)));
        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceType = String.valueOf(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvAgencyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 /*               CityPickerView cityPickerView = CityPickerUtils.showCityPicker(getActivity(), 0);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean districtBean) {
                        tvAgencyAddress.setText(provinceBean.getName() + "-" + cityBean.getName() + "-" + districtBean.getName());
                        AgencyUpgradeFragment.this.provinceBean = provinceBean;
                        AgencyUpgradeFragment.this.cityBean = cityBean;
                        AgencyUpgradeFragment.this.districtBean = districtBean;
                    }

                    @Override
                    public void onCancel() {

                    }
                });*/
                popupWindow = ChooseAreaPopup.getInstance(AgencyUpgradeFragment.this.getActivity()).showPupopWindow(v,new GainAreaCallBack() {
                    @Override
                    public void gainArea(Province province, City city, Districts districts) {
                        switch (agencyType) {
                            case "1":
                                tvAgencyAddress.setText(province.getProvinceName());
                                break;
                            case "2":
                                tvAgencyAddress.setText(province.getProvinceName() + "-" + city.getCityName());
                                break;
                            case "3":
                                tvAgencyAddress.setText(province.getProvinceName() + "-" + city.getCityName() + "-" + districts.getDistrictsName());
                                break;
                            case "4":
                                tvAgencyAddress.setText(province.getProvinceName() + "-" + city.getCityName() + "-" + districts.getDistrictsName());
                                break;
                        }
                        AgencyUpgradeFragment.this.province = province;
                        AgencyUpgradeFragment.this.city = city;
                        AgencyUpgradeFragment.this.districts = districts;
                    }
                });

            }
        });
    }

    //进行代理升级
    private void toUpgradeAgency() {
        if (cbIsRead.isChecked()) {
            safePass = etSafePass.getText().toString();
            String serviceUser  = etServiceUser.getText().toString();
            if (!TextUtils.isEmpty(safePass)&&!TextUtils.isEmpty(serviceUser) && !TextUtils.isEmpty(tvAgencyAddress.getText().toString())) {
                String regions = null;
                switch (agencyType) {
                    case "1":
                        regions = province.getId();
                        break;
                    case "2":
                        regions = province.getId() + "-" + city.getCityId();
                        break;
                    case "3":
                        regions = province.getId() + "-" + city.getCityId() + "-" + districts.getDistrictsId();
                        break;
                    case "4":
                        regions = province.getId() + "-" + city.getCityId() + "-" + districts.getDistrictsId();
                        break;
                }
                RequestParams params = new RequestParams();
                //代理类型  1是省代2是市代3是县代4是服务中心
                //服务类型 1是商城消费者2是商城商家3是联盟消费者4是联盟商家
                params.addFormDataPart("userID", UserSharedPreference.getInstance().getUser().getUserID());
                params.addFormDataPart("proxyType", agencyType);
                params.addFormDataPart("serveType", serviceType);
                params.addFormDataPart("area", tvAgencyAddress.getText().toString());
                params.addFormDataPart("servantusername",serviceUser);
                params.addFormDataPart("regions", regions);
                params.addFormDataPart("pwd", safePass);
                HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.UPDATETOAGENCY, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        toast("成功升级成为代理");
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
            } else {
                toast("输入框不能为空");
            }
        } else {
            toast("请仔细阅读并同意代理协议");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        HttpRequest.cancel(Constant.GET_MESSAGE_CODE_HOST + API.UPDATETOAGENCY);
        super.onDestroyView();
    }
}
