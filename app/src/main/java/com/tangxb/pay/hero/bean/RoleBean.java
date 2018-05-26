package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class RoleBean {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("level")
    private long level;

    public RoleBean() {
    }

    public RoleBean(long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
