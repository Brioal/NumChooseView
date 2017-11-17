package com.brioal.numchoose;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2017/5/17.
 */

public class NumChooseView extends LinearLayout {
    private TextView mBtnDec;//减少的按钮
    private TextView mTvNum;//显示数量的文本
    private TextView mBtnAdd;//添加的按钮
    private Context mContext;
    private int mNum = 1;
    private int mMax = Integer.MAX_VALUE;//设置最大值
    private int mMin = 1;//设置最小值
    private OnNumChangeListener mOnNumChangeListener;

    public NumChooseView(Context context) {
        this(context, null);
    }

    public NumChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    //返回当前值
    public int getNum() {
        return mNum;
    }

    //设置当前值
    public NumChooseView setNum(int num) {
        mNum = num;
        return this;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public int getMin() {
        return mMin;
    }

    /**
     * 设置最小值
     *
     * @param min
     */
    public void setMin(int min) {
        mMin = min;
    }

    //获取最大值
    public int getMax() {
        return mMax;
    }

    //设置最大值
    public NumChooseView setMax(int max) {
        mMax = max;
        return this;
    }

    //设置监听器
    public NumChooseView setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        mOnNumChangeListener = onNumChangeListener;
        return this;
    }

    //初始化
    public void init() {
        removeAllViews();
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.WHITE);
        //创建减号
        mBtnDec = new TextView(getContext());
        mBtnDec.setText("-");
        mBtnDec.setPadding(30, 10, 30, 10);
        mBtnDec.setGravity(Gravity.CENTER);
        mBtnDec.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mNum = Integer.parseInt(mTvNum.getText().toString());
                if (mNum <= mMin) {
                    Toast.makeText(mContext, "已达到可选的最小值", Toast.LENGTH_SHORT).show();
                    return;
                }
                mNum--;
                mTvNum.setText(mNum + "");
                if (mOnNumChangeListener != null) {
                    mOnNumChangeListener.changed(mNum);
                }
            }
        });
        mBtnDec.setBackground(mContext.getResources().getDrawable(R.drawable.ic_bgwhite_round_bg));
        LayoutParams paramsDec = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsDec.weight = 1;
        addView(mBtnDec, paramsDec);
        //创建文本
        mTvNum = new TextView(mContext);
        mTvNum.setText(mNum + "");
        mTvNum.setSingleLine();
        mTvNum.setTextColor(Color.BLACK);
        mTvNum.setPadding(50, 10, 50, 10);
        mTvNum.setGravity(Gravity.CENTER);
        mTvNum.setBackground(mContext.getResources().getDrawable(R.drawable.ic_bgwhite_rect_bg));
        LayoutParams paramsNum = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsNum.weight = 2;
        addView(mTvNum, paramsNum);
        //直接修改事件
        mTvNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showTextInput(mContext, "请输入数量", mNum + "", InputType.TYPE_CLASS_NUMBER, new OnStringLoadListener() {
                    @Override
                    public void success(String content) {
                        mNum = Integer.parseInt(content);
                        if (mNum < mMin) {
                            Toast.makeText(mContext, "已达到可选的最小值", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mNum > mMax) {
                            Toast.makeText(mContext, "已达到可选的最大值", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            mNum = Integer.parseInt(content);
                            mTvNum.setText(mNum + "");
                        } catch (Exception e) {
                            Toast.makeText(mContext, "输入内容错误", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            mTvNum.setText(mNum + "");
                        }
                        if (mOnNumChangeListener != null) {
                            mOnNumChangeListener.changed(mNum);
                        }
                    }

                    @Override
                    public void failed(String errorMsg) {

                    }
                });
            }
        });
        //创建加号
        mBtnAdd = new TextView(mContext);
        mBtnAdd.setText("+");
        mBtnAdd.setPadding(30, 10, 30, 10);
        mBtnAdd.setGravity(Gravity.CENTER);
        mBtnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mNum = Integer.parseInt(mTvNum.getText().toString());
                if (mNum < mMax) {
                    mNum++;
                    mTvNum.setText(mNum + "");
                    if (mOnNumChangeListener != null) {
                        mOnNumChangeListener.changed(mNum);
                    }
                } else {
                    Toast.makeText(mContext,"超过最大值",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnAdd.setBackground(mContext.getResources().getDrawable(R.drawable.ic_bgwhite_round_bg));
        LayoutParams paramsAdd = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsAdd.weight = 1;
        addView(mBtnAdd, paramsAdd);
    }


    public interface OnNumChangeListener {
        void changed(int num);
    }
}
