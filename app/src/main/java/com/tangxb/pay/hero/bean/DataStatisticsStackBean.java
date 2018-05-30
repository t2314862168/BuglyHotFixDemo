package com.tangxb.pay.hero.bean;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class DataStatisticsStackBean {
    int level;
    String id;

    public DataStatisticsStackBean(int level, String id) {
        this.level = level;
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
