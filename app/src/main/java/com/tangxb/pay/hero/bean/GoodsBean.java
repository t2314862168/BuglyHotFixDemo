package com.tangxb.pay.hero.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 单个商品实体<br>
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsBean implements Parcelable {

    /**
     * id : 1
     * name : 百草味话梅西瓜子200g_袋
     * price : 100.0
     * subtitle : 百草味 坚果炒货西瓜子 颗粒饱满休闲零食小吃 话梅西瓜子20袋/箱
     * main_image : https://lichuanshipin.oss-cn-hangzhou.aliyuncs.com/product/ganguo/bcwgz/1.jpg
     * unit : null
     * stock : 0
     * status : 1、在售 0、已下架、-1、删除
     * category_id : 0
     * freight : 0.3
     * weight : 20.0
     * create_time : 1526644421000
     * update_time : null
     * promotion : 1
     */

    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("price")
    private String price;
    /**
     * 商品描述
     */
    @Expose
    @SerializedName("subtitle")
    private String subtitle;
    /**
     * 商品列表显示使用
     */
    @Expose
    @SerializedName("main_image")
    private String mainImage;
    /**
     * 商品详情里面的头图列表
     */
    @Expose
    @SerializedName("sub_images")
    private String subImages;
    /**
     * 商品详情里面的详情图列表
     */
    @Expose
    @SerializedName("detail")
    private String detailImages;
    @Expose
    @SerializedName("unit")
    private String unit;
    @Expose
    @SerializedName("stock")
    private int stock;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("category_id")
    private long categoryId;
    @Expose
    @SerializedName("category_name")
    private String categoryName;
    @Expose
    @SerializedName("freight")
    private String freight;
    @Expose
    @SerializedName("weight")
    private String weight;
    @Expose
    @SerializedName("create_time")
    private long create_time;
    @Expose
    @SerializedName("update_time")
    private long update_time;
    @Expose
    @SerializedName("promotion")
    private int promotion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(String detailImages) {
        this.detailImages = detailImages;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.subtitle);
        dest.writeString(this.mainImage);
        dest.writeString(this.subImages);
        dest.writeString(this.detailImages);
        dest.writeString(this.unit);
        dest.writeInt(this.stock);
        dest.writeInt(this.status);
        dest.writeLong(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeString(this.freight);
        dest.writeString(this.weight);
        dest.writeLong(this.create_time);
        dest.writeLong(this.update_time);
        dest.writeInt(this.promotion);
    }

    public GoodsBean() {
    }

    protected GoodsBean(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.price = in.readString();
        this.subtitle = in.readString();
        this.mainImage = in.readString();
        this.subImages = in.readString();
        this.detailImages = in.readString();
        this.unit = in.readString();
        this.stock = in.readInt();
        this.status = in.readInt();
        this.categoryId = in.readLong();
        this.categoryName = in.readString();
        this.freight = in.readString();
        this.weight = in.readString();
        this.create_time = in.readLong();
        this.update_time = in.readLong();
        this.promotion = in.readInt();
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
