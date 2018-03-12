package cn.cnlinfo.ccf.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.fragment.CallBackByPhoneNumFragment;
import cn.cnlinfo.ccf.fragment.CallBackBySecurityQuestionFragment;

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fixedIndicator)
    FixedIndicatorView fixedIndicator;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ibt_add)
    ImageButton ibtAdd;
    private IndicatorViewPager indicatorViewPager;
    private static final String TITLES[] = {"密保找回", "手机验证找回"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        tvTitle.setText("找回密码");
        ibtAdd.setVisibility(View.INVISIBLE);
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setFixedIndicator();
    }


    private void setFixedIndicator() {
        float selectSize = 15;
        float unSelectSize = 15;
        indicatorViewPager = new IndicatorViewPager(fixedIndicator, vp);
        indicatorViewPager.setAdapter(new CallbackPasswordAdapter(getSupportFragmentManager()));

        OnTransitionTextListener listener = new OnTransitionTextListener(selectSize, unSelectSize, getResources().getColor(R.color.color_green_3bb4bc), getResources().getColor(R.color.color_black_0d0d0d)) {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.tv_tab_fragment);
            }
        };
        indicatorViewPager.setIndicatorOnTransitionListener(listener);
        indicatorViewPager.setPageOffscreenLimit(1);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_bar, ScrollBar.Gravity.BOTTOM));
    }

    class CallbackPasswordAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public CallbackPasswordAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ForgetPasswordActivity.this).inflate(R.layout.tab_text_fragment, container, false);
                TextView tv_tab = (TextView) convertView.findViewById(R.id.tv_tab_fragment);
                tv_tab.setText(TITLES[position]);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new CallBackBySecurityQuestionFragment();
                    break;
                case 1:
                    fragment = new CallBackByPhoneNumFragment();
                    break;
            }
            return fragment;
        }
    }
}
