package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tendcloud.tenddata.TCAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.view.StopScrollViewPager;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class MainPageFragment extends BaseFragment {

    @BindView(R.id.indicator)
    FixedIndicatorView indicator;
    @BindView(R.id.vp)
    StopScrollViewPager vp;

    private IndicatorViewPager indicatorViewPager;
    private IndicatorViewPager.IndicatorFragmentPagerAdapter adapter;
    private Unbinder unbinder;
    private final String[] TITLES = {"主页信息", "循环包", "个人信息"};

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_main_page);
        unbinder = ButterKnife.bind(this,getContentView());
        TCAgent.onPageStart(getActivity(), "主页");
        init();
    }

    private void init() {
        vp.setStopScroll(true);
        setIndicator();
    }



    private void setIndicator() {
        float unSelectSize = 15;
        float selectSize = 15;
        OnTransitionTextListener listener = new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.tv_tab_text);
            }
        };
        indicator.setOnTransitionListener(listener.setColor(getResources().getColor(R.color.color_blue_4d8cd6),
                getResources().getColor(R.color.color_black_0e1214)).setSize(selectSize, unSelectSize));
        //将指示器和ViewPager绑定在一起
        indicatorViewPager = new IndicatorViewPager(indicator, vp);
        adapter = new ItemViewPagerIndicator(getChildFragmentManager());
        indicatorViewPager.setAdapter(adapter);
        indicatorViewPager.setPageOffscreenLimit(2);
        indicator.setScrollBar(new LayoutBar(getActivity(), R.layout.indicator_scroll_bar, ScrollBar.Gravity.BOTTOM));

    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        TCAgent.onPageEnd(getActivity(), "主页");
//        Logger.d("onDestroyViewLazy");
    }

    class ItemViewPagerIndicator extends IndicatorViewPager.IndicatorFragmentPagerAdapter {


        public ItemViewPagerIndicator(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_tab, container, false);
                TextView tv_tab = (TextView) convertView.findViewById(R.id.tv_tab_text);
                tv_tab.setTextSize(12);
                tv_tab.setText(TITLES[position]);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new MainPageInfoFragment();
                    break;
                case 1:
                    fragment = new CyclePackageFragment();
                    break;
                case 2:
                    fragment = new MyInfoFragment();
                    break;
                default:
                    break;
            }
            return fragment;
        }
    }


}