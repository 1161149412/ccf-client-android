package cn.cnlinfo.ccf.event;

/**
 * Created by Administrator on 2018/2/10 0010.
 */

public class UpdateStepEvent {
    private int step;

    public UpdateStepEvent(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
