package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 区域实体<br>
 * Created by tangxuebing on 2018/5/16.
 */

public class AreaBean {
    /**
     * "id":50,
     * "name":"重庆市",
     * "parent_id":0,
     * "abbr":"重庆市",
     * "level":1,
     * "enable":1
     */
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
    @SerializedName("abbr")
    private String abbr;
    @Expose
    @SerializedName("level")
    private int level;
    @Expose
    @SerializedName("enable")
    private int enable;

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

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
