package cn.cnlinfo.ccf.fragment;

import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cnlinfo.ccf.R;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class CCUnionFragment extends BaseFragment {
    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_cc_union);
        TCAgent.onPageStart(getActivity(),"CC联盟");
        unbinder = ButterKnife.bind(this,getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
        TCAgent.onPageEnd(getActivity(),"CC联盟");
    }
}
