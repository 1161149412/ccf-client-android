package cn.cnlinfo.ccf.entity;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class ItemPriceListEntity {

    /**
     * Price : 0.128
     * AddTime : 2018-01-06
     */

    private double Price;
    private String AddTime;

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    @Override
    public String toString() {
        return "ItemPriceListEntity{" +
                "Price=" + Price +
                ", AddTime='" + AddTime + '\'' +
                '}';
    }
}
