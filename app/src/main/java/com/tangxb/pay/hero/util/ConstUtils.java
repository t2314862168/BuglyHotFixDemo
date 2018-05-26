package com.tangxb.pay.hero.util;

import com.tangxb.pay.hero.bean.PermissionBean;
import com.tangxb.pay.hero.bean.RoleBean;

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
     * 1、在售 0、已下架、-1、删除
     */
    public static final int GOOD_ON_SALE = 1;
    /**
     * 1、在售 0、已下架、-1、删除
     */
    public static final int GOOD_NOT_SALE = 0;
    /**
     * 1、在售 0、已下架、-1、删除
     */
    public static final int GOOD_UN_ENABLE_SALE = -1;
    /**
     * 默认为0表示非促销  1 表示促销
     */
    public static final int GOOD_PROMOTION = 1;
    /**
     * 默认为0表示非促销  1 表示促销
     */
    public static final int GOOD_UN_PROMOTION = 0;
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
    /**
     * 客户
     */
    public static final RoleBean RB_1000 = new RoleBean(1000, "客户");
    /**
     * 业务员
     */
    public static final RoleBean RB_600 = new RoleBean(600, "业务员");
    /**
     * 销售经理
     */
    public static final RoleBean RB_500 = new RoleBean(500, "销售经理");
    /**
     * 库管
     */
    public static final RoleBean RB_400 = new RoleBean(400, "库管");
    /**
     * 300
     */
    public static final RoleBean RB_300 = new RoleBean(300, "300");
    /**
     * 总经理
     */
    public static final RoleBean RB_200 = new RoleBean(200, "总经理");
    /**
     * 董事长
     */
    public static final RoleBean RB_100 = new RoleBean(100, "董事长");
    /**
     * 待审核
     */
    public static final RoleBean RB_0 = new RoleBean(0, "待审核");
}
