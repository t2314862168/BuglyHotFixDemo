package com.tangxb.pay.hero.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.view.ShopDialog;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class MDialogUtils {
    public interface OnOkListener {
        void onSure(int text, DialogInterface dialog);
    }

    public interface OnSurePriceListener {
        void onSure(String price, AlertDialog dialog);
    }

    public interface OnSureListener {
        void onSure(int number, int offset, AlertDialog dialog);
    }

    public interface OnSureTextListener {
        void onSure(String text, AlertDialog dialog);
    }

    public interface OnCheckListener {
        void onSure(int which, AlertDialog dialog);
    }

    public static void showEditTextDialog(final Context context, String title, String text, final OnSureTextListener OnSureTextListener) {
        final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_edit_text, null);
        customizeDialog.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.num_edit_text);
        final Button sureBtn = (Button) dialogView.findViewById(R.id.sure_btn);
        if (title != null) {
            TextView titleText = (TextView) dialogView.findViewById(R.id.dialog_title_text);
            titleText.setText(title);
        }
        editText.setText(text);
        editText.setSelection(text.length());
        final AlertDialog show = customizeDialog.show();
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                OnSureTextListener.onSure(text, show);
            }
        });
    }

    public static void showSingleChoiceDialog(final Context context, String title, String[] items, final OnCheckListener onCheckListener) {
        final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.list_dialog, null);
        TextView viewById = (TextView) dialogView.findViewById(R.id.title);
        viewById.setText(title);
        customizeDialog.setView(dialogView);
        ScrollView linearLayout = (ScrollView) dialogView.findViewById(R.id.scroll_layout);
        final AlertDialog dialog = customizeDialog.create();
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        if (items != null && items.length > 0) {
            for (int i = 0; i < items.length; i++) {
                TextView item = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_item2, null);
                item.setText(items[i]);
                item.setId(i);
                ll.addView(item);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView vv = (TextView) v;
                        onCheckListener.onSure(v.getId(), dialog);
                    }
                });
            }
        }
        linearLayout.addView(ll);
        dialog.show();
    }

    public static void showNumberDialog(final Context context, String title, final int origNum, final int mix, final int max, final boolean softkey, final OnSureListener onSureListener) {
        final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.cust_edit_num_dialog, null);
        customizeDialog.setView(dialogView);

        final int[] num = {origNum};
        final EditText editText = (EditText) dialogView.findViewById(R.id.num_edit_text);
        final Button reduceBtn = (Button) dialogView.findViewById(R.id.reduce_btn);
        final Button plusBtn = (Button) dialogView.findViewById(R.id.plus_btn);
        final Button sureBtn = (Button) dialogView.findViewById(R.id.sure_btn);
        if (title != null) {
            TextView titleText = (TextView) dialogView.findViewById(R.id.dialog_title_text);
            titleText.setText(title);
        }
        editText.setText(String.valueOf(origNum));


        final AlertDialog show = customizeDialog.show();


        dialogView.post(new Runnable() {
            @Override
            public void run() {
                if (softkey) {
                    final InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
                editText.setSelection(editText.getText().length());
            }
        });

        reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num[0] <= mix) return;
                num[0]--;
                editText.setText(String.valueOf(num[0]));
                editText.setSelection(editText.getText().length());
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num[0] >= max) return;
                num[0]++;
                editText.setText(String.valueOf(num[0]));
                editText.setSelection(editText.getText().length());
            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    num[0] = Integer.parseInt(editText.getText().toString());
                } catch (Exception e) {
                    editText.setText(String.valueOf(origNum));
                    editText.setSelection(editText.getText().length());
                    return;
                }
                if (num[0] <= mix - 1) {
                    num[0] = mix;
                    editText.setText(String.valueOf(num[0]));
                    return;
                }
                final InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && softInputIsOpen(editText.getContext())) {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
                int offsetNum = num[0] - origNum;
                onSureListener.onSure(num[0], offsetNum, show);
            }
        });
    }

    public static void showPriceDialog(final Context context, String title, final String origNum, final boolean allowNull, final OnSurePriceListener onSurePriceListener) {

        final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.cust_edit_price_dialog, null);
        customizeDialog.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.num_edit_text);
        final Button sureBtn = (Button) dialogView.findViewById(R.id.sure_btn);
        if (title != null) {
            TextView titleText = (TextView) dialogView.findViewById(R.id.dialog_title_text);
            titleText.setText(title);
        }
        editText.setText(String.valueOf(origNum));

        final AlertDialog show = customizeDialog.show();

        dialogView.post(new Runnable() {
            @Override
            public void run() {
                final InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
                editText.setSelection(editText.getText().length());
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = editText.getText().toString();
                if (price.length() != 0) {
                    BigDecimal numD = new BigDecimal(price);
                    price = numD.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else if (allowNull) {
                    price = "0";
                }

                onSurePriceListener.onSure(price, show);
            }
        });
    }

    public static void showPriceDialogNoBigDecimal(final Context context, String title, final String origNum, final OnSurePriceListener onSurePriceListener) {

        final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.cust_edit_price_dialog, null);
        customizeDialog.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.num_edit_text);
        final Button sureBtn = (Button) dialogView.findViewById(R.id.sure_btn);
        if (title != null) {
            TextView titleText = (TextView) dialogView.findViewById(R.id.dialog_title_text);
            titleText.setText(title);
        }
        editText.setText(String.valueOf(origNum));

        final AlertDialog show = customizeDialog.show();

        dialogView.post(new Runnable() {
            @Override
            public void run() {
                final InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
                editText.setSelection(editText.getText().length());
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = editText.getText().toString();
                onSurePriceListener.onSure(price, show);
            }
        });
    }

    /**
     * @param context
     * @return 若返回true，则表示输入法打开
     */
    public static boolean softInputIsOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static void showMessage(Context context, String title) {
        showMessage(context, title, null);
    }

    public static void showMessage(Context context, String title, View.OnClickListener onSureClickListener) {
        showMessage(context, title, null, null, onSureClickListener, null);
    }

    public static void showMessage(Context context, String title, String sureStr, String cancelStr, final View.OnClickListener onSureClickListener, final View.OnClickListener onCancelClickListener) {
        final ShopDialog shopDialog = new ShopDialog(context);
        if (title != null) shopDialog.setMessage(title);
        if (sureStr != null) shopDialog.setSureBtn(sureStr);
        if (cancelStr != null) shopDialog.setCancelBtn(cancelStr);
        shopDialog.setOnSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopDialog.dismiss();
                if (onSureClickListener == null) return;
                onSureClickListener.onClick(v);
            }
        });
        shopDialog.setOnCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopDialog.dismiss();
                if (onCancelClickListener == null) return;
                onCancelClickListener.onClick(v);
            }
        });
        shopDialog.show();
    }

    public static void showMessage(Context context, String title, String content, final OnOkListener onOkListener) {
        final AlertDialog.Builder loginAgain = new AlertDialog.Builder(context);

        loginAgain.setTitle(title);
        loginAgain.setMessage(content);
        loginAgain.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onOkListener.onSure(which, dialog);
            }
        });
        loginAgain.show();
    }
}
