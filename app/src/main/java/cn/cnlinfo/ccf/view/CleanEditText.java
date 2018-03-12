package cn.cnlinfo.ccf.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lljjcoder.citylist.Toast.ToastUtils;

import cn.cnlinfo.ccf.R;
import cn.cnlinfo.ccf.utils.EditTextInputFormatUtil;

/**
 * Created by cuijing on 2017/3/7.
 */

public class CleanEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    //EditText是否聚焦
    private boolean hasFocus = true;
    private int tipMsg;
    private String type;

    public CleanEditText(Context context) {
        super(context);
        init();
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        tipMsg = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto","tipMessage",0);
        type = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","type");
        init();
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        tipMsg = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto","tipMessage",0);
        type = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","type");
        init();//这个方法主要是初始化工作，比如将清除按钮画上去
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,getCompoundDrawables()获取Drawable的四个位置的数组
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.icon_clean);
            //throw new NullPointerException("You can add drawableRight attribute in XML");
        }
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的带小
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 设置清除图标是否可见
     *
     * @param visible
     */
    private void setClearIconVisible(boolean visible) {
        Drawable right = (visible ? mClearDrawable : null);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //getTotalPaddingRight()图标左边缘至控件右边缘的距离
                //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
                //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);//获取焦点且长度大于0时，清除图标显示
        } else {
            setClearIconVisible(false);
            //失去焦点验证输入内容是否合法
            if (getText().length()>0){
                if (!TextUtils.isEmpty(type)){
                    if (type.equals("phone")){
                        if(!EditTextInputFormatUtil.isLegalPhoneNum(getText().toString())){
                            ToastUtils.showShortToast(getContext(),getResources().getString(tipMsg));
                            return;
                        }
                    }else if (type.equals("id")){
                        if (!EditTextInputFormatUtil.isLegalId(getText().toString())){
                            ToastUtils.showShortToast(getContext(),getResources().getString(tipMsg));
                            return;
                        }
                    }else if (type.equals("bank_num")){
                        if (!EditTextInputFormatUtil.isBankCard(getText().toString())){
                            ToastUtils.showShortToast(getContext(),getResources().getString(tipMsg));
                            return;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);//长度改变时，依条件判断是否显示图标
        }
    }
}
