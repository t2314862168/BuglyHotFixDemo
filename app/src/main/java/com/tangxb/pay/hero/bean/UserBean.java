package com.tangxb.pay.hero.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Taxngb on 2017/12/22.
 */
public class UserBean implements Parcelable {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("nickname")
    private String nickname;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("realname")
    private String realName;
    @Expose
    @SerializedName("sex")
    private int sex;
    @Expose
    @SerializedName("status_")
    private int status;
    @Expose
    @SerializedName("parent_id")
    private long parentId;
    @Expose
    @SerializedName("address_id")
    private long addressId;
    @Expose
    @SerializedName("role_id")
    private long roleId;
    @Expose
    @SerializedName("role_name")
    private String roleName;
    @Expose
    @SerializedName("is_multi")
    private int isMulti;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("address")
    private String address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(int isMulti) {
        this.isMulti = isMulti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.icon);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.nickname);
        dest.writeString(this.mobile);
        dest.writeString(this.token);
        dest.writeString(this.realName);
        dest.writeInt(this.sex);
        dest.writeInt(this.status);
        dest.writeLong(this.parentId);
        dest.writeLong(this.addressId);
        dest.writeLong(this.roleId);
        dest.writeString(this.roleName);
        dest.writeInt(this.isMulti);
        dest.writeString(this.city);
        dest.writeString(this.address);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.id = in.readLong();
        this.icon = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.nickname = in.readString();
        this.mobile = in.readString();
        this.token = in.readString();
        this.realName = in.readString();
        this.sex = in.readInt();
        this.status = in.readInt();
        this.parentId = in.readLong();
        this.addressId = in.readLong();
        this.roleId = in.readLong();
        this.roleName = in.readString();
        this.isMulti = in.readInt();
        this.city = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
