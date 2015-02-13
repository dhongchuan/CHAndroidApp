package com.dhongchuan.chapplication.services;

import android.util.Log;
import android.widget.ImageView;

import com.dhongchuan.chapplication.base.BaseLIFOTask;
import com.dhongchuan.chapplication.utils.FileCache;
import com.dhongchuan.chapplication.utils.NetUtil;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class DownloadImageTask extends BaseLIFOTask implements Runnable {
    private int mDestViewID;
    private String mImageURL;
    private FileCache mFileCache;

    private onCompleteListener mCompleteListener;

    public interface onCompleteListener{
        public void handleReslut(int imageViewID, String url, byte[] bytes);
    }

    public DownloadImageTask(int destViewID, String url){
        this.mDestViewID = destViewID;
        this.mImageURL = url;
    }

    public void setCompleteListener(onCompleteListener completeListener){
        this.mCompleteListener = completeListener;
    }

    public void reset(){
        mCompleteListener = null;
        mDestViewID = 0;
        mImageURL = "";
    }

    @Override
    public void run() {
        byte[] byteArrImage = NetUtil.getFileBytesByUrl(mImageURL);
        Log.d("downTask", mImageURL);
        if(mCompleteListener != null){
            mCompleteListener.handleReslut(mDestViewID, mImageURL, byteArrImage);
        }
        reset();
    }
}
