package com.tangxb.pay.hero.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.fragment.OrderMangerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zll on 2018/1/12.
 */

public class OrderManagerActivityFragmentAdapter extends FragmentPagerAdapter {
    private List<OrderBean> mList = new ArrayList<>();
    private List<OrderMangerFragment> fragmentList = new ArrayList<>();

    public OrderManagerActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<OrderBean> mList) {
        this.mList.addAll(mList);
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            OrderMangerFragment fragment = OrderMangerFragment.getInstance(mList.get(i).getName(), mList.get(i).getId());
            fragmentList.add(i, fragment);
        }
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
