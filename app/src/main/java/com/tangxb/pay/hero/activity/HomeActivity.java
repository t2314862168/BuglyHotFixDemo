package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.decoration.MDividerGridItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页界面<br>
 * Created by tangxuebing on 2018/5/8.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private int[] mDataResIds = new int[]{R.string.user_manger, R.string.order_manger
            , R.string.goods_manger, R.string.deliver_goods_manger, R.string.data_statistics
            , R.string.permission_manger};
    private List<String> mData = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mDataResIds.length; i++) {
            mData.add(mResources.getString(mDataResIds[i]));
        }
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(mActivity, R.layout.item_home, mData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv, s);
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        // 请去修改styles.xml里面注释掉的android:listDivider代码
        mRecyclerView.addItemDecoration(new MDividerGridItemDecoration(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(mData.get(position));
            }
        });
        ptrClassicFrameLayout.setEnabled(false);
    }

    public void handleItemClick(String itemStr) {
        Intent intent = null;
        if (itemStr.equals(mResources.getString(R.string.user_manger))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.order_manger))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.goods_manger))) {
            intent = getIntentWithPublicParams(GoodsMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.deliver_goods_manger))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.data_statistics))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.dispatch_manger))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.permission_manger))) {
            intent = getIntentWithPublicParams(UserMangerActivity.class);
        }
        if (intent != null) {
            startCustomActivity(intent);
        }
    }


}
