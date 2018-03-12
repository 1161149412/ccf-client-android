package cn.cnlinfo.ccf.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.utils.QRCodeUtil;

public class BuildQRCodeActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    @BindView(R.id.iv_qrCode)
    ImageView ivQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_qrcode);
        ButterKnife.bind(this);
        tvTitle.setText("我的二维码");
        ibtAdd.setVisibility(View.INVISIBLE);
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ccf);
        User user = UserSharedPreference.getInstance().getUser();
        Bitmap bitmap = QRCodeUtil.buildQRCode("http://www.ccfcc.cc/regUsers.aspx?pm="+user.getInvitationCode(), 164, 164, logo);
        ivQrCode.setImageBitmap(bitmap);
    }
}
