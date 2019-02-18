package com.laaptu.popmovies.movieslist;

import android.content.Context;
import android.util.AttributeSet;

public class AspectImageView extends android.support.v7.widget.AppCompatImageView {
    private static final float ratio = 1.4f;

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
        int newMeasuredHeight = (int) (getMeasuredHeight() * ratio);
        setMeasuredDimension(getMeasuredWidth(), newMeasuredHeight);
    }
}
