package com.tangxb.pay.hero.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.KeyValueBean;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class EditUserInfoActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private List<KeyValueBean> mData = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    private void mockData() {
        KeyValueBean bean1 = new KeyValueBean();
        bean1.setKey("姓名：");
        bean1.setValue("tangxb");
        KeyValueBean bean2 = new KeyValueBean();
        bean2.setKey("电话：");
        bean2.setValue("1789828282");
        KeyValueBean bean3 = new KeyValueBean();
        bean3.setKey("密码：");
        bean3.setValue("点击修改");
        mData.add(bean1);
        mData.add(bean2);
        mData.add(bean3);
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.personal_info);

        mockData();

        CommonAdapter commonAdapter = new CommonAdapter<KeyValueBean>(mActivity, R.layout.item_edit_user_info, mData) {
            @Override
            protected void convert(ViewHolder viewHolder, KeyValueBean item, int position) {
                viewHolder.setText(R.id.tv_key, item.getKey());
                viewHolder.setText(R.id.tv_value, item.getValue());
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.setEnabled(false);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position);
            }
        });
    }

    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {
        switch (position) {
            case 0:
                MDialogUtils.showNumberDialog(mActivity, "商品上浮比例", 0, 0, 100, true, new MDialogUtils.OnSureListener() {
                    @Override
                    public void onSure(final int number, int offset, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case 1:
                MDialogUtils.showEditTextDialog(mActivity, "Title", "Teext", new MDialogUtils.OnSureTextListener() {
                    @Override
                    public void onSure(String text, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case 2:
                MDialogUtils.showSingleChoiceDialog(mActivity, "Title", new String[]{"nomral", "ddd"}, new MDialogUtils.OnCheckListener() {
                    @Override
                    public void onSure(int which, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}
