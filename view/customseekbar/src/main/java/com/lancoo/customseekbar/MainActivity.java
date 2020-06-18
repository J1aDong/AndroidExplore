package com.lancoo.customseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MjSeekBar mj_seek_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mj_seek_bar = findViewById(R.id.mj_seek_bar);

        mj_seek_bar.setMax(1000);

        List<MjSeekBar.Subsection> list = new ArrayList<>();

        list.add(new MjSeekBar.Subsection(0.1,0.2));
        list.add(new MjSeekBar.Subsection(0.3,0.5));
        list.add(new MjSeekBar.Subsection(0.8,0.9));

        mj_seek_bar.setSubsectionList(list);

        mj_seek_bar.setOnSeekBarChangeListener(new MjSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(MjSeekBar seekBar, int progress, boolean fromUser) {
                Log.w(TAG, "onProgressChanged >>progress>>" + progress + ">>fromUser>>" + fromUser);
            }

            @Override
            public void onStartTrackingTouch(MjSeekBar seekBar) {
                Log.w(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(MjSeekBar seekBar) {
                Log.w(TAG, "onStopTrackingTouch");
            }
        });
    }
}
