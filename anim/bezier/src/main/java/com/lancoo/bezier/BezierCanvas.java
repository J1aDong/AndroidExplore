package com.lancoo.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BezierCanvas extends View {
    private final Paint mPaint;
    private final ArrayList<Point> mPoints;
    private final Path mPath;

    public BezierCanvas(Context context) {
        this(context, null);
    }

    public BezierCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        mPoints = new ArrayList<Point>();
        mPaint.setColor(Color.BLACK);

        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        for (Point point : mPoints) {
//            canvas.drawCircle(point.x,point.y,10,mPaint);
//        }

        for (int i = 0; i < mPoints.size(); i++) {
            Point point = mPoints.get(i);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(point.x, point.y, 10, mPaint);

            if (i - 2 >= 0) {
                Point p3 = mPoints.get(i);
                Point p2 = mPoints.get(i - 1);
                Point p1 = mPoints.get(i - 2);
                mPath.reset();
                mPath.moveTo(p1.x, p1.y);
                mPath.quadTo(p2.x, p2.y, p3.x, p3.y);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(mPath, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mPoints.add(new Point(x, y));
                postInvalidate();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }
}
