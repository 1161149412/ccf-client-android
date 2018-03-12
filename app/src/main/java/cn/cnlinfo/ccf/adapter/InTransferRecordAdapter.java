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
import cn.cnlinfo.ccf.entity.TransferRecordEntity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class InTransferRecordAdapter extends BaseRecyclerAdapter<TransferRecordEntity> {

    private Context context;

    public InTransferRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_in_transfer_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.d(list.get(position).toString());
        if (holder instanceof ViewHolder) {
            TransferRecordEntity transferRecordEntity = list.get(position);
            if (transferRecordEntity != null) {
                if (!TextUtils.isEmpty(transferRecordEntity.getDateTime())) {
                    ((ViewHolder) holder).tvTime.setText(transferRecordEntity.getDateTime());
                } else {
                    ((ViewHolder) holder).tvTime.setText("暂无");
                }
                if (!TextUtils.isEmpty(transferRecordEntity.getNum())) {
                    ((ViewHolder) holder).tvNum.setText(transferRecordEntity.getNum());
                } else {
                    ((ViewHolder) holder).tvNum.setText("暂无");
                }
                if (!TextUtils.isEmpty(transferRecordEntity.getSendCode())) {
                    ((ViewHolder) holder).tvTransferSend.setText(transferRecordEntity.getSendCode());
                } else {
                    ((ViewHolder) holder).tvTransferSend.setText("暂无");
                }
                if (!TextUtils.isEmpty(transferRecordEntity.getReceiveCode())) {
                    ((ViewHolder) holder).tvTransferReceive.setText(transferRecordEntity.getReceiveCode());
                } else {
                    ((ViewHolder) holder).tvTransferReceive.setText("暂无");
                }
                if (!TextUtils.isEmpty(transferRecordEntity.getType())) {
                    ((ViewHolder) holder).tvTransferType.setText(transferRecordEntity.getType());
                } else {
                    ((ViewHolder) holder).tvTransferType.setText("暂无");
                }
                if (!TextUtils.isEmpty(transferRecordEntity.getStatus())) {
                    ((ViewHolder) holder).tvTransferStatus.setText(transferRecordEntity.getStatus());
                } else {
                    ((ViewHolder) holder).tvTransferStatus.setText("暂无");
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
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_transfer_send)
        TextView tvTransferSend;
        @BindView(R.id.tv_transfer_receive)
        TextView tvTransferReceive;
        @BindView(R.id.tv_transfer_type)
        TextView tvTransferType;
        @BindView(R.id.tv_transfer_status)
        TextView tvTransferStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
