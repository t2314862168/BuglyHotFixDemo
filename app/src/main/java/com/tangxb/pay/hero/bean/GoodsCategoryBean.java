package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yy520 on 2017-12-2.
 */

public class GoodsCategoryBean {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("parent_id")
    private long parentId;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("status_")
    private int status;

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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
