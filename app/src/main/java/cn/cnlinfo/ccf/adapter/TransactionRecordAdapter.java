package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.entity.TransactionRecord;


/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class TransactionRecordAdapter extends BaseRecyclerAdapter<TransactionRecord>{

    private Context context;

    public TransactionRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_record,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        boolean visibility_Flag = false;
        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {
            if(visibility_Flag){
                linearLayout.setVisibility(View.GONE);
                visibility_Flag = false;
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                visibility_Flag =true;
            }
        }
    }
}
