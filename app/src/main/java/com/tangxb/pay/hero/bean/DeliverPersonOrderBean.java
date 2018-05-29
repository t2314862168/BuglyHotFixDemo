package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class DeliverPersonOrderBean {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("product_id")
    private long product_id;
    @Expose
    @SerializedName("order_id")
    private long order_id;
    @Expose
    @SerializedName("send_num")
    private int send_num;
    @Expose
    @SerializedName("request_num")
    private int request_num;
    @Expose
    @SerializedName("receive_num")
    private Integer receive_num;
    @Expose
    @SerializedName("product_image")
    private String product_image;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("unit")
    private String product_unit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getSend_num() {
        return send_num;
    }

    public void setSend_num(int send_num) {
        this.send_num = send_num;
    }

    public int getRequest_num() {
        return request_num;
    }

    public void setRequest_num(int request_num) {
        this.request_num = request_num;
    }

    public Integer getReceive_num() {
        return receive_num;
    }

    public void setReceive_num(Integer receive_num) {
        this.receive_num = receive_num;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }
}
