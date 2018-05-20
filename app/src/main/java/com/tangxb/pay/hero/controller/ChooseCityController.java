package com.tangxb.pay.hero.controller;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.ChooseCityRxAPI;
import com.tangxb.pay.hero.bean.AreaBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;
import com.tangxb.pay.hero.util.MDialogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class ChooseCityController extends BaseControllerWithActivity {
    public interface ChooseListener {
        void chooseArea(String parentId, String parentName);
    }

    public interface ChoosePositionListener {
        void chooseArea(int position, String parentId, String parentName);
    }

    String titleName;
    ChooseListener listener;
    ChoosePositionListener positionListener;
    StringBuilder stringBuilder;
    int position;

    public ChooseCityController(BaseActivity baseActivity, ChooseListener listener) {
        super(baseActivity);
        titleName = baseActivity.getString(R.string.choose_area);
        this.listener = listener;
        stringBuilder = new StringBuilder();
    }

    public ChooseCityController(BaseActivity baseActivity, ChoosePositionListener listener) {
        super(baseActivity);
        titleName = baseActivity.getString(R.string.choose_area);
        this.positionListener = listener;
        stringBuilder = new StringBuilder();
    }

    public void setPosition(int position) {
        this.position = position;
    }


    /**
     * 选择区域
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param parentId  如果没有父id,则传0
     * @return
     */
    Observable<MBaseBean<List<AreaBean>>> getArea(String token, String signatrue
            , String timestamp, String parentId) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(ChooseCityRxAPI.class)
                .getArea(token, signatrue, timestamp, TextUtils.isEmpty(parentId) ? "0" : parentId);
    }

    /**
     * 选择区域
     *
     * @param parentId 如果没有父id,则传0
     */
    public void chooseArea(final String parentId, final String parentName) {
        if (TextUtils.isEmpty(parentId) || parentId.equals("0")) {
            // 清除之前的数据
            stringBuilder.delete(0, stringBuilder.toString().length());
        } else {
            stringBuilder.append(parentName);
        }
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("parentId", TextUtils.isEmpty(parentId) ? "0" : parentId);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        if (baseActivity.get() == null) return;
        baseActivity.get().addSubscription(getArea(token, signatrue, timestamp, parentId), new Consumer<MBaseBean<List<AreaBean>>>() {
            @Override
            public void accept(MBaseBean<List<AreaBean>> baseBean) throws Exception {
                showAreaDialog(parentId, parentName, baseBean.getData());
            }
        });
    }

    /**
     * 获取到城市数据之后,显示弹窗
     *
     * @param parentId
     * @param parentName
     * @param baseBean
     */
    public void showAreaDialog(String parentId, String parentName, List<AreaBean> baseBean) {
        if (baseActivity.get() == null) return;
        if (baseBean == null || baseBean.size() == 0) {
            if (listener != null) {
                listener.chooseArea(parentId, stringBuilder.toString());
            }
            if (positionListener != null) {
                positionListener.chooseArea(position, parentId, stringBuilder.toString());
            }
            return;
        }
        int size = baseBean.size();
        final long[] areaIds = new long[size];
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            areaIds[i] = baseBean.get(i).getId();
            items[i] = baseBean.get(i).getName();
        }
        MDialogUtils.showSingleChoiceDialog(baseActivity.get(), titleName, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                chooseArea(areaIds[which] + "", items[which]);
            }
        });
    }

}
