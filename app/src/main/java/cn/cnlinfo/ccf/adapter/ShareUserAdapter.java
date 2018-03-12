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
import cn.cnlinfo.ccf.entity.ShareUserEntity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class ShareUserAdapter extends BaseRecyclerAdapter<ShareUserEntity> {


    private Context context;
    private LayoutInflater inflater;
    private static final int VIEWTYPE1 = 0;
    private static final int VIEWTYPE2 = 1;

    public ShareUserAdapter(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE1;
        } else {
            return VIEWTYPE2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEWTYPE1:
                viewHolder = new ViewHolder1(inflater.inflate(R.layout.item_share_user_with_text, parent, false));
                break;
            case VIEWTYPE2:
                viewHolder = new ViewHolder2(inflater.inflate(R.layout.item_share_user, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.d(list.get(position).toString());
        if (holder instanceof ViewHolder1){
            //((ViewHolder1) holder).tvInfo.setText("推荐人员层级列表");
            ((ViewHolder1) holder).tvInfo.setVisibility(View.GONE);//隐藏
            ShareUserEntity userEntity = list.get(position);
            if (!TextUtils.isEmpty(userEntity.getUserCode())) {
                ((ViewHolder1) holder).mTvUserAccount.setText(userEntity.getUserCode());
            } else {
                ((ViewHolder1) holder).mTvUserAccount.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getName())) {
                ((ViewHolder1) holder).mTvUserName.setText(userEntity.getName());
            } else {
                ((ViewHolder1) holder).mTvUserName.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getMobile())) {
                ((ViewHolder1) holder).mTvUserPhone.setText(userEntity.getMobile());
            } else {
                ((ViewHolder1) holder).mTvUserPhone.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getRegDate())) {
                ((ViewHolder1) holder).mTvUserReDate.setText(userEntity.getRegDate());
            } else {
                ((ViewHolder1) holder).mTvUserReDate.setText("暂无");
            }
        }else {
            ShareUserEntity userEntity = list.get(position);
            if (!TextUtils.isEmpty(userEntity.getUserCode())) {
                ((ViewHolder2) holder).mTvUserAccount.setText(userEntity.getUserCode());
            } else {
                ((ViewHolder2) holder).mTvUserAccount.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getName())) {
                ((ViewHolder2) holder).mTvUserName.setText(userEntity.getName());
            } else {
                ((ViewHolder2) holder).mTvUserName.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getMobile())) {
                ((ViewHolder2) holder).mTvUserPhone.setText(userEntity.getMobile());
            } else {
                ((ViewHolder2) holder).mTvUserPhone.setText("暂无");
            }
            if (!TextUtils.isEmpty(userEntity.getRegDate())) {
                ((ViewHolder2) holder).mTvUserReDate.setText(userEntity.getRegDate());
            } else {
                ((ViewHolder2) holder).mTvUserReDate.setText("暂无");
            }
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_account)
        TextView mTvUserAccount;
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_user_phone)
        TextView mTvUserPhone;
        @BindView(R.id.tv_user_re_date)
        TextView mTvUserReDate;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.tv_user_account)
        TextView mTvUserAccount;
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_user_phone)
        TextView mTvUserPhone;
        @BindView(R.id.tv_user_re_date)
        TextView mTvUserReDate;
        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
