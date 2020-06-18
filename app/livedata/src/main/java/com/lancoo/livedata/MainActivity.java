package com.lancoo.livedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private TestViewModel mTestViewModel;
    private TextView mTvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvName = findViewById(R.id.tv_name);

        mTestViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        MutableLiveData<String> nameEvent = mTestViewModel.getNameEvent();
        nameEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.i(TAG, "onChanged: s = " + s);
                mTvName.setText(s);
            }
        });
    }
}
