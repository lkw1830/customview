package com.winston.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.math.MathUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by LKW on 5/5/2017.
 */
public class HorizontalBar extends View {
    private Float mPercentage;
    private int mBackground;
    private Paint mPaint;

    public HorizontalBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttribute(attrs);
        init();
    }

    private void loadAttribute(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.HorizontalBar, 0, 0);
        try {
            mPercentage = a.getFloat(R.styleable.HorizontalBar_percentage, 0);
            mBackground = a.getColor(R.styleable.HorizontalBar_backgroundColor, Color.GRAY);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mBackground);
        canvas.drawRect(0, getTop(), getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, getTop(), mPercentage * getWidth(), getHeight(), mPaint);
    }

    public void showAnimation(float newPercentage) {
        newPercentage = MathUtils.clamp(newPercentage, 0, 1);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "percentage", newPercentage);
        objectAnimator.setDuration(500);
        objectAnimator.setStartDelay(100);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getPercentage() {
        return mPercentage;
    }

    public void setPercentage(float value) {
        mPercentage = MathUtils.clamp(value, 0, 1);
        postInvalidate();
    }

}
