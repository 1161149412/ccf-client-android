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
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.fragment.RegisterUserFragment;

public class RegisterMemberActivity extends BaseActivity {
    @BindView(R.id.fixedIndicator)
    FixedIndicatorView fixedIndicator;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private IndicatorViewPager indicatorViewPager;
    private Unbinder unbinder;
    private RegisterMemberAdapter registerMemberAdapter;
    //private String[] TITLES = {"用户注册", "商家注册", "代理注册"};
    private String[] TITLES = {"用户注册"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("注册成员");
        ibtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setFixedIndicator();
    }

    private void setFixedIndicator() {
        float unSelectSize = 15;
        float selectSize = 15;

        registerMemberAdapter = new RegisterMemberAdapter(getSupportFragmentManager());
        indicatorViewPager = new IndicatorViewPager(fixedIndicator, vp);
        indicatorViewPager.setAdapter(registerMemberAdapter);
        OnTransitionTextListener onTransitionTextListener = new OnTransitionTextListener(unSelectSize, selectSize, getResources().getColor(R.color.color_green_3bb4bc), getResources().getColor(R.color.color_black_0d0d0d)) {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                TextView textView = tabItemView.findViewById(R.id.tv_tab_text);
                textView.setText(TITLES[position]);
                return textView;
            }
        };
        indicatorViewPager.setIndicatorOnTransitionListener(onTransitionTextListener);
        indicatorViewPager.setPageOffscreenLimit(1);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_bar, ScrollBar.Gravity.BOTTOM));
    }


    class RegisterMemberAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public RegisterMemberAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(RegisterMemberActivity.this).inflate(R.layout.item_tab, container, false);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new RegisterUserFragment();
                    break;
             /*   case 1:
                    fragment = new RegisterCustomerFragment();
                    break;
                case 2:
                    fragment = new RegisterAgencyFragment();
                    break;*/
            }
            return fragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
