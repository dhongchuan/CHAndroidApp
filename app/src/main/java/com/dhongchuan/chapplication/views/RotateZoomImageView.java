package com.dhongchuan.chapplication.views;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by hongchuan.du on 2015/2/28.
 */
public class RotateZoomImageView extends ImageView {
    private ScaleGestureDetector mScaleDetector;
    private ScaleGestureDetector.SimpleOnScaleGestureListener mScaleListener;

    private Matrix mImageMatrix;
    /* Last Rotation Angle */
    private int mLastAngle = 0;
    /* Pivot Point for Transforms */
    private int mPivotX, mPivotY;

    public RotateZoomImageView(Context context) {
        this(context, null);
    }

    public RotateZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                //Pass that factor to a scale for the image
                mImageMatrix.postScale(scaleFactor, scaleFactor, mPivotX, mPivotY);
                setImageMatrix(mImageMatrix);
                return true;
            }
        };
        mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
        setScaleType(ScaleType.MATRIX);
        mImageMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            return true;
        }
        switch (event.getPointerCount()){
            case 3:
                return mScaleDetector.onTouchEvent(event);
            case 2:
                doRotationEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    private boolean doRotationEvent(MotionEvent event) {
        return true;
    }
}
