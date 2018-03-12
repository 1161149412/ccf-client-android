package cn.cnlinfo.ccf.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.shizhefei.mvc.MVCHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.HangSellAndBuyAdapter;
import cn.cnlinfo.ccf.entity.ItemHangSellAndBuyEntity;
import cn.cnlinfo.ccf.event.CancelHangBuyAndSellEvent;
import cn.cnlinfo.ccf.mvc.datasource.HangSellAndBuyRecordDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import cn.finalteam.okhttpfinal.HttpRequest;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


public class HangBuyAndSellRecordActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout pfl;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    private Unbinder unbinder;
    private MVCHelper mvcHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_buy_and_sell_record);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvTitle.setText("挂卖/挂买记录");
        getHangSellAndBuyRecord();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.ibt_back, R.id.ibt_add})
    public void click(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.ibt_back:
                finish();
                break;
            case R.id.ibt_add:
                setHangMenuRecord(view);
                break;
        }
    }

    //接收到撤销挂卖挂买单的事件,并且进行刷新操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveCancelHangBuyAndSellEvent(CancelHangBuyAndSellEvent event){
       switch (event.getCode()){
           case -1:
               toast(event.getMsg());
               break;
           case 0:
               toast(event.getMsg());
               mvcHelper.refresh();
               break;
       }
    }
    /**
     * 获取挂买挂卖记录
     * tranType 1是挂卖2是挂买
     */
    private void getHangSellAndBuyRecord() {
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new FullyLinearLayoutManager(this));
        mvcHelper = new MVCUltraHelper<List<ItemHangSellAndBuyEntity>>(pfl);
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.setDataSource(new HangSellAndBuyRecordDataSource(1));
        mvcHelper.setAdapter(new HangSellAndBuyAdapter(this));
        mvcHelper.refresh();
    }

    /**
     * 设置menu展示挂卖和挂买
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setHangMenuRecord(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.END);
        Menu menu = popupMenu.getMenu();
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.layout_trad_record_menu, menu);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.sell:
                        mvcHelper.setDataSource(new HangSellAndBuyRecordDataSource(1));
                        mvcHelper.refresh();
                        break;
                    case R.id.buy:
                        mvcHelper.setDataSource(new HangSellAndBuyRecordDataSource(2));
                        mvcHelper.refresh();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mvcHelper.destory();
        EventBus.getDefault().unregister(this);
        HttpRequest.cancel(Constant.RECORD_CENTER_HOST + API.HANGBYSELLANDBUY);
        HttpRequest.cancel(Constant.OPERATE_CCF_HOST + API.CANCELHANGBUYANDSELL);
    }
}
