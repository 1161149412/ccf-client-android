package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.utils.CityPickerUtils;
import cn.cnlinfo.ccf.utils.SpinnerUtils;
import cn.cnlinfo.ccf.view.CleanEditText;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class RegisterAgencyFragment extends BaseFragment {
    @BindView(R.id.et_invitation_code)
    CleanEditText etInvitationCode;
    @BindView(R.id.et_user_name)
    CleanEditText etUserName;
    @BindView(R.id.et_share_user)
    CleanEditText etShareUser;
    @BindView(R.id.et_service_user)
    CleanEditText etServiceUser;
    @BindView(R.id.et_agency_service)
    CleanEditText etAgencyService;
    @BindView(R.id.sp_agency_type)
    Spinner spAgencyType;
    @BindView(R.id.et_login_pass)
    CleanEditText etLoginPass;
    @BindView(R.id.et_verify_pass)
    CleanEditText etVerifyPass;
    @BindView(R.id.et_phone_num)
    CleanEditText etPhoneNum;
    @BindView(R.id.et_verification_code_register)
    CleanEditText etVerificationCodeRegister;
    @BindView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R.id.btn_register_agency)
    Button btnRegisterAgency;
    Unbinder unbinder;
    @BindView(R.id.sp_service_type)
    Spinner spServiceType;
    private static final int PROVINCEAGENCYTYPE = 0;
    private static final int CITYAGENCYTYPE = 1;
    private static final int ZONEAGENCYTYPE = 2;
    String[] agencyTypeArray = null;
    String[] agencyServiceType = null;
    @BindView(R.id.tv_agency_address)
    TextView tvAgencyAddress;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_register_agency);
        unbinder = ButterKnife.bind(this,getContentView());
        init();
    }

    private void init() {
        agencyTypeArray = getActivity().getResources().getStringArray(R.array.agency_type);
        agencyServiceType = getActivity().getResources().getStringArray(R.array.service_type);
        ArrayAdapter agencyTypeAdapter = SpinnerUtils.getArrayAdapter(getActivity(), agencyTypeArray);
        ArrayAdapter agencyServiceTypeAdapter = SpinnerUtils.getArrayAdapter(getActivity(), agencyServiceType);
        spAgencyType.setAdapter(agencyTypeAdapter);
        spServiceType.setAdapter(agencyServiceTypeAdapter);

        spAgencyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case PROVINCEAGENCYTYPE:
                        tvAgencyAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              CityPickerView cityPickerView =  CityPickerUtils.showCityPicker(getActivity(), PROVINCEAGENCYTYPE);
                              cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                                  @Override
                                  public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean districtBean) {
                                      Logger.d(provinceBean.getId()+"-"+provinceBean.getName());
                                      tvAgencyAddress.setText(provinceBean.getName());
                                  }

                                  @Override
                                  public void onCancel() {

                                  }
                              });
                            }
                        });

                        break;
                    case CITYAGENCYTYPE:
                        tvAgencyAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CityPickerView cityPickerView = CityPickerUtils.showCityPicker(getActivity(), CITYAGENCYTYPE);
                                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                                    @Override
                                    public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean districtBean) {
                                        Logger.d(cityBean.getId()+"-"+cityBean.getName());
                                        tvAgencyAddress.setText(cityBean.getName());
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                            }
                        });

                        break;
                    case ZONEAGENCYTYPE:
                        tvAgencyAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CityPickerView cityPickerView = CityPickerUtils.showCityPicker(getActivity(), ZONEAGENCYTYPE);
                                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                                    @Override
                                    public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean districtBean) {
                                        Logger.d(districtBean.getId()+"-"+districtBean.getName());
                                        tvAgencyAddress.setText(districtBean.getName());
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                            }
                        });

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

}
