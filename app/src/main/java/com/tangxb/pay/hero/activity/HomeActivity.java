package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.CopyBeanController;
import com.tangxb.pay.hero.decoration.MDividerGridItemDecoration;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.tangxb.pay.hero.util.ConstUtils.PM_100;
import static com.tangxb.pay.hero.util.ConstUtils.PM_200;
import static com.tangxb.pay.hero.util.ConstUtils.PM_300;
import static com.tangxb.pay.hero.util.ConstUtils.PM_400;
import static com.tangxb.pay.hero.util.ConstUtils.PM_500;
import static com.tangxb.pay.hero.util.ConstUtils.PM_600;
import static com.tangxb.pay.hero.util.ConstUtils.PM_800;
import static com.tangxb.pay.hero.util.ConstUtils.PM_900;

/**
 * 主页界面<br>
 * Created by tangxuebing on 2018/5/8.
 */

public class HomeActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private int[] mDataResIds = new int[]{R.string.user_manger, R.string.order_manger
            , R.string.goods_manger, R.string.data_statistics
            , R.string.warehouse_manger, R.string.deliver_goods_manger
            , R.string.person_warehouse, R.string.permission_manger, R.string.personal_center};
    private int[] mPermissionIds = new int[]{PM_600.getId(), PM_800.getId()
            , PM_100.getId(), PM_200.getId()
            , PM_900.getId(), PM_300.getId()
            , PM_400.getId(), PM_500.getId()
    };
    private List<String> mData = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    int PERSON_CENTER = 444;
    long firstTime;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.home_page);
        for (int i = 0; i < mPermissionIds.length; i++) {
            if (mApplication.hasPermission(mPermissionIds[i])) {
                mData.add(mResources.getString(mDataResIds[i]));
            }
        }
        // 添加个人中心
        mData.add(mResources.getString(mDataResIds[mDataResIds.length - 1]));
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
            intent = getIntentWithPublicParams(OrderManagerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.goods_manger))) {
            intent = getIntentWithPublicParams(GoodsMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.deliver_goods_manger))) {
            intent = getIntentWithPublicParams(SendGoodsMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.data_statistics))) {
            intent = getIntentWithPublicParams(DataStatisticsActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.person_warehouse))) {
            intent = getIntentWithPublicParams(DispatchMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.warehouse_manger))) {
            intent = getIntentWithPublicParams(WarehouseMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.permission_manger))) {
            intent = getIntentWithPublicParams(PermissionMangerActivity.class);
        } else if (itemStr.equals(mResources.getString(R.string.personal_center))) {
            intent = getIntentWithPublicParams(UserCenterActivity.class);
            intent.putExtra("userBean", mApplication.getUserLoginResultBean().getUser());
            startActivityForResult(intent, PERSON_CENTER);
            return;
        }
        if (intent != null) {
            startCustomActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PERSON_CENTER) {
            UserBean tempBean = data.getParcelableExtra("userBean");
            CopyBeanController.copyUserBean(tempBean, mApplication.getUserLoginResultBean().getUser());
        }
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000L) {
            ToastUtils.t(mApplication, "再按一次退出程序");
            firstTime = secondTime;
            return;
        }
        super.onBackPressed();
    }
}
