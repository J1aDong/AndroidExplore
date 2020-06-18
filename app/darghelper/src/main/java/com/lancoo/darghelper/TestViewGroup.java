package com.lancoo.darghelper;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

public class TestViewGroup extends FrameLayout {
    private static final String TAG = "TestViewGroup";

    private ViewDragHelper mDragHelper;
    private int mDragOriLeft;
    private int mDragOriTop;

    public TestViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public TestViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            //但拖拽状态改变的时候会触发这个方法，比如：从一开始不能拖拽，到拖拽
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            //关键方法：顾名思义，这里就是记录了一些值得变化，可用于我们处理其他的操作，比如，改变背景颜色
            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            //这个方法很少用到，意义不大，可忽略
            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mDragOriLeft = capturedChild.getLeft();
                mDragOriTop = capturedChild.getTop();
            }

            //关键方法：重新定位水平移动的位置，返回left表示不受限制
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            //关键方法：重新定位垂直移动的位置，返回top表示不受限制
            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            // 边缘设置不可用，
            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            // 关键方法：设置水平拖动的距离
            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return super.getViewHorizontalDragRange(child);
            }

            // 关键方法：设置垂直拖动的距离 0 表示不可拖动
            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return super.getViewVerticalDragRange(child);
            }

            // 给自定义的ViewGroup中的字View 从新排序
            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }

            //这个方法也很少用到
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            // 当边缘被触摸的时候调用
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            //关键方法，手指松开会触发这个方法，做复位操作就在此方法中实现
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

//                mDragHelper.settleCapturedViewAt(mDragOriLeft, mDragOriTop);
//                invalidate();
                View child = getChildAt(0);
                if ( child != null && child == releasedChild ) {
                    mDragHelper.flingCapturedView(getPaddingLeft(),getPaddingTop(),
                            getWidth()-getPaddingRight()-child.getWidth(),
                            getHeight()-getPaddingBottom()-child.getHeight());
                } else {
                    mDragHelper.settleCapturedViewAt((int)mDragOriLeft,(int)mDragOriTop);
                }
                invalidate();
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null != mDragHelper && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }
}
