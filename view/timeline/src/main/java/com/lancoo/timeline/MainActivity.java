package com.lancoo.timeline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TimeLineView tlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tlv = (TimeLineView) findViewById(R.id.tlv);
    }

    private void addItem() {
        tlv.addItem(new TimeCourseBean("数学","16:00","", TimeCourseBean.ClassState.STATE_NOT_START));
    }

    public void addView(View v) {
        addItem();
    }

    public void removeView(View v) {
        if (tlv.getChildCount() != 0) {
            tlv.removeViewAt(tlv.getChildCount() - 1);
        }
    }
}
