package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.activity.ResetPasswordActivity;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/10/25 0025.
 */

public class CallBackBySecurityQuestionFragment extends BaseFragment {
    @BindView(R.id.sp_one)
    Spinner spOne;
    @BindView(R.id.ct_answer_one)
    CleanEditText ctAnswerOne;
    @BindView(R.id.sp_two)
    Spinner spTwo;
    @BindView(R.id.ct_answer_two)
    CleanEditText ctAnswerTwo;
    @BindView(R.id.sp_three)
    Spinner spThree;
    @BindView(R.id.ct_answer_three)
    CleanEditText ctAnswerThree;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ct_user_name)
    CleanEditText ctUserName;
    private Unbinder unbinder;
    private String[] array_question = {};
    private ArrayAdapter<String> arr_adapter;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String questionOne;
    private String questionTwo;
    private String questionThree;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_back_by_security_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCheckEncrypted();
            }
        });
        setItemSelectListener();
        return view;
    }

    private void init() {
        //数据
        array_question = getResources().getStringArray(R.array.security_question);
        //适配器
        arr_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_question);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spOne.setAdapter(arr_adapter);
        spTwo.setAdapter(arr_adapter);
        spThree.setAdapter(arr_adapter);
    }

    private void setItemSelectListener() {
        spOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionOne = array_question[position];
//                Logger.d(questionOne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionTwo = array_question[position];
//                Logger.d(questionTwo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionThree = array_question[position];
//                Logger.d(questionThree);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void toCheckEncrypted() {
        gainQuestionAndAnswer();
        Logger.d("toCheckEncrypted");
        if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(answerOne) && !TextUtils.isEmpty(answerTwo) && !TextUtils.isEmpty(answerThree)) {
            if (questionOne != questionTwo && questionOne != questionThree && questionTwo != questionThree) {
                RequestParams params = new RequestParams();
                params.addFormDataPart("uCode",username);
                params.addFormDataPart("question1", questionOne);
                params.addFormDataPart("question2", questionTwo);
                params.addFormDataPart("question3", questionThree);
                params.addFormDataPart("value1", answerOne);
                params.addFormDataPart("value2", answerTwo);
                params.addFormDataPart("value3", answerThree);
                HttpRequest.post(Constant.getHost() + API.CCFVERFYENCRYPTED, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        toast("密保验证成功");
                        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        Logger.d(code + "\n" + msg);
                        toast("密保验证失败");
                    }
                });
            } else {
                toast("密保问题必须不一样");
            }
        } else {
            toast("输入框不能为空");
        }
    }

    private void gainQuestionAndAnswer() {
        answerOne = ctAnswerOne.getText().toString();
        answerTwo = ctAnswerTwo.getText().toString();
        answerThree = ctAnswerThree.getText().toString();
        username = ctUserName.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
