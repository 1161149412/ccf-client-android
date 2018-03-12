package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class SetEncryptedActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    private Unbinder unbinder;
    private String[] array_question = {};
    private ArrayAdapter<String> arr_adapter;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String questionOne;
    private String questionTwo;
    private String questionThree;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_encrypted);
        unbinder = ButterKnife.bind(this);
        init();
        setClickListener();
        setItemSelectListener();
    }


    private void setClickListener(){
        ibtBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    private void setItemSelectListener(){
        spOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionOne = array_question[position];
                Logger.d(questionOne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionTwo = array_question[position];
                Logger.d(questionTwo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionThree = array_question[position];
                Logger.d(questionThree);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void init() {
        tvTitle.setText("密保设置");
        user = JSONObject.parseObject(UserSharedPreference.getInstance().getUserInfo(),User.class);
        array_question = getResources().getStringArray(R.array.security_question);

        //适配器
        arr_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array_question);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spOne.setAdapter(arr_adapter);
        spTwo.setAdapter(arr_adapter);
        spThree.setAdapter(arr_adapter);

    }


    private void toSetEncrypted(){
        gainQuestionAndAnswer();
        if (!TextUtils.isEmpty(answerOne)&&!TextUtils.isEmpty(answerTwo)&&!TextUtils.isEmpty(answerThree)){
            if (questionOne!=questionTwo&&questionOne!=questionThree&&questionTwo!=questionThree){
                if (user!=null){
                    RequestParams params = new RequestParams();
                    params.addFormDataPart("uCode",user.getUserCode());
                    params.addFormDataPart("question1",questionOne);
                    params.addFormDataPart("question2",questionTwo);
                    params.addFormDataPart("question3",questionThree);
                    params.addFormDataPart("value1",answerOne);
                    params.addFormDataPart("value2",answerTwo);
                    params.addFormDataPart("value3",answerThree);
                    HttpRequest.post(Constant.getHost() + API.CCFSETENCRYPTED, params, new CCFHttpRequestCallback() {
                        @Override
                        protected void onDataSuccess(JSONObject data) {
                            showMessage("设置成功");
                            startActivity(new Intent(SetEncryptedActivity.this,SettingActivity.class));
                        }

                        @Override
                        protected void onDataError(int code, boolean flag, String msg) {
                            Logger.d(code+"\n"+msg);
                            showMessage(code,msg);
                        }
                    });
                }
            }else {
                Logger.d(questionOne+"\n"+questionTwo+"\n"+questionThree);
                toast("密保问题必须不一样");
            }
        }else {
            toast("答案不能为空");
        }
    }
    private void gainQuestionAndAnswer(){
        answerOne = ctAnswerOne.getText().toString();
        answerTwo = ctAnswerTwo.getText().toString();
        answerThree = ctAnswerThree.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibt_back:
                finish();
                break;
            case R.id.btn_ok:
                toSetEncrypted();
                break;
        }
    }
}
