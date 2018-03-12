package cn.cnlinfo.ccf.step_count.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by JP on 17/11/2
 */

@Table("step_data")
public class StepData {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @Column("username")
    @NotNull
    private String username;

    @Column("today")
    @NotNull
    private String date;

    @Column("stepNum")
    @NotNull
    private int stepNum;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    @Override
    public String toString() {
        return "StepData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", stepNum=" + stepNum +
                '}';
    }
}
