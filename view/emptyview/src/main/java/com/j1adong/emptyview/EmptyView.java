package com.j1adong.emptyview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmptyView extends FrameLayout {
    private LinearLayout ll_tyjx_error_view_error;
    private ImageView iv_tyjx_error_view_errorimg;
    private TextView tv_tyjx_error_view_errorflag;
    private TextView tv_tyjx_error_view_errorrefresh;
    private LinearLayout ll_tyjx_empty_view_empty;
    private ImageView iv_tyjx_empty_view_emptyimg;
    private TextView tv_tyjx_empty_view_emptyflag;

    OnClickEmptyViewListener onClickEmptyViewListener;

    public void hide() {
        this.setVisibility(INVISIBLE);
    }

    public interface OnClickEmptyViewListener{
        void clickRefresh();
    }

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.tyjx_customview_empty_view, null);

        initViews(view);

        addView(view);

        hide();
    }

    private void initViews(View view) {
        ll_tyjx_error_view_error = view.findViewById(R.id.ll_tyjx_error_view_error);
        iv_tyjx_error_view_errorimg = view.findViewById(R.id.iv_tyjx_error_view_errorimg);
        tv_tyjx_error_view_errorflag = view.findViewById(R.id.tv_tyjx_error_view_errorflag);
        tv_tyjx_error_view_errorrefresh = view.findViewById(R.id.tv_tyjx_error_view_errorrefresh);

        ll_tyjx_empty_view_empty = view.findViewById(R.id.ll_tyjx_empty_view_empty);
        iv_tyjx_empty_view_emptyimg = view.findViewById(R.id.iv_tyjx_empty_view_emptyimg);
        tv_tyjx_empty_view_emptyflag = view.findViewById(R.id.tv_tyjx_empty_view_emptyflag);

        tv_tyjx_error_view_errorrefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onClickEmptyViewListener){
                    onClickEmptyViewListener.clickRefresh();
                }
            }
        });
    }

    public void showError(OnClickEmptyViewListener onClickEmptyViewListener) {
        this.setVisibility(VISIBLE);
        this.onClickEmptyViewListener = onClickEmptyViewListener;

        ll_tyjx_empty_view_empty.setVisibility(INVISIBLE);
        ll_tyjx_error_view_error.setVisibility(VISIBLE);
    }

    public void showEmpty(String title) {
        this.setVisibility(VISIBLE);
        ll_tyjx_empty_view_empty.setVisibility(VISIBLE);
        ll_tyjx_error_view_error.setVisibility(INVISIBLE);

        if (!TextUtils.isEmpty(title)){
            tv_tyjx_empty_view_emptyflag.setText(title);
        }else {
            tv_tyjx_empty_view_emptyflag.setText("没有内容");
        }
    }
}
