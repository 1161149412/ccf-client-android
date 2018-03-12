package cn.cnlinfo.ccf.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class ItemMealEntity {
    /**
     *  "ID": 1,
     "Name": "起航套餐A",
     "Type": 1,//1是起航套餐2是普通套餐
     //套数指标
     "MealWeight": 1
     "RegisterIntegral": 500
     */
    @JSONField(name = "ID")
    private int id;
    @JSONField(name = "Name")
    private String mealName;
    @JSONField(name = "Type")
    private int type;
    @JSONField(name = "MealWeight")
    private int mealWeight;
    @JSONField(name = "RegisterIntegral")
    private int registerIntegral;

    public ItemMealEntity() {
    }

    public ItemMealEntity(int id, String mealName, int type, int mealWeight) {
        this.id = id;
        this.mealName = mealName;
        this.type = type;
        this.mealWeight = mealWeight;
    }

    @Override
    public String toString() {
        return "ItemMealEntity{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", type=" + type +
                ", mealWeight=" + mealWeight +
                ", registerIntegral=" + registerIntegral +
                '}';
    }

    public int getRegisterIntegral() {
        return registerIntegral;
    }

    public void setRegisterIntegral(int registerIntegral) {
        this.registerIntegral = registerIntegral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMealWeight() {
        return mealWeight;
    }

    public void setMealWeight(int mealWeight) {
        this.mealWeight = mealWeight;
    }
}
