package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 具有采购权限的用户实体<br>
 * Created by Taxngb on 2017/12/28.
 */

public class PurchaseUserBean {
    /**
     * id : 1512716132514
     * nickname : 田胜军
     * mobile : 13541131765
     * enable : true
     * parent_id : 0
     * role_id : 10
     * role_name : 总经理
     */
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("nickname")
    private String nickname;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("enable")
    private boolean enable;
    @Expose
    @SerializedName("parent_id")
    private Long parent_id;
    @Expose
    @SerializedName("role_id")
    private Long role_id;
    @Expose
    @SerializedName("role_name")
    private String role_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
