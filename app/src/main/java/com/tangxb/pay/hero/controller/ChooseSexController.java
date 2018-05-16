package com.tangxb.pay.hero.controller;

import android.support.v7.app.AlertDialog;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.util.MDialogUtils;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class ChooseSexController extends BaseControllerWithActivity {
    public interface ChooseListener {
        void chooseArea(int sexIndex, String str);
    }

    String titleName;
    ChooseListener listener;

    public ChooseSexController(BaseActivity baseActivity, ChooseListener listener) {
        super(baseActivity);
        titleName = baseActivity.getString(R.string.please_set_sex);
        this.listener = listener;
    }


    /**
     * 选择性别
     */
    public void chooseSex() {
        if (baseActivity.get() == null) return;
        // 0 女 1 男
        final String[] items = new String[2];
        items[0] = baseActivity.get().getString(R.string.female);
        items[1] = baseActivity.get().getString(R.string.male);
        MDialogUtils.showSingleChoiceDialog(baseActivity.get(), titleName, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                if (listener != null) {
                    listener.chooseArea(which, items[which]);
                }
            }
        });
    }

}
