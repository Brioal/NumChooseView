package com.brioal.numchoose;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2017/5/18.
 */

public class DialogUtil {
    public static void showTextInput(final Context context, String title, String content, int inputType, final OnStringLoadListener stringLoadListener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_text_input, null);
        TextView tvTitle = contentView.findViewById(R.id.text_input_tv_title);
        final EditText etContent = contentView.findViewById(R.id.text_input_et_content);
        TextView tvCancel = contentView.findViewById(R.id.text_input_tv_cancle);
        TextView tvDone = contentView.findViewById(R.id.text_input_tv_done);
        tvTitle.setText(title);
        etContent.setInputType(inputType);
        etContent.setText(content + "");
        etContent.setSelection(content.length());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dialog.getWindow().getDecorView().getWindowToken(), 0);
                        dialog.dismiss();
                        stringLoadListener.failed("");
                    }
                });
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                if (content.isEmpty()) {
                    stringLoadListener.failed("输入文字不能为空");
                    return;
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dialog.getWindow().getDecorView().getWindowToken(), 0);
                        dialog.dismiss();
                    }
                });

                stringLoadListener.success(content);
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //设置可获得焦点
                        etContent.setFocusable(true);
                        etContent.setFocusableInTouchMode(true);
                        //请求获得焦点
                        etContent.requestFocus();
                        //调用系统输入法
                        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(etContent, 2);
                    }
                });
            }
        });
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_layout_text));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(contentView);
    }

    public static void showTextInput(Context context, String title, String content, final OnStringLoadListener stringLoadListener) {
        showTextInput(context, title, content, InputType.TYPE_CLASS_TEXT, stringLoadListener);
    }
}
