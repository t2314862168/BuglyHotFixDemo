package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zll on 2017/5/18.
 */

public class AddressBean {
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("nickname")
    private String nickname;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
