package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.mvc.MVCHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.adapter.UserLeaveMessageRecordAdapter;
import cn.cnlinfo.ccf.mvc.datasource.UserLeaveMessageRecordDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.view.FullyLinearLayoutManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by JP on 2018/3/5 0005.
 */

public class LeaveMessageRecordActivity extends BaseActivity {

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
        setContentView(R.layout.activity_leave_message_record);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("留言记录");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setUserLeaveMessageData();
    }
    /**
     * 设置用户留言记录数据
     */
    private void setUserLeaveMessageData(){
        mvcHelper = new MVCUltraHelper(pfl);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new FullyLinearLayoutManager(this));
        mvcHelper.setDataSource(new UserLeaveMessageRecordDataSource());
        mvcHelper.setAdapter(new UserLeaveMessageRecordAdapter(this));
        mvcHelper.setNeedCheckNetwork(true);
        mvcHelper.refresh();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
