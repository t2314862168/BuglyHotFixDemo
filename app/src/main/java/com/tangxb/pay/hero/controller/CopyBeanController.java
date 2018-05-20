package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.UserBean;

/**
 * Created by zll on 2018/5/19.
 */

public class CopyBeanController {
    public static void copyGoodsBean(GoodsBean tempBean, GoodsBean bean) {
        bean.setName(tempBean.getName());
        bean.setPrice(tempBean.getPrice());
        bean.setSubtitle(tempBean.getSubtitle());
        bean.setMainImage(tempBean.getMainImage());
        bean.setSubImages(tempBean.getSubImages());
        bean.setDetailImages(tempBean.getDetailImages());
        bean.setUnit(tempBean.getUnit());
        bean.setStock(tempBean.getStock());
        bean.setStatus(tempBean.getStatus());
        bean.setCategoryId(tempBean.getCategoryId());
        bean.setCategoryName(tempBean.getCategoryName());
        bean.setFreight(tempBean.getFreight());
        bean.setWeight(tempBean.getWeight());
        bean.setCreate_time(tempBean.getCreate_time());
        bean.setUpdate_time(tempBean.getUpdate_time());
        bean.setPromotion(tempBean.getPromotion());
    }


    public static void copyUserBean(UserBean tempBean, UserBean bean) {
        bean.setId(tempBean.getId());
        bean.setIcon(tempBean.getIcon());
        bean.setUsername(tempBean.getUsername());
        bean.setPassword(tempBean.getPassword());
        bean.setNickname(tempBean.getNickname());
        bean.setMobile(tempBean.getMobile());
        bean.setToken(tempBean.getToken());
        bean.setRealName(tempBean.getRealName());
        bean.setSex(tempBean.getSex());
        bean.setStatus(tempBean.getStatus());
        bean.setAddressId(tempBean.getAddressId());
        bean.setRoleId(tempBean.getRoleId());
        bean.setRoleName(tempBean.getRoleName());
        bean.setIsMulti(tempBean.getIsMulti());
        bean.setCity(tempBean.getCity());
        bean.setAddress(tempBean.getAddress());
    }
}
