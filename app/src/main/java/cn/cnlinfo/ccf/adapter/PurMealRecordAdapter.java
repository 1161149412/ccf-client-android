package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.PurMealRecordEntity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class PurMealRecordAdapter extends BaseRecyclerAdapter<PurMealRecordEntity> {


    private Context context;

    public PurMealRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pur_meal_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     //   Logger.d(list.get(position).toString());
        if (holder instanceof ViewHolder) {
            PurMealRecordEntity purMealRecordEntity = list.get(position);
            if (purMealRecordEntity != null) {
                if (!TextUtils.isEmpty(purMealRecordEntity.getCreateTime())){
                   ((ViewHolder) holder).tvTime.setText(purMealRecordEntity.getCreateTime());
                }else {
                    ((ViewHolder) holder).tvTime.setText("暂无");
                }
                if (!TextUtils.isEmpty(purMealRecordEntity.getCCFPrice())){
                    ((ViewHolder) holder).tvCcfPrice.setText(purMealRecordEntity.getCCFPrice());
                }else {
                    ((ViewHolder) holder).tvCcfPrice.setText("暂无");
                }
                if (!TextUtils.isEmpty(purMealRecordEntity.getSetMealID())){
                    ((ViewHolder) holder).tvMealType.setText(purMealRecordEntity.getSetMealID());
                }else {
                    ((ViewHolder) holder).tvMealType.setText("暂无");
                }
                if (!TextUtils.isEmpty(purMealRecordEntity.getMsg())){
                    ((ViewHolder) holder).tvMealInfo.setText(purMealRecordEntity.getMsg());
                }else {
                    ((ViewHolder) holder).tvMealInfo.setText("暂无");
                }
                if (!TextUtils.isEmpty(purMealRecordEntity.getStatus())){
                    ((ViewHolder) holder).tvMealStatus.setText(purMealRecordEntity.getStatus());
                }else {
                    ((ViewHolder) holder).tvMealStatus.setText("暂无");
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
        @BindView(R.id.tv_ccf_price)
        TextView tvCcfPrice;
        @BindView(R.id.tv_meal_type)
        TextView tvMealType;
        @BindView(R.id.tv_meal_info)
        TextView tvMealInfo;
        @BindView(R.id.tv_meal_status)
        TextView tvMealStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
