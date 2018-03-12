package cn.cnlinfo.ccf.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

import cn.cnlinfo.ccf.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/*引导页*/
@RuntimePermissions
public class GuideActivity extends BaseActivity {

    //装分页显示的view的数组
    private ArrayList<View> pageViews;
    //将小圆点的图片用数组表示
    private ImageView[] imageViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(this, "引导页");
        this.setStatusBarColor(R.color.color_transparent);
        GuideActivityPermissionsDispatcher.needsAlertWithCheck(this);
        GuideActivityPermissionsDispatcher.needsCameraWithCheck(this);
        //将要分页显示的View装入数组中
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<>();
        pageViews.add(inflater.inflate(R.layout.viewpager_guide_image_one, null));
        pageViews.add(inflater.inflate(R.layout.viewpager_guide_image_two, null));
        pageViews.add(inflater.inflate(R.layout.viewpager_guide_image_three, null));

        //创建imageviews数组，大小是要显示的图片的数量
        imageViews = new ImageView[pageViews.size()];
        //从指定的XML文件加载视图
        ViewGroup viewPics = (ViewGroup) inflater.inflate(R.layout.activity_guide, null);

        //实例化小圆点的linearLayout和viewpager
        ViewGroup viewPoints = (ViewGroup) viewPics.findViewById(R.id.viewGroup);
        ViewPager viewPager = (ViewPager) viewPics.findViewById(R.id.guidePages);

        //添加小圆点的图片
        for (int i = 0; i < pageViews.size(); i++) {
            ImageView imageView = new ImageView(GuideActivity.this);
            //设置小圆点imageview的参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(layoutParams);//创建一个宽高均为20 的布局
            imageView.setPadding(20, 0, 20, 0);
            //将小圆点layout添加到数组中
            imageViews[i] = imageView;

            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.guide_circle_white);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.guide_circle_gray);
            }

            //将imageviews添加到小圆点视图组
            viewPoints.addView(imageViews[i]);
        }

        //显示滑动图片的视图
        setContentView(viewPics);

        //设置viewpager的适配器和监听事件
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    @Override
    protected void onDestroy() {
        TCAgent.onPageEnd(this, "引导页");
        super.onDestroy();
    }

    private static final String SHARED_PREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

    private void setGuided() {
        SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_GUIDE_ACTIVITY, "false");
        editor.apply();
    }

    private class GuidePageAdapter extends PagerAdapter {

        //销毁position位置的界面
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            ((ViewPager) v).removeView(pageViews.get(position));
        }


        //获取当前窗体界面数
        @Override
        public int getCount() {
            return pageViews.size();
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(pageViews.get(position));
            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            return v == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }


    private class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.drawable.guide_circle_white);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.drawable.guide_circle_gray);
                }
            }

            if (position == (imageViews.length - 1)) {
                //设置已经引导
                setGuided();
                ImageButton button = (ImageButton) pageViews.get(position).findViewById(R.id.ibt_to_service);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideActivity.this,LoginRegisterActivity.class));
                        GuideActivity.this.finish();
                    }
                });
            }
        }
    }
    @NeedsPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
    void needsAlert() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GuideActivityPermissionsDispatcher.onActivityResult(this, requestCode);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsCamera() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GuideActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}