package cn.cnlinfo.ccf.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tendcloud.tenddata.TCAgent;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.adapter.MainPageFragmentAdapter;
import cn.cnlinfo.ccf.event.ShowMainPageEvent;
import cn.cnlinfo.ccf.fragment.CCMallFragment;
import cn.cnlinfo.ccf.fragment.CCUnionFragment;
import cn.cnlinfo.ccf.fragment.GaugePanelFragment;
import cn.cnlinfo.ccf.fragment.MainPageFragment;
import cn.cnlinfo.ccf.fragment.TradingCenterFragment;
import cn.cnlinfo.ccf.manager.AppManage;
import cn.cnlinfo.ccf.utils.QRCodeUtil;
import cn.cnlinfo.ccf.view.StopScrollViewPager;
import cn.finalteam.okhttpfinal.HttpRequest;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainPageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp)
    StopScrollViewPager vp;
    @BindView(R.id.tv_gauage_panel)
    TextView tvGauagePanel;
    @BindView(R.id.tv_trading_center)
    TextView tvTradingCenter;
    @BindView(R.id.tv_cc_mall)
    TextView tvCcMall;
    @BindView(R.id.tv_cc_union)
    TextView tvCcUnion;
    @BindView(R.id.tv_main_page)
    TextView tvMainPage;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_buttom_nav_bar)
    FrameLayout flButtomNavBar;
    private List<Fragment> fragmentList;
    private MainPageFragmentAdapter pageFragmentAdapter;
    private Unbinder unbinder;
    private AlertDialog alertDialog;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    private PopupMenu popupMenu;
    private static final int REQUEST_CODE_SCAN = 100;
    private long exitTime = 0;
    private long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        TCAgent.onPageStart(this, "主页");
        validLoadGuidePage();
        //设置为false是停止滑动ViewPager切换Fragment
        vp.setStopScroll(true);
        init();
    }


    /**
     * 验证是否加载引导页
     */
    private void validLoadGuidePage() {
        if (!validNewVersionByVersionCode()) {
            if (validLogin()) {
                if (UserSharedPreference.getInstance().getIsFirstLogin()) {
                    showRiskWarningDialog();
                    UserSharedPreference.getInstance().setIsFirstLogin(false);
                } else {

                }
            } else {
                finish();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setPopupMenu(View view) {
        popupMenu = new PopupMenu(this, view, Gravity.END);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        Menu menu = popupMenu.getMenu();
        menuInflater.inflate(R.menu.layout_menu, menu);
        try {
            /**
             * 反射设置图片
             */
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
            mHelper.setForceShowIcon(true);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.sweep:
                        MainPageActivityPermissionsDispatcher.toRichScanWithCheck(MainPageActivity.this);
                        break;
                    case R.id.my_qrcode:
                        startActivity(new Intent(MainPageActivity.this, BuildQRCodeActivity.class));
                        break;
                    case R.id.exit:
                        exit();
                        finish();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MainPageActivity.this, SettingActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 申请需要的权限
     */
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void toRichScan() {
        QRCodeUtil.toRichScan(this, REQUEST_CODE_SCAN);
    }

    /**
     * 拒绝
     */
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void refuse() {
        toast("权限被拒绝");
    }

    /**
     * 不再询问
     */
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void neverEnquire() {
        toast("拍照权限被禁用，如果要使用拍照请手动启用");
    }

    /**
     * 当第一次申请权限时，用户选择拒绝，再次申请时调用此方法，在此方法中提示用户为什么需要这个权限，这需要展现给用户，而用户可以选择“继续”或者“中止”当前的权限许可
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("申请相机权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //再次执行请求
                        request.proceed();// 提示用户权限使用的对话框
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainPageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 弹出风险提示dialog
     */
    private void showRiskWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_risk_warning, null);
        ImageView imageView = view.findViewById(R.id.iv_close_dialog);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }


    /**
     * 初始化操作
     */
    private void init() {
        ibtBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("主页");
        registerOnClickListener();
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        ibtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupMenu(v);
                popupMenu.show();
            }
        });
        fragmentList = new ArrayList<>();
        fragmentList = setFragmentList();
        if (fragmentList != null && fragmentList.size() > 0) {
            pageFragmentAdapter = new MainPageFragmentAdapter(getSupportFragmentManager(), fragmentList);
            vp.setAdapter(pageFragmentAdapter);
        }
        vp.setOffscreenPageLimit(4);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTvMainPageBackgroundColor();
                        break;
                    case 1:
                        setTvGauagePanelBackgroundColor();
                        break;
                    case 2:
                        setTvTradingCenterBackgroundColor();
                        break;
                    case 3:
                        setTvCcMallBackgroundColor();
                        break;
                    case 4:
                        setTvCcUnionBackgroundColor();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private List<Fragment> setFragmentList() {
        MainPageFragment mainPageFragment = new MainPageFragment();
        fragmentList.add(mainPageFragment);
        fragmentList.add(new GaugePanelFragment());
        fragmentList.add(new TradingCenterFragment());
        fragmentList.add(new CCMallFragment());
        fragmentList.add(new CCUnionFragment());
        return fragmentList;
    }

    private void registerOnClickListener() {
        tvMainPage.setOnClickListener(this);
        tvGauagePanel.setOnClickListener(this);
        tvTradingCenter.setOnClickListener(this);
        tvCcMall.setOnClickListener(this);
        tvCcUnion.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //解决内存泄漏
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(this, "主页");
        vp = null;
        HttpRequest.post(cn.cnlinfo.ccf.Constant.GET_DATA_HOST + API.GETUSERINTEGAL);
    }

    //cc商城中点击主页切换到app主页
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowMainPageEvent event){
        vp.setCurrentItem(0,false);
        setTvMainPageBackgroundColor();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.tv_main_page:
                vp.setCurrentItem(0, false);
                setTvMainPageBackgroundColor();
                break;
            case R.id.tv_gauage_panel:
                //smoothScroll为false就是去除切换fragment的动画效果
                vp.setCurrentItem(1, false);
                setTvGauagePanelBackgroundColor();

                break;
            case R.id.tv_trading_center:
                vp.setCurrentItem(2, false);
                setTvTradingCenterBackgroundColor();

                break;
            case R.id.tv_cc_mall:
                vp.setCurrentItem(3, false);
                setTvCcMallBackgroundColor();

                break;
            case R.id.tv_cc_union:
                vp.setCurrentItem(4, false);
                setTvCcUnionBackgroundColor();

                break;

        }
    }

    private void setTvMainPageBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("主页");
        flButtomNavBar.setVisibility(View.VISIBLE);
    }

    private void setTvGauagePanelBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("仪表盘");
        flButtomNavBar.setVisibility(View.VISIBLE);
    }

    private void setTvCcUnionBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("CC联盟");
        flButtomNavBar.setVisibility(View.VISIBLE);
    }

    private void setTvCcMallBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTitle.setText("CC商城");
        flButtomNavBar.setVisibility(View.GONE);
    }

    private void setTvTradingCenterBackgroundColor() {
        tvMainPage.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvGauagePanel.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcUnion.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvCcMall.setBackgroundColor(getResources().getColor(R.color.color_white_faf9f9));
        tvTradingCenter.setBackgroundColor(getResources().getColor(R.color.color_blue_4d8cd6));
        tvTitle.setText("交易中心");
        flButtomNavBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Logger.d("scan QRCode info " + content);
                /**
                 * http://qm.qq.com/cgi-bin/qm/qr?k=cycGNWnspzlbVYtdI8ubsxf7JpUaYWeI
                 * 判断设备中是否存在与之相同的scheme
                 */
              /*  PackageManager packageManager = getPackageManager();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isValid = !activities.isEmpty();
                if (isValid) {
                    startActivity(intent);
                }*/
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", content);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            currentTime = System.currentTimeMillis();
            if ((currentTime - exitTime) > 2000) {
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = currentTime;
            } else {
                AppManage.getInstance().exit(this);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
