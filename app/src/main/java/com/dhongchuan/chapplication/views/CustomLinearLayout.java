package com.dhongchuan.chapplication.views;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by dhongchuan on 15/2/3.
 */
public class CustomLinearLayout extends LinearLayout {
    private int mLastX;
    private int mlastY;
    public CustomLinearLayout(Context context) {
        super(context);
        init();
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mLastX = 0;
        mlastY = 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int orientation = getOrientation();
        if (orientation == VERTICAL){

        }else if(orientation == HORIZONTAL){

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isInterceptTouchEvent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("Action", "Down");
                mLastX = currentX;
                mlastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("Action", "MOVE");
                int deltaX = mLastX - currentX;
                int deltaY = mlastY - currentY;
                scrollBy(deltaX/10, deltaY/10);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("Action", "UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private boolean isInterceptTouchEvent(){
        return false;
    }
}
