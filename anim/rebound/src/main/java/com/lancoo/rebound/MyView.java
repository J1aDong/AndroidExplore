package com.lancoo.rebound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

public class MyView extends View {
    private static final String TAG = "MyView";
    private int mWidth;
    private int mHeight;
    private Point mPonit;
    private int mRadius;
    private Rect mRect;
    private Paint mPaint;
    private boolean canDrag = false;
    private int mOldX;
    private int mOldY;
    private Point mOldPoint;
    private Point mOriPont;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        SpringSystem springSystem = SpringSystem.create();

        Spring spring = springSystem.createSpring();

        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mPonit = new Point(mWidth / 2, mHeight / 2);
        mOriPont = new Point(mWidth / 2, mHeight / 2);
        mRadius = 50;

        Log.w(TAG, String.format("width-->%d,height-->%d", mWidth, mHeight));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画中间的矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(getRect(), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                canDrag = canDrag(x, y);

                mOldX = x;
                mOldY = y;

                mOldPoint = new Point(mPonit.x, mPonit.y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    int newX = x;
                    int newY = y;

                    mPonit.x = mOldPoint.x + newX - mOldX;
                    mPonit.y = mOldPoint.y + newY - mOldY;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                canDrag = false;
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean canDrag(int x, int y) {
        if (Math.abs(x - mPonit.x) < mRadius && Math.abs(y - mPonit.y) < mRadius) {
            return true;
        }
        return false;
    }

    private Rect getRect() {
        if (null == mRect) {
            mRect = new Rect();
        }
        mRect.set(mPonit.x - mRadius, mPonit.y - mRadius, mPonit.x + mRadius, mPonit.y + mRadius);
        return mRect;
    }
}
