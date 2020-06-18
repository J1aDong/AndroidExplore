package com.lancoo.customseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MjSeekBar extends View {

    private int mMax = 100;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private Paint mBackGroundPaint;
    private Paint mCirclePaint;

    private Point mPoint = new Point();

    // 当前的进度
    private int mProgress = 0;

    // 进度圆的半径
    private int mRadius = 20;

    private List<Subsection> subsectionList = new ArrayList<>();

    private float oldX;
    private Paint mProgressPaint;
    private Paint mSubsectionPaint;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    public void setMax(int max) {
        this.mMax = max;
    }

    public MjSeekBar(@NonNull Context context) {
        this(context, null);
    }

    public MjSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MjSeekBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mBackGroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackGroundPaint.setColor(Color.BLACK);
        mBackGroundPaint.setStyle(Paint.Style.STROKE);
        mBackGroundPaint.setStrokeWidth(10);
        mBackGroundPaint.setStrokeCap(Paint.Cap.ROUND);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(Color.GREEN);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(10);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mSubsectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSubsectionPaint.setColor(Color.BLUE);
        mSubsectionPaint.setStyle(Paint.Style.STROKE);
        mSubsectionPaint.setStrokeWidth(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                oldX = event.getX();
                if (null != mOnSeekBarChangeListener) {
                    mOnSeekBarChangeListener.onStartTrackingTouch(this);
                }
                limitCircle();
                analyseProgress(true);

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float newX = event.getX();
                mPoint.x = (int) newX;
                limitCircle();
                analyseProgress(true);
                postInvalidate();

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                limitCircle();
                analyseProgress(true);
                if (null != mOnSeekBarChangeListener) {
                    mOnSeekBarChangeListener.onStopTrackingTouch(this);
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    public void setSubsectionList(List<Subsection> subsectionList) {
        if (null == subsectionList || subsectionList.isEmpty()) {
            return;
        }
        this.subsectionList.clear();
        this.subsectionList.addAll(subsectionList);
    }

    public void setProgress(int progress) {
        int x = (int) (progress / (mMax * 1.0) * (getMeasuredWidth() - mRadius * 2) + mRadius);
        mPoint.x = x;
        postInvalidate();
    }

    private void analyseProgress(boolean fromUser) {
        int x = mPoint.x - mRadius;
        int y = getMeasuredWidth() - mRadius * 2;

        int progress = (int) (x / (y * 1.0) * mMax);

        if (progress != mProgress) {
            mProgress = progress;

            if (null != mOnSeekBarChangeListener) {
                mOnSeekBarChangeListener.onProgressChanged(this, mProgress, fromUser);
            }
        }
    }

    // 限制滑动的圆的范围
    private void limitCircle() {
        if (mPoint.x < mRadius) {
            mPoint.x = mRadius;
        }

        if (mPoint.x > getMeasuredWidth() - mRadius) {
            mPoint.x = getMeasuredWidth() - mRadius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (mPoint.x == 0) {
            mPoint.x = mRadius;
            mPoint.y = height / 2;

            mProgress = 0;
        }

        drawLine(canvas, width, height);
        drawProgress(canvas, width, height);
        drawSubsetion(canvas, width, height);
        drawCircle(canvas, width, height);
    }

    private void drawSubsetion(Canvas canvas, int width, int height) {
        if (!subsectionList.isEmpty()) {
            for (int i = 0; i < subsectionList.size(); i++) {
                Subsection subsection = subsectionList.get(i);

                int startX = (int) ((getMeasuredWidth() - mRadius * 2) * subsection.getStartProgress() + mRadius);
                int endX = (int) ((getMeasuredWidth() - mRadius * 2) * subsection.getEndProgress() + mRadius);
                int y = getHeight() / 2;

                canvas.drawLine(startX, y, endX, y, mSubsectionPaint);
            }
        }
    }

    private void drawProgress(Canvas canvas, int width, int height) {
        canvas.drawLine(mRadius, height / 2, mPoint.x, height / 2, mProgressPaint);
    }

    private void drawCircle(Canvas canvas, int width, int height) {
        canvas.drawCircle(mPoint.x, height / 2, mRadius, mCirclePaint);
    }

    private void drawLine(Canvas canvas, int width, int height) {
        canvas.drawLine(mRadius, height / 2, width - mRadius, height / 2, mBackGroundPaint);
    }

    public interface OnSeekBarChangeListener {

        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param seekBar  The SeekBar whose progress has changed
         * @param progress The current progress level. This will be in the range min..max where min
         *                 and max were set by {@link ProgressBar#setMin(int)} and
         *                 {@link ProgressBar#setMax(int)}, respectively. (The default values for
         *                 min is 0 and max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(MjSeekBar seekBar, int progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStartTrackingTouch(MjSeekBar seekBar);

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStopTrackingTouch(MjSeekBar seekBar);
    }

    public static class Subsection {
        private double startProgress;
        private double endProgress;

        public Subsection(double startProgress, double endProgress) {
            this.startProgress = startProgress;
            this.endProgress = endProgress;
        }

        public double getStartProgress() {
            return startProgress;
        }

        public void setStartProgress(double startProgress) {
            this.startProgress = startProgress;
        }

        public double getEndProgress() {
            return endProgress;
        }

        public void setEndProgress(double endProgress) {
            this.endProgress = endProgress;
        }
    }

}
