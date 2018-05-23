package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class OrderItemBean {
    /**
     * "id":8,
     * "user_id":1,
     * "product_id":72,
     * "sub_freight":546000.00,
     * "sub_price":25950.00,
     * "status":0,
     * "buy_num":3,
     * "give_num":null,
     * "order_id":1526979202458,
     * "proxy_id":1526480121770,
     * "create_time":1526979202000,
     * "update_time":null,
     * "piece_price":8650.00,
     * "product_image":"http://lichuanshipin.oss-cn-hangzhou.aliyuncs.com/product_image/haoshuaihaoshuaijiushijiushi/ganguolei/tangxiaobingv1_152697390417264.jpg",
     * "product_name":"好帅好帅就是就是",
     * "product_unit":"箱",
     * "status_name":"待付款"
     */
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("user_id")
    private long userId;
    @Expose
    @SerializedName("product_id")
    private long productId;
    @Expose
    @SerializedName("sub_freight")
    private String sub_freight;
    @Expose
    @SerializedName("sub_price")
    private String sub_price;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("buy_num")
    private int buy_num;
    @Expose
    @SerializedName("give_num")
    private int give_num;
    @Expose
    @SerializedName("order_id")
    private long order_id;
    @Expose
    @SerializedName("proxy_id")
    private long proxy_id;
    @Expose
    @SerializedName("create_time")
    private String create_time;
    @Expose
    @SerializedName("update_time")
    private String update_time;
    @Expose
    @SerializedName("piece_price")
    private String piece_price;
    @Expose
    @SerializedName("product_image")
    private String product_image;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("product_unit")
    private String product_unit;
    @Expose
    @SerializedName("status_name")
    private String status_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSub_freight() {
        return sub_freight;
    }

    public void setSub_freight(String sub_freight) {
        this.sub_freight = sub_freight;
    }

    public String getSub_price() {
        return sub_price;
    }

    public void setSub_price(String sub_price) {
        this.sub_price = sub_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public int getGive_num() {
        return give_num;
    }

    public void setGive_num(int give_num) {
        this.give_num = give_num;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getProxy_id() {
        return proxy_id;
    }

    public void setProxy_id(long proxy_id) {
        this.proxy_id = proxy_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getPiece_price() {
        return piece_price;
    }

    public void setPiece_price(String piece_price) {
        this.piece_price = piece_price;
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

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
