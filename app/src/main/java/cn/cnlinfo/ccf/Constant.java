package cn.cnlinfo.ccf;

import cn.cnlinfo.ccf.manager.PhoneManager;

/**
 * Created by JP on 2017/10/11 0011.
 */

public class Constant {

    private static final String REQUEST_HOST = "http://www.ccfcc.cc/RegUser.asmx/";
    private static final String REQUEST_HOST_FOR_TEST = "http://www.ccfcc.cc/RegUser.asmx/";
    /**
     * 贡献图谱主机地址
     */
    public static final String CONTRIBUTIONMAP_HOST = "http://www.ccfcc.cc/ChildNetwork.asmx/";
    /**
     * 商城主机地址
     */
    public static final String CCMALL_HOST = "http://www.ccfcc.cc/mall/";
    /**
     * 操作ccf主机地址
     */
    public static final  String OPERATE_CCF_HOST = "http://www.ccfcc.cc/OperateCCF.asmx/";

    /**
     * 获取数据的主机地址
     */
    public static final String GET_DATA_HOST = "http://www.ccfcc.cc/GetDatas.asmx/";

    /**
     * 获取到公告具体信息的主机
     */
    public static final String GET_DETAIL_HOST = "http://www.ccfcc.cc/NewDetail.aspx?newsID=%s";
    /**
     * 发送验证码的主机地址
     */
    public static final String GET_MESSAGE_CODE_HOST = "http://www.ccfcc.cc/UserOperation.asmx/";
    /**
     * 上传步数的主机地址
     */
    public static final String UPLOAD_STEP_HOST = "http://www.ccfcc.cc/StepManage.asmx/";
    /**
     * 记录中心接口
     */
    public static final String RECORD_CENTER_HOST = "http://www.ccfcc.cc/RecordInterface.asmx/";
    /**
     * ccf价格列表主机地址
     */
    public static final String PRICE_LIST_HOST = "http://www.ccfcc.cc/Getdatas.asmx/";

    public static final String APP_DIR = PhoneManager.getSdCardRootPath() + "/ccf/";//app文件目录
    public static final String IMAGE_CACHE_DIR_PATH = APP_DIR + "cache/";// 图片缓存地址
    public static final String UPLOAD_FILES_DIR_PATH = APP_DIR + "upload/";//上传文件、零时文件存放地址
    public static final String DOWNLOAD_DIR_PATH = APP_DIR + "downloads/";// 下载文件存放地址

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String getHost() {
        if (isDebug()) {
            return REQUEST_HOST_FOR_TEST;
        } else {
            return REQUEST_HOST;
        }
    }

}
