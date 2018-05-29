package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tangxuebing on 2018/5/29.
 */

public class ReceiveGoodsBean {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("storage_id")
    private long storage_id;
    @Expose
    @SerializedName("request_id")
    private long request_id;
    @Expose
    @SerializedName("status_")
    private int status;
    @Expose
    @SerializedName("send_id")
    private long send_id;
    @Expose
    @SerializedName("receive_id")
    private long receive_id;
    @Expose
    @SerializedName("cart_no")
    private String cart_no;
    @Expose
    @SerializedName("request_name")
    private String request_name;
    @Expose
    @SerializedName("send_name")
    private String send_name;
    @Expose
    @SerializedName("log")
    private String log;
    @Expose
    @SerializedName("create_time")
    private long create_time;
    @Expose
    @SerializedName("update_time")
    private long update_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(long storage_id) {
        this.storage_id = storage_id;
    }

    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long request_id) {
        this.request_id = request_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSend_id() {
        return send_id;
    }

    public void setSend_id(long send_id) {
        this.send_id = send_id;
    }

    public long getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(long receive_id) {
        this.receive_id = receive_id;
    }

    public String getCart_no() {
        return cart_no;
    }

    public void setCart_no(String cart_no) {
        this.cart_no = cart_no;
    }

    public String getRequest_name() {
        return request_name;
    }

    public void setRequest_name(String request_name) {
        this.request_name = request_name;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
