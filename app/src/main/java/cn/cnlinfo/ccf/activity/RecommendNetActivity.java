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
import cn.cnlinfo.ccf.adapter.ShareUserAdapter;
import cn.cnlinfo.ccf.entity.ShareUserEntity;
import cn.cnlinfo.ccf.mvc.datasource.ShareUserDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class RecommendNetActivity extends BaseActivity {
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
    private ShareUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_net);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("推荐列表");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gainTierList();
    }

    /**
     * 获取当前用户层级列表
     */
    private void gainTierList() {
        //MVCHelper.setLoadViewFractory(new NormalLoadViewFactory());
        //this.setMaterialHeader(pfl);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(true);
        mvcHelper = new MVCUltraHelper<List<ShareUserEntity>>(pfl);
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.setDataSource(new ShareUserDataSource());
        adapter = new ShareUserAdapter(this);
        mvcHelper.setAdapter(adapter);
        mvcHelper.refresh();
       /* mvcHelper.setAdapter2(adapter, new IDataAdapter<List<ShareUserEntity>>() {
            @Override
            public void notifyDataChanged(List<ShareUserEntity> shareUserEntityList, boolean isRefresh) {
                adapter.notifyDataChanged(shareUserEntityList,isRefresh);
                rv.setAdapter(adapter);
            }

            @Override
            public List<ShareUserEntity> getData() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });*/

     /*   rv.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                Logger.d("loadmore");
                mvcHelper.loadMore();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.destory();
    }
}
