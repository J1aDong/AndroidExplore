package com.j1adong.emptyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EmptyView empty_view;
    private TextView tv_hide;
    private TextView tv_error;
    private TextView tv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        init();
    }

    private void init() {
        tv_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_view.hide();
            }
        });

        tv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_view.showError(new EmptyView.OnClickEmptyViewListener() {
                    @Override
                    public void clickRefresh() {
                        empty_view.hide();
                    }
                });
            }
        });

        tv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_view.showEmpty("");
            }
        });
    }

    private void initViews() {
        empty_view = findViewById(R.id.empty_view);
        tv_hide = findViewById(R.id.tv_hide);
        tv_error = findViewById(R.id.tv_error);
        tv_empty = findViewById(R.id.tv_empty);
    }
}
