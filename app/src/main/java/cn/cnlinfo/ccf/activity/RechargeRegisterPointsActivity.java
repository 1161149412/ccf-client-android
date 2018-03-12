package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.net_okhttp.OKHttpManager;
import cn.cnlinfo.ccf.net_okhttp.OkHttp3Utils;
import cn.cnlinfo.ccf.net_okhttp.UiHandlerCallBack;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class RechargeRegisterPointsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_gathering_account)
    TextView tvGatheringAccount;
    @BindView(R.id.tv_image_url)
    TextView tvImageUrl;

    @BindView(R.id.et_transfer_number)
    CleanEditText etTransferNumber;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.btn_purchase_re_points)
    Button btnPurchaseRePoints;
    @BindView(R.id.btn_upload_image)
    Button btnUploadImage;
    private Unbinder unbinder;
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_register_points);
        unbinder = ButterKnife.bind(this);
        btnUploadImage.setOnClickListener(this);
        btnPurchaseRePoints.setOnClickListener(this);
        ibtBack.setOnClickListener(this);
        tvTitle.setText("购买注册积分");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_purchase_re_points:
                toPurchasePoints();
                break;
            case R.id.btn_upload_image:
                toUploadImage();
                break;
            case R.id.ibt_back:
                finish();
                default:
                    break;
        }
    }

    /**
     * 购买注册积分
     */
    private void toPurchasePoints(){
        String reNum = etTransferNumber.getText().toString();
        String safePass = etSafePass.getText().toString();
        if (!TextUtils.isEmpty(reNum)&&!TextUtils.isEmpty(safePass)){
            int re_points = Integer.valueOf(reNum);
            if (re_points>0){
                RequestParams params = new RequestParams();
                params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
                params.addFormDataPart("num",re_points);
                params.addFormDataPart("imgurl",tvImageUrl.getText().toString());
                params.addFormDataPart("pass",safePass);
                HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.PURCHASEREGISTERPOINTS, params, new CCFHttpRequestCallback() {
                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        showMessage(0,"购买成功");
                        startActivity(new Intent(RechargeRegisterPointsActivity.this,MainPageActivity.class));
                        finish();
                    }

                    @Override
                    protected void onDataError(int code, boolean flag, String msg) {
                        showMessage(code,msg);
                    }
                });
            }else {
                toast("注册积分数量不能小于0");
            }
        }else {
            showEditTextNoNull();
        }
    }
    /**
     * 上传图片
     */
    private void toUploadImage(){
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_SELECT_IMG){
            List<String> yourSelectImgPaths = ImageSelector.getImagePaths(data);
            List<File> files = new ArrayList<>();
            for (String yourSelectImgPath : yourSelectImgPaths) {
                File file = new File(yourSelectImgPath);
                files.add(file);
            }
            if (files!=null&&files.size()>0){
                OKHttpManager.getInstance().newCall(OkHttp3Utils.uploadMultiImage(Constant.GET_MESSAGE_CODE_HOST+ API.UPLOADIMAGE,null,"one",files)).enqueue(new UiHandlerCallBack() {
                    @Override
                    public void success(JSONObject data) {
                        Logger.d(data.toJSONString());
                        String imageUrl = data.getString("imgurl");
                        tvImageUrl.setText(imageUrl);
                        showMessage("上传成功");
                    }

                    @Override
                    public void error(int status, String message) {
                        showMessage(status,message);
                    }

                    @Override
                    public void progress(int progress) {

                    }

                    @Override
                    public void failed(int code, String msg) {
                        showMessage(code,msg);
                    }
                });
            }
        }
    }
}
