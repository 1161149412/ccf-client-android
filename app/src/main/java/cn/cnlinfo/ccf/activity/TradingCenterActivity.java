package cn.cnlinfo.ccf.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.shizhefei.mvc.MVCHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.TradingListItemAdapter;
import cn.cnlinfo.ccf.event.CloseActivityEvent;
import cn.cnlinfo.ccf.manager.PhoneManager;
import cn.cnlinfo.ccf.mvc.datasource.TradingListDataSource;
import cn.cnlinfo.ccf.mvc.factory.NormalLoadViewFactory;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.RecyclerViewDivider;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class TradingCenterActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton mIbtBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout mPfl;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;
    private TradingListItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_center);
        unbinder = ButterKnife.bind(this);
        mTvTitle.setText("交易大厅");
        showTradingCenterInfo();
        EventBus.getDefault().register(this);
    }

    /**
     * 显示交易大厅信息
     */
    private void showTradingCenterInfo() {
        setMaterialHeader(mPfl);
        MVCHelper.setLoadViewFractory(new NormalLoadViewFactory());
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayout.HORIZONTAL, PhoneManager.dip2px(8),getResources().getColor(R.color.color_black_0e1214_alpha_75)));
        mvcHelper = new MVCUltraHelper(mPfl);
        mvcHelper.setDataSource(new TradingListDataSource("where (TranType=1 or TranType=2) and Status=1 "));
        adapter = new TradingListItemAdapter(this,1);
        mvcHelper.setAdapter(adapter);
    }

    //从其他页面返回界面执行刷新动作
    @Override
    protected void onResume() {
        super.onResume();
        mvcHelper.refresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.ibt_back,R.id.ibt_add})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ibt_back:
                finish();
                break;
            case R.id.ibt_add:
                showTradingMenu(v);
                break;
        }
    }

    /**
     * 显示交易菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showTradingMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view, Gravity.END);
        Menu menu = popupMenu.getMenu();
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.layout_trading_menu,menu);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                String type = "";
                switch (itemId) {
                    case R.id.sell:
                        type = "where TranType=1 and Status=1";
                        mvcHelper.setDataSource(new TradingListDataSource(type));
                        adapter = new TradingListItemAdapter(TradingCenterActivity.this,1);
                        mvcHelper.setAdapter(adapter);
                        break;
                    case R.id.buy:
                        type = "where TranType=2 and Status=1";
                        mvcHelper.setDataSource(new TradingListDataSource(type));
                        adapter = new TradingListItemAdapter(TradingCenterActivity.this,2);
                        mvcHelper.setAdapter(adapter);
                        break;
                }
                mvcHelper.refresh();
                return false;
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeActivityMessage(CloseActivityEvent closeActivityEvent){
        int code = closeActivityEvent.getCode();
        switch (code){
            case 0:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
