package cn.cnlinfo.ccf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.API;
import cn.cnlinfo.ccf.Constant;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.UserSharedPreference;
import cn.cnlinfo.ccf.activity.WebActivity;
import cn.cnlinfo.ccf.entity.AccountInfo;
import cn.cnlinfo.ccf.entity.ItemNewsEntity;
import cn.cnlinfo.ccf.entity.PlatformInfo;
import cn.cnlinfo.ccf.net_okhttpfinal.CCFHttpRequestCallback;
import cn.cnlinfo.ccf.utils.NetUtil;
import cn.cnlinfo.ccf.view.UpDownTextView;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by JP on 2017/10/23 0023.
 */

public class MainPageInfoFragment extends BaseFragment {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.dot_0)
    View dot0;
    @BindView(R.id.dot_1)
    View dot1;
    @BindView(R.id.dot_2)
    View dot2;
    Unbinder unbinder;
    @BindView(R.id.tv_up_down)
    UpDownTextView tvUpDown;
    @BindView(R.id.gv_account_info)
    GridView gvAccountInfo;
    @BindView(R.id.gv_platform_info)
    GridView gvPlatformInfo;
    private SimpleAdapter simpleAccountAdapter;
    private SimpleAdapter simplePlatformAdapter;
    private ScheduledExecutorService scheduledExecutorService;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Integer> imageUrls;
    private List<ImageView> images;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    private int[] nums = {R.drawable.img_guide_one_cooperation, R.drawable.img_guide_two_advantage, R.drawable.img_guide_three_discount};
    private List<View> dots;
    private String accountTitles[] = {  "账号", "级别", "邀请码", "碳控因子", "消费积分", "产品积分", "注册积分","碳控积分"};
    private String platformTitles[] = {"总量", "平台已激活", "价格", "平台待激活", "用户已激活", "用户待激活", "碳控积分", "消费积分"};
    private List<String> accountAnswer;
    private List<String> platformAnswer;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.item_fragment_one);
        unbinder = ButterKnife.bind(this, getContentView());
        init();
    }

    private void init() {
        //网络未连接，等待dialog不出现
        if(NetUtil.isConnected(getContext())){
            showWaitingDialog(true);
        }
        setBannerData();
        setNoticeInfo();
        setAccountAnwserData();
        setPlatformAnwserData();
    }

    /**
     * 设置个人信息数据
     */
    private void setAccountAnwserData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("userid", UserSharedPreference.getInstance().getUser().getUserID());
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETUSERINFO, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
               // Logger.d(data.toJSONString());
                String accountInfoJsonString = data.getJSONObject("Userinfo").toJSONString();
                UserSharedPreference.getInstance().setAccountInfo(accountInfoJsonString);
                AccountInfo accountInfo = JSONObject.parseObject(accountInfoJsonString,AccountInfo.class);
                accountAnswer = new ArrayList<>();
                accountAnswer.add(accountInfo.getUCode());
                int level = accountInfo.getInLevel();
                switch (level) {
                    //用户级别是0，1，2，3，4，5总的套餐权值大于0则是普通用户
                    case 0:
                        if (accountInfo.getTotalMealWeight()>0){
                            accountAnswer.add("普通用户");
                        }else {
                            accountAnswer.add("体验用户");
                        }

                        break;
                    case 1:
                        accountAnswer.add("1星用户");
                        break;
                    case 2:
                        accountAnswer.add("2星用户");
                        break;
                    case 3:
                        accountAnswer.add("3星用户");
                        break;
                    case 4:
                        accountAnswer.add("4星用户");
                        break;
                    case 5:
                        accountAnswer.add("5星用户");
                        break;

                }
                accountAnswer.add(accountInfo.getInvitationCode());
                accountAnswer.add(String.valueOf(accountInfo.getCCF()));
                accountAnswer.add(String.valueOf(accountInfo.getConsumeIntegral()));
                accountAnswer.add(String.valueOf(accountInfo.getProductScore()));
                accountAnswer.add(String.valueOf(accountInfo.getRegisterIntegral()));
                accountAnswer.add(String.valueOf(accountInfo.getCarbonIntegral()));
                List<Map<String, String>> list = new ArrayList<>();
                for (int i = 0; i < accountTitles.length; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", accountTitles[i]);
                    map.put("answer", accountAnswer.get(i));
                    list.add(map);
                }
                simpleAccountAdapter = new SimpleAdapter(getActivity(),list, R.layout.item_gv_info, new String[]{"title", "answer"}, new int[]{R.id.item_tv_title, R.id.item_tv_answer});
                if (gvAccountInfo!=null){
                    gvAccountInfo.setAdapter(simpleAccountAdapter);
                }
                showWaitingDialog(false);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                showWaitingDialog(false);
                showMessage(code, msg);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                showWaitingDialog(false);
                showMessage(errorCode,msg);

            }
        });
    }

    /**
     * 设置平台信息数据
     */
    private void setPlatformAnwserData() {
        platformAnswer = new ArrayList<>();
        HttpRequest.get(Constant.GET_DATA_HOST + API.GETPLATFORMINFO, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                JSONObject jsonObject = data.getJSONObject("platforminfo");
                PlatformInfo platformInfo = JSONObject.parseObject(jsonObject.toJSONString(), PlatformInfo.class);
                platformAnswer.add("1.2亿");
                platformAnswer.add(platformInfo.getActiveCCF());
                platformAnswer.add(platformInfo.getCurrentPrice());
                platformAnswer.add(platformInfo.getTotalInertiaCCF());
                platformAnswer.add(platformInfo.getUserCCF());
                platformAnswer.add(platformInfo.getUserDCCF());
                platformAnswer.add(platformInfo.getCCScore());
                platformAnswer.add(platformInfo.getActiveConsumeScore());
                List<Map<String, String>> list = new ArrayList<>();
                if (platformAnswer != null && platformAnswer.size() > 0) {
                    for (int i = 0; i < platformTitles.length; i++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("title", platformTitles[i]);
                        map.put("answer", platformAnswer.get(i));
                        list.add(map);
                    }
                }
                simplePlatformAdapter = new SimpleAdapter(getActivity(), list, R.layout.item_gv_info, new String[]{"title", "answer"}, new int[]{R.id.item_tv_title, R.id.item_tv_answer});
                if (gvPlatformInfo != null) {
                    gvPlatformInfo.setAdapter(simplePlatformAdapter);
                }
                showWaitingDialog(false);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                //toast("获取平台信息失败");
                showWaitingDialog(false);
                showMessage(code,msg);

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                showWaitingDialog(false);
                showMessage(errorCode,msg);

            }
        });
    }

    /**
     * 设置上下滚动文本信息
     */
    private void setUpDownTextView(final List<ItemNewsEntity> itemNewsList) {
        tvUpDown.setText("welcome to Carbon control factor");
        if (tvUpDown != null) {
            tvUpDown.setSingleLine();
            tvUpDown.setGravity(Gravity.CENTER);
            tvUpDown.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
            tvUpDown.setTextSize(12);
            tvUpDown.setTextList(itemNewsList);
            tvUpDown.setDuring(500);
            tvUpDown.startAutoScroll();
            tvUpDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemNewsList!=null&&itemNewsList.size()>0){
                        ItemNewsEntity itemNews = itemNewsList.get(tvUpDown.getCurrentIndex());
                        String uri = String.format(Constant.GET_DETAIL_HOST, itemNews.getNewsID());
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("url", uri);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    /**
     * 获取公告信息
     */
    private void setNoticeInfo() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("CurrentPageIndex", 1);
        params.addFormDataPart("PageSize", 4);
        /**
         * 1是公告 2是新闻
         */
        params.addFormDataPart("type", 2);
        params.addFormDataPart("Orderby", "ORDER BY IssueDate DESC");
        HttpRequest.post(Constant.GET_DATA_HOST + API.GETNEWSLIST, params, new CCFHttpRequestCallback() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                JSONArray jsonArray = data.getJSONArray("Newslist");
                List<ItemNewsEntity> itemNewsList = JSONArray.parseArray(jsonArray.toJSONString(), ItemNewsEntity.class);
                setUpDownTextView(itemNewsList);
                showWaitingDialog(false);
            }

            @Override
            protected void onDataError(int code, boolean flag, String msg) {
                //toast("获取公告失败");
                showWaitingDialog(false);
                showMessage(code,msg);

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                showWaitingDialog(false);
                showMessage(errorCode,msg);
            }
        });
    }


    /**
     * 设置Banner的显示图片
     */
    private void setBannerData() {
        //显示的图片
        images = new ArrayList<ImageView>();
        imageUrls = new ArrayList<>();
        imageUrls.add(R.drawable.banner1);
        imageUrls.add(R.drawable.banner2);
        imageUrls.add(R.drawable.banner3);
        for (int i = 0; i < imageUrls.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(imageUrls.get(i)).fitCenter().error(nums[i]).placeholder(nums[i]).into(imageView);
            images.add(imageView);
        }
        dots = new ArrayList<>();
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        viewPagerAdapter = new ViewPagerAdapter();
        vp.setAdapter(viewPagerAdapter);
        vp.setOffscreenPageLimit(3);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.guide_circle_white);
                dots.get(oldPosition).setBackgroundResource(R.drawable.guide_circle_gray);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        /**
         * 开启线程池管理Banner
         */
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

    class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % images.size();
            mHandler.sendEmptyMessage(0);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (vp != null) {
                vp.setCurrentItem(currentItem);
            }
        }
    };

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = images.get(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getActivity(), "" + (position + 1), Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(imageView);
            return imageView;
        }
    }
}
