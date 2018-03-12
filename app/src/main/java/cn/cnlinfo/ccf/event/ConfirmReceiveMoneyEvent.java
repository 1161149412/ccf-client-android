package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class ConfirmReceiveMoneyEvent {
    private int errorCode;
    private String msg;
    public ConfirmReceiveMoneyEvent(){}
    public ConfirmReceiveMoneyEvent(int errorCode, String msg){
        this.errorCode =errorCode;
        this.msg = msg;
    }

    public ConfirmReceiveMoneyEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
    public int getErrorCode(){
        return errorCode;
    }
}
