package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by JP on 2017/11/6 0006.
 */

public class RegisterCustomerFragment extends BaseFragment {
    @BindView(R.id.et_invitation_code)
    CleanEditText etInvitationCode;
    @BindView(R.id.et_user_name)
    CleanEditText etUserName;
    @BindView(R.id.et_share_user)
    CleanEditText etShareUser;
    @BindView(R.id.et_service_user)
    CleanEditText etServiceUser;
    @BindView(R.id.et_agency_service)
    EditText etAgencyService;
    @BindView(R.id.sp_customer_type)
    Spinner spCustomerType;
    @BindView(R.id.et_login_pass)
    CleanEditText etLoginPass;
    @BindView(R.id.et_affirm_pass)
    EditText etAffirmPass;
    @BindView(R.id.et_phone_num)
    CleanEditText etPhoneNum;
    @BindView(R.id.et_verification_code)
    CleanEditText etVerificationCode;
    @BindView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R.id.btn_register_customer)
    Button btnRegisterCustomer;
    Unbinder unbinder;
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_register_customer);
        unbinder = ButterKnife.bind(this,getContentView());
        init();
    }

    private void init() {
        String[] userType = getActivity().getResources().getStringArray(R.array.user_type);
        ArrayAdapter<String> userTypeAdapter = SpinnerUtils.getArrayAdapter(getActivity(), userType);
        spCustomerType.setAdapter(userTypeAdapter);
        tvCustomerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CityPickerView cityPickerView = CityPickerUtils.showCityPicker(getActivity(),-1);
                //监听方法，获取选择结果
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        Logger.d(province.getId()+"-"+city.getId()+"-"+district.getId());
                        tvCustomerAddress.setText(province.getName()+"-"+city.getName()+"-"+district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

}
