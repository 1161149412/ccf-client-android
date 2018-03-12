package cn.cnlinfo.ccf.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by JP on 2017/11/21 0021.
 */

public class SecondaryNode implements Serializable {
    @JSONField(name = "location")
    private String location;
    @JSONField(name = "userID")
    private int userId;
    @JSONField(name = "uCode")
    private String uCode;

    public SecondaryNode() {
    }

    public SecondaryNode(String location, int userId, String uCode) {
        this.location = location;
        this.userId = userId;
        this.uCode = uCode;
    }

    @Override
    public String toString() {
        return "SecondaryNode{" +
                "location='" + location + '\'' +
                ", userId=" + userId +
                ", uCode='" + uCode + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }
}
