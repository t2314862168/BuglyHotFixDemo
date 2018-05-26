package com.tangxb.pay.hero.activity;

import android.support.design.widget.Snackbar;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tangxb.pay.hero.R;

/**
 * 数据统计界面<br>
 * Created by zll on 2018/5/26.
 */

public class DataStatisticsActivity extends BaseActivityWithTitleOnly {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_data_statistics;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.data_statistics);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
