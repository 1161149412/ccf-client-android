package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class SendOrderIdEvent {
    private String orderId;

    public SendOrderIdEvent(String orderId) {
        this.orderId = orderId;
    }

    public SendOrderIdEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
