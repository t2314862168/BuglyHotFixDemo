package com.tangxb.pay.hero.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class OrderBean implements Parcelable {
    /**
     * "sub_id":8,
     * "user_id":1,
     * "id":1526979202458,
     * "status":0,
     * "realname":"张琳琅",
     * "mobile":"15828066838",
     * "city":"四川省成都市温江区",
     * "address":"积极御景",
     * "status_name":"待付款",
     * "product_total_price":283058.00,
     * "buy_num":8,
     * "total_freight":546020.00,
     * "create_time":"2018-05-22 16:53:22",
     * "oper_status":"true"
     */
    @Expose
    @SerializedName("sub_id")
    private long subId;
    @Expose
    @SerializedName("user_id")
    private long userId;
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("realname")
    private String realname;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("status_name")
    private String statusName;
    @Expose
    @SerializedName("product_total_price")
    private String productTotalPrice;
    @Expose
    @SerializedName("buy_num")
    private int buyNum;
    @Expose
    @SerializedName("total_freight")
    private String totalFreight;
    @Expose
    @SerializedName("create_time")
    private String createTime;
    @Expose
    @SerializedName("oper_status")
    private String operStatus;
    @Expose
    @SerializedName("remark")
    private String remark;

    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(String totalFreight) {
        this.totalFreight = totalFreight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.subId);
        dest.writeLong(this.userId);
        dest.writeLong(this.id);
        dest.writeInt(this.status);
        dest.writeString(this.realname);
        dest.writeString(this.mobile);
        dest.writeString(this.city);
        dest.writeString(this.address);
        dest.writeString(this.statusName);
        dest.writeString(this.productTotalPrice);
        dest.writeInt(this.buyNum);
        dest.writeString(this.totalFreight);
        dest.writeString(this.createTime);
        dest.writeString(this.operStatus);
        dest.writeString(this.remark);
    }

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        this.subId = in.readLong();
        this.userId = in.readLong();
        this.id = in.readLong();
        this.status = in.readInt();
        this.realname = in.readString();
        this.mobile = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.statusName = in.readString();
        this.productTotalPrice = in.readString();
        this.buyNum = in.readInt();
        this.totalFreight = in.readString();
        this.createTime = in.readString();
        this.operStatus = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
