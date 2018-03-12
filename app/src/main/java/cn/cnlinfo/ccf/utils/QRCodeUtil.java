package cn.cnlinfo.ccf.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class QRCodeUtil {
    /**
     * 调起扫一扫功能
     */
    public static void toRichScan(Activity context,int code) {
  /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
        * 也可以不传这个参数
        * 不传的话  默认都为默认不震动  其他都为true
        * */
        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        config.setPlayBeep(true);//是否播放提示音
        config.setShake(true);//是否震动
        config.setShowAlbum(true);//是否显示相册
        config.setShowFlashLight(true);//是否显示闪光灯
        //如果不传 ZxingConfig的话，两行代码就能搞定了
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        context.startActivityForResult(intent, code);
    }

    /**
     * 生成二维码图片
     *
     * @return
     */
    public static Bitmap buildQRCode(String msg, int width, int height,Bitmap headImage) {
        Bitmap bitmap = null;
        try {
                    /*
                    * contentEtString：字符串内容
                    * w：图片的宽
                    * h：图片的高
                    * logo：不需要logo的话直接传null
                    * */
            //Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            bitmap = CodeCreator.createQRCode(msg, width, height, headImage);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
