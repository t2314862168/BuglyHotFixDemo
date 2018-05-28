package com.tangxb.pay.hero.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tangxuebing on 2018/5/24.
 */

public class WarehouseBean implements Parcelable {

    /**
     * id : 1527324036853
     * name : zd
     * city : 四川省��枝花市市辖区
     * address : rr
     * status : 1
     * distance : 12
     */
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("status_")
    private int status;
    @Expose
    @SerializedName("distance")
    private int distance;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.address);
        dest.writeInt(this.status);
        dest.writeInt(this.distance);
    }

    public WarehouseBean() {
    }

    protected WarehouseBean(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.status = in.readInt();
        this.distance = in.readInt();
    }

    public static final Parcelable.Creator<WarehouseBean> CREATOR = new Parcelable.Creator<WarehouseBean>() {
        @Override
        public WarehouseBean createFromParcel(Parcel source) {
            return new WarehouseBean(source);
        }

        @Override
        public WarehouseBean[] newArray(int size) {
            return new WarehouseBean[size];
        }
    };
}
