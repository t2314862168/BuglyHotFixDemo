package com.tangxb.pay.hero.event;

/**
 * 搜索事件<br>
 * Created by zll on 2018/1/15.
 */
public class SearchKeyEvent {
    private String searchKey;

    public SearchKeyEvent(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }
}
