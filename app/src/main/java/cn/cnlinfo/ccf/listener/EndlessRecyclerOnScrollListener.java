package cn.cnlinfo.ccf.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

/**
 * Created by JP on 2017/11/7 0007.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingToTop = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 获取最后一个完全显示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();
            Logger.d(lastItemPosition+":"+itemCount);
            // 判断是否滑动到了最后一个Item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingToTop) {
                // 加载更多
                onLoadMore();
            }
        }
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // dx值大于0表示正在向左滑动，小于或等于0表示向右滑动或停止
        // dy值大于0表示正在向下滑动，小于或等于0表示向上滑动或停止
        Logger.d(dx+":"+dy);
        isSlidingToTop = dy <= 0;
    }


    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}
