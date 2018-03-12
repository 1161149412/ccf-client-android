package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class ReceiveComplainEvent {
    private int errorCode;
    private String msg;
    public ReceiveComplainEvent(){}
    public ReceiveComplainEvent(int errorCode, String msg){
        this.errorCode =errorCode;
        this.msg = msg;
    }

    public ReceiveComplainEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
    public int getErrorCode(){
        return errorCode;
    }
}
