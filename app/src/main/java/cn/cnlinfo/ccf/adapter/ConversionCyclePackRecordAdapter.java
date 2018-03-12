package cn.cnlinfo.ccf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.flyco.dialog.listener.OnBtnClickL;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.dialog.DialogCreater;
import cn.cnlinfo.ccf.entity.CyclePackRecordEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.RefreshCyclePackEvent;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/12/22 0022.
 */

public class ConversionCyclePackRecordAdapter extends BaseRecyclerAdapter<CyclePackRecordEntity> {
    private Context context;

    public ConversionCyclePackRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_conversion_cycle_pack_record, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final CyclePackRecordEntity cyclePackRecordEntity = list.get(position);
            if (cyclePackRecordEntity != null) {
                if (!TextUtils.isEmpty(cyclePackRecordEntity.getCreateTime())) {
                    ((ViewHolder) holder).tvTime.setText(cyclePackRecordEntity.getCreateTime());
                } else {
                    ((ViewHolder) holder).tvTime.setText("暂无");
                }
                if (!TextUtils.isEmpty(cyclePackRecordEntity.getStatus())) {
                    ((ViewHolder) holder).btnCycleStatus.setText(cyclePackRecordEntity.getStatus());
//                    Logger.d(cyclePackRecordEntity.getStatus());
                    if (((ViewHolder) holder).btnCycleStatus.getText().toString().equals("未解压")) {
                        ((ViewHolder) holder).btnCycleStatus.setClickable(true);
                        ((ViewHolder) holder).btnCycleStatus.setBackgroundColor(context.getResources().getColor(R.color.color_gray_eaeaea));
                        ((ViewHolder) holder).btnCycleStatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(cyclePackRecordEntity.getPackID())) {
                                    RequestParams params = new RequestParams();
                                    params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
                                    params.addFormDataPart("packid", cyclePackRecordEntity.getPackID());
                                    HttpRequest.post(Constant.GET_MESSAGE_CODE_HOST + API.EXTEACTCYCLEPACK, params, new CCFHttpRequestCallback() {
                                        @Override
                                        protected void onDataSuccess(JSONObject data) {
                                            ((ViewHolder) holder).btnCycleStatus.setText("解压完成");
                                            ((ViewHolder) holder).btnCycleStatus.setClickable(false);
                                            DialogCreater.createTipsWithCancelDialog(context, "解压循环包", "成功解压" + cyclePackRecordEntity.getPackID() + "号循环包","确定",false, new OnBtnClickL() {
                                                @Override
                                                public void onBtnClick() {
                                                    listener.extractRefresh();
                                                    EventBus.getDefault().post(new RefreshCyclePackEvent());
                                                }
                                            }).show();
                                        }

                                        @Override
                                        protected void onDataError(int code, boolean flag, String msg) {
                                            EventBus.getDefault().post(new ErrorMessageEvent(code, msg));
                                        }

                                        @Override
                                        public void onFailure(int errorCode, String msg) {
                                            super.onFailure(errorCode, msg);
                                            EventBus.getDefault().post(new ErrorMessageEvent(errorCode, msg));
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        ((ViewHolder) holder).btnCycleStatus.setClickable(false);
                        ((ViewHolder) holder).btnCycleStatus.setBackgroundColor(context.getResources().getColor(R.color.color_gray_eae8db));
                    }
                } else {
                    ((ViewHolder) holder).btnCycleStatus.setText("暂无");
                }
                if (!TextUtils.isEmpty(cyclePackRecordEntity.getPackID())) {
                    ((ViewHolder) holder).tvCyclePackId.setText(cyclePackRecordEntity.getPackID());
                } else {
                    ((ViewHolder) holder).tvCyclePackId.setText("暂无");
                }
                if (!TextUtils.isEmpty(cyclePackRecordEntity.getIsSuccess())) {
                    ((ViewHolder) holder).tvConversionStatus.setText(cyclePackRecordEntity.getIsSuccess());
                } else {
                    ((ViewHolder) holder).tvConversionStatus.setText("暂无");
                }
                if (!TextUtils.isEmpty(cyclePackRecordEntity.getMsg())) {
                    ((ViewHolder) holder).tvCyclePackInfo.setText(cyclePackRecordEntity.getMsg());
                } else {
                    ((ViewHolder) holder).tvCyclePackInfo.setText("暂无");
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ExtractRefreshListener listener;

    public void setExtractRefreshListener(ExtractRefreshListener listener) {
        this.listener = listener;
    }

    public interface ExtractRefreshListener {
        void extractRefresh();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.btn_cycle_status)
        Button btnCycleStatus;
        @BindView(R.id.tv_cycle_pack_id)
        TextView tvCyclePackId;
        @BindView(R.id.tv_conversion_status)
        TextView tvConversionStatus;
        @BindView(R.id.tv_cycle_pack_info)
        TextView tvCyclePackInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
