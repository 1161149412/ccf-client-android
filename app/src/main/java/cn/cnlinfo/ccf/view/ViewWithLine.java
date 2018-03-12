package cn.cnlinfo.ccf.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.manager.PhoneManager;

/**
 * Created by JP on 2017/11/14 0014.
 */

/**
 * 每个方格都是64*32
 */
@SuppressLint("AppCompatCustomView")
public class ViewWithLine extends View {
    public String rectF_1_1_Text = "";
    public String rectF_2_1_Text = "";
    public String rectF_2_2_Text = "";
    public String rectF_2_3_Text = "";
    public String rectF_2_4_Text = "";
    public String rectF_2_5_Text = "";
    public String rectF_3_1_Text = "";
    public String rectF_3_2_Text = "";
    public String rectF_3_3_Text = "";
    public String rectF_3_4_Text = "";
    public String rectF_3_5_Text = "";
    public String rectF_3_6_Text = "";
    public String rectF_3_7_Text = "";
    public String rectF_3_8_Text = "";
    public String rectF_3_9_Text = "";
    public String rectF_3_10_Text = "";
    public String rectF_3_11_Text = "";
    public String rectF_3_12_Text = "";
    public String rectF_3_13_Text = "";
    public String rectF_3_14_Text = "";
    public String rectF_3_15_Text = "";
    public String rectF_3_16_Text = "";
    public String rectF_3_17_Text = "";
    public String rectF_3_18_Text = "";
    public String rectF_3_19_Text = "";
    public String rectF_3_20_Text = "";
    public String rectF_3_21_Text = "";
    public String rectF_3_22_Text = "";
    public String rectF_3_23_Text = "";
    public String rectF_3_24_Text = "";
    public String rectF_3_25_Text = "";


    private Paint mPaint = null;
    private Paint gPaint = null;
    //画线的画笔
    private Paint bPaint = null;
    //画文字的画笔
    private Paint tPaint = null;
    //控件的宽高
    private int w = 0;
    private int h = 0;
    //第一列第一格
    private RectF rectF_1_1 = null;
    //第二列
    private RectF rectF_2_1 = null;
    private RectF rectF_2_2 = null;
    private RectF rectF_2_3 = null;
    private RectF rectF_2_4 = null;
    private RectF rectF_2_5 = null;
    //第三列
    private RectF rectF_3_1 = null;
    private RectF rectF_3_2 = null;
    private RectF rectF_3_3 = null;
    private RectF rectF_3_4 = null;
    private RectF rectF_3_5 = null;
    private RectF rectF_3_6 = null;
    private RectF rectF_3_7 = null;
    private RectF rectF_3_8 = null;
    private RectF rectF_3_9 = null;
    private RectF rectF_3_10 = null;
    private RectF rectF_3_11 = null;
    private RectF rectF_3_12 = null;
    private RectF rectF_3_13 = null;
    private RectF rectF_3_14 = null;
    private RectF rectF_3_15 = null;
    private RectF rectF_3_16 = null;
    private RectF rectF_3_17 = null;
    private RectF rectF_3_18 = null;
    private RectF rectF_3_19 = null;
    private RectF rectF_3_20 = null;
    private RectF rectF_3_21 = null;
    private RectF rectF_3_22 = null;
    private RectF rectF_3_23 = null;
    private RectF rectF_3_24 = null;
    private RectF rectF_3_25 = null;

    //第一列左上点的x
    private float rectF_1_1_left = 0;
    //第一列左上点的y
    private float rectF_1_1_top = 0;
    //第一列右上点的x
    private float rectF_1_1_right = 0;
    //第一列右上点的y
    private float rectF_1_1_bottom = 0;

    //第二列左边的的x
    private float column_2_left_x = 0;
    //第二列第一格左边的y
    private float column_2_1_left_y = 0;
    //第二列第二格左边的y
    private float column_2_2_left_y = 0;
    //第二列第三格左边的y
    private float column_2_3_left_y = 0;
    //第二列第四格左边的y
    private float column_2_4_left_y = 0;
    //第二列第五格左边的y
    private float column_2_5_left_y = 0;

