package com.tangxb.pay.hero.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Tangxb on 2016/12/3.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<String> {
    public ChatAdapterForRv(Context context, List<String> datas) {
        super(context, datas);
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }
}
