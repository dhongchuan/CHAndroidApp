package com.dhongchuan.chapplication.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.dhongchuan.chapplication.utils.FileCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class ImageLoaderService extends HandlerThread{
    private static final String NAME = "image_load";
    private static final String IMAGE_VIEW_ID_PROMPT = "image_view_id";
    private static final String IMAGE_VIEW_URL_PROMPT = "image_view_url";
    private static final String IMAGE_VIEW_DATA_PROMPT = "image_view_data";
    private static ImageLoaderService sImageLoaderService;
    private static Handler sHandler;


    private Object mLock;
    private Handler mHandleRequest;
    private Handler mHandleCompleteResult;
    private FileCache mFileCache;
    private LIFOThreadPoolProcessor mDownloadProcessor;
    private ConcurrentHashMap<Integer, ImageView> mDestViews;
    private ConcurrentHashMap<ImageView, String> mDestViewsUrlMap;
    private ConcurrentHashMap<ImageView, Future> mDestTaskMap;
    private DownloadImageTask.onCompleteListener mDownloadCompleteListener;

    public ImageLoaderService(Context context) {
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

    public static ImageLoaderService with(Context context){
        if(sImageLoaderService == null){
            sImageLoaderService = new ImageLoaderService(context);
            sImageLoaderService.start();
            sHandler = new Handler(sImageLoaderService.getLooper()){
                @Override
                public void handleMessage(Message msg) {
                    if(sImageLoaderService.getHandleRequest() == null){
                        Log.d("sImageLoaderThread.getHandleRequest()", "null");
                    }
                    sImageLoaderService.getHandleRequest().obtainMessage(msg.what).sendToTarget();
                }
            };
        }
        return sImageLoaderService;
    }

    public void load(ImageView view, String strUrl){
        int viewCode = view.hashCode();
        mDestViewsUrlMap.put(view, strUrl);
        mDestViews.put(viewCode, view);
        sHandler.obtainMessage(viewCode).sendToTarget();
//        mHandleRequest.obtainMessage(viewCode, viewCode).sendToTarget();
    }

    private Handler getHandleRequest(){
        return mHandleRequest;
    }

    @Override
    protected void onLooperPrepared() {
        mHandleRequest = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("onLooperPrepared", "create");
                int key = (Integer) msg.what;//imageView's HashCode
                ImageView view = mDestViews.get(key);//get ImageView
                String url = mDestViewsUrlMap.get(view);//get url

                byte[] cacheData = getDataFromFileCache(url);
                if(cacheData == null || cacheData.length == 0){
                    downloadDataFromNet(key, view, url);
                }else{
                    Message completeMsg = mHandleCompleteResult.obtainMessage();
                    Bundle data = new Bundle();
                    data.putByteArray(IMAGE_VIEW_DATA_PROMPT, cacheData);
                    data.putInt(IMAGE_VIEW_ID_PROMPT, key);
                    data.putString(IMAGE_VIEW_URL_PROMPT, url);
                    completeMsg.setData(data);
                    mHandleCompleteResult.sendMessage(completeMsg);
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
                saveDataToFileCache(url, bytes);
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
        final ImageView view = mDestViews.get(imageID);
        String currentUrl = mDestViewsUrlMap.get(view);
        final Bitmap bitmap = convertBytesToBitmap(data);
        if(currentUrl != null && currentUrl.equals(lastUrl)){
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(bitmap);
                }
            });
        }
        removeData(imageID, view);
    }

    private Bitmap convertBytesToBitmap(byte[] srcData){
        Bitmap destBitmap = BitmapFactory.decodeByteArray(srcData,0, srcData.length);
        return destBitmap;
    }
    private void removeData(int ID, ImageView view) {
        mDestViews.remove(ID);
        mDestTaskMap.remove(view);
        mDestViewsUrlMap.remove(view);
    }

    private void saveDataToFileCache(String url, byte[] data){
        mFileCache.set(url, data);
    }

    private  byte[] getDataFromFileCache(String url){
        return (byte[]) mFileCache.get(url);
    }

}