    //第二列右边的的x
    private float column_2_right_x = 0;
    //第二列第一格右边的y
    private float column_2_1_right_y = 0;
    //第二列第二格右边的y
    private float column_2_2_right_y = 0;
    //第二列第三格右边的y
    private float column_2_3_right_y = 0;
    //第二列第四格右边的y
    private float column_2_4_right_y = 0;
    //第二列第五格右边的y
    private float column_2_5_right_y = 0;

    //第三列左边的的x
    private float column_3_left_x = 0;
    //第三列第一格左边的y
    private float column_3_1_left_y = 0;
    //第三列第二格左边的y
    private float column_3_2_left_y = 0;
    //第三列第三格左边的y
    private float column_3_3_left_y = 0;
    //第三列第四格左边的y
    private float column_3_4_left_y = 0;
    //第三列第五格左边的y
    private float column_3_5_left_y = 0;
    //第三列第六格左边的y
    private float column_3_6_left_y = 0;
    //第三列第七格左边的y
    private float column_3_7_left_y = 0;
    //第三列第八格左边的y
    private float column_3_8_left_y = 0;
    //第三列第九格左边的y
    private float column_3_9_left_y = 0;
    //第三列第十格左边的y
    private float column_3_10_left_y = 0;
    //第三列第十一格左边的y
    private float column_3_11_left_y = 0;
    //第三列第十二格左边的y
    private float column_3_12_left_y = 0;
    //第三列第十三格左边的y
    private float column_3_13_left_y = 0;
    //第三列第十四格左边的y
    private float column_3_14_left_y = 0;
    //第三列第十五格左边的y
    private float column_3_15_left_y = 0;
    //第三列第十六格左边的y
    private float column_3_16_left_y = 0;
    //第三列第十七格左边的y
    private float column_3_17_left_y = 0;
    //第三列第十八格左边的y
    private float column_3_18_left_y = 0;
    //第三列第十九格左边的y
    private float column_3_19_left_y = 0;
    //第三列第二十格左边的y
    private float column_3_20_left_y = 0;
    //第三列第二十一格左边的y
    private float column_3_21_left_y = 0;
    //第三列第二十二格左边的y
    private float column_3_22_left_y = 0;
    //第三列第二十三格左边的y
    private float column_3_23_left_y = 0;
    //第三列第二十四格左边的y
    private float column_3_24_left_y = 0;
    //第三列第二十五格左边的y
    private float column_3_25_left_y = 0;


    //第三列右边的的x
    private float column_3_right_x = 0;
    //第三列第一格右边的y
    private float column_3_1_right_y = 0;
    //第三列第二格右边的y
    private float column_3_2_right_y = 0;
    //第三列第三格右边的y
    private float column_3_3_right_y = 0;
    //第三列第四格右边的y
    private float column_3_4_right_y = 0;
    //第三列第五格右边的y
    private float column_3_5_right_y = 0;
    //第三列第六格右边的y
    private float column_3_6_right_y = 0;
    //第三列第七格右边的y
    private float column_3_7_right_y = 0;
    //第三列第八格右边的y
    private float column_3_8_right_y = 0;
    //第三列第九格右边的y
    private float column_3_9_right_y = 0;
    //第三列第十格右边的y
    private float column_3_10_right_y = 0;
    //第三列第十一格右边的y
    private float column_3_11_right_y = 0;
    //第三列第十二格右边的y
    private float column_3_12_right_y = 0;
    //第三列第十三格右边的y
    private float column_3_13_right_y = 0;
    //第三列第十四格右边的y
    private float column_3_14_right_y = 0;
    //第三列第十五格右边的y
    private float column_3_15_right_y = 0;
    //第三列第十六格右边的y
    private float column_3_16_right_y = 0;
    //第三列第十七格右边的y
    private float column_3_17_right_y = 0;
    //第三列第十八格右边的y
    private float column_3_18_right_y = 0;
    //第三列第十九格右边的y
    private float column_3_19_right_y = 0;
    //第三列第二十格右边的y
    private float column_3_20_right_y = 0;
    //第三列第二水一格右边的y
    private float column_3_21_right_y = 0;
    //第三列第二十二格右边的y
    private float column_3_22_right_y = 0;
    //第三列第二十三格右边的y
    private float column_3_23_right_y = 0;
    //第三列第二十四格右边的y
    private float column_3_24_right_y = 0;
    //第三列第二十五格右边的y
    private float column_3_25_right_y = 0;

