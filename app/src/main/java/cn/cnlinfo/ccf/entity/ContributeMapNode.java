package cn.cnlinfo.ccf.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20 0020.
 *
 */

public class ContributeMapNode implements Serializable{
    /**
     * "ID": 4,
     "UCode": "1001",
     "SumE": 0,
     "TotalMealWeight": 0,
     "MaxRegion": 0.56555398597317719,
     "SumDevote": 0.720639526515709
     */

    @JSONField(name = "ID")
    private int id;
    @JSONField(name = "UCode")
    private String uCode;
    @JSONField(name = "SumE")
    private int sumE;
    @JSONField(name = "TotalMealWeight")
    private int totalMealWeight;
    @JSONField(name = "MaxRegion")
    private double maxRegion;
    @JSONField(name = "SumDevote")
    private double sumDevote;


    public ContributeMapNode() {
    }

    public ContributeMapNode(int id, String uCode, int sumE, int totalMealWeight, double maxRegion, double sumDevote) {
        this.id = id;
        this.uCode = uCode;
        this.sumE = sumE;
        this.totalMealWeight = totalMealWeight;
        this.maxRegion = maxRegion;
        this.sumDevote = sumDevote;
    }

    @Override
    public String toString() {
        return "ContributeMapNode{" +
                "id=" + id +
                ", uCode='" + uCode + '\'' +
                ", sumE=" + sumE +
                ", totalMealWeight=" + totalMealWeight +
                ", maxRegion=" + maxRegion +
                ", sumDevote=" + sumDevote +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public int getSumE() {
        return sumE;
    }

    public void setSumE(int sumE) {
        this.sumE = sumE;
    }

    public int getTotalMealWeight() {
        return totalMealWeight;
    }

    public void setTotalMealWeight(int totalMealWeight) {
        this.totalMealWeight = totalMealWeight;
    }

    public double getMaxRegion() {
        return maxRegion;
    }

    public void setMaxRegion(double maxRegion) {
        this.maxRegion = maxRegion;
    }

    public double getSumDevote() {
        return sumDevote;
    }

    public void setSumDevote(double sumDevote) {
        this.sumDevote = sumDevote;
    }
}
