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
import cn.cnlinfo.ccf.entity.LeaveMessageRecordEntity;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class UserLeaveMessageRecordAdapter extends BaseRecyclerAdapter<LeaveMessageRecordEntity> {


    private Context context;

    public UserLeaveMessageRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_leave_message_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (!isEmpty()) {
                LeaveMessageRecordEntity leaveMessageRecordEntity = list.get(position);
                if (leaveMessageRecordEntity != null) {
                    if (!TextUtils.isEmpty(leaveMessageRecordEntity.getAddtime())){
                        ((ViewHolder) holder).tvTime.setText(leaveMessageRecordEntity.getAddtime());
                    }else {
                        ((ViewHolder) holder).tvTime.setText("暂无");
                    }

                    ((ViewHolder) holder).tvLeaveMessage.setText(leaveMessageRecordEntity.getMsgBody());
                    if (!TextUtils.isEmpty(leaveMessageRecordEntity.getReplyMsg())){
                        ((ViewHolder) holder).tvReplyContent.setText(leaveMessageRecordEntity.getReplyMsg());
                    }else {
                        ((ViewHolder) holder).tvReplyContent.setText("未回复");
                    }
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_leave_message_time)
        TextView tvTime;
        @BindView(R.id.tv_leave_message)
        TextView tvLeaveMessage;
        @BindView(R.id.tv_reply_content)
        TextView tvReplyContent;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
