package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.mvc.MVCHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.UserIntegralRecordAdapter;
import cn.cnlinfo.ccf.mvc.datasource.UserIntegralRecordDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


public class UserIntegralRecordActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton mIbtBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ibt_add)
    ImageButton mIbtAdd;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout mPfl;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_integral_record);
        unbinder = ButterKnife.bind(this);
        mTvTitle.setText("用户积分记录");
        setUserIntegralData();
    }

    /**
     * 设置用户积分记录数据
     */
    private void setUserIntegralData(){
        mvcHelper = new MVCUltraHelper(mPfl);
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new FullyLinearLayoutManager(this));
        mvcHelper.setDataSource(new UserIntegralRecordDataSource());
        mvcHelper.setAdapter(new UserIntegralRecordAdapter(this));
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.cancel();
        mvcHelper.destory();
    }

    @OnClick({R.id.ibt_back, R.id.ibt_add})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ibt_back:
                finish();
                break;
            case R.id.ibt_add:
                break;
        }
    }
}
