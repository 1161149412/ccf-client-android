package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class CancelHangBuyAndSellEvent {
    private int code ;
    private String msg;

    public CancelHangBuyAndSellEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CancelHangBuyAndSellEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
