package cn.cnlinfo.ccf.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cnlinfo.ccf.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by jp on 2017/10/12.
 * <p/>
 * 刷新动画
 */
public class RefreshHeaderView extends FrameLayout implements PtrUIHandler {
    //    public class LoadMoreFooterView extends FrameLayout implements PtrUIHandler {
    private LayoutInflater inflater;

    // 下拉刷新视图（头部视图）
    private ViewGroup headView;

    // 下拉刷新文字
    private TextView tvHeadTitle;

    // 下拉图标
    private ImageView ivWindmill;

    private PtrFrameLayout mPtrFrameLayout;


    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {
        mPtrFrameLayout = layout;
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        headView = (ViewGroup) inflater.inflate(R.layout.refresh_animation_header, this, true);
        ivWindmill = (ImageView) headView.findViewById(R.id.iv_windmill);
        ivWindmill.setVisibility(VISIBLE);
        ivWindmill.setBackground(this.getResources().getDrawable(R.drawable.animation_refresh));
        AnimationDrawable animationDrawable = (AnimationDrawable) ivWindmill.getBackground();
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    public void onUIReset(PtrFrameLayout ptrFrameLayout) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            }
        }

    }
}
