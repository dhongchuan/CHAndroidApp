package com.dhongchuan.chapplication.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.dhongchuan.chapplication.R;
import com.dhongchuan.chapplication.base.ICache;
import com.dhongchuan.chapplication.utils.FileCache;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class ImageLoader extends HandlerThread{
    private static final String NAME = "image_load";
    private static final String IMAGE_VIEW_ID_PROMPT = "image_view_id";
    private static final String IMAGE_VIEW_URL_PROMPT = "image_view_url";
    private static final String IMAGE_VIEW_DATA_PROMPT = "image_view_data";
    private static ImageLoader sImageLoader;

    private Object mLock;
    private Handler mHandleRequest;
    private Handler mHandleCompleteResult;
    private ICache mFileCache;
    private LIFOThreadPoolProcessor mDownloadProcessor;
    private ConcurrentHashMap<Integer, ImageView> mDestViews;
    private ConcurrentHashMap<ImageView, String> mDestViewsUrlMap;
    private ConcurrentHashMap<ImageView, Future> mDestTaskMap;
    private DownloadImageTask.onCompleteListener mDownloadCompleteListener;

    private ImageLoader(Context context) {
        super(NAME);
        mLock = new Object();
        mDownloadProcessor = new LIFOThreadPoolProcessor(5);

        mFileCache = FileCache.obtain(context);
        mDestViews = new ConcurrentHashMap<Integer, ImageView>(10);
        mDestViewsUrlMap = new ConcurrentHashMap<ImageView, String>(10);
        mDestTaskMap = new ConcurrentHashMap<ImageView, Future>(10);
        mDownloadCompleteListener = new DownloadImageTask.onCompleteListener() {
            @Override
            public void handleReslut(int imageViewID, String url, byte[] bytes) {
                synchronized (mLock){
                    Message msg = mHandleCompleteResult.obtainMessage();
                    Bundle data = new Bundle();
                    data.putByteArray(IMAGE_VIEW_DATA_PROMPT, bytes);
                    data.putInt(IMAGE_VIEW_ID_PROMPT, imageViewID);
                    data.putString(IMAGE_VIEW_URL_PROMPT, url);
                    msg.setData(data);
                    mHandleCompleteResult.sendMessage(msg);
                }
            }
        };
    }

    public static ImageLoader with(Context context){
        if(sImageLoader == null){
            sImageLoader = new ImageLoader(context);
            sImageLoader.start();
            sImageLoader.getLooper();
        }
        return sImageLoader;
    }

    public void load(ImageView view, String strUrl){
        int id = view.hashCode();
        Log.d("id", String.valueOf(id));
        mDestViewsUrlMap.put(view, strUrl);
        mDestViews.put(id, view);
        if(mHandleRequest == null){
            Log.d("mHandleRequest", "null");
            mHandleRequest = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int key = (Integer) msg.obj;//imageView's HashCode
                    ImageView view = mDestViews.get(key);//get ImageView
                    String url = mDestViewsUrlMap.get(view);//get url
                    DownloadImageTask downloadImageTask = new DownloadImageTask(key, url);//create task
                    downloadImageTask.setCompleteListener(mDownloadCompleteListener);
                    Future future = mDownloadProcessor.submitTask(downloadImageTask);
                    Log.d("future", String.valueOf(future.hashCode()));
                    mDestTaskMap.put(view, future);//
//                msg.recycle();
                }
            };
        }

        mHandleRequest.obtainMessage(id, id).sendToTarget();
    }

    @Override
    protected void onLooperPrepared() {
        mHandleRequest = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int key = (Integer) msg.obj;//imageView's HashCode
                ImageView view = mDestViews.get(key);//get ImageView
                String url = mDestViewsUrlMap.get(view);//get url

                byte[] cacheData = getDataFromFileCache(url);
                if(cacheData == null || cacheData.length == 0){
                    downloadDataFromNet(key, view, url);
                }else{
                    Log.d("cacheData", String.valueOf(cacheData.length));
                    Message completeMsg = mHandleCompleteResult.obtainMessage();
                    Bundle data = new Bundle();
                    data.putByteArray(IMAGE_VIEW_DATA_PROMPT, cacheData);
                    data.putInt(IMAGE_VIEW_ID_PROMPT, key);
                    data.putString(IMAGE_VIEW_URL_PROMPT, url);
                    completeMsg.setData(data);
                    completeMsg.sendToTarget();
                }
            }
        };
        mHandleCompleteResult = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int id = data.getInt(IMAGE_VIEW_ID_PROMPT);
                String url = data.getString(IMAGE_VIEW_URL_PROMPT);
                byte[] bytes = data.getByteArray(IMAGE_VIEW_DATA_PROMPT);
                showImage(id, url,bytes);
            }
        };
    }

    private void downloadDataFromNet(int key, ImageView view, String url) {
        DownloadImageTask downloadImageTask = new DownloadImageTask(key, url);//create task
        downloadImageTask.setCompleteListener(mDownloadCompleteListener);
        Future future = mDownloadProcessor.submitTask(downloadImageTask);
        mDestTaskMap.put(view, future);//
    }

    private void showImage(int imageID, String lastUrl, byte[] data){
        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        final ImageView view = mDestViews.get(imageID);
//        Future future = mDestTaskMap.get(view);
        String currentUrl = mDestViewsUrlMap.get(view);
        if(currentUrl.equals(lastUrl)){
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(bitmap);
                }
            });
        }
        mFileCache.set(currentUrl,data);
        Log.d("showImage", "ok");
        removeData(imageID, view);
    }
    private void removeData(int ID, ImageView view) {
        mDestViews.remove(ID);
        mDestTaskMap.remove(view);
        mDestViewsUrlMap.remove(view);
    }

    private  byte[] getDataFromFileCache(String url){
        return (byte[]) mFileCache.get(url);
    }

}
