package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.mvc.MVCHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.RunningRankAdapter;
import cn.cnlinfo.ccf.entity.RunningRankEntity;
import cn.cnlinfo.ccf.mvc.datasource.RunningRankDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.SpacesItemDecoration;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class RunningRankActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout pfl;
    private Unbinder unbinder;
    private MVCHelper<List<RunningRankEntity>> mvcHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_rank);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        ibtBack.setOnClickListener(this);
        ibtAdd.setVisibility(View.INVISIBLE);
        tvTitle.setText("跑步排名");
       // MVCHelper.setLoadViewFractory(new MyLoadViewFactory());
       // this.setMaterialHeader(pfl);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
        //rv.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.VERTICAL,8,ContextCompat.getColor(this, R.color.color_black_0e1214_alpha_75)));
        //项之间的距离为8px
        rv.addItemDecoration(new SpacesItemDecoration(8));
        mvcHelper = new MVCUltraHelper<List<RunningRankEntity>>(pfl);
        mvcHelper.setDataSource(new RunningRankDataSource());
        mvcHelper.setAdapter(new IDataAdapter<List<RunningRankEntity>>() {
            @Override
            public void notifyDataChanged(List<RunningRankEntity> list, boolean isRefresh) {
                if (rv!=null){
                    rv.setAdapter(new RunningRankAdapter(RunningRankActivity.this,list));
                }
            }

            @Override
            public List<RunningRankEntity> getData() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        mvcHelper.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.destory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibt_back:
                finish();
                break;
        }
    }
}
