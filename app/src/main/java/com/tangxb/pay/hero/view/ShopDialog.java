package com.tangxb.pay.hero.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tangxb.pay.hero.R;


/**
 * Created by zll on 2017/5/26.
 */

public class ShopDialog implements  View.OnClickListener{

    private Context context;
    private AlertDialog.Builder customizeDialog;
    private TextView messageText;
    private TextView titleText;
    private Button sureBtn,cancelBtn;
    private View dialogView;
    private AlertDialog dialog;

    public ShopDialog(Context context) {
        this.context = context;
        customizeDialog = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.cust_message_dialog, null);
        titleText = (TextView) dialogView.findViewById(R.id.title);
        customizeDialog.setView(dialogView);
        messageText = (TextView) dialogView.findViewById(R.id.message);
       sureBtn = (Button) dialogView.findViewById(R.id.sure_btn);
       cancelBtn = (Button) dialogView.findViewById(R.id.cancel_btn);

        sureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public void setSureBtn(String show){
        sureBtn.setText(show);
    }

    public void setCancelBtn(String show){
        cancelBtn.setText(show);
    }

    public void setTitle(String title){
        titleText.setText(title);
    }

    public void setMessage(String message){
        messageText.setText(message);
    }

    public void setOnSureClickListener(View.OnClickListener onClickListener){
        sureBtn.setOnClickListener(onClickListener);
    }

    public void setOnCancelClickListener(View.OnClickListener onClickListener){
        cancelBtn.setOnClickListener(onClickListener);
    }

    public void show(){
        dialog = customizeDialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
          dialog.dismiss();
    }
}
