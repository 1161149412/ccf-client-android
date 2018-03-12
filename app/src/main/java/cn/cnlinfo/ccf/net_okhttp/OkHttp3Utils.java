package cn.cnlinfo.ccf.net_okhttp;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by JP on 2017/12/15 0015.
 *
 * 上传图片
 */

public class OkHttp3Utils {

    /**
     * 上传png后缀的图片
     */
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 上传多张图片及参数
     * @param reqUri 上传图片的url
     * @param map 上传的其他参数
     * @param req_key  上传图片的关键字
     * @param files
     */
    public static Request uploadMultiImage(String reqUri, Map<String,String> map,String req_key, List <File>files) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        if (map != null) {
            for (String key : map.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, map.get(key));
            }
        }
        if (files != null && files.size() > 0) {
            for (File file : files) {
                multipartBodyBuilder.addFormDataPart(req_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder builder = new Request.Builder();
        return builder.url(reqUri).post(requestBody).build();
    }

    /**
     * 下载文件
     * @param downloadUrl
     * @param callBack
     */
    public static void downloadFile(String downloadUrl,DownLoadFileCallBack callBack){
      OKHttpManager.getInstance().newCall(new Request.Builder().url(downloadUrl).get().build()).enqueue(callBack);
    }
}
