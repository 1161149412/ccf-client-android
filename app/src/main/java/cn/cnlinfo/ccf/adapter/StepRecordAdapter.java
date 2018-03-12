package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.StepRecordEntity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class StepRecordAdapter extends BaseRecyclerAdapter<StepRecordEntity> {



    private Context context;

    public StepRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_step_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.d(list.get(position).toString());
        if (holder instanceof ViewHolder) {
            StepRecordEntity stepRecordEntity = list.get(position);
            if (stepRecordEntity != null) {
                if (!TextUtils.isEmpty(stepRecordEntity.getDate())) {
                    ((ViewHolder) holder).tvTime.setText(stepRecordEntity.getDate());
                } else {
                    ((ViewHolder) holder).tvTime.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getStep())) {
                    ((ViewHolder) holder).tvStep.setText(stepRecordEntity.getStep());
                } else {
                    ((ViewHolder) holder).tvStep.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getCCF())) {
                    ((ViewHolder) holder).tvCcf.setText(stepRecordEntity.getCCF());
                } else {
                    ((ViewHolder) holder).tvCcf.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getCarbonIntegral())) {
                    ((ViewHolder) holder).tvCarbonPoints.setText(stepRecordEntity.getCarbonIntegral());
                } else {
                    ((ViewHolder) holder).tvCarbonPoints.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getMsg())) {
                    ((ViewHolder) holder).tvWalkInfo.setText(stepRecordEntity.getMsg());
                } else {
                    ((ViewHolder) holder).tvWalkInfo.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getF())) {
                    ((ViewHolder) holder).tvContributeValueF.setText(stepRecordEntity.getF());
                } else {
                    ((ViewHolder) holder).tvContributeValueF.setText("暂无");
                }
                if (!TextUtils.isEmpty(stepRecordEntity.getE())) {
                    ((ViewHolder) holder).tvContributeValueE.setText(stepRecordEntity.getE());
                } else {
                    ((ViewHolder) holder).tvContributeValueE.setText("暂无");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_step)
        TextView tvStep;
        @BindView(R.id.tv_ccf)
        TextView tvCcf;
        @BindView(R.id.tv_carbon_points)
        TextView tvCarbonPoints;
        @BindView(R.id.tv_walk_info)
        TextView tvWalkInfo;
        @BindView(R.id.tv_contribute_value_f)
        TextView tvContributeValueF;
        @BindView(R.id.tv_contribute_value_e)
        TextView tvContributeValueE;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
