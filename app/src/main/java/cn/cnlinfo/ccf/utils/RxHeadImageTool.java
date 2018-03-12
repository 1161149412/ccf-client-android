package cn.cnlinfo.ccf.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * android调用系统相机、相册功能，适配6.0权限获取以及7.0以后获取URI(兼容多版本)
 * http://blog.csdn.net/u011150924/article/details/71748464
 */

public class RxHeadImageTool {
    private static final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断

    private static final int CODE_TAKE_PHOTO = 1;//相机RequestCode

    public static  final int CODE_SELECT_IMAGE = 2;//相册RequestCode

    private static Uri getMediaFileUri(int type,Activity activity){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "相册名字");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }

    /**
     * 版本24以上
     */
    private static Uri get24MediaFileUri(int type,Activity activity) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "相册名字");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        /**
         * 第二个参数是在AndroidManifest中provider配置的authorities字段
         */
        return FileProvider.getUriForFile(activity, activity.getPackageName()+".file_provider", mediaFile);
    }
    /**
     * 调起系统相机
     */
    public static Uri tunedUpSysCamera(Activity activity){
        Uri photoUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoUri = get24MediaFileUri(TYPE_TAKE_PHOTO,activity);
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(takeIntent, CODE_TAKE_PHOTO);
        } else {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoUri = getMediaFileUri(TYPE_TAKE_PHOTO,activity);
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(takeIntent, CODE_TAKE_PHOTO);
        }
        return photoUri;
    }

    /**
     * 调起系统相册
     * @param activity
     */
    public static void tunedUpSysAlbum(Activity activity){
        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
    }
}
