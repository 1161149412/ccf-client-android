package cn.cnlinfo.ccf.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.entity.ContributeMapNode;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.listener.LongPressClick;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.view.RegularPolygonView;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class ContributionMapActivity extends BaseActivity implements View.OnClickListener, LongPressClick {

    @BindView(R.id.rpv_center)
    RegularPolygonView rpvCenter;
    @BindView(R.id.rpv_top)
    RegularPolygonView rpvTop;
    @BindView(R.id.rpv_right_center)
    RegularPolygonView rpvRightCenter;
    @BindView(R.id.rpv_left_center)
    RegularPolygonView rpvLeftCenter;
    @BindView(R.id.rpv_bottom_right)
    RegularPolygonView rpvBottomRight;
    @BindView(R.id.rpv_bottom_left)
    RegularPolygonView rpvBottomLeft;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Unbinder unbinder;
    private User user;
    private List<ContributeMapNode> treeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_map);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("贡献图谱");
        init();
        registerOnClickListener();
        registerOnLongClickListener();
    }

    private void init(){
        showWaitingDialog(true);
        user = UserSharedPreference.getInstance().getUser();
        treeList = new ArrayList<>();
        getChildNodeData(user.getUserID());
    }

    private void getChildNodeData(int id) {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userID",id);
        HttpRequest.post(Constant.CONTRIBUTIONMAP_HOST+ API.USERCONTRIBUTEMAP, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                int size = 0;
                JSONArray jsonArray = JSON.parseObject(data.toJSONString()).getJSONArray("tree");
                treeList = JSONArray.parseArray(jsonArray.toJSONString(),ContributeMapNode.class);
                size = treeList.size();
                if (treeList!=null&&size>0){
                        switch (size){
                            case 1:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText("暂无");
                                rpvLeftCenter.setText("暂无");
                                rpvRightCenter.setText("暂无");
                                rpvBottomLeft.setText("暂无");
                                rpvBottomRight.setText("暂无");
                                break;
                            case 2:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText(treeList.get(1).getuCode()+"\n"+"总:  "+treeList.get(1).getSumE());
                                rpvLeftCenter.setText("暂无");
                                rpvRightCenter.setText("暂无");
                                rpvBottomLeft.setText("暂无");
                                rpvBottomRight.setText("暂无");
                                break;
                            case 3:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText(treeList.get(1).getuCode()+"\n"+"总:  "+treeList.get(1).getSumE());
                                rpvLeftCenter.setText(treeList.get(2).getuCode()+"\n"+"总:  "+treeList.get(2).getSumE());
                                rpvRightCenter.setText("暂无");
                                rpvBottomLeft.setText("暂无");
                                rpvBottomRight.setText("暂无");
                                break;
                            case 4:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText(treeList.get(1).getuCode()+"\n"+"总:  "+treeList.get(1).getSumE());
                                rpvLeftCenter.setText(treeList.get(2).getuCode()+"\n"+"总:  "+treeList.get(2).getSumE());
                                rpvRightCenter.setText(treeList.get(3).getuCode()+"\n"+"总:  "+treeList.get(3).getSumE());
                                rpvBottomLeft.setText("暂无");
                                rpvBottomRight.setText("暂无");
                                break;
                            case 5:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText(treeList.get(1).getuCode()+"\n"+"总:  "+treeList.get(1).getSumE());
                                rpvLeftCenter.setText(treeList.get(2).getuCode()+"\n"+"总:  "+treeList.get(2).getSumE());
                                rpvRightCenter.setText(treeList.get(3).getuCode()+"\n"+"总:  "+treeList.get(3).getSumE());
                                rpvBottomLeft.setText(treeList.get(4).getuCode()+"\n"+"总:  "+treeList.get(4).getSumE());
                                rpvBottomRight.setText("暂无");
                                break;
                            case 6:
                                rpvCenter.setText(treeList.get(0).getuCode()+"\n"+"总:  "+treeList.get(0).getSumE());
                                rpvTop.setText(treeList.get(1).getuCode()+"\n"+"总:  "+treeList.get(1).getSumE());
                                rpvLeftCenter.setText(treeList.get(2).getuCode()+"\n"+"总:  "+treeList.get(2).getSumE());
                                rpvRightCenter.setText(treeList.get(3).getuCode()+"\n"+"总:  "+treeList.get(3).getSumE());
                                rpvBottomLeft.setText(treeList.get(4).getuCode()+"\n"+"总:  "+treeList.get(4).getSumE());
                                rpvBottomRight.setText(treeList.get(5).getuCode()+"\n"+"总:  "+treeList.get(5).getSumE());
                                break;
                        }
                    }
                    showWaitingDialog(false);
                }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                    showMessage(code,msg);
                    showWaitingDialog(false);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                showMessage(errorCode,msg);
                showWaitingDialog(false);
            }
        });
    }

    /**
     * 注册点击监听器
     */
    private void registerOnClickListener() {
        ibtBack.setOnClickListener(this);
        rpvCenter.setOnClickListener(this);
        rpvBottomLeft.setOnClickListener(this);
        rpvBottomRight.setOnClickListener(this);
        rpvLeftCenter.setOnClickListener(this);
        rpvRightCenter.setOnClickListener(this);
        rpvTop.setOnClickListener(this);
    }

    /**
     * 注册长按监听器
     */
    private void registerOnLongClickListener() {
        rpvCenter.setLongPressClick(this);
        rpvBottomLeft.setLongPressClick(this);
        rpvBottomRight.setLongPressClick(this);
        rpvLeftCenter.setLongPressClick(this);
        rpvRightCenter.setLongPressClick(this);
        rpvTop.setLongPressClick(this);
    }

    @Override
    public void onClick(View v) {
        ContributeMapNode contributeMapNode = null;
        switch (v.getId()) {
            case R.id.ibt_back:
                finish();
                break;
            case R.id.rpv_center:
                if (rpvCenter.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(0);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;
            case R.id.rpv_top:
                if (rpvTop.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(1);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;

            case R.id.rpv_left_center:
                if (rpvLeftCenter.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(2);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;
            case R.id.rpv_right_center:
                if (rpvRightCenter.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(3);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;
            case R.id.rpv_bottom_left:
                if (rpvBottomLeft.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(4);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;
            case R.id.rpv_bottom_right:
                if (rpvBottomRight.getText().toString()!="暂无"){
                    contributeMapNode = treeList.get(5);
                    getChildNodeData(contributeMapNode.getId());
                }
                break;
        }
    }

    /**
     * 长按事件
     *
     * @param view
     */
    @Override
    public void run(View view) {
        ContributeMapNode contributeMapNode = null;
        switch (view.getId()) {
            case R.id.rpv_center:
                if (rpvCenter.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(0);
                    showMapInfoDialog(contributeMapNode);
                }
                break;

            case R.id.rpv_top:
                if (rpvTop.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(1);
                    showMapInfoDialog(contributeMapNode);
                }
                break;
            case R.id.rpv_left_center:
                if (rpvLeftCenter.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(2);
                    showMapInfoDialog(contributeMapNode);
                }
                break;
            case R.id.rpv_right_center:
                if (rpvRightCenter.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(3);
                    showMapInfoDialog(contributeMapNode);
                }
                break;
            case R.id.rpv_bottom_left:
                if (rpvBottomLeft.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(4);
                    showMapInfoDialog(contributeMapNode);
                }
                break;
            case R.id.rpv_bottom_right:
                if (rpvBottomRight.getText().toString()!="暂无") {
                    contributeMapNode = treeList.get(5);
                    showMapInfoDialog(contributeMapNode);
                }
                break;
        }
    }
    /**
     * 显示节点信息的dialog
     * @param contributeMapNode
     */
    private void showMapInfoDialog(ContributeMapNode contributeMapNode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_contribution_map, null);
        ImageView iv_close = view.findViewById(R.id.iv_close_dialog);
        TextView tv_user_name = view.findViewById(R.id.tv_user_name);
        TextView tv_total_con_value = view.findViewById(R.id.tv_total_con_value);
        TextView tv_maintenance_area = view.findViewById(R.id.tv_maintenance_area);
        TextView tv_con_value = view.findViewById(R.id.tv_con_value);
        TextView tv_total_cover = view.findViewById(R.id.tv_total_cover);
        tv_user_name.setText(contributeMapNode.getuCode());
        tv_total_con_value.setText(String.valueOf(contributeMapNode.getSumE()));
        tv_maintenance_area.setText(String.valueOf(contributeMapNode.getMaxRegion()));
        tv_con_value.setText(String.valueOf(contributeMapNode.getSumDevote()));
        tv_total_cover.setText(String.valueOf(contributeMapNode.getTotalMealWeight()));
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
