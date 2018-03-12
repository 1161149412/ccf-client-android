package cn.cnlinfo.ccf.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Jason on 2017/10/12 0012.
 */

public class StopScrollViewPager extends ViewPager {

    private boolean isCanScorll;

    public StopScrollViewPager(Context context) {
        super(context);
    }

    public StopScrollViewPager(Context context, AttributeSet attrs, boolean isCanScorll) {
        super(context, attrs);
        this.isCanScorll = isCanScorll;
    }

    public StopScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStopScroll(boolean isStop){
        this.isCanScorll = isStop;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScorll&super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScorll&super.onTouchEvent(ev);
    }


}