    /**
     * 方格的宽高
     */
    private float grid_w = 64;
    private float grid_h = 32;
    private float grid_half_h = PhoneManager.dip2px(grid_h/2);
    //列之间的宽度
    private float column_w = 64;
    //第一列距离左边的距离
    private float margin_left = 18;
    //第二列格子之间的宽度
    private float column_2_d = 196;
    //第三列格子之间的宽度
    private float column_3_d = 12;

    public ViewWithLine(Context context) {
        super(context);
        setPaintStyle();

    }

    public void setPaintStyle() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.color_blue_4d8cd6));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.0f);
        bPaint = new Paint();
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setColor(getResources().getColor(R.color.color_blue_0b2841));
        bPaint.setAntiAlias(true);
        bPaint.setStrokeWidth(5.0f);
        gPaint = new Paint();
        gPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        gPaint.setColor(getResources().getColor(R.color.color_gray_b7b4b4));
        gPaint.setAntiAlias(true);
        gPaint.setStrokeWidth(2.0f);
        /**
         * Paint.ANTI_ALIAS_FLAG反锯齿的，就相当于setAntiAlias(true)
         */
        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setStrokeWidth(1.0f);
        tPaint.setTextSize(PhoneManager.px2sp(64));
        tPaint.setColor(Color.WHITE);
        tPaint.setTextAlign(Paint.Align.CENTER);
    }

    public ViewWithLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPaintStyle();
    }

    public ViewWithLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaintStyle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewWithLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setPaintStyle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        setRectF_column_1();
        setRectF_column_2();
        setRectF_column_3();

    }

    //设置第三列的坐标参数
    private void setRectF_column_3() {
        column_3_left_x = column_2_left_x+ PhoneManager.dip2px(grid_w+column_w);
        column_3_1_left_y = column_2_1_left_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_right_x = column_2_right_x+PhoneManager.dip2px(grid_w+column_w);
        column_3_1_right_y = column_2_1_right_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_1 = new RectF(column_3_left_x,column_3_1_left_y,column_3_right_x,column_3_1_right_y);

        column_3_2_left_y = column_2_1_left_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_2_right_y = column_2_1_right_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_2 = new RectF(column_3_left_x,column_3_2_left_y,column_3_right_x,column_3_2_right_y);

        column_3_3_left_y = column_2_1_left_y;
        column_3_3_right_y = column_2_1_right_y;
        rectF_3_3 = new RectF(column_3_left_x,column_3_3_left_y,column_3_right_x,column_3_3_right_y);

        column_3_4_left_y = column_2_1_left_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_4_right_y = column_2_1_right_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_4 = new RectF(column_3_left_x,column_3_4_left_y,column_3_right_x,column_3_4_right_y);

        column_3_5_left_y = column_2_1_left_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_5_right_y = column_2_1_right_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_5 = new RectF(column_3_left_x,column_3_5_left_y,column_3_right_x,column_3_5_right_y);

        column_3_6_left_y = column_2_2_left_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_6_right_y = column_2_2_right_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_6 = new RectF(column_3_left_x,column_3_6_left_y,column_3_right_x,column_3_6_right_y);

        column_3_7_left_y = column_2_2_left_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_7_right_y = column_2_2_right_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_7 = new RectF(column_3_left_x,column_3_7_left_y,column_3_right_x,column_3_7_right_y);

        column_3_8_left_y = column_2_2_left_y;
        column_3_8_right_y = column_2_2_right_y;
        rectF_3_8 = new RectF(column_3_left_x,column_3_8_left_y,column_3_right_x,column_3_8_right_y);

        column_3_9_left_y = column_2_2_left_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_9_right_y = column_2_2_right_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_9 = new RectF(column_3_left_x,column_3_9_left_y,column_3_right_x,column_3_9_right_y);

        column_3_10_left_y = column_2_2_left_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_10_right_y = column_2_2_right_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_10 = new RectF(column_3_left_x,column_3_10_left_y,column_3_right_x,column_3_10_right_y);

        column_3_11_left_y = column_2_3_left_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_11_right_y = column_2_3_right_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_11 = new RectF(column_3_left_x,column_3_11_left_y,column_3_right_x,column_3_11_right_y);

        column_3_12_left_y = column_2_3_left_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_12_right_y = column_2_3_right_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_12 = new RectF(column_3_left_x,column_3_12_left_y,column_3_right_x,column_3_12_right_y);

        column_3_13_left_y = column_2_3_left_y;
        column_3_13_right_y = column_2_3_right_y;
        rectF_3_13 = new RectF(column_3_left_x,column_3_13_left_y,column_3_right_x,column_3_13_right_y);

        column_3_14_left_y = column_2_3_left_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_14_right_y = column_2_3_right_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_14 = new RectF(column_3_left_x,column_3_14_left_y,column_3_right_x,column_3_14_right_y);

        column_3_15_left_y = column_2_3_left_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_15_right_y = column_2_3_right_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_15 = new RectF(column_3_left_x,column_3_15_left_y,column_3_right_x,column_3_15_right_y);

        column_3_16_left_y = column_2_4_left_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_16_right_y = column_2_4_right_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_16 = new RectF(column_3_left_x,column_3_16_left_y,column_3_right_x,column_3_16_right_y);

        column_3_17_left_y = column_2_4_left_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_17_right_y = column_2_4_right_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_17 = new RectF(column_3_left_x,column_3_17_left_y,column_3_right_x,column_3_17_right_y);

        column_3_18_left_y = column_2_4_left_y;
        column_3_18_right_y = column_2_4_right_y;
        rectF_3_18 = new RectF(column_3_left_x,column_3_18_left_y,column_3_right_x,column_3_18_right_y);

        column_3_19_left_y = column_2_4_left_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_19_right_y = column_2_4_right_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_19 = new RectF(column_3_left_x,column_3_19_left_y,column_3_right_x,column_3_19_right_y);

        column_3_20_left_y = column_2_4_left_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_20_right_y = column_2_4_right_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_20 = new RectF(column_3_left_x,column_3_20_left_y,column_3_right_x,column_3_20_right_y);

        column_3_21_left_y = column_2_5_left_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_21_right_y = column_2_5_right_y-PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_21 = new RectF(column_3_left_x,column_3_21_left_y,column_3_right_x,column_3_21_right_y);

        column_3_22_left_y = column_2_5_left_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_22_right_y = column_2_5_right_y-PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_22 = new RectF(column_3_left_x,column_3_22_left_y,column_3_right_x,column_3_22_right_y);

        column_3_23_left_y = column_2_5_left_y;
        column_3_23_right_y = column_2_5_right_y;
        rectF_3_23 = new RectF(column_3_left_x,column_3_23_left_y,column_3_right_x,column_3_23_right_y);

        column_3_24_left_y = column_2_5_left_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        column_3_24_right_y = column_2_5_right_y+PhoneManager.dip2px(1*(grid_h+column_3_d));
        rectF_3_24 = new RectF(column_3_left_x,column_3_24_left_y,column_3_right_x,column_3_24_right_y);

        column_3_25_left_y = column_2_5_left_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        column_3_25_right_y = column_2_5_right_y+PhoneManager.dip2px(2*(grid_h+column_3_d));
        rectF_3_25 = new RectF(column_3_left_x,column_3_25_left_y,column_3_right_x,column_3_25_right_y);
    }

    //设置第二列的坐标参数
    private void setRectF_column_2() {
        column_2_left_x = PhoneManager.dip2px(margin_left+grid_w+column_w);
        column_2_1_left_y =h/2+PhoneManager.dip2px((-column_2_d-grid_h)*2);
        column_2_2_left_y = h/2+PhoneManager.dip2px((-column_2_d-grid_h)*1);
        column_2_3_left_y = h/2;
        column_2_4_left_y = h/2+PhoneManager.dip2px((column_2_d+grid_h)*1);
        column_2_5_left_y = h/2+PhoneManager.dip2px((column_2_d+grid_h)*2);
        column_2_right_x = PhoneManager.dip2px(margin_left+grid_w+column_w+grid_w);
        column_2_1_right_y = h/2+PhoneManager.dip2px(grid_h+(-column_2_d-grid_h)*2);
        column_2_2_right_y = h/2+PhoneManager.dip2px(grid_h+(-column_2_d-grid_h)*1);
        column_2_3_right_y = h/2+PhoneManager.dip2px(grid_h);
        column_2_4_right_y = h/2+PhoneManager.dip2px(grid_h+(column_2_d+grid_h)*1);
        column_2_5_right_y = h/2+PhoneManager.dip2px(grid_h+(column_2_d+grid_h)*2);

        rectF_2_1 = new RectF(column_2_left_x,column_2_1_left_y,column_2_right_x,column_2_1_right_y);
        rectF_2_2 = new RectF(column_2_left_x,column_2_2_left_y,column_2_right_x,column_2_2_right_y);
        rectF_2_3 = new RectF(column_2_left_x,column_2_3_left_y,column_2_right_x,column_2_3_right_y);
        rectF_2_4 = new RectF(column_2_left_x,column_2_4_left_y,column_2_right_x,column_2_4_right_y);
        rectF_2_5 = new RectF(column_2_left_x,column_2_5_left_y,column_2_right_x,column_2_5_right_y);


    }

    //设置第一列的相关信息
    private void setRectF_column_1() {
        rectF_1_1_left = PhoneManager.dip2px(margin_left);
        rectF_1_1_top = h/2;
        rectF_1_1_right = PhoneManager.dip2px(margin_left+grid_w);
        rectF_1_1_bottom = rectF_1_1_top +PhoneManager.dip2px(grid_h);
        rectF_1_1 = new RectF(rectF_1_1_left,h/2, rectF_1_1_right, rectF_1_1_bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectF_1_1,mPaint);
        setTextInfo(canvas,rectF_1_1,rectF_1_1_Text);

        canvas.drawLine(rectF_1_1_right,h/2+grid_half_h,column_2_left_x,column_2_1_left_y+grid_half_h, bPaint);
        canvas.drawLine(rectF_1_1_right,h/2+grid_half_h,column_2_left_x,column_2_2_left_y+grid_half_h, bPaint);
        canvas.drawLine(rectF_1_1_right, h/2+grid_half_h, rectF_1_1_right +PhoneManager.dip2px(column_w),h/2+grid_half_h, bPaint);
        canvas.drawLine(rectF_1_1_right,h/2+grid_half_h,column_2_left_x,column_2_4_left_y+grid_half_h, bPaint);
        canvas.drawLine(rectF_1_1_right,h/2+grid_half_h,column_2_left_x,column_2_5_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_2_1,mPaint);
        setTextInfo(canvas,rectF_2_1,rectF_2_1_Text);
        canvas.drawRect(rectF_2_2,mPaint);
        setTextInfo(canvas,rectF_2_2,rectF_2_2_Text);
        canvas.drawRect(rectF_2_3,mPaint);
        setTextInfo(canvas,rectF_2_3,rectF_2_3_Text);
        canvas.drawRect(rectF_2_4,mPaint);
        setTextInfo(canvas,rectF_2_4,rectF_2_4_Text);
        canvas.drawRect(rectF_2_5,mPaint);
        setTextInfo(canvas,rectF_2_5,rectF_2_5_Text);

        canvas.drawLine(column_2_right_x,column_2_1_right_y-grid_half_h,column_3_left_x,column_3_1_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_1_right_y-grid_half_h,column_3_left_x,column_3_2_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_1_right_y-grid_half_h,column_3_left_x,column_3_3_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_1_right_y-grid_half_h,column_3_left_x,column_3_4_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_1_right_y-grid_half_h,column_3_left_x,column_3_5_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_3_1,mPaint);
        setTextInfo(canvas,rectF_3_1,rectF_3_1_Text);
        canvas.drawRect(rectF_3_2,mPaint);
        setTextInfo(canvas,rectF_3_2,rectF_3_2_Text);
        canvas.drawRect(rectF_3_3,mPaint);
        setTextInfo(canvas,rectF_3_3,rectF_3_3_Text);
        canvas.drawRect(rectF_3_4,mPaint);
        setTextInfo(canvas,rectF_3_4,rectF_3_4_Text);
        canvas.drawRect(rectF_3_5,mPaint);
        setTextInfo(canvas,rectF_3_5,rectF_3_5_Text);

        canvas.drawLine(column_2_right_x,column_2_2_right_y-grid_half_h,column_3_left_x,column_3_6_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_2_right_y-grid_half_h,column_3_left_x,column_3_7_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_2_right_y-grid_half_h,column_3_left_x,column_3_8_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_2_right_y-grid_half_h,column_3_left_x,column_3_9_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_2_right_y-grid_half_h,column_3_left_x,column_3_10_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_3_6,mPaint);
        setTextInfo(canvas,rectF_3_6,rectF_3_6_Text);
        canvas.drawRect(rectF_3_7,mPaint);
        setTextInfo(canvas,rectF_3_7,rectF_3_7_Text);
        canvas.drawRect(rectF_3_8,mPaint);
        setTextInfo(canvas,rectF_3_8,rectF_3_8_Text);
        canvas.drawRect(rectF_3_9,mPaint);
        setTextInfo(canvas,rectF_3_9,rectF_3_9_Text);
        canvas.drawRect(rectF_3_10,mPaint);
        setTextInfo(canvas,rectF_3_10,rectF_3_10_Text);

        canvas.drawLine(column_2_right_x,column_2_3_right_y-grid_half_h,column_3_left_x,column_3_11_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_3_right_y-grid_half_h,column_3_left_x,column_3_12_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_3_right_y-grid_half_h,column_3_left_x,column_3_13_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_3_right_y-grid_half_h,column_3_left_x,column_3_14_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_3_right_y-grid_half_h,column_3_left_x,column_3_15_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_3_11,mPaint);
        setTextInfo(canvas,rectF_3_11,rectF_3_11_Text);
        canvas.drawRect(rectF_3_12,mPaint);
        setTextInfo(canvas,rectF_3_12,rectF_3_12_Text);
        canvas.drawRect(rectF_3_13,mPaint);
        setTextInfo(canvas,rectF_3_13,rectF_3_13_Text);
        canvas.drawRect(rectF_3_14,mPaint);
        setTextInfo(canvas,rectF_3_14,rectF_3_14_Text);
        canvas.drawRect(rectF_3_15,mPaint);
        setTextInfo(canvas,rectF_3_15,rectF_3_15_Text);

        canvas.drawLine(column_2_right_x,column_2_4_right_y-grid_half_h,column_3_left_x,column_3_16_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_4_right_y-grid_half_h,column_3_left_x,column_3_17_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_4_right_y-grid_half_h,column_3_left_x,column_3_18_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_4_right_y-grid_half_h,column_3_left_x,column_3_19_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_4_right_y-grid_half_h,column_3_left_x,column_3_20_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_3_16,mPaint);
        setTextInfo(canvas,rectF_3_16,rectF_3_16_Text);
        canvas.drawRect(rectF_3_17,mPaint);
        setTextInfo(canvas,rectF_3_17,rectF_3_17_Text);
        canvas.drawRect(rectF_3_18,mPaint);
        setTextInfo(canvas,rectF_3_18,rectF_3_18_Text);
        canvas.drawRect(rectF_3_19,mPaint);
        setTextInfo(canvas,rectF_3_19,rectF_3_19_Text);
        canvas.drawRect(rectF_3_20,mPaint);
        setTextInfo(canvas,rectF_3_20,rectF_3_20_Text);

        canvas.drawLine(column_2_right_x,column_2_5_right_y-grid_half_h,column_3_left_x,column_3_21_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_5_right_y-grid_half_h,column_3_left_x,column_3_22_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_5_right_y-grid_half_h,column_3_left_x,column_3_23_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_5_right_y-grid_half_h,column_3_left_x,column_3_24_left_y+grid_half_h, bPaint);
        canvas.drawLine(column_2_right_x,column_2_5_right_y-grid_half_h,column_3_left_x,column_3_25_left_y+grid_half_h, bPaint);

        canvas.drawRect(rectF_3_21,mPaint);
        setTextInfo(canvas,rectF_3_21,rectF_3_21_Text);
        canvas.drawRect(rectF_3_22,mPaint);
        setTextInfo(canvas,rectF_3_22,rectF_3_22_Text);
        canvas.drawRect(rectF_3_23,mPaint);
        setTextInfo(canvas,rectF_3_23,rectF_3_23_Text);
        canvas.drawRect(rectF_3_24,mPaint);
        setTextInfo(canvas,rectF_3_24,rectF_3_24_Text);
        canvas.drawRect(rectF_3_25,mPaint);
        setTextInfo(canvas,rectF_3_25,rectF_3_25_Text);


    }

    private void setTextInfo(Canvas canvas,RectF rectF,String str) {
        Paint.FontMetricsInt fontMetrics = tPaint.getFontMetricsInt();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(str, rectF.centerX(), baseline, tPaint);
    }
}
