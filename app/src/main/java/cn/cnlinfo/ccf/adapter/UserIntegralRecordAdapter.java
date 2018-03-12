package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.UserIntegralRecordEntity;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class UserIntegralRecordAdapter extends BaseRecyclerAdapter<UserIntegralRecordEntity> {

    private Context context;

    public UserIntegralRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate( R.layout.item_user_integral_record, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            if (!isEmpty()){
                UserIntegralRecordEntity userIntegralRecordEntity = list.get(position);
                if (userIntegralRecordEntity!=null){
                    ((ViewHolder) holder).tvTime.setText(userIntegralRecordEntity.getCreateTime());
                    ((ViewHolder) holder).tvType.setText(userIntegralRecordEntity.getType());
                    ((ViewHolder) holder).tvOriginalValue.setText(userIntegralRecordEntity.getOldValue());
                    ((ViewHolder) holder).tvChangeValue.setText(userIntegralRecordEntity.getDelta());
                    ((ViewHolder) holder).tvInfo.setText(userIntegralRecordEntity.getMsg());
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_original_value)
        TextView tvOriginalValue;
        @BindView(R.id.tv_change_value)
        TextView tvChangeValue;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
