package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zll on 2018/5/26.
 */

public class WarehouseAllInOneBean {

    /**
     * product_name : 百草味话梅西瓜子200g_袋
     * product_image : http:05-26 17:37:14.485 26638-26736/com.tangxb.pay.hero D/PRETTY_LOGGER-HttpLogger: │ oweihuameixiguazi200g_dai/dougan/tangxiaobingv1_1526914150536813.jpg
     * unit : 箱
     * request_num : 0
     * wait_num : 0
     * num : 100
     */
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("product_image")
    private String product_image;
    @Expose
    @SerializedName("unit")
    private String unit;
    @Expose
    @SerializedName("request_num")
    private int request_num;
    @Expose
    @SerializedName("wait_num")
    private int wait_num;
    @Expose
    @SerializedName("num")
    private int num;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getRequest_num() {
        return request_num;
    }

    public void setRequest_num(int request_num) {
        this.request_num = request_num;
    }

    public int getWait_num() {
        return wait_num;
    }

    public void setWait_num(int wait_num) {
        this.wait_num = wait_num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
