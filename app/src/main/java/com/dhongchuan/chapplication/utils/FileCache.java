package com.dhongchuan.chapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.dhongchuan.chapplication.base.ICache;

import java.io.File;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class FileCache implements ICache<String, Bitmap> {
    private File mCache;

    public FileCache(Context context){

    }
    @Override
    public void set(String key, Bitmap value) {

    }

    @Override
    public Bitmap get(String key, Bitmap value) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void deleteAll() {

    }
}
