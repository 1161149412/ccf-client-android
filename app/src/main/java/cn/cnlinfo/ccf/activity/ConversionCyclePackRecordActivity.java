package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.ConversionCyclePackRecordAdapter;
import cn.cnlinfo.ccf.entity.CyclePackRecordEntity;
import cn.cnlinfo.ccf.mvc.datasource.CyclePackRecordDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class ConversionCyclePackRecordActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout pfl;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_record);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("兑换循环包记录");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCyclePackRecord();
    }
    private void getCyclePackRecord(){
        //MVCHelper.setLoadViewFractory(new NormalNoLoadViewFactory());
       // this.setMaterialHeader(pfl);
        rv.setLayoutManager(new FullyLinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
        mvcHelper = new MVCUltraHelper<List<CyclePackRecordEntity>>(pfl);
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.setDataSource(new CyclePackRecordDataSource());
        ConversionCyclePackRecordAdapter adapter = new ConversionCyclePackRecordAdapter(this);
        mvcHelper.setAdapter(adapter);
        mvcHelper.refresh();
        adapter.setExtractRefreshListener(new ConversionCyclePackRecordAdapter.ExtractRefreshListener() {
            @Override
            public void extractRefresh() {
                mvcHelper.refresh();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.destory();
    }
}
