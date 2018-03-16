/*
Copyright 2015 chanven

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.tangxb.pay.hero.ui;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.adapter.ChatAdapterForRv;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * RecyclerView with loadmore
 *
 * @author Chanven
 * @date 2015-9-21
 */
public class RecyclerViewActivity2 extends BaseActivity {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private List<String> mData = new ArrayList<String>();
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    int page = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.recyclerview_layout;
    }

    @Override
    protected void initData() {
        init_2();

        //使用下面的方法,获取网络数据
//        addSubscription(getPurchaseUserList(), new DefaultConsumer<String>() {
//            @Override
//            public void operateSuccess(MBaseBean<String> baseBean) {
//
//            }
//        });
    }

//    public Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(String token, String signatrue, String timestamp, int page, int rows) {
//        return RetrofitRxClient.INSTANCE.getRetrofit().create(TaskRxAPI.class).getPurchaseUserList(token, signatrue, timestamp, page, rows);
//    }

    private void init_2() {
        // 结合https://github.com/hongyangAndroid/baseAdapter测试
        ChatAdapterForRv chatAdapterForRv = new ChatAdapterForRv(this, mData);
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) chatAdapterForRv);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_container);
        final DynamicBox box = new DynamicBox(this, linearLayout);
        box.setLoadingMessage("Loading your music ...");
        box.showLoadingLayout();

        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, ViewHolder vh, int position) {
                ToastUtils.t(mApplication, "点击的位置是==" + position);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {

            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        box.hideAll();
                        page = 0;
                        mData.clear();
                        int size = 17;
                        for (int i = 0; i < size; i++) {
                            mData.add(new String("  RecyclerView item  -" + i));
                        }
                        // 注意使用notifyItemRangeChangedHF在下拉刷新的时候,由于之前clear会出现数据不同步问题
                        mAdapter.notifyDataSetChangedHF();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 1500);
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        int beforeChangeSize = mData.size() + mAdapter.getHeadSize() + 1;
                        int size = 1;
                        for (int i = 0; i < size; i++) {
                            mData.add(new String("  RecyclerView item  - add " + page));
                        }
                        mAdapter.notifyItemRangeInsertedHF(beforeChangeSize, size);
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(RecyclerViewActivity2.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }
}
