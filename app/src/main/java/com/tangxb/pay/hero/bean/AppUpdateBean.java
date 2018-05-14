package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * app升级实体<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class AppUpdateBean {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("deprecate")
    private boolean deprecate;
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("localCode")
    private int localCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLocalCode() {
        return localCode;
    }

    public void setLocalCode(int localCode) {
        this.localCode = localCode;
    }
}
