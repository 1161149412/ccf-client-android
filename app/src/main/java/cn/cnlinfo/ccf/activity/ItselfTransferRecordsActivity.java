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
import cn.cnlinfo.ccf.adapter.InTransferRecordAdapter;
import cn.cnlinfo.ccf.entity.TransferRecordEntity;
import cn.cnlinfo.ccf.mvc.datasource.InTransferRecordDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by JP on 2017/11/30 0030.
 * 自身转账记录
 */

public class ItselfTransferRecordsActivity extends BaseActivity {

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
        setContentView(R.layout.activity_itsef_transfer_records);

        unbinder = ButterKnife.bind(this);
        tvTitle.setText("内部转账记录");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getInTransferRecord();
    }
    private void getInTransferRecord(){
        //MVCHelper.setLoadViewFractory(new NormalNoLoadViewFactory());//只需要在一个地方设置，所有地方都可以用到这个设置
        //this.setMaterialHeader(pfl);
        rv.setLayoutManager(new FullyLinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
        mvcHelper = new MVCUltraHelper<List<TransferRecordEntity>>(pfl);
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.setDataSource(new InTransferRecordDataSource());
        mvcHelper.setAdapter(  new InTransferRecordAdapter(this));
        mvcHelper.refresh();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.destory();
    }
}
