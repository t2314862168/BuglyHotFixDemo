package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class DataStatisticsBean {
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("unit")
    private String unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
