package com.tangxb.pay.hero.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class UserLoginResultBean {
    @Expose
    @SerializedName("address")
    private AddressBean address;
    @Expose
    @SerializedName("permission")
    private List<PermissionBean> permission;
    @Expose
    @SerializedName("roleList")
    private List<RoleBean> roleList;
    @Expose
    @SerializedName("user")
    private UserBean user;
    @Expose
    @SerializedName("notice")
    private NoticeBean notice;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<PermissionBean> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionBean> permission) {
        this.permission = permission;
    }

    public List<RoleBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleBean> roleList) {
        this.roleList = roleList;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }
}
