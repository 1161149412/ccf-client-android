package cn.cnlinfo.ccf.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.orhanobut.logger.Logger;
import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.mvc.MVCHelper;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.activity.TradingCenterActivity;
import cn.cnlinfo.ccf.entity.ItemPriceListEntity;
import cn.cnlinfo.ccf.event.ErrorMessageEvent;
import cn.cnlinfo.ccf.event.UserMoneyEvent;
import cn.cnlinfo.ccf.mvc.datasource.PriceChartDataSource;
import cn.cnlinfo.ccf.mvc.helper.MVCUltraHelper;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.DateUtil;
import cn.cnlinfo.ccf.view.CleanEditText;
import cn.cnlinfo.ccf.view.DataValueFormatter;
import cn.cnlinfo.ccf.view.MyMarkerView;
import cn.cnlinfo.ccf.view.MyYValueFormatter;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by JP on 2017/10/11 0011.
 * http://www.cnblogs.com/wangfeng520/p/5984077.html  MPAndroidChart
 */

public class TradingCenterFragment extends BaseFragment implements View.OnClickListener, OnChartValueSelectedListener {

    @BindView(R.id.sp_hang_sell_type)
    Spinner spHangSellType;
    @BindView(R.id.et_num)
    CleanEditText etNum;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_enter_trading_platform)
    Button btnEnterTradingPlatform;
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.spread_pie_chart)
    LineChart spreadPieChart;
    @BindView(R.id.et_safe_pass)
    CleanEditText etSafePass;
    @BindView(R.id.tv_ccf_cur_price)
    TextView tvCcfCurPrice;
    @BindView(R.id.tv_ccf_number)
    TextView tvCcfNumber;
    @BindView(R.id.btn_choose_start_time)
    Button btnChooseStartTime;
    @BindView(R.id.btn_choose_end_time)
    Button btnChooseEndTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private Unbinder unbinder;
    private int type = 0;
    private MVCHelper mvcHelper;
    private ArrayList<String> xVals;
    private ArrayList<Entry> entryArrayList;
    private String currentTime;
    private PriceChartDataSource priceChartDataSource;
    private IDataAdapter<List<ItemPriceListEntity>> adapter;
    private String monthOfYear;
    private String day;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_trading_center);
        unbinder = ButterKnife.bind(this, getContentView());
        TCAgent.onPageStart(getActivity(), "交易中心");
        xVals = new ArrayList<>();//x轴的数据集合
        entryArrayList = new ArrayList<>();//y轴数据集合
        currentTime = DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
        tvStartTime.setText("2018-02-11");
        tvEndTime.setText(currentTime);

        getCurrentUserIntegral();//获取当前用户的ccf数量和实时价格
        setMPAndroidChart();
        getPriceList();//获取价格变动列表，绘制价格走势图
        startHangSellOrBuy();//挂卖/挂买
        setEditTextFocus(etNum);
        btnEnterTradingPlatform.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnChooseStartTime.setOnClickListener(this);
        btnChooseEndTime.setOnClickListener(this);
    }

    //给字符串添加单引号
    @NonNull
    private String addCharForString(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(0, "\'");
        stringBuilder.insert(stringBuilder.length(), "\'");
        return stringBuilder.toString();
    }

    //获取当前用户的ccf数量和实时价格,积分
    private void getCurrentUserIntegral() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETUSERINTEGAL, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                UserMoneyEvent userMoney = JSONObject.parseObject(data.getJSONObject("UserMoney").toJSONString(), UserMoneyEvent.class);
                tvCcfCurPrice.setText(userMoney.getPrice());
                tvCcfNumber.setText(userMoney.getCCF());
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

    //初始化MPAndroidChart
    private void setMPAndroidChart() {
        spreadPieChart.setOnChartValueSelectedListener(this);
        spreadPieChart.setNoDataText("You need to provide data for the chart.");
        spreadPieChart.setDrawBorders(false);//设置图表内格子外的边框是否显示
        spreadPieChart.setPinchZoom(true);//挤压缩放为true
        spreadPieChart.setDrawGridBackground(true);//是否绘制网格背景
        spreadPieChart.setGridBackgroundColor(getContext().getResources().getColor(R.color.color_black_0e1214_alpha_75));//网格背景颜色
        spreadPieChart.setDrawMarkerViews(true);
        spreadPieChart.setScaleYEnabled(true);//设置是否能缩放
        spreadPieChart.setDoubleTapToZoomEnabled(true);//双击缩放
        spreadPieChart.setDescription("CCF\np/d");//描述语言
        spreadPieChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //自定义   MarkerView
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        spreadPieChart.setMarkerView(mv);
        Legend l = spreadPieChart.getLegend();//图例
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTextSize(10f);//set the size of text
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        XAxis xl = spreadPieChart.getXAxis();
        xl.setDrawLabels(true);//设置为true打开绘制轴的标签。
        xl.setDrawAxisLine(true);//设置为true，绘制轴线
        xl.setLabelRotationAngle(-20);//设置x轴字体显示角度
        xl.setDrawGridLines(true);//是否画线
        xl.setTextColor(getContext().getResources().getColor(R.color.color_black_0e1214));//设置x轴文字的颜色
        xl.setAxisLineColor(getContext().getResources().getColor(R.color.color_red_fe4a4a));//设置x轴线的颜色
        xl.setGridColor(getContext().getResources().getColor(R.color.color_red_fe4a4a));//设置x轴网格线颜色


        YAxis leftAxis = spreadPieChart.getAxisLeft();
        leftAxis.setDrawLabels(true);//设置为true打开绘制轴的标签。
        leftAxis.setDrawAxisLine(true);//设置为true，绘制轴线
        leftAxis.setTextColor(getContext().getResources().getColor(R.color.color_black_0e1214));//设置y轴文字的颜色
        leftAxis.setAxisLineColor(getContext().getResources().getColor(R.color.color_red_fe4a4a));//设置y轴线的颜色
        leftAxis.setGridColor(getContext().getResources().getColor(R.color.color_red_fe4a4a));//设置y轴网格线颜色
        //leftAxis.setValueFormatter(new LargeValueFormatter());//
        leftAxis.setValueFormatter(new MyYValueFormatter());//自定义y数据格式化方式
        leftAxis.setDrawGridLines(true);//是否画线
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        spreadPieChart.getAxisRight().setEnabled(false);//设置轴是否被绘制。默认绘制,false不会被绘制。
    }

    //set the chart of ccf's price
    private void getPriceList() {
        mvcHelper = new MVCUltraHelper<List<ItemPriceListEntity>>(ptr);
        mvcHelper.setNeedCheckNetwork(true);
        priceChartDataSource = new PriceChartDataSource(addCharForString(tvStartTime.getText().toString()), addCharForString(tvEndTime.getText().toString()));
        mvcHelper.setDataSource(priceChartDataSource);
        adapter = new IDataAdapter<List<ItemPriceListEntity>>() {
            @Override
            public void notifyDataChanged(List<ItemPriceListEntity> itemPriceListEntities, boolean isRefresh) {
                Logger.d(itemPriceListEntities.toString());

                if (!xVals.isEmpty() && xVals.size() > 0 && !entryArrayList.isEmpty() && entryArrayList.size() > 0) {
                    xVals.clear();
                    entryArrayList.clear();
                }
                if (itemPriceListEntities.isEmpty() || itemPriceListEntities.size() == 0) {
                    toast("暂无数据");
                }
                for (int i = 0; i < itemPriceListEntities.size(); i++) {
                    xVals.add(itemPriceListEntities.get(i).getAddTime());
                    entryArrayList.add(new BarEntry((float) itemPriceListEntities.get(i).getPrice(), i));
                }
                LineDataSet lineDataSet = new LineDataSet(entryArrayList, "价格p(美金)");
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet);
                LineData data = new LineData(xVals, dataSets);
                //data.setValueFormatter(new LargeValueFormatter());
                data.setValueFormatter(new DataValueFormatter());//设置数据显示格式
                // add space between the dataset groups in percent of bar-width
                // data.setValueFormatter(new CustomerValueFormatter());
                data.setDrawValues(true);
                data.setValueTextColor(Color.WHITE);
                data.setValueTextSize(13);
                //data.setValueTypeface(tf);

                spreadPieChart.setData(data);
                spreadPieChart.animateXY(800, 800);//图表数据显示动画
                spreadPieChart.setVisibleXRangeMaximum(15);//设置屏幕显示条数
                spreadPieChart.invalidate();
            }

            @Override
            public List<ItemPriceListEntity> getData() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
        mvcHelper.setAdapter(adapter);
        mvcHelper.refresh();

    }

    /**
     * 开始挂卖/挂买
     */

    private void startHangSellOrBuy() {
        spHangSellType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        mvcHelper.destory();
        TCAgent.onPageEnd(getActivity(), "交易中心");
        HttpRequest.cancel(Constant.OPERATE_CCF_HOST + API.HANGSELL);
        HttpRequest.cancel(Constant.PRICE_LIST_HOST + API.PRICELIST);
        HttpRequest.cancel(Constant.GET_DATA_HOST + API.GETUSERINTEGAL);
    }

    public void chooseDateRefresh(int year, int month, int dayOfMonth, TextView textView) {
        if (Integer.valueOf(tvEndTime.getText().toString().substring(0, 3)) < Integer.valueOf(tvStartTime.getText().toString().substring(0, 3))) {
            toast("起始日期不能超过结束日期");
            return;
        }
        if (Integer.valueOf(tvEndTime.getText().toString().substring(0, 3)) == Integer.valueOf(tvStartTime.getText().toString().substring(0, 3))) {
            if (Integer.valueOf(tvEndTime.getText().toString().substring(5, 6)) < Integer.valueOf(tvStartTime.getText().toString().substring(5, 6))) {
                toast("起始日期不能超过结束日期");
                return;
            }
        }
        if (Integer.valueOf(tvEndTime.getText().toString().substring(0, 3)) == Integer.valueOf(tvStartTime.getText().toString().substring(0, 3))) {
            if (Integer.valueOf(tvEndTime.getText().toString().substring(5, 6)) == Integer.valueOf(tvStartTime.getText().toString().substring(5, 6))) {
                if (Integer.valueOf(tvEndTime.getText().toString().substring(8, 9)) < Integer.valueOf(tvStartTime.getText().toString().substring(8, 9))) {
                    toast("起始日期不能超过结束日期");
                    return;
                }
            }
        }

        if (year > Calendar.getInstance().get(Calendar.YEAR)) {
            toast("年份不能超过当前年份");
            return;
        } else {
            if (year == Calendar.getInstance().get(Calendar.YEAR)) {
                if (month > Calendar.getInstance().get(Calendar.MONTH)) {
                    toast("月份不能超过当前月份");
                    return;
                } else {
                    if (month==Calendar.getInstance().get(Calendar.MONTH)){
                        if (dayOfMonth > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                            toast("选择的号数不能超过当前号数");
                            return;
                        } else {
                            if (month < 10) {
                                monthOfYear = "0" + String.valueOf(month + 1);
                            } else {
                                monthOfYear = String.valueOf(month + 1);
                            }
                            if (dayOfMonth < 10) {
                                day = "0" + String.valueOf(dayOfMonth);
                            } else {
                                day = String.valueOf(dayOfMonth);
                            }
                            textView.setText(String.valueOf(year) + "-" + monthOfYear + "-" + day);
                            priceChartDataSource = new PriceChartDataSource(addCharForString(tvStartTime.getText().toString()), addCharForString(tvEndTime.getText().toString()));
                            mvcHelper.setDataSource(priceChartDataSource);
                            mvcHelper.refresh();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String num = etNum.getText().toString();
                String safePass = etSafePass.getText().toString();
                if (!TextUtils.isEmpty(num) && !TextUtils.isEmpty(safePass)) {
                    if (type == 0) {//挂卖
                        RequestParams params = new RequestParams();
                        params.addFormDataPart("sellerID", UserSharedPreference.getInstance().getUser().getUserID());
                        params.addFormDataPart("ccfValue", num);
                        params.addFormDataPart("pwd", safePass);
                        HttpRequest.post(Constant.OPERATE_CCF_HOST + API.HANGSELL, params, new CCFHttpRequestCallback() {
                            @Override
                            protected void onDataSuccess(JSONObject data) {
                                showMessage(0, "挂卖成功");
                                etNum.setText("");
                                etNum.clearFocus();
                                etNum.setFocusable(false);
                            }

                            @Override
                            protected void onDataError(int code, boolean flag, String msg) {
                                showMessage(code, msg);
                            }
                        });
                    } else {//挂买
                        RequestParams params = new RequestParams();
                        params.addFormDataPart("userID", UserSharedPreference.getInstance().getUser().getUserID());
                        params.addFormDataPart("ccfValue", num);
                        params.addFormDataPart("pwd", safePass);
                        HttpRequest.post(Constant.OPERATE_CCF_HOST + API.HANGBUY, params, new CCFHttpRequestCallback() {
                            @Override
                            protected void onDataSuccess(JSONObject data) {
                                showMessage(0, "挂买成功");
                                etNum.setText("");
                                etNum.clearFocus();
                                etNum.setFocusable(false);
                            }

                            @Override
                            protected void onDataError(int code, boolean flag, String msg) {
                                showMessage(code, msg);
                            }
                        });
                    }
                } else {
                    toast("输入框不能为空");
                }
                break;
            //进入交易大厅
            case R.id.btn_enter_trading_platform:
                startActivity(new Intent(getActivity(), TradingCenterActivity.class));
                break;
            //选择开始时间查询数据
            case R.id.btn_choose_start_time:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chooseDateRefresh(year, month, dayOfMonth, tvStartTime);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();

                break;
            //选择结束时间查询数据
            case R.id.btn_choose_end_time:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chooseDateRefresh(year, month, dayOfMonth, tvEndTime);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();

                break;
            default:
                break;
        }
    }

    //选择图表上的值的具体值
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        //toast(xVals.get(e.getXIndex())+"的ccf价格为:"+e.getVal()+"$");
    }

    @Override
    public void onNothingSelected() {
        Logger.d("Nothing selected");
    }

}
