package com.laaptu.popmovies.movieslist.presentation;

import android.content.Context;
import android.util.AttributeSet;

public class AspectImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final float ratio = 1.4f;
    private int newMeasuredHeight = -1;

    public AspectImageView(Context context) {
        super(context);
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (newMeasuredHeight < 0) {
            newMeasuredHeight = (int) (getMeasuredHeight() * ratio);
            setMeasuredDimension(getMeasuredWidth(), newMeasuredHeight);
        }
    }
}
