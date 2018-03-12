package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.RunningRankEntity;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class RunningRankAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<RunningRankEntity> runningRankEntityList = new ArrayList<>();

    public RunningRankAdapter(Context context,List<RunningRankEntity> list) {
        this.context = context;
        runningRankEntityList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_running_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            RunningRankEntity runningRankEntity = runningRankEntityList.get(position);
            ((MyViewHolder) holder).tvRank.setText(runningRankEntity.getRanking());
            ((MyViewHolder) holder).tvName.setText(runningRankEntity.getUserCode());
            ((MyViewHolder) holder).tvRankStep.setText(String.format(((MyViewHolder) holder).tvRankStep.getText().toString(), runningRankEntity.getStpeNum()));
            //((MyViewHolder) holder).tvPraise.setText(String.format(((MyViewHolder) holder).tvPraise.getText().toString(), runningRankEntity.getNum()));
        }
    }

    @Override
    public int getItemCount() {
        return runningRankEntityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rank)
        TextView tvRank;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_rank_step)
        TextView tvRankStep;
       /* @BindView(R.id.tv_praise)
        TextView tvPraise;*/

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
