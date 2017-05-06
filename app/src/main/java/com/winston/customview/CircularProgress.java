package com.winston.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.math.MathUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import static android.content.ContentValues.TAG;

/**
 * Created by LKW on 5/5/2017.
 */
public class CircularProgress extends View {

    float mProgressValue;
    Paint mPaint;
    RectF mOval;
    boolean mAnimateOnDisplay;

    public CircularProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttribute(attrs);
        init();
    }

    private void loadAttribute(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.CircularProgress, 0, 0);
        try {
            mProgressValue = a.getFloat(R.styleable.CircularProgress_progress, 0);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        mAnimateOnDisplay = false;
                mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        int dpSize = 5;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, dm);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOval = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
        float h = canvas.getHeight() - getPaddingTop() - getPaddingBottom();
        float radius = Math.min(w, h) / 2.0f;
        float left = getPaddingLeft();
        float top = getPaddingTop();
        mPaint.setAlpha(100);
        canvas.drawCircle(left + radius, top + radius, radius, mPaint);
        mPaint.setAlpha(250);

        Path p = new Path();
        p.addArc(mOval, -90, mAnimateOnDisplay ? 360 * mProgressValue : 0.0f);
        canvas.drawPath(p, mPaint);
    }

    public void showAnimation() {
        mAnimateOnDisplay = true;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progressValue", mProgressValue);
        objectAnimator.setDuration(500);
        objectAnimator.setStartDelay(100);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public void setProgressValue(float value) {
        Log.e(TAG, "setCurrentValue: " + mProgressValue);
        mProgressValue = MathUtils.clamp(value, 0, 1);
        postInvalidate();
    }

}
