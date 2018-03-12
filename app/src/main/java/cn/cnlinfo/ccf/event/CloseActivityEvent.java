package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class CloseActivityEvent {
    private int code;

    public CloseActivityEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
