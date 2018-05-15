package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 单个商品实体<br>
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsBean {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("goodsHeadImgs")
    private String goodsHeadImgs;
    @Expose
    @SerializedName("goodsName")
    private String goodsName;
    /**
     * 300元(14斤/件),则piecePrice=300,num=14,unit=斤,buyUnit=件
     */
    @Expose
    @SerializedName("piecePrice")
    private String piecePrice;
    @Expose
    @SerializedName("num")
    private int num;
    @Expose
    @SerializedName("unit")
    private String unit;
    @Expose
    @SerializedName("buyUnit")
    private String buyUnit;
    /**
     * 3.5元/斤,则priceSale=3.5,unit=斤
     */
    @Expose
    @SerializedName("priceSale")
    private String priceSale;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsHeadImgs() {
        return goodsHeadImgs;
    }

    public void setGoodsHeadImgs(String goodsHeadImgs) {
        this.goodsHeadImgs = goodsHeadImgs;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPiecePrice() {
        return piecePrice;
    }

    public void setPiecePrice(String piecePrice) {
        this.piecePrice = piecePrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBuyUnit() {
        return buyUnit;
    }

    public void setBuyUnit(String buyUnit) {
        this.buyUnit = buyUnit;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }
}
