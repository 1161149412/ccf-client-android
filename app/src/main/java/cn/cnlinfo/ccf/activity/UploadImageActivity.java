package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
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
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.net_okhttp.OKHttpManager;
import cn.cnlinfo.ccf.net_okhttp.OkHttp3Utils;
import cn.cnlinfo.ccf.net_okhttp.UiHandlerCallBack;

public class UploadImageActivity extends BaseActivity {

    @BindView(R.id.btn_upload_image)
    Button btnUploadImage;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Unbinder unbinder;
    //上传图片的结果码
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("上传图片");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.show(UploadImageActivity.this, REQUEST_CODE_SELECT_IMG,9);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
                OKHttpManager.getInstance().newCall(OkHttp3Utils.uploadMultiImage("http://www.ccfcc.cc/UserOperation.asmx/uploadingImg",null,"one",files)).enqueue(new UiHandlerCallBack() {
                    @Override
                    public void success(JSONObject data) {
                        Logger.d(data.toJSONString());
                        showMessage("上传成功");
                    }

                    @Override
                    public void error(int status, String message) {

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
