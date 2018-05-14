package com.tangxb.pay.hero.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tangxb.pay.hero.bean.RoleBean;
import com.tangxb.pay.hero.fragment.UserMangerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zll on 2018/1/12.
 */

public class UserManagerActivityFragmentAdapter extends FragmentPagerAdapter {
    private List<RoleBean> mList = new ArrayList<>();
    private List<UserMangerFragment> fragmentList = new ArrayList<>();

    public UserManagerActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<RoleBean> mList) {
        this.mList.addAll(mList);
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            UserMangerFragment fragment = UserMangerFragment.getInstance(mList.get(i).getName(), mList.get(i).getId());
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
