package cn.cnlinfo.ccf.province_city_zone.popup_window;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.orhanobut.logger.Logger;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.province_city_zone.callback.GainAreaCallBack;
import cn.cnlinfo.ccf.province_city_zone.data.ChooseArea;
import cn.cnlinfo.ccf.province_city_zone.entity.City;
import cn.cnlinfo.ccf.province_city_zone.entity.Districts;
import cn.cnlinfo.ccf.province_city_zone.entity.Province;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class ChooseAreaPopup {
    private  Button mBtnCancel;
    private  Button mBtnOk;
    private  WheelView mWvProvince;
    private  WheelView mWvCity;
    private  WheelView mWvDistricts;
    private  PopupWindow mPopupWindow;
    private  View popupView;
    private  int position = Gravity.BOTTOM;
    private static ChooseAreaPopup instance;
    private Context context;
    private List<Province> provinceList;//省对象的列表
    private  HashMap<String,List<String>> subMap = new HashMap<>();
    private  HashMap<String,List<String>> childMap = new HashMap<>();
    private List<String> cityNameList;//市的名称列表
    private ArrayList<String> provinceNameList;//省的名称列表
    private ArrayList<String> districtsNameList;//县的名称列表
    private List<City> cityList;//市对象的列表
    private List<Districts> districtsList;//县对象的列表
    private GainAreaCallBack callBack;
    private Province province;
    private City city;
    private Districts districts;

    private ChooseAreaPopup(Context context){
        this.context = context;
        popupView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow, null);
        provinceList = ChooseArea.getDatas(context);
        initView(popupView);
        createSubDatas();
        initWheel1(context);
        initAndStartListener();
    }

    public static ChooseAreaPopup getInstance(Context context){
        synchronized (ChooseAreaPopup.class){
            if (instance==null){
                instance = new ChooseAreaPopup(context);
            }
        }
        return instance;
    }
    public PopupWindow showPupopWindow(View view,GainAreaCallBack callBack){
        this.callBack = callBack;
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(view,position,0,0);
        return mPopupWindow;
    }


    //设置是否关闭PupopWindow
    public void closePupopWindow(boolean isClose){
        if (isClose){
            if (mPopupWindow != null && mPopupWindow.isShowing()){
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        }
    }


    //初始化控件点击事件监听
    private void initAndStartListener() {
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePupopWindow(true);
            }
        });
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePupopWindow(true);
                callBack.gainArea(province,city,districts);
            }
        });
    }


    //初始化控件
    private  void initView(View popupView) {
        mWvProvince = popupView.findViewById(R.id.wv_province);
        mWvCity = popupView.findViewById(R.id.wv_city);
        mWvDistricts = popupView.findViewById(R.id.wv_districts);
        mBtnCancel = popupView.findViewById(R.id.btn_cancel);
        mBtnOk = popupView.findViewById(R.id.btn_ok);
    }

    /**
     * 初始化设置联动WheelView
     */
    private  void initWheel1(final Context context) {
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 16;
        style.textSize = 12;

        mWvProvince.setWheelAdapter(new ArrayWheelAdapter(context));
        mWvProvince.setSkin(WheelView.Skin.Holo);
        mWvProvince.setWheelData(provinceNameList);
        mWvProvince.setWheelSize(5);
        mWvProvince.setStyle(style);
        mWvProvince.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                province = provinceList.get(position);
                Logger.d(province.toString());
            }
        });

        mWvCity.setWheelAdapter(new ArrayWheelAdapter(context));
        mWvCity.setSkin(WheelView.Skin.Holo);
        mWvCity.setWheelData(subMap.get(provinceList.get(mWvProvince.getSelection()).getProvinceName()));
        mWvCity.setWheelSize(5);
        mWvCity.setStyle(style);
        mWvProvince.join(mWvCity);//加入mWvcity与mWvProvince联动
        mWvProvince.joinDatas(subMap);
        mWvCity.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                city = province.getCityList().get(position);
                Logger.d(city.toString());
            }
        });

        mWvDistricts.setWheelAdapter(new ArrayWheelAdapter(context));
        mWvDistricts.setSkin(WheelView.Skin.Holo);
        mWvDistricts.setWheelSize(5);
        mWvDistricts.setWheelData(childMap.get((subMap.get(provinceList.get(mWvProvince.getSelection()).getProvinceName())).get(mWvCity.getSelection())));
        mWvDistricts.setStyle(style);
        mWvCity.join(mWvDistricts);//加入mWvDistricts与mWvCity联动
        mWvCity.joinDatas(childMap);
        mWvDistricts.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                districts = city.getDistrictsList().get(position);
                Logger.d(districts.toString());
            }
        });

    }

    /**
     * 创建市的map，以省的名字作为键
     * @return
     */
    public HashMap<String,List<String>> createSubDatas(){
        provinceNameList = new ArrayList<>();
        for(int i = 0;i<provinceList.size();i++){

            cityNameList = new ArrayList<>();
            //省对应的市的列表
            cityList = provinceList.get(i).getCityList();
            //每个市的列表的项对应相应的县的列表
            createChildDatas();
            //获取省名称的列表
            provinceNameList.add(provinceList.get(i).getProvinceName());
            subMap.put(provinceList.get(i).getProvinceName(),cityNameList);
        }
        return subMap;
    }

    public  HashMap<String, List<String>> createChildDatas() {
        for (int i = 0;i<cityList.size();i++){
            districtsNameList = new ArrayList<>();
            cityNameList.add(cityList.get(i).getCityName());
            districtsList = cityList.get(i).getDistrictsList();
            for (Districts districts : districtsList) {
                districtsNameList.add(districts.getDistrictsName());
            }
            childMap.put(cityList.get(i).getCityName(),districtsNameList);
        }
        return childMap;
    }
}
