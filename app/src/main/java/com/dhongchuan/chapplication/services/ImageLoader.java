package com.dhongchuan.chapplication.services;

import android.content.Context;
import android.os.HandlerThread;
import android.widget.ImageView;

import com.dhongchuan.chapplication.base.ICache;
import com.dhongchuan.chapplication.utils.FileCache;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class ImageLoader {
    private static ImageLoader sImageLoader;

    private HandlerThread mHanleLoadingThread;
    private ICache mFileCache;
    private ConcurrentHashMap mDestViewsMap;

    private ImageLoader(Context context){
        mFileCache = new FileCache(context);
    }

    public ImageLoader with(Context context){
        if(sImageLoader == null){
            sImageLoader = new ImageLoader(context);
        }
        return sImageLoader;
    }

    public void load(ImageView view, String strUrl){

    }
}
