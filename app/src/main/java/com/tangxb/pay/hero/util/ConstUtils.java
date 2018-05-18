package com.tangxb.pay.hero.util;

import com.tangxb.pay.hero.bean.PermissionBean;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class ConstUtils {
    /**
     * ACCESS_ID,ACCESS_KEY是在阿里云申请的
     */
    public static final String ACCESS_ID = "LTAIbHl7YNNih800";
    /**
     * ACCESS_ID,ACCESS_KEY是在阿里云申请的
     */
    public static final String ACCESS_KEY = "SSZxlQ3Ko7iYFvQGuuJAwT70IMex1V";
    /**
     * OSS_ENDPOINT是一个OSS区域地址
     */
    public static final String OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    /**
     * BUCKET
     */
    public static final String BUCKET = "lichuanshipin";
    /**
     * BUCKET_PATH
     */
    public static String BUCKET_PATH = "http://" + BUCKET + ".oss-cn-hangzhou.aliyuncs.com/";
    /**
     * 保存登录的用户名key
     */
    public static final String ACCOUNT_KEY = "account";
    /**
     * 保存登录的密码key
     */
    public static final String PASSWORD_KEY = "password";
    /**
     * 每一页的页数大小
     */
    public static final int PAGE_SIZE = 12;
    /**
     * 商品管理权限
     */
    public static final PermissionBean PM_100 = new PermissionBean(100, "商品管理");
    /**
     * 数据统计权限
     */
    public static final PermissionBean PM_200 = new PermissionBean(200, "数据统计");
    /**
     * 查看所有统计权限
     */
    public static final PermissionBean PM_201 = new PermissionBean(201, "查看所有统计", 200);
    /**
     * 发货管理权限
     */
    public static final PermissionBean PM_300 = new PermissionBean(300, "发货管理");
    /**
     * 配送管理权限
     */
    public static final PermissionBean PM_400 = new PermissionBean(400, "配送管理");
    /**
     * 权限管理权限
     */
    public static final PermissionBean PM_500 = new PermissionBean(500, "权限管理");
    /**
     * 用户管理权限
     */
    public static final PermissionBean PM_600 = new PermissionBean(600, "用户管理");

    /**
     * 消息管理权限
     */
    public static final PermissionBean PM_700 = new PermissionBean(700, "消息管理");
    /**
     * 审批权限权限
     */
    public static final PermissionBean PM_701 = new PermissionBean(701, "审批权限", 700);
    /**
     * 订单管理权限
     */
    public static final PermissionBean PM_800 = new PermissionBean(800, "订单管理");
    /**
     * 管理所有订单权限
     */
    public static final PermissionBean PM_801 = new PermissionBean(801, "管理所有订单", 800);
}
