package cn.cnlinfo.ccf.activity;

import android.content.Intent;
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
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.BaseRecyclerAdapter;
import cn.cnlinfo.ccf.adapter.SystemNewsAdapter;
import cn.cnlinfo.ccf.entity.ItemNewsEntity;
import cn.cnlinfo.ccf.mvc.datasource.SystemNewsDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class SystemNoticeActivity extends BaseActivity {

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
        setContentView(R.layout.activity_system_notice);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("系统公告");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gainSystemNoticeList();
    }

    /**
     * 获取当前用户层级列表
     */
    private void gainSystemNoticeList() {
        //MVCHelper.setLoadViewFractory(new NormalLoadViewFactory());
        //this.setMaterialHeader(pfl);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(true);
        mvcHelper = new MVCUltraHelper<List<ItemNewsEntity>>(pfl);
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.setDataSource(new SystemNewsDataSource());
        SystemNewsAdapter adapter = new SystemNewsAdapter(this);
        mvcHelper.setAdapter(adapter);
        mvcHelper.refresh();
        adapter.setItemClickCallback(new BaseRecyclerAdapter.ItemClickCallback<ItemNewsEntity>() {
            @Override
            public void onItemClicked(int position, ItemNewsEntity entity) {
                Intent intent = new Intent(SystemNoticeActivity.this,WebActivity.class);
                intent.putExtra("url",String.format(Constant.GET_DETAIL_HOST, entity.getNewsID()));
                startActivity(intent);
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
