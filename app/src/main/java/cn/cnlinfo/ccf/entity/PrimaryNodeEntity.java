package cn.cnlinfo.ccf.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class PrimaryNodeEntity implements Serializable {
    @JSONField(name = "location")
    private String location;
    @JSONField(name = "userID")
    private int userId;
    @JSONField(name = "uCode")
    private String uCode;
    @JSONField(name = "childs")
    private List<SecondaryNode> secondaryNodeList;

    public PrimaryNodeEntity() {
    }

    public PrimaryNodeEntity(String location, int userId, String uCode, List<SecondaryNode> secondaryNodeList) {
        this.location = location;
        this.userId = userId;
        this.uCode = uCode;
        this.secondaryNodeList = secondaryNodeList;
    }

    @Override
    public String toString() {
        return "PrimaryNodeEntity{" +
                "location='" + location + '\'' +
                ", userId=" + userId +
                ", uCode='" + uCode + '\'' +
                ", secondaryNodeList=" + secondaryNodeList +
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

    public List<SecondaryNode> getSecondaryNodeList() {
        return secondaryNodeList;
    }

    public void setSecondaryNodeList(List<SecondaryNode> secondaryNodeList) {
        this.secondaryNodeList = secondaryNodeList;
    }
}
