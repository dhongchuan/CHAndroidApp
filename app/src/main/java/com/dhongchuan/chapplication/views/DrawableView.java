package com.dhongchuan.chapplication.views;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * Created by dhongchuan on 15/2/2.
 */
public class DrawableView extends Drawable implements Drawable.Callback, Animatable{
    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    //callBack

    @Override
    public void invalidateDrawable(Drawable drawable) {

    }

    @Override
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {

    }

    @Override
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {

    }

    //animatable

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
