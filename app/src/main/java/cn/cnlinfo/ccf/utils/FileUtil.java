package cn.cnlinfo.ccf.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.cnlinfo.ccf.Constant;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class FileUtil {

    //保存凭证文件
    public static boolean saveFile(Context context, String fileName, Bitmap bitmap){
        File file = new File(Constant.IMAGE_CACHE_DIR_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath()+"/"+fileName);
            //将bitmap转化成字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] datas = baos.toByteArray();

            fileOutputStream.write(datas);
            fileOutputStream.flush();
            baos.close();
            fileOutputStream.close();
            Toast.makeText(context,"图片已成功保存到"+file.getAbsolutePath()+"/"+fileName,Toast.LENGTH_LONG).show();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"图片保存失败",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"图片保存失败",Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
