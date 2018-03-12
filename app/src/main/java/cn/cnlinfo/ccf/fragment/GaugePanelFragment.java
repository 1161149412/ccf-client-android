package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.activity.ContributionMapActivity;
import cn.cnlinfo.ccf.activity.ForeignTransferActivity;
import cn.cnlinfo.ccf.activity.InternalTransferActivity;
import cn.cnlinfo.ccf.activity.MyParameterActivity;
import cn.cnlinfo.ccf.activity.OrderCenterActivity;
import cn.cnlinfo.ccf.activity.PlatformParameterActivity;
import cn.cnlinfo.ccf.activity.RecommendNetActivity;
import cn.cnlinfo.ccf.activity.RecordCenterActivity;
import cn.cnlinfo.ccf.activity.RegisterMemberActivity;
import cn.cnlinfo.ccf.activity.RunningRankActivity;
import cn.cnlinfo.ccf.activity.SystemNoticeActivity;
import cn.cnlinfo.ccf.activity.UserUpgradeActivity;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class GaugePanelFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.gv_gauge_panel)
    GridView gvGaugePanel;
    private Unbinder unbinder;
    private String[] names = {"注册会员", "贡献图谱", "推荐列表", "我的参数",
            "平台参数", "跑步排名", "新闻公告", "订单中心",
            "对外互转", "内部互转", "用户升级", "记录中心",
            "碳控公益", "在线客服", "碳控服务", "兑换中心"};
    private int[] icons = {R.drawable.icon_register_member, R.drawable.icon_contribution_atlas, R.drawable.icon_recommend_net, R.drawable.icon_my_parameter,
            R.drawable.icon_platform_parameter, R.drawable.icon_start_running, R.drawable.icon_news_notice, R.drawable.icon_order_center,
            R.drawable.icon_external_each_other, R.drawable.icon_internal_each_other, R.drawable.icon_user_upgrade, R.drawable.icon_recode_centre,
            R.drawable.icon_cc_pf, R.drawable.icon_online_service, R.drawable.icon_cc_service, R.drawable.conversion_center};
    private List<Map<String, Object>> list;
    private SimpleAdapter adapter;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_gauge_panel);
        TCAgent.onPageStart(getActivity(), "仪表盘");
        unbinder = ButterKnife.bind(this, getContentView());
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.item_gv_panel, new String[]{"icon", "name"}, new int[]{R.id.item_iv_icon, R.id.item_tv_name});
        gvGaugePanel.setAdapter(adapter);
        gvGaugePanel.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("icon", icons[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        TCAgent.onPageEnd(getActivity(), "仪表盘");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(getActivity(), RegisterMemberActivity.class);
                startActivity(intent);
                break;
            case 1:
                startActivity(new Intent(getActivity(), ContributionMapActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), RecommendNetActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), MyParameterActivity.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(), PlatformParameterActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), RunningRankActivity.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), SystemNoticeActivity.class));
                break;
            case 7:
                startActivity(new Intent(getActivity(), OrderCenterActivity.class));
                break;
            case 8:
                startActivity(new Intent(getActivity(), ForeignTransferActivity.class));
                break;
            case 9:
                startActivity(new Intent(getActivity(), InternalTransferActivity.class));
                break;
            case 10:
                startActivity(new Intent(getActivity(), UserUpgradeActivity.class));
                break;
            case 11:
                startActivity(new Intent(getActivity(), RecordCenterActivity.class));
                break;
            case 12:
                //startActivity(new Intent(getActivity(), CCPublicBenefitActivity.class));
                toast("用户等级不够");
                break;
            case 13:
                //startActivity(new Intent(getActivity(), OnlineServiceActivity.class));
                toast("用户等级不够");
                break;
            case 14:
               // startActivity(new Intent(getActivity(), CCServiceActivity.class));
                toast("用户等级不够");
                break;
            case 15:
                //startActivity(new Intent(getActivity(), ConversionCenterActivity.class));
                toast("用户等级不够");
                break;
        }
    }
}
