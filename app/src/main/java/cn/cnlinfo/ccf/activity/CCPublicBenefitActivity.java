package cn.cnlinfo.ccf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

public class CCPublicBenefitActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_donate_step)
    TextView tvDonateStep;
    @BindView(R.id.tv_beneficent_funds)
    TextView tvBeneficentFunds;
    @BindView(R.id.tv_plant_tree_funds)
    TextView tvPlantTreeFunds;
    @BindView(R.id.tv_love_dedication)
    TextView mTvLoveDedication;
    @BindView(R.id.tv_me_donations)
    TextView mTvMeDonations;
    @BindView(R.id.tv_launch_donations)
    TextView mTvLaunchDonations;
    @BindView(R.id.tv_community_help_one)
    TextView mTvCommunityHelpOne;
    @BindView(R.id.tv_initiate_public_welfare_projects)
    TextView mTvInitiatePublicWelfareProjects;
    @BindView(R.id.tv_my_good_deeds_record)
    TextView mTvMyGoodDeedsRecord;
    @BindView(R.id.tv_need_help)
    TextView mTvNeedHelp;
    @BindView(R.id.tv_cc_environmental_protection)
    TextView mTvCcEnvironmentalProtection;
    @BindView(R.id.tv_environmental_protection_line)
    TextView mTvEnvironmentalProtectionLine;
    @BindView(R.id.tv_cc_line)
    TextView mTvCcLine;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccpublic_benefit);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("碳控公益");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_donate_step, R.id.tv_beneficent_funds, R.id.tv_plant_tree_funds,
            R.id.tv_love_dedication, R.id.tv_me_donations, R.id.tv_launch_donations,
            R.id.tv_community_help_one, R.id.tv_initiate_public_welfare_projects,
            R.id.tv_my_good_deeds_record, R.id.tv_need_help, R.id.tv_cc_environmental_protection,
            R.id.tv_environmental_protection_line, R.id.tv_cc_line})

    public void onClick(View v) {
        switch (v.getId()) {
            //我要捐步
            case R.id.tv_donate_step:
                startActivity(new Intent(this,DonateStepActivity.class));
                break;
            case R.id.tv_beneficent_funds://爱心基金
                startActivity(new Intent(this,BeneficentFundsActivity.class));
                break;
            case R.id.tv_plant_tree_funds://植树基金
                 startActivity(new Intent(this,PlantTreeFundsActivity.class));
                break;
            case R.id.tv_love_dedication://爱心奉献
                startActivity(new Intent(this,LoveDedicationActivity.class));
                break;
            case R.id.tv_me_donations://我要乐捐
                startActivity(new Intent(this,MeDedicationActivity.class));
                break;
            case R.id.tv_launch_donations://发起乐捐
                startActivity(new Intent(this,LaunchDedicationActivity.class));
                break;
            case R.id.tv_community_help_one://公益一帮一
                startActivity(new Intent(this,CommunityHelpOneActivity.class));
                break;
            case R.id.tv_initiate_public_welfare_projects://发起公益项目
                startActivity(new Intent(this,InitiatePublicWelfareProjectsActivity.class));
                break;
            case R.id.tv_my_good_deeds_record://我的善行记录
                startActivity(new Intent(this,MyGoodDeedsRecordActivity.class));
                break;
            case R.id.tv_need_help://需要帮助
                startActivity(new Intent(this,NeedHelpActivity.class));
                break;
            case R.id.tv_cc_environmental_protection://碳控环保
                startActivity(new Intent(this,CCEnvironmentalProtectionActivity.class));
                break;
            case R.id.tv_environmental_protection_line://环保行
                startActivity(new Intent(this,EnvironmentalProtectionLineActivity.class));
                break;
            case R.id.tv_cc_line://碳控行
                startActivity(new Intent(this,CCLineActivity.class));
                break;
            default:
                break;
        }
    }
}
