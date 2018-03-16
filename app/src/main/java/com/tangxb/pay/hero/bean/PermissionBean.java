package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yy520 on 2017-12-2.
 */

public class PermissionBean {
    public static final PermissionBean Shopping = new PermissionBean(100, "购买商品");
    public static final PermissionBean PyShopping = new PermissionBean(200, "辅助下单");
    public static final PermissionBean Purchase = new PermissionBean(300, "采购商品");
    public static final PermissionBean MgProduct = new PermissionBean(400, "商品管理");
    public static final PermissionBean MgOrder = new PermissionBean(500, "订单管理");
    public static final PermissionBean OrderEdit = new PermissionBean(502, "订单编辑信息");
    public static final PermissionBean MgUser = new PermissionBean(600, "用户管理");
    public static final PermissionBean P_AddUser = new PermissionBean(602, "添加用户");
    public static final PermissionBean Supplier = new PermissionBean(700, "查看所有订单");
    public static final PermissionBean P_EDIT_PRODUCT = new PermissionBean(410, "完全编辑商品");
    public static final PermissionBean P_MgAllProduct = new PermissionBean(420, "管理所有商品");
    public static final PermissionBean P_MgAllCATEGORY = new PermissionBean(430, "管理分类");
    public static final PermissionBean proxy = new PermissionBean(800, "代理商权限");
    public static final PermissionBean UploadProduct = new PermissionBean(401, "商品上传");
    public static final PermissionBean P_PAY_ORDER = new PermissionBean(501, "收款权限");
    public static final PermissionBean P_CANCEL_ORDER = new PermissionBean(503, "取消订单");
    public static final PermissionBean P_PURCHASE_PRODUCT_UNIT = new PermissionBean(303, "分件货");
    public static final PermissionBean P_PURCHASE_PRODUCT_DESC = new PermissionBean(304, "分斤货");
    public static final PermissionBean P_CHANGE_PRICE = new PermissionBean(305, "定价");
    public static final PermissionBean P_PAY_PRODUCT = new PermissionBean(306, "付货款");

    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("key")
    private String key;

    public PermissionBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PermissionBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
