package com.lancoo.timeline;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by August on 16/8/20.
 */
public class TimeLineView extends LinearLayout {

    private boolean mIsLeft = true;

    public void addItem(TimeCourseBean timeCourseBean) {

        if (mIsLeft){
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_test, this, false);
            TextView tx_action_left = (TextView) v.findViewById(R.id.tx_action_left);
            TextView tx_action_time_left = (TextView) v.findViewById(R.id.tx_action_time_left);
            TextView tx_action_right = (TextView) v.findViewById(R.id.tx_action_right);
            TextView tx_action_time_right = (TextView) v.findViewById(R.id.tx_action_time_right);
            tx_action_left.setText(timeCourseBean.getCourseName());
            tx_action_time_left.setText(timeCourseBean.getTime());
            tx_action_right.setVisibility(GONE);
            tx_action_time_right.setVisibility(GONE);
            addView(v);
            mIsLeft = !mIsLeft;
        }else {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_test, this, false);
            TextView tx_action_left = (TextView) v.findViewById(R.id.tx_action_left);
            TextView tx_action_time_left = (TextView) v.findViewById(R.id.tx_action_time_left);
            TextView tx_action_right = (TextView) v.findViewById(R.id.tx_action_right);
            TextView tx_action_time_right = (TextView) v.findViewById(R.id.tx_action_time_right);
            tx_action_right.setText(timeCourseBean.getCourseName());
            tx_action_time_right.setText(timeCourseBean.getTime());
            tx_action_left.setVisibility(GONE);
            tx_action_time_left.setVisibility(GONE);
            addView(v);
            mIsLeft = !mIsLeft;
        }
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prePare();
    }

    /**
     * 初始化
     */
    private void prePare() {
        setOrientation(VERTICAL);
    }



    /**
     * dp转px
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}