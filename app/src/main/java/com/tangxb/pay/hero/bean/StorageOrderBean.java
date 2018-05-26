package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tangxuebing on 2018/5/24.
 */

public class StorageOrderBean {
    @Expose
    @SerializedName("product_id")
    private long productId;
    @Expose
    @SerializedName("product_name")
    private String productName;
    @Expose
    @SerializedName("product_image")
    private String productImage;
    @Expose
    @SerializedName("product_unit")
    private String productUnit;
    @Expose
    @SerializedName("buy_num")
    private int buyNum;
    @Expose
    @SerializedName("num")
    private int num;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
}
