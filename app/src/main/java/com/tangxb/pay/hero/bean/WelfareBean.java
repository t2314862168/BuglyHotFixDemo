package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tangxb on 2017/2/15.
 */
public class WelfareBean {
    /**
     * _id : 573d39ea6776591ca681f8c7
     * createdAt : 2016-05-19T11:58:34.715Z
     * desc : 5.19
     * publishedAt : 2016-05-19T12:09:29.617Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f40k4dyrhhj20iz0sg41b.jpg
     * used : true
     * who : daimajia
     */
    @Expose
    @SerializedName("_id")
    private String _id;
    @Expose
    @SerializedName("createdAt")
    private String createdAt;
    @Expose
    @SerializedName("desc")
    private String desc;
    @Expose
    @SerializedName("publishedAt")
    private String publishedAt;
    @Expose
    @SerializedName("source")
    private String source;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("used")
    private boolean used;
    @Expose
    @SerializedName("who")
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
