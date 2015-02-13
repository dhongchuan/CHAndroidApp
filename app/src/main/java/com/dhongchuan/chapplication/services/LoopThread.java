package com.dhongchuan.chapplication.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.dhongchuan.chapplication.base.ICache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created by hongchuan.du on 2015/2/13.
 */
public class LoopThread extends HandlerThread{
    private static final String TAG = "Loop_Thread";
    private static final String IMAGE_VIEW_ID_PROMPT = "image_view_id";
    private static final String IMAGE_VIEW_URL_PROMPT = "image_view_url";
    private static final String IMAGE_VIEW_DATA_PROMPT = "image_view_data";

    private Handler mHandleRequest;
    private Handler mHandleCompleteResult;
    private ICache mFileCache;
    private LIFOThreadPoolProcessor mDownloadProcessor;
    private ConcurrentHashMap<Integer, ImageView> mDestViews;
    private ConcurrentHashMap<ImageView, String> mDestViewsUrlMap;
    private ConcurrentHashMap<ImageView, Future> mDestTaskMap;
    private DownloadImageTask.onCompleteListener mDownloadCompleteListener;
    public LoopThread() {
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        Log.d("onLooperPrepared", "ok");
        mHandleRequest = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int key = (Integer) msg.obj;//imageView's HashCode
                ImageView view = mDestViews.get(key);//get ImageView
                String url = mDestViewsUrlMap.get(view);//get url
                DownloadImageTask downloadImageTask = new DownloadImageTask(key, url);//create task
                downloadImageTask.setCompleteListener(mDownloadCompleteListener);
                Future future = mDownloadProcessor.submitTask(downloadImageTask);
                mDestTaskMap.put(view, future);//
                msg.recycle();
            }
        };
        mHandleCompleteResult = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int id = data.getInt(IMAGE_VIEW_ID_PROMPT);
                String url = data.getString(IMAGE_VIEW_URL_PROMPT);
                byte[] bytes = data.getByteArray(IMAGE_VIEW_DATA_PROMPT);
//                showImage(id, url,bytes);
                msg.recycle();
            }
        };
    }
}
