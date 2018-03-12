package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.utils.FileUtil;

public class PreviewSaveActivity extends BaseActivity {

    @BindView(R.id.iv_icon)
    PhotoView ivIcon;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private Unbinder unbinder;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_save);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        mTvTitle.setText("查看凭证");
        url = getIntent().getStringExtra("url");
        ivIcon.enable();
        Glide.with(this).load(url).asBitmap().centerCrop().into(ivIcon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.ibt_back, R.id.ibt_save})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ibt_back:
                finish();
                break;
            case R.id.ibt_save:
                /**
                 * 在调用getDrawingCache()方法从ImageView对象获取图像之前，一定要调用setDrawingCacheEnabled(true)方法：
                 imageview.setDrawingCacheEnabled(true);
                 否则，无法从ImageView对象iv_photo中获取图像；
                 在调用getDrawingCache()方法从ImageView对象获取图像之后，一定要调用setDrawingCacheEnabled(false)方法：
                 imageview.setDrawingCacheEnabled(false);
                 以清空画图缓冲区，否则，下一次从ImageView对象iv_photo中获取的图像，还是原来的图像。
                 */
                ivIcon.setDrawingCacheEnabled(true);
                boolean flag = FileUtil.saveFile(PreviewSaveActivity.this,url.substring(url.lastIndexOf('/')+1,url.length()),ivIcon.getDrawingCache());
                if (flag){//保存成功关闭当前界面
                    finish();
                }
                ivIcon.setDrawingCacheEnabled(false);
                break;
        }
    }
}
