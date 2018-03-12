package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.activity.PurCCFActivity;
import cn.cnlinfo.ccf.activity.SellCCFActivity;
import cn.cnlinfo.ccf.entity.TradingListItem;
import cn.cnlinfo.ccf.utils.DateUtil;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class TradingListItemAdapter extends BaseRecyclerAdapter<TradingListItem> {

    private LayoutInflater inflater;
    private int tradType;
    private Context context;

    public TradingListItemAdapter(Context context, int tradType) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.tradType = tradType;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflater.inflate(R.layout.item_trading_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TradingListItem tradingListItem = list.get(position);
        if (holder instanceof ViewHolder) {
            if (!TextUtils.isEmpty(tradingListItem.getTranType())) {
                ((ViewHolder) holder).tvTradingType.setText(tradingListItem.getTranType());
            } else {
                ((ViewHolder) holder).tvTradingType.setText("暂无");
            }
            if (!TextUtils.isEmpty(tradingListItem.getCreateTime())) {
                ((ViewHolder) holder).tvTime.setText(DateUtil.formatTime(tradingListItem.getCreateTime(), "yyyy/MM/dd"));
            } else {
                ((ViewHolder) holder).tvTime.setText("暂无");
            }
            if (!TextUtils.isEmpty(tradingListItem.getStatus())) {
                /**
                 * 1是挂卖
                 * 2是挂买
                 */
                if (((ViewHolder) holder).tvTradingType.getText().toString().equals("挂卖")) {
                    ((ViewHolder) holder).btnTradingStatus.setText("购买");
                    ((ViewHolder) holder).btnTradingStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tradingListItem != null) {
                                Intent intent = new Intent(context, PurCCFActivity.class);
                                intent.putExtra("roomId", tradingListItem.getID());
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    ((ViewHolder) holder).btnTradingStatus.setText("出售");
                    ((ViewHolder) holder).btnTradingStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tradingListItem != null) {
                                Intent intent = new Intent(context, SellCCFActivity.class);
                                intent.putExtra("roomId", tradingListItem.getID());
                                context.startActivity(intent);
                            }
                        }
                    });
                }
            } else {
                ((ViewHolder) holder).btnTradingStatus.setText("暂无");
                ((ViewHolder) holder).btnTradingStatus.setClickable(false);
            }
            if (!TextUtils.isEmpty(tradingListItem.getSellerID())) {
                ((ViewHolder) holder).tvUserId.setText(tradingListItem.getSellerID());
            } else {
                ((ViewHolder) holder).tvUserId.setText("暂无");
            }
            if (!TextUtils.isEmpty(tradingListItem.getBusinessLev())) {

                switch (tradingListItem.getBusinessLev()) {
                    case "普通用户":
                        ((ViewHolder) holder).tvUserLevel.setVisibility(View.GONE);
                        ((ViewHolder) holder).tvUserStar.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).tvUserStar.setText("普通用户");
                        break;
                    case "一星交易商":
                        ((ViewHolder) holder).tvUserLevel.setNumStars(1);
                        ((ViewHolder) holder).tvUserLevel.setRating(1);
                        break;
                    case "二星交易商":
                        ((ViewHolder) holder).tvUserLevel.setNumStars(2);
                        ((ViewHolder) holder).tvUserLevel.setRating(2);
                        break;
                    case "三星交易商":
                        ((ViewHolder) holder).tvUserLevel.setNumStars(3);
                        ((ViewHolder) holder).tvUserLevel.setRating(3);
                        break;
                    case "四星交易商":
                        ((ViewHolder) holder).tvUserLevel.setNumStars(4);
                        ((ViewHolder) holder).tvUserLevel.setRating(4);
                        break;
                    case "五星交易商":
                        ((ViewHolder) holder).tvUserLevel.setNumStars(5);
                        ((ViewHolder) holder).tvUserLevel.setRating(5);
                        break;
                }
            } else {
                ((ViewHolder) holder).tvUserLevel.setNumStars(0);
                ((ViewHolder) holder).tvUserLevel.setRating(0);
            }
            if (!TextUtils.isEmpty(tradingListItem.getCCF())) {
                ((ViewHolder) holder).tvCcfNumber.setText(tradingListItem.getCCF());
            } else {
                ((ViewHolder) holder).tvCcfNumber.setText("暂无");
            }
            if (!TextUtils.isEmpty(tradingListItem.getPrice())) {
                ((ViewHolder) holder).tvCurrentPrice.setText(tradingListItem.getPrice());
            } else {
                ((ViewHolder) holder).tvCurrentPrice.setText("暂无");
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_trading_type)
        TextView tvTradingType;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.btn_trading_status)
        Button btnTradingStatus;
        @BindView(R.id.tv_user_id)
        TextView tvUserId;
        @BindView(R.id.tv_user_level)
        RatingBar tvUserLevel;
        @BindView(R.id.tv_ccf_number)
        TextView tvCcfNumber;
        @BindView(R.id.tv_current_price)
        TextView tvCurrentPrice;
        @BindView(R.id.tv_user_star)
        TextView tvUserStar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
