package com.dhongchuan.chapplication.services;

import android.widget.ImageView;

import com.dhongchuan.chapplication.utils.NetUtil;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class DownloadImageTask implements Runnable {
    private ImageView mDestView;
    private String mImageURL;

    private onCompleteListener mCompleteListener;

    public interface onCompleteListener{
        public void handleReslut(ImageView imageView, byte[] bytes);
    }

    public DownloadImageTask(ImageView destView, String url){
        this.mDestView = destView;
        this.mImageURL = url;
    }

    public void setCompleteListener(onCompleteListener completeListener){
        this.mCompleteListener = completeListener;
    }

    public void reset(){
        mCompleteListener = null;
        mDestView = null;
        mImageURL = "";
    }

    @Override
    public void run() {
        byte[] byteArrImage = NetUtil.getFileBytesByUrl(mImageURL);
        if(mCompleteListener != null){
            mCompleteListener.handleReslut(mDestView, byteArrImage);
        }
        reset();
    }
}
