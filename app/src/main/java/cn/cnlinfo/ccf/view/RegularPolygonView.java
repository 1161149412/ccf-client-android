package cn.cnlinfo.ccf.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import cn.cnlinfo.ccf.listener.LongPressClick;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

@SuppressLint("AppCompatCustomView")
public class RegularPolygonView extends TextView{
    private Paint mPaint;
    private float mR, mCx, mCy;
    private static final int mN = 5;
    private static final float DEGREES_UNIT = 360 / mN; //正N边形每个角  360/mN能整除
    private String flag = null;
    private LongPressClick longPressClick = null;
    private long pressDownTime = 0;
    private long upTime = 0;
    public RegularPolygonView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public RegularPolygonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        flag = attrs.getAttributeValue("http://cn.cnlinfo.ccf.view", "flag");
    }

    public RegularPolygonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        flag = attrs.getAttributeValue("http://cn.cnlinfo.ccf.view", "flag");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegularPolygonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCx = getMeasuredWidth() / 2;
        mCy = getMeasuredHeight() / 2;
        mR = Math.min(mCx, mCy) / 4 * 3;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5.0f);
        float d = (float) (2 * mR * Math.sin(Math.toRadians(DEGREES_UNIT / 2)));
        float c = mCy - mR;
        float y = (d * d + mCy * mCy - c * c - mR * mR) / (2 * (mCy - c));
        float x = (float) (mCx + Math.sqrt(-1 * c * c + 2 * c * y + d * d - y * y));
        Logger.d("("+mCx+","+c+")"+"\n"+"("+x+","+y+")");
        for (int i = 0; i < mN; i++) {
            canvas.save();
            canvas.rotate(DEGREES_UNIT * i, mCx, mCy);
            // canvas.drawLine(mCx, mCy, mCx, c, mPaint);
            //canvas.drawLine(mCx, mCy, x, y, mPaint);
            /*for (float j = (int) mCx, k=c;i<x&&j<y;){
                j=j+0.1f;
                k=k+0.1f;
                canvas.drawLine(mCx, mCy, j, k, mPaint);
            }*/
            canvas.drawLine(mCx, c, x, y, mPaint);
            if (flag.equals("center")) {
                canvas.drawLine(mCx, c, mCx, -y, mPaint);
            }
            canvas.restore();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                pressDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                if (x>this.getWidth()/4&&x<this.getWidth()*3/4&&y>this.getHeight()/4&&y<this.getHeight()*3/4){
                    if (upTime-pressDownTime>1000){
                        longPressClick.run(this);
                    }else {
                        callOnClick();
                    }
                }
                break;
        }
        return true;
    }

    public void setLongPressClick(LongPressClick longPressClick){
       this.longPressClick = longPressClick;
    }
}
